package org.beast.addpersonalb.guiLocalServer;

import java.io.Serializable;
import java.util.ArrayList;

public class shippingDataPackage implements Serializable {
    private static String userNick,ip, message;
    private ArrayList<String> Ips;
    private String clientId;

    public static String getUserNick() {
        return userNick;
    }

    public static void setUserNick(String userNick) {
        shippingDataPackage.userNick = userNick;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ArrayList<String> getIps() {return Ips;}

    public void setIps(ArrayList<String> ips) {Ips = ips;}
    public shippingDataPackage() {
        this.Ips = new ArrayList<>(); // Initialize to empty ArrayList
    }

    public String getNick() {return userNick;}

    public static void setNick(String nick) {userNick = nick;}

    public String getIp() {return ip;}

    public void setIp(String ip) {
        shippingDataPackage.ip = ip;}

    public String getMessage() {return message;}

    public void setMessage(String message) {
        shippingDataPackage.message = message;}
}
