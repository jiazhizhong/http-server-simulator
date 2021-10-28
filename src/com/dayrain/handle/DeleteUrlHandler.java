package com.dayrain.handle;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.ServerConfig;
import com.dayrain.component.ServerUrl;
import com.dayrain.utils.ListViewHelper;
import com.dayrain.views.HomePage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

/**
 * 删除路径
 * @author peng
 * @date 2021/10/27
 */
public class DeleteUrlHandler implements EventHandler<ActionEvent> {

    private final ServerConfig serverConfig;

    private final ListView<ServerUrl> serverUrlListView;

    private final ServerUrl serverUrl;

    private final HomePage homePage;

    public DeleteUrlHandler(ServerUrl serverUrl, ServerConfig serverConfig, ListView<ServerUrl> serverUrlListView, HomePage homePage) {
        this.serverUrl = serverUrl;
        this.serverConfig = serverConfig;
        this.serverUrlListView = serverUrlListView;
        this.homePage = homePage;
    }

    @Override
    public void handle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(homePage.getIcon()));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(homePage.getIcon());
        alert.setHeaderText("是否确定删除该接口？");
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setOnAction(event1 -> {
            List<ServerUrl> serverUrls = serverConfig.getServerUrls();
            serverUrls.remove(serverUrl);
            ConfigHolder.save();
            ListViewHelper.deleteAndRefresh(serverUrl, serverUrlListView);
        });
        alert.show();
    }
}
