package com.anotherworld.encryption

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.SwitchCompat

class Settings : AppCompatActivity() {
    val data = Data()

    private lateinit var type_text:   AppCompatSpinner
    private lateinit var type_image:  AppCompatSpinner

    private lateinit var change_default_key_for_text:  EditText
    private lateinit var change_default_key_for_image: EditText
    private lateinit var first:                        EditText
    private lateinit var second:                       EditText
    private lateinit var change_default_room:          EditText
    private lateinit var change_default_name:          EditText
    private lateinit var change_default_code:          EditText
    private lateinit var input_length:                 EditText
    private lateinit var input_number:                 EditText



    override fun onPause() {
        super.onPause()
        data.setTypeText(type_text.selectedItemPosition)
        data.setTypeImage(type_image.selectedItemPosition)
        data.setKeyText(change_default_key_for_text.text.toString())
        data.setKeyImage(change_default_key_for_image.text.toString())
        data.setFirst(first.text.toString())
        data.setSecond(second.text.toString())
        data.setRoom(change_default_room.text.toString())
        data.setName(change_default_name.text.toString())
        data.setCode(change_default_code.text.toString())
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
        change_default_key_for_image.setText(data.getKeyImage())
        first.setText(data.getFirst())
        second.setText(data.getSecond())
        change_default_room.setText(data.getRoom())
        change_default_name.setText(data.getName())
        change_default_code.setText(data.getCode())
        input_length.setText(data.getLength().toString())
        input_number.setText(data.getNumber().toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = resources.getString(R.string.action_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        id()
        getData()
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
        change_default_key_for_image = findViewById(R.id.change_default_key_for_image)
        first = findViewById(R.id.first)
        second = findViewById(R.id.second)
        change_default_room = findViewById(R.id.change_default_room)
        change_default_name = findViewById(R.id.change_default_name)
        change_default_code = findViewById(R.id.change_default_code)
        input_length = findViewById(R.id.input_length)
        input_number = findViewById(R.id.input_number)
    }
}