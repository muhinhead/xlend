// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon May 21 17:06:14 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xquotation extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xquotationId = null;
    private Integer xclientId = null;
    private String rfcnumber = null;
    private Date received = null;
    private Date deadline = null;
    private Date responded = null;
    private String responsedby = null;
    private String responsecmnt = null;
    private Integer responderId = null;
    private Object responsedoc = null;

    public Xquotation(Connection connection) {
        super(connection, "xquotation", "xquotation_id");
        setColumnNames(new String[]{"xquotation_id", "xclient_id", "rfcnumber", "received", "deadline", "responded", "responsedby", "responsecmnt", "responder_id", "responsedoc"});
    }

    public Xquotation(Connection connection, Integer xquotationId, Integer xclientId, String rfcnumber, Date received, Date deadline, Date responded, String responsedby, String responsecmnt, Integer responderId, Object responsedoc) {
        super(connection, "xquotation", "xquotation_id");
        setNew(xquotationId.intValue() <= 0);
//        if (xquotationId.intValue() != 0) {
            this.xquotationId = xquotationId;
//        }
        this.xclientId = xclientId;
        this.rfcnumber = rfcnumber;
        this.received = received;
        this.deadline = deadline;
        this.responded = responded;
        this.responsedby = responsedby;
        this.responsecmnt = responsecmnt;
        this.responderId = responderId;
        this.responsedoc = responsedoc;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xquotation xquotation = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xquotation_id,xclient_id,rfcnumber,received,deadline,responded,responsedby,responsecmnt,responder_id,responsedoc FROM xquotation WHERE xquotation_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xquotation = new Xquotation(getConnection());
                xquotation.setXquotationId(new Integer(rs.getInt(1)));
                xquotation.setXclientId(new Integer(rs.getInt(2)));
                xquotation.setRfcnumber(rs.getString(3));
                xquotation.setReceived(rs.getDate(4));
                xquotation.setDeadline(rs.getDate(5));
                xquotation.setResponded(rs.getDate(6));
                xquotation.setResponsedby(rs.getString(7));
                xquotation.setResponsecmnt(rs.getString(8));
                xquotation.setResponderId(new Integer(rs.getInt(9)));
                xquotation.setResponsedoc(rs.getObject(10));
                xquotation.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xquotation;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xquotation ("+(getXquotationId().intValue()!=0?"xquotation_id,":"")+"xclient_id,rfcnumber,received,deadline,responded,responsedby,responsecmnt,responder_id,responsedoc) values("+(getXquotationId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXquotationId().intValue()!=0) {
                 ps.setObject(++n, getXquotationId());
             }
             ps.setObject(++n, getXclientId());
             ps.setObject(++n, getRfcnumber());
             ps.setObject(++n, getReceived());
             ps.setObject(++n, getDeadline());
             ps.setObject(++n, getResponded());
             ps.setObject(++n, getResponsedby());
             ps.setObject(++n, getResponsecmnt());
             ps.setObject(++n, getResponderId());
             ps.setObject(++n, getResponsedoc());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXquotationId().intValue()==0) {
             stmt = "SELECT max(xquotation_id) FROM xquotation";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXquotationId(new Integer(rs.getInt(1)));
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
                    "UPDATE xquotation " +
                    "SET xclient_id = ?, rfcnumber = ?, received = ?, deadline = ?, responded = ?, responsedby = ?, responsecmnt = ?, responder_id = ?, responsedoc = ?" + 
                    " WHERE xquotation_id = " + getXquotationId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXclientId());
                ps.setObject(2, getRfcnumber());
                ps.setObject(3, getReceived());
                ps.setObject(4, getDeadline());
                ps.setObject(5, getResponded());
                ps.setObject(6, getResponsedby());
                ps.setObject(7, getResponsecmnt());
                ps.setObject(8, getResponderId());
                ps.setObject(9, getResponsedoc());
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
        if (Xorder.exists(getConnection(),"xquotation_id = " + getXquotationId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xorder_xquotation_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from xquotationpage
            Xquotationpage[] records = (Xquotationpage[])Xquotationpage.load(getConnection(),"xquotation_id = " + getXquotationId(),null);
            for (int i = 0; i<records.length; i++) {
                Xquotationpage xquotationpage = records[i];
                xquotationpage.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xquotation " +
                "WHERE xquotation_id = " + getXquotationId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXquotationId(new Integer(-getXquotationId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXquotationId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xquotation_id,xclient_id,rfcnumber,received,deadline,responded,responsedby,responsecmnt,responder_id,responsedoc FROM xquotation " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xquotation(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3),rs.getDate(4),rs.getDate(5),rs.getDate(6),rs.getString(7),rs.getString(8),new Integer(rs.getInt(9)),rs.getObject(10)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xquotation[] objects = new Xquotation[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xquotation xquotation = (Xquotation) lst.get(i);
            objects[i] = xquotation;
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
        String stmt = "SELECT xquotation_id FROM xquotation " +
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
    //    return getXquotationId() + getDelimiter();
    //}

    public Integer getXquotationId() {
        return xquotationId;
    }

    public void setXquotationId(Integer xquotationId) throws ForeignKeyViolationException {
        setWasChanged(this.xquotationId != null && this.xquotationId != xquotationId);
        this.xquotationId = xquotationId;
        setNew(xquotationId.intValue() == 0);
    }

    public Integer getXclientId() {
        return xclientId;
    }

    public void setXclientId(Integer xclientId) throws SQLException, ForeignKeyViolationException {
        if (xclientId!=null && !Xclient.exists(getConnection(),"xclient_id = " + xclientId)) {
            throw new ForeignKeyViolationException("Can't set xclient_id, foreign key violation: xquotation_xclient_fk");
        }
        setWasChanged(this.xclientId != null && !this.xclientId.equals(xclientId));
        this.xclientId = xclientId;
    }

    public String getRfcnumber() {
        return rfcnumber;
    }

    public void setRfcnumber(String rfcnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.rfcnumber != null && !this.rfcnumber.equals(rfcnumber));
        this.rfcnumber = rfcnumber;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.received != null && !this.received.equals(received));
        this.received = received;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.deadline != null && !this.deadline.equals(deadline));
        this.deadline = deadline;
    }

    public Date getResponded() {
        return responded;
    }

    public void setResponded(Date responded) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.responded != null && !this.responded.equals(responded));
        this.responded = responded;
    }

    public String getResponsedby() {
        return responsedby;
    }

    public void setResponsedby(String responsedby) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.responsedby != null && !this.responsedby.equals(responsedby));
        this.responsedby = responsedby;
    }

    public String getResponsecmnt() {
        return responsecmnt;
    }

    public void setResponsecmnt(String responsecmnt) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.responsecmnt != null && !this.responsecmnt.equals(responsecmnt));
        this.responsecmnt = responsecmnt;
    }

    public Integer getResponderId() {
        return responderId;
    }

    public void setResponderId(Integer responderId) throws SQLException, ForeignKeyViolationException {
        if (null != responderId)
            responderId = responderId == 0 ? null : responderId;
        if (responderId!=null && !Userprofile.exists(getConnection(),"profile_id = " + responderId)) {
            throw new ForeignKeyViolationException("Can't set responder_id, foreign key violation: xquotation_profile_fk");
        }
        setWasChanged(this.responderId != null && !this.responderId.equals(responderId));
        this.responderId = responderId;
    }

    public Object getResponsedoc() {
        return responsedoc;
    }

    public void setResponsedoc(Object responsedoc) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.responsedoc != null && !this.responsedoc.equals(responsedoc));
        this.responsedoc = responsedoc;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[10];
        columnValues[0] = getXquotationId();
        columnValues[1] = getXclientId();
        columnValues[2] = getRfcnumber();
        columnValues[3] = getReceived();
        columnValues[4] = getDeadline();
        columnValues[5] = getResponded();
        columnValues[6] = getResponsedby();
        columnValues[7] = getResponsecmnt();
        columnValues[8] = getResponderId();
        columnValues[9] = getResponsedoc();
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
            setXquotationId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXquotationId(null);
        }
        try {
            setXclientId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXclientId(null);
        }
        setRfcnumber(flds[2]);
        setReceived(toDate(flds[3]));
        setDeadline(toDate(flds[4]));
        setResponded(toDate(flds[5]));
        setResponsedby(flds[6]);
        setResponsecmnt(flds[7]);
        try {
            setResponderId(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setResponderId(null);
        }
        setResponsedoc(flds[9]);
    }
}
