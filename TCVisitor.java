import syntaxtree.*;
import visitor.GJDepthFirst;
import symboltable.*;
import typecheck.*;
import staticheckingexception.*;

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
    // Visit VarDeclaration
    n.f14.accept(this,argu);
    // Visit Statement
    n.f15.accept(this,null);
    return "MainClassVisited";
  }

  public String visit(ClassDeclaration n,String argu) {
    //System.out.println("We are in Class Declaration");
    String className = n.f1.accept(this,null);
    TC.SetCurrentMethod("");
    TC.SetCurrentClass(className);
    // Visit VarDeclaration
    n.f3.accept(this,null);
    // Visit MethodDeclaration
    n.f4.accept(this,null);
    return "ClassVisited";
  }

  public String visit(ClassExtendsDeclaration n,String argu) {
    //System.out.println("We are in ClassExtends Declaration");
    String className = n.f1.accept(this,null);
    TC.SetCurrentMethod("");
    TC.SetCurrentClass(className);
    // Visit VarDeclaration
    n.f5.accept(this,null);
    // Visit MethodDeclaration
    n.f6.accept(this,null);
    return "ClassExtendsVisited";
  }

  public String visit(MethodDeclaration n,String argu){
    //System.out.println("We are in Method Declaration");
    String MethodName = n.f2.accept(this,null);
    TC.SetCurrentMethod(MethodName);
    // Visit Type
    n.f1.accept(this,null);
    // Visit FormalParameterList
    n.f4.accept(this,null);
    // Visit VarDeclaration
    n.f7.accept(this,null);
    // Visit Statement
    n.f8.accept(this,null);
    return "MethodDeclarationVisited";
  }

  public String visit(AssignmentStatement n,String argu) {
    //System.out.println("We are in AssignmentStatement");
    String Dest = n.f0.accept(this,null);
    TC.IsVarDeclared(Dest);
    return "AssignmentStatementVisited";
  }

  public String visit(Type n, String argu) throws StatiCheckingException
  {
    System.out.println("We are in Type");
    String type = n.f0.accept(this,null);
    System.out.println(type);
    TC.IsTypeAllowed(type);
    return "TypeVisited";
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
