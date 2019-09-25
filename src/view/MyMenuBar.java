package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

public class MyMenuBar extends MenuBar {

    //menu bar item declaration
    private MenuItem loadItem, saveItem, exitItem, aboutItem;

    public MyMenuBar() {

        Menu menu;

        //first menu
        menu = new Menu("_File");

        //define menu items
        loadItem = new MenuItem("_Load Student Profile");
        loadItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));

        saveItem = new MenuItem("_Save Student Profile");
        saveItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));

        exitItem = new MenuItem("Ex_it");
        exitItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X"));

        //add all items to File menu and then add that to the menu bar
        menu.getItems().addAll(loadItem, saveItem, new SeparatorMenuItem(), exitItem);
        this.getMenus().add(menu);

        //second menu
        menu = new Menu("_Help");

        //define About menu item
        aboutItem = new MenuItem("_About");
        aboutItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));

        //add about to menu and then add that to menu bar
        menu.getItems().add(aboutItem);
        this.getMenus().add(menu);
    }

    //allow handlers to be externally attached by the controller
    public void addLoadMenuHandler(EventHandler<ActionEvent> handler) {
        loadItem.setOnAction(handler);
    }

    public void addSaveMenuHandler(EventHandler<ActionEvent> handler) {
        saveItem.setOnAction(handler);
    }

    public void addExitMenuHandler(EventHandler<ActionEvent> handler) {
        exitItem.setOnAction(handler);
    }

    public void addAboutMenuHandler(EventHandler<ActionEvent> handler) {
        aboutItem.setOnAction(handler);
    }
}
