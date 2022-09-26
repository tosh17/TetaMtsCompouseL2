package ru.thstdio.tetamtscompousel2

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

const val BOX_HEIGHT = 60

private val RedBoxState =
    BoxState(color = Color.Red, rotate = 15f, boxOffset = BOX_HEIGHT + 8)
private val GreenBoxState =
    BoxState(color = Color.Green, rotate = 30f, boxOffset = (BOX_HEIGHT + 8) * 2)
private val BlueBoxState = BoxState(color = Color.Blue)

@Composable
fun ScreenAdvance() {
    var isDrag by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val offsetState = OffsetState(offsetX, offsetY, isDrag)


    val update: (Float, Float) -> Unit = { x, y ->
        isDrag = true
        offsetX += x
        offsetY += y
    }
    val finish: () -> Unit = {
        offsetX = 0f
        offsetY = 0f
        isDrag = false
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BoxColor(boxState = RedBoxState, offsetState)
        BoxColor(boxState = GreenBoxState, offsetState)
        MainBox(
            boxState = BlueBoxState,
            offsetState = offsetState,
            update = update,
            finish = finish
        )

    }
}


@Composable
private fun BoxScope.MainBox(
    boxState: BoxState,
    offsetState: OffsetState,
    update: (Float, Float) -> Unit,
    finish: () -> Unit,
) {
    val offsetXAnimated by animateFloatAsState(
        targetValue = offsetState.x,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )
    val offsetYAnimated by animateFloatAsState(
        targetValue = if (offsetState.isDrag) offsetState.y - boxState.boxOffset.toPx() else 0f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )
    Box(
        Modifier
            .createBoxDragModifier(
                color = boxState.color,
                offsetX = offsetXAnimated,
                offsetY = offsetYAnimated,
                update = update,
                finish = finish
            )
    )
}

@Composable
private fun BoxScope.BoxColor(boxState: BoxState, offsetState: OffsetState) {
    val offsetXAnimated by animateFloatAsState(
        targetValue = offsetState.x,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = 10,
            easing = LinearOutSlowInEasing
        )
    )
    val offsetYAnimated by animateFloatAsState(
        targetValue = if (offsetState.isDrag) offsetState.y - boxState.boxOffset.toPx() else 0f,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = 10,
            easing = LinearOutSlowInEasing
        )
    )
    val rotateAnimated by animateFloatAsState(
        targetValue = if (offsetState.isDrag) 0f else boxState.rotate,
        animationSpec = tween(
            durationMillis = if (offsetState.isDrag) 50 else 400,
            delayMillis = if (offsetState.isDrag) 0 else 400,
            easing = LinearOutSlowInEasing
        )
    )
    Box(
        Modifier
            .createBoxModifier(
                color = boxState.color,
                offsetX = offsetXAnimated,
                offsetY = offsetYAnimated,
                rotate = rotateAnimated
            )
    )
}


@Composable
private fun Modifier.createBoxModifier(
    color: Color,
    offsetX: Float,
    offsetY: Float,
    rotate: Float = 0f
): Modifier {
    return rotateByRightBottom(rotate)
        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .height(BOX_HEIGHT.dp)
        .width((2 * BOX_HEIGHT).dp)
        .clip(RoundedCornerShape(12.dp))
        .background(color.copy(alpha = 0.5f))
}

@Composable
private fun Modifier.createBoxDragModifier(
    color: Color,
    offsetX: Float,
    offsetY: Float,
    rotate: Float = 0f,
    update: (Float, Float) -> Unit,
    finish: () -> Unit,
): Modifier {
    return pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = { finish() }) { change, dragAmount ->
            change.consumeAllChanges()
            update(dragAmount.x, dragAmount.y)
        }
    }.createBoxModifier(color, offsetX, offsetY, rotate)
}


@Composable
private fun Modifier.rotateByRightBottom(rotation: Float): Modifier {
    return graphicsLayer(
        rotationZ = rotation,
        transformOrigin = TransformOrigin(1f, 1f)
    )
}

@Composable
fun Int.toPx(): Float {
    val number = this
    return with(LocalDensity.current) { number.dp.toPx() }
}

internal data class OffsetState(val x: Float, val y: Float, val isDrag: Boolean)
internal data class BoxState(val color: Color, val rotate: Float = 0F, val boxOffset: Int = 0)
