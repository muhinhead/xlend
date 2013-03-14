// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Mar 14 10:11:37 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xbateryissue extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xbateryissueId = null;
    private Date issueDate = null;
    private Date entryDate = null;
    private Integer issuedBy = null;
    private Integer issuedTo = null;
    private Integer xmachineId = null;

    public Xbateryissue(Connection connection) {
        super(connection, "xbateryissue", "xbateryissue_id");
        setColumnNames(new String[]{"xbateryissue_id", "issue_date", "entry_date", "issued_by", "issued_to", "xmachine_id"});
    }

    public Xbateryissue(Connection connection, Integer xbateryissueId, Date issueDate, Date entryDate, Integer issuedBy, Integer issuedTo, Integer xmachineId) {
        super(connection, "xbateryissue", "xbateryissue_id");
        setNew(xbateryissueId.intValue() <= 0);
//        if (xbateryissueId.intValue() != 0) {
            this.xbateryissueId = xbateryissueId;
//        }
        this.issueDate = issueDate;
        this.entryDate = entryDate;
        this.issuedBy = issuedBy;
        this.issuedTo = issuedTo;
        this.xmachineId = xmachineId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xbateryissue xbateryissue = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbateryissue_id,issue_date,entry_date,issued_by,issued_to,xmachine_id FROM xbateryissue WHERE xbateryissue_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xbateryissue = new Xbateryissue(getConnection());
                xbateryissue.setXbateryissueId(new Integer(rs.getInt(1)));
                xbateryissue.setIssueDate(rs.getDate(2));
                xbateryissue.setEntryDate(rs.getDate(3));
                xbateryissue.setIssuedBy(new Integer(rs.getInt(4)));
                xbateryissue.setIssuedTo(new Integer(rs.getInt(5)));
                xbateryissue.setXmachineId(new Integer(rs.getInt(6)));
                xbateryissue.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xbateryissue;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xbateryissue ("+(getXbateryissueId().intValue()!=0?"xbateryissue_id,":"")+"issue_date,entry_date,issued_by,issued_to,xmachine_id) values("+(getXbateryissueId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXbateryissueId().intValue()!=0) {
                 ps.setObject(++n, getXbateryissueId());
             }
             ps.setObject(++n, getIssueDate());
             ps.setObject(++n, getEntryDate());
             ps.setObject(++n, getIssuedBy());
             ps.setObject(++n, getIssuedTo());
             ps.setObject(++n, getXmachineId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXbateryissueId().intValue()==0) {
             stmt = "SELECT max(xbateryissue_id) FROM xbateryissue";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXbateryissueId(new Integer(rs.getInt(1)));
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
                    "UPDATE xbateryissue " +
                    "SET issue_date = ?, entry_date = ?, issued_by = ?, issued_to = ?, xmachine_id = ?" + 
                    " WHERE xbateryissue_id = " + getXbateryissueId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getIssueDate());
                ps.setObject(2, getEntryDate());
                ps.setObject(3, getIssuedBy());
                ps.setObject(4, getIssuedTo());
                ps.setObject(5, getXmachineId());
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
        if (Xbattery.exists(getConnection(),"xbateryissue_id = " + getXbateryissueId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbattery_xbateryissue_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xbateryissue " +
                "WHERE xbateryissue_id = " + getXbateryissueId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXbateryissueId(new Integer(-getXbateryissueId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXbateryissueId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbateryissue_id,issue_date,entry_date,issued_by,issued_to,xmachine_id FROM xbateryissue " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xbateryissue(con,new Integer(rs.getInt(1)),rs.getDate(2),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xbateryissue[] objects = new Xbateryissue[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xbateryissue xbateryissue = (Xbateryissue) lst.get(i);
            objects[i] = xbateryissue;
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
        String stmt = "SELECT xbateryissue_id FROM xbateryissue " +
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
    //    return getXbateryissueId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xbateryissueId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXbateryissueId(id);
        setNew(prevIsNew);
    }

    public Integer getXbateryissueId() {
        return xbateryissueId;
    }

    public void setXbateryissueId(Integer xbateryissueId) throws ForeignKeyViolationException {
        setWasChanged(this.xbateryissueId != null && this.xbateryissueId != xbateryissueId);
        this.xbateryissueId = xbateryissueId;
        setNew(xbateryissueId.intValue() == 0);
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.issueDate != null && !this.issueDate.equals(issueDate));
        this.issueDate = issueDate;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.entryDate != null && !this.entryDate.equals(entryDate));
        this.entryDate = entryDate;
    }

    public Integer getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Integer issuedBy) throws SQLException, ForeignKeyViolationException {
        if (issuedBy!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedBy)) {
            throw new ForeignKeyViolationException("Can't set issued_by, foreign key violation: xbateryissue_xemployee_fk");
        }
        setWasChanged(this.issuedBy != null && !this.issuedBy.equals(issuedBy));
        this.issuedBy = issuedBy;
    }

    public Integer getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(Integer issuedTo) throws SQLException, ForeignKeyViolationException {
        if (issuedTo!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedTo)) {
            throw new ForeignKeyViolationException("Can't set issued_to, foreign key violation: xbateryissue_xemployee_fk2");
        }
        setWasChanged(this.issuedTo != null && !this.issuedTo.equals(issuedTo));
        this.issuedTo = issuedTo;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xbateryissue_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXbateryissueId();
        columnValues[1] = getIssueDate();
        columnValues[2] = getEntryDate();
        columnValues[3] = getIssuedBy();
        columnValues[4] = getIssuedTo();
        columnValues[5] = getXmachineId();
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
            setXbateryissueId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXbateryissueId(null);
        }
        setIssueDate(toDate(flds[1]));
        setEntryDate(toDate(flds[2]));
        try {
            setIssuedBy(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setIssuedBy(null);
        }
        try {
            setIssuedTo(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setIssuedTo(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
    }
}
