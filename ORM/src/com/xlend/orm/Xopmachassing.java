// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Aug 17 15:29:51 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xopmachassing extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xopmachassingId = null;
    private Integer xsiteId = null;
    private Integer xemployeeId = null;
    private Integer xmachineId = null;
    private Date dateStart = null;
    private Date dateEnd = null;

    public Xopmachassing(Connection connection) {
        super(connection, "xopmachassing", "xopmachassing_id");
        setColumnNames(new String[]{"xopmachassing_id", "xsite_id", "xemployee_id", "xmachine_id", "date_start", "date_end"});
    }

    public Xopmachassing(Connection connection, Integer xopmachassingId, Integer xsiteId, Integer xemployeeId, Integer xmachineId, Date dateStart, Date dateEnd) {
        super(connection, "xopmachassing", "xopmachassing_id");
        setNew(xopmachassingId.intValue() <= 0);
//        if (xopmachassingId.intValue() != 0) {
            this.xopmachassingId = xopmachassingId;
//        }
        this.xsiteId = xsiteId;
        this.xemployeeId = xemployeeId;
        this.xmachineId = xmachineId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xopmachassing xopmachassing = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xopmachassing_id,xsite_id,xemployee_id,xmachine_id,date_start,date_end FROM xopmachassing WHERE xopmachassing_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xopmachassing = new Xopmachassing(getConnection());
                xopmachassing.setXopmachassingId(new Integer(rs.getInt(1)));
                xopmachassing.setXsiteId(new Integer(rs.getInt(2)));
                xopmachassing.setXemployeeId(new Integer(rs.getInt(3)));
                xopmachassing.setXmachineId(new Integer(rs.getInt(4)));
                xopmachassing.setDateStart(rs.getDate(5));
                xopmachassing.setDateEnd(rs.getDate(6));
                xopmachassing.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xopmachassing;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xopmachassing ("+(getXopmachassingId().intValue()!=0?"xopmachassing_id,":"")+"xsite_id,xemployee_id,xmachine_id,date_start,date_end) values("+(getXopmachassingId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXopmachassingId().intValue()!=0) {
                 ps.setObject(++n, getXopmachassingId());
             }
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getXemployeeId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getDateStart());
             ps.setObject(++n, getDateEnd());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXopmachassingId().intValue()==0) {
             stmt = "SELECT max(xopmachassing_id) FROM xopmachassing";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXopmachassingId(new Integer(rs.getInt(1)));
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
                    "UPDATE xopmachassing " +
                    "SET xsite_id = ?, xemployee_id = ?, xmachine_id = ?, date_start = ?, date_end = ?" + 
                    " WHERE xopmachassing_id = " + getXopmachassingId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsiteId());
                ps.setObject(2, getXemployeeId());
                ps.setObject(3, getXmachineId());
                ps.setObject(4, getDateStart());
                ps.setObject(5, getDateEnd());
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
                "DELETE FROM xopmachassing " +
                "WHERE xopmachassing_id = " + getXopmachassingId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXopmachassingId(new Integer(-getXopmachassingId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXopmachassingId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xopmachassing_id,xsite_id,xemployee_id,xmachine_id,date_start,date_end FROM xopmachassing " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xopmachassing(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDate(5),rs.getDate(6)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xopmachassing[] objects = new Xopmachassing[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xopmachassing xopmachassing = (Xopmachassing) lst.get(i);
            objects[i] = xopmachassing;
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
        String stmt = "SELECT xopmachassing_id FROM xopmachassing " +
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
    //    return getXopmachassingId() + getDelimiter();
    //}

    public Integer getXopmachassingId() {
        return xopmachassingId;
    }

    public void setXopmachassingId(Integer xopmachassingId) throws ForeignKeyViolationException {
        setWasChanged(this.xopmachassingId != null && this.xopmachassingId != xopmachassingId);
        this.xopmachassingId = xopmachassingId;
        setNew(xopmachassingId.intValue() == 0);
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xopmachassing_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (null != xemployeeId)
            xemployeeId = xemployeeId == 0 ? null : xemployeeId;
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xopmachassing_xemployee_fk");
        }
        setWasChanged(this.xemployeeId != null && !this.xemployeeId.equals(xemployeeId));
        this.xemployeeId = xemployeeId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (null != xmachineId)
            xmachineId = xmachineId == 0 ? null : xmachineId;
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xopmachassing_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dateStart != null && !this.dateStart.equals(dateStart));
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dateEnd != null && !this.dateEnd.equals(dateEnd));
        this.dateEnd = dateEnd;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXopmachassingId();
        columnValues[1] = getXsiteId();
        columnValues[2] = getXemployeeId();
        columnValues[3] = getXmachineId();
        columnValues[4] = getDateStart();
        columnValues[5] = getDateEnd();
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
            setXopmachassingId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXopmachassingId(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setXemployeeId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        setDateStart(toDate(flds[4]));
        setDateEnd(toDate(flds[5]));
    }
}
