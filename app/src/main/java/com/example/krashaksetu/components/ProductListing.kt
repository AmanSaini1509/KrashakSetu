package com.example.krashaksetu.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.krashaksetu.viewModels.ProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListingScreen(
    productViewModel: ProductViewModel,
    onAddProductClick: () -> Unit,
    onEditProductClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
    }
    val products by productViewModel.productList.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Listing", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFF2E7D32))
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { productViewModel.resetFields(); onAddProductClick() }, containerColor = Color(0xFF2E7D32), contentColor = Color.White) {
                Icon(painterResource(android.R.drawable.ic_input_add), contentDescription = "Add Product")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .background(Color(0xFFEEEEFB))
            .fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isLoading) {
                Text(
                    text = "Loading..",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(products) { product ->
                        ProductCard(product = product, onEdit = {
                            productViewModel.setProductForEdit(product)
                            onEditProductClick()
                        },
                            //onDelete = { productViewModel.deleteProduct(product)})
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(2.dp, Color(0xFF2E7D32)),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            product.imageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(product.imageUri),
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .size(130.dp)
                            .padding(end = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = product.name, fontSize = 20.sp, color = Color(0xFF2E7D32))
                    Text(text = "Price: ₹${product.price}", fontSize = 16.sp, color = Color.Black)
                    Text(
                        text = "Quantity: ${product.quantity}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Category: ${product.category}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Description: ${product.description}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = onEdit,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32),
                                contentColor = Color.White
                            )
                        ) { Text("Update") }
                        Button(
                            onClick = onDelete,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32),
                                contentColor = Color.White
                            )
                        ) { Text("Delete") }
                    }
                }
            }
        }
    }
