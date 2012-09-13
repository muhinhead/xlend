// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Sep 13 20:03:38 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Dbversion extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer dbversionId = null;
    private Integer versionId = null;
    private String version = null;

    public Dbversion(Connection connection) {
        super(connection, "dbversion", "dbversion_id");
        setColumnNames(new String[]{"dbversion_id", "version_id", "version"});
    }

    public Dbversion(Connection connection, Integer dbversionId, Integer versionId, String version) {
        super(connection, "dbversion", "dbversion_id");
        setNew(dbversionId.intValue() <= 0);
//        if (dbversionId.intValue() != 0) {
            this.dbversionId = dbversionId;
//        }
        this.versionId = versionId;
        this.version = version;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Dbversion dbversion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT dbversion_id,version_id,version FROM dbversion WHERE dbversion_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                dbversion = new Dbversion(getConnection());
                dbversion.setDbversionId(new Integer(rs.getInt(1)));
                dbversion.setVersionId(new Integer(rs.getInt(2)));
                dbversion.setVersion(rs.getString(3));
                dbversion.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return dbversion;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO dbversion ("+(getDbversionId().intValue()!=0?"dbversion_id,":"")+"version_id,version) values("+(getDbversionId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getDbversionId().intValue()!=0) {
                 ps.setObject(++n, getDbversionId());
             }
             ps.setObject(++n, getVersionId());
             ps.setObject(++n, getVersion());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getDbversionId().intValue()==0) {
             stmt = "SELECT max(dbversion_id) FROM dbversion";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setDbversionId(new Integer(rs.getInt(1)));
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
                    "UPDATE dbversion " +
                    "SET version_id = ?, version = ?" + 
                    " WHERE dbversion_id = " + getDbversionId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getVersionId());
                ps.setObject(2, getVersion());
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
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM dbversion " +
                "WHERE dbversion_id = " + getDbversionId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setDbversionId(new Integer(-getDbversionId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getDbversionId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT dbversion_id,version_id,version FROM dbversion " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Dbversion(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Dbversion[] objects = new Dbversion[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Dbversion dbversion = (Dbversion) lst.get(i);
            objects[i] = dbversion;
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
        String stmt = "SELECT dbversion_id FROM dbversion " +
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
    //    return getDbversionId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return dbversionId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setDbversionId(id);
        setNew(prevIsNew);
    }

    public Integer getDbversionId() {
        return dbversionId;
    }

    public void setDbversionId(Integer dbversionId) throws ForeignKeyViolationException {
        setWasChanged(this.dbversionId != null && this.dbversionId != dbversionId);
        this.dbversionId = dbversionId;
        setNew(dbversionId.intValue() == 0);
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.versionId != null && !this.versionId.equals(versionId));
        this.versionId = versionId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.version != null && !this.version.equals(version));
        this.version = version;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getDbversionId();
        columnValues[1] = getVersionId();
        columnValues[2] = getVersion();
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
            setDbversionId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setDbversionId(null);
        }
        try {
            setVersionId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setVersionId(null);
        }
        setVersion(flds[2]);
    }
}
