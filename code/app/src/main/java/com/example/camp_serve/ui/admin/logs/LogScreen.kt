package com.example.camp_serve.ui.admin.logs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

private data class Notification (
    val campsite: String,
    val entrants: Int,
    val text: String,
    val timeAgo: String
)

private data class Messages (
    val campsite: String,
    val entrant: String,
    val text: String,
    val timeAgo: String
)

private val SampleNotifications =
    listOf(
        Notification("Hinton Camps", 12, "Registration closes tomorrow", "2h ago"),
        Notification("Banff Camps", 12, "Registration closes tomorrow", "2h ago")
)

private val SampleMessages =
    listOf(
        Messages("Hinton Camps", "Cole Smith", "The cookhouse is open on arrival", "2h ago"),
        Messages("Banff Camps", "John Doe", "The cookhouse is open on arrival", "2h ago")
)

@Composable
fun LogScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Logs",
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            color = Color(0xFF715CAA)
        )

        Row(modifier = Modifier.padding(16.dp)) {
            FilterChip( // From https://developer.android.com/develop/ui/compose/components/chip
                onClick = { selectedTab = 1 },
                label = { Text("Notifications") },
                selected = selectedTab == 1
            )

            Spacer(modifier = Modifier.padding(8.dp))

            FilterChip(
                onClick = { selectedTab = 2 },
                label = { Text("Messages") },
                selected = selectedTab == 2
            )
        }
        LazyColumn {
            if(selectedTab == 1) {
                items(SampleNotifications) { notification ->
                    NotificationRow(notification)
                }
            }
            else {
                items(SampleMessages) { message ->
                    MessagesRow(message)
                }
            }
        }
    }
}

@Composable
private fun NotificationRow(notification: Notification) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFEF7FF))
            .border(1.dp, Color(0xFF000000))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("${notification.campsite} - ${notification.entrants.toString()} entrants", fontWeight = FontWeight.Bold)
            Text(notification.text, fontSize = 12.sp)
            Text(notification.timeAgo, fontSize = 12.sp)
        }
    }
}

@Composable
private fun MessagesRow(message: Messages) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFEF7FF))
            .border(1.dp, Color(0xFF000000))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("${message.campsite} - ${message.entrant}", fontWeight = FontWeight.Bold)
            Text(message.text, fontSize = 12.sp)
            Text(message.timeAgo, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogsScreenPreview() {
    LogScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun LogScreenPreview() {
    LogScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun ProfilesScreenPreview() {
    LogScreen(navController = rememberNavController())
}