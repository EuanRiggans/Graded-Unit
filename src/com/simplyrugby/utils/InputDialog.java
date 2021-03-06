package com.simplyrugby.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 * InputDialog utility class to provide easy input dialogs
 *
 * @author Euan Riggans
 */
public final class InputDialog {

    /**
     * Creates a TextInputDialog which can be easily displayed to the user to gather input
     * <pre>
     *     {@code
     *      Optional<String> userInput = InputDialog.textInput("Please enter a string", "Please enter a random string", false, false).showAndWait();
     *     }
     * </pre>
     *
     * @param title        The title of the input dialog
     * @param textBody     The main body of text on the input dialog
     * @param resizable    Will the input dialog be resizable
     * @param exitOnCancel If the user clicks the cancel button, should the program exit
     * @return An pre-made TextInputDialog ready to be shown and gather user input
     */
    public static TextInputDialog textInput(String title, String textBody, boolean resizable, boolean exitOnCancel) {
        TextInputDialog textInputDialog = new TextInputDialog();
        if (exitOnCancel) {
            Button cancel = (Button) textInputDialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.addEventFilter(ActionEvent.ACTION, event ->
                    System.exit(0)
            );
        }
        textInputDialog.setResizable(resizable);
        textInputDialog.setHeaderText(textBody);
        textInputDialog.setTitle(title);
        return textInputDialog;
    }

    /**
     * Creates a confirmation alert which can be easily displayed to the user to gather input
     * <pre>
     *     {@code
     *      Optional<ButtonType> confirmation = InputDialog.confirmationDialog("Are you sure you wish to delete the following note: " + currentlySelectedNote.toLowerCase() + "?", false).showAndWait();
     *     }
     * </pre>
     *
     * @param textBody  The main body of text on the input dialog
     * @param resizable Will the input dialog be resizable
     * @return An pre-made confirmation alert ready to be shown and gather user input
     */
    public static Alert confirmationDialog(String textBody, boolean resizable) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setContentText(textBody);
        confirmation.setResizable(resizable);
        confirmation.setTitle("Delete Note");
        return confirmation;
    }

}
