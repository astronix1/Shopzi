package com.example.shopzi.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ProfileSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title placeholder
        Box(modifier = Modifier.fillMaxWidth(0.4f).height(28.dp).clip(RoundedCornerShape(8.dp)).shimmerEffect())
        Spacer(modifier = Modifier.height(20.dp))

        // Huge Image placeholder
        Box(modifier = Modifier.fillMaxWidth().height(170.dp).clip(RoundedCornerShape(12.dp)).shimmerEffect())
        Spacer(modifier = Modifier.height(14.dp))

        // Name placeholder centered
        Box(modifier = Modifier.fillMaxWidth(0.6f).height(32.dp).clip(RoundedCornerShape(8.dp)).shimmerEffect().align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(40.dp))

        // Address & Email placeholders
        Box(modifier = Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(8.dp)).shimmerEffect())
        Spacer(modifier = Modifier.height(17.dp))
        Box(modifier = Modifier.fillMaxWidth(0.7f).height(24.dp).clip(RoundedCornerShape(8.dp)).shimmerEffect())
    }
}

@Composable
fun CategorySkeleton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Box(modifier = Modifier.size(60.dp).clip(CircleShape).shimmerEffect())
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.size(width = 50.dp, height = 12.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
    }
}