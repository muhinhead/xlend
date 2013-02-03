// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Feb 03 16:35:48 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xbookouts extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xbookoutsId = null;
    private Integer xpartsId = null;
    private Date issueDate = null;
    private Integer xsiteId = null;
    private Integer xmachineId = null;
    private Integer issuedbyId = null;
    private Integer issuedtoId = null;
    private Double quantity = null;

    public Xbookouts(Connection connection) {
        super(connection, "xbookouts", "xbookouts_id");
        setColumnNames(new String[]{"xbookouts_id", "xparts_id", "issue_date", "xsite_id", "xmachine_id", "issuedby_id", "issuedto_id", "quantity"});
    }

    public Xbookouts(Connection connection, Integer xbookoutsId, Integer xpartsId, Date issueDate, Integer xsiteId, Integer xmachineId, Integer issuedbyId, Integer issuedtoId, Double quantity) {
        super(connection, "xbookouts", "xbookouts_id");
        setNew(xbookoutsId.intValue() <= 0);
//        if (xbookoutsId.intValue() != 0) {
            this.xbookoutsId = xbookoutsId;
//        }
        this.xpartsId = xpartsId;
        this.issueDate = issueDate;
        this.xsiteId = xsiteId;
        this.xmachineId = xmachineId;
        this.issuedbyId = issuedbyId;
        this.issuedtoId = issuedtoId;
        this.quantity = quantity;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xbookouts xbookouts = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbookouts_id,xparts_id,issue_date,xsite_id,xmachine_id,issuedby_id,issuedto_id,quantity FROM xbookouts WHERE xbookouts_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xbookouts = new Xbookouts(getConnection());
                xbookouts.setXbookoutsId(new Integer(rs.getInt(1)));
                xbookouts.setXpartsId(new Integer(rs.getInt(2)));
                xbookouts.setIssueDate(rs.getDate(3));
                xbookouts.setXsiteId(new Integer(rs.getInt(4)));
                xbookouts.setXmachineId(new Integer(rs.getInt(5)));
                xbookouts.setIssuedbyId(new Integer(rs.getInt(6)));
                xbookouts.setIssuedtoId(new Integer(rs.getInt(7)));
                xbookouts.setQuantity(rs.getDouble(8));
                xbookouts.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xbookouts;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xbookouts ("+(getXbookoutsId().intValue()!=0?"xbookouts_id,":"")+"xparts_id,issue_date,xsite_id,xmachine_id,issuedby_id,issuedto_id,quantity) values("+(getXbookoutsId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXbookoutsId().intValue()!=0) {
                 ps.setObject(++n, getXbookoutsId());
             }
             ps.setObject(++n, getXpartsId());
             ps.setObject(++n, getIssueDate());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getIssuedbyId());
             ps.setObject(++n, getIssuedtoId());
             ps.setObject(++n, getQuantity());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXbookoutsId().intValue()==0) {
             stmt = "SELECT max(xbookouts_id) FROM xbookouts";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXbookoutsId(new Integer(rs.getInt(1)));
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
                    "UPDATE xbookouts " +
                    "SET xparts_id = ?, issue_date = ?, xsite_id = ?, xmachine_id = ?, issuedby_id = ?, issuedto_id = ?, quantity = ?" + 
                    " WHERE xbookouts_id = " + getXbookoutsId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXpartsId());
                ps.setObject(2, getIssueDate());
                ps.setObject(3, getXsiteId());
                ps.setObject(4, getXmachineId());
                ps.setObject(5, getIssuedbyId());
                ps.setObject(6, getIssuedtoId());
                ps.setObject(7, getQuantity());
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
                "DELETE FROM xbookouts " +
                "WHERE xbookouts_id = " + getXbookoutsId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXbookoutsId(new Integer(-getXbookoutsId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXbookoutsId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbookouts_id,xparts_id,issue_date,xsite_id,xmachine_id,issuedby_id,issuedto_id,quantity FROM xbookouts " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xbookouts(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),rs.getDouble(8)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xbookouts[] objects = new Xbookouts[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xbookouts xbookouts = (Xbookouts) lst.get(i);
            objects[i] = xbookouts;
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
        String stmt = "SELECT xbookouts_id FROM xbookouts " +
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
    //    return getXbookoutsId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xbookoutsId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXbookoutsId(id);
        setNew(prevIsNew);
    }

    public Integer getXbookoutsId() {
        return xbookoutsId;
    }

    public void setXbookoutsId(Integer xbookoutsId) throws ForeignKeyViolationException {
        setWasChanged(this.xbookoutsId != null && this.xbookoutsId != xbookoutsId);
        this.xbookoutsId = xbookoutsId;
        setNew(xbookoutsId.intValue() == 0);
    }

    public Integer getXpartsId() {
        return xpartsId;
    }

    public void setXpartsId(Integer xpartsId) throws SQLException, ForeignKeyViolationException {
        if (xpartsId!=null && !Xparts.exists(getConnection(),"xparts_id = " + xpartsId)) {
            throw new ForeignKeyViolationException("Can't set xparts_id, foreign key violation: xbookouts_xparts_fk");
        }
        setWasChanged(this.xpartsId != null && !this.xpartsId.equals(xpartsId));
        this.xpartsId = xpartsId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.issueDate != null && !this.issueDate.equals(issueDate));
        this.issueDate = issueDate;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xbookouts_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getIssuedbyId() {
        return issuedbyId;
    }

    public void setIssuedbyId(Integer issuedbyId) throws SQLException, ForeignKeyViolationException {
        if (issuedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedbyId)) {
            throw new ForeignKeyViolationException("Can't set issuedby_id, foreign key violation: xbookouts_xemployee_fk");
        }
        setWasChanged(this.issuedbyId != null && !this.issuedbyId.equals(issuedbyId));
        this.issuedbyId = issuedbyId;
    }

    public Integer getIssuedtoId() {
        return issuedtoId;
    }

    public void setIssuedtoId(Integer issuedtoId) throws SQLException, ForeignKeyViolationException {
        if (issuedtoId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedtoId)) {
            throw new ForeignKeyViolationException("Can't set issuedto_id, foreign key violation: xbookouts_xemployee_fk2");
        }
        setWasChanged(this.issuedtoId != null && !this.issuedtoId.equals(issuedtoId));
        this.issuedtoId = issuedtoId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.quantity != null && !this.quantity.equals(quantity));
        this.quantity = quantity;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getXbookoutsId();
        columnValues[1] = getXpartsId();
        columnValues[2] = getIssueDate();
        columnValues[3] = getXsiteId();
        columnValues[4] = getXmachineId();
        columnValues[5] = getIssuedbyId();
        columnValues[6] = getIssuedtoId();
        columnValues[7] = getQuantity();
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
            setXbookoutsId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXbookoutsId(null);
        }
        try {
            setXpartsId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXpartsId(null);
        }
        setIssueDate(toDate(flds[2]));
        try {
            setXsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setIssuedbyId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setIssuedbyId(null);
        }
        try {
            setIssuedtoId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setIssuedtoId(null);
        }
        try {
            setQuantity(Double.parseDouble(flds[7]));
        } catch(NumberFormatException ne) {
            setQuantity(null);
        }
    }
}
