package com.example.myapplication11.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication11.R
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter // Pour charger l'image à partir de l'URI

@Composable
fun CreateScreen(onSubmit: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Lancer un contrat pour choisir une image depuis la galerie
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Affichage de l'image sélectionnée, si elle existe
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it), // Charger l'image à partir de l'URI
                contentDescription = "Image de Don",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        // Bouton pour sélectionner une image
        Button(
            onClick = { pickImageLauncher.launch("image/*") }, // Lancer le sélecteur d'image
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sélectionner une image")
        }

        Text("Ajouter un don", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom du don") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state,
            onValueChange = { state = it },
            label = { Text("État") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Localisation") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = onSubmit,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Ajouter")
        }
    }
}

@Composable
@Preview
fun PreviewCreateScreen() {
    CreateScreen(onSubmit = {})
}
