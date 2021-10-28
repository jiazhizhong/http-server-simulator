package com.dayrain.component;

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

    private HttpServer httpServer;

    public Server(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }


    @Override
    public void run() {
        try {
            this.httpServer = HttpServer.create(new InetSocketAddress(serverConfig.getPort()), 0);
            httpServer.setExecutor(Executors.newCachedThreadPool());
            for (ServerUrl serverUrl : serverConfig.getServerUrls()) {
                addContext(serverUrl);
            }
            httpServer.start();
            System.out.println("【" +serverConfig.getServerName() + "】服务已开启...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if(httpServer != null) {
            System.out.println("【" + serverConfig.getServerName() + "】服务已关闭...");
            httpServer.stop(0);
        }
    }

    public void addContext(ServerUrl serverUrl) {
        httpServer.createContext(serverUrl.getUrl(), new RequestHandler(serverUrl));
    }
}
