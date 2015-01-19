// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xpettyitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpettyitemId = null;
    private Integer xpettyId = null;
    private Integer xpettycategoryId = null;
    private Double amount = null;
    private Integer xmachineId = null;
    private Integer xsiteId = null;
    private Timestamp stamp = null;

    public Xpettyitem(Connection connection) {
        super(connection, "xpettyitem", "xpettyitem_id");
        setColumnNames(new String[]{"xpettyitem_id", "xpetty_id", "xpettycategory_id", "amount", "xmachine_id", "xsite_id", "stamp"});
    }

    public Xpettyitem(Connection connection, Integer xpettyitemId, Integer xpettyId, Integer xpettycategoryId, Double amount, Integer xmachineId, Integer xsiteId, Timestamp stamp) {
        super(connection, "xpettyitem", "xpettyitem_id");
        setNew(xpettyitemId.intValue() <= 0);
//        if (xpettyitemId.intValue() != 0) {
            this.xpettyitemId = xpettyitemId;
//        }
        this.xpettyId = xpettyId;
        this.xpettycategoryId = xpettycategoryId;
        this.amount = amount;
        this.xmachineId = xmachineId;
        this.xsiteId = xsiteId;
        this.stamp = stamp;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xpettyitem xpettyitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpettyitem_id,xpetty_id,xpettycategory_id,amount,xmachine_id,xsite_id,stamp FROM xpettyitem WHERE xpettyitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xpettyitem = new Xpettyitem(getConnection());
                xpettyitem.setXpettyitemId(new Integer(rs.getInt(1)));
                xpettyitem.setXpettyId(new Integer(rs.getInt(2)));
                xpettyitem.setXpettycategoryId(new Integer(rs.getInt(3)));
                xpettyitem.setAmount(rs.getDouble(4));
                xpettyitem.setXmachineId(new Integer(rs.getInt(5)));
                xpettyitem.setXsiteId(new Integer(rs.getInt(6)));
                xpettyitem.setStamp(rs.getTimestamp(7));
                xpettyitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xpettyitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xpettyitem ("+(getXpettyitemId().intValue()!=0?"xpettyitem_id,":"")+"xpetty_id,xpettycategory_id,amount,xmachine_id,xsite_id,stamp) values("+(getXpettyitemId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpettyitemId().intValue()!=0) {
                 ps.setObject(++n, getXpettyitemId());
             }
             ps.setObject(++n, getXpettyId());
             ps.setObject(++n, getXpettycategoryId());
             ps.setObject(++n, getAmount());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getStamp());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpettyitemId().intValue()==0) {
             stmt = "SELECT max(xpettyitem_id) FROM xpettyitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpettyitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE xpettyitem " +
                    "SET xpetty_id = ?, xpettycategory_id = ?, amount = ?, xmachine_id = ?, xsite_id = ?, stamp = ?" + 
                    " WHERE xpettyitem_id = " + getXpettyitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXpettyId());
                ps.setObject(2, getXpettycategoryId());
                ps.setObject(3, getAmount());
                ps.setObject(4, getXmachineId());
                ps.setObject(5, getXsiteId());
                ps.setObject(6, getStamp());
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
                "DELETE FROM xpettyitem " +
                "WHERE xpettyitem_id = " + getXpettyitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpettyitemId(new Integer(-getXpettyitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpettyitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpettyitem_id,xpetty_id,xpettycategory_id,amount,xmachine_id,xsite_id,stamp FROM xpettyitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xpettyitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getDouble(4),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),rs.getTimestamp(7)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xpettyitem[] objects = new Xpettyitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xpettyitem xpettyitem = (Xpettyitem) lst.get(i);
            objects[i] = xpettyitem;
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
        String stmt = "SELECT xpettyitem_id FROM xpettyitem " +
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
    //    return getXpettyitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xpettyitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXpettyitemId(id);
        setNew(prevIsNew);
    }

    public Integer getXpettyitemId() {
        return xpettyitemId;
    }

    public void setXpettyitemId(Integer xpettyitemId) throws ForeignKeyViolationException {
        setWasChanged(this.xpettyitemId != null && this.xpettyitemId != xpettyitemId);
        this.xpettyitemId = xpettyitemId;
        setNew(xpettyitemId.intValue() == 0);
    }

    public Integer getXpettyId() {
        return xpettyId;
    }

    public void setXpettyId(Integer xpettyId) throws SQLException, ForeignKeyViolationException {
        if (xpettyId!=null && !Xpetty.exists(getConnection(),"xpetty_id = " + xpettyId)) {
            throw new ForeignKeyViolationException("Can't set xpetty_id, foreign key violation: xpettyitem_xpetty_fk");
        }
        setWasChanged(this.xpettyId != null && !this.xpettyId.equals(xpettyId));
        this.xpettyId = xpettyId;
    }

    public Integer getXpettycategoryId() {
        return xpettycategoryId;
    }

    public void setXpettycategoryId(Integer xpettycategoryId) throws SQLException, ForeignKeyViolationException {
        if (xpettycategoryId!=null && !Xpettycategory.exists(getConnection(),"xpettycategory_id = " + xpettycategoryId)) {
            throw new ForeignKeyViolationException("Can't set xpettycategory_id, foreign key violation: xpettyitem_xpettycategory_fk");
        }
        setWasChanged(this.xpettycategoryId != null && !this.xpettycategoryId.equals(xpettycategoryId));
        this.xpettycategoryId = xpettycategoryId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.amount != null && !this.amount.equals(amount));
        this.amount = amount;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (null != xmachineId)
            xmachineId = xmachineId == 0 ? null : xmachineId;
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xpettyitem_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xpettyitem_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Timestamp getStamp() {
        return stamp;
    }

    public void setStamp(Timestamp stamp) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.stamp != null && !this.stamp.equals(stamp));
        this.stamp = stamp;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXpettyitemId();
        columnValues[1] = getXpettyId();
        columnValues[2] = getXpettycategoryId();
        columnValues[3] = getAmount();
        columnValues[4] = getXmachineId();
        columnValues[5] = getXsiteId();
        columnValues[6] = getStamp();
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
            setXpettyitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpettyitemId(null);
        }
        try {
            setXpettyId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXpettyId(null);
        }
        try {
            setXpettycategoryId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXpettycategoryId(null);
        }
        try {
            setAmount(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setAmount(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        setStamp(toTimeStamp(flds[6]));
    }
}
