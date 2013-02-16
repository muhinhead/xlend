package com.xlend.gui.quota;

import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPagePanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.contract.EditContractPagePanel;
import com.xlend.orm.Xquotationpage;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditQuotaPagePanel extends EditPagePanel {

    public EditQuotaPagePanel(Xquotationpage qpage) {
        super(qpage);
    }

    @Override
    public void loadData() {
        Xquotationpage qpage = (Xquotationpage) getDbObject();
        if (qpage.getXquotationpageId() != null) {
            idField.setText(qpage.getXquotationpageId().toString());
            pageNumField.setText(qpage.getPagenum().toString());
            descriptionField.setText(qpage.getDescription());
            if (qpage.getPagescan() != null) {
                setPhoto((byte[]) qpage.getPagescan(), qpage.getFileextension());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xquotationpage qpage = (Xquotationpage) getDbObject();
        boolean isNew = false;
        if (qpage.getXquotationpageId() == null) {
            qpage.setXquotationpageId(0);
            isNew = true;
        }
        qpage.setPagenum(Integer.parseInt(pageNumField.getText()));
        qpage.setDescription(descriptionField.getText());
        try {
            qpage.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(qpage);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
