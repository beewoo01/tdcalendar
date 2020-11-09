package cookmap.cookandroid.hw.tdcalendar.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cookmap.cookandroid.hw.tdcalendar.SearchIMG
import cookmap.cookandroid.hw.tdcalendar.model.Img
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Gallery_ViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
   // private var serchImg : ArrayList<Img> = SearchIMG(app).getAll()
    private val list : MutableLiveData<ArrayList<Img>> = MutableLiveData(SearchIMG(app).getAll())
    var item = MutableLiveData<Img>()

    sealed class Action{
        object Navigater : Action()
    }

    private val _action : MutableLiveData<Action> = MutableLiveData()
    val action : LiveData<Action> get() = _action

    fun onClickNavigate(img: Img){
        Log.d(javaClass.simpleName, "onClickNavigate")
        Log.d(javaClass.simpleName, img.uri)
        item.value =  img
        _action.value = Action.Navigater

    }

    suspend fun doingSomthing() : ArrayList<Img> = withContext(Dispatchers.IO){
        val list = SearchIMG(app).getAll()
        list
    }

    fun getAllImg() : MutableLiveData<ArrayList<Img>>{
        //serchImg.postValue(SearchIMG(app).getAll())\
        /*viewModelScope.launch {
            withContext(Dispatchers.Main){
                list.value = doingSomthing()!!
            }
        }*/
        return list
    }
    
}