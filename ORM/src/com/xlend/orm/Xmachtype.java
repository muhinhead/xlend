// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Wed Dec 14 11:51:26 EET 2011
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmachtype extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmachtypeId = null;
    private String machtype = null;
    private Integer parenttypeId = null;

    public Xmachtype(Connection connection) {
        super(connection, "xmachtype", "xmachtype_id");
        setColumnNames(new String[]{"xmachtype_id", "machtype", "parenttype_id"});
    }

    public Xmachtype(Connection connection, Integer xmachtypeId, String machtype, Integer parenttypeId) {
        super(connection, "xmachtype", "xmachtype_id");
        setNew(xmachtypeId.intValue() <= 0);
//        if (xmachtypeId.intValue() != 0) {
            this.xmachtypeId = xmachtypeId;
//        }
        this.machtype = machtype;
        this.parenttypeId = parenttypeId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmachtype xmachtype = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachtype_id,machtype,parenttype_id FROM xmachtype WHERE xmachtype_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachtype = new Xmachtype(getConnection());
                xmachtype.setXmachtypeId(new Integer(rs.getInt(1)));
                xmachtype.setMachtype(rs.getString(2));
                xmachtype.setParenttypeId(new Integer(rs.getInt(3)));
                xmachtype.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmachtype;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmachtype ("+(getXmachtypeId().intValue()!=0?"xmachtype_id,":"")+"machtype,parenttype_id) values("+(getXmachtypeId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachtypeId().intValue()!=0) {
                 ps.setObject(++n, getXmachtypeId());
             }
             ps.setObject(++n, getMachtype());
             ps.setObject(++n, getParenttypeId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmachtypeId().intValue()==0) {
             stmt = "SELECT max(xmachtype_id) FROM xmachtype";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmachtypeId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmachtype " +
                    "SET machtype = ?, parenttype_id = ?" + 
                    " WHERE xmachtype_id = " + getXmachtypeId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getMachtype());
                ps.setObject(2, getParenttypeId());
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
        if (Xmachine.exists(getConnection(),"xmachtype2_id = " + getXmachtypeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xmachine_xmachtype_fk2");
        }
        if (Xmachine.exists(getConnection(),"xmachtype_id = " + getXmachtypeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xmachine_xmachtype_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xmachtype " +
                "WHERE xmachtype_id = " + getXmachtypeId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmachtypeId(new Integer(-getXmachtypeId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmachtypeId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachtype_id,machtype,parenttype_id FROM xmachtype " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachtype(con,new Integer(rs.getInt(1)),rs.getString(2),new Integer(rs.getInt(3))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmachtype[] objects = new Xmachtype[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmachtype xmachtype = (Xmachtype) lst.get(i);
            objects[i] = xmachtype;
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
        String stmt = "SELECT xmachtype_id FROM xmachtype " +
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
    //    return getXmachtypeId() + getDelimiter();
    //}

    public Integer getXmachtypeId() {
        return xmachtypeId;
    }

    public void setXmachtypeId(Integer xmachtypeId) throws ForeignKeyViolationException {
        setWasChanged(this.xmachtypeId != null && this.xmachtypeId != xmachtypeId);
        this.xmachtypeId = xmachtypeId;
        setNew(xmachtypeId.intValue() == 0);
    }

    public String getMachtype() {
        return machtype;
    }

    public void setMachtype(String machtype) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.machtype != null && !this.machtype.equals(machtype));
        this.machtype = machtype;
    }

    public Integer getParenttypeId() {
        return parenttypeId;
    }

    public void setParenttypeId(Integer parenttypeId) throws SQLException, ForeignKeyViolationException {
        if (null != parenttypeId)
            parenttypeId = parenttypeId == 0 ? null : parenttypeId;
        setWasChanged(this.parenttypeId != null && !this.parenttypeId.equals(parenttypeId));
        this.parenttypeId = parenttypeId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getXmachtypeId();
        columnValues[1] = getMachtype();
        columnValues[2] = getParenttypeId();
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
            setXmachtypeId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmachtypeId(null);
        }
        setMachtype(flds[1]);
        try {
            setParenttypeId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setParenttypeId(null);
        }
    }
}
