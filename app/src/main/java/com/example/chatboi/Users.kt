package com.example.chatboi

class Users{
    private var profilePic:String = ""
    private var username:String = ""
    private var mail:String = ""
    private var password:String = ""
    private var userID:String = ""
    private var lastMessage:String = ""
    private var status:String = ""

    constructor(username: String,mail: String,password: String){
        this.username = username
        this.mail = mail
        this.password = password
    }

    constructor(
        profilePic: String,
        username: String,
        mail: String,
        password: String,
        userID: String,
        lastMessage: String,
        status: String
    ){
        this.profilePic = profilePic
        this.username = username
        this.mail = mail
        this.password = password
        this.userID = userID
        this.lastMessage = lastMessage
        this.status = status
    }

    fun getprofilePic():String{
        return profilePic
    }

    fun setprofilePic(profilePic:String){
        this.profilePic = profilePic
    }

    fun getusername():String{
        return username
    }

    fun setusername(username:String){
        this.username = username
    }

    fun getmail():String{
        return mail
    }

    fun setmail(mail:String){
        this.mail = mail
    }

    fun getpassword():String{
        return password
    }

    fun setpassword(password:String){
        this.password = password
    }

    fun getuserID():String{
        return userID
    }

    fun setuserID(userID:String){
        this.userID = userID
    }

    fun getlastMessage():String{
        return lastMessage
    }

    fun setlastMessage(lastMessage:String){
        this.lastMessage = lastMessage
    }

    fun getstatus():String{
        return status
    }

    fun setstatus(status:String){
        this.status = status
    }

}
