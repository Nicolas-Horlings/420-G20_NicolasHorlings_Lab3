package com.example.demolab3;
//imports
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;

public class Controller {
    //call in value
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn emailColumn;

    @FXML //initialize nameColumn and emailColumn into string
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML//initializes name and email
    public void handleSave() {
        String name = nameField.getText();
        String email = emailField.getText();
        //error for email
        if (name.isEmpty() || email.isEmpty() || !email.contains("@")) {
            showAlert("Invalid Input", "Please provide a valid name and email address.");
            return;
        }
        //writes variables into file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", true))) {
            writer.write(name + " | " + email);
            writer.newLine();
            tableView.getItems().add(new User(name, email));
            nameField.clear();//clears TextFields
            emailField.clear();
        } catch (IOException e) {//display error if failed to save
            showAlert("File Error", "Unable to save data.");
        }
    }

    @FXML
    public void handleLoad() {//loads into TableView
        tableView.getItems().clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length == 2) {
                    tableView.getItems().add(new User(parts[0], parts[1]));
                }
            }//error message if failed to load
        } catch (IOException e) {
            showAlert("File Error", "Unable to load data.");
        }
    }

    @FXML//displays variables on table view
    public void handleExit() {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}

