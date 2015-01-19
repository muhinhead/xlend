// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 19 11:03:09 EET 2015
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Picture extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer pictureId = null;
    private Object picture = null;

    public Picture(Connection connection) {
        super(connection, "picture", "picture_id");
        setColumnNames(new String[]{"picture_id", "picture"});
    }

    public Picture(Connection connection, Integer pictureId, Object picture) {
        super(connection, "picture", "picture_id");
        setNew(pictureId.intValue() <= 0);
//        if (pictureId.intValue() != 0) {
            this.pictureId = pictureId;
//        }
        this.picture = picture;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Picture picture = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT picture_id,picture FROM picture WHERE picture_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                picture = new Picture(getConnection());
                picture.setPictureId(new Integer(rs.getInt(1)));
                picture.setPicture(rs.getObject(2));
                picture.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return picture;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO picture ("+(getPictureId().intValue()!=0?"picture_id,":"")+"picture) values("+(getPictureId().intValue()!=0?"?,":"")+"?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getPictureId().intValue()!=0) {
                 ps.setObject(++n, getPictureId());
             }
             ps.setObject(++n, getPicture());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getPictureId().intValue()==0) {
             stmt = "SELECT max(picture_id) FROM picture";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setPictureId(new Integer(rs.getInt(1)));
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
                    "UPDATE picture " +
                    "SET picture = ?" + 
                    " WHERE picture_id = " + getPictureId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getPicture());
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
        if (Profile.exists(getConnection(),"picture_id = " + getPictureId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: profile_picture_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM picture " +
                "WHERE picture_id = " + getPictureId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setPictureId(new Integer(-getPictureId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getPictureId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT picture_id,picture FROM picture " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Picture(con,new Integer(rs.getInt(1)),rs.getObject(2)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Picture[] objects = new Picture[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Picture picture = (Picture) lst.get(i);
            objects[i] = picture;
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
        String stmt = "SELECT picture_id FROM picture " +
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
    //    return getPictureId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return pictureId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setPictureId(id);
        setNew(prevIsNew);
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) throws ForeignKeyViolationException {
        setWasChanged(this.pictureId != null && this.pictureId != pictureId);
        this.pictureId = pictureId;
        setNew(pictureId.intValue() == 0);
    }

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.picture != null && !this.picture.equals(picture));
        this.picture = picture;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[2];
        columnValues[0] = getPictureId();
        columnValues[1] = getPicture();
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
            setPictureId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setPictureId(null);
        }
        setPicture(flds[1]);
    }
}
