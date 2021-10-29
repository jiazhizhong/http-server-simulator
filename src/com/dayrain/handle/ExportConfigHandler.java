package com.dayrain.handle;

import com.dayrain.component.Configuration;
import com.dayrain.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * 导出配置文件
 *
 * @author peng
 * @date 2021/10/28
 */
public class ExportConfigHandler implements EventHandler<ActionEvent> {

    private final Stage primaryStage;

    private final Configuration configuration;

    public ExportConfigHandler(Stage primaryStage, Configuration configuration) {
        this.primaryStage = primaryStage;
        this.configuration = configuration;
    }

    @Override
    public void handle(ActionEvent event) {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        FileChooser fileChooser = new FileChooser();
        String projectName = configuration.getProjectName();
        if (projectName == null) {
            projectName = "";
        }
        projectName += "server";
        fileChooser.setTitle("导出配置");
        fileChooser.setInitialFileName(projectName + ".json");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            FileUtils.saveConfig(configuration, file);
        }
    }
}
