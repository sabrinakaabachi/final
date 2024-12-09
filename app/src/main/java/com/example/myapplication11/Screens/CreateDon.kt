package com.example.myapplication11.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication11.R
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateScreen(onSubmit: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedCategories by remember { mutableStateOf(setOf<String>()) }

    // Liste des catégories
    val categories = listOf("Clothes", "Electronics", "Furniture", "Food")

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

        // Liste de checkboxes pour les catégories
        Column {
            categories.forEach { category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedCategories.contains(category),
                        onCheckedChange = { isChecked ->
                            selectedCategories = if (isChecked) {
                                selectedCategories + category // Ajouter la catégorie
                            } else {
                                selectedCategories - category // Retirer la catégorie
                            }
                        }
                    )
                    Text(text = category, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

        Button(
            onClick = {
                // Passez les informations nécessaires pour soumettre le formulaire
                onSubmit()
            },
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
