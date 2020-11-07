package cookmap.cookandroid.hw.tdcalendar

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cookmap.cookandroid.hw.tdcalendar.model.Img
import cookmap.cookandroid.hw.tdcalendar.model.User
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class SearchIMG(context: Context) {
    private var context = context


    fun getAll(): ArrayList<Img> {
        val columns = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_TAKEN
        )
        val oderBy = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, oderBy
        )
        var array = ArrayList<Img>()
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateTakenColumn =
                it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val dateTaken = Date(it.getLong(dateTakenColumn))
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                array.add(Img(contentUri.toString(), dateTaken.toString()))
                Log.d(
                    javaClass.simpleName, "id: $id, date_taken: " +
                            "$dateTaken, content_uri: $contentUri"
                )
            }


        }
        cursor?.close()

        return array
    }
}