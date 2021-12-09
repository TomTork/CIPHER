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
import androidx.appcompat.widget.AppCompatEditText
import com.anotherworld.encryption.CipherForZip
import java.lang.Exception
import java.util.zip.ZipInputStream


class DirectoryFragment : Fragment() {
    private var radio = 0

    private lateinit var slideshowViewModel: DirectoryViewModel
    private var _binding: FragmentDirectoryBinding? = null
    private lateinit var key: AppCompatEditText
    private lateinit var select: AppCompatButton
    private lateinit var encrypt: RadioButton
    private lateinit var decrypt: RadioButton

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
                zipAll(filePath, "storage/self/primary/Download/out${(Math.random() * 10000).toInt()}-CIPHER.zip")
            }
            else if(decrypt.isChecked){
                Log.d("QQQQQ-TEG", filePath)
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
                            //val encrypt_data: ByteArray = CipherForZip().encrypt(data, key.text.toString())
                            while (true) {
                                //val readBytes = origin.read(data)
                                val readBytes = origin.read(CipherForZip().encrypt(data, key.text.toString()))
                                if (readBytes == -1) {
                                    break
                                }
                                //zipOut.write(data, 0, readBytes)
                                zipOut.write(CipherForZip().encrypt(data, key.text.toString()), 0, readBytes)
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
private class Decompress(private val zipFile: String, private val location: String) {
    fun unzip() {
        try {
            val fin = FileInputStream(zipFile)
            val zin = ZipInputStream(fin)
            var ze: ZipEntry? = null
            while (zin.nextEntry.also { ze = it } != null) {
                Log.v("Decompress", "Unzipping " + ze!!.name)
                if (ze!!.isDirectory) {
                    dirChecker(ze!!.name)
                } else {
                    val fout = FileOutputStream(location + ze!!.name)
                    var c: Int = zin.read()
                    while (c != -1) {
                        fout.write(c)
                        c = zin.read()
                    }
                    zin.closeEntry()
                    fout.close()
                }
            }
            zin.close()
        } catch (e: Exception) {
            Log.e("Decompress", "unzip", e)
        }
    }
    private fun dirChecker(dir: String) {
        val f = File(location + dir)
        if (!f.isDirectory) {
            f.mkdirs()
        }
    }
    init {
        dirChecker("")
    }
}