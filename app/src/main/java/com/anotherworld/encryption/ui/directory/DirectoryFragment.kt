package com.anotherworld.encryption.ui.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anotherworld.encryption.R
import com.anotherworld.encryption.databinding.FragmentDirectoryBinding

class DirectoryFragment : Fragment() {

    private lateinit var slideshowViewModel: DirectoryViewModel
    private var _binding: FragmentDirectoryBinding? = null

    private lateinit var key: EditText
    private lateinit var folder_spinner: AppCompatSpinner
    private lateinit var create_checkbox: AppCompatCheckBox
    private lateinit var select: AppCompatButton
    private lateinit var encrypt: RadioButton
    private lateinit var decrypt: RadioButton

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        slideshowViewModel = ViewModelProvider(this).get(DirectoryViewModel::class.java)
        _binding = FragmentDirectoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        key = root.findViewById(R.id.key)
        folder_spinner = root.findViewById(R.id.folder_spinner)
        create_checkbox = root.findViewById(R.id.create_checkbox)
        select = root.findViewById(R.id.select)
        encrypt = root.findViewById(R.id.encrypt_folder)
        decrypt = root.findViewById(R.id.decrypt_folder)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}