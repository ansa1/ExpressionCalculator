# ExpressionCalculator

Takes an expression from the input, and produce
the result of calculations considering the precedence of operators (and parentheses, of course):

1. Factor (*)
2. Term (+ -)
3. Relation (< > =)


## Dependencies

To be able to build and run the application you are supposed to have the following software installed:

 * Oracle JDK 8 ver. 181
 * Maven ver. 3.5.4  
 
 
  and the appropriate environment variables set.


## How to run

To run the program the following commands should be used in the project root directory:

`mvn package`
<br/>
`cd target`
<br/>
`java -jar ExpressionCalculator-1.0-SNAPSHOT.jar`

After that the calculations results will be written to `./data/out.txt` 
line by line based on the provided input expressions in `./data/in.txt`.  

In order to run only the implemented JUnit tests you can simply use:


`mvn test` 

## Accuracy

Inaccuracy of calculations is expected in case of operations on integers which are beyond 
java.lang.Long boundaries ( a minimum value of -263 and a maximum value of 263-1), since
the ExpressionCalculator is intended to be used only with integers for the time being. 
