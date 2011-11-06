/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui;

//import com.csa.formutil.RecordEditPanel;
//import com.csa.orm.Clientprofile;
//import com.csa.orm.Profile;
//import com.csa.orm.dbobject.DbObject;
//import com.csa.util.PopupDialog;
import com.xlend.orm.Clientprofile;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
class EditContactDialog extends ProfileDialog {

    public EditContactDialog(String string, DbObject[] obs) {
        super(null, string, obs);
    }

    protected ProfilePanel getAdditionalPanel() {
        return new EditClientPanel((Clientprofile) additionalProfile);
    }
}
