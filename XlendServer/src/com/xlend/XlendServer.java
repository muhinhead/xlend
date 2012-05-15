/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend;

import com.xlend.dbutil.DbConnection;
import com.xlend.remote.IMessageSender;
import com.xlend.rmi.RmiMessageSender;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.util.Properties;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Коля
 */
public class XlendServer {

    public static final String version = "0.37";
    private static final String PROPERTYFILENAME = "XlendServer.config";
    private static final String ICONNAME = "Xcost.png";
    private static Logger logger = null;
    private static FileHandler fh;
    private static Thread rmiServer;
    private static TrayIcon ti;
    private static Properties props;
    private static boolean isTraySupported = SystemTray.isSupported();
    private static final String XLEND_SERVER = "Xlend Server";

    private static class CtrlCtrapper extends Thread {

        private Timer oTimer;

        public CtrlCtrapper(Timer p_oTimer) {
            super();
            oTimer = p_oTimer;
        }

        public void run() {
            System.out.println("Ctrl+C pressed!!!");
            oTimer.cancel();
            rmiServer.stop();
        }
    };

    public static void log(String msg) {
        log(msg, null);
    }

    public static void log(Throwable th) {
        log(null, th);
    }

    private static void log(String msg, Throwable th) {
        if (logger == null) {
            try {
                logger = Logger.getLogger("XlendServer");
                fh = new FileHandler("%h/XlendServer.log", 1048576, 10, true);
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
        String appToLog = "\n" + (msg == null ? th.getMessage() : msg);
        LogViewDialog.logBuffer.append(appToLog);
        logger.log(Level.SEVERE, msg, th);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!isTraySupported) {
            System.out.println("Java version: " + System.getProperty("java.version"));
            if (args.length < 1) {
                System.out.println("Usage:\n\tcom.csa.cmc.XlendServer [port] (default 1099)");
            }
        } else {
            initTray();
        }
        final int port = (args.length > 0 ? Integer.parseInt(args[0]) : 1099);
        rmiServer = new Thread() {

            public void run() {
                try {
                    final Timer queueRunner = new Timer();
                    System.out.println("Starting server on port " + port + "... ");
                    java.rmi.registry.LocateRegistry.createRegistry(port);
                    props = new Properties();
                    File propFile = new File(PROPERTYFILENAME);
                    try {
                        if (propFile.exists()) {
                            props.load(new FileInputStream(propFile));
                            DbConnection.setProps(props);
                            System.out.println("Properties loaded from " + PROPERTYFILENAME);
                        }
                        System.out.println("\nPress Ctrl+C to interrupt");
                    } catch (IOException ioe) {
                        log(ioe);
                    }
                    IMessageSender c = new RmiMessageSender();
                    Naming.rebind("rmi://localhost:" + port + "/XlendServer", c);
                    if (!isTraySupported) {
                        Runtime.getRuntime().addShutdownHook(new CtrlCtrapper(queueRunner));
                    }
                } catch (Exception ex) {
                    log("RMI server trouble: " + ex.getMessage());
                    System.exit(1);
                }
            }
        };
        rmiServer.start();
    }

    public static Image loadImage(String iconName) {
        Image im = null;
        File f = new File("images/" + iconName);
        if (f.exists()) {
            try {
                ImageIcon ic = new javax.swing.ImageIcon("images/" + iconName, "");
                im = ic.getImage();
            } catch (Exception ex) {
                log(ex);
            }
        } else {
            try {
                im = ImageIO.read(XlendServer.class.getResourceAsStream("/" + iconName));
            } catch (Exception ie) {
                log(ie);
            }
        }
        return im;
    }

    private static void initTray() {
        final SystemTray tray = SystemTray.getSystemTray();
        try {
            Image icon = loadImage(ICONNAME);
            final PopupMenu popup = new PopupMenu();
            MenuItem miExit = new MenuItem("Exit");
            miExit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    rmiServer.stop();
//                    DbConnection.shutDownDatabase();
                    System.exit(0);
                }
            });
            MenuItem miAbout = new MenuItem("About...");
            miAbout.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    new AboutDialog();
                }
            });
            MenuItem miLog = new MenuItem("Server log...");
            miLog.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    showLog();
                }
            });
//            MenuItem miFix = new MenuItem("Recreate Site Diary tables");
//            miFix.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
//                    recreateSiteDiary();
//                }
//
//                private void recreateSiteDiary() {
//                    DbConnection.recreateSiteDiary();
//                }
//            });

            popup.add(miLog);
            popup.add(miAbout);
            
//            popup.add(miFix);
            
            popup.addSeparator();
            popup.add(miExit);
            ti = new TrayIcon(icon, XLEND_SERVER, popup);
            ti.setActionCommand("DoubleClick");
            ti.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    showLog();
                }
            });
            tray.add(ti);
        } catch (Exception ex) {
            log("RMI server trouble: " + ex.getMessage());
            System.exit(2);
        }
    }

    private static void showLog() {
        new LogViewDialog(version, DbConnection.DB_VERSION);
    }

    public static void setWindowIcon(Window w, String iconName) {
        w.setIconImage(loadImage(iconName));
    }
}
