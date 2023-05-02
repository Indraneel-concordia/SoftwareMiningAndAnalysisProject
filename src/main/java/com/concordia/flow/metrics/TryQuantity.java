package com.concordia.flow.metrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TryStatement;


/**
 * Detect the number of try blocks in the file.
 *
 */
public class TryQuantity 
{
	public static int count = 0;
	public TryQuantity(String file) {
		count = 0;
		ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
		
		String source = "";
		try {
			source = read(file);	
		} catch (IOException e) {
			System.err.println(e);
		}	
		
		parser.setSource(source.toCharArray());
		
		ASTNode root = parser.createAST(null);
		ArrayList<TryStatement> tryStatements = new ArrayList<>();
		Visitor visitor = new Visitor(tryStatements);
		root.accept(visitor);
		count = tryStatements.size();
		//System.out.println("Number of try blocks: " + tryStatements.size());
	}
    
    public static String read(String fileName) throws IOException {
    	Path path = Paths.get(fileName);
    	String source = Files.lines(path).collect(Collectors.joining("\n"));
    	
    	return source;
    }
    
    static class Visitor extends ASTVisitor {
    	private final ArrayList<TryStatement> tryStatements;
    	
    	public Visitor(ArrayList<TryStatement> tryStatements) {
    		this.tryStatements = tryStatements;
    	}
    	
    	@Override
    	public boolean visit(TryStatement node) {
    		tryStatements.add(node);
    		
    		return super.visit(node);
    	}
	}
}
