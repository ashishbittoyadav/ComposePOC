package com.example.melz.composables

import android.app.Application
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.example.melz.R
import com.example.melz.model.MealCategory
import com.example.melz.network.ApiResponse
import com.example.melz.network.httpClientAndroid
import com.example.melz.ui.theme.MelzTheme
import com.example.view_model.MealViewModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.statement.request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Greeting() {
    val categoryState = remember { mutableStateOf(MealCategory()) }
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
//            httpClientAndroid.get("https://www.themealdb.com/api/json/v1/1/categories.php")
//            httpClientAndroid.get("https://www.themealdb.com/api/json/v1/1")
//                .call
//                .body<MealCategory>().let {
//                    Log.d("ComposeBody.TAG", "Greeting: $it")
//                    categoryState.value = it
//                }
//            val r: ApiResponse<MealCategory> = httpClientAndroid.get("https://www.themealdb.com/api/json/v1/1/categories.php").body()
            val r: ApiResponse<MealCategory> =
                httpClientAndroid.get("https://www.themealdb.com/api/json/v1/1").body()
            r.handleResult({ data ->
                Log.d("ComposeBody.TAG", "Greeting: $data")
            }, { error ->
                Log.d("ComposeBody.TAG", "Greeting: $error")
            })
        }
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
                            .border(4.dp, Color.Gray)
                    ) {
                        Text(
                            text = it.strCategoryDescription.trim(),
                            modifier = Modifier
                                .padding(8.dp),
                            style = TextStyle(fontFamily = FontFamily.Cursive)
                        )
                    }
                }
            }
        } else {
            Text(text = "loading...")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowPreview() {
    MelzTheme {
//        MealCard()
        BottomSheet()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet() {

    val state = rememberModalBottomSheetState()
    val coroutineState = rememberCoroutineScope()
    val context = LocalContext.current

    var isOpen by rememberSaveable {
        mutableStateOf(false)
    }

    AnimatedVisibility(visible = !state.isVisible) {
        Button(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                isOpen = true
            }) {

            Text(text = "show Bottom Sheet")
        }
    }

    if (isOpen) {
        ModalBottomSheet(
            sheetState = state,
            onDismissRequest = {
                isOpen = false
            }) {
            MealCard()
        }
    }
}


@Composable
fun MealCard() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding)),
    ) {
        ImageRequest.Builder(context = context)
//            .data()
        Image(
            painter = painterResource(id = R.drawable.rider_love),
            contentDescription = "asd",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .wrapContentSize()
                .size(dimensionResource(id = R.dimen.image_size))
                .clip(RoundedCornerShape(10.dp))
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "Meal title", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Meal Description, adding data to check long text in the description.",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}