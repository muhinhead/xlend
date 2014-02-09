// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jan 30 18:31:18 FET 2014
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xtransscheduleitm extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xtransscheduleitmId = null;
    private Integer machineId = null;
    private Integer siteFromId = null;
    private Integer siteToId = null;
    private Date dateRequired = null;
    private Date dateMove = null;
    private Integer lowbedId = null;
    private Integer operatorId = null;
    private Integer isCompleted = null;

    public Xtransscheduleitm(Connection connection) {
        super(connection, "xtransscheduleitm", "xtransscheduleitm_id");
        setColumnNames(new String[]{"xtransscheduleitm_id", "machine_id", "site_from_id", "site_to_id", "date_required", "date_move", "lowbed_id", "operator_id", "is_completed"});
    }

    public Xtransscheduleitm(Connection connection, Integer xtransscheduleitmId, Integer machineId, Integer siteFromId, Integer siteToId, Date dateRequired, Date dateMove, Integer lowbedId, Integer operatorId, Integer isCompleted) {
        super(connection, "xtransscheduleitm", "xtransscheduleitm_id");
        setNew(xtransscheduleitmId.intValue() <= 0);
//        if (xtransscheduleitmId.intValue() != 0) {
            this.xtransscheduleitmId = xtransscheduleitmId;
//        }
        this.machineId = machineId;
        this.siteFromId = siteFromId;
        this.siteToId = siteToId;
        this.dateRequired = dateRequired;
        this.dateMove = dateMove;
        this.lowbedId = lowbedId;
        this.operatorId = operatorId;
        this.isCompleted = isCompleted;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtransscheduleitm xtransscheduleitm = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtransscheduleitm_id,machine_id,site_from_id,site_to_id,date_required,date_move,lowbed_id,operator_id,is_completed FROM xtransscheduleitm WHERE xtransscheduleitm_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtransscheduleitm = new Xtransscheduleitm(getConnection());
                xtransscheduleitm.setXtransscheduleitmId(new Integer(rs.getInt(1)));
                xtransscheduleitm.setMachineId(new Integer(rs.getInt(2)));
                xtransscheduleitm.setSiteFromId(new Integer(rs.getInt(3)));
                xtransscheduleitm.setSiteToId(new Integer(rs.getInt(4)));
                xtransscheduleitm.setDateRequired(rs.getDate(5));
                xtransscheduleitm.setDateMove(rs.getDate(6));
                xtransscheduleitm.setLowbedId(new Integer(rs.getInt(7)));
                xtransscheduleitm.setOperatorId(new Integer(rs.getInt(8)));
                xtransscheduleitm.setIsCompleted(new Integer(rs.getInt(9)));
                xtransscheduleitm.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xtransscheduleitm;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xtransscheduleitm ("+(getXtransscheduleitmId().intValue()!=0?"xtransscheduleitm_id,":"")+"machine_id,site_from_id,site_to_id,date_required,date_move,lowbed_id,operator_id,is_completed) values("+(getXtransscheduleitmId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtransscheduleitmId().intValue()!=0) {
                 ps.setObject(++n, getXtransscheduleitmId());
             }
             ps.setObject(++n, getMachineId());
             ps.setObject(++n, getSiteFromId());
             ps.setObject(++n, getSiteToId());
             ps.setObject(++n, getDateRequired());
             ps.setObject(++n, getDateMove());
             ps.setObject(++n, getLowbedId());
             ps.setObject(++n, getOperatorId());
             ps.setObject(++n, getIsCompleted());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXtransscheduleitmId().intValue()==0) {
             stmt = "SELECT max(xtransscheduleitm_id) FROM xtransscheduleitm";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXtransscheduleitmId(new Integer(rs.getInt(1)));
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
                    "UPDATE xtransscheduleitm " +
                    "SET machine_id = ?, site_from_id = ?, site_to_id = ?, date_required = ?, date_move = ?, lowbed_id = ?, operator_id = ?, is_completed = ?" + 
                    " WHERE xtransscheduleitm_id = " + getXtransscheduleitmId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getMachineId());
                ps.setObject(2, getSiteFromId());
                ps.setObject(3, getSiteToId());
                ps.setObject(4, getDateRequired());
                ps.setObject(5, getDateMove());
                ps.setObject(6, getLowbedId());
                ps.setObject(7, getOperatorId());
                ps.setObject(8, getIsCompleted());
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
                "DELETE FROM xtransscheduleitm " +
                "WHERE xtransscheduleitm_id = " + getXtransscheduleitmId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXtransscheduleitmId(new Integer(-getXtransscheduleitmId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXtransscheduleitmId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtransscheduleitm_id,machine_id,site_from_id,site_to_id,date_required,date_move,lowbed_id,operator_id,is_completed FROM xtransscheduleitm " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtransscheduleitm(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDate(5),rs.getDate(6),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),new Integer(rs.getInt(9))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xtransscheduleitm[] objects = new Xtransscheduleitm[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xtransscheduleitm xtransscheduleitm = (Xtransscheduleitm) lst.get(i);
            objects[i] = xtransscheduleitm;
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
        String stmt = "SELECT xtransscheduleitm_id FROM xtransscheduleitm " +
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
    //    return getXtransscheduleitmId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xtransscheduleitmId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXtransscheduleitmId(id);
        setNew(prevIsNew);
    }

    public Integer getXtransscheduleitmId() {
        return xtransscheduleitmId;
    }

    public void setXtransscheduleitmId(Integer xtransscheduleitmId) throws ForeignKeyViolationException {
        setWasChanged(this.xtransscheduleitmId != null && this.xtransscheduleitmId != xtransscheduleitmId);
        this.xtransscheduleitmId = xtransscheduleitmId;
        setNew(xtransscheduleitmId.intValue() == 0);
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) throws SQLException, ForeignKeyViolationException {
        if (machineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + machineId)) {
            throw new ForeignKeyViolationException("Can't set machine_id, foreign key violation: xtransscheduleitm_xmachine_fk");
        }
        setWasChanged(this.machineId != null && !this.machineId.equals(machineId));
        this.machineId = machineId;
    }

    public Integer getSiteFromId() {
        return siteFromId;
    }

    public void setSiteFromId(Integer siteFromId) throws SQLException, ForeignKeyViolationException {
        if (siteFromId!=null && !Xsite.exists(getConnection(),"xsite_id = " + siteFromId)) {
            throw new ForeignKeyViolationException("Can't set site_from_id, foreign key violation: xtransscheduleitm_xsite_fk");
        }
        setWasChanged(this.siteFromId != null && !this.siteFromId.equals(siteFromId));
        this.siteFromId = siteFromId;
    }

    public Integer getSiteToId() {
        return siteToId;
    }

    public void setSiteToId(Integer siteToId) throws SQLException, ForeignKeyViolationException {
        if (siteToId!=null && !Xsite.exists(getConnection(),"xsite_id = " + siteToId)) {
            throw new ForeignKeyViolationException("Can't set site_to_id, foreign key violation: xtransscheduleitm_xsite_fk2");
        }
        setWasChanged(this.siteToId != null && !this.siteToId.equals(siteToId));
        this.siteToId = siteToId;
    }

    public Date getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(Date dateRequired) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dateRequired != null && !this.dateRequired.equals(dateRequired));
        this.dateRequired = dateRequired;
    }

    public Date getDateMove() {
        return dateMove;
    }

    public void setDateMove(Date dateMove) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dateMove != null && !this.dateMove.equals(dateMove));
        this.dateMove = dateMove;
    }

    public Integer getLowbedId() {
        return lowbedId;
    }

    public void setLowbedId(Integer lowbedId) throws SQLException, ForeignKeyViolationException {
        if (null != lowbedId)
            lowbedId = lowbedId == 0 ? null : lowbedId;
        if (lowbedId!=null && !Xlowbed.exists(getConnection(),"xlowbed_id = " + lowbedId)) {
            throw new ForeignKeyViolationException("Can't set lowbed_id, foreign key violation: xtransscheduleitm_xmachine_fk2");
        }
        setWasChanged(this.lowbedId != null && !this.lowbedId.equals(lowbedId));
        this.lowbedId = lowbedId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) throws SQLException, ForeignKeyViolationException {
        if (null != operatorId)
            operatorId = operatorId == 0 ? null : operatorId;
        if (operatorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + operatorId)) {
            throw new ForeignKeyViolationException("Can't set operator_id, foreign key violation: xtransscheduleitm_xemployee_fk");
        }
        setWasChanged(this.operatorId != null && !this.operatorId.equals(operatorId));
        this.operatorId = operatorId;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) throws SQLException, ForeignKeyViolationException {
        if (null != isCompleted)
            isCompleted = isCompleted == 0 ? null : isCompleted;
        setWasChanged(this.isCompleted != null && !this.isCompleted.equals(isCompleted));
        this.isCompleted = isCompleted;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[9];
        columnValues[0] = getXtransscheduleitmId();
        columnValues[1] = getMachineId();
        columnValues[2] = getSiteFromId();
        columnValues[3] = getSiteToId();
        columnValues[4] = getDateRequired();
        columnValues[5] = getDateMove();
        columnValues[6] = getLowbedId();
        columnValues[7] = getOperatorId();
        columnValues[8] = getIsCompleted();
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
            setXtransscheduleitmId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXtransscheduleitmId(null);
        }
        try {
            setMachineId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setMachineId(null);
        }
        try {
            setSiteFromId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setSiteFromId(null);
        }
        try {
            setSiteToId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setSiteToId(null);
        }
        setDateRequired(toDate(flds[4]));
        setDateMove(toDate(flds[5]));
        try {
            setLowbedId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setLowbedId(null);
        }
        try {
            setOperatorId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setOperatorId(null);
        }
        try {
            setIsCompleted(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setIsCompleted(null);
        }
    }
}
