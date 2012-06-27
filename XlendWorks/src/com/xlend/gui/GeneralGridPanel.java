package com.xlend.gui;

import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.mvc.dbtable.ITableView;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public abstract class GeneralGridPanel extends DbTableGridPanel {

    private String select;
//    private String originalSelect;
    protected IMessageSender exchanger;
    public boolean isExternalView = false;

    public GeneralGridPanel(IMessageSender exchanger, String select,
            HashMap<Integer, Integer> maxWidths, boolean readOnly, DbTableView tabView) throws RemoteException {
        super();
        this.select = select;
        this.exchanger = exchanger;
        isExternalView = (tabView != null);
        init(new AbstractAction[]{readOnly ? null : addAction(),
                    readOnly ? null : editAction(),
                    readOnly ? null : delAction()}, 
                select, exchanger.getTableBody(select), maxWidths, tabView);
        setIsMultilineSelection(false);
    }
    
    public GeneralGridPanel(IMessageSender exchanger, String select,
            HashMap<Integer, Integer> maxWidths, boolean readOnly) throws RemoteException {
        this(exchanger, select, maxWidths, readOnly, null);
    }

    protected abstract AbstractAction addAction();

    protected abstract AbstractAction editAction();

    protected abstract AbstractAction delAction();

    protected void init(AbstractAction[] acts, String select, Vector[] tableBody, HashMap<Integer, Integer> maxWidths, DbTableView tabView) {
        super.init(acts, select, tableBody, maxWidths, tabView);
        enableActions();
    }

    protected void refresh() {
        int id = getSelectedID();
        if (id > 0) {
            try {
                GeneralFrame.updateGrid(exchanger, getTableView(),
                        getTableDoc(), getSelect(), id);
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
            }
        }
    }

    /**
     * @return the select
     */
    public String getSelect() {
        return select;
    }

    /**
     * @param select the select to set
     */
    public void setSelect(String select) {
        this.select = select;
    }

    protected void enableActions() {
        boolean enableDelete = (XlendWorks.getCurrentUser().getManager()==1 || XlendWorks.getCurrentUser().getSupervisor()==1);
        if (getDelAction() != null) {
            getDelAction().setEnabled(enableDelete);
        }
    }

    void highlightSearch(String text) {
        getTableView().setSearchString(text);
        refresh();
    }

    void setFilter(String text) {
        getTableDoc().setFilter(text);
        try {
            GeneralFrame.updateGrid(exchanger, getTableView(),
                    getTableDoc(), getSelect(), null);
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
    }


    public void setIsMultilineSelection(boolean b) {
        getTableView().setIsMultilineSelection(b);
    }    
}
