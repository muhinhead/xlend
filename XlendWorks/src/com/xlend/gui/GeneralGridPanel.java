package com.xlend.gui;

import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public abstract class GeneralGridPanel extends DbTableGridPanel {

    private String select;
    protected IMessageSender exchanger;

    public GeneralGridPanel(IMessageSender exchanger, String select,
            HashMap<Integer, Integer> maxWidths) throws RemoteException {
        super();
        this.select = select;
        this.exchanger = exchanger;
        init(addAction(), editAction(), delAction(), exchanger.getTableBody(select), null);
    }

    protected abstract AbstractAction addAction();

    protected abstract AbstractAction editAction();

    protected abstract AbstractAction delAction();

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
}
