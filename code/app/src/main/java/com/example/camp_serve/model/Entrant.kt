package com.example.camp_serve.model

/**
 * An entrant: someone who joins campsite lotteries
 *
 * Outstanding issues: [id] is a placeholder until device-based sign-in is done.
 *
 * @property id unique id of the entrant
 * @property name display name
 * @property email contact email
 * @property phone optional phone number, empty if not given
 * @property notificationEnabled false if the entrant opted out of notifications
 */

data class Entrant(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val notificationEnabled: Boolean = true,
)

