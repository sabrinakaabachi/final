package com.example.myapplication11.models

import com.google.gson.annotations.SerializedName

data class Product(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("nom")
	val nom: String? = null,

	@field:SerializedName("category")  // Ajout de la catégorie
	val category: String? = null,  // Déclare le champ catégorie

	@field:SerializedName("location")  // Ajout de la localisation
	val location: String? = null   // Déclare le champ localisation
)
