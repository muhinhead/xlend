// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Aug 07 08:44:26 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xparts extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpartsId = null;
    private String serialnumber = null;
    private String description = null;
    private Integer xmachtypeId = null;
    private Integer xpartcategoryId = null;

    public Xparts(Connection connection) {
        super(connection, "xparts", "xparts_id");
        setColumnNames(new String[]{"xparts_id", "serialnumber", "description", "xmachtype_id", "xpartcategory_id"});
    }

    public Xparts(Connection connection, Integer xpartsId, String serialnumber, String description, Integer xmachtypeId, Integer xpartcategoryId) {
        super(connection, "xparts", "xparts_id");
        setNew(xpartsId.intValue() <= 0);
//        if (xpartsId.intValue() != 0) {
            this.xpartsId = xpartsId;
//        }
        this.serialnumber = serialnumber;
        this.description = description;
        this.xmachtypeId = xmachtypeId;
        this.xpartcategoryId = xpartcategoryId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xparts xparts = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xparts_id,serialnumber,description,xmachtype_id,xpartcategory_id FROM xparts WHERE xparts_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xparts = new Xparts(getConnection());
                xparts.setXpartsId(new Integer(rs.getInt(1)));
                xparts.setSerialnumber(rs.getString(2));
                xparts.setDescription(rs.getString(3));
                xparts.setXmachtypeId(new Integer(rs.getInt(4)));
                xparts.setXpartcategoryId(new Integer(rs.getInt(5)));
                xparts.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xparts;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xparts ("+(getXpartsId().intValue()!=0?"xparts_id,":"")+"serialnumber,description,xmachtype_id,xpartcategory_id) values("+(getXpartsId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpartsId().intValue()!=0) {
                 ps.setObject(++n, getXpartsId());
             }
             ps.setObject(++n, getSerialnumber());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getXmachtypeId());
             ps.setObject(++n, getXpartcategoryId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpartsId().intValue()==0) {
             stmt = "SELECT max(xparts_id) FROM xparts";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpartsId(new Integer(rs.getInt(1)));
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
                    "UPDATE xparts " +
                    "SET serialnumber = ?, description = ?, xmachtype_id = ?, xpartcategory_id = ?" + 
                    " WHERE xparts_id = " + getXpartsId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getSerialnumber());
                ps.setObject(2, getDescription());
                ps.setObject(3, getXmachtypeId());
                ps.setObject(4, getXpartcategoryId());
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
        if (Xpartstocks.exists(getConnection(),"xparts_id = " + getXpartsId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xpartstocks_xparts_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xparts " +
                "WHERE xparts_id = " + getXpartsId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpartsId(new Integer(-getXpartsId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpartsId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xparts_id,serialnumber,description,xmachtype_id,xpartcategory_id FROM xparts " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xparts(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xparts[] objects = new Xparts[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xparts xparts = (Xparts) lst.get(i);
            objects[i] = xparts;
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
        String stmt = "SELECT xparts_id FROM xparts " +
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
    //    return getXpartsId() + getDelimiter();
    //}

    public Integer getXpartsId() {
        return xpartsId;
    }

    public void setXpartsId(Integer xpartsId) throws ForeignKeyViolationException {
        setWasChanged(this.xpartsId != null && this.xpartsId != xpartsId);
        this.xpartsId = xpartsId;
        setNew(xpartsId.intValue() == 0);
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.serialnumber != null && !this.serialnumber.equals(serialnumber));
        this.serialnumber = serialnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }

    public Integer getXmachtypeId() {
        return xmachtypeId;
    }

    public void setXmachtypeId(Integer xmachtypeId) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.xmachtypeId != null && !this.xmachtypeId.equals(xmachtypeId));
        this.xmachtypeId = xmachtypeId;
    }

    public Integer getXpartcategoryId() {
        return xpartcategoryId;
    }

    public void setXpartcategoryId(Integer xpartcategoryId) throws SQLException, ForeignKeyViolationException {
        if (xpartcategoryId!=null && !Xpartcategory.exists(getConnection(),"xpartcategory_id = " + xpartcategoryId)) {
            throw new ForeignKeyViolationException("Can't set xpartcategory_id, foreign key violation: xparts_xpartcategory_fk");
        }
        setWasChanged(this.xpartcategoryId != null && !this.xpartcategoryId.equals(xpartcategoryId));
        this.xpartcategoryId = xpartcategoryId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[5];
        columnValues[0] = getXpartsId();
        columnValues[1] = getSerialnumber();
        columnValues[2] = getDescription();
        columnValues[3] = getXmachtypeId();
        columnValues[4] = getXpartcategoryId();
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
            setXpartsId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpartsId(null);
        }
        setSerialnumber(flds[1]);
        setDescription(flds[2]);
        try {
            setXmachtypeId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXmachtypeId(null);
        }
        try {
            setXpartcategoryId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXpartcategoryId(null);
        }
    }
}
