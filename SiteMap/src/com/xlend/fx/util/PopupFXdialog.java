package com.xlend.fx.util;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Nick Mukhin
 */
public class PopupFXdialog {
    private final Stage parentStage;

    class Delta {

        double x, y;
    }
    protected Stage stage;

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public PopupFXdialog(Class rootClass,Stage parentStage, String title, String fxml, String styleSheet) throws IOException {
        this.parentStage = parentStage;
        stage = new Stage(StageStyle.UTILITY);
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(title);
        stage.setScene(new Scene((Parent) FXMLLoader.load(
                rootClass.getResource(fxml)), Color.TRANSPARENT));
        final Node root = stage.getScene().getRoot();
        final PopupFXdialog.Delta dragDelta = new PopupFXdialog.Delta();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = getStage().getX() - mouseEvent.getScreenX();
                dragDelta.y = getStage().getY() - mouseEvent.getScreenY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getStage().setX(mouseEvent.getScreenX() + dragDelta.x);
                getStage().setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });
        if (styleSheet != null) {
            stage.getScene().getStylesheets().add(rootClass.getResource(styleSheet).toExternalForm());
        }
    }

    public void showAndWait() {
        parentStage.getScene().getRoot().setEffect(new BoxBlur());
        stage.showAndWait();
        parentStage.getScene().getRoot().setEffect(null);
    }
}
