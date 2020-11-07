package cookmap.cookandroid.hw.tdcalendar.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cookmap.cookandroid.hw.tdcalendar.databinding.GalleryItemImageviewBinding
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingParentBinding
import cookmap.cookandroid.hw.tdcalendar.model.Img
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel
import cookmap.cookandroid.hw.tdcalendar.viewmodel.LoginViewModel


class Setting_profile_Dl_Fragment : Fragment() {

    lateinit var bind: SettingParentBinding
    val galviewmodel: Gallery_ViewModel by activityViewModels()
    //var fragmentWidth : Int = 0
    val loginViewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = SettingParentBinding.inflate(inflater, container, false)
        bind.apply {
            viewmodel = galviewmodel
            setLifecycleOwner(requireActivity())
            recyclerSetProfile.adapter = Imageadater(galviewmodel.getAllImg().value!!)

        }
        return bind.root
    }


    inner class Imageadater(var uriArr: ArrayList<Img>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            val binding = GalleryItemImageviewBinding.inflate(LayoutInflater.from(parent.context))
            binding.root.apply {
                val width = (parent.getResources().getDisplayMetrics().widthPixels / 3.25).toInt()
                this.layoutParams = LinearLayout.LayoutParams(width, width).also {
                    it.setMargins(2, 2, 2, 2)
                }
            }
            return viewHolder(binding)
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var image = (holder as viewHolder).bindingItem.galleryImgView
            Glide.with(requireActivity()).load(uriArr.get(position).uri).into(image)
        }

        override fun getItemCount(): Int {
            return uriArr.size
        }

        inner class viewHolder(val bindingItem: GalleryItemImageviewBinding) :
            RecyclerView.ViewHolder(bindingItem.root) {
            init {
                bindingItem.galleryImgView.setOnClickListener {
                    val parm = uriArr.get(adapterPosition)
                    galviewmodel.onClickNavigate(parm.uri, parm.originName)
                }
            }
        }
    }

}