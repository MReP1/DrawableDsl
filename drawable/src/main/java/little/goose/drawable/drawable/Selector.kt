package little.goose.drawable.drawable

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import little.goose.drawable.DrawableDslMarker
import little.goose.drawable.StateElement

inline fun selector(
    selectorBuilder: @DrawableDslMarker Selector.() -> Unit
): StateListDrawable {
    return Selector().apply(selectorBuilder).build()
}

class Selector {

    @PublishedApi
    internal val selectorStates = mutableListOf<SelectorState>()

    inline fun withState(
        stateElement: StateElement,
        selectorStateBuilder: @DrawableDslMarker SelectorState.() -> Unit
    ) {
        selectorStates.add(
            SelectorState(stateElement).apply(selectorStateBuilder)
        )
    }

    inline fun defState(
        selectorStateBuilder: @DrawableDslMarker SelectorState.() -> Unit
    ) {
        selectorStates.add(
            SelectorState(StateElement.defStateElement).apply(selectorStateBuilder)
        )
    }

    fun build() = StateListDrawable().apply {
        for (selectorState in selectorStates) {
            selectorState.drawable?.let { drawable ->
                addState(selectorState.stateElement.states, drawable)
            }
        }
    }

    class SelectorState(internal val stateElement: StateElement) {
        var drawable: Drawable? = null
    }

}