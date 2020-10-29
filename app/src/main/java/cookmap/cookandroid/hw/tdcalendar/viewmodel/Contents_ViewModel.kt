package cookmap.cookandroid.hw.tdcalendar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookmap.cookandroid.hw.tdcalendar.model.Contents
import kotlin.collections.ArrayList

class Contents_ViewModel() : ViewModel() {
     val model = MutableLiveData<ArrayList<Contents>>()


    fun setContentsLive(list : ArrayList<Contents>) {
        model.value = list
    }

    fun getContentsLivedate() : MutableLiveData<ArrayList<Contents>>{
        return model
    }
}