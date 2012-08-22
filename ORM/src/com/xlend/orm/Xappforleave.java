// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Wed Aug 22 15:53:21 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xappforleave extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xappforleaveId = null;
    private Date appdate = null;
    private Integer applicantId = null;
    private Integer isSickleave = null;
    private Integer isAnnualleave = null;
    private Integer isSpecialleave = null;
    private Integer isUnpaidleave = null;
    private Date fromdate = null;
    private Date todate = null;
    private Integer days = null;
    private Integer isSigned = null;
    private Integer isApproved = null;
    private Integer approvedbyId = null;
    private String remarks = null;

    public Xappforleave(Connection connection) {
        super(connection, "xappforleave", "xappforleave_id");
        setColumnNames(new String[]{"xappforleave_id", "appdate", "applicant_id", "is_sickleave", "is_annualleave", "is_specialleave", "is_unpaidleave", "fromdate", "todate", "days", "is_signed", "is_approved", "approvedby_id", "remarks"});
    }

    public Xappforleave(Connection connection, Integer xappforleaveId, Date appdate, Integer applicantId, Integer isSickleave, Integer isAnnualleave, Integer isSpecialleave, Integer isUnpaidleave, Date fromdate, Date todate, Integer days, Integer isSigned, Integer isApproved, Integer approvedbyId, String remarks) {
        super(connection, "xappforleave", "xappforleave_id");
        setNew(xappforleaveId.intValue() <= 0);
//        if (xappforleaveId.intValue() != 0) {
            this.xappforleaveId = xappforleaveId;
//        }
        this.appdate = appdate;
        this.applicantId = applicantId;
        this.isSickleave = isSickleave;
        this.isAnnualleave = isAnnualleave;
        this.isSpecialleave = isSpecialleave;
        this.isUnpaidleave = isUnpaidleave;
        this.fromdate = fromdate;
        this.todate = todate;
        this.days = days;
        this.isSigned = isSigned;
        this.isApproved = isApproved;
        this.approvedbyId = approvedbyId;
        this.remarks = remarks;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xappforleave xappforleave = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xappforleave_id,appdate,applicant_id,is_sickleave,is_annualleave,is_specialleave,is_unpaidleave,fromdate,todate,days,is_signed,is_approved,approvedby_id,remarks FROM xappforleave WHERE xappforleave_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xappforleave = new Xappforleave(getConnection());
                xappforleave.setXappforleaveId(new Integer(rs.getInt(1)));
                xappforleave.setAppdate(rs.getDate(2));
                xappforleave.setApplicantId(new Integer(rs.getInt(3)));
                xappforleave.setIsSickleave(new Integer(rs.getInt(4)));
                xappforleave.setIsAnnualleave(new Integer(rs.getInt(5)));
                xappforleave.setIsSpecialleave(new Integer(rs.getInt(6)));
                xappforleave.setIsUnpaidleave(new Integer(rs.getInt(7)));
                xappforleave.setFromdate(rs.getDate(8));
                xappforleave.setTodate(rs.getDate(9));
                xappforleave.setDays(new Integer(rs.getInt(10)));
                xappforleave.setIsSigned(new Integer(rs.getInt(11)));
                xappforleave.setIsApproved(new Integer(rs.getInt(12)));
                xappforleave.setApprovedbyId(new Integer(rs.getInt(13)));
                xappforleave.setRemarks(rs.getString(14));
                xappforleave.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xappforleave;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xappforleave ("+(getXappforleaveId().intValue()!=0?"xappforleave_id,":"")+"appdate,applicant_id,is_sickleave,is_annualleave,is_specialleave,is_unpaidleave,fromdate,todate,days,is_signed,is_approved,approvedby_id,remarks) values("+(getXappforleaveId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXappforleaveId().intValue()!=0) {
                 ps.setObject(++n, getXappforleaveId());
             }
             ps.setObject(++n, getAppdate());
             ps.setObject(++n, getApplicantId());
             ps.setObject(++n, getIsSickleave());
             ps.setObject(++n, getIsAnnualleave());
             ps.setObject(++n, getIsSpecialleave());
             ps.setObject(++n, getIsUnpaidleave());
             ps.setObject(++n, getFromdate());
             ps.setObject(++n, getTodate());
             ps.setObject(++n, getDays());
             ps.setObject(++n, getIsSigned());
             ps.setObject(++n, getIsApproved());
             ps.setObject(++n, getApprovedbyId());
             ps.setObject(++n, getRemarks());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXappforleaveId().intValue()==0) {
             stmt = "SELECT max(xappforleave_id) FROM xappforleave";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXappforleaveId(new Integer(rs.getInt(1)));
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
                    "UPDATE xappforleave " +
                    "SET appdate = ?, applicant_id = ?, is_sickleave = ?, is_annualleave = ?, is_specialleave = ?, is_unpaidleave = ?, fromdate = ?, todate = ?, days = ?, is_signed = ?, is_approved = ?, approvedby_id = ?, remarks = ?" + 
                    " WHERE xappforleave_id = " + getXappforleaveId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getAppdate());
                ps.setObject(2, getApplicantId());
                ps.setObject(3, getIsSickleave());
                ps.setObject(4, getIsAnnualleave());
                ps.setObject(5, getIsSpecialleave());
                ps.setObject(6, getIsUnpaidleave());
                ps.setObject(7, getFromdate());
                ps.setObject(8, getTodate());
                ps.setObject(9, getDays());
                ps.setObject(10, getIsSigned());
                ps.setObject(11, getIsApproved());
                ps.setObject(12, getApprovedbyId());
                ps.setObject(13, getRemarks());
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
                "DELETE FROM xappforleave " +
                "WHERE xappforleave_id = " + getXappforleaveId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXappforleaveId(new Integer(-getXappforleaveId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXappforleaveId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xappforleave_id,appdate,applicant_id,is_sickleave,is_annualleave,is_specialleave,is_unpaidleave,fromdate,todate,days,is_signed,is_approved,approvedby_id,remarks FROM xappforleave " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xappforleave(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),rs.getDate(8),rs.getDate(9),new Integer(rs.getInt(10)),new Integer(rs.getInt(11)),new Integer(rs.getInt(12)),new Integer(rs.getInt(13)),rs.getString(14)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xappforleave[] objects = new Xappforleave[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xappforleave xappforleave = (Xappforleave) lst.get(i);
            objects[i] = xappforleave;
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
        String stmt = "SELECT xappforleave_id FROM xappforleave " +
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
    //    return getXappforleaveId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xappforleaveId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXappforleaveId(id);
        setNew(prevIsNew);
    }

    public Integer getXappforleaveId() {
        return xappforleaveId;
    }

    public void setXappforleaveId(Integer xappforleaveId) throws ForeignKeyViolationException {
        setWasChanged(this.xappforleaveId != null && this.xappforleaveId != xappforleaveId);
        this.xappforleaveId = xappforleaveId;
        setNew(xappforleaveId.intValue() == 0);
    }

    public Date getAppdate() {
        return appdate;
    }

    public void setAppdate(Date appdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.appdate != null && !this.appdate.equals(appdate));
        this.appdate = appdate;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) throws SQLException, ForeignKeyViolationException {
        if (applicantId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + applicantId)) {
            throw new ForeignKeyViolationException("Can't set applicant_id, foreign key violation: xappforleave_xemployee_fk");
        }
        setWasChanged(this.applicantId != null && !this.applicantId.equals(applicantId));
        this.applicantId = applicantId;
    }

    public Integer getIsSickleave() {
        return isSickleave;
    }

    public void setIsSickleave(Integer isSickleave) throws SQLException, ForeignKeyViolationException {
        if (null != isSickleave)
            isSickleave = isSickleave == 0 ? null : isSickleave;
        setWasChanged(this.isSickleave != null && !this.isSickleave.equals(isSickleave));
        this.isSickleave = isSickleave;
    }

    public Integer getIsAnnualleave() {
        return isAnnualleave;
    }

    public void setIsAnnualleave(Integer isAnnualleave) throws SQLException, ForeignKeyViolationException {
        if (null != isAnnualleave)
            isAnnualleave = isAnnualleave == 0 ? null : isAnnualleave;
        setWasChanged(this.isAnnualleave != null && !this.isAnnualleave.equals(isAnnualleave));
        this.isAnnualleave = isAnnualleave;
    }

    public Integer getIsSpecialleave() {
        return isSpecialleave;
    }

    public void setIsSpecialleave(Integer isSpecialleave) throws SQLException, ForeignKeyViolationException {
        if (null != isSpecialleave)
            isSpecialleave = isSpecialleave == 0 ? null : isSpecialleave;
        setWasChanged(this.isSpecialleave != null && !this.isSpecialleave.equals(isSpecialleave));
        this.isSpecialleave = isSpecialleave;
    }

    public Integer getIsUnpaidleave() {
        return isUnpaidleave;
    }

    public void setIsUnpaidleave(Integer isUnpaidleave) throws SQLException, ForeignKeyViolationException {
        if (null != isUnpaidleave)
            isUnpaidleave = isUnpaidleave == 0 ? null : isUnpaidleave;
        setWasChanged(this.isUnpaidleave != null && !this.isUnpaidleave.equals(isUnpaidleave));
        this.isUnpaidleave = isUnpaidleave;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.fromdate != null && !this.fromdate.equals(fromdate));
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.todate != null && !this.todate.equals(todate));
        this.todate = todate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) throws SQLException, ForeignKeyViolationException {
        if (null != days)
            days = days == 0 ? null : days;
        setWasChanged(this.days != null && !this.days.equals(days));
        this.days = days;
    }

    public Integer getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(Integer isSigned) throws SQLException, ForeignKeyViolationException {
        if (null != isSigned)
            isSigned = isSigned == 0 ? null : isSigned;
        setWasChanged(this.isSigned != null && !this.isSigned.equals(isSigned));
        this.isSigned = isSigned;
    }

    public Integer getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Integer isApproved) throws SQLException, ForeignKeyViolationException {
        if (null != isApproved)
            isApproved = isApproved == 0 ? null : isApproved;
        setWasChanged(this.isApproved != null && !this.isApproved.equals(isApproved));
        this.isApproved = isApproved;
    }

    public Integer getApprovedbyId() {
        return approvedbyId;
    }

    public void setApprovedbyId(Integer approvedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != approvedbyId)
            approvedbyId = approvedbyId == 0 ? null : approvedbyId;
        if (approvedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + approvedbyId)) {
            throw new ForeignKeyViolationException("Can't set approvedby_id, foreign key violation: xappforleave_xemployee_fk2");
        }
        setWasChanged(this.approvedbyId != null && !this.approvedbyId.equals(approvedbyId));
        this.approvedbyId = approvedbyId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.remarks != null && !this.remarks.equals(remarks));
        this.remarks = remarks;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[14];
        columnValues[0] = getXappforleaveId();
        columnValues[1] = getAppdate();
        columnValues[2] = getApplicantId();
        columnValues[3] = getIsSickleave();
        columnValues[4] = getIsAnnualleave();
        columnValues[5] = getIsSpecialleave();
        columnValues[6] = getIsUnpaidleave();
        columnValues[7] = getFromdate();
        columnValues[8] = getTodate();
        columnValues[9] = getDays();
        columnValues[10] = getIsSigned();
        columnValues[11] = getIsApproved();
        columnValues[12] = getApprovedbyId();
        columnValues[13] = getRemarks();
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
            setXappforleaveId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXappforleaveId(null);
        }
        setAppdate(toDate(flds[1]));
        try {
            setApplicantId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setApplicantId(null);
        }
        try {
            setIsSickleave(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setIsSickleave(null);
        }
        try {
            setIsAnnualleave(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setIsAnnualleave(null);
        }
        try {
            setIsSpecialleave(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setIsSpecialleave(null);
        }
        try {
            setIsUnpaidleave(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setIsUnpaidleave(null);
        }
        setFromdate(toDate(flds[7]));
        setTodate(toDate(flds[8]));
        try {
            setDays(Integer.parseInt(flds[9]));
        } catch(NumberFormatException ne) {
            setDays(null);
        }
        try {
            setIsSigned(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setIsSigned(null);
        }
        try {
            setIsApproved(Integer.parseInt(flds[11]));
        } catch(NumberFormatException ne) {
            setIsApproved(null);
        }
        try {
            setApprovedbyId(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setApprovedbyId(null);
        }
        setRemarks(flds[13]);
    }
}
