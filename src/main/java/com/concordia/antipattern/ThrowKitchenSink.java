package com.concordia.antipattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.concordia.flow.metrics.Util;

public class ThrowKitchenSink{
	
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	public static int count = 0;
	
	
	public ThrowKitchenSink(String file) {
		DetectThrowsKitchenSink(file);
	}
	
	public static void DetectThrowsKitchenSink(String file) {
		count = 0;
		String source = "";
		
		try {
			source = Util.read(file);
		}catch(Exception ex) {
			System.out.println(ex);
		}

		parser.setSource(source.toCharArray());
		
		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		compilationUnit.accept(new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {
	    		if(node.thrownExceptionTypes().size() >= 2) {
	    			count++;
	    		}
	    		
	    		return super.visit(node);
	    	}
		});
	}
}
