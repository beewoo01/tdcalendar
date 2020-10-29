package cookmap.cookandroid.hw.tdcalendar.session

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private val editor : SharedPreferences.Editor = prefs.edit()

    fun getString(): Pair<String, String> {
        var pEmail = prefs.getString("email", "")
        var pPwd = prefs.getString("pwd", "")
        var pair = Pair(pEmail, pPwd)
        return pair
    }

    fun setString(email: String, pwd: String) {
        prefs.edit().putString("email", email).apply()
        prefs.edit().putString("pwd", pwd).apply()
    }

}