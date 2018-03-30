package com.alexstyl.radiustransition

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.widget.ImageView
import com.alexstyl.radiustransition.transition.RadiusTransition
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition


class TargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postponeEnterTransition()

        setContentView(R.layout.activity_target)

        window.sharedElementEnterTransition = baseInterpolator()
                .addTransition(RadiusTransition.toSquare())

        window.sharedElementReturnTransition =
                baseInterpolator()
                        .addTransition(RadiusTransition.toCircle())

        val imageView = findViewById<ImageView>(R.id.avatar)

        Glide.with(this)
                .asBitmap()
                .load(Images.PROFILE_IMAGE)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val drawable = RoundedBitmapDrawableFactory.create(resources, resource)
                        drawable.cornerRadius = 0F
                        imageView.setImageDrawable(drawable)
                        startPostponedEnterTransition()
                    }
                })

        imageView.setOnClickListener {
            finishAfterTransition()
        }
    }

    private fun baseInterpolator(): TransitionSet =
            TransitionInflater.from(this).inflateTransition(R.transition.base_transition_set) as TransitionSet
}
