// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jun 28 14:06:17 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xpayment extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpaymentId = null;
    private Integer xsupplierId = null;
    private Date paydate = null;
    private Double ammount = null;
    private Integer paidfrom = null;
    private Integer paydbyId = null;

    public Xpayment(Connection connection) {
        super(connection, "xpayment", "xpayment_id");
        setColumnNames(new String[]{"xpayment_id", "xsupplier_id", "paydate", "ammount", "paidfrom", "paydby_id"});
    }

    public Xpayment(Connection connection, Integer xpaymentId, Integer xsupplierId, Date paydate, Double ammount, Integer paidfrom, Integer paydbyId) {
        super(connection, "xpayment", "xpayment_id");
        setNew(xpaymentId.intValue() <= 0);
//        if (xpaymentId.intValue() != 0) {
            this.xpaymentId = xpaymentId;
//        }
        this.xsupplierId = xsupplierId;
        this.paydate = paydate;
        this.ammount = ammount;
        this.paidfrom = paidfrom;
        this.paydbyId = paydbyId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xpayment xpayment = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpayment_id,xsupplier_id,paydate,ammount,paidfrom,paydby_id FROM xpayment WHERE xpayment_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xpayment = new Xpayment(getConnection());
                xpayment.setXpaymentId(new Integer(rs.getInt(1)));
                xpayment.setXsupplierId(new Integer(rs.getInt(2)));
                xpayment.setPaydate(rs.getDate(3));
                xpayment.setAmmount(rs.getDouble(4));
                xpayment.setPaidfrom(new Integer(rs.getInt(5)));
                xpayment.setPaydbyId(new Integer(rs.getInt(6)));
                xpayment.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xpayment;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xpayment ("+(getXpaymentId().intValue()!=0?"xpayment_id,":"")+"xsupplier_id,paydate,ammount,paidfrom,paydby_id) values("+(getXpaymentId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpaymentId().intValue()!=0) {
                 ps.setObject(++n, getXpaymentId());
             }
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getPaydate());
             ps.setObject(++n, getAmmount());
             ps.setObject(++n, getPaidfrom());
             ps.setObject(++n, getPaydbyId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpaymentId().intValue()==0) {
             stmt = "SELECT max(xpayment_id) FROM xpayment";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpaymentId(new Integer(rs.getInt(1)));
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
                    "UPDATE xpayment " +
                    "SET xsupplier_id = ?, paydate = ?, ammount = ?, paidfrom = ?, paydby_id = ?" + 
                    " WHERE xpayment_id = " + getXpaymentId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsupplierId());
                ps.setObject(2, getPaydate());
                ps.setObject(3, getAmmount());
                ps.setObject(4, getPaidfrom());
                ps.setObject(5, getPaydbyId());
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
                "DELETE FROM xpayment " +
                "WHERE xpayment_id = " + getXpaymentId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpaymentId(new Integer(-getXpaymentId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpaymentId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpayment_id,xsupplier_id,paydate,ammount,paidfrom,paydby_id FROM xpayment " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xpayment(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),rs.getDouble(4),new Integer(rs.getInt(5)),new Integer(rs.getInt(6))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xpayment[] objects = new Xpayment[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xpayment xpayment = (Xpayment) lst.get(i);
            objects[i] = xpayment;
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
        String stmt = "SELECT xpayment_id FROM xpayment " +
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
    //    return getXpaymentId() + getDelimiter();
    //}

    public Integer getXpaymentId() {
        return xpaymentId;
    }

    public void setXpaymentId(Integer xpaymentId) throws ForeignKeyViolationException {
        setWasChanged(this.xpaymentId != null && this.xpaymentId != xpaymentId);
        this.xpaymentId = xpaymentId;
        setNew(xpaymentId.intValue() == 0);
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xpayment_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.paydate != null && !this.paydate.equals(paydate));
        this.paydate = paydate;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.ammount != null && !this.ammount.equals(ammount));
        this.ammount = ammount;
    }

    public Integer getPaidfrom() {
        return paidfrom;
    }

    public void setPaidfrom(Integer paidfrom) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.paidfrom != null && !this.paidfrom.equals(paidfrom));
        this.paidfrom = paidfrom;
    }

    public Integer getPaydbyId() {
        return paydbyId;
    }

    public void setPaydbyId(Integer paydbyId) throws SQLException, ForeignKeyViolationException {
        if (null != paydbyId)
            paydbyId = paydbyId == 0 ? null : paydbyId;
        if (paydbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + paydbyId)) {
            throw new ForeignKeyViolationException("Can't set paydby_id, foreign key violation: xpayment_xemployee_fk");
        }
        setWasChanged(this.paydbyId != null && !this.paydbyId.equals(paydbyId));
        this.paydbyId = paydbyId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXpaymentId();
        columnValues[1] = getXsupplierId();
        columnValues[2] = getPaydate();
        columnValues[3] = getAmmount();
        columnValues[4] = getPaidfrom();
        columnValues[5] = getPaydbyId();
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
            setXpaymentId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpaymentId(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        setPaydate(toDate(flds[2]));
        try {
            setAmmount(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setAmmount(null);
        }
        try {
            setPaidfrom(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setPaidfrom(null);
        }
        try {
            setPaydbyId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setPaydbyId(null);
        }
    }
}
