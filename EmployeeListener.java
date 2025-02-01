package DemoTrail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class EmployeeListener implements ActionListener {


    EmployeeClass emp;

    public EmployeeListener(EmployeeClass emp) {
        this.emp = emp;
        System.out.println("Listner is being assigned");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == emp.updateCusBtn) {
            updateTable(emp.cusTable, emp.modelCust); // Update customer table
        } else if (e.getSource() == emp.updateCarBtn) {
            updateTable(emp.caTable, emp.modelCar); // Update car table
        } else if (e.getSource() == emp.updateResBtn) {
            updateTable(emp.resTable, emp.modelRes); // Update reservation table
        } else if (e.getSource() == emp.updateColBtn) {
            updateTable(emp.colTable, emp.modelColl); // Update collateral table
        } else if (e.getSource() == emp.updateBraBtn) {
            updateTable(emp.braTable, emp.modelBranch); // Update branch table
        } else if (e.getSource() == emp.addBtnCust) {
            insertCustomer(); // Call the method for inserting a customer
        } else if (e.getSource() == emp.addBtnRes) {
            insertReservation(); // Call the method for inserting a reservation
        } else if (e.getSource() == emp.addBtnPay) {
            insertPayment(); // Call the method for inserting a payment
        } else if (e.getSource() == emp.addBtnColl) {
            insertCollateral(); // Call the method for inserting collateral
        } else if (e.getSource() == emp.addBtnBranch) {
            insertRentalBranch(); // Call the method for inserting branch
        }


    }


    // Customer Insertion
    private void insertCustomer() {
        System.out.println("I am in the insert Staatment");
        try (PreparedStatement pstmt = emp.connection.prepareStatement(
                "INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, emp.customerIdField.getText());
            pstmt.setString(2, emp.fNameField.getText());
            pstmt.setString(3, emp.lNameField.getText());
            pstmt.setString(4, emp.phoneField.getText());
            pstmt.setString(5, emp.emailField.getText());
            pstmt.setString(6, emp.regionField.getText());
            pstmt.setString(7, emp.zoneField.getText());
            pstmt.setString(8, emp.houseNoField.getText());
            pstmt.setString(9, emp.licenseField.getText());
            pstmt.executeUpdate();
            emp.loadTableData("SELECT * FROM Customer", emp.modelCust);
            emp.clearFields(emp.inputCustPanel);

        } catch (SQLException ex) {
            emp.showError("Customer Add Error", ex);
            return;
        }
        JOptionPane.showMessageDialog(null, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Reservation Insertion
    private void insertReservation() {
        try (PreparedStatement pstmt = emp.connection.prepareStatement(
                "INSERT INTO Reservation VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, emp.reservationIdField.getText());
            pstmt.setString(2, emp.resCarIdField.getText());
            pstmt.setString(3, emp.resCustomerIdField.getText());
            pstmt.setString(4, emp.resEmpIdField.getText());
            pstmt.setString(5, emp.statusCombo.getSelectedItem().toString());
            pstmt.setString(6, null);
            pstmt.setString(7, emp.startDateField.getText());
            pstmt.setString(8, emp.endDateField.getText());
            pstmt.setString(9, emp.pickUpLocation.getText());
            pstmt.setString(10, emp.dropOffLocation.getText());
            pstmt.setString(11, null);
            pstmt.setString(12, emp.resInsuranceIdField.getText());

            pstmt.executeUpdate();
            emp.loadTableData("SELECT * FROM Reservation", emp.modelRes);
            emp.clearFields(emp.inputResPanel);
            JOptionPane.showMessageDialog(null, "Reservation added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            emp.showError("Reservation Add Error", ex);
        }
    }

    // Payment Insertion
    private void insertPayment() {
        try (PreparedStatement pstmt = emp.connection.prepareStatement(
                "INSERT INTO Payment VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, emp.paymentIdField.getText());
            pstmt.setString(2, emp.payReservationIdField.getText()); // Link to Reservation
            pstmt.setString(3, emp.amountField.getText());
            pstmt.setString(4, emp.noCarField.getText());
            pstmt.setString(5, emp.payTypeCombo.getSelectedItem().toString());
            pstmt.setString(6, emp.totalPriceField.getText());
            pstmt.setString(7, emp.methodCombo.getSelectedItem().toString());
            pstmt.setString(8, emp.paymentDateField.getText());
            pstmt.executeUpdate();

            // Call the stored procedure to update Reservation with Payment_Id
            try (CallableStatement stmt = emp.connection.prepareCall("{CALL Update_Payment}")) {
                stmt.execute();
            }

            // Refresh table and clear fields
            emp.loadTableData("SELECT * FROM Payment", emp.modelPay);
            emp.loadTableData("SELECT * FROM Reservation", emp.modelRes);
            emp.clearFields(emp.inputPayPanel);
            JOptionPane.showMessageDialog(null, "Payment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            emp.showError("Payment Add Error", ex);
        }
    }


    // Collateral Insertion
    private void insertCollateral() {
        try (PreparedStatement pstmt = emp.connection.prepareStatement(
                "INSERT INTO Collateral VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, emp.collateralIdField.getText());
            pstmt.setString(2, emp.collCustomerIdField.getText());
            pstmt.setString(3, emp.collCarIdField.getText());
            pstmt.setString(4, emp.collReservationIdField.getText());
            pstmt.setString(5, emp.collTypeField.getText());
            pstmt.setString(6, emp.collamountField.getText());
            pstmt.setString(7, emp.reciveDateField.getText());
            pstmt.executeUpdate();

            // Call the stored procedure to update Reservation with Collateral_Id
            try (CallableStatement stmt = emp.connection.prepareCall("{CALL Update_Collateral}")) {
                stmt.execute();
            }

            // Refresh table and clear fields
            emp.loadTableData("SELECT * FROM Collateral", emp.modelColl);
            emp.loadTableData("SELECT * FROM Reservation", emp.modelRes);
            emp.clearFields(emp.inputCollPanel);
            JOptionPane.showMessageDialog(null, "Collateral added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            emp.showError("Collateral Add Error", ex);
        }
    }

    private void insertRentalBranch() {
        try (PreparedStatement pstmt = emp.connection.prepareStatement(
                "INSERT INTO Rental_Branch VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, emp.branchIdField.getText());
            pstmt.setString(2, emp.bRegionField.getText());
            pstmt.setString(3, emp.bZoneField.getText());
            pstmt.setInt(4, Integer.parseInt(emp.bWoredaField.getText()));
            pstmt.executeUpdate();
            emp.loadTableData("SELECT * FROM Rental_Branch", emp.modelBranch);
            emp.clearFields(emp.inputBranchPanel);
            JOptionPane.showMessageDialog(null, "Rental_Branch added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            emp.showError("Rental_Branch Add Error", ex);
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
                case "Car":
                    updateCar(primaryKey, updateModel);
                    break;
                case "Reservation":
                    updateReservation(primaryKey, updateModel);
                    break;
                case "Collateral":
                    updateCollateral(primaryKey, updateModel);
                    break;
                case "Rental Branch":
                    updateRentalBranch(primaryKey, updateModel);
                    break;
                default:
                    System.out.println("Unknown table: " + tableName);
            }
            JOptionPane.showMessageDialog(null, "Update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            emp.showError("Update Failed", ex);
        }
    }

    //Updating the Customer Table
    private void updateCustomer(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Customer SET Customer_Id=?, C_FName=?, C_LName=?, C_Phone_No=?, C_Email=?, C_Region=?, C_Zone=?, C_House_No=?, C_Drivers_LN=? WHERE Customer_Id=?";
        try (PreparedStatement pstmt = emp.connection.prepareStatement(query)) {
            pstmt.setString(1, emp.customerIdField.getText());
            pstmt.setString(2, emp.fNameField.getText());
            pstmt.setString(3, emp.lNameField.getText());
            pstmt.setString(4, emp.phoneField.getText());
            pstmt.setString(5, emp.emailField.getText());
            pstmt.setString(6, emp.regionField.getText());
            pstmt.setString(7, emp.zoneField.getText());
            pstmt.setString(8, emp.houseNoField.getText());
            pstmt.setString(9, emp.licenseField.getText());
            pstmt.setString(10, originalId);
            pstmt.executeUpdate();
            emp.loadTableData("SELECT * FROM Customer", model);
        } catch (SQLException ex) {
            // Show SQL error
            emp.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    emp,
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
        try (PreparedStatement pstmt = emp.connection.prepareStatement(query)) {
            pstmt.setString(1, emp.carIdField.getText());
            pstmt.setString(2, emp.branchIdField.getText());
            pstmt.setString(3, emp.modelField.getText());
            pstmt.setString(4, emp.plateField.getText());
            pstmt.setString(5, emp.availableCombo.getSelectedItem().toString());
            pstmt.setString(6, originalId);
            pstmt.executeUpdate();
            emp.loadTableData("SELECT * FROM Car", model);
        } catch (SQLException ex) {
            // Show SQL error
            emp.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    emp,
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
        try (PreparedStatement pstmt = emp.connection.prepareStatement(query)) {
            pstmt.setString(1, emp.reservationIdField.getText());
            pstmt.setString(2, emp.resCarIdField.getText());
            pstmt.setString(3, emp.resCustomerIdField.getText());
            pstmt.setString(4, emp.resEmpIdField.getText());
            pstmt.setString(5, emp.statusCombo.getSelectedItem().toString());
            pstmt.setString(6, emp.resCollateralIdField.getText());
            pstmt.setString(7, emp.startDateField.getText());
            pstmt.setString(8, emp.endDateField.getText());
            pstmt.setString(9, emp.pickUpLocation.getText());
            pstmt.setString(10, emp.dropOffLocation.getText());
            pstmt.setString(11, emp.resPaymentIdField.getText());
            pstmt.setString(12, emp.resInsuranceIdField.getText());
            pstmt.setString(13, originalId);
            pstmt.executeUpdate();
            emp.loadTableData("SELECT * FROM Reservation", model);
        } catch (SQLException ex) {
            // Show SQL error
            emp.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    emp,
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
        try (PreparedStatement pstmt = emp.connection.prepareStatement(query)) {
            pstmt.setString(1, emp.collateralIdField.getText());
            pstmt.setString(2, emp.collCustomerIdField.getText());
            pstmt.setString(3, emp.collCarIdField.getText());
            pstmt.setString(4, emp.collReservationIdField.getText());
            pstmt.setString(5, emp.collTypeField.getText());
            pstmt.setString(6, emp.collamountField.getText());
            pstmt.setString(7, emp.reciveDateField.getText());
            pstmt.setString(8, originalId);
            pstmt.executeUpdate();
            emp.loadTableData("SELECT * FROM Collateral", model);
        } catch (SQLException ex) {
            // Show SQL error
            emp.showError("Update Failed", ex);
        } catch (Exception ex) { // Catch all other exceptions
            JOptionPane.showMessageDialog(
                    emp,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace(); // Log for debugging
        }
    }

    private void updateRentalBranch(String originalId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Rental_Branch SET Branch_id=?, B_Region=?, B_Zone=?, B_Woreda=? WHERE Branch_id=?";
        try (PreparedStatement pstmt = emp.connection.prepareStatement(query)) {
            // Set parameters from input fields
            pstmt.setString(1, emp.branchIdField.getText());
            pstmt.setString(2, emp.bRegionField.getText());
            pstmt.setString(3, emp.bZoneField.getText());
            pstmt.setInt(4, Integer.parseInt(emp.bWoredaField.getText())); // Handle number conversion
            pstmt.setString(5, originalId);

            pstmt.executeUpdate();
            emp.loadTableData("SELECT * FROM Rental_Branch", model);
        } catch (SQLException ex) {
            emp.showError("Branch Update Failed", ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(emp,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}
