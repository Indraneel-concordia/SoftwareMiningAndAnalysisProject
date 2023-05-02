package com.concordia.flow.metrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.w3c.dom.Node;

public class CatchSizeLOC 
{
	public static int count = 0;
	static int prevEndLine = 0;
	
	public CatchSizeLOC(String file) {
		count = 0;
		ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
		
		String source = "";
		try {
			source = read(file);	
		} catch (IOException e) {
			System.err.println(e);
		}	
		
		parser.setSource(source.toCharArray());
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			int countCatchBlock = 0;
			@Override
			public boolean visit(CatchClause node) {
				Block catchBlock = node.getBody();
		        int endLine = cu.getLineNumber(catchBlock.getStartPosition() + catchBlock.getLength() - 1);
		        if(endLine < prevEndLine) {
		        	return false;
		        }
		        prevEndLine = endLine;
		        countCatchBlock ++;
		       
		        String[] lines = catchBlock.toString().split("\\r?\\n");
		        int numLines = lines.length;
		        count += numLines;
		        
		        					
		        return super.visit(node);
			}
		});
		
	}
    
    public static String read(String fileName) throws IOException {
    	Path path = Paths.get(fileName);
    	String source = Files.lines(path).collect(Collectors.joining("\n"));
    	
    	return source;
    }
}
