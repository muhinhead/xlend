package com.xlend.rmi;

import com.xlend.XlendServer;
import com.xlend.dbutil.DbConnection;
import com.xlend.dbutil.DbUtil;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
public class RmiMessageSender extends java.rmi.server.UnicastRemoteObject implements IMessageSender {

//    private Connection connection;
    public RmiMessageSender() throws java.rmi.RemoteException {
//        connection = DbConnection.getConnection();
//        if (null == connection) {
//            throw new java.rmi.RemoteException("Can't establish database connection");
//        }
    }

//    @Override
    public DbObject[] getDbObjects(Class dbobClass, String whereCondition,
            String orderCondition) throws RemoteException {
        DbObject[] rows = null;
        Connection connection = DbConnection.getConnection();
        try {
            Method method = dbobClass.getDeclaredMethod("load", Connection.class, String.class, String.class);
            rows = (DbObject[]) method.invoke(null, connection, whereCondition, orderCondition);
        } catch (Exception ex) {
            XlendServer.log(ex);
            throw new java.rmi.RemoteException(ex.getMessage());
        } finally {
            try {
                DbConnection.closeConnection(connection);
            } catch (SQLException ex) {
                XlendServer.log(ex);
                throw new java.rmi.RemoteException(ex.getMessage());
            }
        }
        return rows;
    }

//    @Override
    public DbObject saveDbObject(DbObject dbob) throws RemoteException {
        if (dbob != null) {
            Connection connection = DbConnection.getConnection();
            try {
                dbob.setConnection(connection);
                dbob.save();
            } catch (Exception ex) {
                XlendServer.log(ex);
                throw new java.rmi.RemoteException("Can't save DB object:", ex);
            } finally {
                try {
                    DbConnection.closeConnection(connection);
                } catch (SQLException ex) {
                    XlendServer.log(ex);
                    throw new java.rmi.RemoteException(ex.getMessage());
                }
            }
        }
        return dbob;
    }

//    @Override
    public DbObject loadDbObjectOnID(Class dbobClass, int id) throws RemoteException {
        Connection connection = DbConnection.getConnection();
        DbObject dbob = null;
        try {
            Constructor constructor = dbobClass.getConstructor(Connection.class);
            dbob = (DbObject) constructor.newInstance(DbConnection.getConnection());
            return dbob.loadOnId(id);
        } catch (Exception ex) {
            XlendServer.log(ex);
            throw new java.rmi.RemoteException(ex.getMessage());
        } finally {
            try {
                DbConnection.closeConnection(connection);
            } catch (SQLException ex) {
                XlendServer.log(ex);
                throw new java.rmi.RemoteException(ex.getMessage());
            }
        }
    }

//    @Override
    public Vector[] getTableBody(String select) throws RemoteException {
        Vector headers = getColNames(select);
        return new Vector[]{headers, getRows(select, headers.size())};
    }

    private Vector getColNames(String select) throws RemoteException {
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
            XlendServer.log(ex);
            throw new java.rmi.RemoteException(ex.getMessage());
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
                XlendServer.log(ex);
                throw new java.rmi.RemoteException(ex.getMessage());
            }
        }
    }

    private Vector getRows(String select, int cols) throws RemoteException {
        Connection connection = DbConnection.getConnection();
        Vector rows = new Vector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Vector line;
            ps = connection.prepareStatement(select);
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
            XlendServer.log(ex);
            throw new java.rmi.RemoteException(ex.getMessage());
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
                XlendServer.log(ex);
                throw new java.rmi.RemoteException(ex.getMessage());
            }
        }
    }

//    @Override
    public void deleteObject(DbObject dbob) throws RemoteException {
        if (dbob != null) {
            Connection connection = DbConnection.getConnection();
            try {
                dbob.setConnection(connection);
                dbob.delete();
            } catch (Exception ex) {
                XlendServer.log(ex);
                throw new java.rmi.RemoteException(ex.getMessage());
            } finally {
                try {
                    DbConnection.closeConnection(connection);
                } catch (SQLException ex) {
                    XlendServer.log(ex);
                    throw new java.rmi.RemoteException(ex.getMessage());
                }
            }
        }
    }

    private void execute(String stmt) throws RemoteException {
        PreparedStatement ps = null;
        Connection connection = DbConnection.getConnection();
        try {
            ps = connection.prepareStatement(stmt);
            ps.execute();
        } catch (SQLException ex) {
            XlendServer.log(ex);
            throw new java.rmi.RemoteException(ex.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException se2) {
            }
            try {
                DbConnection.closeConnection(connection);
            } catch (SQLException ex) {
                XlendServer.log(ex);
                throw new java.rmi.RemoteException(ex.getMessage());
            }
        }
    }

//    @Override
    public void startTransaction(String transactionName) throws RemoteException {
        //execute("SAVEPOINT " + transactionName);
    }

//    @Override
    public void commitTransaction() throws RemoteException {
        //execute("COMMIT");
    }

//    @Override
    public void rollbackTransaction(String transactionName) throws RemoteException {
        //execute("ROLLBACK to " + transactionName);
    }
}
