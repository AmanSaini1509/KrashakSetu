package com.example.krashaksetu.components

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.krashaksetu.viewModels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(productViewModel: ProductViewModel,
                     onSaveProduct: () -> Unit, localizedContext: Context) {

   // val isEditMode = productViewModel.isEditMode.value// Edit mode if the name is pre-filled
//    val imageUri by productViewModel.imageUri.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val validationError by productViewModel.validationError.collectAsState()
    LaunchedEffect(validationError) {
        // Show the Snackbar whenever there's a new validation error
        validationError?.let { error ->
            snackbarHostState.showSnackbar(error)
            productViewModel.validationError.value = null // Reset the error after showing
        }
    }// Observe validation error state

    val currentContext = LocalContext.current
    val activity = currentContext as? androidx.activity.ComponentActivity?: throw IllegalStateException("AddProductScreen must be hosted in an activity")
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        productViewModel.imageUri.value = uri?.toString()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Product", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFF2E7D32))
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Attach the SnackbarHost
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                //.padding(16.dp)
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (productViewModel.imageUri.value != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = productViewModel.imageUri.value),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
            } else {
                TextButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Pick Image", color =  Color(0xFF2E7D32))
                }
            }

            CustomTextField(value = productViewModel.name.value, onValueChange = { productViewModel.name.value = it }, label = "Product Name")
            CustomTextField(
                value = productViewModel.price.value,
                onValueChange = { productViewModel.price.value = it },
                label = "Price",
                keyboardType = KeyboardType.Number
            )
            CustomTextField(
                value = productViewModel.quantity.value,
                onValueChange = { productViewModel.quantity.value = it },
                label = "Quantity",
                keyboardType = KeyboardType.Number
            )
            CustomTextField(value = productViewModel.category.value, onValueChange = { productViewModel.category.value = it }, label = "Category")
            CustomTextField(value = productViewModel.description.value, onValueChange = { productViewModel.description.value = it }, label = "Description")
            Button(
                onClick = {
                    if (productViewModel.isEditMode.value) {
                        productViewModel.updateProduct(parentId, productId) // Pass the parentId and productId // Update product logic
                    } else {
                        productViewModel.addProduct(parentId) // Add new product logic
                    }
                    productViewModel.resetFields() // Reset fields after adding/updating
                    onSaveProduct()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32), contentColor = Color.White),
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(if (productViewModel.isEditMode.value) "Update Product" else "Save Product")
            }
        }
    }
}
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(text = label, color = Color.Gray)
                }
                innerTextField()
            }
        }
    )
}

