// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Wed May 30 20:26:21 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xsalary extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xsalaryId = null;
    private Integer xsalarylistId = null;
    private Integer xemployeeId = null;
    private Double amount = null;

    public Xsalary(Connection connection) {
        super(connection, "xsalary", "xsalary_id");
        setColumnNames(new String[]{"xsalary_id", "xsalarylist_id", "xemployee_id", "amount"});
    }

    public Xsalary(Connection connection, Integer xsalaryId, Integer xsalarylistId, Integer xemployeeId, Double amount) {
        super(connection, "xsalary", "xsalary_id");
        setNew(xsalaryId.intValue() <= 0);
//        if (xsalaryId.intValue() != 0) {
            this.xsalaryId = xsalaryId;
//        }
        this.xsalarylistId = xsalarylistId;
        this.xemployeeId = xemployeeId;
        this.amount = amount;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xsalary xsalary = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsalary_id,xsalarylist_id,xemployee_id,amount FROM xsalary WHERE xsalary_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xsalary = new Xsalary(getConnection());
                xsalary.setXsalaryId(new Integer(rs.getInt(1)));
                xsalary.setXsalarylistId(new Integer(rs.getInt(2)));
                xsalary.setXemployeeId(new Integer(rs.getInt(3)));
                xsalary.setAmount(rs.getDouble(4));
                xsalary.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xsalary;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xsalary ("+(getXsalaryId().intValue()!=0?"xsalary_id,":"")+"xsalarylist_id,xemployee_id,amount) values("+(getXsalaryId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXsalaryId().intValue()!=0) {
                 ps.setObject(++n, getXsalaryId());
             }
             ps.setObject(++n, getXsalarylistId());
             ps.setObject(++n, getXemployeeId());
             ps.setObject(++n, getAmount());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXsalaryId().intValue()==0) {
             stmt = "SELECT max(xsalary_id) FROM xsalary";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXsalaryId(new Integer(rs.getInt(1)));
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
                    "UPDATE xsalary " +
                    "SET xsalarylist_id = ?, xemployee_id = ?, amount = ?" + 
                    " WHERE xsalary_id = " + getXsalaryId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsalarylistId());
                ps.setObject(2, getXemployeeId());
                ps.setObject(3, getAmount());
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
                "DELETE FROM xsalary " +
                "WHERE xsalary_id = " + getXsalaryId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXsalaryId(new Integer(-getXsalaryId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXsalaryId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsalary_id,xsalarylist_id,xemployee_id,amount FROM xsalary " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xsalary(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getDouble(4)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xsalary[] objects = new Xsalary[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xsalary xsalary = (Xsalary) lst.get(i);
            objects[i] = xsalary;
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
        String stmt = "SELECT xsalary_id FROM xsalary " +
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
    //    return getXsalaryId() + getDelimiter();
    //}

    public Integer getXsalaryId() {
        return xsalaryId;
    }

    public void setXsalaryId(Integer xsalaryId) throws ForeignKeyViolationException {
        setWasChanged(this.xsalaryId != null && this.xsalaryId != xsalaryId);
        this.xsalaryId = xsalaryId;
        setNew(xsalaryId.intValue() == 0);
    }

    public Integer getXsalarylistId() {
        return xsalarylistId;
    }

    public void setXsalarylistId(Integer xsalarylistId) throws SQLException, ForeignKeyViolationException {
        if (xsalarylistId!=null && !Xsalarylist.exists(getConnection(),"xsalarylist_id = " + xsalarylistId)) {
            throw new ForeignKeyViolationException("Can't set xsalarylist_id, foreign key violation: xsalary_xsalarylist_fk");
        }
        setWasChanged(this.xsalarylistId != null && !this.xsalarylistId.equals(xsalarylistId));
        this.xsalarylistId = xsalarylistId;
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xsalary_xemployee_fk");
        }
        setWasChanged(this.xemployeeId != null && !this.xemployeeId.equals(xemployeeId));
        this.xemployeeId = xemployeeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.amount != null && !this.amount.equals(amount));
        this.amount = amount;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getXsalaryId();
        columnValues[1] = getXsalarylistId();
        columnValues[2] = getXemployeeId();
        columnValues[3] = getAmount();
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
            setXsalaryId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXsalaryId(null);
        }
        try {
            setXsalarylistId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsalarylistId(null);
        }
        try {
            setXemployeeId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
        try {
            setAmount(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setAmount(null);
        }
    }
}
