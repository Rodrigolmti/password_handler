package br.com.rodrigolmti.security.biometric

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import br.com.rodrigolmti.security.R

class BiometricIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    enum class State {
        OFF,
        ON,
        ERROR
    }

    init {
        setState(if (isInEditMode) State.ON else State.OFF, false)
    }

    var state = State.OFF
        internal set

    fun setState(newState: State, animate: Boolean = true) {
        if (newState == state) return

        @DrawableRes val resId = getDrawable(state, newState, animate)

        if (resId == 0) {
            setImageDrawable(null)
        } else {
            var icon: Drawable? = null

            if (animate) {
                icon = AnimatedVectorDrawableCompat.create(context, resId)
            }

            if (icon == null) {
                icon = VectorDrawableCompat.create(resources, resId, context.theme)
            }

            setImageDrawable(icon)

            if (icon is Animatable) {
                (icon as Animatable).start()
            }
        }

        state = newState
    }

    @DrawableRes
    private fun getDrawable(currentState: State, newState: State, animate: Boolean): Int {
        when (newState) {
            State.OFF -> {
                if (animate) {
                    if (currentState == State.ON) {
                        return R.drawable.fingerprint_draw_off_animation
                    } else if (currentState == State.ERROR) {
                        return R.drawable.fingerprint_error_off_animation
                    }
                }

                return 0
            }
            State.ON -> {
                if (animate) {
                    if (currentState == State.OFF) {
                        return R.drawable.fingerprint_draw_on_animation
                    } else if (currentState == State.ERROR) {
                        return R.drawable.fingerprint_error_state_to_fp_animation
                    }
                }

                return R.drawable.fingerprint_fingerprint
            }
            State.ERROR -> {
                if (animate) {
                    if (currentState == State.ON) {
                        return R.drawable.fingerprint_fp_to_error_state_animation
                    } else if (currentState == State.OFF) {
                        return R.drawable.fingerprint_error_on_animation
                    }
                }

                return R.drawable.fingerprint_error
            }
            else -> throw IllegalArgumentException("Unknown state: $newState")
        }
    }
}