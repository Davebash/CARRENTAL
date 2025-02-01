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
    ImageIcon icon = new ImageIcon("logo.jpg");
    JLabel l1, l2;
    JTextField usernameField, customerIdField, fNameField, lNameField, phoneField, emailField, regionField, zoneField, houseNoField, licenseField,
            reservationIdField, resCustomerIdField, resCarIdField, startDateField, endDateField,
            resPaymentIdField, resInsuranceIdField, resCollateralIdField, paymentIdField, insuranceIdField, insCarIdField, providerField, expireDateField, policyNoField,
            collateralIdField, collReservationIdField, valueField, descriptionField, resEmpIdField, pickUpLocation, dropOffLocation,
            collCarIdField, collCustomerIdField, collamountField, collTypeField, reciveDateField, noCarField, totalPriceField, carBranchIdField, bRegionField, bZoneField, bWoredaField, historyIdField,
            recordTypeField;
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
    }

    protected void initializeDatabase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Successfully connected to SQL Server");
        } catch (ClassNotFoundException e) {
            System.out.println("Initalilyzing Failed");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Sql Failed");
            e.printStackTrace();
        }
    }

    public void loadTableData(String query, DefaultTableModel model) {
        model.setRowCount(0);// remove all existing rows from the table
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
                ((JTextField) c).setText("");//c is still treated as a Component at compile-time, that is why it's cast
            }
        }
    }

    void showError(String title, SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(),
                title, JOptionPane.ERROR_MESSAGE);
    }


}

