import syntaxtree.*;
import visitor.GJDepthFirst;
import symboltable.*;
import typecheck.*;

public class TCVisitor extends GJDepthFirst <String,String> {
  TypeCheck TC; // our type checker

  TCVisitor(SymbolTable symbolTable){
    TC = new TypeCheck(symbolTable);
  }
  public String visit(MainClass n,String argu) {
    //System.out.println("We are in Main Class Declaration");
    String MainClassName = n.f1.accept(this,null);
    String MainName = "main";
    TC.SetCurrentMethod(MainName);
    TC.SetCurrentClass(MainClassName);
    // Visit Statement
    n.f15.accept(this,null);
    return "MainClassVisited";
  }

  public String visit(ClassDeclaration n,String argu) {
    //System.out.println("We are in Class Declaration");
    String className = n.f1.accept(this,null);
    TC.SetCurrentMethod("");
    TC.SetCurrentClass(className);
    // Visit MethodDeclaration
    n.f4.accept(this,null);
    return "ClassVisited";
  }

  public String visit(ClassExtendsDeclaration n,String argu) {
    //System.out.println("We are in ClassExtends Declaration");
    String className = n.f1.accept(this,null);
    TC.SetCurrentMethod("");
    TC.SetCurrentClass(className);
    // Visit MethodDeclaration
    n.f6.accept(this,null);
    return "ClassExtendsVisited";
  }

  public String visit(MethodDeclaration n,String argu){
    //System.out.println("We are in Method Declaration");
    String MethodName = n.f2.accept(this,null);
    TC.SetCurrentMethod(MethodName);
    // Visit Statement
    n.f8.accept(this,null);
    return "MethodDeclarationVisited";
  }

  public String visit(AssignmentStatement n,String argu) {
    System.out.println("We are in AssignmentStatement");
    String Dest = n.f0.accept(this,null);
    TC.IsVarDeclared(Dest);
    return "AssignmentStatementVisited";
  }


  public String visit(IntegerType n, String argu) {
     return n.f0.toString();
  }

  public String visit(ArrayType n, String argu) {
    return "Int Array";
  }

  public String visit(BooleanType n, String argu){
    return n.f0.toString();
  }

  public String visit(Identifier n, String argu) {
     return n.f0.toString();
  }
}
