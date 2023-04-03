package com.example.turfnow.dependency

import android.app.Application
import android.content.Context

class MyApplication(context: Context): Application(){
    val appContainer = Appcontainer(context)
}