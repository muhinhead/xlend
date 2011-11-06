// Generated by com.csa.orm.tools.dbgen.DbGenerator.class at Sun Nov 06 19:18:41 GMT 2011
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Profile extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer profileId = null;
    private String firstName = null;
    private String lastName = null;
    private String address1 = null;
    private String address2 = null;
    private String city = null;
    private String state = null;
    private String zipCode = null;
    private String phone = null;
    private String cellPhone = null;
    private String email = null;

    public Profile(Connection connection) {
        super(connection, "profile", "profile_id");
        setColumnNames(new String[]{"profile_id", "first_name", "last_name", "address1", "address2", "city", "state", "zip_code", "phone", "cell_phone", "email"});
    }

    public Profile(Connection connection, Integer profileId, String firstName, String lastName, String address1, String address2, String city, String state, String zipCode, String phone, String cellPhone, String email) {
        super(connection, "profile", "profile_id");
        setNew(profileId.intValue() <= 0);
//        if (profileId.intValue() != 0) {
            this.profileId = profileId;
//        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.email = email;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Profile profile = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT profile_id,first_name,last_name,address1,address2,city,state,zip_code,phone,cell_phone,email FROM profile WHERE profile_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                profile = new Profile(getConnection());
                profile.setProfileId(new Integer(rs.getInt(1)));
                profile.setFirstName(rs.getString(2));
                profile.setLastName(rs.getString(3));
                profile.setAddress1(rs.getString(4));
                profile.setAddress2(rs.getString(5));
                profile.setCity(rs.getString(6));
                profile.setState(rs.getString(7));
                profile.setZipCode(rs.getString(8));
                profile.setPhone(rs.getString(9));
                profile.setCellPhone(rs.getString(10));
                profile.setEmail(rs.getString(11));
                profile.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return profile;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO profile ("+(getProfileId().intValue()!=0?"profile_id,":"")+"first_name,last_name,address1,address2,city,state,zip_code,phone,cell_phone,email) values("+(getProfileId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getProfileId().intValue()!=0) {
                 ps.setObject(++n, getProfileId());
             }
             ps.setObject(++n, getFirstName());
             ps.setObject(++n, getLastName());
             ps.setObject(++n, getAddress1());
             ps.setObject(++n, getAddress2());
             ps.setObject(++n, getCity());
             ps.setObject(++n, getState());
             ps.setObject(++n, getZipCode());
             ps.setObject(++n, getPhone());
             ps.setObject(++n, getCellPhone());
             ps.setObject(++n, getEmail());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getProfileId().intValue()==0) {
             stmt = "SELECT max(profile_id) FROM profile";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setProfileId(new Integer(rs.getInt(1)));
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
                    "UPDATE profile " +
                    "SET first_name = ?, last_name = ?, address1 = ?, address2 = ?, city = ?, state = ?, zip_code = ?, phone = ?, cell_phone = ?, email = ?" + 
                    " WHERE profile_id = " + getProfileId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getFirstName());
                ps.setObject(2, getLastName());
                ps.setObject(3, getAddress1());
                ps.setObject(4, getAddress2());
                ps.setObject(5, getCity());
                ps.setObject(6, getState());
                ps.setObject(7, getZipCode());
                ps.setObject(8, getPhone());
                ps.setObject(9, getCellPhone());
                ps.setObject(10, getEmail());
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
        if (Clientprofile.exists(getConnection(),"salesperson_id = " + getProfileId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: clientprofile_profile_spers_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from userprofile
            Userprofile[] records = (Userprofile[])Userprofile.load(getConnection(),"profile_id = " + getProfileId(),null);
            for (int i = 0; i<records.length; i++) {
                Userprofile userprofile = records[i];
                userprofile.delete();
            }
        }
        {// delete cascade from clientprofile
            Clientprofile[] records = (Clientprofile[])Clientprofile.load(getConnection(),"profile_id = " + getProfileId(),null);
            for (int i = 0; i<records.length; i++) {
                Clientprofile clientprofile = records[i];
                clientprofile.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM profile " +
                "WHERE profile_id = " + getProfileId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setProfileId(new Integer(-getProfileId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getProfileId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT profile_id,first_name,last_name,address1,address2,city,state,zip_code,phone,cell_phone,email FROM profile " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Profile(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Profile[] objects = new Profile[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Profile profile = (Profile) lst.get(i);
            objects[i] = profile;
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
        String stmt = "SELECT profile_id FROM profile " +
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
    //    return getProfileId() + getDelimiter();
    //}

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) throws ForeignKeyViolationException {
        setWasChanged(this.profileId != null && this.profileId != profileId);
        this.profileId = profileId;
        setNew(profileId.intValue() == 0);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.firstName != null && !this.firstName.equals(firstName));
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.lastName != null && !this.lastName.equals(lastName));
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.address1 != null && !this.address1.equals(address1));
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.address2 != null && !this.address2.equals(address2));
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.city != null && !this.city.equals(city));
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.state != null && !this.state.equals(state));
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.zipCode != null && !this.zipCode.equals(zipCode));
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.phone != null && !this.phone.equals(phone));
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.cellPhone != null && !this.cellPhone.equals(cellPhone));
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.email != null && !this.email.equals(email));
        this.email = email;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[11];
        columnValues[0] = getProfileId();
        columnValues[1] = getFirstName();
        columnValues[2] = getLastName();
        columnValues[3] = getAddress1();
        columnValues[4] = getAddress2();
        columnValues[5] = getCity();
        columnValues[6] = getState();
        columnValues[7] = getZipCode();
        columnValues[8] = getPhone();
        columnValues[9] = getCellPhone();
        columnValues[10] = getEmail();
        return columnValues;
    }

    public static void setTriggers(Triggers triggers) {
        activeTriggers = triggers;
    }

    public static Triggers getTriggers() {
        return activeTriggers;
    }

    @Override
    public void fillFromString(String row) throws ForeignKeyViolationException, SQLException {
        String[] flds = splitStr(row, delimiter);
        try {
            setProfileId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setProfileId(0);
        }
        setFirstName(flds[1]);
        setLastName(flds[2]);
        setAddress1(flds[3]);
        setAddress2(flds[4]);
        setCity(flds[5]);
        setState(flds[6]);
        setZipCode(flds[7]);
        setPhone(flds[8]);
        setCellPhone(flds[9]);
        setEmail(flds[10]);
    }
}
