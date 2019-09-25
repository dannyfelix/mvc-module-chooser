package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

//You may change this class to extend another type if you wish
public class ModuleChooserRootPane extends BorderPane {

    private StudentSetupPane ssp;
    private SelectModulesPane smp;
    private OverviewPane ovp;
    private MyMenuBar mmb;
    private TabPane tp;

    public ModuleChooserRootPane() {
        //initialisation
        tp  = new TabPane();
        ssp = new StudentSetupPane();
        smp = new SelectModulesPane();
        ovp = new OverviewPane();
        mmb = new MyMenuBar();

        tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //disallow tabs from being closed

        //create tabs
        Tab t1 = new Tab("Create Profile", ssp);
        Tab t2 = new Tab("Select Modules", smp);
        Tab t3 = new Tab("Overview Selection", ovp);
        //& add to tab pane
        tp.getTabs().addAll(t1, t2, t3);

        this.setTop(mmb);
        this.setCenter(tp);

    }

    public StudentSetupPane getStudentSetupPane() {
        return ssp;
    }

    public SelectModulesPane getSelectModulesPane() {
        return smp;
    }

    public OverviewPane getOverviewPane() {
        return ovp;
    }

    public MyMenuBar getMenuBar() {
        return mmb;
    }

    //method to change tabs
    public void changeTab(int index) {
        tp.getSelectionModel().select(index);
    }
}
