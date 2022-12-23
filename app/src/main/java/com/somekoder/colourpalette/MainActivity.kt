package com.somekoder.colourpalette

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.reflect.KProperty1
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.somekoder.colourpalette.ui.theme.ColourPaletteTheme
import kotlin.reflect.full.declaredMemberProperties

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColourPaletteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val items =  MaterialTheme.colorScheme::class.declaredMemberProperties.map {
                        MaterialColorView(
                            name = it.name,
                            background = readInstanceProperty(MaterialTheme.colorScheme, it.name),
                            textColor = readInstanceProperty<Color>(MaterialTheme.colorScheme, it.name).invertedLuminance()
                        )
                    }

                    LazyVerticalGrid(
                        cells = GridCells.Fixed(2),
                        content = {
                            items(items = items) { item ->
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .aspectRatio(1f),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 6.dp,
                                        pressedElevation = 8.dp,
                                        disabledElevation = 0.dp
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = item.background),
                                    onClick = {

                                    },
                                ) {
                                    Text(
                                        text = item.name,
                                        color = item.textColor
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

fun Color.invert() : Color {
    println("brandonn: Converting color: $red, $green, $blue")
    val r = 1 - this.red
    val g = 1 - this.green
    val b = 1 - this.blue
    luminance()
    return Color(red = r, green = g, blue = b)
}

fun Color.invertedLuminance() : Color {
    return if (luminance() > 0.50) {
        Color.Black
    }
    else {
        Color.White
    }
}

@Suppress("UNCHECKED_CAST")
fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
    val property = instance::class.members
        .first { it.name == propertyName } as KProperty1<Any, *>
    return property.get(instance) as R
}

data class MaterialColorView(val name: String, val background: Color, val textColor: Color)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ColourPaletteTheme {

    }
}