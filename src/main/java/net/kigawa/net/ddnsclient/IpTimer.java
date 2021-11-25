package net.kigawa.net.ddnsclient;

public class IpTimer extends Thread {
    private final IpFile ipFile;
    private final DDNSClient ddnsClient;
    private boolean isRun = true;

    public IpTimer(IpFile ipFile, DDNSClient ddnsClient) {
        this.ipFile = ipFile;
        this.ddnsClient = ddnsClient;
    }

    public void end() {
        isRun = false;
    }

    public void setRun(boolean run) {
        this.isRun = run;
    }

    @Override
    public void run() {
        if (!isRun) {
            return;
        }
        try {
            sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ip = ddnsClient.getIp();
        if (ipFile == null) {
            run();
            return;
        }
        String readIp = ipFile.readFile();
        if (readIp == null) {
            run();
            return;
        }
        if (readIp.equals(ip)) {
            run();
            return;
        }
        ddnsClient.getCloudflare().updateIp(ddnsClient.getData().getDomain(), ip);
        ipFile.writeIp(ip);
        run();
    }
}
