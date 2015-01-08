// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jan 08 11:11:31 EET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xpettycategory extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpettycategoryId = null;
    private String categoryName = null;

    public Xpettycategory(Connection connection) {
        super(connection, "xpettycategory", "xpettycategory_id");
        setColumnNames(new String[]{"xpettycategory_id", "category_name"});
    }

    public Xpettycategory(Connection connection, Integer xpettycategoryId, String categoryName) {
        super(connection, "xpettycategory", "xpettycategory_id");
        setNew(xpettycategoryId.intValue() <= 0);
//        if (xpettycategoryId.intValue() != 0) {
            this.xpettycategoryId = xpettycategoryId;
//        }
        this.categoryName = categoryName;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xpettycategory xpettycategory = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpettycategory_id,category_name FROM xpettycategory WHERE xpettycategory_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xpettycategory = new Xpettycategory(getConnection());
                xpettycategory.setXpettycategoryId(new Integer(rs.getInt(1)));
                xpettycategory.setCategoryName(rs.getString(2));
                xpettycategory.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xpettycategory;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xpettycategory ("+(getXpettycategoryId().intValue()!=0?"xpettycategory_id,":"")+"category_name) values("+(getXpettycategoryId().intValue()!=0?"?,":"")+"?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpettycategoryId().intValue()!=0) {
                 ps.setObject(++n, getXpettycategoryId());
             }
             ps.setObject(++n, getCategoryName());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpettycategoryId().intValue()==0) {
             stmt = "SELECT max(xpettycategory_id) FROM xpettycategory";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpettycategoryId(new Integer(rs.getInt(1)));
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
                    "UPDATE xpettycategory " +
                    "SET category_name = ?" + 
                    " WHERE xpettycategory_id = " + getXpettycategoryId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getCategoryName());
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
        if (Xpettyitem.exists(getConnection(),"xpettycategory_id = " + getXpettycategoryId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xpettyitem_xpettycategory_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xpettycategory " +
                "WHERE xpettycategory_id = " + getXpettycategoryId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpettycategoryId(new Integer(-getXpettycategoryId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpettycategoryId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpettycategory_id,category_name FROM xpettycategory " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xpettycategory(con,new Integer(rs.getInt(1)),rs.getString(2)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xpettycategory[] objects = new Xpettycategory[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xpettycategory xpettycategory = (Xpettycategory) lst.get(i);
            objects[i] = xpettycategory;
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
        String stmt = "SELECT xpettycategory_id FROM xpettycategory " +
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
    //    return getXpettycategoryId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xpettycategoryId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXpettycategoryId(id);
        setNew(prevIsNew);
    }

    public Integer getXpettycategoryId() {
        return xpettycategoryId;
    }

    public void setXpettycategoryId(Integer xpettycategoryId) throws ForeignKeyViolationException {
        setWasChanged(this.xpettycategoryId != null && this.xpettycategoryId != xpettycategoryId);
        this.xpettycategoryId = xpettycategoryId;
        setNew(xpettycategoryId.intValue() == 0);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.categoryName != null && !this.categoryName.equals(categoryName));
        this.categoryName = categoryName;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[2];
        columnValues[0] = getXpettycategoryId();
        columnValues[1] = getCategoryName();
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
            setXpettycategoryId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpettycategoryId(null);
        }
        setCategoryName(flds[1]);
    }
}
