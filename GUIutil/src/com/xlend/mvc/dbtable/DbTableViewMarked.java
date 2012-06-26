package com.xlend.mvc.dbtable;

import com.xlend.mvc.Controller;
import com.xlend.mvc.Document;
import com.xlend.mvc.IView;
import com.xlend.util.PopupListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Admin
 */
public class DbTableViewMarked extends JTable implements ITableView {

    protected Controller controller;
    private boolean withCopyMark;
    private int selectedRow;
    private Vector colName = new Vector();
    protected Vector rowData = new Vector();
    private int idRowNum;
    protected JPopupMenu pop;
    private HashMap<Integer, Integer> maxColWidths = new HashMap<Integer, Integer>();
    private TableCellRenderer myCellRenderer = new MyColorRenderer(this);
    private String string2find;

    public TableCellRenderer getCellRenderer(int row, int column) {
        return column > 0 ? myCellRenderer : super.getCellRenderer(row, column);
    }

    public Controller getController() {
        return controller;
    }

    public void update(Document document) {
        Object[] body = (Object[]) document.getBody();
        colName = (Vector) body[0];
        rowData = (Vector) body[1];
        idRowNum = -1;
        for(int i=0; i<colName.size();i++) {
            if (colName.get(i).equals("_ID")) {
                idRowNum = i;
                break;
            }
        }
        if (getModel() != tableModel) {
            setModel(tableModel);
        }
        this.invalidate();
        tableModel.fireTableStructureChanged();
        //getColumnModel().getColumn(0).setMaxWidth(50);
        if (null != maxColWidths) {
            for (Integer key : maxColWidths.keySet()) {
                try {
                    getColumnModel().getColumn(key).setMaxWidth(maxColWidths.get(key));
                } catch (ArrayIndexOutOfBoundsException ae) {
                }
            }
        }
        gotoRow(0);
    }

    public void synchronize() {
        controller.getDocument().setBody(new Object[]{colName, rowData});
        controller.updateExcept(this);
    }

    public boolean hasMarked() {
        boolean has = false;
        if (withCopyMark) {
            for (Object row : rowData) {
                Vector line = (Vector) row;
                Boolean marked = (Boolean) line.get(0);
                if (marked) {
                    has = true;
                    break;
                }
            }
        }
        return has;
    }

    public ArrayList<Integer> getMarkedIDs() {
        ArrayList<Integer> markedIDs = new ArrayList<Integer>();
        for (Object row : rowData) {
            Vector line = (Vector) row;
            Boolean marked = (Boolean) line.get(0);
            if (marked) {
                markedIDs.add(Integer.parseInt((String) line.get(1)));
            }
        }
        return markedIDs;
    }

    public class ViewUpdateContentException extends Exception {

        public ViewUpdateContentException(String msg) {
            super(msg);
        }
    }
    
    protected AbstractTableModel tableModel =
            new AbstractTableModel() {
                
                private int colNumAdjusted(int col) {
                    if (idRowNum >= 0 && col >= idRowNum) {
                        return col + 1;
                    } else {
                        return col;
                    }
                }
                
                public void fireTableCellUpdated(int row, int col) {
                    super.fireTableCellUpdated(row, colNumAdjusted(col));
                }

                public String getColumnName(int col) {
                    return (String) colName.get(colNumAdjusted(col));
                }

                public int getRowCount() {
                    return rowData.size();
                }

                public int getColumnCount() {
                    return idRowNum >=0 ? (colName.size()-1) : colName.size();
                    ///return colName.size();
                }

                public boolean isCellEditable(int row, int col) {
                    
                    return (getValueAt(row, col).getClass().equals(Boolean.class));
                }

                public Object getValueAt(int row, int col) {
                    Vector line = (Vector) rowData.get(row);
                    return line.get(colNumAdjusted(col));
                }

                public void setValueAt(Object val, int row, int col) {
                    Vector line = (Vector) rowData.get(row);
                    line.set(colNumAdjusted(col), val);
                    fireTableCellUpdated(row, colNumAdjusted(col));
                    synchronize();//new Integer(row));
                }

                public Class getColumnClass(int c) {
                    try {
                        return getValueAt(0, c).getClass();
                    } catch (NullPointerException e) {
                        return String.class;
                    }
                }
            };
    
    public DbTableViewMarked(boolean withCopyMark) {
        super();
        this.withCopyMark = withCopyMark;
        activatePopup();
        ListSelectionModel rowSM = getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    setSelectedRow(-1);
                } else {
                    setSelectedRow(lsm.getMinSelectionIndex());
                }
                //synchronize(new Integer(getSelectedRow()));
            }
        });
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sizeColumnsToFit(AUTO_RESIZE_OFF);
    }

    public void selectUnselect(int row, boolean yes) {
        if (isWithCopyMark() && row >= 0) {
            Vector line = (Vector) rowData.get(row);
            line.set(0, new Boolean(yes));
            rowData.set(row, line);
            //synchronize(new Integer(row));
            repaint();
        }
    }

    public void selectUnselectAll(boolean yes) {
        for (int i = 0; i < rowData.size(); i++) {
            Vector line = (Vector) rowData.get(i);
            line.set(0, new Boolean(yes));
            rowData.set(i, line);
            //synchronize(new Integer(i));
        }
        repaint();
    }

    public void setMaxColWidth(int col, int width) {
        maxColWidths.put(col, width);
    }

    public void setMaxColWidths(HashMap<Integer, Integer> maxColWidths) {
        this.maxColWidths = maxColWidths;
    }

    public void activatePopup() {
        pop = new JPopupMenu();

        if (withCopyMark) {
            pop.add(new AbstractAction("Select") {

                public void actionPerformed(ActionEvent e) {
                    selectUnselect(getSelectedRow(), true);
                }
            });
            pop.add(new AbstractAction("Unselect") {

                public void actionPerformed(ActionEvent e) {
                    selectUnselect(getSelectedRow(), false);
                }
            });
            pop.add(new JSeparator());
            pop.add(new AbstractAction("Select All") {

                public void actionPerformed(ActionEvent e) {
                    selectUnselectAll(true);
                }
            });
            pop.add(new AbstractAction("Unselect All") {

                public void actionPerformed(ActionEvent e) {
                    selectUnselectAll(false);
                }
            });
        }
        addMouseListener(new PopupListener(pop));
    }

    public void updateContent(Object msg, Object pars)
            throws ViewUpdateContentException {
        HashMap selectedRows = null;
        if (msg != null) {
            Vector params = (Vector) msg;
            Vector cols = (Vector) params.elementAt(0);
            if (params.size() > 2) {
                selectedRows = (HashMap) params.elementAt(2);
            }
            rowData = (Vector) params.elementAt(1);
            colName.clear();
            if (isWithCopyMark()) {
                colName.add(0, "mark");
            }
            for (int i = 0; i < cols.size(); i++) {
                colName.add(cols.get(i));
            }
            if (isWithCopyMark()) {
                Vector line;
                for (int i = 0; i < rowData.size(); i++) {
                    line = (Vector) rowData.get(i);
                    if (selectedRows != null) {
                        String id = (String) line.get(0);
                        line.add(0, new Boolean(selectedRows.containsKey(id)));
                    } else {
                        line.add(0, new Boolean(false));
                    }
                    rowData.set(i, line);
                }
            }
            if (getModel() != tableModel) {
                setModel(tableModel);
            }
            this.invalidate();
            tableModel.fireTableStructureChanged();
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

//    public void synchronize(Object param) {
//        if (controller != null) {
//            Integer row = (Integer) param;
//            try {
//                if (row.intValue() < rowData.size() && row.intValue() >= 0) {
//                    controller.updateDoc(this, rowData.elementAt(row.intValue()));
//                } else {
//                    controller.updateDoc(this, null);
//                }
//            } catch (ViewUpdateContentException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    //-- The "BodyAttrib" maintaining interactive dialog --
//    //public Object bodyAttribDialog(Frame frame);
//    public Object bodyAttribDialog() {
//        return null;
//    }
//    public void addToPane(JPanel p) {
//        p.setLayout(new BorderLayout());
//        p.add(this, BorderLayout.CENTER);
//    }
    //@Override
    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public boolean isWithCopyMark() {
        return withCopyMark;
    }

    //@Override
    public Vector getRowData() {
        return rowData;
    }

    //@Override
    public void gotoRow(int row) {
        if (row >= 0 && row < getRowCount()) {
            setSelectedRow(row);
        }
    }

    public static void addCheckBoxes(DbTableDocument categoryDoc) {
        Object[] content = (Object[]) categoryDoc.getBody();
        Vector headers = (Vector) content[0];
        headers.add(0, "");
        Vector rows = (Vector) content[1];
        for (Object r : rows) {
            Vector line = (Vector) r;
            line.add(0, new Boolean(false));
        }
    }

    @Override
    public void setSearchString(String find) {
        this.string2find = find;
    }

    @Override
    public String getSearchString() {
        return string2find;
    }
}
