// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Wed Aug 22 15:53:21 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xlowbed extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xlowbedId = null;
    private Integer xmachineId = null;
    private Integer driverId = null;
    private Integer assistantId = null;

    public Xlowbed(Connection connection) {
        super(connection, "xlowbed", "xlowbed_id");
        setColumnNames(new String[]{"xlowbed_id", "xmachine_id", "driver_id", "assistant_id"});
    }

    public Xlowbed(Connection connection, Integer xlowbedId, Integer xmachineId, Integer driverId, Integer assistantId) {
        super(connection, "xlowbed", "xlowbed_id");
        setNew(xlowbedId.intValue() <= 0);
//        if (xlowbedId.intValue() != 0) {
            this.xlowbedId = xlowbedId;
//        }
        this.xmachineId = xmachineId;
        this.driverId = driverId;
        this.assistantId = assistantId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xlowbed xlowbed = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xlowbed_id,xmachine_id,driver_id,assistant_id FROM xlowbed WHERE xlowbed_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xlowbed = new Xlowbed(getConnection());
                xlowbed.setXlowbedId(new Integer(rs.getInt(1)));
                xlowbed.setXmachineId(new Integer(rs.getInt(2)));
                xlowbed.setDriverId(new Integer(rs.getInt(3)));
                xlowbed.setAssistantId(new Integer(rs.getInt(4)));
                xlowbed.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xlowbed;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xlowbed ("+(getXlowbedId().intValue()!=0?"xlowbed_id,":"")+"xmachine_id,driver_id,assistant_id) values("+(getXlowbedId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXlowbedId().intValue()!=0) {
                 ps.setObject(++n, getXlowbedId());
             }
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getDriverId());
             ps.setObject(++n, getAssistantId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXlowbedId().intValue()==0) {
             stmt = "SELECT max(xlowbed_id) FROM xlowbed";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXlowbedId(new Integer(rs.getInt(1)));
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
                    "UPDATE xlowbed " +
                    "SET xmachine_id = ?, driver_id = ?, assistant_id = ?" + 
                    " WHERE xlowbed_id = " + getXlowbedId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXmachineId());
                ps.setObject(2, getDriverId());
                ps.setObject(3, getAssistantId());
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
        if (Xtripsheet.exists(getConnection(),"xlowbed_id = " + getXlowbedId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripsheet_xmachine_fk");
        }
        if (Xtransscheduleitm.exists(getConnection(),"lowbed_id = " + getXlowbedId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtransscheduleitm_xmachine_fk2");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xtrip
            Xtrip[] records = (Xtrip[])Xtrip.load(getConnection(),"xlowbed_id = " + getXlowbedId(),null);
            for (int i = 0; i<records.length; i++) {
                Xtrip xtrip = records[i];
                xtrip.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xlowbed " +
                "WHERE xlowbed_id = " + getXlowbedId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXlowbedId(new Integer(-getXlowbedId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXlowbedId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xlowbed_id,xmachine_id,driver_id,assistant_id FROM xlowbed " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xlowbed(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xlowbed[] objects = new Xlowbed[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xlowbed xlowbed = (Xlowbed) lst.get(i);
            objects[i] = xlowbed;
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
        String stmt = "SELECT xlowbed_id FROM xlowbed " +
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
    //    return getXlowbedId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xlowbedId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXlowbedId(id);
        setNew(prevIsNew);
    }

    public Integer getXlowbedId() {
        return xlowbedId;
    }

    public void setXlowbedId(Integer xlowbedId) throws ForeignKeyViolationException {
        setWasChanged(this.xlowbedId != null && this.xlowbedId != xlowbedId);
        this.xlowbedId = xlowbedId;
        setNew(xlowbedId.intValue() == 0);
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xlowbed_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) throws SQLException, ForeignKeyViolationException {
        if (null != driverId)
            driverId = driverId == 0 ? null : driverId;
        if (driverId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + driverId)) {
            throw new ForeignKeyViolationException("Can't set driver_id, foreign key violation: xlowbed_xemployee_fk");
        }
        setWasChanged(this.driverId != null && !this.driverId.equals(driverId));
        this.driverId = driverId;
    }

    public Integer getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Integer assistantId) throws SQLException, ForeignKeyViolationException {
        if (null != assistantId)
            assistantId = assistantId == 0 ? null : assistantId;
        if (assistantId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + assistantId)) {
            throw new ForeignKeyViolationException("Can't set assistant_id, foreign key violation: xlowbed_xemployee_fk2");
        }
        setWasChanged(this.assistantId != null && !this.assistantId.equals(assistantId));
        this.assistantId = assistantId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getXlowbedId();
        columnValues[1] = getXmachineId();
        columnValues[2] = getDriverId();
        columnValues[3] = getAssistantId();
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
            setXlowbedId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXlowbedId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setDriverId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setDriverId(null);
        }
        try {
            setAssistantId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setAssistantId(null);
        }
    }
}
