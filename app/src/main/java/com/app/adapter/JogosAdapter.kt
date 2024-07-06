package com.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.constants.Constants
import com.app.databinding.ItemListaJogosBinding
import com.app.model.Jogo
import com.app.repository.JogoRepository
import com.app.ui.activity.CadastrarJogoActivity

// Classe principal do adaptador que extende RecyclerView.Adapter
class JogosAdapter(private val listaJogos: ArrayList<Jogo>) : RecyclerView.Adapter<JogosAdapter.JogoViewHolder>() {

    // Método chamado quando o RecyclerView precisa criar um novo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogoViewHolder {
        // Infla o layout do item usando ViewBinding
        val binding = ItemListaJogosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JogoViewHolder(binding) // Retorna um novo JogoViewHolder com o binding inflado
    }

    // Método chamado pelo RecyclerView para exibir dados em uma posição específica
    override fun onBindViewHolder(holder: JogoViewHolder, position: Int) {
        val jogo = listaJogos[position] // Obtém o objeto Jogo da lista baseado na posição
        holder.bind(jogo, position) // Chama o método bind do JogoViewHolder para vincular os dados ao ViewHolder
    }

    // Método que retorna o número total de itens na lista de jogos
    override fun getItemCount(): Int {
        return listaJogos.size
    }

    // Classe interna que representa o ViewHolder para cada item na lista
    inner class JogoViewHolder(private val binding: ItemListaJogosBinding) : RecyclerView.ViewHolder(binding.root) {
        // Método que vincula os dados do objeto Jogo aos elementos do layout usando ViewBinding
        fun bind(jogo: Jogo,position: Int) {
            binding.textConsole.text = jogo.console // Define o texto do console do jogo
            binding.nomeJogoLista.text = jogo.titulo // Define o nome do jogo
            binding.notaJogo.rating = jogo.notaJogo // Define a nota do jogo (rating)

            binding.btDetalhes.setOnClickListener{
                val intent = Intent(binding.root.context, CadastrarJogoActivity::class.java)
                intent.putExtra("operacao",Constants.OPERACAO_CONSULTAR)
                intent.putExtra("id",jogo.id)
                binding.root.context.startActivity(intent)
            }
            binding.root.setOnLongClickListener{
                val repo = JogoRepository(binding.root.context)
                repo.delete(jogo.id)
                listaJogos.removeAt(position)
                notifyDataSetChanged()
                true
            }

        }
    }
}
