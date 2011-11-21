package com.xlend.gui;

import com.xlend.remote.IMessageSender;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.Naming;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class ConfigEditor extends PopupDialog {

    private JTextField addressField;
    private JSpinner portSpinner;
    private JButton testBtn;
    private JButton okBtn;
    private JButton cancelBtn;
    private AbstractAction testAction;
    private AbstractAction okAction;
    private AbstractAction cancelAction;
    
    public ConfigEditor(String title, Object obj) {
        super(null, title, obj);
    }

    protected void fillContent() {
        super.fillContent();
        XlendWorks.setWindowIcon(this, "Xcost.png");
        JComponent[] comps = (JComponent[]) getObject();
        addressField = (JTextField) comps[0];
        portSpinner = (JSpinner) comps[1];
        
        JPanel upperPanel = new JPanel(new BorderLayout(5,5));
        JPanel labelPanel = new JPanel(new GridLayout(2,1,5,5));
        JPanel editPanel = new JPanel(new GridLayout(2,1,5,5));
        
        labelPanel.add(new JLabel("    Server's IP or name:", SwingConstants.RIGHT));
        labelPanel.add(new JLabel("    Server's port:", SwingConstants.RIGHT));
        editPanel.add(addressField);
        JPanel portPanel = new JPanel(new BorderLayout());
        portPanel.add(portSpinner, BorderLayout.WEST);
        editPanel.add(portPanel);
        
        upperPanel.add(editPanel, BorderLayout.CENTER);
        upperPanel.add(labelPanel, BorderLayout.WEST);
        upperPanel.add(new JPanel(), BorderLayout.EAST);
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(testBtn = new JButton(testAction = new AbstractAction("Test connection") {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: test connection
                String serverIP = addressField.getText()+":"+portSpinner.getValue();
                try {
                    IMessageSender exch = (IMessageSender) Naming.lookup("rmi://" + serverIP + "/XlendServer");
                    JOptionPane.showMessageDialog(null, "Connection successful","Ok!", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Connection refused","Error:", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));
        btnPanel.add(okBtn = new JButton(okAction = new AbstractAction("Ok") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }){});
        btnPanel.add(cancelBtn = new JButton(cancelAction = new AbstractAction("Cancel"){

            @Override
            public void actionPerformed(ActionEvent e) {
                addressField.setText("");
                portSpinner.setValue(new Integer(0));
                dispose();
            }
        }));
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(upperPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(okBtn);
    }
    
    @Override
    public void freeResources() {
        testBtn.removeActionListener(testAction);
        testAction = null;
        okBtn.removeActionListener(okAction);
        okAction = null;
        cancelBtn.removeActionListener(cancelAction);
        cancelAction = null;
    }
    
}
