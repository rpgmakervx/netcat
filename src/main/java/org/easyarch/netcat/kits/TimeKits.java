package org.easyarch.netcat.kits;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by xingtianyu on 17-3-10
 * 下午12:07
 * description:
 */

public class TimeKits {

    public static Date plus(long time) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MILLISECOND, (int) time);
        return c.getTime();
    }

    public static Date plusSeconds(int second) {
        return plus(second * 1000);
    }

    public static String getGMTTime(Date date){
        Calendar cd = Calendar.getInstance();
        cd.setTimeInMillis(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(cd.getTime());
    }
}
