// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Feb 12 13:05:13 EET 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xdieselpchs extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xdieselpchsId = null;
    private Integer xsupplierId = null;
    private Date purchased = null;
    private Integer authorizerId = null;
    private Integer amountLiters = null;
    private Double amountRands = null;
    private Integer paidbyId = null;
    private Integer xpaidmethodId = null;

    public Xdieselpchs(Connection connection) {
        super(connection, "xdieselpchs", "xdieselpchs_id");
        setColumnNames(new String[]{"xdieselpchs_id", "xsupplier_id", "purchased", "authorizer_id", "amount_liters", "amount_rands", "paidby_id", "xpaidmethod_id"});
    }

    public Xdieselpchs(Connection connection, Integer xdieselpchsId, Integer xsupplierId, Date purchased, Integer authorizerId, Integer amountLiters, Double amountRands, Integer paidbyId, Integer xpaidmethodId) {
        super(connection, "xdieselpchs", "xdieselpchs_id");
        setNew(xdieselpchsId.intValue() <= 0);
//        if (xdieselpchsId.intValue() != 0) {
            this.xdieselpchsId = xdieselpchsId;
//        }
        this.xsupplierId = xsupplierId;
        this.purchased = purchased;
        this.authorizerId = authorizerId;
        this.amountLiters = amountLiters;
        this.amountRands = amountRands;
        this.paidbyId = paidbyId;
        this.xpaidmethodId = xpaidmethodId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xdieselpchs xdieselpchs = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselpchs_id,xsupplier_id,purchased,authorizer_id,amount_liters,amount_rands,paidby_id,xpaidmethod_id FROM xdieselpchs WHERE xdieselpchs_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xdieselpchs = new Xdieselpchs(getConnection());
                xdieselpchs.setXdieselpchsId(new Integer(rs.getInt(1)));
                xdieselpchs.setXsupplierId(new Integer(rs.getInt(2)));
                xdieselpchs.setPurchased(rs.getDate(3));
                xdieselpchs.setAuthorizerId(new Integer(rs.getInt(4)));
                xdieselpchs.setAmountLiters(new Integer(rs.getInt(5)));
                xdieselpchs.setAmountRands(rs.getDouble(6));
                xdieselpchs.setPaidbyId(new Integer(rs.getInt(7)));
                xdieselpchs.setXpaidmethodId(new Integer(rs.getInt(8)));
                xdieselpchs.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xdieselpchs;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xdieselpchs ("+(getXdieselpchsId().intValue()!=0?"xdieselpchs_id,":"")+"xsupplier_id,purchased,authorizer_id,amount_liters,amount_rands,paidby_id,xpaidmethod_id) values("+(getXdieselpchsId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXdieselpchsId().intValue()!=0) {
                 ps.setObject(++n, getXdieselpchsId());
             }
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getPurchased());
             ps.setObject(++n, getAuthorizerId());
             ps.setObject(++n, getAmountLiters());
             ps.setObject(++n, getAmountRands());
             ps.setObject(++n, getPaidbyId());
             ps.setObject(++n, getXpaidmethodId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXdieselpchsId().intValue()==0) {
             stmt = "SELECT max(xdieselpchs_id) FROM xdieselpchs";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXdieselpchsId(new Integer(rs.getInt(1)));
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
                    "UPDATE xdieselpchs " +
                    "SET xsupplier_id = ?, purchased = ?, authorizer_id = ?, amount_liters = ?, amount_rands = ?, paidby_id = ?, xpaidmethod_id = ?" + 
                    " WHERE xdieselpchs_id = " + getXdieselpchsId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsupplierId());
                ps.setObject(2, getPurchased());
                ps.setObject(3, getAuthorizerId());
                ps.setObject(4, getAmountLiters());
                ps.setObject(5, getAmountRands());
                ps.setObject(6, getPaidbyId());
                ps.setObject(7, getXpaidmethodId());
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
                "DELETE FROM xdieselpchs " +
                "WHERE xdieselpchs_id = " + getXdieselpchsId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXdieselpchsId(new Integer(-getXdieselpchsId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXdieselpchsId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselpchs_id,xsupplier_id,purchased,authorizer_id,amount_liters,amount_rands,paidby_id,xpaidmethod_id FROM xdieselpchs " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xdieselpchs(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),rs.getDouble(6),new Integer(rs.getInt(7)),new Integer(rs.getInt(8))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xdieselpchs[] objects = new Xdieselpchs[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xdieselpchs xdieselpchs = (Xdieselpchs) lst.get(i);
            objects[i] = xdieselpchs;
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
        String stmt = "SELECT xdieselpchs_id FROM xdieselpchs " +
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
    //    return getXdieselpchsId() + getDelimiter();
    //}

    public Integer getXdieselpchsId() {
        return xdieselpchsId;
    }

    public void setXdieselpchsId(Integer xdieselpchsId) throws ForeignKeyViolationException {
        setWasChanged(this.xdieselpchsId != null && this.xdieselpchsId != xdieselpchsId);
        this.xdieselpchsId = xdieselpchsId;
        setNew(xdieselpchsId.intValue() == 0);
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xdieselpchs_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Date getPurchased() {
        return purchased;
    }

    public void setPurchased(Date purchased) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.purchased != null && !this.purchased.equals(purchased));
        this.purchased = purchased;
    }

    public Integer getAuthorizerId() {
        return authorizerId;
    }

    public void setAuthorizerId(Integer authorizerId) throws SQLException, ForeignKeyViolationException {
        if (null != authorizerId)
            authorizerId = authorizerId == 0 ? null : authorizerId;
        if (authorizerId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + authorizerId)) {
            throw new ForeignKeyViolationException("Can't set authorizer_id, foreign key violation: xdieselpchs_xemployee_fk");
        }
        setWasChanged(this.authorizerId != null && !this.authorizerId.equals(authorizerId));
        this.authorizerId = authorizerId;
    }

    public Integer getAmountLiters() {
        return amountLiters;
    }

    public void setAmountLiters(Integer amountLiters) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.amountLiters != null && !this.amountLiters.equals(amountLiters));
        this.amountLiters = amountLiters;
    }

    public Double getAmountRands() {
        return amountRands;
    }

    public void setAmountRands(Double amountRands) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.amountRands != null && !this.amountRands.equals(amountRands));
        this.amountRands = amountRands;
    }

    public Integer getPaidbyId() {
        return paidbyId;
    }

    public void setPaidbyId(Integer paidbyId) throws SQLException, ForeignKeyViolationException {
        if (null != paidbyId)
            paidbyId = paidbyId == 0 ? null : paidbyId;
        if (paidbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + paidbyId)) {
            throw new ForeignKeyViolationException("Can't set paidby_id, foreign key violation: xdieselpchs_xemployee_fk2");
        }
        setWasChanged(this.paidbyId != null && !this.paidbyId.equals(paidbyId));
        this.paidbyId = paidbyId;
    }

    public Integer getXpaidmethodId() {
        return xpaidmethodId;
    }

    public void setXpaidmethodId(Integer xpaidmethodId) throws SQLException, ForeignKeyViolationException {
        if (xpaidmethodId!=null && !Xpaidmethod.exists(getConnection(),"xpaidmethod_id = " + xpaidmethodId)) {
            throw new ForeignKeyViolationException("Can't set xpaidmethod_id, foreign key violation: xdieselpchs_xpaidmethod_fk");
        }
        setWasChanged(this.xpaidmethodId != null && !this.xpaidmethodId.equals(xpaidmethodId));
        this.xpaidmethodId = xpaidmethodId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getXdieselpchsId();
        columnValues[1] = getXsupplierId();
        columnValues[2] = getPurchased();
        columnValues[3] = getAuthorizerId();
        columnValues[4] = getAmountLiters();
        columnValues[5] = getAmountRands();
        columnValues[6] = getPaidbyId();
        columnValues[7] = getXpaidmethodId();
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
            setXdieselpchsId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXdieselpchsId(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        setPurchased(toDate(flds[2]));
        try {
            setAuthorizerId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setAuthorizerId(null);
        }
        try {
            setAmountLiters(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setAmountLiters(null);
        }
        try {
            setAmountRands(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setAmountRands(null);
        }
        try {
            setPaidbyId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setPaidbyId(null);
        }
        try {
            setXpaidmethodId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setXpaidmethodId(null);
        }
    }
}
