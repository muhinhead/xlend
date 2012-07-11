// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Jul 10 15:55:19 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xbankbalancepart extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xbankbalancepartId = null;
    private Integer xbankbalanceId = null;
    private Integer xaccountId = null;
    private Double total = null;

    public Xbankbalancepart(Connection connection) {
        super(connection, "xbankbalancepart", "xbankbalancepart_id");
        setColumnNames(new String[]{"xbankbalancepart_id", "xbankbalance_id", "xaccount_id", "total"});
    }

    public Xbankbalancepart(Connection connection, Integer xbankbalancepartId, Integer xbankbalanceId, Integer xaccountId, Double total) {
        super(connection, "xbankbalancepart", "xbankbalancepart_id");
        setNew(xbankbalancepartId.intValue() <= 0);
//        if (xbankbalancepartId.intValue() != 0) {
            this.xbankbalancepartId = xbankbalancepartId;
//        }
        this.xbankbalanceId = xbankbalanceId;
        this.xaccountId = xaccountId;
        this.total = total;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xbankbalancepart xbankbalancepart = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbankbalancepart_id,xbankbalance_id,xaccount_id,total FROM xbankbalancepart WHERE xbankbalancepart_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xbankbalancepart = new Xbankbalancepart(getConnection());
                xbankbalancepart.setXbankbalancepartId(new Integer(rs.getInt(1)));
                xbankbalancepart.setXbankbalanceId(new Integer(rs.getInt(2)));
                xbankbalancepart.setXaccountId(new Integer(rs.getInt(3)));
                xbankbalancepart.setTotal(rs.getDouble(4));
                xbankbalancepart.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xbankbalancepart;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xbankbalancepart ("+(getXbankbalancepartId().intValue()!=0?"xbankbalancepart_id,":"")+"xbankbalance_id,xaccount_id,total) values("+(getXbankbalancepartId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXbankbalancepartId().intValue()!=0) {
                 ps.setObject(++n, getXbankbalancepartId());
             }
             ps.setObject(++n, getXbankbalanceId());
             ps.setObject(++n, getXaccountId());
             ps.setObject(++n, getTotal());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXbankbalancepartId().intValue()==0) {
             stmt = "SELECT max(xbankbalancepart_id) FROM xbankbalancepart";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXbankbalancepartId(new Integer(rs.getInt(1)));
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
                    "UPDATE xbankbalancepart " +
                    "SET xbankbalance_id = ?, xaccount_id = ?, total = ?" + 
                    " WHERE xbankbalancepart_id = " + getXbankbalancepartId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXbankbalanceId());
                ps.setObject(2, getXaccountId());
                ps.setObject(3, getTotal());
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
                "DELETE FROM xbankbalancepart " +
                "WHERE xbankbalancepart_id = " + getXbankbalancepartId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXbankbalancepartId(new Integer(-getXbankbalancepartId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXbankbalancepartId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbankbalancepart_id,xbankbalance_id,xaccount_id,total FROM xbankbalancepart " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xbankbalancepart(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getDouble(4)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xbankbalancepart[] objects = new Xbankbalancepart[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xbankbalancepart xbankbalancepart = (Xbankbalancepart) lst.get(i);
            objects[i] = xbankbalancepart;
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
        String stmt = "SELECT xbankbalancepart_id FROM xbankbalancepart " +
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
    //    return getXbankbalancepartId() + getDelimiter();
    //}

    public Integer getXbankbalancepartId() {
        return xbankbalancepartId;
    }

    public void setXbankbalancepartId(Integer xbankbalancepartId) throws ForeignKeyViolationException {
        setWasChanged(this.xbankbalancepartId != null && this.xbankbalancepartId != xbankbalancepartId);
        this.xbankbalancepartId = xbankbalancepartId;
        setNew(xbankbalancepartId.intValue() == 0);
    }

    public Integer getXbankbalanceId() {
        return xbankbalanceId;
    }

    public void setXbankbalanceId(Integer xbankbalanceId) throws SQLException, ForeignKeyViolationException {
        if (xbankbalanceId!=null && !Xbankbalance.exists(getConnection(),"xbankbalance_id = " + xbankbalanceId)) {
            throw new ForeignKeyViolationException("Can't set xbankbalance_id, foreign key violation: xbankbalancepart_xbankbalance_fk");
        }
        setWasChanged(this.xbankbalanceId != null && !this.xbankbalanceId.equals(xbankbalanceId));
        this.xbankbalanceId = xbankbalanceId;
    }

    public Integer getXaccountId() {
        return xaccountId;
    }

    public void setXaccountId(Integer xaccountId) throws SQLException, ForeignKeyViolationException {
        if (xaccountId!=null && !Xaccounts.exists(getConnection(),"xaccount_id = " + xaccountId)) {
            throw new ForeignKeyViolationException("Can't set xaccount_id, foreign key violation: xbankbalancepart_xaccounts_fk");
        }
        setWasChanged(this.xaccountId != null && !this.xaccountId.equals(xaccountId));
        this.xaccountId = xaccountId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.total != null && !this.total.equals(total));
        this.total = total;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getXbankbalancepartId();
        columnValues[1] = getXbankbalanceId();
        columnValues[2] = getXaccountId();
        columnValues[3] = getTotal();
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
            setXbankbalancepartId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXbankbalancepartId(null);
        }
        try {
            setXbankbalanceId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXbankbalanceId(null);
        }
        try {
            setXaccountId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXaccountId(null);
        }
        try {
            setTotal(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setTotal(null);
        }
    }
}
