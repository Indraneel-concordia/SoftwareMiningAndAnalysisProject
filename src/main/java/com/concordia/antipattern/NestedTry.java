package com.concordia.antipattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;

import com.concordia.flow.metrics.Util;

public class NestedTry{
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	public static int count = 0;
	
	public NestedTry(String file) {
		DetectNestedTry(file);
	}
	
	public static void DetectNestedTry(String file) {
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
			  public boolean visit(TryStatement node) {
				List<Statement> statements = node.getBody().statements();
			    for(Statement statement: statements) {
			    	if(statement instanceof TryStatement) {
			    		count++;
			    	}
			    	
			    	else if(statement.getNodeType() == Statement.FOR_STATEMENT || statement.getNodeType() == Statement.IF_STATEMENT
			    			|| statement.getNodeType() == Statement.SWITCH_STATEMENT || statement.getNodeType() == Statement.WHILE_STATEMENT) {
			    		
			    		statement.accept(new ASTVisitor() {
			    		  @Override
			  			  public boolean visit(TryStatement node)
			  		      {
					    		count++;

			  					return super.visit(node);
			  		      }
			    		});
			    	}
			    	
			    }
			    return super.visit(node);
			  }
		});
	}
	
}
