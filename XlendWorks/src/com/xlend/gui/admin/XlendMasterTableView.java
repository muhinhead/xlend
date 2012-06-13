package com.xlend.gui.admin;

import com.xlend.gui.XlendWorks;
import com.xlend.mvc.Controller;
import com.xlend.mvc.dbtable.DbMasterTableView;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 *
 * @author Nick Mukhin
 */
public class XlendMasterTableView extends DbMasterTableView {

    protected IMessageSender exchanger;

    public XlendMasterTableView(IMessageSender exchanger, 
            Controller detailController, String dependFiled, int masterColNum) 
    {
        super(detailController, dependFiled, masterColNum);
        this.exchanger = exchanger;
    }

    @Override
    protected void updateSelect(int n, String addWhere) {
        String newSelect = "";
        int p = detailSelect.get(n).toUpperCase().indexOf("WHERE");
        if (p > 0) {
            newSelect = detailSelect.get(n).substring(0, p + 5) + " "
                    + addWhere + " AND " + detailSelect.get(n).substring(p + 5);
        } else {
            p = detailSelect.get(n).toUpperCase().indexOf("ORDER BY");
            if (p > 0) {
                newSelect = detailSelect.get(n).substring(0, p) + "WHERE "
                        + addWhere + " " + detailSelect.get(n).substring(p);
            } else {
                newSelect = detailSelect.get(n) + " WHERE " + addWhere;
            }
        }
        try {
            detailsDocument.get(n).setBody(exchanger.getTableBody(newSelect));
        } catch (RemoteException ex) {
            Vector[] body = new Vector[] {
                new Vector(), new Vector()
            };
            body[0].add("null");
            detailsDocument.get(n).setBody(body);
            XlendWorks.log(ex);
        }
        detailControler.get(n).updateExcept(null);
    }
}
