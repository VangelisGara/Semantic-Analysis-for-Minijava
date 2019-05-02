import syntaxtree.*;
import typecheck.*;
import visitor.*;
import java.io.*;

class Main {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: java Driver <inputFile>");
      System.exit(1);
    }
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(args[0]);
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
