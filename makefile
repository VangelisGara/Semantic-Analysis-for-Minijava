all: compile

compile:
	java -jar ../jtb132di.jar minijava.jj
	java -jar ../javacc5.jar minijava-jtb.jj
	javac Main.java

clean:
	rm -f *.class *~
	rm -f ./symboltable/*.class
	rm -f ./typecheck/*.class
	rm -f ./StatiCheckingException/*.class
