package net.kigawa.net.ddnsclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Cloudflare {
    private final String zoneId;
    private final String id;
    private final String eMail;
    private final String key;

    public Cloudflare(String zoneId, String id, String eMail, String key) {
        this.zoneId = zoneId;
        this.id = id;
        this.eMail = eMail;
        this.key = key;
    }

    public void updateIp(String domain, String ip) {
        DDNSClient.logger.info("update ip...");
        try {
            URL url = new URL("https://api.cloudflare.com/client/v4/zones/" + zoneId + "/dns_records/" + id);
            HttpURLConnection urCon = (HttpURLConnection) url.openConnection();
            urCon.setRequestMethod("PUT");
            urCon.setDoOutput(true);
            urCon.setRequestProperty("X-Auth-Email", eMail);
            urCon.setRequestProperty("X-Auth-Key", key);
            urCon.setRequestProperty("Content-Type", "application/json");

            DDNSClient.logger.info("connecting...");
            urCon.connect();

            PrintStream ps = new PrintStream(urCon.getOutputStream());
            ps.print("{\"type\":\"A\",\"name\":\"" + domain + "\",\"content\":\"" + ip + "\",\"ttl\":3600,\"proxied\":false}");
            ps.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(urCon.getInputStream()));
            String out;
            while ((out = br.readLine()) != null) {
                DDNSClient.logger.info("out");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
