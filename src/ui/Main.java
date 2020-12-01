package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Kingdom;

public class Main extends Application{
	
	private Kingdom king;
	private KingdomGUI kingGUI;
	
	
	public Main() {
		king= new Kingdom();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		kingGUI = new KingdomGUI(stage, king);
	
		FXMLLoader f = new FXMLLoader(getClass().getResource("interfaz.fxml"));
		f.setController(kingGUI);
		Parent root = f.load();
		Scene sc = new Scene(root);
		stage.setScene(sc);
		stage.sizeToScene();
		sc.getStylesheets().add(getClass().getResource("iconos.css").toExternalForm());
		stage.setScene(sc);
		stage.centerOnScreen();
		stage.setResizable(true);
		stage.setTitle("EL LADRON DE BANCOS");
		stage.show();
	}

}
