package com.example.camp_serve.data

import com.example.camp_serve.model.Alert
import com.example.camp_serve.model.AlertType

val sampleAlerts = listOf(
    Alert("1", AlertType.WON, "Lakeview Campsite", "You won the lottery! Reserve before the booking period ends."),
    Alert("2", AlertType.LOST, "Pine Ridge Campsite", "You were not selected this time."),
    Alert("3", AlertType.RESERVED, "Cedar Hollow", "Your reservation is confirmed."),
    Alert("4", AlertType.CANCELLED, "Birch Point", "Your reservation was cancelled by the owner.")
)

class TempAlertRepo : AlertRepository {
    override suspend fun getAlerts(): List<Alert> = sampleAlerts
}