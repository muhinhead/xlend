// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmoveableassets extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmoveableassetsId = null;
    private String assetName = null;
    private Integer bookedTo = null;
    private Integer xsiteId = null;
    private Date bookedOut = null;
    private Date deadline = null;
    private Date returned = null;

    public Xmoveableassets(Connection connection) {
        super(connection, "xmoveableassets", "xmoveableassets_id");
        setColumnNames(new String[]{"xmoveableassets_id", "asset_name", "booked_to", "xsite_id", "booked_out", "deadline", "returned"});
    }

    public Xmoveableassets(Connection connection, Integer xmoveableassetsId, String assetName, Integer bookedTo, Integer xsiteId, Date bookedOut, Date deadline, Date returned) {
        super(connection, "xmoveableassets", "xmoveableassets_id");
        setNew(xmoveableassetsId.intValue() <= 0);
//        if (xmoveableassetsId.intValue() != 0) {
            this.xmoveableassetsId = xmoveableassetsId;
//        }
        this.assetName = assetName;
        this.bookedTo = bookedTo;
        this.xsiteId = xsiteId;
        this.bookedOut = bookedOut;
        this.deadline = deadline;
        this.returned = returned;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmoveableassets xmoveableassets = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmoveableassets_id,asset_name,booked_to,xsite_id,booked_out,deadline,returned FROM xmoveableassets WHERE xmoveableassets_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmoveableassets = new Xmoveableassets(getConnection());
                xmoveableassets.setXmoveableassetsId(new Integer(rs.getInt(1)));
                xmoveableassets.setAssetName(rs.getString(2));
                xmoveableassets.setBookedTo(new Integer(rs.getInt(3)));
                xmoveableassets.setXsiteId(new Integer(rs.getInt(4)));
                xmoveableassets.setBookedOut(rs.getDate(5));
                xmoveableassets.setDeadline(rs.getDate(6));
                xmoveableassets.setReturned(rs.getDate(7));
                xmoveableassets.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmoveableassets;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmoveableassets ("+(getXmoveableassetsId().intValue()!=0?"xmoveableassets_id,":"")+"asset_name,booked_to,xsite_id,booked_out,deadline,returned) values("+(getXmoveableassetsId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmoveableassetsId().intValue()!=0) {
                 ps.setObject(++n, getXmoveableassetsId());
             }
             ps.setObject(++n, getAssetName());
             ps.setObject(++n, getBookedTo());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getBookedOut());
             ps.setObject(++n, getDeadline());
             ps.setObject(++n, getReturned());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmoveableassetsId().intValue()==0) {
             stmt = "SELECT max(xmoveableassets_id) FROM xmoveableassets";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmoveableassetsId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmoveableassets " +
                    "SET asset_name = ?, booked_to = ?, xsite_id = ?, booked_out = ?, deadline = ?, returned = ?" + 
                    " WHERE xmoveableassets_id = " + getXmoveableassetsId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getAssetName());
                ps.setObject(2, getBookedTo());
                ps.setObject(3, getXsiteId());
                ps.setObject(4, getBookedOut());
                ps.setObject(5, getDeadline());
                ps.setObject(6, getReturned());
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
                "DELETE FROM xmoveableassets " +
                "WHERE xmoveableassets_id = " + getXmoveableassetsId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmoveableassetsId(new Integer(-getXmoveableassetsId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmoveableassetsId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmoveableassets_id,asset_name,booked_to,xsite_id,booked_out,deadline,returned FROM xmoveableassets " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmoveableassets(con,new Integer(rs.getInt(1)),rs.getString(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDate(5),rs.getDate(6),rs.getDate(7)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmoveableassets[] objects = new Xmoveableassets[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmoveableassets xmoveableassets = (Xmoveableassets) lst.get(i);
            objects[i] = xmoveableassets;
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
        String stmt = "SELECT xmoveableassets_id FROM xmoveableassets " +
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
    //    return getXmoveableassetsId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xmoveableassetsId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXmoveableassetsId(id);
        setNew(prevIsNew);
    }

    public Integer getXmoveableassetsId() {
        return xmoveableassetsId;
    }

    public void setXmoveableassetsId(Integer xmoveableassetsId) throws ForeignKeyViolationException {
        setWasChanged(this.xmoveableassetsId != null && this.xmoveableassetsId != xmoveableassetsId);
        this.xmoveableassetsId = xmoveableassetsId;
        setNew(xmoveableassetsId.intValue() == 0);
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.assetName != null && !this.assetName.equals(assetName));
        this.assetName = assetName;
    }

    public Integer getBookedTo() {
        return bookedTo;
    }

    public void setBookedTo(Integer bookedTo) throws SQLException, ForeignKeyViolationException {
        if (bookedTo!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + bookedTo)) {
            throw new ForeignKeyViolationException("Can't set booked_to, foreign key violation: xmoveableassets_xemployee_fk");
        }
        setWasChanged(this.bookedTo != null && !this.bookedTo.equals(bookedTo));
        this.bookedTo = bookedTo;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xmoveableassets_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Date getBookedOut() {
        return bookedOut;
    }

    public void setBookedOut(Date bookedOut) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.bookedOut != null && !this.bookedOut.equals(bookedOut));
        this.bookedOut = bookedOut;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.deadline != null && !this.deadline.equals(deadline));
        this.deadline = deadline;
    }

    public Date getReturned() {
        return returned;
    }

    public void setReturned(Date returned) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.returned != null && !this.returned.equals(returned));
        this.returned = returned;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXmoveableassetsId();
        columnValues[1] = getAssetName();
        columnValues[2] = getBookedTo();
        columnValues[3] = getXsiteId();
        columnValues[4] = getBookedOut();
        columnValues[5] = getDeadline();
        columnValues[6] = getReturned();
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
            setXmoveableassetsId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmoveableassetsId(null);
        }
        setAssetName(flds[1]);
        try {
            setBookedTo(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setBookedTo(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        setBookedOut(toDate(flds[4]));
        setDeadline(toDate(flds[5]));
        setReturned(toDate(flds[6]));
    }
}
