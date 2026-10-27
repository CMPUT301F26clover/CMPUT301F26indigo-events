package com.example.camp_serve.data

import com.example.camp_serve.model.Alert

interface AlertRepository {
    suspend fun getAlerts(): List<Alert>
}