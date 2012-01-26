// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jan 26 10:16:04 EET 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xemployee extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xemployeeId = null;
    private String clockNum = null;
    private String firstName = null;
    private String surName = null;
    private String nickName = null;
    private String idNum = null;
    private String phone0Num = null;
    private String phone1Num = null;
    private String phone2Num = null;
    private String relation1 = null;
    private String relation2 = null;
    private String address = null;
    private Integer contractLen = null;
    private Date contractStart = null;
    private Date contractEnd = null;
    private Integer rate = null;
    private Integer xpositionId = null;
    private String taxnum = null;
    private Object photo = null;
    private Object photo2 = null;

    public Xemployee(Connection connection) {
        super(connection, "xemployee", "xemployee_id");
        setColumnNames(new String[]{"xemployee_id", "clock_num", "first_name", "sur_name", "nick_name", "id_num", "phone0_num", "phone1_num", "phone2_num", "relation1", "relation2", "address", "contract_len", "contract_start", "contract_end", "rate", "xposition_id", "taxnum", "photo", "photo2"});
    }

    public Xemployee(Connection connection, Integer xemployeeId, String clockNum, String firstName, String surName, String nickName, String idNum, String phone0Num, String phone1Num, String phone2Num, String relation1, String relation2, String address, Integer contractLen, Date contractStart, Date contractEnd, Integer rate, Integer xpositionId, String taxnum, Object photo, Object photo2) {
        super(connection, "xemployee", "xemployee_id");
        setNew(xemployeeId.intValue() <= 0);
//        if (xemployeeId.intValue() != 0) {
            this.xemployeeId = xemployeeId;
//        }
        this.clockNum = clockNum;
        this.firstName = firstName;
        this.surName = surName;
        this.nickName = nickName;
        this.idNum = idNum;
        this.phone0Num = phone0Num;
        this.phone1Num = phone1Num;
        this.phone2Num = phone2Num;
        this.relation1 = relation1;
        this.relation2 = relation2;
        this.address = address;
        this.contractLen = contractLen;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.rate = rate;
        this.xpositionId = xpositionId;
        this.taxnum = taxnum;
        this.photo = photo;
        this.photo2 = photo2;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xemployee xemployee = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xemployee_id,clock_num,first_name,sur_name,nick_name,id_num,phone0_num,phone1_num,phone2_num,relation1,relation2,address,contract_len,contract_start,contract_end,rate,xposition_id,taxnum,photo,photo2 FROM xemployee WHERE xemployee_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xemployee = new Xemployee(getConnection());
                xemployee.setXemployeeId(new Integer(rs.getInt(1)));
                xemployee.setClockNum(rs.getString(2));
                xemployee.setFirstName(rs.getString(3));
                xemployee.setSurName(rs.getString(4));
                xemployee.setNickName(rs.getString(5));
                xemployee.setIdNum(rs.getString(6));
                xemployee.setPhone0Num(rs.getString(7));
                xemployee.setPhone1Num(rs.getString(8));
                xemployee.setPhone2Num(rs.getString(9));
                xemployee.setRelation1(rs.getString(10));
                xemployee.setRelation2(rs.getString(11));
                xemployee.setAddress(rs.getString(12));
                xemployee.setContractLen(new Integer(rs.getInt(13)));
                xemployee.setContractStart(rs.getDate(14));
                xemployee.setContractEnd(rs.getDate(15));
                xemployee.setRate(new Integer(rs.getInt(16)));
                xemployee.setXpositionId(new Integer(rs.getInt(17)));
                xemployee.setTaxnum(rs.getString(18));
                xemployee.setPhoto(rs.getObject(19));
                xemployee.setPhoto2(rs.getObject(20));
                xemployee.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xemployee;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xemployee ("+(getXemployeeId().intValue()!=0?"xemployee_id,":"")+"clock_num,first_name,sur_name,nick_name,id_num,phone0_num,phone1_num,phone2_num,relation1,relation2,address,contract_len,contract_start,contract_end,rate,xposition_id,taxnum,photo,photo2) values("+(getXemployeeId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXemployeeId().intValue()!=0) {
                 ps.setObject(++n, getXemployeeId());
             }
             ps.setObject(++n, getClockNum());
             ps.setObject(++n, getFirstName());
             ps.setObject(++n, getSurName());
             ps.setObject(++n, getNickName());
             ps.setObject(++n, getIdNum());
             ps.setObject(++n, getPhone0Num());
             ps.setObject(++n, getPhone1Num());
             ps.setObject(++n, getPhone2Num());
             ps.setObject(++n, getRelation1());
             ps.setObject(++n, getRelation2());
             ps.setObject(++n, getAddress());
             ps.setObject(++n, getContractLen());
             ps.setObject(++n, getContractStart());
             ps.setObject(++n, getContractEnd());
             ps.setObject(++n, getRate());
             ps.setObject(++n, getXpositionId());
             ps.setObject(++n, getTaxnum());
             ps.setObject(++n, getPhoto());
             ps.setObject(++n, getPhoto2());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXemployeeId().intValue()==0) {
             stmt = "SELECT max(xemployee_id) FROM xemployee";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXemployeeId(new Integer(rs.getInt(1)));
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
                    "UPDATE xemployee " +
                    "SET clock_num = ?, first_name = ?, sur_name = ?, nick_name = ?, id_num = ?, phone0_num = ?, phone1_num = ?, phone2_num = ?, relation1 = ?, relation2 = ?, address = ?, contract_len = ?, contract_start = ?, contract_end = ?, rate = ?, xposition_id = ?, taxnum = ?, photo = ?, photo2 = ?" + 
                    " WHERE xemployee_id = " + getXemployeeId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getClockNum());
                ps.setObject(2, getFirstName());
                ps.setObject(3, getSurName());
                ps.setObject(4, getNickName());
                ps.setObject(5, getIdNum());
                ps.setObject(6, getPhone0Num());
                ps.setObject(7, getPhone1Num());
                ps.setObject(8, getPhone2Num());
                ps.setObject(9, getRelation1());
                ps.setObject(10, getRelation2());
                ps.setObject(11, getAddress());
                ps.setObject(12, getContractLen());
                ps.setObject(13, getContractStart());
                ps.setObject(14, getContractEnd());
                ps.setObject(15, getRate());
                ps.setObject(16, getXpositionId());
                ps.setObject(17, getTaxnum());
                ps.setObject(18, getPhoto());
                ps.setObject(19, getPhoto2());
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
        if (Xbreakdown.exists(getConnection(),"attendedby_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbreakdown_xemployee_fk3");
        }
        if (Xbreakdown.exists(getConnection(),"reportedby_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbreakdown_xemployee_fk2");
        }
        if (Xdieselcard.exists(getConnection(),"personiss_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdieselcard_xemployee_fk2");
        }
        if (Xmachineonsite.exists(getConnection(),"xemployee_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xmachineonsite_xemployee_fk");
        }
        if (Xconsume.exists(getConnection(),"authorizer_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xconsume_xemployee_fk2");
        }
        if (Xdieselpchs.exists(getConnection(),"authorizer_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdieselpchs_xemployee_fk");
        }
        if (Xdieselpchs.exists(getConnection(),"paidby_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdieselpchs_xemployee_fk2");
        }
        if (Xconsume.exists(getConnection(),"collector_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xconsume_xemployee_fk3");
        }
        if (Xconsume.exists(getConnection(),"payer_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xconsume_xemployee_fk4");
        }
        if (Xbreakdown.exists(getConnection(),"reportedto_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbreakdown_xemployee_fk");
        }
        if (Xbreakdown.exists(getConnection(),"operator_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbreakdown_xemployee_fk4");
        }
        if (Xdieselcard.exists(getConnection(),"operator_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xdieselcard_xemployee_fk");
        }
        if (Xconsume.exists(getConnection(),"requester_id = " + getXemployeeId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xconsume_xemployee_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xtimesheet
            Xtimesheet[] records = (Xtimesheet[])Xtimesheet.load(getConnection(),"xemployee_id = " + getXemployeeId(),null);
            for (int i = 0; i<records.length; i++) {
                Xtimesheet xtimesheet = records[i];
                xtimesheet.delete();
            }
        }
        {// delete cascade from xwagesumitem
            Xwagesumitem[] records = (Xwagesumitem[])Xwagesumitem.load(getConnection(),"xemployee_id = " + getXemployeeId(),null);
            for (int i = 0; i<records.length; i++) {
                Xwagesumitem xwagesumitem = records[i];
                xwagesumitem.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xemployee " +
                "WHERE xemployee_id = " + getXemployeeId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXemployeeId(new Integer(-getXemployeeId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXemployeeId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xemployee_id,clock_num,first_name,sur_name,nick_name,id_num,phone0_num,phone1_num,phone2_num,relation1,relation2,address,contract_len,contract_start,contract_end,rate,xposition_id,taxnum,photo,photo2 FROM xemployee " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xemployee(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),new Integer(rs.getInt(13)),rs.getDate(14),rs.getDate(15),new Integer(rs.getInt(16)),new Integer(rs.getInt(17)),rs.getString(18),rs.getObject(19),rs.getObject(20)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xemployee[] objects = new Xemployee[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xemployee xemployee = (Xemployee) lst.get(i);
            objects[i] = xemployee;
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
        String stmt = "SELECT xemployee_id FROM xemployee " +
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
    //    return getXemployeeId() + getDelimiter();
    //}

    public Integer getXemployeeId() {
        return xemployeeId;
    }

    public void setXemployeeId(Integer xemployeeId) throws ForeignKeyViolationException {
        setWasChanged(this.xemployeeId != null && this.xemployeeId != xemployeeId);
        this.xemployeeId = xemployeeId;
        setNew(xemployeeId.intValue() == 0);
    }

    public String getClockNum() {
        return clockNum;
    }

    public void setClockNum(String clockNum) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.clockNum != null && !this.clockNum.equals(clockNum));
        this.clockNum = clockNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.firstName != null && !this.firstName.equals(firstName));
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.surName != null && !this.surName.equals(surName));
        this.surName = surName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.nickName != null && !this.nickName.equals(nickName));
        this.nickName = nickName;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.idNum != null && !this.idNum.equals(idNum));
        this.idNum = idNum;
    }

    public String getPhone0Num() {
        return phone0Num;
    }

    public void setPhone0Num(String phone0Num) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.phone0Num != null && !this.phone0Num.equals(phone0Num));
        this.phone0Num = phone0Num;
    }

    public String getPhone1Num() {
        return phone1Num;
    }

    public void setPhone1Num(String phone1Num) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.phone1Num != null && !this.phone1Num.equals(phone1Num));
        this.phone1Num = phone1Num;
    }

    public String getPhone2Num() {
        return phone2Num;
    }

    public void setPhone2Num(String phone2Num) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.phone2Num != null && !this.phone2Num.equals(phone2Num));
        this.phone2Num = phone2Num;
    }

    public String getRelation1() {
        return relation1;
    }

    public void setRelation1(String relation1) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.relation1 != null && !this.relation1.equals(relation1));
        this.relation1 = relation1;
    }

    public String getRelation2() {
        return relation2;
    }

    public void setRelation2(String relation2) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.relation2 != null && !this.relation2.equals(relation2));
        this.relation2 = relation2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.address != null && !this.address.equals(address));
        this.address = address;
    }

    public Integer getContractLen() {
        return contractLen;
    }

    public void setContractLen(Integer contractLen) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contractLen != null && !this.contractLen.equals(contractLen));
        this.contractLen = contractLen;
    }

    public Date getContractStart() {
        return contractStart;
    }

    public void setContractStart(Date contractStart) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contractStart != null && !this.contractStart.equals(contractStart));
        this.contractStart = contractStart;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contractEnd != null && !this.contractEnd.equals(contractEnd));
        this.contractEnd = contractEnd;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.rate != null && !this.rate.equals(rate));
        this.rate = rate;
    }

    public Integer getXpositionId() {
        return xpositionId;
    }

    public void setXpositionId(Integer xpositionId) throws SQLException, ForeignKeyViolationException {
        if (null != xpositionId)
            xpositionId = xpositionId == 0 ? null : xpositionId;
        if (xpositionId!=null && !Xposition.exists(getConnection(),"xposition_id = " + xpositionId)) {
            throw new ForeignKeyViolationException("Can't set xposition_id, foreign key violation: xemployee_xposition_fk");
        }
        setWasChanged(this.xpositionId != null && !this.xpositionId.equals(xpositionId));
        this.xpositionId = xpositionId;
    }

    public String getTaxnum() {
        return taxnum;
    }

    public void setTaxnum(String taxnum) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.taxnum != null && !this.taxnum.equals(taxnum));
        this.taxnum = taxnum;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.photo != null && !this.photo.equals(photo));
        this.photo = photo;
    }

    public Object getPhoto2() {
        return photo2;
    }

    public void setPhoto2(Object photo2) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.photo2 != null && !this.photo2.equals(photo2));
        this.photo2 = photo2;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[20];
        columnValues[0] = getXemployeeId();
        columnValues[1] = getClockNum();
        columnValues[2] = getFirstName();
        columnValues[3] = getSurName();
        columnValues[4] = getNickName();
        columnValues[5] = getIdNum();
        columnValues[6] = getPhone0Num();
        columnValues[7] = getPhone1Num();
        columnValues[8] = getPhone2Num();
        columnValues[9] = getRelation1();
        columnValues[10] = getRelation2();
        columnValues[11] = getAddress();
        columnValues[12] = getContractLen();
        columnValues[13] = getContractStart();
        columnValues[14] = getContractEnd();
        columnValues[15] = getRate();
        columnValues[16] = getXpositionId();
        columnValues[17] = getTaxnum();
        columnValues[18] = getPhoto();
        columnValues[19] = getPhoto2();
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
            setXemployeeId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXemployeeId(null);
        }
        setClockNum(flds[1]);
        setFirstName(flds[2]);
        setSurName(flds[3]);
        setNickName(flds[4]);
        setIdNum(flds[5]);
        setPhone0Num(flds[6]);
        setPhone1Num(flds[7]);
        setPhone2Num(flds[8]);
        setRelation1(flds[9]);
        setRelation2(flds[10]);
        setAddress(flds[11]);
        try {
            setContractLen(Integer.parseInt(flds[12]));
        } catch(NumberFormatException ne) {
            setContractLen(null);
        }
        setContractStart(toDate(flds[13]));
        setContractEnd(toDate(flds[14]));
        try {
            setRate(Integer.parseInt(flds[15]));
        } catch(NumberFormatException ne) {
            setRate(null);
        }
        try {
            setXpositionId(Integer.parseInt(flds[16]));
        } catch(NumberFormatException ne) {
            setXpositionId(null);
        }
        setTaxnum(flds[17]);
        setPhoto(flds[18]);
        setPhoto2(flds[19]);
    }
}
