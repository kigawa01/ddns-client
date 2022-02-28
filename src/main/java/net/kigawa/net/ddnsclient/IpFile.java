package net.kigawa.net.ddnsclient;

import java.io.*;

public class IpFile {
    private final File ownIp = DDNSClient.ownIp;
    private final DDNSClient ddnsClient;

    public IpFile(DDNSClient ddnsClient) {
        this.ddnsClient = ddnsClient;
    }

    public String readFile() {
        try {
            if (!ownIp.exists()) {
                writeIp("0");
            }

            BufferedReader br = new BufferedReader(new FileReader(ownIp));
            String ip = br.readLine();
            DDNSClient.logger.fine("on readFile " + ip);
            br.close();
            return ip;
        } catch (IOException e) {
            DDNSClient.logger.warning(e);
        }
        return null;
    }

    public void writeIp(String ip) {
        try {
            if (!ownIp.exists()) ownIp.createNewFile();

            FileWriter fileWriter = new FileWriter(ownIp);
            fileWriter.write(ip);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
