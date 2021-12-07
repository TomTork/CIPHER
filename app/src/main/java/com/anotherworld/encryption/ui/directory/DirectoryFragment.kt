package com.anotherworld.encryption.ui.directory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import org.apache.commons.io.FileUtils
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import android.os.Environment




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
            zipAll(filePath, "storage/self/primary/Download/out${(Math.random() * 10000).toInt()}-CIPHER.zip")
            if (!create_checkbox.isChecked){
                val dir = File(filePath)
                if (dir.isDirectory) {
                    val children = dir.list()!!
                    for (i in children.indices) {
                        File(dir, children[i]).delete()
                    }
                }
            }
        }
    }
    private var countDownTimer: CountDownTimer? = null
    private var sec = 5
    private fun timer(filePath: String){
        countDownTimer = object : CountDownTimer((sec * 1000).toLong(), 1000){
            override fun onTick(p0: Long) {
                sec--
            }
            override fun onFinish() {

            }
        }
        countDownTimer!!.start()
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
    fun zipAll(directory: String, zipFile: String) {
        val sourceFile = File(directory)
        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use {
            it.use {
                zipFiles(it, sourceFile, "")
            }
        }
    }
    private fun zipFiles(zipOut: ZipOutputStream, sourceFile: File, parentDirPath: String) {
        val data = ByteArray(2048)
        for (f in sourceFile.listFiles()) {
            if (f.isDirectory) {
                val entry = ZipEntry(f.name + File.separator)
                entry.time = f.lastModified()
                entry.isDirectory
                entry.size = f.length()
                zipOut.putNextEntry(entry)
                zipFiles(zipOut, f, f.name)
            } else {
                if (!f.name.contains(".zip")) {
                    FileInputStream(f).use { fi ->
                        BufferedInputStream(fi).use { origin ->
                            val path = parentDirPath + File.separator + f.name
                            val entry = ZipEntry(path)
                            entry.time = f.lastModified()
                            entry.isDirectory
                            entry.size = f.length()
                            zipOut.putNextEntry(entry)
                            while (true) {
                                val readBytes = origin.read(data)
                                if (readBytes == -1) {
                                    break
                                }
                                zipOut.write(data, 0, readBytes)
                            }
                        }
                    }
                } else {
                    zipOut.closeEntry()
                    zipOut.close()
                }
            }
        }
    }

}