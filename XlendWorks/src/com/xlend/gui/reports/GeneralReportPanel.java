package com.xlend.gui.reports;

import com.xlend.remote.IMessageSender;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
    protected StringBuffer html;
    private JLabel procLbl;
    protected int prevZoomerValue = 100;

    public GeneralReportPanel(IMessageSender exchanger) {
        super(new BorderLayout());
        upperPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(upperPane, BorderLayout.NORTH);
        upperPane.add(zoomer = new JSlider(50, 150));
        upperPane.add(procLbl = new JLabel("%" + zoomer.getValue()));
        zoomer.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                procLbl.setText("" + zoomer.getValue() + "%");
                updateReport();
            }
        });
        setExchanger(exchanger);
    }

    protected static boolean adjustFontSize(StringBuffer html, int oldZoomValue, int newZoomValue) {
        boolean updated = (oldZoomValue != newZoomValue);
        if (updated) {
            String fs = "\"font-size: ";
            double factor = (double) newZoomValue / (double) oldZoomValue;
            int p = html.indexOf(fs);
            int pp = 0;
            while (p >= 0) {
                pp = html.indexOf("%", p);
                int oldSize = Integer.parseInt(html.substring(p + fs.length(), pp));
                Integer newSize = new Integer((int) (oldSize * factor));
                html.replace(p + fs.length(), pp, newSize.toString());
                p = html.indexOf(fs, pp);
            }
        }
        return updated;
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

    protected void adjustCache() {
        if (prevZoomerValue != zoomer.getValue()) {
//            adjustFontSize(html, (int) (prevZoomerValue * 1.4), (int) (zoomer.getValue() * 1.4));
//            adjustFontSize(html, (int) (prevZoomerValue * 1.2), (int) (zoomer.getValue() * 1.2));
//            adjustFontSize(html, (int) (prevZoomerValue * 1.1), (int) (zoomer.getValue() * 1.1));
//            adjustFontSize(html, prevZoomerValue - 10, zoomer.getValue() - 10);
            adjustFontSize(html, prevZoomerValue, zoomer.getValue());
            prevZoomerValue = zoomer.getValue();
        }
    }

    protected abstract JEditorPane createEditorPanel();

    public JEditorPane getEditorPanel() {
        return editorPanel;
    }
}
