package com.app.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.app.R
import com.app.databinding.ActivityCadastrarJogoBinding
import com.app.model.Jogo
import com.app.repository.JogoRepository

class CadastrarJogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarJogoBinding // Declare o binding como lateinit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Registrar jogo")
        binding =
            ActivityCadastrarJogoBinding.inflate(layoutInflater) // Inflar o layout usando o binding
        setContentView(binding.root) // Definir o layout root do binding como o conteúdo da activity

        btSalvar()
    }

    private fun btSalvar() {
        binding.btSalvarJogo.setOnClickListener {
            if(validarFormulario()){
                salvarJogo()
                finish()
            }
        }
    }

    private fun salvarJogo() {
        val jogo = Jogo(
            0,
            titulo = binding.editTextNomeJogo.text.toString(),
            produtora = binding.editTextProdutoraJogo.text.toString(),
            notaJogo = binding.ratingBarNotaJogo.rating,
            console = binding.spinnerConsole.selectedItem.toString(),
            zerado = binding.checkboxFinalizado.isChecked
        )
        //criar uma instancia do repositorio
        val repo = JogoRepository(this)
        val id = repo.save(jogo)
        println("registro criado ###########: $id")
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
