package com.example.krashaksetu.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.krashaksetu.viewModels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Profile", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFF2E7D32)),
                actions = {
                    IconButton(onClick = { viewModel.toggleEditing() }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        ProfileScreenContent(paddingValues = padding, viewModel)
    }
}

@Composable
fun ProfileScreenContent(paddingValues: PaddingValues, viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            //.padding(16.dp)
            .fillMaxSize()
            .background(Color(0xFFEEEEFB)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture Section
        ProfileImageSection(
            profileImageUrl = viewModel.profileImageUrl
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mobile Number Card
        ProfileCard(title = "Mobile Number") {
            if (viewModel.isEditing) {
                OutlinedTextField(
                    value = viewModel.mobileNumber,
                    onValueChange = viewModel::updateMobileNumber,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(text = viewModel.mobileNumber)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email Card
        ProfileCard(title = "Email") {
            if (viewModel.isEditing) {
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = viewModel::updateEmail,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(text = viewModel.email)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Location Info Card
        ProfileCard(title = "Location Info") {
            Text(text = "State: ${viewModel.state}")
            Text(text = "District: ${viewModel.district}")
            Text(text = "Taluka/Tehsil: ${viewModel.tehsil}")
            Text(text = "Village: ${viewModel.village}")
            Text(text = "PIN Code: ${viewModel.pinCode}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Personal Info Card
        ProfileCard(title = "Personal Info") {
            Text(text = "Marital Status: ${viewModel.maritalStatus}")
            Text(text = "Annual Income: ${viewModel.annualIncome}")
            Text(text = "Highest Education: ${viewModel.highestEducation}")
            Text(text = "Age: ${viewModel.age}")
        }
    }
}

@Composable
fun ProfileImageSection(
    profileImageUrl: String
) {
    Box(
        modifier = Modifier
            .size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = profileImageUrl),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun ProfileCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color(0xFF2E7D32)),
        border = BorderStroke(2.dp, Color(0xFF2E7D32))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
