package cookmap.cookandroid.hw.tdcalendar.model

import android.net.Uri
import android.webkit.WebStorage

class Img {
    var uri : String = ""
    var originName : String = ""

    constructor(uri: String, originName : String){
        this.uri = uri
        this.originName = originName
    }
}