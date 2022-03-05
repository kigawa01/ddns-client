package net.kigawa.ddnsclient;

import net.kigawa.yamlutil.YamlData;

public class ConfigData extends YamlData {
    String zoneId;
    String id;
    String eMail;
    String key;
    String domain;
    boolean secret=false;
    String secretPath;

    public void setSecretPath(String secretPath) {
        this.secretPath = secretPath;
    }

    public String getSecretPath() {
        return secretPath;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    @Override
    public String getName() {
        return "config";
    }
}
