package application;

import java.io.File;
import java.sql.Connection;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class Main extends Application {

	protected Button preGenbtn, fileUpdatebtn, resetbtn, processOrderbtn;
	protected ComboBox<String> selectLog, selectEcon;
	protected Connection con; //connection variable for sql
	protected File logFiles, econFiles;
	protected File[] logList, econList;
	protected Saw saw = new Saw();

	protected boolean spam = true;

	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = buildMaster();
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private Pane buildMaster()
	{
		VBox master = new VBox();
		master.setPadding(new Insets(20, 20, 20, 20));
		Pane top = buildTop();
		top.setPadding(new Insets(20, 20, 20, 20));
		Pane bottom = buildBottom();
		bottom.setPadding(new Insets(20, 20, 20, 20));
		master.getChildren().addAll(top, bottom);

		return master;
	}

	private Pane buildTop()
	{
		GridPane top = new GridPane();
		top.setPadding(new Insets(20, 20, 20, 20));
		top.setVgap(30);
		top.setHgap(8);

		selectLog = new ComboBox<String>();
		selectLog.getItems().add("--- Select Logs File ---");
		selectLog.getSelectionModel().selectFirst();
		selectEcon = new ComboBox<String>();
		selectEcon.getItems().add("--- Select Econ File ---");
		selectEcon.getSelectionModel().selectFirst();

		preGenbtn = new Button();
		preGenbtn.setText("Generate Preview");
		preGenbtn.setOnAction(new MainEventHandler(this));

		fileUpdatebtn = new Button();
		fileUpdatebtn.setText("Update File System");
		fileUpdatebtn.setOnAction(new MainEventHandler(this));

		resetbtn = new Button();
		resetbtn.setText("Reset Cut");
		resetbtn.setOnAction(new MainEventHandler(this));

		processOrderbtn = new Button();
		processOrderbtn.setText("Process Order");
		processOrderbtn.setOnAction(new MainEventHandler(this));

		GridPane.setConstraints(preGenbtn,16 , 1);
		GridPane.setConstraints(fileUpdatebtn, 32, 0);
		GridPane.setConstraints(resetbtn, 16 , 0);
		GridPane.setConstraints(processOrderbtn, 32, 1);
		GridPane.setConstraints(selectLog, 0 , 0);
		GridPane.setConstraints(selectEcon, 0, 1);

		top.getChildren().addAll(preGenbtn, fileUpdatebtn, resetbtn, processOrderbtn, selectLog, selectEcon);

		return top;
	}

	private Pane buildBottom()
	{
		HBox bottom = new HBox();
		bottom.setPadding(new Insets(20, 20, 20, 20));

		saw.getTextArea().setPrefRowCount(100);
		saw.getTextArea().setPrefColumnCount(100);

		bottom.getChildren().add(saw.getTextArea());

		return bottom;
	}
}
