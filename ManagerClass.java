package DemoTrail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManagerClass extends CarRentalSystem {

    private JTabbedPane tabbedPane;
    JPanel inputBranchPanel,inputCarPanel;
    JTextField carIdField, branchIdField, modelField, plateField,
            payReservationIdField, amountField, paymentDateField, carHisIdField
            ;
    JButton addBtnCar,addBtnIns,addBtnBranch;
    JButton updateCusBtn,updateCarBtn,updateResBtn,
            updateInsBtn,updateColBtn,updateBraBtn;
    ManagerListner listener;
    JTable cusTable,caTable,empTable,resTable,payTable,insTable,colTable,braTable,hisTable;

    public ManagerClass() {
        super("Hello World");
        initializeDatabase();
        setIcon();
        setTitle("Flash Car Rental- Manager");
        setSize(1200, 800);
        listener = new ManagerListner(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        Create_ManagerUI();

        add(tabbedPane);
        setVisible(true);
    }


    private void Create_ManagerUI(){
        tabbedPane.addTab("Customers", createManagerCustomerPanel());
        tabbedPane.addTab("Cars", createManagerCarPanel());
        tabbedPane.addTab("Reservations", createManagerReservationPanel());
        tabbedPane.addTab("Payments", createManagerPaymentPanel());
        tabbedPane.addTab("Employees", createManagerEmployeePanel());
        tabbedPane.addTab("Insurance", createManagerInsurancePanel());
        tabbedPane.addTab("Collateral", createManagerCollateralPanel());
        tabbedPane.addTab("Rental Branch",createManagerRentalBranchPanel());
        tabbedPane.addTab("Car History", createManagerCarHistoryPanel());
        tabbedPane.addTab("Search", createManagerSearchPanel(connection));

    }

    // Customer Panel
    private JPanel createManagerCustomerPanel() {
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

        modelCust = new DefaultTableModel();
        cusTable = new JTable(modelCust);
        cusTable.setEnabled(false);

        cusTable.setName("Customer");

        loadTableData("SELECT * FROM Customer", Customer_Table(modelCust));

        addBtnCust = new JButton("Add");
        addBtnCust.addActionListener(listener);

        updateCusBtn = new JButton("Update");
        updateCusBtn.addActionListener(listener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnCust);
        buttonPanel.add(updateCusBtn);

        panel.add(inputCustPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(cusTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }



    // Car Panel
    private JPanel createManagerCarPanel() {
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
        addBtnCar.addActionListener(listener);

        updateCarBtn = new JButton("Update");
        updateCarBtn.addActionListener(listener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnCar);
        buttonPanel.add(updateCarBtn);

        panel.add(inputCarPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(caTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Reservation Panel
    private JPanel createManagerReservationPanel() {
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
        addBtnRes.addActionListener(listener);

        updateResBtn = new JButton("Update");
        updateResBtn.addActionListener(listener);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnRes);
        buttonPanel.add(updateResBtn);

        panel.add(inputResPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Payment Panel
    private JPanel createManagerPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputPayPanel = new JPanel(new GridLayout(0, 2));

        paymentIdField = new JTextField();
        payReservationIdField = new JTextField();
        amountField = new JTextField();
        noCarField = new JTextField();
        payTypeCombo = new JComboBox<>(new String[]{"Partial", "Full"});
        totalPriceField = new JTextField();
        methodCombo = new JComboBox<>(new String[]{"Cash", "Credit Card", "Debit Card", "Online"});
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
        inputPayPanel.add(new JLabel("Payment Date (YYYY-MM-DD):"));
        inputPayPanel.add(paymentDateField);

        modelPay =  new DefaultTableModel();
        payTable = new JTable(modelPay);
        payTable.setEnabled(false);
        payTable.setName("Payment");

        loadTableData("SELECT * FROM Payment", Payment_Table(modelPay));

        addBtnPay = new JButton("Add");
        addBtnPay.addActionListener(listener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnPay);


        panel.add(inputPayPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(payTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Employee Panel
    private JPanel createManagerEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create a custom DefaultTableModel that makes the table non-editable
        modelEmp =  new DefaultTableModel();
        empTable = new JTable(modelEmp);
        empTable.setEnabled(false);
        empTable.setName("Employee");

        loadTableData("SELECT * FROM Employee", Employee_Table(modelEmp));

        panel.add(new JScrollPane(empTable), BorderLayout.CENTER);

        return panel;
    }

    // Insurance Panel
    private JPanel createManagerInsurancePanel() {
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
        addBtnIns.addActionListener(listener);

        updateInsBtn = new JButton("Update");
        updateInsBtn.addActionListener(listener);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnIns);
        buttonPanel.add(updateInsBtn);

        panel.add(inputInsPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(insTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Collateral Panel
    private JPanel createManagerCollateralPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputCollPanel = new JPanel(new GridLayout(0, 2));

        collateralIdField = new JTextField();
        collReservationIdField = new JTextField();
        collCarIdField = new JTextField();
        collCustomerIdField = new JTextField();
        collTypeField = new JTextField();
        collamountField = new JTextField();
        reciveDateField = new JTextField();

        inputCollPanel.add(new JLabel("Collateral ID (colidXX):"));
        inputCollPanel.add(collateralIdField);
        inputCollPanel.add(new JLabel("Reservation ID:"));
        inputCollPanel.add(collReservationIdField);
        inputCollPanel.add(new JLabel("Car ID:"));
        inputCollPanel.add(collCarIdField);
        inputCollPanel.add(new JLabel("Customer ID:"));
        inputCollPanel.add(collCustomerIdField);
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
        addBtnColl.addActionListener(listener);

        updateColBtn = new JButton("Update");
        updateColBtn.addActionListener(listener);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnColl);
        buttonPanel.add(updateColBtn);

        panel.add(inputCollPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(colTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createManagerRentalBranchPanel() {
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
        addBtnBranch.addActionListener(listener);

        updateBraBtn = new JButton("Update");
        updateBraBtn.addActionListener(listener);



        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnBranch);
        buttonPanel.add(updateBraBtn);

        panel.add(inputBranchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(braTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createManagerCarHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        inputHistoryPanel = new JPanel(new GridLayout(0, 2));

        historyIdField = new JTextField();
        carHisIdField = new JTextField();
        descriptionField = new JTextField();
        recordTypeField = new JTextField();

        inputHistoryPanel.add(new JLabel("History ID (hidXX):"));
        inputHistoryPanel.add(historyIdField);
        inputHistoryPanel.add(new JLabel("Car ID:"));
        inputHistoryPanel.add(carHisIdField);
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
        addBtnHistory.addActionListener(listener);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtnHistory);

        panel.add(inputHistoryPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(hisTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    //Search-Panel
    public static JPanel createManagerSearchPanel(Connection dbConnection) {
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
            String selectedOption = (String) searchComboBox.getSelectedItem();
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
                    tableModel.setRowCount(0); // Clear the table
                    java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
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

}
