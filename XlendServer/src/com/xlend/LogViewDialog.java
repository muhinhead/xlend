package com.xlend;

import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Nick Mukhin
 */
public class LogViewDialog extends PopupDialog {

    private AbstractAction closeAction;
    private JButton closeBtn;
    private JScrollPane sp;
    private final String serverVersion;
    private final String dbVersion;
//    private final File logFile;
    public static StringBuffer logBuffer = new StringBuffer().append(loadFile())
            .append("--Started at:" + Calendar.getInstance().getTime() + "\n");

    public LogViewDialog(String serverVerion, String dbVersion) {
        super(null, "Xcost Server Log", null);
//        this.logFile = log;
        this.serverVersion = serverVerion;
        this.dbVersion = dbVersion;
        XlendServer.setWindowIcon(this, "Xcost.png");
    }

    protected void fillContent() {
        super.fillContent();

        JTextArea logViewArea = new JTextArea();
        logViewArea.setText(logBuffer.toString());

        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn = new JButton(closeAction = new AbstractAction("Close") {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));
        getContentPane().add(sp = new JScrollPane(logViewArea));
        sp.setPreferredSize(new Dimension(800, 400));
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    @Override
    public void freeResources() {
        closeBtn.removeActionListener(closeAction);
        closeAction = null;
    }

    private static String loadFile() {
        BufferedReader reader = null;
        try {
            StringBuffer sb = new StringBuffer();
            reader = new BufferedReader(new FileReader(System.getProperty("user.home")
                    + "/XlendServer.log.0"));
            String text;
            while ((text = reader.readLine()) != null) {
                sb.append(text).append("\n");
            }
            return sb.toString();
        } catch (Exception ex) {
            XlendServer.log(ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                }
            }
        }
        return "...Error loading log...";
    }
}
