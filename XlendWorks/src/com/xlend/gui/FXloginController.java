package com.xlend.gui;

import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.net.URL;
import java.rmi.Naming;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * FXML Controller class
 *
 * @author Nick Mukhin
 */
public class FXloginController implements Initializable, ControlledScreen {

    private final String NMSOFTWARE = "Nick Mukhin (c)2013";
    private ScreensController myController;
    @FXML
    private HBox btnHbox;
    @FXML
    private ComboBox loginComboBox;
    @FXML
    private PasswordField pwdField;
    private Node loginButtonNode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginComboBox.getItems().clear();
        loginComboBox.getItems().addAll(XlendWorks.loadAllLogins());
        loginButtonNode = FXutils.createButton(getClass(), "/LoginFX.png", new Runnable() {
            @Override
            public void run() {
//                DashBoard.showAdmin();
                String login = (String) loginComboBox.getValue();
                String pwd = pwdField.getText();
                try {
                    DbObject[] users = XlendWorks.getExchanger().getDbObjects(Userprofile.class,
                            "login='" + login + "' and pwdmd5='" + pwd + "'", null);
                    if (users.length > 0) {
                        XlendWorks.setCurrentUser((Userprofile) users[0]);
                        if (myController.getScreen("FXdashboard") == null) {
                            myController.loadScreen("FXdashboard", "FXdashboard.fxml");
                        }
                        FXdashboardController.getInstance().setGreeting((Userprofile) users[0]);
                        myController.setScreen("FXdashboard");
                    } else {
//                        JOptionPane.showMessageDialog(null, "Access Denied", "Sorry!", JOptionPane.ERROR_MESSAGE);
//                        myController.setScreen("FXlogin");
                        Dialogs.showErrorDialog(null, "", "Access Denied!", "Sorry!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Server not respondind, sorry", "Error:", JOptionPane.ERROR_MESSAGE);
                    XlendWorks.log(ex);
//                    dispose();
                }
            }
        });
        btnHbox.getChildren().add(loginButtonNode);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.myController = screenParent;
    }

    @FXML
    public void close() {
        DashBoard.ourInstance.exit();
    }

    @FXML
    public void options() {
        String newAddress = XlendWorks.serverSetup("Options");
        if (newAddress != null) {
            DashBoard.getProperties().setProperty("ServerAddress", newAddress);
            try {
                XlendWorks.setExchanger(//exchanger =
                        (IMessageSender) Naming.lookup("rmi://"
                        + newAddress + "/XlendServer"));
            } catch (Exception ex) {
                XlendWorks.logAndShowMessage(ex);
                System.exit(1);
            }
        }
    }

    @FXML
    public void about() {
        new AboutDialog();
    }

    private void setLookAndFeel(String lf) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(lf);
        SwingUtilities.updateComponentTreeUI(DashBoard.ourInstance);
        DashBoard.getProperties().setProperty("LookAndFeel", lf);
        DashBoard.saveProps();
    }

    @FXML
    public void setTynyTheme() {
        try {
            setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setNimbusTheme() {
        try {
            setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setNimrodTheme() {
        try {
            setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setPlasticTheme() {
        try {
            setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setHiFiTheme() {
        try {
            HiFiLookAndFeel.setTheme("Default", "", NMSOFTWARE);
            setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setNoireTheme() {
        try {
            NoireLookAndFeel.setTheme("Default", "", NMSOFTWARE);
            setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setBernsteinTheme() {
        try {
            BernsteinLookAndFeel.setTheme("Default", "", NMSOFTWARE);
            setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setAeroTheme() {
        try {
            AeroLookAndFeel.setTheme("Green", "", NMSOFTWARE);
            setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setSystemTheme() {
        try {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setJavaTheme() {
        try {
            setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e1) {
        }
    }

    @FXML
    public void setMotifTheme() {
        try {
            setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e1) {
        }
    }
}
