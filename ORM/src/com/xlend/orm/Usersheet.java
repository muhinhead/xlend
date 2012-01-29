// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Jan 28 18:10:38 EET 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Usersheet extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer usersheetId = null;
    private Integer profileId = null;
    private Integer sheetId = null;

    public Usersheet(Connection connection) {
        super(connection, "usersheet", "usersheet_id");
        setColumnNames(new String[]{"usersheet_id", "profile_id", "sheet_id"});
    }

    public Usersheet(Connection connection, Integer usersheetId, Integer profileId, Integer sheetId) {
        super(connection, "usersheet", "usersheet_id");
        setNew(usersheetId.intValue() <= 0);
//        if (usersheetId.intValue() != 0) {
            this.usersheetId = usersheetId;
//        }
        this.profileId = profileId;
        this.sheetId = sheetId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Usersheet usersheet = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT usersheet_id,profile_id,sheet_id FROM usersheet WHERE usersheet_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                usersheet = new Usersheet(getConnection());
                usersheet.setUsersheetId(new Integer(rs.getInt(1)));
                usersheet.setProfileId(new Integer(rs.getInt(2)));
                usersheet.setSheetId(new Integer(rs.getInt(3)));
                usersheet.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return usersheet;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO usersheet ("+(getUsersheetId().intValue()!=0?"usersheet_id,":"")+"profile_id,sheet_id) values("+(getUsersheetId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getUsersheetId().intValue()!=0) {
                 ps.setObject(++n, getUsersheetId());
             }
             ps.setObject(++n, getProfileId());
             ps.setObject(++n, getSheetId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getUsersheetId().intValue()==0) {
             stmt = "SELECT max(usersheet_id) FROM usersheet";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setUsersheetId(new Integer(rs.getInt(1)));
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
                    "UPDATE usersheet " +
                    "SET profile_id = ?, sheet_id = ?" + 
                    " WHERE usersheet_id = " + getUsersheetId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getProfileId());
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
                "DELETE FROM usersheet " +
                "WHERE usersheet_id = " + getUsersheetId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setUsersheetId(new Integer(-getUsersheetId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getUsersheetId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT usersheet_id,profile_id,sheet_id FROM usersheet " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Usersheet(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Usersheet[] objects = new Usersheet[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Usersheet usersheet = (Usersheet) lst.get(i);
            objects[i] = usersheet;
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
        String stmt = "SELECT usersheet_id FROM usersheet " +
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
    //    return getUsersheetId() + getDelimiter();
    //}

    public Integer getUsersheetId() {
        return usersheetId;
    }

    public void setUsersheetId(Integer usersheetId) throws ForeignKeyViolationException {
        setWasChanged(this.usersheetId != null && this.usersheetId != usersheetId);
        this.usersheetId = usersheetId;
        setNew(usersheetId.intValue() == 0);
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) throws SQLException, ForeignKeyViolationException {
        if (profileId!=null && !Userprofile.exists(getConnection(),"profile_id = " + profileId)) {
            throw new ForeignKeyViolationException("Can't set profile_id, foreign key violation: usersheet_user_fk");
        }
        setWasChanged(this.profileId != null && !this.profileId.equals(profileId));
        this.profileId = profileId;
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public void setSheetId(Integer sheetId) throws SQLException, ForeignKeyViolationException {
        if (sheetId!=null && !Sheet.exists(getConnection(),"sheet_id = " + sheetId)) {
            throw new ForeignKeyViolationException("Can't set sheet_id, foreign key violation: usersheet_sheet_fk");
        }
        setWasChanged(this.sheetId != null && !this.sheetId.equals(sheetId));
        this.sheetId = sheetId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getUsersheetId();
        columnValues[1] = getProfileId();
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
            setUsersheetId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setUsersheetId(null);
        }
        try {
            setProfileId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setProfileId(null);
        }
        try {
            setSheetId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setSheetId(null);
        }
    }
}