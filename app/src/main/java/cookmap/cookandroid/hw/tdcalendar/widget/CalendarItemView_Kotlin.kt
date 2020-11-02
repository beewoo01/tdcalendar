package cookmap.cookandroid.hw.tdcalendar.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import cookmap.cookandroid.hw.tdcalendar.R
import java.util.*

class CalendarItemView_Kotlin : View{

    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    val mPaintTextWhite = Paint(Paint.ANTI_ALIAS_FLAG)
    val mPaintTextSunday = Paint(Paint.ANTI_ALIAS_FLAG)
    val mPaintTextSaturday = Paint(Paint.ANTI_ALIAS_FLAG)

    val mPaintBackground = Paint(Paint.ANTI_ALIAS_FLAG)
    val mPaintBackgroundToday = Paint(Paint.ANTI_ALIAS_FLAG)
    val mPaintBackgroundEvent = Paint(Paint.ANTI_ALIAS_FLAG)

    private var dayOfWeek = -1
    private var isStaticText = false
    private var millis : Long = 0
    private lateinit var rect : Rect
    private var isTouchMode = false

    private val dp11 = Common.dp2px(context, 11F)
    private val dp16 = Common.dp2px(context, 16F)

    private var hasEvent = false
    private var mColorEvent = R.color.colorYellow
    private val RADIUS : Float = 100f


    constructor(context: Context) : super(context){
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        initialize()
    }

    private fun initialize(){
        mPaint.color = Color.BLACK
        mPaint.textSize = dp11
        mPaint.textAlign = Paint.Align.CENTER

        mPaintTextWhite.color = Color.WHITE
        mPaintTextWhite.textSize = dp11
        mPaintTextWhite.textAlign = Paint.Align.CENTER

        mPaintTextSunday.color = Color.RED
        mPaintTextSunday.textSize = dp11
        mPaintTextSunday.textAlign = Paint.Align.CENTER

        mPaintTextSaturday.color = Color.BLUE
        mPaintTextSaturday.textSize = dp11
        mPaintTextSaturday.textAlign = Paint.Align.CENTER

        mPaintBackground.color = ContextCompat.getColor(context, R.color.selectedDay)
        mPaintBackgroundToday.color = ContextCompat.getColor(context, R.color.today)
        mPaintBackgroundEvent.color = ContextCompat.getColor(context, R.color.colorPrimary)

        isClickable = true

        setOnTouchListener { v, event ->
            var action = event.action
            when(action){
                MotionEvent.ACTION_DOWN -> {
                    rect = Rect(v.left, v.top, v.right, v.bottom)
                    isTouchMode = true
                }
                MotionEvent.ACTION_UP -> {
                    (parent as? CalendarView_Kotlin)?.setCurrentSelectedView(v)
                    isTouchMode = false
                }
                MotionEvent.ACTION_CANCEL -> isTouchMode = false
                MotionEvent.ACTION_MOVE -> {
                    if (!rect.contains((v.left + (event.getX()).toInt()), (v.top + (event.getY()).toInt()))) {
                        isTouchMode = false
                        return@setOnTouchListener true
                    }
                }
            }
            return@setOnTouchListener false
        }
        setPadding(30, 0, 30, 0)


    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val xPos = canvas!!.width / 2
        val yPos = (canvas.height / 2 - ((mPaint.descent() + mPaint.ascent()) / 2)).toInt()
        var calendar : Calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        var calendarView : CalendarView_Kotlin = parent as CalendarView_Kotlin
        if (calendarView.parent is ViewGroup){
            var parent : ViewGroup = calendarView.parent as ViewGroup
            var tagView = parent.getTag() as? CalendarItemView_Kotlin

            if (!isStaticText && tagView != null && tagView.getTag() != null && tagView.getTag() is Long){
                var millis : Long = tagView.getTag() as Long
                if (isSameDay(millis, this.millis)){
                    val rectF =
                        RectF(xPos - dp16, height / 2 - dp16, xPos + dp16, height / 2 + dp16)
                    canvas.drawRoundRect(rectF, RADIUS, RADIUS, mPaintBackground)
                }
            }
        }

        if (!isStaticText && isToday(millis)){
            var rectf = RectF(xPos - dp16, height / 2 - dp16, xPos + dp16, height / 2 + dp16)
            canvas.drawRoundRect(rectf, RADIUS, RADIUS, mPaintBackgroundToday)
        }

        if (isStaticText){
            canvas.drawText(
                CalendarView_Kotlin.DAY_OF_WEEK?.get(dayOfWeek),
                //CalendarView.DAY_OF_WEEK[dayOfWeek],
                xPos.toFloat(),
                yPos.toFloat(),
                mPaint
            )
        }else{
            if (isToday(millis)){
                canvas.drawText(
                    calendar[Calendar.DATE].toString() + "",
                    xPos.toFloat(),
                    yPos.toFloat(),
                    mPaintTextWhite
                )
            }else{
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                    canvas.drawText(calendar[Calendar.DATE].toString() + "", xPos.toFloat(), yPos.toFloat(), mPaintTextSaturday)

                }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    canvas.drawText(calendar[Calendar.DATE].toString() + "", xPos.toFloat(), yPos.toFloat(), mPaintTextSunday)
                }else{
                    canvas.drawText(calendar[Calendar.DATE].toString() + "", xPos.toFloat(), yPos.toFloat(), mPaint)
                }
            }

        }

        if (!isStaticText && hasEvent){
            mPaintBackgroundEvent.color = resources.getColor(mColorEvent)

            var rectF = RectF((xPos - 5).toFloat(), (height / 2 + 20).toFloat(), (xPos + 5).toFloat(), (height / 2 + 30).toFloat())
            canvas.drawRoundRect(rectF, RADIUS, RADIUS, mPaintBackgroundEvent)
        }


    }

    fun isToday(millis: Long) : Boolean{
        var cal1 = Calendar.getInstance()
        return isSameDay(cal1.timeInMillis, millis)
    }

    fun setDate(millis: Long){
        this.millis = millis
        setTag(millis)
    }

    fun setDayOfWeek(dayOfWeek: Int){
        this.dayOfWeek = dayOfWeek
        isStaticText = true
    }

    fun setEvent(){
        hasEvent = true
    }

    fun isSameDay(millis1: Long, millis2: Long) : Boolean{
        var cal1 = Calendar.getInstance()
        var cal2 = Calendar.getInstance()
        cal1.timeInMillis = millis1
        cal2.timeInMillis = millis2
        return cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.MONTH] == cal2[Calendar.MONTH] && cal1[Calendar.DATE] == cal2[Calendar.DATE]
    }

    fun isStaticText() : Boolean{
        return isStaticText
    }


}