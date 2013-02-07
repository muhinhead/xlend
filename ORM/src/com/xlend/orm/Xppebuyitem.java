// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Feb 07 10:32:52 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xppebuyitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xppebuyitemId = null;
    private Integer xppebuyId = null;
    private Integer xppetypeId = null;
    private Integer quantity = null;
    private Double priceperunit = null;

    public Xppebuyitem(Connection connection) {
        super(connection, "xppebuyitem", "xppebuyitem_id");
        setColumnNames(new String[]{"xppebuyitem_id", "xppebuy_id", "xppetype_id", "quantity", "priceperunit"});
    }

    public Xppebuyitem(Connection connection, Integer xppebuyitemId, Integer xppebuyId, Integer xppetypeId, Integer quantity, Double priceperunit) {
        super(connection, "xppebuyitem", "xppebuyitem_id");
        setNew(xppebuyitemId.intValue() <= 0);
//        if (xppebuyitemId.intValue() != 0) {
            this.xppebuyitemId = xppebuyitemId;
//        }
        this.xppebuyId = xppebuyId;
        this.xppetypeId = xppetypeId;
        this.quantity = quantity;
        this.priceperunit = priceperunit;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xppebuyitem xppebuyitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppebuyitem_id,xppebuy_id,xppetype_id,quantity,priceperunit FROM xppebuyitem WHERE xppebuyitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xppebuyitem = new Xppebuyitem(getConnection());
                xppebuyitem.setXppebuyitemId(new Integer(rs.getInt(1)));
                xppebuyitem.setXppebuyId(new Integer(rs.getInt(2)));
                xppebuyitem.setXppetypeId(new Integer(rs.getInt(3)));
                xppebuyitem.setQuantity(new Integer(rs.getInt(4)));
                xppebuyitem.setPriceperunit(rs.getDouble(5));
                xppebuyitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xppebuyitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xppebuyitem ("+(getXppebuyitemId().intValue()!=0?"xppebuyitem_id,":"")+"xppebuy_id,xppetype_id,quantity,priceperunit) values("+(getXppebuyitemId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXppebuyitemId().intValue()!=0) {
                 ps.setObject(++n, getXppebuyitemId());
             }
             ps.setObject(++n, getXppebuyId());
             ps.setObject(++n, getXppetypeId());
             ps.setObject(++n, getQuantity());
             ps.setObject(++n, getPriceperunit());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXppebuyitemId().intValue()==0) {
             stmt = "SELECT max(xppebuyitem_id) FROM xppebuyitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXppebuyitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE xppebuyitem " +
                    "SET xppebuy_id = ?, xppetype_id = ?, quantity = ?, priceperunit = ?" + 
                    " WHERE xppebuyitem_id = " + getXppebuyitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXppebuyId());
                ps.setObject(2, getXppetypeId());
                ps.setObject(3, getQuantity());
                ps.setObject(4, getPriceperunit());
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
                "DELETE FROM xppebuyitem " +
                "WHERE xppebuyitem_id = " + getXppebuyitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXppebuyitemId(new Integer(-getXppebuyitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXppebuyitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppebuyitem_id,xppebuy_id,xppetype_id,quantity,priceperunit FROM xppebuyitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xppebuyitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDouble(5)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xppebuyitem[] objects = new Xppebuyitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xppebuyitem xppebuyitem = (Xppebuyitem) lst.get(i);
            objects[i] = xppebuyitem;
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
        String stmt = "SELECT xppebuyitem_id FROM xppebuyitem " +
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
    //    return getXppebuyitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xppebuyitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXppebuyitemId(id);
        setNew(prevIsNew);
    }

    public Integer getXppebuyitemId() {
        return xppebuyitemId;
    }

    public void setXppebuyitemId(Integer xppebuyitemId) throws ForeignKeyViolationException {
        setWasChanged(this.xppebuyitemId != null && this.xppebuyitemId != xppebuyitemId);
        this.xppebuyitemId = xppebuyitemId;
        setNew(xppebuyitemId.intValue() == 0);
    }

    public Integer getXppebuyId() {
        return xppebuyId;
    }

    public void setXppebuyId(Integer xppebuyId) throws SQLException, ForeignKeyViolationException {
        if (xppebuyId!=null && !Xppebuy.exists(getConnection(),"xppebuy_id = " + xppebuyId)) {
            throw new ForeignKeyViolationException("Can't set xppebuy_id, foreign key violation: xppebuyitem_xppeby_fk");
        }
        setWasChanged(this.xppebuyId != null && !this.xppebuyId.equals(xppebuyId));
        this.xppebuyId = xppebuyId;
    }

    public Integer getXppetypeId() {
        return xppetypeId;
    }

    public void setXppetypeId(Integer xppetypeId) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.xppetypeId != null && !this.xppetypeId.equals(xppetypeId));
        this.xppetypeId = xppetypeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.quantity != null && !this.quantity.equals(quantity));
        this.quantity = quantity;
    }

    public Double getPriceperunit() {
        return priceperunit;
    }

    public void setPriceperunit(Double priceperunit) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.priceperunit != null && !this.priceperunit.equals(priceperunit));
        this.priceperunit = priceperunit;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[5];
        columnValues[0] = getXppebuyitemId();
        columnValues[1] = getXppebuyId();
        columnValues[2] = getXppetypeId();
        columnValues[3] = getQuantity();
        columnValues[4] = getPriceperunit();
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
            setXppebuyitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXppebuyitemId(null);
        }
        try {
            setXppebuyId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXppebuyId(null);
        }
        try {
            setXppetypeId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXppetypeId(null);
        }
        try {
            setQuantity(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setQuantity(null);
        }
        try {
            setPriceperunit(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setPriceperunit(null);
        }
    }
}
