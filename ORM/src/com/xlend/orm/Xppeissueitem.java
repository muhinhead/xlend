// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Sep 12 08:40:42 FET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xppeissueitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xppeissueitemId = null;
    private Integer xppeissueId = null;
    private Integer xppetypeId = null;
    private Integer quantity = null;

    public Xppeissueitem(Connection connection) {
        super(connection, "xppeissueitem", "xppeissueitem_id");
        setColumnNames(new String[]{"xppeissueitem_id", "xppeissue_id", "xppetype_id", "quantity"});
    }

    public Xppeissueitem(Connection connection, Integer xppeissueitemId, Integer xppeissueId, Integer xppetypeId, Integer quantity) {
        super(connection, "xppeissueitem", "xppeissueitem_id");
        setNew(xppeissueitemId.intValue() <= 0);
//        if (xppeissueitemId.intValue() != 0) {
            this.xppeissueitemId = xppeissueitemId;
//        }
        this.xppeissueId = xppeissueId;
        this.xppetypeId = xppetypeId;
        this.quantity = quantity;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xppeissueitem xppeissueitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppeissueitem_id,xppeissue_id,xppetype_id,quantity FROM xppeissueitem WHERE xppeissueitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xppeissueitem = new Xppeissueitem(getConnection());
                xppeissueitem.setXppeissueitemId(new Integer(rs.getInt(1)));
                xppeissueitem.setXppeissueId(new Integer(rs.getInt(2)));
                xppeissueitem.setXppetypeId(new Integer(rs.getInt(3)));
                xppeissueitem.setQuantity(new Integer(rs.getInt(4)));
                xppeissueitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xppeissueitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xppeissueitem ("+(getXppeissueitemId().intValue()!=0?"xppeissueitem_id,":"")+"xppeissue_id,xppetype_id,quantity) values("+(getXppeissueitemId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXppeissueitemId().intValue()!=0) {
                 ps.setObject(++n, getXppeissueitemId());
             }
             ps.setObject(++n, getXppeissueId());
             ps.setObject(++n, getXppetypeId());
             ps.setObject(++n, getQuantity());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXppeissueitemId().intValue()==0) {
             stmt = "SELECT max(xppeissueitem_id) FROM xppeissueitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXppeissueitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE xppeissueitem " +
                    "SET xppeissue_id = ?, xppetype_id = ?, quantity = ?" + 
                    " WHERE xppeissueitem_id = " + getXppeissueitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXppeissueId());
                ps.setObject(2, getXppetypeId());
                ps.setObject(3, getQuantity());
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
                "DELETE FROM xppeissueitem " +
                "WHERE xppeissueitem_id = " + getXppeissueitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXppeissueitemId(new Integer(-getXppeissueitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXppeissueitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppeissueitem_id,xppeissue_id,xppetype_id,quantity FROM xppeissueitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xppeissueitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xppeissueitem[] objects = new Xppeissueitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xppeissueitem xppeissueitem = (Xppeissueitem) lst.get(i);
            objects[i] = xppeissueitem;
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
        String stmt = "SELECT xppeissueitem_id FROM xppeissueitem " +
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
    //    return getXppeissueitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xppeissueitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXppeissueitemId(id);
        setNew(prevIsNew);
    }

    public Integer getXppeissueitemId() {
        return xppeissueitemId;
    }

    public void setXppeissueitemId(Integer xppeissueitemId) throws ForeignKeyViolationException {
        setWasChanged(this.xppeissueitemId != null && this.xppeissueitemId != xppeissueitemId);
        this.xppeissueitemId = xppeissueitemId;
        setNew(xppeissueitemId.intValue() == 0);
    }

    public Integer getXppeissueId() {
        return xppeissueId;
    }

    public void setXppeissueId(Integer xppeissueId) throws SQLException, ForeignKeyViolationException {
        if (xppeissueId!=null && !Xppeissue.exists(getConnection(),"xppeissue_id = " + xppeissueId)) {
            throw new ForeignKeyViolationException("Can't set xppeissue_id, foreign key violation: xppeissueitem_xppeissue_fk");
        }
        setWasChanged(this.xppeissueId != null && !this.xppeissueId.equals(xppeissueId));
        this.xppeissueId = xppeissueId;
    }

    public Integer getXppetypeId() {
        return xppetypeId;
    }

    public void setXppetypeId(Integer xppetypeId) throws SQLException, ForeignKeyViolationException {
        if (xppetypeId!=null && !Xppetype.exists(getConnection(),"xppetype_id = " + xppetypeId)) {
            throw new ForeignKeyViolationException("Can't set xppetype_id, foreign key violation: xppeissueitem_xppetype_fk");
        }
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
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getXppeissueitemId();
        columnValues[1] = getXppeissueId();
        columnValues[2] = getXppetypeId();
        columnValues[3] = getQuantity();
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
            setXppeissueitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXppeissueitemId(null);
        }
        try {
            setXppeissueId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXppeissueId(null);
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
    }
}
