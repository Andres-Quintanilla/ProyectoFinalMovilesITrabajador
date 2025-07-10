package com.example.ProyectoFinalMovilesTrabajador.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ProyectoFinalMovilesTrabajador.data.model.Categoria
import com.example.proyectofinalmovilestrabajador.databinding.ItemCategoriaBinding

class CategoriaAdapter(
    private var categorias: List<Categoria>,
    private val onItemClick: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    inner class CategoriaViewHolder(val binding: ItemCategoriaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val binding = ItemCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.binding.textCategoria.text = categoria.name
        holder.binding.root.setOnClickListener {
            onItemClick(categoria)
        }
    }

    override fun getItemCount(): Int = categorias.size

    fun actualizarLista(nuevaLista: List<Categoria>) {
        categorias = nuevaLista
        notifyDataSetChanged()
    }

}
