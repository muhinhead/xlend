// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Oct 24 03:58:00 FET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xbattery extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xbatteryId = null;
    private String batteryCode = null;
    private Double vatExclUnit = null;
    private String batteryId = null;
    private Integer xbatterypurchaseId = null;
    private Integer xbateryissueId = null;

    public Xbattery(Connection connection) {
        super(connection, "xbattery", "xbattery_id");
        setColumnNames(new String[]{"xbattery_id", "battery_code", "vat_excl_unit", "battery_id", "xbatterypurchase_id", "xbateryissue_id"});
    }

    public Xbattery(Connection connection, Integer xbatteryId, String batteryCode, Double vatExclUnit, String batteryId, Integer xbatterypurchaseId, Integer xbateryissueId) {
        super(connection, "xbattery", "xbattery_id");
        setNew(xbatteryId.intValue() <= 0);
//        if (xbatteryId.intValue() != 0) {
            this.xbatteryId = xbatteryId;
//        }
        this.batteryCode = batteryCode;
        this.vatExclUnit = vatExclUnit;
        this.batteryId = batteryId;
        this.xbatterypurchaseId = xbatterypurchaseId;
        this.xbateryissueId = xbateryissueId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xbattery xbattery = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbattery_id,battery_code,vat_excl_unit,battery_id,xbatterypurchase_id,xbateryissue_id FROM xbattery WHERE xbattery_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xbattery = new Xbattery(getConnection());
                xbattery.setXbatteryId(new Integer(rs.getInt(1)));
                xbattery.setBatteryCode(rs.getString(2));
                xbattery.setVatExclUnit(rs.getDouble(3));
                xbattery.setBatteryId(rs.getString(4));
                xbattery.setXbatterypurchaseId(new Integer(rs.getInt(5)));
                xbattery.setXbateryissueId(new Integer(rs.getInt(6)));
                xbattery.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xbattery;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xbattery ("+(getXbatteryId().intValue()!=0?"xbattery_id,":"")+"battery_code,vat_excl_unit,battery_id,xbatterypurchase_id,xbateryissue_id) values("+(getXbatteryId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXbatteryId().intValue()!=0) {
                 ps.setObject(++n, getXbatteryId());
             }
             ps.setObject(++n, getBatteryCode());
             ps.setObject(++n, getVatExclUnit());
             ps.setObject(++n, getBatteryId());
             ps.setObject(++n, getXbatterypurchaseId());
             ps.setObject(++n, getXbateryissueId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXbatteryId().intValue()==0) {
             stmt = "SELECT max(xbattery_id) FROM xbattery";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXbatteryId(new Integer(rs.getInt(1)));
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
                    "UPDATE xbattery " +
                    "SET battery_code = ?, vat_excl_unit = ?, battery_id = ?, xbatterypurchase_id = ?, xbateryissue_id = ?" + 
                    " WHERE xbattery_id = " + getXbatteryId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getBatteryCode());
                ps.setObject(2, getVatExclUnit());
                ps.setObject(3, getBatteryId());
                ps.setObject(4, getXbatterypurchaseId());
                ps.setObject(5, getXbateryissueId());
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
                "DELETE FROM xbattery " +
                "WHERE xbattery_id = " + getXbatteryId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXbatteryId(new Integer(-getXbatteryId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXbatteryId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbattery_id,battery_code,vat_excl_unit,battery_id,xbatterypurchase_id,xbateryissue_id FROM xbattery " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xbattery(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getDouble(3),rs.getString(4),new Integer(rs.getInt(5)),new Integer(rs.getInt(6))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xbattery[] objects = new Xbattery[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xbattery xbattery = (Xbattery) lst.get(i);
            objects[i] = xbattery;
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
        String stmt = "SELECT xbattery_id FROM xbattery " +
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
    //    return getXbatteryId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xbatteryId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXbatteryId(id);
        setNew(prevIsNew);
    }

    public Integer getXbatteryId() {
        return xbatteryId;
    }

    public void setXbatteryId(Integer xbatteryId) throws ForeignKeyViolationException {
        setWasChanged(this.xbatteryId != null && this.xbatteryId != xbatteryId);
        this.xbatteryId = xbatteryId;
        setNew(xbatteryId.intValue() == 0);
    }

    public String getBatteryCode() {
        return batteryCode;
    }

    public void setBatteryCode(String batteryCode) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.batteryCode != null && !this.batteryCode.equals(batteryCode));
        this.batteryCode = batteryCode;
    }

    public Double getVatExclUnit() {
        return vatExclUnit;
    }

    public void setVatExclUnit(Double vatExclUnit) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.vatExclUnit != null && !this.vatExclUnit.equals(vatExclUnit));
        this.vatExclUnit = vatExclUnit;
    }

    public String getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(String batteryId) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.batteryId != null && !this.batteryId.equals(batteryId));
        this.batteryId = batteryId;
    }

    public Integer getXbatterypurchaseId() {
        return xbatterypurchaseId;
    }

    public void setXbatterypurchaseId(Integer xbatterypurchaseId) throws SQLException, ForeignKeyViolationException {
        if (xbatterypurchaseId!=null && !Xbatterypurchase.exists(getConnection(),"xbatterypurchase_id = " + xbatterypurchaseId)) {
            throw new ForeignKeyViolationException("Can't set xbatterypurchase_id, foreign key violation: xbattery_xbatterypurchase_fk");
        }
        setWasChanged(this.xbatterypurchaseId != null && !this.xbatterypurchaseId.equals(xbatterypurchaseId));
        this.xbatterypurchaseId = xbatterypurchaseId;
    }

    public Integer getXbateryissueId() {
        return xbateryissueId;
    }

    public void setXbateryissueId(Integer xbateryissueId) throws SQLException, ForeignKeyViolationException {
        if (null != xbateryissueId)
            xbateryissueId = xbateryissueId == 0 ? null : xbateryissueId;
        if (xbateryissueId!=null && !Xbateryissue.exists(getConnection(),"xbateryissue_id = " + xbateryissueId)) {
            throw new ForeignKeyViolationException("Can't set xbateryissue_id, foreign key violation: xbattery_xbateryissue_fk");
        }
        setWasChanged(this.xbateryissueId != null && !this.xbateryissueId.equals(xbateryissueId));
        this.xbateryissueId = xbateryissueId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXbatteryId();
        columnValues[1] = getBatteryCode();
        columnValues[2] = getVatExclUnit();
        columnValues[3] = getBatteryId();
        columnValues[4] = getXbatterypurchaseId();
        columnValues[5] = getXbateryissueId();
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
            setXbatteryId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXbatteryId(null);
        }
        setBatteryCode(flds[1]);
        try {
            setVatExclUnit(Double.parseDouble(flds[2]));
        } catch(NumberFormatException ne) {
            setVatExclUnit(null);
        }
        setBatteryId(flds[3]);
        try {
            setXbatterypurchaseId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXbatterypurchaseId(null);
        }
        try {
            setXbateryissueId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setXbateryissueId(null);
        }
    }
}
