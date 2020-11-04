package cookmap.cookandroid.hw.tdcalendar

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object BingAdapter{

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, path: String) {
        Glide.with(view.context).load(path).into(view)
    }
}
