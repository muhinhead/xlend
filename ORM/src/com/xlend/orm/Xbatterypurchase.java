// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xbatterypurchase extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xbatterypurchaseId = null;
    private Date purchaseDate = null;
    private Date entryDate = null;
    private Integer purchasedBy = null;
    private Integer xsupplierId = null;
    private Double invoiceVatIncl = null;
    private Double invoiceVatExcl = null;

    public Xbatterypurchase(Connection connection) {
        super(connection, "xbatterypurchase", "xbatterypurchase_id");
        setColumnNames(new String[]{"xbatterypurchase_id", "purchase_date", "entry_date", "purchased_by", "xsupplier_id", "invoice_vat_incl", "invoice_vat_excl"});
    }

    public Xbatterypurchase(Connection connection, Integer xbatterypurchaseId, Date purchaseDate, Date entryDate, Integer purchasedBy, Integer xsupplierId, Double invoiceVatIncl, Double invoiceVatExcl) {
        super(connection, "xbatterypurchase", "xbatterypurchase_id");
        setNew(xbatterypurchaseId.intValue() <= 0);
//        if (xbatterypurchaseId.intValue() != 0) {
            this.xbatterypurchaseId = xbatterypurchaseId;
//        }
        this.purchaseDate = purchaseDate;
        this.entryDate = entryDate;
        this.purchasedBy = purchasedBy;
        this.xsupplierId = xsupplierId;
        this.invoiceVatIncl = invoiceVatIncl;
        this.invoiceVatExcl = invoiceVatExcl;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xbatterypurchase xbatterypurchase = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbatterypurchase_id,purchase_date,entry_date,purchased_by,xsupplier_id,invoice_vat_incl,invoice_vat_excl FROM xbatterypurchase WHERE xbatterypurchase_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xbatterypurchase = new Xbatterypurchase(getConnection());
                xbatterypurchase.setXbatterypurchaseId(new Integer(rs.getInt(1)));
                xbatterypurchase.setPurchaseDate(rs.getDate(2));
                xbatterypurchase.setEntryDate(rs.getDate(3));
                xbatterypurchase.setPurchasedBy(new Integer(rs.getInt(4)));
                xbatterypurchase.setXsupplierId(new Integer(rs.getInt(5)));
                xbatterypurchase.setInvoiceVatIncl(rs.getDouble(6));
                xbatterypurchase.setInvoiceVatExcl(rs.getDouble(7));
                xbatterypurchase.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xbatterypurchase;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xbatterypurchase ("+(getXbatterypurchaseId().intValue()!=0?"xbatterypurchase_id,":"")+"purchase_date,entry_date,purchased_by,xsupplier_id,invoice_vat_incl,invoice_vat_excl) values("+(getXbatterypurchaseId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXbatterypurchaseId().intValue()!=0) {
                 ps.setObject(++n, getXbatterypurchaseId());
             }
             ps.setObject(++n, getPurchaseDate());
             ps.setObject(++n, getEntryDate());
             ps.setObject(++n, getPurchasedBy());
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getInvoiceVatIncl());
             ps.setObject(++n, getInvoiceVatExcl());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXbatterypurchaseId().intValue()==0) {
             stmt = "SELECT max(xbatterypurchase_id) FROM xbatterypurchase";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXbatterypurchaseId(new Integer(rs.getInt(1)));
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
                    "UPDATE xbatterypurchase " +
                    "SET purchase_date = ?, entry_date = ?, purchased_by = ?, xsupplier_id = ?, invoice_vat_incl = ?, invoice_vat_excl = ?" + 
                    " WHERE xbatterypurchase_id = " + getXbatterypurchaseId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getPurchaseDate());
                ps.setObject(2, getEntryDate());
                ps.setObject(3, getPurchasedBy());
                ps.setObject(4, getXsupplierId());
                ps.setObject(5, getInvoiceVatIncl());
                ps.setObject(6, getInvoiceVatExcl());
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
        if (Xbattery.exists(getConnection(),"xbatterypurchase_id = " + getXbatterypurchaseId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbattery_xbatterypurchase_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xbatterypurchase " +
                "WHERE xbatterypurchase_id = " + getXbatterypurchaseId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXbatterypurchaseId(new Integer(-getXbatterypurchaseId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXbatterypurchaseId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbatterypurchase_id,purchase_date,entry_date,purchased_by,xsupplier_id,invoice_vat_incl,invoice_vat_excl FROM xbatterypurchase " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xbatterypurchase(con,new Integer(rs.getInt(1)),rs.getDate(2),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),rs.getDouble(6),rs.getDouble(7)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xbatterypurchase[] objects = new Xbatterypurchase[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xbatterypurchase xbatterypurchase = (Xbatterypurchase) lst.get(i);
            objects[i] = xbatterypurchase;
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
        String stmt = "SELECT xbatterypurchase_id FROM xbatterypurchase " +
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
    //    return getXbatterypurchaseId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xbatterypurchaseId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXbatterypurchaseId(id);
        setNew(prevIsNew);
    }

    public Integer getXbatterypurchaseId() {
        return xbatterypurchaseId;
    }

    public void setXbatterypurchaseId(Integer xbatterypurchaseId) throws ForeignKeyViolationException {
        setWasChanged(this.xbatterypurchaseId != null && this.xbatterypurchaseId != xbatterypurchaseId);
        this.xbatterypurchaseId = xbatterypurchaseId;
        setNew(xbatterypurchaseId.intValue() == 0);
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.purchaseDate != null && !this.purchaseDate.equals(purchaseDate));
        this.purchaseDate = purchaseDate;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.entryDate != null && !this.entryDate.equals(entryDate));
        this.entryDate = entryDate;
    }

    public Integer getPurchasedBy() {
        return purchasedBy;
    }

    public void setPurchasedBy(Integer purchasedBy) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.purchasedBy != null && !this.purchasedBy.equals(purchasedBy));
        this.purchasedBy = purchasedBy;
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xbatterypurchase_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Double getInvoiceVatIncl() {
        return invoiceVatIncl;
    }

    public void setInvoiceVatIncl(Double invoiceVatIncl) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invoiceVatIncl != null && !this.invoiceVatIncl.equals(invoiceVatIncl));
        this.invoiceVatIncl = invoiceVatIncl;
    }

    public Double getInvoiceVatExcl() {
        return invoiceVatExcl;
    }

    public void setInvoiceVatExcl(Double invoiceVatExcl) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invoiceVatExcl != null && !this.invoiceVatExcl.equals(invoiceVatExcl));
        this.invoiceVatExcl = invoiceVatExcl;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXbatterypurchaseId();
        columnValues[1] = getPurchaseDate();
        columnValues[2] = getEntryDate();
        columnValues[3] = getPurchasedBy();
        columnValues[4] = getXsupplierId();
        columnValues[5] = getInvoiceVatIncl();
        columnValues[6] = getInvoiceVatExcl();
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
            setXbatterypurchaseId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXbatterypurchaseId(null);
        }
        setPurchaseDate(toDate(flds[1]));
        setEntryDate(toDate(flds[2]));
        try {
            setPurchasedBy(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setPurchasedBy(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        try {
            setInvoiceVatIncl(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setInvoiceVatIncl(null);
        }
        try {
            setInvoiceVatExcl(Double.parseDouble(flds[6]));
        } catch(NumberFormatException ne) {
            setInvoiceVatExcl(null);
        }
    }
}
