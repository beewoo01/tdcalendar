package cookmap.cookandroid.hw.tdcalendar.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingImageCromViewBinding
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel

class Setting_ImgCrop_Dl_Fragmnet() : DialogFragment(){

    private lateinit var parmUri : String

    fun newInstance(parm: String): Setting_ImgCrop_Dl_Fragmnet {
        return Setting_ImgCrop_Dl_Fragmnet().apply {
            arguments = Bundle().apply {
                putString("uri", parm)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SettingImageCromViewBinding.inflate(inflater, container, false)
        val viewmodel : Gallery_ViewModel by viewModels()
        binding.apply {
            //val selectedImageUri : Uri = Uri.parse(bundle.getString("uri"))
            arguments.let {
                parmUri = it?.getString("uri")!!
                this.cropImageView.setImageUriAsync(Uri.parse(parmUri))
            }
        }

        return binding.root
    }
}