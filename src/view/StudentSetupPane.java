package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.*;

import java.io.Serializable;
import java.time.LocalDate;


public class StudentSetupPane extends GridPane implements Serializable {

    transient private ComboBox<Course> cboCourse;
    transient private TextField txtPNum, txtFName, txtSName, txtEmail;
    transient private DatePicker date;
    transient private Button createBtn;

    public StudentSetupPane() {
        //styling
        this.setPadding(new Insets(80));
        this.setVgap(15);
        this.setHgap(20);
        this.setAlignment(Pos.CENTER);

        //for aligning labels to the right side
        ColumnConstraints c0 = new ColumnConstraints();
        c0.setHalignment(HPos.RIGHT);
        this.getColumnConstraints().add(c0);

        //label creation
        Label lblCourse = new Label("Select Course: ");
        Label lblPnum   = new Label("Input PNumber: ");
        Label lblFname  = new Label("Input First Name: ");
        Label lblSname  = new Label("Input Surname: ");
        Label lblEmail  = new Label("Input Email: ");
        Label lblDate   = new Label("Input Date: ");

        //create combo box / text fields / date / button
        cboCourse = new ComboBox<Course>();
        txtPNum   = new TextField();
        txtFName  = new TextField();
        txtSName  = new TextField();
        txtEmail  = new TextField();
        date      = new DatePicker();
        createBtn = new Button("Create Profile");

        //adding to container
        this.add(lblCourse, 0, 0);
        this.add(cboCourse, 1, 0);

        this.add(lblPnum, 0, 1);
        this.add(txtPNum, 1, 1);

        this.add(lblFname, 0, 2);
        this.add(txtFName, 1, 2);

        this.add(lblSname, 0, 3);
        this.add(txtSName, 1, 3);

        this.add(lblEmail, 0, 4);
        this.add(txtEmail, 1, 4);

        this.add(lblDate, 0, 5);
        this.add(date, 1, 5);

        this.add(new HBox(), 0, 6);
        this.add(createBtn, 1, 6);
    }

    //method to populate Course Selector combo box
    public void populateComboBox(Course[] courses) {
        cboCourse.getItems().addAll(courses);
        cboCourse.getSelectionModel().select(0);
    }

    public Course getSelectedCourse() {
        return cboCourse.getSelectionModel().getSelectedItem();
    }

    public String getFNameInput() {
        return txtFName.getText();
    }

    public String getSNameInput() {
        return txtSName.getText();
    }

    public String getPNumInput() {
        return txtPNum.getText();
    }

    public String getEmailInput() {
        return txtEmail.getText();
    }

    public LocalDate getDateInput() {
        return date.getValue();
    }

    //method to check that all fields contain information
    public boolean checkValid() {
        if(txtFName.getText().isEmpty() || txtSName.getText().isEmpty() ||
                txtPNum.getText().isEmpty() || txtEmail.getText().isEmpty() || date.getValue() == null) {
            return true;
        } else {
            return false;
        }
    }
    //method to check PNumber format validity
    public boolean checkPNumValid() {
        if(txtPNum.getText().matches("[P-p].*")) {
            return false;
        } else {
            return true;
        }

    }
    //get index value of selected course
    public int getcboValue() {
        int index = cboCourse.getSelectionModel().getSelectedIndex();
        return index;
    }

    //getters
    public int getCboCourse() {
        return cboCourse.getSelectionModel().getSelectedIndex();
    }

    public String getTxtPNum() {
        return txtPNum.getText();
    }

    public Name getTxtNames() {
        Name name = new Name(txtFName.getText(), txtSName.getText());
        return name;
    }

    public String getTxtEmail() {
        return txtEmail.getText();
    }

    public LocalDate getDate() {
        return date.getValue();
    }

    //setters for setting the setup pane fields
    public void setCboCourse(Course c) {
         if(c.getCourseName().equals("Computer Science")) {
             cboCourse.getSelectionModel().select(0);
         } else {
             cboCourse.getSelectionModel().select(1);
         }
    }

    public void setTxtPNum(String s) {
        txtPNum.setText(s);
    }

    public void setTxtNames(Name n) {
        txtFName.setText(n.getFirstName());
        txtFName.setText(n.getFamilyName());
    }

    public void setTxtEmail(String e) {
        txtEmail.setText(e);
    }

    public void setDate(LocalDate d) {
        date.setValue(d);
    }

    //clear Setup pane method for loading
    public void clearSetup() {
        txtPNum.clear();
        txtFName.clear();
        txtSName.clear();
        txtSName.clear();
        txtEmail.clear();
        date.setValue(null);
    }

    //attach create profile button handler
    public void addCreateProfileHandler(EventHandler<ActionEvent> handler) {
        createBtn.setOnAction(handler);
    }
}
