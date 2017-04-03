package simpleASTParser;



import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class MethodInvocationASTVisitor extends ASTVisitor{
	
	private final CompilationUnit cu;
	private String filename;

	public MethodInvocationASTVisitor(char[] arr,String file_name) {
		
		this.filename = file_name;
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(arr);

		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		cu = (CompilationUnit) parser.createAST(null);

	}
	
	
	public boolean visit(PackageDeclaration  node) {
		
		System.out.println("====> " + this.filename + " <<|>> " + 
				cu.getLineNumber(node.getStartPosition()) + 
				" <<|>> PackageDeclaration <<|>> " + node.getName() );
		return true;
	
	}
	
	
	
	public boolean visit(ImportDeclaration  node) {
		
		System.out.println("====> " + this.filename + " <<|>> " + 
				cu.getLineNumber(node.getStartPosition()) + 
				" <<|>> ImportDeclaration <<|>> " + node.getName() );
		return true;
	
	}
	
	
	
	
	public boolean visit(MethodInvocation  node) {
		
		//System.out.println(">>>>");
		
		if(node.getExpression()!=null) {
			
			Expression exp = node.getExpression();
			
			String name = exp.getClass().getTypeName();
			
			
			System.out.println(name);
				 
		    
			System.out.println("====> " + this.filename + " <<|>> " + 
					cu.getLineNumber(node.getStartPosition()) + 
					" <<|>> MethodInvocation <<|>> " + node.getExpression()
					+ "--->" + node.getName()
					+ "," + exp.getClass());
		}
		else {
			System.out.println("====> " + this.filename + " <<|>> " + 
					cu.getLineNumber(node.getStartPosition()) + 
					" <<|>> MethodInvocation <<|>> " + node.getName());
			
		}
		
		/*
		
		System.out.println(this.filename + " <<|>> " + 
				cu.getLineNumber(node.getStartPosition()) + 
				"<<|>> MethodInvocation <<|>> " + node.getName());
		*/
		
		return true;
	}

	public void parse() {
		cu.accept(this);
	}
}

