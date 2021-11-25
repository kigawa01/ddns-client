package net.kigawa.net.ddnsclient;

import java.io.File;
import java.nio.file.Paths;
import java.util.Calendar;

public class Util {
    public static File getAbsolutFile() {
        return Paths.get("").toAbsolutePath().toFile();
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer sb = new StringBuffer().append(calendar.get(Calendar.YEAR));
        sb.append("-").append(calendar.get(Calendar.MONTH));
        sb.append("-").append(calendar.get(Calendar.DAY_OF_MONTH));
        return sb.toString();
    }

    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer sb = new StringBuffer(calendar.get(Calendar.HOUR_OF_DAY));
        sb.append("-").append(calendar.get(Calendar.MINUTE));
        sb.append("-").append(calendar.get(Calendar.SECOND));
        return sb.toString();
    }
}
