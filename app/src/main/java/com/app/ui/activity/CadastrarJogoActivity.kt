package com.app.ui.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.app.R
import com.app.constants.Constants
import com.app.databinding.ActivityCadastrarJogoBinding
import com.app.model.Jogo
import com.app.repository.JogoRepository

class CadastrarJogoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCadastrarJogoBinding // Declare o binding como lateinit
    private lateinit var adapter:ArrayAdapter<CharSequence>
    private lateinit var operacao:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Registrar jogo")
        binding =
            ActivityCadastrarJogoBinding.inflate(layoutInflater) // Inflar o layout usando o binding
        setContentView(binding.root) // Definir o layout root do binding como o conteúdo da activity
        preencherSpinnerConsole()
        btSalvar()

        //preencher o valor da variavel operacao
        operacao = intent.getStringExtra("operacao")!!
        if(operacao != Constants.OPERACAO_NOVO_CADASTRO){
            preencherFormulario()
        }
        binding.inserirImagem.setOnClickListener(this)
    }

    private fun preencherSpinnerConsole() {
        adapter = ArrayAdapter.createFromResource(this,R.array.consoles,android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerConsole.adapter = adapter
    }

    private fun preencherFormulario() {
        var jogo = Jogo()
        var id = intent.getIntExtra("id",0)
        val repository = JogoRepository(this)
        jogo = repository.getJogo(id)
        binding.editTextNomeJogo.setText(jogo.titulo)
        binding.editTextProdutoraJogo.setText(jogo.produtora)

        //selecionar o console na lista
        val position = adapter.getPosition(jogo.console)
        binding.spinnerConsole.setSelection(position)

        binding.checkboxFinalizado.isChecked = jogo.zerado
        binding.ratingBarNotaJogo.rating = jogo.notaJogo
    }

    private fun btSalvar() {
        binding.btSalvarJogo.setOnClickListener {
            if(validarFormulario() && operacao == Constants.OPERACAO_NOVO_CADASTRO){
                salvarJogo()
                finish()
            }else{
                atualizarJogo()
                finish()
            }
        }
    }

    private fun atualizarJogo() {

        //criar um objeto jogo
            val jogo = Jogo(
                id=intent.getIntExtra("id",0),
                titulo = binding.editTextNomeJogo.text.toString(),
                produtora = binding.editTextProdutoraJogo.text.toString(),
                notaJogo = binding.ratingBarNotaJogo.rating,
                console = binding.spinnerConsole.selectedItem.toString(),
                zerado = binding.checkboxFinalizado.isChecked
            )
            //criar uma instancia do repositorio
            val repo = JogoRepository(this)
            val count = repo.update(jogo)
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

    override fun onClick(view: View) {
        val id = view.id
        when(id){
            R.id.inserir_imagem ->{
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent,5000)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
            val inputStream = contentResolver.openInputStream(data.data!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.imageViewFotoJogo.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.imageViewFotoJogo.setImageBitmap(bitmap)
        }
    }


}
