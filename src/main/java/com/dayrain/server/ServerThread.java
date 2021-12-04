package com.dayrain.server;

import com.dayrain.component.Server;
import com.dayrain.component.ServerUrl;

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

    public void removeContext(String url) {
        server.removeContext(url);
    }

    public void replaceUrl(String beforeUrl, ServerUrl serverUrl) {
        removeContext(beforeUrl);
        addContext(serverUrl);
    }
}
