package com.anotherworld.encryption.ui.password

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.anotherworld.encryption.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment() {

    private lateinit var slideshowViewModel: PasswordViewModel
    private var _binding: FragmentPasswordBinding? = null
    private lateinit var update: ImageButton
    private lateinit var sp: RecyclerView

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        slideshowViewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    class ConstructorAdapter internal constructor(context: Context?, constructor: List<ConstructorPassword>, private val onClickListener: OnStateClickListener) :
//        RecyclerView.Adapter<ConstructorAdapter.ViewHolder>() {
//        internal interface OnStateClickListener {
//            fun onStateClick(state: ConstructorPassword?, position: Int)
//        }
//        private val inflater: LayoutInflater = LayoutInflater.from(context)
//        private val constructor: List<ConstructorPassword> = constructor
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view: View = inflater.inflate(R.layout.constructor_password, parent, false)
//            return ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val cons: ConstructorPassword = constructor[position]
//            holder.password.setText(cons.password)
//            holder.itemView.setOnClickListener { onClickListener.onStateClick(cons, position) }
//        }
//
//        override fun getItemCount(): Int {
//            return constructor.size
//        }
//
//        class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
//            val password: EditText = view.findViewById(R.id.pass_et)
//            val copy: ImageButton = view.findViewById(R.id.copy_password)
//        }
//
//    }

}