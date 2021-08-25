package com.nextint.nextmeme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextint.nextmeme.ui.theme.NextMemeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp{
                ShowDataWithLazyColumn()
//                Column() {
//                    Greeting("Chanyeol")
//                    Divider(color = Color.White)
//                    MultipleGreeting(DataNames(mutableListOf("Kai","Suho","Jinyoung","Felix"),
//                        mutableListOf("EXO","EXO","GOT7","SKZ")))
//                }
            }
        }
    }
}

@Composable
fun MyApp(content : @Composable () -> Unit){
    NextMemeTheme {
        Surface(color = Color.Black) {
            content()
        }
    }
}

data class DataNames (val names : List<String>, val group : List<String>)

@Composable
fun Greeting(name: String) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    val backgroudColor by animateColorAsState(
        targetValue = if (isSelected) Color.Red else Color.Transparent,
        animationSpec = tween(3000)
    )
    Surface(color = backgroudColor) {
        Text(
            text = "Love Value #$name!",
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(4.dp)
                .clickable(onClick = { isSelected = !isSelected })
        )
    }
    Divider(color = Color.Black)
}

@Composable
fun MultipleGreeting(idolName : DataNames){
    val counter = remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        idolName.names.forEach {
            Row() {
                Text(
                    text = "Hi $it",
                    color =  MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp)
                )
                Surface(color = Color.Blue) {
                    Text(text = "From ${idolName.group.get(idolName.names.indexOf(it))}",
                        color =  Color.White,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                VoteCounter(
                    name = it,
                    count = counter.value,
                    updateCounter = {newCount -> counter.value = newCount})
            }
        }
    }
}

@Composable
fun VoteCounter(name : String, count : Int, updateCounter : (Int) -> Unit){
    Row() {
        Button(
            onClick = { updateCounter(count+1)},
            modifier = Modifier.padding(4.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (count > 5) Color.LightGray else MaterialTheme.colors.primary )
        ) {
            Text(text = "Vote ${name}")
        }
        Text(
            text = "Total vote of $name = ${count}",
            modifier = Modifier.padding(4.dp),
            color = Color.White
        )
    }
}

@Composable
fun ShowDataWithLazyColumn(loveValue : List<String> = List(1000){ "$it"}, modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier) {
        items(items = loveValue) {
            love -> Greeting(name = love)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
   MyApp{
       Column() {
           Greeting("Chanyeol")
           MultipleGreeting(DataNames(mutableListOf("Kai","Suho","Jinyoung","Felix"),
               mutableListOf("EXO","EXO","GOT7","SKZ")))
       }
   }
}

@Preview(showBackground = true)
@Composable
fun LazyPreview(){
    ShowDataWithLazyColumn()
}