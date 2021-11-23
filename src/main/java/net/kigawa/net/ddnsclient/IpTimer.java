package net.kigawa.net.ddnsclient;

public class IpTimer extends Thread {
    private final IpFile ipFile;
    private final DDNSClient ddnsClient;
    private boolean isRun = true;

    public IpTimer(IpFile ipFile, DDNSClient ddnsClient) {
        this.ipFile = ipFile;
        this.ddnsClient = ddnsClient;
    }


    public void setRun(boolean run) {
        this.isRun = run;
    }

    @Override
    public void run() {
        if (!isRun) return;
        try {
            sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ip = ddnsClient.getIp();
        if (ipFile.readFile().equals(ip)) run();

    }
}
