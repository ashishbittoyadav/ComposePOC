package com.example.melz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.melz.composables.ListOfCategory
import com.example.melz.composables.MealDetailScreen
import com.example.melz.model.Category
import com.example.melz.ui.theme.MelzTheme
import com.example.view_model.MealViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MelzTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        ListOfCategory()
//                    MealDetailScreen(
//                        Category(
//                            idCategory = "2",
//                            strCategory = "Meal Category",
//                            strCategoryDescription = "this is the description of the meal.",
//                            strCategoryThumb = "https://www.themealdb.com/images/category/beef.png"
//                        )
//                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MelzTheme {
//    }
//}