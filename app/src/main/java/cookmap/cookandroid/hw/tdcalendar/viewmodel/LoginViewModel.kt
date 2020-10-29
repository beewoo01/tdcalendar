package cookmap.cookandroid.hw.tdcalendar.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookmap.cookandroid.hw.tdcalendar.controller.SocketHelper
import cookmap.cookandroid.hw.tdcalendar.model.User
import cookmap.cookandroid.hw.tdcalendar.session.session
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val mSocketHelper : SocketHelper = SocketHelper()
    val Tag = "LoginViewmodel"
    lateinit var mSocket: Socket
    val user = MutableLiveData<User>()
    lateinit var sharedPreferences: SharedPreferences
    var isRememvered = false

    fun start(){
        Log.d("viewmodel", "start에옴")
        mSocket = mSocketHelper.getSocket()!!
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        if (mSocket == null){
            Log.d("viewmodel", "mSocket null 이라네")
        }else{
            Log.d("viewmodel", "mSocket not null 이라네")
        }
    }

    fun socketOn(action: String){
        Log.d(Tag, "socketOn")
        mSocket.on(action, onLogin)
    }

    fun socketEmit(action : String, user : User){
        var jsonObject = JSONObject()
        jsonObject.put("email", user.Email)
        jsonObject.put("pwd", user.Password)
        //jsonObject.put("name", user.Name)
        mSocket.emit(action, jsonObject)
    }

    fun socketOff(action : String){
        mSocket.off()
    }

    private var onConnect : Emitter.Listener = Emitter.Listener { args ->
        //val  user = User("1", "일번")
        //mSocket.emit("login", user.NickName)
        Log.d("Socket", "is connected Sussece")
    }

    private var onLogin : Emitter.Listener = Emitter.Listener { args ->
        Log.d("onLogin", "Model Onlogin 에 옴")
        val receivedDate = args[0] as JSONObject
        Log.d("onLoginFunction", receivedDate.getString("data"))
        //Log.d("onLoginFunction", receivedDate.getString("sendresult"))
        if (receivedDate.getInt("data") == 1){
            this.user.postValue(User(name = "로그인"))
            // 에러 처리 Toast 메세지로 출력 ex) 로그인에 실패 했습니다 : 비밀번호 틀렸을경우
        }else{
            val jsonArray = receivedDate.getJSONArray("sendresult")
            for (i in 0.. jsonArray.length() -1){
                val iObject = jsonArray.getJSONObject(i)
                val id = iObject.getInt("userUid")
                val email = iObject.getString("userEmail")
                val pwd = iObject.getString("userPwd")
                val name = iObject.getString("userName")
                val profile = iObject.getString("userProfile")
                val invitation = iObject.getString("invitation")
                this.user.postValue(User(email, pwd, name, id, profile, invitation))
                if (!isRememvered) session.prefs.setString(email, pwd)

            }

        }

    }

    fun connect(){
        mSocket.connect()
    }
}
