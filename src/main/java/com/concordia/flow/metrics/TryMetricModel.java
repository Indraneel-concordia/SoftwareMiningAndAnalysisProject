package com.concordia.flow.metrics;

import java.io.FileWriter;
import java.util.List;

public class TryMetricModel {
	private String FilePath;
	private String Project;
	private int InvokedMethods;
	private int TryQuantity;
	private int TrySizeLOC;
	private int TrySizeSLOC;
	
	public TryMetricModel(String filePath, String project, int invokedMethods, int tryQuantity, int trySizeLOC,
			int trySizeSLOC) {
		super();
		FilePath = filePath;
		Project = project;
		InvokedMethods = invokedMethods;
		TryQuantity = tryQuantity;
		TrySizeLOC = trySizeLOC;
		TrySizeSLOC = trySizeSLOC;
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
	public int getInvokedMethods() {
		return InvokedMethods;
	}
	public void setInvokedMethods(int invokedMethods) {
		InvokedMethods = invokedMethods;
	}
	public int getTryQuantity() {
		return TryQuantity;
	}
	public void setTryQuantity(int tryQuantity) {
		TryQuantity = tryQuantity;
	}
	public int getTrySizeLOC() {
		return TrySizeLOC;
	}
	public void setTrySizeLOC(int trySizeLOC) {
		TrySizeLOC = trySizeLOC;
	}
	public int getTrySizeSLOC() {
		return TrySizeSLOC;
	}
	public void setTrySizeSLOC(int trySizeSLOC) {
		TrySizeSLOC = trySizeSLOC;
	}
	
	public String ReturnCSV() {
		String CSV = FilePath + "," + Project + "," + InvokedMethods + "," + TryQuantity + "," + TrySizeLOC + "," + TrySizeSLOC;
		return CSV;
	}
	
	public static void GenerateCSVFromList(List<TryMetricModel> catchFlowMetricsList, String csvName, String column) {
		try {
			FileWriter writer = new FileWriter(csvName);
			writer.write(column + "\n");
			
			for(TryMetricModel model : catchFlowMetricsList) {
				writer.write(model.ReturnCSV() + "\n");
			}
			writer.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
