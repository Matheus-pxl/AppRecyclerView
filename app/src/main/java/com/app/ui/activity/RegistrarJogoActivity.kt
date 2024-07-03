package com.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.databinding.ActivityRegistrarJogoBinding // Importe a classe de binding

class RegistrarJogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarJogoBinding // Declare o binding como lateinit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarJogoBinding.inflate(layoutInflater) // Inflar o layout usando o binding
        setContentView(binding.root) // Definir o layout root do binding como o conteúdo da activity


    }
}
