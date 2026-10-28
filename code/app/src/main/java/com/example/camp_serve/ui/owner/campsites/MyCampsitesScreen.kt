package com.example.camp_serve.ui.owner.campsites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.camp_serve.data.CampsiteRepository
import com.example.camp_serve.data.TempCampsiteRepo
import com.example.camp_serve.model.Campsite

/*
 * Owner's home screen that lists all the campsites they registered (US 02.03.01).
 * TODO: register and campsite details screens aren't connected yet (no navigation).
 */

/**
 * Shows the owner's campsites, or a message if they don't have any.
 *
 * @param repository where the campsites are loaded from
 * @param ownerId id of the owner using the app
 * @param onRegisterClick called when the owner taps "Register campsite"
 * @param onCampsiteClick called when the owner taps one of their campsites
 */
@Composable
fun MyCampsitesScreen(
    repository: CampsiteRepository,
    ownerId: String,
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit = {},
    onCampsiteClick: (Campsite) -> Unit = {}
) {
    var loading by remember { mutableStateOf(true) }
    var campsites by remember { mutableStateOf(listOf<Campsite>()) }

    // load the owner's campsites when the screen opens
    LaunchedEffect(ownerId) {
        campsites = repository.getCampsitesForOwner(ownerId)
        loading = false
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "My Campsites",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (campsites.isEmpty()) {
                Text(
                    text = "You haven't registered any campsites yet.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(campsites) { campsite ->
                        CampsiteCard(campsite = campsite, onClick = { onCampsiteClick(campsite) })
                    }
                }
            }
        }

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register campsite")
        }
    }
}

/**
 * One campsite in the list, shows its name and location.
 */
@Composable
private fun CampsiteCard(campsite: Campsite, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(campsite.name, fontWeight = FontWeight.Bold)
            Text(campsite.location)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyCampsitesScreenPreview() {
    MyCampsitesScreen(repository = TempCampsiteRepo(), ownerId = "fake-owner-01")
}