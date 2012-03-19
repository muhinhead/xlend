package com.xlend.mvc.dbtable;

import com.xlend.mvc.Document;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
public class DbTableDocument extends Document {

    private String selectStatement;
    private Object[] selectParams = null;
    private Vector rowData;
    private Vector colNames;
    private Connection connection;
    private static final String errDb = "Database error:";
    private String filterText = null;

    public DbTableDocument(String name, Object[] body) {
        super(name);
        setBody(body);
    }

    public DbTableDocument(String name, String select, Connection connection) {
        super(name, new Object[]{select, connection});
    }

    public DbTableDocument(String name, String select, Object[] parameters,
            Connection connection) {
        super(name, new Object[]{select, connection, parameters});
        selectParams = parameters;
    }

    protected void initialize(Object initObject) {
        if (colNames == null) {
            colNames = new Vector();
        } else {
            colNames.clear();
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] arr = (Object[]) initObject;
        selectStatement = (String) arr[0];
        connection = (Connection) arr[1];
        try {
            ps = connection.prepareStatement(selectStatement);
            if (arr.length > 2) {
                selectParams = (Object[]) arr[2];
                int n = 1;
                for (Object param : selectParams) {
                    ps.setObject(n++, param);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            for (int i = 0; i < md.getColumnCount(); i++) {
                colNames.add(md.getColumnLabel(i + 1));
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, se.toString(), errDb, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DbTableDocument.class.getName()).log(Level.SEVERE, null, se);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se1) {
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException se2) {
                }
            }
        }
    }

    protected Vector loadData() {
        Vector rows = new Vector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Vector line;
            int i;
            ps = connection.prepareStatement(selectStatement);
            if (selectParams != null) {
                int n = 1;
                for (Object param : selectParams) {
                    ps.setObject(n++, param);
                }
            }
            rs = ps.executeQuery();
            boolean filtered = true;
            while (rs.next()) {
                line = new Vector();
                for (i = 1; i <= colNames.size(); i++) {
                    String ceil = rs.getString(i);
                    ceil = ceil == null ? "" : ceil;
                    if (filterText != null && filterText.trim().length() > 0) {
                        filtered = (ceil.toUpperCase().indexOf(filterText.toUpperCase()) >= 0);
                    }
                    line.add(ceil);
                }
                if (filtered) {
                    rows.add(line);
                }
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, se.toString(), errDb, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DbTableDocument.class.getName()).log(Level.SEVERE, null, se);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se1) {
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException se2) {
                }
            }
        }
        return rows;
    }

    public Object getBody() {
        if (rowData == null || rowData.size() == 0) {
            if (connection != null) {
                rowData = loadData();
            }
        }
        Object[] content = new Object[]{colNames, rowData};
        return content;
    }

    public void setBody(String select, Object[] params) {
        initialize(new Object[]{select, connection, params});
        rowData = loadData();
    }

    public void setBody(Object body) {
        if (body instanceof String) {
            initialize(new Object[]{body, connection});
            rowData = loadData();
        } else {
            Object[] content = (Object[]) body;
            colNames = (Vector) content[0];
            rowData = (Vector) content[1];
        }
    }

    public String getCeil(int row, int col) {
        Vector line = (Vector) rowData.get(row);
        return (String) line.get(col);
    }

    public String getSelectStatement() {
        return selectStatement;
    }

    public Vector getRowData() {
        return rowData;
    }

    public void setFilter(String text) {
        filterText = text;
    }
}
