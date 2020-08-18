package com.example.asday12_2

import android.app.Application
import cn.bmob.v3.Bmob

/**
 *@Description
 *@邓兴杰
 *@QQ1793126995
 */
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        //Init by default
        Bmob.initialize(this, "4aa76bb270366a008e35ce814e73d822")
    }
}