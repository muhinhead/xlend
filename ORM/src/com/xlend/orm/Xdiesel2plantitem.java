// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Feb 07 17:33:05 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xdiesel2plantitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xdiesel2plantitemId = null;
    private Integer xdiesel2plantId = null;
    private Date addDate = null;
    private Integer xmachineId = null;
    private Integer xsiteId = null;
    private Integer operatorId = null;
    private Integer hourMeter = null;
    private Integer issuedbyId = null;
    private Double liters = null;

    public Xdiesel2plantitem(Connection connection) {
        super(connection, "xdiesel2plantitem", "xdiesel2plantitem_id");
        setColumnNames(new String[]{"xdiesel2plantitem_id", "xdiesel2plant_id", "add_date", "xmachine_id", "xsite_id", "operator_id", "hour_meter", "issuedby_id", "liters"});
    }

    public Xdiesel2plantitem(Connection connection, Integer xdiesel2plantitemId, Integer xdiesel2plantId, Date addDate, Integer xmachineId, Integer xsiteId, Integer operatorId, Integer hourMeter, Integer issuedbyId, Double liters) {
        super(connection, "xdiesel2plantitem", "xdiesel2plantitem_id");
        setNew(xdiesel2plantitemId.intValue() <= 0);
//        if (xdiesel2plantitemId.intValue() != 0) {
            this.xdiesel2plantitemId = xdiesel2plantitemId;
//        }
        this.xdiesel2plantId = xdiesel2plantId;
        this.addDate = addDate;
        this.xmachineId = xmachineId;
        this.xsiteId = xsiteId;
        this.operatorId = operatorId;
        this.hourMeter = hourMeter;
        this.issuedbyId = issuedbyId;
        this.liters = liters;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xdiesel2plantitem xdiesel2plantitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdiesel2plantitem_id,xdiesel2plant_id,add_date,xmachine_id,xsite_id,operator_id,hour_meter,issuedby_id,liters FROM xdiesel2plantitem WHERE xdiesel2plantitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xdiesel2plantitem = new Xdiesel2plantitem(getConnection());
                xdiesel2plantitem.setXdiesel2plantitemId(new Integer(rs.getInt(1)));
                xdiesel2plantitem.setXdiesel2plantId(new Integer(rs.getInt(2)));
                xdiesel2plantitem.setAddDate(rs.getDate(3));
                xdiesel2plantitem.setXmachineId(new Integer(rs.getInt(4)));
                xdiesel2plantitem.setXsiteId(new Integer(rs.getInt(5)));
                xdiesel2plantitem.setOperatorId(new Integer(rs.getInt(6)));
                xdiesel2plantitem.setHourMeter(new Integer(rs.getInt(7)));
                xdiesel2plantitem.setIssuedbyId(new Integer(rs.getInt(8)));
                xdiesel2plantitem.setLiters(rs.getDouble(9));
                xdiesel2plantitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xdiesel2plantitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xdiesel2plantitem ("+(getXdiesel2plantitemId().intValue()!=0?"xdiesel2plantitem_id,":"")+"xdiesel2plant_id,add_date,xmachine_id,xsite_id,operator_id,hour_meter,issuedby_id,liters) values("+(getXdiesel2plantitemId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXdiesel2plantitemId().intValue()!=0) {
                 ps.setObject(++n, getXdiesel2plantitemId());
             }
             ps.setObject(++n, getXdiesel2plantId());
             ps.setObject(++n, getAddDate());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getOperatorId());
             ps.setObject(++n, getHourMeter());
             ps.setObject(++n, getIssuedbyId());
             ps.setObject(++n, getLiters());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXdiesel2plantitemId().intValue()==0) {
             stmt = "SELECT max(xdiesel2plantitem_id) FROM xdiesel2plantitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXdiesel2plantitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE xdiesel2plantitem " +
                    "SET xdiesel2plant_id = ?, add_date = ?, xmachine_id = ?, xsite_id = ?, operator_id = ?, hour_meter = ?, issuedby_id = ?, liters = ?" + 
                    " WHERE xdiesel2plantitem_id = " + getXdiesel2plantitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXdiesel2plantId());
                ps.setObject(2, getAddDate());
                ps.setObject(3, getXmachineId());
                ps.setObject(4, getXsiteId());
                ps.setObject(5, getOperatorId());
                ps.setObject(6, getHourMeter());
                ps.setObject(7, getIssuedbyId());
                ps.setObject(8, getLiters());
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
                "DELETE FROM xdiesel2plantitem " +
                "WHERE xdiesel2plantitem_id = " + getXdiesel2plantitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXdiesel2plantitemId(new Integer(-getXdiesel2plantitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXdiesel2plantitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdiesel2plantitem_id,xdiesel2plant_id,add_date,xmachine_id,xsite_id,operator_id,hour_meter,issuedby_id,liters FROM xdiesel2plantitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xdiesel2plantitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getDate(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),new Integer(rs.getInt(8)),rs.getDouble(9)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xdiesel2plantitem[] objects = new Xdiesel2plantitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xdiesel2plantitem xdiesel2plantitem = (Xdiesel2plantitem) lst.get(i);
            objects[i] = xdiesel2plantitem;
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
        String stmt = "SELECT xdiesel2plantitem_id FROM xdiesel2plantitem " +
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
    //    return getXdiesel2plantitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xdiesel2plantitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXdiesel2plantitemId(id);
        setNew(prevIsNew);
    }

    public Integer getXdiesel2plantitemId() {
        return xdiesel2plantitemId;
    }

    public void setXdiesel2plantitemId(Integer xdiesel2plantitemId) throws ForeignKeyViolationException {
        setWasChanged(this.xdiesel2plantitemId != null && this.xdiesel2plantitemId != xdiesel2plantitemId);
        this.xdiesel2plantitemId = xdiesel2plantitemId;
        setNew(xdiesel2plantitemId.intValue() == 0);
    }

    public Integer getXdiesel2plantId() {
        return xdiesel2plantId;
    }

    public void setXdiesel2plantId(Integer xdiesel2plantId) throws SQLException, ForeignKeyViolationException {
        if (xdiesel2plantId!=null && !Xdiesel2plant.exists(getConnection(),"xdiesel2plant_id = " + xdiesel2plantId)) {
            throw new ForeignKeyViolationException("Can't set xdiesel2plant_id, foreign key violation: xdiesel2plantitem_xdiesel2plant_fk");
        }
        setWasChanged(this.xdiesel2plantId != null && !this.xdiesel2plantId.equals(xdiesel2plantId));
        this.xdiesel2plantId = xdiesel2plantId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.addDate != null && !this.addDate.equals(addDate));
        this.addDate = addDate;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xdiesel2plantitem_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xdiesel2plantitem_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) throws SQLException, ForeignKeyViolationException {
        if (operatorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + operatorId)) {
            throw new ForeignKeyViolationException("Can't set operator_id, foreign key violation: xdiesel2plantitem_xemployee_fk");
        }
        setWasChanged(this.operatorId != null && !this.operatorId.equals(operatorId));
        this.operatorId = operatorId;
    }

    public Integer getHourMeter() {
        return hourMeter;
    }

    public void setHourMeter(Integer hourMeter) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.hourMeter != null && !this.hourMeter.equals(hourMeter));
        this.hourMeter = hourMeter;
    }

    public Integer getIssuedbyId() {
        return issuedbyId;
    }

    public void setIssuedbyId(Integer issuedbyId) throws SQLException, ForeignKeyViolationException {
        if (issuedbyId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + issuedbyId)) {
            throw new ForeignKeyViolationException("Can't set issuedby_id, foreign key violation: xdiesel2plantitem_xemployee_fk2");
        }
        setWasChanged(this.issuedbyId != null && !this.issuedbyId.equals(issuedbyId));
        this.issuedbyId = issuedbyId;
    }

    public Double getLiters() {
        return liters;
    }

    public void setLiters(Double liters) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.liters != null && !this.liters.equals(liters));
        this.liters = liters;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[9];
        columnValues[0] = getXdiesel2plantitemId();
        columnValues[1] = getXdiesel2plantId();
        columnValues[2] = getAddDate();
        columnValues[3] = getXmachineId();
        columnValues[4] = getXsiteId();
        columnValues[5] = getOperatorId();
        columnValues[6] = getHourMeter();
        columnValues[7] = getIssuedbyId();
        columnValues[8] = getLiters();
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
            setXdiesel2plantitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXdiesel2plantitemId(null);
        }
        try {
            setXdiesel2plantId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXdiesel2plantId(null);
        }
        setAddDate(toDate(flds[2]));
        try {
            setXmachineId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setXsiteId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setOperatorId(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setOperatorId(null);
        }
        try {
            setHourMeter(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setHourMeter(null);
        }
        try {
            setIssuedbyId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setIssuedbyId(null);
        }
        try {
            setLiters(Double.parseDouble(flds[8]));
        } catch(NumberFormatException ne) {
            setLiters(null);
        }
    }
}
