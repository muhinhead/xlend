// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jan 15 18:35:54 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xppetype extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xppetypeId = null;
    private String xppetype = null;
    private Integer stocklevel = null;

    public Xppetype(Connection connection) {
        super(connection, "xppetype", "xppetype_id");
        setColumnNames(new String[]{"xppetype_id", "xppetype", "stocklevel"});
    }

    public Xppetype(Connection connection, Integer xppetypeId, String xppetype, Integer stocklevel) {
        super(connection, "xppetype", "xppetype_id");
        setNew(xppetypeId.intValue() <= 0);
//        if (xppetypeId.intValue() != 0) {
            this.xppetypeId = xppetypeId;
//        }
        this.xppetype = xppetype;
        this.stocklevel = stocklevel;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xppetype xppetype = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppetype_id,xppetype,stocklevel FROM xppetype WHERE xppetype_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xppetype = new Xppetype(getConnection());
                xppetype.setXppetypeId(new Integer(rs.getInt(1)));
                xppetype.setXppetype(rs.getString(2));
                xppetype.setStocklevel(new Integer(rs.getInt(3)));
                xppetype.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xppetype;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xppetype ("+(getXppetypeId().intValue()!=0?"xppetype_id,":"")+"xppetype,stocklevel) values("+(getXppetypeId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXppetypeId().intValue()!=0) {
                 ps.setObject(++n, getXppetypeId());
             }
             ps.setObject(++n, getXppetype());
             ps.setObject(++n, getStocklevel());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXppetypeId().intValue()==0) {
             stmt = "SELECT max(xppetype_id) FROM xppetype";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXppetypeId(new Integer(rs.getInt(1)));
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
                    "UPDATE xppetype " +
                    "SET xppetype = ?, stocklevel = ?" + 
                    " WHERE xppetype_id = " + getXppetypeId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXppetype());
                ps.setObject(2, getStocklevel());
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
        if (Xppeissueitem.exists(getConnection(),"xppetype_id = " + getXppetypeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xppeissueitem_xppetype_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xppetype " +
                "WHERE xppetype_id = " + getXppetypeId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXppetypeId(new Integer(-getXppetypeId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXppetypeId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppetype_id,xppetype,stocklevel FROM xppetype " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xppetype(con,new Integer(rs.getInt(1)),rs.getString(2),new Integer(rs.getInt(3))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xppetype[] objects = new Xppetype[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xppetype xppetype = (Xppetype) lst.get(i);
            objects[i] = xppetype;
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
        String stmt = "SELECT xppetype_id FROM xppetype " +
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
    //    return getXppetypeId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xppetypeId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXppetypeId(id);
        setNew(prevIsNew);
    }

    public Integer getXppetypeId() {
        return xppetypeId;
    }

    public void setXppetypeId(Integer xppetypeId) throws ForeignKeyViolationException {
        setWasChanged(this.xppetypeId != null && this.xppetypeId != xppetypeId);
        this.xppetypeId = xppetypeId;
        setNew(xppetypeId.intValue() == 0);
    }

    public String getXppetype() {
        return xppetype;
    }

    public void setXppetype(String xppetype) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.xppetype != null && !this.xppetype.equals(xppetype));
        this.xppetype = xppetype;
    }

    public Integer getStocklevel() {
        return stocklevel;
    }

    public void setStocklevel(Integer stocklevel) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.stocklevel != null && !this.stocklevel.equals(stocklevel));
        this.stocklevel = stocklevel;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getXppetypeId();
        columnValues[1] = getXppetype();
        columnValues[2] = getStocklevel();
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
            setXppetypeId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXppetypeId(null);
        }
        setXppetype(flds[1]);
        try {
            setStocklevel(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setStocklevel(null);
        }
    }
}
