package com.example.ProyectoFinalMovilesTrabajador.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ProyectoFinalMovilesTrabajador.data.model.Categoria
import com.example.ProyectoFinalMovilesTrabajador.viewModel.RegisterViewModel
import com.example.proyectofinalmovilestrabajador.R
import com.example.proyectofinalmovilestrabajador.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private var imagenUri: Uri? = null
    private val categoriasSeleccionadas = mutableSetOf<Int>()
    private val PICK_IMAGE_REQUEST = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        setupEventListeners()
        viewModel.obtenerCategorias(requireContext()) { categorias ->
            mostrarCategorias(categorias)
        }
        return binding.root
    }

    private fun setupEventListeners() {
        binding.buttonSeleccionarFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        binding.buttonRegister.setOnClickListener {
            val nombre = binding.editTextNombre.text.toString().trim()
            val apellido = binding.editTextApellido.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty() || imagenUri == null || categoriasSeleccionadas.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos y selecciona foto y ocupaciones", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.registrarTrabajador(
                context = requireContext(),
                nombre = nombre,
                apellido = apellido,
                email = email,
                password = password,
                imagenUri = imagenUri!!,
                ocupaciones = categoriasSeleccionadas.toList(),
                onSuccess = {
                    Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                },
                onError = {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun mostrarCategorias(categorias: List<Categoria>) {
        val container = binding.contenedorCategorias
        categorias.forEach { categoria ->
            val checkBox = CheckBox(requireContext()).apply {
                text = categoria.name
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) categoriasSeleccionadas.add(categoria.id)
                    else categoriasSeleccionadas.remove(categoria.id)
                }
            }
            container.addView(checkBox)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imagenUri = data.data
            binding.imageProfile.setImageURI(imagenUri)
        }
    }

}