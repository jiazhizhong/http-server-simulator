package com.dayrain.entity;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * 服务
 * @author peng
 * @date 2021/10/25
 */
public class Server implements Runnable {

    private final ServerConfig serverConfig;

    public Server(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public void run() {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(serverConfig.getPort()), 0);
            httpServer.setExecutor(Executors.newCachedThreadPool());
            for (ServerUrl serverUrl : serverConfig.getServerUrls()) {
                httpServer.createContext(serverUrl.getUrl(), new RequestHandler(serverUrl));
            }
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
