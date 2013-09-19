// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Sep 19 11:57:01 FET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xloans extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xloansId = null;
    private Date loandate = null;
    private Integer isLoan = null;
    private Integer isAdvance = null;
    private Integer isTransport = null;
    private Integer requestedbyId = null;
    private Integer authorizedbyId = null;
    private Date issueddate = null;
    private Date deductstartdate = null;
    private Double amount = null;
    private Double deduction = null;
    private Integer deducttime = null;
    private Integer isSigned = null;

    public Xloans(Connection connection) {
        super(connection, "xloans", "xloans_id");
        setColumnNames(new String[]{"xloans_id", "loandate", "is_loan", "is_advance", "is_transport", "requestedby_id", "authorizedby_id", "issueddate", "deductstartdate", "amount", "deduction", "deducttime", "is_signed"});
    }

    public Xloans(Connection connection, Integer xloansId, Date loandate, Integer isLoan, Integer isAdvance, Integer isTransport, Integer requestedbyId, Integer authorizedbyId, Date issueddate, Date deductstartdate, Double amount, Double deduction, Integer deducttime, Integer isSigned) {
        super(connection, "xloans", "xloans_id");
        setNew(xloansId.intValue() <= 0);
//        if (xloansId.intValue() != 0) {
            this.xloansId = xloansId;
//        }
        this.loandate = loandate;
        this.isLoan = isLoan;
        this.isAdvance = isAdvance;
        this.isTransport = isTransport;
        this.requestedbyId = requestedbyId;
        this.authorizedbyId = authorizedbyId;
        this.issueddate = issueddate;
        this.deductstartdate = deductstartdate;
        this.amount = amount;
        this.deduction = deduction;
        this.deducttime = deducttime;
        this.isSigned = isSigned;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xloans xloans = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xloans_id,loandate,is_loan,is_advance,is_transport,requestedby_id,authorizedby_id,issueddate,deductstartdate,amount,deduction,deducttime,is_signed FROM xloans WHERE xloans_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xloans = new Xloans(getConnection());
                xloans.setXloansId(new Integer(rs.getInt(1)));
                xloans.setLoandate(rs.getDate(2));
                xloans.setIsLoan(new Integer(rs.getInt(3)));
                xloans.setIsAdvance(new Integer(rs.getInt(4)));
                xloans.setIsTransport(new Integer(rs.getInt(5)));
                xloans.setRequestedbyId(new Integer(rs.getInt(6)));
                xloans.setAuthorizedbyId(new Integer(rs.getInt(7)));
                xloans.setIssueddate(rs.getDate(8));
                xloans.setDeductstartdate(rs.getDate(9));
                xloans.setAmount(rs.getDouble(10));
                xloans.setDeduction(rs.getDouble(11));
                xloans.setDeducttime(new Integer(rs.getInt(12)));
                xloans.setIsSigned(new Integer(rs.getInt(13)));
                xloans.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xloans;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xloans ("+(getXloansId().intValue()!=0?"xloans_id,":"")+"loandate,is_loan,is_advance,is_transport,requestedby_id,authorizedby_id,issueddate,deductstartdate,amount,deduction,deducttime,is_signed) values("+(getXloansId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXloansId().intValue()!=0) {
                 ps.setObject(++n, getXloansId());
             }
             ps.setObject(++n, getLoandate());
             ps.setObject(++n, getIsLoan());
             ps.setObject(++n, getIsAdvance());
             ps.setObject(++n, getIsTransport());
             ps.setObject(++n, getRequestedbyId());
             ps.setObject(++n, getAuthorizedbyId());
             ps.setObject(++n, getIssueddate());
             ps.setObject(++n, getDeductstartdate());
             ps.setObject(++n, getAmount());
             ps.setObject(++n, getDeduction());
             ps.setObject(++n, getDeducttime());
             ps.setObject(++n, getIsSigned());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXloansId().intValue()==0) {
             stmt = "SELECT max(xloans_id) FROM xloans";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXloansId(new Integer(rs.getInt(1)));
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
                    "UPDATE xloans " +
                    "SET loandate = ?, is_loan = ?, is_advance = ?, is_transport = ?, requestedby_id = ?, authorizedby_id = ?, issueddate = ?, deductstartdate = ?, amount = ?, deduction = ?, deducttime = ?, is_signed = ?" + 
                    " WHERE xloans_id = " + getXloansId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getLoandate());
                ps.setObject(2, getIsLoan());
                ps.setObject(3, getIsAdvance());
                ps.setObject(4, getIsTransport());
                ps.setObject(5, getRequestedbyId());
                ps.setObject(6, getAuthorizedbyId());
                ps.setObject(7, getIssueddate());
                ps.setObject(8, getDeductstartdate());
                ps.setObject(9, getAmount());
                ps.setObject(10, getDeduction());
                ps.setObject(11, getDeducttime());
                ps.setObject(12, getIsSigned());
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
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xloans " +
                "WHERE xloans_id = " + getXloansId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXloansId(new Integer(-getXloansId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXloansId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xloans_id,loandate,is_loan,is_advance,is_transport,requestedby_id,authorizedby_id,issueddate,deductstartdate,amount,deduction,deducttime,is_signed FROM xloans " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xloans(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),rs.getDate(8),rs.getDate(9),rs.getDouble(10),rs.getDouble(11),new Integer(rs.getInt(12)),new Integer(rs.getInt(13))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xloans[] objects = new Xloans[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xloans xloans = (Xloans) lst.get(i);
            objects[i] = xloans;
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
        String stmt = "SELECT xloans_id FROM xloans " +
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
    //    return getXloansId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xloansId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXloansId(id);
        setNew(prevIsNew);
    }

    public Integer getXloansId() {
        return xloansId;
    }

    public void setXloansId(Integer xloansId) throws ForeignKeyViolationException {
        setWasChanged(this.xloansId != null && this.xloansId != xloansId);
        this.xloansId = xloansId;
        setNew(xloansId.intValue() == 0);
    }

    public Date getLoandate() {
        return loandate;
    }

    public void setLoandate(Date loandate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.loandate != null && !this.loandate.equals(loandate));
        this.loandate = loandate;
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

    public Integer getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(Integer isAdvance) throws SQLException, ForeignKeyViolationException {
        if (null != isAdvance)
            isAdvance = isAdvance == 0 ? null : isAdvance;
        setWasChanged(this.isAdvance != null && !this.isAdvance.equals(isAdvance));
        this.isAdvance = isAdvance;
    }

    public Integer getIsTransport() {
        return isTransport;
    }

    public void setIsTransport(Integer isTransport) throws SQLException, ForeignKeyViolationException {
        if (null != isTransport)
            isTransport = isTransport == 0 ? null : isTransport;
        setWasChanged(this.isTransport != null && !this.isTransport.equals(isTransport));
        this.isTransport = isTransport;
    }

    public Integer getRequestedbyId() {
        return requestedbyId;
    }

    public void setRequestedbyId(Integer requestedbyId) throws SQLException, ForeignKeyViolationException {
        if (requestedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + requestedbyId)) {
            throw new ForeignKeyViolationException("Can't set requestedby_id, foreign key violation: xloans_xemployee_fk");
        }
        setWasChanged(this.requestedbyId != null && !this.requestedbyId.equals(requestedbyId));
        this.requestedbyId = requestedbyId;
    }

    public Integer getAuthorizedbyId() {
        return authorizedbyId;
    }

    public void setAuthorizedbyId(Integer authorizedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != authorizedbyId)
            authorizedbyId = authorizedbyId == 0 ? null : authorizedbyId;
        if (authorizedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + authorizedbyId)) {
            throw new ForeignKeyViolationException("Can't set authorizedby_id, foreign key violation: xloans_xemployee_fk2");
        }
        setWasChanged(this.authorizedbyId != null && !this.authorizedbyId.equals(authorizedbyId));
        this.authorizedbyId = authorizedbyId;
    }

    public Date getIssueddate() {
        return issueddate;
    }

    public void setIssueddate(Date issueddate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.issueddate != null && !this.issueddate.equals(issueddate));
        this.issueddate = issueddate;
    }

    public Date getDeductstartdate() {
        return deductstartdate;
    }

    public void setDeductstartdate(Date deductstartdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.deductstartdate != null && !this.deductstartdate.equals(deductstartdate));
        this.deductstartdate = deductstartdate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.amount != null && !this.amount.equals(amount));
        this.amount = amount;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.deduction != null && !this.deduction.equals(deduction));
        this.deduction = deduction;
    }

    public Integer getDeducttime() {
        return deducttime;
    }

    public void setDeducttime(Integer deducttime) throws SQLException, ForeignKeyViolationException {
        if (null != deducttime)
            deducttime = deducttime == 0 ? null : deducttime;
        setWasChanged(this.deducttime != null && !this.deducttime.equals(deducttime));
        this.deducttime = deducttime;
    }

    public Integer getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(Integer isSigned) throws SQLException, ForeignKeyViolationException {
        if (null != isSigned)
            isSigned = isSigned == 0 ? null : isSigned;
        setWasChanged(this.isSigned != null && !this.isSigned.equals(isSigned));
        this.isSigned = isSigned;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[13];
        columnValues[0] = getXloansId();
        columnValues[1] = getLoandate();
        columnValues[2] = getIsLoan();
        columnValues[3] = getIsAdvance();
        columnValues[4] = getIsTransport();
        columnValues[5] = getRequestedbyId();
        columnValues[6] = getAuthorizedbyId();
        columnValues[7] = getIssueddate();
        columnValues[8] = getDeductstartdate();
        columnValues[9] = getAmount();
        columnValues[10] = getDeduction();
        columnValues[11] = getDeducttime();
        columnValues[12] = getIsSigned();
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
            setXloansId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXloansId(null);
        }
        setLoandate(toDate(flds[1]));
        try {
            setIsLoan(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setIsLoan(null);
        }
        try {
            setIsAdvance(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setIsAdvance(null);
        }
        try {
            setIsTransport(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setIsTransport(null);
        }
        try {
            setRequestedbyId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setRequestedbyId(null);
        }
        try {
            setAuthorizedbyId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setAuthorizedbyId(null);
        }
        setIssueddate(toDate(flds[7]));
        setDeductstartdate(toDate(flds[8]));
        try {
            setAmount(Double.parseDouble(flds[9]));
        } catch(NumberFormatException ne) {
            setAmount(null);
        }
        try {
            setDeduction(Double.parseDouble(flds[10]));
        } catch(NumberFormatException ne) {
            setDeduction(null);
        }
        try {
            setDeducttime(Integer.parseInt(flds[11]));
        } catch(NumberFormatException ne) {
            setDeducttime(null);
        }
        try {
            setIsSigned(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setIsSigned(null);
        }
    }
}
