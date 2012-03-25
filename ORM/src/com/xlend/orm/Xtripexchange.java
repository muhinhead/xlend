// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Mar 25 09:36:43 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xtripexchange extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xtripexchangeId = null;
    private Integer xtripId = null;
    private Integer xsiteId = null;
    private Integer machineId = null;
    private Integer withmachineId = null;
    private Integer distanceEmpty = null;
    private Integer distanceLoaded = null;

    public Xtripexchange(Connection connection) {
        super(connection, "xtripexchange", "xtripexchange_id");
        setColumnNames(new String[]{"xtripexchange_id", "xtrip_id", "xsite_id", "machine_id", "withmachine_id", "distance_empty", "distance_loaded"});
    }

    public Xtripexchange(Connection connection, Integer xtripexchangeId, Integer xtripId, Integer xsiteId, Integer machineId, Integer withmachineId, Integer distanceEmpty, Integer distanceLoaded) {
        super(connection, "xtripexchange", "xtripexchange_id");
        setNew(xtripexchangeId.intValue() <= 0);
//        if (xtripexchangeId.intValue() != 0) {
            this.xtripexchangeId = xtripexchangeId;
//        }
        this.xtripId = xtripId;
        this.xsiteId = xsiteId;
        this.machineId = machineId;
        this.withmachineId = withmachineId;
        this.distanceEmpty = distanceEmpty;
        this.distanceLoaded = distanceLoaded;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtripexchange xtripexchange = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripexchange_id,xtrip_id,xsite_id,machine_id,withmachine_id,distance_empty,distance_loaded FROM xtripexchange WHERE xtripexchange_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtripexchange = new Xtripexchange(getConnection());
                xtripexchange.setXtripexchangeId(new Integer(rs.getInt(1)));
                xtripexchange.setXtripId(new Integer(rs.getInt(2)));
                xtripexchange.setXsiteId(new Integer(rs.getInt(3)));
                xtripexchange.setMachineId(new Integer(rs.getInt(4)));
                xtripexchange.setWithmachineId(new Integer(rs.getInt(5)));
                xtripexchange.setDistanceEmpty(new Integer(rs.getInt(6)));
                xtripexchange.setDistanceLoaded(new Integer(rs.getInt(7)));
                xtripexchange.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xtripexchange;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xtripexchange ("+(getXtripexchangeId().intValue()!=0?"xtripexchange_id,":"")+"xtrip_id,xsite_id,machine_id,withmachine_id,distance_empty,distance_loaded) values("+(getXtripexchangeId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtripexchangeId().intValue()!=0) {
                 ps.setObject(++n, getXtripexchangeId());
             }
             ps.setObject(++n, getXtripId());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getMachineId());
             ps.setObject(++n, getWithmachineId());
             ps.setObject(++n, getDistanceEmpty());
             ps.setObject(++n, getDistanceLoaded());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXtripexchangeId().intValue()==0) {
             stmt = "SELECT max(xtripexchange_id) FROM xtripexchange";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXtripexchangeId(new Integer(rs.getInt(1)));
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
                    "UPDATE xtripexchange " +
                    "SET xtrip_id = ?, xsite_id = ?, machine_id = ?, withmachine_id = ?, distance_empty = ?, distance_loaded = ?" + 
                    " WHERE xtripexchange_id = " + getXtripexchangeId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXtripId());
                ps.setObject(2, getXsiteId());
                ps.setObject(3, getMachineId());
                ps.setObject(4, getWithmachineId());
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
                "DELETE FROM xtripexchange " +
                "WHERE xtripexchange_id = " + getXtripexchangeId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXtripexchangeId(new Integer(-getXtripexchangeId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXtripexchangeId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripexchange_id,xtrip_id,xsite_id,machine_id,withmachine_id,distance_empty,distance_loaded FROM xtripexchange " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtripexchange(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xtripexchange[] objects = new Xtripexchange[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xtripexchange xtripexchange = (Xtripexchange) lst.get(i);
            objects[i] = xtripexchange;
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
        String stmt = "SELECT xtripexchange_id FROM xtripexchange " +
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
    //    return getXtripexchangeId() + getDelimiter();
    //}

    public Integer getXtripexchangeId() {
        return xtripexchangeId;
    }

    public void setXtripexchangeId(Integer xtripexchangeId) throws ForeignKeyViolationException {
        setWasChanged(this.xtripexchangeId != null && this.xtripexchangeId != xtripexchangeId);
        this.xtripexchangeId = xtripexchangeId;
        setNew(xtripexchangeId.intValue() == 0);
    }

    public Integer getXtripId() {
        return xtripId;
    }

    public void setXtripId(Integer xtripId) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.xtripId != null && !this.xtripId.equals(xtripId));
        this.xtripId = xtripId;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xtripexchange_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) throws SQLException, ForeignKeyViolationException {
        if (machineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + machineId)) {
            throw new ForeignKeyViolationException("Can't set machine_id, foreign key violation: xtripexchange_xmachine_fk");
        }
        setWasChanged(this.machineId != null && !this.machineId.equals(machineId));
        this.machineId = machineId;
    }

    public Integer getWithmachineId() {
        return withmachineId;
    }

    public void setWithmachineId(Integer withmachineId) throws SQLException, ForeignKeyViolationException {
        if (withmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + withmachineId)) {
            throw new ForeignKeyViolationException("Can't set withmachine_id, foreign key violation: xtripexchange_xmachine_fk2");
        }
        setWasChanged(this.withmachineId != null && !this.withmachineId.equals(withmachineId));
        this.withmachineId = withmachineId;
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
        columnValues[0] = getXtripexchangeId();
        columnValues[1] = getXtripId();
        columnValues[2] = getXsiteId();
        columnValues[3] = getMachineId();
        columnValues[4] = getWithmachineId();
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
            setXtripexchangeId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXtripexchangeId(null);
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
            setMachineId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setMachineId(null);
        }
        try {
            setWithmachineId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setWithmachineId(null);
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
