package com.xlend.gui.order;

import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPagePanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xorderpage;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditOrderPagePanel extends EditPagePanel {

    public EditOrderPagePanel(Xorderpage xorderpage) {
        super(xorderpage);
    }

    @Override
    public void loadData() {
        Xorderpage opage = (Xorderpage) getDbObject();
        if (opage.getXorderpageId() != null) {
            idField.setText(opage.getXorderpageId().toString());
            pageNumField.setText(opage.getPagenum().toString());
            descriptionField.setText(opage.getDescription());
            if (opage.getPagescan() != null) {
                setPhoto((byte[]) opage.getPagescan(), opage.getFileextension());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xorderpage opage = (Xorderpage) getDbObject();
        boolean isNew = false;
        if (opage.getXorderpageId() == null) {
            opage.setXorderpageId(0);
            isNew = true;
        }
        opage.setPagenum(Integer.parseInt(pageNumField.getText()));
        opage.setDescription(descriptionField.getText());
        try {
            opage.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(opage);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
