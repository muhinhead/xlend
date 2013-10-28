// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Oct 28 15:34:33 FET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xdiesel2plant extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xdiesel2plantId = null;
    private Integer xdieselcartId = null;
    private Date lastDate = null;
    private Integer driverId = null;

    public Xdiesel2plant(Connection connection) {
        super(connection, "xdiesel2plant", "xdiesel2plant_id");
        setColumnNames(new String[]{"xdiesel2plant_id", "xdieselcart_id", "last_date", "driver_id"});
    }

    public Xdiesel2plant(Connection connection, Integer xdiesel2plantId, Integer xdieselcartId, Date lastDate, Integer driverId) {
        super(connection, "xdiesel2plant", "xdiesel2plant_id");
        setNew(xdiesel2plantId.intValue() <= 0);
//        if (xdiesel2plantId.intValue() != 0) {
            this.xdiesel2plantId = xdiesel2plantId;
//        }
        this.xdieselcartId = xdieselcartId;
        this.lastDate = lastDate;
        this.driverId = driverId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xdiesel2plant xdiesel2plant = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdiesel2plant_id,xdieselcart_id,last_date,driver_id FROM xdiesel2plant WHERE xdiesel2plant_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xdiesel2plant = new Xdiesel2plant(getConnection());
                xdiesel2plant.setXdiesel2plantId(new Integer(rs.getInt(1)));
                xdiesel2plant.setXdieselcartId(new Integer(rs.getInt(2)));
                xdiesel2plant.setLastDate(rs.getDate(3));
                xdiesel2plant.setDriverId(new Integer(rs.getInt(4)));
                xdiesel2plant.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xdiesel2plant;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xdiesel2plant ("+(getXdiesel2plantId().intValue()!=0?"xdiesel2plant_id,":"")+"xdieselcart_id,last_date,driver_id) values("+(getXdiesel2plantId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXdiesel2plantId().intValue()!=0) {
                 ps.setObject(++n, getXdiesel2plantId());
             }
             ps.setObject(++n, getXdieselcartId());
             ps.setObject(++n, getLastDate());
             ps.setObject(++n, getDriverId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXdiesel2plantId().intValue()==0) {
             stmt = "SELECT max(xdiesel2plant_id) FROM xdiesel2plant";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXdiesel2plantId(new Integer(rs.getInt(1)));
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
                    "UPDATE xdiesel2plant " +
                    "SET xdieselcart_id = ?, last_date = ?, driver_id = ?" + 
                    " WHERE xdiesel2plant_id = " + getXdiesel2plantId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXdieselcartId());
                ps.setObject(2, getLastDate());
                ps.setObject(3, getDriverId());
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
        if (Xdiesel2plantitem.exists(getConnection(),"xdiesel2plant_id = " + getXdiesel2plantId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdiesel2plantitem_xdiesel2plant_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xdiesel2plant " +
                "WHERE xdiesel2plant_id = " + getXdiesel2plantId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXdiesel2plantId(new Integer(-getXdiesel2plantId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXdiesel2plantId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdiesel2plant_id,xdieselcart_id,last_date,driver_id FROM xdiesel2plant " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xdiesel2plant(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xdiesel2plant[] objects = new Xdiesel2plant[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xdiesel2plant xdiesel2plant = (Xdiesel2plant) lst.get(i);
            objects[i] = xdiesel2plant;
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
        String stmt = "SELECT xdiesel2plant_id FROM xdiesel2plant " +
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
    //    return getXdiesel2plantId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xdiesel2plantId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXdiesel2plantId(id);
        setNew(prevIsNew);
    }

    public Integer getXdiesel2plantId() {
        return xdiesel2plantId;
    }

    public void setXdiesel2plantId(Integer xdiesel2plantId) throws ForeignKeyViolationException {
        setWasChanged(this.xdiesel2plantId != null && this.xdiesel2plantId != xdiesel2plantId);
        this.xdiesel2plantId = xdiesel2plantId;
        setNew(xdiesel2plantId.intValue() == 0);
    }

    public Integer getXdieselcartId() {
        return xdieselcartId;
    }

    public void setXdieselcartId(Integer xdieselcartId) throws SQLException, ForeignKeyViolationException {
        if (xdieselcartId!=null && !Xdieselcart.exists(getConnection(),"xdieselcart_id = " + xdieselcartId)) {
            throw new ForeignKeyViolationException("Can't set xdieselcart_id, foreign key violation: xdiesel2plant_xdieselcart_fk");
        }
        setWasChanged(this.xdieselcartId != null && !this.xdieselcartId.equals(xdieselcartId));
        this.xdieselcartId = xdieselcartId;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.lastDate != null && !this.lastDate.equals(lastDate));
        this.lastDate = lastDate;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) throws SQLException, ForeignKeyViolationException {
        if (driverId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + driverId)) {
            throw new ForeignKeyViolationException("Can't set driver_id, foreign key violation: xdiesel2plant_xemployee_fk");
        }
        setWasChanged(this.driverId != null && !this.driverId.equals(driverId));
        this.driverId = driverId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getXdiesel2plantId();
        columnValues[1] = getXdieselcartId();
        columnValues[2] = getLastDate();
        columnValues[3] = getDriverId();
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
            setXdiesel2plantId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXdiesel2plantId(null);
        }
        try {
            setXdieselcartId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXdieselcartId(null);
        }
        setLastDate(toDate(flds[2]));
        try {
            setDriverId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setDriverId(null);
        }
    }
}
