package cookmap.cookandroid.hw.tdcalendar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import java.util.Calendar;

import cookmap.cookandroid.hw.tdcalendar.R;


public class CalendarItemView extends View {

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaintTextWhite = new Paint(Paint.ANTI_ALIAS_FLAG);

    Paint mPaintTextSunday = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaintTextSaturday = new Paint(Paint.ANTI_ALIAS_FLAG);

    Paint mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaintBackgroundToday = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaintBackgroundEvent = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int dayOfWeek = -1;
    private boolean isStaticText = false;
    private long millis;
    private Rect rect;
    private boolean isTouchMode;
    private int dp11;
    private int dp16;
    private boolean hasEvent = false;
    //private int[] mColorEvents;
    private int mColorEvents = R.color.colorYellow;
    private final float RADIUS = 100f;

    public CalendarItemView(Context context) {
        super(context);
        initialize();
    }

    public CalendarItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        dp11 = (int) Common.dp2px(getContext(), 11);
        dp16 = (int) Common.dp2px(getContext(), 16);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(dp11);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaintTextWhite.setColor(Color.WHITE);
        mPaintTextWhite.setTextSize(dp11);
        mPaintTextWhite.setTextAlign(Paint.Align.CENTER);

        mPaintTextSunday.setColor(Color.RED);
        mPaintTextSunday.setTextSize(dp11);
        mPaintTextSunday.setTextAlign(Paint.Align.CENTER);

        mPaintTextSaturday.setColor(Color.BLUE);
        mPaintTextSaturday.setTextSize(dp11);
        mPaintTextSaturday.setTextAlign(Paint.Align.CENTER);


        mPaintBackground.setColor(ContextCompat.getColor(getContext(), R.color.selectedDay));
        mPaintBackgroundToday.setColor(ContextCompat.getColor(getContext(), R.color.today));
        mPaintBackgroundEvent.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        setClickable(true);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                Log.d("hatti.onTouchEvent", event.getAction() + "");
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        isTouchMode = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isTouchMode) {
                            ((CalendarView) getParent()).setCurrentSelectedView(v);
                            isTouchMode = false;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        isTouchMode = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                            isTouchMode = false;
                            return true;
                        }
                        break;
                }
                return false;
            }
        });
        setPadding(30, 0, 30, 0);
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        /*int count_memo = Database_Room.getInstance(getContext()).getDao().getMemoCount(
                new Convert_Date().Convert_Date(calendar.getTimeInMillis()));
        Log.d("convert!!!", new Convert_Date().Convert_Date(calendar.getTimeInMillis()));
        if (count_memo > 0){
            setEvent();
        }*/

        CalendarView calendarView = (CalendarView) getParent();
        Log.d("OnDraw!!!", String.valueOf(calendarView.getParent()));
        if (calendarView.getParent() instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) calendarView.getParent();
            CalendarItemView tagView = (CalendarItemView) parent.getTag();

            if (!isStaticText && tagView != null && tagView.getTag() != null && tagView.getTag() instanceof Long) {
                long millis = (long) tagView.getTag();
                if (isSameDay(millis, this.millis)) {
                    RectF rectF = new RectF(xPos - dp16, getHeight() / 2 - dp16, xPos + dp16, getHeight() / 2 + dp16);
                    canvas.drawRoundRect(rectF, RADIUS, RADIUS, mPaintBackground);
                }
            }
        }

        if (!isStaticText && isToday(millis)) {
            RectF rectF = new RectF(xPos - dp16, getHeight() / 2 - dp16, xPos + dp16, getHeight() / 2 + dp16);
            canvas.drawRoundRect(rectF, RADIUS, RADIUS, mPaintBackgroundToday);
        }

        if (isStaticText) {
            // 요일 표시
            canvas.drawText(CalendarView.DAY_OF_WEEK[dayOfWeek], xPos, yPos, mPaint);
        } else {
            // 날짜 표시
            if (isToday(millis)) {
                canvas.drawText(calendar.get(Calendar.DATE) + "", xPos, yPos, mPaintTextWhite);
                //((MultiCalendarActivity)getContext()).initData(millis);
            } else {
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                    Log.d("Saturday", "SATURDAY");
                    canvas.drawText(calendar.get(Calendar.DATE)+ "", xPos, yPos, mPaintTextSaturday);
                }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    Log.d("SUNDAY", "SUNDAY");
                    canvas.drawText(calendar.get(Calendar.DATE)+ "", xPos, yPos, mPaintTextSunday);
                }else{
                    canvas.drawText(calendar.get(Calendar.DATE) + "", xPos, yPos, mPaint);
                }

            }
        }

        if (!isStaticText && hasEvent) {
            //mPaintBackgroundEvent.setColor(getResources().getColor(mColorEvents[0]));
            mPaintBackgroundEvent.setColor(getResources().getColor(mColorEvents));
            //ContextCompat.getColor(getContext(), mColorEvents);
            RectF rectF = new RectF(xPos - 5, getHeight() / 2 + 20, xPos + 5, getHeight() / 2 + 30);
            //canvas.drawRoundRect(rectF, RADIUS, RADIUS, mPaintBackgroundToday);
            canvas.drawRoundRect(rectF, RADIUS, RADIUS, mPaintBackgroundEvent);
        }

    }

    private boolean isToday(long millis) {
        Calendar cal1 = Calendar.getInstance();
        return isSameDay(cal1.getTimeInMillis(), millis);

    }

    public void setDate(long millis) {
        this.millis = millis;
        setTag(millis);
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        isStaticText = true;
    }

    public void setEvent() {
        hasEvent = true;
        //mColorEvents = R.color.colorYellow;
    }

    /*public void setEvent(int... resid) {
        hasEvent = true;
        mColorEvents = resid;
    }*/

    public boolean isSameDay(long millis1, long millis2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(millis1);
        cal2.setTimeInMillis(millis2);
        Log.d("hatti.calendar", "" + cal1.get(Calendar.YEAR) + "" + cal1.get(Calendar.MONTH) + "" + cal1.get(Calendar.DATE) + " VS " +
                cal2.get(Calendar.YEAR) + "" + cal2.get(Calendar.MONTH) + "" + cal2.get(Calendar.DATE));
        return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE));
    }

    public boolean isStaticText() {
        return isStaticText;
    }
}
