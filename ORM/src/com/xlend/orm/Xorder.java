// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Jan 29 15:15:44 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xorder extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xorderId = null;
    private Integer xclientId = null;
    private Integer xcontractId = null;
    private Integer xquotationId = null;
    private String ordernumber = null;
    private Date orderdate = null;
    private String contactname = null;
    private String contactphone = null;
    private String contactfax = null;
    private String deliveryaddress = null;
    private String invoiceaddress = null;

    public Xorder(Connection connection) {
        super(connection, "xorder", "xorder_id");
        setColumnNames(new String[]{"xorder_id", "xclient_id", "xcontract_id", "xquotation_id", "ordernumber", "orderdate", "contactname", "contactphone", "contactfax", "deliveryaddress", "invoiceaddress"});
    }

    public Xorder(Connection connection, Integer xorderId, Integer xclientId, Integer xcontractId, Integer xquotationId, String ordernumber, Date orderdate, String contactname, String contactphone, String contactfax, String deliveryaddress, String invoiceaddress) {
        super(connection, "xorder", "xorder_id");
        setNew(xorderId.intValue() <= 0);
//        if (xorderId.intValue() != 0) {
            this.xorderId = xorderId;
//        }
        this.xclientId = xclientId;
        this.xcontractId = xcontractId;
        this.xquotationId = xquotationId;
        this.ordernumber = ordernumber;
        this.orderdate = orderdate;
        this.contactname = contactname;
        this.contactphone = contactphone;
        this.contactfax = contactfax;
        this.deliveryaddress = deliveryaddress;
        this.invoiceaddress = invoiceaddress;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xorder xorder = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xorder_id,xclient_id,xcontract_id,xquotation_id,ordernumber,orderdate,contactname,contactphone,contactfax,deliveryaddress,invoiceaddress FROM xorder WHERE xorder_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xorder = new Xorder(getConnection());
                xorder.setXorderId(new Integer(rs.getInt(1)));
                xorder.setXclientId(new Integer(rs.getInt(2)));
                xorder.setXcontractId(new Integer(rs.getInt(3)));
                xorder.setXquotationId(new Integer(rs.getInt(4)));
                xorder.setOrdernumber(rs.getString(5));
                xorder.setOrderdate(rs.getDate(6));
                xorder.setContactname(rs.getString(7));
                xorder.setContactphone(rs.getString(8));
                xorder.setContactfax(rs.getString(9));
                xorder.setDeliveryaddress(rs.getString(10));
                xorder.setInvoiceaddress(rs.getString(11));
                xorder.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xorder;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xorder ("+(getXorderId().intValue()!=0?"xorder_id,":"")+"xclient_id,xcontract_id,xquotation_id,ordernumber,orderdate,contactname,contactphone,contactfax,deliveryaddress,invoiceaddress) values("+(getXorderId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXorderId().intValue()!=0) {
                 ps.setObject(++n, getXorderId());
             }
             ps.setObject(++n, getXclientId());
             ps.setObject(++n, getXcontractId());
             ps.setObject(++n, getXquotationId());
             ps.setObject(++n, getOrdernumber());
             ps.setObject(++n, getOrderdate());
             ps.setObject(++n, getContactname());
             ps.setObject(++n, getContactphone());
             ps.setObject(++n, getContactfax());
             ps.setObject(++n, getDeliveryaddress());
             ps.setObject(++n, getInvoiceaddress());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXorderId().intValue()==0) {
             stmt = "SELECT max(xorder_id) FROM xorder";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXorderId(new Integer(rs.getInt(1)));
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
                    "UPDATE xorder " +
                    "SET xclient_id = ?, xcontract_id = ?, xquotation_id = ?, ordernumber = ?, orderdate = ?, contactname = ?, contactphone = ?, contactfax = ?, deliveryaddress = ?, invoiceaddress = ?" + 
                    " WHERE xorder_id = " + getXorderId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXclientId());
                ps.setObject(2, getXcontractId());
                ps.setObject(3, getXquotationId());
                ps.setObject(4, getOrdernumber());
                ps.setObject(5, getOrderdate());
                ps.setObject(6, getContactname());
                ps.setObject(7, getContactphone());
                ps.setObject(8, getContactfax());
                ps.setObject(9, getDeliveryaddress());
                ps.setObject(10, getInvoiceaddress());
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
        if (Xtimesheet.exists(getConnection(),"xorder_id = " + getXorderId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtimesheet_xorder_fk");
        }
        if (Xsite.exists(getConnection(),"xorder2_id = " + getXorderId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xsite_xorder_fk2");
        }
        if (Xsite.exists(getConnection(),"xorder3_id = " + getXorderId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xsite_xorder_fk3");
        }
        if (Xsite.exists(getConnection(),"xorder_id = " + getXorderId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xsite_xorder_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xorderitem
            Xorderitem[] records = (Xorderitem[])Xorderitem.load(getConnection(),"xorder_id = " + getXorderId(),null);
            for (int i = 0; i<records.length; i++) {
                Xorderitem xorderitem = records[i];
                xorderitem.delete();
            }
        }
        {// delete cascade from xorderpage
            Xorderpage[] records = (Xorderpage[])Xorderpage.load(getConnection(),"xorder_id = " + getXorderId(),null);
            for (int i = 0; i<records.length; i++) {
                Xorderpage xorderpage = records[i];
                xorderpage.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xorder " +
                "WHERE xorder_id = " + getXorderId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXorderId(new Integer(-getXorderId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXorderId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xorder_id,xclient_id,xcontract_id,xquotation_id,ordernumber,orderdate,contactname,contactphone,contactfax,deliveryaddress,invoiceaddress FROM xorder " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xorder(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getString(5),rs.getDate(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xorder[] objects = new Xorder[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xorder xorder = (Xorder) lst.get(i);
            objects[i] = xorder;
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
        String stmt = "SELECT xorder_id FROM xorder " +
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
    //    return getXorderId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xorderId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXorderId(id);
        setNew(prevIsNew);
    }

    public Integer getXorderId() {
        return xorderId;
    }

    public void setXorderId(Integer xorderId) throws ForeignKeyViolationException {
        setWasChanged(this.xorderId != null && this.xorderId != xorderId);
        this.xorderId = xorderId;
        setNew(xorderId.intValue() == 0);
    }

    public Integer getXclientId() {
        return xclientId;
    }

    public void setXclientId(Integer xclientId) throws SQLException, ForeignKeyViolationException {
        if (xclientId!=null && !Xclient.exists(getConnection(),"xclient_id = " + xclientId)) {
            throw new ForeignKeyViolationException("Can't set xclient_id, foreign key violation: xorder_xclient_fk");
        }
        setWasChanged(this.xclientId != null && !this.xclientId.equals(xclientId));
        this.xclientId = xclientId;
    }

    public Integer getXcontractId() {
        return xcontractId;
    }

    public void setXcontractId(Integer xcontractId) throws SQLException, ForeignKeyViolationException {
        if (null != xcontractId)
            xcontractId = xcontractId == 0 ? null : xcontractId;
        if (xcontractId!=null && !Xcontract.exists(getConnection(),"xcontract_id = " + xcontractId)) {
            throw new ForeignKeyViolationException("Can't set xcontract_id, foreign key violation: xorder_xcontract");
        }
        setWasChanged(this.xcontractId != null && !this.xcontractId.equals(xcontractId));
        this.xcontractId = xcontractId;
    }

    public Integer getXquotationId() {
        return xquotationId;
    }

    public void setXquotationId(Integer xquotationId) throws SQLException, ForeignKeyViolationException {
        if (null != xquotationId)
            xquotationId = xquotationId == 0 ? null : xquotationId;
        if (xquotationId!=null && !Xquotation.exists(getConnection(),"xquotation_id = " + xquotationId)) {
            throw new ForeignKeyViolationException("Can't set xquotation_id, foreign key violation: xorder_xquotation_fk");
        }
        setWasChanged(this.xquotationId != null && !this.xquotationId.equals(xquotationId));
        this.xquotationId = xquotationId;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.ordernumber != null && !this.ordernumber.equals(ordernumber));
        this.ordernumber = ordernumber;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.orderdate != null && !this.orderdate.equals(orderdate));
        this.orderdate = orderdate;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contactname != null && !this.contactname.equals(contactname));
        this.contactname = contactname;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contactphone != null && !this.contactphone.equals(contactphone));
        this.contactphone = contactphone;
    }

    public String getContactfax() {
        return contactfax;
    }

    public void setContactfax(String contactfax) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contactfax != null && !this.contactfax.equals(contactfax));
        this.contactfax = contactfax;
    }

    public String getDeliveryaddress() {
        return deliveryaddress;
    }

    public void setDeliveryaddress(String deliveryaddress) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.deliveryaddress != null && !this.deliveryaddress.equals(deliveryaddress));
        this.deliveryaddress = deliveryaddress;
    }

    public String getInvoiceaddress() {
        return invoiceaddress;
    }

    public void setInvoiceaddress(String invoiceaddress) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invoiceaddress != null && !this.invoiceaddress.equals(invoiceaddress));
        this.invoiceaddress = invoiceaddress;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[11];
        columnValues[0] = getXorderId();
        columnValues[1] = getXclientId();
        columnValues[2] = getXcontractId();
        columnValues[3] = getXquotationId();
        columnValues[4] = getOrdernumber();
        columnValues[5] = getOrderdate();
        columnValues[6] = getContactname();
        columnValues[7] = getContactphone();
        columnValues[8] = getContactfax();
        columnValues[9] = getDeliveryaddress();
        columnValues[10] = getInvoiceaddress();
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
            setXorderId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXorderId(null);
        }
        try {
            setXclientId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXclientId(null);
        }
        try {
            setXcontractId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXcontractId(null);
        }
        try {
            setXquotationId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXquotationId(null);
        }
        setOrdernumber(flds[4]);
        setOrderdate(toDate(flds[5]));
        setContactname(flds[6]);
        setContactphone(flds[7]);
        setContactfax(flds[8]);
        setDeliveryaddress(flds[9]);
        setInvoiceaddress(flds[10]);
    }
}
