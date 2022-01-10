package com.anotherworld.encryption

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class Settings : AppCompatActivity() {
    val data = Data()

    private lateinit var type_text:   AppCompatSpinner
    private lateinit var type_image:  AppCompatSpinner

    private lateinit var change_default_key_for_text:  EditText
    private lateinit var first:                        EditText
    private lateinit var second:                       EditText
    private lateinit var change_default_room:          EditText
    private lateinit var change_default_name:          EditText
    private lateinit var change_default_code:          EditText
    private lateinit var input_length:                 EditText
    private lateinit var input_number:                 EditText
    private lateinit var change_default_key_for_folder:EditText

    private lateinit var list_method:                  AppCompatButton
    private val listMethod: List<String> = listOf("AES/CBC/ISO10126Padding", "AES/CFB/ISO10126Padding", "AES/OFB/ISO10126Padding", "AES/CBC/NoPadding", "AES/CFB/NoPadding",
    "AES/CTR/NoPadding", "AES/CTS/NoPadding", "AES/OFB/NoPadding", "AES/CBC/PKCS5Padding", "AES/CFB/PKCS5Padding", "AES/OFB/PKCS5Padding", "AES/OFB32/PKCS5Padding",
    "AES/CFB128/NoPadding", "AES/CFB128/PKCS5Padding", "BLOWFISH/CBC/ISO10126Padding", "BLOWFISH/CFB/ISO10126Padding", "BLOWFISH/OFB/ISO10126Padding",
    "BLOWFISH/CBC/NoPadding", "BLOWFISH/CFB/NoPadding", "BLOWFISH/CTR/NoPadding", "BLOWFISH/CTS/NoPadding", "BLOWFISH/OFB/NoPadding", "BLOWFISH/CBC/PKCS5Padding", "BLOWFISH/CFB/PKCS5Padding",
    "BLOWFISH/OFB/PKCS5Padding", "DES/CBC/ISO10126Padding", "DES/CFB/ISO10126Padding", "DES/OFB/ISO10126Padding", "DES/CBC/NoPadding", "DES/CFB/NoPadding", "DES/CTR/NoPadding",
    "DES/CTS/NoPadding", "DES/OFB/NoPadding", "DES/CBC/PKCS5Padding", "DES/CFB/PKCS5Padding", "DES/OFB/PKCS5Padding")
    private val listLength: List<String> = listOf("16", "16", "16", "16", "16", "16", "16", "16", "16", "16", "16", "16", "16", "16", "8", "8", "8", "8", "8", "8", "8"
        , "8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "8")


    override fun onPause() {
        super.onPause()
        data.setTypeText(type_text.selectedItemPosition)
        data.setTypeImage(type_image.selectedItemPosition)
        data.setKeyText(change_default_key_for_text.text.toString())
        data.setFirst(first.text.toString())
        data.setSecond(second.text.toString())
        data.setRoom(change_default_room.text.toString())
        data.setName(change_default_name.text.toString())
        data.setCode(change_default_code.text.toString())
        data.setKeyFolder(change_default_key_for_folder.text.toString())
        if(input_length.text.toString().isEmpty()){
            data.setLength(16)
        }
        else { data.setLength(input_length.text.toString().toInt()) }
        if(input_number.text.toString().isEmpty()){
            data.setNumber(10)
        }
        else if(input_number.text.toString().toInt() > 0)data.setNumber(input_number.text.toString().toInt())
    }
    fun getData(){
        type_text.setSelection(data.getTypeText())
        type_image.setSelection(data.getTypeImage())
        change_default_key_for_text.setText(data.getKeyText())
        first.setText(data.getFirst())
        second.setText(data.getSecond())
        change_default_room.setText(data.getRoom())
        change_default_name.setText(data.getName())
        change_default_code.setText(data.getCode())
        input_length.setText(data.getLength().toString())
        input_number.setText(data.getNumber().toString())
        change_default_key_for_folder.setText(data.getKeyFolder())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = resources.getString(R.string.action_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        id()
        getData()
        val listDescription: List<String> = listOf("-", "-", "-", resources.getString(R.string.method1), "-", "-", resources.getString(R.string.method2), "-"
            , "-", "-", "-", "-", "-", "-", "-", "-", "-", resources.getString(R.string.method3), "-", "-", "-", "-", "-", "-", "-", "-", "-", "-",
            resources.getString(R.string.method3), "-", "-", "-", "-", "-", "-", "-")
        type_text.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 == 5){
                    val view: View = layoutInflater.inflate(R.layout.dialog_for_vendor, null)
                    val alertDialog: AlertDialog = AlertDialog.Builder(this@Settings).create()
                    alertDialog.setTitle(R.string.fill)
                    alertDialog.setIcon(R.mipmap.ic_launcher)
                    alertDialog.setCancelable(true)
                    val method: EditText = view.findViewById(R.id.vendor_method)
                    val length: EditText = view.findViewById(R.id.vendor_length)
                    method.setText(data.getMethodVendor())
                    length.setText(data.getLengthVendor().toString())
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, resources.getText(R.string.confirm), DialogInterface.OnClickListener {
                            dialog, which ->
                        data.setMethodVendor(method.text.toString())
                        data.setLengthVendor(length.text.toString().toInt())
                        alertDialog.hide()
                    })
                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, resources.getText(R.string.cancel), DialogInterface.OnClickListener {
                            dialog, which -> alertDialog.hide()
                    })
                    alertDialog.setView(view)
                    alertDialog.setOnShowListener {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.OK))
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.Cancel))
                    }
                    alertDialog.show()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO
            }
        }
        type_image.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 == 3){
                    val view: View = layoutInflater.inflate(R.layout.dialog_for_vendor, null)
                    val alertDialog: AlertDialog = AlertDialog.Builder(this@Settings).create()
                    alertDialog.setTitle(R.string.fill)
                    alertDialog.setIcon(R.mipmap.ic_launcher)
                    alertDialog.setCancelable(true)
                    val method: EditText = view.findViewById(R.id.vendor_method)
                    val length: EditText = view.findViewById(R.id.vendor_length)
                    method.setText(data.getMethodImageVendor())
                    length.setText(data.getLengthImageVendor().toString())
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, resources.getText(R.string.confirm), DialogInterface.OnClickListener {
                            dialog, which ->
                        data.setMethodImageVendor(method.text.toString())
                        data.setLengthImageVendor(length.text.toString().toInt())
                        alertDialog.hide()
                    })
                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, resources.getText(R.string.cancel), DialogInterface.OnClickListener {
                            dialog, which -> alertDialog.hide()
                    })
                    alertDialog.setView(view)
                    alertDialog.setOnShowListener {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.OK))
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.Cancel))
                    }
                    alertDialog.show()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO
            }
        }
        list_method.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.method_encryption, null)
            val recyclerView = view.findViewById<RecyclerView>(R.id.list_method)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = CustomRecyclerAdapter(listMethod, listLength, listDescription)
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
            dialog.setContentView(view)
            dialog.show()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun id(){
        type_text = findViewById(R.id.type_text)
        type_image = findViewById(R.id.type_image)
        change_default_key_for_text = findViewById(R.id.change_default_key_for_text)
        first = findViewById(R.id.first)
        second = findViewById(R.id.second)
        change_default_room = findViewById(R.id.change_default_room)
        change_default_name = findViewById(R.id.change_default_name)
        change_default_code = findViewById(R.id.change_default_code)
        input_length = findViewById(R.id.input_length)
        input_number = findViewById(R.id.input_number)
        change_default_key_for_folder = findViewById(R.id.change_default_key_for_folder)
        list_method = findViewById(R.id.list_method_encrypt)
    }

    private class CustomRecyclerAdapter(private val method_list: List<String>, private val length_list: List<String>, private val description_list: List<String>) :
        RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var text_method: TextView? = null
            var length_key: TextView? = null
            var description: TextView? = null
            var copy_method: ImageButton? = null

            init {
                text_method = itemView.findViewById(R.id.text_method)
                length_key = itemView.findViewById(R.id.length_key)
                description = itemView.findViewById(R.id.description)
                copy_method = itemView.findViewById(R.id.copy_method)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.method_encryption_constructor, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.text_method?.text = method_list[position]
            holder.length_key?.text = length_list[position]
            holder.description?.text = description_list[position]
            holder.copy_method?.setOnClickListener {
                val clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("", holder.text_method?.text.toString())
                clipboard.setPrimaryClip(clip)
                Snackbar.make(it, R.string.copied, Snackbar.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount() = method_list.size
    }
}
