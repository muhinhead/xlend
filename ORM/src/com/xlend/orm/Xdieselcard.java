// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Jul 12 10:50:29 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xdieselcard extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xdieselcardId = null;
    private Date carddate = null;
    private Integer xmachineId = null;
    private Integer operatorId = null;
    private Integer xsiteId = null;
    private Integer amountLiters = null;
    private Integer personissId = null;

    public Xdieselcard(Connection connection) {
        super(connection, "xdieselcard", "xdieselcard_id");
        setColumnNames(new String[]{"xdieselcard_id", "carddate", "xmachine_id", "operator_id", "xsite_id", "amount_liters", "personiss_id"});
    }

    public Xdieselcard(Connection connection, Integer xdieselcardId, Date carddate, Integer xmachineId, Integer operatorId, Integer xsiteId, Integer amountLiters, Integer personissId) {
        super(connection, "xdieselcard", "xdieselcard_id");
        setNew(xdieselcardId.intValue() <= 0);
//        if (xdieselcardId.intValue() != 0) {
            this.xdieselcardId = xdieselcardId;
//        }
        this.carddate = carddate;
        this.xmachineId = xmachineId;
        this.operatorId = operatorId;
        this.xsiteId = xsiteId;
        this.amountLiters = amountLiters;
        this.personissId = personissId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xdieselcard xdieselcard = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselcard_id,carddate,xmachine_id,operator_id,xsite_id,amount_liters,personiss_id FROM xdieselcard WHERE xdieselcard_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xdieselcard = new Xdieselcard(getConnection());
                xdieselcard.setXdieselcardId(new Integer(rs.getInt(1)));
                xdieselcard.setCarddate(rs.getDate(2));
                xdieselcard.setXmachineId(new Integer(rs.getInt(3)));
                xdieselcard.setOperatorId(new Integer(rs.getInt(4)));
                xdieselcard.setXsiteId(new Integer(rs.getInt(5)));
                xdieselcard.setAmountLiters(new Integer(rs.getInt(6)));
                xdieselcard.setPersonissId(new Integer(rs.getInt(7)));
                xdieselcard.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xdieselcard;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xdieselcard ("+(getXdieselcardId().intValue()!=0?"xdieselcard_id,":"")+"carddate,xmachine_id,operator_id,xsite_id,amount_liters,personiss_id) values("+(getXdieselcardId().intValue()!=0?"?,":"")+"?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXdieselcardId().intValue()!=0) {
                 ps.setObject(++n, getXdieselcardId());
             }
             ps.setObject(++n, getCarddate());
             ps.setObject(++n, getXmachineId());
             ps.setObject(++n, getOperatorId());
             ps.setObject(++n, getXsiteId());
             ps.setObject(++n, getAmountLiters());
             ps.setObject(++n, getPersonissId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXdieselcardId().intValue()==0) {
             stmt = "SELECT max(xdieselcard_id) FROM xdieselcard";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXdieselcardId(new Integer(rs.getInt(1)));
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
                    "UPDATE xdieselcard " +
                    "SET carddate = ?, xmachine_id = ?, operator_id = ?, xsite_id = ?, amount_liters = ?, personiss_id = ?" + 
                    " WHERE xdieselcard_id = " + getXdieselcardId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getCarddate());
                ps.setObject(2, getXmachineId());
                ps.setObject(3, getOperatorId());
                ps.setObject(4, getXsiteId());
                ps.setObject(5, getAmountLiters());
                ps.setObject(6, getPersonissId());
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
                "DELETE FROM xdieselcard " +
                "WHERE xdieselcard_id = " + getXdieselcardId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXdieselcardId(new Integer(-getXdieselcardId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXdieselcardId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xdieselcard_id,carddate,xmachine_id,operator_id,xsite_id,amount_liters,personiss_id FROM xdieselcard " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xdieselcard(con,new Integer(rs.getInt(1)),rs.getDate(2),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),new Integer(rs.getInt(6)),new Integer(rs.getInt(7))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xdieselcard[] objects = new Xdieselcard[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xdieselcard xdieselcard = (Xdieselcard) lst.get(i);
            objects[i] = xdieselcard;
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
        String stmt = "SELECT xdieselcard_id FROM xdieselcard " +
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
    //    return getXdieselcardId() + getDelimiter();
    //}

    public Integer getXdieselcardId() {
        return xdieselcardId;
    }

    public void setXdieselcardId(Integer xdieselcardId) throws ForeignKeyViolationException {
        setWasChanged(this.xdieselcardId != null && this.xdieselcardId != xdieselcardId);
        this.xdieselcardId = xdieselcardId;
        setNew(xdieselcardId.intValue() == 0);
    }

    public Date getCarddate() {
        return carddate;
    }

    public void setCarddate(Date carddate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.carddate != null && !this.carddate.equals(carddate));
        this.carddate = carddate;
    }

    public Integer getXmachineId() {
        return xmachineId;
    }

    public void setXmachineId(Integer xmachineId) throws SQLException, ForeignKeyViolationException {
        if (xmachineId!=null && !Xmachine.exists(getConnection(),"xmachine_id = " + xmachineId)) {
            throw new ForeignKeyViolationException("Can't set xmachine_id, foreign key violation: xdieselcard_xmachine_fk");
        }
        setWasChanged(this.xmachineId != null && !this.xmachineId.equals(xmachineId));
        this.xmachineId = xmachineId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) throws SQLException, ForeignKeyViolationException {
        if (operatorId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + operatorId)) {
            throw new ForeignKeyViolationException("Can't set operator_id, foreign key violation: xdieselcard_xemployee_fk");
        }
        setWasChanged(this.operatorId != null && !this.operatorId.equals(operatorId));
        this.operatorId = operatorId;
    }

    public Integer getXsiteId() {
        return xsiteId;
    }

    public void setXsiteId(Integer xsiteId) throws SQLException, ForeignKeyViolationException {
        if (xsiteId!=null && !Xsite.exists(getConnection(),"xsite_id = " + xsiteId)) {
            throw new ForeignKeyViolationException("Can't set xsite_id, foreign key violation: xdieselcard_xsite_fk");
        }
        setWasChanged(this.xsiteId != null && !this.xsiteId.equals(xsiteId));
        this.xsiteId = xsiteId;
    }

    public Integer getAmountLiters() {
        return amountLiters;
    }

    public void setAmountLiters(Integer amountLiters) throws SQLException, ForeignKeyViolationException {
        if (null != amountLiters)
            amountLiters = amountLiters == 0 ? null : amountLiters;
        setWasChanged(this.amountLiters != null && !this.amountLiters.equals(amountLiters));
        this.amountLiters = amountLiters;
    }

    public Integer getPersonissId() {
        return personissId;
    }

    public void setPersonissId(Integer personissId) throws SQLException, ForeignKeyViolationException {
        if (null != personissId)
            personissId = personissId == 0 ? null : personissId;
        if (personissId!=null && !Xemployee.exists(getConnection(),"xemployee_id = " + personissId)) {
            throw new ForeignKeyViolationException("Can't set personiss_id, foreign key violation: xdieselcard_xemployee_fk2");
        }
        setWasChanged(this.personissId != null && !this.personissId.equals(personissId));
        this.personissId = personissId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[7];
        columnValues[0] = getXdieselcardId();
        columnValues[1] = getCarddate();
        columnValues[2] = getXmachineId();
        columnValues[3] = getOperatorId();
        columnValues[4] = getXsiteId();
        columnValues[5] = getAmountLiters();
        columnValues[6] = getPersonissId();
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
            setXdieselcardId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXdieselcardId(null);
        }
        setCarddate(toDate(flds[1]));
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
        try {
            setXsiteId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setXsiteId(null);
        }
        try {
            setAmountLiters(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setAmountLiters(null);
        }
        try {
            setPersonissId(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setPersonissId(null);
        }
    }
}
