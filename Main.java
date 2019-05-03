import java.io.*;
import syntaxtree.*;
import staticheckingexception.*;
import typecheck.*;
import visitor.*;

class Main {
  public static void main(String[] args){
    if (args.length < 1){
      System.err.println("Please add input files");
      System.exit(1);
    }
    FileInputStream fis = null;
    // Static Checking for every file given as input
    for(int i=0; i<args.length; i++){
      try {
        System.out.println("\n▸ Static Checking file: " + args[i] + "\n");
        fis = new FileInputStream(args[i]);
        MiniJavaParser parser = new MiniJavaParser(fis);
        System.err.println(" 1.Program parsed successfully. \n\n\n");
        Goal root = parser.Goal(); // get the root of the tree
        // Populate the symbol table
        STPVisitor SymbolTablePopulator = new STPVisitor();
        root.accept(SymbolTablePopulator, null);
        SymbolTablePopulator.getSymbolTable().ListEverything();
        System.out.println("\n 2.Populating the Symbol Table - Uniqueness Checks finished successfully. \n\n\n");
        // Type check the program
        TCVisitor TypeChecker = new TCVisitor(SymbolTablePopulator.getSymbolTable());
        root.accept(TypeChecker,null);
        System.out.println("\n 3.Type Cheking the input finished successfully. \n\n\n");
        System.out.println("\n ✓ Program passed the static checking. \n\n\n");
      }
      catch (ParseException ex) {
        System.out.println(ex.getMessage());
      }
      catch (StatiCheckingException ex){
        System.err.println("\n\n" + ex);
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
