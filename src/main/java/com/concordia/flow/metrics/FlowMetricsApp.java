package com.concordia.flow.metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.concordia.antipattern.AntiPatternModelClass;
import com.concordia.antipattern.DestructiveWrapping;
import com.concordia.antipattern.NestedTry;
import com.concordia.antipattern.ThrowWithinFinally;
import com.concordia.antipattern.ThrowKitchenSink;

public class FlowMetricsApp {
	public static void main(String[] args) {
		
		List<FlowMetricModel> catchFlowMetricsList = new ArrayList<FlowMetricModel>();
		List<TryMetricModel> tryFlowMetricsList = new ArrayList<TryMetricModel>();
		List<AntiPatternModelClass> antiPatternList = new ArrayList<AntiPatternModelClass>();
		
		String path = "/Users/kosur/OneDrive/Desktop/dataset/hibernate-orm";
		String projectName = "hibernate-5.0";
		
		for(int i=0; i<2; i++) {
			if(i == 1) {
				path = "/Users/kosur/OneDrive/Desktop/dataset/hadoop";
				projectName = "hadoop-2.6";
			}
			
			System.out.println("Application working with: " + projectName);
			
			List<String> filePathList = Util.getFilePath(path);
			
			for(String file : filePathList) {
				
				//Generate Catch Based metrics
				new CatchQuantity(file);
				new CatchSizeLOC(file);
				new CatchSizeSLOC(file);
				new FlowHandlingAction(file);
				
				FlowMetricModel catchModel = new FlowMetricModel(
						file, 
						projectName, 
						CatchQuantity.count, CatchSizeLOC.count, 
						CatchSizeSLOC.count, 
						FlowHandlingAction.caughtExceptions, 
						FlowHandlingAction.totalPossibleExceptions, 
						FlowHandlingAction.percentageOfCaughtExceptions);
				
				catchFlowMetricsList.add(catchModel);
				

//				new InvokedMethods(file);
//				new TryQuantity(file);
//				new TrySizeLOC(file);
//				new TrySizeSLOC(file);
//				
//				TryMetricModel tryModel = new TryMetricModel(file,
//						projectName,
//						InvokedMethods.count, 
//						TryQuantity.count, 
//						TrySizeLOC.count, 
//						TrySizeSLOC.count);
//				
//				tryFlowMetricsList.add(tryModel);
				
//				new DestructiveWrapping(file);
//				new NestedTry(file);
//				new ThrowKitchenSink(file);
//				new ThrowWithinFinally(file);
//				
//				AntiPatternModelClass antiModel = new AntiPatternModelClass(
//						path, 
//						projectName, 
//						DestructiveWrapping.count, 
//						NestedTry.count, 
//						ThrowKitchenSink.count, 
//						ThrowWithinFinally.count);
//				
//				antiPatternList.add(antiModel);
				
			}
		}
		
		String catchColumn = "FilePath,Project,CatchQuantity,CatchSizeLOC,CatchSizeSLOC,CaughtExceptions,TotalPossibleExceptions,PercentageOfCaughtExceptions";
		FlowMetricModel.GenerateCSVFromList(catchFlowMetricsList, "Catch_Based.csv", catchColumn);
		System.out.println("CSV Generated for Catch Based");
//		
//		String tryColumn = "FilePath,Project,InvokedMethods,TryQuantity,TrySizeLOC,TrySizeSLOC";
//		TryMetricModel.GenerateCSVFromList(tryFlowMetricsList, "Try_Based.csv", tryColumn);
//		System.out.println("CSV Generated for Try Based");
		
//		String antiPatternColumn = "FilePath,Project,DestructiveWrapping,NestedTry,ThorwsKitchenSink,ThrowWithinFinally";
//		AntiPatternModelClass.GenerateCSVFromList(antiPatternList, "Throws_Anti-Pattern_Based.csv", antiPatternColumn);
//		System.out.println("CSV Generated for Anti Pattern Based");
	}
}
