package com.dayrain.handle;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.Configuration;
import com.dayrain.utils.FileUtils;
import com.dayrain.views.HomePage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * 导入配置文件
 * @author peng
 * @date 2021/10/28
 */
public class ImportConfigHandler implements EventHandler<ActionEvent> {

    private final Stage primaryStage;

    private final HomePage homePage;

    public ImportConfigHandler(Stage primaryStage, HomePage homePage) {
        this.primaryStage = primaryStage;
        this.homePage = homePage;
    }

    @Override
    public void handle(ActionEvent event) {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择一个文件");
        //过滤器
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("只能导入json文件", "*.json"));
        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            Configuration loadConfig = FileUtils.load(file);
            ConfigHolder.replace(loadConfig);
            FileUtils.saveConfig(loadConfig);
            homePage.replaceConfig(loadConfig);
            homePage.restart();
        }
    }
}
