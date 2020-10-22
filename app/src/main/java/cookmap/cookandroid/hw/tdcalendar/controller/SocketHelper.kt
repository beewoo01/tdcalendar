package cookmap.cookandroid.hw.tdcalendar.controller

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException




class SocketHelper {
    private var socket: Socket? = null

    constructor(){
        try {
            Log.d("constructor", "왔습니다.")
            socket = IO.socket("https://beewoo01.tk/")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun getSocket(): Socket? {
        return socket
    }
}