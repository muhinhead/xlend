// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Jan 29 15:15:44 EET 2013
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xaccounts extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer xaccountId = null;
    private String accname = null;
    private String accnumber = null;
    private String bank = null;
    private String branch = null;

    public Xaccounts(Connection connection) {
        super(connection, "xaccounts", "xaccount_id");
        setColumnNames(new String[]{"xaccount_id", "accname", "accnumber", "bank", "branch"});
    }

    public Xaccounts(Connection connection, Integer xaccountId, String accname, String accnumber, String bank, String branch) {
        super(connection, "xaccounts", "xaccount_id");
        setNew(xaccountId.intValue() <= 0);
//        if (xaccountId.intValue() != 0) {
            this.xaccountId = xaccountId;
//        }
        this.accname = accname;
        this.accnumber = accnumber;
        this.bank = bank;
        this.branch = branch;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xaccounts xaccounts = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xaccount_id,accname,accnumber,bank,branch FROM xaccounts WHERE xaccount_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xaccounts = new Xaccounts(getConnection());
                xaccounts.setXaccountId(new Integer(rs.getInt(1)));
                xaccounts.setAccname(rs.getString(2));
                xaccounts.setAccnumber(rs.getString(3));
                xaccounts.setBank(rs.getString(4));
                xaccounts.setBranch(rs.getString(5));
                xaccounts.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xaccounts;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xaccounts ("+(getXaccountId().intValue()!=0?"xaccount_id,":"")+"accname,accnumber,bank,branch) values("+(getXaccountId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXaccountId().intValue()!=0) {
                 ps.setObject(++n, getXaccountId());
             }
             ps.setObject(++n, getAccname());
             ps.setObject(++n, getAccnumber());
             ps.setObject(++n, getBank());
             ps.setObject(++n, getBranch());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXaccountId().intValue()==0) {
             stmt = "SELECT max(xaccount_id) FROM xaccounts";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXaccountId(new Integer(rs.getInt(1)));
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
                    "UPDATE xaccounts " +
                    "SET accname = ?, accnumber = ?, bank = ?, branch = ?" + 
                    " WHERE xaccount_id = " + getXaccountId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getAccname());
                ps.setObject(2, getAccnumber());
                ps.setObject(3, getBank());
                ps.setObject(4, getBranch());
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
        if (Xbankbalancepart.exists(getConnection(),"xaccount_id = " + getXaccountId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: xbankbalancepart_xaccounts_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM xaccounts " +
                "WHERE xaccount_id = " + getXaccountId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXaccountId(new Integer(-getXaccountId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXaccountId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xaccount_id,accname,accnumber,bank,branch FROM xaccounts " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xaccounts(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xaccounts[] objects = new Xaccounts[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xaccounts xaccounts = (Xaccounts) lst.get(i);
            objects[i] = xaccounts;
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
        String stmt = "SELECT xaccount_id FROM xaccounts " +
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
    //    return getXaccountId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xaccountId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXaccountId(id);
        setNew(prevIsNew);
    }

    public Integer getXaccountId() {
        return xaccountId;
    }

    public void setXaccountId(Integer xaccountId) throws ForeignKeyViolationException {
        setWasChanged(this.xaccountId != null && this.xaccountId != xaccountId);
        this.xaccountId = xaccountId;
        setNew(xaccountId.intValue() == 0);
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.accname != null && !this.accname.equals(accname));
        this.accname = accname;
    }

    public String getAccnumber() {
        return accnumber;
    }

    public void setAccnumber(String accnumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.accnumber != null && !this.accnumber.equals(accnumber));
        this.accnumber = accnumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.bank != null && !this.bank.equals(bank));
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.branch != null && !this.branch.equals(branch));
        this.branch = branch;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[5];
        columnValues[0] = getXaccountId();
        columnValues[1] = getAccname();
        columnValues[2] = getAccnumber();
        columnValues[3] = getBank();
        columnValues[4] = getBranch();
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
            setXaccountId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXaccountId(null);
        }
        setAccname(flds[1]);
        setAccnumber(flds[2]);
        setBank(flds[3]);
        setBranch(flds[4]);
    }
}
