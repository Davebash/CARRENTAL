package DemoTrail;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class AdminListner implements ActionListener {


    AdminInterface admin;

    public AdminListner(AdminInterface admin) {
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
        } else if (e.getSource() == admin.addBtnEmp) {
            insertEmployee(); // Call the method for inserting an employee
        } else if (e.getSource() == admin.addBtnIns) {
            insertInsurance(); // Call the method for inserting insurance
        } else if (e.getSource() == admin.addBtnColl) {
            insertCollateral(); // Call the method for inserting collateral
        } else if (e.getSource() == admin.addBtnBranch) {
            insertRentalBranch(); // Call the method for inserting branch
        } else if (e.getSource() == admin.addBtnHistory) {
            insertCarHis(); // Call the method for inserting car_history
        } else if (e.getSource() == admin.updateCusBtn) {
            updateTable("Customer", admin.modelCust); // Update customer table
        } else if (e.getSource() == admin.updateCarBtn) {
            updateTable("Car", admin.modelCar); // Update car table
        } else if (e.getSource() == admin.updateResBtn) {
            updateTable("Reservation", admin.modelRes); // Update reservation table
        } else if (e.getSource() == admin.updateEmpBtn) {
            updateTable("Employee", admin.modelEmp); // Update employee table
        } else if (e.getSource() == admin.updatePayBtn) {
            updateTable("Payment", admin.modelPay); // Update payment table
        } else if (e.getSource() == admin.updateInsBtn) {
            updateTable("Insurance", admin.modelIns); // Update insurance table
        } else if (e.getSource() == admin.updateColBtn) {
            updateTable("Collateral", admin.modelColl); // Update collateral table
        } else if (e.getSource() == admin.updateBraBtn) {
            updateTable("Rental Branch", admin.modelBranch); // Update branch table
        } else if (e.getSource() == admin.updateHisBtn) {
            updateTable("Car History", admin.modelCarHistory); // Update history table
        }else if (e.getSource() == admin.deleteCusbtn) {
            String cusId = admin.customerIdField.getText();
            deleteRecord("Customer", "Customer_Id", cusId);
        } else if (e.getSource() == admin.deleteCarBtn) {
            String carId = admin.carIdField.getText();
            deleteRecord("Car", "Car_Id", carId);
        } else if (e.getSource() == admin.deleteResBtn) {
            String reservationId = admin.reservationIdField.getText();
            deleteRecord("Reservation", "Reservation_Id", reservationId);
        } else if (e.getSource() == admin.deletePayBtn) {
            String paymentId = admin.paymentIdField.getText();
            deleteRecord("Payment", "Payment_Id", paymentId);
        } else if (e.getSource() == admin.deleteEmpBtn) {
            String employeeId = admin.employeeIdField.getText();
            deleteRecord("Employee", "Emp_Id", employeeId);
        } else if (e.getSource() == admin.deleteInsBtn) {
            String insuranceId = admin.insuranceIdField.getText();
            deleteRecord("Insurance", "Insurance_Id", insuranceId);
        } else if (e.getSource() == admin.deleteColBtn) {
            String collateralId = admin.collateralIdField.getText();
            deleteRecord("Collateral", "Collateral_Id", collateralId);
        } else if (e.getSource() == admin.deleteBraBtn) {
            String branchId = admin.branchIdField.getText();
            deleteRecord("Rental_Branch", "Branch_Id", branchId);
        } else if (e.getSource() == admin.deleteHisBtn) {
            String historyId = admin.historyIdField.getText();
            deleteRecord("Car_History", "History_Id", historyId);
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
            pstmt.setString(2, admin.carBranchIdField.getText());
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



    // Employee Insertion
    private void insertEmployee() {
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Employee VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            // Set parameters from the input fields
            pstmt.setString(1, admin.employeeIdField.getText()); // Emp_id
            pstmt.setString(2, admin.empFNameField.getText()); // E_F_name
            pstmt.setString(3, admin.empMNameField.getText()); // E_M_name (assuming this field exists)
            pstmt.setString(4, admin.empLNameField.getText()); // E_L_name
            pstmt.setString(5, admin.empRegionField.getText()); // E_region (assuming this field exists)
            pstmt.setString(6, admin.empWoredaField.getText()); // E_woreda (assuming this field exists)
            pstmt.setString(7, admin.empHouseNoField.getText()); // E_houseno (assuming this field exists)
            pstmt.setString(8, admin.empRole.getSelectedItem().toString()); // E_role
            pstmt.setString(9, admin.empBranchIdField.getText());
            pstmt.setString(10, admin.empEmailField.getText()); // email
            pstmt.setString(11, admin.empPhoneField.getText()); // PhoneNo

            // Execute the insert statement
            pstmt.executeUpdate();

            // Reload the table data to reflect the new entry
            admin.loadTableData("SELECT * FROM Employee", admin.modelEmp);

            // Clear the input fields after insertion
            admin.clearFields(admin.inputEmpPanel);

            // Show success message
            JOptionPane.showMessageDialog(null, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            // Handle SQL errors
            admin.showError("Employee Add Error", ex);
        }
    }

    // Insurance Insertion
    private void insertInsurance() {
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Insurance VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, admin.insuranceIdField.getText());
            pstmt.setString(2, admin.insCarIdField.getText());
            pstmt.setString(3, admin.policyNoField.getText());
            pstmt.setString(4, admin.providerField.getText());
            pstmt.setString(5, admin.expireDateField.getText());
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Insurance", admin.modelIns);
            admin.clearFields(admin.inputInsPanel);
            JOptionPane.showMessageDialog(null, "Insurance added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            admin.showError("Insurance Add Error", ex);
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


    private void insertRentalBranch() {
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Rental_Branch VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, admin.branchIdField.getText());
            pstmt.setString(2, admin.bRegionField.getText());
            pstmt.setString(3, admin.bZoneField.getText());
            pstmt.setInt(4, Integer.parseInt(admin.bWoredaField.getText()));
            pstmt.executeUpdate();
            admin.loadTableData("SELECT * FROM Rental_Branch", admin.modelBranch);
            admin.clearFields(admin.inputBranchPanel);
            JOptionPane.showMessageDialog(null, "Rental_Branch added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            admin.showError("Rental_Branch Add Error", ex);
        }
    }

    private void insertCarHis() {
        System.out.println("I am in the insertCarHis Statement");
        try (PreparedStatement pstmt = admin.connection.prepareStatement(
                "INSERT INTO Car_History VALUES (?, ?, ?, ?)")) {

            // Set parameters from the input fields
            pstmt.setString(1, admin.historyIdField.getText()); // History ID
            pstmt.setString(2, admin.carIDhisField.getText()); // Car ID
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


    private void updateTable(String tableName, DefaultTableModel model) {
        try {
            String primaryKey = getPrimaryKeyValue(tableName); // Get the primary key value from the corresponding field
            if (primaryKey == null || primaryKey.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the ID of the record to update!", "Missing ID", JOptionPane.WARNING_MESSAGE);
                return;
            }

            switch (tableName) {
                case "Customer":
                    updateCustomer(primaryKey, model);
                    break;
                case "Car":
                    updateCar(primaryKey, model);
                    break;
                case "Reservation":
                    updateReservation(primaryKey, model);
                    break;
                case "Payment":
                    updatePayment(primaryKey, model);
                    break;
                case "Employee":
                    updateEmployee(primaryKey, model);
                    break;
                case "Insurance":
                    updateInsurance(primaryKey, model);
                    break;
                case "Collateral":
                    updateCollateral(primaryKey, model);
                    break;
                case "Rental Branch":
                    updateRentalBranch(primaryKey, model);
                    break;
                case "Car History":
                    updateCarHistory(primaryKey, model);
                    break;
                default:
                    System.out.println("Unknown table: " + tableName);
            }
            JOptionPane.showMessageDialog(null, "Update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            admin.showError("Update Failed", ex);
        }
    }

    private String getPrimaryKeyValue(String tableName) {
        switch (tableName) {
            case "Customer":
                return admin.customerIdField.getText();
            case "Car":
                return admin.carIdField.getText();
            case "Reservation":
                return admin.reservationIdField.getText();
            case "Payment":
                return admin.paymentIdField.getText();
            case "Employee":
                return admin.employeeIdField.getText();
            case "Insurance":
                return admin.insuranceIdField.getText();
            case "Collateral":
                return admin.collateralIdField.getText();
            case "Rental Branch":
                return admin.branchIdField.getText();
            case "Car History":
                return admin.historyIdField.getText();
            default:
                return null;
        }
    }

    //Updating the Customer Table
    private void updateCustomer(String customerId, DefaultTableModel model) throws SQLException {
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
            pstmt.setString(10, customerId); // Use the provided customer ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Customer", model);
            } else {
                JOptionPane.showMessageDialog(null, "No customer found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Update Failed", ex);
        }
    }

    // Car Update
    private void updateCar(String carId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Car SET Car_Id=?, Branch_Id=?, Model=?, Plate_No=?, IsAvailable=? WHERE Car_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.carIdField.getText());
            pstmt.setString(2, admin.carBranchIdField.getText());
            pstmt.setString(3, admin.modelField.getText());
            pstmt.setString(4, admin.plateField.getText());
            pstmt.setString(5, admin.availableCombo.getSelectedItem().toString());
            pstmt.setString(6, carId); // Use the provided car ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Car", model);
            } else {
                JOptionPane.showMessageDialog(null, "No car found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Car Update Failed", ex);
        }
    }

    // Reservation Update
    private void updateReservation(String reservationId, DefaultTableModel model) throws SQLException {
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
            pstmt.setString(13, reservationId); // Use the provided reservation ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Reservation", model);
            } else {
                JOptionPane.showMessageDialog(null, "No reservation found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Reservation Update Failed", ex);
        }
    }

    // Payment Update
    private void updatePayment(String paymentId, DefaultTableModel model) throws SQLException {
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
            pstmt.setString(9, paymentId); // Use the provided payment ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Payment", model);
            } else {
                JOptionPane.showMessageDialog(null, "No payment found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Payment Update Failed", ex);
        }
    }

    // Employee Update
    private void updateEmployee(String employeeId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Employee SET Emp_id=?, E_F_name=?, E_M_name=?, E_L_name=?, E_region=?, E_woreda=?, E_houseno=?, E_role=?, Branch_id=?, email=?, PhoneNo=? WHERE Emp_id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.employeeIdField.getText());
            pstmt.setString(2, admin.empFNameField.getText());
            pstmt.setString(3, admin.empMNameField.getText());
            pstmt.setString(4, admin.empLNameField.getText());
            pstmt.setString(5, admin.empRegionField.getText());
            pstmt.setString(6, admin.empWoredaField.getText());
            pstmt.setString(7, admin.empHouseNoField.getText());
            pstmt.setString(8, admin.empRole.getSelectedItem().toString());
            pstmt.setString(9, admin.empBranchIdField.getText());
            pstmt.setString(10, admin.empEmailField.getText());
            pstmt.setString(11, admin.empPhoneField.getText());
            pstmt.setString(12, employeeId); // Use the provided employee ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Employee", model);
            } else {
                JOptionPane.showMessageDialog(null, "No employee found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Employee Update Failed", ex);
        }
    }

    // Insurance Update
    private void updateInsurance(String insuranceId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Insurance SET Insurance_Id=?, Car_Id=?, Insurance_Provider=?, Policy_Number=?, I_Expiry_Date=? WHERE Insurance_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.insuranceIdField.getText());
            pstmt.setString(2, admin.insCarIdField.getText());
            pstmt.setString(3, admin.providerField.getText());
            pstmt.setString(4, admin.policyNoField.getText());
            pstmt.setString(5, admin.expireDateField.getText());
            pstmt.setString(6, insuranceId); // Use the provided insurance ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Insurance", model);
            } else {
                JOptionPane.showMessageDialog(null, "No insurance found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Insurance Update Failed", ex);
        }
    }

    // Collateral Update
    private void updateCollateral(String collateralId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Collateral SET Collateral_Id=?, Customer_Id=?, Car_Id=?, Reservation_Id=?, Collateral_Type=?, Amount=?, Date_Received=? WHERE Collateral_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.collateralIdField.getText());
            pstmt.setString(2, admin.collCustomerIdField.getText());
            pstmt.setString(3, admin.collCarIdField.getText());
            pstmt.setString(4, admin.collReservationIdField.getText());
            pstmt.setString(5, admin.collTypeField.getText());
            pstmt.setString(6, admin.collamountField.getText());
            pstmt.setString(7, admin.reciveDateField.getText());
            pstmt.setString(8, collateralId); // Use the provided collateral ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Collateral", model);
            } else {
                JOptionPane.showMessageDialog(null, "No collateral found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Collateral Update Failed", ex);
        }
    }


    //RentalBranch Update
    private void updateRentalBranch(String branchId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Rental_Branch SET Branch_id=?, B_Region=?, B_Zone=?, B_Woreda=? WHERE Branch_id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.branchIdField.getText());
            pstmt.setString(2, admin.bRegionField.getText());
            pstmt.setString(3, admin.bZoneField.getText());
            pstmt.setInt(4, Integer.parseInt(admin.bWoredaField.getText()));
            pstmt.setString(5, branchId); // Use the provided branch ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Rental_Branch", model);
            } else {
                JOptionPane.showMessageDialog(null, "No branch found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("Branch Update Failed", ex);
        }
    }

    //CarHistory Update
    private void updateCarHistory(String historyId, DefaultTableModel model) throws SQLException {
        String query = "UPDATE Car_History SET History_Id=?, Car_Id=?, Description=?, Record_Type=? WHERE History_Id=?";
        try (PreparedStatement pstmt = admin.connection.prepareStatement(query)) {
            pstmt.setString(1, admin.historyIdField.getText());
            pstmt.setString(2, admin.carIDhisField.getText());
            pstmt.setString(3, admin.descriptionField.getText());
            pstmt.setString(4, admin.recordTypeField.getText());
            pstmt.setString(5, historyId); // Use the provided history ID
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                admin.loadTableData("SELECT * FROM Car_History", model);
            } else {
                JOptionPane.showMessageDialog(null, "No history found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            admin.showError("History Update Failed", ex);
        }
    }


    private void deleteRecord(String tableName, String primaryKeyColumn, String primaryKeyValue) {
        String query = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
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
                return (DefaultTableModel) admin.cusTable.getModel();// getModel returns a generic TableModel, we cast it to DefaultTableModel
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
