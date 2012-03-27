// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Mar 27 16:17:54 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xtimesheet extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xtimesheetId = null;
    private Integer xemployeeId = null;
    private Integer xorderId = null;
    private Integer xsiteId = null;
    private Date weekend = null;
    private Integer clocksheet = null;
    private Object image = null;

    public Xtimesheet(Connection connection) {
        super(connection, "xtimesheet", "xtimesheet_id");
        setColumnNames(new String[]{"xtimesheet_id", "xemployee_id", "xorder_id", "xsite_id", "weekend", "clocksheet", "image"});
    }

    public Xtimesheet(Connection connection, Integer xtimesheetId, Integer xemployeeId, Integer xorderId, Integer xsiteId, Date weekend, Integer clocksheet, Object image) {
        super(connection, "xtimesheet", "xtimesheet_id");
        setNew(xtimesheetId.intValue() <= 0);
//        if (xtimesheetId.intValue() != 0) {
            this.xtimesheetId = xtimesheetId;
//        }
        this.xemployeeId = xemployeeId;
        this.xorderId = xorderId;
        this.xsiteId = xsiteId;
        this.weekend = weekend;
        this.clocksheet = clocksheet;
        this.image = image;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtimesheet xtimesheet = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtimesheet_id,xemployee_id,xorder_id,xsite_id,weekend,clocksheet,image FROM xtimesheet WHERE xtimesheet_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtimesheet = new Xtimesheet(getConnection());
                xtimesheet.setXtimesheetId(new Integer(rs.getInt(1)));
                xtimesheet.setXemployeeId(new Integer(rs.getInt(2)));
                xtimesheet.setXorderId(new Integer(rs.getInt(3)));
                xtimesheet.setXsiteId(new Integer(rs.getInt(4)));
                xtimesheet.setWeekend(rs.getDate(5));
                xtimesheet.setClocksheet(new Integer(rs.getInt(6)));
                xtimesheet.setImage(rs.getObject(7));
                xtimesheet.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xtimesheet;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xtimesheet ("+(getXtimesheetId().intValue()!=0?"xtimesheet_id,":"")+"xemployee_id,xorder_id,xsite_id,weekend,clocksheet,image) values("+(getXtimesheetId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtimesheetId().intValue()!=0) {
                 ps.setObject(++n, getXtimesheetId());
             }
             ps.setObject(++n, getXemployeeId());
             ps.setObject(++n, getXorderId());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getWeekend());
             ps.setObject(++n, getClocksheet());
             ps.setObject(++n, getImage());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXtimesheetId().intValue()==0) {
             stmt = "SELECT max(xtimesheet_id) FROM xtimesheet";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXtimesheetId(new Integer(rs.getInt(1)));
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
                    "UPDATE xtimesheet " +
                    "SET xemployee_id = ?, xorder_id = ?, xsite_id = ?, weekend = ?, clocksheet = ?, image = ?" + 
                    " WHERE xtimesheet_id = " + getXtimesheetId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXemployeeId());
                ps.setObject(2, getXorderId());
                ps.setObject(3, getXsiteId());
                ps.setObject(4, getWeekend());
                ps.setObject(5, getClocksheet());
                ps.setObject(6, getImage());
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
        if (Xwagesumitem.exists(getConnection(),"xtimesheet_id = " + getXtimesheetId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xwagesumitem_xtimesheet_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xwage
            Xwage[] records = (Xwage[])Xwage.load(getConnection(),"xtimesheet_id = " + getXtimesheetId(),null);
            for (int i = 0; i<records.length; i++) {
                Xwage xwage = records[i];
                xwage.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xtimesheet " +
                "WHERE xtimesheet_id = " + getXtimesheetId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXtimesheetId(new Integer(-getXtimesheetId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXtimesheetId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtimesheet_id,xemployee_id,xorder_id,xsite_id,weekend,clocksheet,image FROM xtimesheet " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtimesheet(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDate(5),new Integer(rs.getInt(6)),rs.getObject(7)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xtimesheet[] objects = new Xtimesheet[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xtimesheet xtimesheet = (Xtimesheet) lst.get(i);
            objects[i] = xtimesheet;
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
        String stmt = "SELECT xtimesheet_id FROM xtimesheet " +
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
    //    return getXtimesheetId() + getDelimiter();
    //}

    public Integer getXtimesheetId() {
        return xtimesheetId;
    }

    public void setXtimesheetId(Integer xtimesheetId) throws ForeignKeyViolationException {
        setWasChanged(this.xtimesheetId != null && this.xtimesheetId != xtimesheetId);
        this.xtimesheetId = xtimesheetId;
        setNew(xtimesheetId.intValue() == 0);
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xtimesheet_xemployee_fk");
        }
        setWasChanged(this.xemployeeId != null && !this.xemployeeId.equals(xemployeeId));
        this.xemployeeId = xemployeeId;
    }

    public Integer getXorderId() {
        return xorderId;
    }

    public void setXorderId(Integer xorderId) throws SQLException, ForeignKeyViolationException {
        if (xorderId!=null && !Xorder.exists(getConnection(),"xorder_id = " + xorderId)) {
            throw new ForeignKeyViolationException("Can't set xorder_id, foreign key violation: xtimesheet_xorder_fk");
        }
        setWasChanged(this.xorderId != null && !this.xorderId.equals(xorderId));
        this.xorderId = xorderId;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xtimesheet_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Date getWeekend() {
        return weekend;
    }

    public void setWeekend(Date weekend) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.weekend != null && !this.weekend.equals(weekend));
        this.weekend = weekend;
    }

    public Integer getClocksheet() {
        return clocksheet;
    }

    public void setClocksheet(Integer clocksheet) throws SQLException, ForeignKeyViolationException {
        if (null != clocksheet)
            clocksheet = clocksheet == 0 ? null : clocksheet;
        setWasChanged(this.clocksheet != null && !this.clocksheet.equals(clocksheet));
        this.clocksheet = clocksheet;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.image != null && !this.image.equals(image));
        this.image = image;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXtimesheetId();
        columnValues[1] = getXemployeeId();
        columnValues[2] = getXorderId();
        columnValues[3] = getXsiteId();
        columnValues[4] = getWeekend();
        columnValues[5] = getClocksheet();
        columnValues[6] = getImage();
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
            setXtimesheetId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXtimesheetId(null);
        }
        try {
            setXemployeeId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
        try {
            setXorderId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXorderId(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        setWeekend(toDate(flds[4]));
        try {
            setClocksheet(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setClocksheet(null);
        }
        setImage(flds[6]);
    }
}
