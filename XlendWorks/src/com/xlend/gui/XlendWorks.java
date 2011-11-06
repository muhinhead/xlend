package com.xlend.gui;

import com.xlend.orm.Userprofile;
import com.xlend.remote.IMessageSender;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
public class XlendWorks {

    private static Userprofile currentUser;
    private static Logger logger = null;
    private static FileHandler fh;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String serverIP = MainFrame.readProperty("ServerAddress", "localhost");
        IMessageSender exchanger;
        try {
            exchanger = (IMessageSender) Naming.lookup("rmi://" + serverIP + "/XlendServer");
            if (login(exchanger)) {
                new MainFrame(exchanger);
            } else {
                System.exit(1);
            }
        } catch (Exception ne) {
            logAndShowMessage(ne);
            System.exit(1);
        }
    }

    public static void log(String msg) {
        log(msg, null);
    }

    public static void log(Throwable th) {
        log(null, th);
    }

    private static void log(String msg, Throwable th) {
        if (logger == null) {
            try {
                logger = Logger.getLogger("XlendWorks");
                fh = new FileHandler("%h/XlendWorks.log", 1048576, 10, true);
                logger.addHandler(fh);
                logger.setLevel(Level.ALL);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.log(Level.SEVERE, msg, th);
    }

    /**
     * @return the currentUser
     */
    public static Userprofile getCurrentUser() {
        return currentUser;
    }

    /**
     * @param aCurrentUser the currentUser to set
     */
    public static void setCurrentUser(Userprofile aCurrentUser) {
        currentUser = aCurrentUser;
    }

    public static void logAndShowMessage(Exception ne) {
        log(ne);
        MainFrame.errMessageBox("Error:", ne.getMessage());
    }

    private static boolean login(IMessageSender exchanger) {
        JTextField loginField = new JTextField(20);
        JPasswordField pwdField = new JPasswordField(20);
        new LoginDialog(new Object[]{loginField, pwdField, exchanger});
        return LoginDialog.isOkPressed();
    }

    public static void setWindowIcon(Window w, String iconName) {
        if (new File("images/" + iconName).exists()) {
            try {
                ImageIcon ic = new javax.swing.ImageIcon("images/" + iconName, "");
                Image im = ic.getImage();
                w.setIconImage(ic.getImage());
            } catch (Exception ex) {
                log(ex);
            }
        } else {
            try {
                w.setIconImage(ImageIO.read(w.getClass().getResourceAsStream("/" + iconName)));
            } catch (Exception ie) {
                log(ie);
            }
        }
    }

}
