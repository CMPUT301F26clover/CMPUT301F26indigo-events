package com.example.camp_serve.model

/**
 * A campsite registered by a campsite owner.
 * Every field has a default value so Firestore can turn it back into a campsite.
 *
 * @property id unique id of the campsite
 * @property ownerId id of the owner who registered it
 * @property name name of the campsite
 * @property location town or park it is in
 * @property description description and amenities
 * @property maxOccupancy maximum number of guests
 * @property price price per night in dollars
 * @property rating average rating, 0 if not rated yet
 * @property rules rules and cancellation policy
 * @property tags tags like "lake" or "family"
 * @property latitude map latitude
 * @property longitude map longitude
 * @property imageUrl link to the campsite image, empty if none
 * @property availableDates date of when campsite is open, in the "YYYY-MM-DD" format.
 */
data class Campsite(
    val id: String = "",
    val ownerId: String = "",
    val name: String = "",
    val location: String = "",
    val description: String = "",
    val maxOccupancy: Int = 0,
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val rules: String = "",
    val tags: List<String> = emptyList(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val imageUrl: String = "",
    val availableDates: List<String> = emptyList(),
)