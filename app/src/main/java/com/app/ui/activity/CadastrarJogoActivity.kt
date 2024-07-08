package com.app.ui.activity

import android.content.Intent
import android.graphics.Bitmap
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
    private lateinit var adapter: ArrayAdapter<CharSequence>
    private lateinit var operacao: String
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar o layout usando o binding
        binding = ActivityCadastrarJogoBinding.inflate(layoutInflater)

        // Definir o layout root do binding como o conteúdo da activity
        setContentView(binding.root)

        // Preencher o spinner com os consoles disponíveis
        preencherSpinnerConsole()

        // Configurar o botão salvar e operação (novo cadastro ou atualização)
        btSalvar()

        // Obter a operação passada pela intent
        operacao = intent.getStringExtra("operacao")!!

        // Se não for um novo cadastro, preencher o formulário com os dados do jogo
        if (operacao != Constants.OPERACAO_NOVO_CADASTRO) {
            preencherFormulario()
        }

        // Configurar o clique para inserir imagem
        binding.inserirImagem.setOnClickListener(this)
    }

    // Método para preencher o spinner com os consoles disponíveis
    private fun preencherSpinnerConsole() {
        adapter = ArrayAdapter.createFromResource(
            this,
            R.array.consoles,
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinnerConsole.adapter = adapter
    }

    // Método para preencher o formulário com os dados do jogo existente
    private fun preencherFormulario() {
        var jogo = Jogo()
        var id = intent.getIntExtra("id", 0)
        val repository = JogoRepository(this)
        jogo = repository.getJogo(id)
        binding.editTextNomeJogo.setText(jogo.titulo)
        binding.editTextProdutoraJogo.setText(jogo.produtora)

        // Selecionar o console na lista
        val position = adapter.getPosition(jogo.console)
        binding.spinnerConsole.setSelection(position)

        binding.checkboxFinalizado.isChecked = jogo.zerado
        binding.ratingBarNotaJogo.rating = jogo.notaJogo

        if (jogo.imagem != null) {
            binding.imageViewFotoJogo.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.imageViewFotoJogo.setImageBitmap(jogo.imagem)
        }else{
            binding.imageViewFotoJogo.setImageResource(R.drawable.ic_insert_photo)
        }
    }

    // Método para configurar o botão salvar
    private fun btSalvar() {
        binding.btSalvarJogo.setOnClickListener {
            // Validar o formulário e salvar ou atualizar o jogo
            if (validarFormulario() && operacao == Constants.OPERACAO_NOVO_CADASTRO) {
                salvarJogo()
                finish()
            } else {
                atualizarJogo()
                finish()
            }
        }
    }

    // Método para atualizar os dados do jogo existente
    private fun atualizarJogo() {
        // Criar um objeto jogo com os dados atualizados do formulário
        val jogo = Jogo(
            id = intent.getIntExtra("id", 0),
            titulo = binding.editTextNomeJogo.text.toString(),
            produtora = binding.editTextProdutoraJogo.text.toString(),
            notaJogo = binding.ratingBarNotaJogo.rating,
            console = binding.spinnerConsole.selectedItem.toString(),
            zerado = binding.checkboxFinalizado.isChecked
        )
        // Criar uma instância do repositório de jogos e atualizar o jogo
        val repo = JogoRepository(this)
        val count = repo.update(jogo)
    }

    // Método para salvar um novo jogo
    private fun salvarJogo() {
        // Criar um objeto jogo com os dados do formulário
        val jogo = Jogo(
            0,
            titulo = binding.editTextNomeJogo.text.toString(),
            produtora = binding.editTextProdutoraJogo.text.toString(),
            notaJogo = binding.ratingBarNotaJogo.rating,
            console = binding.spinnerConsole.selectedItem.toString(),
            zerado = binding.checkboxFinalizado.isChecked,
            imagem = bitmap
        )
        // Criar uma instância do repositório de jogos e salvar o jogo
        val repo = JogoRepository(this)
        val id = repo.save(jogo)
    }

    // Método para validar o formulário
    private fun validarFormulario(): Boolean {
        var valida = true
        if (binding.editTextNomeJogo.length() < 3) {
            binding.layoutNomeDoJogo.isErrorEnabled = true
            binding.layoutNomeDoJogo.error = "Nome do jogo é obrigatório"
            valida = false
        } else {
            binding.layoutNomeDoJogo.isErrorEnabled = false
            binding.layoutNomeDoJogo.error = null
        }
        if (binding.editTextProdutoraJogo.length() < 3) {
            binding.layoutProdutoraDoJogo.isErrorEnabled = true
            binding.layoutProdutoraDoJogo.error = "Produtora do jogo é obrigatória"
            valida = false
        } else {
            binding.layoutProdutoraDoJogo.isErrorEnabled = false
            binding.layoutProdutoraDoJogo.error = null
        }
        return valida
    }

    // Método para inflar o menu da activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    // Método para lidar com cliques nos elementos da UI
    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.inserir_imagem -> {
                // Abrir a galeria para selecionar uma imagem
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 5000)
            }
        }
    }

    // Método para lidar com o resultado da seleção de imagem da galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && resultCode == RESULT_OK) {
            // Obter o stream de dados da imagem selecionada
            val inputStream = contentResolver.openInputStream(data.data!!)
            // Decodificar o stream em um Bitmap
            val bitmap = BitmapFactory.decodeStream(inputStream)
            // Configurar a imagem no ImageView da UI
            binding.imageViewFotoJogo.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.imageViewFotoJogo.setImageBitmap(bitmap)
        }
    }
}
