package br.edu.ifsp.taskfacil.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.edu.ifsp.taskfacil.dao.TaskDAO;
import br.edu.ifsp.taskfacil.models.Task;
import br.edu.ifsp.taskfacil.models.User;
import br.edu.ifsp.taskfacil.tools.TFMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VBoxAppController implements Initializable{
	@FXML
	private TableView<Task> tableViewTasks;
	@FXML
	private TableColumn<Task,Long> tableColumnId;
	@FXML
	private TableColumn<Task,String> tableColumnTitulo;
	@FXML
	private TableColumn<Task,String> tableColumnDescricao;
	@FXML
	private TableColumn<Task,String> tableColumnLocal;
	@FXML
	private TableColumn<Task,String> tableColumnData;
	@FXML
	private TableView<User> tableViewColaboradores;
	@FXML
	private TableColumn<User,Long> tableColumnIdColaborador;
	@FXML
	private TableColumn<User,String> tableColumnNameColaborador;
	@FXML
	private Button buttonIncluir;
	@FXML
	private Button buttonEditar;
	@FXML
	private Button buttonExcluir;
	@FXML
	private Button buttonIncluirColaborador;
	@FXML
	private Button buttonExcluirColaborador;

	private List<Task> taskList;
	private ObservableList<Task> taskObservableList;
	private TaskDAO dao;
	private Task taskSel;
	
	private List<User> colaboradoresList;
	private ObservableList<User> colaboradoresObservableList;
	
	private User userLogado;
	
	public void setUserLogado(User user){
		this.userLogado = user;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Inicializando...");
		this.dao = new TaskDAO();
		loadTableViewTasks();
		
		// Listen acionado diante de quaisquer alteraÃ§Ãµes na seleÃ§Ã£o de itens do TableView
        this.tableViewTasks.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemTableViewTasks(newValue));
 	}
	
	private void loadTableViewTasks() {
		this.taskList = this.dao.all();
		
		// a string Ã© o nome do atributo da classe do objeto
		this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		this.tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		this.tableColumnLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
		this.tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
		
		// conversÃ£o de ArrayList para ObservableList
		this.taskObservableList = FXCollections.observableArrayList(this.taskList);
		this.tableViewTasks.setItems(this.taskObservableList);
	}
	
	private void loadTableViewColaboradores() {
		this.colaboradoresList = this.taskSel.getLista();
		
		this.tableColumnIdColaborador.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tableColumnNameColaborador.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		// conversÃ£o de ArrayList para ObservableList
		this.colaboradoresObservableList = FXCollections.observableArrayList(this.colaboradoresList);
		this.tableViewColaboradores.setItems(this.colaboradoresObservableList);
	}
	
	public void selectItemTableViewTasks(Task task) {
		System.out.println(task);
		this.taskSel = task;
		loadTableViewColaboradores();
	}
	
	@FXML
	public void handleButtonIncluir() throws IOException {
		Task task = new Task();
		boolean buttonConfirmarClicked = showAnchorPaneCadastroTasksDialog(task);
		
		if (buttonConfirmarClicked) {
			this.dao.insert(task);
			loadTableViewTasks();
		}
	}
	
	@FXML
	public void handleButtonEditar() throws IOException {
		Task task = this.tableViewTasks.getSelectionModel().getSelectedItem();
		
		if (task == null) {
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setContentText("Por favor, escolha uma tarefa na Tabela!");
			errorAlert.show();
		}
		else {
			boolean buttonConfirmarClicked = showAnchorPaneCadastroTasksDialog(task);
			
			if (buttonConfirmarClicked) {
				this.dao.update(task);
				loadTableViewTasks();
			}
		}
	}

	
	@FXML
	public void handleButtonExcluir() {
		Task task = this.tableViewTasks.getSelectionModel().getSelectedItem();
		
		if (task == null) {
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setContentText("Por favor, escolha uma tarefa na Tabela!");
			errorAlert.show();
		}
		else {
			Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
			confirmationAlert.setHeaderText("Remoção de Tarefa");
			confirmationAlert.setContentText("Deseja realmente apagar a tarefa?");
			
			Optional<ButtonType> result = confirmationAlert.showAndWait();
			if (result.get() == ButtonType.OK) {
				this.dao.delete(task);
				loadTableViewTasks();
			}
		}
	}

	public boolean showAnchorPaneCadastroTasksDialog(Task task) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AnchorPaneCadastroTasksDialogController.class.getResource("/br/edu/ifsp/taskfacil/views/AnchorPaneCadastroTasksDialog.fxml"));
		AnchorPane page = (AnchorPane) loader.load();
		
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Cadastro de Tarefas");
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		
		AnchorPaneCadastroTasksDialogController controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setTask(task);
		
		dialogStage.showAndWait();
		
		return controller.isButtonConfirmarClicked();
	}

	@FXML
	public void handleButtonIncluirColaborador() throws IOException {
		Task task = this.tableViewTasks.getSelectionModel().getSelectedItem();
		
		if (task == null) {
			TFMessage.showMessage("Atenção!", "Por favor, escolha uma tarefa na Tabela!");
		}
		else {
			User user = new User();
			boolean buttonConfirmarClicked = showAnchorPaneCadastroColaboradoresDialog(user);
			
			if (buttonConfirmarClicked) {
				task.setColaborador(user);
				this.dao.update(task);
				loadTableViewColaboradores();
			}
		}
	}
	
	@FXML
	public void handleButtonExcluirColaborador() throws IOException {
		Task task = this.tableViewTasks.getSelectionModel().getSelectedItem();
		User user = this.tableViewColaboradores.getSelectionModel().getSelectedItem();
		
		
		if (user == null) {
			TFMessage.showMessage("Atenção!", "Por favor, escolha um colaborador na Tabela!");
		}
		else {
			Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
			confirmationAlert.setHeaderText("Remoção de Colaborador");
			confirmationAlert.setContentText("Deseja realmente apagar o colaborador?");
			
			Optional<ButtonType> result = confirmationAlert.showAndWait();
			
			if (result.get() == ButtonType.OK) {
				//this.dao.insert(task);
				task.removeColaborador(user);
				//task.setColaborador(user);
				this.dao.update(task);
				loadTableViewColaboradores();
			}
		}
	}
	
	public boolean showAnchorPaneCadastroColaboradoresDialog(User user) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AnchorPaneCadastroTasksDialogController.class.getResource("/br/edu/ifsp/taskfacil/views/AnchorPaneCadastroColaboradoresDialog.fxml"));
		AnchorPane page = (AnchorPane) loader.load();
		
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Adição de Colaboradores");
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		
		AnchorPaneCadastroColaboradoresDialogController controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setColaborador(user);
		
		dialogStage.showAndWait();
		
		return controller.isButtonConfirmarClicked();
	}

}
