package com.example.foody

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Annotating Application class with @HiltAndroidApp (the place where dagger components will generated)
@HiltAndroidApp
class MyApplication:Application() {
}