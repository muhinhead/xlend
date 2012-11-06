// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Nov 06 14:53:52 EET 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Reportgroup extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer reportgroupId = null;
    private Integer sheetgroupId = null;
    private Integer sheetId = null;

    public Reportgroup(Connection connection) {
        super(connection, "reportgroup", "reportgroup_id");
        setColumnNames(new String[]{"reportgroup_id", "sheetgroup_id", "sheet_id"});
    }

    public Reportgroup(Connection connection, Integer reportgroupId, Integer sheetgroupId, Integer sheetId) {
        super(connection, "reportgroup", "reportgroup_id");
        setNew(reportgroupId.intValue() <= 0);
//        if (reportgroupId.intValue() != 0) {
            this.reportgroupId = reportgroupId;
//        }
        this.sheetgroupId = sheetgroupId;
        this.sheetId = sheetId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Reportgroup reportgroup = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT reportgroup_id,sheetgroup_id,sheet_id FROM reportgroup WHERE reportgroup_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                reportgroup = new Reportgroup(getConnection());
                reportgroup.setReportgroupId(new Integer(rs.getInt(1)));
                reportgroup.setSheetgroupId(new Integer(rs.getInt(2)));
                reportgroup.setSheetId(new Integer(rs.getInt(3)));
                reportgroup.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return reportgroup;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO reportgroup ("+(getReportgroupId().intValue()!=0?"reportgroup_id,":"")+"sheetgroup_id,sheet_id) values("+(getReportgroupId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getReportgroupId().intValue()!=0) {
                 ps.setObject(++n, getReportgroupId());
             }
             ps.setObject(++n, getSheetgroupId());
             ps.setObject(++n, getSheetId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getReportgroupId().intValue()==0) {
             stmt = "SELECT max(reportgroup_id) FROM reportgroup";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setReportgroupId(new Integer(rs.getInt(1)));
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
                    "UPDATE reportgroup " +
                    "SET sheetgroup_id = ?, sheet_id = ?" + 
                    " WHERE reportgroup_id = " + getReportgroupId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getSheetgroupId());
                ps.setObject(2, getSheetId());
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
                "DELETE FROM reportgroup " +
                "WHERE reportgroup_id = " + getReportgroupId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setReportgroupId(new Integer(-getReportgroupId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getReportgroupId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT reportgroup_id,sheetgroup_id,sheet_id FROM reportgroup " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Reportgroup(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Reportgroup[] objects = new Reportgroup[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Reportgroup reportgroup = (Reportgroup) lst.get(i);
            objects[i] = reportgroup;
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
        String stmt = "SELECT reportgroup_id FROM reportgroup " +
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
    //    return getReportgroupId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return reportgroupId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setReportgroupId(id);
        setNew(prevIsNew);
    }

    public Integer getReportgroupId() {
        return reportgroupId;
    }

    public void setReportgroupId(Integer reportgroupId) throws ForeignKeyViolationException {
        setWasChanged(this.reportgroupId != null && this.reportgroupId != reportgroupId);
        this.reportgroupId = reportgroupId;
        setNew(reportgroupId.intValue() == 0);
    }

    public Integer getSheetgroupId() {
        return sheetgroupId;
    }

    public void setSheetgroupId(Integer sheetgroupId) throws SQLException, ForeignKeyViolationException {
        if (sheetgroupId!=null && !Sheet.exists(getConnection(),"sheet_id = " + sheetgroupId)) {
            throw new ForeignKeyViolationException("Can't set sheetgroup_id, foreign key violation: reportgroup_sheet_fk");
        }
        setWasChanged(this.sheetgroupId != null && !this.sheetgroupId.equals(sheetgroupId));
        this.sheetgroupId = sheetgroupId;
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public void setSheetId(Integer sheetId) throws SQLException, ForeignKeyViolationException {
        if (sheetId!=null && !Sheet.exists(getConnection(),"sheet_id = " + sheetId)) {
            throw new ForeignKeyViolationException("Can't set sheet_id, foreign key violation: reportgroup_sheet_fk2");
        }
        setWasChanged(this.sheetId != null && !this.sheetId.equals(sheetId));
        this.sheetId = sheetId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getReportgroupId();
        columnValues[1] = getSheetgroupId();
        columnValues[2] = getSheetId();
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
            setReportgroupId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setReportgroupId(null);
        }
        try {
            setSheetgroupId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setSheetgroupId(null);
        }
        try {
            setSheetId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setSheetId(null);
        }
    }
}
