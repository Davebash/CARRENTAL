package DemoTrail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminInterface  extends CarRentalSystem {
    private JTabbedPane tabbedPane;
    JPanel inputBranchPanel,inputEmpPanel,inputCarPanel;
    JTextField carIdField,carHisIdField, branchIdField, modelField, plateField,carIDhisField,
            employeeIdField,empBranchIdField, empFNameField, empLNameField, empPhoneField,
            empEmailField,empMNameField, empRegionField, empWoredaField, empHouseNoField,
            payReservationIdField, amountField, paymentDateField;
    JComboBox<String>  empRole;
    JButton addBtnCar,addBtnEmp,addBtnIns,addBtnBranch;
    JButton updateCusBtn,updateCarBtn,updateResBtn,updateEmpBtn,updatePayBtn,
            updateInsBtn,updateColBtn,updateBraBtn,updateHisBtn, deleteCusbtn,deleteCarBtn,deleteResBtn,deleteEmpBtn,
            deletePayBtn, deleteInsBtn,deleteColBtn,deleteBraBtn,deleteHisBtn;

    AdminListner listner;
    JTable cusTable,caTable,empTable,resTable,payTable,insTable,colTable,braTable,hisTable;




    AdminInterface() {
        super("Hello World");
        initializeDatabase();
        setIcon();
        setTitle("Flash Car Rental - Administrator");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        listner = new AdminListner(this);

        Create_AdminUI();

        add(tabbedPane);
        setVisible(true);
    }

    private void Create_AdminUI(){
        tabbedPane.addTab("Customers", createAdminCustomerPanel());
        tabbedPane.addTab("Cars", createAdminCarPanel());
        tabbedPane.addTab("Reservations", createAdminReservationPanel());
        tabbedPane.addTab("Payments", createAdminPaymentPanel());
        tabbedPane.addTab("Employees", createAdminEmployeePanel());
        tabbedPane.addTab("Insurance", createAdminInsurancePanel());
        tabbedPane.addTab("Collateral", createAdminCollateralPanel());
        tabbedPane.addTab("Rental Branch",createAdminRentalBranchPanel());
        tabbedPane.addTab("Car History", createAdminCarHistoryPanel());
        tabbedPane.addTab("Report", createAdminReportPanel(connection));
        tabbedPane.addTab("Search", createAdminSearchPanel(connection));

    }

    // Customer Panel
    private JPanel createAdminCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputCustPanel = new JPanel(new GridLayout(0, 2));

        customerIdField = new JTextField();
        fNameField = new JTextField();
        lNameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        regionField = new JTextField();
        zoneField = new JTextField();
        houseNoField = new JTextField();
        licenseField = new JTextField();

        inputCustPanel.add(new JLabel("Customer ID (cidXX):"));
        inputCustPanel.add(customerIdField);
        inputCustPanel.add(new JLabel("First Name:"));
        inputCustPanel.add(fNameField);
        inputCustPanel.add(new JLabel("Last Name:"));
        inputCustPanel.add(lNameField);
        inputCustPanel.add(new JLabel("Phone:"));
        inputCustPanel.add(phoneField);
        inputCustPanel.add(new JLabel("Email:"));
        inputCustPanel.add(emailField);
        inputCustPanel.add(new JLabel("Region:"));
        inputCustPanel.add(regionField);
        inputCustPanel.add(new JLabel("Zone:"));
        inputCustPanel.add(zoneField);
        inputCustPanel.add(new JLabel("House No:"));
        inputCustPanel.add(houseNoField);
        inputCustPanel.add(new JLabel("Driver License:"));
        inputCustPanel.add(licenseField);

        modelCust =  new DefaultTableModel();
        cusTable = new JTable(modelCust);
        cusTable.setEnabled(false);


        cusTable.setName("Customer");

        loadTableData("SELECT * FROM Customer", Customer_Table(modelCust));


        addBtnCust = new JButton("Add");
        addBtnCust.addActionListener(listner);

        updateCusBtn = new JButton("Update");
        updateCusBtn.addActionListener(listner);

        deleteCusbtn = new JButton("Delete");
        deleteCusbtn.addActionListener(listner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnCust);
        buttonPanel.add(updateCusBtn);
        buttonPanel.add(deleteCusbtn);

        panel.add(inputCustPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(cusTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }



    // Car Panel
    private JPanel createAdminCarPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputCarPanel = new JPanel(new GridLayout(0, 2));

        carIdField = new JTextField();
        carBranchIdField = new JTextField();
        modelField = new JTextField();
        plateField = new JTextField();
        availableCombo = new JComboBox<>(new String[]{"1", "0"});

        inputCarPanel.add(new JLabel("Car ID (caidXX):"));
        inputCarPanel.add(carIdField);
        inputCarPanel.add(new JLabel("Branch ID (bidXX):"));
        inputCarPanel.add(carBranchIdField);
        inputCarPanel.add(new JLabel("Model:"));
        inputCarPanel.add(modelField);
        inputCarPanel.add(new JLabel("Plate No:"));
        inputCarPanel.add(plateField);
        inputCarPanel.add(new JLabel("Available:"));
        inputCarPanel.add(availableCombo);

        modelCar =  new DefaultTableModel();
        caTable = new JTable(modelCar);
        caTable.setEnabled(false);

        caTable.setName("Car");

        loadTableData("SELECT * FROM Car", Car_Table(modelCar));

        addBtnCar = new JButton("Add");
        addBtnCar.addActionListener(listner);

        updateCarBtn = new JButton("Update");
        updateCarBtn.addActionListener(listner);

        deleteCarBtn = new JButton("Delete");
        deleteCarBtn.addActionListener(listner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnCar);
        buttonPanel.add(updateCarBtn);
        buttonPanel.add(deleteCarBtn);

        panel.add(inputCarPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(caTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Reservation Panel
    private JPanel createAdminReservationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputResPanel = new JPanel(new GridLayout(0, 2));

        reservationIdField = new JTextField();
        resCarIdField = new JTextField();
        resCustomerIdField = new JTextField();
        resEmpIdField = new JTextField();
        startDateField = new JTextField();
        endDateField = new JTextField();
        statusCombo = new JComboBox<>(new Integer[]{0, 1, 2});
        statusCombo.setSelectedIndex(1);
        pickUpLocation = new JTextField();
        dropOffLocation = new JTextField();
        resInsuranceIdField = new JTextField();

        inputResPanel.add(new JLabel("Reservation ID (ridXX):"));
        inputResPanel.add(reservationIdField);
        inputResPanel.add(new JLabel("Car ID:"));
        inputResPanel.add(resCarIdField);
        inputResPanel.add(new JLabel("Customer ID:"));
        inputResPanel.add(resCustomerIdField);
        inputResPanel.add(new JLabel("Emp ID:"));
        inputResPanel.add(resEmpIdField);
        inputResPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        inputResPanel.add(startDateField);
        inputResPanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        inputResPanel.add(endDateField);
        inputResPanel.add(new JLabel("Pick-Up Location:"));
        inputResPanel.add(pickUpLocation);
        inputResPanel.add(new JLabel("Drop-Off Location:"));
        inputResPanel.add(dropOffLocation);
        inputResPanel.add(new JLabel("Status:"));
        inputResPanel.add(statusCombo);
        inputResPanel.add(new JLabel("Insurance ID:"));
        inputResPanel.add(resInsuranceIdField);

        modelRes =  new DefaultTableModel();
        resTable = new JTable(modelRes);
        resTable.setEnabled(false);
        resTable.setName("Reservation");

        loadTableData("SELECT * FROM Reservation", Reservation_Table(modelRes));

        addBtnRes = new JButton("Add");
        addBtnRes.addActionListener(listner);

        updateResBtn = new JButton("Update");
        updateResBtn.addActionListener(listner);

        deleteResBtn = new JButton("Delete");
        deleteResBtn.addActionListener(listner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnRes);
        buttonPanel.add(updateResBtn);
        buttonPanel.add(deleteResBtn);

        panel.add(inputResPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Payment Panel
    private JPanel createAdminPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputPayPanel = new JPanel(new GridLayout(0, 2));

        paymentIdField = new JTextField();
        payReservationIdField = new JTextField();
        amountField = new JTextField();
        noCarField = new JTextField();
        payTypeCombo = new JComboBox<>(new String[]{"Partial", "Full"});
        totalPriceField = new JTextField();
        methodCombo = new JComboBox<>(new String[]{"Cash", "Card", "Other"});
        paymentDateField = new JTextField();

        inputPayPanel.add(new JLabel("Payment ID (pidXX):"));
        inputPayPanel.add(paymentIdField);
        inputPayPanel.add(new JLabel("Reservation ID:"));
        inputPayPanel.add(payReservationIdField);
        inputPayPanel.add(new JLabel("Amount:"));
        inputPayPanel.add(amountField);
        inputPayPanel.add(new JLabel("Number of Cars:"));
        inputPayPanel.add(noCarField);
        inputPayPanel.add(new JLabel("Type:"));
        inputPayPanel.add(payTypeCombo);
        inputPayPanel.add(new JLabel("Total Price:"));
        inputPayPanel.add(totalPriceField);
        inputPayPanel.add(new JLabel("Method:"));
        inputPayPanel.add(methodCombo);
        inputPayPanel.add(new JLabel("Payment Date (YYYY-MM-DD):"));
        inputPayPanel.add(paymentDateField);

        modelPay =  new DefaultTableModel();
        payTable = new JTable(modelPay);
        payTable.setEnabled(false);
        payTable.setName("Payment");

        loadTableData("SELECT * FROM Payment", Payment_Table(modelPay));

        addBtnPay = new JButton("Add");
        addBtnPay.addActionListener(listner);

        updatePayBtn = new JButton("Update");
        updatePayBtn.addActionListener(listner);

        deletePayBtn = new JButton("Delete");
        deletePayBtn.addActionListener(listner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnPay);
        buttonPanel.add(updatePayBtn);
        buttonPanel.add(deletePayBtn);

        panel.add(inputPayPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(payTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Employee Panel
    private JPanel createAdminEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputEmpPanel = new JPanel(new GridLayout(0, 2));

        employeeIdField = new JTextField();
        empFNameField = new JTextField();
        empMNameField = new JTextField();
        empLNameField = new JTextField();
        empRegionField = new JTextField();
        empWoredaField = new JTextField();
        empHouseNoField = new JTextField();
        empRole = new JComboBox<>(new String[]{"Manager", "Staff", "Mechanic", "Sales"});
        empEmailField = new JTextField();
        empPhoneField = new JTextField();
        empBranchIdField = new JTextField();

        inputEmpPanel.add(new JLabel("Employee ID (eidXX):"));
        inputEmpPanel.add(employeeIdField);
        inputEmpPanel.add(new JLabel("First Name:"));
        inputEmpPanel.add(empFNameField);
        inputEmpPanel.add(new JLabel("Middle Name:"));
        inputEmpPanel.add(empMNameField);
        inputEmpPanel.add(new JLabel("Last Name:"));
        inputEmpPanel.add(empLNameField);
        inputEmpPanel.add(new JLabel("Region:"));
        inputEmpPanel.add(empRegionField);
        inputEmpPanel.add(new JLabel("Woreda:"));
        inputEmpPanel.add(empWoredaField);
        inputEmpPanel.add(new JLabel("House No:"));
        inputEmpPanel.add(empHouseNoField);
        inputEmpPanel.add(new JLabel("Role:"));
        inputEmpPanel.add(empRole);
        inputEmpPanel.add(new JLabel("Branch ID:"));
        inputEmpPanel.add(empBranchIdField);
        inputEmpPanel.add(new JLabel("Email:"));
        inputEmpPanel.add(empEmailField);
        inputEmpPanel.add(new JLabel("Phone:"));
        inputEmpPanel.add(empPhoneField);

        modelEmp =  new DefaultTableModel();
        empTable = new JTable(modelEmp);
        empTable.setEnabled(false);
        empTable.setName("Employee");


        loadTableData("SELECT * FROM Employee", Employee_Table(modelEmp));

        addBtnEmp = new JButton("Add");
        addBtnEmp.addActionListener(listner);

        updateEmpBtn = new JButton("Update");
        updateEmpBtn.addActionListener(listner);

        deleteEmpBtn = new JButton("Delete");
        deleteEmpBtn.addActionListener(listner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnEmp);
        buttonPanel.add(updateEmpBtn);
        buttonPanel.add(deleteEmpBtn);

        panel.add(inputEmpPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(empTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Insurance Panel
    private JPanel createAdminInsurancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputInsPanel = new JPanel(new GridLayout(0, 2));

        insuranceIdField = new JTextField();
        insCarIdField = new JTextField();
        policyNoField = new JTextField();
        providerField = new JTextField();
        expireDateField = new JTextField();

        inputInsPanel.add(new JLabel("Insurance ID (iidXX):"));
        inputInsPanel.add(insuranceIdField);
        inputInsPanel.add(new JLabel("Car ID:"));
        inputInsPanel.add(insCarIdField);
        inputInsPanel.add(new JLabel("Policy Number:"));
        inputInsPanel.add(policyNoField);
        inputInsPanel.add(new JLabel("Insurance Provider:"));
        inputInsPanel.add(providerField);
        inputInsPanel.add(new JLabel("Expire Date (YYYY-MM-DD):"));
        inputInsPanel.add(expireDateField);

        modelIns =  new DefaultTableModel();
        insTable = new JTable(modelIns);
        insTable.setEnabled(false);
        insTable.setName("Insurance");

        loadTableData("SELECT * FROM Insurance", Insurance_Table(modelIns));

        addBtnIns = new JButton("Add");
        addBtnIns.addActionListener(listner);

        updateInsBtn = new JButton("Update");
        updateInsBtn.addActionListener(listner);

        deleteInsBtn = new JButton("Delete");
        deleteInsBtn.addActionListener(listner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnIns);
        buttonPanel.add(updateInsBtn);
        buttonPanel.add(deleteInsBtn);

        panel.add(inputInsPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(insTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Collateral Panel
    private JPanel createAdminCollateralPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputCollPanel = new JPanel(new GridLayout(0, 2));

        collateralIdField = new JTextField();
        collReservationIdField = new JTextField();
        collCarIdField = new JTextField();
        collCustomerIdField = new JTextField();
        collTypeField = new JTextField();
        collamountField = new JTextField();
        reciveDateField = new JTextField();

        inputCollPanel.add(new JLabel("Collateral ID (colXX):"));
        inputCollPanel.add(collateralIdField);
        inputCollPanel.add(new JLabel("Customer ID:"));
        inputCollPanel.add(collCustomerIdField);
        inputCollPanel.add(new JLabel("Car ID:"));
        inputCollPanel.add(collCarIdField);
        inputCollPanel.add(new JLabel("Reservation ID:"));
        inputCollPanel.add(collReservationIdField);
        inputCollPanel.add(new JLabel("Type:"));
        inputCollPanel.add(collTypeField);
        inputCollPanel.add(new JLabel("Amount:"));
        inputCollPanel.add(collamountField);
        inputCollPanel.add(new JLabel("Recive Date (YYYY-MM-DD):"));
        inputCollPanel.add(reciveDateField);

        modelColl =  new DefaultTableModel();
        colTable = new JTable(modelColl);
        colTable.setEnabled(false);
        colTable.setName("Collateral");

        loadTableData("SELECT * FROM Collateral", Collateral_Table(modelColl));

        addBtnColl = new JButton("Add");
        addBtnColl.addActionListener(listner);

        updateColBtn = new JButton("Update");
        updateColBtn.addActionListener(listner);

        deleteColBtn = new JButton("Delete");
        deleteColBtn.addActionListener(listner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnColl);
        buttonPanel.add(updateColBtn);
        buttonPanel.add(deleteColBtn);

        panel.add(inputCollPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(colTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAdminRentalBranchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputBranchPanel = new JPanel(new GridLayout(0, 2));

        branchIdField = new JTextField();
        bRegionField = new JTextField();
        bZoneField = new JTextField();
        bWoredaField = new JTextField();

        inputBranchPanel.add(new JLabel("Branch ID (bidXX):"));
        inputBranchPanel.add(branchIdField);
        inputBranchPanel.add(new JLabel("Region:"));
        inputBranchPanel.add(bRegionField);
        inputBranchPanel.add(new JLabel("Zone (alphanumeric only):"));
        inputBranchPanel.add(bZoneField);
        inputBranchPanel.add(new JLabel("Woreda:"));
        inputBranchPanel.add(bWoredaField);

        modelBranch =  new DefaultTableModel();
        braTable = new JTable(modelBranch);
        braTable.setEnabled(false);
        braTable.setName("Rental Branch");

        loadTableData("SELECT * FROM Rental_Branch", Rental_Branch(modelBranch));

        addBtnBranch = new JButton("Add");
        addBtnBranch.addActionListener(listner);

        updateBraBtn = new JButton("Update");
        updateBraBtn.addActionListener(listner);

        deleteBraBtn = new JButton("Delete");
        deleteBraBtn.addActionListener(listner);



        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnBranch);
        buttonPanel.add(updateBraBtn);
        buttonPanel.add(deleteBraBtn);

        panel.add(inputBranchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(braTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAdminCarHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputHistoryPanel = new JPanel(new GridLayout(0, 2));

        historyIdField = new JTextField();
        carHisIdField = new JTextField();
        descriptionField = new JTextField();
        recordTypeField = new JTextField();
        carIDhisField = new JTextField();

        inputHistoryPanel.add(new JLabel("History ID (hidXX):"));
        inputHistoryPanel.add(historyIdField);
        inputHistoryPanel.add(new JLabel("Car ID:"));
        inputHistoryPanel.add(carIDhisField);
        inputHistoryPanel.add(new JLabel("Description:"));
        inputHistoryPanel.add(descriptionField);
        inputHistoryPanel.add(new JLabel("Record Type (Service/Damage/Maintenance/Other):"));
        inputHistoryPanel.add(recordTypeField);

        modelCarHistory =  new DefaultTableModel();
        hisTable = new JTable(modelCarHistory);
        hisTable.setEnabled(false);
        hisTable.setName("Car History");

        loadTableData("SELECT * FROM Car_History", Car_History(modelCarHistory));

        addBtnHistory = new JButton("Add");
        addBtnHistory.addActionListener(listner);

        updateHisBtn = new JButton("Update");
        updateHisBtn.addActionListener(listner);

        deleteHisBtn = new JButton("Delete");
        deleteHisBtn.addActionListener(listner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnHistory);
        buttonPanel.add(updateHisBtn);
        buttonPanel.add(deleteHisBtn);

        panel.add(inputHistoryPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(hisTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }


    private DefaultTableModel Customer_Table(DefaultTableModel model){
        model.addColumn("Customer ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Phone");
        model.addColumn("Email");
        model.addColumn("Region");
        model.addColumn("Zone");
        model.addColumn("House No");
        model.addColumn("License");

        return model;
    }
    private DefaultTableModel Car_Table(DefaultTableModel model){

        model.addColumn("Car ID");
        model.addColumn("Branch ID");
        model.addColumn("Model");
        model.addColumn("Plate No");
        model.addColumn("Available");
        return model;
    }
    private DefaultTableModel Reservation_Table(DefaultTableModel model){

        model.addColumn("Reservation_Id");
        model.addColumn("Car_Id");
        model.addColumn("Customer_Id");
        model.addColumn("Emp_Id");
        model.addColumn("Reservation_Status");
        model.addColumn("Collateral_Id");
        model.addColumn("PickUp_Date");
        model.addColumn("Dropoff_Date");
        model.addColumn("Pickup_Location");
        model.addColumn("Dropoff_Location");
        model.addColumn("Payment_Id");
        model.addColumn("Insurance_Id");

        return model;
    }
    private DefaultTableModel Payment_Table(DefaultTableModel model){
        model.addColumn("Payment_Id");
        model.addColumn("Reservation_Id");
        model.addColumn("Price_Per_Car");
        model.addColumn("No_Cars");
        model.addColumn("Payment_Type");
        model.addColumn("Total_Price");
        model.addColumn("Payment_Method");
        model.addColumn("Payment_Date");

        return model;
    }
    private DefaultTableModel Employee_Table(DefaultTableModel model){
        model.addColumn("Emp_id");
        model.addColumn("E_F_name");
        model.addColumn("E_M_name");
        model.addColumn("E_L_name");
        model.addColumn("E_region");
        model.addColumn("E_woreda");
        model.addColumn("E_houseno");
        model.addColumn("E_role");
        model.addColumn("Branch_id");
        model.addColumn("email");
        model.addColumn("PhoneNo");

        return model;
    }

    private DefaultTableModel Insurance_Table(DefaultTableModel model){

        model.addColumn("Insurance ID");
        model.addColumn("Reservation ID");
        model.addColumn("Type");
        model.addColumn("Coverage Amount");
        model.addColumn("Premium");
        return model;
    }
    private DefaultTableModel Collateral_Table(DefaultTableModel model){
        model.addColumn("Collateral_Id");
        model.addColumn("Customer_Id");
        model.addColumn("Car_Id");
        model.addColumn("Reservation_Id");
        model.addColumn("Collateral_Type");
        model.addColumn("Amount");
        model.addColumn("Date_Received");

        return model;
    }
    private DefaultTableModel Rental_Branch(DefaultTableModel model){
        model.addColumn("Branch_id");
        model.addColumn("B_Region");
        model.addColumn("B_Zone");
        model.addColumn("B_Woreda");

        return model;
    }
    private DefaultTableModel Car_History(DefaultTableModel model){
        model.addColumn("History_Id");
        model.addColumn("Car_Id");
        model.addColumn("Description");
        model.addColumn("Record_Type");

        return model;
    }


    //Report-Panel
    public JPanel createAdminReportPanel(Connection dbConnection) {
        JPanel resultPanel = new JPanel(new BorderLayout());

        // Create the combo box with search options
        String[] searchOptions = {
                "Employee Branch",
                "Customer Reservation Status",
                "Total Revenue Summary",
                "Customer Reservation History",
                "Insurance Expiry",
                "Branch Revenue Summary",
                "Employee Performance Report",
                "Car Report"
        };
        JComboBox<String> searchComboBox = new JComboBox<>(searchOptions);
        searchComboBox.setSelectedIndex(0); // Set the first option as the default selection

        // Create the search button
        JButton searchButton = new JButton("Search");
        searchButton.setFocusable(false);
        searchButton.addActionListener(new reportButtonListner(dbConnection, searchComboBox, resultPanel));

        // Create the result table
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable resultTable = new JTable(tableModel);
        resultTable.setEnabled(false); // Make the table non-editable

        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Create the clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setFocusable(false);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0); // Clear the table data
                tableModel.setColumnCount(0); // Clear the table columns
            }
        });

        // Create the top panel with the combo box and search button
        JPanel topPanel = new JPanel();
        topPanel.add(searchComboBox);
        topPanel.add(searchButton);

        // Create the bottom panel with the clear button
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(clearButton);

        // Add components to the result panel
        resultPanel.add(topPanel, BorderLayout.NORTH);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        resultPanel.add(bottomPanel, BorderLayout.SOUTH);

        return resultPanel; // Return the correct panel
    }


    private static class reportButtonListner implements ActionListener {
        private final Connection connection;
        private final JComboBox<String> searchComboBox;
        private final JPanel resultPanel;

        private reportButtonListner(Connection connection, JComboBox<String> searchComboBox, JPanel resultPanel) {
            this.connection = connection;
            this.searchComboBox = searchComboBox;
            this.resultPanel = resultPanel;
        }



        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedOption = (String) searchComboBox.getSelectedItem();
            if (selectedOption == null) {
                JOptionPane.showMessageDialog(resultPanel, "Please select a report to view.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ResultSet resultSet = null;
                switch (selectedOption) {
                    case "Employee Branch":
                        resultSet = connection.prepareStatement("SELECT * FROM Employee_Branch").executeQuery();
                        break;
                    case "Customer Reservation Status":
                        resultSet = connection.prepareStatement("SELECT * FROM Customer_Reservation_Status").executeQuery();
                        break;
                    case "Total Revenue Summary":
                        resultSet = connection.prepareStatement("SELECT * FROM Total_Revenue_Summary").executeQuery();
                        break;
                    case "Customer Reservation History":
                        resultSet = connection.prepareStatement("SELECT * FROM Customer_Reservation_History").executeQuery();
                        break;
                    case "Insurance Expiry":
                        resultSet = connection.prepareStatement("SELECT * FROM Insurance_Expiry").executeQuery();
                        break;
                    case "Branch Revenue Summary":
                        resultSet = connection.prepareStatement("SELECT * FROM Branch_Revenue_Summary").executeQuery();
                        break;
                    case "Employee Performance Report":
                        resultSet = connection.prepareStatement("SELECT * FROM Employee_Performance_Report").executeQuery();
                        break;
                    case "Car Report":
                        resultSet = connection.prepareStatement("SELECT * FROM Car_Report").executeQuery();
                        break;
                }

                if (resultSet != null) {
                    // Clear the existing table
                    DefaultTableModel tableModel = (DefaultTableModel) ((JTable) ((JScrollPane) resultPanel.getComponent(1)).getViewport().getView()).getModel();
                    tableModel.setRowCount(0);

                    // Set up the table columns based on the ResultSet metadata
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    tableModel.setColumnCount(0); // Clear existing columns
                    for (int i = 1; i <= columnCount; i++) {
                        tableModel.addColumn(metaData.getColumnName(i));
                    }

                    // Populate the table with data from the ResultSet
                    while (resultSet.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= row.length; i++) {
                            row[i - 1] = resultSet.getObject(i);
                        }
                        tableModel.addRow(row);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(resultPanel, "Error executing report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    //Search-Panel
    public static JPanel createAdminSearchPanel(Connection dbConnection) {
        JPanel resultPanel = new JPanel(new BorderLayout());

        // Create the combo box with search options
        String[] searchOptions = {"Customer by ID", "Employee by Name", "Employee by ID", "Car by ID", "Reservation by ID", "Car History by Car ID"};
        JComboBox<String> searchComboBox = new JComboBox<>(searchOptions);
        searchComboBox.setSelectedIndex(0); // Set "Customer by ID" as the default selection

        // Create the search button
        JButton searchButton = new JButton("Search");
        searchButton.setFocusable(false);
        searchButton.addActionListener(new SearchButtonListener(dbConnection, searchComboBox, resultPanel));

        // Create the result table
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable resultTable = new JTable(tableModel);
        resultTable.setEnabled(false); // Make the table non-editable

        JScrollPane scrollPane = new JScrollPane(resultTable);
        JButton clearButton = new JButton("Clear");
        clearButton.setFocusable(false);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                tableModel.setColumnCount(0);
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(searchComboBox);
        topPanel.add(searchButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(clearButton);

        resultPanel.add(topPanel, BorderLayout.NORTH);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        resultPanel.add(bottomPanel, BorderLayout.SOUTH);

        return resultPanel;
    }

    private static class SearchButtonListener implements ActionListener {
        private final Connection connection;
        private final JComboBox<String> searchComboBox;
        private final JPanel resultPanel;

        public SearchButtonListener(Connection connection, JComboBox<String> searchComboBox, JPanel resultPanel) {
            this.connection = connection;
            this.searchComboBox = searchComboBox;
            this.resultPanel = resultPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedOption = (String) searchComboBox.getSelectedItem();//returns Object, that's why we cast it to string
            if (selectedOption == null) {
                JOptionPane.showMessageDialog(resultPanel, "Please select a search option.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String exampleValue = "";
            switch (selectedOption) {
                case "Customer by ID":
                    exampleValue = "cid01";
                    break;
                case "Employee by Name":
                    exampleValue = "John";
                    break;
                case "Employee by ID":
                    exampleValue = "emp01";
                    break;
                case "Car by ID":
                    exampleValue = "caid02";
                    break;
                case "Reservation by ID":
                    exampleValue = "rid01";
                    break;
                case "Car History by Car ID":
                    exampleValue = "caid02";
                    break;
            }

            String input = JOptionPane.showInputDialog(resultPanel, "Enter search value:\nExample: " + exampleValue);
            if (input == null || input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(resultPanel, "Please enter a search value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ResultSet resultSet = null;
                switch (selectedOption) {
                    case "Customer by ID":
                       // prepareStatement() is a method that prepares an SQL statement for execution.
                        resultSet = connection.prepareStatement("SELECT * FROM Customer_Info('" + input + "')").executeQuery();
                        break;
                    case "Employee by Name":
                        resultSet = connection.prepareStatement("SELECT * FROM Search_EmployeeByName('" + input + "')").executeQuery();
                        break;
                    case "Employee by ID":
                        resultSet = connection.prepareStatement("SELECT * FROM Search_EmployeeById('" + input + "')").executeQuery();
                        break;
                    case "Car by ID":
                        resultSet = connection.prepareStatement("SELECT * FROM Car_Info('" + input + "')").executeQuery();
                        break;
                    case "Reservation by ID":
                        resultSet = connection.prepareStatement("SELECT * FROM Search_ReservationById('" + input + "')").executeQuery();
                        break;
                    case "Car History by Car ID":
                        resultSet = connection.prepareStatement("SELECT * FROM Search_CarHistoryById('" + input + "')").executeQuery();
                        break;
                }

                if (resultSet != null) {
                    DefaultTableModel tableModel = (DefaultTableModel) ((JTable) ((JScrollPane) resultPanel.getComponent(1)).getViewport().getView()).getModel();
                    tableModel.setRowCount(0); // clear the table
                    ResultSetMetaData metaData = resultSet.getMetaData();// information about the resultset(column_name and number_of_columns)
                    int columnCount = metaData.getColumnCount();

                    tableModel.setColumnCount(0);
                    for (int i = 1; i <= columnCount; i++) {
                        tableModel.addColumn(metaData.getColumnName(i));
                    }

                    while (resultSet.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= row.length; i++) {
                            row[i - 1] = resultSet.getObject(i);
                        }
                        tableModel.addRow(row);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(resultPanel, "Error executing search: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}




