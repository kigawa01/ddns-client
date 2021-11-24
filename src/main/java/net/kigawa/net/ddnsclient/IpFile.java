package net.kigawa.net.ddnsclient;

import java.io.*;

public class IpFile {
    private final File ownIp = DDNSClient.ownIp;
    private final DDNSClient ddnsClient;

    public IpFile(DDNSClient ddnsClient) {
        this.ddnsClient = ddnsClient;
        createFile();
    }

    public String readFile() {
        DDNSClient.logger.info("read file...");
        try {
            BufferedReader br = new BufferedReader(new FileReader(ownIp));
            String ip = br.readLine();
            br.close();
            return ip;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createFile() {
        if (ownIp.exists()) return;
        try {
            ownIp.createNewFile();

            FileWriter fileWriter = new FileWriter(ownIp);
            fileWriter.write(ddnsClient.getIp());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
