package com.xlend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.swing.JApplet;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Nick Mukhin
 */
public class HTMLapplet extends JApplet {
    
    private String url = "http://www.oracle.com/us/index.html";
    private static JFXPanel fxContainer;
    private static final int JFXPANEL_WIDTH_INT = 700;
    private static final int JFXPANEL_HEIGHT_INT = 550;
    private static final int PANEL_WIDTH_INT = 675;
    private static final int PANEL_HEIGHT_INT = 400;    

    public HTMLapplet(String aUrl) {
        super();
        setUrl(aUrl);
    }
    
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param aUrl the url to set
     */
    public void setUrl(String aUrl) {
        url = aUrl;
    }
    
    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createScene();
            }
        });
    }
    
    private void createScene() {
        fxContainer.setScene(new Scene(createBrowser()));
    }
    
    private Pane createBrowser() {
        Double widthDouble = new Integer(PANEL_WIDTH_INT).doubleValue();
        Double heightDouble = new Integer(PANEL_HEIGHT_INT).doubleValue();
        WebView view = new WebView();
        view.setMinSize(widthDouble, heightDouble);
        view.setPrefSize(widthDouble, heightDouble);
        final WebEngine eng = view.getEngine();
        final Label warningLabel = new Label("Do you need to specify web proxy information?");
        eng.load(url);

        ChangeListener handler = new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (warningLabel.isVisible()) {
                    warningLabel.setVisible(false);
                }
            }
        };
        eng.getLoadWorker().progressProperty().addListener(handler);

        final TextField locationField = new TextField(url);
        locationField.setMaxHeight(Double.MAX_VALUE);
        Button goButton = new Button("Go");
        goButton.setDefaultButton(true);
        EventHandler<ActionEvent> goAction = new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                eng.load(locationField.getText().startsWith("http://") ? locationField.getText()
                        : "http://" + locationField.getText());
            }
        };
        goButton.setOnAction(goAction);
        locationField.setOnAction(goAction);
        eng.locationProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                locationField.setText(newValue);
            }
        });
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setVgap(5);
        grid.setHgap(5);
        GridPane.setConstraints(locationField, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES);
        GridPane.setConstraints(goButton, 1, 0);
        GridPane.setConstraints(view, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(warningLabel, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES);
        grid.getColumnConstraints().addAll(
                new ColumnConstraints(widthDouble - 200, widthDouble - 200, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true),
                new ColumnConstraints(40, 40, 40, Priority.NEVER, HPos.CENTER, true));
        grid.getChildren().addAll(locationField, goButton, warningLabel, view);
        return grid;
    }
    
}
