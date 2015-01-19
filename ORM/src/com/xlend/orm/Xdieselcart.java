// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 11:03:09 EET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xdieselcart extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xdieselcartId = null;
    private Integer fleetNr = null;
    private String regNr = null;
    private Date expdate = null;
    private Integer litres = null;
    private String chassisNr = null;
    private Integer assignedId = null;
    private Object photo = null;

    public Xdieselcart(Connection connection) {
        super(connection, "xdieselcart", "xdieselcart_id");
        setColumnNames(new String[]{"xdieselcart_id", "fleet_nr", "reg_nr", "expdate", "litres", "chassis_nr", "assigned_id", "photo"});
    }

    public Xdieselcart(Connection connection, Integer xdieselcartId, Integer fleetNr, String regNr, Date expdate, Integer litres, String chassisNr, Integer assignedId, Object photo) {
        super(connection, "xdieselcart", "xdieselcart_id");
        setNew(xdieselcartId.intValue() <= 0);
//        if (xdieselcartId.intValue() != 0) {
            this.xdieselcartId = xdieselcartId;
//        }
        this.fleetNr = fleetNr;
        this.regNr = regNr;
        this.expdate = expdate;
        this.litres = litres;
        this.chassisNr = chassisNr;
        this.assignedId = assignedId;
        this.photo = photo;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xdieselcart xdieselcart = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselcart_id,fleet_nr,reg_nr,expdate,litres,chassis_nr,assigned_id,photo FROM xdieselcart WHERE xdieselcart_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xdieselcart = new Xdieselcart(getConnection());
                xdieselcart.setXdieselcartId(new Integer(rs.getInt(1)));
                xdieselcart.setFleetNr(new Integer(rs.getInt(2)));
                xdieselcart.setRegNr(rs.getString(3));
                xdieselcart.setExpdate(rs.getDate(4));
                xdieselcart.setLitres(new Integer(rs.getInt(5)));
                xdieselcart.setChassisNr(rs.getString(6));
                xdieselcart.setAssignedId(new Integer(rs.getInt(7)));
                xdieselcart.setPhoto(rs.getObject(8));
                xdieselcart.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xdieselcart;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xdieselcart ("+(getXdieselcartId().intValue()!=0?"xdieselcart_id,":"")+"fleet_nr,reg_nr,expdate,litres,chassis_nr,assigned_id,photo) values("+(getXdieselcartId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXdieselcartId().intValue()!=0) {
                 ps.setObject(++n, getXdieselcartId());
             }
             ps.setObject(++n, getFleetNr());
             ps.setObject(++n, getRegNr());
             ps.setObject(++n, getExpdate());
             ps.setObject(++n, getLitres());
             ps.setObject(++n, getChassisNr());
             ps.setObject(++n, getAssignedId());
             ps.setObject(++n, getPhoto());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXdieselcartId().intValue()==0) {
             stmt = "SELECT max(xdieselcart_id) FROM xdieselcart";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXdieselcartId(new Integer(rs.getInt(1)));
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
                    "UPDATE xdieselcart " +
                    "SET fleet_nr = ?, reg_nr = ?, expdate = ?, litres = ?, chassis_nr = ?, assigned_id = ?, photo = ?" + 
                    " WHERE xdieselcart_id = " + getXdieselcartId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getFleetNr());
                ps.setObject(2, getRegNr());
                ps.setObject(3, getExpdate());
                ps.setObject(4, getLitres());
                ps.setObject(5, getChassisNr());
                ps.setObject(6, getAssignedId());
                ps.setObject(7, getPhoto());
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
        if (Xdieselcartissue.exists(getConnection(),"xdieselcart_id = " + getXdieselcartId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdieselcartissue_xdieselcart_fk");
        }
        if (Xdiesel2plant.exists(getConnection(),"xdieselcart_id = " + getXdieselcartId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdiesel2plant_xdieselcart_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xdieselcart " +
                "WHERE xdieselcart_id = " + getXdieselcartId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXdieselcartId(new Integer(-getXdieselcartId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXdieselcartId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselcart_id,fleet_nr,reg_nr,expdate,litres,chassis_nr,assigned_id,photo FROM xdieselcart " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xdieselcart(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3),rs.getDate(4),new Integer(rs.getInt(5)),rs.getString(6),new Integer(rs.getInt(7)),rs.getObject(8)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xdieselcart[] objects = new Xdieselcart[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xdieselcart xdieselcart = (Xdieselcart) lst.get(i);
            objects[i] = xdieselcart;
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
        String stmt = "SELECT xdieselcart_id FROM xdieselcart " +
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
    //    return getXdieselcartId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xdieselcartId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXdieselcartId(id);
        setNew(prevIsNew);
    }

    public Integer getXdieselcartId() {
        return xdieselcartId;
    }

    public void setXdieselcartId(Integer xdieselcartId) throws ForeignKeyViolationException {
        setWasChanged(this.xdieselcartId != null && this.xdieselcartId != xdieselcartId);
        this.xdieselcartId = xdieselcartId;
        setNew(xdieselcartId.intValue() == 0);
    }

    public Integer getFleetNr() {
        return fleetNr;
    }

    public void setFleetNr(Integer fleetNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.fleetNr != null && !this.fleetNr.equals(fleetNr));
        this.fleetNr = fleetNr;
    }

    public String getRegNr() {
        return regNr;
    }

    public void setRegNr(String regNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.regNr != null && !this.regNr.equals(regNr));
        this.regNr = regNr;
    }

    public Date getExpdate() {
        return expdate;
    }

    public void setExpdate(Date expdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.expdate != null && !this.expdate.equals(expdate));
        this.expdate = expdate;
    }

    public Integer getLitres() {
        return litres;
    }

    public void setLitres(Integer litres) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.litres != null && !this.litres.equals(litres));
        this.litres = litres;
    }

    public String getChassisNr() {
        return chassisNr;
    }

    public void setChassisNr(String chassisNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.chassisNr != null && !this.chassisNr.equals(chassisNr));
        this.chassisNr = chassisNr;
    }

    public Integer getAssignedId() {
        return assignedId;
    }

    public void setAssignedId(Integer assignedId) throws SQLException, ForeignKeyViolationException {
        if (null != assignedId)
            assignedId = assignedId == 0 ? null : assignedId;
        if (assignedId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + assignedId)) {
            throw new ForeignKeyViolationException("Can't set assigned_id, foreign key violation: xdieselcart_xmachine_fk");
        }
        setWasChanged(this.assignedId != null && !this.assignedId.equals(assignedId));
        this.assignedId = assignedId;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.photo != null && !this.photo.equals(photo));
        this.photo = photo;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getXdieselcartId();
        columnValues[1] = getFleetNr();
        columnValues[2] = getRegNr();
        columnValues[3] = getExpdate();
        columnValues[4] = getLitres();
        columnValues[5] = getChassisNr();
        columnValues[6] = getAssignedId();
        columnValues[7] = getPhoto();
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
            setXdieselcartId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXdieselcartId(null);
        }
        try {
            setFleetNr(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setFleetNr(null);
        }
        setRegNr(flds[2]);
        setExpdate(toDate(flds[3]));
        try {
            setLitres(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setLitres(null);
        }
        setChassisNr(flds[5]);
        try {
            setAssignedId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setAssignedId(null);
        }
        setPhoto(flds[7]);
    }
}
