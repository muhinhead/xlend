/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui;

//import com.csa.dbutil.ComboItem;
//import com.csa.dbutil.DbUtil;
//import com.csa.formutil.RecordEditPanel;
//import com.csa.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public abstract class ProfilePanel extends RecordEditPanel {

    protected JPanel lblPanel;
    protected JPanel editPanel;
    protected JPanel upPanel;
    protected int profile_id;
    
    private static ComboItem[] getComboItems(String select, ComboItem unknown) {
        Vector itemsVector = null;
        try {
            itemsVector = DashBoard.getExchanger().getTableBody(select)[1];
            ComboItem[] items = new ComboItem[itemsVector.size() + (unknown != null ? 1 : 0)];
            int i = 0;
            if (unknown != null) {
                items[i++] = unknown;
            }
            for (Object ob : itemsVector) {
                Vector line = (Vector) ob;
                items[i++] = new ComboItem(Integer.parseInt((String) line.get(0)), (String) line.get(1));
            }
            return items;
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return new ComboItem[]{};
    }

    public static ComboItem[] getClientGroups() {
        return getComboItems("Select clientgroup_id,groupname from clientgroup order by upper(groupname)", null);
    }

    public static ComboItem[] getSalesPersons() {
        ComboItem nobody = new ComboItem(0, "- unknown -");
        return getComboItems(
                "Select profile_id,first_name||' '||last_name "
                + "from v_userprofile where salesperson=1 order by upper(last_name)", nobody);
    }

    public ProfilePanel(DbObject dbObject) {
        super(dbObject);
    }

    /**
     * @param profile_id the profile_id to set
     */
    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        setLayout(new BorderLayout());
        lblPanel = new JPanel(new GridLayout(labels.length, 1, 5, 5));
        editPanel = new JPanel(new GridLayout(edits.length, 1, 5, 5));
        upPanel = new JPanel(new BorderLayout());
        add(upPanel, BorderLayout.NORTH);
        upPanel.add(lblPanel, BorderLayout.WEST);
        upPanel.add(editPanel, BorderLayout.CENTER);
        upPanel.add(new JPanel(), BorderLayout.EAST);
    }
}
