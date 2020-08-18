package com.example.asday12_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val staticNum = 13
    private var shouldAutoSplit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPhoneEditText.addTextChangedListener(object : LoginTextWatcher(){
            override fun afterTextChanged(s: Editable?) {
                mLogin.isEnabled = s.toString().length == staticNum

                //Determine whether you're typing or deleting
                if(!shouldAutoSplit){
                    return
                }
                s.toString().length.also {
                    if (it == 3 || it == 8){
                        //Space Needed
                        //133 3333 3333
                        s?.append(' ')
                    }
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shouldAutoSplit = (count == 1)
            }

        })

        mLogin.setOnClickListener{
            Intent().apply {
                // Jump to where
                setClass(this@MainActivity,VerifyActivity::class.java)
                // Configure the data for the jump
                putExtra("phone",getPhoneNumber(mPhoneEditText.text))

                startActivity(this)
            }
        }
    }


    //Converts formatted contents to normal data
    private fun getPhoneNumber(editable: Editable):String{
        //Create a new object to operate the contents in editable object
        SpannableStringBuilder(editable.toString()).also {
            it.delete(3,4)
            it.delete (7,8)
            return it.toString()
        }

        /**
        editable.delete(3,4)
        editable.delete(7,8)
        */

        return editable.toString()
    }
}

open class LoginTextWatcher :TextWatcher{
    override fun afterTextChanged(s: Editable?) {    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){    }

}

