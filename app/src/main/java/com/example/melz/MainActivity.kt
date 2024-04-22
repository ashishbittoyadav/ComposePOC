package com.example.melz

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

interface HiltInterface{
    fun doSomething(result:String)
}
@Module
@InstallIn(ActivityComponent::class)
class MyModule{
    @Provides
    fun provideHiltInterface(@ActivityContext context: Context):HiltInterface{
        return context as HiltInterface
    }
}

class Test @Inject constructor(private val hiltInterface: HiltInterface){
    fun callTest(){
        hiltInterface.doSomething("do something.")
    }
}
@AndroidEntryPoint
class MainActivity : ComponentActivity(),HiltInterface {

    @Inject lateinit var test: Test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MelzTheme {
                test.callTest()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        ListOfCategory()
                }
            }
        }
    }

    override fun doSomething(result: String) {
        Log.d("HiltInterface.TAG", "doSomething: name $result")
    }
}