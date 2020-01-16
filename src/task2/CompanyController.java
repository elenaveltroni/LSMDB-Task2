package task2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import task2.DocumentController;

public class CompanyController implements Initializable {
	private User current_company;
	
	@FXML private Label username_field;
	@FXML private Label company_field;
	@FXML private Label address_field;
	@FXML private Label country_field;
	@FXML private Label email_field;
	@FXML private Label number_field;
	@FXML private Label business_field;
	@FXML private Button logout_button;
	@FXML private Button modify_button;
	@FXML private Button delete_button;

	
	public void initCompany(User u) {
		this.current_company = new User(u);
		showCompanyInformation(this.current_company);
	}
	
	public void fileChooser() {
		Stage fileChooserStage = new Stage();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
		    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
		);
		
		File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
		if(selectedFile!= null) {
			//inserimento in mongodb
			System.out.println(selectedFile.getName());
		}
		
	}
		
	public void showCompanyInformation(User u) {
		username_field.setText(current_company.getUsername());
		company_field.setText(current_company.getCompanyName());
		address_field.setText(current_company.getAddress());
		country_field.setText(current_company.getCountry());
		email_field.setText(current_company.getEmail());
		number_field.setText(current_company.getNumber());
		business_field.setText(current_company.getCoreBusiness());
	}
	
	public void logout(ActionEvent event) throws IOException {
		//closing the window
    	Stage stage = (Stage) logout_button.getScene().getWindow();
    	stage.close();
          
	}
	
	public void modify() {
		//closing the window
    	Stage stage = (Stage) modify_button.getScene().getWindow();
    	stage.close();
    	
		//opening a new window with a new controller
        Stage dialogStage = new Stage();
        Scene scene;
        
        String resource = "ChangesFXML.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(resource));
        
        Parent root;
		try {
			root = (Parent) loader.load();
			scene = new Scene(root);
	        dialogStage.setTitle("Update your information");
	        dialogStage.initModality(Modality.APPLICATION_MODAL);
	        dialogStage.setScene(scene);
	        dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
        		
       ChangesController controller = loader.getController();
       controller.initUser(current_company);
	}
	
	public void deleteAccount() {
		Alert windowAlert = new Alert(AlertType.CONFIRMATION);
		windowAlert.setHeaderText(null);
		windowAlert.setContentText("Are you sure you want to cancel your account?");
		windowAlert.setTitle("Are you sure?");
    	
		Optional<ButtonType> result = windowAlert.showAndWait();
		if (result.get() == ButtonType.OK){
		    MongoHandler.deleteAccountByUsername(current_company.getUsername());
			
			//closing the window
	    	Stage stage = (Stage) delete_button.getScene().getWindow();
	    	stage.close();
		} else {
		    return;
		}
	}	

	@Override
	 public void initialize(URL url, ResourceBundle rb) {
		
	}
}
