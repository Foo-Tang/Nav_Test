package com.example.myapplication

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.versionedparcelable.VersionedParcelize
import com.example.myapplication.destinations.Greeting1Destination
import com.example.myapplication.destinations.Greeting2Destination
import com.example.myapplication.destinations.Greeting3Destination
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

@Parcelize
data class demo(val string: String, var int: Int, val name: String) : Parcelable


@RootNavGraph(start = true) // sets this as the start destination of the default nav graph
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    /*...*/
    val demo1 = demo(string = "test", int = 1, name = "Will")

    navigator.navigate(Greeting1Destination(id = 1, name = "Adam", demo1))
}


@Destination
@Composable
fun Greeting1(id: Int, name: String, data: demo, destinationsNavigator: DestinationsNavigator) {

    Column {
        Button(onClick = {destinationsNavigator.navigate(Greeting2Destination(5, "John"))}){
            Text(text = "Go To Second Screen! $name")
        }
        Button(onClick = {destinationsNavigator.navigate(Greeting3Destination())}){
            Text(text = "Go To Third Screen! $name")
        }
        Text("This is the First Screen")
        Text(data.string)
        Text(data.int.toString())
        Text(data.name)
    }

}

@Destination
@Composable
fun Greeting2(id: Int, name: String, modifier: Modifier = Modifier, destinationsNavigator: DestinationsNavigator) {

    val demo2 = demo(string = "test2", int = 2, name = "Demo2")

    Column {
        Button(onClick = {destinationsNavigator.navigate(Greeting3Destination())}){
            Text(text = "Go to Third Screen! $name")
        }
        Button(onClick = {destinationsNavigator.navigate(Greeting1Destination(1, "Jack", demo2))}){
            Text(text = "Go to first Screen! $name")
        }
        Button(onClick = { destinationsNavigator.popBackStack()}) {
            Text("Go Back")
        }
        Text("This is the Second Screen")
    }
}

@Destination
@Composable
fun Greeting3(destinationsNavigator: DestinationsNavigator){
    val demo2 = demo(string = "test2", int = 2, name = "Hobbs")

    Column {
        Button(onClick = {destinationsNavigator.navigate(Greeting1Destination(1, "Shaw", demo2))}){
            Text(text = "Go to First Screen")
        }
        Button(onClick = { destinationsNavigator.popBackStack()}) {
            Text("Go Back")
        }
        Text("This is the Third Screen")
    }
}