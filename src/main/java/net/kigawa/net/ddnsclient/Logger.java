package net.kigawa.net.ddnsclient;

import java.io.*;

public class Logger {
    private File dir;
    private File log;
    private boolean isOut;
    private boolean isLog;
    private BufferedWriter bw;

    public Logger(boolean out, boolean log) {
        this(Util.getAbsolutFile(), out, log);
    }

    public Logger(File dir, boolean out, boolean log) {
        this.dir = dir;
        isLog = log;
        isOut = out;

        if (!log) return;
        this.log = createLogFile(dir);
        try {
            bw = new BufferedWriter(new FileWriter(this.log));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createLogFile(File dir) {
        int num = -1;
        File log;
        do {
            StringBuffer name = new StringBuffer(Util.getDate());
            if (num < 0) name.append("-").append(num);
            log = new File(dir, name.toString());
            num++;
        } while (log.exists());
        return log;
    }

    public void writeLine(Object o) {
        try {
            bw.write(o.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(Object o) {
        StringBuffer sb = new StringBuffer("[").append("INFO").append("] ");
        sb.append(o);
        log(sb);
    }

    public void log(Object o) {
        StringBuffer sb = new StringBuffer(Util.getTime());
        sb.append(" | ").append(o);
        if (isOut) System.out.println(sb);
        if (isLog) writeLine(o);
    }
}
