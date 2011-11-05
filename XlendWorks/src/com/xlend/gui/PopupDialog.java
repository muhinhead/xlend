package com.xlend.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nick Mukhin
 */
public abstract class PopupDialog extends JDialog {

    protected double xScale;
    protected double yScale;
    protected Frame ownerFrame;
    private Object object;

    public PopupDialog(Frame owner, String title,
            double xScale, double yScale, Object obj) {
        super(owner, title);
        ownerFrame = owner;
        setObject(obj);
        init(xScale, yScale);
    }

    private void init(double xScale, double yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
        initSize();
        fillContent();
        setMinimumSize(getSize());
        setVisible(true);
    }

    protected abstract void fillContent();

    protected void initSize() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (xScale * d.width), (int) (yScale * d.height));
        setLocation((int) ((1.0 - xScale) / 2 * d.width), (int) ((1.0 - yScale) / 2 * d.height));
        this.setModal(true);
    }

    protected void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

        validate();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private void removeComponents(Container cont) {
        Component[] components = cont.getComponents();
        for (int i = components.length - 1; i >= 0; i--) {
            Component comp = components[i];
            if (comp != null) {
//                if (comp instanceof Container) {
//                    removeComponents((Container) comp);
//                }
                if (comp instanceof PopupDialog) {
                    ((PopupDialog) comp).freeResources();
                }
                cont.remove(comp);
                comp = null;
            }
        }
    }

    public abstract void freeResources();

    @Override
    public void dispose() {
        freeResources();
        removeComponents(getContentPane());
        super.dispose();
        Runtime.getRuntime().gc();
    }

    public static void updateList(final JTable tableView) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException ex) {
                }
                AbstractTableModel model = (AbstractTableModel) tableView.getModel();
                int selectedRow = tableView.getSelectedRow();
                model.fireTableDataChanged();
                tableView.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });
    }
}
