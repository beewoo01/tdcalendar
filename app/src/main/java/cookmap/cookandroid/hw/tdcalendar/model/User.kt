package cookmap.cookandroid.hw.tdcalendar.model


data class User (var Email : String, var Password : String){
    var name : String? = null
    var idx : Int = 0
    var frofile : String? = null
    var invitation : Boolean = true
}