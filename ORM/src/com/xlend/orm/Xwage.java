// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xwage extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xwageId = null;
    private Integer xtimesheetId = null;
    private Date day = null;
    private Double normaltime = null;
    private Double overtime = null;
    private Double doubletime = null;
    private String stoppeddetails = null;
    private Integer tsnum = null;

    public Xwage(Connection connection) {
        super(connection, "xwage", "xwage_id");
        setColumnNames(new String[]{"xwage_id", "xtimesheet_id", "day", "normaltime", "overtime", "doubletime", "stoppeddetails", "tsnum"});
    }

    public Xwage(Connection connection, Integer xwageId, Integer xtimesheetId, Date day, Double normaltime, Double overtime, Double doubletime, String stoppeddetails, Integer tsnum) {
        super(connection, "xwage", "xwage_id");
        setNew(xwageId.intValue() <= 0);
//        if (xwageId.intValue() != 0) {
            this.xwageId = xwageId;
//        }
        this.xtimesheetId = xtimesheetId;
        this.day = day;
        this.normaltime = normaltime;
        this.overtime = overtime;
        this.doubletime = doubletime;
        this.stoppeddetails = stoppeddetails;
        this.tsnum = tsnum;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xwage xwage = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xwage_id,xtimesheet_id,day,normaltime,overtime,doubletime,stoppeddetails,tsnum FROM xwage WHERE xwage_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xwage = new Xwage(getConnection());
                xwage.setXwageId(new Integer(rs.getInt(1)));
                xwage.setXtimesheetId(new Integer(rs.getInt(2)));
                xwage.setDay(rs.getDate(3));
                xwage.setNormaltime(rs.getDouble(4));
                xwage.setOvertime(rs.getDouble(5));
                xwage.setDoubletime(rs.getDouble(6));
                xwage.setStoppeddetails(rs.getString(7));
                xwage.setTsnum(new Integer(rs.getInt(8)));
                xwage.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xwage;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xwage ("+(getXwageId().intValue()!=0?"xwage_id,":"")+"xtimesheet_id,day,normaltime,overtime,doubletime,stoppeddetails,tsnum) values("+(getXwageId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXwageId().intValue()!=0) {
                 ps.setObject(++n, getXwageId());
             }
             ps.setObject(++n, getXtimesheetId());
             ps.setObject(++n, getDay());
             ps.setObject(++n, getNormaltime());
             ps.setObject(++n, getOvertime());
             ps.setObject(++n, getDoubletime());
             ps.setObject(++n, getStoppeddetails());
             ps.setObject(++n, getTsnum());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXwageId().intValue()==0) {
             stmt = "SELECT max(xwage_id) FROM xwage";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXwageId(new Integer(rs.getInt(1)));
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
                    "UPDATE xwage " +
                    "SET xtimesheet_id = ?, day = ?, normaltime = ?, overtime = ?, doubletime = ?, stoppeddetails = ?, tsnum = ?" + 
                    " WHERE xwage_id = " + getXwageId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXtimesheetId());
                ps.setObject(2, getDay());
                ps.setObject(3, getNormaltime());
                ps.setObject(4, getOvertime());
                ps.setObject(5, getDoubletime());
                ps.setObject(6, getStoppeddetails());
                ps.setObject(7, getTsnum());
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
                "DELETE FROM xwage " +
                "WHERE xwage_id = " + getXwageId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXwageId(new Integer(-getXwageId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXwageId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xwage_id,xtimesheet_id,day,normaltime,overtime,doubletime,stoppeddetails,tsnum FROM xwage " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xwage(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),rs.getString(7),new Integer(rs.getInt(8))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xwage[] objects = new Xwage[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xwage xwage = (Xwage) lst.get(i);
            objects[i] = xwage;
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
        String stmt = "SELECT xwage_id FROM xwage " +
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
    //    return getXwageId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xwageId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXwageId(id);
        setNew(prevIsNew);
    }

    public Integer getXwageId() {
        return xwageId;
    }

    public void setXwageId(Integer xwageId) throws ForeignKeyViolationException {
        setWasChanged(this.xwageId != null && this.xwageId != xwageId);
        this.xwageId = xwageId;
        setNew(xwageId.intValue() == 0);
    }

    public Integer getXtimesheetId() {
        return xtimesheetId;
    }

    public void setXtimesheetId(Integer xtimesheetId) throws SQLException, ForeignKeyViolationException {
        if (xtimesheetId!=null && !Xtimesheet.exists(getConnection(),"xtimesheet_id = " + xtimesheetId)) {
            throw new ForeignKeyViolationException("Can't set xtimesheet_id, foreign key violation: xwage_xtimesheet_fk");
        }
        setWasChanged(this.xtimesheetId != null && !this.xtimesheetId.equals(xtimesheetId));
        this.xtimesheetId = xtimesheetId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.day != null && !this.day.equals(day));
        this.day = day;
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

    public String getStoppeddetails() {
        return stoppeddetails;
    }

    public void setStoppeddetails(String stoppeddetails) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.stoppeddetails != null && !this.stoppeddetails.equals(stoppeddetails));
        this.stoppeddetails = stoppeddetails;
    }

    public Integer getTsnum() {
        return tsnum;
    }

    public void setTsnum(Integer tsnum) throws SQLException, ForeignKeyViolationException {
        if (null != tsnum)
            tsnum = tsnum == 0 ? null : tsnum;
        setWasChanged(this.tsnum != null && !this.tsnum.equals(tsnum));
        this.tsnum = tsnum;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getXwageId();
        columnValues[1] = getXtimesheetId();
        columnValues[2] = getDay();
        columnValues[3] = getNormaltime();
        columnValues[4] = getOvertime();
        columnValues[5] = getDoubletime();
        columnValues[6] = getStoppeddetails();
        columnValues[7] = getTsnum();
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
            setXwageId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXwageId(null);
        }
        try {
            setXtimesheetId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXtimesheetId(null);
        }
        setDay(toDate(flds[2]));
        try {
            setNormaltime(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setNormaltime(null);
        }
        try {
            setOvertime(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setOvertime(null);
        }
        try {
            setDoubletime(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setDoubletime(null);
        }
        setStoppeddetails(flds[6]);
        try {
            setTsnum(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setTsnum(null);
        }
    }
}
