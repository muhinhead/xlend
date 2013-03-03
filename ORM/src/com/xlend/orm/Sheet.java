// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Mar 03 14:21:17 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Sheet extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer sheetId = null;
    private String sheetname = null;
    private String classname = null;
    private Integer parentId = null;

    public Sheet(Connection connection) {
        super(connection, "sheet", "sheet_id");
        setColumnNames(new String[]{"sheet_id", "sheetname", "classname", "parent_id"});
    }

    public Sheet(Connection connection, Integer sheetId, String sheetname, String classname, Integer parentId) {
        super(connection, "sheet", "sheet_id");
        setNew(sheetId.intValue() <= 0);
//        if (sheetId.intValue() != 0) {
            this.sheetId = sheetId;
//        }
        this.sheetname = sheetname;
        this.classname = classname;
        this.parentId = parentId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Sheet sheet = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT sheet_id,sheetname,classname,parent_id FROM sheet WHERE sheet_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                sheet = new Sheet(getConnection());
                sheet.setSheetId(new Integer(rs.getInt(1)));
                sheet.setSheetname(rs.getString(2));
                sheet.setClassname(rs.getString(3));
                sheet.setParentId(new Integer(rs.getInt(4)));
                sheet.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return sheet;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO sheet ("+(getSheetId().intValue()!=0?"sheet_id,":"")+"sheetname,classname,parent_id) values("+(getSheetId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getSheetId().intValue()!=0) {
                 ps.setObject(++n, getSheetId());
             }
             ps.setObject(++n, getSheetname());
             ps.setObject(++n, getClassname());
             ps.setObject(++n, getParentId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getSheetId().intValue()==0) {
             stmt = "SELECT max(sheet_id) FROM sheet";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setSheetId(new Integer(rs.getInt(1)));
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
                    "UPDATE sheet " +
                    "SET sheetname = ?, classname = ?, parent_id = ?" + 
                    " WHERE sheet_id = " + getSheetId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getSheetname());
                ps.setObject(2, getClassname());
                ps.setObject(3, getParentId());
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
        {// delete cascade from reportgroup
            Reportgroup[] records = (Reportgroup[])Reportgroup.load(getConnection(),"sheet_id = " + getSheetId(),null);
            for (int i = 0; i<records.length; i++) {
                Reportgroup reportgroup = records[i];
                reportgroup.delete();
            }
        }
        {// delete cascade from reportgroup
            Reportgroup[] records = (Reportgroup[])Reportgroup.load(getConnection(),"sheetgroup_id = " + getSheetId(),null);
            for (int i = 0; i<records.length; i++) {
                Reportgroup reportgroup = records[i];
                reportgroup.delete();
            }
        }
        {// delete cascade from usersheet
            Usersheet[] records = (Usersheet[])Usersheet.load(getConnection(),"sheet_id = " + getSheetId(),null);
            for (int i = 0; i<records.length; i++) {
                Usersheet usersheet = records[i];
                usersheet.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM sheet " +
                "WHERE sheet_id = " + getSheetId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setSheetId(new Integer(-getSheetId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getSheetId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT sheet_id,sheetname,classname,parent_id FROM sheet " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Sheet(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),new Integer(rs.getInt(4))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Sheet[] objects = new Sheet[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Sheet sheet = (Sheet) lst.get(i);
            objects[i] = sheet;
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
        String stmt = "SELECT sheet_id FROM sheet " +
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
    //    return getSheetId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return sheetId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setSheetId(id);
        setNew(prevIsNew);
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public void setSheetId(Integer sheetId) throws ForeignKeyViolationException {
        setWasChanged(this.sheetId != null && this.sheetId != sheetId);
        this.sheetId = sheetId;
        setNew(sheetId.intValue() == 0);
    }

    public String getSheetname() {
        return sheetname;
    }

    public void setSheetname(String sheetname) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.sheetname != null && !this.sheetname.equals(sheetname));
        this.sheetname = sheetname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.classname != null && !this.classname.equals(classname));
        this.classname = classname;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) throws SQLException, ForeignKeyViolationException {
        if (null != parentId)
            parentId = parentId == 0 ? null : parentId;
        setWasChanged(this.parentId != null && !this.parentId.equals(parentId));
        this.parentId = parentId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getSheetId();
        columnValues[1] = getSheetname();
        columnValues[2] = getClassname();
        columnValues[3] = getParentId();
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
            setSheetId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setSheetId(null);
        }
        setSheetname(flds[1]);
        setClassname(flds[2]);
        try {
            setParentId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setParentId(null);
        }
    }
}
