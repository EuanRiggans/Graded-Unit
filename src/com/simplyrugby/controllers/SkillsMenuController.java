package com.simplyrugby.controllers;

import com.simplyrugby.exceptions.PlayerNotFoundException;
import com.simplyrugby.models.Model;
import com.simplyrugby.objects.ComboBoxItem;
import com.simplyrugby.objects.SkillCategory;
import com.simplyrugby.utils.Search;
import com.simplyrugby.utils.SimpleAlerts;
import com.simplyrugby.utils.UserDocs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

/**
 *  Controller for the skills menu view
 *
 * @author Euan Riggans
 */
public class SkillsMenuController {

    /**
     * The model
     */
    private Model model;

    /**
     * Combo box that stores the skill categories
     */
    @FXML
    private ComboBox<ComboBoxItem> cmbSkillCategories;
    /**
     * The player id of the player who's skill categories are being displayed
     */
    private int playerID;

    /**
     * Sets the current model
     *
     * @param model The model
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Initialising the view and adding the players skill categories to the combo box
     *
     * @param playerID Player id of the player who's skills are being displayed
     */
    @FXML
    public void init(int playerID) {
        this.playerID = playerID;
        try {
            for (SkillCategory skillCategory : Search.getPlayerFromID(playerID).getSkills()) {
                ComboBoxItem comboBoxItem = new ComboBoxItem(skillCategory.getCategory(), 0);
                cmbSkillCategories.getItems().add(comboBoxItem);
            }
        } catch (PlayerNotFoundException e) {
            SimpleAlerts.exceptionAlert("Unexpected error occurred when fetch skill details for that player", e);
        }
    }

    /**
     * Displays the skill details of the selected skill category
     *
     * @param event Calling event
     */
    @FXML
    private void btnViewDetailsClickHandler(javafx.event.ActionEvent event) {
        if (cmbSkillCategories.getSelectionModel().isEmpty()) {
            SimpleAlerts.simpleAlert(Alert.AlertType.INFORMATION, "No skill category selected", "You need to select a skill category").showAndWait();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/SkillDetails.fxml"));
            Parent root;
            root = fxmlLoader.load();
            SkillDetailsController controller = fxmlLoader.getController();
            controller.setModel(this.model);
            controller.init(cmbSkillCategories.getValue().getItemText(), playerID);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Skills Details - " + cmbSkillCategories.getValue().getItemText());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            File tempIconLocation = new File("resources/sru_logo.png");
            Image icon = new Image("file:\\" + tempIconLocation.getCanonicalPath());
            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException e) {
            SimpleAlerts.exceptionAlert("Unexpected error occurred when displaying skill details for that skill", e).showAndWait();
        }
    }

    /**
     * Displays the notes for the selected skill category
     *
     * @param event Calling event
     */
    @FXML
    private void btnViewNotesClickHandler(javafx.event.ActionEvent event) {
        if (cmbSkillCategories.getSelectionModel().isEmpty()) {
            SimpleAlerts.simpleAlert(Alert.AlertType.INFORMATION, "No skill category selected", "You need to select a skill category").showAndWait();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/Notes.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            NotesController controller = fxmlLoader.getController();
            controller.setModel(this.model);
            controller.init(playerID, cmbSkillCategories.getValue().getItemText());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            try {
                stage.setTitle("Notes for - " + Search.getPlayerFromID(playerID).getFullName() + " - " + cmbSkillCategories.getValue().getItemText());
            } catch (PlayerNotFoundException e) {
                SimpleAlerts.exceptionAlert("Unexpected error occurred while updating page title", e);
            }
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            File tempIconLocation = new File("resources/sru_logo.png");
            Image icon = new Image("file:\\" + tempIconLocation.getCanonicalPath());
            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException e) {
            SimpleAlerts.exceptionAlert("Unexpected error occurred while displaying notes for that skill", e).showAndWait();
        }
    }

    /**
     * Opens the user docs upon the user clicking the hyperlink
     */
    @FXML
    private void openUserDocs() {
        UserDocs.openUserDocs();
    }
}
