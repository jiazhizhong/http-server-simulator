package com.dayrain.handle;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.Configuration;
import com.dayrain.component.ServerConfig;
import com.dayrain.views.HomePage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DeleteServerHandler implements EventHandler<ActionEvent> {

    private final ServerConfig serverConfig;

    private final Configuration configuration;

    private final HomePage homePage;

    public DeleteServerHandler(ServerConfig serverConfig, Configuration configuration, HomePage homePage) {
        this.serverConfig = serverConfig;
        this.configuration = configuration;
        this.homePage = homePage;
    }

    @Override
    public void handle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setGraphic(new ImageView(homePage.getIcon()));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(homePage.getIcon());
        alert.setHeaderText("是否确定删除该服务？");
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setOnAction(event1 -> {
            configuration.getServerConfigs().remove(serverConfig);
            homePage.refreshServerContainer();
            ConfigHolder.save();
        });
        alert.show();
    }
}
