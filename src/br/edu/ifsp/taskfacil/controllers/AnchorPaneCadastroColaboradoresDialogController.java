package br.edu.ifsp.taskfacil.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifsp.taskfacil.models.Task;
import br.edu.ifsp.taskfacil.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AnchorPaneCadastroColaboradoresDialogController implements Initializable{
	@FXML
	private TextField textFieldIdColaborador;
	@FXML
	private Label labelNomeColaborador;
	@FXML
	private Button buttonConfirmarColaborador;
	@FXML
	private Button buttonCancelarColaborador;
	
	
	// servirá para abrir uma outra janela/pop up simultaneamente em nossa aplicação
	private Stage dialogStage;
	private boolean buttonConfirmarClicked = false;
	private Task task;
	private User user;
	
	
	
	
	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isButtonConfirmarClicked() {
		return buttonConfirmarClicked;
	}

	public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
		this.buttonConfirmarClicked = buttonConfirmarClicked;
	}

	public Task getTask() {
		return task;
	}

	public void setColaborador(User user) {
		this.user = user;
		
		this.textFieldIdColaborador.setText(String.valueOf(user.getId()));
		this.labelNomeColaborador.setText(user.getName());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	@FXML
	public void handleButtonConfirmar() {
		this.buttonConfirmarClicked = true;
		this.user.setId(Long.parseLong(this.textFieldIdColaborador.getText()));
		this.dialogStage.close();
	}
	
	@FXML
	public void handleButtonCancelar() {
		this.buttonConfirmarClicked = false;
		this.dialogStage.close();
	}

}
