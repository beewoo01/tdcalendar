package cookmap.cookandroid.hw.tdcalendar

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cookmap.cookandroid.hw.tdcalendar.model.Img
import cookmap.cookandroid.hw.tdcalendar.view.Setting_profile_Dl_Fragment


object BingAdapter{

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, path: String) {
        Glide.with(view.context).load(path).into(view)
    }


}
