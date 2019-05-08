package edu.ncu.zww.app.wei_im.utils;

import com.stfalcon.chatkit.utils.DateFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// 消息列表时间转换
public class MsgDataFormatter implements DateFormatter.Formatter {

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        } else if (DateFormatter.isYesterday(date)) {
            return "昨天";
        } else if (DateFormatter.isCurrentYear(date)) {
            DateFormat dateFormat = new SimpleDateFormat("MM-dd");
            return dateFormat.format(date);
        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        }
    }
}
