package com.app.model

data class Jogo(
    var id: Int=0,
    var titulo: String="",
    var produtora:String="",
    var notaJogo: Float=0.0f,
    var console: String="",
    var zerado:Boolean=false
)