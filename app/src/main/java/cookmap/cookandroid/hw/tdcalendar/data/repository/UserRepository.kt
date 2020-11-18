package cookmap.cookandroid.hw.tdcalendar.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cookmap.cookandroid.hw.tdcalendar.data.nework.Retrofit_Api
import cookmap.cookandroid.hw.tdcalendar.model.User
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    fun userLogin(email: String, password: String): LiveData<Pair<String?, User?>> {
        val loginResponse = MutableLiveData<Pair<String?, User?>>()
        Retrofit_Api().userLogin(email, password)
            .enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        val result = response.body()
                        result.let {
                            loginResponse.value = Pair("Success", it)
                        }
                        Log.d("onResponse", "isSuccessful")
                    }else{

                        Log.d("onResponse else", response.errorBody()?.string())
                        loginResponse.value = Pair("Login fail", User(email, password))
                        //response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("onResponse", "fail")
                    loginResponse.value = Pair(t.message, User(email, password))
                    Log.d("onResponse", t.message)
                }

            })
        return loginResponse
    }
}