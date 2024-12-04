package com.example.krashaksetu.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.krashaksetu.R
import com.example.krashaksetu.Screen
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun  PreviewDashboardScreen() {
    //HomeScreen(navController = NavController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawerContent(onItemClick = {
                // Close the drawer when an item is clicked
                scope.launch { drawerState.close() }
            })
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Dashboard",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            // Open the drawer when the menu icon is clicked
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32))
                )
            }
        ) { paddingValues ->
            HomeScreenContent(paddingValues = paddingValues, navController = navController)
        }
    }

}

@Composable
fun DashboardCard(title: String, icon: ImageVector) {
    Column(
        modifier = Modifier
            .size(100.dp) // Fixed smaller size for cards
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(36.dp) // Smaller icon size
        )
        Text(
            text = title,
            fontSize = 10.sp, // Smaller font size
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E3D6D),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun HomeScreenContent(paddingValues: PaddingValues, navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = Color(0xFFEEEEFB)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageCarousel()
        Spacer(Modifier.height(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(0.25f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.List, contentDescription = "product list", tint = Color.White,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp)
                            .background(color = Color(0xFF2E7D32), shape = RoundedCornerShape(10.dp))
                            .padding(16.dp)
                            .clickable{navController.navigate(Screen.ProductListing.route)})

                    Text(text = "Product\nListing", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp), color = Color(0xFF2E3D6D), textAlign = TextAlign.Center, fontSize = 12.sp)
                }
                Column(
                    modifier = Modifier.weight(0.25f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.Inventory, contentDescription = "Inventory Management", tint = Color.White,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp)
                            .background(color = Color(0xFF2E7D32), shape = RoundedCornerShape(10.dp))
                            .padding(16.dp))

                    Text(text = "Inventory\nManagement", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp), color = Color(0xFF2E3D6D), textAlign = TextAlign.Center, fontSize = 12.sp)
                }
                Column(
                    modifier = Modifier.weight(0.25f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.History, contentDescription = "Order History", tint = Color.White,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp)
                            .background(color = Color(0xFF2E7D32), shape = RoundedCornerShape(10.dp))
                            .padding(16.dp))

                    Text(text = "Order\nHistory", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp), color = Color(0xFF2E3D6D), textAlign = TextAlign.Center, fontSize = 12.sp)
                }
                Column(
                    modifier = Modifier.weight(0.25f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.AccountBalanceWallet, contentDescription = "Transaction", tint = Color.White,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp)
                            .background(color = Color(0xFF2E7D32), shape = RoundedCornerShape(10.dp))
                            .padding(16.dp))

                    Text(text = "Transaction\nManagement", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp), color = Color(0xFF2E3D6D), textAlign = TextAlign.Center, fontSize = 12.sp)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp,)
            ) {
                Column(
                    modifier = Modifier.weight(0.25f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.HeadsetMic, contentDescription = "Help Desk", tint = Color.White,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp)
                            .background(color = Color(0xFF2E7D32), shape = RoundedCornerShape(10.dp))
                            .padding(16.dp))

                    Text(text = "Help\nDesk", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp), color = Color(0xFF2E3D6D), textAlign = TextAlign.Center, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun DrawerItem(icon: ImageVector ,title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF2E7D32),
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
@Composable
fun NavigationDrawerContent(onItemClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .width(250.dp)
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Menu",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF2E7D32),
        )
        Divider(Modifier.fillMaxWidth(), color = Color(0xFF2E7D32), thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))
        DrawerItem(Icons.Default.Person,"Profile", onClick = onItemClick)
        DrawerItem(Icons.Default.Settings,"Settings", onClick = onItemClick)
        DrawerItem(Icons.Default.Logout,"Logout", onClick = onItemClick)
    }
}

