package com.anotherworld.encryption.ui.directory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anotherworld.encryption.R
import com.anotherworld.encryption.databinding.FragmentDirectoryBinding
import java.io.*
import androidx.appcompat.widget.AppCompatEditText
import net.lingala.zip4j.model.enums.AesKeyStrength

import net.lingala.zip4j.model.enums.EncryptionMethod

import net.lingala.zip4j.model.ZipParameters
import kotlin.Exception


class DirectoryFragment : Fragment() {
    private var radio = 0

    private lateinit var slideshowViewModel: DirectoryViewModel
    private var _binding: FragmentDirectoryBinding? = null
    private lateinit var select: AppCompatButton
    private lateinit var encrypt: RadioButton
    private lateinit var decrypt: RadioButton
    private lateinit var key: AppCompatEditText

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        slideshowViewModel = ViewModelProvider(this).get(DirectoryViewModel::class.java)
        _binding = FragmentDirectoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        select = root.findViewById(R.id.select)
        encrypt = root.findViewById(R.id.encrypt_folder)
        decrypt = root.findViewById(R.id.decrypt_folder)
        key = root.findViewById(R.id.key)
        encrypt.setOnClickListener { radio = 0; decrypt.isChecked = false }
        decrypt.setOnClickListener { radio = 1; encrypt.isChecked = false }

        select.setOnClickListener { choose() }
        return root
    }
    private var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val filePath = "storage/self/primary/" + data?.data!!.path!!.substringAfterLast(":")
            if(encrypt.isChecked){
                val zipParameters = ZipParameters()
                zipParameters.isEncryptFiles = true
                zipParameters.encryptionMethod = EncryptionMethod.AES
                zipParameters.aesKeyStrength = AesKeyStrength.KEY_STRENGTH_256
                val filesToAdd = File(filePath).listFiles().toList()
                val zipFile: net.lingala.zip4j.ZipFile = net.lingala.zip4j.ZipFile("storage/self/primary/Download/${(Math.random() * 10000).toInt()}-OUT-CIPHER.zip", key.text.toString().toCharArray())
                zipFile.addFiles(filesToAdd, zipParameters)
            }
            else if(decrypt.isChecked){
                val filePassword = key.text.toString()
                val destinationAddress = "storage/self/primary/Download/"
                val zipFile = net.lingala.zip4j.ZipFile(filePath, filePassword.toCharArray())
                try {
                    zipFile.extractAll(destinationAddress)
                } catch (e: Exception) {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun choose() {
        if(radio == 0 && encrypt.isChecked && key.text.toString().isNotEmpty()){
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            launchSomeActivity.launch(intent)
        }
        else if(radio == 1 && decrypt.isChecked && key.text.toString().isNotEmpty()){
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

}