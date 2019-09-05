package de.sprax2013.advanced_dev_utils.time_and_date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnixMillisUtils {
    /**
     * @return Value of
     * <code>UnixMillisUtils.unixMillisToString(unixMillis, "dd.MM.yyyy HH:mm")</code>
     *
     * @see #unixMillisToString(Long, String)
     */
    public static String unixMillisToString(Long unixMillis) {
        return unixMillisToString(unixMillis, "dd.MM.yyyy HH:mm");
    }

    /**
     * Creates a human-readable String from <b>unixMillis</b>
     *
     * @param unixMillis The milliseconds since January 1, 1970, 00:00:00 GMT
     * @param format     {@link SimpleDateFormat}
     *
     * @throws NullPointerException     When <b>format</b> is null
     * @throws IllegalArgumentException When <b>format</b> is invalid
     * @see #unixMillisToString(Long)
     */
    public static String unixMillisToString(Long unixMillis, String format) {
        return new SimpleDateFormat(format).format(new Date(unixMillis));
    }
}