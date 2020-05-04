package com.example.taskkotlin

import android.util.Log.println


class Triangle : Hierarchy(12,14) {
    override fun area() {
        var ar = (1/2)*height*width
    }
}