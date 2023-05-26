package com.judahben149.spendr.utils.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
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

fun ImageView.mapCategoryIcon(categoryName: String) {
    when(categoryName) {
        "Automobile" -> {
            this.setImageResource(R.drawable.automobile)
        }

        "Bank" -> {
            this.setImageResource(R.drawable.bank)
        }

        "Charity" -> {
            this.setImageResource(R.drawable.charity)
        }

        "Childcare" -> {
            this.setImageResource(R.drawable.child_care)
        }

        "Clothing" -> {
            this.setImageResource(R.drawable.clothing)
        }

        "Education" -> {
            this.setImageResource(R.drawable.education)
        }

        "Entertainment" -> {
            this.setImageResource(R.drawable.entertainment)
        }

        "Fitness" -> {
            this.setImageResource(R.drawable.fitness)
        }

        "Food" -> {
            this.setImageResource(R.drawable.food)
        }

        "Gifting" -> {
            this.setImageResource(R.drawable.gift)
        }

        "Groceries" -> {
            this.setImageResource(R.drawable.grocery)
        }

        "Loan" -> {
            this.setImageResource(R.drawable.loan)
        }

        "Medical" -> {
            this.setImageResource(R.drawable.medical)
        }

        "Miscellaneous" -> {
            this.setImageResource(R.drawable.miscellaneous)
        }

        "Pets" -> {
            this.setImageResource(R.drawable.pet)
        }

        "Rent" -> {
            this.setImageResource(R.drawable.rent)
        }

        "Salary" -> {
            this.setImageResource(R.drawable.salary)
        }

        "Savings" -> {
            this.setImageResource(R.drawable.savings)
        }

        "Shopping" -> {
            this.setImageResource(R.drawable.shopping)
        }

        "Subscription" -> {
            this.setImageResource(R.drawable.subscription)
        }

        "Tax" -> {
            this.setImageResource(R.drawable.tax)
        }

        "Transport" -> {
            this.setImageResource(R.drawable.transport)
        }

        "Travel" -> {
            this.setImageResource(R.drawable.travel)
        }

        "Utilities" -> {
            this.setImageResource(R.drawable.utility)
        }
        else -> {
            //icon for uncategorized cash entries
            this.setImageResource(R.drawable.utility)
        }
    }
}