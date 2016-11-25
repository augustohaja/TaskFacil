package br.edu.ifsp.taskfacil.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifsp.taskfacil.models.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AnchorPaneCadastroTasksDialogController implements Initializable {
	@FXML
	private Label labelId;
	@FXML
	private TextField textFieldTitulo;
	@FXML
	private TextField textFieldDescricao;
	@FXML
	private TextField textFieldLocal;
	@FXML
	private TextField textFieldData;
	@FXML
	private Button buttonConfirmar;
	@FXML
	private Button buttonCancelar;
	
	
	// servirá para abrir uma outra janela/pop up simultaneamente em nossa aplicação
	private Stage dialogStage;
	private boolean buttonConfirmarClicked = false;
	private Task task;
	
	
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

	public void setTask(Task task) {
		this.task = task;
		
		if (String.valueOf(task.getId())!=null)
				this.labelId.setText(String.valueOf(task.getId()));
		this.textFieldTitulo.setText(task.getTitulo());
		this.textFieldDescricao.setText(task.getDescricao());
		this.textFieldLocal.setText(task.getLocal());
		this.textFieldData.setText(task.getData());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub	
	}
	
	@FXML
	public void handleButtonConfirmar() {
		this.buttonConfirmarClicked = true;
		
		this.task.setTitulo(this.textFieldTitulo.getText());
		this.task.setDescricao(this.textFieldDescricao.getText());
		this.task.setLocal(this.textFieldLocal.getText());
		this.task.setData(this.textFieldData.getText());
		
		this.dialogStage.close();
	}
	
	
	@FXML
	public void handleButtonCancelar() {
		this.buttonConfirmarClicked = false;
		this.dialogStage.close();
	}

}
