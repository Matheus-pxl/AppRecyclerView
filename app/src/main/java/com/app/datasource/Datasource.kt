package com.app.datasource

import com.app.model.Jogo

class Datasource {
    companion object{
        fun getJogos():ArrayList<Jogo>{
            var jogos = ArrayList<Jogo>()
            jogos.add(Jogo(100,"RDR2",3,"PS4"))
            jogos.add(Jogo(110,"Devil may cry",4,"PC"))
            jogos.add(Jogo(120,"Rust",3,"X-BOX"))
            jogos.add(Jogo(130,"Roller Coaster",1,"PC"))
            jogos.add(Jogo(140,"The Last of Us",5,"PS5"))
            jogos.add(Jogo(150,"Naruto",2,"PS2"))

            return jogos
        }
    }
}