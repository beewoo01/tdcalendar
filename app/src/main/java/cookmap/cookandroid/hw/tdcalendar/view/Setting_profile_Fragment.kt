package cookmap.cookandroid.hw.tdcalendar.view

import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cookmap.cookandroid.hw.tdcalendar.databinding.GalleryItemImageviewBinding
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingParentBinding
import cookmap.cookandroid.hw.tdcalendar.viewmodel.Gallery_ViewModel
import java.util.*
import kotlin.collections.ArrayList

class Setting_profile_Fragment : DialogFragment() {

    lateinit var bind: SettingParentBinding
    var fragmentWidth : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = SettingParentBinding.inflate(inflater, container, false)
        val viewmodel: Gallery_ViewModel by viewModels()
        bind.apply {

            recyclerSetProfile.adapter = Imageadater(viewmodel.getAllImg().value!!)
            //recyclerSetProfile.adapter = Imageadater(getImage())
        }
        //(activity as MainActivity?)?.getSupportActionBar()?.setTitle("Setting")
        //getImage()

        return bind.root
    }

    /*private fun getImage() : ArrayList<String>{
        val columns = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_TAKEN)
        val oderBy = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        val cursor = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, oderBy)
        var array = ArrayList<String>()
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateTakenColumn =
                it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

            while (it.moveToNext()){
                val id = it.getLong(idColumn)
                val dateTaken = Date(it.getLong(dateTakenColumn))
                val contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                array.add(contentUri.toString())
                Log.d(javaClass.simpleName, "id: $id, date_taken: " +
                        "$dateTaken, content_uri: $contentUri")
            }
        }
        cursor?.close()
        return array
    }*/

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
            var image = (holder as viewHolder).binding.galleryImgView
            Glide.with(requireActivity()).load(uriArr.get(position)).into(image)
        }

        override fun getItemCount(): Int {
            return uriArr.size
        }

        inner class viewHolder(bindingItem: GalleryItemImageviewBinding) :
            RecyclerView.ViewHolder(bindingItem.root) {
            val binding = bindingItem
        }


    }

}