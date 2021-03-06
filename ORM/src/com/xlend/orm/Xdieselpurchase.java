// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xdieselpurchase extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xdieselpurchaseId = null;
    private Integer xsupplierId = null;
    private Date purchaseDate = null;
    private Double litres = null;
    private Double randFactor = null;
    private Double paid = null;

    public Xdieselpurchase(Connection connection) {
        super(connection, "xdieselpurchase", "xdieselpurchase_id");
        setColumnNames(new String[]{"xdieselpurchase_id", "xsupplier_id", "purchase_date", "litres", "rand_factor", "paid"});
    }

    public Xdieselpurchase(Connection connection, Integer xdieselpurchaseId, Integer xsupplierId, Date purchaseDate, Double litres, Double randFactor, Double paid) {
        super(connection, "xdieselpurchase", "xdieselpurchase_id");
        setNew(xdieselpurchaseId.intValue() <= 0);
//        if (xdieselpurchaseId.intValue() != 0) {
            this.xdieselpurchaseId = xdieselpurchaseId;
//        }
        this.xsupplierId = xsupplierId;
        this.purchaseDate = purchaseDate;
        this.litres = litres;
        this.randFactor = randFactor;
        this.paid = paid;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xdieselpurchase xdieselpurchase = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselpurchase_id,xsupplier_id,purchase_date,litres,rand_factor,paid FROM xdieselpurchase WHERE xdieselpurchase_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xdieselpurchase = new Xdieselpurchase(getConnection());
                xdieselpurchase.setXdieselpurchaseId(new Integer(rs.getInt(1)));
                xdieselpurchase.setXsupplierId(new Integer(rs.getInt(2)));
                xdieselpurchase.setPurchaseDate(rs.getDate(3));
                xdieselpurchase.setLitres(rs.getDouble(4));
                xdieselpurchase.setRandFactor(rs.getDouble(5));
                xdieselpurchase.setPaid(rs.getDouble(6));
                xdieselpurchase.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xdieselpurchase;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xdieselpurchase ("+(getXdieselpurchaseId().intValue()!=0?"xdieselpurchase_id,":"")+"xsupplier_id,purchase_date,litres,rand_factor,paid) values("+(getXdieselpurchaseId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXdieselpurchaseId().intValue()!=0) {
                 ps.setObject(++n, getXdieselpurchaseId());
             }
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getPurchaseDate());
             ps.setObject(++n, getLitres());
             ps.setObject(++n, getRandFactor());
             ps.setObject(++n, getPaid());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXdieselpurchaseId().intValue()==0) {
             stmt = "SELECT max(xdieselpurchase_id) FROM xdieselpurchase";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXdieselpurchaseId(new Integer(rs.getInt(1)));
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
                    "UPDATE xdieselpurchase " +
                    "SET xsupplier_id = ?, purchase_date = ?, litres = ?, rand_factor = ?, paid = ?" + 
                    " WHERE xdieselpurchase_id = " + getXdieselpurchaseId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsupplierId());
                ps.setObject(2, getPurchaseDate());
                ps.setObject(3, getLitres());
                ps.setObject(4, getRandFactor());
                ps.setObject(5, getPaid());
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
                "DELETE FROM xdieselpurchase " +
                "WHERE xdieselpurchase_id = " + getXdieselpurchaseId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXdieselpurchaseId(new Integer(-getXdieselpurchaseId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXdieselpurchaseId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselpurchase_id,xsupplier_id,purchase_date,litres,rand_factor,paid FROM xdieselpurchase " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xdieselpurchase(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xdieselpurchase[] objects = new Xdieselpurchase[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xdieselpurchase xdieselpurchase = (Xdieselpurchase) lst.get(i);
            objects[i] = xdieselpurchase;
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
        String stmt = "SELECT xdieselpurchase_id FROM xdieselpurchase " +
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
    //    return getXdieselpurchaseId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xdieselpurchaseId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXdieselpurchaseId(id);
        setNew(prevIsNew);
    }

    public Integer getXdieselpurchaseId() {
        return xdieselpurchaseId;
    }

    public void setXdieselpurchaseId(Integer xdieselpurchaseId) throws ForeignKeyViolationException {
        setWasChanged(this.xdieselpurchaseId != null && this.xdieselpurchaseId != xdieselpurchaseId);
        this.xdieselpurchaseId = xdieselpurchaseId;
        setNew(xdieselpurchaseId.intValue() == 0);
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xdieselpurchase_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.purchaseDate != null && !this.purchaseDate.equals(purchaseDate));
        this.purchaseDate = purchaseDate;
    }

    public Double getLitres() {
        return litres;
    }

    public void setLitres(Double litres) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.litres != null && !this.litres.equals(litres));
        this.litres = litres;
    }

    public Double getRandFactor() {
        return randFactor;
    }

    public void setRandFactor(Double randFactor) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.randFactor != null && !this.randFactor.equals(randFactor));
        this.randFactor = randFactor;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.paid != null && !this.paid.equals(paid));
        this.paid = paid;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXdieselpurchaseId();
        columnValues[1] = getXsupplierId();
        columnValues[2] = getPurchaseDate();
        columnValues[3] = getLitres();
        columnValues[4] = getRandFactor();
        columnValues[5] = getPaid();
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
            setXdieselpurchaseId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXdieselpurchaseId(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        setPurchaseDate(toDate(flds[2]));
        try {
            setLitres(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setLitres(null);
        }
        try {
            setRandFactor(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setRandFactor(null);
        }
        try {
            setPaid(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setPaid(null);
        }
    }
}
