package com.anotherworld.encryption.ui.text

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anotherworld.encryption.*
import com.anotherworld.encryption.databinding.FragmentTextBinding
import com.google.android.material.snackbar.Snackbar

class TextFragment : Fragment() {

    private lateinit var homeViewModel: TextViewModel
    private var _binding: FragmentTextBinding? = null
    private lateinit var input: EditText
    private lateinit var code: EditText
    private lateinit var translate: TextView
    private lateinit var copy_translate: ImageButton
    private lateinit var random: ImageButton
    private lateinit var copy_code: ImageButton
    private lateinit var sw_encrypt: RadioButton
    private lateinit var sw_decrypt: RadioButton
    private lateinit var execute: Button
    private lateinit var copy_input: ImageButton

    var sw = 0 //0 - encrypt; 1 - decrypt
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        code.setText(Data().getKeyText())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(TextViewModel::class.java)
        _binding = FragmentTextBinding.inflate(inflater, container, false)
        val root: View = binding.root
        input = root.findViewById(R.id.input)
        code = root.findViewById(R.id.code)
        translate = root.findViewById(R.id.translate)
        copy_translate = root.findViewById(R.id.copy_translate)
        copy_code = root.findViewById(R.id.copy_code)
        random = root.findViewById(R.id.random_code)
        execute = root.findViewById(R.id.execute)
        sw_encrypt = root.findViewById(R.id.encrypt)
        sw_decrypt = root.findViewById(R.id.decrypt)
        copy_input = root.findViewById(R.id.copy_input)
        sw_encrypt.setOnClickListener { sw = 0; sw_decrypt.isChecked = false }
        sw_decrypt.setOnClickListener { sw = 1; sw_encrypt.isChecked = false }
        copy_translate.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", translate.text.toString())
            clipboard.setPrimaryClip(clip)
            Snackbar.make(it, R.string.copied, Snackbar.LENGTH_SHORT).show()
        }
        copy_code.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", code.text.toString())
            clipboard.setPrimaryClip(clip)
            Snackbar.make(it, R.string.copied, Snackbar.LENGTH_SHORT).show()
        }
        copy_input.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", input.text.toString())
            clipboard.setPrimaryClip(clip)
            Snackbar.make(it, R.string.copied, Snackbar.LENGTH_SHORT).show()
        }
        random.setOnClickListener { code.setText(Math.random().toString().replace(".", (Math.random() * 10).toInt().toString()).substring(0, 16)) }
        execute.setOnClickListener {
            when{
                sw == 0 && input.text.toString().isNotEmpty() -> {
                    when(Data().getTypeText()){
                        0 -> {
                            try{
                                val a = AESFORTEXT(input.text.toString(), code.text.toString())
                                code.setText(a.key)
                                if(a.encrypt()){
                                    translate.setText(Data().getValue())
                                }
                                else Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }catch (e: Exception){
                                Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        1 -> {
                            try {
                                val gms = GMS(input.text.toString(), code.text.toString())
                                code.setText(gms.key)
                                translate.setText(gms.encrypt())
                            }catch (e: Exception){
                                Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        2 -> {
                            try{
                                val cipher1 = CIPHER1()
                                translate.setText(cipher1.encrypt("!${input.text.toString()}"))
                                code.setText(cipher1.secret)
                            }catch (e: Exception){
                                Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        3 -> {
                            try {
                                val cipher2 = CIPHER2()
                                translate.setText(cipher2.encrypt(input.text.toString(), code.text.toString()))
                            }catch (e: Exception){
                                Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                sw == 1 && input.text.toString().isNotEmpty() -> {
                    when(Data().getTypeText()){
                        0 -> {
                            try{
                                val a = AESFORTEXT(input.text.toString(), code.text.toString())
                                code.setText(a.key)
                                translate.setText(a.decrypt())
                            }catch (e: Exception){
                                Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        1 -> {
                            try{
                                val gms = GMS(input.text.toString(), code.text.toString())
                                code.setText(gms.key)
                                translate.setText(gms.decrypt())
                            }catch (e: Exception){
                                Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        2 -> {
                            try{
                                val cipher1 = CIPHER1()
                                translate.setText(cipher1.decrypt(input.text.toString(), code.text.toString()).replaceFirst("!", ""))
                            }catch (e: Exception){
                                Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        3 -> {
                            try{
                                val cipher2 = CIPHER2()
                                translate.setText(cipher2.decrypt(input.text.toString(), code.text.toString()))
                            }catch (e: Exception){
                                Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                else -> { Snackbar.make(it, R.string.check, Snackbar.LENGTH_SHORT).show() }
            }
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}