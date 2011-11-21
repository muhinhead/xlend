package com.xlend.orm;

import com.xlend.orm.dbobject.ForeignKeyViolationException;
import java.sql.SQLException;

/**
 *
 * @author Nick Mukhin
 */
public interface IPage {
    Object getPagescan();
    void setPagescan(Object pagescan) throws SQLException, ForeignKeyViolationException;
}
