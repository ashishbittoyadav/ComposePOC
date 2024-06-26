package com.example.melz.composables

import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.melz.Constant
import com.example.melz.R
import com.example.melz.model.Category
import com.example.melz.model.MealCategory
import com.example.melz.ui.theme.MelzTheme
import com.example.view_model.MealUiState
import com.example.view_model.MealViewModel
import javax.inject.Inject

@Composable
fun ListOfCategory() {
//    val mealViewModel: MealViewModel = viewModel()

    val mealViewModel = hiltViewModel<MealViewModel>()

    mealViewModel.getCategory()


    val navController = rememberNavController()

    mealViewModel.responseState.collectAsState().value.let { mealUiState ->
        NavHost(navController = navController, startDestination = "meal_list") {
            composable(
                "meal_detail/{mealID}",
                arguments = listOf(navArgument("mealID") {
                    type = NavType.IntType
                })
            ) {
                MealDetailScreen(categoryId = it.arguments!!.getInt("mealID"), navController)
            }
            composable("meal_list") {
                MealList(mealUiState, navController)
            }
        }
//        LazyColumn {
//            if (!it.isLoading) {
//                if (it.error.isNotEmpty()) {
//                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
//                } else {
//                    (it.mealCategory as MealCategory).categories.let { categoryItems ->
//                        if (categoryItems != null) {
//                            items(categoryItems) {
//                                Surface(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .wrapContentHeight()
//                                        .padding(4.dp)
//                                        .border(4.dp, Color.Gray, shape = RoundedCornerShape(15.dp))
//                                ) {
//                                    MealCard(it)
//                                }
//                            }
//                        } else {
//                            Toast.makeText(context, "list is empty...", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowPreview() {
    MelzTheme {
    }
}

@Composable
fun MealDetailScreen(categoryId: Int, navController: NavHostController) {
//    val mealViewModel: MealViewModel = viewModel()
    val mealViewModel = hiltViewModel<MealViewModel>()

    mealViewModel.getCategoryById(categoryId.toString())

    mealViewModel.responseState.collectAsState().value.let {
        if (it.mealCategory != null) {
            (it.mealCategory as Category).let { category ->
                Surface(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(dimensionResource(id = R.dimen.padding)),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "close",
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable {
                                    navController.popBackStack()
                                })
                        AsyncImage(
                            model = category.strCategoryThumb,
                            contentDescription = category.strCategory,
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .align(Alignment.CenterHorizontally)
                                .clip(CircleShape)
                        )
                        Row(modifier = Modifier.padding(vertical = 20.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .border(
                                            width = 1.dp,
                                            color = if (category.strCategory == Constant.VEGETARIAN || category.strCategory == Constant.VEGAN) Color.Green else Color.Red,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                ) {
                                    Text(
                                        text = category.strCategory,
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier
                                            .padding(horizontal = 5.dp)
                                    )
                                }
                                Text(
                                    text = category.strCategoryDescription,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .verticalScroll(rememberScrollState())
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealList(it: MealUiState, navController: NavHostController) {
    val context = LocalContext.current
    LazyColumn {
        if (!it.isLoading) {
            if (it.error.isNotEmpty()) {
                Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
            } else {
                (it.mealCategory as MealCategory).categories.let { categoryItems ->
                    if (categoryItems != null) {
                        items(categoryItems) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(4.dp)
                                    .border(4.dp, Color.Gray, shape = RoundedCornerShape(15.dp))
                                    .background(Color.Red)
                            ) {
                                MealCard(it, navController)
                            }
                        }
                    } else {
                        Toast.makeText(context, "list is empty...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun MealCard(category: Category, navController: NavHostController) {
    val expand = remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding)),
    ) {
        AsyncImage(
            model = category.strCategoryThumb,
            contentDescription = category.strCategory,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Text(text = category.strCategory, style = MaterialTheme.typography.titleLarge)
            Surface(modifier = Modifier.clickable {
                navController.navigate(route = "meal_detail/${category.idCategory.toInt()}")

            }
            ) {
                Text(
                    text = category.strCategoryDescription,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = if (expand.value) Int.MAX_VALUE else 3,
                )
            }
        }
    }
}

@Composable
fun SearchBox() {
    var textState by remember {
        mutableStateOf("")
    }

    val keyboard = LocalSoftwareKeyboardController.current
    val focus = LocalFocusManager.current

    OutlinedTextField(
        value = textState,
        onValueChange = {
            textState = it
        },
        label = { Text(text = "enter meal") },
        trailingIcon = {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "search meal")
        },
        modifier = Modifier.fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(40.dp))
            .border(
                BorderStroke(1.dp, Brush.horizontalGradient(listOf(Color.Red,Color.Green)))
                , RoundedCornerShape(40.dp))
            ,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {

            keyboard?.hide()
            focus.clearFocus()
        }
    )
}

@Preview
@Composable
private fun Demo() {
    SearchBox()
}

