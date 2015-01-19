// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xaccpayment extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xaccpaymentId = null;
    private Integer xemployeeId = null;
    private Double amount = null;
    private Integer xsiteId = null;
    private Date date1 = null;
    private Date date2 = null;

    public Xaccpayment(Connection connection) {
        super(connection, "xaccpayment", "xaccpayment_id");
        setColumnNames(new String[]{"xaccpayment_id", "xemployee_id", "amount", "xsite_id", "date1", "date2"});
    }

    public Xaccpayment(Connection connection, Integer xaccpaymentId, Integer xemployeeId, Double amount, Integer xsiteId, Date date1, Date date2) {
        super(connection, "xaccpayment", "xaccpayment_id");
        setNew(xaccpaymentId.intValue() <= 0);
//        if (xaccpaymentId.intValue() != 0) {
            this.xaccpaymentId = xaccpaymentId;
//        }
        this.xemployeeId = xemployeeId;
        this.amount = amount;
        this.xsiteId = xsiteId;
        this.date1 = date1;
        this.date2 = date2;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xaccpayment xaccpayment = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xaccpayment_id,xemployee_id,amount,xsite_id,date1,date2 FROM xaccpayment WHERE xaccpayment_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xaccpayment = new Xaccpayment(getConnection());
                xaccpayment.setXaccpaymentId(new Integer(rs.getInt(1)));
                xaccpayment.setXemployeeId(new Integer(rs.getInt(2)));
                xaccpayment.setAmount(rs.getDouble(3));
                xaccpayment.setXsiteId(new Integer(rs.getInt(4)));
                xaccpayment.setDate1(rs.getDate(5));
                xaccpayment.setDate2(rs.getDate(6));
                xaccpayment.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xaccpayment;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xaccpayment ("+(getXaccpaymentId().intValue()!=0?"xaccpayment_id,":"")+"xemployee_id,amount,xsite_id,date1,date2) values("+(getXaccpaymentId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXaccpaymentId().intValue()!=0) {
                 ps.setObject(++n, getXaccpaymentId());
             }
             ps.setObject(++n, getXemployeeId());
             ps.setObject(++n, getAmount());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getDate1());
             ps.setObject(++n, getDate2());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXaccpaymentId().intValue()==0) {
             stmt = "SELECT max(xaccpayment_id) FROM xaccpayment";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXaccpaymentId(new Integer(rs.getInt(1)));
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
                    "UPDATE xaccpayment " +
                    "SET xemployee_id = ?, amount = ?, xsite_id = ?, date1 = ?, date2 = ?" + 
                    " WHERE xaccpayment_id = " + getXaccpaymentId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXemployeeId());
                ps.setObject(2, getAmount());
                ps.setObject(3, getXsiteId());
                ps.setObject(4, getDate1());
                ps.setObject(5, getDate2());
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
                "DELETE FROM xaccpayment " +
                "WHERE xaccpayment_id = " + getXaccpaymentId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXaccpaymentId(new Integer(-getXaccpaymentId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXaccpaymentId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xaccpayment_id,xemployee_id,amount,xsite_id,date1,date2 FROM xaccpayment " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xaccpayment(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDouble(3),new Integer(rs.getInt(4)),rs.getDate(5),rs.getDate(6)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xaccpayment[] objects = new Xaccpayment[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xaccpayment xaccpayment = (Xaccpayment) lst.get(i);
            objects[i] = xaccpayment;
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
        String stmt = "SELECT xaccpayment_id FROM xaccpayment " +
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
    //    return getXaccpaymentId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xaccpaymentId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXaccpaymentId(id);
        setNew(prevIsNew);
    }

    public Integer getXaccpaymentId() {
        return xaccpaymentId;
    }

    public void setXaccpaymentId(Integer xaccpaymentId) throws ForeignKeyViolationException {
        setWasChanged(this.xaccpaymentId != null && this.xaccpaymentId != xaccpaymentId);
        this.xaccpaymentId = xaccpaymentId;
        setNew(xaccpaymentId.intValue() == 0);
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xaccpayment_xemployee_fk");
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

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xaccpayment_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.date1 != null && !this.date1.equals(date1));
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.date2 != null && !this.date2.equals(date2));
        this.date2 = date2;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXaccpaymentId();
        columnValues[1] = getXemployeeId();
        columnValues[2] = getAmount();
        columnValues[3] = getXsiteId();
        columnValues[4] = getDate1();
        columnValues[5] = getDate2();
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
            setXaccpaymentId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXaccpaymentId(null);
        }
        try {
            setXemployeeId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
        try {
            setAmount(Double.parseDouble(flds[2]));
        } catch(NumberFormatException ne) {
            setAmount(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        setDate1(toDate(flds[4]));
        setDate2(toDate(flds[5]));
    }
}
