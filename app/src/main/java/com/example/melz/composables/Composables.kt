package com.example.melz.composables

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.melz.R
import com.example.melz.model.Category
import com.example.melz.model.MealCategory
import com.example.melz.network.NetworkCallHelper
import com.example.melz.ui.theme.MelzTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Greeting() {
    val categoryState = remember { mutableStateOf(MealCategory()) }
    val errorState = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            NetworkCallHelper.getResponse().onFailure {
                errorState.value = it.message!!
            }.onSuccess {
                categoryState.value = it
            }

            NetworkCallHelper.getResponseWithParams().onSuccess {
                Log.d("Compose.TAG", "Greeting: $it")
            }.onFailure {
                Log.d("Compose.TAG", "Greeting: $it")
            }
        }
    }
    errorState.value.let {
        if (it.isNotEmpty())
            Text(text = it)
    }
    categoryState.value.categories.let {
        if (!it.isNullOrEmpty()) {
            LazyColumn {
                items(it) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(4.dp)
                            .border(4.dp, Color.Gray, shape = RoundedCornerShape(15.dp))
                    ) {
                        MealCard(it)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowPreview() {
    MelzTheme {
//        MealCard()
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomSheet() {
//
//    val state = rememberModalBottomSheetState()
//    val coroutineState = rememberCoroutineScope()
//    val context = LocalContext.current
//
//    var isOpen by rememberSaveable {
//        mutableStateOf(false)
//    }
//
//    AnimatedVisibility(visible = !state.isVisible) {
//        Button(
//            modifier = Modifier.wrapContentSize(),
//            onClick = {
//                isOpen = true
//            }) {
//
//            Text(text = "show Bottom Sheet")
//        }
//    }
//
//    if (isOpen) {
//        ModalBottomSheet(
//            sheetState = state,
//            onDismissRequest = {
//                isOpen = false
//            }) {
//            MealCard()
//        }
//    }
//}


@Composable
fun MealCard(category: Category) {
    val context = LocalContext.current
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
                expand.value = !expand.value
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