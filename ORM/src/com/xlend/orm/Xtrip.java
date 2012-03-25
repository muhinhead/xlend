// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Mar 25 09:36:43 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xtrip extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xtripId = null;
    private Integer xlowbedId = null;
    private Date tripDate = null;
    private Integer fromsiteId = null;
    private Integer tositeId = null;
    private Integer loaded = null;
    private Integer distance = null;
    private Integer driverId = null;
    private Integer assistantId = null;
    private Integer tripType = null;

    public Xtrip(Connection connection) {
        super(connection, "xtrip", "xtrip_id");
        setColumnNames(new String[]{"xtrip_id", "xlowbed_id", "trip_date", "fromsite_id", "tosite_id", "loaded", "distance", "driver_id", "assistant_id", "trip_type"});
    }

    public Xtrip(Connection connection, Integer xtripId, Integer xlowbedId, Date tripDate, Integer fromsiteId, Integer tositeId, Integer loaded, Integer distance, Integer driverId, Integer assistantId, Integer tripType) {
        super(connection, "xtrip", "xtrip_id");
        setNew(xtripId.intValue() <= 0);
//        if (xtripId.intValue() != 0) {
            this.xtripId = xtripId;
//        }
        this.xlowbedId = xlowbedId;
        this.tripDate = tripDate;
        this.fromsiteId = fromsiteId;
        this.tositeId = tositeId;
        this.loaded = loaded;
        this.distance = distance;
        this.driverId = driverId;
        this.assistantId = assistantId;
        this.tripType = tripType;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtrip xtrip = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtrip_id,xlowbed_id,trip_date,fromsite_id,tosite_id,loaded,distance,driver_id,assistant_id,trip_type FROM xtrip WHERE xtrip_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtrip = new Xtrip(getConnection());
                xtrip.setXtripId(new Integer(rs.getInt(1)));
                xtrip.setXlowbedId(new Integer(rs.getInt(2)));
                xtrip.setTripDate(rs.getDate(3));
                xtrip.setFromsiteId(new Integer(rs.getInt(4)));
                xtrip.setTositeId(new Integer(rs.getInt(5)));
                xtrip.setLoaded(new Integer(rs.getInt(6)));
                xtrip.setDistance(new Integer(rs.getInt(7)));
                xtrip.setDriverId(new Integer(rs.getInt(8)));
                xtrip.setAssistantId(new Integer(rs.getInt(9)));
                xtrip.setTripType(new Integer(rs.getInt(10)));
                xtrip.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xtrip;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xtrip ("+(getXtripId().intValue()!=0?"xtrip_id,":"")+"xlowbed_id,trip_date,fromsite_id,tosite_id,loaded,distance,driver_id,assistant_id,trip_type) values("+(getXtripId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtripId().intValue()!=0) {
                 ps.setObject(++n, getXtripId());
             }
             ps.setObject(++n, getXlowbedId());
             ps.setObject(++n, getTripDate());
             ps.setObject(++n, getFromsiteId());
             ps.setObject(++n, getTositeId());
             ps.setObject(++n, getLoaded());
             ps.setObject(++n, getDistance());
             ps.setObject(++n, getDriverId());
             ps.setObject(++n, getAssistantId());
             ps.setObject(++n, getTripType());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXtripId().intValue()==0) {
             stmt = "SELECT max(xtrip_id) FROM xtrip";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXtripId(new Integer(rs.getInt(1)));
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
                    "UPDATE xtrip " +
                    "SET xlowbed_id = ?, trip_date = ?, fromsite_id = ?, tosite_id = ?, loaded = ?, distance = ?, driver_id = ?, assistant_id = ?, trip_type = ?" + 
                    " WHERE xtrip_id = " + getXtripId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXlowbedId());
                ps.setObject(2, getTripDate());
                ps.setObject(3, getFromsiteId());
                ps.setObject(4, getTositeId());
                ps.setObject(5, getLoaded());
                ps.setObject(6, getDistance());
                ps.setObject(7, getDriverId());
                ps.setObject(8, getAssistantId());
                ps.setObject(9, getTripType());
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
        {// delete cascade from xtripestablish
            Xtripestablish[] records = (Xtripestablish[])Xtripestablish.load(getConnection(),"xtrip_id = " + getXtripId(),null);
            for (int i = 0; i<records.length; i++) {
                Xtripestablish xtripestablish = records[i];
                xtripestablish.delete();
            }
        }
        {// delete cascade from xtripmoving
            Xtripmoving[] records = (Xtripmoving[])Xtripmoving.load(getConnection(),"xtrip_id = " + getXtripId(),null);
            for (int i = 0; i<records.length; i++) {
                Xtripmoving xtripmoving = records[i];
                xtripmoving.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xtrip " +
                "WHERE xtrip_id = " + getXtripId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXtripId(new Integer(-getXtripId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXtripId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtrip_id,xlowbed_id,trip_date,fromsite_id,tosite_id,loaded,distance,driver_id,assistant_id,trip_type FROM xtrip " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtrip(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),new Integer(rs.getInt(9)),new Integer(rs.getInt(10))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xtrip[] objects = new Xtrip[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xtrip xtrip = (Xtrip) lst.get(i);
            objects[i] = xtrip;
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
        String stmt = "SELECT xtrip_id FROM xtrip " +
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
    //    return getXtripId() + getDelimiter();
    //}

    public Integer getXtripId() {
        return xtripId;
    }

    public void setXtripId(Integer xtripId) throws ForeignKeyViolationException {
        setWasChanged(this.xtripId != null && this.xtripId != xtripId);
        this.xtripId = xtripId;
        setNew(xtripId.intValue() == 0);
    }

    public Integer getXlowbedId() {
        return xlowbedId;
    }

    public void setXlowbedId(Integer xlowbedId) throws SQLException, ForeignKeyViolationException {
        if (xlowbedId!=null && !Xlowbed.exists(getConnection(),"xlowbed_id = " + xlowbedId)) {
            throw new ForeignKeyViolationException("Can't set xlowbed_id, foreign key violation: xtrip_xlowbed_fk");
        }
        setWasChanged(this.xlowbedId != null && !this.xlowbedId.equals(xlowbedId));
        this.xlowbedId = xlowbedId;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.tripDate != null && !this.tripDate.equals(tripDate));
        this.tripDate = tripDate;
    }

    public Integer getFromsiteId() {
        return fromsiteId;
    }

    public void setFromsiteId(Integer fromsiteId) throws SQLException, ForeignKeyViolationException {
        if (fromsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + fromsiteId)) {
            throw new ForeignKeyViolationException("Can't set fromsite_id, foreign key violation: xtrip_xsite_fk");
        }
        setWasChanged(this.fromsiteId != null && !this.fromsiteId.equals(fromsiteId));
        this.fromsiteId = fromsiteId;
    }

    public Integer getTositeId() {
        return tositeId;
    }

    public void setTositeId(Integer tositeId) throws SQLException, ForeignKeyViolationException {
        if (tositeId!=null && !Xsite.exists(getConnection(),"xsite_id = " + tositeId)) {
            throw new ForeignKeyViolationException("Can't set tosite_id, foreign key violation: xtrip_xsite_fk2");
        }
        setWasChanged(this.tositeId != null && !this.tositeId.equals(tositeId));
        this.tositeId = tositeId;
    }

    public Integer getLoaded() {
        return loaded;
    }

    public void setLoaded(Integer loaded) throws SQLException, ForeignKeyViolationException {
        if (null != loaded)
            loaded = loaded == 0 ? null : loaded;
        setWasChanged(this.loaded != null && !this.loaded.equals(loaded));
        this.loaded = loaded;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.distance != null && !this.distance.equals(distance));
        this.distance = distance;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) throws SQLException, ForeignKeyViolationException {
        if (null != driverId)
            driverId = driverId == 0 ? null : driverId;
        if (driverId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + driverId)) {
            throw new ForeignKeyViolationException("Can't set driver_id, foreign key violation: xtrip_xemployee_fk");
        }
        setWasChanged(this.driverId != null && !this.driverId.equals(driverId));
        this.driverId = driverId;
    }

    public Integer getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Integer assistantId) throws SQLException, ForeignKeyViolationException {
        if (null != assistantId)
            assistantId = assistantId == 0 ? null : assistantId;
        if (assistantId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + assistantId)) {
            throw new ForeignKeyViolationException("Can't set assistant_id, foreign key violation: xtrip_xemployee_fk2");
        }
        setWasChanged(this.assistantId != null && !this.assistantId.equals(assistantId));
        this.assistantId = assistantId;
    }

    public Integer getTripType() {
        return tripType;
    }

    public void setTripType(Integer tripType) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.tripType != null && !this.tripType.equals(tripType));
        this.tripType = tripType;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[10];
        columnValues[0] = getXtripId();
        columnValues[1] = getXlowbedId();
        columnValues[2] = getTripDate();
        columnValues[3] = getFromsiteId();
        columnValues[4] = getTositeId();
        columnValues[5] = getLoaded();
        columnValues[6] = getDistance();
        columnValues[7] = getDriverId();
        columnValues[8] = getAssistantId();
        columnValues[9] = getTripType();
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
            setXtripId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXtripId(null);
        }
        try {
            setXlowbedId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXlowbedId(null);
        }
        setTripDate(toDate(flds[2]));
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
            setLoaded(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setLoaded(null);
        }
        try {
            setDistance(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setDistance(null);
        }
        try {
            setDriverId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setDriverId(null);
        }
        try {
            setAssistantId(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setAssistantId(null);
        }
        try {
            setTripType(Integer.parseInt(flds[9]));
        } catch(NumberFormatException ne) {
            setTripType(null);
        }
    }
}
