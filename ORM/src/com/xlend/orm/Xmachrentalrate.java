// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Wed Jan 30 14:50:08 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmachrentalrate extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmachrentalrateId = null;
    private Date actualDate = null;
    private Double dieselPrice = null;
    private Double factor = null;

    public Xmachrentalrate(Connection connection) {
        super(connection, "xmachrentalrate", "xmachrentalrate_id");
        setColumnNames(new String[]{"xmachrentalrate_id", "actual_date", "diesel_price", "factor"});
    }

    public Xmachrentalrate(Connection connection, Integer xmachrentalrateId, Date actualDate, Double dieselPrice, Double factor) {
        super(connection, "xmachrentalrate", "xmachrentalrate_id");
        setNew(xmachrentalrateId.intValue() <= 0);
//        if (xmachrentalrateId.intValue() != 0) {
            this.xmachrentalrateId = xmachrentalrateId;
//        }
        this.actualDate = actualDate;
        this.dieselPrice = dieselPrice;
        this.factor = factor;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmachrentalrate xmachrentalrate = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachrentalrate_id,actual_date,diesel_price,factor FROM xmachrentalrate WHERE xmachrentalrate_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachrentalrate = new Xmachrentalrate(getConnection());
                xmachrentalrate.setXmachrentalrateId(new Integer(rs.getInt(1)));
                xmachrentalrate.setActualDate(rs.getDate(2));
                xmachrentalrate.setDieselPrice(rs.getDouble(3));
                xmachrentalrate.setFactor(rs.getDouble(4));
                xmachrentalrate.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmachrentalrate;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmachrentalrate ("+(getXmachrentalrateId().intValue()!=0?"xmachrentalrate_id,":"")+"actual_date,diesel_price,factor) values("+(getXmachrentalrateId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachrentalrateId().intValue()!=0) {
                 ps.setObject(++n, getXmachrentalrateId());
             }
             ps.setObject(++n, getActualDate());
             ps.setObject(++n, getDieselPrice());
             ps.setObject(++n, getFactor());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmachrentalrateId().intValue()==0) {
             stmt = "SELECT max(xmachrentalrate_id) FROM xmachrentalrate";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmachrentalrateId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmachrentalrate " +
                    "SET actual_date = ?, diesel_price = ?, factor = ?" + 
                    " WHERE xmachrentalrate_id = " + getXmachrentalrateId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getActualDate());
                ps.setObject(2, getDieselPrice());
                ps.setObject(3, getFactor());
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
        {// delete cascade from xmachrentalrateitm
            Xmachrentalrateitm[] records = (Xmachrentalrateitm[])Xmachrentalrateitm.load(getConnection(),"xmachrentalrate_id = " + getXmachrentalrateId(),null);
            for (int i = 0; i<records.length; i++) {
                Xmachrentalrateitm xmachrentalrateitm = records[i];
                xmachrentalrateitm.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xmachrentalrate " +
                "WHERE xmachrentalrate_id = " + getXmachrentalrateId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmachrentalrateId(new Integer(-getXmachrentalrateId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmachrentalrateId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachrentalrate_id,actual_date,diesel_price,factor FROM xmachrentalrate " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachrentalrate(con,new Integer(rs.getInt(1)),rs.getDate(2),rs.getDouble(3),rs.getDouble(4)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmachrentalrate[] objects = new Xmachrentalrate[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmachrentalrate xmachrentalrate = (Xmachrentalrate) lst.get(i);
            objects[i] = xmachrentalrate;
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
        String stmt = "SELECT xmachrentalrate_id FROM xmachrentalrate " +
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
    //    return getXmachrentalrateId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xmachrentalrateId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXmachrentalrateId(id);
        setNew(prevIsNew);
    }

    public Integer getXmachrentalrateId() {
        return xmachrentalrateId;
    }

    public void setXmachrentalrateId(Integer xmachrentalrateId) throws ForeignKeyViolationException {
        setWasChanged(this.xmachrentalrateId != null && this.xmachrentalrateId != xmachrentalrateId);
        this.xmachrentalrateId = xmachrentalrateId;
        setNew(xmachrentalrateId.intValue() == 0);
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.actualDate != null && !this.actualDate.equals(actualDate));
        this.actualDate = actualDate;
    }

    public Double getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(Double dieselPrice) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dieselPrice != null && !this.dieselPrice.equals(dieselPrice));
        this.dieselPrice = dieselPrice;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.factor != null && !this.factor.equals(factor));
        this.factor = factor;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getXmachrentalrateId();
        columnValues[1] = getActualDate();
        columnValues[2] = getDieselPrice();
        columnValues[3] = getFactor();
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
            setXmachrentalrateId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmachrentalrateId(null);
        }
        setActualDate(toDate(flds[1]));
        try {
            setDieselPrice(Double.parseDouble(flds[2]));
        } catch(NumberFormatException ne) {
            setDieselPrice(null);
        }
        try {
            setFactor(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setFactor(null);
        }
    }
}
