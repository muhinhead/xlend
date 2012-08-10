// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Aug 10 08:49:50 EEST 2012
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
    private String tmvnr = null;
    private Integer xmachtypeId = null;
    private Integer xmachtype2Id = null;
    private String regNr = null;
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
        setColumnNames(new String[]{"xmachine_id", "tmvnr", "xmachtype_id", "xmachtype2_id", "reg_nr", "expdate", "vehicleid_nr", "engine_nr", "chassis_nr", "classify", "insurance_nr", "insurance_tp", "insurance_amt", "deposit_amt", "contract_fee", "monthly_pay", "paystart", "payend", "photo"});
    }

    public Xmachine(Connection connection, Integer xmachineId, String tmvnr, Integer xmachtypeId, Integer xmachtype2Id, String regNr, Date expdate, String vehicleidNr, String engineNr, String chassisNr, String classify, String insuranceNr, String insuranceTp, Integer insuranceAmt, Integer depositAmt, Integer contractFee, Integer monthlyPay, Date paystart, Date payend, Object photo) {
        super(connection, "xmachine", "xmachine_id");
        setNew(xmachineId.intValue() <= 0);
//        if (xmachineId.intValue() != 0) {
            this.xmachineId = xmachineId;
//        }
        this.tmvnr = tmvnr;
        this.xmachtypeId = xmachtypeId;
        this.xmachtype2Id = xmachtype2Id;
        this.regNr = regNr;
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
        String stmt = "SELECT xmachine_id,tmvnr,xmachtype_id,xmachtype2_id,reg_nr,expdate,vehicleid_nr,engine_nr,chassis_nr,classify,insurance_nr,insurance_tp,insurance_amt,deposit_amt,contract_fee,monthly_pay,paystart,payend,photo FROM xmachine WHERE xmachine_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachine = new Xmachine(getConnection());
                xmachine.setXmachineId(new Integer(rs.getInt(1)));
                xmachine.setTmvnr(rs.getString(2));
                xmachine.setXmachtypeId(new Integer(rs.getInt(3)));
                xmachine.setXmachtype2Id(new Integer(rs.getInt(4)));
                xmachine.setRegNr(rs.getString(5));
                xmachine.setExpdate(rs.getDate(6));
                xmachine.setVehicleidNr(rs.getString(7));
                xmachine.setEngineNr(rs.getString(8));
                xmachine.setChassisNr(rs.getString(9));
                xmachine.setClassify(rs.getString(10));
                xmachine.setInsuranceNr(rs.getString(11));
                xmachine.setInsuranceTp(rs.getString(12));
                xmachine.setInsuranceAmt(new Integer(rs.getInt(13)));
                xmachine.setDepositAmt(new Integer(rs.getInt(14)));
                xmachine.setContractFee(new Integer(rs.getInt(15)));
                xmachine.setMonthlyPay(new Integer(rs.getInt(16)));
                xmachine.setPaystart(rs.getDate(17));
                xmachine.setPayend(rs.getDate(18));
                xmachine.setPhoto(rs.getObject(19));
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
                "INSERT INTO xmachine ("+(getXmachineId().intValue()!=0?"xmachine_id,":"")+"tmvnr,xmachtype_id,xmachtype2_id,reg_nr,expdate,vehicleid_nr,engine_nr,chassis_nr,classify,insurance_nr,insurance_tp,insurance_amt,deposit_amt,contract_fee,monthly_pay,paystart,payend,photo) values("+(getXmachineId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachineId().intValue()!=0) {
                 ps.setObject(++n, getXmachineId());
             }
             ps.setObject(++n, getTmvnr());
             ps.setObject(++n, getXmachtypeId());
             ps.setObject(++n, getXmachtype2Id());
             ps.setObject(++n, getRegNr());
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
                    "SET tmvnr = ?, xmachtype_id = ?, xmachtype2_id = ?, reg_nr = ?, expdate = ?, vehicleid_nr = ?, engine_nr = ?, chassis_nr = ?, classify = ?, insurance_nr = ?, insurance_tp = ?, insurance_amt = ?, deposit_amt = ?, contract_fee = ?, monthly_pay = ?, paystart = ?, payend = ?, photo = ?" + 
                    " WHERE xmachine_id = " + getXmachineId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getTmvnr());
                ps.setObject(2, getXmachtypeId());
                ps.setObject(3, getXmachtype2Id());
                ps.setObject(4, getRegNr());
                ps.setObject(5, getExpdate());
                ps.setObject(6, getVehicleidNr());
                ps.setObject(7, getEngineNr());
                ps.setObject(8, getChassisNr());
                ps.setObject(9, getClassify());
                ps.setObject(10, getInsuranceNr());
                ps.setObject(11, getInsuranceTp());
                ps.setObject(12, getInsuranceAmt());
                ps.setObject(13, getDepositAmt());
                ps.setObject(14, getContractFee());
                ps.setObject(15, getMonthlyPay());
                ps.setObject(16, getPaystart());
                ps.setObject(17, getPayend());
                ps.setObject(18, getPhoto());
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
        if (Xjobcard.exists(getConnection(),"machine_id1_day6 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk16");
        }
        if (Xjobcard.exists(getConnection(),"machine_id1_day7 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk17");
        }
        if (Xhourcompare.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xhourcompare_xmachine_fk");
        }
        if (Xbookouts.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbookouts_xmachine_fk");
        }
        if (Xabsenteeism.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xabsenteeism_xmachine_fk");
        }
        if (Xjobcard.exists(getConnection(),"machine_id5_day2 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk52");
        }
        if (Xjobcard.exists(getConnection(),"machine_id5_day3 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk53");
        }
        if (Xlowbed.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xlowbed_xmachine_fk");
        }
        if (Xjobcard.exists(getConnection(),"machine_id5_day4 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk54");
        }
        if (Xjobcard.exists(getConnection(),"machine_id5_day5 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk55");
        }
        if (Xjobcard.exists(getConnection(),"machine_id5_day6 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk56");
        }
        if (Xjobcard.exists(getConnection(),"machine_id5_day7 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk57");
        }
        if (Xjobcard.exists(getConnection(),"machine_id1_day1 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk11");
        }
        if (Xjobcard.exists(getConnection(),"machine_id1_day3 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk13");
        }
        if (Xjobcard.exists(getConnection(),"machine_id1_day2 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk12");
        }
        if (Xjobcard.exists(getConnection(),"machine_id1_day5 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk15");
        }
        if (Xjobcard.exists(getConnection(),"machine_id5_day1 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk51");
        }
        if (Xtripsheetpart.exists(getConnection(),"loaded1_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripsheetpart_xmachine_fk");
        }
        if (Xjobcard.exists(getConnection(),"machine_id1_day4 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk14");
        }
        if (Xsitediarypart.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xsitediarypart_xmachine_fk");
        }
        if (Xjobcard.exists(getConnection(),"machine_id4_day3 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk43");
        }
        if (Xjobcard.exists(getConnection(),"machine_id4_day4 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk44");
        }
        if (Xjobcard.exists(getConnection(),"machine_id4_day1 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk41");
        }
        if (Xjobcard.exists(getConnection(),"machine_id4_day2 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk42");
        }
        if (Xjobcard.exists(getConnection(),"machine_id4_day7 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk47");
        }
        if (Xjobcard.exists(getConnection(),"machine_id4_day5 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk45");
        }
        if (Xjobcard.exists(getConnection(),"machine_id4_day6 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk46");
        }
        if (Xtrip.exists(getConnection(),"withmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtrip_xmachine_fk2");
        }
        if (Xopmachassing.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xopmachassing_xmachine_fk");
        }
        if (Xconsume.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xconsume_xmachine_fk");
        }
        if (Xissuing.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xissuing_xmachine_fk");
        }
        if (Xbreakdown.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbreakdown_xmachine_fk");
        }
        if (Xopclocksheet.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xopclocksheet_xmachine_fk");
        }
        if (Xjobcard.exists(getConnection(),"machine_id3_day5 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk35");
        }
        if (Xjobcard.exists(getConnection(),"machine_id3_day4 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk34");
        }
        if (Xjobcard.exists(getConnection(),"machine_id3_day7 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk37");
        }
        if (Xjobcard.exists(getConnection(),"machine_id3_day6 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk36");
        }
        if (Xtransscheduleitm.exists(getConnection(),"machine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtransscheduleitm_xmachine_fk");
        }
        if (Xjobcard.exists(getConnection(),"machine_id3_day1 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk31");
        }
        if (Xjobcard.exists(getConnection(),"machine_id3_day3 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk33");
        }
        if (Xjobcard.exists(getConnection(),"machine_id3_day2 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk32");
        }
        if (Xjobcard.exists(getConnection(),"machine_id2_day7 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk27");
        }
        if (Xtrip.exists(getConnection(),"machine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtrip_xmachine_fk");
        }
        if (Xbreakdown.exists(getConnection(),"vehicle_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbreakdown_xmachine_fk2");
        }
        if (Xtripsheetpart.exists(getConnection(),"loaded2_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xtripsheetpart_xmachine_fk2");
        }
        if (Xdieselcard.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdieselcard_xmachine_fk");
        }
        if (Xjobcard.exists(getConnection(),"machine_id2_day6 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk26");
        }
        if (Xjobcard.exists(getConnection(),"machine_id2_day5 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk25");
        }
        if (Xjobcard.exists(getConnection(),"machine_id2_day4 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk24");
        }
        if (Xjobcard.exists(getConnection(),"machine_id2_day3 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk23");
        }
        if (Xjobcard.exists(getConnection(),"machine_id2_day2 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk22");
        }
        if (Xjobcard.exists(getConnection(),"machine_id2_day1 = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xjobcard_xmachine_fk21");
        }
        if (Xsitediaryitem.exists(getConnection(),"xmachine_id = " + getXmachineId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xsitediaryitem_xmachine_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xmachineincident
            Xmachineincident[] records = (Xmachineincident[])Xmachineincident.load(getConnection(),"xmachine_id = " + getXmachineId(),null);
            for (int i = 0; i<records.length; i++) {
                Xmachineincident xmachineincident = records[i];
                xmachineincident.delete();
            }
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
        String stmt = "SELECT xmachine_id,tmvnr,xmachtype_id,xmachtype2_id,reg_nr,expdate,vehicleid_nr,engine_nr,chassis_nr,classify,insurance_nr,insurance_tp,insurance_amt,deposit_amt,contract_fee,monthly_pay,paystart,payend,photo FROM xmachine " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachine(con,new Integer(rs.getInt(1)),rs.getString(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getString(5),rs.getDate(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),new Integer(rs.getInt(13)),new Integer(rs.getInt(14)),new Integer(rs.getInt(15)),new Integer(rs.getInt(16)),rs.getDate(17),rs.getDate(18),rs.getObject(19)));
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

    public String getTmvnr() {
        return tmvnr;
    }

    public void setTmvnr(String tmvnr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.tmvnr != null && !this.tmvnr.equals(tmvnr));
        this.tmvnr = tmvnr;
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

    public Integer getXmachtype2Id() {
        return xmachtype2Id;
    }

    public void setXmachtype2Id(Integer xmachtype2Id) throws SQLException, ForeignKeyViolationException {
        if (null != xmachtype2Id)
            xmachtype2Id = xmachtype2Id == 0 ? null : xmachtype2Id;
        setWasChanged(this.xmachtype2Id != null && !this.xmachtype2Id.equals(xmachtype2Id));
        this.xmachtype2Id = xmachtype2Id;
    }

    public String getRegNr() {
        return regNr;
    }

    public void setRegNr(String regNr) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.regNr != null && !this.regNr.equals(regNr));
        this.regNr = regNr;
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
        Object[] columnValues = new Object[19];
        columnValues[0] = getXmachineId();
        columnValues[1] = getTmvnr();
        columnValues[2] = getXmachtypeId();
        columnValues[3] = getXmachtype2Id();
        columnValues[4] = getRegNr();
        columnValues[5] = getExpdate();
        columnValues[6] = getVehicleidNr();
        columnValues[7] = getEngineNr();
        columnValues[8] = getChassisNr();
        columnValues[9] = getClassify();
        columnValues[10] = getInsuranceNr();
        columnValues[11] = getInsuranceTp();
        columnValues[12] = getInsuranceAmt();
        columnValues[13] = getDepositAmt();
        columnValues[14] = getContractFee();
        columnValues[15] = getMonthlyPay();
        columnValues[16] = getPaystart();
        columnValues[17] = getPayend();
        columnValues[18] = getPhoto();
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
        setTmvnr(flds[1]);
        try {
            setXmachtypeId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXmachtypeId(null);
        }
        try {
            setXmachtype2Id(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXmachtype2Id(null);
        }
        setRegNr(flds[4]);
        setExpdate(toDate(flds[5]));
        setVehicleidNr(flds[6]);
        setEngineNr(flds[7]);
        setChassisNr(flds[8]);
        setClassify(flds[9]);
        setInsuranceNr(flds[10]);
        setInsuranceTp(flds[11]);
        try {
            setInsuranceAmt(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setInsuranceAmt(null);
        }
        try {
            setDepositAmt(Integer.parseInt(flds[13]));
        } catch(NumberFormatException ne) {
            setDepositAmt(null);
        }
        try {
            setContractFee(Integer.parseInt(flds[14]));
        } catch(NumberFormatException ne) {
            setContractFee(null);
        }
        try {
            setMonthlyPay(Integer.parseInt(flds[15]));
        } catch(NumberFormatException ne) {
            setMonthlyPay(null);
        }
        setPaystart(toDate(flds[16]));
        setPayend(toDate(flds[17]));
        setPhoto(flds[18]);
    }
}
