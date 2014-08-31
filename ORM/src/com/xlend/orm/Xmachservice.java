// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Aug 30 12:06:05 EEST 2014
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xmachservice extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xmachserviceId = null;
    private Date servicedate = null;
    private Date entrydate = null;
    private Integer servicedbyId = null;
    private Integer assistedbyId = null;
    private Integer xmachineId = null;
    private String engineOil = null;
    private String hydraulicOil = null;
    private String brakeFluid = null;
    private String transmissionOil = null;
    private String gearboxOil = null;
    private String antiFreeze = null;
    private String diffChecked1 = null;
    private String diffChecked2 = null;
    private String diffChecked3 = null;

    public Xmachservice(Connection connection) {
        super(connection, "xmachservice", "xmachservice_id");
        setColumnNames(new String[]{"xmachservice_id", "servicedate", "entrydate", "servicedby_id", "assistedby_id", "xmachine_id", "engine_oil", "hydraulic_oil", "brake_fluid", "transmission_oil", "gearbox_oil", "anti_freeze", "diff_checked1", "diff_checked2", "diff_checked3"});
    }

    public Xmachservice(Connection connection, Integer xmachserviceId, Date servicedate, Date entrydate, Integer servicedbyId, Integer assistedbyId, Integer xmachineId, String engineOil, String hydraulicOil, String brakeFluid, String transmissionOil, String gearboxOil, String antiFreeze, String diffChecked1, String diffChecked2, String diffChecked3) {
        super(connection, "xmachservice", "xmachservice_id");
        setNew(xmachserviceId.intValue() <= 0);
//        if (xmachserviceId.intValue() != 0) {
            this.xmachserviceId = xmachserviceId;
//        }
        this.servicedate = servicedate;
        this.entrydate = entrydate;
        this.servicedbyId = servicedbyId;
        this.assistedbyId = assistedbyId;
        this.xmachineId = xmachineId;
        this.engineOil = engineOil;
        this.hydraulicOil = hydraulicOil;
        this.brakeFluid = brakeFluid;
        this.transmissionOil = transmissionOil;
        this.gearboxOil = gearboxOil;
        this.antiFreeze = antiFreeze;
        this.diffChecked1 = diffChecked1;
        this.diffChecked2 = diffChecked2;
        this.diffChecked3 = diffChecked3;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xmachservice xmachservice = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachservice_id,servicedate,entrydate,servicedby_id,assistedby_id,xmachine_id,engine_oil,hydraulic_oil,brake_fluid,transmission_oil,gearbox_oil,anti_freeze,diff_checked1,diff_checked2,diff_checked3 FROM xmachservice WHERE xmachservice_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xmachservice = new Xmachservice(getConnection());
                xmachservice.setXmachserviceId(new Integer(rs.getInt(1)));
                xmachservice.setServicedate(rs.getDate(2));
                xmachservice.setEntrydate(rs.getDate(3));
                xmachservice.setServicedbyId(new Integer(rs.getInt(4)));
                xmachservice.setAssistedbyId(new Integer(rs.getInt(5)));
                xmachservice.setXmachineId(new Integer(rs.getInt(6)));
                xmachservice.setEngineOil(rs.getString(7));
                xmachservice.setHydraulicOil(rs.getString(8));
                xmachservice.setBrakeFluid(rs.getString(9));
                xmachservice.setTransmissionOil(rs.getString(10));
                xmachservice.setGearboxOil(rs.getString(11));
                xmachservice.setAntiFreeze(rs.getString(12));
                xmachservice.setDiffChecked1(rs.getString(13));
                xmachservice.setDiffChecked2(rs.getString(14));
                xmachservice.setDiffChecked3(rs.getString(15));
                xmachservice.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xmachservice;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xmachservice ("+(getXmachserviceId().intValue()!=0?"xmachservice_id,":"")+"servicedate,entrydate,servicedby_id,assistedby_id,xmachine_id,engine_oil,hydraulic_oil,brake_fluid,transmission_oil,gearbox_oil,anti_freeze,diff_checked1,diff_checked2,diff_checked3) values("+(getXmachserviceId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXmachserviceId().intValue()!=0) {
                 ps.setObject(++n, getXmachserviceId());
             }
             ps.setObject(++n, getServicedate());
             ps.setObject(++n, getEntrydate());
             ps.setObject(++n, getServicedbyId());
             ps.setObject(++n, getAssistedbyId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getEngineOil());
             ps.setObject(++n, getHydraulicOil());
             ps.setObject(++n, getBrakeFluid());
             ps.setObject(++n, getTransmissionOil());
             ps.setObject(++n, getGearboxOil());
             ps.setObject(++n, getAntiFreeze());
             ps.setObject(++n, getDiffChecked1());
             ps.setObject(++n, getDiffChecked2());
             ps.setObject(++n, getDiffChecked3());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXmachserviceId().intValue()==0) {
             stmt = "SELECT max(xmachservice_id) FROM xmachservice";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXmachserviceId(new Integer(rs.getInt(1)));
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
                    "UPDATE xmachservice " +
                    "SET servicedate = ?, entrydate = ?, servicedby_id = ?, assistedby_id = ?, xmachine_id = ?, engine_oil = ?, hydraulic_oil = ?, brake_fluid = ?, transmission_oil = ?, gearbox_oil = ?, anti_freeze = ?, diff_checked1 = ?, diff_checked2 = ?, diff_checked3 = ?" + 
                    " WHERE xmachservice_id = " + getXmachserviceId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getServicedate());
                ps.setObject(2, getEntrydate());
                ps.setObject(3, getServicedbyId());
                ps.setObject(4, getAssistedbyId());
                ps.setObject(5, getXmachineId());
                ps.setObject(6, getEngineOil());
                ps.setObject(7, getHydraulicOil());
                ps.setObject(8, getBrakeFluid());
                ps.setObject(9, getTransmissionOil());
                ps.setObject(10, getGearboxOil());
                ps.setObject(11, getAntiFreeze());
                ps.setObject(12, getDiffChecked1());
                ps.setObject(13, getDiffChecked2());
                ps.setObject(14, getDiffChecked3());
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
                "DELETE FROM xmachservice " +
                "WHERE xmachservice_id = " + getXmachserviceId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXmachserviceId(new Integer(-getXmachserviceId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXmachserviceId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xmachservice_id,servicedate,entrydate,servicedby_id,assistedby_id,xmachine_id,engine_oil,hydraulic_oil,brake_fluid,transmission_oil,gearbox_oil,anti_freeze,diff_checked1,diff_checked2,diff_checked3 FROM xmachservice " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xmachservice(con,new Integer(rs.getInt(1)),rs.getDate(2),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xmachservice[] objects = new Xmachservice[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xmachservice xmachservice = (Xmachservice) lst.get(i);
            objects[i] = xmachservice;
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
        String stmt = "SELECT xmachservice_id FROM xmachservice " +
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
    //    return getXmachserviceId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xmachserviceId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXmachserviceId(id);
        setNew(prevIsNew);
    }

    public Integer getXmachserviceId() {
        return xmachserviceId;
    }

    public void setXmachserviceId(Integer xmachserviceId) throws ForeignKeyViolationException {
        setWasChanged(this.xmachserviceId != null && this.xmachserviceId != xmachserviceId);
        this.xmachserviceId = xmachserviceId;
        setNew(xmachserviceId.intValue() == 0);
    }

    public Date getServicedate() {
        return servicedate;
    }

    public void setServicedate(Date servicedate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.servicedate != null && !this.servicedate.equals(servicedate));
        this.servicedate = servicedate;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.entrydate != null && !this.entrydate.equals(entrydate));
        this.entrydate = entrydate;
    }

    public Integer getServicedbyId() {
        return servicedbyId;
    }

    public void setServicedbyId(Integer servicedbyId) throws SQLException, ForeignKeyViolationException {
        if (servicedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + servicedbyId)) {
            throw new ForeignKeyViolationException("Can't set servicedby_id, foreign key violation: xmachservice_xemployee_fk");
        }
        setWasChanged(this.servicedbyId != null && !this.servicedbyId.equals(servicedbyId));
        this.servicedbyId = servicedbyId;
    }

    public Integer getAssistedbyId() {
        return assistedbyId;
    }

    public void setAssistedbyId(Integer assistedbyId) throws SQLException, ForeignKeyViolationException {
        if (assistedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + assistedbyId)) {
            throw new ForeignKeyViolationException("Can't set assistedby_id, foreign key violation: xmachservice_xemployee_fk2");
        }
        setWasChanged(this.assistedbyId != null && !this.assistedbyId.equals(assistedbyId));
        this.assistedbyId = assistedbyId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xmachservice_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public String getEngineOil() {
        return engineOil;
    }

    public void setEngineOil(String engineOil) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.engineOil != null && !this.engineOil.equals(engineOil));
        this.engineOil = engineOil;
    }

    public String getHydraulicOil() {
        return hydraulicOil;
    }

    public void setHydraulicOil(String hydraulicOil) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.hydraulicOil != null && !this.hydraulicOil.equals(hydraulicOil));
        this.hydraulicOil = hydraulicOil;
    }

    public String getBrakeFluid() {
        return brakeFluid;
    }

    public void setBrakeFluid(String brakeFluid) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.brakeFluid != null && !this.brakeFluid.equals(brakeFluid));
        this.brakeFluid = brakeFluid;
    }

    public String getTransmissionOil() {
        return transmissionOil;
    }

    public void setTransmissionOil(String transmissionOil) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.transmissionOil != null && !this.transmissionOil.equals(transmissionOil));
        this.transmissionOil = transmissionOil;
    }

    public String getGearboxOil() {
        return gearboxOil;
    }

    public void setGearboxOil(String gearboxOil) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.gearboxOil != null && !this.gearboxOil.equals(gearboxOil));
        this.gearboxOil = gearboxOil;
    }

    public String getAntiFreeze() {
        return antiFreeze;
    }

    public void setAntiFreeze(String antiFreeze) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.antiFreeze != null && !this.antiFreeze.equals(antiFreeze));
        this.antiFreeze = antiFreeze;
    }

    public String getDiffChecked1() {
        return diffChecked1;
    }

    public void setDiffChecked1(String diffChecked1) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.diffChecked1 != null && !this.diffChecked1.equals(diffChecked1));
        this.diffChecked1 = diffChecked1;
    }

    public String getDiffChecked2() {
        return diffChecked2;
    }

    public void setDiffChecked2(String diffChecked2) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.diffChecked2 != null && !this.diffChecked2.equals(diffChecked2));
        this.diffChecked2 = diffChecked2;
    }

    public String getDiffChecked3() {
        return diffChecked3;
    }

    public void setDiffChecked3(String diffChecked3) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.diffChecked3 != null && !this.diffChecked3.equals(diffChecked3));
        this.diffChecked3 = diffChecked3;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[15];
        columnValues[0] = getXmachserviceId();
        columnValues[1] = getServicedate();
        columnValues[2] = getEntrydate();
        columnValues[3] = getServicedbyId();
        columnValues[4] = getAssistedbyId();
        columnValues[5] = getXmachineId();
        columnValues[6] = getEngineOil();
        columnValues[7] = getHydraulicOil();
        columnValues[8] = getBrakeFluid();
        columnValues[9] = getTransmissionOil();
        columnValues[10] = getGearboxOil();
        columnValues[11] = getAntiFreeze();
        columnValues[12] = getDiffChecked1();
        columnValues[13] = getDiffChecked2();
        columnValues[14] = getDiffChecked3();
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
            setXmachserviceId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXmachserviceId(null);
        }
        setServicedate(toDate(flds[1]));
        setEntrydate(toDate(flds[2]));
        try {
            setServicedbyId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setServicedbyId(null);
        }
        try {
            setAssistedbyId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setAssistedbyId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        setEngineOil(flds[6]);
        setHydraulicOil(flds[7]);
        setBrakeFluid(flds[8]);
        setTransmissionOil(flds[9]);
        setGearboxOil(flds[10]);
        setAntiFreeze(flds[11]);
        setDiffChecked1(flds[12]);
        setDiffChecked2(flds[13]);
        setDiffChecked3(flds[14]);
    }
}
