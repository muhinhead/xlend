// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon May 14 19:53:46 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xtripsheet extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xtripsheetId = null;
    private Date tripdate = null;
    private Integer xlowbedId = null;
    private Integer driverId = null;
    private Integer authorizedId = null;

    public Xtripsheet(Connection connection) {
        super(connection, "xtripsheet", "xtripsheet_id");
        setColumnNames(new String[]{"xtripsheet_id", "tripdate", "xlowbed_id", "driver_id", "authorized_id"});
    }

    public Xtripsheet(Connection connection, Integer xtripsheetId, Date tripdate, Integer xlowbedId, Integer driverId, Integer authorizedId) {
        super(connection, "xtripsheet", "xtripsheet_id");
        setNew(xtripsheetId.intValue() <= 0);
//        if (xtripsheetId.intValue() != 0) {
            this.xtripsheetId = xtripsheetId;
//        }
        this.tripdate = tripdate;
        this.xlowbedId = xlowbedId;
        this.driverId = driverId;
        this.authorizedId = authorizedId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtripsheet xtripsheet = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripsheet_id,tripdate,xlowbed_id,driver_id,authorized_id FROM xtripsheet WHERE xtripsheet_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtripsheet = new Xtripsheet(getConnection());
                xtripsheet.setXtripsheetId(new Integer(rs.getInt(1)));
                xtripsheet.setTripdate(rs.getDate(2));
                xtripsheet.setXlowbedId(new Integer(rs.getInt(3)));
                xtripsheet.setDriverId(new Integer(rs.getInt(4)));
                xtripsheet.setAuthorizedId(new Integer(rs.getInt(5)));
                xtripsheet.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xtripsheet;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xtripsheet ("+(getXtripsheetId().intValue()!=0?"xtripsheet_id,":"")+"tripdate,xlowbed_id,driver_id,authorized_id) values("+(getXtripsheetId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtripsheetId().intValue()!=0) {
                 ps.setObject(++n, getXtripsheetId());
             }
             ps.setObject(++n, getTripdate());
             ps.setObject(++n, getXlowbedId());
             ps.setObject(++n, getDriverId());
             ps.setObject(++n, getAuthorizedId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXtripsheetId().intValue()==0) {
             stmt = "SELECT max(xtripsheet_id) FROM xtripsheet";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXtripsheetId(new Integer(rs.getInt(1)));
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
                    "UPDATE xtripsheet " +
                    "SET tripdate = ?, xlowbed_id = ?, driver_id = ?, authorized_id = ?" + 
                    " WHERE xtripsheet_id = " + getXtripsheetId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getTripdate());
                ps.setObject(2, getXlowbedId());
                ps.setObject(3, getDriverId());
                ps.setObject(4, getAuthorizedId());
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
        {// delete cascade from xtripsheetpart
            Xtripsheetpart[] records = (Xtripsheetpart[])Xtripsheetpart.load(getConnection(),"xtripsheet_id = " + getXtripsheetId(),null);
            for (int i = 0; i<records.length; i++) {
                Xtripsheetpart xtripsheetpart = records[i];
                xtripsheetpart.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xtripsheet " +
                "WHERE xtripsheet_id = " + getXtripsheetId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXtripsheetId(new Integer(-getXtripsheetId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXtripsheetId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripsheet_id,tripdate,xlowbed_id,driver_id,authorized_id FROM xtripsheet " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtripsheet(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xtripsheet[] objects = new Xtripsheet[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xtripsheet xtripsheet = (Xtripsheet) lst.get(i);
            objects[i] = xtripsheet;
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
        String stmt = "SELECT xtripsheet_id FROM xtripsheet " +
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
    //    return getXtripsheetId() + getDelimiter();
    //}

    public Integer getXtripsheetId() {
        return xtripsheetId;
    }

    public void setXtripsheetId(Integer xtripsheetId) throws ForeignKeyViolationException {
        setWasChanged(this.xtripsheetId != null && this.xtripsheetId != xtripsheetId);
        this.xtripsheetId = xtripsheetId;
        setNew(xtripsheetId.intValue() == 0);
    }

    public Date getTripdate() {
        return tripdate;
    }

    public void setTripdate(Date tripdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.tripdate != null && !this.tripdate.equals(tripdate));
        this.tripdate = tripdate;
    }

    public Integer getXlowbedId() {
        return xlowbedId;
    }

    public void setXlowbedId(Integer xlowbedId) throws SQLException, ForeignKeyViolationException {
        if (xlowbedId!=null && !Xlowbed.exists(getConnection(),"xlowbed_id = " + xlowbedId)) {
            throw new ForeignKeyViolationException("Can't set xlowbed_id, foreign key violation: xtripsheet_xmachine_fk");
        }
        setWasChanged(this.xlowbedId != null && !this.xlowbedId.equals(xlowbedId));
        this.xlowbedId = xlowbedId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) throws SQLException, ForeignKeyViolationException {
        if (driverId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + driverId)) {
            throw new ForeignKeyViolationException("Can't set driver_id, foreign key violation: xtripsheet_xemployee_fk");
        }
        setWasChanged(this.driverId != null && !this.driverId.equals(driverId));
        this.driverId = driverId;
    }

    public Integer getAuthorizedId() {
        return authorizedId;
    }

    public void setAuthorizedId(Integer authorizedId) throws SQLException, ForeignKeyViolationException {
        if (authorizedId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + authorizedId)) {
            throw new ForeignKeyViolationException("Can't set authorized_id, foreign key violation: xtripsheet_xemployee_fk2");
        }
        setWasChanged(this.authorizedId != null && !this.authorizedId.equals(authorizedId));
        this.authorizedId = authorizedId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[5];
        columnValues[0] = getXtripsheetId();
        columnValues[1] = getTripdate();
        columnValues[2] = getXlowbedId();
        columnValues[3] = getDriverId();
        columnValues[4] = getAuthorizedId();
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
            setXtripsheetId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXtripsheetId(null);
        }
        setTripdate(toDate(flds[1]));
        try {
            setXlowbedId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXlowbedId(null);
        }
        try {
            setDriverId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setDriverId(null);
        }
        try {
            setAuthorizedId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setAuthorizedId(null);
        }
    }
}
