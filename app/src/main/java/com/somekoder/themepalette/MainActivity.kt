package com.somekoder.themepalette

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.somekoder.themepalette.domain.utils.hexToString
import com.somekoder.themepalette.domain.utils.invertedLuminance
import com.somekoder.themepalette.domain.utils.readInstanceProperty
import com.somekoder.themepalette.ui.theme.AppTheme
import kotlin.reflect.full.declaredMemberProperties

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Scaffold(
                    topBar = {
                        SmallTopAppBar(
                            title = {
                                Text(text = "Theme Palette")
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                        )
                    },
                    content = {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            val items =  MaterialTheme.colorScheme::class.declaredMemberProperties.map { colorProperty ->
                                MaterialColorItem(
                                    name = colorProperty.name,
                                    background = readInstanceProperty(MaterialTheme.colorScheme, colorProperty.name),
                                    textColor = readInstanceProperty<Color>(MaterialTheme.colorScheme, colorProperty.name).invertedLuminance()
                                )
                            }

                            LazyVerticalGrid(
                                cells = GridCells.Adaptive(160.dp),
                                content = {
                                    items(items = items) { item ->
                                        MaterialColorView(item = item)
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MaterialColorView(item: MaterialColorItem) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.name,
                color = item.textColor,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.background.hexToString(),
                color = item.textColor,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}



data class MaterialColorItem(val name: String, val background: Color, val textColor: Color)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainActivity()
}