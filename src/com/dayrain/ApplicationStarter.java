package com.dayrain;

import com.dayrain.entity.Configuration;
import com.dayrain.entity.RequestType;
import com.dayrain.entity.Server;
import com.dayrain.entity.ServerConfig;
import com.dayrain.entity.ServerUrl;
import com.dayrain.views.HomePage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ApplicationStarter extends Application {
    Configuration configuration = configInit();
    public static void main(String[] args) {


//        List<ServerConfig> serverConfigs = configuration.getServerConfigs();
//        for (ServerConfig serverConfig : serverConfigs) {
//            Server server = new Server(serverConfig);
//            Thread thread = new Thread(server);
//            thread.start();
//        }
        launch(args);
    }

    private static Configuration configInit() {
        Configuration configuration = new Configuration();
        configuration.setServerConfigs(new ArrayList<ServerConfig>(){
            {
                add(new ServerConfig("测试", 8080, new ArrayList<ServerUrl>(){
                    {
                        add(new ServerUrl("登录", "/login", RequestType.GET, "{\n" +
                                "    \"username\": \"admin\",\n" +
                                "    \"password\": \"123\"\n" +
                                "}"));

                        add(new ServerUrl("登录", "/logout", RequestType.GET, "{\n" +
                                "    \"username\": \"admin\",\n" +
                                "    \"password\": \"123\"\n" +
                                "}"));
                    }
                }));
            }
        });
        configuration.setWidth(1200);
        configuration.setHeight(800);

        return configuration;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new HomePage(primaryStage, configuration).start();
    }
}
