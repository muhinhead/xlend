// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Sep 13 20:03:38 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xparts extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpartsId = null;
    private String partnumber = null;
    private String description = null;
    private String storename = null;
    private String machinemodel = null;
    private Double quantity = null;
    private Integer lastsupplierId = null;
    private Integer prevsupplierId = null;
    private Double priceperunit = null;
    private Date purchased = null;
    private Integer xpartcategoryId = null;

    public Xparts(Connection connection) {
        super(connection, "xparts", "xparts_id");
        setColumnNames(new String[]{"xparts_id", "partnumber", "description", "storename", "machinemodel", "quantity", "lastsupplier_id", "prevsupplier_id", "priceperunit", "purchased", "xpartcategory_id"});
    }

    public Xparts(Connection connection, Integer xpartsId, String partnumber, String description, String storename, String machinemodel, Double quantity, Integer lastsupplierId, Integer prevsupplierId, Double priceperunit, Date purchased, Integer xpartcategoryId) {
        super(connection, "xparts", "xparts_id");
        setNew(xpartsId.intValue() <= 0);
//        if (xpartsId.intValue() != 0) {
            this.xpartsId = xpartsId;
//        }
        this.partnumber = partnumber;
        this.description = description;
        this.storename = storename;
        this.machinemodel = machinemodel;
        this.quantity = quantity;
        this.lastsupplierId = lastsupplierId;
        this.prevsupplierId = prevsupplierId;
        this.priceperunit = priceperunit;
        this.purchased = purchased;
        this.xpartcategoryId = xpartcategoryId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xparts xparts = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xparts_id,partnumber,description,storename,machinemodel,quantity,lastsupplier_id,prevsupplier_id,priceperunit,purchased,xpartcategory_id FROM xparts WHERE xparts_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xparts = new Xparts(getConnection());
                xparts.setXpartsId(new Integer(rs.getInt(1)));
                xparts.setPartnumber(rs.getString(2));
                xparts.setDescription(rs.getString(3));
                xparts.setStorename(rs.getString(4));
                xparts.setMachinemodel(rs.getString(5));
                xparts.setQuantity(rs.getDouble(6));
                xparts.setLastsupplierId(new Integer(rs.getInt(7)));
                xparts.setPrevsupplierId(new Integer(rs.getInt(8)));
                xparts.setPriceperunit(rs.getDouble(9));
                xparts.setPurchased(rs.getDate(10));
                xparts.setXpartcategoryId(new Integer(rs.getInt(11)));
                xparts.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xparts;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xparts ("+(getXpartsId().intValue()!=0?"xparts_id,":"")+"partnumber,description,storename,machinemodel,quantity,lastsupplier_id,prevsupplier_id,priceperunit,purchased,xpartcategory_id) values("+(getXpartsId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpartsId().intValue()!=0) {
                 ps.setObject(++n, getXpartsId());
             }
             ps.setObject(++n, getPartnumber());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getStorename());
             ps.setObject(++n, getMachinemodel());
             ps.setObject(++n, getQuantity());
             ps.setObject(++n, getLastsupplierId());
             ps.setObject(++n, getPrevsupplierId());
             ps.setObject(++n, getPriceperunit());
             ps.setObject(++n, getPurchased());
             ps.setObject(++n, getXpartcategoryId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpartsId().intValue()==0) {
             stmt = "SELECT max(xparts_id) FROM xparts";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpartsId(new Integer(rs.getInt(1)));
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
                    "UPDATE xparts " +
                    "SET partnumber = ?, description = ?, storename = ?, machinemodel = ?, quantity = ?, lastsupplier_id = ?, prevsupplier_id = ?, priceperunit = ?, purchased = ?, xpartcategory_id = ?" + 
                    " WHERE xparts_id = " + getXpartsId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getPartnumber());
                ps.setObject(2, getDescription());
                ps.setObject(3, getStorename());
                ps.setObject(4, getMachinemodel());
                ps.setObject(5, getQuantity());
                ps.setObject(6, getLastsupplierId());
                ps.setObject(7, getPrevsupplierId());
                ps.setObject(8, getPriceperunit());
                ps.setObject(9, getPurchased());
                ps.setObject(10, getXpartcategoryId());
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
        {// delete cascade from xaddstocks
            Xaddstocks[] records = (Xaddstocks[])Xaddstocks.load(getConnection(),"xparts_id = " + getXpartsId(),null);
            for (int i = 0; i<records.length; i++) {
                Xaddstocks xaddstocks = records[i];
                xaddstocks.delete();
            }
        }
        {// delete cascade from xbookouts
            Xbookouts[] records = (Xbookouts[])Xbookouts.load(getConnection(),"xparts_id = " + getXpartsId(),null);
            for (int i = 0; i<records.length; i++) {
                Xbookouts xbookouts = records[i];
                xbookouts.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xparts " +
                "WHERE xparts_id = " + getXpartsId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpartsId(new Integer(-getXpartsId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpartsId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xparts_id,partnumber,description,storename,machinemodel,quantity,lastsupplier_id,prevsupplier_id,priceperunit,purchased,xpartcategory_id FROM xparts " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xparts(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),rs.getDouble(9),rs.getDate(10),new Integer(rs.getInt(11))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xparts[] objects = new Xparts[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xparts xparts = (Xparts) lst.get(i);
            objects[i] = xparts;
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
        String stmt = "SELECT xparts_id FROM xparts " +
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
    //    return getXpartsId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xpartsId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXpartsId(id);
        setNew(prevIsNew);
    }

    public Integer getXpartsId() {
        return xpartsId;
    }

    public void setXpartsId(Integer xpartsId) throws ForeignKeyViolationException {
        setWasChanged(this.xpartsId != null && this.xpartsId != xpartsId);
        this.xpartsId = xpartsId;
        setNew(xpartsId.intValue() == 0);
    }

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.partnumber != null && !this.partnumber.equals(partnumber));
        this.partnumber = partnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.storename != null && !this.storename.equals(storename));
        this.storename = storename;
    }

    public String getMachinemodel() {
        return machinemodel;
    }

    public void setMachinemodel(String machinemodel) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.machinemodel != null && !this.machinemodel.equals(machinemodel));
        this.machinemodel = machinemodel;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.quantity != null && !this.quantity.equals(quantity));
        this.quantity = quantity;
    }

    public Integer getLastsupplierId() {
        return lastsupplierId;
    }

    public void setLastsupplierId(Integer lastsupplierId) throws SQLException, ForeignKeyViolationException {
        if (null != lastsupplierId)
            lastsupplierId = lastsupplierId == 0 ? null : lastsupplierId;
        if (lastsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + lastsupplierId)) {
            throw new ForeignKeyViolationException("Can't set lastsupplier_id, foreign key violation: xparts_xsupplier_fk");
        }
        setWasChanged(this.lastsupplierId != null && !this.lastsupplierId.equals(lastsupplierId));
        this.lastsupplierId = lastsupplierId;
    }

    public Integer getPrevsupplierId() {
        return prevsupplierId;
    }

    public void setPrevsupplierId(Integer prevsupplierId) throws SQLException, ForeignKeyViolationException {
        if (null != prevsupplierId)
            prevsupplierId = prevsupplierId == 0 ? null : prevsupplierId;
        if (prevsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + prevsupplierId)) {
            throw new ForeignKeyViolationException("Can't set prevsupplier_id, foreign key violation: xparts_xsupplier_fk2");
        }
        setWasChanged(this.prevsupplierId != null && !this.prevsupplierId.equals(prevsupplierId));
        this.prevsupplierId = prevsupplierId;
    }

    public Double getPriceperunit() {
        return priceperunit;
    }

    public void setPriceperunit(Double priceperunit) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.priceperunit != null && !this.priceperunit.equals(priceperunit));
        this.priceperunit = priceperunit;
    }

    public Date getPurchased() {
        return purchased;
    }

    public void setPurchased(Date purchased) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.purchased != null && !this.purchased.equals(purchased));
        this.purchased = purchased;
    }

    public Integer getXpartcategoryId() {
        return xpartcategoryId;
    }

    public void setXpartcategoryId(Integer xpartcategoryId) throws SQLException, ForeignKeyViolationException {
        if (xpartcategoryId!=null && !Xpartcategory.exists(getConnection(),"xpartcategory_id = " + xpartcategoryId)) {
            throw new ForeignKeyViolationException("Can't set xpartcategory_id, foreign key violation: xparts_xpartcategory_fk");
        }
        setWasChanged(this.xpartcategoryId != null && !this.xpartcategoryId.equals(xpartcategoryId));
        this.xpartcategoryId = xpartcategoryId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[11];
        columnValues[0] = getXpartsId();
        columnValues[1] = getPartnumber();
        columnValues[2] = getDescription();
        columnValues[3] = getStorename();
        columnValues[4] = getMachinemodel();
        columnValues[5] = getQuantity();
        columnValues[6] = getLastsupplierId();
        columnValues[7] = getPrevsupplierId();
        columnValues[8] = getPriceperunit();
        columnValues[9] = getPurchased();
        columnValues[10] = getXpartcategoryId();
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
            setXpartsId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpartsId(null);
        }
        setPartnumber(flds[1]);
        setDescription(flds[2]);
        setStorename(flds[3]);
        setMachinemodel(flds[4]);
        try {
            setQuantity(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setQuantity(null);
        }
        try {
            setLastsupplierId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setLastsupplierId(null);
        }
        try {
            setPrevsupplierId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setPrevsupplierId(null);
        }
        try {
            setPriceperunit(Double.parseDouble(flds[8]));
        } catch(NumberFormatException ne) {
            setPriceperunit(null);
        }
        setPurchased(toDate(flds[9]));
        try {
            setXpartcategoryId(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setXpartcategoryId(null);
        }
    }
}
