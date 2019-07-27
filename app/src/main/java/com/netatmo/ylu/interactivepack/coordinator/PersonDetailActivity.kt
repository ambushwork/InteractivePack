package com.netatmo.ylu.interactivepack.coordinator

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.netatmo.ylu.interactivepack.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.demo_dialog_person_detail.*
import java.lang.Exception


class PersonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.demo_dialog_person_detail)
        val url = intent.extras?.getString("TRANSITION")
        postponeEnterTransition()
        Picasso.get().load(url).into(object : com.squareup.picasso.Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                dialog_image_view.setImageBitmap(bitmap)
                supportStartPostponedEnterTransition()
            }

        })
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}