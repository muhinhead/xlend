package com.xlend.gui.contract;

import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPagePanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.orm.Xcontractpage;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractPagePanel extends EditPagePanel {

    public EditContractPagePanel(Xcontractpage xcontractpage) {
        super(xcontractpage);
    }

    @Override
    public void loadData() {
        Xcontractpage contrpage = (Xcontractpage) getDbObject();
        if (contrpage.getXcontractpageId() != null) {
            idField.setText(contrpage.getXcontractId().toString());
            pageNumField.setText(contrpage.getPagenum().toString());
            descriptionField.setText(contrpage.getDescription());
            if (contrpage.getPagescan() != null) {
                setPhoto((byte[]) contrpage.getPagescan());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xcontractpage contrpage = (Xcontractpage) getDbObject();
        boolean isNew = false;
        if (contrpage.getXcontractpageId() == null) {
            contrpage.setXcontractpageId(0);
            isNew = true;
        }
        contrpage.setPagenum(Integer.parseInt(pageNumField.getText()));
        contrpage.setDescription(descriptionField.getText());
        try {
            contrpage.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(contrpage);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
