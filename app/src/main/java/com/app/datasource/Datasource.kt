package com.app.datasource

import com.app.model.Jogo

class Datasource {
    companion object{
        fun getJogos():ArrayList<Jogo>{
            var jogos = ArrayList<Jogo>()
            jogos.add(Jogo(100,"RDR2",3.0f,"PS4"))
            jogos.add(Jogo(110,"Devil may cry",4.0f,"PC"))
            jogos.add(Jogo(120,"Rust",3.0f,"X-BOX"))
            jogos.add(Jogo(130,"Roller Coaster",1.0f,"PC"))
            jogos.add(Jogo(140,"The Last of Us",5.0f,"PS5"))
            jogos.add(Jogo(150,"Naruto",2.0f,"PS2"))

            return jogos
        }
    }
}