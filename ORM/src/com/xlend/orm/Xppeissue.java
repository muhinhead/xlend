// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Mar 01 08:33:05 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xppeissue extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xppeissueId = null;
    private Date issuedate = null;
    private Integer issuedbyId = null;
    private Integer issuedtoId = null;
    private Integer authorizedbyId = null;

    public Xppeissue(Connection connection) {
        super(connection, "xppeissue", "xppeissue_id");
        setColumnNames(new String[]{"xppeissue_id", "issuedate", "issuedby_id", "issuedto_id", "authorizedby_id"});
    }

    public Xppeissue(Connection connection, Integer xppeissueId, Date issuedate, Integer issuedbyId, Integer issuedtoId, Integer authorizedbyId) {
        super(connection, "xppeissue", "xppeissue_id");
        setNew(xppeissueId.intValue() <= 0);
//        if (xppeissueId.intValue() != 0) {
            this.xppeissueId = xppeissueId;
//        }
        this.issuedate = issuedate;
        this.issuedbyId = issuedbyId;
        this.issuedtoId = issuedtoId;
        this.authorizedbyId = authorizedbyId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xppeissue xppeissue = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppeissue_id,issuedate,issuedby_id,issuedto_id,authorizedby_id FROM xppeissue WHERE xppeissue_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xppeissue = new Xppeissue(getConnection());
                xppeissue.setXppeissueId(new Integer(rs.getInt(1)));
                xppeissue.setIssuedate(rs.getDate(2));
                xppeissue.setIssuedbyId(new Integer(rs.getInt(3)));
                xppeissue.setIssuedtoId(new Integer(rs.getInt(4)));
                xppeissue.setAuthorizedbyId(new Integer(rs.getInt(5)));
                xppeissue.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xppeissue;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xppeissue ("+(getXppeissueId().intValue()!=0?"xppeissue_id,":"")+"issuedate,issuedby_id,issuedto_id,authorizedby_id) values("+(getXppeissueId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXppeissueId().intValue()!=0) {
                 ps.setObject(++n, getXppeissueId());
             }
             ps.setObject(++n, getIssuedate());
             ps.setObject(++n, getIssuedbyId());
             ps.setObject(++n, getIssuedtoId());
             ps.setObject(++n, getAuthorizedbyId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXppeissueId().intValue()==0) {
             stmt = "SELECT max(xppeissue_id) FROM xppeissue";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXppeissueId(new Integer(rs.getInt(1)));
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
                    "UPDATE xppeissue " +
                    "SET issuedate = ?, issuedby_id = ?, issuedto_id = ?, authorizedby_id = ?" + 
                    " WHERE xppeissue_id = " + getXppeissueId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getIssuedate());
                ps.setObject(2, getIssuedbyId());
                ps.setObject(3, getIssuedtoId());
                ps.setObject(4, getAuthorizedbyId());
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
        if (Xppeissueitem.exists(getConnection(),"xppeissue_id = " + getXppeissueId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xppeissueitem_xppeissue_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xppeissue " +
                "WHERE xppeissue_id = " + getXppeissueId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXppeissueId(new Integer(-getXppeissueId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXppeissueId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xppeissue_id,issuedate,issuedby_id,issuedto_id,authorizedby_id FROM xppeissue " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xppeissue(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xppeissue[] objects = new Xppeissue[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xppeissue xppeissue = (Xppeissue) lst.get(i);
            objects[i] = xppeissue;
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
        String stmt = "SELECT xppeissue_id FROM xppeissue " +
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
    //    return getXppeissueId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xppeissueId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXppeissueId(id);
        setNew(prevIsNew);
    }

    public Integer getXppeissueId() {
        return xppeissueId;
    }

    public void setXppeissueId(Integer xppeissueId) throws ForeignKeyViolationException {
        setWasChanged(this.xppeissueId != null && this.xppeissueId != xppeissueId);
        this.xppeissueId = xppeissueId;
        setNew(xppeissueId.intValue() == 0);
    }

    public Date getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Date issuedate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.issuedate != null && !this.issuedate.equals(issuedate));
        this.issuedate = issuedate;
    }

    public Integer getIssuedbyId() {
        return issuedbyId;
    }

    public void setIssuedbyId(Integer issuedbyId) throws SQLException, ForeignKeyViolationException {
        if (issuedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedbyId)) {
            throw new ForeignKeyViolationException("Can't set issuedby_id, foreign key violation: xppeissue_xemployee_fk");
        }
        setWasChanged(this.issuedbyId != null && !this.issuedbyId.equals(issuedbyId));
        this.issuedbyId = issuedbyId;
    }

    public Integer getIssuedtoId() {
        return issuedtoId;
    }

    public void setIssuedtoId(Integer issuedtoId) throws SQLException, ForeignKeyViolationException {
        if (issuedtoId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedtoId)) {
            throw new ForeignKeyViolationException("Can't set issuedto_id, foreign key violation: xppeissue_xemployee_fk2");
        }
        setWasChanged(this.issuedtoId != null && !this.issuedtoId.equals(issuedtoId));
        this.issuedtoId = issuedtoId;
    }

    public Integer getAuthorizedbyId() {
        return authorizedbyId;
    }

    public void setAuthorizedbyId(Integer authorizedbyId) throws SQLException, ForeignKeyViolationException {
        if (authorizedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + authorizedbyId)) {
            throw new ForeignKeyViolationException("Can't set authorizedby_id, foreign key violation: xppeissue_xemployee_fk3");
        }
        setWasChanged(this.authorizedbyId != null && !this.authorizedbyId.equals(authorizedbyId));
        this.authorizedbyId = authorizedbyId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[5];
        columnValues[0] = getXppeissueId();
        columnValues[1] = getIssuedate();
        columnValues[2] = getIssuedbyId();
        columnValues[3] = getIssuedtoId();
        columnValues[4] = getAuthorizedbyId();
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
            setXppeissueId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXppeissueId(null);
        }
        setIssuedate(toDate(flds[1]));
        try {
            setIssuedbyId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setIssuedbyId(null);
        }
        try {
            setIssuedtoId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setIssuedtoId(null);
        }
        try {
            setAuthorizedbyId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setAuthorizedbyId(null);
        }
    }
}
