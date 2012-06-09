package com.xlend.gui.reports;

import com.xlend.remote.IMessageSender;
import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;

/**
 *
 * @author Nick Mukhin
 */
public abstract class GeneralReportPanel extends JPanel {

    protected static IMessageSender exchanger;
    protected JEditorPane editorPanel;
    protected JSlider zoomer;
    protected JPanel upperPane;
    protected JScrollPane scrollPane;
    private JLabel procLbl;

    public GeneralReportPanel(IMessageSender exchanger) {
        super(new BorderLayout());
        upperPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(upperPane, BorderLayout.NORTH);
        upperPane.add(zoomer = new JSlider(50, 120));
        upperPane.add(procLbl = new JLabel("%"));
        zoomer.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                procLbl.setText(""+zoomer.getValue()+"%");
                updateReport();
            }
        });
        setExchanger(exchanger);
    }

    protected void setExchanger(IMessageSender exchanger) {
        this.exchanger = exchanger;
    }

    public void updateReport() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (scrollPane != null && editorPanel != null) {
            scrollPane.remove(scrollPane);
            remove(scrollPane);
        }
        editorPanel = null;
        JEditorPane p;
        scrollPane = new JScrollPane(p = createEditorPanel());
        setVisible(false);
        add(scrollPane, BorderLayout.CENTER);
        p.setCaretPosition(0);
        setVisible(true);
        setCursor(Cursor.getDefaultCursor());
    }

    protected abstract JEditorPane createEditorPanel();

    public JEditorPane getEditorPanel() {
        return editorPanel;
    }
}
