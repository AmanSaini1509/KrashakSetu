package com.example.krashaksetu.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.krashaksetu.Screen
import com.example.krashaksetu.viewModels.LanguageViewModel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LanguageSelectionScreenPreview() {
    //LanguageSelectionScreen()
}

@Composable
fun LanguageSelectionScreen(navController: NavController, languageViewModel: LanguageViewModel, context: Context) {
    val languages = listOf(
        "English",
        "हिंदी",
        "मराठी",
        "ગુજરાતી",
        "ಕನ್ನಡ",
        "తెలుగు"
    )
    val languageCodes = listOf("en", "hi", "mr", "gu", "kn", "te")
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState(initial = "en")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Choose your language.",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp),
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(languages) { index, language ->
                LanguageItem(
                    name = language,
                    shortName = languageCodes[index],
                    isSelected = languageCodes[index] == selectedLanguage,
                    onSelect = { languageViewModel.updateLanguage(languageCodes[index])}
                )
            }
        }

        Button(
            onClick = { navController.popBackStack()
                navController.navigate(Screen.Login.route)
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32))
        ) {
            Text(text = "Continue", color = Color.White)
        }
    }
}

@Composable
fun LanguageItem(
    name: String,
    shortName: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFFE8F5E9) else Color.White)
            .border(
                width = 2.dp,
                color = if (isSelected) Color(0xFF2E7D32) else Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onSelect)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = if (isSelected) Color(0xFF2E7D32) else Color.LightGray,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = shortName,
                color = if (isSelected) Color.White else Color.Black
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = if (isSelected) Color(0xFF2E7D32) else Color.Black
        )
    }
}
