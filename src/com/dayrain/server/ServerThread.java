package com.dayrain.server;

import com.dayrain.entity.Server;
import com.dayrain.entity.ServerUrl;

public class ServerThread extends Thread {
    private final Server server;

    public ServerThread(Server server) {
        super(server);
        this.server = server;
    }

    public void stopServer() {
        server.stop();
    }

    public void addContext(ServerUrl serverUrl) {
        server.addContext(serverUrl);
    }
}
