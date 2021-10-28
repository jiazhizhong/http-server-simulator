package com.dayrain.style;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class LabelFactory {

    public static Label getLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Microsoft YaHei",18));
        return label;
    }
}
