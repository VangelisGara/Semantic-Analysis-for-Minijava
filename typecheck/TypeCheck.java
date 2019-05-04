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

  // Check if NOT operation is allowed for the given clause
  public void CheckNotOperation(String clause) throws StatiCheckingException
  {
    if(clause == "an integer" || clause == "this" || clause == "integer array" || clause.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal NOT operation in class " + this.currentClass + " of method " + this.currentMethod + ", clause must be of type boolean");
    if(clause != "boolean"){
      if( this.IsVarDeclared(clause) ){
        // get the type of the var
        String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
        typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(clause);
        typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(clause);
        typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(clause);
        String superclass = ST.classes_data.get(currentClass).extendsFrom;
        if(superclass != "")
          typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(clause);

        // check if type is boolean
        if(typeGotFromMethodVar != "boolean" && typeGotFromMethodArg != "boolean" &&  typeGotFromField != "boolean" && typeGotFromSuperField != "boolean")
          throw new StatiCheckingException("\n✗ Illegal NOT operation for clause " + clause + " in class " + this.currentClass + " of method" + this.currentMethod + ", clause must be of type boolean");
      }
    }
  }

  // Check if AND operation is allowed for the given clause
  public void CheckAndOperation(String lClause,String rClause) throws StatiCheckingException
  {
    // check left clause
    if(lClause == "an integer" || lClause == "this" || lClause == "integer array" || lClause.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal AND operation in class " + this.currentClass + " of method " + this.currentMethod + ", clause must be of type boolean");
    if(lClause != "boolean"){
      if( this.IsVarDeclared(lClause) ){
        // get the type of the var
        String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
        typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(lClause);
        typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(lClause);
        typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(lClause);
        String superclass = ST.classes_data.get(currentClass).extendsFrom;
        if(superclass != "")
          typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(lClause);

        // check if type is boolean
        if(typeGotFromMethodVar != "boolean" && typeGotFromMethodArg != "boolean" &&  typeGotFromField != "boolean" && typeGotFromSuperField != "boolean")
          throw new StatiCheckingException("\n✗ Illegal AND operation for clause " + lClause + " in class " + this.currentClass + " of method" + this.currentMethod + ", clause must be of type boolean");
      }
    }
    // check right clause
    if(rClause == "an integer" || rClause == "this" || rClause == "integer array" || rClause.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal AND operation in class " + this.currentClass + " of method " + this.currentMethod + ", clause must be of type boolean");
    if(rClause != "boolean"){
      if( this.IsVarDeclared(rClause) ){
        // get the type of the var
        String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
        typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(rClause);
        typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(rClause);
        typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(rClause);
        String superclass = ST.classes_data.get(currentClass).extendsFrom;
        if(superclass != "")
          typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(rClause);

        // check if type is boolean
        if(typeGotFromMethodVar != "boolean" && typeGotFromMethodArg != "boolean" &&  typeGotFromField != "boolean" && typeGotFromSuperField != "boolean")
          throw new StatiCheckingException("\n✗ Illegal AND operation for clause " + rClause + " in class " + this.currentClass + " of method" + this.currentMethod + ", clause must be of type boolean");
      }
    }

  }

  // Check if Compare Expression is allowed for the given primary expressions
  public void CheckArithmeticExpression(String lPrimaryExpr,String rPrimaryExpr) throws StatiCheckingException
  {
    // check left primary expression
    if(lPrimaryExpr == "boolean" || lPrimaryExpr == "this" || lPrimaryExpr == "integer array" || lPrimaryExpr.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal COMPARE operation in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type int");
    if(lPrimaryExpr != "an integer"){
      if( this.IsVarDeclared(lPrimaryExpr) ){
        // get the type of the var
        String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
        typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(lPrimaryExpr);
        typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(lPrimaryExpr);
        typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(lPrimaryExpr);
        String superclass = ST.classes_data.get(currentClass).extendsFrom;
        if(superclass != "")
          typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(lPrimaryExpr);

        // check if type is boolean
        if(typeGotFromMethodVar != "int" && typeGotFromMethodArg != "int" &&  typeGotFromField != "int" && typeGotFromSuperField != "int")
          throw new StatiCheckingException("\n✗ Illegal COMPARE operation for expression " + lPrimaryExpr + " in class " + this.currentClass + " of method" + this.currentMethod + ", expression must be of type integer");
      }
    }
    // check right primary expression
    if(rPrimaryExpr == "boolean" || rPrimaryExpr == "this" || rPrimaryExpr == "integer array" || rPrimaryExpr.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal COMPARE operation in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type int");
    if(rPrimaryExpr != "an integer"){
      if( this.IsVarDeclared(rPrimaryExpr) ){
        // get the type of the var
        String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
        typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(rPrimaryExpr);
        typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(rPrimaryExpr);
        typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(rPrimaryExpr);
        String superclass = ST.classes_data.get(currentClass).extendsFrom;
        if(superclass != "")
          typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(rPrimaryExpr);

        // check if type is boolean
        if(typeGotFromMethodVar != "int" && typeGotFromMethodArg != "int" &&  typeGotFromField != "int" && typeGotFromSuperField != "int")
          throw new StatiCheckingException("\n✗ Illegal COMPARE operation for expression " + rPrimaryExpr + " in class " + this.currentClass + " of method" + this.currentMethod + ", expression must be of type int");
      }
    }
  }

  // Check if the array lookup operation is legal
  public void CheckArrayLookUp(String Arr,String Index) throws StatiCheckingException
  {
    // check if the first expr is an array
    if(Arr == "boolean" || Arr == "this" || Arr == "integer array" || Arr.startsWith("/") || Arr == "an integer" )
      throw new StatiCheckingException("\n✗ Illegal array look in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type array");
    this.IsVarDeclared(Arr);
    this.IsVarArray(Arr);
    // check if the  index is an integer
    if(Index == "boolean" || Index == "this" || Index == "integer array" || Index.startsWith("/") )
      throw new StatiCheckingException("\n✗ Illegal array look up in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type int");
    if(Index != "an integer"){
      if( this.IsVarDeclared(Index) ){
        // get the type of the var
        String typeGotFromField,typeGotFromMethodVar,typeGotFromMethodArg,typeGotFromSuperField=null;
        typeGotFromMethodVar = ST.classes_data.get(currentClass).methods_data.get(currentMethod).method_variables_data.get(Index);
        typeGotFromMethodArg = ST.classes_data.get(currentClass).methods_data.get(currentMethod).arguments_data.get(Index);
        typeGotFromField = ST.classes_data.get(currentClass).class_variables_data.get(Index);
        String superclass = ST.classes_data.get(currentClass).extendsFrom;
        if(superclass != "")
          typeGotFromSuperField = ST.classes_data.get(superclass).class_variables_data.get(Index);

        // check if type is boolean
        if(typeGotFromMethodVar != "int" && typeGotFromMethodArg != "int" &&  typeGotFromField != "int" && typeGotFromSuperField != "int")
          throw new StatiCheckingException("\n✗ Illegal array look up for expression " + Index + " in class " + this.currentClass + " of method" + this.currentMethod + ", expression must be of type integer");
      }
    }
  }

  // Check if the array lookup operation is legal
  public void CheckArrayLength(String Arr) throws StatiCheckingException
  {
    // check if the first expr is an array
    if(Arr == "boolean" || Arr == "this" || Arr == "integer array" || Arr.startsWith("/") || Arr == "an integer" )
      throw new StatiCheckingException("\n✗ Illegal array look in class " + this.currentClass + " of method " + this.currentMethod + ", expression must be of type array");
    this.IsVarDeclared(Arr);
    this.IsVarArray(Arr);
  }

}
