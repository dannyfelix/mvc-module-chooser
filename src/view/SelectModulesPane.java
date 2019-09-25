package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Delivery;
import model.Module;

import java.util.Collection;


public class SelectModulesPane extends BorderPane {

    private Button t1addBtn, t1rmBtn, t2addBtn, t2rmBtn, rstBtn, sbtBtn;
    private TextField txtt1Cred, txtt2Cred;
    private ListView<Module> t1UnSel, t2UnSel, t1Sel, t2Sel, yrSel;
    private int t1Cred, t2Cred;

    public SelectModulesPane() {

        HBox root = new HBox();
        this.setPadding(new Insets(20));

        //declaration
        Label lblt1UnSel = new Label("Unselected Term 1 Modules");
        Label lblt2UnSel = new Label("Unselected Term 2 Modules");
        Label lblt1Sel   = new Label("Selected Term 1 Modules");
        Label lblt2Sel   = new Label("Selected Term 2 Modules");
        Label lblyrSel   = new Label("Selected Year Long Modules");
        Label lblt1Btns  = new Label("Term 1:");
        Label lblt2Btns  = new Label("Term 2:");
        Label lblt1Cred  = new Label("Current Term 1 Credits:");
        Label lblt2Cred  = new Label("Current Term 2 Credits:");

        t1UnSel   = new ListView<>();
        t2UnSel   = new ListView<>();
        t1Sel     = new ListView<>();
        t2Sel     = new ListView<>();
        yrSel     = new ListView<>();
        t1addBtn  = new Button("Add");
        t1rmBtn   = new Button("Remove");
        t2addBtn  = new Button("Add");
        t2rmBtn   = new Button("Remove");
        rstBtn    = new Button("Reset");
        sbtBtn    = new Button("Submit");
        txtt1Cred = new TextField("0");
        txtt2Cred = new TextField("0");

        ObservableList<Module> t1UnSelMod = FXCollections.observableArrayList();
        ObservableList<Module> t2UnSelMod = FXCollections.observableArrayList();
        ObservableList<Module> t1SelMod = FXCollections.observableArrayList();
        ObservableList<Module> t2SelMod = FXCollections.observableArrayList();
        ObservableList<Module> yrSelMod = FXCollections.observableArrayList();

        txtt1Cred.setEditable(false);
        txtt2Cred.setEditable(false);

        t1UnSel.setItems(t1UnSelMod);
        t2UnSel.setItems(t2UnSelMod);
        t1Sel.setItems(t1SelMod);
        t2Sel.setItems(t2SelMod);
        yrSel.setItems(yrSelMod);

        t1UnSel.setItems(t1UnSelMod);
        t2UnSel.setItems(t2UnSelMod);
        t1Sel.setItems(t1SelMod);
        t2Sel.setItems(t2SelMod);
        yrSel.setItems(yrSelMod);

        //styling for controls
        t1UnSel.setPrefSize(350, 100);
        t2UnSel.setPrefSize(350, 100);
        t1Sel.setPrefSize(350, 100);
        t2Sel.setPrefSize(350, 100);
        yrSel.setPrefSize(350, 50);
        txtt1Cred.setMaxWidth(50);
        txtt2Cred.setMaxWidth(50);
        t1addBtn.setPrefWidth(70);
        t1rmBtn.setPrefWidth(70);
        t2addBtn.setPrefWidth(70);
        t2rmBtn.setPrefWidth(70);
        rstBtn.setPrefWidth(70);
        sbtBtn.setPrefWidth(70);

        //boxes for label + control
        VBox vboxt1Unsel = new VBox();
        VBox vboxt2Unsel = new VBox();
        VBox vboxt1Sel   = new VBox();
        VBox vboxt2Sel   = new VBox();
        VBox vboxyrSel   = new VBox();
        HBox hboxt1Btns  = new HBox();
        HBox hboxt2Btns  = new HBox();
        HBox hboxt1Cred  = new HBox();
        HBox hboxt2Cred  = new HBox();
        HBox hboxbtns    = new HBox();

        //box styling
        hboxt1Btns.setSpacing(20);
        hboxt2Btns.setSpacing(20);
        hboxt1Cred.setSpacing(20);
        hboxt2Cred.setSpacing(20);
        hboxbtns.setSpacing(20);

        //adding label + control to boxes
        vboxt1Unsel.getChildren().addAll(lblt1UnSel, t1UnSel);
        vboxt2Unsel.getChildren().addAll(lblt2UnSel, t2UnSel);
        vboxt1Sel.getChildren().addAll(lblt1Sel, t1Sel);
        vboxt2Sel.getChildren().addAll(lblt2Sel, t2Sel);
        vboxyrSel.getChildren().addAll(lblyrSel, yrSel);
        hboxt1Btns.getChildren().addAll(lblt1Btns, t1addBtn, t1rmBtn);
        hboxt2Btns.getChildren().addAll(lblt2Btns, t2addBtn, t2rmBtn);
        hboxt1Cred.getChildren().addAll(lblt1Cred, txtt1Cred);
        hboxt2Cred.getChildren().addAll(lblt2Cred,txtt2Cred);
        hboxbtns.getChildren().addAll(rstBtn, sbtBtn);

        //more styling
        hboxt1Btns.setAlignment(Pos.CENTER);
        hboxt2Btns.setAlignment(Pos.CENTER);
        hboxt1Cred.setAlignment(Pos.CENTER);
        hboxt2Cred.setAlignment(Pos.CENTER);
        hboxbtns.setAlignment(Pos.CENTER);

        //setVgrow allows the UI to resize with the window
        //setVgrow for listViews
        VBox.setVgrow(t1UnSel, Priority.ALWAYS);
        VBox.setVgrow(t2UnSel, Priority.ALWAYS);
        VBox.setVgrow(t1Sel, Priority.ALWAYS);
        VBox.setVgrow(t2Sel, Priority.ALWAYS);
        VBox.setVgrow(yrSel, Priority.ALWAYS);
        //setVgrow for containers
        VBox.setVgrow(vboxt1Unsel, Priority.ALWAYS);
        VBox.setVgrow(vboxt2Unsel, Priority.ALWAYS);
        VBox.setVgrow(vboxt1Sel, Priority.ALWAYS);
        VBox.setVgrow(vboxt2Sel, Priority.ALWAYS);
        VBox.setVgrow(vboxyrSel, Priority.ALWAYS);

        //split the controls into two separate columns
        VBox lColumn = new VBox(vboxt1Unsel, hboxt1Btns, vboxt2Unsel, hboxt2Btns, hboxt1Cred);
        lColumn.setSpacing(15);
        HBox.setHgrow(lColumn, Priority.ALWAYS);

        VBox rColumn = new VBox(vboxyrSel, vboxt1Sel, vboxt2Sel, hboxt2Cred);
        rColumn.setSpacing(15);
        HBox.setHgrow(rColumn, Priority.ALWAYS);

        root.getChildren().addAll(lColumn, rColumn);
        root.setSpacing(15);
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // allows it to expand to full window size, as the max size is the biggest number a double can be

        BorderPane.setAlignment(root, Pos.CENTER);
        this.setCenter(new StackPane(root));
        this.setBottom(hboxbtns);
    }

    public void setCourseModules(Collection<Module> modules) {

        t1UnSel.getItems().clear();
        t2UnSel.getItems().clear();
        t1Sel.getItems().clear();
        t2Sel.getItems().clear();
        yrSel.getItems().clear();

        //forEach lambda to add the modules to their correct list
        modules.forEach(m -> {
            if (m.getRunPlan().equals(Delivery.TERM_1)) {
                if (m.isMandatory()) {
                    t1Sel.getItems().add(m);
                } else {
                    t1UnSel.getItems().add(m);
                }
            } else if (m.getRunPlan().equals(Delivery.TERM_2)) {
                if (m.isMandatory()) {
                    t2Sel.getItems().add(m);
                } else {
                    t2UnSel.getItems().add(m);
                }
            } else {
                yrSel.getItems().add(m);
            }
        });
    }

    //add module methods for button handlers
    public void addt1Module(Module m) {
        t1UnSel.getItems().remove(m);
        t1Sel.getItems().add(m);
    }

    public void addt2Module(Module m) {
        t2UnSel.getItems().remove(m);
        t2Sel.getItems().add(m);
    }

    public void rmt1Module(Module m) {
        t1Sel.getItems().remove(m);
        t1UnSel.getItems().add(m);
    }

    public void rmt2Module(Module m) {
        t2Sel.getItems().remove(m);
        t2UnSel.getItems().add(m);
    }

    //methods to get chosen item from each list
    public Module getSelectiont1Add() {
        return t1UnSel.getSelectionModel().getSelectedItem();
    }

    public Module getSelectiont1Remove() {
        return t1Sel.getSelectionModel().getSelectedItem();
    }

    public Module getSelectiont2Add() {
        return t2UnSel.getSelectionModel().getSelectedItem();
    }

    public Module getSelectiont2Remove() {
        return t2Sel.getSelectionModel().getSelectedItem();
    }

    //add handler methods
    public void addResetHandler(EventHandler<ActionEvent> handler) {
        rstBtn.setOnAction(handler);
    }

    public void addSubmitHandler(EventHandler<ActionEvent> handler) {
        sbtBtn.setOnAction(handler);
    }

    public void addt1AddHandler(EventHandler<ActionEvent> handler) {
        t1addBtn.setOnAction(handler);
    }

    public void addt1RemoveHandler(EventHandler<ActionEvent> handler) {
        t1rmBtn.setOnAction(handler);
    }

    public void addt2AddHandler(EventHandler<ActionEvent> handler) {
        t2addBtn.setOnAction(handler);
    }

    public void addt2RemoveHandler(EventHandler<ActionEvent> handler) {
        t2rmBtn.setOnAction(handler);
    }

    //TODO -- set modules from load (?)

    public void initialiseCred() {
        t1Cred = 0;
        t2Cred = 0;
        t1Sel.getItems().forEach(m -> t1Cred += m.getCredits());
        t2Sel.getItems().forEach(m -> t2Cred += m.getCredits());
        yrSel.getItems().forEach(m -> t1Cred += (m.getCredits() / 2));
        yrSel.getItems().forEach(m -> t2Cred += (m.getCredits() / 2)); //adds yr long credits split to both terms
        txtt1Cred.setText("" + t1Cred);
        txtt2Cred.setText("" + t2Cred);
    }

    public int gett1Cred() {
        return t1Cred;
    }

    public int gett2Cred() {
        return t2Cred;
    }

    //increment or decrement Credit by getting credit from module as i
    public void incrementt1Cred(int i) {
        t1Cred += i;
        txtt1Cred.setText("" + t1Cred);
    }

    public void decrementt1Cred(int i) {
        t1Cred -= i;
        txtt1Cred.setText("" + t1Cred);
    }

    public void incrementt2Cred(int i) {
        t2Cred += i;
        txtt2Cred.setText("" + t2Cred);
    }

    public void decrementt2Cred(int i) {
        t2Cred -= i;
        txtt2Cred.setText("" + t2Cred);
    }

    public ObservableList<Module> getyrSelMod() {
        return yrSel.getItems();
    }

    public ObservableList<Module> gett1SelMod() {
        return t1Sel.getItems();
    }

    public ObservableList<Module> gett2SelMod() {
        return t2Sel.getItems();
    }

}
