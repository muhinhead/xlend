// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Jun 08 10:51:40 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xtripmoving extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xtripmovingId = null;
    private Integer xtripId = null;
    private Integer xmachineId = null;
    private Integer fromsiteId = null;
    private Integer tositeId = null;
    private Integer distanceEmpty = null;
    private Integer distanceLoaded = null;

    public Xtripmoving(Connection connection) {
        super(connection, "xtripmoving", "xtripmoving_id");
        setColumnNames(new String[]{"xtripmoving_id", "xtrip_id", "xmachine_id", "fromsite_id", "tosite_id", "distance_empty", "distance_loaded"});
    }

    public Xtripmoving(Connection connection, Integer xtripmovingId, Integer xtripId, Integer xmachineId, Integer fromsiteId, Integer tositeId, Integer distanceEmpty, Integer distanceLoaded) {
        super(connection, "xtripmoving", "xtripmoving_id");
        setNew(xtripmovingId.intValue() <= 0);
//        if (xtripmovingId.intValue() != 0) {
            this.xtripmovingId = xtripmovingId;
//        }
        this.xtripId = xtripId;
        this.xmachineId = xmachineId;
        this.fromsiteId = fromsiteId;
        this.tositeId = tositeId;
        this.distanceEmpty = distanceEmpty;
        this.distanceLoaded = distanceLoaded;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtripmoving xtripmoving = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripmoving_id,xtrip_id,xmachine_id,fromsite_id,tosite_id,distance_empty,distance_loaded FROM xtripmoving WHERE xtripmoving_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtripmoving = new Xtripmoving(getConnection());
                xtripmoving.setXtripmovingId(new Integer(rs.getInt(1)));
                xtripmoving.setXtripId(new Integer(rs.getInt(2)));
                xtripmoving.setXmachineId(new Integer(rs.getInt(3)));
                xtripmoving.setFromsiteId(new Integer(rs.getInt(4)));
                xtripmoving.setTositeId(new Integer(rs.getInt(5)));
                xtripmoving.setDistanceEmpty(new Integer(rs.getInt(6)));
                xtripmoving.setDistanceLoaded(new Integer(rs.getInt(7)));
                xtripmoving.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xtripmoving;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xtripmoving ("+(getXtripmovingId().intValue()!=0?"xtripmoving_id,":"")+"xtrip_id,xmachine_id,fromsite_id,tosite_id,distance_empty,distance_loaded) values("+(getXtripmovingId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtripmovingId().intValue()!=0) {
                 ps.setObject(++n, getXtripmovingId());
             }
             ps.setObject(++n, getXtripId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getFromsiteId());
             ps.setObject(++n, getTositeId());
             ps.setObject(++n, getDistanceEmpty());
             ps.setObject(++n, getDistanceLoaded());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXtripmovingId().intValue()==0) {
             stmt = "SELECT max(xtripmoving_id) FROM xtripmoving";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXtripmovingId(new Integer(rs.getInt(1)));
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
                    "UPDATE xtripmoving " +
                    "SET xtrip_id = ?, xmachine_id = ?, fromsite_id = ?, tosite_id = ?, distance_empty = ?, distance_loaded = ?" + 
                    " WHERE xtripmoving_id = " + getXtripmovingId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXtripId());
                ps.setObject(2, getXmachineId());
                ps.setObject(3, getFromsiteId());
                ps.setObject(4, getTositeId());
                ps.setObject(5, getDistanceEmpty());
                ps.setObject(6, getDistanceLoaded());
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
                "DELETE FROM xtripmoving " +
                "WHERE xtripmoving_id = " + getXtripmovingId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXtripmovingId(new Integer(-getXtripmovingId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXtripmovingId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripmoving_id,xtrip_id,xmachine_id,fromsite_id,tosite_id,distance_empty,distance_loaded FROM xtripmoving " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtripmoving(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xtripmoving[] objects = new Xtripmoving[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xtripmoving xtripmoving = (Xtripmoving) lst.get(i);
            objects[i] = xtripmoving;
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
        String stmt = "SELECT xtripmoving_id FROM xtripmoving " +
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
    //    return getXtripmovingId() + getDelimiter();
    //}

    public Integer getXtripmovingId() {
        return xtripmovingId;
    }

    public void setXtripmovingId(Integer xtripmovingId) throws ForeignKeyViolationException {
        setWasChanged(this.xtripmovingId != null && this.xtripmovingId != xtripmovingId);
        this.xtripmovingId = xtripmovingId;
        setNew(xtripmovingId.intValue() == 0);
    }

    public Integer getXtripId() {
        return xtripId;
    }

    public void setXtripId(Integer xtripId) throws SQLException, ForeignKeyViolationException {
        if (xtripId!=null && !Xtrip.exists(getConnection(),"xtrip_id = " + xtripId)) {
            throw new ForeignKeyViolationException("Can't set xtrip_id, foreign key violation: xtripmoving_xtrip_fk");
        }
        setWasChanged(this.xtripId != null && !this.xtripId.equals(xtripId));
        this.xtripId = xtripId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xtripmoving_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getFromsiteId() {
        return fromsiteId;
    }

    public void setFromsiteId(Integer fromsiteId) throws SQLException, ForeignKeyViolationException {
        if (fromsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + fromsiteId)) {
            throw new ForeignKeyViolationException("Can't set fromsite_id, foreign key violation: xtripmoving_xsite_fk");
        }
        setWasChanged(this.fromsiteId != null && !this.fromsiteId.equals(fromsiteId));
        this.fromsiteId = fromsiteId;
    }

    public Integer getTositeId() {
        return tositeId;
    }

    public void setTositeId(Integer tositeId) throws SQLException, ForeignKeyViolationException {
        if (tositeId!=null && !Xsite.exists(getConnection(),"xsite_id = " + tositeId)) {
            throw new ForeignKeyViolationException("Can't set tosite_id, foreign key violation: xtripmoving_xsite_fk2");
        }
        setWasChanged(this.tositeId != null && !this.tositeId.equals(tositeId));
        this.tositeId = tositeId;
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
        setWasChanged(this.distanceLoaded != null && !this.distanceLoaded.equals(distanceLoaded));
        this.distanceLoaded = distanceLoaded;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXtripmovingId();
        columnValues[1] = getXtripId();
        columnValues[2] = getXmachineId();
        columnValues[3] = getFromsiteId();
        columnValues[4] = getTositeId();
        columnValues[5] = getDistanceEmpty();
        columnValues[6] = getDistanceLoaded();
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
            setXtripmovingId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXtripmovingId(null);
        }
        try {
            setXtripId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXtripId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setFromsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setFromsiteId(null);
        }
        try {
            setTositeId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setTositeId(null);
        }
        try {
            setDistanceEmpty(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setDistanceEmpty(null);
        }
        try {
            setDistanceLoaded(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setDistanceLoaded(null);
        }
    }
}
