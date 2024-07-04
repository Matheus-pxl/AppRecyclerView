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
        val cursor =
            db.query(DatabaseDefinitions.Jogo.TABLE_NAME, null, null, null, null, null, null)
        var jogos = ArrayList<Jogo>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var jogo = Jogo(
                    cursor.getInt(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.TITULO)),
                    cursor.getString(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.PRODUTORA)),
                    cursor.getFloat(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.NOTA)),
                    cursor.getString(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.CONSOLE)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.ZERADO)) == 1
                )
                jogos.add(jogo)

            }
        }
        return jogos
    }

    fun getJogo(id: Int) {

    }
}