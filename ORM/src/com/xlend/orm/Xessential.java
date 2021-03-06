// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xessential extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xessentialId = null;
    private Date issueDate = null;
    private Date returnDate = null;
    private Date redressDate = null;
    private Double redressAmt = null;
    private String essential = null;
    private Integer driverId = null;
    private Integer issuedBy = null;
    private Integer receivedBy = null;

    public Xessential(Connection connection) {
        super(connection, "xessential", "xessential_id");
        setColumnNames(new String[]{"xessential_id", "issue_date", "return_date", "redress_date", "redress_amt", "essential", "driver_id", "issued_by", "received_by"});
    }

    public Xessential(Connection connection, Integer xessentialId, Date issueDate, Date returnDate, Date redressDate, Double redressAmt, String essential, Integer driverId, Integer issuedBy, Integer receivedBy) {
        super(connection, "xessential", "xessential_id");
        setNew(xessentialId.intValue() <= 0);
//        if (xessentialId.intValue() != 0) {
            this.xessentialId = xessentialId;
//        }
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.redressDate = redressDate;
        this.redressAmt = redressAmt;
        this.essential = essential;
        this.driverId = driverId;
        this.issuedBy = issuedBy;
        this.receivedBy = receivedBy;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xessential xessential = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xessential_id,issue_date,return_date,redress_date,redress_amt,essential,driver_id,issued_by,received_by FROM xessential WHERE xessential_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xessential = new Xessential(getConnection());
                xessential.setXessentialId(new Integer(rs.getInt(1)));
                xessential.setIssueDate(rs.getDate(2));
                xessential.setReturnDate(rs.getDate(3));
                xessential.setRedressDate(rs.getDate(4));
                xessential.setRedressAmt(rs.getDouble(5));
                xessential.setEssential(rs.getString(6));
                xessential.setDriverId(new Integer(rs.getInt(7)));
                xessential.setIssuedBy(new Integer(rs.getInt(8)));
                xessential.setReceivedBy(new Integer(rs.getInt(9)));
                xessential.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xessential;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xessential ("+(getXessentialId().intValue()!=0?"xessential_id,":"")+"issue_date,return_date,redress_date,redress_amt,essential,driver_id,issued_by,received_by) values("+(getXessentialId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXessentialId().intValue()!=0) {
                 ps.setObject(++n, getXessentialId());
             }
             ps.setObject(++n, getIssueDate());
             ps.setObject(++n, getReturnDate());
             ps.setObject(++n, getRedressDate());
             ps.setObject(++n, getRedressAmt());
             ps.setObject(++n, getEssential());
             ps.setObject(++n, getDriverId());
             ps.setObject(++n, getIssuedBy());
             ps.setObject(++n, getReceivedBy());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXessentialId().intValue()==0) {
             stmt = "SELECT max(xessential_id) FROM xessential";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXessentialId(new Integer(rs.getInt(1)));
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
                    "UPDATE xessential " +
                    "SET issue_date = ?, return_date = ?, redress_date = ?, redress_amt = ?, essential = ?, driver_id = ?, issued_by = ?, received_by = ?" + 
                    " WHERE xessential_id = " + getXessentialId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getIssueDate());
                ps.setObject(2, getReturnDate());
                ps.setObject(3, getRedressDate());
                ps.setObject(4, getRedressAmt());
                ps.setObject(5, getEssential());
                ps.setObject(6, getDriverId());
                ps.setObject(7, getIssuedBy());
                ps.setObject(8, getReceivedBy());
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
                "DELETE FROM xessential " +
                "WHERE xessential_id = " + getXessentialId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXessentialId(new Integer(-getXessentialId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXessentialId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xessential_id,issue_date,return_date,redress_date,redress_amt,essential,driver_id,issued_by,received_by FROM xessential " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xessential(con,new Integer(rs.getInt(1)),rs.getDate(2),rs.getDate(3),rs.getDate(4),rs.getDouble(5),rs.getString(6),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),new Integer(rs.getInt(9))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xessential[] objects = new Xessential[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xessential xessential = (Xessential) lst.get(i);
            objects[i] = xessential;
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
        String stmt = "SELECT xessential_id FROM xessential " +
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
    //    return getXessentialId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xessentialId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXessentialId(id);
        setNew(prevIsNew);
    }

    public Integer getXessentialId() {
        return xessentialId;
    }

    public void setXessentialId(Integer xessentialId) throws ForeignKeyViolationException {
        setWasChanged(this.xessentialId != null && this.xessentialId != xessentialId);
        this.xessentialId = xessentialId;
        setNew(xessentialId.intValue() == 0);
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.issueDate != null && !this.issueDate.equals(issueDate));
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.returnDate != null && !this.returnDate.equals(returnDate));
        this.returnDate = returnDate;
    }

    public Date getRedressDate() {
        return redressDate;
    }

    public void setRedressDate(Date redressDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.redressDate != null && !this.redressDate.equals(redressDate));
        this.redressDate = redressDate;
    }

    public Double getRedressAmt() {
        return redressAmt;
    }

    public void setRedressAmt(Double redressAmt) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.redressAmt != null && !this.redressAmt.equals(redressAmt));
        this.redressAmt = redressAmt;
    }

    public String getEssential() {
        return essential;
    }

    public void setEssential(String essential) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.essential != null && !this.essential.equals(essential));
        this.essential = essential;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) throws SQLException, ForeignKeyViolationException {
        if (driverId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + driverId)) {
            throw new ForeignKeyViolationException("Can't set driver_id, foreign key violation: xessential_xemployee_fk");
        }
        setWasChanged(this.driverId != null && !this.driverId.equals(driverId));
        this.driverId = driverId;
    }

    public Integer getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Integer issuedBy) throws SQLException, ForeignKeyViolationException {
        if (issuedBy!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedBy)) {
            throw new ForeignKeyViolationException("Can't set issued_by, foreign key violation: xessential_xemployee_fk1");
        }
        setWasChanged(this.issuedBy != null && !this.issuedBy.equals(issuedBy));
        this.issuedBy = issuedBy;
    }

    public Integer getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Integer receivedBy) throws SQLException, ForeignKeyViolationException {
        if (null != receivedBy)
            receivedBy = receivedBy == 0 ? null : receivedBy;
        if (receivedBy!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + receivedBy)) {
            throw new ForeignKeyViolationException("Can't set received_by, foreign key violation: xessential_xemployee_fk2");
        }
        setWasChanged(this.receivedBy != null && !this.receivedBy.equals(receivedBy));
        this.receivedBy = receivedBy;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[9];
        columnValues[0] = getXessentialId();
        columnValues[1] = getIssueDate();
        columnValues[2] = getReturnDate();
        columnValues[3] = getRedressDate();
        columnValues[4] = getRedressAmt();
        columnValues[5] = getEssential();
        columnValues[6] = getDriverId();
        columnValues[7] = getIssuedBy();
        columnValues[8] = getReceivedBy();
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
            setXessentialId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXessentialId(null);
        }
        setIssueDate(toDate(flds[1]));
        setReturnDate(toDate(flds[2]));
        setRedressDate(toDate(flds[3]));
        try {
            setRedressAmt(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setRedressAmt(null);
        }
        setEssential(flds[5]);
        try {
            setDriverId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setDriverId(null);
        }
        try {
            setIssuedBy(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setIssuedBy(null);
        }
        try {
            setReceivedBy(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setReceivedBy(null);
        }
    }
}
