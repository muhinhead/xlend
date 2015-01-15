// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jan 15 18:35:55 FET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xsitediary extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xsitediaryId = null;
    private Date diarydate = null;
    private Date weekend = null;
    private Integer xsiteId = null;
    private Integer managerId = null;
    private String siteForeman = null;
    private String siteNumber = null;

    public Xsitediary(Connection connection) {
        super(connection, "xsitediary", "xsitediary_id");
        setColumnNames(new String[]{"xsitediary_id", "diarydate", "weekend", "xsite_id", "manager_id", "site_foreman", "site_number"});
    }

    public Xsitediary(Connection connection, Integer xsitediaryId, Date diarydate, Date weekend, Integer xsiteId, Integer managerId, String siteForeman, String siteNumber) {
        super(connection, "xsitediary", "xsitediary_id");
        setNew(xsitediaryId.intValue() <= 0);
//        if (xsitediaryId.intValue() != 0) {
            this.xsitediaryId = xsitediaryId;
//        }
        this.diarydate = diarydate;
        this.weekend = weekend;
        this.xsiteId = xsiteId;
        this.managerId = managerId;
        this.siteForeman = siteForeman;
        this.siteNumber = siteNumber;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xsitediary xsitediary = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsitediary_id,diarydate,weekend,xsite_id,manager_id,site_foreman,site_number FROM xsitediary WHERE xsitediary_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xsitediary = new Xsitediary(getConnection());
                xsitediary.setXsitediaryId(new Integer(rs.getInt(1)));
                xsitediary.setDiarydate(rs.getDate(2));
                xsitediary.setWeekend(rs.getDate(3));
                xsitediary.setXsiteId(new Integer(rs.getInt(4)));
                xsitediary.setManagerId(new Integer(rs.getInt(5)));
                xsitediary.setSiteForeman(rs.getString(6));
                xsitediary.setSiteNumber(rs.getString(7));
                xsitediary.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xsitediary;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xsitediary ("+(getXsitediaryId().intValue()!=0?"xsitediary_id,":"")+"diarydate,weekend,xsite_id,manager_id,site_foreman,site_number) values("+(getXsitediaryId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXsitediaryId().intValue()!=0) {
                 ps.setObject(++n, getXsitediaryId());
             }
             ps.setObject(++n, getDiarydate());
             ps.setObject(++n, getWeekend());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getManagerId());
             ps.setObject(++n, getSiteForeman());
             ps.setObject(++n, getSiteNumber());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXsitediaryId().intValue()==0) {
             stmt = "SELECT max(xsitediary_id) FROM xsitediary";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXsitediaryId(new Integer(rs.getInt(1)));
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
                    "UPDATE xsitediary " +
                    "SET diarydate = ?, weekend = ?, xsite_id = ?, manager_id = ?, site_foreman = ?, site_number = ?" + 
                    " WHERE xsitediary_id = " + getXsitediaryId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getDiarydate());
                ps.setObject(2, getWeekend());
                ps.setObject(3, getXsiteId());
                ps.setObject(4, getManagerId());
                ps.setObject(5, getSiteForeman());
                ps.setObject(6, getSiteNumber());
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
        {// delete cascade from xsitediaryitem
            Xsitediaryitem[] records = (Xsitediaryitem[])Xsitediaryitem.load(getConnection(),"xsitediary_id = " + getXsitediaryId(),null);
            for (int i = 0; i<records.length; i++) {
                Xsitediaryitem xsitediaryitem = records[i];
                xsitediaryitem.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xsitediary " +
                "WHERE xsitediary_id = " + getXsitediaryId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXsitediaryId(new Integer(-getXsitediaryId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXsitediaryId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsitediary_id,diarydate,weekend,xsite_id,manager_id,site_foreman,site_number FROM xsitediary " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xsitediary(con,new Integer(rs.getInt(1)),rs.getDate(2),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),rs.getString(6),rs.getString(7)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xsitediary[] objects = new Xsitediary[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xsitediary xsitediary = (Xsitediary) lst.get(i);
            objects[i] = xsitediary;
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
        String stmt = "SELECT xsitediary_id FROM xsitediary " +
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
    //    return getXsitediaryId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xsitediaryId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXsitediaryId(id);
        setNew(prevIsNew);
    }

    public Integer getXsitediaryId() {
        return xsitediaryId;
    }

    public void setXsitediaryId(Integer xsitediaryId) throws ForeignKeyViolationException {
        setWasChanged(this.xsitediaryId != null && this.xsitediaryId != xsitediaryId);
        this.xsitediaryId = xsitediaryId;
        setNew(xsitediaryId.intValue() == 0);
    }

    public Date getDiarydate() {
        return diarydate;
    }

    public void setDiarydate(Date diarydate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.diarydate != null && !this.diarydate.equals(diarydate));
        this.diarydate = diarydate;
    }

    public Date getWeekend() {
        return weekend;
    }

    public void setWeekend(Date weekend) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.weekend != null && !this.weekend.equals(weekend));
        this.weekend = weekend;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xsitediary_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) throws SQLException, ForeignKeyViolationException {
        if (managerId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + managerId)) {
            throw new ForeignKeyViolationException("Can't set manager_id, foreign key violation: xsitediary_xemployee_fk");
        }
        setWasChanged(this.managerId != null && !this.managerId.equals(managerId));
        this.managerId = managerId;
    }

    public String getSiteForeman() {
        return siteForeman;
    }

    public void setSiteForeman(String siteForeman) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.siteForeman != null && !this.siteForeman.equals(siteForeman));
        this.siteForeman = siteForeman;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.siteNumber != null && !this.siteNumber.equals(siteNumber));
        this.siteNumber = siteNumber;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXsitediaryId();
        columnValues[1] = getDiarydate();
        columnValues[2] = getWeekend();
        columnValues[3] = getXsiteId();
        columnValues[4] = getManagerId();
        columnValues[5] = getSiteForeman();
        columnValues[6] = getSiteNumber();
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
            setXsitediaryId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXsitediaryId(null);
        }
        setDiarydate(toDate(flds[1]));
        setWeekend(toDate(flds[2]));
        try {
            setXsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setManagerId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setManagerId(null);
        }
        setSiteForeman(flds[5]);
        setSiteNumber(flds[6]);
    }
}
