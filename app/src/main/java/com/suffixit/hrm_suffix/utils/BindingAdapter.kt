package com.suffixit.hrm_suffix.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


fun ImageView.setImage(url:String){
    Picasso.get().load(url).into(this)
}