package little.goose.drawable.color

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import little.goose.drawable.DrawableDslMarker
import little.goose.drawable.StateElement

fun colorSelector(
    colorSelectorBuilder: @DrawableDslMarker ColorSelector.() -> Unit
): ColorStateList {
    return ColorSelector().apply(colorSelectorBuilder).build()
}

class ColorSelector {

    @PublishedApi
    internal val stateColors = mutableListOf<StateColor>()

    inline fun withState(
        stateElement: StateElement,
        stateColorBuilder: @DrawableDslMarker StateColor.() -> Unit
    ) {
        stateColors.add(StateColor(stateElement).apply(stateColorBuilder))
    }

    inline fun defState(
        stateColorBuilder: @DrawableDslMarker StateColor.() -> Unit
    ) {
        stateColors.add(StateColor(StateElement.defStateElement).apply(stateColorBuilder))
    }

    fun build(): ColorStateList {
        val stateList = mutableListOf<IntArray>()
        val colorList = mutableListOf<Int>()
        for (stateColor in stateColors) {
            stateList.add(stateColor.stateElement.states)
            colorList.add(stateColor.color)
        }
        return ColorStateList(stateList.toTypedArray(), colorList.toIntArray())
    }

    class StateColor(internal val stateElement: StateElement) {
        @ColorInt
        var color: Int = 0
    }

}