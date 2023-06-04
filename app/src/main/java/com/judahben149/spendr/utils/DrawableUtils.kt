package com.judahben149.spendr.utils

import com.judahben149.spendr.R

object DrawableUtils {

    fun returnIconDrawable(iconId: Int): Int {
        return when (iconId) {
            1 -> {
                R.drawable.automobile
            }

            2 -> {
                R.drawable.bank
            }

            3 -> {
                R.drawable.charity
            }

            4 -> {
                R.drawable.child_care
            }

            5 -> {
                R.drawable.clothing
            }

            6 -> {
                R.drawable.education
            }

            7 -> {
                R.drawable.entertainment
            }

            8 -> {
                R.drawable.fitness
            }

            9 -> {
                R.drawable.food
            }

            10 -> {
                R.drawable.gift
            }

            11 -> {
                R.drawable.grocery
            }

            12 -> {
                R.drawable.loan
            }

            13 -> {
                R.drawable.medical
            }

            14 -> {
                R.drawable.miscellaneous
            }

            15 -> {
                R.drawable.pet
            }

            16 -> {
                R.drawable.rent
            }

            17 -> {
                R.drawable.savings
            }

            18 -> {
                R.drawable.shopping
            }

            19 -> {
                R.drawable.subscription
            }

            20 -> {
                R.drawable.tax
            }

            21 -> {
                R.drawable.transport
            }

            22 -> {
                R.drawable.travel
            }

            23 -> {
                R.drawable.utility
            }

            24 -> {
                R.drawable.salary
            }
            else -> {
                //icon for uncategorized cash entries
                R.drawable.question_mark
            }
        }
    }
}