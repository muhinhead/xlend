// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jan 30 18:31:19 FET 2014
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xabsenteeism extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xabsenteeismId = null;
    private Integer xemployeeId = null;
    private Date absentdate = null;
    private Integer xsiteId = null;
    private Integer xmachineId = null;
    private Integer standing = null;
    private Integer reportedbyId = null;
    private Integer reportedtoId = null;
    private Integer notcommunicated = null;
    private Integer permgranted = null;
    private Integer grantedbyId = null;
    private String reason = null;
    private Integer medicalCond = null;
    private Integer funeral = null;
    private Integer familyProblem = null;
    private Integer inJail = null;
    private Integer pdpExpired = null;
    private Integer licenseProblem = null;
    private Integer ppeSafety = null;
    private Integer wageDispute = null;
    private Integer drunkOnSite = null;
    private Integer workAccident = null;
    private Integer noReason = null;
    private Integer medicalCert = null;
    private Integer deathCert = null;

    public Xabsenteeism(Connection connection) {
        super(connection, "xabsenteeism", "xabsenteeism_id");
        setColumnNames(new String[]{"xabsenteeism_id", "xemployee_id", "absentdate", "xsite_id", "xmachine_id", "standing", "reportedby_id", "reportedto_id", "notcommunicated", "permgranted", "grantedby_id", "reason", "medical_cond", "funeral", "family_problem", "in_jail", "pdp_expired", "license_problem", "ppe_safety", "wage_dispute", "drunk_on_site", "work_accident", "no_reason", "medical_cert", "death_cert"});
    }

    public Xabsenteeism(Connection connection, Integer xabsenteeismId, Integer xemployeeId, Date absentdate, Integer xsiteId, Integer xmachineId, Integer standing, Integer reportedbyId, Integer reportedtoId, Integer notcommunicated, Integer permgranted, Integer grantedbyId, String reason, Integer medicalCond, Integer funeral, Integer familyProblem, Integer inJail, Integer pdpExpired, Integer licenseProblem, Integer ppeSafety, Integer wageDispute, Integer drunkOnSite, Integer workAccident, Integer noReason, Integer medicalCert, Integer deathCert) {
        super(connection, "xabsenteeism", "xabsenteeism_id");
        setNew(xabsenteeismId.intValue() <= 0);
//        if (xabsenteeismId.intValue() != 0) {
            this.xabsenteeismId = xabsenteeismId;
//        }
        this.xemployeeId = xemployeeId;
        this.absentdate = absentdate;
        this.xsiteId = xsiteId;
        this.xmachineId = xmachineId;
        this.standing = standing;
        this.reportedbyId = reportedbyId;
        this.reportedtoId = reportedtoId;
        this.notcommunicated = notcommunicated;
        this.permgranted = permgranted;
        this.grantedbyId = grantedbyId;
        this.reason = reason;
        this.medicalCond = medicalCond;
        this.funeral = funeral;
        this.familyProblem = familyProblem;
        this.inJail = inJail;
        this.pdpExpired = pdpExpired;
        this.licenseProblem = licenseProblem;
        this.ppeSafety = ppeSafety;
        this.wageDispute = wageDispute;
        this.drunkOnSite = drunkOnSite;
        this.workAccident = workAccident;
        this.noReason = noReason;
        this.medicalCert = medicalCert;
        this.deathCert = deathCert;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xabsenteeism xabsenteeism = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xabsenteeism_id,xemployee_id,absentdate,xsite_id,xmachine_id,standing,reportedby_id,reportedto_id,notcommunicated,permgranted,grantedby_id,reason,medical_cond,funeral,family_problem,in_jail,pdp_expired,license_problem,ppe_safety,wage_dispute,drunk_on_site,work_accident,no_reason,medical_cert,death_cert FROM xabsenteeism WHERE xabsenteeism_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xabsenteeism = new Xabsenteeism(getConnection());
                xabsenteeism.setXabsenteeismId(new Integer(rs.getInt(1)));
                xabsenteeism.setXemployeeId(new Integer(rs.getInt(2)));
                xabsenteeism.setAbsentdate(rs.getDate(3));
                xabsenteeism.setXsiteId(new Integer(rs.getInt(4)));
                xabsenteeism.setXmachineId(new Integer(rs.getInt(5)));
                xabsenteeism.setStanding(new Integer(rs.getInt(6)));
                xabsenteeism.setReportedbyId(new Integer(rs.getInt(7)));
                xabsenteeism.setReportedtoId(new Integer(rs.getInt(8)));
                xabsenteeism.setNotcommunicated(new Integer(rs.getInt(9)));
                xabsenteeism.setPermgranted(new Integer(rs.getInt(10)));
                xabsenteeism.setGrantedbyId(new Integer(rs.getInt(11)));
                xabsenteeism.setReason(rs.getString(12));
                xabsenteeism.setMedicalCond(new Integer(rs.getInt(13)));
                xabsenteeism.setFuneral(new Integer(rs.getInt(14)));
                xabsenteeism.setFamilyProblem(new Integer(rs.getInt(15)));
                xabsenteeism.setInJail(new Integer(rs.getInt(16)));
                xabsenteeism.setPdpExpired(new Integer(rs.getInt(17)));
                xabsenteeism.setLicenseProblem(new Integer(rs.getInt(18)));
                xabsenteeism.setPpeSafety(new Integer(rs.getInt(19)));
                xabsenteeism.setWageDispute(new Integer(rs.getInt(20)));
                xabsenteeism.setDrunkOnSite(new Integer(rs.getInt(21)));
                xabsenteeism.setWorkAccident(new Integer(rs.getInt(22)));
                xabsenteeism.setNoReason(new Integer(rs.getInt(23)));
                xabsenteeism.setMedicalCert(new Integer(rs.getInt(24)));
                xabsenteeism.setDeathCert(new Integer(rs.getInt(25)));
                xabsenteeism.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xabsenteeism;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xabsenteeism ("+(getXabsenteeismId().intValue()!=0?"xabsenteeism_id,":"")+"xemployee_id,absentdate,xsite_id,xmachine_id,standing,reportedby_id,reportedto_id,notcommunicated,permgranted,grantedby_id,reason,medical_cond,funeral,family_problem,in_jail,pdp_expired,license_problem,ppe_safety,wage_dispute,drunk_on_site,work_accident,no_reason,medical_cert,death_cert) values("+(getXabsenteeismId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXabsenteeismId().intValue()!=0) {
                 ps.setObject(++n, getXabsenteeismId());
             }
             ps.setObject(++n, getXemployeeId());
             ps.setObject(++n, getAbsentdate());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getStanding());
             ps.setObject(++n, getReportedbyId());
             ps.setObject(++n, getReportedtoId());
             ps.setObject(++n, getNotcommunicated());
             ps.setObject(++n, getPermgranted());
             ps.setObject(++n, getGrantedbyId());
             ps.setObject(++n, getReason());
             ps.setObject(++n, getMedicalCond());
             ps.setObject(++n, getFuneral());
             ps.setObject(++n, getFamilyProblem());
             ps.setObject(++n, getInJail());
             ps.setObject(++n, getPdpExpired());
             ps.setObject(++n, getLicenseProblem());
             ps.setObject(++n, getPpeSafety());
             ps.setObject(++n, getWageDispute());
             ps.setObject(++n, getDrunkOnSite());
             ps.setObject(++n, getWorkAccident());
             ps.setObject(++n, getNoReason());
             ps.setObject(++n, getMedicalCert());
             ps.setObject(++n, getDeathCert());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXabsenteeismId().intValue()==0) {
             stmt = "SELECT max(xabsenteeism_id) FROM xabsenteeism";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXabsenteeismId(new Integer(rs.getInt(1)));
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
                    "UPDATE xabsenteeism " +
                    "SET xemployee_id = ?, absentdate = ?, xsite_id = ?, xmachine_id = ?, standing = ?, reportedby_id = ?, reportedto_id = ?, notcommunicated = ?, permgranted = ?, grantedby_id = ?, reason = ?, medical_cond = ?, funeral = ?, family_problem = ?, in_jail = ?, pdp_expired = ?, license_problem = ?, ppe_safety = ?, wage_dispute = ?, drunk_on_site = ?, work_accident = ?, no_reason = ?, medical_cert = ?, death_cert = ?" + 
                    " WHERE xabsenteeism_id = " + getXabsenteeismId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXemployeeId());
                ps.setObject(2, getAbsentdate());
                ps.setObject(3, getXsiteId());
                ps.setObject(4, getXmachineId());
                ps.setObject(5, getStanding());
                ps.setObject(6, getReportedbyId());
                ps.setObject(7, getReportedtoId());
                ps.setObject(8, getNotcommunicated());
                ps.setObject(9, getPermgranted());
                ps.setObject(10, getGrantedbyId());
                ps.setObject(11, getReason());
                ps.setObject(12, getMedicalCond());
                ps.setObject(13, getFuneral());
                ps.setObject(14, getFamilyProblem());
                ps.setObject(15, getInJail());
                ps.setObject(16, getPdpExpired());
                ps.setObject(17, getLicenseProblem());
                ps.setObject(18, getPpeSafety());
                ps.setObject(19, getWageDispute());
                ps.setObject(20, getDrunkOnSite());
                ps.setObject(21, getWorkAccident());
                ps.setObject(22, getNoReason());
                ps.setObject(23, getMedicalCert());
                ps.setObject(24, getDeathCert());
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
                "DELETE FROM xabsenteeism " +
                "WHERE xabsenteeism_id = " + getXabsenteeismId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXabsenteeismId(new Integer(-getXabsenteeismId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXabsenteeismId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xabsenteeism_id,xemployee_id,absentdate,xsite_id,xmachine_id,standing,reportedby_id,reportedto_id,notcommunicated,permgranted,grantedby_id,reason,medical_cond,funeral,family_problem,in_jail,pdp_expired,license_problem,ppe_safety,wage_dispute,drunk_on_site,work_accident,no_reason,medical_cert,death_cert FROM xabsenteeism " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xabsenteeism(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),new Integer(rs.getInt(9)),new Integer(rs.getInt(10)),new Integer(rs.getInt(11)),rs.getString(12),new Integer(rs.getInt(13)),new Integer(rs.getInt(14)),new Integer(rs.getInt(15)),new Integer(rs.getInt(16)),new Integer(rs.getInt(17)),new Integer(rs.getInt(18)),new Integer(rs.getInt(19)),new Integer(rs.getInt(20)),new Integer(rs.getInt(21)),new Integer(rs.getInt(22)),new Integer(rs.getInt(23)),new Integer(rs.getInt(24)),new Integer(rs.getInt(25))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xabsenteeism[] objects = new Xabsenteeism[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xabsenteeism xabsenteeism = (Xabsenteeism) lst.get(i);
            objects[i] = xabsenteeism;
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
        String stmt = "SELECT xabsenteeism_id FROM xabsenteeism " +
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
    //    return getXabsenteeismId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xabsenteeismId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXabsenteeismId(id);
        setNew(prevIsNew);
    }

    public Integer getXabsenteeismId() {
        return xabsenteeismId;
    }

    public void setXabsenteeismId(Integer xabsenteeismId) throws ForeignKeyViolationException {
        setWasChanged(this.xabsenteeismId != null && this.xabsenteeismId != xabsenteeismId);
        this.xabsenteeismId = xabsenteeismId;
        setNew(xabsenteeismId.intValue() == 0);
    }

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws SQLException, ForeignKeyViolationException {
        if (xemployeeId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + xemployeeId)) {
            throw new ForeignKeyViolationException("Can't set xemployee_id, foreign key violation: xabsenteeism_xemployee_fk");
        }
        setWasChanged(this.xemployeeId != null && !this.xemployeeId.equals(xemployeeId));
        this.xemployeeId = xemployeeId;
    }

    public Date getAbsentdate() {
        return absentdate;
    }

    public void setAbsentdate(Date absentdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.absentdate != null && !this.absentdate.equals(absentdate));
        this.absentdate = absentdate;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xabsenteeism_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (null != xmachineId)
            xmachineId = xmachineId == 0 ? null : xmachineId;
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xabsenteeism_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getStanding() {
        return standing;
    }

    public void setStanding(Integer standing) throws SQLException, ForeignKeyViolationException {
        if (null != standing)
            standing = standing == 0 ? null : standing;
        setWasChanged(this.standing != null && !this.standing.equals(standing));
        this.standing = standing;
    }

    public Integer getReportedbyId() {
        return reportedbyId;
    }

    public void setReportedbyId(Integer reportedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != reportedbyId)
            reportedbyId = reportedbyId == 0 ? null : reportedbyId;
        if (reportedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + reportedbyId)) {
            throw new ForeignKeyViolationException("Can't set reportedby_id, foreign key violation: xabsenteeism_xemployee_fk2");
        }
        setWasChanged(this.reportedbyId != null && !this.reportedbyId.equals(reportedbyId));
        this.reportedbyId = reportedbyId;
    }

    public Integer getReportedtoId() {
        return reportedtoId;
    }

    public void setReportedtoId(Integer reportedtoId) throws SQLException, ForeignKeyViolationException {
        if (null != reportedtoId)
            reportedtoId = reportedtoId == 0 ? null : reportedtoId;
        if (reportedtoId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + reportedtoId)) {
            throw new ForeignKeyViolationException("Can't set reportedto_id, foreign key violation: xabsenteeism_xemployee_fk3");
        }
        setWasChanged(this.reportedtoId != null && !this.reportedtoId.equals(reportedtoId));
        this.reportedtoId = reportedtoId;
    }

    public Integer getNotcommunicated() {
        return notcommunicated;
    }

    public void setNotcommunicated(Integer notcommunicated) throws SQLException, ForeignKeyViolationException {
        if (null != notcommunicated)
            notcommunicated = notcommunicated == 0 ? null : notcommunicated;
        setWasChanged(this.notcommunicated != null && !this.notcommunicated.equals(notcommunicated));
        this.notcommunicated = notcommunicated;
    }

    public Integer getPermgranted() {
        return permgranted;
    }

    public void setPermgranted(Integer permgranted) throws SQLException, ForeignKeyViolationException {
        if (null != permgranted)
            permgranted = permgranted == 0 ? null : permgranted;
        setWasChanged(this.permgranted != null && !this.permgranted.equals(permgranted));
        this.permgranted = permgranted;
    }

    public Integer getGrantedbyId() {
        return grantedbyId;
    }

    public void setGrantedbyId(Integer grantedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != grantedbyId)
            grantedbyId = grantedbyId == 0 ? null : grantedbyId;
        if (grantedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + grantedbyId)) {
            throw new ForeignKeyViolationException("Can't set grantedby_id, foreign key violation: xabsenteeism_xemployee_fk4");
        }
        setWasChanged(this.grantedbyId != null && !this.grantedbyId.equals(grantedbyId));
        this.grantedbyId = grantedbyId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.reason != null && !this.reason.equals(reason));
        this.reason = reason;
    }

    public Integer getMedicalCond() {
        return medicalCond;
    }

    public void setMedicalCond(Integer medicalCond) throws SQLException, ForeignKeyViolationException {
        if (null != medicalCond)
            medicalCond = medicalCond == 0 ? null : medicalCond;
        setWasChanged(this.medicalCond != null && !this.medicalCond.equals(medicalCond));
        this.medicalCond = medicalCond;
    }

    public Integer getFuneral() {
        return funeral;
    }

    public void setFuneral(Integer funeral) throws SQLException, ForeignKeyViolationException {
        if (null != funeral)
            funeral = funeral == 0 ? null : funeral;
        setWasChanged(this.funeral != null && !this.funeral.equals(funeral));
        this.funeral = funeral;
    }

    public Integer getFamilyProblem() {
        return familyProblem;
    }

    public void setFamilyProblem(Integer familyProblem) throws SQLException, ForeignKeyViolationException {
        if (null != familyProblem)
            familyProblem = familyProblem == 0 ? null : familyProblem;
        setWasChanged(this.familyProblem != null && !this.familyProblem.equals(familyProblem));
        this.familyProblem = familyProblem;
    }

    public Integer getInJail() {
        return inJail;
    }

    public void setInJail(Integer inJail) throws SQLException, ForeignKeyViolationException {
        if (null != inJail)
            inJail = inJail == 0 ? null : inJail;
        setWasChanged(this.inJail != null && !this.inJail.equals(inJail));
        this.inJail = inJail;
    }

    public Integer getPdpExpired() {
        return pdpExpired;
    }

    public void setPdpExpired(Integer pdpExpired) throws SQLException, ForeignKeyViolationException {
        if (null != pdpExpired)
            pdpExpired = pdpExpired == 0 ? null : pdpExpired;
        setWasChanged(this.pdpExpired != null && !this.pdpExpired.equals(pdpExpired));
        this.pdpExpired = pdpExpired;
    }

    public Integer getLicenseProblem() {
        return licenseProblem;
    }

    public void setLicenseProblem(Integer licenseProblem) throws SQLException, ForeignKeyViolationException {
        if (null != licenseProblem)
            licenseProblem = licenseProblem == 0 ? null : licenseProblem;
        setWasChanged(this.licenseProblem != null && !this.licenseProblem.equals(licenseProblem));
        this.licenseProblem = licenseProblem;
    }

    public Integer getPpeSafety() {
        return ppeSafety;
    }

    public void setPpeSafety(Integer ppeSafety) throws SQLException, ForeignKeyViolationException {
        if (null != ppeSafety)
            ppeSafety = ppeSafety == 0 ? null : ppeSafety;
        setWasChanged(this.ppeSafety != null && !this.ppeSafety.equals(ppeSafety));
        this.ppeSafety = ppeSafety;
    }

    public Integer getWageDispute() {
        return wageDispute;
    }

    public void setWageDispute(Integer wageDispute) throws SQLException, ForeignKeyViolationException {
        if (null != wageDispute)
            wageDispute = wageDispute == 0 ? null : wageDispute;
        setWasChanged(this.wageDispute != null && !this.wageDispute.equals(wageDispute));
        this.wageDispute = wageDispute;
    }

    public Integer getDrunkOnSite() {
        return drunkOnSite;
    }

    public void setDrunkOnSite(Integer drunkOnSite) throws SQLException, ForeignKeyViolationException {
        if (null != drunkOnSite)
            drunkOnSite = drunkOnSite == 0 ? null : drunkOnSite;
        setWasChanged(this.drunkOnSite != null && !this.drunkOnSite.equals(drunkOnSite));
        this.drunkOnSite = drunkOnSite;
    }

    public Integer getWorkAccident() {
        return workAccident;
    }

    public void setWorkAccident(Integer workAccident) throws SQLException, ForeignKeyViolationException {
        if (null != workAccident)
            workAccident = workAccident == 0 ? null : workAccident;
        setWasChanged(this.workAccident != null && !this.workAccident.equals(workAccident));
        this.workAccident = workAccident;
    }

    public Integer getNoReason() {
        return noReason;
    }

    public void setNoReason(Integer noReason) throws SQLException, ForeignKeyViolationException {
        if (null != noReason)
            noReason = noReason == 0 ? null : noReason;
        setWasChanged(this.noReason != null && !this.noReason.equals(noReason));
        this.noReason = noReason;
    }

    public Integer getMedicalCert() {
        return medicalCert;
    }

    public void setMedicalCert(Integer medicalCert) throws SQLException, ForeignKeyViolationException {
        if (null != medicalCert)
            medicalCert = medicalCert == 0 ? null : medicalCert;
        setWasChanged(this.medicalCert != null && !this.medicalCert.equals(medicalCert));
        this.medicalCert = medicalCert;
    }

    public Integer getDeathCert() {
        return deathCert;
    }

    public void setDeathCert(Integer deathCert) throws SQLException, ForeignKeyViolationException {
        if (null != deathCert)
            deathCert = deathCert == 0 ? null : deathCert;
        setWasChanged(this.deathCert != null && !this.deathCert.equals(deathCert));
        this.deathCert = deathCert;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[25];
        columnValues[0] = getXabsenteeismId();
        columnValues[1] = getXemployeeId();
        columnValues[2] = getAbsentdate();
        columnValues[3] = getXsiteId();
        columnValues[4] = getXmachineId();
        columnValues[5] = getStanding();
        columnValues[6] = getReportedbyId();
        columnValues[7] = getReportedtoId();
        columnValues[8] = getNotcommunicated();
        columnValues[9] = getPermgranted();
        columnValues[10] = getGrantedbyId();
        columnValues[11] = getReason();
        columnValues[12] = getMedicalCond();
        columnValues[13] = getFuneral();
        columnValues[14] = getFamilyProblem();
        columnValues[15] = getInJail();
        columnValues[16] = getPdpExpired();
        columnValues[17] = getLicenseProblem();
        columnValues[18] = getPpeSafety();
        columnValues[19] = getWageDispute();
        columnValues[20] = getDrunkOnSite();
        columnValues[21] = getWorkAccident();
        columnValues[22] = getNoReason();
        columnValues[23] = getMedicalCert();
        columnValues[24] = getDeathCert();
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
            setXabsenteeismId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXabsenteeismId(null);
        }
        try {
            setXemployeeId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
        setAbsentdate(toDate(flds[2]));
        try {
            setXsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setStanding(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setStanding(null);
        }
        try {
            setReportedbyId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setReportedbyId(null);
        }
        try {
            setReportedtoId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setReportedtoId(null);
        }
        try {
            setNotcommunicated(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setNotcommunicated(null);
        }
        try {
            setPermgranted(Integer.parseInt(flds[9]));
        } catch(NumberFormatException ne) {
            setPermgranted(null);
        }
        try {
            setGrantedbyId(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setGrantedbyId(null);
        }
        setReason(flds[11]);
        try {
            setMedicalCond(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setMedicalCond(null);
        }
        try {
            setFuneral(Integer.parseInt(flds[13]));
        } catch(NumberFormatException ne) {
            setFuneral(null);
        }
        try {
            setFamilyProblem(Integer.parseInt(flds[14]));
        } catch(NumberFormatException ne) {
            setFamilyProblem(null);
        }
        try {
            setInJail(Integer.parseInt(flds[15]));
        } catch(NumberFormatException ne) {
            setInJail(null);
        }
        try {
            setPdpExpired(Integer.parseInt(flds[16]));
        } catch(NumberFormatException ne) {
            setPdpExpired(null);
        }
        try {
            setLicenseProblem(Integer.parseInt(flds[17]));
        } catch(NumberFormatException ne) {
            setLicenseProblem(null);
        }
        try {
            setPpeSafety(Integer.parseInt(flds[18]));
        } catch(NumberFormatException ne) {
            setPpeSafety(null);
        }
        try {
            setWageDispute(Integer.parseInt(flds[19]));
        } catch(NumberFormatException ne) {
            setWageDispute(null);
        }
        try {
            setDrunkOnSite(Integer.parseInt(flds[20]));
        } catch(NumberFormatException ne) {
            setDrunkOnSite(null);
        }
        try {
            setWorkAccident(Integer.parseInt(flds[21]));
        } catch(NumberFormatException ne) {
            setWorkAccident(null);
        }
        try {
            setNoReason(Integer.parseInt(flds[22]));
        } catch(NumberFormatException ne) {
            setNoReason(null);
        }
        try {
            setMedicalCert(Integer.parseInt(flds[23]));
        } catch(NumberFormatException ne) {
            setMedicalCert(null);
        }
        try {
            setDeathCert(Integer.parseInt(flds[24]));
        } catch(NumberFormatException ne) {
            setDeathCert(null);
        }
    }
}
