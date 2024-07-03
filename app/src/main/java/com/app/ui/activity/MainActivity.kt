package com.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.adapter.JogosAdapter
import com.app.databinding.ActivityMainBinding
import com.app.datasource.Datasource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Declaração do binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciarRecyclerView()
    }

    private fun iniciarRecyclerView() {
//para o layout ficar horizontal
//        binding.recyclerViewJogos.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewJogos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewJogos.adapter = JogosAdapter(Datasource.getJogos())
    }
}
