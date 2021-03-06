// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xhourcompareday extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xhourcomparedayId = null;
    private Integer xhourcompareId = null;
    private Integer dayNo = null;
    private Double ocs = null;
    private Double hm = null;
    private Double dr = null;
    private Double std = null;
    private Double tsh = null;
    private Double tsn = null;
    private Double invn = null;

    public Xhourcompareday(Connection connection) {
        super(connection, "xhourcompareday", "xhourcompareday_id");
        setColumnNames(new String[]{"xhourcompareday_id", "xhourcompare_id", "day_no", "ocs", "hm", "dr", "std", "tsh", "tsn", "invn"});
    }

    public Xhourcompareday(Connection connection, Integer xhourcomparedayId, Integer xhourcompareId, Integer dayNo, Double ocs, Double hm, Double dr, Double std, Double tsh, Double tsn, Double invn) {
        super(connection, "xhourcompareday", "xhourcompareday_id");
        setNew(xhourcomparedayId.intValue() <= 0);
//        if (xhourcomparedayId.intValue() != 0) {
            this.xhourcomparedayId = xhourcomparedayId;
//        }
        this.xhourcompareId = xhourcompareId;
        this.dayNo = dayNo;
        this.ocs = ocs;
        this.hm = hm;
        this.dr = dr;
        this.std = std;
        this.tsh = tsh;
        this.tsn = tsn;
        this.invn = invn;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xhourcompareday xhourcompareday = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xhourcompareday_id,xhourcompare_id,day_no,ocs,hm,dr,std,tsh,tsn,invn FROM xhourcompareday WHERE xhourcompareday_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xhourcompareday = new Xhourcompareday(getConnection());
                xhourcompareday.setXhourcomparedayId(new Integer(rs.getInt(1)));
                xhourcompareday.setXhourcompareId(new Integer(rs.getInt(2)));
                xhourcompareday.setDayNo(new Integer(rs.getInt(3)));
                xhourcompareday.setOcs(rs.getDouble(4));
                xhourcompareday.setHm(rs.getDouble(5));
                xhourcompareday.setDr(rs.getDouble(6));
                xhourcompareday.setStd(rs.getDouble(7));
                xhourcompareday.setTsh(rs.getDouble(8));
                xhourcompareday.setTsn(rs.getDouble(9));
                xhourcompareday.setInvn(rs.getDouble(10));
                xhourcompareday.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xhourcompareday;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xhourcompareday ("+(getXhourcomparedayId().intValue()!=0?"xhourcompareday_id,":"")+"xhourcompare_id,day_no,ocs,hm,dr,std,tsh,tsn,invn) values("+(getXhourcomparedayId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXhourcomparedayId().intValue()!=0) {
                 ps.setObject(++n, getXhourcomparedayId());
             }
             ps.setObject(++n, getXhourcompareId());
             ps.setObject(++n, getDayNo());
             ps.setObject(++n, getOcs());
             ps.setObject(++n, getHm());
             ps.setObject(++n, getDr());
             ps.setObject(++n, getStd());
             ps.setObject(++n, getTsh());
             ps.setObject(++n, getTsn());
             ps.setObject(++n, getInvn());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXhourcomparedayId().intValue()==0) {
             stmt = "SELECT max(xhourcompareday_id) FROM xhourcompareday";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXhourcomparedayId(new Integer(rs.getInt(1)));
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
                    "UPDATE xhourcompareday " +
                    "SET xhourcompare_id = ?, day_no = ?, ocs = ?, hm = ?, dr = ?, std = ?, tsh = ?, tsn = ?, invn = ?" + 
                    " WHERE xhourcompareday_id = " + getXhourcomparedayId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXhourcompareId());
                ps.setObject(2, getDayNo());
                ps.setObject(3, getOcs());
                ps.setObject(4, getHm());
                ps.setObject(5, getDr());
                ps.setObject(6, getStd());
                ps.setObject(7, getTsh());
                ps.setObject(8, getTsn());
                ps.setObject(9, getInvn());
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
                "DELETE FROM xhourcompareday " +
                "WHERE xhourcompareday_id = " + getXhourcomparedayId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXhourcomparedayId(new Integer(-getXhourcomparedayId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXhourcomparedayId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xhourcompareday_id,xhourcompare_id,day_no,ocs,hm,dr,std,tsh,tsn,invn FROM xhourcompareday " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xhourcompareday(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),rs.getDouble(7),rs.getDouble(8),rs.getDouble(9),rs.getDouble(10)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xhourcompareday[] objects = new Xhourcompareday[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xhourcompareday xhourcompareday = (Xhourcompareday) lst.get(i);
            objects[i] = xhourcompareday;
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
        String stmt = "SELECT xhourcompareday_id FROM xhourcompareday " +
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
    //    return getXhourcomparedayId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xhourcomparedayId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXhourcomparedayId(id);
        setNew(prevIsNew);
    }

    public Integer getXhourcomparedayId() {
        return xhourcomparedayId;
    }

    public void setXhourcomparedayId(Integer xhourcomparedayId) throws ForeignKeyViolationException {
        setWasChanged(this.xhourcomparedayId != null && this.xhourcomparedayId != xhourcomparedayId);
        this.xhourcomparedayId = xhourcomparedayId;
        setNew(xhourcomparedayId.intValue() == 0);
    }

    public Integer getXhourcompareId() {
        return xhourcompareId;
    }

    public void setXhourcompareId(Integer xhourcompareId) throws SQLException, ForeignKeyViolationException {
        if (xhourcompareId!=null && !Xhourcompare.exists(getConnection(),"xhourcompare_id = " + xhourcompareId)) {
            throw new ForeignKeyViolationException("Can't set xhourcompare_id, foreign key violation: xhourcompareday_xhourcompare_fk");
        }
        setWasChanged(this.xhourcompareId != null && !this.xhourcompareId.equals(xhourcompareId));
        this.xhourcompareId = xhourcompareId;
    }

    public Integer getDayNo() {
        return dayNo;
    }

    public void setDayNo(Integer dayNo) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dayNo != null && !this.dayNo.equals(dayNo));
        this.dayNo = dayNo;
    }

    public Double getOcs() {
        return ocs;
    }

    public void setOcs(Double ocs) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.ocs != null && !this.ocs.equals(ocs));
        this.ocs = ocs;
    }

    public Double getHm() {
        return hm;
    }

    public void setHm(Double hm) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.hm != null && !this.hm.equals(hm));
        this.hm = hm;
    }

    public Double getDr() {
        return dr;
    }

    public void setDr(Double dr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dr != null && !this.dr.equals(dr));
        this.dr = dr;
    }

    public Double getStd() {
        return std;
    }

    public void setStd(Double std) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.std != null && !this.std.equals(std));
        this.std = std;
    }

    public Double getTsh() {
        return tsh;
    }

    public void setTsh(Double tsh) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.tsh != null && !this.tsh.equals(tsh));
        this.tsh = tsh;
    }

    public Double getTsn() {
        return tsn;
    }

    public void setTsn(Double tsn) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.tsn != null && !this.tsn.equals(tsn));
        this.tsn = tsn;
    }

    public Double getInvn() {
        return invn;
    }

    public void setInvn(Double invn) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invn != null && !this.invn.equals(invn));
        this.invn = invn;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[10];
        columnValues[0] = getXhourcomparedayId();
        columnValues[1] = getXhourcompareId();
        columnValues[2] = getDayNo();
        columnValues[3] = getOcs();
        columnValues[4] = getHm();
        columnValues[5] = getDr();
        columnValues[6] = getStd();
        columnValues[7] = getTsh();
        columnValues[8] = getTsn();
        columnValues[9] = getInvn();
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
            setXhourcomparedayId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXhourcomparedayId(null);
        }
        try {
            setXhourcompareId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXhourcompareId(null);
        }
        try {
            setDayNo(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setDayNo(null);
        }
        try {
            setOcs(Double.parseDouble(flds[3]));
        } catch(NumberFormatException ne) {
            setOcs(null);
        }
        try {
            setHm(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setHm(null);
        }
        try {
            setDr(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setDr(null);
        }
        try {
            setStd(Double.parseDouble(flds[6]));
        } catch(NumberFormatException ne) {
            setStd(null);
        }
        try {
            setTsh(Double.parseDouble(flds[7]));
        } catch(NumberFormatException ne) {
            setTsh(null);
        }
        try {
            setTsn(Double.parseDouble(flds[8]));
        } catch(NumberFormatException ne) {
            setTsn(null);
        }
        try {
            setInvn(Double.parseDouble(flds[9]));
        } catch(NumberFormatException ne) {
            setInvn(null);
        }
    }
}
