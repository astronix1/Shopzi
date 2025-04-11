package com.example.shopzi.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType
import com.tbuonomo.viewpagerdotsindicator.compose.type.WormIndicatorType
import kotlinx.coroutines.delay

@Composable
fun BannerView(modifier: Modifier = Modifier) {
    var banlist by remember {
        mutableStateOf<List<String>>(emptyList())
    }
    val pagerState = rememberPagerState(0) {
        banlist.size
    }


    LaunchedEffect(banlist.size) {
        if (banlist.isNotEmpty()) {
            while (true) {
                delay(2000L)
                val nextPage = (pagerState.currentPage + 1) % banlist.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }


    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("banners").get().addOnCompleteListener {
            banlist = it.result.get("urls") as List<String>
        }
    }
    Column(modifier = modifier) {

        HorizontalPager(state = pagerState, pageSpacing = 20.dp)  {
            AsyncImage(model = banlist[it],
                contentDescription = "Banner",
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp))
                )
        }
        Spacer(modifier =  Modifier.height(10.dp))

        DotsIndicator(
            dotCount = banlist.size,
            type = ShiftIndicatorType(dotsGraphic =
                DotGraphic(color = MaterialTheme.colorScheme.primary,
                size = 6.dp)),
            pagerState = pagerState
        )
    }
}