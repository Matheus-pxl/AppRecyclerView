package com.app.model

import android.graphics.Bitmap

data class Jogo(
    var id: Int = 0,
    var titulo: String = "",
    var produtora: String = "",
    var notaJogo: Float = 0.0f,
    var console: String = "",
    var zerado: Boolean = false,
    var imagem: Bitmap? = null
)