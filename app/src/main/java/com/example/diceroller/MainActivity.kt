package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme
import java.time.format.TextStyle
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center),
) {

    var result by remember { mutableStateOf(1) }
    var message = when (result) {
        1 -> stringResource(id = R.string.critical_failure)
        20 -> stringResource(id = R.string.critical_success)
        else -> ""
    }
    val imageResource = when (result) {
        1 -> R.drawable.d20_1
        2 -> R.drawable.d20_2
        3 -> R.drawable.d20_3
        4 -> R.drawable.d20_4
        5 -> R.drawable.d20_5
        6 -> R.drawable.d20_6
        7 -> R.drawable.d20_7
        8 -> R.drawable.d20_8
        9 -> R.drawable.d20_9
        10 -> R.drawable.d20_10
        11 -> R.drawable.d20_11
        12 -> R.drawable.d20_12
        13 -> R.drawable.d20_13
        14 -> R.drawable.d20_14
        15 -> R.drawable.d20_15
        16 -> R.drawable.d20_16
        17 -> R.drawable.d20_17
        18 -> R.drawable.d20_18
        19 -> R.drawable.d20_19
        else -> R.drawable.d20_20
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = result.toString(),
            modifier = Modifier.size(200.dp)
        )
        Text(text = message)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { result = (1..20).random() }) {
            Text(text = stringResource(R.string.roll))
        }
    }
}

@Composable
fun SpinningWheel() {
    val rotation by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val center = this.center
            val radius = size.minDimension / 2f
            drawCircle(
                color = Color.Black,
                center = center,
                radius = radius,
                style = Stroke(width = 4.dp.toPx())
            )
            drawNumbers(center, radius, rotation)
        }
    )
}

fun DrawScope.drawNumbers(center: Offset, radius: Float, rotation: Float) {
    val paint = android.graphics.Paint().apply {
        isAntiAlias = true
        textSize = radius / 20f
        color = android.graphics.Color.BLACK
    }

    for (i in 1..100) {
        val angle = (i - 5) * (2 * Math.PI / 100) + Math.toRadians(rotation.toDouble())
        val x = center.x + (radius * 0.95f * cos(angle)).toFloat()
        val y = center.y + (radius * 0.95f * sin(angle)).toFloat()
        drawContext.canvas.nativeCanvas.drawText(
            i.toString(),
            x - paint.measureText(i.toString()) / 2,
            y - (paint.descent() + paint.ascent()) / 2,
            paint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage()
}
