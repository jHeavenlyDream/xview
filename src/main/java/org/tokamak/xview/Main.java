package org.tokamak.xview;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.tokamak.xview.exception.DataFormException;


import java.io.File;
import java.io.IOException;

public class Main extends Application {

	private ListView<String> channelList;
	private JFreeChart chart;
	private DataFile data;

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
		//controlPane.setStyle("-fx-background-color: #546685");
		controlPane.setMinWidth(150);

		Label label = new Label("Channels");
		AnchorPane.setLeftAnchor(label, 10.0);
		channelList = new ListView<>();
		AnchorPane.setTopAnchor(channelList, 20.0);
		AnchorPane.setBottomAnchor(channelList, 10.0);
		AnchorPane.setLeftAnchor(channelList, 10.0);
		AnchorPane.setRightAnchor(channelList, 10.0);
		controlPane.getChildren().addAll(label, channelList);

		//center
		chart = ChartFactory.createXYLineChart(
				"channel",
				"time",
				"I",
				null);

		ChartViewer viewer = new ChartViewer(chart);

		//set components on scene
		root.setTop(menuBar);
		root.setLeft(controlPane);
		root.setCenter(viewer);

		openItem.setOnAction(e ->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Tokamak data files");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Tokamak data", "*.xxx")
			);

			File selectedFile =  fileChooser.showOpenDialog(primaryStage);

			if(selectedFile != null){
				loadDataFile(selectedFile);
			}
		});

		channelList.getSelectionModel().selectedItemProperty()
				.addListener((changed, oldVal, newVal) -> showChannel());

	}

	private void loadDataFile(File dataFile){
		try {
			data = new DataFileReader().load(dataFile);
			channelList.setItems(FXCollections.observableArrayList(data.listChannels()));
			chart.getXYPlot().setDataset(data.getXyDataset());
		}catch (DataFormException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}

	}

	private void showChannel(){
		for (int i = 0; i < data.getXyDataset().getSeriesCount(); i++)
			chart.getXYPlot().getRenderer().setSeriesVisible(i,false);
		chart.getXYPlot().getRenderer()
				.setSeriesVisible(channelList.getSelectionModel().getSelectedIndex(),true);
	}

}
