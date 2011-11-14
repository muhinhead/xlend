// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Nov 14 11:33:55 EET 2011
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Userprofile extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer profileId = null;
    private String fax = null;
    private String webaddress = null;
    private String officeHours = null;
    private Integer salesperson = null;
    private Integer manager = null;
    private String login = null;
    private String pwdmd5 = null;

    public Userprofile(Connection connection) {
        super(connection, "userprofile", "profile_id");
        setColumnNames(new String[]{"profile_id", "fax", "webaddress", "office_hours", "salesperson", "manager", "login", "pwdmd5"});
    }

    public Userprofile(Connection connection, Integer profileId, String fax, String webaddress, String officeHours, Integer salesperson, Integer manager, String login, String pwdmd5) {
        super(connection, "userprofile", "profile_id");
        setNew(profileId.intValue() <= 0);
//        if (profileId.intValue() != 0) {
            this.profileId = profileId;
//        }
        this.fax = fax;
        this.webaddress = webaddress;
        this.officeHours = officeHours;
        this.salesperson = salesperson;
        this.manager = manager;
        this.login = login;
        this.pwdmd5 = pwdmd5;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Userprofile userprofile = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT profile_id,fax,webaddress,office_hours,salesperson,manager,login,pwdmd5 FROM userprofile WHERE profile_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                userprofile = new Userprofile(getConnection());
                userprofile.setProfileId(new Integer(rs.getInt(1)));
                userprofile.setFax(rs.getString(2));
                userprofile.setWebaddress(rs.getString(3));
                userprofile.setOfficeHours(rs.getString(4));
                userprofile.setSalesperson(new Integer(rs.getInt(5)));
                userprofile.setManager(new Integer(rs.getInt(6)));
                userprofile.setLogin(rs.getString(7));
                userprofile.setPwdmd5(rs.getString(8));
                userprofile.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return userprofile;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO userprofile ("+(getProfileId().intValue()!=0?"profile_id,":"")+"fax,webaddress,office_hours,salesperson,manager,login,pwdmd5) values("+(getProfileId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getProfileId().intValue()!=0) {
                 ps.setObject(++n, getProfileId());
             }
             ps.setObject(++n, getFax());
             ps.setObject(++n, getWebaddress());
             ps.setObject(++n, getOfficeHours());
             ps.setObject(++n, getSalesperson());
             ps.setObject(++n, getManager());
             ps.setObject(++n, getLogin());
             ps.setObject(++n, getPwdmd5());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getProfileId().intValue()==0) {
             stmt = "SELECT max(profile_id) FROM userprofile";
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
                    "UPDATE userprofile " +
                    "SET fax = ?, webaddress = ?, office_hours = ?, salesperson = ?, manager = ?, login = ?, pwdmd5 = ?" + 
                    " WHERE profile_id = " + getProfileId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getFax());
                ps.setObject(2, getWebaddress());
                ps.setObject(3, getOfficeHours());
                ps.setObject(4, getSalesperson());
                ps.setObject(5, getManager());
                ps.setObject(6, getLogin());
                ps.setObject(7, getPwdmd5());
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
                "DELETE FROM userprofile " +
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
        String stmt = "SELECT profile_id,fax,webaddress,office_hours,salesperson,manager,login,pwdmd5 FROM userprofile " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Userprofile(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),rs.getString(7),rs.getString(8)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Userprofile[] objects = new Userprofile[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Userprofile userprofile = (Userprofile) lst.get(i);
            objects[i] = userprofile;
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
        String stmt = "SELECT profile_id FROM userprofile " +
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.fax != null && !this.fax.equals(fax));
        this.fax = fax;
    }

    public String getWebaddress() {
        return webaddress;
    }

    public void setWebaddress(String webaddress) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.webaddress != null && !this.webaddress.equals(webaddress));
        this.webaddress = webaddress;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.officeHours != null && !this.officeHours.equals(officeHours));
        this.officeHours = officeHours;
    }

    public Integer getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(Integer salesperson) throws SQLException, ForeignKeyViolationException {
        if (null != salesperson)
            salesperson = salesperson == 0 ? null : salesperson;
        setWasChanged(this.salesperson != null && !this.salesperson.equals(salesperson));
        this.salesperson = salesperson;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) throws SQLException, ForeignKeyViolationException {
        if (null != manager)
            manager = manager == 0 ? null : manager;
        setWasChanged(this.manager != null && !this.manager.equals(manager));
        this.manager = manager;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.login != null && !this.login.equals(login));
        this.login = login;
    }

    public String getPwdmd5() {
        return pwdmd5;
    }

    public void setPwdmd5(String pwdmd5) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.pwdmd5 != null && !this.pwdmd5.equals(pwdmd5));
        this.pwdmd5 = pwdmd5;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getProfileId();
        columnValues[1] = getFax();
        columnValues[2] = getWebaddress();
        columnValues[3] = getOfficeHours();
        columnValues[4] = getSalesperson();
        columnValues[5] = getManager();
        columnValues[6] = getLogin();
        columnValues[7] = getPwdmd5();
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
            setProfileId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setProfileId(null);
        }
        setFax(flds[1]);
        setWebaddress(flds[2]);
        setOfficeHours(flds[3]);
        try {
            setSalesperson(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setSalesperson(null);
        }
        try {
            setManager(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setManager(null);
        }
        setLogin(flds[6]);
        setPwdmd5(flds[7]);
    }
}
