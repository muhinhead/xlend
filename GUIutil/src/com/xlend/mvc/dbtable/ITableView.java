/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.mvc.dbtable;

import java.util.Vector;

/**
 *
 * @author nick
 */
public interface ITableView {

    Vector getRowData();

    int getSelectedRow();

    void gotoRow(int row);
    
    int getRowCount();
    
}
