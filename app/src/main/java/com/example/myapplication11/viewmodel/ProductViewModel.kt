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
    // État UI actuel
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState

    // Gérer la catégorie sélectionnée
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    // Liste de produits pour la simulation
    private val allProducts = mutableListOf(
        Product("Product 1", "Description 1", "Good", "Clothes", "Tunis"),
        Product("Product 2", "Description 2", "New", "Education", "Bizerte"),
        Product("Product 3", "Description 3", "Used", "Education", "Beja")
    )

    // Méthode pour ajouter un produit (ou don)
    fun addProduct(name: String, description: String, state: String, location: String, availability: Int, category: String) {
        val newProduct = Product(name, description, state, location, category)
        // Ajouter le produit à la liste existante
        allProducts.add(newProduct)
        _uiState.value = ProductUiState.Success(allProducts)
    }

    // Méthode pour récupérer les produits filtrés par catégorie
    fun fetchProducts() {
        // Appliquer le filtre basé sur la catégorie sélectionnée
        _selectedCategory.value?.let { category ->
            val filteredProducts = allProducts.filter { it.category == category }
            _uiState.value = ProductUiState.Success(filteredProducts)
        } ?: run {
            // Si aucune catégorie n'est sélectionnée, afficher tous les produits
            _uiState.value = ProductUiState.Success(allProducts)
        }
    }

    // Mettre à jour la catégorie sélectionnée
    fun setCategory(category: String) {
        _selectedCategory.value = category
        fetchProducts()  // Filtrer les produits en fonction de la catégorie sélectionnée
    }

    fun addProduct(name: String, description: String, state: String, location: String, category: String) {

    }
}
