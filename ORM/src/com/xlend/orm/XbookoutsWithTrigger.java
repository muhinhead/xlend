package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.TriggerAdapter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
public class XbookoutsWithTrigger extends Xbookouts {

    private Double oldQty;

    public XbookoutsWithTrigger(Connection connection) {
        super(connection);
        addTriggers();
    }

    public XbookoutsWithTrigger(Xbookouts papa) {
        super(papa.getConnection(), papa.getXbookoutsId(), papa.getXpartsId(),
                papa.getIssueDate(), papa.getXsiteId(), papa.getXmachineId(),
                papa.getIssuedbyId(), papa.getIssuedtoId(), papa.getQuantity());
        setNew(papa.isNew());
        addTriggers();
    }

    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
        addTriggers();
    }

    @Override
    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        return new XbookoutsWithTrigger((Xbookouts)super.loadOnId(id));
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
                Xbookouts self = (Xbookouts) dbObject;
                double qty = self.getQuantity();
                if (qty > 0) {
                    try {
                        int xpartID = self.getXpartsId();
                        Xparts part = (Xparts) new Xparts(getConnection()).loadOnId(xpartID);
                        double newQty = part.getQuantity() - qty;
                        if (newQty < 0) {
                            newQty = 0;
                            self.setQuantity(part.getQuantity());
                            self.save();
                        }
                        part.setQuantity(newQty);
                        part.save();
                    } catch (ForeignKeyViolationException ex) {
                    }
                }
            }

            @Override
            public void afterUpdate(DbObject dbObject) throws SQLException {
                Xbookouts self = (Xbookouts) dbObject;
                if (oldQty != null && self.getQuantity() != null) {
                    double diff = self.getQuantity() - oldQty;
                    if (diff != 0) {
                        try {
                            int xpartID = self.getXpartsId();
                            Xparts part = (Xparts) new Xparts(getConnection()).loadOnId(xpartID);
                            part.setQuantity(part.getQuantity() - diff);
                            part.save();
                        } catch (ForeignKeyViolationException ex) {
                        }
                    }
                }
            }

            @Override
            public void afterDelete(DbObject dbObject) throws SQLException {
                Xbookouts self = (Xbookouts) dbObject;
                double qty = (self.getQuantity()==null?0:self.getQuantity().doubleValue());
                if (qty > 0) {
                    try {
                        int xpartID = self.getXpartsId();
                        Xparts part = (Xparts) new Xparts(getConnection()).loadOnId(xpartID);
                        part.setQuantity(part.getQuantity() + qty);
                        part.save();
                    } catch (ForeignKeyViolationException ex) {
                    }
                }
            }
        };
        setTriggers(t);
    }
}
