package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.TriggerAdapter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
public class XbookoutsWithTrigger extends Xbookouts {

    public XbookoutsWithTrigger(Connection connection) {
        super(connection);
        addTriggers();
    }

    public XbookoutsWithTrigger(Connection connection, Integer xbookoutsId, Integer xpartsId, Date issueDate, Integer xsiteId, Integer xmachineId, Integer issuedbyId, Integer issuedtoId, Integer quantity) {
        super(connection, xbookoutsId, xpartsId, issueDate, xsiteId, xmachineId, issuedbyId, issuedtoId, quantity);
        addTriggers();
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
                Xbookouts self = (Xbookouts) dbObject;
                int qty = self.getQuantity();
                if (qty > 0) {
                    try {
                        int xpartID = self.getXpartsId();
                        Xparts part = (Xparts) new Xparts(getConnection()).loadOnId(xpartID);
                        int newQty = part.getQuantity() - qty;
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
            public void afterDelete(DbObject dbObject) throws SQLException {
            }
        };
        setTriggers(t);
    }
}
