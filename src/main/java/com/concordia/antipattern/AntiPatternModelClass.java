package com.concordia.antipattern;

import java.io.FileWriter;
import java.util.List;

import com.concordia.flow.metrics.FlowMetricModel;

public class AntiPatternModelClass {
	private String FilePath;
	private String Project;
	private int DestructiveWrapping;
	private int NestedTry;
	private int ThorwsKitchenSink;
	private int ThrowWithinFinally;
	
	public AntiPatternModelClass(String filePath, String project, int destructiveWrapping, int nestedTry,
			int thorwsKitchenSink, int throwWithinFinally) {
		super();
		FilePath = filePath;
		Project = project;
		DestructiveWrapping = destructiveWrapping;
		NestedTry = nestedTry;
		ThorwsKitchenSink = thorwsKitchenSink;
		ThrowWithinFinally = throwWithinFinally;
	}

	public String getFilePath() {
		return FilePath;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	public String getProject() {
		return Project;
	}

	public void setProject(String project) {
		Project = project;
	}

	public int getDestructiveWrapping() {
		return DestructiveWrapping;
	}

	public void setDestructiveWrapping(int destructiveWrapping) {
		DestructiveWrapping = destructiveWrapping;
	}

	public int getNestedTry() {
		return NestedTry;
	}

	public void setNestedTry(int nestedTry) {
		NestedTry = nestedTry;
	}

	public int getThorwsKitchenSink() {
		return ThorwsKitchenSink;
	}

	public void setThorwsKitchenSink(int thorwsKitchenSink) {
		ThorwsKitchenSink = thorwsKitchenSink;
	}

	public int getThrowWithinFinally() {
		return ThrowWithinFinally;
	}

	public void setThrowWithinFinally(int throwWithinFinally) {
		ThrowWithinFinally = throwWithinFinally;
	}
	
	public String ReturnCSV() {
		String CSV = FilePath + "," + Project + "," + DestructiveWrapping + "," + NestedTry + "," + ThorwsKitchenSink + "," + ThrowWithinFinally;
		return CSV;
	}
	
	public static void GenerateCSVFromList(List<AntiPatternModelClass> catchFlowMetricsList, String csvName, String column) {
		try {
			FileWriter writer = new FileWriter(csvName);
			writer.write(column + "\n");
			
			for(AntiPatternModelClass model : catchFlowMetricsList) {
				writer.write(model.ReturnCSV() + "\n");
			}
			writer.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
