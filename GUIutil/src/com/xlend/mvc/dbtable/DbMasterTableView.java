package com.xlend.mvc.dbtable;

import com.xlend.mvc.Controller;
import com.xlend.mvc.Document;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Nick Mukhin
 */
public class DbMasterTableView extends DbTableView {
    protected ArrayList<DbTableDocument> detailsDocument = new ArrayList<DbTableDocument>();
    protected ArrayList<Controller> detailControler = new ArrayList<Controller>();
    protected ArrayList<String> detailSelect = new ArrayList<String>();
    protected ArrayList<String> dependFiled = new ArrayList<String>();
    protected ArrayList<Integer> masterColNum = new ArrayList<Integer>();

    public DbMasterTableView(Controller detailController, String dependFiled, 
            int masterColNum) {
        super();
        addDetailController(detailController, dependFiled, masterColNum);
    }

    public void addDetailController(Controller detailControler, String dependFiled, int masterColNum) {
        this.detailControler.add(detailControler);
        this.detailsDocument.add((DbTableDocument) detailControler.getDocument());
        this.dependFiled.add(dependFiled);
        this.masterColNum.add(masterColNum);
        this.detailSelect.add(((DbTableDocument) detailControler.getDocument()).getSelectStatement());
    }

    @Override
    public void setSelectedRow(int selectedRow) {
        super.setSelectedRow(selectedRow);
        if (detailsDocument != null && selectedRow >= 0) {
            Object[] body = (Object[]) getController().getDocument().getBody();
            Vector rowData = (Vector) body[1];
            if (selectedRow < ((Vector) rowData).size()) {
                Vector line = (Vector) rowData.get(selectedRow);
                for (int n = 0; n < detailControler.size(); n++) {
                    updateSelect(n, dependFiled.get(n) + "=" + (String) line.get(masterColNum.get(n)));
                }
            } else {
                for (int n = 0; n < detailControler.size(); n++) {
                    updateSelect(n, dependFiled.get(n) + "=-1");
                }
            }
        }
    }

    protected void updateSelect(int n, String addWhere) {
        String newSelect = "";
        int p = detailSelect.get(n).toUpperCase().indexOf("WHERE");
        if (p > 0) {
            newSelect = detailSelect.get(n).substring(0, p + 5) + " " +
                    addWhere + " AND " + detailSelect.get(n).substring(p + 5);
        } else {
            p = detailSelect.get(n).toUpperCase().indexOf("ORDER BY");
            if (p > 0) {
                newSelect = detailSelect.get(n).substring(0, p) + "WHERE " +
                        addWhere + " " + detailSelect.get(n).substring(p);
            } else {
                newSelect = detailSelect.get(n) + " WHERE " + addWhere;
            }
        }
        detailsDocument.get(n).setBody(newSelect);
        //DbTableViewMarked.addCheckBoxes(detailsDocument.get(n));
        detailControler.get(n).updateExcept(null);
    }

    public void gotoRow(int row) {
        super.gotoRow(row);
        setSelectedRow(row);
    }

    public void update(Document document) {
        super.update(document);
        gotoRow(0);
    }

}
