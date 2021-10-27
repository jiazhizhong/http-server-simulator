package com.dayrain.handle;

import com.dayrain.entity.ConfigHolder;
import com.dayrain.entity.ServerConfig;
import com.dayrain.entity.ServerUrl;
import com.dayrain.server.ServerThread;
import com.dayrain.utils.ListViewHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.List;

/**
 * 删除路径
 * @author peng
 * @date 2021/10/27
 */
public class DeleteUrlHandler implements EventHandler<ActionEvent> {

    private final ServerConfig serverConfig;

    private final ListView<ServerUrl> serverUrlListView;

    private final HashMap<String, ServerThread> threadMap;

    private final ServerUrl serverUrl;

    public DeleteUrlHandler(ServerUrl serverUrl, ServerConfig serverConfig, ListView<ServerUrl> serverUrlListView, HashMap<String, ServerThread> threadMap) {
        this.serverUrl = serverUrl;
        this.serverConfig = serverConfig;
        this.serverUrlListView = serverUrlListView;
        this.threadMap = threadMap;
    }

    @Override
    public void handle(ActionEvent event) {
        List<ServerUrl> serverUrls = serverConfig.getServerUrls();
        serverUrls.remove(serverUrl);
        ConfigHolder.save();
        ListViewHelper.deleteAndRefresh(serverUrl, serverUrlListView);
    }
}
