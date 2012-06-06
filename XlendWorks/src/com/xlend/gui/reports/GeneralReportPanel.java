package com.xlend.gui.reports;

import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public abstract class GeneralReportPanel extends JPanel {
    protected static IMessageSender exchanger;
    protected JEditorPane editorPanel;
    
    public GeneralReportPanel(IMessageSender exchanger) {
        super(new BorderLayout());
        setExchanger(exchanger);
    }

    protected void setExchanger(IMessageSender exchanger) {
        this.exchanger = exchanger;
    }

    public abstract void updateReport();
    protected abstract JEditorPane createEditorPanel();

    public JEditorPane getEditorPanel() {
        return editorPanel;
    }
}
