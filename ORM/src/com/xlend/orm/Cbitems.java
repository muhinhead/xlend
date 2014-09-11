// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Sep 06 18:00:39 EEST 2014
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Cbitems extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer cbitemId = null;
    private String name = null;
    private Integer id = null;
    private String val = null;

    public Cbitems(Connection connection) {
        super(connection, "cbitems", "cbitem_id");
        setColumnNames(new String[]{"cbitem_id", "name", "id", "val"});
    }

    public Cbitems(Connection connection, Integer cbitemId, String name, Integer id, String val) {
        super(connection, "cbitems", "cbitem_id");
        setNew(cbitemId.intValue() <= 0);
//        if (cbitemId.intValue() != 0) {
            this.cbitemId = cbitemId;
//        }
        this.name = name;
        this.id = id;
        this.val = val;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Cbitems cbitems = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT cbitem_id,name,id,val FROM cbitems WHERE cbitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                cbitems = new Cbitems(getConnection());
                cbitems.setCbitemId(new Integer(rs.getInt(1)));
                cbitems.setName(rs.getString(2));
                cbitems.setId(new Integer(rs.getInt(3)));
                cbitems.setVal(rs.getString(4));
                cbitems.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return cbitems;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO cbitems ("+(getCbitemId().intValue()!=0?"cbitem_id,":"")+"name,id,val) values("+(getCbitemId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getCbitemId().intValue()!=0) {
                 ps.setObject(++n, getCbitemId());
             }
             ps.setObject(++n, getName());
             ps.setObject(++n, getId());
             ps.setObject(++n, getVal());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getCbitemId().intValue()==0) {
             stmt = "SELECT max(cbitem_id) FROM cbitems";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setCbitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE cbitems " +
                    "SET name = ?, id = ?, val = ?" + 
                    " WHERE cbitem_id = " + getCbitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getName());
                ps.setObject(2, getId());
                ps.setObject(3, getVal());
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
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM cbitems " +
                "WHERE cbitem_id = " + getCbitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setCbitemId(new Integer(-getCbitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getCbitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT cbitem_id,name,id,val FROM cbitems " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Cbitems(con,new Integer(rs.getInt(1)),rs.getString(2),new Integer(rs.getInt(3)),rs.getString(4)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Cbitems[] objects = new Cbitems[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Cbitems cbitems = (Cbitems) lst.get(i);
            objects[i] = cbitems;
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
        String stmt = "SELECT cbitem_id FROM cbitems " +
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
    //    return getCbitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return cbitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setCbitemId(id);
        setNew(prevIsNew);
    }

    public Integer getCbitemId() {
        return cbitemId;
    }

    public void setCbitemId(Integer cbitemId) throws ForeignKeyViolationException {
        setWasChanged(this.cbitemId != null && this.cbitemId != cbitemId);
        this.cbitemId = cbitemId;
        setNew(cbitemId.intValue() == 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.name != null && !this.name.equals(name));
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.id != null && !this.id.equals(id));
        this.id = id;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.val != null && !this.val.equals(val));
        this.val = val;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getCbitemId();
        columnValues[1] = getName();
        columnValues[2] = getId();
        columnValues[3] = getVal();
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
            setCbitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setCbitemId(null);
        }
        setName(flds[1]);
        try {
            setId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setId(null);
        }
        setVal(flds[3]);
    }
}
