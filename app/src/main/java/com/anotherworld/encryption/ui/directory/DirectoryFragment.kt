package com.anotherworld.encryption.ui.directory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anotherworld.encryption.R
import com.anotherworld.encryption.databinding.FragmentDirectoryBinding
import com.google.android.material.snackbar.Snackbar
import java.io.*
import java.lang.Exception
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipOutputStream

class DirectoryFragment : Fragment() {
    private var radio = 0

    private lateinit var slideshowViewModel: DirectoryViewModel
    private var _binding: FragmentDirectoryBinding? = null

    private lateinit var key: EditText
    private lateinit var folder_spinner: AppCompatSpinner
    private lateinit var create_checkbox: AppCompatCheckBox
    private lateinit var select: AppCompatButton
    private lateinit var encrypt: RadioButton
    private lateinit var decrypt: RadioButton

    private val PICK_REQUEST = 71
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
        encrypt.setOnClickListener { radio = 0; decrypt.isChecked = false }
        decrypt.setOnClickListener { radio = 1; encrypt.isChecked = false }

        select.setOnClickListener { choose() }
        return root
    }

    private var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val filePath = "storage/self/primary/" + data?.data!!.path!!.substringAfterLast(":")
            createZip(filePath, data?.data!!.path!!.substringAfterLast(":"))
        }
    }

    private fun choose() {
        if(radio == 0 && encrypt.isChecked){
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            launchSomeActivity.launch(intent)
        }
        else if(radio == 1 && decrypt.isChecked){
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launchSomeActivity.launch(intent)
        }
        else{
            Toast.makeText(context, R.string.check, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun createZip(filepath: String, filename: String){
        try {
            ZipOutputStream(FileOutputStream("storage/self/primary/CIPHER/output.zip")).use { zout ->
                FileInputStream(filename).use { fis ->
                    val entry1 = ZipEntry("notes.txt")
                    zout.putNextEntry(entry1)
                    // считываем содержимое файла в массив byte
                    val buffer = ByteArray(fis.available())
                    fis.read(buffer)
                    // добавляем содержимое к архиву
                    zout.write(buffer)
                    // закрываем текущую запись для новой записи
                    zout.closeEntry()
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}