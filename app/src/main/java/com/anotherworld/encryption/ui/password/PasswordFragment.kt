package com.anotherworld.encryption.ui.password

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anotherworld.encryption.Data
import com.anotherworld.encryption.R
import com.anotherworld.encryption.databinding.FragmentPasswordBinding

import com.google.android.material.snackbar.Snackbar


class PasswordFragment : Fragment() {

    private lateinit var slideshowViewModel: PasswordViewModel
    private var _binding: FragmentPasswordBinding? = null
    private lateinit var sp: RecyclerView
    private val data: Data = Data()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        slideshowViewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sp = root.findViewById(R.id.sp)
        sp.layoutManager = LinearLayoutManager(requireContext())
        sp.adapter = CustomRecyclerAdapter(fillList(data.getNumber(), data.getLength()))
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillList(number: Int = 10, len: Int = 16): List<String> {
        val data = mutableListOf<String>()
        (0 until number).forEach { i ->
            run {
                if(len <= 16) data.add(Math.random().toString().replace(".", (Math.random() * 10).toInt().toString()).substring(0, len))
                else{
                    val s = Math.random().toString().replace(".", (Math.random() * 10).toInt().toString()).substring(0, 16) + Math.random().toString().replace(".", (Math.random() * 10).toInt().toString()).substring(0, 16)
                    data.add(s.substring(0, len))
                }
            }
        }
        return data
    }

    class CustomRecyclerAdapter(private val passwords: List<String>) :
        RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var password: EditText? = null
            var copy: ImageButton? = null

            init {
                password = itemView.findViewById(R.id.pass_et)
                copy = itemView.findViewById(R.id.copy_password)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.constructor_password, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.password?.setText(passwords[position])
            holder.copy?.setOnClickListener {
                val clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("", holder.password?.text.toString())
                clipboard.setPrimaryClip(clip)
                Snackbar.make(it, R.string.copied, Snackbar.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount() = passwords.size
    }

}