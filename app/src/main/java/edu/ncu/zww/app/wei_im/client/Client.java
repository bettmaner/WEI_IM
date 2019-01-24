package edu.ncu.zww.app.wei_im.client;

import java.net.Socket;

public class Client {
    private Socket client;
    private String ip;
    private int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}
