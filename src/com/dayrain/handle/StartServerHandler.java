package com.dayrain.handle;

import com.dayrain.entity.Server;
import com.dayrain.entity.ServerConfig;
import com.dayrain.server.ServerThread;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
/**
 * 服务启动与关闭控制器
 * @author peng
 * @date 2021/10/27
 */
public class StartServerHandler implements EventHandler<ActionEvent> {

    private final Button openButton;

    private final Circle statusCircle;

    private final ServerConfig serverConfig;

    private final HashMap<String, ServerThread> threadMap;

    public StartServerHandler(Button openButton, Circle statusCircle, ServerConfig serverConfig, HashMap<String, ServerThread> threadMap) {
        this.openButton = openButton;
        this.statusCircle = statusCircle;
        this.serverConfig = serverConfig;
        this.threadMap = threadMap;
    }

    @Override
    public void handle(ActionEvent event) {
        String serverName = serverConfig.getServerName();
        if(threadMap.containsKey(serverName)) {
            ServerThread serverThread = threadMap.get(serverName);
            if(serverThread != null) {
                serverThread.stopServer();
            }
            threadMap.remove(serverName);
            openButton.setText("开启服务");
            statusCircle.setFill(Color.RED);
        }else {
            ServerThread serverThread = new ServerThread(new Server(serverConfig));
            serverThread.start();
            threadMap.put(serverName, serverThread);
            openButton.setText("关闭服务");
            statusCircle.setFill(Color.GREEN);
        }
    }
}
