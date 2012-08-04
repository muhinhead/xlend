// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Aug 04 17:19:28 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xpartstocks extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpartstocksId = null;
    private Integer xpartsId = null;
    private Integer xstocksId = null;
    private Integer xsupplierId = null;
    private Double rest = null;

    public Xpartstocks(Connection connection) {
        super(connection, "xpartstocks", "xpartstocks_id");
        setColumnNames(new String[]{"xpartstocks_id", "xparts_id", "xstocks_id", "xsupplier_id", "rest"});
    }

    public Xpartstocks(Connection connection, Integer xpartstocksId, Integer xpartsId, Integer xstocksId, Integer xsupplierId, Double rest) {
        super(connection, "xpartstocks", "xpartstocks_id");
        setNew(xpartstocksId.intValue() <= 0);
//        if (xpartstocksId.intValue() != 0) {
            this.xpartstocksId = xpartstocksId;
//        }
        this.xpartsId = xpartsId;
        this.xstocksId = xstocksId;
        this.xsupplierId = xsupplierId;
        this.rest = rest;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xpartstocks xpartstocks = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpartstocks_id,xparts_id,xstocks_id,xsupplier_id,rest FROM xpartstocks WHERE xpartstocks_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xpartstocks = new Xpartstocks(getConnection());
                xpartstocks.setXpartstocksId(new Integer(rs.getInt(1)));
                xpartstocks.setXpartsId(new Integer(rs.getInt(2)));
                xpartstocks.setXstocksId(new Integer(rs.getInt(3)));
                xpartstocks.setXsupplierId(new Integer(rs.getInt(4)));
                xpartstocks.setRest(rs.getDouble(5));
                xpartstocks.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xpartstocks;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xpartstocks ("+(getXpartstocksId().intValue()!=0?"xpartstocks_id,":"")+"xparts_id,xstocks_id,xsupplier_id,rest) values("+(getXpartstocksId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpartstocksId().intValue()!=0) {
                 ps.setObject(++n, getXpartstocksId());
             }
             ps.setObject(++n, getXpartsId());
             ps.setObject(++n, getXstocksId());
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getRest());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpartstocksId().intValue()==0) {
             stmt = "SELECT max(xpartstocks_id) FROM xpartstocks";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpartstocksId(new Integer(rs.getInt(1)));
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
                    "UPDATE xpartstocks " +
                    "SET xparts_id = ?, xstocks_id = ?, xsupplier_id = ?, rest = ?" + 
                    " WHERE xpartstocks_id = " + getXpartstocksId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXpartsId());
                ps.setObject(2, getXstocksId());
                ps.setObject(3, getXsupplierId());
                ps.setObject(4, getRest());
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
                "DELETE FROM xpartstocks " +
                "WHERE xpartstocks_id = " + getXpartstocksId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpartstocksId(new Integer(-getXpartstocksId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpartstocksId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpartstocks_id,xparts_id,xstocks_id,xsupplier_id,rest FROM xpartstocks " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xpartstocks(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDouble(5)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xpartstocks[] objects = new Xpartstocks[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xpartstocks xpartstocks = (Xpartstocks) lst.get(i);
            objects[i] = xpartstocks;
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
        String stmt = "SELECT xpartstocks_id FROM xpartstocks " +
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
    //    return getXpartstocksId() + getDelimiter();
    //}

    public Integer getXpartstocksId() {
        return xpartstocksId;
    }

    public void setXpartstocksId(Integer xpartstocksId) throws ForeignKeyViolationException {
        setWasChanged(this.xpartstocksId != null && this.xpartstocksId != xpartstocksId);
        this.xpartstocksId = xpartstocksId;
        setNew(xpartstocksId.intValue() == 0);
    }

    public Integer getXpartsId() {
        return xpartsId;
    }

    public void setXpartsId(Integer xpartsId) throws SQLException, ForeignKeyViolationException {
        if (xpartsId!=null && !Xparts.exists(getConnection(),"xparts_id = " + xpartsId)) {
            throw new ForeignKeyViolationException("Can't set xparts_id, foreign key violation: xpartstocks_xparts_fk");
        }
        setWasChanged(this.xpartsId != null && !this.xpartsId.equals(xpartsId));
        this.xpartsId = xpartsId;
    }

    public Integer getXstocksId() {
        return xstocksId;
    }

    public void setXstocksId(Integer xstocksId) throws SQLException, ForeignKeyViolationException {
        if (xstocksId!=null && !Xstocks.exists(getConnection(),"xstocks_id = " + xstocksId)) {
            throw new ForeignKeyViolationException("Can't set xstocks_id, foreign key violation: xpartstocks_xstocks_fk");
        }
        setWasChanged(this.xstocksId != null && !this.xstocksId.equals(xstocksId));
        this.xstocksId = xstocksId;
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xpartstocks_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Double getRest() {
        return rest;
    }

    public void setRest(Double rest) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.rest != null && !this.rest.equals(rest));
        this.rest = rest;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[5];
        columnValues[0] = getXpartstocksId();
        columnValues[1] = getXpartsId();
        columnValues[2] = getXstocksId();
        columnValues[3] = getXsupplierId();
        columnValues[4] = getRest();
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
            setXpartstocksId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpartstocksId(null);
        }
        try {
            setXpartsId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXpartsId(null);
        }
        try {
            setXstocksId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXstocksId(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        try {
            setRest(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setRest(null);
        }
    }
}
