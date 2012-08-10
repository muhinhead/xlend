package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.TriggerAdapter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author Nick Mukhin
 */
public class XaddstocksWithTrigger extends Xaddstocks {

    public XaddstocksWithTrigger(Connection connection, Integer xaddstocksId, Integer xpartsId, Date purchaseDate, Integer enteredbyId, Integer xsupplierId, Double priceperunit, Integer quantity) {
        super(connection, xaddstocksId, xpartsId, purchaseDate, enteredbyId, xsupplierId, priceperunit, quantity);
    }

    public XaddstocksWithTrigger(Connection connection) {
        super(connection);
    }

    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
        addTriggers();
    }

    private void addTriggers() {
        TriggerAdapter t = new TriggerAdapter() {

            @Override
            public void afterInsert(DbObject dbObject) throws SQLException {
                Xaddstocks self = (Xaddstocks) dbObject;
                int qty = self.getQuantity();
                if (qty > 0) {
                    try {
                        int xpartID = self.getXpartsId();
                        Xparts part = (Xparts) new Xparts(getConnection()).loadOnId(xpartID);
                        part.setQuantity(part.getQuantity() + qty);
                        part.setPriceperunit((Double) self.getPriceperunit());
                        part.setPriceperunit(self.getPriceperunit());
                        part.setPurchased(self.getPurchaseDate());
                        if (self.getXsupplierId().intValue() != part.getLastsupplierId().intValue()) {
                            part.setPrevsupplierId(part.getLastsupplierId());
                            part.setLastsupplierId(self.getXsupplierId());
                        }
                        part.save();
                    } catch (ForeignKeyViolationException ex) {
                    }
                }
            }

            @Override
            public void afterDelete(DbObject dbObject) throws SQLException {
            }
        };
        setTriggers(t);
    }
}
