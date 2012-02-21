// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Feb 21 16:24:18 EET 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmachineonsite extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmachineonsateId = null;
    private Integer xsiteId = null;
    private Integer xmachineId = null;
    private Integer xemployeeId = null;
    private Date estdate = null;
    private Date deestdate = null;

    public Xmachineonsite(Connection connection) {
        super(connection, "xmachineonsite", "xmachineonsate_id");
        setColumnNames(new String[]{"xmachineonsate_id", "xsite_id", "xmachine_id", "xemployee_id", "estdate", "deestdate"});
    }

    public Xmachineonsite(Connection connection, Integer xmachineonsateId, Integer xsiteId, Integer xmachineId, Integer xemployeeId, Date estdate, Date deestdate) {
        super(connection, "xmachineonsite", "xmachineonsate_id");
        setNew(xmachineonsateId.intValue() <= 0);
//        if (xmachineonsateId.intValue() != 0) {
            this.xmachineonsateId = xmachineonsateId;
//        }
        this.xsiteId = xsiteId;
        this.xmachineId = xmachineId;
        this.xemployeeId = xemployeeId;
        this.estdate = estdate;
        this.deestdate = deestdate;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmachineonsite xmachineonsite = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachineonsate_id,xsite_id,xmachine_id,xemployee_id,estdate,deestdate FROM xmachineonsite WHERE xmachineonsate_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachineonsite = new Xmachineonsite(getConnection());
                xmachineonsite.setXmachineonsateId(new Integer(rs.getInt(1)));
                xmachineonsite.setXsiteId(new Integer(rs.getInt(2)));
                xmachineonsite.setXmachineId(new Integer(rs.getInt(3)));
                xmachineonsite.setXemployeeId(new Integer(rs.getInt(4)));
                xmachineonsite.setEstdate(rs.getDate(5));
                xmachineonsite.setDeestdate(rs.getDate(6));
                xmachineonsite.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmachineonsite;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmachineonsite ("+(getXmachineonsateId().intValue()!=0?"xmachineonsate_id,":"")+"xsite_id,xmachine_id,xemployee_id,estdate,deestdate) values("+(getXmachineonsateId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachineonsateId().intValue()!=0) {
                 ps.setObject(++n, getXmachineonsateId());
             }
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getXemployeeId());
             ps.setObject(++n, getEstdate());
             ps.setObject(++n, getDeestdate());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmachineonsateId().intValue()==0) {
             stmt = "SELECT max(xmachineonsate_id) FROM xmachineonsite";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmachineonsateId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmachineonsite " +
                    "SET xsite_id = ?, xmachine_id = ?, xemployee_id = ?, estdate = ?, deestdate = ?" + 
                    " WHERE xmachineonsate_id = " + getXmachineonsateId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsiteId());
                ps.setObject(2, getXmachineId());
                ps.setObject(3, getXemployeeId());
                ps.setObject(4, getEstdate());
                ps.setObject(5, getDeestdate());
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
                "DELETE FROM xmachineonsite " +
                "WHERE xmachineonsate_id = " + getXmachineonsateId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmachineonsateId(new Integer(-getXmachineonsateId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmachineonsateId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachineonsate_id,xsite_id,xmachine_id,xemployee_id,estdate,deestdate FROM xmachineonsite " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachineonsite(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDate(5),rs.getDate(6)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmachineonsite[] objects = new Xmachineonsite[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmachineonsite xmachineonsite = (Xmachineonsite) lst.get(i);
            objects[i] = xmachineonsite;
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
        String stmt = "SELECT xmachineonsate_id FROM xmachineonsite " +
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
    //    return getXmachineonsateId() + getDelimiter();
    //}

    public Integer getXmachineonsateId() {
        return xmachineonsateId;
    }

    public void setXmachineonsateId(Integer xmachineonsateId) throws ForeignKeyViolationException {
        setWasChanged(this.xmachineonsateId != null && this.xmachineonsateId != xmachineonsateId);
        this.xmachineonsateId = xmachineonsateId;
        setNew(xmachineonsateId.intValue() == 0);
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xmachineonsite_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xmachineonsite_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (null != xemployeeId)
            xemployeeId = xemployeeId == 0 ? null : xemployeeId;
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xmachineonsite_xemployee_fk");
        }
        setWasChanged(this.xemployeeId != null && !this.xemployeeId.equals(xemployeeId));
        this.xemployeeId = xemployeeId;
    }

    public Date getEstdate() {
        return estdate;
    }

    public void setEstdate(Date estdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.estdate != null && !this.estdate.equals(estdate));
        this.estdate = estdate;
    }

    public Date getDeestdate() {
        return deestdate;
    }

    public void setDeestdate(Date deestdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.deestdate != null && !this.deestdate.equals(deestdate));
        this.deestdate = deestdate;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXmachineonsateId();
        columnValues[1] = getXsiteId();
        columnValues[2] = getXmachineId();
        columnValues[3] = getXemployeeId();
        columnValues[4] = getEstdate();
        columnValues[5] = getDeestdate();
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
            setXmachineonsateId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmachineonsateId(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setXemployeeId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
        setEstdate(toDate(flds[4]));
        setDeestdate(toDate(flds[5]));
    }
}
