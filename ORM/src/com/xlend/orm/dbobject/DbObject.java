package com.xlend.orm.dbobject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Nick Mukhin
 */
public abstract class DbObject implements Serializable {

    private transient Connection connection = null;
    private String tableName = null;
    private String idColumnName = null;
    private String[] columnNames = null;
    private boolean isNew = true;
    private boolean wasChanged = false;
    protected static String delimiter = "\t";

    public DbObject(Connection connection) {
        this.setConnection(connection);
    }

    public DbObject(Connection connection, String tableName, String idColumnName) {
        this.setConnection(connection);
        this.setTableName(tableName);
        this.setIdColumnName(idColumnName);
    }

    public abstract DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException;

    protected abstract void insert() throws SQLException, ForeignKeyViolationException;

    public abstract void save() throws SQLException, ForeignKeyViolationException;

    public abstract void delete() throws SQLException, ForeignKeyViolationException;

    public abstract boolean isDeleted();
//    public abstract void restore() throws SQLException;

    public static DbObject[] load(Connection con, String whereCondition, String orderCondition)
            throws SQLException {
        return null;
    }

    public static boolean exists(Connection con, String whereCondition) throws SQLException {
        return false;
    }

    public String getHeaderLine() {
        String result = "";
        for (int i = 0; i < getColumnNames().length; i++) {
            if (i > 0) {
                result += getDelimiter();
            }
            result += getColumnNames()[i];
        }
        return result;
    }

    public abstract Object[] getAsRow();

    public String toString() {
        Object[] objRow = getAsRow();
        StringBuffer row = new StringBuffer();
        for (Object fld : objRow) {
            if (row.length() > 0) {
                row.append(delimiter);
            }
            row.append(fld == null ? " " : fld.toString());
        }
        return row.toString();
    }

    public abstract void fillFromString(String row) throws ForeignKeyViolationException, SQLException;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isWasChanged() {
        return wasChanged;
    }

    public void setWasChanged(boolean wasChanged) {
        this.wasChanged = wasChanged;
    }

    public static String getDelimiter() {
        return delimiter;
    }

    public static void setDelimiter(String delimiter) {
        DbObject.delimiter = delimiter;
    }

    public static Date toDate(String yearMonthDay) {
        int year = Integer.parseInt(yearMonthDay.substring(0, 4)) - 1900;
        int month = Integer.parseInt(yearMonthDay.substring(5, 7)) - 1;
        int day = Integer.parseInt(yearMonthDay.substring(8, 10));
        return new Date(year, month, day);
    }

    public static String[] splitStr(String line, String delim) {
        Vector v = split(line, delim.charAt(0));
        String[] ans = new String[v.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (String) v.get(i);
        }
        return ans;
    }

    public static Vector split(String line, char delim) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == delim) {
                count++;
            }
        }
        count++;
        Vector ans = new Vector();
        int pos = 0;
        for (int i = 0; i < count; i++) {
            int nextstart = line.indexOf(delim, pos) + 1;
            if (nextstart > 0) {
                ans.add(line.substring(pos, nextstart - 1));
            } else {
                ans.add(line.substring(pos));
            }
            pos = nextstart;
        }
        return ans;
    }
}
