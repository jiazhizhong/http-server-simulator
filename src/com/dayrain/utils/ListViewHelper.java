package com.dayrain.utils;

import com.dayrain.entity.ServerUrl;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ListViewHelper {

    public static void addAndRefresh(ServerUrl serverUrl, ListView<ServerUrl>serverUrls) {
        serverUrls.getItems().add(serverUrl);
        refresh(serverUrls);
    }

    public static void deleteAndRefresh(ServerUrl serverUrl, ListView<ServerUrl>serverUrls) {
        serverUrls.getItems().remove(serverUrl);
        refresh(serverUrls);
    }

    public static void refresh(ListView<ServerUrl>serverUrls) {
        ObservableList<ServerUrl> items = serverUrls.getItems();
        serverUrls.setItems(null);
        serverUrls.setItems(items);
    }
}
