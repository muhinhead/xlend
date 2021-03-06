// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmachineorderitm extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmachineorderitmId = null;
    private Integer xmachineorderId = null;
    private Integer xmachineId = null;
    private Integer xemployeeId = null;

    public Xmachineorderitm(Connection connection) {
        super(connection, "xmachineorderitm", "xmachineorderitm_id");
        setColumnNames(new String[]{"xmachineorderitm_id", "xmachineorder_id", "xmachine_id", "xemployee_id"});
    }

    public Xmachineorderitm(Connection connection, Integer xmachineorderitmId, Integer xmachineorderId, Integer xmachineId, Integer xemployeeId) {
        super(connection, "xmachineorderitm", "xmachineorderitm_id");
        setNew(xmachineorderitmId.intValue() <= 0);
//        if (xmachineorderitmId.intValue() != 0) {
            this.xmachineorderitmId = xmachineorderitmId;
//        }
        this.xmachineorderId = xmachineorderId;
        this.xmachineId = xmachineId;
        this.xemployeeId = xemployeeId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmachineorderitm xmachineorderitm = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachineorderitm_id,xmachineorder_id,xmachine_id,xemployee_id FROM xmachineorderitm WHERE xmachineorderitm_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachineorderitm = new Xmachineorderitm(getConnection());
                xmachineorderitm.setXmachineorderitmId(new Integer(rs.getInt(1)));
                xmachineorderitm.setXmachineorderId(new Integer(rs.getInt(2)));
                xmachineorderitm.setXmachineId(new Integer(rs.getInt(3)));
                xmachineorderitm.setXemployeeId(new Integer(rs.getInt(4)));
                xmachineorderitm.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmachineorderitm;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmachineorderitm ("+(getXmachineorderitmId().intValue()!=0?"xmachineorderitm_id,":"")+"xmachineorder_id,xmachine_id,xemployee_id) values("+(getXmachineorderitmId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachineorderitmId().intValue()!=0) {
                 ps.setObject(++n, getXmachineorderitmId());
             }
             ps.setObject(++n, getXmachineorderId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getXemployeeId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmachineorderitmId().intValue()==0) {
             stmt = "SELECT max(xmachineorderitm_id) FROM xmachineorderitm";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmachineorderitmId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmachineorderitm " +
                    "SET xmachineorder_id = ?, xmachine_id = ?, xemployee_id = ?" + 
                    " WHERE xmachineorderitm_id = " + getXmachineorderitmId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXmachineorderId());
                ps.setObject(2, getXmachineId());
                ps.setObject(3, getXemployeeId());
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
                "DELETE FROM xmachineorderitm " +
                "WHERE xmachineorderitm_id = " + getXmachineorderitmId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmachineorderitmId(new Integer(-getXmachineorderitmId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmachineorderitmId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachineorderitm_id,xmachineorder_id,xmachine_id,xemployee_id FROM xmachineorderitm " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachineorderitm(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmachineorderitm[] objects = new Xmachineorderitm[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmachineorderitm xmachineorderitm = (Xmachineorderitm) lst.get(i);
            objects[i] = xmachineorderitm;
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
        String stmt = "SELECT xmachineorderitm_id FROM xmachineorderitm " +
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
    //    return getXmachineorderitmId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xmachineorderitmId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXmachineorderitmId(id);
        setNew(prevIsNew);
    }

    public Integer getXmachineorderitmId() {
        return xmachineorderitmId;
    }

    public void setXmachineorderitmId(Integer xmachineorderitmId) throws ForeignKeyViolationException {
        setWasChanged(this.xmachineorderitmId != null && this.xmachineorderitmId != xmachineorderitmId);
        this.xmachineorderitmId = xmachineorderitmId;
        setNew(xmachineorderitmId.intValue() == 0);
    }

    public Integer getXmachineorderId() {
        return xmachineorderId;
    }

    public void setXmachineorderId(Integer xmachineorderId) throws SQLException, ForeignKeyViolationException {
        if (xmachineorderId!=null && !Xmachineorder.exists(getConnection(),"xmachineorder_id = " + xmachineorderId)) {
            throw new ForeignKeyViolationException("Can't set xmachineorder_id, foreign key violation: xmachineorderitm_xmachineorder_fk");
        }
        setWasChanged(this.xmachineorderId != null && !this.xmachineorderId.equals(xmachineorderId));
        this.xmachineorderId = xmachineorderId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xmachineorderitm_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xmachineorderitm_xemployee_fk");
        }
        setWasChanged(this.xemployeeId != null && !this.xemployeeId.equals(xemployeeId));
        this.xemployeeId = xemployeeId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getXmachineorderitmId();
        columnValues[1] = getXmachineorderId();
        columnValues[2] = getXmachineId();
        columnValues[3] = getXemployeeId();
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
            setXmachineorderitmId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmachineorderitmId(null);
        }
        try {
            setXmachineorderId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXmachineorderId(null);
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
    }
}
