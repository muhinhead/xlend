// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Aug 04 17:19:28 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xsitediarypart extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xsitediarypartId = null;
    private Integer xsitediaryId = null;
    private Date partdate = null;
    private Integer xmachineId = null;
    private Integer hrsWorked = null;
    private Integer hrsStanding = null;
    private Integer operatorId = null;
    private String comments = null;

    public Xsitediarypart(Connection connection) {
        super(connection, "xsitediarypart", "xsitediarypart_id");
        setColumnNames(new String[]{"xsitediarypart_id", "xsitediary_id", "partdate", "xmachine_id", "hrs_worked", "hrs_standing", "operator_id", "comments"});
    }

    public Xsitediarypart(Connection connection, Integer xsitediarypartId, Integer xsitediaryId, Date partdate, Integer xmachineId, Integer hrsWorked, Integer hrsStanding, Integer operatorId, String comments) {
        super(connection, "xsitediarypart", "xsitediarypart_id");
        setNew(xsitediarypartId.intValue() <= 0);
//        if (xsitediarypartId.intValue() != 0) {
            this.xsitediarypartId = xsitediarypartId;
//        }
        this.xsitediaryId = xsitediaryId;
        this.partdate = partdate;
        this.xmachineId = xmachineId;
        this.hrsWorked = hrsWorked;
        this.hrsStanding = hrsStanding;
        this.operatorId = operatorId;
        this.comments = comments;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xsitediarypart xsitediarypart = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsitediarypart_id,xsitediary_id,partdate,xmachine_id,hrs_worked,hrs_standing,operator_id,comments FROM xsitediarypart WHERE xsitediarypart_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xsitediarypart = new Xsitediarypart(getConnection());
                xsitediarypart.setXsitediarypartId(new Integer(rs.getInt(1)));
                xsitediarypart.setXsitediaryId(new Integer(rs.getInt(2)));
                xsitediarypart.setPartdate(rs.getDate(3));
                xsitediarypart.setXmachineId(new Integer(rs.getInt(4)));
                xsitediarypart.setHrsWorked(new Integer(rs.getInt(5)));
                xsitediarypart.setHrsStanding(new Integer(rs.getInt(6)));
                xsitediarypart.setOperatorId(new Integer(rs.getInt(7)));
                xsitediarypart.setComments(rs.getString(8));
                xsitediarypart.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xsitediarypart;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xsitediarypart ("+(getXsitediarypartId().intValue()!=0?"xsitediarypart_id,":"")+"xsitediary_id,partdate,xmachine_id,hrs_worked,hrs_standing,operator_id,comments) values("+(getXsitediarypartId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXsitediarypartId().intValue()!=0) {
                 ps.setObject(++n, getXsitediarypartId());
             }
             ps.setObject(++n, getXsitediaryId());
             ps.setObject(++n, getPartdate());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getHrsWorked());
             ps.setObject(++n, getHrsStanding());
             ps.setObject(++n, getOperatorId());
             ps.setObject(++n, getComments());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXsitediarypartId().intValue()==0) {
             stmt = "SELECT max(xsitediarypart_id) FROM xsitediarypart";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXsitediarypartId(new Integer(rs.getInt(1)));
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
                    "UPDATE xsitediarypart " +
                    "SET xsitediary_id = ?, partdate = ?, xmachine_id = ?, hrs_worked = ?, hrs_standing = ?, operator_id = ?, comments = ?" + 
                    " WHERE xsitediarypart_id = " + getXsitediarypartId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsitediaryId());
                ps.setObject(2, getPartdate());
                ps.setObject(3, getXmachineId());
                ps.setObject(4, getHrsWorked());
                ps.setObject(5, getHrsStanding());
                ps.setObject(6, getOperatorId());
                ps.setObject(7, getComments());
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
                "DELETE FROM xsitediarypart " +
                "WHERE xsitediarypart_id = " + getXsitediarypartId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXsitediarypartId(new Integer(-getXsitediarypartId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXsitediarypartId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsitediarypart_id,xsitediary_id,partdate,xmachine_id,hrs_worked,hrs_standing,operator_id,comments FROM xsitediarypart " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xsitediarypart(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),rs.getString(8)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xsitediarypart[] objects = new Xsitediarypart[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xsitediarypart xsitediarypart = (Xsitediarypart) lst.get(i);
            objects[i] = xsitediarypart;
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
        String stmt = "SELECT xsitediarypart_id FROM xsitediarypart " +
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
    //    return getXsitediarypartId() + getDelimiter();
    //}

    public Integer getXsitediarypartId() {
        return xsitediarypartId;
    }

    public void setXsitediarypartId(Integer xsitediarypartId) throws ForeignKeyViolationException {
        setWasChanged(this.xsitediarypartId != null && this.xsitediarypartId != xsitediarypartId);
        this.xsitediarypartId = xsitediarypartId;
        setNew(xsitediarypartId.intValue() == 0);
    }

    public Integer getXsitediaryId() {
        return xsitediaryId;
    }

    public void setXsitediaryId(Integer xsitediaryId) throws SQLException, ForeignKeyViolationException {
        if (xsitediaryId!=null && !Xsitediary.exists(getConnection(),"xsitediary_id = " + xsitediaryId)) {
            throw new ForeignKeyViolationException("Can't set xsitediary_id, foreign key violation: xsitediarypart_xsitediary_fk");
        }
        setWasChanged(this.xsitediaryId != null && !this.xsitediaryId.equals(xsitediaryId));
        this.xsitediaryId = xsitediaryId;
    }

    public Date getPartdate() {
        return partdate;
    }

    public void setPartdate(Date partdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.partdate != null && !this.partdate.equals(partdate));
        this.partdate = partdate;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xsitediarypart_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getHrsWorked() {
        return hrsWorked;
    }

    public void setHrsWorked(Integer hrsWorked) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.hrsWorked != null && !this.hrsWorked.equals(hrsWorked));
        this.hrsWorked = hrsWorked;
    }

    public Integer getHrsStanding() {
        return hrsStanding;
    }

    public void setHrsStanding(Integer hrsStanding) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.hrsStanding != null && !this.hrsStanding.equals(hrsStanding));
        this.hrsStanding = hrsStanding;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) throws SQLException, ForeignKeyViolationException {
        if (operatorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + operatorId)) {
            throw new ForeignKeyViolationException("Can't set operator_id, foreign key violation: xsitediarypart_xemployee_fk");
        }
        setWasChanged(this.operatorId != null && !this.operatorId.equals(operatorId));
        this.operatorId = operatorId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.comments != null && !this.comments.equals(comments));
        this.comments = comments;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getXsitediarypartId();
        columnValues[1] = getXsitediaryId();
        columnValues[2] = getPartdate();
        columnValues[3] = getXmachineId();
        columnValues[4] = getHrsWorked();
        columnValues[5] = getHrsStanding();
        columnValues[6] = getOperatorId();
        columnValues[7] = getComments();
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
            setXsitediarypartId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXsitediarypartId(null);
        }
        try {
            setXsitediaryId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsitediaryId(null);
        }
        setPartdate(toDate(flds[2]));
        try {
            setXmachineId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setHrsWorked(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setHrsWorked(null);
        }
        try {
            setHrsStanding(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setHrsStanding(null);
        }
        try {
            setOperatorId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setOperatorId(null);
        }
        setComments(flds[7]);
    }
}
