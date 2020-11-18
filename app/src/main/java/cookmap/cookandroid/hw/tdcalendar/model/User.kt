package cookmap.cookandroid.hw.tdcalendar.model

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("userUid")
    var idx: Int,
    @SerializedName("userEmail")
    var Email : String,
    @SerializedName("userPwd")
    var Password : String,
    @SerializedName("userName")
    var name: String,
    @SerializedName("userProfile")
    var profile: String,
    @SerializedName("invitation")
    var invitation: String
) {
    constructor(email : String, Password: String)
}
