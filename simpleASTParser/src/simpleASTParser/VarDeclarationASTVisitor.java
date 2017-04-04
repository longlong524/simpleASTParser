package simpleASTParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jface.text.projection.Fragment;

public class VarDeclarationASTVisitor extends ASTVisitor{
	
	public static List<String> StringLiterals=new LinkedList<String>();
	public static List<Character> CharacterLiterals=new LinkedList<Character>();
	public static List<String> NumLiterals=new LinkedList<String>();
	
	public static Map<String,String> VARLiterals=new HashMap<String,String>();
	public static List<String> SuperFields=new LinkedList<String>();
	
	private final CompilationUnit cu;
	private String filename;

	public VarDeclarationASTVisitor(char[] arr,String file_name) {
		
		this.filename = file_name;
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(arr);

		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		cu = (CompilationUnit) parser.createAST(null);

	}
	
	/*
	public boolean visit(VariableDeclarationFragment  node) {
		
		System.out.println("====> " + this.filename + " <<|>> " + 
				cu.getLineNumber(node.getStartPosition()) + 
				" <<|>> VariableDeclarationFragment <<|>> " + node.getName());
		return true;
	
	}
	*/
	
	@Override
	public boolean visit(NumberLiteral node) {
		NumLiterals.add(node.getToken());
		System.err.println("NumLiterals:"+node.getToken());
		return true;
	}


	@Override
	public boolean visit(StringLiteral node) {
		StringLiterals.add(node.getLiteralValue());
		System.err.println("StringLiterals:"+node.getLiteralValue());
		return true;
	}

	public boolean visit(VariableDeclarationStatement  node) {
		
		System.out.println("====> " + this.filename + " <<|>> " + 
				cu.getLineNumber(node.getStartPosition()) + 
				" <<|>> VariableDeclarationStatement <<|>> " + node.getType());
		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> ffs=node.fragments();
		for(VariableDeclarationFragment f:ffs){
			System.err.println(f.getName().toString()+" :: "+node.getType().toString());
			VARLiterals.put(f.getName().toString(),node.getType().toString());
		}
		
		return true;
	}
	
	@Override
	public boolean visit(SuperFieldAccess node) {
		SuperFields.add(node.getName().toString());
		return true;
	}



	@Override
	public boolean visit(FieldDeclaration node) {
		
		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> ffs=node.fragments();
		for(VariableDeclarationFragment f:ffs){
			System.err.println(f.getName().toString()+" :: "+node.getType().toString());
			VARLiterals.put(f.getName().toString(),node.getType().toString());
		}
		return true;
	}

	public boolean visit(LabeledStatement  node) {
		
		System.out.println("====> " + this.filename + " <<|>> " + 
				cu.getLineNumber(node.getStartPosition()) + 
				" <<|>> LabeledStatement <<|>> " );
		Statement a;

		return true;
	}
	
	
	@Override
	public boolean visit(CharacterLiteral node) {
		// TODO Auto-generated method stub
		CharacterLiterals.add(node.charValue());
		return true;
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		// TODO Auto-generated method stub
		return true;
	}

	public void parse() {
		cu.accept(this);
	}
}

