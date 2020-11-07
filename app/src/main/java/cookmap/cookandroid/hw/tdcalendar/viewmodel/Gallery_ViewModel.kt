package cookmap.cookandroid.hw.tdcalendar.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cookmap.cookandroid.hw.tdcalendar.SearchIMG
import cookmap.cookandroid.hw.tdcalendar.model.Img
import cookmap.cookandroid.hw.tdcalendar.onClickNavigator

class Gallery_ViewModel(application: Application) : AndroidViewModel(application) {

    private val serchImg: ArrayList<Img> = SearchIMG(application).getAll()
    private val liveList =  MutableLiveData<ArrayList<Img>>()
    var item = MutableLiveData<Pair<String?, String?>>()
    var testString = MutableLiveData<String>()


    sealed class Action{
        object Navigate : Action()
        data class Popup(val popupId: Int):Action()
        data class Imgk(val uri:String, val ori_name: String):Action()
    }

    private val _action : MutableLiveData<Action> = MutableLiveData()
    val action : LiveData<Action> get() = _action

    /*fun onClickNavigate(){
        _action.value = Action.Navigate
    }*/
    fun onClickNavigate(uri : String, ori_name: String){
        _action.value = Action.Imgk(uri, ori_name)
        item.value = Pair(uri, ori_name)
    }

    fun onClickPopup(popupId : Int){
        _action.value = Action.Popup(popupId)
    }



    fun getAllImg() : MutableLiveData<ArrayList<Img>>{
        liveList.value = serchImg
        Log.d("getAllImg", liveList.value?.get(1)?.originName)
        return liveList
    }

    fun setItem(position : Int ){
        val img= liveList.value?.get(position)
       // item = Pair(img?.uri, img?.originName)
    }

    fun itemClick(position: Int){
        Log.d("Gallery_ViewModel", "itemClick")
        val img= liveList.value?.get(position)
      //  item = Pair(img?.uri, img?.originName)
    }

    
}