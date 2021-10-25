package com.dayrain;

import com.dayrain.entity.Configuration;
import com.dayrain.entity.ConfigurationHolder;
import com.dayrain.entity.RequestType;
import com.dayrain.entity.Server;
import com.dayrain.entity.ServerConfig;
import com.dayrain.entity.ServerUrl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ApplicationStarter extends Application {

    public static void main(String[] args) {
        ConfigurationHolder.reload(get());
        Configuration configuration = ConfigurationHolder.get();
        List<ServerConfig> serverConfigs = configuration.getServerConfigs();
        for (ServerConfig serverConfig : serverConfigs) {
            Server server = new Server(serverConfig);
            Thread thread = new Thread(server);
            thread.start();
        }
//        launch(args);
    }

    private static Configuration get() {
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

        return configuration;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
