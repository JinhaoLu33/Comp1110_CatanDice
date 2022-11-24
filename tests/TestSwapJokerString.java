import comp1110.ass2.Game;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSwapJokerString {

    @Test
    public void testJokerToKnight() {
        //check boardState string
        //check joker at the start
        //check joker at the end

        Game gameTest1 = new Game("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3");
        gameTest1.SwapJokerString(1);
        assertEquals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,J2,J3", gameTest1.boardState);

        Game gameTest2 = new Game("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3");
        gameTest2.SwapJokerString(3);
        assertEquals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,K3", gameTest2.boardState);

        Game gameTest3 = new Game("J1,J2,J3,R0,S3,R1,C7,R2,S4,R3,R4,R5,R6");
        gameTest3.SwapJokerString(1);
        assertEquals("K1,J2,J3,R0,S3,R1,C7,R2,S4,R3,R4,R5,R6", gameTest3.boardState);

    }

    @Test
    public void testSwapped() {

        Game gameTest1 = new Game("J1,K2,J3,R0,S3,R1,C7,R2,S4,R3,R4,R5,R6");
        gameTest1.SwapJokerString(2);
        assertEquals("J1,K2,J3,R0,S3,R1,C7,R2,S4,R3,R4,R5,R6", gameTest1.boardState);

        Game gameTest2 = new Game("J1,J2,J3,J4,J5,K6,R0,S3,R1,C7,R2,S4,R3,R4,R5,R6");
        gameTest2.SwapJokerString(6);
        assertEquals("J1,J2,J3,J4,J5,K6,R0,S3,R1,C7,R2,S4,R3,R4,R5,R6", gameTest2.boardState);

    }

    @Test
    public void testNotBuilt() {
        Game gameTest1 = new Game("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6");
        gameTest1.SwapJokerString(1);
        assertEquals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6", gameTest1.boardState);

        Game gameTest2 = new Game("J1,J2,J3,J4,R0,S3,R1,C7,R2,S4,R3,R4,R5,R6");
        gameTest2.SwapJokerString(5);
        assertEquals("J1,J2,J3,J4,R0,S3,R1,C7,R2,S4,R3,R4,R5,R6", gameTest2.boardState);

    }


}
