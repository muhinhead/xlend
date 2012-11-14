// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Wed Nov 14 16:58:26 EET 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xsitediaryitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xsitediaryitemId = null;
    private Integer xsitediaryId = null;
    private Integer xmachineId = null;
    private Integer operatorId = null;
    private String day1value = null;
    private String day2value = null;
    private String day3value = null;
    private String day4value = null;
    private String day5value = null;
    private String day6value = null;
    private String day7value = null;

    public Xsitediaryitem(Connection connection) {
        super(connection, "xsitediaryitem", "xsitediaryitem_id");
        setColumnNames(new String[]{"xsitediaryitem_id", "xsitediary_id", "xmachine_id", "operator_id", "day1value", "day2value", "day3value", "day4value", "day5value", "day6value", "day7value"});
    }

    public Xsitediaryitem(Connection connection, Integer xsitediaryitemId, Integer xsitediaryId, Integer xmachineId, Integer operatorId, String day1value, String day2value, String day3value, String day4value, String day5value, String day6value, String day7value) {
        super(connection, "xsitediaryitem", "xsitediaryitem_id");
        setNew(xsitediaryitemId.intValue() <= 0);
//        if (xsitediaryitemId.intValue() != 0) {
            this.xsitediaryitemId = xsitediaryitemId;
//        }
        this.xsitediaryId = xsitediaryId;
        this.xmachineId = xmachineId;
        this.operatorId = operatorId;
        this.day1value = day1value;
        this.day2value = day2value;
        this.day3value = day3value;
        this.day4value = day4value;
        this.day5value = day5value;
        this.day6value = day6value;
        this.day7value = day7value;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xsitediaryitem xsitediaryitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsitediaryitem_id,xsitediary_id,xmachine_id,operator_id,day1value,day2value,day3value,day4value,day5value,day6value,day7value FROM xsitediaryitem WHERE xsitediaryitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xsitediaryitem = new Xsitediaryitem(getConnection());
                xsitediaryitem.setXsitediaryitemId(new Integer(rs.getInt(1)));
                xsitediaryitem.setXsitediaryId(new Integer(rs.getInt(2)));
                xsitediaryitem.setXmachineId(new Integer(rs.getInt(3)));
                xsitediaryitem.setOperatorId(new Integer(rs.getInt(4)));
                xsitediaryitem.setDay1value(rs.getString(5));
                xsitediaryitem.setDay2value(rs.getString(6));
                xsitediaryitem.setDay3value(rs.getString(7));
                xsitediaryitem.setDay4value(rs.getString(8));
                xsitediaryitem.setDay5value(rs.getString(9));
                xsitediaryitem.setDay6value(rs.getString(10));
                xsitediaryitem.setDay7value(rs.getString(11));
                xsitediaryitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xsitediaryitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xsitediaryitem ("+(getXsitediaryitemId().intValue()!=0?"xsitediaryitem_id,":"")+"xsitediary_id,xmachine_id,operator_id,day1value,day2value,day3value,day4value,day5value,day6value,day7value) values("+(getXsitediaryitemId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXsitediaryitemId().intValue()!=0) {
                 ps.setObject(++n, getXsitediaryitemId());
             }
             ps.setObject(++n, getXsitediaryId());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getOperatorId());
             ps.setObject(++n, getDay1value());
             ps.setObject(++n, getDay2value());
             ps.setObject(++n, getDay3value());
             ps.setObject(++n, getDay4value());
             ps.setObject(++n, getDay5value());
             ps.setObject(++n, getDay6value());
             ps.setObject(++n, getDay7value());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXsitediaryitemId().intValue()==0) {
             stmt = "SELECT max(xsitediaryitem_id) FROM xsitediaryitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXsitediaryitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE xsitediaryitem " +
                    "SET xsitediary_id = ?, xmachine_id = ?, operator_id = ?, day1value = ?, day2value = ?, day3value = ?, day4value = ?, day5value = ?, day6value = ?, day7value = ?" + 
                    " WHERE xsitediaryitem_id = " + getXsitediaryitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXsitediaryId());
                ps.setObject(2, getXmachineId());
                ps.setObject(3, getOperatorId());
                ps.setObject(4, getDay1value());
                ps.setObject(5, getDay2value());
                ps.setObject(6, getDay3value());
                ps.setObject(7, getDay4value());
                ps.setObject(8, getDay5value());
                ps.setObject(9, getDay6value());
                ps.setObject(10, getDay7value());
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
                "DELETE FROM xsitediaryitem " +
                "WHERE xsitediaryitem_id = " + getXsitediaryitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXsitediaryitemId(new Integer(-getXsitediaryitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXsitediaryitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xsitediaryitem_id,xsitediary_id,xmachine_id,operator_id,day1value,day2value,day3value,day4value,day5value,day6value,day7value FROM xsitediaryitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xsitediaryitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xsitediaryitem[] objects = new Xsitediaryitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xsitediaryitem xsitediaryitem = (Xsitediaryitem) lst.get(i);
            objects[i] = xsitediaryitem;
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
        String stmt = "SELECT xsitediaryitem_id FROM xsitediaryitem " +
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
    //    return getXsitediaryitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xsitediaryitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXsitediaryitemId(id);
        setNew(prevIsNew);
    }

    public Integer getXsitediaryitemId() {
        return xsitediaryitemId;
    }

    public void setXsitediaryitemId(Integer xsitediaryitemId) throws ForeignKeyViolationException {
        setWasChanged(this.xsitediaryitemId != null && this.xsitediaryitemId != xsitediaryitemId);
        this.xsitediaryitemId = xsitediaryitemId;
        setNew(xsitediaryitemId.intValue() == 0);
    }

    public Integer getXsitediaryId() {
        return xsitediaryId;
    }

    public void setXsitediaryId(Integer xsitediaryId) throws SQLException, ForeignKeyViolationException {
        if (xsitediaryId!=null && !Xsitediary.exists(getConnection(),"xsitediary_id = " + xsitediaryId)) {
            throw new ForeignKeyViolationException("Can't set xsitediary_id, foreign key violation: xsitediaryitem_xsitediary_fk");
        }
        setWasChanged(this.xsitediaryId != null && !this.xsitediaryId.equals(xsitediaryId));
        this.xsitediaryId = xsitediaryId;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xsitediaryitem_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) throws SQLException, ForeignKeyViolationException {
        if (operatorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + operatorId)) {
            throw new ForeignKeyViolationException("Can't set operator_id, foreign key violation: xsitediaryitem_xemployee_fk");
        }
        setWasChanged(this.operatorId != null && !this.operatorId.equals(operatorId));
        this.operatorId = operatorId;
    }

    public String getDay1value() {
        return day1value;
    }

    public void setDay1value(String day1value) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.day1value != null && !this.day1value.equals(day1value));
        this.day1value = day1value;
    }

    public String getDay2value() {
        return day2value;
    }

    public void setDay2value(String day2value) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.day2value != null && !this.day2value.equals(day2value));
        this.day2value = day2value;
    }

    public String getDay3value() {
        return day3value;
    }

    public void setDay3value(String day3value) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.day3value != null && !this.day3value.equals(day3value));
        this.day3value = day3value;
    }

    public String getDay4value() {
        return day4value;
    }

    public void setDay4value(String day4value) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.day4value != null && !this.day4value.equals(day4value));
        this.day4value = day4value;
    }

    public String getDay5value() {
        return day5value;
    }

    public void setDay5value(String day5value) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.day5value != null && !this.day5value.equals(day5value));
        this.day5value = day5value;
    }

    public String getDay6value() {
        return day6value;
    }

    public void setDay6value(String day6value) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.day6value != null && !this.day6value.equals(day6value));
        this.day6value = day6value;
    }

    public String getDay7value() {
        return day7value;
    }

    public void setDay7value(String day7value) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.day7value != null && !this.day7value.equals(day7value));
        this.day7value = day7value;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[11];
        columnValues[0] = getXsitediaryitemId();
        columnValues[1] = getXsitediaryId();
        columnValues[2] = getXmachineId();
        columnValues[3] = getOperatorId();
        columnValues[4] = getDay1value();
        columnValues[5] = getDay2value();
        columnValues[6] = getDay3value();
        columnValues[7] = getDay4value();
        columnValues[8] = getDay5value();
        columnValues[9] = getDay6value();
        columnValues[10] = getDay7value();
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
            setXsitediaryitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXsitediaryitemId(null);
        }
        try {
            setXsitediaryId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXsitediaryId(null);
        }
        try {
            setXmachineId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setXmachineId(null);
        }
        try {
            setOperatorId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setOperatorId(null);
        }
        setDay1value(flds[4]);
        setDay2value(flds[5]);
        setDay3value(flds[6]);
        setDay4value(flds[7]);
        setDay5value(flds[8]);
        setDay6value(flds[9]);
        setDay7value(flds[10]);
    }
}
