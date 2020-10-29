package cookmap.cookandroid.hw.tdcalendar.session

import android.app.Application

class session : Application() {
    companion object{
        lateinit var prefs : PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}