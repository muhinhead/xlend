// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jun 28 14:06:17 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xtripestablish extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xtripestablishId = null;
    private Integer xtripId = null;
    private Integer xsiteId = null;
    private Integer xmachineId = null;
    private Integer distanceEmpty = null;
    private Integer distanceLoaded = null;

    public Xtripestablish(Connection connection) {
        super(connection, "xtripestablish", "xtripestablish_id");
        setColumnNames(new String[]{"xtripestablish_id", "xtrip_id", "xsite_id", "xmachine_id", "distance_empty", "distance_loaded"});
    }

    public Xtripestablish(Connection connection, Integer xtripestablishId, Integer xtripId, Integer xsiteId, Integer xmachineId, Integer distanceEmpty, Integer distanceLoaded) {
        super(connection, "xtripestablish", "xtripestablish_id");
        setNew(xtripestablishId.intValue() <= 0);
//        if (xtripestablishId.intValue() != 0) {
            this.xtripestablishId = xtripestablishId;
//        }
        this.xtripId = xtripId;
        this.xsiteId = xsiteId;
        this.xmachineId = xmachineId;
        this.distanceEmpty = distanceEmpty;
        this.distanceLoaded = distanceLoaded;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtripestablish xtripestablish = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripestablish_id,xtrip_id,xsite_id,xmachine_id,distance_empty,distance_loaded FROM xtripestablish WHERE xtripestablish_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtripestablish = new Xtripestablish(getConnection());
                xtripestablish.setXtripestablishId(new Integer(rs.getInt(1)));
                xtripestablish.setXtripId(new Integer(rs.getInt(2)));
                xtripestablish.setXsiteId(new Integer(rs.getInt(3)));
                xtripestablish.setXmachineId(new Integer(rs.getInt(4)));
                xtripestablish.setDistanceEmpty(new Integer(rs.getInt(5)));
                xtripestablish.setDistanceLoaded(new Integer(rs.getInt(6)));
                xtripestablish.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xtripestablish;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xtripestablish ("+(getXtripestablishId().intValue()!=0?"xtripestablish_id,":"")+"xtrip_id,xsite_id,xmachine_id,distance_empty,distance_loaded) values("+(getXtripestablishId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtripestablishId().intValue()!=0) {
                 ps.setObject(++n, getXtripestablishId());
             }
             ps.setObject(++n, getXtripId());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getDistanceEmpty());
             ps.setObject(++n, getDistanceLoaded());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXtripestablishId().intValue()==0) {
             stmt = "SELECT max(xtripestablish_id) FROM xtripestablish";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXtripestablishId(new Integer(rs.getInt(1)));
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
                    "UPDATE xtripestablish " +
                    "SET xtrip_id = ?, xsite_id = ?, xmachine_id = ?, distance_empty = ?, distance_loaded = ?" + 
                    " WHERE xtripestablish_id = " + getXtripestablishId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXtripId());
                ps.setObject(2, getXsiteId());
                ps.setObject(3, getXmachineId());
                ps.setObject(4, getDistanceEmpty());
                ps.setObject(5, getDistanceLoaded());
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
                "DELETE FROM xtripestablish " +
                "WHERE xtripestablish_id = " + getXtripestablishId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXtripestablishId(new Integer(-getXtripestablishId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXtripestablishId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripestablish_id,xtrip_id,xsite_id,xmachine_id,distance_empty,distance_loaded FROM xtripestablish " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtripestablish(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xtripestablish[] objects = new Xtripestablish[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xtripestablish xtripestablish = (Xtripestablish) lst.get(i);
            objects[i] = xtripestablish;
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
        String stmt = "SELECT xtripestablish_id FROM xtripestablish " +
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
    //    return getXtripestablishId() + getDelimiter();
    //}

    public Integer getXtripestablishId() {
        return xtripestablishId;
    }

    public void setXtripestablishId(Integer xtripestablishId) throws ForeignKeyViolationException {
        setWasChanged(this.xtripestablishId != null && this.xtripestablishId != xtripestablishId);
        this.xtripestablishId = xtripestablishId;
        setNew(xtripestablishId.intValue() == 0);
    }

    public Integer getXtripId() {
        return xtripId;
    }

    public void setXtripId(Integer xtripId) throws SQLException, ForeignKeyViolationException {
        if (xtripId!=null && !Xtrip.exists(getConnection(),"xtrip_id = " + xtripId)) {
            throw new ForeignKeyViolationException("Can't set xtrip_id, foreign key violation: xtripestablish_xtrip_fk");
        }
        setWasChanged(this.xtripId != null && !this.xtripId.equals(xtripId));
        this.xtripId = xtripId;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xtripestablish_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xtripestablish_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getDistanceEmpty() {
        return distanceEmpty;
    }

    public void setDistanceEmpty(Integer distanceEmpty) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.distanceEmpty != null && !this.distanceEmpty.equals(distanceEmpty));
        this.distanceEmpty = distanceEmpty;
    }

    public Integer getDistanceLoaded() {
        return distanceLoaded;
    }

    public void setDistanceLoaded(Integer distanceLoaded) throws SQLException, ForeignKeyViolationException {
        if (null != distanceLoaded)
            distanceLoaded = distanceLoaded == 0 ? null : distanceLoaded;
        setWasChanged(this.distanceLoaded != null && !this.distanceLoaded.equals(distanceLoaded));
        this.distanceLoaded = distanceLoaded;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXtripestablishId();
        columnValues[1] = getXtripId();
        columnValues[2] = getXsiteId();
        columnValues[3] = getXmachineId();
        columnValues[4] = getDistanceEmpty();
        columnValues[5] = getDistanceLoaded();
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
            setXtripestablishId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXtripestablishId(null);
        }
        try {
            setXtripId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXtripId(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setDistanceEmpty(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setDistanceEmpty(null);
        }
        try {
            setDistanceLoaded(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setDistanceLoaded(null);
        }
    }
}
