// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Aug 28 13:14:29 FET 2014
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xaddstocks extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xaddstocksId = null;
    private Integer xpartsId = null;
    private Date purchaseDate = null;
    private Integer enteredbyId = null;
    private Integer xsupplierId = null;
    private Double priceperunit = null;
    private Double quantity = null;

    public Xaddstocks(Connection connection) {
        super(connection, "xaddstocks", "xaddstocks_id");
        setColumnNames(new String[]{"xaddstocks_id", "xparts_id", "purchase_date", "enteredby_id", "xsupplier_id", "priceperunit", "quantity"});
    }

    public Xaddstocks(Connection connection, Integer xaddstocksId, Integer xpartsId, Date purchaseDate, Integer enteredbyId, Integer xsupplierId, Double priceperunit, Double quantity) {
        super(connection, "xaddstocks", "xaddstocks_id");
        setNew(xaddstocksId.intValue() <= 0);
//        if (xaddstocksId.intValue() != 0) {
            this.xaddstocksId = xaddstocksId;
//        }
        this.xpartsId = xpartsId;
        this.purchaseDate = purchaseDate;
        this.enteredbyId = enteredbyId;
        this.xsupplierId = xsupplierId;
        this.priceperunit = priceperunit;
        this.quantity = quantity;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xaddstocks xaddstocks = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xaddstocks_id,xparts_id,purchase_date,enteredby_id,xsupplier_id,priceperunit,quantity FROM xaddstocks WHERE xaddstocks_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xaddstocks = new Xaddstocks(getConnection());
                xaddstocks.setXaddstocksId(new Integer(rs.getInt(1)));
                xaddstocks.setXpartsId(new Integer(rs.getInt(2)));
                xaddstocks.setPurchaseDate(rs.getDate(3));
                xaddstocks.setEnteredbyId(new Integer(rs.getInt(4)));
                xaddstocks.setXsupplierId(new Integer(rs.getInt(5)));
                xaddstocks.setPriceperunit(rs.getDouble(6));
                xaddstocks.setQuantity(rs.getDouble(7));
                xaddstocks.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xaddstocks;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xaddstocks ("+(getXaddstocksId().intValue()!=0?"xaddstocks_id,":"")+"xparts_id,purchase_date,enteredby_id,xsupplier_id,priceperunit,quantity) values("+(getXaddstocksId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXaddstocksId().intValue()!=0) {
                 ps.setObject(++n, getXaddstocksId());
             }
             ps.setObject(++n, getXpartsId());
             ps.setObject(++n, getPurchaseDate());
             ps.setObject(++n, getEnteredbyId());
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getPriceperunit());
             ps.setObject(++n, getQuantity());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXaddstocksId().intValue()==0) {
             stmt = "SELECT max(xaddstocks_id) FROM xaddstocks";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXaddstocksId(new Integer(rs.getInt(1)));
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
                    "UPDATE xaddstocks " +
                    "SET xparts_id = ?, purchase_date = ?, enteredby_id = ?, xsupplier_id = ?, priceperunit = ?, quantity = ?" + 
                    " WHERE xaddstocks_id = " + getXaddstocksId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXpartsId());
                ps.setObject(2, getPurchaseDate());
                ps.setObject(3, getEnteredbyId());
                ps.setObject(4, getXsupplierId());
                ps.setObject(5, getPriceperunit());
                ps.setObject(6, getQuantity());
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
                "DELETE FROM xaddstocks " +
                "WHERE xaddstocks_id = " + getXaddstocksId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXaddstocksId(new Integer(-getXaddstocksId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXaddstocksId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xaddstocks_id,xparts_id,purchase_date,enteredby_id,xsupplier_id,priceperunit,quantity FROM xaddstocks " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xaddstocks(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),rs.getDouble(6),rs.getDouble(7)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xaddstocks[] objects = new Xaddstocks[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xaddstocks xaddstocks = (Xaddstocks) lst.get(i);
            objects[i] = xaddstocks;
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
        String stmt = "SELECT xaddstocks_id FROM xaddstocks " +
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
    //    return getXaddstocksId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xaddstocksId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXaddstocksId(id);
        setNew(prevIsNew);
    }

    public Integer getXaddstocksId() {
        return xaddstocksId;
    }

    public void setXaddstocksId(Integer xaddstocksId) throws ForeignKeyViolationException {
        setWasChanged(this.xaddstocksId != null && this.xaddstocksId != xaddstocksId);
        this.xaddstocksId = xaddstocksId;
        setNew(xaddstocksId.intValue() == 0);
    }

    public Integer getXpartsId() {
        return xpartsId;
    }

    public void setXpartsId(Integer xpartsId) throws SQLException, ForeignKeyViolationException {
        if (xpartsId!=null && !Xparts.exists(getConnection(),"xparts_id = " + xpartsId)) {
            throw new ForeignKeyViolationException("Can't set xparts_id, foreign key violation: xaddstocks_xparts_fk");
        }
        setWasChanged(this.xpartsId != null && !this.xpartsId.equals(xpartsId));
        this.xpartsId = xpartsId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.purchaseDate != null && !this.purchaseDate.equals(purchaseDate));
        this.purchaseDate = purchaseDate;
    }

    public Integer getEnteredbyId() {
        return enteredbyId;
    }

    public void setEnteredbyId(Integer enteredbyId) throws SQLException, ForeignKeyViolationException {
        if (enteredbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + enteredbyId)) {
            throw new ForeignKeyViolationException("Can't set enteredby_id, foreign key violation: xaddstocks_xemployee_fk");
        }
        setWasChanged(this.enteredbyId != null && !this.enteredbyId.equals(enteredbyId));
        this.enteredbyId = enteredbyId;
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xaddstocks_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Double getPriceperunit() {
        return priceperunit;
    }

    public void setPriceperunit(Double priceperunit) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.priceperunit != null && !this.priceperunit.equals(priceperunit));
        this.priceperunit = priceperunit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.quantity != null && !this.quantity.equals(quantity));
        this.quantity = quantity;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXaddstocksId();
        columnValues[1] = getXpartsId();
        columnValues[2] = getPurchaseDate();
        columnValues[3] = getEnteredbyId();
        columnValues[4] = getXsupplierId();
        columnValues[5] = getPriceperunit();
        columnValues[6] = getQuantity();
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
            setXaddstocksId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXaddstocksId(null);
        }
        try {
            setXpartsId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXpartsId(null);
        }
        setPurchaseDate(toDate(flds[2]));
        try {
            setEnteredbyId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setEnteredbyId(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        try {
            setPriceperunit(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setPriceperunit(null);
        }
        try {
            setQuantity(Double.parseDouble(flds[6]));
        } catch(NumberFormatException ne) {
            setQuantity(null);
        }
    }
}
