package ru.thstdio.tetamtscompousel2

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ScreenBasic() {
    val style = TextStyle(color = Color.White, textAlign = TextAlign.Center)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 84.dp
    val sizePx = with(LocalDensity.current) { screenWidth.toPx() }

    val swipeableState = rememberSwipeableState(0)
    val anchors = mapOf(0f to 0, sizePx to 1)

    val alpha = 1 - swipeableState.offset.value / sizePx
    val iconIsDone = swipeableState.offset.value / sizePx > 0.9
    val transition = updateTransition(iconIsDone)
    Box(
        modifier = Modifier
            .padding(top = 128.dp)
            .height(70.dp)
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .swipeable(
                state = swipeableState,
                orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                anchors = anchors
            )
            .background(Color.Green)
            .padding(6.dp)
    ) {
        Column(Modifier.align(Alignment.Center).alpha(alpha)) {
            Text(
                text = "Order 1000 rub",
                style = style,
                fontSize = 24.sp

            )
            Text(
                text = "Swipe to confirm",
                style = style,
                fontSize = 16.sp
            )
        }
        transition.AnimatedContent { targetState ->
            // По описанию вроде наиболее подходящее для смены состояния но получается с большим промаргиванием :(
            Icon(
                modifier = Modifier
                    .size(56.dp)
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .clip(CircleShape)
                    .background(Color.White),
                imageVector = if(!targetState) Icons.Rounded.ArrowForward else Icons.Rounded.Done,
                contentDescription = "Swipe",
                tint = Color.Green
            )
        }


    }

}