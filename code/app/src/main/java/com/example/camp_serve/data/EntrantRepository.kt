package com.example.camp_serve.data
import com.example.camp_serve.model.Entrant

/**
 * Where entrant data comes from and goes to. Screens depend on this interface only
 */

interface EntrantRepository {

    // returns entrant for this device
    suspend fun getEntrant(): Entrant

    // saves the changed profile fields
    suspend fun updateEntrant(entrant:Entrant)

    // delete entrant profile on this device
    suspend fun deleteEntrant()
}