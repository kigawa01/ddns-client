package net.kigawa.net.ddnsclient;

import net.kigawa.kutil.kutil.app.Application;
import net.kigawa.kutil.kutil.file.FileUtil;
import net.kigawa.kutil.kutil.thread.ThreadExecutors;
import net.kigawa.kutil.log.log.Logger;
import net.kigawa.kutil.terminal.Terminal;
import net.kigawa.yamlutil.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;

public class DDNSClient extends Application {
    public static final File ownIp = FileUtil.getRelativeFile("ownIp");
    public static final File config = FileUtil.getRelativeFile("config.yml");
    public static final File logDir = FileUtil.getRelativeFile("logs");
    public static Level log = Level.INFO;
    public static boolean jline = false;
    public static DDNSClient ddnsClient;
    public static Logger logger;

    private final IpFile ipFile;
    private final IpTimer ipTimer;
    private final Yaml yaml;
    private ConfigData data;
    private URL checkIp;
    private Cloudflare cloudflare;

    public DDNSClient() {
        logger = new Logger(DDNSClient.class.getName(), null, Level.INFO, logDir);
        addModule(logger);
        addModule(new ThreadExecutors(logger));
        addModule(new Terminal(jline, logger));

        ipFile = new IpFile(this);
        ipTimer = new IpTimer(ipFile, this);
        yaml = new Yaml(Util.getAbsolutFile(), logger);

        Terminal.terminal.addOnRead(this::command);

        enable();
    }

    public static void main(String[] args) {
        for (String arg : args) {

            if (arg.startsWith("--")) {
                switch (arg.substring(2)) {
                    case "no-jline":
                        jline = false;
                        break;
                }
                continue;
            }

            if (arg.startsWith("-")) {
                if (arg.contains("d")) log = Level.FINE;
                continue;
            }
        }


        ddnsClient = new DDNSClient();
        Scanner scanner = new Scanner(System.in);
    }

    public void command(String str) {

        if (str.equals("stop") | str.equals("end")) {
            ddnsClient.disable();
            return;
        }
        if (str.equals("test")) {
            ddnsClient.getCloudflare().updateIp(ddnsClient.getData().getDomain(), ddnsClient.getIp());
        }
    }

    public Cloudflare getCloudflare() {
        return cloudflare;
    }

    public ConfigData getData() {
        return data;
    }

    public String getIp() {
        try {
            newURL(0);
            BufferedReader br = new BufferedReader(new InputStreamReader(checkIp.openStream()));
            String ip = br.readLine();
            br.close();
            return ip;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newURL(int count) {
        if (count > 5) return;
        try {
            checkIp = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            newURL(count + 1);
        }
    }

    @Override
    protected void onDisable() {
        ipTimer.end();
    }

    @Override
    protected void onEnable() {
        logger.info("start DDNSClient");
        logger.info("load data...");
        data = yaml.load(ConfigData.class, config);
        if (data == null) {
            data = new ConfigData();
            yaml.save(data);
        }
        logger.info("new cloudflare...");
        cloudflare = new Cloudflare(data.getZoneId(), data.getId(), data.getEMail(), data.getKey());

        logger.info("start timer");
        ipTimer.start();
    }

    public void updateIp() {
        logger.info("update ip...");

    }
}
