package com.example.asday12_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import kotlinx.android.synthetic.main.activity_verify.*

class VerifyActivity : AppCompatActivity() {

    private val verifyViews: Array<TextView> by lazy {
        arrayOf(mV1,mV2,mV3,mV4,mV5,mV6)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        // Get the data
        intent.getStringExtra("phone").also {
           // Show the phone number
            mPhone.text = it
        }



        //Listen the event that contents changed in text
        mOrigin.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //将输入的内容拆分到每一个textView中
                for((i,item) in s?.withIndex()!!){
                    verifyViews[i].text = item.toString()
                }

                //如果位数小于6个  后面的文本框不显示任何内容
                for(i in s.length..5){
                    verifyViews[i].text = ""
                }

                //判断输入的验证码个数是不是六个
                if (s.length == 6){
                    //发起验证的请求
                    BmobUtil.verifySMSCode(mPhone.text.toString(), s.toString()){
                        if(it == BmobUtil.SUCCESS){
                            //跳转到主页
                            startActivity(Intent(this@VerifyActivity,HomeActivity::class.java))
                        }else{
                            Toast.makeText(this@VerifyActivity,"验证失败",Toast.LENGTH_SHORT).show()
                            mOrigin.text.clear()
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
       BmobUtil.requestSMSCode(mPhone.text.toString()){
           if (it == BmobUtil.SUCCESS){
               Toast.makeText(this,"发送验证码成功",Toast.LENGTH_SHORT).show()
           }else{
               Toast.makeText(this,"发送验证码失败",Toast.LENGTH_SHORT).show()
           }
       }
    }
}