// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Mar 10 16:43:25 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xppebuy extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xppebuyId = null;
    private Date buydate = null;
    private Integer boughtbyId = null;
    private Integer xsupplierId = null;
    private Integer authorizedbyId = null;

    public Xppebuy(Connection connection) {
        super(connection, "xppebuy", "xppebuy_id");
        setColumnNames(new String[]{"xppebuy_id", "buydate", "boughtby_id", "xsupplier_id", "authorizedby_id"});
    }

    public Xppebuy(Connection connection, Integer xppebuyId, Date buydate, Integer boughtbyId, Integer xsupplierId, Integer authorizedbyId) {
        super(connection, "xppebuy", "xppebuy_id");
        setNew(xppebuyId.intValue() <= 0);
//        if (xppebuyId.intValue() != 0) {
            this.xppebuyId = xppebuyId;
//        }
        this.buydate = buydate;
        this.boughtbyId = boughtbyId;
        this.xsupplierId = xsupplierId;
        this.authorizedbyId = authorizedbyId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xppebuy xppebuy = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppebuy_id,buydate,boughtby_id,xsupplier_id,authorizedby_id FROM xppebuy WHERE xppebuy_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xppebuy = new Xppebuy(getConnection());
                xppebuy.setXppebuyId(new Integer(rs.getInt(1)));
                xppebuy.setBuydate(rs.getDate(2));
                xppebuy.setBoughtbyId(new Integer(rs.getInt(3)));
                xppebuy.setXsupplierId(new Integer(rs.getInt(4)));
                xppebuy.setAuthorizedbyId(new Integer(rs.getInt(5)));
                xppebuy.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xppebuy;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xppebuy ("+(getXppebuyId().intValue()!=0?"xppebuy_id,":"")+"buydate,boughtby_id,xsupplier_id,authorizedby_id) values("+(getXppebuyId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXppebuyId().intValue()!=0) {
                 ps.setObject(++n, getXppebuyId());
             }
             ps.setObject(++n, getBuydate());
             ps.setObject(++n, getBoughtbyId());
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getAuthorizedbyId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXppebuyId().intValue()==0) {
             stmt = "SELECT max(xppebuy_id) FROM xppebuy";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXppebuyId(new Integer(rs.getInt(1)));
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
                    "UPDATE xppebuy " +
                    "SET buydate = ?, boughtby_id = ?, xsupplier_id = ?, authorizedby_id = ?" + 
                    " WHERE xppebuy_id = " + getXppebuyId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getBuydate());
                ps.setObject(2, getBoughtbyId());
                ps.setObject(3, getXsupplierId());
                ps.setObject(4, getAuthorizedbyId());
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
        if (Xppebuyitem.exists(getConnection(),"xppebuy_id = " + getXppebuyId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xppebuyitem_xppeby_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xppebuy " +
                "WHERE xppebuy_id = " + getXppebuyId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXppebuyId(new Integer(-getXppebuyId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXppebuyId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppebuy_id,buydate,boughtby_id,xsupplier_id,authorizedby_id FROM xppebuy " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xppebuy(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xppebuy[] objects = new Xppebuy[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xppebuy xppebuy = (Xppebuy) lst.get(i);
            objects[i] = xppebuy;
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
        String stmt = "SELECT xppebuy_id FROM xppebuy " +
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
    //    return getXppebuyId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xppebuyId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXppebuyId(id);
        setNew(prevIsNew);
    }

    public Integer getXppebuyId() {
        return xppebuyId;
    }

    public void setXppebuyId(Integer xppebuyId) throws ForeignKeyViolationException {
        setWasChanged(this.xppebuyId != null && this.xppebuyId != xppebuyId);
        this.xppebuyId = xppebuyId;
        setNew(xppebuyId.intValue() == 0);
    }

    public Date getBuydate() {
        return buydate;
    }

    public void setBuydate(Date buydate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.buydate != null && !this.buydate.equals(buydate));
        this.buydate = buydate;
    }

    public Integer getBoughtbyId() {
        return boughtbyId;
    }

    public void setBoughtbyId(Integer boughtbyId) throws SQLException, ForeignKeyViolationException {
        if (boughtbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + boughtbyId)) {
            throw new ForeignKeyViolationException("Can't set boughtby_id, foreign key violation: xppebuy_xemployee_fk");
        }
        setWasChanged(this.boughtbyId != null && !this.boughtbyId.equals(boughtbyId));
        this.boughtbyId = boughtbyId;
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xppebuy_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Integer getAuthorizedbyId() {
        return authorizedbyId;
    }

    public void setAuthorizedbyId(Integer authorizedbyId) throws SQLException, ForeignKeyViolationException {
        if (authorizedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + authorizedbyId)) {
            throw new ForeignKeyViolationException("Can't set authorizedby_id, foreign key violation: xppebuy_xemployee_fk2");
        }
        setWasChanged(this.authorizedbyId != null && !this.authorizedbyId.equals(authorizedbyId));
        this.authorizedbyId = authorizedbyId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[5];
        columnValues[0] = getXppebuyId();
        columnValues[1] = getBuydate();
        columnValues[2] = getBoughtbyId();
        columnValues[3] = getXsupplierId();
        columnValues[4] = getAuthorizedbyId();
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
            setXppebuyId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXppebuyId(null);
        }
        setBuydate(toDate(flds[1]));
        try {
            setBoughtbyId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setBoughtbyId(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        try {
            setAuthorizedbyId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setAuthorizedbyId(null);
        }
    }
}
