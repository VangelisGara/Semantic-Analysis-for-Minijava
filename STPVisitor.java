import syntaxtree.*;
import visitor.GJDepthFirst;
import symboltable.*;
import staticheckingexception.*;

public class STPVisitor extends GJDepthFirst <String,String> {
  SymbolTable ST; // our symbol table
  String currentClass;
  String currentMethod;

  STPVisitor(){
    System.out.println("\n 2.Populating the Symbol Table - Uniqueness Checks");
    ST = new SymbolTable();
  }

  public String visit(MainClass n,String argu) {
    //System.out.println("We are in Main Class Declaration");
    String MainClassName = n.f1.accept(this,null);
    String MainArg = n.f11.accept(this,null);
    String MainName = "main";
    currentClass = MainClassName;
    currentMethod = MainName;
    MethodInfo MainMethodsInfo = new MethodInfo();
    MainMethodsInfo.type = "void";
    MainMethodsInfo.InsertArgumentToMethod(MainArg,"String Array");
    ClassInfo MainClassInfo = new ClassInfo();
    MainClassInfo.InsertMethodToClass(MainName,MainMethodsInfo);
    ST.InsertClassToSymbolTable(MainClassName,MainClassInfo);
    // Visit VarDeclaration
    n.f14.accept(this,null);
    return "MainClassVisited";
  }

  public String visit(ClassDeclaration n,String argu) throws StatiCheckingException
  {
    //System.out.println("We are in Class Declaration");
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

  public String visit(ClassExtendsDeclaration n,String argu) throws StatiCheckingException
  {
    //System.out.println("We are in ClassExtends Declaration");
    String className = n.f1.accept(this,null);
    String extendsfrom = n.f3.accept(this,null);
    //System.out.println(extendsfrom);
    ClassInfo classInfo = new ClassInfo();
    currentClass = className;
    currentMethod = "";
    classInfo.extendsFrom = extendsfrom;
    ST.InsertClassToSymbolTable(className,classInfo);
    // Visit VarDeclaration
    n.f5.accept(this,null);
    // Visit MethodDeclaration
    n.f6.accept(this,null);
    return "ClassExtendsVisited";
  }

  public String visit(VarDeclaration n,String argu){
    //System.out.println("We are in Var Declaration");
    //System.out.println(currentClass + "," + currentMethod);
    String vartype = n.f0.accept(this,null);
    String varname = n.f1.accept(this,null);
    //System.out.println(vartype + " " + varname);
    if(currentMethod == ""){
      // Insert fields to class that belongs on symbol table
      ST.classes_data.get(currentClass).InsertFieldToClass(varname,vartype);
    }
    else {
      // Insert fields to the method of the class that belongs on symbol table
      ST.classes_data.get(currentClass).methods_data.get(currentMethod).InsertVarToMethod(varname,vartype);
    }
    return "VarDeclarationVisited";
  }

  public String visit(MethodDeclaration n,String argu){
    //System.out.println("We are in Method Declaration");
    String MethodType = n.f1.accept(this,null);
    String MethodName = n.f2.accept(this,null);
    currentMethod = MethodName;
    //System.out.println(MethodType + " " + MethodName);
    //System.out.println(currentClass + " " + currentMethod);
    MethodInfo newMethod = new MethodInfo();
    newMethod.type = MethodType;
    // Add method to the current class' info
    ST.classes_data.get(currentClass).InsertMethodToClass(MethodName,newMethod);
    // Visit FormalParameterList
    n.f4.accept(this,null);
    // Visit VarDeclaration
    n.f7.accept(this,null);
    return "MethodDeclarationVisited";
  }

  public String visit(FormalParameter n, String argu) {
    //System.out.println("We are in Formal Parameter");
    String parameter_type = n.f0.accept(this,null);
    String parameter_name = n.f1.accept(this,null);
    // Insert argument to current method of current class
    ST.classes_data.get(currentClass).methods_data.get(currentMethod).InsertArgumentToMethod(parameter_name,parameter_type);
    return "FormalParameterVisited";
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

  public SymbolTable getSymbolTable(){
    return this.ST;
  }
}
