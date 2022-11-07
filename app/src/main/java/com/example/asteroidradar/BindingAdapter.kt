package com.example.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.asteroidradar.models.PictureOfDay
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription=imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
        imageView.setImageResource(R.drawable.ic_baseline_insert_emoticon_24)
    } else {
        imageView.contentDescription=imageView.context.getString(R.string.not_hazardous_asteroid_image)
        imageView.setImageResource(R.drawable.ic_baseline_emoji_emotions_24)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription=imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
        imageView.setImageResource(R.drawable.asteroid_safe)
    } else {
        imageView.contentDescription=imageView.context.getString(R.string.not_hazardous_asteroid_image)
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

