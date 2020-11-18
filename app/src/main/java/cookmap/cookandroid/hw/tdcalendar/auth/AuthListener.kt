package cookmap.cookandroid.hw.tdcalendar.auth

import androidx.lifecycle.LiveData
import cookmap.cookandroid.hw.tdcalendar.model.User
import org.json.JSONObject

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse : LiveData<Pair<String?, User?>>)
    fun onFailure(message : String)
}