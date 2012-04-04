// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Apr 01 20:09:32 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xsite extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xsiteId = null;
    private String name = null;
    private String description = null;
    private Integer dieselsponsor = null;
    private String sitetype = null;
    private Integer xorderId = null;

    public Xsite(Connection connection) {
        super(connection, "xsite", "xsite_id");
        setColumnNames(new String[]{"xsite_id", "name", "description", "dieselsponsor", "sitetype", "xorder_id"});
    }

    public Xsite(Connection connection, Integer xsiteId, String name, String description, Integer dieselsponsor, String sitetype, Integer xorderId) {
        super(connection, "xsite", "xsite_id");
        setNew(xsiteId.intValue() <= 0);
//        if (xsiteId.intValue() != 0) {
            this.xsiteId = xsiteId;
//        }
        this.name = name;
        this.description = description;
        this.dieselsponsor = dieselsponsor;
        this.sitetype = sitetype;
        this.xorderId = xorderId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xsite xsite = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsite_id,name,description,dieselsponsor,sitetype,xorder_id FROM xsite WHERE xsite_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xsite = new Xsite(getConnection());
                xsite.setXsiteId(new Integer(rs.getInt(1)));
                xsite.setName(rs.getString(2));
                xsite.setDescription(rs.getString(3));
                xsite.setDieselsponsor(new Integer(rs.getInt(4)));
                xsite.setSitetype(rs.getString(5));
                xsite.setXorderId(new Integer(rs.getInt(6)));
                xsite.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xsite;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xsite ("+(getXsiteId().intValue()!=0?"xsite_id,":"")+"name,description,dieselsponsor,sitetype,xorder_id) values("+(getXsiteId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXsiteId().intValue()!=0) {
                 ps.setObject(++n, getXsiteId());
             }
             ps.setObject(++n, getName());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getDieselsponsor());
             ps.setObject(++n, getSitetype());
             ps.setObject(++n, getXorderId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXsiteId().intValue()==0) {
             stmt = "SELECT max(xsite_id) FROM xsite";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXsiteId(new Integer(rs.getInt(1)));
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
                    "UPDATE xsite " +
                    "SET name = ?, description = ?, dieselsponsor = ?, sitetype = ?, xorder_id = ?" + 
                    " WHERE xsite_id = " + getXsiteId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getName());
                ps.setObject(2, getDescription());
                ps.setObject(3, getDieselsponsor());
                ps.setObject(4, getSitetype());
                ps.setObject(5, getXorderId());
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
        if (Xtrip.exists(getConnection(),"fromsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtrip_xsite_fk");
        }
        if (Xtrip.exists(getConnection(),"tosite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtrip_xsite_fk2");
        }
        if (Xmachineonsite.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xmachineonsite_xsite_fk");
        }
        if (Xissuing.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xissuing_xsite_fk");
        }
        if (Xtripexchange.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripexchange_xsite_fk");
        }
        if (Xtripsheetpart.exists(getConnection(),"tosite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripsheetpart_xsite_fk2");
        }
        if (Xtripmoving.exists(getConnection(),"fromsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripmoving_xsite_fk");
        }
        if (Xdieselcard.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdieselcard_xsite_fk");
        }
        if (Xfuel.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xfuel_xsite_fk");
        }
        if (Xtripsheetpart.exists(getConnection(),"fromsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripsheetpart_xsite_fk");
        }
        if (Xbreakdown.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbreakdown_xsite_fk");
        }
        if (Xtripestablish.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripestablish_xsite_fk");
        }
        if (Xsitediary.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xsitediary_xsite_fk");
        }
        if (Xtripmoving.exists(getConnection(),"tosite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripmoving_xsite_fk2");
        }
        if (Xtimesheet.exists(getConnection(),"xsite_id = " + getXsiteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtimesheet_xsite_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xabsenteeism
            Xabsenteeism[] records = (Xabsenteeism[])Xabsenteeism.load(getConnection(),"xsite_id = " + getXsiteId(),null);
            for (int i = 0; i<records.length; i++) {
                Xabsenteeism xabsenteeism = records[i];
                xabsenteeism.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xsite " +
                "WHERE xsite_id = " + getXsiteId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXsiteId(new Integer(-getXsiteId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXsiteId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsite_id,name,description,dieselsponsor,sitetype,xorder_id FROM xsite " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xsite(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),new Integer(rs.getInt(4)),rs.getString(5),new Integer(rs.getInt(6))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xsite[] objects = new Xsite[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xsite xsite = (Xsite) lst.get(i);
            objects[i] = xsite;
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
        String stmt = "SELECT xsite_id FROM xsite " +
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
    //    return getXsiteId() + getDelimiter();
    //}

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws ForeignKeyViolationException {
        setWasChanged(this.xsiteId != null && this.xsiteId != xsiteId);
        this.xsiteId = xsiteId;
        setNew(xsiteId.intValue() == 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.name != null && !this.name.equals(name));
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }

    public Integer getDieselsponsor() {
        return dieselsponsor;
    }

    public void setDieselsponsor(Integer dieselsponsor) throws SQLException, ForeignKeyViolationException {
        if (null != dieselsponsor)
            dieselsponsor = dieselsponsor == 0 ? null : dieselsponsor;
        setWasChanged(this.dieselsponsor != null && !this.dieselsponsor.equals(dieselsponsor));
        this.dieselsponsor = dieselsponsor;
    }

    public String getSitetype() {
        return sitetype;
    }

    public void setSitetype(String sitetype) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.sitetype != null && !this.sitetype.equals(sitetype));
        this.sitetype = sitetype;
    }

    public Integer getXorderId() {
        return xorderId;
    }

    public void setXorderId(Integer xorderId) throws SQLException, ForeignKeyViolationException {
        if (null != xorderId)
            xorderId = xorderId == 0 ? null : xorderId;
        if (xorderId!=null && !Xorder.exists(getConnection(),"xorder_id = " + xorderId)) {
            throw new ForeignKeyViolationException("Can't set xorder_id, foreign key violation: xsite_xorder_fk");
        }
        setWasChanged(this.xorderId != null && !this.xorderId.equals(xorderId));
        this.xorderId = xorderId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXsiteId();
        columnValues[1] = getName();
        columnValues[2] = getDescription();
        columnValues[3] = getDieselsponsor();
        columnValues[4] = getSitetype();
        columnValues[5] = getXorderId();
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
            setXsiteId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        setName(flds[1]);
        setDescription(flds[2]);
        try {
            setDieselsponsor(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setDieselsponsor(null);
        }
        setSitetype(flds[4]);
        try {
            setXorderId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setXorderId(null);
        }
    }
}
