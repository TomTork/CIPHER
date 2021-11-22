package com.anotherworld.encryption.ui.chat

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.anotherworld.encryption.AESFORFIREBASE
import com.anotherworld.encryption.Data
import com.anotherworld.encryption.R
import com.anotherworld.encryption.databinding.FragmentChatBinding
import com.firebase.ui.database.FirebaseListAdapter
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.coroutines.launch

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel
    private var _binding: FragmentChatBinding? = null
    private lateinit var code_room: EditText
    private lateinit var name: EditText
    private lateinit var cipher: EditText
    private lateinit var rec: ListView
    private lateinit var inputChat: EditText
    private lateinit var send: ImageButton
    private lateinit var ll: LinearLayout
    private var parameter = 0
    private var old_code_room = ""

    private val binding get() = _binding!!

    //FIREBASE
    var adapter: FirebaseListAdapter<Constructor>? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var mAuth: FirebaseAuth

    override fun onResume() {
        super.onResume()
        code_room.setText(Data().getRoom())
        name.setText(Data().getName())
        cipher.setText(Data().getCode())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        code_room = root.findViewById(R.id.code_room)
        name = root.findViewById(R.id.name)
        cipher = root.findViewById(R.id.cipher)
        rec = root.findViewById<ListView>(R.id.rec)
        inputChat = root.findViewById(R.id.inputChat)
        send = root.findViewById(R.id.send)
        ll = root.findViewById(R.id.ll)
        timer()
        auth = Firebase.auth
        mAuth = Firebase.auth
        mAuth.signInAnonymously()
        send.setOnClickListener {
            if(inputChat.text.toString().isNotEmpty() && name.text.toString().isNotEmpty() && cipher.text.toString().isNotEmpty() && code_room.text.toString().isNotEmpty()){
                key = AESFORFIREBASE(inputChat.text.toString(), cipher.text.toString()).key
                old_code_room = code_room.text.toString()
                cipher.setText(key)
                FirebaseDatabase.getInstance().getReference(code_room.text.toString()).push().setValue(Constructor(AESFORFIREBASE(inputChat.text.toString(), cipher.text.toString()).encrypt(), name.text.toString()))
                inputChat.setText("")
            }
            else Snackbar.make(it, R.string.check, Snackbar.LENGTH_SHORT).show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun display(){
        adapter = object :FirebaseListAdapter<Constructor>(context, Constructor::class.java, R.layout.constructor, FirebaseDatabase.getInstance().getReference(code_room.text.toString())){
            override fun populateView(v: View, model: Constructor, position: Int) {
                val author: TextView = v.findViewById(R.id.name_constructor)
                author.text = model.author
                val message: TextView = v.findViewById(R.id.message)
                message.text = model.message
                try{
                    message.text = AESFORFIREBASE(message.text.toString(), key!!).decrypt()
                }catch (e: Exception){
                    message.text = "ERROR"
                }
                if(author.text.toString() == name.text.toString()){
                    author.setTextColor(resources.getColor(R.color.user))
                }
                else author.setTextColor(resources.getColor(R.color.user2))
            }
        }
        rec.adapter = adapter
    }

    var countDownTimer: CountDownTimer? = null
    var second = 1
    private fun timer(){
        lifecycleScope.launch {
            countDownTimer = object : CountDownTimer((second * 1000).toLong(), 1000){
                override fun onTick(p0: Long) {
                    second--
                    if((parameter == 0 && name.text.toString().isNotEmpty() && cipher.text.toString().isNotEmpty() && code_room.text.toString().isNotEmpty()) || old_code_room != code_room.text.toString() && name.text.toString().isNotEmpty() && cipher.text.toString().isNotEmpty()){
                        old_code_room = code_room.text.toString()
                        parameter = 1
                        display()
                    }
                    if(inputChat.text.toString().isNotEmpty()){
                        ll.alpha = 0F
                    }
                    else{
                        ll.alpha = 1F
                    }
                }
                override fun onFinish() {
                    second = 1
                    countDownTimer!!.start()
                }
            }
            countDownTimer!!.start()
        }
    }
}

