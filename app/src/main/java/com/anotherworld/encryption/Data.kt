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
    private val first = File(d("first"))
    private val second = File(d("second"))
    private val room = File(d("room"))
    private val name = File(d("name"))
    private val code = File(d("code"))
    private val type_text = File(d("typetext"))
    private val type_image = File(d("typeimage"))
    private val len_password = File(d("len"))
    private val number = File(d("number"))
    private val user = File(d("user"))
    private val key_folder = File(d("keyfolder"))
    private val method_vendor = File(d("methodvendor"))
    private val length_vendor = File(d("lengthvendor"))
    private val method_vendor_image = File(d("methodvendorimage"))
    private val length_vendor_image = File(d("lengthvendorimage"))
    init {
        try{
            if (!height.exists() || !key_text.exists() || !len_password.exists() || !number.exists() || !user.exists() || !key_folder.exists() || !method_vendor.exists()
                || !method_vendor_image.exists()){
                width.createNewFile()
                height.createNewFile()
                value.createNewFile()
                number.createNewFile()

                key_text.createNewFile()
                key_image.createNewFile()
                first.createNewFile()
                second.createNewFile()
                room.createNewFile()
                name.createNewFile()
                code.createNewFile()
                type_text.createNewFile()
                type_image.createNewFile()
                len_password.createNewFile()
                user.createNewFile()
                key_folder.createNewFile()
                method_vendor.createNewFile()
                length_vendor.createNewFile()
                method_vendor_image.createNewFile()
                length_vendor_image.createNewFile()

                setNumber(10)
                setLength(16)
                setTypeText(0)
                setTypeImage(0)

                setHeight(0)
                setWidth(0)
                setUser(0)
            }
        }catch (e: FileNotFoundException){
            width.createNewFile()
            value.createNewFile()
            height.createNewFile()
            number.createNewFile()

            key_text.createNewFile()
            key_image.createNewFile()
            first.createNewFile()
            second.createNewFile()
            room.createNewFile()
            name.createNewFile()
            code.createNewFile()
            type_text.createNewFile()
            type_image.createNewFile()
            len_password.createNewFile()
            user.createNewFile()
            key_folder.createNewFile()
            method_vendor.createNewFile()
            length_vendor.createNewFile()
            method_vendor_image.createNewFile()
            length_vendor_image.createNewFile()

            setNumber(10)
            setLength(16)
            setTypeText(0)
            setTypeImage(0)

            setHeight(0)
            setWidth(0)
            setUser(0)
        }
    }
    fun getMethodImageVendor(): String{
        return FileInputStream(method_vendor_image).bufferedReader().use { it.readText() }.toString()
    }
    fun setMethodImageVendor(value: String){
        method_vendor_image.writeText(value)
    }
    fun setLengthImageVendor(i: Int){
        length_vendor_image.writeText(i.toString())
    }
    fun getLengthImageVendor(): Int{
        return try{
            FileInputStream(length_vendor_image).bufferedReader().use { it.readText() }.toString().toInt()
        }catch (e: Exception){ 0 }
    }
    fun getLengthVendor(): Int{
        return FileInputStream(length_vendor).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun setLengthVendor(value: Int){
        length_vendor.writeText(value.toString())
    }
    fun getMethodVendor(): String{
        return FileInputStream(method_vendor).bufferedReader().use { it.readText() }.toString()
    }
    fun setMethodVendor(value: String){
        method_vendor.writeText(value)
    }
    fun setKeyFolder(value: String){
        key_folder.writeText(value)
    }
    fun getKeyFolder(): String{
        return FileInputStream(key_folder).bufferedReader().use { it.readText() }.toString()
    }
    fun setUser(value: Int){
        user.writeText(value.toString())
    }
    fun getUser(): Int{
        return FileInputStream(user).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun setNumber(value: Int){
        number.writeText(value.toString())
    }
    fun getNumber(): Int{
        return FileInputStream(number).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun getLength(): Int{
        return FileInputStream(len_password).bufferedReader().use { it.readText() }.toString().toInt()
    }
    fun setLength(value: Int){
        len_password.writeText(value.toString())
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