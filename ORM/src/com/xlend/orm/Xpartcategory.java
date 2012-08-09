// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Aug 09 09:09:25 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xpartcategory extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpartcategoryId = null;
    private Integer groupId = null;
    private String name = null;
    private Integer parentId = null;

    public Xpartcategory(Connection connection) {
        super(connection, "xpartcategory", "xpartcategory_id");
        setColumnNames(new String[]{"xpartcategory_id", "group_id", "name", "parent_id"});
    }

    public Xpartcategory(Connection connection, Integer xpartcategoryId, Integer groupId, String name, Integer parentId) {
        super(connection, "xpartcategory", "xpartcategory_id");
        setNew(xpartcategoryId.intValue() <= 0);
//        if (xpartcategoryId.intValue() != 0) {
            this.xpartcategoryId = xpartcategoryId;
//        }
        this.groupId = groupId;
        this.name = name;
        this.parentId = parentId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xpartcategory xpartcategory = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpartcategory_id,group_id,name,parent_id FROM xpartcategory WHERE xpartcategory_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xpartcategory = new Xpartcategory(getConnection());
                xpartcategory.setXpartcategoryId(new Integer(rs.getInt(1)));
                xpartcategory.setGroupId(new Integer(rs.getInt(2)));
                xpartcategory.setName(rs.getString(3));
                xpartcategory.setParentId(new Integer(rs.getInt(4)));
                xpartcategory.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xpartcategory;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xpartcategory ("+(getXpartcategoryId().intValue()!=0?"xpartcategory_id,":"")+"group_id,name,parent_id) values("+(getXpartcategoryId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpartcategoryId().intValue()!=0) {
                 ps.setObject(++n, getXpartcategoryId());
             }
             ps.setObject(++n, getGroupId());
             ps.setObject(++n, getName());
             ps.setObject(++n, getParentId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpartcategoryId().intValue()==0) {
             stmt = "SELECT max(xpartcategory_id) FROM xpartcategory";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpartcategoryId(new Integer(rs.getInt(1)));
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
                    "UPDATE xpartcategory " +
                    "SET group_id = ?, name = ?, parent_id = ?" + 
                    " WHERE xpartcategory_id = " + getXpartcategoryId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getGroupId());
                ps.setObject(2, getName());
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
        if (Xparts.exists(getConnection(),"xpartcategory_id = " + getXpartcategoryId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xparts_xpartcategory_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xpartcategory " +
                "WHERE xpartcategory_id = " + getXpartcategoryId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpartcategoryId(new Integer(-getXpartcategoryId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpartcategoryId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpartcategory_id,group_id,name,parent_id FROM xpartcategory " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xpartcategory(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3),new Integer(rs.getInt(4))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xpartcategory[] objects = new Xpartcategory[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xpartcategory xpartcategory = (Xpartcategory) lst.get(i);
            objects[i] = xpartcategory;
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
        String stmt = "SELECT xpartcategory_id FROM xpartcategory " +
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
    //    return getXpartcategoryId() + getDelimiter();
    //}

    public Integer getXpartcategoryId() {
        return xpartcategoryId;
    }

    public void setXpartcategoryId(Integer xpartcategoryId) throws ForeignKeyViolationException {
        setWasChanged(this.xpartcategoryId != null && this.xpartcategoryId != xpartcategoryId);
        this.xpartcategoryId = xpartcategoryId;
        setNew(xpartcategoryId.intValue() == 0);
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.groupId != null && !this.groupId.equals(groupId));
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.name != null && !this.name.equals(name));
        this.name = name;
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
        columnValues[0] = getXpartcategoryId();
        columnValues[1] = getGroupId();
        columnValues[2] = getName();
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
            setXpartcategoryId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpartcategoryId(null);
        }
        try {
            setGroupId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setGroupId(null);
        }
        setName(flds[2]);
        try {
            setParentId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setParentId(null);
        }
    }
}
