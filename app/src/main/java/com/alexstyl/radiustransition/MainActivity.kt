package com.alexstyl.radiustransition

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.avatar)

        Glide.with(this)
                .asBitmap()
                .load(Images.PROFILE_IMAGE)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val drawable = RoundedBitmapDrawableFactory.create(resources, resource)
                        drawable.cornerRadius = 1000F
                        imageView.setImageDrawable(drawable)
                    }
                })

        imageView.setOnClickListener {

            val intent = Intent(this, TargetActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity, imageView, ViewCompat.getTransitionName(imageView))

            startActivity(intent, options.toBundle())
        }
    }
}
