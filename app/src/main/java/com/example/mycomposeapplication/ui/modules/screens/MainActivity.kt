package com.example.mycomposeapplication.ui.modules.screens

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomposeapplication.R
import com.example.mycomposeapplication.data.entity.Item
import com.example.mycomposeapplication.ui.modules.viewmodels.MainActivityVm
import com.example.mycomposeapplication.ui.theme.Shapes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityVm>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var count = 0
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels


//        val dip = 14f
//        val r: Resources = resources


        setContent {
            println("$width")
            LazyRowItemsDemo(viewModel, width, resources)
//            AddItem("Sid", "Sidesh")
//            AndroidView(factory = {
//                TextView(it)
//            })
//            Box(modifier = Modifier.fillMaxWidth()) {
//                getList().forEach {
//                    TheList(name = it.name, qty = it.qty, mrp = it.mrp)
//                }
//            }
        }
    }

    private fun getItemList(): List<Item> {
        val list = ArrayList<Item>()
        for (i in 0 until 100) {
            val item = Item(
                R.drawable.ic_launcher_foreground,
                java.util.UUID.randomUUID().toString(),
                java.util.UUID.randomUUID().toString()
            )
            list.add(item)
        }
        return list
    }
}

@Composable
fun LazyRowItemsDemo(viewModel: MainActivityVm, width: Int, resources: Resources) {
    val revealedCardIds = viewModel.revealedCardIdsList.collectAsState()
    LazyColumn {
        items(viewModel.cards.value) {
            AddBoxes(
                it = it,
                isReveled = revealedCardIds.value.contains(it),
                viewModel = viewModel,
                width,
                resources
            )
        }
    }
}

@Composable
fun AddBoxes(
    it: Int,
    isReveled: Boolean,
    viewModel: MainActivityVm,
    width: Int,
    resources: Resources
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val size = remember { mutableStateOf(IntSize.Zero) }

        BackgroundItem(it)
        ForeGroundItem(it, isReveled, viewModel, width)
    }
}

@Composable
fun ForeGroundItem(it: Int, isReveled: Boolean, viewModel: MainActivityVm, width: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset {
                if (isReveled) {
                    IntOffset(-(width / 3), 0)
                } else {
                    IntOffset(0, 0)
                }
            }
            .padding(8.dp)
            .background(Color.LightGray)
            .pointerInput(Unit) {
                forEachGesture {
                    this.detectHorizontalDragGestures { change, dragAmount ->
                        val newValue = change.positionChange()
                        if (newValue.x <= 20) {
                            viewModel.onItemExpanded(it)
                            return@detectHorizontalDragGestures
                        } else if (newValue.x >= 0) {
                            viewModel.onItemCollapsed(it)
                            return@detectHorizontalDragGestures
                        }
                        change.consumePositionChange()
                    }
                }
            }
            .border(width = 1.dp, Color.Gray, shape = Shapes.medium),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = it.toString(),
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

@Composable
fun BackgroundItem(it: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Magenta),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth(0.5F)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Green)
                .clickable {
                    println("clickable --> $it")
                },
        ) {
            Text(
                text = it.toString(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddItem(img: Int?, name: String?, details: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .pointerInput(Unit) {
                forEachGesture {
                    this.detectHorizontalDragGestures { change, dragAmount ->
                        println("$dragAmount | ${change.previousPosition} | ${change.position} | ${change.type}")
                    }
                    this.detectDragGestures { change, dragAmount ->
                        println("$dragAmount | ${change.previousPosition} | ${change.position} | ${change.type}")
                    }
                }
            }
            .border(width = 1.dp, Color.Gray, shape = Shapes.medium)
//            .verticalScroll(rememberScrollState())
            .horizontalScroll(state = ScrollState(0), enabled = true, reverseScrolling = false),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        img?.let {
            Image(
                painterResource(id = it),
                contentDescription = "",
                alignment = Alignment.BottomCenter,
                colorFilter = ColorFilter.tint(color = Color.Green)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name.toString(), Modifier.padding(4.dp))
            Divider(thickness = 8.dp, color = Color.Transparent)
            Text(text = details.toString(), Modifier.padding(4.dp))
        }
    }
}

val pointerModifier = Modifier
    .pointerInput(Unit) {
        forEachGesture {
            awaitPointerEventScope {
                awaitFirstDown()
                // ACTION_DOWN here
                do {
                    //This PointerEvent contains details including
                    // event, id, position and more
                    val event: PointerEvent = awaitPointerEvent()
                    // ACTION_MOVE loop
                    // Consuming event prevents other gestures or scroll to intercept
                    event.changes.forEach { pointerInputChange: PointerInputChange ->
                        pointerInputChange.consumePositionChange()
                    }
                } while (event.changes.any { it.pressed })
                // ACTION_UP is here
            }
        }
    }

@Composable
fun TheList(name: String, qty: String, mrp: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.Gray, Shapes.small)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                }) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = name
            )
        }
        Divider(
            modifier = Modifier
                .height(8.dp)
                .padding(8.dp)
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = qty,
            )
            Text(
                text = mrp,
            )
        }
    }
}

fun getList(): List<ListData> {
    val list = ArrayList<ListData>()
    for (i in 0..10) {
        list.add(
            ListData(
                getRandomString(20),
                "Qty: " + (0..100).random().toString(),
                "Mrp: " + (0..100).random().toString()
            )
        )
    }
    return list
}

fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

data class ListData(
    val name: String,
    val qty: String,
    val mrp: String
)