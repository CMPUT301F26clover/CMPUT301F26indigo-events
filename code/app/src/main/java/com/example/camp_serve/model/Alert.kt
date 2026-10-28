package com.example.camp_serve.model

enum class AlertType { WON, LOST, RESERVED, CANCELLED }

/**
 * A notification shown to an entrant (US 01.03.01, 01.03.02, 01.05.01, 01.07.02).
 *
 * @property id unique id of the alert
 * @property type what happened: won, lost, reserved or cancelled
 * @property campsite name of the campsite the alert is about
 * @property message text shown to the entrant
 */
data class Alert(
    val id: String = "",
    val type: AlertType = AlertType.LOST,
    val campsite: String = "",
    val message: String = ""
)