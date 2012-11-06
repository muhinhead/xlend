package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.TriggerAdapter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Nick Mukhin
 */
public class XaddstocksWithTrigger extends Xaddstocks {

    private Double oldQty;

    public XaddstocksWithTrigger(Connection connection, Integer xaddstocksId, Integer xpartsId, Date purchaseDate, Integer enteredbyId, Integer xsupplierId, Double priceperunit, Double quantity) {
        super(connection, xaddstocksId, xpartsId, purchaseDate, enteredbyId, xsupplierId, priceperunit, quantity);
        addTriggers();
    }

    public XaddstocksWithTrigger(Connection connection) {
        super(connection);
        addTriggers();
    }

    public XaddstocksWithTrigger(Xaddstocks papa) {
        super(papa.getConnection(), papa.getXaddstocksId(), papa.getXpartsId(),
                papa.getPurchaseDate(), papa.getEnteredbyId(), papa.getXsupplierId(),
                papa.getPriceperunit(), papa.getQuantity());
        setNew(papa.isNew());
    }

    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
        addTriggers();
    }

    @Override
    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Xaddstocks papa = (Xaddstocks) super.loadOnId(id);
        return papa == null ? null : new XaddstocksWithTrigger(papa);
    }

    @Override
    public void setQuantity(Double quantity) throws SQLException, ForeignKeyViolationException {
        if (quantity != getQuantity()) {
            oldQty = getQuantity();
        }
        super.setQuantity(quantity);
    }

    private void addTriggers() {
        TriggerAdapter t = new TriggerAdapter() {
            @Override
            public void afterInsert(DbObject dbObject) throws SQLException {
                Xaddstocks self = (Xaddstocks) dbObject;
                double qty = self.getQuantity();
                if (qty > 0) {
                    try {
                        int xpartID = self.getXpartsId();
                        Xparts part = (Xparts) new Xparts(getConnection()).loadOnId(xpartID);
                        part.setQuantity(part.getQuantity() + qty);
                        part.setPriceperunit((Double) self.getPriceperunit());
                        part.setPriceperunit(self.getPriceperunit());
                        part.setPurchased(self.getPurchaseDate());
                        if (part.getLastsupplierId() != null && self.getXsupplierId().intValue() != part.getLastsupplierId().intValue()) {
                            part.setPrevsupplierId(part.getLastsupplierId());
                            part.setLastsupplierId(self.getXsupplierId());
                        }
                        part.save();
                    } catch (ForeignKeyViolationException ex) {
                    }
                }
            }

            @Override
            public void afterUpdate(DbObject dbObject) throws SQLException {
                Xaddstocks self = (Xaddstocks) dbObject;
                if (oldQty != null && self.getQuantity() != null) {
                    double diff = self.getQuantity() - oldQty;
                    if (diff != 0) {
                        try {
                            int xpartID = self.getXpartsId();
                            Xparts part = (Xparts) new Xparts(getConnection()).loadOnId(xpartID);
                            part.setQuantity(part.getQuantity() + diff);
                            part.save();
                        } catch (ForeignKeyViolationException ex) {
                        }
                    }
                }
            }

            @Override
            public void afterDelete(DbObject dbObject) throws SQLException {
                Xaddstocks self = (Xaddstocks) dbObject;
                double qty = (self.getQuantity() == null ? 0 : self.getQuantity().doubleValue());
                if (qty > 0) {
                    try {
                        int xpartID = self.getXpartsId();
                        Xparts part = (Xparts) new Xparts(getConnection()).loadOnId(xpartID);
                        part.setQuantity(part.getQuantity() - qty);
                        part.save();
                    } catch (ForeignKeyViolationException ex) {
                    }
                }
            }
        };
        setTriggers(t);
    }
}
