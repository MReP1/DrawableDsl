package little.goose.drawable.drawable

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.ColorInt

fun ripple(rippleBuilder: Ripple.() -> Unit): RippleDrawable {
    return Ripple().apply(rippleBuilder).build()
}

class Ripple {

    @ColorInt
    var color: Int = 0

    var content: Drawable? = null

    var mask: Drawable? = null

    inline fun content(contentBuilder: () -> Drawable) {
        content = contentBuilder()
    }

    inline fun mask(maskBuilder: () -> Drawable) {
        mask = maskBuilder()
    }

    fun build() = RippleDrawable(ColorStateList.valueOf(color), content, mask)
}