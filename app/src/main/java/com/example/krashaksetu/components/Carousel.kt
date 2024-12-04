package com.example.krashaksetu.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.krashaksetu.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ImageCarousel() {
    val imageResources = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4
    )

    AutomatedImageCarousel(imageResources = imageResources)
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutomatedImageCarousel(imageResources: List<Int>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // Automate page scroll every 3 seconds
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(3000) // Delay between image changes
                val nextPage = (pagerState.currentPage + 1) % imageResources.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            count = imageResources.size,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        ) { page ->
            Image(
                painter = painterResource(id = imageResources[page]),
                contentDescription = "Image $page",
                modifier = Modifier
                    .fillMaxSize(),
                    //.padding(8.dp)
                    //.clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillWidth
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = Color.Gray,
            indicatorWidth = 8.dp,
            indicatorHeight = 8.dp,
            spacing = 8.dp,
            modifier = Modifier.padding(16.dp)
        )
    }
}