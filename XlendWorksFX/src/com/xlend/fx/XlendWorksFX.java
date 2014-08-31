package com.xlend.fx;

import com.xlend.fx.admin.AdmnUserDialog;
import com.xlend.fx.util.FXpopupDialog;
import com.xlend.fx.util.Utils;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author Nick Mukhin
 */
public class XlendWorksFX extends Application {

    private Pane loginPane;
    private Pane dashboardPane;
    private StackPane root;
    public static final String version = "0.87";
    private static String directoryResult;
    private static String serverAddressResult;
    private ComboBox<String> usersCB;
    private PasswordField pwdField;
    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        root = new StackPane();
        Utils.setMainStage(primaryStage);
        if (connectToRMI()) {
            root.getChildren().add(loginPane = getLoginPane());
            root.getChildren().add(dashboardPane = getDashboardPane());
            dashboardPane.setVisible(false);

            Scene scene = new Scene(root, 800, 620);

            primaryStage.setTitle("Xlend Works");
            primaryStage.setScene(scene);
            scene.getStylesheets().add(XlendWorksFX.class.getResource("Xlend.css").toExternalForm());
            primaryStage.show();
        }
    }

    private void hideLoginAndShowDashboard() {
        Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(root.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                loginPane.setVisible(false);
                dashboardPane.setVisible(true);
                Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(root.opacityProperty(), 0.0)),
                        new KeyFrame(new Duration(800), new KeyValue(root.opacityProperty(), 1.0)));
                fadeIn.play();
            }
        }, new KeyValue(root.opacityProperty(), 0.0)));
        fade.play();
    }

    private void hideDashboardAndShowLogin() {
        Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(root.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                dashboardPane.setVisible(false);
                loginPane.setVisible(true);
                Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(root.opacityProperty(), 0.0)),
                        new KeyFrame(new Duration(800), new KeyValue(root.opacityProperty(), 1.0)));
                fadeIn.play();
            }
        }, new KeyValue(root.opacityProperty(), 0.0)));
        fade.play();
    }

    private Pane getLoginPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);
        usersCB = new ComboBox<String>();
        usersCB.setItems(FXCollections.observableArrayList(Utils.loadAllLogins()));
        usersCB.setEditable(true);
        grid.add(usersCB, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        pwdField = new PasswordField();
        grid.add(pwdField, 1, 2);

        Node loginButtonNode = FXutils.createButton(getClass(), "LoginFX.png", new Runnable() {
            @Override
            public void run() {
                String login = (String) usersCB.getValue();
                String pwd = new String(pwdField.getText());
                try {
                    DbObject[] users = Utils.getExchanger().getDbObjects(Userprofile.class,
                            "login='" + login + "' and pwdmd5='" + pwd + "'", null);
                    if (users.length > 0) {
                        Userprofile currentUser = (Userprofile) users[0];
                        try {
                            currentUser.setPwdmd5("");
                        } catch (Exception ex) {
                        }
                        Utils.setCurrentUser(currentUser);
                        hideLoginAndShowDashboard();
                    } else {
                        Dialogs.showErrorDialog(null, "Access denied!", "Error", "Oops!");
                        usersCB.requestFocus();
                    }
                } catch (Exception ex) {
                    Dialogs.showErrorDialog(null, "Server not respondind, sorry!", "Error", "Oops!");
                    Utils.log(ex);
                }
            }
        });

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(loginButtonNode);
        grid.add(hbBtn, 2, 0, 1, 3);
        return grid;
    }

    private Pane getDashboardPane() {
        BorderPane panel = new BorderPane();
        panel.setPadding(new Insets(25, 25, 25, 25));

        Node docsBtn = FXutils.createButton(getClass(), "Docs.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);
        Node hrBtn = FXutils.createButton(getClass(), "HR.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);
        Node partsBtn = FXutils.createButton(getClass(), "Parts.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);
        Node sitesBtn = FXutils.createButton(getClass(), "Sites.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);
        Node fleetBtn = FXutils.createButton(getClass(), "Fleet.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);

        Node bankingBtn = FXutils.createButton(getClass(), "Banking.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);
        Node reportsBtn = FXutils.createButton(getClass(), "Reports.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);
        Node logisticsBtn = FXutils.createButton(getClass(), "Logistics.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);
        Node fuelBtn = FXutils.createButton(getClass(), "Fuel.png", new Runnable() {
            @Override
            public void run() {
                //TODO 
            }
        }, false);

        HBox zeroBtnLine = new HBox(400);
        zeroBtnLine.setAlignment(Pos.CENTER);

        Node adminButtonNode = FXutils.createButton(getClass(), "AdminFX.png", new Runnable() {
            @Override
            public void run() {
                try {
                    //TODO: open admin console
                    //                Dialogs.DialogResponse result = new UserListDialog("Admin's console", "Adjust settings").show();
                    //                System.out.println("!!result=" + result.toString());
                    AdmnUserDialog adud = new AdmnUserDialog(XlendWorksFX.mainStage,null);
                    adud.showAndWait();
                } catch (IOException ex) {
                    Utils.logAndShowMessage(ex);
                }
            }
        });
        zeroBtnLine.getChildren().add(adminButtonNode);
        Node logoutButtonNode = FXutils.createButton(getClass(), "ExitFX.png", new Runnable() {
            @Override
            public void run() {

                hideDashboardAndShowLogin();
            }
        });
        zeroBtnLine.getChildren().add(logoutButtonNode);

        panel.setTop(zeroBtnLine);

        HBox firstBtnLine = new HBox(10);
        firstBtnLine.setAlignment(Pos.CENTER);
        firstBtnLine.getChildren().add(docsBtn);
        firstBtnLine.getChildren().add(hrBtn);
        firstBtnLine.getChildren().add(partsBtn);
        firstBtnLine.getChildren().add(sitesBtn);
        firstBtnLine.getChildren().add(fleetBtn);
//        panel.add(firstBtnLine, 0, 18);
        VBox btnsBox = new VBox(10);
        btnsBox.getChildren().add(firstBtnLine);

        HBox secondBtnLine = new HBox(10);
        secondBtnLine.setAlignment(Pos.CENTER);
        secondBtnLine.getChildren().add(bankingBtn);
        secondBtnLine.getChildren().add(reportsBtn);
        secondBtnLine.getChildren().add(logisticsBtn);
        secondBtnLine.getChildren().add(fuelBtn);
//        panel.add(secondBtnLine, 0, 19);
        btnsBox.getChildren().add(secondBtnLine);

        panel.setBottom(btnsBox);
        return panel;
    }

    private static boolean connectToRMI() {
        String serverIP = Utils.readProperty("ServerAddress", "localhost");
        while (true) {
            try {
                Utils.setExchanger((IMessageSender) Naming.lookup("rmi://" + serverIP + "/XlendServer"));
                return Utils.matchVersions(version);
            } catch (Exception ex) {
                Utils.logAndShowMessage(ex);
                if ((serverIP = serverSetup("Check server settings")) == null) {
                    System.exit(1);
                } else {
                    Utils.saveProps();
                }
            }
        }
    }

    public static String serverSetup(String title) {
        String address = Utils.readProperty("ServerAddress", "localhost");
        String[] vals = address.split(":");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        final TextField imageDirField = new TextField();
        imageDirField.setPromptText("Image directory");
        final TextField addressField = new TextField();
        addressField.setPromptText("Server name or address");

        grid.add(new Label("Image directory:"), 0, 0);
        grid.add(imageDirField, 1, 0);
        Button imgDirBtn;
        grid.add(imgDirBtn = new Button("..."), 2, 0);
        grid.add(new Label("Server name or address:"), 0, 1);
        grid.add(addressField, 1, 1);

        imgDirBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Choose images directory");
                //Show open file dialog
                File file = directoryChooser.showDialog(null);
                if (file != null) {
                    imageDirField.setText(file.getPath());
                }
            }
        });

        Callback<Void, Void> myCallback;

        myCallback = new Callback<Void, Void>() {
            @Override
            public Void call(Void param) {
                directoryResult = imageDirField.getText();
                serverAddressResult = addressField.getText();
                return null;
            }
        };

        FXpopupDialog fd = new FXpopupDialog(null, "Settings", "Please check settings", myCallback);
        DialogResponse resp = fd.show(grid);
//        DialogResponse resp = Dialogs.showCustomDialog(null, grid,
//                "Please log in", "Login", DialogOptions.OK_CANCEL, myCallback);
        if (resp == DialogResponse.OK) {
            return serverAddressResult;
        }
        return null;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
