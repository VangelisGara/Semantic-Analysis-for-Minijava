package typecheck;
import symboltable.*;
import staticheckingexception.*;

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
  public void IsVarDeclared(String var) throws StatiCheckingException
  {
    boolean declaredAsField = ST.classes_data.get(currentClass).class_variables_data.containsKey(var);
    boolean declaredAsMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.containsKey(var);
    boolean declaredAsArgument = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.containsKey(var);
    boolean declaredAsFieldInSuperclass = false;
    String superclass = ST.classes_data.get(currentClass).extendsFrom;
    if(superclass != "")
      declaredAsFieldInSuperclass = ST.classes_data.get(superclass).class_variables_data.containsKey(var);
    if( !(declaredAsField || declaredAsArgument || declaredAsMethodVar || declaredAsFieldInSuperclass) )
      throw new StatiCheckingException("\n✗ Var " + var + " in method " + this.currentMethod + " of class " + this.currentClass + " has not been declared");
  }

  // Check if variable is an array
  public void IsVarArray(String var) throws StatiCheckingException
  {
    // get the type of the var
    String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
    typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(var);
    typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(var);
    typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(var);
    String superclass = ST.classes_data.get(currentClass).extendsFrom;
    if(superclass != "")
      typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(var);

    // check if type is int array
    if(typeGotFromMethodVar != "Int Array" && typeGotFromMethodArg != "Int Array" &&  typeGotFromField != "Int Array" && typeGotFromSuperField != "Int Array")
        throw new StatiCheckingException("\n✗ Var " + var + " in method " + this.currentMethod + " of class " + this.currentClass + " isn't an int array");
  }

  // Check that type is allowed
  public void IsTypeAllowed(String type) throws StatiCheckingException
  {
    // check if type is one of the classes declared
    if( !(ST.classes_data.containsKey(type)) ){
      // check if type is one of the basic types
      if(type != "int" && type != "boolean" && type != "Int Array"){
        if(this.currentMethod == "")
          throw new StatiCheckingException("\n✗ Illegal type " + type + " in class " + this.currentClass);
        else
          throw new StatiCheckingException("\n✗ Illegal type " + type + " in method " + this.currentMethod + " of class " + this.currentClass);
      }
    }
  }

}
