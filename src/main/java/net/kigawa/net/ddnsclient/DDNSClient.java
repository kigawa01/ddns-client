package net.kigawa.net.ddnsclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class DDNSClient {
    public static final File current = Paths.get("").toAbsolutePath().toFile();
    public static final File ownIp = new File(current, "ownIp");
    public static DDNSClient ddnsClient;
    private final IpFile ipFile;
    private URL checkIp;

    public DDNSClient() {
        ipFile = new IpFile(this);
    }

    public static void main(String[] args) {
        ddnsClient = new DDNSClient();

    }

    private void newURL(int count) {
        if (count > 5) return;
        try {
            checkIp = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            newURL(count + 1);
        }
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
}
