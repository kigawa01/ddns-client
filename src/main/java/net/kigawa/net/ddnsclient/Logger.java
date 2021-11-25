package net.kigawa.net.ddnsclient;

import java.io.*;

public class Logger implements net.kigawa.util.Logger {
    private File log;
    private final boolean isLog;
    private final boolean isDebug;
    private BufferedWriter bw;

    public Logger( boolean log, boolean debug) {
        this(Util.getAbsolutFile(), log, debug);
    }

    public Logger(File dir,  boolean log, boolean debug) {
        isLog = log;
        isDebug = debug;

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

    public void debug(Object o) {
        if (!isDebug) return;
        StringBuffer sb = new StringBuffer("[DEBUG] ").append(o);
        log(o);
    }

    public void info(Object o) {
        StringBuffer sb = new StringBuffer("[INFO] ");
        sb.append(o);
        log(sb);
    }

    public void log(Object o) {
        StringBuffer sb = new StringBuffer(Util.getTime());
        sb.append(" | ").append(o);
        System.out.println(sb);
        if (isLog) writeLine(o);
    }

    @Override
    public void logger(String message) {
        debug(message);
    }
}
