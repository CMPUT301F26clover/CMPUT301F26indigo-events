package com.example.camp_serve.ui.owner.campsites

/*
 * Simple checks for the register/edit campsite form.
 */

// name and location can't be empty
fun isValidText(text: String): Boolean {
    return text.trim() != ""
}

// max occupancy has to be a whole number that's at least 1
fun isValidOccupancy(occupancy: String): Boolean {
    val number = occupancy.trim().toIntOrNull()
    if (number == null) {
        return false
    }
    return number >= 1
}

// price has to be a number that's 0 or more
fun isValidPrice(price: String): Boolean {
    val number = price.trim().toDoubleOrNull()
    if (number == null) {
        return false
    }
    return number >= 0
}