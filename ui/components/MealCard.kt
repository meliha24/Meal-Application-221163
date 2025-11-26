package com.example.mealapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mealapp.data.models.MealSummary

@Composable
fun MealCard(meal: MealSummary, onClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(6.dp)) {
        Column(modifier = Modifier.width(160.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(text = meal.strMeal, modifier = Modifier.padding(8.dp), maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}
