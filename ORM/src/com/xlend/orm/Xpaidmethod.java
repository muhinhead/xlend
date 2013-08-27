// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Aug 27 12:59:58 FET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xpaidmethod extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpaidmethodId = null;
    private String method = null;

    public Xpaidmethod(Connection connection) {
        super(connection, "xpaidmethod", "xpaidmethod_id");
        setColumnNames(new String[]{"xpaidmethod_id", "method"});
    }

    public Xpaidmethod(Connection connection, Integer xpaidmethodId, String method) {
        super(connection, "xpaidmethod", "xpaidmethod_id");
        setNew(xpaidmethodId.intValue() <= 0);
//        if (xpaidmethodId.intValue() != 0) {
            this.xpaidmethodId = xpaidmethodId;
//        }
        this.method = method;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xpaidmethod xpaidmethod = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpaidmethod_id,method FROM xpaidmethod WHERE xpaidmethod_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xpaidmethod = new Xpaidmethod(getConnection());
                xpaidmethod.setXpaidmethodId(new Integer(rs.getInt(1)));
                xpaidmethod.setMethod(rs.getString(2));
                xpaidmethod.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xpaidmethod;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xpaidmethod ("+(getXpaidmethodId().intValue()!=0?"xpaidmethod_id,":"")+"method) values("+(getXpaidmethodId().intValue()!=0?"?,":"")+"?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpaidmethodId().intValue()!=0) {
                 ps.setObject(++n, getXpaidmethodId());
             }
             ps.setObject(++n, getMethod());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpaidmethodId().intValue()==0) {
             stmt = "SELECT max(xpaidmethod_id) FROM xpaidmethod";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpaidmethodId(new Integer(rs.getInt(1)));
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
                    "UPDATE xpaidmethod " +
                    "SET method = ?" + 
                    " WHERE xpaidmethod_id = " + getXpaidmethodId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getMethod());
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
        if (Xconsume.exists(getConnection(),"xpaidmethod_id = " + getXpaidmethodId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xconsume_xpaidmethod_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xpaidmethod " +
                "WHERE xpaidmethod_id = " + getXpaidmethodId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpaidmethodId(new Integer(-getXpaidmethodId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpaidmethodId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xpaidmethod_id,method FROM xpaidmethod " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xpaidmethod(con,new Integer(rs.getInt(1)),rs.getString(2)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xpaidmethod[] objects = new Xpaidmethod[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xpaidmethod xpaidmethod = (Xpaidmethod) lst.get(i);
            objects[i] = xpaidmethod;
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
        String stmt = "SELECT xpaidmethod_id FROM xpaidmethod " +
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
    //    return getXpaidmethodId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xpaidmethodId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXpaidmethodId(id);
        setNew(prevIsNew);
    }

    public Integer getXpaidmethodId() {
        return xpaidmethodId;
    }

    public void setXpaidmethodId(Integer xpaidmethodId) throws ForeignKeyViolationException {
        setWasChanged(this.xpaidmethodId != null && this.xpaidmethodId != xpaidmethodId);
        this.xpaidmethodId = xpaidmethodId;
        setNew(xpaidmethodId.intValue() == 0);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.method != null && !this.method.equals(method));
        this.method = method;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[2];
        columnValues[0] = getXpaidmethodId();
        columnValues[1] = getMethod();
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
            setXpaidmethodId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpaidmethodId(null);
        }
        setMethod(flds[1]);
    }
}
