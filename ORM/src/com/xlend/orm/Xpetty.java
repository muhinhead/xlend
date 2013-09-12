// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Sep 12 15:25:59 FET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xpetty extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpettyId = null;
    private Date issueDate = null;
    private Integer xemployeeInId = null;
    private Integer xmachineId = null;
    private Integer xsiteId = null;
    private Double amount = null;
    private Double changeAmt = null;
    private Integer isLoan = null;
    private Integer isPetty = null;
    private Integer isAllowance = null;
    private String notes = null;
    private Date receiptDate = null;
    private Integer xemployeeOutId = null;

    public Xpetty(Connection connection) {
        super(connection, "xpetty", "xpetty_id");
        setColumnNames(new String[]{"xpetty_id", "issue_date", "xemployee_in_id", "xmachine_id", "xsite_id", "amount", "change_amt", "is_loan", "is_petty", "is_allowance", "notes", "receipt_date", "xemployee_out_id"});
    }

    public Xpetty(Connection connection, Integer xpettyId, Date issueDate, Integer xemployeeInId, Integer xmachineId, Integer xsiteId, Double amount, Double changeAmt, Integer isLoan, Integer isPetty, Integer isAllowance, String notes, Date receiptDate, Integer xemployeeOutId) {
        super(connection, "xpetty", "xpetty_id");
        setNew(xpettyId.intValue() <= 0);
//        if (xpettyId.intValue() != 0) {
            this.xpettyId = xpettyId;
//        }
        this.issueDate = issueDate;
        this.xemployeeInId = xemployeeInId;
        this.xmachineId = xmachineId;
        this.xsiteId = xsiteId;
        this.amount = amount;
        this.changeAmt = changeAmt;
        this.isLoan = isLoan;
        this.isPetty = isPetty;
        this.isAllowance = isAllowance;
        this.notes = notes;
        this.receiptDate = receiptDate;
        this.xemployeeOutId = xemployeeOutId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xpetty xpetty = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpetty_id,issue_date,xemployee_in_id,xmachine_id,xsite_id,amount,change_amt,is_loan,is_petty,is_allowance,notes,receipt_date,xemployee_out_id FROM xpetty WHERE xpetty_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xpetty = new Xpetty(getConnection());
                xpetty.setXpettyId(new Integer(rs.getInt(1)));
                xpetty.setIssueDate(rs.getDate(2));
                xpetty.setXemployeeInId(new Integer(rs.getInt(3)));
                xpetty.setXmachineId(new Integer(rs.getInt(4)));
                xpetty.setXsiteId(new Integer(rs.getInt(5)));
                xpetty.setAmount(rs.getDouble(6));
                xpetty.setChangeAmt(rs.getDouble(7));
                xpetty.setIsLoan(new Integer(rs.getInt(8)));
                xpetty.setIsPetty(new Integer(rs.getInt(9)));
                xpetty.setIsAllowance(new Integer(rs.getInt(10)));
                xpetty.setNotes(rs.getString(11));
                xpetty.setReceiptDate(rs.getDate(12));
                xpetty.setXemployeeOutId(new Integer(rs.getInt(13)));
                xpetty.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xpetty;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xpetty ("+(getXpettyId().intValue()!=0?"xpetty_id,":"")+"issue_date,xemployee_in_id,xmachine_id,xsite_id,amount,change_amt,is_loan,is_petty,is_allowance,notes,receipt_date,xemployee_out_id) values("+(getXpettyId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpettyId().intValue()!=0) {
                 ps.setObject(++n, getXpettyId());
             }
             ps.setObject(++n, getIssueDate());
             ps.setObject(++n, getXemployeeInId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getAmount());
             ps.setObject(++n, getChangeAmt());
             ps.setObject(++n, getIsLoan());
             ps.setObject(++n, getIsPetty());
             ps.setObject(++n, getIsAllowance());
             ps.setObject(++n, getNotes());
             ps.setObject(++n, getReceiptDate());
             ps.setObject(++n, getXemployeeOutId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpettyId().intValue()==0) {
             stmt = "SELECT max(xpetty_id) FROM xpetty";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpettyId(new Integer(rs.getInt(1)));
                 }
             } finally {
                 try {
                     if (rs != null) rs.close();
                 } finally {
                     if (ps != null) ps.close();
                 }
             }
         }
         setNew(false);
         setWasChanged(false);
         if (getTriggers() != null) {
             getTriggers().afterInsert(this);
         }
    }

    public void save() throws SQLException, ForeignKeyViolationException {
        if (isNew()) {
            insert();
        } else {
            if (getTriggers() != null) {
                getTriggers().beforeUpdate(this);
            }
            PreparedStatement ps = null;
            String stmt =
                    "UPDATE xpetty " +
                    "SET issue_date = ?, xemployee_in_id = ?, xmachine_id = ?, xsite_id = ?, amount = ?, change_amt = ?, is_loan = ?, is_petty = ?, is_allowance = ?, notes = ?, receipt_date = ?, xemployee_out_id = ?" + 
                    " WHERE xpetty_id = " + getXpettyId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getIssueDate());
                ps.setObject(2, getXemployeeInId());
                ps.setObject(3, getXmachineId());
                ps.setObject(4, getXsiteId());
                ps.setObject(5, getAmount());
                ps.setObject(6, getChangeAmt());
                ps.setObject(7, getIsLoan());
                ps.setObject(8, getIsPetty());
                ps.setObject(9, getIsAllowance());
                ps.setObject(10, getNotes());
                ps.setObject(11, getReceiptDate());
                ps.setObject(12, getXemployeeOutId());
                ps.execute();
            } finally {
                if (ps != null) ps.close();
            }
            setWasChanged(false);
            if (getTriggers() != null) {
                getTriggers().afterUpdate(this);
            }
        }
    }

    public void delete() throws SQLException, ForeignKeyViolationException {
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xpettyitem
            Xpettyitem[] records = (Xpettyitem[])Xpettyitem.load(getConnection(),"xpetty_id = " + getXpettyId(),null);
            for (int i = 0; i<records.length; i++) {
                Xpettyitem xpettyitem = records[i];
                xpettyitem.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xpetty " +
                "WHERE xpetty_id = " + getXpettyId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpettyId(new Integer(-getXpettyId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpettyId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpetty_id,issue_date,xemployee_in_id,xmachine_id,xsite_id,amount,change_amt,is_loan,is_petty,is_allowance,notes,receipt_date,xemployee_out_id FROM xpetty " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xpetty(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),rs.getDouble(6),rs.getDouble(7),new Integer(rs.getInt(8)),new Integer(rs.getInt(9)),new Integer(rs.getInt(10)),rs.getString(11),rs.getDate(12),new Integer(rs.getInt(13))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xpetty[] objects = new Xpetty[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xpetty xpetty = (Xpetty) lst.get(i);
            objects[i] = xpetty;
        }
        return objects;
    }

    public static boolean exists(Connection con, String whereCondition) throws SQLException {
        if (con == null) {
            return true;
        }
        boolean ok = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpetty_id FROM xpetty " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                "WHERE " + whereCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            ok = rs.next();
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return ok;
    }

    //public String toString() {
    //    return getXpettyId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xpettyId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXpettyId(id);
        setNew(prevIsNew);
    }

    public Integer getXpettyId() {
        return xpettyId;
    }

    public void setXpettyId(Integer xpettyId) throws ForeignKeyViolationException {
        setWasChanged(this.xpettyId != null && this.xpettyId != xpettyId);
        this.xpettyId = xpettyId;
        setNew(xpettyId.intValue() == 0);
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.issueDate != null && !this.issueDate.equals(issueDate));
        this.issueDate = issueDate;
    }

    public Integer getXemployeeInId() {
        return xemployeeInId;
    }

    public void setXemployeeInId(Integer xemployeeInId) throws SQLException, ForeignKeyViolationException {
        if (xemployeeInId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeInId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_in_id, foreign key violation: xpetty_xemployee_fk");
        }
        setWasChanged(this.xemployeeInId != null && !this.xemployeeInId.equals(xemployeeInId));
        this.xemployeeInId = xemployeeInId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (null != xmachineId)
            xmachineId = xmachineId == 0 ? null : xmachineId;
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xpetty_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xpetty_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.amount != null && !this.amount.equals(amount));
        this.amount = amount;
    }

    public Double getChangeAmt() {
        return changeAmt;
    }

    public void setChangeAmt(Double changeAmt) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.changeAmt != null && !this.changeAmt.equals(changeAmt));
        this.changeAmt = changeAmt;
    }

    public Integer getIsLoan() {
        return isLoan;
    }

    public void setIsLoan(Integer isLoan) throws SQLException, ForeignKeyViolationException {
        if (null != isLoan)
            isLoan = isLoan == 0 ? null : isLoan;
        setWasChanged(this.isLoan != null && !this.isLoan.equals(isLoan));
        this.isLoan = isLoan;
    }

    public Integer getIsPetty() {
        return isPetty;
    }

    public void setIsPetty(Integer isPetty) throws SQLException, ForeignKeyViolationException {
        if (null != isPetty)
            isPetty = isPetty == 0 ? null : isPetty;
        setWasChanged(this.isPetty != null && !this.isPetty.equals(isPetty));
        this.isPetty = isPetty;
    }

    public Integer getIsAllowance() {
        return isAllowance;
    }

    public void setIsAllowance(Integer isAllowance) throws SQLException, ForeignKeyViolationException {
        if (null != isAllowance)
            isAllowance = isAllowance == 0 ? null : isAllowance;
        setWasChanged(this.isAllowance != null && !this.isAllowance.equals(isAllowance));
        this.isAllowance = isAllowance;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.notes != null && !this.notes.equals(notes));
        this.notes = notes;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.receiptDate != null && !this.receiptDate.equals(receiptDate));
        this.receiptDate = receiptDate;
    }

    public Integer getXemployeeOutId() {
        return xemployeeOutId;
    }

    public void setXemployeeOutId(Integer xemployeeOutId) throws SQLException, ForeignKeyViolationException {
        if (null != xemployeeOutId)
            xemployeeOutId = xemployeeOutId == 0 ? null : xemployeeOutId;
        if (xemployeeOutId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeOutId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_out_id, foreign key violation: xpetty_xemployee_fk2");
        }
        setWasChanged(this.xemployeeOutId != null && !this.xemployeeOutId.equals(xemployeeOutId));
        this.xemployeeOutId = xemployeeOutId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[13];
        columnValues[0] = getXpettyId();
        columnValues[1] = getIssueDate();
        columnValues[2] = getXemployeeInId();
        columnValues[3] = getXmachineId();
        columnValues[4] = getXsiteId();
        columnValues[5] = getAmount();
        columnValues[6] = getChangeAmt();
        columnValues[7] = getIsLoan();
        columnValues[8] = getIsPetty();
        columnValues[9] = getIsAllowance();
        columnValues[10] = getNotes();
        columnValues[11] = getReceiptDate();
        columnValues[12] = getXemployeeOutId();
        return columnValues;
    }

    public static void setTriggers(Triggers triggers) {
        activeTriggers = triggers;
    }

    public static Triggers getTriggers() {
        return activeTriggers;
    }

    //for SOAP exhange
    @Override
    public void fillFromString(String row) throws ForeignKeyViolationException, SQLException {
        String[] flds = splitStr(row, delimiter);
        try {
            setXpettyId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpettyId(null);
        }
        setIssueDate(toDate(flds[1]));
        try {
            setXemployeeInId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXemployeeInId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setAmount(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setAmount(null);
        }
        try {
            setChangeAmt(Double.parseDouble(flds[6]));
        } catch(NumberFormatException ne) {
            setChangeAmt(null);
        }
        try {
            setIsLoan(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setIsLoan(null);
        }
        try {
            setIsPetty(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setIsPetty(null);
        }
        try {
            setIsAllowance(Integer.parseInt(flds[9]));
        } catch(NumberFormatException ne) {
            setIsAllowance(null);
        }
        setNotes(flds[10]);
        setReceiptDate(toDate(flds[11]));
        try {
            setXemployeeOutId(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setXemployeeOutId(null);
        }
    }
}
