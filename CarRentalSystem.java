package DemoTrail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CarRentalSystem extends JFrame {
    protected Connection connection;//For All
    private final String ADMIN = "Admin";
    private final String MANAGER = "Manager";
    private final String EMPLOYEE = "Employee";
    private final String APASSWORD = "87654321";
    private final String MPASSWORD = "123";
    private final String EPASSWORD = "12345678";
    protected JPanel panel, p1, p2, p3, inputCustPanel, inputResPanel, inputPayPanel, inputInsPanel, inputCollPanel, inputHistoryPanel;
    ImageIcon icon = new ImageIcon("DemoTrail/logo.png");
    JLabel l1, l2;
    JTextField usernameField, customerIdField, fNameField, lNameField, phoneField, emailField, regionField, zoneField, houseNoField, licenseField,
            reservationIdField, resCustomerIdField, resCarIdField, startDateField, endDateField,
            resPaymentIdField, resInsuranceIdField, resCollateralIdField, paymentIdField, insuranceIdField, insCarIdField, providerField, expireDateField, policyNoField,
            collateralIdField, collReservationIdField, valueField, descriptionField, resEmpIdField, pickUpLocation, dropOffLocation,
            collCarIdField, collCustomerIdField, collamountField, collTypeField, reciveDateField, noCarField, totalPriceField, carBranchIdField, bRegionField, bZoneField, bWoredaField, historyIdField,
            carHisIdField , recordTypeField;
    JComboBox<String> availableCombo, methodCombo, empRole, typeCombo, payTypeCombo;
    JComboBox <Integer> statusCombo;
    JPasswordField passwordField;
    String password;
    JButton addBtnCust, addBtnCar, addBtnRes, addBtnPay, addBtnColl, addBtnHistory;
    DefaultTableModel modelCust, modelCar, modelRes, modelPay, modelEmp, modelIns, modelColl, modelBranch, modelCarHistory;

    public void setIcon() {
        setIconImage(icon.getImage());
    }

    public String getAdminUsername() {
        return ADMIN;
    }

    public String getManagerUsername() {
        return MANAGER;
    }

    public String getEmployeeUsername() {
        return EMPLOYEE;
    }

    public String getAdminPassword() {
        return APASSWORD;
    }

    public String getManagerPassword() {
        return MPASSWORD;
    }

    public String getEmployeePassword() {
        return EPASSWORD;
    }

    // SQL Server connection parameters
    protected static final String CONNECTION_STRING =
            "jdbc:sqlserver://LAPTOP-3CP3R4H1:1433;databaseName=rental;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    public CarRentalSystem(String message) {
        System.out.println(message);

        ImageIcon icon = new ImageIcon("logo.jpg");
        setIconImage(icon.getImage());
    }

    protected void initializeDatabase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Successfully connected to SQL Server");
        } catch (ClassNotFoundException e) {
            System.out.println("initalilyzing Faild");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Sql Faild");
            throw new RuntimeException(e);
        }
    }


    String getExampleId(String category) {
        switch (category) {
            case "Customer": return "cid01";
            case "Car": return "caid01";
            case "Reservation": return "rid01";
            case "Payment": return "pid01";
            case "Employee": return "emp01";
            case "Insurance": return "iid01";
            case "Collateral": return "col01";
            default: return "UNKNOWN";
        }
    }

    boolean validateId(Connection dbConnection, String category, String id) {
        String query = "SELECT " + getIdColumn(category) + " FROM " + category + " WHERE " + getIdColumn(category) + " = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    String getIdColumn(String category) {
        switch (category) {
            case "Customer": return "Customer_Id";
            case "Car": return "Car_Id";
            case "Reservation": return "Reservation_Id";
            case "Payment": return "Payment_Id";
            case "Employee": return "Emp_id";
            case "Insurance": return "Insurance_Id";
            case "Collateral": return "Collateral_Id";
            default: return null;
        }
    }



    private boolean isIdValid(Connection conn, String tableName, String idColumn, String id) {
        String validateSql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + idColumn + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(validateSql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
        return false;
    }




    private String executeQuery(Connection conn, String query, String id) {
        StringBuilder result = new StringBuilder();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = rs.getString(i);
                    result.append(columnName).append(": ").append(value).append("\n");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
        return result.toString();
    }


    public void loadTableData(String query, DefaultTableModel model) {
        model.setRowCount(0);
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[model.getColumnCount()];
                for (int i = 0; i < row.length; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
        } catch (SQLException ex) {
            showError("Data Load Error", ex);
        }
    }

    void clearFields(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).setText("");
            }
        }
    }

    void showError(String title, SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(),
                title, JOptionPane.ERROR_MESSAGE);
    }


}

