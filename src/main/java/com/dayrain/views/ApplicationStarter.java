package com.dayrain.views;

import com.dayrain.component.ConfigHolder;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 启动入口
 * @author peng
 * @date 2021/11/11
 */
public class ApplicationStarter extends Application {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            ConfigHolder.init();
            ViewHolder.setPrimaryStage(primaryStage);
            new HomeView().start();
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
