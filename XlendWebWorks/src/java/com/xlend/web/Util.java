package com.xlend.web;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.swing.ImageIcon;

/**
 *
 * @author Nick Mukhin
 */
public class Util {

    public static Image loadImage(String iconName, ServletContext servletContext) {
        Image im = null;
        String path = servletContext.getRealPath(File.separator) + "images/" + iconName;
        File f = new File(path);
        if (f.exists()) {
            try {
                ImageIcon ic = new javax.swing.ImageIcon(path, "");
                im = ic.getImage();
            } catch (Exception ex) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return im;
    }

    public static Vector[] getTableBody(String select) throws RemoteException {
        return getTableBody(select, 0, 0);
    }

    public static Vector[] getTableBody(String select, int page, int pagesize) throws java.rmi.RemoteException {
        Vector headers = getColNames(select);
        int startrow = 0, endrow = 0;
        if (page > 0 || pagesize > 0) {
            startrow = page * pagesize + 1; //int page starts from 0, int startrow starts from 1
            endrow = (page + 1) * pagesize; //last row of page
        }
        return new Vector[]{headers, getRows(select, headers.size(), startrow, endrow)};
    }

    private static Vector getColNames(String select) {
        Connection connection = DbConnection.getConnection();
        Vector colNames = new Vector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(select);
            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            for (int i = 0; i < md.getColumnCount(); i++) {
                colNames.add(md.getColumnLabel(i + 1));
            }
            return colNames;
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                DbConnection.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private static Vector getRows(String select, int cols, int startrow, int endrow) {
        Connection connection = DbConnection.getConnection();
        Vector rows = new Vector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String pagedSelect;
            if (startrow == 0 && endrow == 0) {
                pagedSelect = select;
            } else {
                pagedSelect = select.replaceFirst("select", "SELECT").replaceAll("Select", "SELECT");
                pagedSelect += (" LIMIT " + (startrow - 1) + "," + (endrow - startrow + 1));
            }
            Vector line;
            ps = connection.prepareStatement(pagedSelect);
            rs = ps.executeQuery();
            while (rs.next()) {
                line = new Vector();
                for (int c = 0; c < cols; c++) {
                    String ceil = rs.getString(c + 1);
                    ceil = ceil == null ? "" : ceil;
                    line.add(ceil);
                }
                rows.add(line);
            }
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                DbConnection.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static String showTable(String select) {
        StringBuffer ans = new StringBuffer();
        ans.append("<table class=\"gridtable\">");
        try {
            Vector[] assignmentsBody = Util.getTableBody(select);
            Vector hdr = assignmentsBody[0];
            Vector body = assignmentsBody[1];
            ans.append("<tr>");
            for (Object h : hdr) {
                ans.append("<th>" + h.toString() + "</th>");
            }
            ans.append("</tr>");
            for (Object h : body) {
                ans.append("<tr>");
                Vector line = (Vector)h;
                for (Object c : line) {
                    ans.append("<td>" + c.toString() + "</td>");
                }
                ans.append("</tr>");
            }
        } catch (Exception e) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            ans.append("<tr><td>").append(baos.toString()).append("</td></tr>");
        }
        ans.append("</table>");
        return ans.toString();
    }
}
