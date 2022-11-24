package comp1110.ass2;

import java.util.*;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestPathTo {

    public static String pathToString(String[] path) {
	StringJoiner j = new StringJoiner(",");
	for (var s : path)
	    j.add(s);
	return j.toString();
    }

    private String errorPrefix(String target, String board_state) {
        return "CatanDice.pathTo(" + target + ", " + board_state + ")";
    }

    private void test(String t, String b, String[] p) {
        String errorPrefix = errorPrefix(t, b);
        String[] out = CatanDice.pathTo(t, b);
	String msg = errorPrefix + " returned " + pathToString(out) + " expected  " + pathToString(p);
	assertTrue(Arrays.equals(out, p), msg);
    }

    static String[] board_state = {
	"",
	"R0,R1,C7,S3",
	"R0,R1,C7,S3,R2,S4",
	"R0,R1,C7,R2,R3,R4,C12",
	"S3,J1,K2,K3,J4,J5",
	"",
	"",
	"R0,R1,C7,R2,R3,R4,C12,R5,R6,R7,R12,R13,C20,R14,R15,C30",
	"S3,R0,R2,S4,R3,R5,S5,R6,R7,S7,R8,R9,S9,R10,R11,S11",
	"S3,R0,R2,S4,R3,R5,S5,R6,R7,S7,R8,R9,S9,R10,R11,S11",
	"R0,R1,C7,R2,R3,R4,C12,R5,R6,R7,R12,R13,C20,R14,R15,C30",
	"S3,R0,R2,S4,R3,R5,S5,R6,R7,S7,R8,R9,S9,R10,R11,S11",
	"S3,J1,K2,K3,J4,J5",
	"R0,R1,C7",
	"R0,R1,C7,S3,R2,S4,R3,R5,S5",
	"S3,R0,R2,S4,R3,R5,S5",
	"S3,R0,R2,S4,R3,R5,S5,R6,R7,S7,R8,R9,S9",
	"S3,R0,R2,S4,R3,R5,S5,R6,R7,S7,R8,R9,S9",
    };
    static String[] target = {
	"S5",
	"S5",
	"S5",
	"S5",
	"S5",
	"S11",
	"C30",
	"S11",
	"C30",
	"S7",
	"S7",
	"C12",
	"S11",
	"R4",
	"R4",
	"R13",
	"R13",
	"R3",
    };
    static String[][] path = {
	{"R0","R2","R3","R5"},
	{"R2","R3","R5"},
	{"R3","R5"},
	{"R5"},
	{"R0","R2","R3","R5"},
	{"R0","R2","R3","R5","R6","R7","R8","R9","R10","R11"},
	{"R0","R2","R3","R5","R6","R7","R12","R13","R14","R15"},
	{"R8","R9","R10","R11"},
	{"R12","R13","R14","R15"},
	{},
	{},
	{"R4"},
	{"R0","R2","R3","R5","R6","R7","R8","R9","R10","R11"},
	{"R2","R3"},
	{},
	{"R6","R7","R12"},
	{"R12"},
	{},
    };

    @Test
    public void testAll() {
	for (int i = 0; i < board_state.length; i++) {
	    test(target[i], board_state[i], path[i]);
	}
    }

    public static void main(String[] args) {
	TestPathTo tests = new TestPathTo();
	System.out.println("testing...");
	tests.testAll();
	System.out.println("all done!");
    }

}
