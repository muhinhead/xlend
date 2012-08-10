// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Aug 10 08:49:50 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xclient extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xclientId = null;
    private String clientcode = null;
    private String companyname = null;
    private String contactname = null;
    private String phonenumber = null;
    private String vatnumber = null;
    private String address = null;
    private String description = null;

    public Xclient(Connection connection) {
        super(connection, "xclient", "xclient_id");
        setColumnNames(new String[]{"xclient_id", "clientcode", "companyname", "contactname", "phonenumber", "vatnumber", "address", "description"});
    }

    public Xclient(Connection connection, Integer xclientId, String clientcode, String companyname, String contactname, String phonenumber, String vatnumber, String address, String description) {
        super(connection, "xclient", "xclient_id");
        setNew(xclientId.intValue() <= 0);
//        if (xclientId.intValue() != 0) {
            this.xclientId = xclientId;
//        }
        this.clientcode = clientcode;
        this.companyname = companyname;
        this.contactname = contactname;
        this.phonenumber = phonenumber;
        this.vatnumber = vatnumber;
        this.address = address;
        this.description = description;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xclient xclient = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xclient_id,clientcode,companyname,contactname,phonenumber,vatnumber,address,description FROM xclient WHERE xclient_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xclient = new Xclient(getConnection());
                xclient.setXclientId(new Integer(rs.getInt(1)));
                xclient.setClientcode(rs.getString(2));
                xclient.setCompanyname(rs.getString(3));
                xclient.setContactname(rs.getString(4));
                xclient.setPhonenumber(rs.getString(5));
                xclient.setVatnumber(rs.getString(6));
                xclient.setAddress(rs.getString(7));
                xclient.setDescription(rs.getString(8));
                xclient.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xclient;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xclient ("+(getXclientId().intValue()!=0?"xclient_id,":"")+"clientcode,companyname,contactname,phonenumber,vatnumber,address,description) values("+(getXclientId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXclientId().intValue()!=0) {
                 ps.setObject(++n, getXclientId());
             }
             ps.setObject(++n, getClientcode());
             ps.setObject(++n, getCompanyname());
             ps.setObject(++n, getContactname());
             ps.setObject(++n, getPhonenumber());
             ps.setObject(++n, getVatnumber());
             ps.setObject(++n, getAddress());
             ps.setObject(++n, getDescription());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXclientId().intValue()==0) {
             stmt = "SELECT max(xclient_id) FROM xclient";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXclientId(new Integer(rs.getInt(1)));
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
                    "UPDATE xclient " +
                    "SET clientcode = ?, companyname = ?, contactname = ?, phonenumber = ?, vatnumber = ?, address = ?, description = ?" + 
                    " WHERE xclient_id = " + getXclientId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getClientcode());
                ps.setObject(2, getCompanyname());
                ps.setObject(3, getContactname());
                ps.setObject(4, getPhonenumber());
                ps.setObject(5, getVatnumber());
                ps.setObject(6, getAddress());
                ps.setObject(7, getDescription());
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
        if (Xquotation.exists(getConnection(),"xclient_id = " + getXclientId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xquotation_xclient_fk");
        }
        if (Xcontract.exists(getConnection(),"xclient_id = " + getXclientId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xcontract_xclient_fk");
        }
        if (Xorder.exists(getConnection(),"xclient_id = " + getXclientId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xorder_xclient_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xclient " +
                "WHERE xclient_id = " + getXclientId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXclientId(new Integer(-getXclientId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXclientId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xclient_id,clientcode,companyname,contactname,phonenumber,vatnumber,address,description FROM xclient " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xclient(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xclient[] objects = new Xclient[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xclient xclient = (Xclient) lst.get(i);
            objects[i] = xclient;
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
        String stmt = "SELECT xclient_id FROM xclient " +
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
    //    return getXclientId() + getDelimiter();
    //}

    public Integer getXclientId() {
        return xclientId;
    }

    public void setXclientId(Integer xclientId) throws ForeignKeyViolationException {
        setWasChanged(this.xclientId != null && this.xclientId != xclientId);
        this.xclientId = xclientId;
        setNew(xclientId.intValue() == 0);
    }

    public String getClientcode() {
        return clientcode;
    }

    public void setClientcode(String clientcode) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.clientcode != null && !this.clientcode.equals(clientcode));
        this.clientcode = clientcode;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.companyname != null && !this.companyname.equals(companyname));
        this.companyname = companyname;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contactname != null && !this.contactname.equals(contactname));
        this.contactname = contactname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.phonenumber != null && !this.phonenumber.equals(phonenumber));
        this.phonenumber = phonenumber;
    }

    public String getVatnumber() {
        return vatnumber;
    }

    public void setVatnumber(String vatnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.vatnumber != null && !this.vatnumber.equals(vatnumber));
        this.vatnumber = vatnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.address != null && !this.address.equals(address));
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getXclientId();
        columnValues[1] = getClientcode();
        columnValues[2] = getCompanyname();
        columnValues[3] = getContactname();
        columnValues[4] = getPhonenumber();
        columnValues[5] = getVatnumber();
        columnValues[6] = getAddress();
        columnValues[7] = getDescription();
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
            setXclientId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXclientId(null);
        }
        setClientcode(flds[1]);
        setCompanyname(flds[2]);
        setContactname(flds[3]);
        setPhonenumber(flds[4]);
        setVatnumber(flds[5]);
        setAddress(flds[6]);
        setDescription(flds[7]);
    }
}
