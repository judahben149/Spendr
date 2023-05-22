package com.judahben149.spendr.utils.extensions

import com.judahben149.spendr.R
import soup.neumorphism.NeumorphButton
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