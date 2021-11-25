package net.kigawa.net.ddnsclient;

import net.kigawa.yamlutil.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

public class DDNSClient {
    public static final File current = Paths.get("").toAbsolutePath().toFile();
    public static final File ownIp = new File(current, "ownIp");
    public static final File config = new File(current, "config.yml");
    public static DDNSClient ddnsClient;
    public static Logger logger;
    public static boolean isEnd;
    private final IpFile ipFile;
    private final IpTimer ipTimer;
    private final Yaml yaml;
    private ConfigData data;
    private URL checkIp;
    private Cloudflare cloudflare;

    public DDNSClient() {
        ipFile = new IpFile(this);
        ipTimer = new IpTimer(ipFile, this);
        yaml = new Yaml(Util.getAbsolutFile(), DDNSClient.logger);
        data = yaml.load(ConfigData.class, config);
        if (data == null) {
            data = new ConfigData();
            yaml.save(data);
        }
        cloudflare = new Cloudflare(data.getZoneId(), data.getId(), data.getEMail(), data.getKey());
    }

    public static void main(String[] args) {
        boolean log = true;
        boolean debug = false;
        int index = 0;
        String arg = args[index];
        if (arg.startsWith("-")) {
            if (arg.contains("t")) log = false;
            if (arg.contains("d")) debug = true;
        }
        logger = new Logger(log, debug);
        ddnsClient = new DDNSClient();

        Scanner scanner = new Scanner(System.in);
        while (isEnd) {
            scanner(scanner);
        }
    }

    public static void scanner(Scanner scanner) {
        String command = scanner.next();
        if (command.equals("stop") | command.equals("end")) {
            isEnd = true;
            ddnsClient.end();
        }
    }

    public void end() {
        ipTimer.end();
    }

    public void newURL(int count) {
        logger.info("create url...");
        if (count > 5) return;
        try {
            checkIp = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            newURL(count + 1);
        }
    }

    public ConfigData getData() {
        return data;
    }

    public Cloudflare getCloudflare() {
        return cloudflare;
    }

    public String getIp() {
        logger.info("get own ip...");
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

    public void updateIp(){
        logger.info("update ip...");

    }
}
