package cookmap.cookandroid.hw.tdcalendar.model

class Contents{
    var title : String = ""
    var explan : String = ""
    var roomName : String = ""
    var date : String = ""
    var time : String = ""

    constructor(title: String, explan: String, roomName: String, date: String, time: String) {
        this.title = title
        this.explan = explan
        this.roomName = roomName
        this.date = date
        this.time = time
    }


}