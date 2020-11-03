package cookmap.cookandroid.hw.tdcalendar.view

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cookmap.cookandroid.hw.tdcalendar.adapter.setting_frag_adapter
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingFragmentBinding
import cookmap.cookandroid.hw.tdcalendar.viewmodel.LoginViewModel
import androidx.activity.result.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission

class Setting_Fragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            permissions.entries.forEachIndexed { index, mutableEntry ->
                Log.e("DEBUG", "${mutableEntry.key} = ${mutableEntry.value}")
                Log.e("DEBUG INDEX ", "${index}")
                Log.d("ket.get", mutableEntry.key.get(index).toString())
                Log.d("ket", mutableEntry.value.toString())
                if (index == 1 && mutableEntry.value) showDialog(true, true)
            }

        }

    private fun showDialog(first : Boolean, second : Boolean) {
        if (first && second){
            // TODO: 2020-11-03 dialogFragment연결
            /*val display : Display? = activity?.windowManager?.defaultDisplay
            val size = Point()
            display?.getSize(size)*/
            Setting_profile_Fragment().show(parentFragmentManager, "setProfile")
        }else{
            Toast.makeText(activity, "접근하려면 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("settingFragment", "왓습니다.")
        val bind = SettingFragmentBinding.inflate(inflater, container, false)
        bind.fragment = this

        with(bind) {
            viewmodel = loginViewModel
            lifecycleOwner = requireActivity()
            recyclerSetting.apply {
                adapter = setting_frag_adapter()
            }
        }
        //val view = bind.root
        return bind.root
    }

    fun setImage() {
        Log.d("setImage", "왓습니다.")
        requestMultiplePermissions.launch(
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
        )
    }


}