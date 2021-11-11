package com.dayrain.views;

import javafx.stage.Stage;
/**
 * 主要视图的句柄
 * @author peng
 * @date 2021/11/11
 */
public class ViewHolder {

    private static Stage primaryStage;

    private static LogArea logArea;

    private static ServerContainer serverContainer;

    private static HomeView homeView;

    static void setPrimaryStage(Stage primaryStage) {
        ViewHolder.primaryStage = primaryStage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    static void setLogArea(LogArea logArea) {
        ViewHolder.logArea = logArea;
    }

    public static LogArea getLogArea() {
        return logArea;
    }

    public static ServerContainer getServerContainer() {
        return serverContainer;
    }

    static void setServerContainer(ServerContainer serverContainer) {
        ViewHolder.serverContainer = serverContainer;
    }

    static void setHomePage(HomeView homeView) {
        ViewHolder.homeView = homeView;
    }

    public static HomeView getHomeView() {
        return ViewHolder.homeView;
    }
}
