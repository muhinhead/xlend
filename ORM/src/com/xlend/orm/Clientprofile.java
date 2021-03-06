// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 18:31:15 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Clientprofile extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer profileId = null;
    private Integer salespersonId = null;
    private Date birthday = null;
    private String sourceType = null;
    private String sourceDescr = null;
    private Integer salesPotential = null;

    public Clientprofile(Connection connection) {
        super(connection, "clientprofile", "profile_id");
        setColumnNames(new String[]{"profile_id", "salesperson_id", "birthday", "source_type", "source_descr", "sales_potential"});
    }

    public Clientprofile(Connection connection, Integer profileId, Integer salespersonId, Date birthday, String sourceType, String sourceDescr, Integer salesPotential) {
        super(connection, "clientprofile", "profile_id");
        setNew(profileId.intValue() <= 0);
//        if (profileId.intValue() != 0) {
            this.profileId = profileId;
//        }
        this.salespersonId = salespersonId;
        this.birthday = birthday;
        this.sourceType = sourceType;
        this.sourceDescr = sourceDescr;
        this.salesPotential = salesPotential;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Clientprofile clientprofile = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT profile_id,salesperson_id,birthday,source_type,source_descr,sales_potential FROM clientprofile WHERE profile_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                clientprofile = new Clientprofile(getConnection());
                clientprofile.setProfileId(new Integer(rs.getInt(1)));
                clientprofile.setSalespersonId(new Integer(rs.getInt(2)));
                clientprofile.setBirthday(rs.getDate(3));
                clientprofile.setSourceType(rs.getString(4));
                clientprofile.setSourceDescr(rs.getString(5));
                clientprofile.setSalesPotential(new Integer(rs.getInt(6)));
                clientprofile.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return clientprofile;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO clientprofile ("+(getProfileId().intValue()!=0?"profile_id,":"")+"salesperson_id,birthday,source_type,source_descr,sales_potential) values("+(getProfileId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getProfileId().intValue()!=0) {
                 ps.setObject(++n, getProfileId());
             }
             ps.setObject(++n, getSalespersonId());
             ps.setObject(++n, getBirthday());
             ps.setObject(++n, getSourceType());
             ps.setObject(++n, getSourceDescr());
             ps.setObject(++n, getSalesPotential());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getProfileId().intValue()==0) {
             stmt = "SELECT max(profile_id) FROM clientprofile";
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
                    "UPDATE clientprofile " +
                    "SET salesperson_id = ?, birthday = ?, source_type = ?, source_descr = ?, sales_potential = ?" + 
                    " WHERE profile_id = " + getProfileId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getSalespersonId());
                ps.setObject(2, getBirthday());
                ps.setObject(3, getSourceType());
                ps.setObject(4, getSourceDescr());
                ps.setObject(5, getSalesPotential());
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
                "DELETE FROM clientprofile " +
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
        String stmt = "SELECT profile_id,salesperson_id,birthday,source_type,source_descr,sales_potential FROM clientprofile " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Clientprofile(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),rs.getString(4),rs.getString(5),new Integer(rs.getInt(6))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Clientprofile[] objects = new Clientprofile[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Clientprofile clientprofile = (Clientprofile) lst.get(i);
            objects[i] = clientprofile;
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
        String stmt = "SELECT profile_id FROM clientprofile " +
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

    public Integer getPK_ID() {
        return profileId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setProfileId(id);
        setNew(prevIsNew);
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) throws ForeignKeyViolationException {
        setWasChanged(this.profileId != null && this.profileId != profileId);
        this.profileId = profileId;
        setNew(profileId.intValue() == 0);
    }

    public Integer getSalespersonId() {
        return salespersonId;
    }

    public void setSalespersonId(Integer salespersonId) throws SQLException, ForeignKeyViolationException {
        if (null != salespersonId)
            salespersonId = salespersonId == 0 ? null : salespersonId;
        if (salespersonId!=null && !Profile.exists(getConnection(),"profile_id = " + salespersonId)) {
            throw new ForeignKeyViolationException("Can't set salesperson_id, foreign key violation: clientprofile_profile_spers_fk");
        }
        setWasChanged(this.salespersonId != null && !this.salespersonId.equals(salespersonId));
        this.salespersonId = salespersonId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.birthday != null && !this.birthday.equals(birthday));
        this.birthday = birthday;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.sourceType != null && !this.sourceType.equals(sourceType));
        this.sourceType = sourceType;
    }

    public String getSourceDescr() {
        return sourceDescr;
    }

    public void setSourceDescr(String sourceDescr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.sourceDescr != null && !this.sourceDescr.equals(sourceDescr));
        this.sourceDescr = sourceDescr;
    }

    public Integer getSalesPotential() {
        return salesPotential;
    }

    public void setSalesPotential(Integer salesPotential) throws SQLException, ForeignKeyViolationException {
        if (null != salesPotential)
            salesPotential = salesPotential == 0 ? null : salesPotential;
        setWasChanged(this.salesPotential != null && !this.salesPotential.equals(salesPotential));
        this.salesPotential = salesPotential;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getProfileId();
        columnValues[1] = getSalespersonId();
        columnValues[2] = getBirthday();
        columnValues[3] = getSourceType();
        columnValues[4] = getSourceDescr();
        columnValues[5] = getSalesPotential();
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
        try {
            setSalespersonId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setSalespersonId(null);
        }
        setBirthday(toDate(flds[2]));
        setSourceType(flds[3]);
        setSourceDescr(flds[4]);
        try {
            setSalesPotential(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setSalesPotential(null);
        }
    }
}
