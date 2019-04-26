package symboltable;
import java.util.*;

// Class containing data for a Class
public class ClassInfo {
  public String extendsFrom,extendsTo;
  public Map <String,String> class_variables_data; // [ class variable name , type  ]
  public Map <String,MethodInfo> methods_data; // [ class methods name , methods info ]

  public ClassInfo(){
    class_variables_data = new HashMap<String,String>();
    methods_data = new HashMap<String,MethodInfo>();
  }

  public void InsertMethodToClass(String MethodsName){
    MethodInfo methodsInf = new MethodInfo();
    methods_data.put(MethodsName,methodsInf);
  }

  public void InsertMethodToClass(String MethodsName,MethodInfo methodsInf){
    methods_data.put(MethodsName,methodsInf);
  }

  public void InsertFieldToClass(String fieldName, String fieldType){
    class_variables_data.put(fieldName,fieldType);
  }

  // List all methods in a class
  public void ListMethods(){
    System.out.println("    Class contains the following methods:");
    Set< Map.Entry <String,MethodInfo> > st = methods_data.entrySet();
     for (Map.Entry<String,MethodInfo> cur:st){
         System.out.print(cur.getKey()+", ");
     }
     System.out.println("");
  }

  // List all methods in detailed form
  public void ListMethodsDetailed(){
    System.out.println("    Class contains the following methods:");
    Set< Map.Entry <String,MethodInfo> > st = methods_data.entrySet();
    for (Map.Entry<String,MethodInfo> cur:st){
      System.out.println("    • " + cur.getKey());
      cur.getValue().ListArguments();
      cur.getValue().ListVariables();
    }
    System.out.println("");
  }

  // List all fields of the class
  public void ListFields(){
    System.out.println("    Class contains the following fields:");
    Set< Map.Entry <String,String> > st = class_variables_data.entrySet();
    for (Map.Entry<String,String> cur:st){
      System.out.print("      " + cur.getKey()+":");
      System.out.println(cur.getValue());    }
    System.out.println("");
  }

}
