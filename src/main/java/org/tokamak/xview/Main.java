package org.tokamak.xview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

public class Main extends Application {

    public static void main(String[] args) { 
    	launch(args);
    }

    @Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		primaryStage.setTitle("xview");
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();

		//create menu bar
		MenuBar menuBar = new MenuBar();

		//create menus
		Menu menuFile = new Menu("File");
		Menu menuHelp = new Menu("Help");

		//create menu items
		MenuItem openItem = new MenuItem("Open");
		MenuItem exitItem = new MenuItem("Exit");

		MenuItem aboutItem = new MenuItem("About");

		menuFile.getItems().addAll(openItem, exitItem);
		menuHelp.getItems().addAll(aboutItem);

		//add menus to menubar
		menuBar.getMenus().addAll(menuFile, menuHelp);

		//left panel
		AnchorPane controlPane = new AnchorPane();
		controlPane.setStyle("-fx-background-color: #546685");
		controlPane.setMinWidth(200);

		Label label = new Label("Channels");
		ListView<String> channelList = new ListView<>();
		AnchorPane.setTopAnchor(channelList, 20.0);
		AnchorPane.setBottomAnchor(channelList, 10.0);
		controlPane.getChildren().addAll(label, channelList);

		//center
		XYDataset dataset = new XYSeriesCollection();
		JFreeChart chart = ChartFactory.createXYLineChart(
				"channel",
				"time",
				"I",
				dataset);

		ChartViewer viewer = new ChartViewer(chart);

		//set components on scene
		root.setTop(menuBar);
		root.setLeft(controlPane);
		root.setCenter(viewer);
	}
}
