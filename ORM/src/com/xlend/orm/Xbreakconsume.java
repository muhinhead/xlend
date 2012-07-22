// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Jul 22 08:56:26 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xbreakconsume extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xbreakconsumeId = null;
    private Integer xconsumeId = null;
    private Integer xbreakdownId = null;

    public Xbreakconsume(Connection connection) {
        super(connection, "xbreakconsume", "xbreakconsume_id");
        setColumnNames(new String[]{"xbreakconsume_id", "xconsume_id", "xbreakdown_id"});
    }

    public Xbreakconsume(Connection connection, Integer xbreakconsumeId, Integer xconsumeId, Integer xbreakdownId) {
        super(connection, "xbreakconsume", "xbreakconsume_id");
        setNew(xbreakconsumeId.intValue() <= 0);
//        if (xbreakconsumeId.intValue() != 0) {
            this.xbreakconsumeId = xbreakconsumeId;
//        }
        this.xconsumeId = xconsumeId;
        this.xbreakdownId = xbreakdownId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xbreakconsume xbreakconsume = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbreakconsume_id,xconsume_id,xbreakdown_id FROM xbreakconsume WHERE xbreakconsume_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xbreakconsume = new Xbreakconsume(getConnection());
                xbreakconsume.setXbreakconsumeId(new Integer(rs.getInt(1)));
                xbreakconsume.setXconsumeId(new Integer(rs.getInt(2)));
                xbreakconsume.setXbreakdownId(new Integer(rs.getInt(3)));
                xbreakconsume.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xbreakconsume;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xbreakconsume ("+(getXbreakconsumeId().intValue()!=0?"xbreakconsume_id,":"")+"xconsume_id,xbreakdown_id) values("+(getXbreakconsumeId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXbreakconsumeId().intValue()!=0) {
                 ps.setObject(++n, getXbreakconsumeId());
             }
             ps.setObject(++n, getXconsumeId());
             ps.setObject(++n, getXbreakdownId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXbreakconsumeId().intValue()==0) {
             stmt = "SELECT max(xbreakconsume_id) FROM xbreakconsume";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXbreakconsumeId(new Integer(rs.getInt(1)));
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
                    "UPDATE xbreakconsume " +
                    "SET xconsume_id = ?, xbreakdown_id = ?" + 
                    " WHERE xbreakconsume_id = " + getXbreakconsumeId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXconsumeId());
                ps.setObject(2, getXbreakdownId());
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
                "DELETE FROM xbreakconsume " +
                "WHERE xbreakconsume_id = " + getXbreakconsumeId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXbreakconsumeId(new Integer(-getXbreakconsumeId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXbreakconsumeId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbreakconsume_id,xconsume_id,xbreakdown_id FROM xbreakconsume " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xbreakconsume(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xbreakconsume[] objects = new Xbreakconsume[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xbreakconsume xbreakconsume = (Xbreakconsume) lst.get(i);
            objects[i] = xbreakconsume;
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
        String stmt = "SELECT xbreakconsume_id FROM xbreakconsume " +
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
    //    return getXbreakconsumeId() + getDelimiter();
    //}

    public Integer getXbreakconsumeId() {
        return xbreakconsumeId;
    }

    public void setXbreakconsumeId(Integer xbreakconsumeId) throws ForeignKeyViolationException {
        setWasChanged(this.xbreakconsumeId != null && this.xbreakconsumeId != xbreakconsumeId);
        this.xbreakconsumeId = xbreakconsumeId;
        setNew(xbreakconsumeId.intValue() == 0);
    }

    public Integer getXconsumeId() {
        return xconsumeId;
    }

    public void setXconsumeId(Integer xconsumeId) throws SQLException, ForeignKeyViolationException {
        if (xconsumeId!=null && !Xconsume.exists(getConnection(),"xconsume_id = " + xconsumeId)) {
            throw new ForeignKeyViolationException("Can't set xconsume_id, foreign key violation: xbreakconsume_xconsume_fk");
        }
        setWasChanged(this.xconsumeId != null && !this.xconsumeId.equals(xconsumeId));
        this.xconsumeId = xconsumeId;
    }

    public Integer getXbreakdownId() {
        return xbreakdownId;
    }

    public void setXbreakdownId(Integer xbreakdownId) throws SQLException, ForeignKeyViolationException {
        if (xbreakdownId!=null && !Xbreakdown.exists(getConnection(),"xbreakdown_id = " + xbreakdownId)) {
            throw new ForeignKeyViolationException("Can't set xbreakdown_id, foreign key violation: xbreakconsume_xbreakdown_fk");
        }
        setWasChanged(this.xbreakdownId != null && !this.xbreakdownId.equals(xbreakdownId));
        this.xbreakdownId = xbreakdownId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getXbreakconsumeId();
        columnValues[1] = getXconsumeId();
        columnValues[2] = getXbreakdownId();
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
            setXbreakconsumeId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXbreakconsumeId(null);
        }
        try {
            setXconsumeId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXconsumeId(null);
        }
        try {
            setXbreakdownId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXbreakdownId(null);
        }
    }
}
