// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Apr 25 17:26:11 EEST 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xbankbalance extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xbankbalanceId = null;
    private Timestamp balancedate = null;
    private Double totalvalue = null;

    public Xbankbalance(Connection connection) {
        super(connection, "xbankbalance", "xbankbalance_id");
        setColumnNames(new String[]{"xbankbalance_id", "balancedate", "totalvalue"});
    }

    public Xbankbalance(Connection connection, Integer xbankbalanceId, Timestamp balancedate, Double totalvalue) {
        super(connection, "xbankbalance", "xbankbalance_id");
        setNew(xbankbalanceId.intValue() <= 0);
//        if (xbankbalanceId.intValue() != 0) {
            this.xbankbalanceId = xbankbalanceId;
//        }
        this.balancedate = balancedate;
        this.totalvalue = totalvalue;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xbankbalance xbankbalance = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbankbalance_id,balancedate,totalvalue FROM xbankbalance WHERE xbankbalance_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xbankbalance = new Xbankbalance(getConnection());
                xbankbalance.setXbankbalanceId(new Integer(rs.getInt(1)));
                xbankbalance.setBalancedate(rs.getTimestamp(2));
                xbankbalance.setTotalvalue(rs.getDouble(3));
                xbankbalance.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xbankbalance;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xbankbalance ("+(getXbankbalanceId().intValue()!=0?"xbankbalance_id,":"")+"balancedate,totalvalue) values("+(getXbankbalanceId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXbankbalanceId().intValue()!=0) {
                 ps.setObject(++n, getXbankbalanceId());
             }
             ps.setObject(++n, getBalancedate());
             ps.setObject(++n, getTotalvalue());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXbankbalanceId().intValue()==0) {
             stmt = "SELECT max(xbankbalance_id) FROM xbankbalance";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXbankbalanceId(new Integer(rs.getInt(1)));
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
                    "UPDATE xbankbalance " +
                    "SET balancedate = ?, totalvalue = ?" + 
                    " WHERE xbankbalance_id = " + getXbankbalanceId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getBalancedate());
                ps.setObject(2, getTotalvalue());
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
        {// delete cascade from xbankbalancepart
            Xbankbalancepart[] records = (Xbankbalancepart[])Xbankbalancepart.load(getConnection(),"xbankbalance_id = " + getXbankbalanceId(),null);
            for (int i = 0; i<records.length; i++) {
                Xbankbalancepart xbankbalancepart = records[i];
                xbankbalancepart.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xbankbalance " +
                "WHERE xbankbalance_id = " + getXbankbalanceId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXbankbalanceId(new Integer(-getXbankbalanceId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXbankbalanceId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbankbalance_id,balancedate,totalvalue FROM xbankbalance " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xbankbalance(con,new Integer(rs.getInt(1)),rs.getTimestamp(2),rs.getDouble(3)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xbankbalance[] objects = new Xbankbalance[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xbankbalance xbankbalance = (Xbankbalance) lst.get(i);
            objects[i] = xbankbalance;
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
        String stmt = "SELECT xbankbalance_id FROM xbankbalance " +
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
    //    return getXbankbalanceId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xbankbalanceId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXbankbalanceId(id);
        setNew(prevIsNew);
    }

    public Integer getXbankbalanceId() {
        return xbankbalanceId;
    }

    public void setXbankbalanceId(Integer xbankbalanceId) throws ForeignKeyViolationException {
        setWasChanged(this.xbankbalanceId != null && this.xbankbalanceId != xbankbalanceId);
        this.xbankbalanceId = xbankbalanceId;
        setNew(xbankbalanceId.intValue() == 0);
    }

    public Timestamp getBalancedate() {
        return balancedate;
    }

    public void setBalancedate(Timestamp balancedate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.balancedate != null && !this.balancedate.equals(balancedate));
        this.balancedate = balancedate;
    }

    public Double getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(Double totalvalue) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.totalvalue != null && !this.totalvalue.equals(totalvalue));
        this.totalvalue = totalvalue;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getXbankbalanceId();
        columnValues[1] = getBalancedate();
        columnValues[2] = getTotalvalue();
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
            setXbankbalanceId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXbankbalanceId(null);
        }
        setBalancedate(toTimeStamp(flds[1]));
        try {
            setTotalvalue(Double.parseDouble(flds[2]));
        } catch(NumberFormatException ne) {
            setTotalvalue(null);
        }
    }
}
