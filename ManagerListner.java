package DemoTrail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManagerListner implements ActionListener {

    ManagerClass admin;

    public ManagerListner(ManagerClass admin) {
        this.admin = admin;
        System.out.println("Listner is being assigned");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == admin.addBtnCust) {
            insertCustomer(); // Call the method for inserting a customer
        } else if (e.getSource() == admin.addBtnCar) {
            insertCar(); // Call the method for inserting a car
        } else if (e.getSource() == admin.addBtnRes) {
            insertReservation(); // Call the method for inserting a reservation
        } else if (e.getSource() == admin.addBtnPay) {
            insertPayment(); // Call the method for inserting a payment
        } else if (e.getSource() == admin.addBtnColl) {
            insertCollateral(); // Call the method for inserting collateral
        } else if (e.getSource() == admin.addBtnHistory) {
            insertCarHis();
        } else if (e.getSource() == admin.updateCusBtn) {
            updateTable(admin.cusTable, admin.modelCust); // Update customer table
        }


    }


    // Customer Insertion
    private void insertCustomer() {
        System.out.println("I am in the insert Staatment");
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, admin.customerIdField.getText());
            pstmt.setString(2, admin.fNameField.getText());
            pstmt.setString(3, admin.lNameField.getText());
            pstmt.setString(4, admin.phoneField.getText());
            pstmt.setString(5, admin.emailField.getText());
            pstmt.setString(6, admin.regionField.getText());
            pstmt.setString(7, admin.zoneField.getText());
            pstmt.setString(8, admin.houseNoField.getText());
            pstmt.setString(9, admin.licenseField.getText());
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Customer", admin.modelCust);
            admin.clearFields(admin.inputCustPanel);

        } catch (SQLException ex) {
            admin.showError("Customer Add Error", ex);
            return;
        }
        JOptionPane.showMessageDialog(null, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }


    // Car Insertion
    private void insertCar() {
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Car VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, admin.carIdField.getText());
            pstmt.setString(2, admin.branchIdField.getText());
            pstmt.setString(3, admin.modelField.getText());
            pstmt.setString(4, admin.plateField.getText());
            pstmt.setString(5, admin.availableCombo.getSelectedItem().toString());
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Car", admin.modelCar);
            admin.clearFields(admin.inputCarPanel);
            JOptionPane.showMessageDialog(null, "Car added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            admin.showError("Car Add Error", ex);
        }
    }


    // Reservation Insertion
    private void insertReservation() {
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Reservation VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, admin.reservationIdField.getText());
            pstmt.setString(2, admin.resCarIdField.getText());
            pstmt.setString(3, admin.resCustomerIdField.getText());
            pstmt.setString(4, admin.resEmpIdField.getText());
            pstmt.setString(5, admin.statusCombo.getSelectedItem().toString());
            pstmt.setString(6, null);
            pstmt.setString(7, admin.startDateField.getText());
            pstmt.setString(8, admin.endDateField.getText());
            pstmt.setString(9, admin.pickUpLocation.getText());
            pstmt.setString(10, admin.dropOffLocation.getText());
            pstmt.setString(11, null);
            pstmt.setString(12, admin.resInsuranceIdField.getText());

            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Reservation", admin.modelRes);
            admin.clearFields(admin.inputResPanel);
            JOptionPane.showMessageDialog(null, "Reservation added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            admin.showError("Reservation Add Error", ex);
        }
    }


    // Payment Insertion
    private void insertPayment() {
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Payment VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, admin.paymentIdField.getText());
            pstmt.setString(2, admin.payReservationIdField.getText()); // Link to Reservation
            pstmt.setString(3, admin.amountField.getText());
            pstmt.setString(4, admin.noCarField.getText());
            pstmt.setString(5, admin.payTypeCombo.getSelectedItem().toString());
            pstmt.setString(6, admin.totalPriceField.getText());
            pstmt.setString(7, admin.methodCombo.getSelectedItem().toString());
            pstmt.setString(8, admin.paymentDateField.getText());
            pstmt.executeUpdate();

            // Call the stored procedure to update Reservation with Payment_Id
            try (CallableStatement stmt = admin.connection.prepareCall("{CALL Update_Payment}")) {
                stmt.execute();
            }

            // Refresh table and clear fields
            admin.loadTableData("SELECT * FROM Payment", admin.modelPay);
            admin.loadTableData("SELECT * FROM Reservation", admin.modelRes);
            admin.clearFields(admin.inputPayPanel);
            JOptionPane.showMessageDialog(null, "Payment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            admin.showError("Payment Add Error", ex);
        }
    }


    // Collateral Insertion
    private void insertCollateral() {
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Collateral VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, admin.collateralIdField.getText());
            pstmt.setString(2, admin.collCustomerIdField.getText());
            pstmt.setString(3, admin.collCarIdField.getText());
            pstmt.setString(4, admin.collReservationIdField.getText());
            pstmt.setString(5, admin.collTypeField.getText());
            pstmt.setString(6, admin.collamountField.getText());
            pstmt.setString(7, admin.reciveDateField.getText());
            pstmt.executeUpdate();

            // Call the stored procedure to update Reservation with Collateral_Id
            try (CallableStatement stmt = admin.connection.prepareCall("{CALL Update_Collateral}")) {
                stmt.execute();
            }

            // Refresh table and clear fields
            admin.loadTableData("SELECT * FROM Collateral", admin.modelColl);
            admin.loadTableData("SELECT * FROM Reservation", admin.modelRes);
            admin.clearFields(admin.inputCollPanel);
            JOptionPane.showMessageDialog(null, "Collateral added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            admin.showError("Collateral Add Error", ex);
        }
    }
    
    private void insertCarHis() {
        System.out.println("I am in the insertCarHis Statement");
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Car_History (History_Id, Car_Id, Description, Record_Type) VALUES (?, ?, ?, ?)")) {

            // Set parameters from the input fields
            pstmt.setString(1, admin.historyIdField.getText()); // History ID
            pstmt.setString(2, admin.carHisIdField.getText()); // Car ID
            pstmt.setString(3, admin.descriptionField.getText()); // Description
            pstmt.setString(4, admin.recordTypeField.getText()); // Record Type

            // Execute the insert statement
            pstmt.executeUpdate();

            // Reload the table data to reflect the new entry
            admin.loadTableData("SELECT * FROM Car_History", admin.modelCarHistory);

            // Clear the input fields after insertion
            admin.clearFields(admin.inputHistoryPanel);

            // Show success message
            JOptionPane.showMessageDialog(null, "Car History added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            // Handle SQL errors
            admin.showError("Car History Add Error", ex);
        }
    }

    private void updateTable(JTable updateTable, DefaultTableModel updateModel) {
        int row = updateTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a record to update!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tableName = updateTable.getName();
        String primaryKey = updateModel.getValueAt(row, 0).toString();

        try {
            switch (tableName) {
                case "Customer":
                    updateCustomer(primaryKey, updateModel);
                    break;
                default:
                    System.out.println("Unknown table: " + tableName);
            }
            JOptionPane.showMessageDialog(null, "Update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            admin.showError("Update Failed", ex);
        }
    }

    //Updating the Customer Table
    private void updateCustomer(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Customer SET Customer_Id=?, C_FName=?, C_LName=?, C_Phone_No=?, C_Email=?, C_Region=?, C_Zone=?, C_House_No=?, C_Drivers_LN=? WHERE Customer_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.customerIdField.getText());
            pstmt.setString(2, admin.fNameField.getText());
            pstmt.setString(3, admin.lNameField.getText());
            pstmt.setString(4, admin.phoneField.getText());
            pstmt.setString(5, admin.emailField.getText());
            pstmt.setString(6, admin.regionField.getText());
            pstmt.setString(7, admin.zoneField.getText());
            pstmt.setString(8, admin.houseNoField.getText());
            pstmt.setString(9, admin.licenseField.getText());
            pstmt.setString(10, originalId);
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Customer", model);
        } catch (SQLException ex) {
            // Show SQL error
            admin.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    admin,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace(); // Log for debugging
        }
    }

    // Car Update
    private void updateCar(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Car SET Car_Id=?, Branch_Id=?, Model=?, Plate_No=?, IsAvailable=? WHERE Car_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.carIdField.getText());
            pstmt.setString(2, admin.branchIdField.getText());
            pstmt.setString(3, admin.modelField.getText());
            pstmt.setString(4, admin.plateField.getText());
            pstmt.setString(5, admin.availableCombo.getSelectedItem().toString());
            pstmt.setString(6, originalId);
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Car", model);
        } catch (SQLException ex) {
            // Show SQL error
            admin.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    admin,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace(); // Log for debugging
        }
    }

    // Reservation Update
    private void updateReservation(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Reservation SET Reservation_Id=?, Car_Id=?, Customer_Id=?, Emp_Id=?, Reservation_Status=?, Collateral_Id=?, PickUp_Date=?, Dropoff_Date=?, Pickup_Location=?, Dropoff_Location=?, Payment_Id=?, Insurance_Id=? WHERE Reservation_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.reservationIdField.getText());
            pstmt.setString(2, admin.resCarIdField.getText());
            pstmt.setString(3, admin.resCustomerIdField.getText());
            pstmt.setString(4, admin.resEmpIdField.getText());
            pstmt.setString(5, admin.statusCombo.getSelectedItem().toString());
            pstmt.setString(6, admin.resCollateralIdField.getText());
            pstmt.setString(7, admin.startDateField.getText());
            pstmt.setString(8, admin.endDateField.getText());
            pstmt.setString(9, admin.pickUpLocation.getText());
            pstmt.setString(10, admin.dropOffLocation.getText());
            pstmt.setString(11, admin.resPaymentIdField.getText());
            pstmt.setString(12, admin.resInsuranceIdField.getText());
            pstmt.setString(13, originalId);
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Reservation", model);
        } catch (SQLException ex) {
            // Show SQL error
            admin.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    admin,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace(); // Log for debugging
        }
    }

    // Payment Update
    private void updatePayment(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Payment SET Payment_Id=?, Reservation_Id=?, Price_Per_Car=?, No_Cars=?, Payment_Type=?, Total_Price=?, Payment_Method=?, Payment_Date=? WHERE Payment_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.paymentIdField.getText());
            pstmt.setString(2, admin.payReservationIdField.getText());
            pstmt.setString(3, admin.amountField.getText());
            pstmt.setString(4, admin.noCarField.getText());
            pstmt.setString(5, admin.payTypeCombo.getSelectedItem().toString());
            pstmt.setString(6, admin.totalPriceField.getText());
            pstmt.setString(7, admin.methodCombo.getSelectedItem().toString());
            pstmt.setString(8, admin.paymentDateField.getText());
            pstmt.setString(9, originalId);
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Payment", model);
        } catch (SQLException ex) {
            // Show SQL error
            admin.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    admin,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace(); // Log for debugging
        }
    }

    // Insurance Update
    private void updateInsurance(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Insurance SET Insurance_Id=?, Car_Id=?, Insurance_Provider=?, Policy_Number=?, I_Expiry_Date=? WHERE Insurance_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.insuranceIdField.getText());
            pstmt.setString(2, admin.insCarIdField.getText());
            pstmt.setString(3, admin.providerField.getText());
            pstmt.setString(4, admin.policyNoField.getText());
            pstmt.setString(5, admin.expireDateField.getText());
            pstmt.setString(6, originalId);
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Insurance", model);
        } catch (SQLException ex) {
            // Show SQL error
            admin.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    admin,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace(); // Log for debugging
        }
    }

    // Collateral Update
    private void updateCollateral(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Collateral SET Collateral_Id=?, Customer_Id=?, Car_Id=?, Reservation_Id=?, Collateral_Type=?, Amount=?, Date_Received=? WHERE Collateral_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.collateralIdField.getText());
            pstmt.setString(2, admin.collCustomerIdField.getText());
            pstmt.setString(3, admin.collCarIdField.getText());
            pstmt.setString(4, admin.collReservationIdField.getText());
            pstmt.setString(5, admin.collTypeField.getText());
            pstmt.setString(6, admin.collamountField.getText());
            pstmt.setString(7, admin.reciveDateField.getText());
            pstmt.setString(8, originalId);
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Collateral", model);
        } catch (SQLException ex) {
            // Show SQL error
            admin.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    admin,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace(); // Log for debugging
        }
    }

    private void updateRentalBranch(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Rental_Branch SET Branch_id=?, B_Region=?, B_Zone=?, B_Woreda=? WHERE Branch_id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            // Set parameters from input fields
            pstmt.setString(1, admin.branchIdField.getText());
            pstmt.setString(2, admin.bRegionField.getText());
            pstmt.setString(3, admin.bZoneField.getText());
            pstmt.setInt(4, Integer.parseInt(admin.bWoredaField.getText())); // Handle number conversion
            pstmt.setString(5, originalId);

            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Rental_Branch", model);
        } catch (SQLException ex) {
            admin.showError("Branch Update Failed", ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(admin,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updateCarHistory(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Car_History SET History_Id=?, Car_Id=?, Description=?, Record_Type=? WHERE History_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            // Set parameters from input fields
            pstmt.setString(1, admin.historyIdField.getText());
            pstmt.setString(2, admin.carBranchIdField.getText());
            pstmt.setString(3, admin.descriptionField.getText());
            pstmt.setString(4, admin.recordTypeField.getText());
            pstmt.setString(5, originalId);

            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Car_History", model);
        } catch (SQLException ex) {
            admin.showError("History Update Failed", ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(admin,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteCustomer(String customerId) {
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "DELETE FROM Customer WHERE Customer_Id = ?")) {

            pstmt.setString(1, customerId); // Use the provided customerId
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                admin.loadTableData("SELECT * FROM Customer", admin.modelCust);
                JOptionPane.showMessageDialog(null, "Customer deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No customer found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            admin.showError("Customer Delete Error", ex);
        }
    }

    private void deleteRecord(String tableName, String primaryKeyColumn, String primaryKeyValue) {
        String query = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
        DefaultTableModel mod;
//        admin.tableName.getModel();
//        admin.cusTable.getModel()
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, primaryKeyValue); // Set the primary key value
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                // Reload the table data to reflect the deletion
                admin.loadTableData("SELECT * FROM " + tableName, getTableModel(tableName));
                JOptionPane.showMessageDialog(null, "Record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No record found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Delete Error", ex);
        }
    }

    private DefaultTableModel getTableModel(String tableName) {
        switch (tableName) {
            case "Customer":
                return (DefaultTableModel) admin.cusTable.getModel();
            case "Car":
                return (DefaultTableModel) admin.caTable.getModel();
            case "Reservation":
                return (DefaultTableModel) admin.resTable.getModel();
            case "Payment":
                return (DefaultTableModel) admin.payTable.getModel();
            case "Employee":
                return (DefaultTableModel) admin.empTable.getModel();
            case "Insurance":
                return (DefaultTableModel) admin.insTable.getModel();
            case "Collateral":
                return (DefaultTableModel) admin.colTable.getModel();
            case "Rental_Branch":
                return (DefaultTableModel) admin.braTable.getModel();
            case "Car_History":
                return (DefaultTableModel) admin.hisTable.getModel();
            default:
                System.out.println("Unknown table: " + tableName);
                return null;
        }
    }

}
