import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser;

    @Test
    public void testTerm() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        parser = new Parser("85650+(14124-12498)+(1525+((4124+2-14124)+6437)-((6326+3236)-233+124214-24124+(12414-1412124)))");
        Method method = parser.getClass().getDeclaredMethod("parseTerm");
        method.setAccessible(true);
        long actual = ((Expression) method.invoke(parser)).calculate();
        long expected = 1375531;
        assertEquals(expected, actual);
    }

    @Test
    public void testRelation() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        parser = new Parser("((132=123)>24)<((24=24)<245>(2142=241<(1241>241))<2141>(41=(124<241))>4124)");
        Method method = parser.getClass().getDeclaredMethod("parseRelation");
        method.setAccessible(true);
        long actual = ((Expression) method.invoke(parser)).calculate();
        long expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testFactor() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        parser = new Parser("3*(2*((2*5)*4*(3*3)*(3*3*9*8*(9*6*(8*7)))))");
        Method method = parser.getClass().getDeclaredMethod("parseFactor");
        method.setAccessible(true);
        long actual = ((Expression) method.invoke(parser)).calculate();
        long expected = 4232632320L;
        assertEquals(expected, actual);
    }

    @Test
    public void testParseRelationOperator1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        parser = new Parser("=1");
        Method method = parser.getClass().getDeclaredMethod("parseRelOperator");
        method.setAccessible(true);
        Relation.Opcode actual = (Relation.Opcode) method.invoke(parser);
        Relation.Opcode expected = Relation.Opcode.equal;
        assertEquals(expected, actual);
    }

    @Test
    public void testParseRelationOperator2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        parser = new Parser("<1");
        Method method = parser.getClass().getDeclaredMethod("parseRelOperator");
        method.setAccessible(true);
        Relation.Opcode actual = (Relation.Opcode) method.invoke(parser);
        Relation.Opcode expected = Relation.Opcode.less;
        assertEquals(expected, actual);
    }

    @Test
    public void testParseRelationOperator3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        parser = new Parser(">1");
        Method method = parser.getClass().getDeclaredMethod("parseRelOperator");
        method.setAccessible(true);
        Relation.Opcode actual = (Relation.Opcode) method.invoke(parser);
        Relation.Opcode expected = Relation.Opcode.greater;
        assertEquals(expected, actual);
    }


    @Test
    public void testParseTermOperator1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        parser = new Parser("+1");
        Method method = parser.getClass().getDeclaredMethod("parseTermOperator");
        method.setAccessible(true);
        Term.Opcode actual = (Term.Opcode) method.invoke(parser);
        Term.Opcode expected = Term.Opcode.plus;
        assertEquals(expected, actual);
    }

    @Test
    public void testParseTermOperator2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        parser = new Parser("-1");
        Method method = parser.getClass().getDeclaredMethod("parseTermOperator");
        method.setAccessible(true);
        Term.Opcode actual = (Term.Opcode) method.invoke(parser);
        Term.Opcode expected = Term.Opcode.minus;
        assertEquals(expected, actual);
    }

    @Test
    public void testParseFacOperator1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        parser = new Parser("*1");
        Method method = parser.getClass().getDeclaredMethod("parseFacOperator");
        method.setAccessible(true);
        Factor.Opcode actual = (Factor.Opcode) method.invoke(parser);
        Factor.Opcode expected = Factor.Opcode.mult;
        assertEquals(expected, actual);
    }

    @Test
    public void testPrimary1() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        parser = new Parser("(((((54747)))))");
        Method method = parser.getClass().getDeclaredMethod("parsePrimary");
        method.setAccessible(true);
        long actual = ((Expression) method.invoke(parser)).calculate();
        long expected = 54747;
        assertEquals(expected, actual);
    }

    @Test
    public void testPrimary2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        parser = new Parser("(((((-1234567)))))");
        Method method = parser.getClass().getDeclaredMethod("parsePrimary");
        method.setAccessible(true);
        long actual = ((Expression) method.invoke(parser)).calculate();
        long expected = -1234567;
        assertEquals(expected, actual);
    }


    @Test
    public void testCalc1() {
        parser = new Parser("(2+2*2+(2*2+2)*2+(2*2+2*2+2)*2)*2+2*(2+2)+2");
        long actual = parser.parse().calculate();
        long expected = 86;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalc2() {
        parser = new Parser("1251=42*2>2412-24<4124*(1=2<2414>142)=2*500");
        long actual = parser.parse().calculate();
        long expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalc3() {
        parser = new Parser("21-22424-(1412*24142)*12412*0");
        long actual = parser.parse().calculate();
        long expected = -22403;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalc4() {
        parser = new Parser("1*((2+3)+(4+5)*(6+7)-(8*9+10*(11+12*13)*(14*15))+(((16-17)*18-19)*20");
        long actual = parser.parse().calculate();
        long expected = -351390;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalc5() {
        parser = new Parser("1*((2+3)+(4+5)*(6+7)-(8*9+10*(11+12*13)*(14*15))+(((16-17)*18-19)*20");
        long actual = parser.parse().calculate();
        long expected = -351390;
        assertEquals(expected, actual);
    }

    @Test
    public void testParse1() {
        parser = new Parser("1+2*(412+(251+(523+12))*2)*100+(2+1)+(1241*(12-(12414+141*211)))");
        String actual = parser.parse().toJSONInline(0);
        String expected = "{\"+\": [{\"+\": [{\"+\": [1, {\"*\": [{\"*\": [2, {\"+\": [412, {\"*\": [{\"+\": [251, {\"+\": [523, 12]}]}, 2]}]}]}, 100]}]}, {\"+\": [2, 1]}]}, {\"*\": [1241, {\"-\": [12, {\"+\": [12414, {\"*\": [141, 211]}]}]}]}]}";
        assertEquals(expected, actual);
    }

    @Test
    public void testParse2() {
        parser = new Parser("(1=2)*((346=623>5125+12512)<122521+1224)=1*12512125*(0+125125)*0>12");
        String actual = parser.parse().toJSONInline(0);
        String expected = "{\">\": [{\"=\": [{\"*\": [{\"=\": [1, 2]}, {\"<\": [{\">\": [{\"=\": [346, 623]}, {\"+\": [5125, 12512]}]}, {\"+\": [122521, 1224]}]}]}, {\"*\": [{\"*\": [{\"*\": [1, 12512125]}, {\"+\": [0, 125125]}]}, 0]}]}, 12]}";
        assertEquals(expected, actual);
    }

    @Test
    public void testParse3() {
        parser = new Parser("(52*(5125*(215*(236-7548)*21)-1251)-1512251");
        String actual = parser.parse().toJSONInline(0);
        String expected = "{\"-\": [{\"*\": [52, {\"-\": [{\"*\": [5125, {\"*\": [{\"*\": [215, {\"-\": [236, 7548]}]}, 21]}]}, 1251]}]}, 1512251]}";
        assertEquals(expected, actual);
    }

    @Test
    public void testParse4() {
        parser = new Parser("(12*21-12112*251*((1-36236-262)-272+2336236-(263+2362))-(236*32+(23632*23))-(236+236)-236+23769)-2236");
        String actual = parser.parse().toJSONInline(0);
        String expected = "{\"-\": [{\"+\": [{\"-\": [{\"-\": [{\"-\": [{\"-\": [{\"*\": [12, 21]}, {\"*\": [{\"*\": [12112, 251]}, {\"-\": [{\"+\": [{\"-\": [{\"-\": [{\"-\": [1, 36236]}, 262]}, 272]}, 2336236]}, {\"+\": [263, 2362]}]}]}]}, {\"+\": [{\"*\": [236, 32]}, {\"*\": [23632, 23]}]}]}, {\"+\": [236, 236]}]}, 236]}, 23769]}, 2236]}";
        assertEquals(expected, actual);
    }

    @Test
    public void testParse5() {
        parser = new Parser("12*((15*(2>51*(362*(34<23-(112)))))+2512-12512+2*22>235)=61-1212+(24<(859<56+125)-(12=51)=25)");
        String actual = parser.parse().toJSONInline(0);
        String expected = "{\"=\": [{\"*\": [12, {\">\": [{\"+\": [{\"-\": [{\"+\": [{\"*\": [15, {\">\": [2, {\"*\": [51, {\"*\": [362, {\"<\": [34, {\"-\": [23, 112]}]}]}]}]}]}, 2512]}, 12512]}, {\"*\": [2, 22]}]}, 235]}]}, {\"+\": [{\"-\": [61, 1212]}, {\"=\": [{\"<\": [24, {\"-\": [{\"<\": [859, {\"+\": [56, 125]}]}, {\"=\": [12, 51]}]}]}, 25]}]}]}";
        assertEquals(expected, actual);
    }
}