package cookmap.cookandroid.hw.tdcalendar

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import cookmap.cookandroid.hw.tdcalendar.databinding.ActivityMainBinding
import cookmap.cookandroid.hw.tdcalendar.databinding.NaviHeaderBinding
import cookmap.cookandroid.hw.tdcalendar.view.Main_Fragment
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Date_ViewModel
import cookmap.cookandroid.hw.tdcalendar.viewmodel.LoginViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*


class MainActivity : AppCompatActivity() {

    var TAG = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: Date_ViewModel
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var headerbind : NaviHeaderBinding
    lateinit var mSocket : Socket
    var users : Array<String> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(Date_ViewModel::class.java)
        val headerView : View = binding.designNavigationView.getHeaderView(0)
        headerbind = NaviHeaderBinding.bind(headerView)
        binding.activity = this@MainActivity
        headerbind.viewmodel = loginViewModel
        headerbind.setLifecycleOwner(this)
        headerbind.nickName.setText("닉네임")
        //loginViewModel.onLoginResponse.observe(this, this::observeViewmodel)


        initView()
        viewModel.date = Calendar.getInstance().timeInMillis
        Log.d("date?", viewModel.date.toString())
        Log.d("timeInMillis?", Calendar.getInstance().timeInMillis.toString())
        //observeViewmodel()

        //binding.drawerLayout.closeDrawers()

    }
    fun observeViewmodel(data : JSONObject){
        Log.d("observeViewmodel", data.getString("data"))
        //Log.d("observeVie user.email", loginViewModel.user.Email)

        //headerbind.nickName.setText(loginViewModel.user.name)
    }

    fun initView(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_menu_24)

        binding.designNavigationView.setNavigationItemSelectedListener { item: MenuItem ->
            item.setCheckable(true)
            binding.drawerLayout.closeDrawers()
            onChangeFragment(item)
            true
        }
    }

    fun userInfoClick(view: View){
        var loginDialog = LogIn_dialog()
        loginDialog.show(supportFragmentManager, "dialog")
    }


    private val onLoginReceived = Emitter.Listener { args ->
        val receivedDate = args[0] as JSONObject
        var message = receivedDate.getString("result")
        Log.d("login", message)
    }

    private val onMessageReceived = Emitter.Listener { args ->
        try {
            Log.d("onMeaasgeRecived", "왔음")
            val receivedDate = args[0] as JSONObject
            var massage = receivedDate.getString("msg")
            Log.d(TAG, massage)
            Log.d(TAG, receivedDate.getString("data"))
            //Log.d(TAG, receivedDate.getString("data"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val onConnect : Emitter.Listener = Emitter.Listener {
        Log.d("CordovaLog", "--connect: success")
        mSocket.emit("login", "hi")
    }

    val onNewUser: Emitter.Listener = Emitter.Listener {

        var data = it[0] //String으로 넘어옵니다. JSONArray로 넘어오지 않도록 서버에서 코딩한 것 기억나시죠?
        if (data is String) {
            users = data.split(",").toTypedArray() //파싱해줍니다.
            for (a: String in users) {
                Log.d("user", a) //누구누구 있는지 한 번 쫘악 뽑아줘봅시다.
            }
        } else {
            Log.d("error", "Something went wrong")
        }

    }

    val onMyMessage = Emitter.Listener {
        Log.d("on", "Mymessage has been triggered.")
        Log.d("mymsg : ", it[0].toString())
    }

    val onNewMessage = Emitter.Listener {
        Log.d("on", "New message has been triggered.")
        Log.d("new msg : ", it[0].toString())
    }

    val onLogout = Emitter.Listener {
        Log.d("on", "Logout has been triggered.")

        try {
//             {"diconnected" : nickname,
//              "whoIsOn" : whoIsOn
//         } 이런 식으로 넘겨진 데이터를 파싱하는 방법입니다.
            val jsonObj: JSONObject = it[0] as JSONObject //it[0] 은 사실 Any 타입으로 넘어오지만 캐스팅을 해줍니다.
            Log.d("logout ", jsonObj.getString("disconnected"))
            Log.d("WHO IS ON NOW", jsonObj.getString("whoIsOn"))

            //Disconnect socket!
            mSocket.disconnect()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        Log.d("click", "됨")
        binding.drawerLayout.open()
        return super.onSupportNavigateUp()
    }

    fun onChangeFragment(item: MenuItem){
        val ft = supportFragmentManager.beginTransaction()
        when(item.itemId){
            R.id.action_main -> ft.replace(R.id.nav_fragment, Main_Fragment()).commit()
            R.id.action_group -> Log.d("click", "group")
            R.id.action_scadule -> true
            R.id.action_make_room -> true
            R.id.action_settings -> true
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}