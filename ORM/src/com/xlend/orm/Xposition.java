// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jun 28 14:06:17 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xposition extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xpositionId = null;
    private String pos = null;

    public Xposition(Connection connection) {
        super(connection, "xposition", "xposition_id");
        setColumnNames(new String[]{"xposition_id", "pos"});
    }

    public Xposition(Connection connection, Integer xpositionId, String pos) {
        super(connection, "xposition", "xposition_id");
        setNew(xpositionId.intValue() <= 0);
//        if (xpositionId.intValue() != 0) {
            this.xpositionId = xpositionId;
//        }
        this.pos = pos;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xposition xposition = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xposition_id,pos FROM xposition WHERE xposition_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xposition = new Xposition(getConnection());
                xposition.setXpositionId(new Integer(rs.getInt(1)));
                xposition.setPos(rs.getString(2));
                xposition.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xposition;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xposition ("+(getXpositionId().intValue()!=0?"xposition_id,":"")+"pos) values("+(getXpositionId().intValue()!=0?"?,":"")+"?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXpositionId().intValue()!=0) {
                 ps.setObject(++n, getXpositionId());
             }
             ps.setObject(++n, getPos());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXpositionId().intValue()==0) {
             stmt = "SELECT max(xposition_id) FROM xposition";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXpositionId(new Integer(rs.getInt(1)));
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
                    "UPDATE xposition " +
                    "SET pos = ?" + 
                    " WHERE xposition_id = " + getXpositionId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getPos());
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
        if (Xemployee.exists(getConnection(),"xposition_id = " + getXpositionId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xemployee_xposition_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xposition " +
                "WHERE xposition_id = " + getXpositionId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXpositionId(new Integer(-getXpositionId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXpositionId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xposition_id,pos FROM xposition " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xposition(con,new Integer(rs.getInt(1)),rs.getString(2)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xposition[] objects = new Xposition[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xposition xposition = (Xposition) lst.get(i);
            objects[i] = xposition;
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
        String stmt = "SELECT xposition_id FROM xposition " +
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
    //    return getXpositionId() + getDelimiter();
    //}

    public Integer getXpositionId() {
        return xpositionId;
    }

    public void setXpositionId(Integer xpositionId) throws ForeignKeyViolationException {
        setWasChanged(this.xpositionId != null && this.xpositionId != xpositionId);
        this.xpositionId = xpositionId;
        setNew(xpositionId.intValue() == 0);
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.pos != null && !this.pos.equals(pos));
        this.pos = pos;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[2];
        columnValues[0] = getXpositionId();
        columnValues[1] = getPos();
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
            setXpositionId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXpositionId(null);
        }
        setPos(flds[1]);
    }
}
