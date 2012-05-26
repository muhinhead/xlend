// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri May 25 08:19:27 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xbreakdown extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xbreakdownId = null;
    private Date breakdowndate = null;
    private Integer xmachineId = null;
    private Integer xsiteId = null;
    private Integer reportedtoId = null;
    private Integer reportedbyId = null;
    private Integer attendedbyId = null;
    private Integer vehicleId = null;
    private Date repairdate = null;
    private Integer repaired = null;
    private Integer operatorfault = null;
    private Integer operatorId = null;
    private Integer xconsumeId = null;
    private Integer km2site1way = null;
    private Integer hoursonjob = null;
    private Integer timeleft = null;
    private Integer timeback = null;
    private Integer stayedover = null;
    private Integer accomprice = null;
    private String invoicenumber = null;
    private Double amount = null;

    public Xbreakdown(Connection connection) {
        super(connection, "xbreakdown", "xbreakdown_id");
        setColumnNames(new String[]{"xbreakdown_id", "breakdowndate", "xmachine_id", "xsite_id", "reportedto_id", "reportedby_id", "attendedby_id", "vehicle_id", "repairdate", "repaired", "operatorfault", "operator_id", "xconsume_id", "km2site1way", "hoursonjob", "timeleft", "timeback", "stayedover", "accomprice", "invoicenumber", "amount"});
    }

    public Xbreakdown(Connection connection, Integer xbreakdownId, Date breakdowndate, Integer xmachineId, Integer xsiteId, Integer reportedtoId, Integer reportedbyId, Integer attendedbyId, Integer vehicleId, Date repairdate, Integer repaired, Integer operatorfault, Integer operatorId, Integer xconsumeId, Integer km2site1way, Integer hoursonjob, Integer timeleft, Integer timeback, Integer stayedover, Integer accomprice, String invoicenumber, Double amount) {
        super(connection, "xbreakdown", "xbreakdown_id");
        setNew(xbreakdownId.intValue() <= 0);
//        if (xbreakdownId.intValue() != 0) {
            this.xbreakdownId = xbreakdownId;
//        }
        this.breakdowndate = breakdowndate;
        this.xmachineId = xmachineId;
        this.xsiteId = xsiteId;
        this.reportedtoId = reportedtoId;
        this.reportedbyId = reportedbyId;
        this.attendedbyId = attendedbyId;
        this.vehicleId = vehicleId;
        this.repairdate = repairdate;
        this.repaired = repaired;
        this.operatorfault = operatorfault;
        this.operatorId = operatorId;
        this.xconsumeId = xconsumeId;
        this.km2site1way = km2site1way;
        this.hoursonjob = hoursonjob;
        this.timeleft = timeleft;
        this.timeback = timeback;
        this.stayedover = stayedover;
        this.accomprice = accomprice;
        this.invoicenumber = invoicenumber;
        this.amount = amount;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xbreakdown xbreakdown = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbreakdown_id,breakdowndate,xmachine_id,xsite_id,reportedto_id,reportedby_id,attendedby_id,vehicle_id,repairdate,repaired,operatorfault,operator_id,xconsume_id,km2site1way,hoursonjob,timeleft,timeback,stayedover,accomprice,invoicenumber,amount FROM xbreakdown WHERE xbreakdown_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xbreakdown = new Xbreakdown(getConnection());
                xbreakdown.setXbreakdownId(new Integer(rs.getInt(1)));
                xbreakdown.setBreakdowndate(rs.getDate(2));
                xbreakdown.setXmachineId(new Integer(rs.getInt(3)));
                xbreakdown.setXsiteId(new Integer(rs.getInt(4)));
                xbreakdown.setReportedtoId(new Integer(rs.getInt(5)));
                xbreakdown.setReportedbyId(new Integer(rs.getInt(6)));
                xbreakdown.setAttendedbyId(new Integer(rs.getInt(7)));
                xbreakdown.setVehicleId(new Integer(rs.getInt(8)));
                xbreakdown.setRepairdate(rs.getDate(9));
                xbreakdown.setRepaired(new Integer(rs.getInt(10)));
                xbreakdown.setOperatorfault(new Integer(rs.getInt(11)));
                xbreakdown.setOperatorId(new Integer(rs.getInt(12)));
                xbreakdown.setXconsumeId(new Integer(rs.getInt(13)));
                xbreakdown.setKm2site1way(new Integer(rs.getInt(14)));
                xbreakdown.setHoursonjob(new Integer(rs.getInt(15)));
                xbreakdown.setTimeleft(new Integer(rs.getInt(16)));
                xbreakdown.setTimeback(new Integer(rs.getInt(17)));
                xbreakdown.setStayedover(new Integer(rs.getInt(18)));
                xbreakdown.setAccomprice(new Integer(rs.getInt(19)));
                xbreakdown.setInvoicenumber(rs.getString(20));
                xbreakdown.setAmount(rs.getDouble(21));
                xbreakdown.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xbreakdown;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xbreakdown ("+(getXbreakdownId().intValue()!=0?"xbreakdown_id,":"")+"breakdowndate,xmachine_id,xsite_id,reportedto_id,reportedby_id,attendedby_id,vehicle_id,repairdate,repaired,operatorfault,operator_id,xconsume_id,km2site1way,hoursonjob,timeleft,timeback,stayedover,accomprice,invoicenumber,amount) values("+(getXbreakdownId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXbreakdownId().intValue()!=0) {
                 ps.setObject(++n, getXbreakdownId());
             }
             ps.setObject(++n, getBreakdowndate());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getReportedtoId());
             ps.setObject(++n, getReportedbyId());
             ps.setObject(++n, getAttendedbyId());
             ps.setObject(++n, getVehicleId());
             ps.setObject(++n, getRepairdate());
             ps.setObject(++n, getRepaired());
             ps.setObject(++n, getOperatorfault());
             ps.setObject(++n, getOperatorId());
             ps.setObject(++n, getXconsumeId());
             ps.setObject(++n, getKm2site1way());
             ps.setObject(++n, getHoursonjob());
             ps.setObject(++n, getTimeleft());
             ps.setObject(++n, getTimeback());
             ps.setObject(++n, getStayedover());
             ps.setObject(++n, getAccomprice());
             ps.setObject(++n, getInvoicenumber());
             ps.setObject(++n, getAmount());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXbreakdownId().intValue()==0) {
             stmt = "SELECT max(xbreakdown_id) FROM xbreakdown";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXbreakdownId(new Integer(rs.getInt(1)));
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
                    "UPDATE xbreakdown " +
                    "SET breakdowndate = ?, xmachine_id = ?, xsite_id = ?, reportedto_id = ?, reportedby_id = ?, attendedby_id = ?, vehicle_id = ?, repairdate = ?, repaired = ?, operatorfault = ?, operator_id = ?, xconsume_id = ?, km2site1way = ?, hoursonjob = ?, timeleft = ?, timeback = ?, stayedover = ?, accomprice = ?, invoicenumber = ?, amount = ?" + 
                    " WHERE xbreakdown_id = " + getXbreakdownId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getBreakdowndate());
                ps.setObject(2, getXmachineId());
                ps.setObject(3, getXsiteId());
                ps.setObject(4, getReportedtoId());
                ps.setObject(5, getReportedbyId());
                ps.setObject(6, getAttendedbyId());
                ps.setObject(7, getVehicleId());
                ps.setObject(8, getRepairdate());
                ps.setObject(9, getRepaired());
                ps.setObject(10, getOperatorfault());
                ps.setObject(11, getOperatorId());
                ps.setObject(12, getXconsumeId());
                ps.setObject(13, getKm2site1way());
                ps.setObject(14, getHoursonjob());
                ps.setObject(15, getTimeleft());
                ps.setObject(16, getTimeback());
                ps.setObject(17, getStayedover());
                ps.setObject(18, getAccomprice());
                ps.setObject(19, getInvoicenumber());
                ps.setObject(20, getAmount());
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
                "DELETE FROM xbreakdown " +
                "WHERE xbreakdown_id = " + getXbreakdownId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXbreakdownId(new Integer(-getXbreakdownId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXbreakdownId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xbreakdown_id,breakdowndate,xmachine_id,xsite_id,reportedto_id,reportedby_id,attendedby_id,vehicle_id,repairdate,repaired,operatorfault,operator_id,xconsume_id,km2site1way,hoursonjob,timeleft,timeback,stayedover,accomprice,invoicenumber,amount FROM xbreakdown " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xbreakdown(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),rs.getDate(9),new Integer(rs.getInt(10)),new Integer(rs.getInt(11)),new Integer(rs.getInt(12)),new Integer(rs.getInt(13)),new Integer(rs.getInt(14)),new Integer(rs.getInt(15)),new Integer(rs.getInt(16)),new Integer(rs.getInt(17)),new Integer(rs.getInt(18)),new Integer(rs.getInt(19)),rs.getString(20),rs.getDouble(21)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xbreakdown[] objects = new Xbreakdown[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xbreakdown xbreakdown = (Xbreakdown) lst.get(i);
            objects[i] = xbreakdown;
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
        String stmt = "SELECT xbreakdown_id FROM xbreakdown " +
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
    //    return getXbreakdownId() + getDelimiter();
    //}

    public Integer getXbreakdownId() {
        return xbreakdownId;
    }

    public void setXbreakdownId(Integer xbreakdownId) throws ForeignKeyViolationException {
        setWasChanged(this.xbreakdownId != null && this.xbreakdownId != xbreakdownId);
        this.xbreakdownId = xbreakdownId;
        setNew(xbreakdownId.intValue() == 0);
    }

    public Date getBreakdowndate() {
        return breakdowndate;
    }

    public void setBreakdowndate(Date breakdowndate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.breakdowndate != null && !this.breakdowndate.equals(breakdowndate));
        this.breakdowndate = breakdowndate;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xbreakdown_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xbreakdown_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getReportedtoId() {
        return reportedtoId;
    }

    public void setReportedtoId(Integer reportedtoId) throws SQLException, ForeignKeyViolationException {
        if (null != reportedtoId)
            reportedtoId = reportedtoId == 0 ? null : reportedtoId;
        if (reportedtoId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + reportedtoId)) {
            throw new ForeignKeyViolationException("Can't set reportedto_id, foreign key violation: xbreakdown_xemployee_fk");
        }
        setWasChanged(this.reportedtoId != null && !this.reportedtoId.equals(reportedtoId));
        this.reportedtoId = reportedtoId;
    }

    public Integer getReportedbyId() {
        return reportedbyId;
    }

    public void setReportedbyId(Integer reportedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != reportedbyId)
            reportedbyId = reportedbyId == 0 ? null : reportedbyId;
        if (reportedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + reportedbyId)) {
            throw new ForeignKeyViolationException("Can't set reportedby_id, foreign key violation: xbreakdown_xemployee_fk2");
        }
        setWasChanged(this.reportedbyId != null && !this.reportedbyId.equals(reportedbyId));
        this.reportedbyId = reportedbyId;
    }

    public Integer getAttendedbyId() {
        return attendedbyId;
    }

    public void setAttendedbyId(Integer attendedbyId) throws SQLException, ForeignKeyViolationException {
        if (null != attendedbyId)
            attendedbyId = attendedbyId == 0 ? null : attendedbyId;
        if (attendedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + attendedbyId)) {
            throw new ForeignKeyViolationException("Can't set attendedby_id, foreign key violation: xbreakdown_xemployee_fk3");
        }
        setWasChanged(this.attendedbyId != null && !this.attendedbyId.equals(attendedbyId));
        this.attendedbyId = attendedbyId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) throws SQLException, ForeignKeyViolationException {
        if (null != vehicleId)
            vehicleId = vehicleId == 0 ? null : vehicleId;
        if (vehicleId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + vehicleId)) {
            throw new ForeignKeyViolationException("Can't set vehicle_id, foreign key violation: xbreakdown_xmachine_fk2");
        }
        setWasChanged(this.vehicleId != null && !this.vehicleId.equals(vehicleId));
        this.vehicleId = vehicleId;
    }

    public Date getRepairdate() {
        return repairdate;
    }

    public void setRepairdate(Date repairdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.repairdate != null && !this.repairdate.equals(repairdate));
        this.repairdate = repairdate;
    }

    public Integer getRepaired() {
        return repaired;
    }

    public void setRepaired(Integer repaired) throws SQLException, ForeignKeyViolationException {
        if (null != repaired)
            repaired = repaired == 0 ? null : repaired;
        setWasChanged(this.repaired != null && !this.repaired.equals(repaired));
        this.repaired = repaired;
    }

    public Integer getOperatorfault() {
        return operatorfault;
    }

    public void setOperatorfault(Integer operatorfault) throws SQLException, ForeignKeyViolationException {
        if (null != operatorfault)
            operatorfault = operatorfault == 0 ? null : operatorfault;
        setWasChanged(this.operatorfault != null && !this.operatorfault.equals(operatorfault));
        this.operatorfault = operatorfault;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) throws SQLException, ForeignKeyViolationException {
        if (null != operatorId)
            operatorId = operatorId == 0 ? null : operatorId;
        if (operatorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + operatorId)) {
            throw new ForeignKeyViolationException("Can't set operator_id, foreign key violation: xbreakdown_xemployee_fk4");
        }
        setWasChanged(this.operatorId != null && !this.operatorId.equals(operatorId));
        this.operatorId = operatorId;
    }

    public Integer getXconsumeId() {
        return xconsumeId;
    }

    public void setXconsumeId(Integer xconsumeId) throws SQLException, ForeignKeyViolationException {
        if (null != xconsumeId)
            xconsumeId = xconsumeId == 0 ? null : xconsumeId;
        if (xconsumeId!=null && !Xconsume.exists(getConnection(),"xconsume_id = " + xconsumeId)) {
            throw new ForeignKeyViolationException("Can't set xconsume_id, foreign key violation: xbreakdown_xconsume_fk");
        }
        setWasChanged(this.xconsumeId != null && !this.xconsumeId.equals(xconsumeId));
        this.xconsumeId = xconsumeId;
    }

    public Integer getKm2site1way() {
        return km2site1way;
    }

    public void setKm2site1way(Integer km2site1way) throws SQLException, ForeignKeyViolationException {
        if (null != km2site1way)
            km2site1way = km2site1way == 0 ? null : km2site1way;
        setWasChanged(this.km2site1way != null && !this.km2site1way.equals(km2site1way));
        this.km2site1way = km2site1way;
    }

    public Integer getHoursonjob() {
        return hoursonjob;
    }

    public void setHoursonjob(Integer hoursonjob) throws SQLException, ForeignKeyViolationException {
        if (null != hoursonjob)
            hoursonjob = hoursonjob == 0 ? null : hoursonjob;
        setWasChanged(this.hoursonjob != null && !this.hoursonjob.equals(hoursonjob));
        this.hoursonjob = hoursonjob;
    }

    public Integer getTimeleft() {
        return timeleft;
    }

    public void setTimeleft(Integer timeleft) throws SQLException, ForeignKeyViolationException {
        if (null != timeleft)
            timeleft = timeleft == 0 ? null : timeleft;
        setWasChanged(this.timeleft != null && !this.timeleft.equals(timeleft));
        this.timeleft = timeleft;
    }

    public Integer getTimeback() {
        return timeback;
    }

    public void setTimeback(Integer timeback) throws SQLException, ForeignKeyViolationException {
        if (null != timeback)
            timeback = timeback == 0 ? null : timeback;
        setWasChanged(this.timeback != null && !this.timeback.equals(timeback));
        this.timeback = timeback;
    }

    public Integer getStayedover() {
        return stayedover;
    }

    public void setStayedover(Integer stayedover) throws SQLException, ForeignKeyViolationException {
        if (null != stayedover)
            stayedover = stayedover == 0 ? null : stayedover;
        setWasChanged(this.stayedover != null && !this.stayedover.equals(stayedover));
        this.stayedover = stayedover;
    }

    public Integer getAccomprice() {
        return accomprice;
    }

    public void setAccomprice(Integer accomprice) throws SQLException, ForeignKeyViolationException {
        if (null != accomprice)
            accomprice = accomprice == 0 ? null : accomprice;
        setWasChanged(this.accomprice != null && !this.accomprice.equals(accomprice));
        this.accomprice = accomprice;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invoicenumber != null && !this.invoicenumber.equals(invoicenumber));
        this.invoicenumber = invoicenumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.amount != null && !this.amount.equals(amount));
        this.amount = amount;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[21];
        columnValues[0] = getXbreakdownId();
        columnValues[1] = getBreakdowndate();
        columnValues[2] = getXmachineId();
        columnValues[3] = getXsiteId();
        columnValues[4] = getReportedtoId();
        columnValues[5] = getReportedbyId();
        columnValues[6] = getAttendedbyId();
        columnValues[7] = getVehicleId();
        columnValues[8] = getRepairdate();
        columnValues[9] = getRepaired();
        columnValues[10] = getOperatorfault();
        columnValues[11] = getOperatorId();
        columnValues[12] = getXconsumeId();
        columnValues[13] = getKm2site1way();
        columnValues[14] = getHoursonjob();
        columnValues[15] = getTimeleft();
        columnValues[16] = getTimeback();
        columnValues[17] = getStayedover();
        columnValues[18] = getAccomprice();
        columnValues[19] = getInvoicenumber();
        columnValues[20] = getAmount();
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
            setXbreakdownId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXbreakdownId(null);
        }
        setBreakdowndate(toDate(flds[1]));
        try {
            setXmachineId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setReportedtoId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setReportedtoId(null);
        }
        try {
            setReportedbyId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setReportedbyId(null);
        }
        try {
            setAttendedbyId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setAttendedbyId(null);
        }
        try {
            setVehicleId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setVehicleId(null);
        }
        setRepairdate(toDate(flds[8]));
        try {
            setRepaired(Integer.parseInt(flds[9]));
        } catch(NumberFormatException ne) {
            setRepaired(null);
        }
        try {
            setOperatorfault(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setOperatorfault(null);
        }
        try {
            setOperatorId(Integer.parseInt(flds[11]));
        } catch(NumberFormatException ne) {
            setOperatorId(null);
        }
        try {
            setXconsumeId(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setXconsumeId(null);
        }
        try {
            setKm2site1way(Integer.parseInt(flds[13]));
        } catch(NumberFormatException ne) {
            setKm2site1way(null);
        }
        try {
            setHoursonjob(Integer.parseInt(flds[14]));
        } catch(NumberFormatException ne) {
            setHoursonjob(null);
        }
        try {
            setTimeleft(Integer.parseInt(flds[15]));
        } catch(NumberFormatException ne) {
            setTimeleft(null);
        }
        try {
            setTimeback(Integer.parseInt(flds[16]));
        } catch(NumberFormatException ne) {
            setTimeback(null);
        }
        try {
            setStayedover(Integer.parseInt(flds[17]));
        } catch(NumberFormatException ne) {
            setStayedover(null);
        }
        try {
            setAccomprice(Integer.parseInt(flds[18]));
        } catch(NumberFormatException ne) {
            setAccomprice(null);
        }
        setInvoicenumber(flds[19]);
        try {
            setAmount(Double.parseDouble(flds[20]));
        } catch(NumberFormatException ne) {
            setAmount(null);
        }
    }
}
