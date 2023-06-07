package com.judahben149.spendr.utils.extensions

import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.airbnb.lottie.LottieAnimationView
import com.judahben149.spendr.R
import soup.neumorphism.NeumorphButton
import soup.neumorphism.NeumorphCardView
import soup.neumorphism.NeumorphImageButton

fun NeumorphImageButton.highlight() {
    this.setBackgroundColor(resources.getColor(R.color.neu_btn_highlighted_background))
    this.setShadowColorDark(resources.getColor(R.color.neu_btn_highlighted_shadow_color_dark))
    this.setShadowColorLight(resources.getColor(R.color.neu_btn_highlighted_shadow_color_light))
}

fun NeumorphImageButton.unHighlight() {
    this.setBackgroundColor(resources.getColor(R.color.neu_btn_unHighlighted_background))
    this.setShadowColorDark(resources.getColor(R.color.neu_btn_unHighlighted_shadow_color_dark))
    this.setShadowColorLight(resources.getColor(R.color.neu_btn_unHighlighted_shadow_color_light))
}

fun NeumorphCardView.highlight() {
    this.setBackgroundColor(resources.getColor(R.color.neu_btn_highlighted_background))
    this.setShadowColorDark(resources.getColor(R.color.neu_btn_highlighted_shadow_color_dark))
    this.setShadowColorLight(resources.getColor(R.color.neu_btn_highlighted_shadow_color_light))
}

fun NeumorphCardView.unHighlight() {
    this.setBackgroundColor(resources.getColor(R.color.neu_btn_unHighlighted_background))
    this.setShadowColorDark(resources.getColor(R.color.neu_btn_unHighlighted_shadow_color_dark))
    this.setShadowColorLight(resources.getColor(R.color.neu_btn_unHighlighted_shadow_color_light))
}


fun ImageView.setIcon(@DrawableRes iconResId: Int) {
    this.setImageResource(iconResId)
}

fun ImageView.mapCategoryIcon(categoryIconId: Int) {
    when(categoryIconId) {
        1 -> {
            this.setImageResource(R.drawable.automobile)
        }

        2 -> {
            this.setImageResource(R.drawable.bank)
        }

        3 -> {
            this.setImageResource(R.drawable.charity)
        }

        4 -> {
            this.setImageResource(R.drawable.child_care)
        }

        5 -> {
            this.setImageResource(R.drawable.clothing)
        }

        6 -> {
            this.setImageResource(R.drawable.education)
        }

        7 -> {
            this.setImageResource(R.drawable.entertainment)
        }

        8 -> {
            this.setImageResource(R.drawable.fitness)
        }

        9 -> {
            this.setImageResource(R.drawable.food)
        }

        10 -> {
            this.setImageResource(R.drawable.gift)
        }

        11 -> {
            this.setImageResource(R.drawable.grocery)
        }

        12 -> {
            this.setImageResource(R.drawable.loan)
        }

        13 -> {
            this.setImageResource(R.drawable.medical)
        }

        14 -> {
            this.setImageResource(R.drawable.miscellaneous)
        }

        15 -> {
            this.setImageResource(R.drawable.pet)
        }

        16 -> {
            this.setImageResource(R.drawable.rent)
        }

        17 -> {
            this.setImageResource(R.drawable.savings)
        }

        18 -> {
            this.setImageResource(R.drawable.shopping)
        }

        19 -> {
            this.setImageResource(R.drawable.subscription)
        }

        20 -> {
            this.setImageResource(R.drawable.tax)
        }

        21 -> {
            this.setImageResource(R.drawable.transport)
        }

        22 -> {
            this.setImageResource(R.drawable.travel)
        }

        23 -> {
            this.setImageResource(R.drawable.utility)
        }

        24 -> {
            this.setImageResource(R.drawable.salary)
        }

        else -> {
            //icon for uncategorized cash entries
            this.setImageResource(R.drawable.question_mark)
        }
    }
}

fun TextView.animateToolBarTitle() {
    this.translationX = 50f
    this.alpha = 0f

    this.animate()
        .translationX(0f)
        .alpha(1f)
        .setDuration(250)
        .start()
}

fun LottieAnimationView.startDelayedAnimation(repeatDelayInMillis: Long) {
    this.progress = 0F
    this.playAnimation()

    Handler().postDelayed({
        startDelayedAnimation(repeatDelayInMillis)
    }, repeatDelayInMillis)
}