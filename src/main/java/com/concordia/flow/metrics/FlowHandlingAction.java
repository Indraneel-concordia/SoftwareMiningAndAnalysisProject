package com.concordia.flow.metrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;

public class FlowHandlingAction {
	
	public static int totalPossibleExceptions = 0;
    public static int caughtExceptions = 0;
    public static double percentageOfCaughtExceptions = 0;
    
    public FlowHandlingAction(String file) {
    	totalPossibleExceptions = 0;
	    caughtExceptions = 0;
	    percentageOfCaughtExceptions = 0;
	    
		ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
		
    	String source = "";
		try {
			source = read(file);	
		} catch (IOException e) {
			System.err.println(e);
		}	
		
		parser.setSource(source.toCharArray());
		
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		ExceptionHandlerAnalyzer analyzer = new ExceptionHandlerAnalyzer();
        cu.accept(analyzer);

        totalPossibleExceptions = analyzer.getTotalPossibleExceptions();
        caughtExceptions = analyzer.getCaughtExceptions();
        percentageOfCaughtExceptions = analyzer.getPercentageOfCaughtExceptions();

        System.out.println("Total possible exceptions: " + totalPossibleExceptions);
        System.out.println("Caught exceptions: " + caughtExceptions);
        System.out.println("Percentage of caught exceptions: " + percentageOfCaughtExceptions + "%");
    }

	public static String read(String fileName) throws IOException {
    	Path path = Paths.get(fileName);
    	String source = Files.lines(path).collect(Collectors.joining("\n"));
    	
    	return source;
    }
	
	static class ExceptionHandlerAnalyzer extends ASTVisitor {
	    private int totalPossibleExceptions = 0;
	    private int caughtExceptions = 0;

	    public boolean visit(TryStatement node) {
	        totalPossibleExceptions += node.catchClauses().size();
	        return true;
	    }

	    public boolean visit(CatchClause node) {
	    	Block block = node.getBody();
	        if (block != null) {
	            for (Object statement : block.statements()) {
	            	if (statement instanceof ExpressionStatement) {
	                    Expression expression = ((ExpressionStatement) statement).getExpression();
	                    if (expression instanceof MethodInvocation) {
	                    	MethodInvocation methodInvocation = (MethodInvocation) expression;
	                    	if(methodInvocation.toString() != null) {
	                    		caughtExceptions ++;
	                    	}
	                    }
	            	}
	            }
	        }
	        
	        return true;
	    }

	    public int getTotalPossibleExceptions() {
	        return totalPossibleExceptions;
	    }

	    public int getCaughtExceptions() {
	        return caughtExceptions;
	    }

	    public double getPercentageOfCaughtExceptions() {
	        if (totalPossibleExceptions == 0) {
	            return 0;
	        }
	        return (double) caughtExceptions / totalPossibleExceptions * 100;
	    }
	}
}
