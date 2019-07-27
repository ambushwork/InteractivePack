package com.netatmo.ylu.interactivepack.coordinator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.netatmo.ylu.interactivepack.R
import com.squareup.picasso.Picasso

class PersonDialogFragment() : DialogFragment() {


    companion object {
        fun newInstance(transitionName: String): PersonDialogFragment {
            val instance = PersonDialogFragment()
            val args = Bundle()
            args.putString("transitionName", transitionName)
            instance.arguments = args
            return instance
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.demo_dialog_person_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.dialog_image_view)
        val transitionName = arguments?.getString("transitionName")
        imageView.transitionName = transitionName
        Picasso.get().load(transitionName).into(imageView)
        dialog?.setContentView(R.layout.demo_dialog_person_detail)
    }


}