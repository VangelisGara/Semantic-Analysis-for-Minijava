package symboltable;
import java.util.*;

// Class containing data about a method
public class MethodInfo {
  public String type; // method's type
  public LinkedHashMap <String,String> arguments_data; // [ argument name , type ]
  public Map <String,String> method_variables_data; // [ methods variable name , type ]

  // Constructor
  public MethodInfo(){
    arguments_data = new LinkedHashMap<String,String>();
    method_variables_data = new HashMap<String,String>();
  }

  // Insert a method argument
  public void InsertArgument(String argName,String type){
    arguments_data.put(argName,type);
  }

  // Insert a method variable
  public void InsertVar(String varName, String varType){
    method_variables_data.put(varName,varType);
  }

  // List all variables the method has
  public void ListVariables(){
    System.out.println("        Method contains the following variables:");
    Set< Map.Entry <String,String> > st = method_variables_data.entrySet();
     for (Map.Entry<String,String> cur:st){
         System.out.print("         " + cur.getKey()+":");
         System.out.println(cur.getValue());
     }
     System.out.println("");
  }

  // List all arguments the method has
  public void ListArguments(){
    System.out.println("        Method contains the following arguments:");
    Set< Map.Entry <String,String> > st = arguments_data.entrySet();
     for (Map.Entry<String,String> cur:st){
         System.out.print("         " + cur.getKey()+":");
         System.out.println(cur.getValue());
     }
     System.out.println("");
  }
}
