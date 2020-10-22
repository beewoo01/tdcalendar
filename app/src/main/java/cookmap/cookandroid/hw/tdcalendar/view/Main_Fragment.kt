package cookmap.cookandroid.hw.tdcalendar.view

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import cookmap.cookandroid.hw.tdcalendar.MainActivity
import cookmap.cookandroid.hw.tdcalendar.R
import cookmap.cookandroid.hw.tdcalendar.databinding.MainFragmentBinding
import cookmap.cookandroid.hw.tdcalendar.widget.AdapterFrgCalendar
import cookmap.cookandroid.hw.tdcalendar.widget.FrgCalendar
import java.util.*

class Main_Fragment : Fragment() , FrgCalendar.OnFragmentListener{

    private lateinit var bind : MainFragmentBinding
    private lateinit var adapter : AdapterFrgCalendar
    private val COUNT_PAGE = 12

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        initView()
        return bind.root
    }

    fun initView(){
        val calendar = Calendar.getInstance()
        adapter = AdapterFrgCalendar(activity, calendar)
        bind.pager.adapter = adapter
        adapter.setOnFragmentListener(this)
        adapter.setNumOfMonth(COUNT_PAGE)
        bind.pager.setCurrentItem(COUNT_PAGE)
        var title : String = adapter.getMonthDisplayed(COUNT_PAGE)
        Log.d("title?", title)
        (activity as MainActivity?)?.getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"#ffffff\">$title</font>"))
        bind.pager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                var title : String = adapter.getMonthDisplayed(position)
                (activity as MainActivity?)?.getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"#ffffff\">$title</font>"))
                Log.d("inFunction ", title)
                if (position == 0){
                    adapter.addPrev()
                    bind.pager.setCurrentItem(COUNT_PAGE, false)
                }else if (position == adapter.itemCount -1){
                    adapter.addNext()
                    bind.pager.setCurrentItem(adapter.itemCount - (COUNT_PAGE + 1), false)
                }
            }
        })
    }

    fun resizeHeight(mRootView: View){
        if (mRootView.height <= 1){
            return
        }
        val layoutParams : ViewGroup.LayoutParams = bind.pager.layoutParams
        if (layoutParams.height <= 0){
            layoutParams.height = mRootView.height
            bind.pager.layoutParams = layoutParams
            return
        }
        val anim = ValueAnimator.ofInt(bind.pager.layoutParams.height, mRootView.height)
        anim.addUpdateListener { animator ->
            val value = animator.animatedValue as Int
            val layoutParam : ViewGroup.LayoutParams = bind.pager.layoutParams
            layoutParam.height = value
            bind.pager.layoutParams = layoutParam
        }

        anim.duration = 200
        anim.start()
    }


    override fun onFragmentListener(view: View?) {
        if (view != null) {
            resizeHeight(view)
        }
    }
}