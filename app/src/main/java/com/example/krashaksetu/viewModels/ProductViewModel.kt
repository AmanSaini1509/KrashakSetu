package com.example.krashaksetu.viewModels

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.krashaksetu.components.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow

class ProductViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    val productList = MutableStateFlow<List<Product>>(listOf())
    val isLoading = MutableStateFlow(false)
    val validationError = MutableStateFlow<String?>(null)

    var name = mutableStateOf("")
    var price = mutableStateOf("")
    var quantity = mutableStateOf("")
    var category = mutableStateOf("")
    var description = mutableStateOf("")
    var imageUri = mutableStateOf<String?>(null)
    val isEditMode = mutableStateOf(false)
    private var editingProductId: String? = null

    // Fetch products from all subcollections
    fun fetchProducts() {
        isLoading.value = true
        firestore.collection("crops_data").get()
            .addOnSuccessListener { snapshot ->
                val tasks = snapshot.documents.map { parentDoc ->
                    firestore.collection("crops_data")
                        .document(parentDoc.id)
                        .collection("products")
                        .get()
                        .continueWith { subSnapshot ->
                            subSnapshot.result?.documents?.mapNotNull { subDoc ->
                                Product(
                                    id = subDoc.id,
                                    name = subDoc.getString("name") ?: "",
                                    price = subDoc.getString("price") ?: "",
                                    quantity = subDoc.getString("quantity") ?: "",
                                    category = subDoc.getString("category") ?: "",
                                    description = subDoc.getString("description") ?: "",
                                    imageUri = subDoc.getString("image") ?: ""
                                )
                            }
                        }
                }

                Tasks.whenAllComplete(tasks).addOnCompleteListener {
                    productList.value = tasks.flatMap { it.result as List<Product> }
                    isLoading.value = false
                }
            }
            .addOnFailureListener {
                isLoading.value = false
                validationError.value = "Failed to fetch products: ${it.message}"
            }
    }

    // Add or Update a Product
    fun saveProduct(parentId: String) {
        val error = validateFields()
        if (error != null) {
            validationError.value = error
            return
        }

        val productData = hashMapOf(
            "name" to name.value,
            "price" to price.value,
            "quantity" to quantity.value,
            "category" to category.value,
            "description" to description.value
        )

        if (imageUri.value != null) {
            val imageName = "${System.currentTimeMillis()}.jpg"
            val storageRef = storage.reference.child("productImages/$imageName")

            storageRef.putFile(Uri.parse(imageUri.value!!))
                .continueWithTask { storageRef.downloadUrl }
                .addOnSuccessListener { downloadUrl ->
                    productData["image"] = downloadUrl.toString()
                    saveToFirestore(parentId, productData)
                }
                .addOnFailureListener {
                    validationError.value = "Failed to upload image: ${it.message}"
                }
        } else {
            saveToFirestore(parentId, productData)
        }
    }

    private fun saveToFirestore(parentId: String, productData: HashMap<String, Any>) {
        val collectionRef = firestore.collection("crops_data")
            .document(parentId)
            .collection("products")

        val task = if (isEditMode.value && editingProductId != null) {
            collectionRef.document(editingProductId!!).set(productData)
        } else {
            collectionRef.add(productData)
        }

        task.addOnCompleteListener { fetchProducts() }
            .addOnFailureListener {
                validationError.value = "Failed to save product: ${it.message}"
            }
            .addOnSuccessListener {
                resetFields()
            }
    }

    // Delete a product
    fun deleteProduct(parentId: String, productId: String) {
        firestore.collection("crops_data")
            .document(parentId)
            .collection("products")
            .document(productId)
            .delete()
            .addOnCompleteListener { fetchProducts() }
            .addOnFailureListener {
                validationError.value = "Failed to delete product: ${it.message}"
            }
    }

    // Set up a product for editing
    fun setProductForEdit(product: Product) {
        editingProductId = product.id
        name.value = product.name
        price.value = product.price
        quantity.value = product.quantity
        category.value = product.category
        description.value = product.description
        imageUri.value = product.imageUri
        isEditMode.value = true
    }

    // Reset form fields
    fun resetFields() {
        name.value = ""
        price.value = ""
        quantity.value = ""
        category.value = ""
        description.value = ""
        imageUri.value = null
        isEditMode.value = false
        editingProductId = null
        validationError.value = null
    }

    // Validate form fields
    private fun validateFields(): String? {
        return when {
            name.value.isBlank() -> "Product name is required"
            price.value.isBlank() -> "Price is required"
            quantity.value.isBlank() -> "Quantity is required"
            category.value.isBlank() -> "Category is required"
            description.value.isBlank() -> "Description is required"
            else -> null
        }
    }
}

