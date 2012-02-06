// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Feb 06 12:27:35 EET 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xquotationpage extends DbObject implements IPage {
    private static Triggers activeTriggers = null;
    private Integer xquotationpageId = null;
    private Integer xquotationId = null;
    private Integer pagenum = null;
    private String description = null;
    private String fileextension = null;
    private Object pagescan = null;

    public Xquotationpage(Connection connection) {
        super(connection, "xquotationpage", "xquotationpage_id");
        setColumnNames(new String[]{"xquotationpage_id", "xquotation_id", "pagenum", "description", "fileextension", "pagescan"});
    }

    public Xquotationpage(Connection connection, Integer xquotationpageId, Integer xquotationId, Integer pagenum, String description, String fileextension, Object pagescan) {
        super(connection, "xquotationpage", "xquotationpage_id");
        setNew(xquotationpageId.intValue() <= 0);
//        if (xquotationpageId.intValue() != 0) {
            this.xquotationpageId = xquotationpageId;
//        }
        this.xquotationId = xquotationId;
        this.pagenum = pagenum;
        this.description = description;
        this.fileextension = fileextension;
        this.pagescan = pagescan;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xquotationpage xquotationpage = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xquotationpage_id,xquotation_id,pagenum,description,fileextension,pagescan FROM xquotationpage WHERE xquotationpage_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xquotationpage = new Xquotationpage(getConnection());
                xquotationpage.setXquotationpageId(new Integer(rs.getInt(1)));
                xquotationpage.setXquotationId(new Integer(rs.getInt(2)));
                xquotationpage.setPagenum(new Integer(rs.getInt(3)));
                xquotationpage.setDescription(rs.getString(4));
                xquotationpage.setFileextension(rs.getString(5));
                xquotationpage.setPagescan(rs.getObject(6));
                xquotationpage.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xquotationpage;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xquotationpage ("+(getXquotationpageId().intValue()!=0?"xquotationpage_id,":"")+"xquotation_id,pagenum,description,fileextension,pagescan) values("+(getXquotationpageId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXquotationpageId().intValue()!=0) {
                 ps.setObject(++n, getXquotationpageId());
             }
             ps.setObject(++n, getXquotationId());
             ps.setObject(++n, getPagenum());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getFileextension());
             ps.setObject(++n, getPagescan());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXquotationpageId().intValue()==0) {
             stmt = "SELECT max(xquotationpage_id) FROM xquotationpage";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXquotationpageId(new Integer(rs.getInt(1)));
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
                    "UPDATE xquotationpage " +
                    "SET xquotation_id = ?, pagenum = ?, description = ?, fileextension = ?, pagescan = ?" + 
                    " WHERE xquotationpage_id = " + getXquotationpageId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXquotationId());
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
                "DELETE FROM xquotationpage " +
                "WHERE xquotationpage_id = " + getXquotationpageId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXquotationpageId(new Integer(-getXquotationpageId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXquotationpageId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xquotationpage_id,xquotation_id,pagenum,description,fileextension,pagescan FROM xquotationpage " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xquotationpage(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getString(4),rs.getString(5),rs.getObject(6)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xquotationpage[] objects = new Xquotationpage[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xquotationpage xquotationpage = (Xquotationpage) lst.get(i);
            objects[i] = xquotationpage;
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
        String stmt = "SELECT xquotationpage_id FROM xquotationpage " +
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
    //    return getXquotationpageId() + getDelimiter();
    //}

    public Integer getXquotationpageId() {
        return xquotationpageId;
    }

    public void setXquotationpageId(Integer xquotationpageId) throws ForeignKeyViolationException {
        setWasChanged(this.xquotationpageId != null && this.xquotationpageId != xquotationpageId);
        this.xquotationpageId = xquotationpageId;
        setNew(xquotationpageId.intValue() == 0);
    }

    public Integer getXquotationId() {
        return xquotationId;
    }

    public void setXquotationId(Integer xquotationId) throws SQLException, ForeignKeyViolationException {
        if (xquotationId!=null && !Xquotation.exists(getConnection(),"xquotation_id = " + xquotationId)) {
            throw new ForeignKeyViolationException("Can't set xquotation_id, foreign key violation: xquotationpage_xquotation_fk");
        }
        setWasChanged(this.xquotationId != null && !this.xquotationId.equals(xquotationId));
        this.xquotationId = xquotationId;
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
        columnValues[0] = getXquotationpageId();
        columnValues[1] = getXquotationId();
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
            setXquotationpageId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXquotationpageId(null);
        }
        try {
            setXquotationId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXquotationId(null);
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
