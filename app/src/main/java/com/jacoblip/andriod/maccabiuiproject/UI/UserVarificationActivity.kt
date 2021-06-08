package com.jacoblip.andriod.maccabiuiproject.UI

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.jacoblip.andriod.maccabiuiproject.R
import kotlinx.android.synthetic.main.activity_verafacation.*

private const val theID = "123456"
private const val thePassword = "abcdef"

const val TAG = "UserActivity"
class UserVarificationActivity:AppCompatActivity() {

    lateinit var textNumberID:TextInputEditText
    lateinit var textPassword:TextInputEditText
    lateinit var submitButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verafacation)
        submitButton = findViewById(R.id.submitButton)
        textNumberID = findViewById(R.id.ID_Number)
        textPassword = findViewById(R.id.password)
        Toast.makeText(applicationContext,"מספר תעודת הזהות הוא:123456  והסיסמה היא:abcdef",Toast.LENGTH_LONG).show()
        val extras = intent.extras
        if (extras != null) {
            val greeting = extras.getString("greeting")
            val name = extras.getString("name")
            nameTV.text = name
            greetingsTV.text = greeting
        }
        submitButton.setOnClickListener { submitFun() }
    }

    fun submitFun(){


        val strID = textNumberID.text.toString().trim()
        val strPassword = textPassword.text.toString().trim()
        Log.i(TAG,strID)
        Log.i(TAG,strPassword)
        var error = 0

        if(TextUtils.isEmpty(strID)||TextUtils.isEmpty(strPassword)){
            error = 1
        }
        if(strID == theID&& strPassword== thePassword){
            val returnBackIntent = Intent()
            returnBackIntent.putExtra("result",true)
            setResult(AppCompatActivity.RESULT_OK,returnBackIntent)
            finish()
        }else{
            if(error==0)
            error = 2
        }

        when(error){
            1->{
                textPassword.error = "לא יכול להיות ריק"
                textNumberID.error = "לא יכול להיות ריק"
            }
            2->{
                textPassword.error = "נתונים שגויים"
                textNumberID.error = "נתונים שגויים"
                Toast.makeText(applicationContext,"לא הצלחנו לזהות אותך אנא נסה שנית",Toast.LENGTH_LONG).show()
            }
        }

    }
}