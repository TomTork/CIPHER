package com.anotherworld.encryption

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class Data {
    private val com = "data/data/com.anotherworld.encryption/"
    private fun d(name: String): String{
        return "$com$name.txt"
    }
    private val width = File(d("width"))
    private val height = File(d("height"))
    private val value = File(d("value"))

    private val key_text = File(d("keytext"))
    private val key_image = File(d("keyimage"))
    private val key_video = File(d("keyvideo"))
    private val first = File(d("first"))
    private val second = File(d("second"))
    private val room = File(d("room"))
    private val name = File(d("name"))
    private val code = File(d("code"))
    private val type_text = File(d("typetext"))
    private val type_image = File(d("typeimage"))
    private val type_video = File(d("typevideo"))
    private val sw_image = File(d("swimage"))
    private val sw_video = File(d("swvideo"))
    init {
        try{
            if (!height.exists() || !key_text.exists()){
                width.createNewFile()
                height.createNewFile()
                value.createNewFile()

                key_text.createNewFile()
                key_image.createNewFile()
                key_video.createNewFile()
                first.createNewFile()
                second.createNewFile()
                room.createNewFile()
                name.createNewFile()
                code.createNewFile()
                type_text.createNewFile()
                type_image.createNewFile()
                type_video.createNewFile()
                sw_image.createNewFile()
                sw_video.createNewFile()

                setTypeText(0)
                setTypeImage(0)
                setTypeVideo(0)
                setSWImage(0)
                setSWVideo(0)

                setHeight(0)
                setWidth(0)
            }
        }catch (e: FileNotFoundException){
            width.createNewFile()
            value.createNewFile()
            height.createNewFile()

            key_text.createNewFile()
            key_image.createNewFile()
            key_video.createNewFile()
            first.createNewFile()
            second.createNewFile()
            room.createNewFile()
            name.createNewFile()
            code.createNewFile()
            type_text.createNewFile()
            type_image.createNewFile()
            type_video.createNewFile()
            sw_image.createNewFile()
            sw_video.createNewFile()

            setTypeText(0)
            setTypeImage(0)
            setTypeVideo(0)
            setSWImage(0)
            setSWVideo(0)

            setHeight(0)
            setWidth(0)
        }
    }
    fun getSWVideo(): Int{
        return FileInputStream(sw_video).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun setSWVideo(value: Int){
        sw_video.writeText(value.toString())
    }
    fun setSWImage(value: Int){
        sw_image.writeText(value.toString())
    }
    fun getSWImage(): Int{
        return FileInputStream(sw_image).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun getTypeVideo(): Int{
        return FileInputStream(type_video).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun setTypeVideo(value: Int){
        type_video.writeText(value.toString())
    }
    fun getTypeImage(): Int{
        return FileInputStream(type_image).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun setTypeImage(value: Int){
        type_image.writeText(value.toString())
    }
    fun getTypeText(): Int{
        return FileInputStream(type_text).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun setTypeText(value: Int){
        type_text.writeText(value.toString())
    }
    fun setCode(value: String){
        code.writeText(value)
    }
    fun getCode(): String{
        return FileInputStream(code).bufferedReader().use { it.readText() }.toString()
    }
    fun getName(): String{
        return FileInputStream(name).bufferedReader().use { it.readText() }.toString()
    }
    fun setName(value: String){
        name.writeText(value)
    }
    fun getRoom(): String{
        return FileInputStream(room).bufferedReader().use { it.readText() }.toString()
    }
    fun setRoom(value: String){
        room.writeText(value)
    }
    fun getSecond(): String{
        return FileInputStream(second).bufferedReader().use { it.readText() }.toString()
    }
    fun setSecond(value: String){
        second.writeText(value.toString())
    }
    fun setFirst(value: String){
        first.writeText(value.toString())
    }
    fun getFirst(): String{
        return FileInputStream(first).bufferedReader().use { it.readText() }.toString()
    }
    fun getKeyVideo(): String{
        return FileInputStream(key_video).bufferedReader().use { it.readText() }.toString()
    }
    fun setKeyVideo(value: String){
        key_video.writeText(value)
    }
    fun getKeyImage(): String{
        return FileInputStream(key_image).bufferedReader().use { it.readText() }.toString()
    }
    fun setKeyImage(value: String){
        key_image.writeText(value)
    }
    fun getKeyText(): String{
        return FileInputStream(key_text).bufferedReader().use { it.readText() }.toString()
    }
    fun setKeyText(value: String){
        key_text.writeText(value)
    }
    fun setValue(v: String){
        value.writeText(v)
    }
    fun getValue(): String{
        return FileInputStream(value).bufferedReader().use { it.readText() }.toString()
    }
    fun getHeight(): Int{
        return FileInputStream(height).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun getWidth(): Int{
        return FileInputStream(width).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun setWidth(value: Int){
        width.writeText(value.toString())
    }
    fun setHeight(value: Int){
        height.writeText(value.toString())
    }
}