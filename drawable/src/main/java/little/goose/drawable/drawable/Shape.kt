package little.goose.drawable.drawable

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.annotation.ColorInt
import little.goose.drawable.DrawableDslMarker

inline fun shape(
    shapeType: Shape.Type,
    shape: @DrawableDslMarker Shape.() -> Unit
): GradientDrawable {
    return Shape(shapeType).apply(shape).build()
}

class Shape(private val shapeType: Type) {

    enum class Type {
        RECTANGLE, OVAL, LINE, RING
    }

    @PublishedApi
    internal var corner: Corners? = null

    @PublishedApi
    internal var padding: Padding? = null

    @PublishedApi
    internal var gradient: Gradient? = null

    @PublishedApi
    internal var stroke: Stroke? = null

    @PublishedApi
    internal var solid: Solid? = null

    inline fun solid(solidBuilder: @DrawableDslMarker Solid.() -> Unit) {
        solid = Solid().apply(solidBuilder)
    }

    inline fun stoke(strokeBuilder: @DrawableDslMarker Stroke.() -> Unit) {
        this.stroke = Stroke().apply(strokeBuilder)
    }

    inline fun corner(cornerBuilder: @DrawableDslMarker Corners.() -> Unit) {
        corner = Corners().apply(cornerBuilder)
    }

    inline fun padding(paddingBuilder: @DrawableDslMarker Padding.() -> Unit) {
        padding = Padding().apply(paddingBuilder)
    }

    inline fun gradient(gradientBuilder: @DrawableDslMarker Gradient.() -> Unit) {
        gradient = Gradient().apply(gradientBuilder)
    }

    fun build() = GradientDrawable().apply {
        shape = shapeType.ordinal

        gradient?.let { gradient ->
            colors = gradient.colors
            useLevel = gradient.useLevel
            gradientType = gradient.type.ordinal
            gradientRadius = gradient.radius
            orientation = gradient.orientation
            setGradientCenter(gradient.centerX, gradient.centerY)
        } ?: run {
            color = ColorStateList.valueOf(solid?.color ?: 0)
        }

        padding?.let { padding ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setPadding(padding.left, padding.top, padding.right, padding.bottom)
            }
        }

        stroke?.let { stroke ->
            setStroke(stroke.width, stroke.color, stroke.dashWidth, stroke.dashGap)
        }

        corner?.let { corner ->
            cornerRadii = corner.radiusArray
        }
    }

    class Solid {
        @ColorInt
        var color: Int = 0
    }

    class Corners {

        var radius: Float = 0F

        var topLeft: Float? = null
        var topRight: Float? = null
        var bottomLeft: Float? = null
        var bottomRight: Float? = null

        @PublishedApi
        internal var topLeftRadius: Radius? = null

        @PublishedApi
        internal var topRightRadius: Radius? = null

        @PublishedApi
        internal var bottomLeftRadius: Radius? = null

        @PublishedApi
        internal var bottomRightRadius: Radius? = null

        @PublishedApi
        internal val radiusArray
            get() = floatArrayOf(
                topLeftRadius?.x ?: topLeft ?: radius,
                topLeftRadius?.y ?: topLeft ?: radius,
                topRightRadius?.x ?: topRight ?: radius,
                topRightRadius?.y ?: topRight ?: radius,
                bottomLeftRadius?.x ?: bottomLeft ?: radius,
                bottomLeftRadius?.y ?: bottomLeft ?: radius,
                bottomRightRadius?.x ?: bottomRight ?: radius,
                bottomRightRadius?.y ?: bottomRight ?: radius
            )

        inline fun topLeftRadius(radiusBuilder: @DrawableDslMarker Radius.() -> Unit) {
            topLeftRadius = Radius().apply(radiusBuilder)
        }

        inline fun topRightRadius(radiusBuilder: @DrawableDslMarker Radius.() -> Unit) {
            topRightRadius = Radius().apply(radiusBuilder)
        }

        inline fun bottomLeftRadius(radiusBuilder: @DrawableDslMarker Radius.() -> Unit) {
            bottomLeftRadius = Radius().apply(radiusBuilder)
        }

        inline fun bottomRightRadius(radiusBuilder: @DrawableDslMarker Radius.() -> Unit) {
            bottomRightRadius = Radius().apply(radiusBuilder)
        }

        class Radius {
            var x: Float = 0F
            var y: Float = 0F
        }
    }

    class Stroke {
        private var dash: Dash? = null

        @ColorInt
        var color: Int = Color.TRANSPARENT
        var width: Int = 0
        val dashWidth: Float get() = dash?.width ?: 0F
        val dashGap: Float get() = dash?.gap ?: 0F

        fun dash(dashBuilder: Dash.() -> Unit) {
            dash = Dash().apply(dashBuilder)
        }

        class Dash {
            var width: Float = 0F
            var gap: Float = 0F
        }
    }

    class Padding {
        var left: Int = 0
        var top: Int = 0
        var right: Int = 0
        var bottom: Int = 0
    }

    class Gradient {

        enum class Type {
            LINEAR, RADIAL, SWEEP
        }

        private var color: Color? = null

        var orientation = GradientDrawable.Orientation.TOP_BOTTOM
        var type = Type.LINEAR
        var radius = 0F
        var centerX = 0F
        var centerY = 0F
        var useLevel = false

        val colors
            get() = color?.let { color ->
                color.center?.let { centerColor ->
                    intArrayOf(color.start, centerColor, color.end)
                } ?: intArrayOf(color.start, color.end)
            } ?: intArrayOf(0, 0)

        fun color(colorBuilder: Color.() -> Unit) {
            color = Color().apply(colorBuilder)
        }

        class Color {
            @ColorInt
            var start: Int = 0

            @ColorInt
            var center: Int? = null

            @ColorInt
            var end: Int = 0
        }
    }
}