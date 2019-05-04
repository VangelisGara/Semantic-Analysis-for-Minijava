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
  public boolean IsVarDeclared(String var) throws StatiCheckingException
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
    return true;
  }

  // Check if class has been declared
  public void IsClassDeclared(String className) throws StatiCheckingException
  {
    if( !(ST.classes_data.containsKey(className)) )
      throw new StatiCheckingException("\n✗ There is no class object " + className + " in method " + this.currentMethod + " of class " + this.currentClass);
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
    if(typeGotFromMethodVar != "int array" && typeGotFromMethodArg != "int array" &&  typeGotFromField != "int array" && typeGotFromSuperField != "int array")
        throw new StatiCheckingException("\n✗ Var " + var + " in method " + this.currentMethod + " of class " + this.currentClass + " isn't an int array");
  }

  // Check if variable is int
  public void IsVarInt(String var) throws StatiCheckingException
  {
    // get the type of the var
    String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
    typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(var);
    typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(var);
    typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(var);
    String superclass = ST.classes_data.get(currentClass).extendsFrom;
    if(superclass != "")
      typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(var);

    // check if type is int
    if(typeGotFromMethodVar != "int" && typeGotFromMethodArg != "int" &&  typeGotFromField != "int" && typeGotFromSuperField != "int")
      throw new StatiCheckingException("\n✗ Var " + var + " in method " + this.currentMethod + " of class " + this.currentClass + " isn't an int");
  }

  // Check if variable is a boolean
  public void IsVarBoolean(String var) throws StatiCheckingException
  {
    // get the type of the var
    String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
    typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(var);
    typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(var);
    typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(var);
    String superclass = ST.classes_data.get(currentClass).extendsFrom;
    if(superclass != "")
      typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(var);

    // check if type is boolean
    if(typeGotFromMethodVar != "boolean" && typeGotFromMethodArg != "boolean" &&  typeGotFromField != "boolean" && typeGotFromSuperField != "boolean")
      throw new StatiCheckingException("\n✗ Var " + var + " in method " + this.currentMethod + " of class " + this.currentClass + " isn't a boolean");
  }

  // Check that type is allowed
  public void IsTypeAllowed(String type) throws StatiCheckingException
  {
    // check if type is one of the classes declared
    if( !(ST.classes_data.containsKey(type)) ){
      // check if type is one of the basic types
      if(type != "int" && type != "boolean" && type != "int array"){
        if(this.currentMethod == "")
          throw new StatiCheckingException("\n✗ Illegal type " + type + " in class " + this.currentClass);
        else
          throw new StatiCheckingException("\n✗ Illegal type " + type + " in method " + this.currentMethod + " of class " + this.currentClass);
      }
    }
  }

  // Check if NOT operation is allowed for the given clause
  public void CheckNotOperation(String clause) throws StatiCheckingException
  {
    if(clause == "int" || clause == "this" || clause == "int array" || clause.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal NOT operation in class " + this.currentClass + " of method " + this.currentMethod + ", clause must be of type boolean");
    if(clause != "boolean"){
      this.IsVarDeclared(clause);
      this.IsVarBoolean(clause);
    }
  }

  // Check if AND operation is allowed for the given clause
  public void CheckAndOperation(String lClause,String rClause) throws StatiCheckingException
  {
    // check left clause
    if(lClause == "int" || lClause == "this" || lClause == "int array" || lClause.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal AND operation in class " + this.currentClass + " of method " + this.currentMethod + ", clause must be of type boolean");
    if(lClause != "boolean"){
      this.IsVarDeclared(lClause);
      this.IsVarBoolean(lClause);
    }
    // check right clause
    if(rClause == "int" || rClause == "this" || rClause == "int array" || rClause.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal AND operation in class " + this.currentClass + " of method " + this.currentMethod + ", clause must be of type boolean");
    if(rClause != "boolean"){
      this.IsVarDeclared(rClause);
      this.IsVarBoolean(rClause);
    }
  }

  // Check if Compare Expression is allowed for the given primary expressions
  public void CheckArithmeticExpression(String lPrimaryExpr,String rPrimaryExpr) throws StatiCheckingException
  {
    // check left primary expression
    if(lPrimaryExpr == "boolean" || lPrimaryExpr == "this" || lPrimaryExpr == "int array" || lPrimaryExpr.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal COMPARE operation in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type int");
    if(lPrimaryExpr != "int"){
      this.IsVarDeclared(lPrimaryExpr);
      this.IsVarInt(lPrimaryExpr);
    }

    // check right primary expression
    if(rPrimaryExpr == "boolean" || rPrimaryExpr == "this" || rPrimaryExpr == "int array" || rPrimaryExpr.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal COMPARE operation in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type int");
    if(rPrimaryExpr != "int"){
      this.IsVarDeclared(rPrimaryExpr);
      this.IsVarInt(rPrimaryExpr);
    }
  }

  // Check if the array lookup operation is legal
  public void CheckArrayLookUp(String Arr,String Index) throws StatiCheckingException
  {
    // check if the first expr is an array
    if(Arr == "boolean" || Arr == "this" || Arr.startsWith("/") || Arr == "int" )
      throw new StatiCheckingException("\n✗ Illegal array look up in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type array");
    if(Arr != "int array"){
      this.IsVarDeclared(Arr);
      this.IsVarArray(Arr);
    }
    // check if the  index is int
    if(Index == "boolean" || Index == "this" || Index == "int array" || Index.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal array look up in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type int");
    if(Index != "int"){
      this.IsVarDeclared(Index);
      this.IsVarInt(Index);
    }
  }

  // Check if the array length operation is legal
  public void CheckArrayLength(String Arr) throws StatiCheckingException
  {
    // check if the first expr is an array
    if(Arr == "boolean" || Arr == "this" || Arr.startsWith("/") || Arr == "int" )
      throw new StatiCheckingException("\n✗ Illegal array length operation in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type array");
    if(Arr != "int array"){
      this.IsVarDeclared(Arr);
      this.IsVarArray(Arr);
    }
  }

}
