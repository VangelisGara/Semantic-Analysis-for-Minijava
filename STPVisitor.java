import syntaxtree.*;
import visitor.GJDepthFirst;
import symboltable.*;

public class STPVisitor extends GJDepthFirst <String,String> {
  SymbolTable ST;
  String currentClass;
  String currentMethod;

  STPVisitor(){
    ST = new SymbolTable();
  }

  public String visit(MainClass n,String argu) {
    System.out.println("We are in Main Class Declaration");
    String MainClassName = n.f1.accept(this,null);
    String MainArg = n.f11.accept(this,null);
    String MainName = "main";
    currentClass = MainClassName;
    currentMethod = MainName;
    MethodInfo MainMethodsInfo = new MethodInfo();
    MainMethodsInfo.InsertArgument(MainArg,"String Array");
    ClassInfo MainClassInfo = new ClassInfo();
    MainClassInfo.InsertMethodToClass(MainName,MainMethodsInfo);
    ST.InsertClassToSymbolTable(MainClassName,MainClassInfo);
    // Visit VarDeclaration
    n.f14.accept(this,null);
    return "MainClassVisited";
  }

  public String visit(ClassDeclaration n,String argu) {
    System.out.println("We are in Class Declaration");
    String className = n.f1.accept(this,null);
    ClassInfo classInfo = new ClassInfo();
    currentClass = className;
    currentMethod = "";
    ST.InsertClassToSymbolTable(className,classInfo);
    // Visit VarDeclaration
    n.f3.accept(this,null);
    // Visit Method Declaration
    n.f4.accept(this,null);
    return "ClassVisited";
  }

  public String visit(ClassExtendsDeclaration n,String argu) {
    System.out.println("We are in ClassExtends Declaration");
    String className = n.f1.accept(this,null);
    String extendsFrom = n.f3.accept(this,null);
    System.out.println(extendsFrom);
    ClassInfo classInfo = new ClassInfo();
    currentClass = className;
    currentMethod = "";
    ST.InsertClassToSymbolTable(className,classInfo);
    // Visit VarDeclaration
    n.f5.accept(this,null);
    return "ClassExtendsVisited";
  }

  public String visit(VarDeclaration n,String argu){
    System.out.println("We are in Var Declaration");
    System.out.println(currentClass + "," + currentMethod);
    String vartype = n.f0.accept(this,null);
    String varname = n.f1.accept(this,null);
    System.out.println(vartype + " " + varname);
    return "VarDeclarationVisited";
  }

  public String visit(MethodDeclaration n,String argu){
    System.out.println("We are in Method Declaration");
    String MethodType = n.f1.accept(this,null);
    String MethodName = n.f2.accept(this,null);
    currentMethod = MethodName;
    System.out.println(MethodType + " " + MethodName);
    System.out.println(currentClass + " " + currentMethod);
    MethodInfo newMethod = new MethodInfo();
    newMethod.type = MethodType;
    // Add method to the current class' info
    ST.classes_data.get(currentClass).InsertMethodToClass(MethodName,newMethod);
    return "MethodDeclarationVisited";
  }

  public String visit(IntegerType n, String argu) {
     return n.f0.toString();
  }

  public String visit(BooleanType n, String argu){
    return n.f0.toString();
  }

  public String visit(Identifier n, String argu) {
     return n.f0.toString();
  }

  public SymbolTable getSymbolTable(){
    return this.ST;
  }
}
