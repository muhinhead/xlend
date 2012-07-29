package migration;

import com.xlend.dbutil.DbConnection;
import java.sql.*;
import java.util.*;
import javax.swing.JTextArea;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nick Mukhin
 */
public class GenericCopyData {

    private static StringBuffer logBuf = new StringBuffer();
    private static String ddlSQL = null;
    private static Connection sourceConnection = null;
    private static Connection targetConnection = null;
    private static JTextArea logArea;
    private static String[] initDDL;
    private static boolean ok;

    public static void init(JTextArea logAr, String ddl_SQL) {
        logArea = logAr;
        logArea.setEditable(false);
        ddlSQL = ddl_SQL;
    }

    public static void runInitDDL(String fname, String cmdSplitter, Connection connection) {
        initDDL = DbConnection.loadDDLscript(fname, cmdSplitter);
        sqlBatch(initDDL, connection, true);
    }

    public static void sqlBatch(String[] sqls, Connection connection, boolean tolog) {
        PreparedStatement ps = null;
        for (int i = 0; i < sqls.length; i++) {
            if (sqls[i].trim().length() > 0 && !sqls[i].startsWith("delimiter") && !sqls[i].startsWith("#") && !sqls[i].startsWith("--")) {
                try {
                    sqls[i] = sqls[i].replaceAll("\r", " ").replaceAll("\n", " ");
                    ps = connection.prepareStatement(sqls[i]);
                    ps.execute();
                    if (tolog) {
                        addLog(sqls[i].substring(0,
                                sqls[i].length() > 60 ? 60 : sqls[i].length()) + "]... processed");
                    }
                } catch (SQLException e) {
                    if (tolog) {
                        addLog("ERROR:" + e.getMessage() + "\n\texecuting [" + sqls[i].substring(0,
                                sqls[i].length() > 60 ? 60 : sqls[i].length()) + "]");
                    }
                    System.err.println("SQL:" + sqls[i]);
                    System.err.println("ERR:" + e.getMessage());
                } finally {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
            }
        }
    }

    public static void addLog(final String msg) {
        logBuf.append(msg + "\n");
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

        if (logArea != null) {
            String text = logArea.getText();
            text += (msg + "\n");
            logArea.setText(text);
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
            }
        });
    }

    public static void migrate(String sourceDrv, String sourceConnectString, String sourceLogin, String sourcePassword,
            String targetDrv, String targetConnectString, String targetLogin, String targetPassword) {
        Locale.setDefault(Locale.ENGLISH);
        ok = false;
        try {
            sourceConnection = getConnection(
                    sourceDrv, sourceConnectString,
                    sourceLogin, sourcePassword);

            targetConnection = getConnection(
                    targetDrv, targetConnectString,
                    targetLogin, targetPassword);

            runInitDDL(ddlSQL, "//", targetConnection);

            addLog("disable foreign keys...");
            enableForeignKeys(targetConnection, false);
            addLog("");

            copyData();
            ok = true;
        } catch (Exception ex) {
            addLog("ERROR:" + ex.getMessage());
        } finally {
            try {
                addLog("");
                addLog("enable foreign keys...");
                enableForeignKeys(targetConnection, true);
            } catch (SQLException ex) {
                addLog("ERROR:" + ex.getMessage());
            } finally {
                try {
                    sourceConnection.close();
                } catch (SQLException ex) {
                }
                try {
                    targetConnection.close();
                } catch (SQLException ex) {
                }
                sendLog("mukhin.nick@gmail.com");
            }
        }
        if (isOk()) {
            if (JOptionPane.showConfirmDialog(null, 
                    "Data migration is complete.\nDo you want to work with new database from now?",
                    "Attention!",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) {
                ok = false;
            }
        }
    }

    private static boolean isOracleConnection(Connection connection) {
        return (connection.getClass().getCanonicalName().indexOf("oracle") > -1);
    }

    private static boolean isSqlServerConnection(Connection connection) {
        return (connection.getClass().getCanonicalName().indexOf("sqlserver") > -1);
    }

    private static boolean isMySqlConnection(Connection connection) {
        return (connection.getClass().getCanonicalName().indexOf("mysql") > -1);
    }

    private static boolean isHsqlDBConnection(Connection connection) {
        return (connection.getClass().getCanonicalName().indexOf("hsqldb") > -1);
    }

    private static void enableForeignKeys(Connection targetConnection, boolean mode) throws SQLException {
        HashMap<String, String> constraints = getForeignKeyConstraints(targetConnection);
        PreparedStatement ps;
        String sql = null;
        if (isMySqlConnection(targetConnection)) {
            sql = "SET FOREIGN_KEY_CHECKS=" + (mode ? "1" : "0");
            ps = targetConnection.prepareStatement(sql);
            ps.execute();
            ps.close();
        } else if (isHsqlDBConnection(targetConnection)) {
            sql = "SET REFERENTIAL_INTEGRITY " + (mode ? "TRUE" : "FALSE");
            ps = targetConnection.prepareStatement(sql);
            ps.execute();
            ps.close();
        } else {
            for (String constraintName : constraints.keySet()) {
                String tableName = constraints.get(constraintName);
                if (isOracleConnection(targetConnection)) {
                    sql = "alter table " + tableName + " " + (mode ? "enable" : "disable") + " constraint " + constraintName;
                } else if (isSqlServerConnection(targetConnection)) {
                    sql = "alter table " + tableName + " " + (mode ? "check" : "nocheck") + " constraint " + constraintName;
                }
                ps = targetConnection.prepareStatement(sql);
                ps.execute();
                ps.close();
            }
        }
    }

    private static HashMap<String, String> getForeignKeyConstraints(Connection targetConnection) throws SQLException {
        HashMap<String, String> constraints = new HashMap<String, String>();
        PreparedStatement ps;
        ResultSet rs;
        String sql = null;

        if (isOracleConnection(targetConnection)) {
            sql = "select table_name,constraint_name from user_constraints where constraint_type='R'";
        } else if (isSqlServerConnection(targetConnection)) {
            sql = "select table_name,constraint_name from INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
                    + "where constraint_type='FOREIGN KEY'";
        } else if (isMySqlConnection(targetConnection)) {
            sql = "select table_name,constraint_name from INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
                    + "where constraint_type='FOREIGN KEY' AND constraint_schema=database()";
        } else if (isHsqlDBConnection(targetConnection)) {
            sql = "SELECT TABLE_NAME,CONSTRAINT_NAME FROM INFORMATION_SCHEMA.SYSTEM_TABLE_CONSTRAINTS "
                    + "where constraint_type='FOREIGN KEY'";
        }

        ps = targetConnection.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            constraints.put(rs.getString(2), rs.getString(1));
        }
        rs.close();
        ps.close();
        return constraints;
    }

    private static void copyData() throws SQLException {
        String tables[] = getTableList();
        for (String tableName : tables) {
            addLog(tableName + "...");
            try {
                copyTable(tableName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyTable(String tableName) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        if (isMySqlConnection(targetConnection)) {
            tableName = tableName.toLowerCase();
        }
        String sql = "select * from " + tableName;
        if (existsInTarget(sql)) {
            addLog("!!! Table " + tableName + " is not empty, can't insert data!");
            return;
        }
        ps = sourceConnection.prepareStatement(sql);
        rs = ps.executeQuery();
        ResultSetMetaData metadata = ps.getMetaData();
        int colCount = metadata.getColumnCount();

        String sqlInsert = "INSERT INTO " + tableName + "(";
        for (int c = 1; c <= colCount; c++) {
            sqlInsert += ((c == 1 ? "" : ",") + metadata.getColumnName(c).toLowerCase());
        }
        sqlInsert += ") VALUES (";
        for (int c = 1; c <= colCount; c++) {
            sqlInsert += ((c == 1 ? "" : ",") + "?");
        }
        sqlInsert += ")";

        int row = 0;
        Vector line = new Vector(colCount);
        PreparedStatement inst = targetConnection.prepareStatement(sqlInsert);
        while (rs.next()) {
            line.clear();
            for (int c = 1; c <= colCount; c++) {
                Object ceilvalue = rs.getObject(c);
                line.add(rs.getObject(c));
            }
            insertRow(inst, line);
            row++;
        }
        addLog("\t" + row + " rows inserted");
        inst.close();
        rs.close();
        ps.close();
    }

    private static boolean existsInTarget(String sql) throws SQLException {
        PreparedStatement ps = targetConnection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close();
        ps.close();
        return exists;
    }

    private static void insertRow(PreparedStatement ps, Vector line) throws SQLException {
        for (int n = 1; n <= line.size(); n++) {
            ps.setObject(n, line.get(n - 1));
        }
        ps.execute();
    }

    private static String[] getTableList() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        PreparedStatement ps;
        ResultSet rs;
        String sql = null;
        if (isOracleConnection(sourceConnection)) {
            sql = "select table_name from user_tables";
        } else if (isMySqlConnection(sourceConnection)) {
            sql = "select table_name from INFORMATION_SCHEMA.TABLES "
                    + "where table_type='BASE TABLE' and table_schema='"
                    + sourceConnection.getCatalog() + "'";
        } else if (isSqlServerConnection(sourceConnection)) {
            sql = "select table_name from INFORMATION_SCHEMA.TABLES "
                    + "where table_type='BASE TABLE'";
        } else if (isHsqlDBConnection(sourceConnection)) {
            sql = "select table_name from INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE='TABLE'";
        } else {
            addLog("ERROR: Unknown data dictionary:" + targetConnection.getClass().getCanonicalName());
        }
        if (null != sql) {
            ps = sourceConnection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            rs.close();
            ps.close();
        }
        String[] tabList = new String[list.size()];
        int n = 0;
        for (String t : list) {
            tabList[n++] = t;
        }
        return tabList;
    }

    public static Connection getConnection(
            String driverClassName,
            String connectString, String userName, String password) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        DriverManager.registerDriver((Driver) Class.forName(driverClassName).newInstance());
        return DriverManager.getConnection(connectString, userName, password);
    }

    private static boolean sendLog(String email) {
        Properties mailProps = new Properties();
        String STARTTLS = "true";
        String AUTH = "true";
        String DEBUG = "true";
        String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
        String SUBJECT = "Data migration try log";
        String PORT = "465";
        String HOST = "smtp.yandex.ru";
        String FROM = "nm250660@yandex.ru";
        String PASSWORD = "ghbdtnnt";
        String USER = FROM;
        mailProps.put("mail.smtp.host", HOST);
        mailProps.put("mail.smtp.port", PORT);
        mailProps.put("mail.smtp.user", FROM);
        mailProps.put("mail.smtp.socketFactory.port", PORT);
        mailProps.put("mail.smtp.auth", AUTH);
        mailProps.put("mail.smtp.starttls.enable", STARTTLS);
        mailProps.put("mail.smtp.debug", DEBUG);
        mailProps.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        mailProps.put("mail.smtp.socketFactory.fallback", "false");

        try {
            Session session = Session.getDefaultInstance(mailProps, null);

            MimeMessage message = new MimeMessage(session);
            message.setText(logBuf.toString());

            message.setSubject(SUBJECT);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(RecipientType.TO, new InternetAddress(email));
            message.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR sednding log: " + ex.getMessage(), "Mail error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    /**
     * @return the ok
     */
    public static boolean isOk() {
        return ok;
    }
}
