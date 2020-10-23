package cookmap.cookandroid.hw.tdcalendar

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import cookmap.cookandroid.hw.tdcalendar.databinding.LoginDialogBinding
import cookmap.cookandroid.hw.tdcalendar.model.User
import cookmap.cookandroid.hw.tdcalendar.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_dialog.view.*
import org.json.JSONException
import org.json.JSONObject

class LogIn_dialog : DialogFragment() {


    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: LoginDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_dialog, container, false)
        var root : View = binding.root
        binding.fragment = this@LogIn_dialog
        binding.backBtnLogin.setOnClickListener {
            dismiss()
        }
        return root
    }


    override fun onResume() {
        super.onResume()

        val windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }

    fun setLogin(){
        var editEmail = binding.userEmailEdit.text.toString()
        var editPwd = binding.userPwdEdit.text.toString()
        if (TextUtils.isEmpty(editEmail) || TextUtils.isEmpty(editPwd)){
            Toast.makeText(activity, "빈 칸 없이 작성 해주세요", Toast.LENGTH_SHORT).show()
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editEmail).matches()){
            Toast.makeText(activity, "이메일 형식에 어긋납니다.", Toast.LENGTH_SHORT).show()
        }else{
            val user = User(editEmail, editPwd)
            observeViewmodel(user)
        }
    }

    fun observeViewmodel(user : User){

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.start()
        loginViewModel.connect()
        loginViewModel.socketEmit("login", user)
        loginViewModel.socketOn("serverMessage")
        loginViewModel.onLoginResponse.observe(this, this::onLoginResponse)

        Log.d("observeViewmodel", "observeViewmodel 함수");
    }

    private fun onLoginResponse(data: JSONObject) {
        //TODO json 파싱 후 UI 업데이트
        if (data.getInt("data") == 1){
            
        }
        Log.d("Main_onLoginResponse", "여기옴")
        Log.d("Main_onLoginResponse", "data " + data.getString("data"))
        Log.d("Main_onLoginResponse", "sendresult "+ data.getString("sendresult"))
        this.dismiss()

    }

}