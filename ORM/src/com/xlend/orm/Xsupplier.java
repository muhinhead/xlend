// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Jan 28 18:10:38 EET 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xsupplier extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xsupplierId = null;
    private String companyname = null;
    private String contactperson = null;
    private String productdesc = null;
    private String phone = null;
    private String fax = null;
    private String cell = null;
    private String email = null;
    private String vatnr = null;
    private String companyRegnr = null;
    private String address = null;
    private String banking = null;

    public Xsupplier(Connection connection) {
        super(connection, "xsupplier", "xsupplier_id");
        setColumnNames(new String[]{"xsupplier_id", "companyname", "contactperson", "productdesc", "phone", "fax", "cell", "email", "vatnr", "company_regnr", "address", "banking"});
    }

    public Xsupplier(Connection connection, Integer xsupplierId, String companyname, String contactperson, String productdesc, String phone, String fax, String cell, String email, String vatnr, String companyRegnr, String address, String banking) {
        super(connection, "xsupplier", "xsupplier_id");
        setNew(xsupplierId.intValue() <= 0);
//        if (xsupplierId.intValue() != 0) {
            this.xsupplierId = xsupplierId;
//        }
        this.companyname = companyname;
        this.contactperson = contactperson;
        this.productdesc = productdesc;
        this.phone = phone;
        this.fax = fax;
        this.cell = cell;
        this.email = email;
        this.vatnr = vatnr;
        this.companyRegnr = companyRegnr;
        this.address = address;
        this.banking = banking;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xsupplier xsupplier = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsupplier_id,companyname,contactperson,productdesc,phone,fax,cell,email,vatnr,company_regnr,address,banking FROM xsupplier WHERE xsupplier_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xsupplier = new Xsupplier(getConnection());
                xsupplier.setXsupplierId(new Integer(rs.getInt(1)));
                xsupplier.setCompanyname(rs.getString(2));
                xsupplier.setContactperson(rs.getString(3));
                xsupplier.setProductdesc(rs.getString(4));
                xsupplier.setPhone(rs.getString(5));
                xsupplier.setFax(rs.getString(6));
                xsupplier.setCell(rs.getString(7));
                xsupplier.setEmail(rs.getString(8));
                xsupplier.setVatnr(rs.getString(9));
                xsupplier.setCompanyRegnr(rs.getString(10));
                xsupplier.setAddress(rs.getString(11));
                xsupplier.setBanking(rs.getString(12));
                xsupplier.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xsupplier;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xsupplier ("+(getXsupplierId().intValue()!=0?"xsupplier_id,":"")+"companyname,contactperson,productdesc,phone,fax,cell,email,vatnr,company_regnr,address,banking) values("+(getXsupplierId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXsupplierId().intValue()!=0) {
                 ps.setObject(++n, getXsupplierId());
             }
             ps.setObject(++n, getCompanyname());
             ps.setObject(++n, getContactperson());
             ps.setObject(++n, getProductdesc());
             ps.setObject(++n, getPhone());
             ps.setObject(++n, getFax());
             ps.setObject(++n, getCell());
             ps.setObject(++n, getEmail());
             ps.setObject(++n, getVatnr());
             ps.setObject(++n, getCompanyRegnr());
             ps.setObject(++n, getAddress());
             ps.setObject(++n, getBanking());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXsupplierId().intValue()==0) {
             stmt = "SELECT max(xsupplier_id) FROM xsupplier";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXsupplierId(new Integer(rs.getInt(1)));
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
                    "UPDATE xsupplier " +
                    "SET companyname = ?, contactperson = ?, productdesc = ?, phone = ?, fax = ?, cell = ?, email = ?, vatnr = ?, company_regnr = ?, address = ?, banking = ?" + 
                    " WHERE xsupplier_id = " + getXsupplierId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getCompanyname());
                ps.setObject(2, getContactperson());
                ps.setObject(3, getProductdesc());
                ps.setObject(4, getPhone());
                ps.setObject(5, getFax());
                ps.setObject(6, getCell());
                ps.setObject(7, getEmail());
                ps.setObject(8, getVatnr());
                ps.setObject(9, getCompanyRegnr());
                ps.setObject(10, getAddress());
                ps.setObject(11, getBanking());
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
        if (Xcreditor.exists(getConnection(),"xsupplier_id = " + getXsupplierId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xcreditor_xsupplier_fk");
        }
        if (Xconsume.exists(getConnection(),"xsupplier_id = " + getXsupplierId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xconsume_xsupplier_fk");
        }
        if (Xdieselpchs.exists(getConnection(),"xsupplier_id = " + getXsupplierId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdieselpchs_xsupplier_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xsupplier " +
                "WHERE xsupplier_id = " + getXsupplierId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXsupplierId(new Integer(-getXsupplierId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXsupplierId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsupplier_id,companyname,contactperson,productdesc,phone,fax,cell,email,vatnr,company_regnr,address,banking FROM xsupplier " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xsupplier(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xsupplier[] objects = new Xsupplier[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xsupplier xsupplier = (Xsupplier) lst.get(i);
            objects[i] = xsupplier;
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
        String stmt = "SELECT xsupplier_id FROM xsupplier " +
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
    //    return getXsupplierId() + getDelimiter();
    //}

    public Integer getXsupplierId() {
        return xsupplierId;
    }

    public void setXsupplierId(Integer xsupplierId) throws ForeignKeyViolationException {
        setWasChanged(this.xsupplierId != null && this.xsupplierId != xsupplierId);
        this.xsupplierId = xsupplierId;
        setNew(xsupplierId.intValue() == 0);
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.companyname != null && !this.companyname.equals(companyname));
        this.companyname = companyname;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contactperson != null && !this.contactperson.equals(contactperson));
        this.contactperson = contactperson;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.productdesc != null && !this.productdesc.equals(productdesc));
        this.productdesc = productdesc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.phone != null && !this.phone.equals(phone));
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.fax != null && !this.fax.equals(fax));
        this.fax = fax;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.cell != null && !this.cell.equals(cell));
        this.cell = cell;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.email != null && !this.email.equals(email));
        this.email = email;
    }

    public String getVatnr() {
        return vatnr;
    }

    public void setVatnr(String vatnr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.vatnr != null && !this.vatnr.equals(vatnr));
        this.vatnr = vatnr;
    }

    public String getCompanyRegnr() {
        return companyRegnr;
    }

    public void setCompanyRegnr(String companyRegnr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.companyRegnr != null && !this.companyRegnr.equals(companyRegnr));
        this.companyRegnr = companyRegnr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.address != null && !this.address.equals(address));
        this.address = address;
    }

    public String getBanking() {
        return banking;
    }

    public void setBanking(String banking) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.banking != null && !this.banking.equals(banking));
        this.banking = banking;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[12];
        columnValues[0] = getXsupplierId();
        columnValues[1] = getCompanyname();
        columnValues[2] = getContactperson();
        columnValues[3] = getProductdesc();
        columnValues[4] = getPhone();
        columnValues[5] = getFax();
        columnValues[6] = getCell();
        columnValues[7] = getEmail();
        columnValues[8] = getVatnr();
        columnValues[9] = getCompanyRegnr();
        columnValues[10] = getAddress();
        columnValues[11] = getBanking();
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
            setXsupplierId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXsupplierId(null);
        }
        setCompanyname(flds[1]);
        setContactperson(flds[2]);
        setProductdesc(flds[3]);
        setPhone(flds[4]);
        setFax(flds[5]);
        setCell(flds[6]);
        setEmail(flds[7]);
        setVatnr(flds[8]);
        setCompanyRegnr(flds[9]);
        setAddress(flds[10]);
        setBanking(flds[11]);
    }
}