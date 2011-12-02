// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Dec 02 17:45:23 EET 2011
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xquotation extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xquotationId = null;
    private Integer xclientId = null;
    private String rfcnumber = null;

    public Xquotation(Connection connection) {
        super(connection, "xquotation", "xquotation_id");
        setColumnNames(new String[]{"xquotation_id", "xclient_id", "rfcnumber"});
    }

    public Xquotation(Connection connection, Integer xquotationId, Integer xclientId, String rfcnumber) {
        super(connection, "xquotation", "xquotation_id");
        setNew(xquotationId.intValue() <= 0);
//        if (xquotationId.intValue() != 0) {
            this.xquotationId = xquotationId;
//        }
        this.xclientId = xclientId;
        this.rfcnumber = rfcnumber;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xquotation xquotation = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xquotation_id,xclient_id,rfcnumber FROM xquotation WHERE xquotation_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xquotation = new Xquotation(getConnection());
                xquotation.setXquotationId(new Integer(rs.getInt(1)));
                xquotation.setXclientId(new Integer(rs.getInt(2)));
                xquotation.setRfcnumber(rs.getString(3));
                xquotation.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xquotation;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xquotation ("+(getXquotationId().intValue()!=0?"xquotation_id,":"")+"xclient_id,rfcnumber) values("+(getXquotationId().intValue()!=0?"?,":"")+"?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXquotationId().intValue()!=0) {
                 ps.setObject(++n, getXquotationId());
             }
             ps.setObject(++n, getXclientId());
             ps.setObject(++n, getRfcnumber());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXquotationId().intValue()==0) {
             stmt = "SELECT max(xquotation_id) FROM xquotation";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXquotationId(new Integer(rs.getInt(1)));
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
                    "UPDATE xquotation " +
                    "SET xclient_id = ?, rfcnumber = ?" + 
                    " WHERE xquotation_id = " + getXquotationId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXclientId());
                ps.setObject(2, getRfcnumber());
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
        if (Xorder.exists(getConnection(),"xquotation_id = " + getXquotationId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xorder_xquotation_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xquotationpage
            Xquotationpage[] records = (Xquotationpage[])Xquotationpage.load(getConnection(),"xquotation_id = " + getXquotationId(),null);
            for (int i = 0; i<records.length; i++) {
                Xquotationpage xquotationpage = records[i];
                xquotationpage.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xquotation " +
                "WHERE xquotation_id = " + getXquotationId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXquotationId(new Integer(-getXquotationId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXquotationId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xquotation_id,xclient_id,rfcnumber FROM xquotation " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xquotation(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xquotation[] objects = new Xquotation[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xquotation xquotation = (Xquotation) lst.get(i);
            objects[i] = xquotation;
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
        String stmt = "SELECT xquotation_id FROM xquotation " +
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
    //    return getXquotationId() + getDelimiter();
    //}

    public Integer getXquotationId() {
        return xquotationId;
    }

    public void setXquotationId(Integer xquotationId) throws ForeignKeyViolationException {
        setWasChanged(this.xquotationId != null && this.xquotationId != xquotationId);
        this.xquotationId = xquotationId;
        setNew(xquotationId.intValue() == 0);
    }

    public Integer getXclientId() {
        return xclientId;
    }

    public void setXclientId(Integer xclientId) throws SQLException, ForeignKeyViolationException {
        if (xclientId!=null && !Xclient.exists(getConnection(),"xclient_id = " + xclientId)) {
            throw new ForeignKeyViolationException("Can't set xclient_id, foreign key violation: xquotation_xclient_fk");
        }
        setWasChanged(this.xclientId != null && !this.xclientId.equals(xclientId));
        this.xclientId = xclientId;
    }

    public String getRfcnumber() {
        return rfcnumber;
    }

    public void setRfcnumber(String rfcnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.rfcnumber != null && !this.rfcnumber.equals(rfcnumber));
        this.rfcnumber = rfcnumber;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[3];
        columnValues[0] = getXquotationId();
        columnValues[1] = getXclientId();
        columnValues[2] = getRfcnumber();
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
            setXquotationId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXquotationId(null);
        }
        try {
            setXclientId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXclientId(null);
        }
        setRfcnumber(flds[2]);
    }
}
