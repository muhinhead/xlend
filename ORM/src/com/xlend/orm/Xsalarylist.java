// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 11:03:09 EET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xsalarylist extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xsalarylistId = null;
    private Date payday = null;

    public Xsalarylist(Connection connection) {
        super(connection, "xsalarylist", "xsalarylist_id");
        setColumnNames(new String[]{"xsalarylist_id", "payday"});
    }

    public Xsalarylist(Connection connection, Integer xsalarylistId, Date payday) {
        super(connection, "xsalarylist", "xsalarylist_id");
        setNew(xsalarylistId.intValue() <= 0);
//        if (xsalarylistId.intValue() != 0) {
            this.xsalarylistId = xsalarylistId;
//        }
        this.payday = payday;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xsalarylist xsalarylist = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsalarylist_id,payday FROM xsalarylist WHERE xsalarylist_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xsalarylist = new Xsalarylist(getConnection());
                xsalarylist.setXsalarylistId(new Integer(rs.getInt(1)));
                xsalarylist.setPayday(rs.getDate(2));
                xsalarylist.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xsalarylist;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xsalarylist ("+(getXsalarylistId().intValue()!=0?"xsalarylist_id,":"")+"payday) values("+(getXsalarylistId().intValue()!=0?"?,":"")+"?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXsalarylistId().intValue()!=0) {
                 ps.setObject(++n, getXsalarylistId());
             }
             ps.setObject(++n, getPayday());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXsalarylistId().intValue()==0) {
             stmt = "SELECT max(xsalarylist_id) FROM xsalarylist";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXsalarylistId(new Integer(rs.getInt(1)));
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
                    "UPDATE xsalarylist " +
                    "SET payday = ?" + 
                    " WHERE xsalarylist_id = " + getXsalarylistId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getPayday());
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
        {// delete cascade from xsalary
            Xsalary[] records = (Xsalary[])Xsalary.load(getConnection(),"xsalarylist_id = " + getXsalarylistId(),null);
            for (int i = 0; i<records.length; i++) {
                Xsalary xsalary = records[i];
                xsalary.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xsalarylist " +
                "WHERE xsalarylist_id = " + getXsalarylistId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXsalarylistId(new Integer(-getXsalarylistId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXsalarylistId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsalarylist_id,payday FROM xsalarylist " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xsalarylist(con,new Integer(rs.getInt(1)),rs.getDate(2)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xsalarylist[] objects = new Xsalarylist[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xsalarylist xsalarylist = (Xsalarylist) lst.get(i);
            objects[i] = xsalarylist;
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
        String stmt = "SELECT xsalarylist_id FROM xsalarylist " +
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
    //    return getXsalarylistId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xsalarylistId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXsalarylistId(id);
        setNew(prevIsNew);
    }

    public Integer getXsalarylistId() {
        return xsalarylistId;
    }

    public void setXsalarylistId(Integer xsalarylistId) throws ForeignKeyViolationException {
        setWasChanged(this.xsalarylistId != null && this.xsalarylistId != xsalarylistId);
        this.xsalarylistId = xsalarylistId;
        setNew(xsalarylistId.intValue() == 0);
    }

    public Date getPayday() {
        return payday;
    }

    public void setPayday(Date payday) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.payday != null && !this.payday.equals(payday));
        this.payday = payday;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[2];
        columnValues[0] = getXsalarylistId();
        columnValues[1] = getPayday();
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
            setXsalarylistId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXsalarylistId(null);
        }
        setPayday(toDate(flds[1]));
    }
}
