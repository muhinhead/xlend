// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Sep 07 22:45:42 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xemployeeincident extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xemployeeincidentId = null;
    private Integer xincidentsId = null;
    private Integer xemployeeId = null;

    public Xemployeeincident(Connection connection) {
        super(connection, "xemployeeincident", "xemployeeincident_id");
        setColumnNames(new String[]{"xemployeeincident_id", "xincidents_id", "xemployee_id"});
    }

    public Xemployeeincident(Connection connection, Integer xemployeeincidentId, Integer xincidentsId, Integer xemployeeId) {
        super(connection, "xemployeeincident", "xemployeeincident_id");
        setNew(xemployeeincidentId.intValue() <= 0);
//        if (xemployeeincidentId.intValue() != 0) {
            this.xemployeeincidentId = xemployeeincidentId;
//        }
        this.xincidentsId = xincidentsId;
        this.xemployeeId = xemployeeId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xemployeeincident xemployeeincident = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xemployeeincident_id,xincidents_id,xemployee_id FROM xemployeeincident WHERE xemployeeincident_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xemployeeincident = new Xemployeeincident(getConnection());
                xemployeeincident.setXemployeeincidentId(new Integer(rs.getInt(1)));
                xemployeeincident.setXincidentsId(new Integer(rs.getInt(2)));
                xemployeeincident.setXemployeeId(new Integer(rs.getInt(3)));
                xemployeeincident.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xemployeeincident;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xemployeeincident ("+(getXemployeeincidentId().intValue()!=0?"xemployeeincident_id,":"")+"xincidents_id,xemployee_id) values("+(getXemployeeincidentId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXemployeeincidentId().intValue()!=0) {
                 ps.setObject(++n, getXemployeeincidentId());
             }
             ps.setObject(++n, getXincidentsId());
             ps.setObject(++n, getXemployeeId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXemployeeincidentId().intValue()==0) {
             stmt = "SELECT max(xemployeeincident_id) FROM xemployeeincident";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXemployeeincidentId(new Integer(rs.getInt(1)));
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
                    "UPDATE xemployeeincident " +
                    "SET xincidents_id = ?, xemployee_id = ?" + 
                    " WHERE xemployeeincident_id = " + getXemployeeincidentId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXincidentsId());
                ps.setObject(2, getXemployeeId());
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
                "DELETE FROM xemployeeincident " +
                "WHERE xemployeeincident_id = " + getXemployeeincidentId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXemployeeincidentId(new Integer(-getXemployeeincidentId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXemployeeincidentId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xemployeeincident_id,xincidents_id,xemployee_id FROM xemployeeincident " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xemployeeincident(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xemployeeincident[] objects = new Xemployeeincident[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xemployeeincident xemployeeincident = (Xemployeeincident) lst.get(i);
            objects[i] = xemployeeincident;
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
        String stmt = "SELECT xemployeeincident_id FROM xemployeeincident " +
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
    //    return getXemployeeincidentId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xemployeeincidentId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXemployeeincidentId(id);
        setNew(prevIsNew);
    }

    public Integer getXemployeeincidentId() {
        return xemployeeincidentId;
    }

    public void setXemployeeincidentId(Integer xemployeeincidentId) throws ForeignKeyViolationException {
        setWasChanged(this.xemployeeincidentId != null && this.xemployeeincidentId != xemployeeincidentId);
        this.xemployeeincidentId = xemployeeincidentId;
        setNew(xemployeeincidentId.intValue() == 0);
    }

    public Integer getXincidentsId() {
        return xincidentsId;
    }

    public void setXincidentsId(Integer xincidentsId) throws SQLException, ForeignKeyViolationException {
        if (xincidentsId!=null && !Xincidents.exists(getConnection(),"xincidents_id = " + xincidentsId)) {
            throw new ForeignKeyViolationException("Can't set xincidents_id, foreign key violation: xemployeeincident_xincidents_fk");
        }
        setWasChanged(this.xincidentsId != null && !this.xincidentsId.equals(xincidentsId));
        this.xincidentsId = xincidentsId;
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xemployeeincident_xemployee_fk");
        }
        setWasChanged(this.xemployeeId != null && !this.xemployeeId.equals(xemployeeId));
        this.xemployeeId = xemployeeId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getXemployeeincidentId();
        columnValues[1] = getXincidentsId();
        columnValues[2] = getXemployeeId();
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
            setXemployeeincidentId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXemployeeincidentId(null);
        }
        try {
            setXincidentsId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXincidentsId(null);
        }
        try {
            setXemployeeId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
    }
}
