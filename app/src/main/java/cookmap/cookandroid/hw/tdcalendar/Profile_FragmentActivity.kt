package cookmap.cookandroid.hw.tdcalendar

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import cookmap.cookandroid.hw.tdcalendar.databinding.ProfileFragmentActivityBinding
import cookmap.cookandroid.hw.tdcalendar.view.Setting_Fragment
import cookmap.cookandroid.hw.tdcalendar.view.Setting_ImgCrop_Dl_Fragmnet
import cookmap.cookandroid.hw.tdcalendar.view.Setting_profile_Dl_Fragment
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel
import kotlinx.android.synthetic.main.profile_fragment_activity.*

class Profile_FragmentActivity : FragmentActivity() {

    lateinit var binding : ProfileFragmentActivityBinding
    val galleryViewmodel : Gallery_ViewModel by viewModels()
    var count = 0

    private fun bindViewModel(){
        Log.d("bindViewModel", "bindViewModel")
        galleryViewmodel.action.observe(this, Observer {
            when (it){
                is Gallery_ViewModel.Action.Navigate ->{
                    movoFragment(Setting_ImgCrop_Dl_Fragmnet(), count)
                }
                is Gallery_ViewModel.Action.Imgk ->{
                    movoFragment(Setting_ImgCrop_Dl_Fragmnet(), count)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.profile_fragment_activity)
        binding.galViewModel = galleryViewmodel
        binding.setLifecycleOwner(this)
        movoFragment(Setting_profile_Dl_Fragment() , count)
        bindViewModel()
    }
    fun movoFragment(fragment : Fragment, count : Int){
        var ft = supportFragmentManager.beginTransaction()
        if (count == 0){
            Log.d(javaClass.simpleName, "count 0 in here")
            ft.replace( R.id.frame_layout_profile, fragment).addToBackStack(null).commit()
            this.count = 1
        }else{
            Log.d(javaClass.simpleName, "count 1 in here")
            ft.replace( R.id.frame_layout_profile, fragment).commit()
            this.count = 0
        }

    }

    override fun onBackPressed() {
        if (count == 1){
            finish()
        }else{
            super.onBackPressed()
        }

    }

}