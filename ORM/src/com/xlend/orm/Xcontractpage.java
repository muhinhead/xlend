// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Fri Jun 22 09:50:02 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xcontractpage extends DbObject implements IPage {
    private static Triggers activeTriggers = null;
    private Integer xcontractpageId = null;
    private Integer xcontractId = null;
    private Integer pagenum = null;
    private String description = null;
    private String fileextension = null;
    private Object pagescan = null;

    public Xcontractpage(Connection connection) {
        super(connection, "xcontractpage", "xcontractpage_id");
        setColumnNames(new String[]{"xcontractpage_id", "xcontract_id", "pagenum", "description", "fileextension", "pagescan"});
    }

    public Xcontractpage(Connection connection, Integer xcontractpageId, Integer xcontractId, Integer pagenum, String description, String fileextension, Object pagescan) {
        super(connection, "xcontractpage", "xcontractpage_id");
        setNew(xcontractpageId.intValue() <= 0);
//        if (xcontractpageId.intValue() != 0) {
            this.xcontractpageId = xcontractpageId;
//        }
        this.xcontractId = xcontractId;
        this.pagenum = pagenum;
        this.description = description;
        this.fileextension = fileextension;
        this.pagescan = pagescan;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xcontractpage xcontractpage = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xcontractpage_id,xcontract_id,pagenum,description,fileextension,pagescan FROM xcontractpage WHERE xcontractpage_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xcontractpage = new Xcontractpage(getConnection());
                xcontractpage.setXcontractpageId(new Integer(rs.getInt(1)));
                xcontractpage.setXcontractId(new Integer(rs.getInt(2)));
                xcontractpage.setPagenum(new Integer(rs.getInt(3)));
                xcontractpage.setDescription(rs.getString(4));
                xcontractpage.setFileextension(rs.getString(5));
                xcontractpage.setPagescan(rs.getObject(6));
                xcontractpage.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xcontractpage;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xcontractpage ("+(getXcontractpageId().intValue()!=0?"xcontractpage_id,":"")+"xcontract_id,pagenum,description,fileextension,pagescan) values("+(getXcontractpageId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXcontractpageId().intValue()!=0) {
                 ps.setObject(++n, getXcontractpageId());
             }
             ps.setObject(++n, getXcontractId());
             ps.setObject(++n, getPagenum());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getFileextension());
             ps.setObject(++n, getPagescan());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXcontractpageId().intValue()==0) {
             stmt = "SELECT max(xcontractpage_id) FROM xcontractpage";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXcontractpageId(new Integer(rs.getInt(1)));
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
                    "UPDATE xcontractpage " +
                    "SET xcontract_id = ?, pagenum = ?, description = ?, fileextension = ?, pagescan = ?" + 
                    " WHERE xcontractpage_id = " + getXcontractpageId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXcontractId());
                ps.setObject(2, getPagenum());
                ps.setObject(3, getDescription());
                ps.setObject(4, getFileextension());
                ps.setObject(5, getPagescan());
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
                "DELETE FROM xcontractpage " +
                "WHERE xcontractpage_id = " + getXcontractpageId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXcontractpageId(new Integer(-getXcontractpageId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXcontractpageId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xcontractpage_id,xcontract_id,pagenum,description,fileextension,pagescan FROM xcontractpage " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xcontractpage(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getString(4),rs.getString(5),rs.getObject(6)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xcontractpage[] objects = new Xcontractpage[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xcontractpage xcontractpage = (Xcontractpage) lst.get(i);
            objects[i] = xcontractpage;
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
        String stmt = "SELECT xcontractpage_id FROM xcontractpage " +
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
    //    return getXcontractpageId() + getDelimiter();
    //}

    public Integer getXcontractpageId() {
        return xcontractpageId;
    }

    public void setXcontractpageId(Integer xcontractpageId) throws ForeignKeyViolationException {
        setWasChanged(this.xcontractpageId != null && this.xcontractpageId != xcontractpageId);
        this.xcontractpageId = xcontractpageId;
        setNew(xcontractpageId.intValue() == 0);
    }

    public Integer getXcontractId() {
        return xcontractId;
    }

    public void setXcontractId(Integer xcontractId) throws SQLException, ForeignKeyViolationException {
        if (xcontractId!=null && !Xcontract.exists(getConnection(),"xcontract_id = " + xcontractId)) {
            throw new ForeignKeyViolationException("Can't set xcontract_id, foreign key violation: xcontractpage_xcontract_fk");
        }
        setWasChanged(this.xcontractId != null && !this.xcontractId.equals(xcontractId));
        this.xcontractId = xcontractId;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) throws SQLException, ForeignKeyViolationException {
        if (null != pagenum)
            pagenum = pagenum == 0 ? null : pagenum;
        setWasChanged(this.pagenum != null && !this.pagenum.equals(pagenum));
        this.pagenum = pagenum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }

    public String getFileextension() {
        return fileextension;
    }

    public void setFileextension(String fileextension) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.fileextension != null && !this.fileextension.equals(fileextension));
        this.fileextension = fileextension;
    }

    public Object getPagescan() {
        return pagescan;
    }

    public void setPagescan(Object pagescan) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.pagescan != null && !this.pagescan.equals(pagescan));
        this.pagescan = pagescan;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[6];
        columnValues[0] = getXcontractpageId();
        columnValues[1] = getXcontractId();
        columnValues[2] = getPagenum();
        columnValues[3] = getDescription();
        columnValues[4] = getFileextension();
        columnValues[5] = getPagescan();
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
            setXcontractpageId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXcontractpageId(null);
        }
        try {
            setXcontractId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXcontractId(null);
        }
        try {
            setPagenum(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setPagenum(null);
        }
        setDescription(flds[3]);
        setFileextension(flds[4]);
        setPagescan(flds[5]);
    }
}
