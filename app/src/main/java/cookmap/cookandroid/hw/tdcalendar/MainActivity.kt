package cookmap.cookandroid.hw.tdcalendar

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import cookmap.cookandroid.hw.tdcalendar.auth.AuthListener
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AuthListener {

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
        loginViewModel.testname.observe(this,  Observer {
            Log.d("로그인 바뀜", it)
        })

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
            lifecycleScope.launch {
                withContext(coroutineContext){

                    onLogin(User(session.prefs.getString().first , session.prefs.getString().second))
                }
            }
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
        loginViewModel.onRogin(user.Email, user.Password)
    }

    fun userInfoClick(){
        if (loginViewModel.user.value?.Email == null){
            var loginDialog = LogIn_dialog()
            loginDialog.show(supportFragmentManager, "dialog")
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

    override fun onStarted() {
        progressbar.show()
    }

    override fun onSuccess(loginResponse: LiveData<Pair<String?, User?>>) {
        progressbar.hide()
    }

    override fun onFailure(message: String) {
        progressbar.hide()
        toast(message)
    }
}