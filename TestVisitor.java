import syntaxtree.*;
import visitor.GJDepthFirst;
import symboltable.*;

public class TestVisitor extends GJDepthFirst <String,String> {
  SymbolTable ST;
  public String visit(VarDeclaration n,String argu){
    System.out.println("We are in Var Declaration");
    String vartype = n.f0.accept(this,null);
    String varname = n.f1.accept(this,null);
    System.out.println(vartype);
    System.out.println(varname);
    return "vardecl done";
  }

  public String visit(AssignmentStatement n,String argu) {
    System.out.println("We are in Assign Statement");
    String dest = n.f0.accept(this,null);
    String val = n.f2.accept(this,null);
    System.out.println(dest);
    System.out.println(val);
    return "assigndone";
  }

  public String visit(IntegerType n, String argu) {
     return n.f0.toString();
  }

  public String visit(Identifier n, String argu) {
     return n.f0.toString();
  }

}
