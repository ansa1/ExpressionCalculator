# ExpressionCalculator

Takes an expression from the input, and produce
the result of calculations considering the precedence of operators (and parentheses, of course):
1. Factor (*)
2. Term (+ -)
3. Relation (< > =)


## Dependencies

The project programming language is Java 8. Thus, to be enable to build and run the application you are supposed to have Java jdk1.8 
and jre1.8 installed and the appropriate environment variables set.


## How to run

Main Class is Program.java.


To run the program the following commands should be used in the root project directory:

`javac Program.java`
<br/>
`java Program`


## Accuracy

Inaccuracy of calculations is expected in case of operations on integers which are beyond 
java.lang.Long boundaries ( a minimum value of -263 and a maximum value of 263-1), since
the ExpressionCalculator is intended to be used only with integers for the time being. 
