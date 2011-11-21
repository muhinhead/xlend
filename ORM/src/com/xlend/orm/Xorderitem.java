// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Nov 21 10:25:16 EET 2011
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
    private String materialnumber = null;
    private String description = null;
    private Integer quantity = null;
    private String measureitem = null;
    private Double priceperone = null;

    public Xorderitem(Connection connection) {
        super(connection, "xorderitem", "xorderitem_id");
        setColumnNames(new String[]{"xorderitem_id", "xorder_id", "itemnumber", "materialnumber", "description", "quantity", "measureitem", "priceperone"});
    }

    public Xorderitem(Connection connection, Integer xorderitemId, Integer xorderId, String itemnumber, String materialnumber, String description, Integer quantity, String measureitem, Double priceperone) {
        super(connection, "xorderitem", "xorderitem_id");
        setNew(xorderitemId.intValue() <= 0);
//        if (xorderitemId.intValue() != 0) {
            this.xorderitemId = xorderitemId;
//        }
        this.xorderId = xorderId;
        this.itemnumber = itemnumber;
        this.materialnumber = materialnumber;
        this.description = description;
        this.quantity = quantity;
        this.measureitem = measureitem;
        this.priceperone = priceperone;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xorderitem xorderitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xorderitem_id,xorder_id,itemnumber,materialnumber,description,quantity,measureitem,priceperone FROM xorderitem WHERE xorderitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xorderitem = new Xorderitem(getConnection());
                xorderitem.setXorderitemId(new Integer(rs.getInt(1)));
                xorderitem.setXorderId(new Integer(rs.getInt(2)));
                xorderitem.setItemnumber(rs.getString(3));
                xorderitem.setMaterialnumber(rs.getString(4));
                xorderitem.setDescription(rs.getString(5));
                xorderitem.setQuantity(new Integer(rs.getInt(6)));
                xorderitem.setMeasureitem(rs.getString(7));
                xorderitem.setPriceperone(rs.getDouble(8));
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
                "INSERT INTO xorderitem ("+(getXorderitemId().intValue()!=0?"xorderitem_id,":"")+"xorder_id,itemnumber,materialnumber,description,quantity,measureitem,priceperone) values("+(getXorderitemId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXorderitemId().intValue()!=0) {
                 ps.setObject(++n, getXorderitemId());
             }
             ps.setObject(++n, getXorderId());
             ps.setObject(++n, getItemnumber());
             ps.setObject(++n, getMaterialnumber());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getQuantity());
             ps.setObject(++n, getMeasureitem());
             ps.setObject(++n, getPriceperone());
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
                    "SET xorder_id = ?, itemnumber = ?, materialnumber = ?, description = ?, quantity = ?, measureitem = ?, priceperone = ?" + 
                    " WHERE xorderitem_id = " + getXorderitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXorderId());
                ps.setObject(2, getItemnumber());
                ps.setObject(3, getMaterialnumber());
                ps.setObject(4, getDescription());
                ps.setObject(5, getQuantity());
                ps.setObject(6, getMeasureitem());
                ps.setObject(7, getPriceperone());
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
        String stmt = "SELECT xorderitem_id,xorder_id,itemnumber,materialnumber,description,quantity,measureitem,priceperone FROM xorderitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xorderitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3),rs.getString(4),rs.getString(5),new Integer(rs.getInt(6)),rs.getString(7),rs.getDouble(8)));
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

    public String getMaterialnumber() {
        return materialnumber;
    }

    public void setMaterialnumber(String materialnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.materialnumber != null && !this.materialnumber.equals(materialnumber));
        this.materialnumber = materialnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
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
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getXorderitemId();
        columnValues[1] = getXorderId();
        columnValues[2] = getItemnumber();
        columnValues[3] = getMaterialnumber();
        columnValues[4] = getDescription();
        columnValues[5] = getQuantity();
        columnValues[6] = getMeasureitem();
        columnValues[7] = getPriceperone();
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
        setMaterialnumber(flds[3]);
        setDescription(flds[4]);
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
    }
}
