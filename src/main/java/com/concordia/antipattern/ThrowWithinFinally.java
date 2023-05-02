package com.concordia.antipattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;

import com.concordia.flow.metrics.Util;

public class ThrowWithinFinally{
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	public static int count = 0;
	
	public ThrowWithinFinally(String file) {
		DetectThrowWithinFinally(file);
	}
	
	public static void DetectThrowWithinFinally(String file) {
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
				  Block finallyBlock = node.getFinally();
				  if(finallyBlock != null) {
					  List<Statement> statements = finallyBlock.statements();
					    for(Statement statement: statements) {
					    	if(statement instanceof ThrowStatement) {
					    		count++;
					    	}
					    	
					    	else if(statement.getNodeType() == Statement.FOR_STATEMENT || statement.getNodeType() == Statement.WHILE_STATEMENT
					    			|| statement.getNodeType() == Statement.IF_STATEMENT || statement.getNodeType() == Statement.SWITCH_STATEMENT) {
					    		//System.out.println(statement);
					    		statement.accept(new ASTVisitor() {
					    		  @Override
					  			  public boolean visit(ThrowStatement node)
					  		      {
			  			    		count++;
			  			    		
					  				return super.visit(node);
					  		      }
					    		});
					    	}
					    	
					    }
				  }
				
			    return super.visit(node);
			  }
		});
	}
}
