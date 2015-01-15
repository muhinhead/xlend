// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jan 15 18:35:54 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xorderitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xorderitemId = null;
    private Integer xorderId = null;
    private String itemnumber = null;
    private Date deliveryreq = null;
    private Date delivery = null;
    private Integer quantity = null;
    private String measureitem = null;
    private Double priceperone = null;
    private Integer xmachtypeId = null;
    private Double totalvalue = null;
    private String description = null;

    public Xorderitem(Connection connection) {
        super(connection, "xorderitem", "xorderitem_id");
        setColumnNames(new String[]{"xorderitem_id", "xorder_id", "itemnumber", "deliveryreq", "delivery", "quantity", "measureitem", "priceperone", "xmachtype_id", "totalvalue", "description"});
    }

    public Xorderitem(Connection connection, Integer xorderitemId, Integer xorderId, String itemnumber, Date deliveryreq, Date delivery, Integer quantity, String measureitem, Double priceperone, Integer xmachtypeId, Double totalvalue, String description) {
        super(connection, "xorderitem", "xorderitem_id");
        setNew(xorderitemId.intValue() <= 0);
//        if (xorderitemId.intValue() != 0) {
            this.xorderitemId = xorderitemId;
//        }
        this.xorderId = xorderId;
        this.itemnumber = itemnumber;
        this.deliveryreq = deliveryreq;
        this.delivery = delivery;
        this.quantity = quantity;
        this.measureitem = measureitem;
        this.priceperone = priceperone;
        this.xmachtypeId = xmachtypeId;
        this.totalvalue = totalvalue;
        this.description = description;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xorderitem xorderitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xorderitem_id,xorder_id,itemnumber,deliveryreq,delivery,quantity,measureitem,priceperone,xmachtype_id,totalvalue,description FROM xorderitem WHERE xorderitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xorderitem = new Xorderitem(getConnection());
                xorderitem.setXorderitemId(new Integer(rs.getInt(1)));
                xorderitem.setXorderId(new Integer(rs.getInt(2)));
                xorderitem.setItemnumber(rs.getString(3));
                xorderitem.setDeliveryreq(rs.getDate(4));
                xorderitem.setDelivery(rs.getDate(5));
                xorderitem.setQuantity(new Integer(rs.getInt(6)));
                xorderitem.setMeasureitem(rs.getString(7));
                xorderitem.setPriceperone(rs.getDouble(8));
                xorderitem.setXmachtypeId(new Integer(rs.getInt(9)));
                xorderitem.setTotalvalue(rs.getDouble(10));
                xorderitem.setDescription(rs.getString(11));
                xorderitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xorderitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xorderitem ("+(getXorderitemId().intValue()!=0?"xorderitem_id,":"")+"xorder_id,itemnumber,deliveryreq,delivery,quantity,measureitem,priceperone,xmachtype_id,totalvalue,description) values("+(getXorderitemId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXorderitemId().intValue()!=0) {
                 ps.setObject(++n, getXorderitemId());
             }
             ps.setObject(++n, getXorderId());
             ps.setObject(++n, getItemnumber());
             ps.setObject(++n, getDeliveryreq());
             ps.setObject(++n, getDelivery());
             ps.setObject(++n, getQuantity());
             ps.setObject(++n, getMeasureitem());
             ps.setObject(++n, getPriceperone());
             ps.setObject(++n, getXmachtypeId());
             ps.setObject(++n, getTotalvalue());
             ps.setObject(++n, getDescription());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXorderitemId().intValue()==0) {
             stmt = "SELECT max(xorderitem_id) FROM xorderitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXorderitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE xorderitem " +
                    "SET xorder_id = ?, itemnumber = ?, deliveryreq = ?, delivery = ?, quantity = ?, measureitem = ?, priceperone = ?, xmachtype_id = ?, totalvalue = ?, description = ?" + 
                    " WHERE xorderitem_id = " + getXorderitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXorderId());
                ps.setObject(2, getItemnumber());
                ps.setObject(3, getDeliveryreq());
                ps.setObject(4, getDelivery());
                ps.setObject(5, getQuantity());
                ps.setObject(6, getMeasureitem());
                ps.setObject(7, getPriceperone());
                ps.setObject(8, getXmachtypeId());
                ps.setObject(9, getTotalvalue());
                ps.setObject(10, getDescription());
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
                "DELETE FROM xorderitem " +
                "WHERE xorderitem_id = " + getXorderitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXorderitemId(new Integer(-getXorderitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXorderitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xorderitem_id,xorder_id,itemnumber,deliveryreq,delivery,quantity,measureitem,priceperone,xmachtype_id,totalvalue,description FROM xorderitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xorderitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3),rs.getDate(4),rs.getDate(5),new Integer(rs.getInt(6)),rs.getString(7),rs.getDouble(8),new Integer(rs.getInt(9)),rs.getDouble(10),rs.getString(11)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xorderitem[] objects = new Xorderitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xorderitem xorderitem = (Xorderitem) lst.get(i);
            objects[i] = xorderitem;
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
        String stmt = "SELECT xorderitem_id FROM xorderitem " +
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
    //    return getXorderitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xorderitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXorderitemId(id);
        setNew(prevIsNew);
    }

    public Integer getXorderitemId() {
        return xorderitemId;
    }

    public void setXorderitemId(Integer xorderitemId) throws ForeignKeyViolationException {
        setWasChanged(this.xorderitemId != null && this.xorderitemId != xorderitemId);
        this.xorderitemId = xorderitemId;
        setNew(xorderitemId.intValue() == 0);
    }

    public Integer getXorderId() {
        return xorderId;
    }

    public void setXorderId(Integer xorderId) throws SQLException, ForeignKeyViolationException {
        if (xorderId!=null && !Xorder.exists(getConnection(),"xorder_id = " + xorderId)) {
            throw new ForeignKeyViolationException("Can't set xorder_id, foreign key violation: xoreritem_xorder_fk");
        }
        setWasChanged(this.xorderId != null && !this.xorderId.equals(xorderId));
        this.xorderId = xorderId;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public void setItemnumber(String itemnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.itemnumber != null && !this.itemnumber.equals(itemnumber));
        this.itemnumber = itemnumber;
    }

    public Date getDeliveryreq() {
        return deliveryreq;
    }

    public void setDeliveryreq(Date deliveryreq) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.deliveryreq != null && !this.deliveryreq.equals(deliveryreq));
        this.deliveryreq = deliveryreq;
    }

    public Date getDelivery() {
        return delivery;
    }

    public void setDelivery(Date delivery) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.delivery != null && !this.delivery.equals(delivery));
        this.delivery = delivery;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.quantity != null && !this.quantity.equals(quantity));
        this.quantity = quantity;
    }

    public String getMeasureitem() {
        return measureitem;
    }

    public void setMeasureitem(String measureitem) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.measureitem != null && !this.measureitem.equals(measureitem));
        this.measureitem = measureitem;
    }

    public Double getPriceperone() {
        return priceperone;
    }

    public void setPriceperone(Double priceperone) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.priceperone != null && !this.priceperone.equals(priceperone));
        this.priceperone = priceperone;
    }

    public Integer getXmachtypeId() {
        return xmachtypeId;
    }

    public void setXmachtypeId(Integer xmachtypeId) throws SQLException, ForeignKeyViolationException {
        if (null != xmachtypeId)
            xmachtypeId = xmachtypeId == 0 ? null : xmachtypeId;
        if (xmachtypeId!=null && !Xmachtype.exists(getConnection(),"xmachtype_id = " + xmachtypeId)) {
            throw new ForeignKeyViolationException("Can't set xmachtype_id, foreign key violation: xorderitem_xmachtype_fk");
        }
        setWasChanged(this.xmachtypeId != null && !this.xmachtypeId.equals(xmachtypeId));
        this.xmachtypeId = xmachtypeId;
    }

    public Double getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(Double totalvalue) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.totalvalue != null && !this.totalvalue.equals(totalvalue));
        this.totalvalue = totalvalue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[11];
        columnValues[0] = getXorderitemId();
        columnValues[1] = getXorderId();
        columnValues[2] = getItemnumber();
        columnValues[3] = getDeliveryreq();
        columnValues[4] = getDelivery();
        columnValues[5] = getQuantity();
        columnValues[6] = getMeasureitem();
        columnValues[7] = getPriceperone();
        columnValues[8] = getXmachtypeId();
        columnValues[9] = getTotalvalue();
        columnValues[10] = getDescription();
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
            setXorderitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXorderitemId(null);
        }
        try {
            setXorderId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXorderId(null);
        }
        setItemnumber(flds[2]);
        setDeliveryreq(toDate(flds[3]));
        setDelivery(toDate(flds[4]));
        try {
            setQuantity(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setQuantity(null);
        }
        setMeasureitem(flds[6]);
        try {
            setPriceperone(Double.parseDouble(flds[7]));
        } catch(NumberFormatException ne) {
            setPriceperone(null);
        }
        try {
            setXmachtypeId(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setXmachtypeId(null);
        }
        try {
            setTotalvalue(Double.parseDouble(flds[9]));
        } catch(NumberFormatException ne) {
            setTotalvalue(null);
        }
        setDescription(flds[10]);
    }
}
