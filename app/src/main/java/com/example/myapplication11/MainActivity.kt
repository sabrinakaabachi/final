package com.example.myapplication11

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication11.R
import com.example.myapplication11.models.Product
import com.example.myapplication11.ui.theme.MyApplication11Theme
import com.example.myapplication11.viewmodel.ProductUiState
import com.example.myapplication11.viewmodel.ProductViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication11.ui.CreateScreen
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import java.io.File

// Main Activity
class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication11Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(productViewModel)
                }
            }
        }
    }
}

// Main Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(productViewModel: ProductViewModel) {
    val navController = rememberNavController()
    val uiState by productViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Your move makes someone's life better") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF1976D2))
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        content = { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("home") {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        GreetingSection()
                        SearchBar()
                        CategorySection()
                        ProductSection(uiState)
                    }
                }
                composable("create") {
                    AddImageScreen()
                }
                composable("explore") {
                    Text(
                        text = "Explore Page",
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                composable("profile") {
                    Text(
                        text = "Profile Page",
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    )
}

// Composables pour les sections
@Composable
fun GreetingSection() {
    Text(
        text = "Hello, Rizalkenz 👋",
        fontSize = 22.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        label = { Text("Search campaigns") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun CategorySection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CategoryCard("All", Color(0xFFB39DDB), modifier = Modifier.weight(1f))
        CategoryCard("Clothes", Color(0xFF81C784), modifier = Modifier.weight(1f))
        CategoryCard("Education", Color(0xFF64B5F6), modifier = Modifier.weight(1f))
        CategoryCard("Humanity", Color(0xFFFF8A65), modifier = Modifier.weight(1f))
    }
}

@Composable
fun CategoryCard(text: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .height(100.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductSection(uiState: ProductUiState) {
    when (uiState) {
        is ProductUiState.Loading -> {
            Text(
                text = "Loading...",
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
        is ProductUiState.Success -> {
            val products = uiState.produits
            products.forEach { product ->
                ProductCard(product)
            }
        }
        is ProductUiState.Error -> {
            Text(
                text = "An error occurred while fetching products.",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val imageUrl = product.image
            val painter = if (imageUrl.isNullOrEmpty()) {
                rememberAsyncImagePainter(R.drawable.first) // Utilisation d'une image de placeholder si aucune image n'est fournie
            } else {
                rememberAsyncImagePainter(model = imageUrl)
            }

            Image(
                painter = painter,
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            product.nom?.let {
                Text(text = it, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            product.description?.let { Text(text = it) }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Get It Now")
            }
        }
    }
}

// Image Picker Composable
@Composable
fun AddImageScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> selectedImageUri = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(selectedImageUri),
                contentDescription = "Selected Image",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = "Aucune image sélectionnée",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { pickImageLauncher.launch("image/*") }) {
            Text("Sélectionner une image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))


        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state,
            onValueChange = { state = it },
            label = { Text("State") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Handle submit */ }) {
            Text("Submit")
        }
    }
}

// Bottom Navigation Bar
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color(0xFF1976D2),
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute(navController) == "home",
            onClick = { navController.navigate("home") }
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Add, contentDescription = null) },
            label = { Text("Create") },
            selected = false,
            onClick = { navController.navigate("create") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Explore, contentDescription = null) },
            label = { Text("Explore") },
            selected = false,
            onClick = { navController.navigate("explore") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate("profile") }
        )
    }
}
