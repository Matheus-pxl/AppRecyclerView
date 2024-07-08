package com.app.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract.Data
import com.app.datasource.DatabaseDefinitions
import com.app.datasource.DatabaseHelper
import com.app.model.Jogo
import java.io.ByteArrayOutputStream

class JogoRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun save(jogo: Jogo): Int {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put(DatabaseDefinitions.Jogo.Columns.TITULO, jogo.titulo)
            put(DatabaseDefinitions.Jogo.Columns.PRODUTORA, jogo.produtora)
            put(DatabaseDefinitions.Jogo.Columns.NOTA, jogo.notaJogo)
            put(DatabaseDefinitions.Jogo.Columns.CONSOLE, jogo.console)
            put(DatabaseDefinitions.Jogo.Columns.ZERADO, jogo.zerado)

            //transformar o bitmap em um array de bytes
            val stream = ByteArrayOutputStream()
            jogo.imagem!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val imagemArray = stream.toByteArray()
            put(DatabaseDefinitions.Jogo.Columns.IMAGEM, imagemArray)
        }

        val id = db.insert(DatabaseDefinitions.Jogo.TABLE_NAME, null, valores)
        return id.toInt()
    }

    fun update(jogo: Jogo): Int {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put(DatabaseDefinitions.Jogo.Columns.TITULO, jogo.titulo)
            put(DatabaseDefinitions.Jogo.Columns.PRODUTORA, jogo.produtora)
            put(DatabaseDefinitions.Jogo.Columns.NOTA, jogo.notaJogo)
            put(DatabaseDefinitions.Jogo.Columns.CONSOLE, jogo.console)
            put(DatabaseDefinitions.Jogo.Columns.ZERADO, jogo.zerado)
        }
        val selection = "${DatabaseDefinitions.Jogo.Columns.ID} = ?"
        val selectionArgs = arrayOf(jogo.id.toString())
        val count =
            db.update(DatabaseDefinitions.Jogo.TABLE_NAME, valores, selection, selectionArgs)
        return count
    }

    fun delete(id: Int): Int {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseDefinitions.Jogo.Columns.ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val deleteRows = db.delete(DatabaseDefinitions.Jogo.TABLE_NAME, selection, selectionArgs)
        return deleteRows
    }

    @SuppressLint("Range")
    fun getJogos(): ArrayList<Jogo> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            DatabaseDefinitions.Jogo.Columns.ID,
            DatabaseDefinitions.Jogo.Columns.TITULO,
            DatabaseDefinitions.Jogo.Columns.CONSOLE,
            DatabaseDefinitions.Jogo.Columns.NOTA,
        )
        val sortOrder = "${DatabaseDefinitions.Jogo.Columns.TITULO} ASC"
        val cursor = db.query(
            DatabaseDefinitions.Jogo.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )
        val jogos = ArrayList<Jogo>()
        cursor?.use {
            while (it.moveToNext()) {
                val jogo = Jogo(
                    id = it.getInt(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.ID)),
                    titulo = it.getString(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.TITULO)),
                    console = it.getString(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.CONSOLE)),
                    notaJogo = it.getFloat(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.NOTA)),
                    produtora = "", // Adicione a produtora aqui conforme necessário
                    zerado = false // Adicione o valor correto conforme necessário
                )
                jogos.add(jogo)
            }
        }
        return jogos
    }

    @SuppressLint("Range")
    fun getJogo(id: Int): Jogo {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            DatabaseDefinitions.Jogo.Columns.ID,
            DatabaseDefinitions.Jogo.Columns.TITULO,
            DatabaseDefinitions.Jogo.Columns.PRODUTORA,
            DatabaseDefinitions.Jogo.Columns.NOTA,
            DatabaseDefinitions.Jogo.Columns.CONSOLE,
            DatabaseDefinitions.Jogo.Columns.ZERADO,
            DatabaseDefinitions.Jogo.Columns.IMAGEM
        )
        val selection = "${DatabaseDefinitions.Jogo.Columns.ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db.query(
            DatabaseDefinitions.Jogo.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var jogo = Jogo()
        cursor?.use {
            if (it.moveToNext()) {
                jogo.id = it.getInt(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.ID))
                jogo.titulo =
                    it.getString(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.TITULO))
                jogo.produtora =
                    it.getString(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.PRODUTORA))
                jogo.notaJogo =
                    it.getFloat(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.NOTA))
                jogo.console =
                    it.getString(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.CONSOLE))
                jogo.zerado =
                    it.getInt(it.getColumnIndex(DatabaseDefinitions.Jogo.Columns.ZERADO)) == 1
                val imagem =
                    cursor.getBlob(cursor.getColumnIndex(DatabaseDefinitions.Jogo.Columns.IMAGEM))

                if (imagem != null) {
                    jogo.imagem = byteArrayToBitmap(imagem)

                }

            }
        }
        return jogo
    }

    private fun byteArrayToBitmap(imagem: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.size)
        return bitmap
    }
}
