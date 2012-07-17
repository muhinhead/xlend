// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Jul 17 17:58:04 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xtripsheetpart extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xtripsheetpartId = null;
    private Integer xtripsheetId = null;
    private Date partdate = null;
    private Integer fromsiteId = null;
    private Integer tositeId = null;
    private Integer loaded1Id = null;
    private Integer loaded2Id = null;
    private Integer isempty = null;
    private Timestamp timestart = null;
    private Timestamp timeend = null;
    private Integer kilimeters = null;
    private Integer assistantId = null;

    public Xtripsheetpart(Connection connection) {
        super(connection, "xtripsheetpart", "xtripsheetpart_id");
        setColumnNames(new String[]{"xtripsheetpart_id", "xtripsheet_id", "partdate", "fromsite_id", "tosite_id", "loaded1_id", "loaded2_id", "isempty", "timestart", "timeend", "kilimeters", "assistant_id"});
    }

    public Xtripsheetpart(Connection connection, Integer xtripsheetpartId, Integer xtripsheetId, Date partdate, Integer fromsiteId, Integer tositeId, Integer loaded1Id, Integer loaded2Id, Integer isempty, Timestamp timestart, Timestamp timeend, Integer kilimeters, Integer assistantId) {
        super(connection, "xtripsheetpart", "xtripsheetpart_id");
        setNew(xtripsheetpartId.intValue() <= 0);
//        if (xtripsheetpartId.intValue() != 0) {
            this.xtripsheetpartId = xtripsheetpartId;
//        }
        this.xtripsheetId = xtripsheetId;
        this.partdate = partdate;
        this.fromsiteId = fromsiteId;
        this.tositeId = tositeId;
        this.loaded1Id = loaded1Id;
        this.loaded2Id = loaded2Id;
        this.isempty = isempty;
        this.timestart = timestart;
        this.timeend = timeend;
        this.kilimeters = kilimeters;
        this.assistantId = assistantId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xtripsheetpart xtripsheetpart = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripsheetpart_id,xtripsheet_id,partdate,fromsite_id,tosite_id,loaded1_id,loaded2_id,isempty,timestart,timeend,kilimeters,assistant_id FROM xtripsheetpart WHERE xtripsheetpart_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xtripsheetpart = new Xtripsheetpart(getConnection());
                xtripsheetpart.setXtripsheetpartId(new Integer(rs.getInt(1)));
                xtripsheetpart.setXtripsheetId(new Integer(rs.getInt(2)));
                xtripsheetpart.setPartdate(rs.getDate(3));
                xtripsheetpart.setFromsiteId(new Integer(rs.getInt(4)));
                xtripsheetpart.setTositeId(new Integer(rs.getInt(5)));
                xtripsheetpart.setLoaded1Id(new Integer(rs.getInt(6)));
                xtripsheetpart.setLoaded2Id(new Integer(rs.getInt(7)));
                xtripsheetpart.setIsempty(new Integer(rs.getInt(8)));
                xtripsheetpart.setTimestart(rs.getTimestamp(9));
                xtripsheetpart.setTimeend(rs.getTimestamp(10));
                xtripsheetpart.setKilimeters(new Integer(rs.getInt(11)));
                xtripsheetpart.setAssistantId(new Integer(rs.getInt(12)));
                xtripsheetpart.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xtripsheetpart;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xtripsheetpart ("+(getXtripsheetpartId().intValue()!=0?"xtripsheetpart_id,":"")+"xtripsheet_id,partdate,fromsite_id,tosite_id,loaded1_id,loaded2_id,isempty,timestart,timeend,kilimeters,assistant_id) values("+(getXtripsheetpartId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXtripsheetpartId().intValue()!=0) {
                 ps.setObject(++n, getXtripsheetpartId());
             }
             ps.setObject(++n, getXtripsheetId());
             ps.setObject(++n, getPartdate());
             ps.setObject(++n, getFromsiteId());
             ps.setObject(++n, getTositeId());
             ps.setObject(++n, getLoaded1Id());
             ps.setObject(++n, getLoaded2Id());
             ps.setObject(++n, getIsempty());
             ps.setObject(++n, getTimestart());
             ps.setObject(++n, getTimeend());
             ps.setObject(++n, getKilimeters());
             ps.setObject(++n, getAssistantId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXtripsheetpartId().intValue()==0) {
             stmt = "SELECT max(xtripsheetpart_id) FROM xtripsheetpart";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXtripsheetpartId(new Integer(rs.getInt(1)));
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
                    "UPDATE xtripsheetpart " +
                    "SET xtripsheet_id = ?, partdate = ?, fromsite_id = ?, tosite_id = ?, loaded1_id = ?, loaded2_id = ?, isempty = ?, timestart = ?, timeend = ?, kilimeters = ?, assistant_id = ?" + 
                    " WHERE xtripsheetpart_id = " + getXtripsheetpartId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXtripsheetId());
                ps.setObject(2, getPartdate());
                ps.setObject(3, getFromsiteId());
                ps.setObject(4, getTositeId());
                ps.setObject(5, getLoaded1Id());
                ps.setObject(6, getLoaded2Id());
                ps.setObject(7, getIsempty());
                ps.setObject(8, getTimestart());
                ps.setObject(9, getTimeend());
                ps.setObject(10, getKilimeters());
                ps.setObject(11, getAssistantId());
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
                "DELETE FROM xtripsheetpart " +
                "WHERE xtripsheetpart_id = " + getXtripsheetpartId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXtripsheetpartId(new Integer(-getXtripsheetpartId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXtripsheetpartId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xtripsheetpart_id,xtripsheet_id,partdate,fromsite_id,tosite_id,loaded1_id,loaded2_id,isempty,timestart,timeend,kilimeters,assistant_id FROM xtripsheetpart " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xtripsheetpart(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),rs.getTimestamp(9),rs.getTimestamp(10),new Integer(rs.getInt(11)),new Integer(rs.getInt(12))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xtripsheetpart[] objects = new Xtripsheetpart[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xtripsheetpart xtripsheetpart = (Xtripsheetpart) lst.get(i);
            objects[i] = xtripsheetpart;
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
        String stmt = "SELECT xtripsheetpart_id FROM xtripsheetpart " +
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
    //    return getXtripsheetpartId() + getDelimiter();
    //}

    public Integer getXtripsheetpartId() {
        return xtripsheetpartId;
    }

    public void setXtripsheetpartId(Integer xtripsheetpartId) throws ForeignKeyViolationException {
        setWasChanged(this.xtripsheetpartId != null && this.xtripsheetpartId != xtripsheetpartId);
        this.xtripsheetpartId = xtripsheetpartId;
        setNew(xtripsheetpartId.intValue() == 0);
    }

    public Integer getXtripsheetId() {
        return xtripsheetId;
    }

    public void setXtripsheetId(Integer xtripsheetId) throws SQLException, ForeignKeyViolationException {
        if (xtripsheetId!=null && !Xtripsheet.exists(getConnection(),"xtripsheet_id = " + xtripsheetId)) {
            throw new ForeignKeyViolationException("Can't set xtripsheet_id, foreign key violation: xtripsheetpart_xtripsheet_fk");
        }
        setWasChanged(this.xtripsheetId != null && !this.xtripsheetId.equals(xtripsheetId));
        this.xtripsheetId = xtripsheetId;
    }

    public Date getPartdate() {
        return partdate;
    }

    public void setPartdate(Date partdate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.partdate != null && !this.partdate.equals(partdate));
        this.partdate = partdate;
    }

    public Integer getFromsiteId() {
        return fromsiteId;
    }

    public void setFromsiteId(Integer fromsiteId) throws SQLException, ForeignKeyViolationException {
        if (fromsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + fromsiteId)) {
            throw new ForeignKeyViolationException("Can't set fromsite_id, foreign key violation: xtripsheetpart_xsite_fk");
        }
        setWasChanged(this.fromsiteId != null && !this.fromsiteId.equals(fromsiteId));
        this.fromsiteId = fromsiteId;
    }

    public Integer getTositeId() {
        return tositeId;
    }

    public void setTositeId(Integer tositeId) throws SQLException, ForeignKeyViolationException {
        if (tositeId!=null && !Xsite.exists(getConnection(),"xsite_id = " + tositeId)) {
            throw new ForeignKeyViolationException("Can't set tosite_id, foreign key violation: xtripsheetpart_xsite_fk2");
        }
        setWasChanged(this.tositeId != null && !this.tositeId.equals(tositeId));
        this.tositeId = tositeId;
    }

    public Integer getLoaded1Id() {
        return loaded1Id;
    }

    public void setLoaded1Id(Integer loaded1Id) throws SQLException, ForeignKeyViolationException {
        if (null != loaded1Id)
            loaded1Id = loaded1Id == 0 ? null : loaded1Id;
        if (loaded1Id!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + loaded1Id)) {
            throw new ForeignKeyViolationException("Can't set loaded1_id, foreign key violation: xtripsheetpart_xmachine_fk");
        }
        setWasChanged(this.loaded1Id != null && !this.loaded1Id.equals(loaded1Id));
        this.loaded1Id = loaded1Id;
    }

    public Integer getLoaded2Id() {
        return loaded2Id;
    }

    public void setLoaded2Id(Integer loaded2Id) throws SQLException, ForeignKeyViolationException {
        if (null != loaded2Id)
            loaded2Id = loaded2Id == 0 ? null : loaded2Id;
        if (loaded2Id!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + loaded2Id)) {
            throw new ForeignKeyViolationException("Can't set loaded2_id, foreign key violation: xtripsheetpart_xmachine_fk2");
        }
        setWasChanged(this.loaded2Id != null && !this.loaded2Id.equals(loaded2Id));
        this.loaded2Id = loaded2Id;
    }

    public Integer getIsempty() {
        return isempty;
    }

    public void setIsempty(Integer isempty) throws SQLException, ForeignKeyViolationException {
        if (null != isempty)
            isempty = isempty == 0 ? null : isempty;
        setWasChanged(this.isempty != null && !this.isempty.equals(isempty));
        this.isempty = isempty;
    }

    public Timestamp getTimestart() {
        return timestart;
    }

    public void setTimestart(Timestamp timestart) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.timestart != null && !this.timestart.equals(timestart));
        this.timestart = timestart;
    }

    public Timestamp getTimeend() {
        return timeend;
    }

    public void setTimeend(Timestamp timeend) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.timeend != null && !this.timeend.equals(timeend));
        this.timeend = timeend;
    }

    public Integer getKilimeters() {
        return kilimeters;
    }

    public void setKilimeters(Integer kilimeters) throws SQLException, ForeignKeyViolationException {
        if (null != kilimeters)
            kilimeters = kilimeters == 0 ? null : kilimeters;
        setWasChanged(this.kilimeters != null && !this.kilimeters.equals(kilimeters));
        this.kilimeters = kilimeters;
    }

    public Integer getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Integer assistantId) throws SQLException, ForeignKeyViolationException {
        if (null != assistantId)
            assistantId = assistantId == 0 ? null : assistantId;
        if (assistantId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + assistantId)) {
            throw new ForeignKeyViolationException("Can't set assistant_id, foreign key violation: xtripsheetpart_xemployee_fk");
        }
        setWasChanged(this.assistantId != null && !this.assistantId.equals(assistantId));
        this.assistantId = assistantId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[12];
        columnValues[0] = getXtripsheetpartId();
        columnValues[1] = getXtripsheetId();
        columnValues[2] = getPartdate();
        columnValues[3] = getFromsiteId();
        columnValues[4] = getTositeId();
        columnValues[5] = getLoaded1Id();
        columnValues[6] = getLoaded2Id();
        columnValues[7] = getIsempty();
        columnValues[8] = getTimestart();
        columnValues[9] = getTimeend();
        columnValues[10] = getKilimeters();
        columnValues[11] = getAssistantId();
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
            setXtripsheetpartId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXtripsheetpartId(null);
        }
        try {
            setXtripsheetId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXtripsheetId(null);
        }
        setPartdate(toDate(flds[2]));
        try {
            setFromsiteId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setFromsiteId(null);
        }
        try {
            setTositeId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setTositeId(null);
        }
        try {
            setLoaded1Id(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setLoaded1Id(null);
        }
        try {
            setLoaded2Id(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setLoaded2Id(null);
        }
        try {
            setIsempty(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setIsempty(null);
        }
        setTimestart(toTimeStamp(flds[8]));
        setTimeend(toTimeStamp(flds[9]));
        try {
            setKilimeters(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setKilimeters(null);
        }
        try {
            setAssistantId(Integer.parseInt(flds[11]));
        } catch(NumberFormatException ne) {
            setAssistantId(null);
        }
    }
}
