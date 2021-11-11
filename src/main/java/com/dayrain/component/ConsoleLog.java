package com.dayrain.component;

import com.dayrain.views.LogArea;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/**
 * 请求日志打印
 * @author peng
 * @date 2021/11/8
 */
public class ConsoleLog {

    private static final HashMap<String, String> logs = new HashMap<>();

    private static final String NO_REQUEST = "暂无请求";

    private static LogArea logArea;

    public static void initTextArea(LogArea area) {
        logArea = area;
    }

    public synchronized static void log(ServerUrl serverUrl, String params, String resp) {
        String log = logs.getOrDefault(serverUrl.getServerName(), null);
        if (log == null || NO_REQUEST.equals(log)) {
            log = "";
        }

        if (params == null || "".equals(params)) {
            params = "空";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[").append(now()).append("]");
        stringBuilder.append(serverUrl.getUrlName()).append("   ");
        stringBuilder.append(serverUrl.getUrl()).append("   ").append(serverUrl.getRequestType().name()).append("\n");
        stringBuilder.append("参数: ").append("\n");
        stringBuilder.append(params).append("\n");
        stringBuilder.append("返回值: ").append("\n");
        stringBuilder.append(resp);
        stringBuilder.append("\n\n");

        log += stringBuilder.toString();

        if(log.length() > 5000) {
            log = log.substring(log.length() - 5000);
            System.out.println(log.length());
        }

        logs.put(serverUrl.getServerName(), log);

        if (serverUrl.getServerName().equals(logArea.getServerName())) {
            if (NO_REQUEST.equals(logArea.getText())) {
                logArea.setText(log);
            } else {
                logArea.setText("");
                logArea.appendText(log);
            }
            logArea.setScrollTop(Double.MAX_VALUE);
        }
    }

    public synchronized static void resetTextArea(String serverName) {
        if (!logs.containsKey(serverName)) {
            logs.put(serverName, NO_REQUEST);
        }

        logArea.setServerName(serverName);
        logArea.setText(logs.get(serverName));
        logArea.appendText("");
        logArea.setScrollTop(Double.MAX_VALUE);
    }

    private static String now() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-DD-mm HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
