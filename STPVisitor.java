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
    return "MainClassVisited";
  }

  public String visit(ClassDeclaration n,String argu) {
    System.out.println("We are in Class Declaration");
    String className = n.f1.accept(this,null);
    ClassInfo classInfo = new ClassInfo();
    currentClass = className;
    currentMethod = "";
    ST.InsertClassToSymbolTable(className,classInfo);
    ST.ListClasses();
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
    ST.ListClasses();
    return "ClassVisited";
  }

  public String visit(VarDeclaration n,String argu){
    System.out.println("We are in Var Declaration");
    System.out.println(currentClass + currentMethod);
    String vartype = n.f0.accept(this,null);
    String varname = n.f1.accept(this,null);
    System.out.println(vartype);
    System.out.println(varname);
    return "vardecldone";
  }

  public String visit(IntegerType n, String argu) {
     return n.f0.toString();
  }

  public String visit(Identifier n, String argu) {
     return n.f0.toString();
  }

}
