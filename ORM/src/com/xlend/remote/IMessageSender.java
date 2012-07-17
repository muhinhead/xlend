package com.xlend.remote;

//import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.DbObject;
import java.util.Vector;

/**
 *
 * @author Nick Mukhin
 */
public interface IMessageSender extends java.rmi.Remote {
    public DbObject[] getDbObjects(Class dbobClass, String whereCondition, 
            String orderCondition) throws java.rmi.RemoteException;
    public DbObject saveDbObject(DbObject dbob) throws java.rmi.RemoteException;
    public void deleteObject(DbObject dbob) throws java.rmi.RemoteException;
    public DbObject loadDbObjectOnID(Class dbobClass, int id) 
            throws java.rmi.RemoteException;
    public Vector[] getTableBody(String select) throws java.rmi.RemoteException;
    public Vector[] getTableBody(String select, int page, int pagesize) throws java.rmi.RemoteException;
    public int getCount(String select) throws java.rmi.RemoteException;
    public void startTransaction(String transactionName) throws java.rmi.RemoteException;
    public void commitTransaction() throws java.rmi.RemoteException;
    public void rollbackTransaction(String transactionName) throws java.rmi.RemoteException;
}
