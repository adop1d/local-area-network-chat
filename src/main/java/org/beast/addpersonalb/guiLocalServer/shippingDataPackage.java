package org.beast.addpersonalb.guiLocalServer;

import java.io.Serializable;
import java.util.ArrayList;

public class shippingDataPackage implements Serializable {
    private String userNick,ip, message;
    private ArrayList<String> Ips;
    private String clientId;

    public shippingDataPackage() {
        this.Ips = new ArrayList<>(); // Inicializa lista vacía
    }
    public shippingDataPackage(String userNick, String ip, String message) {
        this.userNick = userNick;
        this.ip = ip;
        this.message = message;
        this.Ips = new ArrayList<>(); // Evita NullPointerException
    }

    public String getUserNick() { return userNick; }
    public void setUserNick(String userNick) { this.userNick = userNick; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public ArrayList<String> getIps() { return Ips; }
    public void setIps(ArrayList<String> ips) { this.Ips = ips; }

    public String getNick() { return userNick; }
    public void setNick(String nick) { this.userNick = nick; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}