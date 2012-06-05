package com.xlend.gui.hr;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Cbitems;
import com.xlend.orm.Xloans;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author nick
 */
class EditLoanPanel extends RecordEditPanel {

    private DefaultComboBoxModel requestedByCbModel, authorizedByCbModel,
            representedByCbModel, deductTimeModeCbModel;
    private JComboBox requestedByCB, authorizedByCB, //representedByCB, 
            deductTimeModeCB;
    private JTextField idField;
    private JSpinner dateSP, issuedDateSP, deductStartDateSP;
    private JRadioButton isLoanRB, isAdvanceRB, isTransportRB;
    private JSpinner amountSP, deductSP;
    private JRadioButton signedRB, unSignedRB;
    private JLabel lblIss, lblDedStart, lblDedAmt, lblAt;

    public EditLoanPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Date:",
            "Requested by:", //"Date issued:"
            "Authorized by:",//"Dediction start:"
            "Amount R:",
            "Deduction R:", //  "@"
            "Signed:"//,
//            "Representative:"
        };
        requestedByCbModel = new DefaultComboBoxModel();
        authorizedByCbModel = new DefaultComboBoxModel();
        representedByCbModel = new DefaultComboBoxModel();
        deductTimeModeCbModel = new DefaultComboBoxModel();

        authorizedByCbModel.addElement(new ComboItem(0, "---"));
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            requestedByCbModel.addElement(ci);
//            authorizedByCbModel.addElement(ci);
            representedByCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger(), "coalesce(wage_category,1)=1")) {
            authorizedByCbModel.addElement(ci);
        }

        deductTimeModeCbModel.addElement(new ComboItem(1, "Weekly"));
        deductTimeModeCbModel.addElement(new ComboItem(1, "Monthly"));
        deductTimeModeCbModel.addElement(new ComboItem(1, "Once all"));

//        idField = new JTextField();

        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getBorderPanel(new JComponent[]{
                getGridPanel(new JComponent[]{
                    dateSP = new SelectedDateSpinner(),
                    new JPanel(),
                    isLoanRB = new JRadioButton("Loan"),
                    isAdvanceRB = new JRadioButton("Advance"),
                    isTransportRB = new JRadioButton("Transport")
                })
            }),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(requestedByCB = new JComboBox(requestedByCbModel),
                new EmployeeLookupAction(requestedByCB)),
                new JPanel(),
                getBorderPanel(new JComponent[]{
                    getGridPanel(new JComponent[]{lblIss = new JLabel("Date issued:", SwingConstants.RIGHT),
                        issuedDateSP = new SelectedDateSpinner()})
                })
            }),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(authorizedByCB = new JComboBox(authorizedByCbModel),
                new EmployeeLookupAction(authorizedByCB, "coalesce(wage_category,1)=1")),
                new JPanel(),
                getBorderPanel(new JComponent[]{
                    getGridPanel(new JComponent[]{lblDedStart = new JLabel(" Deduction start:", SwingConstants.RIGHT),
                        deductStartDateSP = new SelectedDateSpinner()})
                })
            }),
            getBorderPanel(new JComponent[]{amountSP = new SelectedNumberSpinner(0, 0, 1000000, 1)}),
            getGridPanel(getBorderPanel(new JComponent[]{
                deductSP = new SelectedNumberSpinner(0, 0, 1000000, 1),
                lblAt = new JLabel("@", SwingConstants.RIGHT),
                deductTimeModeCB = new JComboBox(deductTimeModeCbModel)
            }), 2),
            getBorderPanel(new JComponent[]{getGridPanel(new JComponent[]{
                    signedRB = new JRadioButton("Y"),
                    unSignedRB = new JRadioButton("N")
                })})
//            getBorderPanel(new JComponent[]{
//                comboPanelWithLookupBtn(
//                representedByCB = new JComboBox(representedByCbModel), new EmployeeLookupAction(representedByCB))
//            })
        };
        for (JSpinner sp : new JSpinner[]{dateSP, issuedDateSP, deductStartDateSP}) {
            sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(sp);
        }
        ButtonGroup isGrp = new ButtonGroup();
        isGrp.add(isLoanRB);
        isGrp.add(isAdvanceRB);
        isGrp.add(isTransportRB);
        ButtonGroup signedGrp = new ButtonGroup();
        signedGrp.add(signedRB);
        signedGrp.add(unSignedRB);

        lblIss.setPreferredSize(new Dimension(lblDedStart.getPreferredSize().width, lblIss.getPreferredSize().height));

        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        lblDedAmt = labels[5];

        isTransportRB.addActionListener(checkTransport());
        isAdvanceRB.addActionListener(checkTransport());
        isLoanRB.addActionListener(checkTransport());
    }

    @Override
    public void loadData() {
        Xloans xl = (Xloans) getDbObject();
        if (xl != null) {
            idField.setText(xl.getXloansId().toString());
            if (xl.getLoandate() != null) {
                dateSP.setValue(new java.util.Date(xl.getLoandate().getTime()));
            }
            if (xl.getIssueddate() != null) {
                issuedDateSP.setValue(new java.util.Date(xl.getIssueddate().getTime()));
            }
            if (xl.getDeductstartdate() != null) {
                deductStartDateSP.setValue(new java.util.Date(xl.getDeductstartdate().getTime()));
            }
            if (xl.getAmount() != null) {
                amountSP.setValue(xl.getAmount().intValue());
            }
            if (xl.getDeduction() != null) {
                deductSP.setValue(xl.getDeduction().intValue());
            }
            if (xl.getAuthorizedbyId() != null) {
                selectComboItem(authorizedByCB, xl.getAuthorizedbyId());
            }
            if (xl.getRequestedbyId() != null) {
                selectComboItem(requestedByCB, xl.getRequestedbyId());
            }
//            if (xl.getRepresentId() != null) {
//                selectComboItem(representedByCB, xl.getRepresentId());
//            }
            if (xl.getDeducttime() != null) {
                selectComboItem(deductTimeModeCB, xl.getDeducttime());
            }
            isAdvanceRB.setSelected(xl.getIsAdvance() != null && xl.getIsAdvance() == 1);
            isLoanRB.setSelected(xl.getIsLoan() != null && xl.getIsLoan() == 1);
            isTransportRB.setSelected(xl.getIsTransport() != null && xl.getIsTransport() == 1);
            unSignedRB.setSelected(xl.getIsSigned() == null || xl.getIsSigned() == 0);
            signedRB.setSelected(xl.getIsSigned() != null && xl.getIsSigned() == 1);
        }
        deductEnable();
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xloans xl = (Xloans) getDbObject();
        if (xl == null) {
            xl = new Xloans(null);
            xl.setXloansId(0);
            isNew = true;
        }
        Date dt = (Date) dateSP.getValue();
        if (dt != null) {
            xl.setLoandate(new java.sql.Date(dt.getTime()));
        }
        xl.setIsAdvance(isAdvanceRB.isSelected() ? 1 : 0);
        xl.setIsLoan(isLoanRB.isSelected() ? 1 : 0);
        xl.setIsTransport(isTransportRB.isSelected() ? 1 : 0);
        xl.setRequestedbyId(getSelectedCbItem(requestedByCB));
        dt = (Date) issuedDateSP.getValue();
        if (dt != null) {
            xl.setIssueddate(new java.sql.Date(dt.getTime()));
        }
        xl.setAuthorizedbyId(getSelectedCbItem(authorizedByCB));
        dt = (Date) deductStartDateSP.getValue();
        if (dt != null) {
            xl.setDeductstartdate(new java.sql.Date(dt.getTime()));
        }
        xl.setAmount(new Double((Integer) amountSP.getValue()));
        xl.setDeduction(new Double((Integer) deductSP.getValue()));
        xl.setDeducttime(getSelectedCbItem(deductTimeModeCB));
        xl.setIsSigned(signedRB.isSelected() ? 1 : 0);
//        xl.setRepresentId(getSelectedCbItem(representedByCB));
        return saveDbRecord(xl, isNew);
    }

    private ActionListener checkTransport() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deductEnable();
            }
        };
    }

    private void deductEnable() {
        boolean deductActive = !isTransportRB.isSelected();
        deductSP.setEnabled(deductActive);
        deductStartDateSP.setEnabled(deductActive);
        lblDedAmt.setEnabled(deductActive);
        lblDedStart.setEnabled(deductActive);
        lblAt.setEnabled(deductActive);
        deductTimeModeCB.setEnabled(deductActive);
    }
}
