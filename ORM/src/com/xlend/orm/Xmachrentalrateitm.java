// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmachrentalrateitm extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmachrentalrateitmId = null;
    private Integer xmachrentalrateId = null;
    private Integer xmachtypeId = null;
    private Double litresHour = null;
    private Double dry = null;
    private Double realWet = null;
    private Double goodWet = null;

    public Xmachrentalrateitm(Connection connection) {
        super(connection, "xmachrentalrateitm", "xmachrentalrateitm_id");
        setColumnNames(new String[]{"xmachrentalrateitm_id", "xmachrentalrate_id", "xmachtype_id", "litres_hour", "dry", "real_wet", "good_wet"});
    }

    public Xmachrentalrateitm(Connection connection, Integer xmachrentalrateitmId, Integer xmachrentalrateId, Integer xmachtypeId, Double litresHour, Double dry, Double realWet, Double goodWet) {
        super(connection, "xmachrentalrateitm", "xmachrentalrateitm_id");
        setNew(xmachrentalrateitmId.intValue() <= 0);
//        if (xmachrentalrateitmId.intValue() != 0) {
            this.xmachrentalrateitmId = xmachrentalrateitmId;
//        }
        this.xmachrentalrateId = xmachrentalrateId;
        this.xmachtypeId = xmachtypeId;
        this.litresHour = litresHour;
        this.dry = dry;
        this.realWet = realWet;
        this.goodWet = goodWet;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmachrentalrateitm xmachrentalrateitm = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachrentalrateitm_id,xmachrentalrate_id,xmachtype_id,litres_hour,dry,real_wet,good_wet FROM xmachrentalrateitm WHERE xmachrentalrateitm_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachrentalrateitm = new Xmachrentalrateitm(getConnection());
                xmachrentalrateitm.setXmachrentalrateitmId(new Integer(rs.getInt(1)));
                xmachrentalrateitm.setXmachrentalrateId(new Integer(rs.getInt(2)));
                xmachrentalrateitm.setXmachtypeId(new Integer(rs.getInt(3)));
                xmachrentalrateitm.setLitresHour(rs.getDouble(4));
                xmachrentalrateitm.setDry(rs.getDouble(5));
                xmachrentalrateitm.setRealWet(rs.getDouble(6));
                xmachrentalrateitm.setGoodWet(rs.getDouble(7));
                xmachrentalrateitm.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmachrentalrateitm;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmachrentalrateitm ("+(getXmachrentalrateitmId().intValue()!=0?"xmachrentalrateitm_id,":"")+"xmachrentalrate_id,xmachtype_id,litres_hour,dry,real_wet,good_wet) values("+(getXmachrentalrateitmId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachrentalrateitmId().intValue()!=0) {
                 ps.setObject(++n, getXmachrentalrateitmId());
             }
             ps.setObject(++n, getXmachrentalrateId());
             ps.setObject(++n, getXmachtypeId());
             ps.setObject(++n, getLitresHour());
             ps.setObject(++n, getDry());
             ps.setObject(++n, getRealWet());
             ps.setObject(++n, getGoodWet());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmachrentalrateitmId().intValue()==0) {
             stmt = "SELECT max(xmachrentalrateitm_id) FROM xmachrentalrateitm";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmachrentalrateitmId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmachrentalrateitm " +
                    "SET xmachrentalrate_id = ?, xmachtype_id = ?, litres_hour = ?, dry = ?, real_wet = ?, good_wet = ?" + 
                    " WHERE xmachrentalrateitm_id = " + getXmachrentalrateitmId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXmachrentalrateId());
                ps.setObject(2, getXmachtypeId());
                ps.setObject(3, getLitresHour());
                ps.setObject(4, getDry());
                ps.setObject(5, getRealWet());
                ps.setObject(6, getGoodWet());
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
                "DELETE FROM xmachrentalrateitm " +
                "WHERE xmachrentalrateitm_id = " + getXmachrentalrateitmId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmachrentalrateitmId(new Integer(-getXmachrentalrateitmId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmachrentalrateitmId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachrentalrateitm_id,xmachrentalrate_id,xmachtype_id,litres_hour,dry,real_wet,good_wet FROM xmachrentalrateitm " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachrentalrateitm(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),rs.getDouble(7)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmachrentalrateitm[] objects = new Xmachrentalrateitm[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmachrentalrateitm xmachrentalrateitm = (Xmachrentalrateitm) lst.get(i);
            objects[i] = xmachrentalrateitm;
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
        String stmt = "SELECT xmachrentalrateitm_id FROM xmachrentalrateitm " +
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
    //    return getXmachrentalrateitmId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xmachrentalrateitmId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXmachrentalrateitmId(id);
        setNew(prevIsNew);
    }

    public Integer getXmachrentalrateitmId() {
        return xmachrentalrateitmId;
    }

    public void setXmachrentalrateitmId(Integer xmachrentalrateitmId) throws ForeignKeyViolationException {
        setWasChanged(this.xmachrentalrateitmId != null && this.xmachrentalrateitmId != xmachrentalrateitmId);
        this.xmachrentalrateitmId = xmachrentalrateitmId;
        setNew(xmachrentalrateitmId.intValue() == 0);
    }

    public Integer getXmachrentalrateId() {
        return xmachrentalrateId;
    }

    public void setXmachrentalrateId(Integer xmachrentalrateId) throws SQLException, ForeignKeyViolationException {
        if (xmachrentalrateId!=null && !Xmachrentalrate.exists(getConnection(),"xmachrentalrate_id = " + xmachrentalrateId)) {
            throw new ForeignKeyViolationException("Can't set xmachrentalrate_id, foreign key violation: xmachrentalrateitm_xmachrentalrate_fk");
        }
        setWasChanged(this.xmachrentalrateId != null && !this.xmachrentalrateId.equals(xmachrentalrateId));
        this.xmachrentalrateId = xmachrentalrateId;
    }

    public Integer getXmachtypeId() {
        return xmachtypeId;
    }

    public void setXmachtypeId(Integer xmachtypeId) throws SQLException, ForeignKeyViolationException {
        if (xmachtypeId!=null && !Xmachtype.exists(getConnection(),"xmachtype_id = " + xmachtypeId)) {
            throw new ForeignKeyViolationException("Can't set xmachtype_id, foreign key violation: xmachrentalrateitm_xmachtype_fk");
        }
        setWasChanged(this.xmachtypeId != null && !this.xmachtypeId.equals(xmachtypeId));
        this.xmachtypeId = xmachtypeId;
    }

    public Double getLitresHour() {
        return litresHour;
    }

    public void setLitresHour(Double litresHour) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.litresHour != null && !this.litresHour.equals(litresHour));
        this.litresHour = litresHour;
    }

    public Double getDry() {
        return dry;
    }

    public void setDry(Double dry) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dry != null && !this.dry.equals(dry));
        this.dry = dry;
    }

    public Double getRealWet() {
        return realWet;
    }

    public void setRealWet(Double realWet) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.realWet != null && !this.realWet.equals(realWet));
        this.realWet = realWet;
    }

    public Double getGoodWet() {
        return goodWet;
    }

    public void setGoodWet(Double goodWet) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.goodWet != null && !this.goodWet.equals(goodWet));
        this.goodWet = goodWet;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXmachrentalrateitmId();
        columnValues[1] = getXmachrentalrateId();
        columnValues[2] = getXmachtypeId();
        columnValues[3] = getLitresHour();
        columnValues[4] = getDry();
        columnValues[5] = getRealWet();
        columnValues[6] = getGoodWet();
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
            setXmachrentalrateitmId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmachrentalrateitmId(null);
        }
        try {
            setXmachrentalrateId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXmachrentalrateId(null);
        }
        try {
            setXmachtypeId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXmachtypeId(null);
        }
        try {
            setLitresHour(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setLitresHour(null);
        }
        try {
            setDry(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setDry(null);
        }
        try {
            setRealWet(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setRealWet(null);
        }
        try {
            setGoodWet(Double.parseDouble(flds[6]));
        } catch(NumberFormatException ne) {
            setGoodWet(null);
        }
    }
}
