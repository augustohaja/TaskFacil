package br.edu.ifsp.taskfacil.tools;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class TFMessage {
	public static void showMessage(String title, String message){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	public static Optional<ButtonType> showErrorMessage(String message){
		 Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setHeaderText("Remoção de Tarefa");
		confirmationAlert.setContentText("Deseja realmente apagar a tarefa?");
		
		Optional<ButtonType> result = confirmationAlert.showAndWait();
		return result;
	}
	
}
