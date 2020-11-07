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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cookmap.cookandroid.hw.tdcalendar.MainActivity
import cookmap.cookandroid.hw.tdcalendar.Profile_FragmentActivity
import cookmap.cookandroid.hw.tdcalendar.onClickNavigator
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel

class Setting_Fragment : Fragment() , onClickNavigator{

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val galleryViewmodel:Gallery_ViewModel by viewModels()

    /*private fun bindViewModel(){
        Log.d("bindViewModel", "bindViewModel")
        galleryViewmodel.action.observe(viewLifecycleOwner, Observer {
            when (it){
                is Gallery_ViewModel.Action.Navigate ->{
                    Log.d("bindViewModel.", "왓다")
                }
            }
        })
    }*/

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            permissions.entries.forEachIndexed { index, mutableEntry ->
                Log.e("DEBUG", "${mutableEntry.key} = ${mutableEntry.value}")
                Log.e("DEBUG INDEX ", "${index}")
                Log.d("ket.get", mutableEntry.key.get(index).toString())
                Log.d("ket", mutableEntry.value.toString())
                if (index == 1 && mutableEntry.value){
                    startActivity(Intent(activity, Profile_FragmentActivity::class.java) )
                    //showDialog(Setting_profile_Dl_Fragment(), "setProfile")
                }
                else if (index == 1 && !mutableEntry.value || index == 0 && !mutableEntry.value) {
                    Toast.makeText(activity, "접근하려면 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }

        }

    fun showDialog(dialogFragment: Fragment, tag : String) {
        //((MainActivity)).setFragment(fragment = dialogFragment))
        (activity as MainActivity).setFragment(dialogFragment)
        //dialogFragment.show(parentFragmentManager, tag)
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
        bind.galViewModel = galleryViewmodel

        //galleryViewmodel.setNavigator(this)
        /*galleryViewmodel.action.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "일단은 옴!", Toast.LENGTH_SHORT).show()
            Log.d("galleryViewmodel", "들어왔어요")
            when (it) {
                is Gallery_ViewModel.Action.Navigate -> {
                    Toast.makeText(requireContext(), "Navigate!", Toast.LENGTH_SHORT).show()
                }
            }
        })*/

        //bindViewModel()
        with(bind) {
            viewmodel = loginViewModel
            lifecycleOwner = requireActivity()
            loginViewModel.testname.value = "SettingFragment"
            recyclerSetting.apply {
                adapter = setting_frag_adapter()
            }
        }

        /*galleryViewmodel.item.value = "feww3"
        galleryViewmodel.item.observe(viewLifecycleOwner, Observer {
            Log.d("여기옴", "setting_fragment Viewmodel")
            showDialog(Setting_ImgCrop_Dl_Fragmnet().newInstance(it), "img_Crop")
        })*/

        //val view = bind.root
        return bind.root
    }

    fun setImage() {
        Log.d("setImage", "왓습니다.")
        requestMultiplePermissions.launch(
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
        )
    }

    override fun onitemClick() {
        Log.d("SettinfFragment", "onitemClick")
        //showDialog(Setting_ImgCrop_Dl_Fragmnet(), "")
        TODO("Not yet implemented")
    }


}