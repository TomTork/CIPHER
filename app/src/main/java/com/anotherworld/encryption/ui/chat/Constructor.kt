package com.anotherworld.encryption.ui.chat

var key: String? = null
class Constructor {
    var message: String? = null
    var author: String? = null
    constructor(message: String?, author: String?){
        try{
            this.message = message
        }catch (e: Exception){
            this.message = "ERROR"
        }
        this.author = author
    }
    constructor(){}
}