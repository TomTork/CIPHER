package com.anotherworld.encryption

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.SwitchCompat

class Settings : AppCompatActivity() {
    val data = Data()

    private lateinit var type_text:   AppCompatSpinner
    private lateinit var type_image:  AppCompatSpinner
    private lateinit var type_video:  AppCompatSpinner

    private lateinit var change_default_key_for_text:  EditText
    private lateinit var change_default_key_for_image: EditText
    private lateinit var change_default_key_for_video: EditText
    private lateinit var first:                        EditText
    private lateinit var second:                       EditText
    private lateinit var change_default_room:          EditText
    private lateinit var change_default_name:          EditText
    private lateinit var change_default_code:          EditText

    private lateinit var sw_store_image: SwitchCompat
    private lateinit var sw_store_video: SwitchCompat

    override fun onPause() {
        super.onPause()
        data.setTypeText(type_text.selectedItemPosition)
        data.setTypeImage(type_image.selectedItemPosition)
        data.setTypeVideo(type_video.selectedItemPosition)
        data.setKeyText(change_default_key_for_text.text.toString())
        data.setKeyImage(change_default_key_for_image.text.toString())
        data.setKeyVideo(change_default_key_for_video.text.toString())
        data.setFirst(first.text.toString())
        data.setSecond(second.text.toString())
        data.setRoom(change_default_room.text.toString())
        data.setName(change_default_name.text.toString())
        data.setCode(change_default_code.text.toString())
        if(sw_store_image.isChecked){
            data.setSWImage(1)
        }
        else data.setSWImage(0)
        if(sw_store_video.isChecked){
            data.setSWVideo(1)
        }
        else data.setSWVideo(0)
    }
    fun getData(){
        type_text.setSelection(data.getTypeText())
        type_image.setSelection(data.getTypeImage())
        type_video.setSelection(data.getTypeVideo())
        change_default_key_for_text.setText(data.getKeyText())
        change_default_key_for_image.setText(data.getKeyImage())
        change_default_key_for_video.setText(data.getKeyVideo())
        first.setText(data.getFirst())
        second.setText(data.getSecond())
        change_default_room.setText(data.getRoom())
        change_default_name.setText(data.getName())
        change_default_code.setText(data.getCode())
        sw_store_image.isChecked = data.getSWImage() != 0
        sw_store_video.isChecked = data.getSWVideo() != 0
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
        type_video = findViewById(R.id.type_video)
        change_default_key_for_text = findViewById(R.id.change_default_key_for_text)
        change_default_key_for_image = findViewById(R.id.change_default_key_for_image)
        change_default_key_for_video = findViewById(R.id.change_default_key_for_video)
        first = findViewById(R.id.first)
        second = findViewById(R.id.second)
        change_default_room = findViewById(R.id.change_default_room)
        change_default_name = findViewById(R.id.change_default_name)
        change_default_code = findViewById(R.id.change_default_code)
        sw_store_image = findViewById(R.id.sw_store_image)
        sw_store_video = findViewById(R.id.sw_store_video)
    }
}