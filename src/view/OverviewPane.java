package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class OverviewPane extends BorderPane {

    private TextArea profile;
    private Button saveBtn;

    public OverviewPane() {
        profile = new TextArea();
        profile.setEditable(false);
        this.setPadding(new Insets(40));

        saveBtn = new Button("Save Profile");
        BorderPane.setMargin(profile, new Insets(20));
        BorderPane.setAlignment(saveBtn, Pos.CENTER);

        this.setCenter(profile);
        this.setBottom(saveBtn);
    }

    public void addSaveProfileHandler(EventHandler<ActionEvent> handler) {
        saveBtn.setOnAction(handler);
    }

    public TextArea getProfile() {
        return profile;
    }
}
