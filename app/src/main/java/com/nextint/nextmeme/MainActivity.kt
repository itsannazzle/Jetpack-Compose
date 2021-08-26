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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextint.nextmeme.model.IdolData
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
fun MultipleGreeting(idolName : IdolData){
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

@Composable
fun DramaProfileCard(modifier: Modifier = Modifier){
    Row(

        modifier
            .clickable(onClick = {})
            .padding(16.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.surface),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
        }
//        Image(painterResource(id = R.string.app_name))
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "Start up",
                fontWeight = FontWeight.Bold,
            )
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "October 17, 2020",
                    style = MaterialTheme.typography.body2
                    )
            }

        }
        Button(onClick = { /*TODO*/ }, modifier
            .padding(4.dp)) {
            Text(text = "See more")
        }
    }
}

@Composable
fun LayoutCodeLab(){
    Scaffold(topBar = {
       TopAppBar(title = {
           Text(text = "Top app bar", style = MaterialTheme.typography.h5)
       })
    }) {
        innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier : Modifier = Modifier){
    Column(modifier = modifier) {
        Text(text = "Sup there!")
        Text(text = "Have a good day folks")
    }
}


@Preview(showBackground = true)
@Composable
fun LayoutCodeLabPreview(){
    NextMemeTheme {
        LayoutCodeLab()
    }
}


@Composable
fun DramaProfileCardPreview(){
    NextMemeTheme {
           DramaProfileCard()
    }
}

@Composable
fun DefaultPreview() {
   MyApp{
       Column() {
           Greeting("Chanyeol")
           MultipleGreeting(
               IdolData(mutableListOf("Kai","Suho","Jinyoung","Felix"),
               mutableListOf("EXO","EXO","GOT7","SKZ"))
           )
       }
   }
}


@Composable
fun LazyPreview(){
    ShowDataWithLazyColumn()
}