package com.example.camp_serve.ui.entrant.campsites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private data class Campsite( // TODO: replace with reference to shared campsite class
    val name: String,
    val location: String,
    val owner: String,
    val occupancy: Int,
    val price: Double,
    val rating: Double,
)

private val sampleCampsites = // TODO: replace with firestore database
    listOf(
        Campsite("Lakeside Camp", "Banff",  "Alex", 5, 12.99, 4.5),
        Campsite("Pine Ridge", "Jasper", "Sam", 6, 15.99, 4.2)
    )

@Composable
fun CampsitesScreen(navController: NavController) {
    var userInput by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("") }
    val campsites = sampleCampsites // read only

    val filtered = if (query.isBlank()) { // empty query -> show all
        campsites
    } else {
        campsites.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // title
        Text(
            text = "Campsites",
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            color = Color(0xFF715CAA)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // search bar & search button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search Campsites") },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    query = userInput
                }
            ) {
                Text("Search")
            }
        }

        // Campsite list
        if (filtered.isEmpty()) {
            Text(
                text = "No campsites found.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 18.sp
            )
        } else {
            LazyColumn {
                items(filtered) { campsite ->
                    Text(
                        text = campsite.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}