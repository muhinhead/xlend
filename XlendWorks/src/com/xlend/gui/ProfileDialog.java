package com.xlend.gui;

import com.xlend.orm.Profile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.ImagePanel;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

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
        super.fillContent();
        EditContactDialog.okPressed = false;
        if (getObject() != null) {
            DbObject[] recs = (DbObject[]) getObject();
            profile = (Profile) recs[0];
            additionalProfile = recs[1];
        }
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        mainPanel.add(profileEditPanel = new EditProfilePanel(profile));
        mainPanel.add(additionalEditPanel = getAdditionalPanel());
        if (profile != null) {
            additionalEditPanel.setProfile_id(profile.getProfileId());
        }

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
                            DashBoard.getExchanger().commitTransaction();
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
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
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
            DashBoard.getExchanger().startTransaction(transactionName);
            insideTransaction = true;
        } catch (RemoteException ex1) {
            Logger.getLogger(EditContactDialog.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    private void rollbackTransaction(String transactionName) {
        if (insideTransaction) {
            try {
                DashBoard.getExchanger().rollbackTransaction(transactionName);
                insideTransaction = false;
            } catch (RemoteException ex1) {
                Logger.getLogger(EditContactDialog.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    protected abstract ProfilePanel getAdditionalPanel();
}
