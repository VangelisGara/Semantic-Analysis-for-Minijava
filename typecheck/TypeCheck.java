package typecheck;
import symboltable.*;

public class TypeCheck{
  public SymbolTable ST;
  String currentClass="";
  String currentMethod="";

  // Constructor
  public TypeCheck(SymbolTable symbolTable){
    ST = symbolTable;
  }

  public void SetCurrentMethod(String curMethod){
    currentMethod = curMethod;
  }

  public void SetCurrentClass(String curClass){
    currentClass = curClass;
  }

  // Check that variable has been declared
  public void IsVarDeclared(String var){
    //System.out.println(var);
    //System.out.println(currentClass);
    //System.out.println(currentMethod);
    boolean declaredAsField = ST.classes_data.get(currentClass).class_variables_data.containsKey(var);
    boolean declaredAsMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.containsKey(var);
    boolean declaredAsArgument = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.containsKey(var);
    //System.out.println(declaredAsField);
    //System.out.println(declaredAsMethodVar);
    //System.out.println(declaredAsArgument);
    if( !(declaredAsField || declaredAsArgument || declaredAsMethodVar) ){
      System.out.println(var + " has not been declared");
      System.exit(1);
    }
  }
}
