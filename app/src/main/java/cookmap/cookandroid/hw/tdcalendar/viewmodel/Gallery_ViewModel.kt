package cookmap.cookandroid.hw.tdcalendar.viewmodel

import android.app.Application
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import cookmap.cookandroid.hw.tdcalendar.SearchIMG
import cookmap.cookandroid.hw.tdcalendar.controller.ApiService
import cookmap.cookandroid.hw.tdcalendar.controller.SearchRefrofit
import cookmap.cookandroid.hw.tdcalendar.controller.SocketHelper
import cookmap.cookandroid.hw.tdcalendar.model.Img
import cookmap.cookandroid.hw.tdcalendar.model.User
import kotlinx.android.synthetic.main.login_dialog.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class Gallery_ViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application

    val socketHelper : SocketHelper = SocketHelper()
    val socket = socketHelper.getSocket()

    var item = MutableLiveData<Img>()
    private val _data = MutableLiveData<List<Img>>()
    val data : LiveData<List<Img>>
        get() = _data

    init {
        fetchData()
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _data.postValue(SearchIMG(app).getAll())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    sealed class Action{
        object Navigater : Action()
    }

    private val _action : MutableLiveData<Action> = MutableLiveData()
    val action : LiveData<Action> get() = _action

    fun onClickNavigate(img: Img){
        item.value =  img
        _action.value = Action.Navigater

    }

    fun emitterImg(path: String){
        Log.d("emitterImg", "11111111111111111")
        val file = File(path)
        val bos = ByteArrayOutputStream()
        val bitmapDate = bos.toByteArray()
        val fos = FileOutputStream(file)
        fos. write(bitmapDate)
        fos.flush()
        fos.close()
        val client = OkHttpClient.Builder().build()
        //val apiService = Retrofit.Builder().baseUrl("https://beewoo01.tk/").client(client)
            //.build().create(ApiService::class.java)

        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        val body = MultipartBody.Part.createFormData("upload", file.name, reqFile)
        var gson = GsonBuilder().setLenient().create()
        var retrofit = Retrofit.Builder().baseUrl("http://192.168.56.1:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        var server = retrofit.create(ApiService::class.java)
        server.postImage(User().idx.toString(),body)


        server.postImage(User().idx.toString(), body)?.enqueue(object : retrofit2.Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Log.d("emitterImg", "onResponse")
                if (response.isSuccessful){
                    Toast.makeText(app,"IsSuccessful", Toast.LENGTH_SHORT).show()
                    Log.d("onResponse", "isSuccessful")
                }else{
                    Toast.makeText(app, "Some error occurred...", Toast.LENGTH_LONG).show();
                    Log.d("onResponse", "else!!")
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.d("레트로핏 결과1",t.message)
            }

        })

    }
    
}