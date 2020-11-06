package cookmap.cookandroid.hw.tdcalendar.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cookmap.cookandroid.hw.tdcalendar.adapter.setting_frag_adapter
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingFragmentBinding
import cookmap.cookandroid.hw.tdcalendar.viewmodel.LoginViewModel
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel

class Setting_Fragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val galleryViewmodel:Gallery_ViewModel by viewModels()

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            permissions.entries.forEachIndexed { index, mutableEntry ->
                Log.e("DEBUG", "${mutableEntry.key} = ${mutableEntry.value}")
                Log.e("DEBUG INDEX ", "${index}")
                Log.d("ket.get", mutableEntry.key.get(index).toString())
                Log.d("ket", mutableEntry.value.toString())
                if (index == 1 && mutableEntry.value){
                    showDialog(Setting_profile_Dl_Fragment(), "setProfile")
                }
                else if (index == 1 && !mutableEntry.value || index == 0 && !mutableEntry.value) {
                    Toast.makeText(activity, "접근하려면 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }

        }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DESTROYED ", "DESTROYED ")
    }

    fun showDialog(dialogFragment: DialogFragment, tag : String) {
        dialogFragment.show(parentFragmentManager, tag)
        //Setting_profile_Fragment().show(parentFragmentManager, "setProfile")
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
            loginViewModel.testname.value = "SettingFragment"
            recyclerSetting.apply {
                adapter = setting_frag_adapter()
            }
        }

        galleryViewmodel.item.value = "feww3"
        galleryViewmodel.item.observe(viewLifecycleOwner, Observer {
            Log.d("여기옴", "setting_fragment Viewmodel")
            showDialog(Setting_ImgCrop_Dl_Fragmnet().newInstance(it), "img_Crop")
        })

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