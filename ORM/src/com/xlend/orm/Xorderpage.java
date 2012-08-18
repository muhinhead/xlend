// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Aug 18 09:44:46 EEST 2012
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Xorderpage extends DbObject implements IPage {
    private static Triggers activeTriggers = null;
    private Integer xorderpageId = null;
    private Integer xorderId = null;
    private Integer pagenum = null;
    private String description = null;
    private String fileextension = null;
    private Object pagescan = null;

    public Xorderpage(Connection connection) {
        super(connection, "xorderpage", "xorderpage_id");
        setColumnNames(new String[]{"xorderpage_id", "xorder_id", "pagenum", "description", "fileextension", "pagescan"});
    }

    public Xorderpage(Connection connection, Integer xorderpageId, Integer xorderId, Integer pagenum, String description, String fileextension, Object pagescan) {
        super(connection, "xorderpage", "xorderpage_id");
        setNew(xorderpageId.intValue() <= 0);
//        if (xorderpageId.intValue() != 0) {
            this.xorderpageId = xorderpageId;
//        }
        this.xorderId = xorderId;
        this.pagenum = pagenum;
        this.description = description;
        this.fileextension = fileextension;
        this.pagescan = pagescan;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xorderpage xorderpage = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xorderpage_id,xorder_id,pagenum,description,fileextension,pagescan FROM xorderpage WHERE xorderpage_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                xorderpage = new Xorderpage(getConnection());
                xorderpage.setXorderpageId(new Integer(rs.getInt(1)));
                xorderpage.setXorderId(new Integer(rs.getInt(2)));
                xorderpage.setPagenum(new Integer(rs.getInt(3)));
                xorderpage.setDescription(rs.getString(4));
                xorderpage.setFileextension(rs.getString(5));
                xorderpage.setPagescan(rs.getObject(6));
                xorderpage.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return xorderpage;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO xorderpage ("+(getXorderpageId().intValue()!=0?"xorderpage_id,":"")+"xorder_id,pagenum,description,fileextension,pagescan) values("+(getXorderpageId().intValue()!=0?"?,":"")+"?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getXorderpageId().intValue()!=0) {
                 ps.setObject(++n, getXorderpageId());
             }
             ps.setObject(++n, getXorderId());
             ps.setObject(++n, getPagenum());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getFileextension());
             ps.setObject(++n, getPagescan());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getXorderpageId().intValue()==0) {
             stmt = "SELECT max(xorderpage_id) FROM xorderpage";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setXorderpageId(new Integer(rs.getInt(1)));
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
                    "UPDATE xorderpage " +
                    "SET xorder_id = ?, pagenum = ?, description = ?, fileextension = ?, pagescan = ?" + 
                    " WHERE xorderpage_id = " + getXorderpageId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getXorderId());
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
                "DELETE FROM xorderpage " +
                "WHERE xorderpage_id = " + getXorderpageId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setXorderpageId(new Integer(-getXorderpageId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getXorderpageId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT xorderpage_id,xorder_id,pagenum,description,fileextension,pagescan FROM xorderpage " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Xorderpage(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getString(4),rs.getString(5),rs.getObject(6)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Xorderpage[] objects = new Xorderpage[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Xorderpage xorderpage = (Xorderpage) lst.get(i);
            objects[i] = xorderpage;
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
        String stmt = "SELECT xorderpage_id FROM xorderpage " +
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
    //    return getXorderpageId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return xorderpageId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setXorderpageId(id);
        setNew(prevIsNew);
    }

    public Integer getXorderpageId() {
        return xorderpageId;
    }

    public void setXorderpageId(Integer xorderpageId) throws ForeignKeyViolationException {
        setWasChanged(this.xorderpageId != null && this.xorderpageId != xorderpageId);
        this.xorderpageId = xorderpageId;
        setNew(xorderpageId.intValue() == 0);
    }

    public Integer getXorderId() {
        return xorderId;
    }

    public void setXorderId(Integer xorderId) throws SQLException, ForeignKeyViolationException {
        if (xorderId!=null && !Xorder.exists(getConnection(),"xorder_id = " + xorderId)) {
            throw new ForeignKeyViolationException("Can't set xorder_id, foreign key violation: xorderpage_xorder_fk");
        }
        setWasChanged(this.xorderId != null && !this.xorderId.equals(xorderId));
        this.xorderId = xorderId;
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
        columnValues[0] = getXorderpageId();
        columnValues[1] = getXorderId();
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
            setXorderpageId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setXorderpageId(null);
        }
        try {
            setXorderId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setXorderId(null);
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
