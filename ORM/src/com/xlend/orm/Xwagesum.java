// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Sep 09 11:36:44 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xwagesum extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xwagesumId = null;
    private Date weekend = null;

    public Xwagesum(Connection connection) {
        super(connection, "xwagesum", "xwagesum_id");
        setColumnNames(new String[]{"xwagesum_id", "weekend"});
    }

    public Xwagesum(Connection connection, Integer xwagesumId, Date weekend) {
        super(connection, "xwagesum", "xwagesum_id");
        setNew(xwagesumId.intValue() <= 0);
//        if (xwagesumId.intValue() != 0) {
            this.xwagesumId = xwagesumId;
//        }
        this.weekend = weekend;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xwagesum xwagesum = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xwagesum_id,weekend FROM xwagesum WHERE xwagesum_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xwagesum = new Xwagesum(getConnection());
                xwagesum.setXwagesumId(new Integer(rs.getInt(1)));
                xwagesum.setWeekend(rs.getDate(2));
                xwagesum.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xwagesum;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xwagesum ("+(getXwagesumId().intValue()!=0?"xwagesum_id,":"")+"weekend) values("+(getXwagesumId().intValue()!=0?"?,":"")+"?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXwagesumId().intValue()!=0) {
                 ps.setObject(++n, getXwagesumId());
             }
             ps.setObject(++n, getWeekend());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXwagesumId().intValue()==0) {
             stmt = "SELECT max(xwagesum_id) FROM xwagesum";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXwagesumId(new Integer(rs.getInt(1)));
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
                    "UPDATE xwagesum " +
                    "SET weekend = ?" + 
                    " WHERE xwagesum_id = " + getXwagesumId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getWeekend());
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
        {// delete cascade from xwagesumitem
            Xwagesumitem[] records = (Xwagesumitem[])Xwagesumitem.load(getConnection(),"xwagesum_id = " + getXwagesumId(),null);
            for (int i = 0; i<records.length; i++) {
                Xwagesumitem xwagesumitem = records[i];
                xwagesumitem.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xwagesum " +
                "WHERE xwagesum_id = " + getXwagesumId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXwagesumId(new Integer(-getXwagesumId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXwagesumId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xwagesum_id,weekend FROM xwagesum " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xwagesum(con,new Integer(rs.getInt(1)),rs.getDate(2)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xwagesum[] objects = new Xwagesum[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xwagesum xwagesum = (Xwagesum) lst.get(i);
            objects[i] = xwagesum;
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
        String stmt = "SELECT xwagesum_id FROM xwagesum " +
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
    //    return getXwagesumId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xwagesumId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXwagesumId(id);
        setNew(prevIsNew);
    }

    public Integer getXwagesumId() {
        return xwagesumId;
    }

    public void setXwagesumId(Integer xwagesumId) throws ForeignKeyViolationException {
        setWasChanged(this.xwagesumId != null && this.xwagesumId != xwagesumId);
        this.xwagesumId = xwagesumId;
        setNew(xwagesumId.intValue() == 0);
    }

    public Date getWeekend() {
        return weekend;
    }

    public void setWeekend(Date weekend) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.weekend != null && !this.weekend.equals(weekend));
        this.weekend = weekend;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[2];
        columnValues[0] = getXwagesumId();
        columnValues[1] = getWeekend();
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
            setXwagesumId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXwagesumId(null);
        }
        setWeekend(toDate(flds[1]));
    }
}
