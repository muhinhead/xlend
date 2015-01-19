// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 11:03:09 EET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xcontract extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xcontractId = null;
    private String contractref = null;
    private String description = null;
    private Integer xclientId = null;

    public Xcontract(Connection connection) {
        super(connection, "xcontract", "xcontract_id");
        setColumnNames(new String[]{"xcontract_id", "contractref", "description", "xclient_id"});
    }

    public Xcontract(Connection connection, Integer xcontractId, String contractref, String description, Integer xclientId) {
        super(connection, "xcontract", "xcontract_id");
        setNew(xcontractId.intValue() <= 0);
//        if (xcontractId.intValue() != 0) {
            this.xcontractId = xcontractId;
//        }
        this.contractref = contractref;
        this.description = description;
        this.xclientId = xclientId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xcontract xcontract = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xcontract_id,contractref,description,xclient_id FROM xcontract WHERE xcontract_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xcontract = new Xcontract(getConnection());
                xcontract.setXcontractId(new Integer(rs.getInt(1)));
                xcontract.setContractref(rs.getString(2));
                xcontract.setDescription(rs.getString(3));
                xcontract.setXclientId(new Integer(rs.getInt(4)));
                xcontract.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xcontract;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xcontract ("+(getXcontractId().intValue()!=0?"xcontract_id,":"")+"contractref,description,xclient_id) values("+(getXcontractId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXcontractId().intValue()!=0) {
                 ps.setObject(++n, getXcontractId());
             }
             ps.setObject(++n, getContractref());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getXclientId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXcontractId().intValue()==0) {
             stmt = "SELECT max(xcontract_id) FROM xcontract";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXcontractId(new Integer(rs.getInt(1)));
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
                    "UPDATE xcontract " +
                    "SET contractref = ?, description = ?, xclient_id = ?" + 
                    " WHERE xcontract_id = " + getXcontractId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getContractref());
                ps.setObject(2, getDescription());
                ps.setObject(3, getXclientId());
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
        if (Xorder.exists(getConnection(),"xcontract_id = " + getXcontractId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xorder_xcontract");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xcontractpage
            Xcontractpage[] records = (Xcontractpage[])Xcontractpage.load(getConnection(),"xcontract_id = " + getXcontractId(),null);
            for (int i = 0; i<records.length; i++) {
                Xcontractpage xcontractpage = records[i];
                xcontractpage.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xcontract " +
                "WHERE xcontract_id = " + getXcontractId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXcontractId(new Integer(-getXcontractId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXcontractId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xcontract_id,contractref,description,xclient_id FROM xcontract " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xcontract(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),new Integer(rs.getInt(4))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xcontract[] objects = new Xcontract[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xcontract xcontract = (Xcontract) lst.get(i);
            objects[i] = xcontract;
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
        String stmt = "SELECT xcontract_id FROM xcontract " +
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
    //    return getXcontractId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xcontractId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXcontractId(id);
        setNew(prevIsNew);
    }

    public Integer getXcontractId() {
        return xcontractId;
    }

    public void setXcontractId(Integer xcontractId) throws ForeignKeyViolationException {
        setWasChanged(this.xcontractId != null && this.xcontractId != xcontractId);
        this.xcontractId = xcontractId;
        setNew(xcontractId.intValue() == 0);
    }

    public String getContractref() {
        return contractref;
    }

    public void setContractref(String contractref) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contractref != null && !this.contractref.equals(contractref));
        this.contractref = contractref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }

    public Integer getXclientId() {
        return xclientId;
    }

    public void setXclientId(Integer xclientId) throws SQLException, ForeignKeyViolationException {
        if (xclientId!=null && !Xclient.exists(getConnection(),"xclient_id = " + xclientId)) {
            throw new ForeignKeyViolationException("Can't set xclient_id, foreign key violation: xcontract_xclient_fk");
        }
        setWasChanged(this.xclientId != null && !this.xclientId.equals(xclientId));
        this.xclientId = xclientId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getXcontractId();
        columnValues[1] = getContractref();
        columnValues[2] = getDescription();
        columnValues[3] = getXclientId();
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
            setXcontractId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXcontractId(null);
        }
        setContractref(flds[1]);
        setDescription(flds[2]);
        try {
            setXclientId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXclientId(null);
        }
    }
}
