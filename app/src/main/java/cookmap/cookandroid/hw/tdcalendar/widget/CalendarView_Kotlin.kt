package cookmap.cookandroid.hw.tdcalendar.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import cookmap.cookandroid.hw.tdcalendar.R
import java.util.*

class CalendarView_Kotlin : ViewGroup {

    private val mScreenWidth :Int
    private val mWidthDate : Int

    private var mMillis : Long = 0
    private var mDteOfWeek : Int = 0

    private var mDateOfWeek : Int = 0

    private var mMaxtDateOfMonth = 0

    private var mDefaultTextSize = 40
    private var mTextColor = Color.BLUE

    private var mPaint : Paint = makePaint(mTextColor)
    var mCurrentSelectedView : CalendarItemView_Kotlin? = null

    companion object{
        var DAY_OF_WEEK : Array<String>? = null
    }



    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        mScreenWidth = resources.displayMetrics.widthPixels
        mWidthDate = mScreenWidth / 7
        DAY_OF_WEEK = resources.getStringArray(R.array.day_of_week)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var count = childCount
        var maxHeight = 0
        var maxWidth = 0
        var childState = 0
        var mLeftWidth = 0
        var rowCount = 0
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = mMillis

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility == GONE) continue

            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            maxWidth += Math.max(maxWidth, child.getMeasuredWidth())
            mLeftWidth += child.getMeasuredWidth()

            if (mLeftWidth / mScreenWidth > rowCount) {
                maxHeight += child.measuredHeight
                rowCount++
            } else {
                maxHeight = Math.max(maxHeight, child.measuredHeight)
            }
            childState = combineMeasuredStates(childState, child.measuredState)

        }

        maxHeight = (Math.ceil((count + mDateOfWeek - 1) / 7.0) * (mWidthDate * 0.75)).toInt()
        maxWidth = Math.max(maxWidth, suggestedMinimumWidth)
        val expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST)

        setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
            resolveSizeAndState(maxHeight, expandSpec, childState shl MEASURED_HEIGHT_STATE_SHIFT)
        )

        var params = layoutParams
        params.height = measuredHeight

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        var curWidth: Int
        var curHeight: Int
        var curLeft: Int
        var curTop: Int
        var maxHeight: Int

        val childLeft = this.paddingLeft
        val childTop = this.paddingTop
        val childRight = this.measuredWidth - this.paddingRight
        val childBottom = this.measuredHeight - this.paddingBottom
        val childWidth = childRight - childLeft
        val childHeight = childBottom - childTop

        maxHeight = 0
        curLeft = childLeft
        curTop = childTop

        for (i in 0 until count){
            var child = getChildAt(i)

            if (child.visibility == GONE) return

            child.measure(
                MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(
                    childHeight, MeasureSpec.AT_MOST
                )
            )

            curWidth = mWidthDate
            curHeight = (mWidthDate * 0.75).toInt()

            if (curLeft + curWidth >= childRight) {
                curLeft = childLeft
                curTop += maxHeight
                maxHeight = 0
            }

            if (i == 7) {
                curLeft = (mDateOfWeek - 1) * curWidth
            }
            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight)

            if (maxHeight < curHeight) {
                maxHeight = curHeight
            }
            curLeft += curWidth

        }

    }

    fun setDate(millis: Long){
        mMillis = millis
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        calendar.set(Calendar.DATE, 1)

        mDateOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        mMaxtDateOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private fun makePaint(color: Int) : Paint{
        var p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = color
        p.textSize = mDefaultTextSize.toFloat()
        return  p
    }
    fun initCalendar(dayOfWeek: Int, maxDateOfMonth: Int){
        mDateOfWeek = dayOfWeek
    }

    fun setCurrentSelectedView(view: View){
        if (parent is ViewGroup){
            var pager = parent as ViewGroup
            val tagView = pager.tag as? View

            if (tagView != null){
                val time = tagView.tag as Long
                val c = Calendar.getInstance()
                c.timeInMillis = time
                for (i in 0 until pager.childCount) {
                    for (j in 0 until childCount) {
                        val child =
                            (pager.getChildAt(i) as CalendarView_Kotlin).getChildAt(j) as CalendarItemView_Kotlin

                        if(child == null){
                            continue
                        }

                        if (child.isStaticText()) {
                            continue
                        }
                        if (child.isSameDay((child.tag as Long), (tagView.tag as Long))) {
                            child.invalidate()
                            break
                        }
                    }
                }
            }

            var time = view.getTag() as Long
            var cal = Calendar.getInstance()
            cal.timeInMillis = time
            pager.setTag(view)
            view.invalidate()



        }
    }
}