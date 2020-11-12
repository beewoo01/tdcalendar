package cookmap.cookandroid.hw.tdcalendar.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import cookmap.cookandroid.hw.tdcalendar.MainActivity
import cookmap.cookandroid.hw.tdcalendar.Profile_FragmentActivity
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel

class Setting_Fragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val galleryViewmodel:Gallery_ViewModel by viewModels()

    private val requestMultiplePermissions =
        // 권한 요청 및 확인
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            permissions.entries.forEachIndexed { index, mutableEntry ->

                if (index == 1 && mutableEntry.value){
                    startActivity(Intent(activity, Profile_FragmentActivity::class.java) )
                }
                else if (index == 1 && !mutableEntry.value || index == 0 && !mutableEntry.value) {
                    Toast.makeText(activity, "접근하려면 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
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
        bind.galViewModel = galleryViewmodel

        //bindViewModel()
        with(bind) {
            viewmodel = loginViewModel
            lifecycleOwner = requireActivity()
            loginViewModel.testname.value = "SettingFragment"
            //Glide.with(requireActivity()).load("file:/data/user/0/cookmap.cookandroid.hw.tdcalendar/cache/cropped6817747921810640664.jpg").into(userProfileSetting)
            Glide.with(requireActivity()).load("/data/user/0/cookmap.cookandroid.hw.tdcalendar/cache/cropped6817747921810640664.jpg").into(userProfileSetting)
            galleryViewmodel.item.observe(requireActivity(), Observer {
                Log.d("observe에옴", "Setting_Fragment")
               // Glide.with(requireActivity()).load("content://media/external/images/media/1091").into(userProfileSetting)
            })

            //Glide.with(requireActivity()).load(uriArr.get(position).uri).into(image)
            recyclerSetting.apply {
                adapter = setting_frag_adapter()
            }
        }
        return bind.root
    }

    fun setImage() {
        Log.d("setImage", "왓습니다.")
        requestMultiplePermissions.launch(
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
        )
    }




}