package net.kigawa.net.ddnsclient;

import net.kigawa.kutil.kutil.interfaces.Module;
import net.kigawa.kutil.thread.ThreadExecutor;

import static java.lang.Thread.sleep;

public class IpTimer implements Module {
    private final IpFile ipFile;
    private final DDNSClient ddnsClient;
    private boolean isRun = true;

    public IpTimer(IpFile ipFile, DDNSClient ddnsClient) {
        this.ipFile = ipFile;
        this.ddnsClient = ddnsClient;
    }

    @Override
    public void enable() {
        DDNSClient.logger.info("enable ip timer");
        ThreadExecutor.getInstance(DDNSClient.class.getName()).execute(this::run);
    }

    @Override
    public void disable() {
        DDNSClient.logger.info("disable ip timer");
        end();
    }

    public void end() {
        isRun = false;
    }

    public void run() {
        DDNSClient.logger.fine("on run");
        if (!isRun) {
            DDNSClient.logger.fine("no run");
            return;
        }
        try {
            sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ip = ddnsClient.getIp();
        if (ipFile == null) {
            DDNSClient.logger.fine("ip file is null");
            run();
            return;
        }
        final String readIp = ipFile.readFile();
        if (readIp == null) {
            DDNSClient.logger.fine("read ip is null");
            run();
            return;
        }
        DDNSClient.logger.fine("ip>" + ip, "readIp>" + readIp);
        if (readIp.equals(ip)) {
            DDNSClient.logger.fine("match ip");
            run();
            return;
        }
        DDNSClient.logger.fine("no match ip");
        ddnsClient.getCloudflare().updateIp(ddnsClient.getData().getDomain(), ip);
        ipFile.writeIp(ip);
        run();
    }
}
