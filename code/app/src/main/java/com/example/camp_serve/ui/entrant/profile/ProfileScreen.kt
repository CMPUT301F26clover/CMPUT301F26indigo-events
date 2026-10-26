package com.example.camp_serve.ui.entrant.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.camp_serve.data.EntrantRepository
import com.example.camp_serve.model.Entrant
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen (
    repository: EntrantRepository,
    modifier: Modifier = Modifier,
    onProfileDeleted : () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(true) }
    var current by remember { mutableStateOf(Entrant()) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var status by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember {mutableStateOf(false)}

    LaunchedEffect(Unit) {
        val entrant = repository.getEntrant()
        current = entrant
        name = entrant.name
        email = entrant.email
        phone = entrant.phone
        loading = false
    }

    if (loading){
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    } else {
        val nameOk = name.isNotBlank()
        val emailOk =  isValidEmail(email)
        val phoneOk = isValidPhone(phone)

        Column(
            modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Text("My Profile", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                value = name,
                onValueChange = {name = it; status = null},
                label = {Text("Name")},
                isError = !nameOk,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = {email = it; status = null},
                label = {Text("Email")},
                isError = !emailOk,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = phone,
                onValueChange = {phone = it; status = null},
                label = {Text("Phone (optional)")},
                isError = !phoneOk,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                onClick = {
                    scope.launch {
                        val updated = current.copy(
                            name = name.trim(),
                            email = email.trim(),
                            phone = phone.trim()
                        )
                        repository.updateEntrant(updated)
                        current = updated
                        status = "Profile Saved"
                    }

                },
                enabled = nameOk && emailOk && phoneOk,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save changes")
            }

            status?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

            Button(
                onClick = {
                    showDeleteDialog = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()

            ){
                Text("Delete Profile")
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = {showDeleteDialog = false},
                    title = { Text("Delete profile?")},
                    text = { Text("This permanently deleted your profile. It cannot be reversed")},
                    confirmButton = {
                        TextButton(onClick = {
                            scope.launch {
                                repository.deleteEntrant()
                                showDeleteDialog = false
                                onProfileDeleted()
                            }
                        }) {
                           Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {showDeleteDialog = false}
                        ) {
                            Text("Cancel")
                        }
                    }

                )
            }
        }
    }

}