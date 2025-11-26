package com.example.mealapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mealapp.data.models.Category

@Composable
fun CategoryCard(category: Category, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .clickable(onClick = onClick)
        .padding(8.dp)
        .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = category.strCategoryThumb,
                contentDescription = category.strCategory,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = category.strCategory, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = category.strCategoryDescription.takeIf { it.length <= 120 } ?: (category.strCategoryDescription.take(120) + "..."),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
