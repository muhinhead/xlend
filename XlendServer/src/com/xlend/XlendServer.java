/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend;

import com.xlend.dbutil.DbConnection;
import com.xlend.remote.IMessageSender;
import com.xlend.rmi.RmiMessageSender;
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

/**
 *
 * @author Коля
 */
public class XlendServer {

    private static Logger logger = null;
    private static FileHandler fh;
    private static Thread rmiServer;
    public static final String PROPERTYFILENAME = "XlendServer.config";
    private static Properties props;

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
        logger.log(Level.SEVERE, msg, th);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java version used: "+System.getProperty("java.version"));
        if (args.length < 1) {
            System.out.println("Usage:\n\tcom.csa.cmc.XlendServer [port] (default 1099)");
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
                    Runtime.getRuntime().addShutdownHook(new CtrlCtrapper(queueRunner));
                } catch (Exception ex) {
                    log("RMI server trouble: " + ex.getMessage());
                    System.exit(1);
                }
            }
        };
        rmiServer.start();
    }
}
