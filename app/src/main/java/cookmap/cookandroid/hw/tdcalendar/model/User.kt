package cookmap.cookandroid.hw.tdcalendar.model



class User{

    var Email : String = ""
    var Password : String = ""
    var name: String = ""
    var idx: Int = 0
    var profile: String = ""
    var invitation: String = ""

    constructor(Email: String, Password: String){
        this.Email = Email
        this.Password = Password
    }

    constructor()

    constructor(name: String){ this.name = name }


    constructor(Email: String, Password: String, name: String,
                idx : Int, profile: String, invitation: String){
        this.Email = Email
        this.Password = Password
        this.name = name
        this.idx = idx
        this.profile = profile
        this.invitation = invitation
    }


}
