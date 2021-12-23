package com.anotherworld.encryption.ui.scale

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anotherworld.encryption.Data
import com.anotherworld.encryption.R
import com.anotherworld.encryption.databinding.FragmentScaleBinding
import com.google.android.material.snackbar.Snackbar
import java.math.BigInteger

class ScaleFragment : Fragment() {

    private lateinit var scaleViewModel: ScaleViewModel
    private var _binding: FragmentScaleBinding? = null
    private lateinit var inputNumbers: EditText
    private lateinit var system: EditText
    private lateinit var outputNumbers: TextView
    private lateinit var system2: EditText
    private lateinit var copy_numbers: ImageButton
    private lateinit var translateNumbers: Button
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        system.setText(Data().getFirst())
        system2.setText(Data().getSecond())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scaleViewModel = ViewModelProvider(this).get(ScaleViewModel::class.java)
        _binding = FragmentScaleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        inputNumbers = root.findViewById(R.id.inputNumbers)
        system = root.findViewById(R.id.system)
        outputNumbers = root.findViewById(R.id.outputNumbers)
        system2 = root.findViewById(R.id.system2)
        copy_numbers = root.findViewById(R.id.copy_numbers)
        translateNumbers = root.findViewById(R.id.translateNumbers)
        copy_numbers.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", outputNumbers.text.toString())
            clipboard.setPrimaryClip(clip)
            Snackbar.make(it, R.string.copied, Snackbar.LENGTH_SHORT).show()
        }
        translateNumbers.setOnClickListener {
            when{
                inputNumbers.text.toString().isNotEmpty() && system.text.toString().isNotEmpty() && system2.text.toString().isNotEmpty() -> {
                    try{
                        val b = BigInteger(BigInteger(inputNumbers.text.toString(), system.text.toString().toInt()).toString(system2.text.toString().toInt()))
                        outputNumbers.text = b.toString()
                    }catch (e: Exception){
                        outputNumbers.text = "ERROR"
                        Snackbar.make(it, R.string.ops, Snackbar.LENGTH_SHORT).show()
                    }
                }
                else -> Snackbar.make(it, R.string.check, Snackbar.LENGTH_SHORT).show()
            }
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}