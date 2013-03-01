// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Mar 01 08:33:05 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xhourcompare extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xhourcompareId = null;
    private Date monthYear = null;
    private Integer xsiteId = null;
    private Integer operatorId = null;
    private Integer xmachineId = null;

    public Xhourcompare(Connection connection) {
        super(connection, "xhourcompare", "xhourcompare_id");
        setColumnNames(new String[]{"xhourcompare_id", "month_year", "xsite_id", "operator_id", "xmachine_id"});
    }

    public Xhourcompare(Connection connection, Integer xhourcompareId, Date monthYear, Integer xsiteId, Integer operatorId, Integer xmachineId) {
        super(connection, "xhourcompare", "xhourcompare_id");
        setNew(xhourcompareId.intValue() <= 0);
//        if (xhourcompareId.intValue() != 0) {
            this.xhourcompareId = xhourcompareId;
//        }
        this.monthYear = monthYear;
        this.xsiteId = xsiteId;
        this.operatorId = operatorId;
        this.xmachineId = xmachineId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xhourcompare xhourcompare = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xhourcompare_id,month_year,xsite_id,operator_id,xmachine_id FROM xhourcompare WHERE xhourcompare_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xhourcompare = new Xhourcompare(getConnection());
                xhourcompare.setXhourcompareId(new Integer(rs.getInt(1)));
                xhourcompare.setMonthYear(rs.getDate(2));
                xhourcompare.setXsiteId(new Integer(rs.getInt(3)));
                xhourcompare.setOperatorId(new Integer(rs.getInt(4)));
                xhourcompare.setXmachineId(new Integer(rs.getInt(5)));
                xhourcompare.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xhourcompare;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xhourcompare ("+(getXhourcompareId().intValue()!=0?"xhourcompare_id,":"")+"month_year,xsite_id,operator_id,xmachine_id) values("+(getXhourcompareId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXhourcompareId().intValue()!=0) {
                 ps.setObject(++n, getXhourcompareId());
             }
             ps.setObject(++n, getMonthYear());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getOperatorId());
             ps.setObject(++n, getXmachineId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXhourcompareId().intValue()==0) {
             stmt = "SELECT max(xhourcompare_id) FROM xhourcompare";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXhourcompareId(new Integer(rs.getInt(1)));
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
                    "UPDATE xhourcompare " +
                    "SET month_year = ?, xsite_id = ?, operator_id = ?, xmachine_id = ?" + 
                    " WHERE xhourcompare_id = " + getXhourcompareId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getMonthYear());
                ps.setObject(2, getXsiteId());
                ps.setObject(3, getOperatorId());
                ps.setObject(4, getXmachineId());
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
        if (Xhourcompareday.exists(getConnection(),"xhourcompare_id = " + getXhourcompareId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xhourcompareday_xhourcompare_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xhourcompare " +
                "WHERE xhourcompare_id = " + getXhourcompareId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXhourcompareId(new Integer(-getXhourcompareId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXhourcompareId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xhourcompare_id,month_year,xsite_id,operator_id,xmachine_id FROM xhourcompare " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xhourcompare(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xhourcompare[] objects = new Xhourcompare[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xhourcompare xhourcompare = (Xhourcompare) lst.get(i);
            objects[i] = xhourcompare;
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
        String stmt = "SELECT xhourcompare_id FROM xhourcompare " +
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
    //    return getXhourcompareId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xhourcompareId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXhourcompareId(id);
        setNew(prevIsNew);
    }

    public Integer getXhourcompareId() {
        return xhourcompareId;
    }

    public void setXhourcompareId(Integer xhourcompareId) throws ForeignKeyViolationException {
        setWasChanged(this.xhourcompareId != null && this.xhourcompareId != xhourcompareId);
        this.xhourcompareId = xhourcompareId;
        setNew(xhourcompareId.intValue() == 0);
    }

    public Date getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(Date monthYear) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.monthYear != null && !this.monthYear.equals(monthYear));
        this.monthYear = monthYear;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xhourcompare_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) throws SQLException, ForeignKeyViolationException {
        if (operatorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + operatorId)) {
            throw new ForeignKeyViolationException("Can't set operator_id, foreign key violation: xhourcompare_xemployee_fk");
        }
        setWasChanged(this.operatorId != null && !this.operatorId.equals(operatorId));
        this.operatorId = operatorId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xhourcompare_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[5];
        columnValues[0] = getXhourcompareId();
        columnValues[1] = getMonthYear();
        columnValues[2] = getXsiteId();
        columnValues[3] = getOperatorId();
        columnValues[4] = getXmachineId();
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
            setXhourcompareId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXhourcompareId(null);
        }
        setMonthYear(toDate(flds[1]));
        try {
            setXsiteId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setOperatorId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setOperatorId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
    }
}
