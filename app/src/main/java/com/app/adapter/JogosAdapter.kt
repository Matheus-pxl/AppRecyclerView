package com.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.databinding.ItemListaJogosBinding
import com.app.model.Jogo

class JogosAdapter(private val listaJogos: ArrayList<Jogo>) : RecyclerView.Adapter<JogosAdapter.JogoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogoViewHolder {
        val binding = ItemListaJogosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JogoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JogoViewHolder, position: Int) {
        val jogo = listaJogos[position]
        holder.bind(jogo)
    }

    override fun getItemCount(): Int {
        return listaJogos.size
    }

    class JogoViewHolder(private val binding: ItemListaJogosBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(jogo: Jogo) {
            binding.textConsole.text = jogo.console
            binding.nomeJogoLista.text=jogo.nomeJogo
            binding.notaJogo.rating=jogo.notaJogo
        }
    }
}
