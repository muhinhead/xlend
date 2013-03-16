// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Mar 16 17:44:59 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xincidents extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xincidentsId = null;
    private Date reportdate = null;
    private Date incidentdate = null;
    private Integer xsiteId = null;
    private String blobmachines = null;
    private String blobpeople = null;
    private String location = null;
    private String description = null;
    private String damages = null;
    private Integer estimatedCost = null;
    private Integer reportedbyId = null;
    private Integer reportedtoId = null;
    private Integer isSigned = null;
    private Integer isVerified = null;
    private Integer verifiedbyId = null;
    private Integer lostIncome = null;

    public Xincidents(Connection connection) {
        super(connection, "xincidents", "xincidents_id");
        setColumnNames(new String[]{"xincidents_id", "reportdate", "incidentdate", "xsite_id", "blobmachines", "blobpeople", "location", "description", "damages", "estimated_cost", "reportedby_id", "reportedto_id", "is_signed", "is_verified", "verifiedby_id", "lost_income"});
    }

    public Xincidents(Connection connection, Integer xincidentsId, Date reportdate, Date incidentdate, Integer xsiteId, String blobmachines, String blobpeople, String location, String description, String damages, Integer estimatedCost, Integer reportedbyId, Integer reportedtoId, Integer isSigned, Integer isVerified, Integer verifiedbyId, Integer lostIncome) {
        super(connection, "xincidents", "xincidents_id");
        setNew(xincidentsId.intValue() <= 0);
//        if (xincidentsId.intValue() != 0) {
            this.xincidentsId = xincidentsId;
//        }
        this.reportdate = reportdate;
        this.incidentdate = incidentdate;
        this.xsiteId = xsiteId;
        this.blobmachines = blobmachines;
        this.blobpeople = blobpeople;
        this.location = location;
        this.description = description;
        this.damages = damages;
        this.estimatedCost = estimatedCost;
        this.reportedbyId = reportedbyId;
        this.reportedtoId = reportedtoId;
        this.isSigned = isSigned;
        this.isVerified = isVerified;
        this.verifiedbyId = verifiedbyId;
        this.lostIncome = lostIncome;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xincidents xincidents = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xincidents_id,reportdate,incidentdate,xsite_id,blobmachines,blobpeople,location,description,damages,estimated_cost,reportedby_id,reportedto_id,is_signed,is_verified,verifiedby_id,lost_income FROM xincidents WHERE xincidents_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xincidents = new Xincidents(getConnection());
                xincidents.setXincidentsId(new Integer(rs.getInt(1)));
                xincidents.setReportdate(rs.getDate(2));
                xincidents.setIncidentdate(rs.getDate(3));
                xincidents.setXsiteId(new Integer(rs.getInt(4)));
                xincidents.setBlobmachines(rs.getString(5));
                xincidents.setBlobpeople(rs.getString(6));
                xincidents.setLocation(rs.getString(7));
                xincidents.setDescription(rs.getString(8));
                xincidents.setDamages(rs.getString(9));
                xincidents.setEstimatedCost(new Integer(rs.getInt(10)));
                xincidents.setReportedbyId(new Integer(rs.getInt(11)));
                xincidents.setReportedtoId(new Integer(rs.getInt(12)));
                xincidents.setIsSigned(new Integer(rs.getInt(13)));
                xincidents.setIsVerified(new Integer(rs.getInt(14)));
                xincidents.setVerifiedbyId(new Integer(rs.getInt(15)));
                xincidents.setLostIncome(new Integer(rs.getInt(16)));
                xincidents.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xincidents;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xincidents ("+(getXincidentsId().intValue()!=0?"xincidents_id,":"")+"reportdate,incidentdate,xsite_id,blobmachines,blobpeople,location,description,damages,estimated_cost,reportedby_id,reportedto_id,is_signed,is_verified,verifiedby_id,lost_income) values("+(getXincidentsId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXincidentsId().intValue()!=0) {
                 ps.setObject(++n, getXincidentsId());
             }
             ps.setObject(++n, getReportdate());
             ps.setObject(++n, getIncidentdate());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getBlobmachines());
             ps.setObject(++n, getBlobpeople());
             ps.setObject(++n, getLocation());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getDamages());
             ps.setObject(++n, getEstimatedCost());
             ps.setObject(++n, getReportedbyId());
             ps.setObject(++n, getReportedtoId());
             ps.setObject(++n, getIsSigned());
             ps.setObject(++n, getIsVerified());
             ps.setObject(++n, getVerifiedbyId());
             ps.setObject(++n, getLostIncome());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXincidentsId().intValue()==0) {
             stmt = "SELECT max(xincidents_id) FROM xincidents";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXincidentsId(new Integer(rs.getInt(1)));
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
                    "UPDATE xincidents " +
                    "SET reportdate = ?, incidentdate = ?, xsite_id = ?, blobmachines = ?, blobpeople = ?, location = ?, description = ?, damages = ?, estimated_cost = ?, reportedby_id = ?, reportedto_id = ?, is_signed = ?, is_verified = ?, verifiedby_id = ?, lost_income = ?" + 
                    " WHERE xincidents_id = " + getXincidentsId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getReportdate());
                ps.setObject(2, getIncidentdate());
                ps.setObject(3, getXsiteId());
                ps.setObject(4, getBlobmachines());
                ps.setObject(5, getBlobpeople());
                ps.setObject(6, getLocation());
                ps.setObject(7, getDescription());
                ps.setObject(8, getDamages());
                ps.setObject(9, getEstimatedCost());
                ps.setObject(10, getReportedbyId());
                ps.setObject(11, getReportedtoId());
                ps.setObject(12, getIsSigned());
                ps.setObject(13, getIsVerified());
                ps.setObject(14, getVerifiedbyId());
                ps.setObject(15, getLostIncome());
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
        {// delete cascade from xemployeeincident
            Xemployeeincident[] records = (Xemployeeincident[])Xemployeeincident.load(getConnection(),"xincidents_id = " + getXincidentsId(),null);
            for (int i = 0; i<records.length; i++) {
                Xemployeeincident xemployeeincident = records[i];
                xemployeeincident.delete();
            }
        }
        {// delete cascade from xmachineincident
            Xmachineincident[] records = (Xmachineincident[])Xmachineincident.load(getConnection(),"xincidents_id = " + getXincidentsId(),null);
            for (int i = 0; i<records.length; i++) {
                Xmachineincident xmachineincident = records[i];
                xmachineincident.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xincidents " +
                "WHERE xincidents_id = " + getXincidentsId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXincidentsId(new Integer(-getXincidentsId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXincidentsId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xincidents_id,reportdate,incidentdate,xsite_id,blobmachines,blobpeople,location,description,damages,estimated_cost,reportedby_id,reportedto_id,is_signed,is_verified,verifiedby_id,lost_income FROM xincidents " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xincidents(con,new Integer(rs.getInt(1)),rs.getDate(2),rs.getDate(3),new Integer(rs.getInt(4)),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),new Integer(rs.getInt(10)),new Integer(rs.getInt(11)),new Integer(rs.getInt(12)),new Integer(rs.getInt(13)),new Integer(rs.getInt(14)),new Integer(rs.getInt(15)),new Integer(rs.getInt(16))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xincidents[] objects = new Xincidents[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xincidents xincidents = (Xincidents) lst.get(i);
            objects[i] = xincidents;
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
        String stmt = "SELECT xincidents_id FROM xincidents " +
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
    //    return getXincidentsId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xincidentsId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXincidentsId(id);
        setNew(prevIsNew);
    }

    public Integer getXincidentsId() {
        return xincidentsId;
    }

    public void setXincidentsId(Integer xincidentsId) throws ForeignKeyViolationException {
        setWasChanged(this.xincidentsId != null && this.xincidentsId != xincidentsId);
        this.xincidentsId = xincidentsId;
        setNew(xincidentsId.intValue() == 0);
    }

    public Date getReportdate() {
        return reportdate;
    }

    public void setReportdate(Date reportdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.reportdate != null && !this.reportdate.equals(reportdate));
        this.reportdate = reportdate;
    }

    public Date getIncidentdate() {
        return incidentdate;
    }

    public void setIncidentdate(Date incidentdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.incidentdate != null && !this.incidentdate.equals(incidentdate));
        this.incidentdate = incidentdate;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (null != xsiteId)
            xsiteId = xsiteId == 0 ? null : xsiteId;
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xincidents_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public String getBlobmachines() {
        return blobmachines;
    }

    public void setBlobmachines(String blobmachines) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.blobmachines != null && !this.blobmachines.equals(blobmachines));
        this.blobmachines = blobmachines;
    }

    public String getBlobpeople() {
        return blobpeople;
    }

    public void setBlobpeople(String blobpeople) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.blobpeople != null && !this.blobpeople.equals(blobpeople));
        this.blobpeople = blobpeople;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.location != null && !this.location.equals(location));
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }

    public String getDamages() {
        return damages;
    }

    public void setDamages(String damages) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.damages != null && !this.damages.equals(damages));
        this.damages = damages;
    }

    public Integer getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Integer estimatedCost) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.estimatedCost != null && !this.estimatedCost.equals(estimatedCost));
        this.estimatedCost = estimatedCost;
    }

    public Integer getReportedbyId() {
        return reportedbyId;
    }

    public void setReportedbyId(Integer reportedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != reportedbyId)
            reportedbyId = reportedbyId == 0 ? null : reportedbyId;
        if (reportedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + reportedbyId)) {
            throw new ForeignKeyViolationException("Can't set reportedby_id, foreign key violation: xincidents_xemployee_fk");
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
            throw new ForeignKeyViolationException("Can't set reportedto_id, foreign key violation: xincidents_xemployee_fk2");
        }
        setWasChanged(this.reportedtoId != null && !this.reportedtoId.equals(reportedtoId));
        this.reportedtoId = reportedtoId;
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

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) throws SQLException, ForeignKeyViolationException {
        if (null != isVerified)
            isVerified = isVerified == 0 ? null : isVerified;
        setWasChanged(this.isVerified != null && !this.isVerified.equals(isVerified));
        this.isVerified = isVerified;
    }

    public Integer getVerifiedbyId() {
        return verifiedbyId;
    }

    public void setVerifiedbyId(Integer verifiedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != verifiedbyId)
            verifiedbyId = verifiedbyId == 0 ? null : verifiedbyId;
        if (verifiedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + verifiedbyId)) {
            throw new ForeignKeyViolationException("Can't set verifiedby_id, foreign key violation: xincidents_xemployee_fk3");
        }
        setWasChanged(this.verifiedbyId != null && !this.verifiedbyId.equals(verifiedbyId));
        this.verifiedbyId = verifiedbyId;
    }

    public Integer getLostIncome() {
        return lostIncome;
    }

    public void setLostIncome(Integer lostIncome) throws SQLException, ForeignKeyViolationException {
        if (null != lostIncome)
            lostIncome = lostIncome == 0 ? null : lostIncome;
        setWasChanged(this.lostIncome != null && !this.lostIncome.equals(lostIncome));
        this.lostIncome = lostIncome;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[16];
        columnValues[0] = getXincidentsId();
        columnValues[1] = getReportdate();
        columnValues[2] = getIncidentdate();
        columnValues[3] = getXsiteId();
        columnValues[4] = getBlobmachines();
        columnValues[5] = getBlobpeople();
        columnValues[6] = getLocation();
        columnValues[7] = getDescription();
        columnValues[8] = getDamages();
        columnValues[9] = getEstimatedCost();
        columnValues[10] = getReportedbyId();
        columnValues[11] = getReportedtoId();
        columnValues[12] = getIsSigned();
        columnValues[13] = getIsVerified();
        columnValues[14] = getVerifiedbyId();
        columnValues[15] = getLostIncome();
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
            setXincidentsId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXincidentsId(null);
        }
        setReportdate(toDate(flds[1]));
        setIncidentdate(toDate(flds[2]));
        try {
            setXsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        setBlobmachines(flds[4]);
        setBlobpeople(flds[5]);
        setLocation(flds[6]);
        setDescription(flds[7]);
        setDamages(flds[8]);
        try {
            setEstimatedCost(Integer.parseInt(flds[9]));
        } catch(NumberFormatException ne) {
            setEstimatedCost(null);
        }
        try {
            setReportedbyId(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setReportedbyId(null);
        }
        try {
            setReportedtoId(Integer.parseInt(flds[11]));
        } catch(NumberFormatException ne) {
            setReportedtoId(null);
        }
        try {
            setIsSigned(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setIsSigned(null);
        }
        try {
            setIsVerified(Integer.parseInt(flds[13]));
        } catch(NumberFormatException ne) {
            setIsVerified(null);
        }
        try {
            setVerifiedbyId(Integer.parseInt(flds[14]));
        } catch(NumberFormatException ne) {
            setVerifiedbyId(null);
        }
        try {
            setLostIncome(Integer.parseInt(flds[15]));
        } catch(NumberFormatException ne) {
            setLostIncome(null);
        }
    }
}
