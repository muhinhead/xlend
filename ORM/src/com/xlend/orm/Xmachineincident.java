// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Apr 21 12:01:28 EEST 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmachineincident extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmachineincidentId = null;
    private Integer xincidentsId = null;
    private Integer xmachineId = null;

    public Xmachineincident(Connection connection) {
        super(connection, "xmachineincident", "xmachineincident_id");
        setColumnNames(new String[]{"xmachineincident_id", "xincidents_id", "xmachine_id"});
    }

    public Xmachineincident(Connection connection, Integer xmachineincidentId, Integer xincidentsId, Integer xmachineId) {
        super(connection, "xmachineincident", "xmachineincident_id");
        setNew(xmachineincidentId.intValue() <= 0);
//        if (xmachineincidentId.intValue() != 0) {
            this.xmachineincidentId = xmachineincidentId;
//        }
        this.xincidentsId = xincidentsId;
        this.xmachineId = xmachineId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmachineincident xmachineincident = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachineincident_id,xincidents_id,xmachine_id FROM xmachineincident WHERE xmachineincident_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachineincident = new Xmachineincident(getConnection());
                xmachineincident.setXmachineincidentId(new Integer(rs.getInt(1)));
                xmachineincident.setXincidentsId(new Integer(rs.getInt(2)));
                xmachineincident.setXmachineId(new Integer(rs.getInt(3)));
                xmachineincident.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmachineincident;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmachineincident ("+(getXmachineincidentId().intValue()!=0?"xmachineincident_id,":"")+"xincidents_id,xmachine_id) values("+(getXmachineincidentId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachineincidentId().intValue()!=0) {
                 ps.setObject(++n, getXmachineincidentId());
             }
             ps.setObject(++n, getXincidentsId());
             ps.setObject(++n, getXmachineId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmachineincidentId().intValue()==0) {
             stmt = "SELECT max(xmachineincident_id) FROM xmachineincident";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmachineincidentId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmachineincident " +
                    "SET xincidents_id = ?, xmachine_id = ?" + 
                    " WHERE xmachineincident_id = " + getXmachineincidentId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXincidentsId());
                ps.setObject(2, getXmachineId());
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
                "DELETE FROM xmachineincident " +
                "WHERE xmachineincident_id = " + getXmachineincidentId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmachineincidentId(new Integer(-getXmachineincidentId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmachineincidentId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachineincident_id,xincidents_id,xmachine_id FROM xmachineincident " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachineincident(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmachineincident[] objects = new Xmachineincident[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmachineincident xmachineincident = (Xmachineincident) lst.get(i);
            objects[i] = xmachineincident;
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
        String stmt = "SELECT xmachineincident_id FROM xmachineincident " +
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
    //    return getXmachineincidentId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xmachineincidentId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXmachineincidentId(id);
        setNew(prevIsNew);
    }

    public Integer getXmachineincidentId() {
        return xmachineincidentId;
    }

    public void setXmachineincidentId(Integer xmachineincidentId) throws ForeignKeyViolationException {
        setWasChanged(this.xmachineincidentId != null && this.xmachineincidentId != xmachineincidentId);
        this.xmachineincidentId = xmachineincidentId;
        setNew(xmachineincidentId.intValue() == 0);
    }

    public Integer getXincidentsId() {
        return xincidentsId;
    }

    public void setXincidentsId(Integer xincidentsId) throws SQLException, ForeignKeyViolationException {
        if (xincidentsId!=null && !Xincidents.exists(getConnection(),"xincidents_id = " + xincidentsId)) {
            throw new ForeignKeyViolationException("Can't set xincidents_id, foreign key violation: xmachineincident_xincidents_fk");
        }
        setWasChanged(this.xincidentsId != null && !this.xincidentsId.equals(xincidentsId));
        this.xincidentsId = xincidentsId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xmachineincident_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getXmachineincidentId();
        columnValues[1] = getXincidentsId();
        columnValues[2] = getXmachineId();
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
            setXmachineincidentId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmachineincidentId(null);
        }
        try {
            setXincidentsId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXincidentsId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
    }
}
