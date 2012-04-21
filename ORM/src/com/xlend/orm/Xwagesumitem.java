// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Apr 21 19:42:29 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xwagesumitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xwagesumitemId = null;
    private Integer xwagesumId = null;
    private Integer xemployeeId = null;
    private Integer xtimesheetId = null;
    private Integer weeklywage = null;
    private Double normaltime = null;
    private Double overtime = null;
    private Double doubletime = null;

    public Xwagesumitem(Connection connection) {
        super(connection, "xwagesumitem", "xwagesumitem_id");
        setColumnNames(new String[]{"xwagesumitem_id", "xwagesum_id", "xemployee_id", "xtimesheet_id", "weeklywage", "normaltime", "overtime", "doubletime"});
    }

    public Xwagesumitem(Connection connection, Integer xwagesumitemId, Integer xwagesumId, Integer xemployeeId, Integer xtimesheetId, Integer weeklywage, Double normaltime, Double overtime, Double doubletime) {
        super(connection, "xwagesumitem", "xwagesumitem_id");
        setNew(xwagesumitemId.intValue() <= 0);
//        if (xwagesumitemId.intValue() != 0) {
            this.xwagesumitemId = xwagesumitemId;
//        }
        this.xwagesumId = xwagesumId;
        this.xemployeeId = xemployeeId;
        this.xtimesheetId = xtimesheetId;
        this.weeklywage = weeklywage;
        this.normaltime = normaltime;
        this.overtime = overtime;
        this.doubletime = doubletime;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xwagesumitem xwagesumitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xwagesumitem_id,xwagesum_id,xemployee_id,xtimesheet_id,weeklywage,normaltime,overtime,doubletime FROM xwagesumitem WHERE xwagesumitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xwagesumitem = new Xwagesumitem(getConnection());
                xwagesumitem.setXwagesumitemId(new Integer(rs.getInt(1)));
                xwagesumitem.setXwagesumId(new Integer(rs.getInt(2)));
                xwagesumitem.setXemployeeId(new Integer(rs.getInt(3)));
                xwagesumitem.setXtimesheetId(new Integer(rs.getInt(4)));
                xwagesumitem.setWeeklywage(new Integer(rs.getInt(5)));
                xwagesumitem.setNormaltime(rs.getDouble(6));
                xwagesumitem.setOvertime(rs.getDouble(7));
                xwagesumitem.setDoubletime(rs.getDouble(8));
                xwagesumitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xwagesumitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xwagesumitem ("+(getXwagesumitemId().intValue()!=0?"xwagesumitem_id,":"")+"xwagesum_id,xemployee_id,xtimesheet_id,weeklywage,normaltime,overtime,doubletime) values("+(getXwagesumitemId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXwagesumitemId().intValue()!=0) {
                 ps.setObject(++n, getXwagesumitemId());
             }
             ps.setObject(++n, getXwagesumId());
             ps.setObject(++n, getXemployeeId());
             ps.setObject(++n, getXtimesheetId());
             ps.setObject(++n, getWeeklywage());
             ps.setObject(++n, getNormaltime());
             ps.setObject(++n, getOvertime());
             ps.setObject(++n, getDoubletime());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXwagesumitemId().intValue()==0) {
             stmt = "SELECT max(xwagesumitem_id) FROM xwagesumitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXwagesumitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE xwagesumitem " +
                    "SET xwagesum_id = ?, xemployee_id = ?, xtimesheet_id = ?, weeklywage = ?, normaltime = ?, overtime = ?, doubletime = ?" + 
                    " WHERE xwagesumitem_id = " + getXwagesumitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXwagesumId());
                ps.setObject(2, getXemployeeId());
                ps.setObject(3, getXtimesheetId());
                ps.setObject(4, getWeeklywage());
                ps.setObject(5, getNormaltime());
                ps.setObject(6, getOvertime());
                ps.setObject(7, getDoubletime());
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
                "DELETE FROM xwagesumitem " +
                "WHERE xwagesumitem_id = " + getXwagesumitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXwagesumitemId(new Integer(-getXwagesumitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXwagesumitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xwagesumitem_id,xwagesum_id,xemployee_id,xtimesheet_id,weeklywage,normaltime,overtime,doubletime FROM xwagesumitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xwagesumitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),rs.getDouble(6),rs.getDouble(7),rs.getDouble(8)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xwagesumitem[] objects = new Xwagesumitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xwagesumitem xwagesumitem = (Xwagesumitem) lst.get(i);
            objects[i] = xwagesumitem;
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
        String stmt = "SELECT xwagesumitem_id FROM xwagesumitem " +
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
    //    return getXwagesumitemId() + getDelimiter();
    //}

    public Integer getXwagesumitemId() {
        return xwagesumitemId;
    }

    public void setXwagesumitemId(Integer xwagesumitemId) throws ForeignKeyViolationException {
        setWasChanged(this.xwagesumitemId != null && this.xwagesumitemId != xwagesumitemId);
        this.xwagesumitemId = xwagesumitemId;
        setNew(xwagesumitemId.intValue() == 0);
    }

    public Integer getXwagesumId() {
        return xwagesumId;
    }

    public void setXwagesumId(Integer xwagesumId) throws SQLException, ForeignKeyViolationException {
        if (null != xwagesumId)
            xwagesumId = xwagesumId == 0 ? null : xwagesumId;
        if (xwagesumId!=null && !Xwagesum.exists(getConnection(),"xwagesum_id = " + xwagesumId)) {
            throw new ForeignKeyViolationException("Can't set xwagesum_id, foreign key violation: xwagesumitem_xwagesum_fk");
        }
        setWasChanged(this.xwagesumId != null && !this.xwagesumId.equals(xwagesumId));
        this.xwagesumId = xwagesumId;
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xwagesumitem_xemployee_fk");
        }
        setWasChanged(this.xemployeeId != null && !this.xemployeeId.equals(xemployeeId));
        this.xemployeeId = xemployeeId;
    }

    public Integer getXtimesheetId() {
        return xtimesheetId;
    }

    public void setXtimesheetId(Integer xtimesheetId) throws SQLException, ForeignKeyViolationException {
        if (null != xtimesheetId)
            xtimesheetId = xtimesheetId == 0 ? null : xtimesheetId;
        if (xtimesheetId!=null && !Xtimesheet.exists(getConnection(),"xtimesheet_id = " + xtimesheetId)) {
            throw new ForeignKeyViolationException("Can't set xtimesheet_id, foreign key violation: xwagesumitem_xtimesheet_fk");
        }
        setWasChanged(this.xtimesheetId != null && !this.xtimesheetId.equals(xtimesheetId));
        this.xtimesheetId = xtimesheetId;
    }

    public Integer getWeeklywage() {
        return weeklywage;
    }

    public void setWeeklywage(Integer weeklywage) throws SQLException, ForeignKeyViolationException {
        if (null != weeklywage)
            weeklywage = weeklywage == 0 ? null : weeklywage;
        setWasChanged(this.weeklywage != null && !this.weeklywage.equals(weeklywage));
        this.weeklywage = weeklywage;
    }

    public Double getNormaltime() {
        return normaltime;
    }

    public void setNormaltime(Double normaltime) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.normaltime != null && !this.normaltime.equals(normaltime));
        this.normaltime = normaltime;
    }

    public Double getOvertime() {
        return overtime;
    }

    public void setOvertime(Double overtime) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.overtime != null && !this.overtime.equals(overtime));
        this.overtime = overtime;
    }

    public Double getDoubletime() {
        return doubletime;
    }

    public void setDoubletime(Double doubletime) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.doubletime != null && !this.doubletime.equals(doubletime));
        this.doubletime = doubletime;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getXwagesumitemId();
        columnValues[1] = getXwagesumId();
        columnValues[2] = getXemployeeId();
        columnValues[3] = getXtimesheetId();
        columnValues[4] = getWeeklywage();
        columnValues[5] = getNormaltime();
        columnValues[6] = getOvertime();
        columnValues[7] = getDoubletime();
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
            setXwagesumitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXwagesumitemId(null);
        }
        try {
            setXwagesumId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXwagesumId(null);
        }
        try {
            setXemployeeId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
        try {
            setXtimesheetId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXtimesheetId(null);
        }
        try {
            setWeeklywage(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setWeeklywage(null);
        }
        try {
            setNormaltime(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setNormaltime(null);
        }
        try {
            setOvertime(Double.parseDouble(flds[6]));
        } catch(NumberFormatException ne) {
            setOvertime(null);
        }
        try {
            setDoubletime(Double.parseDouble(flds[7]));
        } catch(NumberFormatException ne) {
            setDoubletime(null);
        }
    }
}
