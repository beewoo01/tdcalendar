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
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingParentBinding
import java.util.*

class Setting_profile_Fragment : DialogFragment() {

    val mainChildTag = "settin_main"
    val imageChildTag = "Image_fragment"
    lateinit var bind : SettingParentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = SettingParentBinding.inflate(inflater, container, false)
        //(activity as MainActivity?)?.getSupportActionBar()?.setTitle("Setting")
        getImage()

        return bind.root
    }

    private fun getImage(){
        val columns = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_TAKEN)
        val oderBy = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        val cursor = activity?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, oderBy)

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateTakenColumn =
                it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

            while (it.moveToNext()){
                val id = it.getLong(idColumn)
                val dateTaken = Date(it.getLong(dateTakenColumn))
                val contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())

                Log.d(javaClass.simpleName, "id: $id, date_taken: " +
                        "$dateTaken, content_uri: $contentUri")
            }
        }
        cursor?.close()
    }

    override fun onResume() {
        super.onResume()
        // didalog size 조절
        val windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }


    inner class Imageadater : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }


    }

}