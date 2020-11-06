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
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cookmap.cookandroid.hw.tdcalendar.databinding.GalleryItemImageviewBinding
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingParentBinding
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel
import cookmap.cookandroid.hw.tdcalendar.viewmodel.LoginViewModel


class Setting_profile_Dl_Fragment : DialogFragment() {

    lateinit var bind: SettingParentBinding
    val viewmodel: Gallery_ViewModel by viewModels()
    var fragmentWidth : Int = 0
    val loginViewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = SettingParentBinding.inflate(inflater, container, false)

        bind.apply {
            recyclerSetProfile.adapter = Imageadater(viewmodel.getAllImg().value!!)
            // todo
            loginViewModel.testname.value = "DIALOG_FRAGMENT"

            viewmodel.item.observe(requireActivity(), Observer {
                dismiss()
            })

            //this.cropImageView.setImageUriAsync()
            //recyclerSetProfile.adapter = Imageadater(getImage())
        }
        return bind.root
    }

    override fun onResume() {
        super.onResume()
        // didalog size 조절
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        fragmentWidth = params?.width!!
        Log.d("resume width", params?.width.toString())

        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }


    inner class Imageadater(var uriArr: ArrayList<String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            val binding = GalleryItemImageviewBinding.inflate(LayoutInflater.from(parent.context))
            binding.root.apply {
                val width = (fragmentWidth / 3.25).toInt()
                this.layoutParams = LinearLayout.LayoutParams(width, width).also {
                    it.setMargins(2, 2, 2, 2)
                }
            }
            return viewHolder(binding)
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var image = (holder as viewHolder).bindingItem.galleryImgView
            Glide.with(requireActivity()).load(uriArr.get(position)).into(image)
        }

        override fun getItemCount(): Int {
            return uriArr.size
        }

        inner class viewHolder(val bindingItem: GalleryItemImageviewBinding) :
            RecyclerView.ViewHolder(bindingItem.root) {
            init {
                bindingItem.galleryImgView.setOnClickListener {
                    viewmodel.setItem(adapterPosition)
                }
            }
        }
    }

}