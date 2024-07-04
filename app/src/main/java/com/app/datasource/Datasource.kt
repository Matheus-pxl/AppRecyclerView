package com.app.datasource

import com.app.model.Jogo

class Datasource {
    companion object{
        fun getJogos():ArrayList<Jogo>{
            var jogos = ArrayList<Jogo>()
            jogos.add(Jogo(100,"RDR2","",3.0f,"PS4"))

            return jogos
        }
    }
}