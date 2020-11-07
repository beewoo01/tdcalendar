package cookmap.cookandroid.hw.tdcalendar.view

import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingImageCromViewBinding
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel

class Setting_ImgCrop_Dl_Fragmnet : Fragment(){

    private lateinit var parmUri : String
    var fragmentWidth : Int = 0

    fun newInstance(): Setting_ImgCrop_Dl_Fragmnet {
        return Setting_ImgCrop_Dl_Fragmnet()/*Setting_ImgCrop_Dl_Fragmnet().apply {
            arguments = Bundle().apply {
                putString("uri", parm)
            }*/
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
                //parmUri = it?.getString("uri")!!
                parmUri = viewmodel.
                parmUri = viewmodel.item.first.toString()
                Log.d("uri!!!", "parmUri")
                Log.d("uri!!!", parmUri)
                this.cropImageView.setImageUriAsync(Uri.parse(parmUri))
            }
        }

        return binding.root
    }
}