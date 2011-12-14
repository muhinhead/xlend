// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sun Dec 11 15:20:58 EET 2011
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmachine extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmachineId = null;
    private Integer xlicensestatId = null;
    private String tmvnr = null;
    private String descr = null;
    private Integer xmachtypeId = null;
    private String type2 = null;
    private String regNr = null;
    private String oldregNr = null;
    private Date licensedate = null;
    private Date expdate = null;
    private String vehicleidNr = null;
    private String engineNr = null;
    private String chassisNr = null;
    private String classify = null;
    private String insuranceNr = null;
    private String insuranceTp = null;
    private Integer insuranceAmt = null;
    private Integer depositAmt = null;
    private Integer contractFee = null;
    private Integer monthlyPay = null;
    private Date paystart = null;
    private Date payend = null;
    private Object photo = null;

    public Xmachine(Connection connection) {
        super(connection, "xmachine", "xmachine_id");
        setColumnNames(new String[]{"xmachine_id", "xlicensestat_id", "tmvnr", "descr", "xmachtype_id", "type2", "reg_nr", "oldreg_nr", "licensedate", "expdate", "vehicleid_nr", "engine_nr", "chassis_nr", "classify", "insurance_nr", "insurance_tp", "insurance_amt", "deposit_amt", "contract_fee", "monthly_pay", "paystart", "payend", "photo"});
    }

    public Xmachine(Connection connection, Integer xmachineId, Integer xlicensestatId, String tmvnr, String descr, Integer xmachtypeId, String type2, String regNr, String oldregNr, Date licensedate, Date expdate, String vehicleidNr, String engineNr, String chassisNr, String classify, String insuranceNr, String insuranceTp, Integer insuranceAmt, Integer depositAmt, Integer contractFee, Integer monthlyPay, Date paystart, Date payend, Object photo) {
        super(connection, "xmachine", "xmachine_id");
        setNew(xmachineId.intValue() <= 0);
//        if (xmachineId.intValue() != 0) {
            this.xmachineId = xmachineId;
//        }
        this.xlicensestatId = xlicensestatId;
        this.tmvnr = tmvnr;
        this.descr = descr;
        this.xmachtypeId = xmachtypeId;
        this.type2 = type2;
        this.regNr = regNr;
        this.oldregNr = oldregNr;
        this.licensedate = licensedate;
        this.expdate = expdate;
        this.vehicleidNr = vehicleidNr;
        this.engineNr = engineNr;
        this.chassisNr = chassisNr;
        this.classify = classify;
        this.insuranceNr = insuranceNr;
        this.insuranceTp = insuranceTp;
        this.insuranceAmt = insuranceAmt;
        this.depositAmt = depositAmt;
        this.contractFee = contractFee;
        this.monthlyPay = monthlyPay;
        this.paystart = paystart;
        this.payend = payend;
        this.photo = photo;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmachine xmachine = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachine_id,xlicensestat_id,tmvnr,descr,xmachtype_id,type2,reg_nr,oldreg_nr,licensedate,expdate,vehicleid_nr,engine_nr,chassis_nr,classify,insurance_nr,insurance_tp,insurance_amt,deposit_amt,contract_fee,monthly_pay,paystart,payend,photo FROM xmachine WHERE xmachine_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachine = new Xmachine(getConnection());
                xmachine.setXmachineId(new Integer(rs.getInt(1)));
                xmachine.setXlicensestatId(new Integer(rs.getInt(2)));
                xmachine.setTmvnr(rs.getString(3));
                xmachine.setDescr(rs.getString(4));
                xmachine.setXmachtypeId(new Integer(rs.getInt(5)));
                xmachine.setType2(rs.getString(6));
                xmachine.setRegNr(rs.getString(7));
                xmachine.setOldregNr(rs.getString(8));
                xmachine.setLicensedate(rs.getDate(9));
                xmachine.setExpdate(rs.getDate(10));
                xmachine.setVehicleidNr(rs.getString(11));
                xmachine.setEngineNr(rs.getString(12));
                xmachine.setChassisNr(rs.getString(13));
                xmachine.setClassify(rs.getString(14));
                xmachine.setInsuranceNr(rs.getString(15));
                xmachine.setInsuranceTp(rs.getString(16));
                xmachine.setInsuranceAmt(new Integer(rs.getInt(17)));
                xmachine.setDepositAmt(new Integer(rs.getInt(18)));
                xmachine.setContractFee(new Integer(rs.getInt(19)));
                xmachine.setMonthlyPay(new Integer(rs.getInt(20)));
                xmachine.setPaystart(rs.getDate(21));
                xmachine.setPayend(rs.getDate(22));
                xmachine.setPhoto(rs.getObject(23));
                xmachine.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmachine;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmachine ("+(getXmachineId().intValue()!=0?"xmachine_id,":"")+"xlicensestat_id,tmvnr,descr,xmachtype_id,type2,reg_nr,oldreg_nr,licensedate,expdate,vehicleid_nr,engine_nr,chassis_nr,classify,insurance_nr,insurance_tp,insurance_amt,deposit_amt,contract_fee,monthly_pay,paystart,payend,photo) values("+(getXmachineId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachineId().intValue()!=0) {
                 ps.setObject(++n, getXmachineId());
             }
             ps.setObject(++n, getXlicensestatId());
             ps.setObject(++n, getTmvnr());
             ps.setObject(++n, getDescr());
             ps.setObject(++n, getXmachtypeId());
             ps.setObject(++n, getType2());
             ps.setObject(++n, getRegNr());
             ps.setObject(++n, getOldregNr());
             ps.setObject(++n, getLicensedate());
             ps.setObject(++n, getExpdate());
             ps.setObject(++n, getVehicleidNr());
             ps.setObject(++n, getEngineNr());
             ps.setObject(++n, getChassisNr());
             ps.setObject(++n, getClassify());
             ps.setObject(++n, getInsuranceNr());
             ps.setObject(++n, getInsuranceTp());
             ps.setObject(++n, getInsuranceAmt());
             ps.setObject(++n, getDepositAmt());
             ps.setObject(++n, getContractFee());
             ps.setObject(++n, getMonthlyPay());
             ps.setObject(++n, getPaystart());
             ps.setObject(++n, getPayend());
             ps.setObject(++n, getPhoto());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmachineId().intValue()==0) {
             stmt = "SELECT max(xmachine_id) FROM xmachine";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmachineId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmachine " +
                    "SET xlicensestat_id = ?, tmvnr = ?, descr = ?, xmachtype_id = ?, type2 = ?, reg_nr = ?, oldreg_nr = ?, licensedate = ?, expdate = ?, vehicleid_nr = ?, engine_nr = ?, chassis_nr = ?, classify = ?, insurance_nr = ?, insurance_tp = ?, insurance_amt = ?, deposit_amt = ?, contract_fee = ?, monthly_pay = ?, paystart = ?, payend = ?, photo = ?" + 
                    " WHERE xmachine_id = " + getXmachineId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXlicensestatId());
                ps.setObject(2, getTmvnr());
                ps.setObject(3, getDescr());
                ps.setObject(4, getXmachtypeId());
                ps.setObject(5, getType2());
                ps.setObject(6, getRegNr());
                ps.setObject(7, getOldregNr());
                ps.setObject(8, getLicensedate());
                ps.setObject(9, getExpdate());
                ps.setObject(10, getVehicleidNr());
                ps.setObject(11, getEngineNr());
                ps.setObject(12, getChassisNr());
                ps.setObject(13, getClassify());
                ps.setObject(14, getInsuranceNr());
                ps.setObject(15, getInsuranceTp());
                ps.setObject(16, getInsuranceAmt());
                ps.setObject(17, getDepositAmt());
                ps.setObject(18, getContractFee());
                ps.setObject(19, getMonthlyPay());
                ps.setObject(20, getPaystart());
                ps.setObject(21, getPayend());
                ps.setObject(22, getPhoto());
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
                "DELETE FROM xmachine " +
                "WHERE xmachine_id = " + getXmachineId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmachineId(new Integer(-getXmachineId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmachineId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachine_id,xlicensestat_id,tmvnr,descr,xmachtype_id,type2,reg_nr,oldreg_nr,licensedate,expdate,vehicleid_nr,engine_nr,chassis_nr,classify,insurance_nr,insurance_tp,insurance_amt,deposit_amt,contract_fee,monthly_pay,paystart,payend,photo FROM xmachine " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachine(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3),rs.getString(4),new Integer(rs.getInt(5)),rs.getString(6),rs.getString(7),rs.getString(8),rs.getDate(9),rs.getDate(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),rs.getString(16),new Integer(rs.getInt(17)),new Integer(rs.getInt(18)),new Integer(rs.getInt(19)),new Integer(rs.getInt(20)),rs.getDate(21),rs.getDate(22),rs.getObject(23)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmachine[] objects = new Xmachine[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmachine xmachine = (Xmachine) lst.get(i);
            objects[i] = xmachine;
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
        String stmt = "SELECT xmachine_id FROM xmachine " +
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
    //    return getXmachineId() + getDelimiter();
    //}

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws ForeignKeyViolationException {
        setWasChanged(this.xmachineId != null && this.xmachineId != xmachineId);
        this.xmachineId = xmachineId;
        setNew(xmachineId.intValue() == 0);
    }

    public Integer getXlicensestatId() {
        return xlicensestatId;
    }

    public void setXlicensestatId(Integer xlicensestatId) throws SQLException, ForeignKeyViolationException {
        if (xlicensestatId!=null && !Xlicensestat.exists(getConnection(),"xlicensestat_id = " + xlicensestatId)) {
            throw new ForeignKeyViolationException("Can't set xlicensestat_id, foreign key violation: xmachine_xlicensestat_fk");
        }
        setWasChanged(this.xlicensestatId != null && !this.xlicensestatId.equals(xlicensestatId));
        this.xlicensestatId = xlicensestatId;
    }

    public String getTmvnr() {
        return tmvnr;
    }

    public void setTmvnr(String tmvnr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.tmvnr != null && !this.tmvnr.equals(tmvnr));
        this.tmvnr = tmvnr;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.descr != null && !this.descr.equals(descr));
        this.descr = descr;
    }

    public Integer getXmachtypeId() {
        return xmachtypeId;
    }

    public void setXmachtypeId(Integer xmachtypeId) throws SQLException, ForeignKeyViolationException {
        if (xmachtypeId!=null && !Xmachtype.exists(getConnection(),"xmachtype_id = " + xmachtypeId)) {
            throw new ForeignKeyViolationException("Can't set xmachtype_id, foreign key violation: xmachine_xmachtype_fk");
        }
        setWasChanged(this.xmachtypeId != null && !this.xmachtypeId.equals(xmachtypeId));
        this.xmachtypeId = xmachtypeId;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.type2 != null && !this.type2.equals(type2));
        this.type2 = type2;
    }

    public String getRegNr() {
        return regNr;
    }

    public void setRegNr(String regNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.regNr != null && !this.regNr.equals(regNr));
        this.regNr = regNr;
    }

    public String getOldregNr() {
        return oldregNr;
    }

    public void setOldregNr(String oldregNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.oldregNr != null && !this.oldregNr.equals(oldregNr));
        this.oldregNr = oldregNr;
    }

    public Date getLicensedate() {
        return licensedate;
    }

    public void setLicensedate(Date licensedate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.licensedate != null && !this.licensedate.equals(licensedate));
        this.licensedate = licensedate;
    }

    public Date getExpdate() {
        return expdate;
    }

    public void setExpdate(Date expdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.expdate != null && !this.expdate.equals(expdate));
        this.expdate = expdate;
    }

    public String getVehicleidNr() {
        return vehicleidNr;
    }

    public void setVehicleidNr(String vehicleidNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.vehicleidNr != null && !this.vehicleidNr.equals(vehicleidNr));
        this.vehicleidNr = vehicleidNr;
    }

    public String getEngineNr() {
        return engineNr;
    }

    public void setEngineNr(String engineNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.engineNr != null && !this.engineNr.equals(engineNr));
        this.engineNr = engineNr;
    }

    public String getChassisNr() {
        return chassisNr;
    }

    public void setChassisNr(String chassisNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.chassisNr != null && !this.chassisNr.equals(chassisNr));
        this.chassisNr = chassisNr;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.classify != null && !this.classify.equals(classify));
        this.classify = classify;
    }

    public String getInsuranceNr() {
        return insuranceNr;
    }

    public void setInsuranceNr(String insuranceNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.insuranceNr != null && !this.insuranceNr.equals(insuranceNr));
        this.insuranceNr = insuranceNr;
    }

    public String getInsuranceTp() {
        return insuranceTp;
    }

    public void setInsuranceTp(String insuranceTp) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.insuranceTp != null && !this.insuranceTp.equals(insuranceTp));
        this.insuranceTp = insuranceTp;
    }

    public Integer getInsuranceAmt() {
        return insuranceAmt;
    }

    public void setInsuranceAmt(Integer insuranceAmt) throws SQLException, ForeignKeyViolationException {
        if (null != insuranceAmt)
            insuranceAmt = insuranceAmt == 0 ? null : insuranceAmt;
        setWasChanged(this.insuranceAmt != null && !this.insuranceAmt.equals(insuranceAmt));
        this.insuranceAmt = insuranceAmt;
    }

    public Integer getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(Integer depositAmt) throws SQLException, ForeignKeyViolationException {
        if (null != depositAmt)
            depositAmt = depositAmt == 0 ? null : depositAmt;
        setWasChanged(this.depositAmt != null && !this.depositAmt.equals(depositAmt));
        this.depositAmt = depositAmt;
    }

    public Integer getContractFee() {
        return contractFee;
    }

    public void setContractFee(Integer contractFee) throws SQLException, ForeignKeyViolationException {
        if (null != contractFee)
            contractFee = contractFee == 0 ? null : contractFee;
        setWasChanged(this.contractFee != null && !this.contractFee.equals(contractFee));
        this.contractFee = contractFee;
    }

    public Integer getMonthlyPay() {
        return monthlyPay;
    }

    public void setMonthlyPay(Integer monthlyPay) throws SQLException, ForeignKeyViolationException {
        if (null != monthlyPay)
            monthlyPay = monthlyPay == 0 ? null : monthlyPay;
        setWasChanged(this.monthlyPay != null && !this.monthlyPay.equals(monthlyPay));
        this.monthlyPay = monthlyPay;
    }

    public Date getPaystart() {
        return paystart;
    }

    public void setPaystart(Date paystart) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.paystart != null && !this.paystart.equals(paystart));
        this.paystart = paystart;
    }

    public Date getPayend() {
        return payend;
    }

    public void setPayend(Date payend) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.payend != null && !this.payend.equals(payend));
        this.payend = payend;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.photo != null && !this.photo.equals(photo));
        this.photo = photo;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[23];
        columnValues[0] = getXmachineId();
        columnValues[1] = getXlicensestatId();
        columnValues[2] = getTmvnr();
        columnValues[3] = getDescr();
        columnValues[4] = getXmachtypeId();
        columnValues[5] = getType2();
        columnValues[6] = getRegNr();
        columnValues[7] = getOldregNr();
        columnValues[8] = getLicensedate();
        columnValues[9] = getExpdate();
        columnValues[10] = getVehicleidNr();
        columnValues[11] = getEngineNr();
        columnValues[12] = getChassisNr();
        columnValues[13] = getClassify();
        columnValues[14] = getInsuranceNr();
        columnValues[15] = getInsuranceTp();
        columnValues[16] = getInsuranceAmt();
        columnValues[17] = getDepositAmt();
        columnValues[18] = getContractFee();
        columnValues[19] = getMonthlyPay();
        columnValues[20] = getPaystart();
        columnValues[21] = getPayend();
        columnValues[22] = getPhoto();
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
            setXmachineId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setXlicensestatId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXlicensestatId(null);
        }
        setTmvnr(flds[2]);
        setDescr(flds[3]);
        try {
            setXmachtypeId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXmachtypeId(null);
        }
        setType2(flds[5]);
        setRegNr(flds[6]);
        setOldregNr(flds[7]);
        setLicensedate(toDate(flds[8]));
        setExpdate(toDate(flds[9]));
        setVehicleidNr(flds[10]);
        setEngineNr(flds[11]);
        setChassisNr(flds[12]);
        setClassify(flds[13]);
        setInsuranceNr(flds[14]);
        setInsuranceTp(flds[15]);
        try {
            setInsuranceAmt(Integer.parseInt(flds[16]));
        } catch(NumberFormatException ne) {
            setInsuranceAmt(null);
        }
        try {
            setDepositAmt(Integer.parseInt(flds[17]));
        } catch(NumberFormatException ne) {
            setDepositAmt(null);
        }
        try {
            setContractFee(Integer.parseInt(flds[18]));
        } catch(NumberFormatException ne) {
            setContractFee(null);
        }
        try {
            setMonthlyPay(Integer.parseInt(flds[19]));
        } catch(NumberFormatException ne) {
            setMonthlyPay(null);
        }
        setPaystart(toDate(flds[20]));
        setPayend(toDate(flds[21]));
        setPhoto(flds[22]);
    }
}