package com.example.campnavfinal.mvvm

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider


class CenteredPopupPositionProvider(
    private val horizontalOffset: Int = 0,
    private val verticalOffset: Int = 0
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val horizontalPosition = calculateHorizontalPosition(windowSize, popupContentSize)
        val verticalPosition = calculateVerticalPosition(windowSize, popupContentSize)

        return IntOffset(horizontalPosition + horizontalOffset, verticalPosition + verticalOffset)
    }

    private fun calculateHorizontalPosition(windowSize: IntSize, popupContentSize: IntSize): Int {
        val windowWidth = windowSize.width
        val popupWidth = popupContentSize.width

        return (windowWidth - popupWidth) / 2
    }

    private fun calculateVerticalPosition(windowSize: IntSize, popupContentSize: IntSize): Int {
        val windowHeight = windowSize.height
        val popupHeight = popupContentSize.height

        return (windowHeight - popupHeight) / 2
    }
}

