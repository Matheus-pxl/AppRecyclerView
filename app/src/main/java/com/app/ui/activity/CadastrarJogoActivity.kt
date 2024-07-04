package com.app.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.app.R
import com.app.databinding.ActivityRegistrarJogoBinding // Importe a classe de binding

class CadastrarJogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarJogoBinding // Declare o binding como lateinit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Registrar jogo")
        binding =
            ActivityRegistrarJogoBinding.inflate(layoutInflater) // Inflar o layout usando o binding
        setContentView(binding.root) // Definir o layout root do binding como o conteúdo da activity

        salvarJogo()
    }

    private fun salvarJogo() {
        binding.btSalvarJogo.setOnClickListener {
            if(validarFormulario()){

            }
        }
    }

    private fun validarFormulario(): Boolean {
        var valida = true
        if (binding.editTextNomeJogo.length() < 3) {
            binding.layoutNomeDoJogo.isErrorEnabled = true
            binding.layoutNomeDoJogo.error = "Nome do jogo é obrigatorio"
            valida = false
        } else {
            binding.layoutNomeDoJogo.isErrorEnabled = false
            binding.layoutNomeDoJogo.error = null
        }
        if (binding.editTextProdutoraJogo.length() < 3) {
            binding.layoutProdutoraDoJogo.isErrorEnabled = true
            binding.layoutProdutoraDoJogo.error = "Produtora do jogo é obrigatorio"
            valida = false
        } else {
            binding.layoutProdutoraDoJogo.isErrorEnabled = false
            binding.layoutProdutoraDoJogo.error = null
        }
        return valida

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

}
