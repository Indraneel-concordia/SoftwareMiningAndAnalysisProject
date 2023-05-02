package com.concordia.antipattern;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;

import com.concordia.flow.metrics.Util;

public class DestructiveWrapping {
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	public static int count = 0;
	
	public DestructiveWrapping(String file) {
			DetectDestructiveWrapping(file);
	}
	
	public static void DetectDestructiveWrapping(String file) {
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
			  public boolean visit(CatchClause node) {
				
				String catchException = node.getException().getType().toString();
				
				List<Statement> statements = node.getBody().statements();
			    for(Statement statement: statements) {
			    	
			    	if(statement instanceof ThrowStatement) {
			    		count++;
			    	}
			    	
			    	else if(statement.getNodeType() == Statement.FOR_STATEMENT || statement.getNodeType() == Statement.WHILE_STATEMENT
			    			|| statement.getNodeType() == Statement.IF_STATEMENT || statement.getNodeType() == Statement.SWITCH_STATEMENT) {
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
			    return super.visit(node);
			  }

		});
		
	}
}


