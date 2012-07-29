package migration;

import com.xlend.XlendServer;
import com.xlend.dbutil.DbConnection;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class MigrationDialog extends PopupDialog {
    private JScrollPane sp;
    private JTextArea logArea;
    private JTextField loginField;
    private JPasswordField pwdField;
    private JButton startBtn;
    

    public MigrationDialog() {
        super(null, "Data Migration", null);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        JPanel cntPanel = new JPanel(new BorderLayout());
        JPanel upCntPanel = new JPanel(new BorderLayout());
        JPanel upLblPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JPanel upEditsPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        upLblPanel.add(new JLabel("Target DB login:", SwingConstants.RIGHT));
        upLblPanel.add(new JLabel("   Target DB password:", SwingConstants.RIGHT));
        upEditsPanel.add(loginField = new JTextField(20));
        upEditsPanel.add(new JPanel());
        upEditsPanel.add(pwdField = new JPasswordField());
        upEditsPanel.add(new JPanel());
        upEditsPanel.add(new JButton(new AbstractAction("Test connection"){

            public void actionPerformed(ActionEvent e) {
                try {
                    Connection oldCnct = DbConnection.getConnection(); //to apply last changes to source DB
                    oldCnct.close();
                    Connection cnct = GenericCopyData.getConnection("com.mysql.jdbc.Driver", 
                                       "jdbc:mysql://localhost/", 
                                       loginField.getText(), new String(pwdField.getPassword()));
                    GenericCopyData.sqlBatch(new String[]{"create database xlend"}, cnct, false);
                    startBtn.setEnabled(true);
                    JOptionPane.showMessageDialog(MigrationDialog.this, "Connection established!");
                } catch (Exception ex) {
                    startBtn.setEnabled(false);
                    JOptionPane.showMessageDialog(MigrationDialog.this, ex.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));
                
        upCntPanel.add(upLblPanel, BorderLayout.WEST);
        upCntPanel.add(upEditsPanel, BorderLayout.CENTER);
        
        cntPanel.add(upCntPanel, BorderLayout.NORTH);
        cntPanel.add(sp = new JScrollPane(logArea = new JTextArea(40, 5)), BorderLayout.CENTER);
        
        JPanel downBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        downBtnPanel.add(startBtn = new JButton(new AbstractAction("Start"){

            public void actionPerformed(ActionEvent e) {
                Properties props = DbConnection.getProps();
                GenericCopyData.init(logArea, "crebas_mysql.sql");
                GenericCopyData.migrate(props.getProperty("dbDriverName",
                    "org.hsqldb.jdbcDriver"), 
                        props.getProperty("dbConnection",
                    "jdbc:hsqldb:file://" + DbConnection.getCurDir() + "/DB/XlendServer"), 
                        props.getProperty("dbUser", "sa"), props.getProperty("dbPassword", ""), 
                        "com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost/xlend", 
                        loginField.getText(), new String(pwdField.getPassword()));
                if (GenericCopyData.isOk()) {
                    props.setProperty("dbDriverName", "com.mysql.jdbc.Driver");
                    props.setProperty("dbConnection","jdbc:mysql://localhost/xlend");
                    props.setProperty("dbUser", loginField.getText());
                    props.setProperty("dbPassword", new String(pwdField.getPassword()));
                    saveProperties(props);
                    JOptionPane.showMessageDialog(MigrationDialog.this, "Restart server please","Attention",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        }));
        
        downBtnPanel.add(new JButton(new AbstractAction("Close") {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));

        add(new JPanel(), BorderLayout.WEST);
        add(new JPanel(), BorderLayout.EAST);
        add(cntPanel, BorderLayout.CENTER);
        add(downBtnPanel, BorderLayout.SOUTH);
        sp.setPreferredSize(new Dimension(sp.getPreferredSize().width, 300));
        startBtn.setEnabled(false);
    }
    public static void saveProperties(Properties props) {
        try {
            if (props != null) {
                props.store(new FileOutputStream(XlendServer.PROPERTYFILENAME),
                        "-----------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void freeResources() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
