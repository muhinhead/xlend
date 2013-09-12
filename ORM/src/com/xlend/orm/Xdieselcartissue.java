// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Sep 12 08:40:41 FET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xdieselcartissue extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xdieselcartissueId = null;
    private Date issueDate = null;
    private Integer driverId = null;
    private Integer xdieselcartId = null;
    private Double liters = null;
    private Integer xsupplierId = null;

    public Xdieselcartissue(Connection connection) {
        super(connection, "xdieselcartissue", "xdieselcartissue_id");
        setColumnNames(new String[]{"xdieselcartissue_id", "issue_date", "driver_id", "xdieselcart_id", "liters", "xsupplier_id"});
    }

    public Xdieselcartissue(Connection connection, Integer xdieselcartissueId, Date issueDate, Integer driverId, Integer xdieselcartId, Double liters, Integer xsupplierId) {
        super(connection, "xdieselcartissue", "xdieselcartissue_id");
        setNew(xdieselcartissueId.intValue() <= 0);
//        if (xdieselcartissueId.intValue() != 0) {
            this.xdieselcartissueId = xdieselcartissueId;
//        }
        this.issueDate = issueDate;
        this.driverId = driverId;
        this.xdieselcartId = xdieselcartId;
        this.liters = liters;
        this.xsupplierId = xsupplierId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xdieselcartissue xdieselcartissue = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselcartissue_id,issue_date,driver_id,xdieselcart_id,liters,xsupplier_id FROM xdieselcartissue WHERE xdieselcartissue_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xdieselcartissue = new Xdieselcartissue(getConnection());
                xdieselcartissue.setXdieselcartissueId(new Integer(rs.getInt(1)));
                xdieselcartissue.setIssueDate(rs.getDate(2));
                xdieselcartissue.setDriverId(new Integer(rs.getInt(3)));
                xdieselcartissue.setXdieselcartId(new Integer(rs.getInt(4)));
                xdieselcartissue.setLiters(rs.getDouble(5));
                xdieselcartissue.setXsupplierId(new Integer(rs.getInt(6)));
                xdieselcartissue.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xdieselcartissue;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xdieselcartissue ("+(getXdieselcartissueId().intValue()!=0?"xdieselcartissue_id,":"")+"issue_date,driver_id,xdieselcart_id,liters,xsupplier_id) values("+(getXdieselcartissueId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXdieselcartissueId().intValue()!=0) {
                 ps.setObject(++n, getXdieselcartissueId());
             }
             ps.setObject(++n, getIssueDate());
             ps.setObject(++n, getDriverId());
             ps.setObject(++n, getXdieselcartId());
             ps.setObject(++n, getLiters());
             ps.setObject(++n, getXsupplierId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXdieselcartissueId().intValue()==0) {
             stmt = "SELECT max(xdieselcartissue_id) FROM xdieselcartissue";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXdieselcartissueId(new Integer(rs.getInt(1)));
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
                    "UPDATE xdieselcartissue " +
                    "SET issue_date = ?, driver_id = ?, xdieselcart_id = ?, liters = ?, xsupplier_id = ?" + 
                    " WHERE xdieselcartissue_id = " + getXdieselcartissueId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getIssueDate());
                ps.setObject(2, getDriverId());
                ps.setObject(3, getXdieselcartId());
                ps.setObject(4, getLiters());
                ps.setObject(5, getXsupplierId());
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
                "DELETE FROM xdieselcartissue " +
                "WHERE xdieselcartissue_id = " + getXdieselcartissueId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXdieselcartissueId(new Integer(-getXdieselcartissueId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXdieselcartissueId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselcartissue_id,issue_date,driver_id,xdieselcart_id,liters,xsupplier_id FROM xdieselcartissue " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xdieselcartissue(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDouble(5),new Integer(rs.getInt(6))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xdieselcartissue[] objects = new Xdieselcartissue[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xdieselcartissue xdieselcartissue = (Xdieselcartissue) lst.get(i);
            objects[i] = xdieselcartissue;
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
        String stmt = "SELECT xdieselcartissue_id FROM xdieselcartissue " +
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
    //    return getXdieselcartissueId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xdieselcartissueId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXdieselcartissueId(id);
        setNew(prevIsNew);
    }

    public Integer getXdieselcartissueId() {
        return xdieselcartissueId;
    }

    public void setXdieselcartissueId(Integer xdieselcartissueId) throws ForeignKeyViolationException {
        setWasChanged(this.xdieselcartissueId != null && this.xdieselcartissueId != xdieselcartissueId);
        this.xdieselcartissueId = xdieselcartissueId;
        setNew(xdieselcartissueId.intValue() == 0);
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.issueDate != null && !this.issueDate.equals(issueDate));
        this.issueDate = issueDate;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) throws SQLException, ForeignKeyViolationException {
        if (driverId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + driverId)) {
            throw new ForeignKeyViolationException("Can't set driver_id, foreign key violation: xdieselcartissue_xemployee_fk");
        }
        setWasChanged(this.driverId != null && !this.driverId.equals(driverId));
        this.driverId = driverId;
    }

    public Integer getXdieselcartId() {
        return xdieselcartId;
    }

    public void setXdieselcartId(Integer xdieselcartId) throws SQLException, ForeignKeyViolationException {
        if (xdieselcartId!=null && !Xdieselcart.exists(getConnection(),"xdieselcart_id = " + xdieselcartId)) {
            throw new ForeignKeyViolationException("Can't set xdieselcart_id, foreign key violation: xdieselcartissue_xdieselcart_fk");
        }
        setWasChanged(this.xdieselcartId != null && !this.xdieselcartId.equals(xdieselcartId));
        this.xdieselcartId = xdieselcartId;
    }

    public Double getLiters() {
        return liters;
    }

    public void setLiters(Double liters) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.liters != null && !this.liters.equals(liters));
        this.liters = liters;
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xdieselcartissue_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXdieselcartissueId();
        columnValues[1] = getIssueDate();
        columnValues[2] = getDriverId();
        columnValues[3] = getXdieselcartId();
        columnValues[4] = getLiters();
        columnValues[5] = getXsupplierId();
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
            setXdieselcartissueId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXdieselcartissueId(null);
        }
        setIssueDate(toDate(flds[1]));
        try {
            setDriverId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setDriverId(null);
        }
        try {
            setXdieselcartId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXdieselcartId(null);
        }
        try {
            setLiters(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setLiters(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
    }
}
