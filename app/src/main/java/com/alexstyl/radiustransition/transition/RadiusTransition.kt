package com.alexstyl.radiustransition.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.transition.Transition
import android.transition.TransitionValues
import android.util.FloatProperty
import android.view.ViewGroup
import android.widget.ImageView


class RadiusTransition private constructor(private val startingRadius: Float, private val endingRadius: Float) : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) {
        if (transitionValues.view is ImageView) {
            transitionValues.values[PROPNAME_RADIUS] = startingRadius
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        if (transitionValues.view is ImageView) {
            transitionValues.values[PROPNAME_RADIUS] = endingRadius
        }
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator? {

        if (startValues == null || endValues == null) {
            return null
        }

        val endImageView = endValues.view as ImageView
        val start = startValues.values[PROPNAME_RADIUS] as Float
        val end = endValues.values[PROPNAME_RADIUS] as Float

        val objectAnimator = ObjectAnimator
                .ofFloat(endImageView, RADIUS_PROPERTY, start, end)
                .setDuration(super.getDuration())
        objectAnimator.interpolator = super.getInterpolator()
        return objectAnimator
    }

    companion object {

        private const val PROPNAME_RADIUS = "RadiusTransition:radius"

        private val RADIUS_PROPERTY = object : FloatProperty<ImageView>("radius") {

            override fun get(imageView: ImageView): Float {
                val drawable = imageView.drawable as RoundedBitmapDrawable
                return drawable.cornerRadius
            }

            override fun setValue(imageView: ImageView, radius: Float) {
                val drawable = imageView.drawable as RoundedBitmapDrawable
                drawable.cornerRadius = radius
            }
        }

        fun toCircle(): RadiusTransition {
            return RadiusTransition(0F, 1000F)
        }

        fun toSquare(): RadiusTransition {
            return RadiusTransition(1000F, 0F)
        }
    }
}
