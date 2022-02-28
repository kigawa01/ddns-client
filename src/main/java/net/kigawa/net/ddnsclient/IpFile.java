package net.kigawa.net.ddnsclient;

import java.io.*;

public class IpFile {
    private final File ownIp = DDNSClient.ownIp;
    private final DDNSClient ddnsClient;

    public IpFile(DDNSClient ddnsClient) {
        this.ddnsClient = ddnsClient;
        createFile();
    }

    public void createFile() {
        try {
            if (!ownIp.exists()) ownIp.createNewFile();

            FileWriter fileWriter = new FileWriter(ownIp);
            fileWriter.write(ddnsClient.getIp());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ownIp));
            String ip = br.readLine();
            br.close();
            return ip;
        } catch (IOException e) {
            DDNSClient.logger.warning(e);
        }
        return null;
    }

    public void writeIp(String ip) {
        try {
            FileWriter fileWriter = new FileWriter(ownIp);
            fileWriter.write(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
