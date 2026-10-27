package com.example.camp_serve.ui.entrant.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.camp_serve.data.AlertRepository
import com.example.camp_serve.data.EntrantRepository
import com.example.camp_serve.data.sampleAlerts
import com.example.camp_serve.model.Alert
import com.example.camp_serve.model.AlertType
import com.example.camp_serve.model.Entrant
import com.example.camp_serve.ui.theme.CampserveTheme
import kotlinx.coroutines.launch

/**
 * Loads the entrant's alerts and notification setting, then shows [AlertsScreen].
 * Toggling the switch saves entrant.notificationEnabled (US 01.10.01).
 * Outstanding issues: Reserve is not connected to the Reserve screen yet.
 *
 * @param alertRepository where alerts are loaded from
 * @param entrantRepository where the notification opt-out is saved
 * @param onReserve called when the entrant taps Reserve on a winning alert
 */
@Composable
fun AlertsRoute(
    alertRepository: AlertRepository,
    entrantRepository: EntrantRepository,
    modifier: Modifier = Modifier,
    onReserve: (Alert) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(true) }
    var alerts by remember { mutableStateOf<List<Alert>>(emptyList()) }
    var entrant by remember { mutableStateOf(Entrant()) }

    LaunchedEffect(Unit) {
        alerts = alertRepository.getAlerts()
        entrant = entrantRepository.getEntrant()
        loading = false
    }

    if (loading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        AlertsScreen(
            alerts = alerts,
            notificationsEnabled = entrant.notificationEnabled,
            onNotificationsToggle = { enabled ->
                scope.launch {
                    val updated = entrant.copy(notificationEnabled = enabled)
                    entrantRepository.updateEntrant(updated)
                    entrant = updated
                }
            },
            onReserve = onReserve,
            modifier = modifier
        )
    }
}

@Composable
fun AlertsScreen(
    alerts: List<Alert>,
    notificationsEnabled: Boolean,
    onNotificationsToggle: (Boolean) -> Unit,
    onReserve: (Alert) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Alerts",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Receive notifications")
            Switch(checked = notificationsEnabled, onCheckedChange = onNotificationsToggle)
        }

        if (alerts.isEmpty()) {
            Text("No alerts yet.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(alerts) { alert -> AlertCard(alert, onReserve) }
            }
        }
    }
}

@Composable
private fun AlertCard(alert: Alert, onReserve: (Alert) -> Unit) {
    val title = when (alert.type) {
        AlertType.WON -> "You won!"
        AlertType.LOST -> "Not selected"
        AlertType.RESERVED -> "Reservation confirmed"
        AlertType.CANCELLED -> "Reservation cancelled"
    }
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(alert.campsite, style = MaterialTheme.typography.titleMedium)
            Text(alert.message, style = MaterialTheme.typography.bodyMedium)
            if (alert.type == AlertType.WON) {
                Button(onClick = { onReserve(alert) }, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Reserve")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlertsScreenPreview() {
    CampserveTheme {
        AlertsScreen(
            alerts = sampleAlerts,
            notificationsEnabled = true,
            onNotificationsToggle = {},
            onReserve = {}
        )
    }
}