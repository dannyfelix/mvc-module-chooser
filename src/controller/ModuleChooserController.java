package controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import model.*; //imports all model & view classes
import view.*;

import java.io.*;
import java.util.Collection;
import java.util.Optional;

public class ModuleChooserController {

    //fields to be used throughout the class
    private ModuleChooserRootPane view;
    private StudentProfile model;
    private StudentSetupPane ssp;
    private SelectModulesPane smp;
    private OverviewPane ovp;
    private MyMenuBar mmb;
    private Course[] course;

    public ModuleChooserController(ModuleChooserRootPane view, StudentProfile model) {
        //initialise model and view fields
        this.model = model;
        this.view = view;

        ssp = view.getStudentSetupPane();
        smp = view.getSelectModulesPane();
        ovp = view.getOverviewPane();
        mmb = view.getMenuBar();
        course = setupAndGetCourses();

        ssp.populateComboBox(setupAndGetCourses());

        //attach event handlers to view using private helper method
        this.attachEventHandlers();

    }

    private void attachEventHandlers() {
        //attaching event handlers - lambda / inner-class
        //StudentSetupPane Handlers
        ssp.addCreateProfileHandler(new CreateProfileHandler());

        //SelectModulesPane Handlers
        smp.addt1AddHandler(new t1AddHandler());
        smp.addt2AddHandler(new t2AddHandler());
        smp.addt1RemoveHandler(new t1RemoveHandler());
        smp.addt2RemoveHandler(new t2RemoveHandler());
        smp.addResetHandler(new ResetHandler());
        smp.addSubmitHandler(new SubmitHandler());

        //OverviewPane handler
        ovp.addSaveProfileHandler(new SaveProfileHandler());

        mmb.addLoadMenuHandler(new LoadMenuHandler());
        mmb.addSaveMenuHandler(new SaveMenuHandler());
        mmb.addExitMenuHandler(e -> System.exit(0)); // two lambdas :-D
        mmb.addAboutMenuHandler(e -> this.alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", null,
                "Final Year Module Chooser\n\nCopyright © 2018 - Daniel Lewis - All Rights Reserved\n\n\n\nHaha, just kidding."));
    }

    //inner-class event handler to create profile from inputted data
    public class CreateProfileHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            //validity checkers
            if(ssp.checkValid()) {
                alertDialogBuilder(AlertType.ERROR, "Error", "Information Missing.", "Please fill all fields with the necessary information.");
                return; //^ alert dialog displays if not all of the fields are filled, return ends method if methods return true.
            }
            if(ssp.checkPNumValid()) {
                alertDialogBuilder(AlertType.ERROR, "Error", "Invalid PNumber.", "PNumber must start with a P.");
                return; //^ checks PNumber is a proper format
            }
            //populate the data model with inputted data.
            model.setpNumber(ssp.getPNumInput());
            model.setStudentName(new Name(ssp.getFNameInput(), ssp.getSNameInput()));
            model.setEmail(ssp.getEmailInput());
            model.setDate(ssp.getDateInput());
            model.setCourse(ssp.getSelectedCourse());

            smp.setCourseModules(course[ssp.getcboValue()].getModulesOnCourse());
            smp.initialiseCred();
            //congratulations!
            alertDialogBuilder(AlertType.CONFIRMATION, "Profile Created", null, "Your profile has successfully been created.");
            //change tab to module selection
            view.changeTab(1);
        }
    }

    //add and remove buttons for module selector
    public class t1AddHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Module selection = smp.getSelectiont1Add();
            if (selection != null) { //stops null entries from entering the selection boxes
                if (/*selection.getCredits() + */smp.gett1Cred() >= 60) { //stops excess modules
                    alertDialogBuilder(AlertType.ERROR, "Too many modules", null, "This exceeds your credit limit, please remove modules if you want to add more.");
                    return;
                }
                smp.incrementt1Cred(selection.getCredits());
                smp.addt1Module(selection);
            } else {
                alertDialogBuilder(AlertType.INFORMATION, "No option selected", null, "Please select an option to add.");
            }
        }

    }

    public class t1RemoveHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Module selection = smp.getSelectiont1Remove();
            if (selection != null) {
                if (selection.isMandatory()) {
                    alertDialogBuilder(AlertType.ERROR, "Mandatory Module", null, "This module is mandatory, it cannot be removed.");
                    return;
                }
                smp.decrementt1Cred(selection.getCredits());
                smp.rmt1Module(selection);
            } else {
                alertDialogBuilder(AlertType.INFORMATION, "No option selected", null, "Please select an option to remove.");
            }
        }
    }

    public class t2AddHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Module selection = smp.getSelectiont2Add();
            if (selection != null) {
                if (/*selection.getCredits() + */smp.gett2Cred() >= 60) {
                    alertDialogBuilder(AlertType.ERROR, "Too many modules", null, "This exceeds your credit limit, please remove modules if you want to add more.");
                    return;
                }
                smp.incrementt2Cred(selection.getCredits());
                smp.addt2Module(selection);
            } else {
                alertDialogBuilder(AlertType.INFORMATION, "No option selected", null, "Please select an option to add.");
            }
        }

    }

    public class t2RemoveHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Module selection = smp.getSelectiont2Remove();
            if (selection != null) {
                if (selection.isMandatory()) {
                    alertDialogBuilder(AlertType.ERROR, "Mandatory Module", null, "This module is mandatory, it cannot be removed.");
                    return;
                }
                smp.decrementt2Cred(selection.getCredits());
                smp.rmt2Module(selection);
            } else {
                alertDialogBuilder(AlertType.INFORMATION, "No option selected", null, "Please select an option to remove.");
            }
        }

    }

    //resets module selector pane to default based on student setup (specifically course choice)
    public class ResetHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            smp.setCourseModules(course[ssp.getcboValue()].getModulesOnCourse()); //reinitialise module lists
            smp.initialiseCred(); //reinitialise credits
        }
    }

    //submit model data saved from first two tabs to overview tab
    public class SubmitHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            if (smp.gett1Cred() + smp.gett2Cred() != 120) {
                alertDialogBuilder(AlertType.ERROR, "Not enough modules selected", null, "Please select 60 credits worth of modules from each term.");
                return;
            }
            ObservableList<Module> yrModules = smp.getyrSelMod();
            ObservableList<Module> t1Modules = smp.gett1SelMod();
            ObservableList<Module> t2Modules = smp.gett2SelMod();
            yrModules.forEach(m -> model.addSelectedModule(m));
            t1Modules.forEach(m -> model.addSelectedModule(m));
            t2Modules.forEach(m -> model.addSelectedModule(m));
            setProfileOverview();
            alertDialogBuilder(AlertType.CONFIRMATION, "Modules selected", null, "Modules have successfully been selected.");
            view.changeTab(2);
        }
    }

    //save profile overview as txt file
    public class SaveProfileHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Collection<Module> modules = model.getSelectedModules();
            File file = new File(model.getpNumber().toUpperCase() + ".txt");
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println("Name: " + model.getStudentName().getFullName());
                writer.println("PNum: " + model.getpNumber().toUpperCase());
                writer.println("Email: " + model.getEmail());
                writer.println("Date: " + model.getDate());
                writer.println("Course: " + model.getCourse());
                writer.println("Selected Modules: ");
                writer.println("================================");
                modules.forEach(m -> {
                    writer.println(m.toString());
                });
                writer.flush();
                writer.close();
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            alertDialogBuilder(AlertType.CONFIRMATION, "Success", null, "Your profile has been saved to " + model.getpNumber().toUpperCase() + ".txt! \n\n" +
                    "This file can be found in this program's root directory.");
        }
    }

    //save binary student data
    public class SaveMenuHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            try {
                if(ssp.getPNumInput().isEmpty()) {
                    alertDialogBuilder(AlertType.ERROR, "PNumber Required", null, "PNumber is required in order to save.");
                    return;
                }
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ssp.getPNumInput().toUpperCase() + "Obj.dat"));
                oos.writeObject(ssp);
                oos.flush();

                alertDialogBuilder(AlertType.CONFIRMATION, "Success", null, "Your data has been saved");
            }
            catch (IOException ioE) {
                ioE.printStackTrace();
            }
        }
    }

    //load binary student data
    public class LoadMenuHandler implements EventHandler<ActionEvent> {
        StudentSetupPane student;
        String pNum; //must be declared outside of handle to be final / effectively final
        public void handle(ActionEvent e) {
            TextInputDialog input = new TextInputDialog();
            input.setTitle("Enter PNumber");
            input.setContentText("Please enter a PNumber to load: ");
            Optional<String> inputPNum = input.showAndWait();
            inputPNum.ifPresent(p -> pNum = p); //checks the inputted PNumber data exists
            try {
                FileInputStream fis = new FileInputStream(pNum.toUpperCase() + "Obj.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);

                student = (StudentSetupPane) ois.readObject();

            }
            catch(IOException ioE) {
                alertDialogBuilder(AlertType.ERROR, "Error", null, "The requested ssp data was not found.");
            }
            catch (ClassNotFoundException c) {
                c.printStackTrace();
            }

            model.setpNumber(student.getTxtPNum());
            model.setStudentName(student.getTxtNames());
            model.setEmail(student.getTxtEmail());
            model.setDate(student.getDate());

        }
    }

    public void setProfileOverview() {
        TextArea profileArea = ovp.getProfile();
        Collection<Module> modules = model.getSelectedModules();
        profileArea.setText(
                "Name: " + model.getStudentName().getFullName() + "\n" +
                "PNum: " + model.getpNumber().toUpperCase() + "\n" +
                "Email: " + model.getEmail() + "\n" +
                "Date: " + model.getDate() + "\n" +
                "Course: " + model.getCourse() + "\n\n" +
                "Selected Modules: " + "\n" + "================================" + "\n"
        );
        modules.forEach(m -> {
            profileArea.appendText(m.toString() + "\n"); //utilises the toString method in module.java to return formatted module list.
        });
    }

    private Course[] setupAndGetCourses() {
        Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
        Module imat3451 = new Module("IMAT3451", "Computing Project", 30, true, Delivery.YEAR_LONG);
        Module ctec3902_SoftEng = new Module("CTEC3902", "Rigerous Systems", 15, true, Delivery.TERM_2);
        Module ctec3902_CompSci = new Module("CTEC3902", "Rigerous Systems", 15, false, Delivery.TERM_2);
        Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
        Module ctec3426 = new Module("CTEC3426", "Telematics", 15, false, Delivery.TERM_1);
        Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);
        Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);
        Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
        Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
        Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_1);
        Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_2);
        Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
        Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Delivery.TERM_1);
        Module imat3611 = new Module("IMAT3611", "Computing Ethics and Privacy", 15, false, Delivery.TERM_1);
        Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
        Module imat3614 = new Module("IMAT3614", "Big Data", 15, false, Delivery.TERM_2);
        Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Delivery.TERM_2);

        Course compSci = new Course("Computer Science");
        compSci.addModule(imat3423);
        compSci.addModule(imat3451);
        compSci.addModule(ctec3902_CompSci);
        compSci.addModule(ctec3110);
        compSci.addModule(ctec3426);
        compSci.addModule(ctec3605);
        compSci.addModule(ctec3606);
        compSci.addModule(ctec3410);
        compSci.addModule(ctec3904);
        compSci.addModule(ctec3905);
        compSci.addModule(ctec3906);
        compSci.addModule(imat3410);
        compSci.addModule(imat3406);
        compSci.addModule(imat3611);
        compSci.addModule(imat3613);
        compSci.addModule(imat3614);
        compSci.addModule(imat3428_CompSci);

        Course softEng = new Course("Software Engineering");
        softEng.addModule(imat3423);
        softEng.addModule(imat3451);
        softEng.addModule(ctec3902_SoftEng);
        softEng.addModule(ctec3110);
        softEng.addModule(ctec3426);
        softEng.addModule(ctec3605);
        softEng.addModule(ctec3606);
        softEng.addModule(ctec3410);
        softEng.addModule(ctec3904);
        softEng.addModule(ctec3905);
        softEng.addModule(ctec3906);
        softEng.addModule(imat3410);
        softEng.addModule(imat3406);
        softEng.addModule(imat3611);
        softEng.addModule(imat3613);
        softEng.addModule(imat3614);

        Course[] courses = new Course[2];
        courses[0] = compSci;
        courses[1] = softEng;

        return courses;
    }

    //dialog builder method for lambda event handlers
    private void alertDialogBuilder(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

