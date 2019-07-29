package com.netatmo.ylu.interactivepack

class Utils {
    companion object {
        fun getRandomLargePic(index: Int): String {
            return String.format("https://picsum.photos/id/%s/1920/1080", index.toString())
        }
    }
}