package cookmap.cookandroid.hw.tdcalendar.widget;

import android.content.Context;

public class Common {
    public static float dp2px(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
