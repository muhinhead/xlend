// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Nov 18 19:48:32 FET 2013
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
    private Integer insiteId = null;
    private Integer tositeId = null;
    private String othersite = null;
    private Integer driverId = null;
    private Integer assistantId = null;
    private Integer tripType = null;
    private Integer distanceEmpty = null;
    private Integer distanceLoaded = null;
    private Integer machineId = null;
    private Integer withmachineId = null;
    private Integer isCopmplete = null;
    private Integer operatorId = null;

    public Xtrip(Connection connection) {
        super(connection, "xtrip", "xtrip_id");
        setColumnNames(new String[]{"xtrip_id", "xlowbed_id", "trip_date", "fromsite_id", "insite_id", "tosite_id", "othersite", "driver_id", "assistant_id", "trip_type", "distance_empty", "distance_loaded", "machine_id", "withmachine_id", "is_copmplete", "operator_id"});
    }

    public Xtrip(Connection connection, Integer xtripId, Integer xlowbedId, Date tripDate, Integer fromsiteId, Integer insiteId, Integer tositeId, String othersite, Integer driverId, Integer assistantId, Integer tripType, Integer distanceEmpty, Integer distanceLoaded, Integer machineId, Integer withmachineId, Integer isCopmplete, Integer operatorId) {
        super(connection, "xtrip", "xtrip_id");
        setNew(xtripId.intValue() <= 0);
//        if (xtripId.intValue() != 0) {
            this.xtripId = xtripId;
//        }
        this.xlowbedId = xlowbedId;
        this.tripDate = tripDate;
        this.fromsiteId = fromsiteId;
        this.insiteId = insiteId;
        this.tositeId = tositeId;
        this.othersite = othersite;
        this.driverId = driverId;
        this.assistantId = assistantId;
        this.tripType = tripType;
        this.distanceEmpty = distanceEmpty;
        this.distanceLoaded = distanceLoaded;
        this.machineId = machineId;
        this.withmachineId = withmachineId;
        this.isCopmplete = isCopmplete;
        this.operatorId = operatorId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtrip xtrip = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtrip_id,xlowbed_id,trip_date,fromsite_id,insite_id,tosite_id,othersite,driver_id,assistant_id,trip_type,distance_empty,distance_loaded,machine_id,withmachine_id,is_copmplete,operator_id FROM xtrip WHERE xtrip_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtrip = new Xtrip(getConnection());
                xtrip.setXtripId(new Integer(rs.getInt(1)));
                xtrip.setXlowbedId(new Integer(rs.getInt(2)));
                xtrip.setTripDate(rs.getDate(3));
                xtrip.setFromsiteId(new Integer(rs.getInt(4)));
                xtrip.setInsiteId(new Integer(rs.getInt(5)));
                xtrip.setTositeId(new Integer(rs.getInt(6)));
                xtrip.setOthersite(rs.getString(7));
                xtrip.setDriverId(new Integer(rs.getInt(8)));
                xtrip.setAssistantId(new Integer(rs.getInt(9)));
                xtrip.setTripType(new Integer(rs.getInt(10)));
                xtrip.setDistanceEmpty(new Integer(rs.getInt(11)));
                xtrip.setDistanceLoaded(new Integer(rs.getInt(12)));
                xtrip.setMachineId(new Integer(rs.getInt(13)));
                xtrip.setWithmachineId(new Integer(rs.getInt(14)));
                xtrip.setIsCopmplete(new Integer(rs.getInt(15)));
                xtrip.setOperatorId(new Integer(rs.getInt(16)));
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
                "INSERT INTO xtrip ("+(getXtripId().intValue()!=0?"xtrip_id,":"")+"xlowbed_id,trip_date,fromsite_id,insite_id,tosite_id,othersite,driver_id,assistant_id,trip_type,distance_empty,distance_loaded,machine_id,withmachine_id,is_copmplete,operator_id) values("+(getXtripId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtripId().intValue()!=0) {
                 ps.setObject(++n, getXtripId());
             }
             ps.setObject(++n, getXlowbedId());
             ps.setObject(++n, getTripDate());
             ps.setObject(++n, getFromsiteId());
             ps.setObject(++n, getInsiteId());
             ps.setObject(++n, getTositeId());
             ps.setObject(++n, getOthersite());
             ps.setObject(++n, getDriverId());
             ps.setObject(++n, getAssistantId());
             ps.setObject(++n, getTripType());
             ps.setObject(++n, getDistanceEmpty());
             ps.setObject(++n, getDistanceLoaded());
             ps.setObject(++n, getMachineId());
             ps.setObject(++n, getWithmachineId());
             ps.setObject(++n, getIsCopmplete());
             ps.setObject(++n, getOperatorId());
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
                    "SET xlowbed_id = ?, trip_date = ?, fromsite_id = ?, insite_id = ?, tosite_id = ?, othersite = ?, driver_id = ?, assistant_id = ?, trip_type = ?, distance_empty = ?, distance_loaded = ?, machine_id = ?, withmachine_id = ?, is_copmplete = ?, operator_id = ?" + 
                    " WHERE xtrip_id = " + getXtripId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXlowbedId());
                ps.setObject(2, getTripDate());
                ps.setObject(3, getFromsiteId());
                ps.setObject(4, getInsiteId());
                ps.setObject(5, getTositeId());
                ps.setObject(6, getOthersite());
                ps.setObject(7, getDriverId());
                ps.setObject(8, getAssistantId());
                ps.setObject(9, getTripType());
                ps.setObject(10, getDistanceEmpty());
                ps.setObject(11, getDistanceLoaded());
                ps.setObject(12, getMachineId());
                ps.setObject(13, getWithmachineId());
                ps.setObject(14, getIsCopmplete());
                ps.setObject(15, getOperatorId());
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
        String stmt = "SELECT xtrip_id,xlowbed_id,trip_date,fromsite_id,insite_id,tosite_id,othersite,driver_id,assistant_id,trip_type,distance_empty,distance_loaded,machine_id,withmachine_id,is_copmplete,operator_id FROM xtrip " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtrip(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),rs.getString(7),new Integer(rs.getInt(8)),new Integer(rs.getInt(9)),new Integer(rs.getInt(10)),new Integer(rs.getInt(11)),new Integer(rs.getInt(12)),new Integer(rs.getInt(13)),new Integer(rs.getInt(14)),new Integer(rs.getInt(15)),new Integer(rs.getInt(16))));
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

    public Integer getPK_ID() {
        return xtripId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXtripId(id);
        setNew(prevIsNew);
    }

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

    public Integer getInsiteId() {
        return insiteId;
    }

    public void setInsiteId(Integer insiteId) throws SQLException, ForeignKeyViolationException {
        if (null != insiteId)
            insiteId = insiteId == 0 ? null : insiteId;
        if (insiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + insiteId)) {
            throw new ForeignKeyViolationException("Can't set insite_id, foreign key violation: xtrip_xsite_fk3");
        }
        setWasChanged(this.insiteId != null && !this.insiteId.equals(insiteId));
        this.insiteId = insiteId;
    }

    public Integer getTositeId() {
        return tositeId;
    }

    public void setTositeId(Integer tositeId) throws SQLException, ForeignKeyViolationException {
        if (null != tositeId)
            tositeId = tositeId == 0 ? null : tositeId;
        if (tositeId!=null && !Xsite.exists(getConnection(),"xsite_id = " + tositeId)) {
            throw new ForeignKeyViolationException("Can't set tosite_id, foreign key violation: xtrip_xsite_fk2");
        }
        setWasChanged(this.tositeId != null && !this.tositeId.equals(tositeId));
        this.tositeId = tositeId;
    }

    public String getOthersite() {
        return othersite;
    }

    public void setOthersite(String othersite) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.othersite != null && !this.othersite.equals(othersite));
        this.othersite = othersite;
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

    public Integer getDistanceEmpty() {
        return distanceEmpty;
    }

    public void setDistanceEmpty(Integer distanceEmpty) throws SQLException, ForeignKeyViolationException {
        if (null != distanceEmpty)
            distanceEmpty = distanceEmpty == 0 ? null : distanceEmpty;
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

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) throws SQLException, ForeignKeyViolationException {
        if (null != machineId)
            machineId = machineId == 0 ? null : machineId;
        if (machineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + machineId)) {
            throw new ForeignKeyViolationException("Can't set machine_id, foreign key violation: xtrip_xmachine_fk");
        }
        setWasChanged(this.machineId != null && !this.machineId.equals(machineId));
        this.machineId = machineId;
    }

    public Integer getWithmachineId() {
        return withmachineId;
    }

    public void setWithmachineId(Integer withmachineId) throws SQLException, ForeignKeyViolationException {
        if (null != withmachineId)
            withmachineId = withmachineId == 0 ? null : withmachineId;
        if (withmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + withmachineId)) {
            throw new ForeignKeyViolationException("Can't set withmachine_id, foreign key violation: xtrip_xmachine_fk2");
        }
        setWasChanged(this.withmachineId != null && !this.withmachineId.equals(withmachineId));
        this.withmachineId = withmachineId;
    }

    public Integer getIsCopmplete() {
        return isCopmplete;
    }

    public void setIsCopmplete(Integer isCopmplete) throws SQLException, ForeignKeyViolationException {
        if (null != isCopmplete)
            isCopmplete = isCopmplete == 0 ? null : isCopmplete;
        setWasChanged(this.isCopmplete != null && !this.isCopmplete.equals(isCopmplete));
        this.isCopmplete = isCopmplete;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) throws SQLException, ForeignKeyViolationException {
        if (null != operatorId)
            operatorId = operatorId == 0 ? null : operatorId;
        if (operatorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + operatorId)) {
            throw new ForeignKeyViolationException("Can't set operator_id, foreign key violation: xtrip_xemployee_fk3");
        }
        setWasChanged(this.operatorId != null && !this.operatorId.equals(operatorId));
        this.operatorId = operatorId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[16];
        columnValues[0] = getXtripId();
        columnValues[1] = getXlowbedId();
        columnValues[2] = getTripDate();
        columnValues[3] = getFromsiteId();
        columnValues[4] = getInsiteId();
        columnValues[5] = getTositeId();
        columnValues[6] = getOthersite();
        columnValues[7] = getDriverId();
        columnValues[8] = getAssistantId();
        columnValues[9] = getTripType();
        columnValues[10] = getDistanceEmpty();
        columnValues[11] = getDistanceLoaded();
        columnValues[12] = getMachineId();
        columnValues[13] = getWithmachineId();
        columnValues[14] = getIsCopmplete();
        columnValues[15] = getOperatorId();
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
            setInsiteId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setInsiteId(null);
        }
        try {
            setTositeId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setTositeId(null);
        }
        setOthersite(flds[6]);
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
        try {
            setDistanceEmpty(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setDistanceEmpty(null);
        }
        try {
            setDistanceLoaded(Integer.parseInt(flds[11]));
        } catch(NumberFormatException ne) {
            setDistanceLoaded(null);
        }
        try {
            setMachineId(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setMachineId(null);
        }
        try {
            setWithmachineId(Integer.parseInt(flds[13]));
        } catch(NumberFormatException ne) {
            setWithmachineId(null);
        }
        try {
            setIsCopmplete(Integer.parseInt(flds[14]));
        } catch(NumberFormatException ne) {
            setIsCopmplete(null);
        }
        try {
            setOperatorId(Integer.parseInt(flds[15]));
        } catch(NumberFormatException ne) {
            setOperatorId(null);
        }
    }
}
