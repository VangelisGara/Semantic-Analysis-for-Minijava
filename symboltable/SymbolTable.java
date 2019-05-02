package symboltable;
import java.util.*;
import typecheckexception.*;

// Symbol table for our semantic analysis - type checking
public class SymbolTable {
  public Map <String,ClassInfo> classes_data; // [ class name , class info ]

  // Constructor
  public SymbolTable(){
    classes_data = new HashMap<String,ClassInfo>();
  }

  // Insert a class to synbol table
  public void InsertClassToSymbolTable(String className, ClassInfo classInfo) throws TypeCheckException
  {
    classes_data.put(className,classInfo);
  }

  // List all classes in symbol table
  public void ListClasses(){
    System.out.println("Symbol Table contains the following classes:");
    Set< Map.Entry< String,ClassInfo> > st = classes_data.entrySet();
     for (Map.Entry< String,ClassInfo> cur:st){
         System.out.print(cur.getKey()+", ");
     }
     System.out.println("");
  }

  // List everything in symbol table
  public void ListEverything(){
    System.out.println(" \n\n----------------------------------\n");
    System.out.println(" Symbol Table contains the following classes:");
    Set< Map.Entry <String,ClassInfo> > st = classes_data.entrySet();
     for (Map.Entry <String,ClassInfo> cur:st){
         System.out.println(" • " + cur.getKey() + " and extends from " + cur.getValue().extendsFrom);
         cur.getValue().ListFields();
         cur.getValue().ListMethodsDetailed();
     }
     System.out.println("");
     System.out.println(" \n\n----------------------------------\n");
  }
}
