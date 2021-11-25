package com.anotherworld.encryption

import android.util.Base64.*
import java.security.Key
import java.security.KeyPairGenerator
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CipherData {}

class AES(s: ByteArray, secret: String, method: String, encr: String = ""){
    var enc = s.toList().toString().replace("[", "").replace("]", "").replace(" ", "")
    var key = secret
    var m = method
    var encry = encr
    init {
        if (key.length < 16){
            do {
                key += (Math.random() * 10).toInt()
            }while (key.length != 16)
        }
        else if(key.length > 16){
            key = key.substring(0, 16).replace(".", "1")
        }
    }
    fun encrypt(secret: String = key, data: String = enc, method: String = m): Boolean {
        val decodedKey = Base64.getDecoder().decode(secret)
        val cipher = Cipher.getInstance(method)
        val originalKey: SecretKey = SecretKeySpec(Arrays.copyOf(decodedKey, 16), method.substringBefore("/"))
        cipher.init(Cipher.ENCRYPT_MODE, originalKey, IvParameterSpec(ByteArray(16)))
        val cipherText = cipher.doFinal(data.toByteArray(charset("UTF-8")))
        Data().setValue(Base64.getEncoder().encodeToString(cipherText))
        if(Data().getValue().isNotEmpty()) return true
        return false
    }
    fun decrypt(secret: String = key, encryptedString: String = encry, method: String = m): String {
        val decodedKey = Base64.getDecoder().decode(secret)
        return try {
            val cipher = Cipher.getInstance(method)
            val originalKey: SecretKey = SecretKeySpec(Arrays.copyOf(decodedKey, 16), method.substringBefore("/"))
            cipher.init(Cipher.DECRYPT_MODE, originalKey, IvParameterSpec(ByteArray(16)))
            val cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString))
            String(cipherText)
        } catch (e: java.lang.Exception) {
            throw RuntimeException("Error occured while decrypting data", e)
        }
    }
}
class AESFORTEXT(value: String, secret: String){
    var key = secret
    var vv = value
    init {
        if (key.length < 16){
            do {
                key += (Math.random() * 10).toInt()
            }while (key.length != 16)
        }
        else if(key.length > 16){
            key = key.substring(0, 16).replace(".", "1")
        }
    }
    fun encrypt(secret: String = key, data: String = vv, method: String = "AES/CBC/PKCS5Padding"): Boolean {
        val decodedKey = Base64.getDecoder().decode(secret)
        val cipher = Cipher.getInstance(method)
        val originalKey: SecretKey = SecretKeySpec(Arrays.copyOf(decodedKey, 16), method.substringBefore("/"))
        cipher.init(Cipher.ENCRYPT_MODE, originalKey, IvParameterSpec(ByteArray(16)))
        val cipherText = cipher.doFinal(data.toByteArray(charset("UTF-8")))
        Data().setValue(Base64.getEncoder().encodeToString(cipherText))
        if(Data().getValue().isNotEmpty()) return true
        return false
    }
    fun decrypt(secret: String = key, encryptedString: String = vv, method: String = "AES/CBC/PKCS5Padding"): String {
        val decodedKey = Base64.getDecoder().decode(secret)
        return try {
            val cipher = Cipher.getInstance(method)
            val originalKey: SecretKey = SecretKeySpec(Arrays.copyOf(decodedKey, 16), method.substringBefore("/"))
            cipher.init(Cipher.DECRYPT_MODE, originalKey, IvParameterSpec(ByteArray(16)))
            val cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString))
            String(cipherText)
        } catch (e: java.lang.Exception) {
            throw RuntimeException("Error occured while decrypting data", e)
        }
    }
}
class AESFORFIREBASE(value: String, secret: String){
    var key = secret
    var vv = value
    init {
        if (key.length < 16){
            do {
                key += (Math.random() * 10).toInt()
            }while (key.length != 16)
        }
        else if(key.length > 16){
            key = key.substring(0, 16).replace(".", "1")
        }
    }
    fun encrypt(secret: String = key, data: String = vv, method: String = "AES/CBC/PKCS5Padding"): String {
        val decodedKey = Base64.getDecoder().decode(secret)
        val cipher = Cipher.getInstance(method)
        val originalKey: SecretKey = SecretKeySpec(Arrays.copyOf(decodedKey, 16), method.substringBefore("/"))
        cipher.init(Cipher.ENCRYPT_MODE, originalKey, IvParameterSpec(ByteArray(16)))
        val cipherText = cipher.doFinal(data.toByteArray(charset("UTF-8")))
        Data().setValue(Base64.getEncoder().encodeToString(cipherText))
        return Data().getValue()
    }
    fun decrypt(secret: String = key, encryptedString: String = vv, method: String = "AES/CBC/PKCS5Padding"): String {
        val decodedKey = Base64.getDecoder().decode(secret)
        return try {
            val cipher = Cipher.getInstance(method)
            val originalKey: SecretKey = SecretKeySpec(Arrays.copyOf(decodedKey, 16), method.substringBefore("/"))
            cipher.init(Cipher.DECRYPT_MODE, originalKey, IvParameterSpec(ByteArray(16)))
            val cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString))
            String(cipherText)
        } catch (e: java.lang.Exception) {
            throw RuntimeException("Error occured while decrypting data", e)
        }
    }
}
class GMS(value: String, secret: String){
    var key = secret
    var vv = value
    init {
        if (key.length < 16){
            do {
                key += (Math.random() * 10).toInt()
            }while (key.length != 16)
        }
        else if(key.length > 16){
            key = key.substring(0, 16).replace(".", "1")
        }
    }
    fun encrypt(value: String = vv, password: String = key): String {
        val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
        val iv = ByteArray(16)
        val charArray = password.toCharArray()
        for (i in 0 until charArray.size){
            iv[i] = charArray[i].toByte()
        }
        val ivParameterSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        val encryptedValue = cipher.doFinal(value.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedValue)
    }

    fun decrypt(value: String = vv, password: String = key): String {
        val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
        val iv = ByteArray(16)
        val charArray = password.toCharArray()
        for (i in 0 until charArray.size){
            iv[i] = charArray[i].toByte()
        }
        val ivParameterSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        val decryptedByteValue = cipher.doFinal(Base64.getDecoder().decode(value))
        return String(decryptedByteValue)
    }
}
class GMSFORIMAGE(byteArray: ByteArray, secret: String, enc: String = ""){
    var key = secret
    var encr = enc
    var vv = byteArray.toList().toString().replace("[", "").replace("]", "").replace(" ", "")
    init {
        if (key.length < 16){
            do {
                key += (Math.random() * 10).toInt()
            }while (key.length != 16)
        }
        else if(key.length > 16){
            key = key.substring(0, 16).replace(".", "1")
        }
    }
    fun encrypt(value: String = vv, password: String = key): Boolean {
        val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
        val iv = ByteArray(16)
        val charArray = password.toCharArray()
        for (i in 0 until charArray.size){
            iv[i] = charArray[i].toByte()
        }
        val ivParameterSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        val encryptedValue = cipher.doFinal(value.toByteArray())
        Data().setValue(Base64.getEncoder().encodeToString(encryptedValue))
        if (Data().getValue().isNotEmpty())return true
        return false
    }
    fun decrypt(value: String = encr, password: String = key): String {
        val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
        val iv = ByteArray(16)
        val charArray = password.toCharArray()
        for (i in 0 until charArray.size){
            iv[i] = charArray[i].toByte()
        }
        val ivParameterSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        val decryptedByteValue = cipher.doFinal(Base64.getDecoder().decode(value))
        return String(decryptedByteValue)
    }
}
class CIPHER1{
    var secret = ""
    fun encrypt(valueBase: String): String{
        val array = valueBase.toCharArray().map { it.code }
        val numbers = valueBase.toCharArray().map { it.code.toString().length }.toString().filterNot { "[], ".indexOf(it) > -1 }
        secret += "$numbers|"
        var value: String = array.toList().toString().filterNot { "[], ".indexOf(it) > -1 }
        var num = 65
        for(i in value.indices){
            for(t in i until (value.length / 2)){
                if(value.substring(i, t).isInt() && value.substring(i, t).length >= 2 && count(value, value.substring(i, t)) >= 2){
                    if(num <= 90){
                        secret += value.substring(i, t) + ";"
                        value = value.replace(value.substring(i, t), num.toChar().toString())
                        num++
                    }
                    else break
                }
            }
        }
        val mi = "|" + secret.substringAfter("|")
        val se = secret.substringBefore("|")
        var sec = se
        var otv = ""
        val set = IntArray(se.length){ se[it].toString().toInt() }.toSet().toList()
        var nu = 0
        for(i in set.indices){
            for(t in 1 until se.length + 1){
                if(sec.length >= t && sec.substring(0, t)[t - 1].toString().toInt() == set[i]){
                    nu++
                }
                else{ break }
            }
            if(nu >= 3){
                otv += set[i].toString() + "^$nu;"
                sec = sec.replaceFirst(sec.substring(0, nu), "")
                nu = 0
            }
            else nu = 0
        }
        secret = otv + sec + mi
        Data().setValue(value)
        return value
    }
    private fun String.isInt(): Boolean{
        var temp = this
        temp = temp.filterNot { "0123456789".indexOf(it) > -1 }
        if (temp.isEmpty())return true
        return false
    }
    fun decrypt(value: String, secret: String): String{
        val num = secret.substringBefore("|")
        var code = secret.substringAfter("|")
        var numN = ""
        if(num.contains("^")){
            var time = num
            for (i in 0 until count(num, "^")){
                for(t in 0 until time.substringBefore(";").substringAfter("^").toInt()){
                    numN += time.substringBefore("^")
                }
                time = time.replaceFirst(time.substringBefore(";"), "").replaceFirst(";", "")
            }
        }
        val arr: ArrayList<String> = ArrayList()
        var answer = value
        for(o in 65..90){
            arr.add(o.toChar().toString())
        }
        for (r in value.indices){
            if(code.isNotEmpty()){
                answer = answer.replace(arr[r], code.substringBefore(";"))
                code = code.replaceFirst(code.substringBefore(";"), "").replaceFirst(";", "")
            }
            else break
        }
        if(numN == "")numN = num
        val arrNum = numN.toCharArray().toList()
        val array = ArrayList<Int>()
        for (i in arrNum.indices){
            array.add(answer.substring(0, arrNum[i].toString().toInt()).toInt())
            answer = answer.replaceFirst(answer.substring(0, arrNum[i].toString().toInt()), "")
        }
        return array.joinToString { it.toChar().toString() }.replace(", ", "")
    }
}
fun count(str: String, target: String): Int {
    if(target.isNotEmpty()) return (str.length - str.replace(target, "").length) / target.length
    return -1
}
class CIPHER2{ //Z = 0 Y = " "
    fun encrypt(valueBase: String, secret: String): String{
        val key = secret.hashCode().toString().substringAfter("-")
        var f = 0
        var answer = ""
        val kk = valueBase.replace(" ", "Y")
        val array = kk.toCharArray().map { String.format("%a", it.code.toFloat()).substringAfter(".") + randomUPPER() }
        var value = array.toList().toString().filterNot { "[], ".indexOf(it) > -1 }.replace("0", "Z")
        if (value[0].toString() == "0")value = value.replaceFirst("0", "1")
        var onlyText = value.filterNot { "0123456789".indexOf(it) > -1 }
        println(value)
        for (i in onlyText.indices){
            if (value.substringBefore(onlyText[0].toString()).isNotEmpty()){
                answer += value.substringBefore(onlyText[0].toString()).toInt() shl key[f].toString().toInt()
                value = value.replaceFirst(value.substringBefore(onlyText[0].toString()), "").replaceFirst(onlyText[0].toString(), "")
                answer += onlyText[0].toString()
                onlyText = onlyText.replaceFirst(onlyText[0].toString(), "")
                if (f == key.length - 1)f = 0
                else f++
            }
            else{
                answer += value[0].toString()
                onlyText = onlyText.replaceFirst(onlyText[0].toString(), "")
                value = value.replaceFirst(value[0].toString(), "")
            }
        }
        return answer
    }
    fun decrypt(valueBase: String, secret: String): String{
        val key = secret.hashCode().toString().substringAfter("-")
        var f = 0
        var onlyText = valueBase.filterNot { "0123456789".indexOf(it) > -1 }
        var answer = ""
        var value = valueBase
        for (i in onlyText.indices){
            if (value.substringBefore(onlyText[0].toString()).isNotEmpty()){
                answer += value.substringBefore(onlyText[0].toString()).toInt() shr key[f].toString().toInt()
                value = value.replaceFirst(value.substringBefore(onlyText[0].toString()), "").replaceFirst(onlyText[0].toString(), "")
                answer += onlyText[0].toString()
                onlyText = onlyText.replaceFirst(onlyText[0].toString(), "")
                if (f == key.length - 1)f = 0
                else f++
            }
            else{
                answer += value[0].toString()
                onlyText = onlyText.replaceFirst(onlyText[0].toString(), "")
                value = value.replaceFirst(value[0].toString(), "")
            }
        }
        answer = answer.replace("Z", "0")
        var new = ""
        for (i in answer.indices){
            new += if (answer[i].isUpperCase()) " "
            else answer[i].toString()
        }
        new = "0x1.$new"
        var new2 = ""
        for (i in new.indices){
            new2 += if (new[i].toString() == " ") " 0x1."
            else new[i].toString()
        }
        new2 = new2.substringBeforeLast(" 0x1.")
        val arr = new2.split(" ")
        val final = ArrayList<String>()
        for (i in arr.indices){
            final.add(arr[i].toFloat().toInt().toChar().toString())
        }
        return final.toList().toString().filterNot { "[], ".indexOf(it) > -1 }.replace("Y", " ")
    }
    private fun randomUPPER(): String{
        val random = Random()
        return IntArray(1) { random.nextInt(88 - 65) + 65 }.asList().map { it.toChar() }.toString().filterNot { "[]".indexOf(it) > -1 }
    }
}