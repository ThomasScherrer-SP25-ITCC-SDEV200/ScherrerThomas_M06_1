package com.example.thomasscherrer_m06_1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

    public class StaffDatabaseApp extends Application {
        private TextField tfID = new TextField();
        private TextField tfLastName = new TextField();
        private TextField tfFirstName = new TextField();
        private TextField tfMI = new TextField();
        private TextField tfAddress = new TextField();
        private TextField tfCity = new TextField();
        private TextField tfState = new TextField();
        private TextField tfTelephone = new TextField();
        private TextField tfEmail = new TextField();
        private Connection connection;

        @Override
        public void start(Stage primaryStage) {
            connectToDatabase();

            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10));
            gridPane.setHgap(5);
            gridPane.setVgap(5);

            gridPane.add(new Label("ID:"), 0, 0);
            gridPane.add(tfID, 1, 0);
            gridPane.add(new Label("Last Name:"), 0, 1);
            gridPane.add(tfLastName, 1, 1);
            gridPane.add(new Label("First Name:"), 0, 2);
            gridPane.add(tfFirstName, 1, 2);
            gridPane.add(new Label("MI:"), 0, 3);
            gridPane.add(tfMI, 1, 3);
            gridPane.add(new Label("Address:"), 0, 4);
            gridPane.add(tfAddress, 1, 4);
            gridPane.add(new Label("City:"), 0, 5);
            gridPane.add(tfCity, 1, 5);
            gridPane.add(new Label("State:"), 0, 6);
            gridPane.add(tfState, 1, 6);
            gridPane.add(new Label("Telephone:"), 0, 7);
            gridPane.add(tfTelephone, 1, 7);
            gridPane.add(new Label("Email:"), 0, 8);
            gridPane.add(tfEmail, 1, 8);

            Button btnView = new Button("View");
            Button btnInsert = new Button("Insert");
            Button btnUpdate = new Button("Update");

            gridPane.add(btnView, 0, 9);
            gridPane.add(btnInsert, 1, 9);
            gridPane.add(btnUpdate, 2, 9);

            btnView.setOnAction(e -> viewRecord());
            btnInsert.setOnAction(e -> insertRecord());
            btnUpdate.setOnAction(e -> updateRecord());

            Scene scene = new Scene(gridPane, 400, 400);
            primaryStage.setTitle("Staff Database Manager");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        private void connectToDatabase() {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/main", "root", "");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        private void viewRecord() {
            try {
                String query = "SELECT * FROM Staff WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, tfID.getText());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    tfLastName.setText(rs.getString("lastName"));
                    tfFirstName.setText(rs.getString("firstName"));
                    tfMI.setText(rs.getString("mi"));
                    tfAddress.setText(rs.getString("address"));
                    tfCity.setText(rs.getString("city"));
                    tfState.setText(rs.getString("state"));
                    tfTelephone.setText(rs.getString("telephone"));
                    tfEmail.setText(rs.getString("email"));
                } else {
                    showAlert("No record found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void insertRecord() {
            try {
                String query = "INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, tfID.getText());
                stmt.setString(2, tfLastName.getText());
                stmt.setString(3, tfFirstName.getText());
                stmt.setString(4, tfMI.getText());
                stmt.setString(5, tfAddress.getText());
                stmt.setString(6, tfCity.getText());
                stmt.setString(7, tfState.getText());
                stmt.setString(8, tfTelephone.getText());
                stmt.setString(9, tfEmail.getText());
                stmt.executeUpdate();
                showAlert("Record inserted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void updateRecord() {
            try {
                String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, tfLastName.getText());
                stmt.setString(2, tfFirstName.getText());
                stmt.setString(3, tfMI.getText());
                stmt.setString(4, tfAddress.getText());
                stmt.setString(5, tfCity.getText());
                stmt.setString(6, tfState.getText());
                stmt.setString(7, tfTelephone.getText());
                stmt.setString(8, tfEmail.getText());
                stmt.setString(9, tfID.getText());
                stmt.executeUpdate();
                showAlert("Record updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void showAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(message);
            alert.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

