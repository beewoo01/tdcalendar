package cookmap.cookandroid.hw.tdcalendar

import android.R.attr.data
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.core.graphics.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import cookmap.cookandroid.hw.tdcalendar.databinding.ProfileFragmentActivityBinding
import cookmap.cookandroid.hw.tdcalendar.view.Setting_profile_Dl_Fragment
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel
import java.lang.Exception


class Profile_FragmentActivity : FragmentActivity() {

    lateinit var binding: ProfileFragmentActivityBinding
    val galleryViewmodel: Gallery_ViewModel by viewModels()
    var count = 0


    val requestActivity = registerForActivityResult(
        StartActivityForResult()
    ) {
        val result = CropImage.getActivityResult(it.data)
        if (it.resultCode == RESULT_OK) {
            val resultUri: Uri = result.getUri()

           /* Log.d("encodedPath??", resultUri.encodedPath)
            Log.d("toString Path??", resultUri.toString())*/
            galleryViewmodel.emitterImg(resultUri.encodedPath)


            //upload(resultUri.toString())
        } else if (it.resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Log.d("resultCode", result.error.toString())
        }
    }

    //file:///data/user/0/cookmap.cookandroid.hw.tdcalendar/cache/cropped7381478666513832706.jpg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.profile_fragment_activity)
        binding.also {
            it.galViewModel = galleryViewmodel
            it.setLifecycleOwner(this)
            it.profileFa = this

        }
        movoFragment(Setting_profile_Dl_Fragment(), count)
        bindViewModel()

    }


    fun movoFragment(fragment: Fragment, count: Int) {
        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout_profile, fragment).commit()

    }


    private fun bindViewModel() {
        Log.d("bindViewModel", "bindViewModel")
        galleryViewmodel.action.observe(this, Observer {
            when (it) {
                is Gallery_ViewModel.Action.Navigater -> {
                    galleryViewmodel.item.value?.let {
                        val intent = Intent(
                            CropImage.activity(Uri.parse(it.uri))
                                .setCropShape(CropImageView.CropShape.OVAL)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setGuidelinesColor(Color.RED)
                                .getIntent(this)
                        )
                        requestActivity.launch(intent)
                    }

                }
            }
        })
    }

    private fun upload() {

    }

    override fun onBackPressed() {
        finish()
    }

}