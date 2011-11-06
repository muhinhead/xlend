/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui;

//import com.csa.orm.Clientprofile;
//import com.csa.orm.Profile;
//import com.csa.orm.dbobject.DbObject;
//import com.csa.util.PopupDialog;
import com.xlend.orm.Profile;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public abstract class ProfileDialog extends PopupDialog {

    private boolean insideTransaction = false;
    public static boolean okPressed;
    private JButton saveButton;
    protected Profile profile = null;
    protected DbObject additionalProfile = null;
    protected EditProfilePanel profileEditPanel;
    protected ProfilePanel additionalEditPanel;
    private AbstractAction saveAction;
    private JButton cancelButton;
    private AbstractAction cancelAction;

    public ProfileDialog(Frame owner, String title, Object obj) {
        super(owner, title, obj);
    }

    @Override
    protected void fillContent() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
        EditContactDialog.okPressed = false;
        if (getObject() != null) {
            DbObject[] recs = (DbObject[]) getObject();
            profile = (Profile) recs[0];
            additionalProfile = recs[1];
        }
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        getContentPane().setLayout(new BorderLayout());
        mainPanel.add(profileEditPanel = new EditProfilePanel(profile));
        mainPanel.add(additionalEditPanel = getAdditionalPanel());
        if (profile != null) {
            additionalEditPanel.setProfile_id(profile.getProfileId());
        }
        getContentPane().add(new JPanel(), BorderLayout.NORTH);
        getContentPane().add(new JPanel(), BorderLayout.WEST);
        getContentPane().add(new JPanel(), BorderLayout.EAST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton = new JButton(saveAction = new AbstractAction("Save") {

            private final String TRANSNAME = "CLIENTPROFILESAVE";

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startTransaction(TRANSNAME);
                    if (profileEditPanel.save()) {
                        profile = (Profile) profileEditPanel.getDbObject();
                        if (profile != null) {
                            additionalEditPanel.setProfile_id(profile.getProfileId());
                        }
                        if (additionalEditPanel.save()) {
                            MainFrame.getExchanger().commitTransaction();
                            EditContactDialog.okPressed = true;
                            dispose();
                        } else {
                            rollbackTransaction(TRANSNAME);
                        }
                    } else {
                        rollbackTransaction(TRANSNAME);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(EditContactDialog.class.getName()).log(Level.SEVERE, null, ex);
                    MainFrame.errMessageBox("Error:", ex.getMessage());
                    rollbackTransaction(TRANSNAME);
                }
            }
        }));
        btnPanel.add(cancelButton = new JButton(cancelAction = new AbstractAction("Cancel") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));
        JPanel aroundButton = new JPanel(new BorderLayout());
        aroundButton.add(btnPanel, BorderLayout.EAST);
        getContentPane().add(aroundButton, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(saveButton);
    }

    @Override
    public void freeResources() {
        saveButton.removeActionListener(saveAction);
        cancelButton.removeActionListener(cancelAction);
        saveAction = null;
        cancelAction = null;
    }

    private void startTransaction(String transactionName) {
        try {
            MainFrame.getExchanger().startTransaction(transactionName);
            insideTransaction = true;
        } catch (RemoteException ex1) {
            Logger.getLogger(EditContactDialog.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    private void rollbackTransaction(String transactionName) {
        if (insideTransaction) {
            try {
                MainFrame.getExchanger().rollbackTransaction(transactionName);
                insideTransaction = false;
            } catch (RemoteException ex1) {
                Logger.getLogger(EditContactDialog.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    protected abstract ProfilePanel getAdditionalPanel();
}
