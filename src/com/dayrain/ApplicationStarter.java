package com.dayrain;

import com.dayrain.entity.Configuration;
import com.dayrain.entity.RequestType;
import com.dayrain.entity.ServerConfig;
import com.dayrain.entity.ServerUrl;
import com.dayrain.utils.FileUtils;
import com.dayrain.views.HomePage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class ApplicationStarter extends Application {

    public static void main(String[] args) {
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

                        add(new ServerUrl("登出", "/logout", RequestType.GET, "{\n" +
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
        new HomePage(primaryStage).start();
    }
}
