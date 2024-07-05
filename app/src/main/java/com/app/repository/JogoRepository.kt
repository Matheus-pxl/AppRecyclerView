package com.app.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.app.datasource.DatabaseDefinitions
import com.app.datasource.DatabaseHelper
import com.app.model.Jogo

class JogoRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun save(jogo: Jogo): Int {
        //colocar o banco em modo ESCRITA
        val db = dbHelper.writableDatabase
        //criar um mapa com os valores que serao inseridos
        val valores = ContentValues()
        valores.put(DatabaseDefinitions.Jogo.Columns.TITULO, jogo.titulo)
        valores.put(DatabaseDefinitions.Jogo.Columns.PRODUTORA, jogo.produtora)
        valores.put(DatabaseDefinitions.Jogo.Columns.NOTA, jogo.notaJogo)
        valores.put(DatabaseDefinitions.Jogo.Columns.CONSOLE, jogo.console)
        valores.put(DatabaseDefinitions.Jogo.Columns.ZERADO, jogo.zerado)

        //inserir os dados no banco
        val id = db.insert(DatabaseDefinitions.Jogo.TABLE_NAME, null, valores)
        return id.toInt()
    }

    fun update(jogo: Jogo) {

    }

    fun delete(id: Int) {

    }

    @SuppressLint("Range")
    fun getJogos(): ArrayList<Jogo> {
        val db = dbHelper.readableDatabase
      //definir os campos que serao devolvidos na consulta
        val projection = arrayOf(
            DatabaseDefinitions.Jogo.Columns.ID,
            DatabaseDefinitions.Jogo.Columns.TITULO,
            DatabaseDefinitions.Jogo.Columns.CONSOLE,
            DatabaseDefinitions.Jogo.Columns.NOTA,

            )
        //definir a ordem de exibição da lista, ordenar pelo nome do jogo
        val sortOrder = "${DatabaseDefinitions.Jogo.Columns.TITULO} ASC"

        val cursor =
            db.query(DatabaseDefinitions.Jogo.TABLE_NAME, projection, null, null, null, null, sortOrder)
        var jogos = ArrayList<Jogo>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var jogo = Jogo(
                    id= cursor.getInt(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.ID)),
                    titulo = cursor.getString(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.TITULO)),
                    console = cursor.getString(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.CONSOLE)),
                    notaJogo = cursor.getFloat(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.NOTA))
                )
                jogos.add(jogo)

            }
        }
        return jogos
    }

    fun getJogo(id: Int) {

    }
}