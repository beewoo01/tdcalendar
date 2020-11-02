package cookmap.cookandroid.hw.tdcalendar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cookmap.cookandroid.hw.tdcalendar.adapter.setting_frag_adapter
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingFragmentBinding
import cookmap.cookandroid.hw.tdcalendar.viewmodel.LoginViewModel

class Setting_Fragment : Fragment(){

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = SettingFragmentBinding.inflate(inflater, container, false)

        with(bind){
            viewmodel = loginViewModel
            lifecycleOwner = requireActivity()
            recyclerSetting.apply {
                adapter = setting_frag_adapter()
            }
        }
        //val view = bind.root
        return bind.root
    }

    fun setImage(){

    }
}