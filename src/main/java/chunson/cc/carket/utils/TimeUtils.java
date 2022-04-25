package chunson.cc.carket.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils
{
    public static String humanTime(String time)
    {
        String str;
        Date date = null;
//        long now = Calendar.getInstance().getTimeInMillis();
        Calendar here = Calendar.getInstance();
        Calendar there = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            date = sdf.parse(time);
            there.setTime(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        if (here.get(Calendar.YEAR) > there.get(Calendar.YEAR))
        {
            str = time;
        }
        else if (here.get(Calendar.DATE) > there.get(Calendar.DATE)+1)
        {
            str = there.get(Calendar.MONTH) + "月" + there.get(Calendar.DATE) + "日 " + there.get(Calendar.HOUR_OF_DAY) + ":" + there.get(Calendar.MINUTE) + ":" + there.get(Calendar.SECOND);
        }
        else if (here.get(Calendar.DATE) > there.get(Calendar.DATE))
        {
            str = "昨天 " + there.get(Calendar.HOUR) + ":" + there.get(Calendar.MINUTE) + ":" + there.get(Calendar.SECOND);
        }
        else
        {
            str = "今天 " + there.get(Calendar.HOUR) + ":" + there.get(Calendar.MINUTE) + ":" + there.get(Calendar.SECOND);
        }


//        assert date != null;
//        long delTime = (now - date.getTime()) / 1000;
//        if (delTime > 7 * 24 * 60 * 60)
//        {
//            return time;
//        } else if (delTime > 2 * 24 * 60 * 60)
//        {
//            str = (int) (delTime / (24 * 60 * 60)) + "天前";
//        } else if (delTime > 24 * 60 * 60)
//        {
//            str = "昨天";
//        } else if (delTime > 60 * 60)
//        {
//            str = (int) (delTime / (60 * 60)) + "小时前";
//        } else if (delTime > 60)
//        {
//            str = (int) (delTime / (60)) + "分钟前";
//        } else if (delTime > 1)
//        {
//            str = delTime + "秒前";
//        } else
//        {
//            str = "1秒前";
//        }

        return str;
    }
}
