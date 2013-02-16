/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPanelWithPhoto;
import com.xlend.gui.MyJideTabbedPane;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author nick
 */
public abstract class AbstractMechDevicePanel extends EditPanelWithPhoto {

    protected JTextField chassisNrField;
    protected SelectedDateSpinner expDateSP;
    protected JTextField idField;
    protected JTextField regNrField;
    protected JLabel licenseStatusLBL;
    protected JCheckBox licensedChB;
    protected JTabbedPane tabbedPane;
    protected boolean licensed;

    public AbstractMechDevicePanel(DbObject dbObject) {
        super(dbObject);
    }

    protected void adjustLicenseFierlds() {
        expDateSP.setVisible(licensed);
        licenseStatusLBL.setVisible(licensed);
    }

    protected ChangeListener expDateSPchangeListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                repaintLicFields();
            }
        };
    }

    protected ActionListener licensedChBaction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                licensed = licensedChB.isSelected();
                adjustLicenseFierlds();
            }
        };
    }

    protected void licenseExpSetup() {
        expDateSP.addChangeListener(expDateSPchangeListener());
        licensedChB.addActionListener(licensedChBaction());
        licenseStatusLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }

    protected JComponent getTabbedPanel() {
        tabbedPane = new MyJideTabbedPane();
        tabbedPane.add(getPicPanel(), "Photo");
        tabbedPane.setPreferredSize(new Dimension(tabbedPane.getPreferredSize().width, 400));
        return tabbedPane;
    }

    protected void repaintLicFields() {
        Date expDt = (Date) expDateSP.getValue();
        Date today = Calendar.getInstance().getTime();
        long diff = (expDt.getTime() - today.getTime()) / (1000 * 3600 * 24);
        licenseStatusLBL.setForeground(Color.black);
        licenseStatusLBL.setBackground(Color.white);
        if (expDt.before(today)) {
            licenseStatusLBL.setForeground(Color.red);
            licenseStatusLBL.setText("Expired");
        } else if (diff <= 60) {
            licenseStatusLBL.setForeground(Color.blue);
            licenseStatusLBL.setText("2 Month Warning");
        } else {
            licenseStatusLBL.setText("Current");
        }
    }
}
