package com.omnidroid.app.mobile.shared.compose.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

// 3DS HOME Menu design tokens: pastel icon fills, tile chrome and text grays.
val ThreeDsPastelGreen = Color(0xFFB7E4A8)
val ThreeDsPastelOrange = Color(0xFFFFCC99)
val ThreeDsPastelPink = Color(0xFFFFB7C9)
val ThreeDsPastelBlue = Color(0xFFA8D8F0)
val ThreeDsPastelYellow = Color(0xFFFFE59A)
val ThreeDsPastelPurple = Color(0xFFCDBBF0)

val ThreeDsTileWhite = Color(0xFFFFFFFF)
val ThreeDsTileBorder = Color(0xFFE3DFD2)
val ThreeDsSelectedBorder = Color(0xFF8A867C)
val ThreeDsTextDark = Color(0xFF3C3A35)
val ThreeDsTextGray = Color(0xFF8A867C)

private val ThreeDsPastels =
    listOf(
        ThreeDsPastelGreen,
        ThreeDsPastelOrange,
        ThreeDsPastelBlue,
        ThreeDsPastelPink,
        ThreeDsPastelYellow,
        ThreeDsPastelPurple,
    )

fun threeDsPastelFor(index: Int): Color = ThreeDsPastels[Math.floorMod(index, ThreeDsPastels.size)]

/**
 * Cream base with the faint diagonal hatch of the classic 3DS HOME Menu, plus a soft
 * vertical light-to-warm gradient for depth. Apply as the root background of a screen.
 */
fun Modifier.threeDsStripedBackground(
    base: Color = HomeChromeBackground,
    stripe: Color = ThreeDsTextDark,
): Modifier =
    drawBehind {
        drawRect(color = base)
        val stripeWidth = 7.dp.toPx()
        val gap = 21.dp.toPx()
        val period = stripeWidth + gap
        val center = Offset(size.width / 2f, size.height / 2f)
        val halfDiagonal = sqrt(size.width * size.width + size.height * size.height) / 2f
        rotate(degrees = 45f, pivot = center) {
            var x = center.x - halfDiagonal - period
            val end = center.x + halfDiagonal + period
            while (x < end) {
                drawRect(
                    color = stripe.copy(alpha = 0.045f),
                    topLeft = Offset(x, center.y - halfDiagonal - period),
                    size = Size(stripeWidth, halfDiagonal * 2f + period * 2f),
                )
                x += period
            }
        }
    }
