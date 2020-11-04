package cookmap.cookandroid.hw.tdcalendar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookmap.cookandroid.hw.tdcalendar.SearchIMG

class Gallery_ViewModel(application: Application) : AndroidViewModel(application) {

    private val serchImg = SearchIMG(application).getAll()
    private val liveList =  MutableLiveData<ArrayList<String>>()

    fun getAllImg() : MutableLiveData<ArrayList<String>>{
        liveList.value = serchImg
        return liveList
    }

    
}