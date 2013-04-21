// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Apr 21 12:01:28 EEST 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xfuel extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xfuelId = null;
    private Date fdate = null;
    private Double ammount = null;
    private Integer liters = null;
    private Integer xsiteId = null;
    private Integer issuedbyId = null;
    private Integer issuedtoId = null;
    private Integer xsupplierId = null;
    private Integer iscache = null;

    public Xfuel(Connection connection) {
        super(connection, "xfuel", "xfuel_id");
        setColumnNames(new String[]{"xfuel_id", "fdate", "ammount", "liters", "xsite_id", "issuedby_id", "issuedto_id", "xsupplier_id", "iscache"});
    }

    public Xfuel(Connection connection, Integer xfuelId, Date fdate, Double ammount, Integer liters, Integer xsiteId, Integer issuedbyId, Integer issuedtoId, Integer xsupplierId, Integer iscache) {
        super(connection, "xfuel", "xfuel_id");
        setNew(xfuelId.intValue() <= 0);
//        if (xfuelId.intValue() != 0) {
            this.xfuelId = xfuelId;
//        }
        this.fdate = fdate;
        this.ammount = ammount;
        this.liters = liters;
        this.xsiteId = xsiteId;
        this.issuedbyId = issuedbyId;
        this.issuedtoId = issuedtoId;
        this.xsupplierId = xsupplierId;
        this.iscache = iscache;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xfuel xfuel = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xfuel_id,fdate,ammount,liters,xsite_id,issuedby_id,issuedto_id,xsupplier_id,iscache FROM xfuel WHERE xfuel_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xfuel = new Xfuel(getConnection());
                xfuel.setXfuelId(new Integer(rs.getInt(1)));
                xfuel.setFdate(rs.getDate(2));
                xfuel.setAmmount(rs.getDouble(3));
                xfuel.setLiters(new Integer(rs.getInt(4)));
                xfuel.setXsiteId(new Integer(rs.getInt(5)));
                xfuel.setIssuedbyId(new Integer(rs.getInt(6)));
                xfuel.setIssuedtoId(new Integer(rs.getInt(7)));
                xfuel.setXsupplierId(new Integer(rs.getInt(8)));
                xfuel.setIscache(new Integer(rs.getInt(9)));
                xfuel.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xfuel;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xfuel ("+(getXfuelId().intValue()!=0?"xfuel_id,":"")+"fdate,ammount,liters,xsite_id,issuedby_id,issuedto_id,xsupplier_id,iscache) values("+(getXfuelId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXfuelId().intValue()!=0) {
                 ps.setObject(++n, getXfuelId());
             }
             ps.setObject(++n, getFdate());
             ps.setObject(++n, getAmmount());
             ps.setObject(++n, getLiters());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getIssuedbyId());
             ps.setObject(++n, getIssuedtoId());
             ps.setObject(++n, getXsupplierId());
             ps.setObject(++n, getIscache());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXfuelId().intValue()==0) {
             stmt = "SELECT max(xfuel_id) FROM xfuel";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXfuelId(new Integer(rs.getInt(1)));
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
                    "UPDATE xfuel " +
                    "SET fdate = ?, ammount = ?, liters = ?, xsite_id = ?, issuedby_id = ?, issuedto_id = ?, xsupplier_id = ?, iscache = ?" + 
                    " WHERE xfuel_id = " + getXfuelId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getFdate());
                ps.setObject(2, getAmmount());
                ps.setObject(3, getLiters());
                ps.setObject(4, getXsiteId());
                ps.setObject(5, getIssuedbyId());
                ps.setObject(6, getIssuedtoId());
                ps.setObject(7, getXsupplierId());
                ps.setObject(8, getIscache());
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
                "DELETE FROM xfuel " +
                "WHERE xfuel_id = " + getXfuelId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXfuelId(new Integer(-getXfuelId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXfuelId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xfuel_id,fdate,ammount,liters,xsite_id,issuedby_id,issuedto_id,xsupplier_id,iscache FROM xfuel " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xfuel(con,new Integer(rs.getInt(1)),rs.getDate(2),rs.getDouble(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),new Integer(rs.getInt(9))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xfuel[] objects = new Xfuel[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xfuel xfuel = (Xfuel) lst.get(i);
            objects[i] = xfuel;
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
        String stmt = "SELECT xfuel_id FROM xfuel " +
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
    //    return getXfuelId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xfuelId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXfuelId(id);
        setNew(prevIsNew);
    }

    public Integer getXfuelId() {
        return xfuelId;
    }

    public void setXfuelId(Integer xfuelId) throws ForeignKeyViolationException {
        setWasChanged(this.xfuelId != null && this.xfuelId != xfuelId);
        this.xfuelId = xfuelId;
        setNew(xfuelId.intValue() == 0);
    }

    public Date getFdate() {
        return fdate;
    }

    public void setFdate(Date fdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.fdate != null && !this.fdate.equals(fdate));
        this.fdate = fdate;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.ammount != null && !this.ammount.equals(ammount));
        this.ammount = ammount;
    }

    public Integer getLiters() {
        return liters;
    }

    public void setLiters(Integer liters) throws SQLException, ForeignKeyViolationException {
        if (null != liters)
            liters = liters == 0 ? null : liters;
        setWasChanged(this.liters != null && !this.liters.equals(liters));
        this.liters = liters;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (null != xsiteId)
            xsiteId = xsiteId == 0 ? null : xsiteId;
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xfuel_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getIssuedbyId() {
        return issuedbyId;
    }

    public void setIssuedbyId(Integer issuedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != issuedbyId)
            issuedbyId = issuedbyId == 0 ? null : issuedbyId;
        if (issuedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedbyId)) {
            throw new ForeignKeyViolationException("Can't set issuedby_id, foreign key violation: xfuel_xemployee_fk");
        }
        setWasChanged(this.issuedbyId != null && !this.issuedbyId.equals(issuedbyId));
        this.issuedbyId = issuedbyId;
    }

    public Integer getIssuedtoId() {
        return issuedtoId;
    }

    public void setIssuedtoId(Integer issuedtoId) throws SQLException, ForeignKeyViolationException {
        if (null != issuedtoId)
            issuedtoId = issuedtoId == 0 ? null : issuedtoId;
        if (issuedtoId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedtoId)) {
            throw new ForeignKeyViolationException("Can't set issuedto_id, foreign key violation: xfuel_xemployee_fk2");
        }
        setWasChanged(this.issuedtoId != null && !this.issuedtoId.equals(issuedtoId));
        this.issuedtoId = issuedtoId;
    }

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws SQLException, ForeignKeyViolationException {
        if (null != xsupplierId)
            xsupplierId = xsupplierId == 0 ? null : xsupplierId;
        if (xsupplierId!=null && !Xsupplier.exists(getConnection(),"xsupplier_id = " + xsupplierId)) {
            throw new ForeignKeyViolationException("Can't set xsupplier_id, foreign key violation: xfuel_xsupplier_fk");
        }
        setWasChanged(this.xsupplierId != null && !this.xsupplierId.equals(xsupplierId));
        this.xsupplierId = xsupplierId;
    }

    public Integer getIscache() {
        return iscache;
    }

    public void setIscache(Integer iscache) throws SQLException, ForeignKeyViolationException {
        if (null != iscache)
            iscache = iscache == 0 ? null : iscache;
        setWasChanged(this.iscache != null && !this.iscache.equals(iscache));
        this.iscache = iscache;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[9];
        columnValues[0] = getXfuelId();
        columnValues[1] = getFdate();
        columnValues[2] = getAmmount();
        columnValues[3] = getLiters();
        columnValues[4] = getXsiteId();
        columnValues[5] = getIssuedbyId();
        columnValues[6] = getIssuedtoId();
        columnValues[7] = getXsupplierId();
        columnValues[8] = getIscache();
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
            setXfuelId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXfuelId(null);
        }
        setFdate(toDate(flds[1]));
        try {
            setAmmount(Double.parseDouble(flds[2]));
        } catch(NumberFormatException ne) {
            setAmmount(null);
        }
        try {
            setLiters(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setLiters(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setIssuedbyId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setIssuedbyId(null);
        }
        try {
            setIssuedtoId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setIssuedtoId(null);
        }
        try {
            setXsupplierId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        try {
            setIscache(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setIscache(null);
        }
    }
}
