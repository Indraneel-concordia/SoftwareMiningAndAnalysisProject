package com.concordia.flow.metrics;

import java.io.FileWriter;
import java.util.List;

public class FlowMetricModel {
	private String FilePath;
	private String Project;
	private int CatchQuantity;
	private int CatchSizeLOC;
	private int CatchSizeSLOC;
	private int CaughtExceptions;
	private int TotalPossibleExceptions;
	private double PercentageOfCaughtExceptions;
	
	public FlowMetricModel(String filePath, String project, int catchQuantity, int catchSizeLOC,
			int catchSizeSLOC, int caughtExceptions, int totalPossibleExceptions, double percentageOfCaughtExceptions) {
		super();
		FilePath = filePath;
		Project = project;
		CatchQuantity = catchQuantity;
		CatchSizeLOC = catchSizeLOC;
		CatchSizeSLOC = catchSizeSLOC;
		CaughtExceptions = caughtExceptions;
		TotalPossibleExceptions = totalPossibleExceptions;
		PercentageOfCaughtExceptions = percentageOfCaughtExceptions;
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

	public int getCatchQuantity() {
		return CatchQuantity;
	}

	public void setCatchQuantity(int catchQuantity) {
		CatchQuantity = catchQuantity;
	}

	public int getCatchSizeLOC() {
		return CatchSizeLOC;
	}

	public void setCatchSizeLOC(int catchSizeLOC) {
		CatchSizeLOC = catchSizeLOC;
	}

	public int getCatchSizeSLOC() {
		return CatchSizeSLOC;
	}

	public void setCatchSizeSLOC(int catchSizeSLOC) {
		CatchSizeSLOC = catchSizeSLOC;
	}

	public int getCaughtExceptions() {
		return CaughtExceptions;
	}

	public void setCaughtExceptions(int caughtExceptions) {
		CaughtExceptions = caughtExceptions;
	}

	public int getTotalPossibleExceptions() {
		return TotalPossibleExceptions;
	}

	public void setTotalPossibleExceptions(int totalPossibleExceptions) {
		TotalPossibleExceptions = totalPossibleExceptions;
	}

	public double getPercentageOfCaughtExceptions() {
		return PercentageOfCaughtExceptions;
	}

	public void setPercentageOfCaughtExceptions(double percentageOfCaughtExceptions) {
		PercentageOfCaughtExceptions = percentageOfCaughtExceptions;
	}

	public String ReturnCSV() {
		String CSV = FilePath + "," + Project + "," + CatchQuantity + "," + CatchSizeLOC + "," + CatchSizeSLOC + "," + CaughtExceptions + "," + TotalPossibleExceptions + "," + PercentageOfCaughtExceptions;
		return CSV;
	}
	
	public static void GenerateCSVFromList(List<FlowMetricModel> catchFlowMetricsList, String csvName, String column) {
		try {
			FileWriter writer = new FileWriter(csvName);
			writer.write(column + "\n");
			
			for(FlowMetricModel model : catchFlowMetricsList) {
				writer.write(model.ReturnCSV() + "\n");
			}
			writer.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
