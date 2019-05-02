import syntaxtree.*;
import typecheck.*;
import visitor.*;
import java.io.*;
import typecheckexception.*;

class Main {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: Please add input files <inputFile>");
      System.exit(1);
    }
    FileInputStream fis = null;
    for(int i=0; i<args.length; i++){
      try {
        System.out.println("\n▸ Static Checking file: " + args[i] + "\n");
        fis = new FileInputStream(args[i]);
        MiniJavaParser parser = new MiniJavaParser(fis);
        System.err.println(" 1.Program parsed successfully.");
        // Get the root of the tree
        Goal root = parser.Goal();
        // Populate the symbol table
        STPVisitor SymbolTablePopulator = new STPVisitor();
        root.accept(SymbolTablePopulator, null);
        SymbolTablePopulator.getSymbolTable().ListEverything();
        // Type check the program
        TCVisitor TypeChecker = new TCVisitor(SymbolTablePopulator.getSymbolTable());
        root.accept(TypeChecker,null);
      }
      catch (ParseException ex) {
        System.out.println(ex.getMessage());
      }
      catch (TypeCheckException ex){
        System.err.println(ex);
      }
      catch (FileNotFoundException ex) {
        System.err.println(ex.getMessage());
      }
      finally {
        try {
          if (fis != null) fis.close();
        }
        catch (IOException ex) {
          System.err.println(ex.getMessage());
        }
      }
    }
  }
}
