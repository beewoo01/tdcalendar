package cookmap.cookandroid.hw.tdcalendar.data.nework

import cookmap.cookandroid.hw.tdcalendar.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Retrofit_Api {
    @FormUrlEncoded
    @POST("/signup")
    fun userLogin(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<User>


    @FormUrlEncoded
    @POST("/login")
    fun userSignup(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<ResponseBody>

    companion object{
        operator fun invoke(): Retrofit_Api{

            return Retrofit.Builder()
                .baseUrl("https://beewoo01.tk")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Retrofit_Api::class.java)
        }
    }
}
