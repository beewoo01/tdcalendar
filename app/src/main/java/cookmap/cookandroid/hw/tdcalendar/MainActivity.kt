package cookmap.cookandroid.hw.tdcalendar

import android.Manifest
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import cookmap.cookandroid.hw.tdcalendar.databinding.ActivityMainBinding
import cookmap.cookandroid.hw.tdcalendar.databinding.NaviHeaderBinding
import cookmap.cookandroid.hw.tdcalendar.model.User
import cookmap.cookandroid.hw.tdcalendar.session.session
import cookmap.cookandroid.hw.tdcalendar.view.Main_Fragment
import cookmap.cookandroid.hw.tdcalendar.view.Setting_Fragment
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Date_ViewModel
import cookmap.cookandroid.hw.tdcalendar.viewmodel.LoginViewModel
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var TAG = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: Date_ViewModel
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var headerbind : NaviHeaderBinding
    private lateinit var mSocket : Socket
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
        headerbind.activity = this@MainActivity
        initView()
        viewModel.date = Calendar.getInstance().timeInMillis
        loginViewModel.testname.value = "메인엑티비티"

    }

    fun initView(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_menu_24)
        //main fragment 로 연결
        setFragment(Main_Fragment())
        //supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, Main_Fragment()).commit()
        binding.designNavigationView.setNavigationItemSelectedListener(this)

        if (!session.prefs.getString().first.equals("") ||
            !session.prefs.getString().second.equals("")){
            onLogin(User(session.prefs.getString().first , session.prefs.getString().second))
        }
    }

    fun setFragment( fragment : Fragment){
        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_fragment, fragment).commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //var ft = supportFragmentManager.beginTransaction()
        lateinit var fragment : Fragment

        when(item.itemId){
            R.id.action_main -> fragment = Main_Fragment()
            R.id.action_group -> fragment = Main_Fragment()
            R.id.action_scadule -> fragment = Main_Fragment()
            R.id.action_make_room -> fragment = Main_Fragment()
            R.id.action_settings -> fragment = Setting_Fragment()

        }
        setFragment(fragment)
        binding.drawerLayout.closeDrawers()
        return true
    }

    fun onLogin(user: User) {
        loginViewModel.start()
        loginViewModel.connect()
        loginViewModel.isRememvered = true
        loginViewModel.socketEmit("login", user)
        loginViewModel.socketOn("serverMessage")
    }

    fun userInfoClick(){
        if (loginViewModel.user.value?.Email == null){
            var loginDialog = LogIn_dialog()
            loginDialog.show(supportFragmentManager, "dialog")
        }

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
        binding.drawerLayout.open()
        return super.onSupportNavigateUp()
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