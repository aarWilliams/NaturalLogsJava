package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainEventHandler implements EventHandler<ActionEvent>
{

	Main main;

	public MainEventHandler(Main main)
	{
		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {

		Object cmd = event.getSource();

		if(main.fileUpdatebtn.equals(cmd))
		{
			main.selectLog.getItems().clear();
			main.selectLog.getItems().add("--- Select Log File ---");
			main.selectLog.getSelectionModel().selectFirst();
			main.logFiles = new File("../NaturalLogsOps/src/LogFiles");
			main.logList = main.logFiles.listFiles();
			String text = "Log files that were added: \n";
			for(File file : main.logList)
			{
				if(file.isFile())
				{
					main.selectLog.getItems().add(file.getName());
					text += file.getName() + "\n";
				}
			}
			main.selectEcon.getItems().clear();
			main.selectEcon.getItems().add("--- Select Econ File ---");
			main.selectEcon.getSelectionModel().selectFirst();
			main.econFiles = new File("../NaturalLogsOps/src/EconFiles");
			main.econList = main.econFiles.listFiles();
			text += "\nEconomic files that were added: \n";
			for(File file : main.econList)
			{
				if(file.isFile())
				{
					main.selectEcon.getItems().add(file.getName());
					text += file.getName() + "\n";
				}
			}
			main.saw.getTextArea().setText(text);
		}
		else if(main.preGenbtn.equals(cmd))
		{
			File log = new File("");
			File econ = new File("");
			try{
					for(File file : main.logList)
				{
					if(file.getName().equals(main.selectLog.getSelectionModel().getSelectedItem()))
					{
						log = file;
						break;
					}
				}
				for(File file : main.econList)
				{
					if(file.getName().equals(main.selectEcon.getSelectionModel().getSelectedItem()))
					{
						econ = file;
						break;
					}
				}

				try
				{
					Optimizer.optimization(log, econ, main.saw);
					main.spam = false;
				}
				catch(FileNotFoundException e)
				{
					main.saw.getTextArea().setText("File not found");
					//Nothing implemented if file is not found.
				}
			}
			catch(NullPointerException e)
			{
				main.saw.getTextArea().setText("File system has not been updated; please update the file system.");
			}
		}
		else if(main.resetbtn.equals(cmd))
		{
			main.spam = true;
			main.saw.setScrap(0.0);
			main.saw.setLumberlist(new ArrayList<>());
			main.saw.setCountArray(new double[3]);
			main.saw.setScrapEcon(0.0);
			main.saw.setTtlOne(0.0);
			main.saw.setTtlTwo(0.0);
			main.saw.setTtlThree(0.0);
			main.saw.setTtlScrap(0.0);
			main.saw.setTtlVal(0.0);
			main.saw.setlOne(new Lumber(1,1,1,0));
			main.saw.setlTwo(new Lumber(1,1,1,0));
			main.saw.setlThree(new Lumber(1,1,1,0));
			main.saw.getTextArea().setText("");
		}
		else if(main.processOrderbtn.equals(cmd))
		{
			if(main.spam == false)
			{
				String result = "";
				try {
					Class.forName("com.mysql.jdbc.Driver");
					main.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse?useSSL=false","root", "root");
				} catch(Exception e) {
					main.saw.getTextArea().setText("JDBC not implemented. ");
					e.printStackTrace();
				}
				int count = 0;
				for(Lumber l: main.saw.getLumberlist())
				{
					try{
						boolean exists = Query.queryExist(main.con, l);

						if(exists == true)
						{
							result += "Lumber entry to process:\n" + Query.querySelect(main.con, l);
							Query.queryUpdate(main.con, l, count, main.saw.getCountArray(), main.saw.getTtlScrap(), main.saw.getScrapEcon());
							result += "\nAfter processing:\n" + Query.querySelect(main.con, l) + "\n";
						}
						else
						{
							result += l.getHeight() + "x" + l.getWidth() + "x" + l.getLength()
							+ " did not exist. Creating new entry.";
							Query.queryInsert(main.con, l, count, main.saw.getCountArray(), main.saw.getTtlScrap(), main.saw.getScrapEcon());
							result += "\nAfter processing:\n" + Query.querySelect(main.con, l) + "\n";
						}
						main.saw.getTextArea().setText(result);
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
					count++;
				}
				main.spam = true;
			}
			else
			{
				main.saw.getTextArea().setText("Duplicate entries are not allowed. Please reset the cut and generate a new preview.");
			}
		}
	}
}
