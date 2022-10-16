package little.goose.drawable

interface StateElement {

    val states: IntArray

    operator fun plus(other: State): StateElement {
        val state = other.states[0]
        return if (states.contains(state)) this else object : StateElement {
            override val states: IntArray = this@StateElement.states + state
        }
    }

    companion object {
        val defStateElement
            get() = object : StateElement {
                override val states: IntArray = intArrayOf()
            }
    }
}

enum class State(override val states: IntArray) : StateElement {

    FOCUSED(intArrayOf(android.R.attr.state_focused)),
    WINDOW_FOCUSED(intArrayOf(android.R.attr.state_window_focused)),
    ENABLED(intArrayOf(android.R.attr.state_enabled)),
    CHECKABLE(intArrayOf(android.R.attr.state_checkable)),
    CHECKED(intArrayOf(android.R.attr.state_checked)),
    SELECTED(intArrayOf(android.R.attr.state_selected)),
    PRESSED(intArrayOf(android.R.attr.state_pressed)),
    ACTIVATED(intArrayOf(android.R.attr.state_activated)),
    ACTIVE(intArrayOf(android.R.attr.state_active)),
    SINGLE(intArrayOf(android.R.attr.state_single)),
    FIRST(intArrayOf(android.R.attr.state_first)),
    MIDDLE(intArrayOf(android.R.attr.state_middle)),
    LAST(intArrayOf(android.R.attr.state_last)),
    ACCELERATED(intArrayOf(android.R.attr.state_accelerated)),
    HOVERED(intArrayOf(android.R.attr.state_hovered)),
    DRAG_CAN_ACCEPT(intArrayOf(android.R.attr.state_drag_can_accept)),
    DRAG_HOVERED(intArrayOf(android.R.attr.state_drag_hovered))

}