package com.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.R
import com.app.adapter.JogosAdapter
import com.app.databinding.ActivityMainBinding
import com.app.datasource.Datasource

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding // Declaração do binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Jogos finalizados")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciarRecyclerView()
        binding.fabCadastrarNovoJogo.setOnClickListener(this)
    }


    private fun iniciarRecyclerView() {
//para o layout ficar horizontal
//        binding.recyclerViewJogos.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewJogos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewJogos.adapter = JogosAdapter(Datasource.getJogos())
    }

    override fun onClick(v: View) {
        if(v.id == R.id.fab_cadastrar_novo_jogo){
            val intent = Intent(this, CadastrarJogoActivity::class.java)
            startActivity(intent)
        }
    }
}
