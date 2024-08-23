package com.janey.photo.utils

fun String.formatTags(): String = "#${this.replace(" ", " #")}"
