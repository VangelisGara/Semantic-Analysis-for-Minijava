import syntaxtree.*;
import visitor.GJDepthFirst;
import symboltable.*;

public class STPVisitor extends GJDepthFirst <String,String> {
  SymbolTable ST;

  STPVisitor(){
    ST = new SymbolTable();
  }

  public String visit(MainClass n,String argu) {
    System.out.println("We are in Main Class Declaration");
    String MainClassName = n.f1.accept(this,null);
    String MainArg = n.f11.accept(this,null);
    String MainName = "main";
    MethodInfo MainMethodsInfo = new MethodInfo();
    MainMethodsInfo.InsertArgument(MainArg,"String Array");
    ClassInfo MainClassInfo = new ClassInfo();
    MainClassInfo.InsertMethodToClass(MainName,MainMethodsInfo);
    ST.InsertClassToSymbolTable(MainClassName,MainClassInfo);

    ST.ListEverything();

    return "MainClassVisited";
  }

  /*public String visit(VarDeclaration n,String argu){
    System.out.println("We are in Var Declaration");
    String vartype = n.f0.accept(this,null);
    String varname = n.f1.accept(this,null);
    System.out.println(vartype);
    System.out.println(varname);
    return "vardecl done";
  } */

  public String visit(IntegerType n, String argu) {
     return n.f0.toString();
  }

  public String visit(Identifier n, String argu) {
     return n.f0.toString();
  }

}
