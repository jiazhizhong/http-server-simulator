package com.dayrain.handle;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.Configuration;
import com.dayrain.style.ButtonFactory;
import com.dayrain.style.LabelFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateRandomLenHandler implements EventHandler<ActionEvent> {

    private final Stage primaryStage;

    public UpdateRandomLenHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(ActionEvent event) {
        Configuration configuration = ConfigHolder.get();
        Stage stage = new Stage();
        Label strName = LabelFactory.getLabel("随机字符长度:");
        strName.setPrefWidth(150);
        TextField strField = new TextField(String.valueOf(configuration.getStringLen()));
        Label intName = LabelFactory.getLabel("随机整数长度:");
        TextField intField = new TextField(String.valueOf(configuration.getIntLen()));
        intField.setPrefWidth(150);

        HBox btnHBox = new HBox();
        Label saveTips1 = LabelFactory.getLabel("注: $string$代指随机字符串");
        Label saveTips2 = LabelFactory.getLabel("$int$代指随机整数");
        Button saveButton = ButtonFactory.getButton("保存");
        btnHBox.getChildren().addAll(saveButton);
        btnHBox.setAlignment(Pos.CENTER_RIGHT);
        btnHBox.setSpacing(20d);

        GridPane gridPane = new GridPane();
        gridPane.add(strName, 0, 0);
        gridPane.add(strField, 1, 0);
        gridPane.add(intName, 0, 1);
        gridPane.add(intField, 1, 1);
        gridPane.add(saveTips1, 0, 3);
        gridPane.add(saveTips2, 1, 3);

        gridPane.add(btnHBox, 1, 4);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20d);
        gridPane.setVgap(10d);

        stage.setWidth(500);
        stage.setHeight(400);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(gridPane));
        stage.getIcons().addAll(primaryStage.getIcons());
        stage.show();

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String strLen = strField.getText();
                String intLen = intField.getText();
                configuration.setStringLen(Integer.parseInt(strLen));
                configuration.setIntLen(Integer.parseInt(intLen));
                ConfigHolder.save();
                stage.close();
            }
        });
    }
}
