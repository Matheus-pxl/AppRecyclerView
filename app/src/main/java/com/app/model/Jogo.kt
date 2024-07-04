package com.app.model

data class Jogo(
    var codigo: Int,
    var titulo: String,
    var produtora:String,
    var notaJogo: Float,
    var console: String,
    var zerado:Boolean=false
)