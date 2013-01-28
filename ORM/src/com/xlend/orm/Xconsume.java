// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 28 16:35:00 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xconsume extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xconsumeId = null;
    private Integer xsupplierId = null;
    private Integer xmachineId = null;
    private Integer requesterId = null;
    private Date invoicedate = null;
    private String invoicenumber = null;
    private Integer authorizerId = null;
    private Integer collectorId = null;
    private String description = null;
    private String partnumber = null;
    private Integer amountLiters = null;
    private Double amountRands = null;
    private Integer payerId = null;
    private Integer xpaidmethodId = null;
    private String chequenumber = null;
    private String accnum = null;
    private Integer xsiteId = null;

    public Xconsume(Connection connection) {
        super(connection, "xconsume", "xconsume_id");
        setColumnNames(new String[]{"xconsume_id", "xsupplier_id", "xmachine_id", "requester_id", "invoicedate", "invoicenumber", "authorizer_id", "collector_id", "description", "partnumber", "amount_liters", "amount_rands", "payer_id", "xpaidmethod_id", "chequenumber", "accnum", "xsite_id"});
    }

    public Xconsume(Connection connection, Integer xconsumeId, Integer xsupplierId, Integer xmachineId, Integer requesterId, Date invoicedate, String invoicenumber, Integer authorizerId, Integer collectorId, String description, String partnumber, Integer amountLiters, Double amountRands, Integer payerId, Integer xpaidmethodId, String chequenumber, String accnum, Integer xsiteId) {
        super(connection, "xconsume", "xconsume_id");
        setNew(xconsumeId.intValue() <= 0);
//        if (xconsumeId.intValue() != 0) {
            this.xconsumeId = xconsumeId;
//        }
        this.xsupplierId = xsupplierId;
        this.xmachineId = xmachineId;
        this.requesterId = requesterId;
        this.invoicedate = invoicedate;
        this.invoicenumber = invoicenumber;
        this.authorizerId = authorizerId;
        this.collectorId = collectorId;
        this.description = description;
        this.partnumber = partnumber;
        this.amountLiters = amountLiters;
        this.amountRands = amountRands;
        this.payerId = payerId;
        this.xpaidmethodId = xpaidmethodId;
        this.chequenumber = chequenumber;
        this.accnum = accnum;
        this.xsiteId = xsiteId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xconsume xconsume = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xconsume_id,xsupplier_id,xmachine_id,requester_id,invoicedate,invoicenumber,authorizer_id,collector_id,description,partnumber,amount_liters,amount_rands,payer_id,xpaidmethod_id,chequenumber,accnum,xsite_id FROM xconsume WHERE xconsume_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xconsume = new Xconsume(getConnection());
                xconsume.setXconsumeId(new Integer(rs.getInt(1)));
                xconsume.setXsupplierId(new Integer(rs.getInt(2)));
                xconsume.setXmachineId(new Integer(rs.getInt(3)));
                xconsume.setRequesterId(new Integer(rs.getInt(4)));
                xconsume.setInvoicedate(rs.getDate(5));
                xconsume.setInvoicenumber(rs.getString(6));
                xconsume.setAuthorizerId(new Integer(rs.getInt(7)));
                xconsume.setCollectorId(new Integer(rs.getInt(8)));
                xconsume.setDescription(rs.getString(9));
                xconsume.setPartnumber(rs.getString(10));
                xconsume.setAmountLiters(new Integer(rs.getInt(11)));
                xconsume.setAmountRands(rs.getDouble(12));
                xconsume.setPayerId(new Integer(rs.getInt(13)));
                xconsume.setXpaidmethodId(new Integer(rs.getInt(14)));
                xconsume.setChequenumber(rs.getString(15));
                xconsume.setAccnum(rs.getString(16));
                xconsume.setXsiteId(new Integer(rs.getInt(17)));
                xconsume.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xconsume;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xconsume ("+(getXconsumeId().intValue()!=0?"xconsume_id,":"")+"xsupplier_id,xmachine_id,requester_id,invoicedate,invoicenumber,authorizer_id,collector_id,description,partnumber,amount_liters,amount_rands,payer_id,xpaidmethod_id,chequenumber,accnum,xsite_id) values("+(getXconsumeId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXconsumeId().intValue()!=0) {
                 ps.setObject(++n, getXconsumeId());
             }
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getRequesterId());
             ps.setObject(++n, getInvoicedate());
             ps.setObject(++n, getInvoicenumber());
             ps.setObject(++n, getAuthorizerId());
             ps.setObject(++n, getCollectorId());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getPartnumber());
             ps.setObject(++n, getAmountLiters());
             ps.setObject(++n, getAmountRands());
             ps.setObject(++n, getPayerId());
             ps.setObject(++n, getXpaidmethodId());
             ps.setObject(++n, getChequenumber());
             ps.setObject(++n, getAccnum());
             ps.setObject(++n, getXsiteId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXconsumeId().intValue()==0) {
             stmt = "SELECT max(xconsume_id) FROM xconsume";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXconsumeId(new Integer(rs.getInt(1)));
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
                    "UPDATE xconsume " +
                    "SET xsupplier_id = ?, xmachine_id = ?, requester_id = ?, invoicedate = ?, invoicenumber = ?, authorizer_id = ?, collector_id = ?, description = ?, partnumber = ?, amount_liters = ?, amount_rands = ?, payer_id = ?, xpaidmethod_id = ?, chequenumber = ?, accnum = ?, xsite_id = ?" + 
                    " WHERE xconsume_id = " + getXconsumeId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsupplierId());
                ps.setObject(2, getXmachineId());
                ps.setObject(3, getRequesterId());
                ps.setObject(4, getInvoicedate());
                ps.setObject(5, getInvoicenumber());
                ps.setObject(6, getAuthorizerId());
                ps.setObject(7, getCollectorId());
                ps.setObject(8, getDescription());
                ps.setObject(9, getPartnumber());
                ps.setObject(10, getAmountLiters());
                ps.setObject(11, getAmountRands());
                ps.setObject(12, getPayerId());
                ps.setObject(13, getXpaidmethodId());
                ps.setObject(14, getChequenumber());
                ps.setObject(15, getAccnum());
                ps.setObject(16, getXsiteId());
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
        if (Xbreakconsume.exists(getConnection(),"xconsume_id = " + getXconsumeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbreakconsume_xconsume_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xconsume " +
                "WHERE xconsume_id = " + getXconsumeId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXconsumeId(new Integer(-getXconsumeId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXconsumeId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xconsume_id,xsupplier_id,xmachine_id,requester_id,invoicedate,invoicenumber,authorizer_id,collector_id,description,partnumber,amount_liters,amount_rands,payer_id,xpaidmethod_id,chequenumber,accnum,xsite_id FROM xconsume " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xconsume(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDate(5),rs.getString(6),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),rs.getString(9),rs.getString(10),new Integer(rs.getInt(11)),rs.getDouble(12),new Integer(rs.getInt(13)),new Integer(rs.getInt(14)),rs.getString(15),rs.getString(16),new Integer(rs.getInt(17))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xconsume[] objects = new Xconsume[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xconsume xconsume = (Xconsume) lst.get(i);
            objects[i] = xconsume;
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
        String stmt = "SELECT xconsume_id FROM xconsume " +
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
    //    return getXconsumeId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xconsumeId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXconsumeId(id);
        setNew(prevIsNew);
    }

    public Integer getXconsumeId() {
        return xconsumeId;
    }

    public void setXconsumeId(Integer xconsumeId) throws ForeignKeyViolationException {
        setWasChanged(this.xconsumeId != null && this.xconsumeId != xconsumeId);
        this.xconsumeId = xconsumeId;
        setNew(xconsumeId.intValue() == 0);
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xconsume_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xconsume_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) throws SQLException, ForeignKeyViolationException {
        if (requesterId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + requesterId)) {
            throw new ForeignKeyViolationException("Can't set requester_id, foreign key violation: xconsume_xemployee_fk");
        }
        setWasChanged(this.requesterId != null && !this.requesterId.equals(requesterId));
        this.requesterId = requesterId;
    }

    public Date getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(Date invoicedate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invoicedate != null && !this.invoicedate.equals(invoicedate));
        this.invoicedate = invoicedate;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invoicenumber != null && !this.invoicenumber.equals(invoicenumber));
        this.invoicenumber = invoicenumber;
    }

    public Integer getAuthorizerId() {
        return authorizerId;
    }

    public void setAuthorizerId(Integer authorizerId) throws SQLException, ForeignKeyViolationException {
        if (null != authorizerId)
            authorizerId = authorizerId == 0 ? null : authorizerId;
        if (authorizerId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + authorizerId)) {
            throw new ForeignKeyViolationException("Can't set authorizer_id, foreign key violation: xconsume_xemployee_fk2");
        }
        setWasChanged(this.authorizerId != null && !this.authorizerId.equals(authorizerId));
        this.authorizerId = authorizerId;
    }

    public Integer getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Integer collectorId) throws SQLException, ForeignKeyViolationException {
        if (null != collectorId)
            collectorId = collectorId == 0 ? null : collectorId;
        if (collectorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + collectorId)) {
            throw new ForeignKeyViolationException("Can't set collector_id, foreign key violation: xconsume_xemployee_fk3");
        }
        setWasChanged(this.collectorId != null && !this.collectorId.equals(collectorId));
        this.collectorId = collectorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.partnumber != null && !this.partnumber.equals(partnumber));
        this.partnumber = partnumber;
    }

    public Integer getAmountLiters() {
        return amountLiters;
    }

    public void setAmountLiters(Integer amountLiters) throws SQLException, ForeignKeyViolationException {
        if (null != amountLiters)
            amountLiters = amountLiters == 0 ? null : amountLiters;
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

    public Integer getPayerId() {
        return payerId;
    }

    public void setPayerId(Integer payerId) throws SQLException, ForeignKeyViolationException {
        if (null != payerId)
            payerId = payerId == 0 ? null : payerId;
        if (payerId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + payerId)) {
            throw new ForeignKeyViolationException("Can't set payer_id, foreign key violation: xconsume_xemployee_fk4");
        }
        setWasChanged(this.payerId != null && !this.payerId.equals(payerId));
        this.payerId = payerId;
    }

    public Integer getXpaidmethodId() {
        return xpaidmethodId;
    }

    public void setXpaidmethodId(Integer xpaidmethodId) throws SQLException, ForeignKeyViolationException {
        if (xpaidmethodId!=null && !Xpaidmethod.exists(getConnection(),"xpaidmethod_id = " + xpaidmethodId)) {
            throw new ForeignKeyViolationException("Can't set xpaidmethod_id, foreign key violation: xconsume_xpaidmethod_fk");
        }
        setWasChanged(this.xpaidmethodId != null && !this.xpaidmethodId.equals(xpaidmethodId));
        this.xpaidmethodId = xpaidmethodId;
    }

    public String getChequenumber() {
        return chequenumber;
    }

    public void setChequenumber(String chequenumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.chequenumber != null && !this.chequenumber.equals(chequenumber));
        this.chequenumber = chequenumber;
    }

    public String getAccnum() {
        return accnum;
    }

    public void setAccnum(String accnum) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.accnum != null && !this.accnum.equals(accnum));
        this.accnum = accnum;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (null != xsiteId)
            xsiteId = xsiteId == 0 ? null : xsiteId;
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xconsume_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[17];
        columnValues[0] = getXconsumeId();
        columnValues[1] = getXsupplierId();
        columnValues[2] = getXmachineId();
        columnValues[3] = getRequesterId();
        columnValues[4] = getInvoicedate();
        columnValues[5] = getInvoicenumber();
        columnValues[6] = getAuthorizerId();
        columnValues[7] = getCollectorId();
        columnValues[8] = getDescription();
        columnValues[9] = getPartnumber();
        columnValues[10] = getAmountLiters();
        columnValues[11] = getAmountRands();
        columnValues[12] = getPayerId();
        columnValues[13] = getXpaidmethodId();
        columnValues[14] = getChequenumber();
        columnValues[15] = getAccnum();
        columnValues[16] = getXsiteId();
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
            setXconsumeId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXconsumeId(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setRequesterId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setRequesterId(null);
        }
        setInvoicedate(toDate(flds[4]));
        setInvoicenumber(flds[5]);
        try {
            setAuthorizerId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setAuthorizerId(null);
        }
        try {
            setCollectorId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setCollectorId(null);
        }
        setDescription(flds[8]);
        setPartnumber(flds[9]);
        try {
            setAmountLiters(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setAmountLiters(null);
        }
        try {
            setAmountRands(Double.parseDouble(flds[11]));
        } catch(NumberFormatException ne) {
            setAmountRands(null);
        }
        try {
            setPayerId(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setPayerId(null);
        }
        try {
            setXpaidmethodId(Integer.parseInt(flds[13]));
        } catch(NumberFormatException ne) {
            setXpaidmethodId(null);
        }
        setChequenumber(flds[14]);
        setAccnum(flds[15]);
        try {
            setXsiteId(Integer.parseInt(flds[16]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
    }
}
