package com.netatmo.ylu.interactivepack.imageswitcher

import android.os.Bundle
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.netatmo.ylu.interactivepack.R
import com.netatmo.ylu.interactivepack.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.demo_activity_image_switcher.*

class ImageSwitcherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_image_switcher)
        Picasso.get().load(Utils.getRandomLargePic(1)).into(image_view_1)
        Picasso.get().load(Utils.getRandomLargePic(2)).into(image_view_2)
        Picasso.get().load(Utils.getRandomLargePic(3)).into(image_view_3)
        Picasso.get().load(Utils.getRandomLargePic(4)).into(image_view_4)
        flip_button.setOnClickListener {
            view_flipper.startFlipping()

        }
    }
}