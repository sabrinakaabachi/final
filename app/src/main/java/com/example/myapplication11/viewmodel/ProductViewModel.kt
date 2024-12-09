package com.example.myapplication11.viewmodel


import androidx.lifecycle.ViewModel

import com.example.myapplication11.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface ProductUiState {
    data class Success(val produits: List<Product>) : ProductUiState
    object Error : ProductUiState
    object Loading : ProductUiState
}

class ProductViewModel : ViewModel() {
    // Votre état UI actuel
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState

    // Fonction pour ajouter un produit (ou don)
    fun addProduct(name: String, description: String, state: String, location: String, availability: Int) {
        // Logique pour ajouter le produit, par exemple en l'envoyant à une base de données
        val newProduct = Product(name, description, state, location, availability)
        // Simule l'ajout du produit à une liste ou base de données
        // Vous pouvez ajouter ce produit à une liste ou appeler un API
    }

    fun fetchProducts() {
        // Logique pour récupérer les produits
    }
}


