package com.janey.photo.utils

fun formatProfileUrl(
    iconFarm: Int, iconServer: String, ownerId: String
): String = "https://farm${iconFarm}.staticflickr.com/${iconServer}/buddyicons/${ownerId}.jpg"