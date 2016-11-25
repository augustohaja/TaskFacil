package br.edu.ifsp.taskfacil.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.edu.ifsp.taskfacil.dao.UserDAO;
import br.edu.ifsp.taskfacil.models.User;
import br.edu.ifsp.taskfacil.tools.SHAUtil;
import br.edu.ifsp.taskfacil.tools.TFMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VBoxLoginController implements Initializable{
	@FXML
	private TextField textFieldUser;
	@FXML
	private PasswordField passwordFieldPassword;
	@FXML
	private TextField textFieldName;
	@FXML
	private Button buttonEntrar;
	@FXML
	private Button buttonCadastrar;
	@FXML
	private Label labelName;
	@FXML
	private Hyperlink hyperlinkNaoECadastrado;
	@FXML
	private Hyperlink hyperlinkLogin;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void handleButtonEntrar() throws IOException, NoSuchAlgorithmException{
		if (validateFieldsEntrar()){
			String usuario = textFieldUser.getText();
			String senha = SHAUtil.geraHash(passwordFieldPassword.getText());
			
			User user = new User();
			UserDAO dao = new UserDAO();
			ArrayList<User> list = new ArrayList<User>(); 
			
			if (!dao.searchByEmail(usuario).isEmpty()){
				list = (ArrayList<User>) dao.searchByEmail(usuario);
				user = list.get(0);
				if (user.getPassword().equals(senha)){
					loadVBoxApp();
				} else {
					TFMessage.showMessage(null,"Usuário/Senha inválido(s)!");
					clearFields();
				}
			} else {
				TFMessage.showMessage(null,"Usuário/Senha inválido(s)!");
				clearFields();
			}	
			handleHyperlinkLogin();	
		}
	}

	private void loadVBoxApp() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/br/edu/ifsp/taskfacil/views/VBoxApp.fxml"));
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setTitle("TaskFacil - Agenda de Tarefas");
		stage.show();
	}
	
	public Boolean validateFieldsEntrar(){
		Boolean retorno = true;
		ArrayList<String> problemas = new ArrayList<String>();
		
		if (textFieldUser.getText().isEmpty()){
			problemas.add("E-mail ");
		} else {
			if (!textFieldUser.getText().matches("\\w+@\\w+\\.\\w{2,3}(\\.\\w{2,3})?")){
				TFMessage.showMessage(null,"Digite um e-mail válido!");
				retorno = false;
			}
		}
		if (passwordFieldPassword.getText().isEmpty()){
			problemas.add("Password ");
		}
		
		if (!problemas.isEmpty()){
			String msg = "O(s) seguinte(s) campo(s), não podem ficar em branco: \n ";
			for (String s : problemas){
				msg += "- "+ s; 
			}
			retorno = problemas.isEmpty();
			TFMessage.showMessage(null,msg);
		}
		return retorno;
	}
	
	public void handleButtonCadastrar() throws IOException, NoSuchAlgorithmException{
		if (validateFieldsCadastrar()){
			String usuario = textFieldUser.getText();
			String senha = SHAUtil.geraHash(passwordFieldPassword.getText());
			String nome = textFieldName.getText();
			
			User user = new User(nome,usuario,senha);
			UserDAO dao = new UserDAO();
			
			if (dao.searchByEmail(usuario).isEmpty()){
				dao.insert(user);
				clearFields();
			} else {
				TFMessage.showMessage(null,"E-Mail já utilizado!");
			}	
			handleHyperlinkLogin();	
		}
	}
	
	public Boolean validateFieldsCadastrar(){
		Boolean retorno = true;
		ArrayList<String> problemas = new ArrayList<String>();
		
		if (textFieldUser.getText().isEmpty()){
			problemas.add("E-mail ");
		} else {
			if (!textFieldUser.getText().matches("\\w+@\\w+\\.\\w{2,3}(\\.\\w{2,3})?")){
				TFMessage.showMessage(null,"Digite um e-mail válido!");
				retorno = false;
			}
		}
		if (passwordFieldPassword.getText().isEmpty()){
			problemas.add("Password ");
		}
		if (textFieldName.getText().isEmpty()){
			problemas.add("Nome ");	
		}
		
		if (!problemas.isEmpty()){
			String msg = "O(s) seguinte(s) campo(s), não podem ficar em branco: \n ";
			for (String s : problemas){
				msg += "- "+ s; 
			}
			retorno = problemas.isEmpty();
			TFMessage.showMessage(null,msg);
		}
		return retorno;
	}
	
	public void clearFields(){
		textFieldUser.setText("");
		passwordFieldPassword.setText("");
		textFieldName.setText("");
	}
	
	public void handleHyperlinkNaoECadastrado(){
		buttonEntrar.setVisible(false);
		buttonCadastrar.setVisible(true);
		labelName.setVisible(true);
		textFieldName.setVisible(true);
		hyperlinkNaoECadastrado.setVisible(false);
		hyperlinkLogin.setVisible(true);
	}
	
	public void handleHyperlinkLogin(){
		buttonEntrar.setVisible(true);
		buttonCadastrar.setVisible(false);
		labelName.setVisible(false);
		textFieldName.setVisible(false);
		hyperlinkNaoECadastrado.setVisible(true);
		hyperlinkLogin.setVisible(false);
	}
}
