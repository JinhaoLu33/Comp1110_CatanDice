package comp1110.ass2;

import java.util.*;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestBuildPlan {

    private String errorPrefix(String target, int[] rv, String board_state) {
	StringJoiner j = new StringJoiner(",");
	for (int i = 0; i < 6; i++)
	    j.add(Integer.toString(rv[i]));
        return "CatanDice.buildPlan(" + target + ", " + board_state + ", " + j.toString() + ")";
    }

    private void test(String t, int[] rv, String b, String[][] plans) {
        String errorPrefix = errorPrefix(t, rv, b);
        String[] out = CatanDice.buildPlan(t, b, rv);
	if (plans == null) {
	    assertEquals(null, out, errorPrefix);
	}
	else {
	    String msg = errorPrefix + " returned " + PlanUtils.planToString(out) + " expected one of " + PlanUtils.planSetToString(plans);
	    assertTrue(PlanUtils.equalsOneOf(out, plans), msg);
	}
    }

    static String[] unsolvable_board = {
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1",
	"R0,S3,R1",
	"R0,S3,R1",
	"R0,S3,R1",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3",
	"R0,S3,R1",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,J1,J2"
    };
    
    static int[][] unsolvable_resources = {
	{ 0,1,0,1,2,2 } ,
	{ 0,0,1,3,2,0 } ,
	{ 0,1,1,0,2,2 } ,
	{ 1,3,0,0,1,1 } ,
	{ 0,0,0,3,3,0 } ,
	{ 0,0,1,2,3,0 } ,
	{ 1,2,1,0,1,1 } ,
	{ 0,2,1,0,2,1 } ,
	{ 1,0,0,2,2,1 } ,
	{ 2,0,1,0,3,0 } ,
	{ 0,1,1,2,0,2 } ,
	{ 0,0,1,0,5,0 } ,
	{ 0,0,3,0,3,0 } ,
	{ 1,0,0,0,5,0 } ,
	{ 1,0,1,0,4,0 } ,
	{ 1,0,2,0,3,0 } ,
	{ 2,1,2,0,1,0 } ,
	{ 0,1,1,0,2,2 } ,
	{ 3,0,0,1,2,0 } ,
	{ 1,2,1,0,1,1 } ,
	{ 0,2,0,3,0,1 } ,
	{ 0,1,0,2,0,3 } ,
	{ 0,0,1,3,1,1 } ,
	{ 1,3,0,0,0,2 } ,
	{ 0,2,3,0,1,0 } ,
	{ 0,2,2,0,0,2 } ,
	{ 0,2,3,0,1,0 } ,
	{ 0,2,2,2,0,0 } ,
	{ 4,0,1,1,0,0 } ,
	{ 2,0,2,1,1,0 } ,
	{ 1,2,1,1,0,1 } ,
	{ 0,0,0,4,2,0 } 
    };
    
    static String[] unsolvable_target = {
	"S7",
	"S7",
	"S7",
	"C12",
	"S5",
	"S7",
	"C12",
	"S5",
	"S4",
	"R12",
	"R12",
	"R12",
	"R2",
	"R3",
	"R8",
	"R8",
	"R7",
	"R3",
	"J1",
	"R2",
	"R3",
	"R3",
	"J4",
	"C7",
	"J5",
	"J5",
	"J2",
	"J4",
	"C7",
	"J5",
	"C7",
	"S4"
    };

    public void testUnsolvable(String[] board, int[][] resources, String[] target) {
	for (int i = 0; i < board.length; i++) {
	    test(target[i], resources[i], board[i], null);
	}
    }

    @Test
    public void testUnsolvable() {
	testUnsolvable(unsolvable_board, unsolvable_resources, unsolvable_target);
    }

    static String[] trivial_board = {
	"R0,S3,R1",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3",
	"R0,S3,R1",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6"
    };
    static int[][] trivial_resources = {
	{ 1,1,1,0,0,3 } ,
	{ 1,2,2,0,0,1 } ,
	{ 3,2,1,0,0,0 } ,
	{ 1,1,1,1,1,1 } ,
	{ 2,0,1,2,1,0 } ,
	{ 0,1,1,2,2,0 } ,
	{ 4,2,0,0,0,0 } ,
	{ 0,0,1,1,1,3 } 
    };
    static String[] trivial_target = {
	"J1",
	"J3",
	"C12",
	"J4",
	"R2",
	"S5",
	"C7",
	"R7"
    };
    static String[][][] trivial_plans = {
	{{"build J1"}},
	{{"build J3"}},
	{{"build C12"}},
	{{"build J4"}},
	{{"build R2"}},
	{{"build S5"}},
	{{"build C7"}},
	{{"build R7"}}
    };

    static String[] simple_board = {
	"R0,S3,R1,J1,J2",
	"R0,S3,R1",
	"R0,S3,R1",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6"
    };
    static int[][] simple_resources = {
	{ 2,2,2,0,0,0 } ,
	{ 0,0,1,3,2,0 } ,
	{ 0,1,1,2,2,0 } ,
	{ 0,1,0,2,2,1 } ,
	{ 2,2,2,0,0,0 } ,
	{ 2,2,2,0,0,0 } ,
	{ 0,0,2,2,2,0 } 
    };
    static String[] simple_target = {
	"J4",
	"R3",
	"S4",
	"R8",
	"J2",
	"J5",
	"R12"
    };
    static String[][][] simple_plans = {
	{{"build J3","build J4"}},
	{{"build R2","build R3"}},
	{{"build R2","build S4"}},
	{{"build R7","build R8"}},
	{{"build J1","build J2"}},
	{{"build J4","build J5"}},
	{{"build R7","build R12"}}
    };

    static String[] noswap_board = {
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3",
	"R0,S3,R1",
	"R0,S3,R1",
	"R0,S3,R1",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3"
    };
    static int[][] noswap_resources = {
	{ 1,1,0,1,0,3 } ,
	{ 0,1,0,0,3,2 } ,
	{ 0,0,1,1,2,2 } ,
	{ 0,1,1,0,2,2 } ,
	{ 0,0,0,3,1,2 } ,
	{ 1,1,0,0,2,2 } ,
	{ 2,2,0,0,0,2 } ,
	{ 0,2,0,2,0,2 } ,
	{ 0,0,0,1,3,2 } 
    };
    static String[] noswap_target = {
	"R7",
	"R7",
	"R12",
	"S5",
	"R3",
	"R2",
	"C7",
	"R2",
	"R8"
    };
    static String[][][] noswap_plans = {
	{{"trade 4","build R7"}},
	{{"trade 3","build R7"}},
	{{"build R7","trade 3","build R12"}, {"trade 3","build R7","build R12"}},
	{{"trade 3","build S5"}},
	{{"build R2","trade 4","build R3"}, {"trade 4","build R2","build R3"}},
	{{"trade 3","build R2"}},
	{{"trade 0","build C7"}},
	{{"trade 4","build R2"}},
	{{"build R7","trade 3","build R8"}, {"trade 3","build R7","build R8"}}
    };

    static String[] general_board = {
	"R0,S3,R1",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,J1,J2",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",
	"R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3"
    };
    static int[][] general_resources = {
	{ 1,2,2,0,1,0 } ,
	{ 2,2,0,0,1,1 } ,
	{ 2,2,1,0,0,1 } ,
	{ 2,3,0,1,0,0 } ,
	{ 1,2,2,0,0,1 } ,
	{ 2,2,1,0,1,0 } ,
	{ 3,1,2,0,0,0 } ,
	{ 2,2,0,1,0,1 } ,
	{ 1,2,2,0,1,0 } ,
	{ 0,1,3,0,2,0 } ,
	{ 1,2,2,1,0,0 } ,
	{ 0,0,1,3,2,0 } ,
	{ 0,3,0,1,2,0 } ,
	{ 0,0,2,2,2,0 } ,
	{ 3,0,1,0,2,0 } ,
	{ 4,1,0,0,1,0 } ,
	{ 3,1,1,0,0,1 } ,
	{ 3,0,1,1,1,0 } ,
	{ 0,1,0,2,3,0 } ,
	{ 0,1,2,1,1,1 } ,
	{ 2,1,2,0,1,0 } 
    };
    static String[] general_target = {
	"J2",
	"C12",
	"J4",
	"J4",
	"J4",
	"J5",
	"J4",
	"C7",
	"J5",
	"J3",
	"J2",
	"S4",
	"S5",
	"S5",
	"J3",
	"C7",
	"C12",
	"J4",
	"S5",
	"J4",
	"J5"
    };
    static String[][][] general_plans = {
	{{"build J1","swap 4 0","build J2"}},
	{{"swap 4 0","build C12"}, {"swap 5 0","build C12"}},
	{{"build J3","swap 5 2","build J4"}},
	{{"swap 0 2","build J4"}, {"swap 1 2","build J4"}, {"swap 3 2","build J4"}},
	{{"build J3","swap 5 0","build J4"}, {"swap 5 0","build J3","build J4"}},
	{{"build J4","swap 4 2","build J5"}, {"swap 4 2","build J4","build J5"}},
	{{"build J3","swap 0 1","build J4"}, {"swap 0 1","build J3","build J4"}},
	{{"swap 3 0","build C7"}, {"swap 5 0","build C7"}},
	{{"build J4","swap 4 0","build J5"}, {"swap 4 0","build J4","build J5"}},
	{{"swap 2 0","build J3"}, {"swap 4 0","build J3"}},
	{{"build J1","swap 3 0","build J2"}},
	{{"build R2","swap 3 1","build S4"}, {"swap 3 1","build R2","build S4"}},
	{{"swap 1 2","build S5"}, {"swap 4 2","build S5"}},
	{{"swap 2 1","build S5"}, {"swap 3 1","build S5"}, {"swap 4 1","build S5"}},
	{{"swap 0 1","build J3"}, {"swap 4 1","build J3"}},
	{{"swap 0 1","build C7"}, {"swap 4 1","build C7"}},
	{{"swap 2 1","build C12"}, {"swap 5 1","build C12"}},
	{{"swap 0 1","build J4"}, {"swap 3 1","build J4"}, {"swap 4 1","build J4"}},
	{{"swap 3 2","build S5"}, {"swap 4 2","build S5"}},
	{{"swap 2 0","build J4"}, {"swap 3 0","build J4"}, {"swap 4 0","build J4"}, {"swap 5 0","build J4"}},
	{{"build J4","swap 4 1","build J5"}, {"swap 4 1","build J4","build J5"}}
    };
    
    public void testSolvable(String[] board, int[][] resources, String[] target, String[][][] plans) {
	for (int i = 0; i < board.length; i++) {
	    test(target[i], resources[i], board[i], plans[i]);
	}
    }
	
    @Test
    public void testTrivial() {
	testSolvable(trivial_board, trivial_resources, trivial_target, trivial_plans);
    }
    
    @Test
    public void testSimple() {
	testSolvable(simple_board, simple_resources, simple_target, simple_plans);
    }
    
    @Test
    public void testNoSwap() {
	testSolvable(noswap_board, noswap_resources, noswap_target, noswap_plans);
    }

    @Test
    public void testGeneral() {
	testSolvable(general_board, general_resources, general_target, general_plans);
    }

    /* A collection of hand-crafted test cases, with complex plans */
    @Test
    public void testComplex() {
	String[] board = {
	    "R0,R1,S3,J1,J2,J3",
	    "R0,R1,S3,J1,J2,J3",
	    "R0,R1,S3",
	    "R0,R1,R2",
	    "R0,R1,R2,S3,K1,K2,K3",
	    "R0,R1,R2,S3,J1,J2,J3",
	    "R0,R1,S3,J1,J2,J3",
	    "R0,R1,S3,J1,J2,J3"
	};
	int[][] resources = {
	    { 3,1,0,0,0,2 } ,
	    { 0,0,0,1,1,4 } ,
	    { 0,1,1,2,2,0 } ,
	    { 0,1,1,2,2,0 } ,
	    { 0,0,0,1,1,4 } ,
	    { 0,0,0,1,1,4 } ,
	    { 0,0,0,2,2,2 } ,
	    { 0,0,3,0,0,3 } 
	};
	String[] target = {
	    "C7",
	    "S4",
	    "S4",
	    "S4",
	    "S4",
	    "S4",
	    "S4",
	    "R2"
	};
	String[][][] plans = {
	    {{"trade 1","build C7"}, {"swap 5 1","build C7"}}, // 0
	    null, // 1
	    {{"build R2","build S4"}}, // 2
	    null, // 3
	    {{"trade 1","trade 2","build S4"}, {"trade 2","trade 1","build S4"}}, // 4
	    {{"trade 1","trade 2","build S4"}, {"trade 1","swap 5 2","build S4"}, {"trade 2","trade 1","build S4"}, {"trade 2","swap 5 1","build S4"}, {"swap 5 1","trade 2","build S4"}, {"swap 5 1","swap 5 2","build S4"}, {"swap 5 2","trade 1","build S4"}, {"swap 5 2","swap 5 1","build S4"}}, // 5
	    {{"build R2","swap 5 1","swap 5 2","build S4"}, {"build R2","swap 5 2","swap 5 1","build S4"}, {"swap 5 1","build R2","swap 5 2","build S4"}, {"swap 5 1","swap 5 2","build R2","build S4"}, {"swap 5 2","build R2","swap 5 1","build S4"}, {"swap 5 2","swap 5 1","build R2","build S4"}}, // 6
	    {{"trade 4","swap 5 1","swap 2 0","build J4","swap 2 3","build R2"}, {"swap 2 0","trade 4","swap 5 1","build J4","swap 2 3","build R2"}, {"swap 2 0","swap 2 1","build J4","trade 4","swap 5 3","build R2"}, {"swap 2 0","swap 2 1","build J4","swap 5 3","trade 4","build R2"}, {"swap 2 0","swap 2 1","trade 4","build J4","swap 5 3","build R2"}, {"swap 2 1","trade 4","swap 5 0","build J4","swap 2 3","build R2"}, {"swap 2 1","swap 2 0","build J4","trade 4","swap 5 3","build R2"}, {"swap 2 1","swap 2 0","build J4","swap 5 3","trade 4","build R2"}, {"swap 2 1","swap 2 0","trade 4","build J4","swap 5 3","build R2"}, {"swap 2 1","swap 5 0","build J4","swap 2 3","trade 4","build R2"}, {"swap 2 1","swap 5 0","trade 4","build J4","swap 2 3","build R2"}, {"swap 5 1","swap 2 0","build J4","swap 2 3","trade 4","build R2"}, {"swap 5 1","swap 2 0","trade 4","build J4","swap 2 3","build R2"}} // 7
	};
	for (int i = 0; i < 7; i++) {
	    test(target[i], resources[i], board[i], plans[i]);
	}
    }

    public static void main(String[] args) {
	TestBuildPlan tests = new TestBuildPlan();
	System.out.println("testing...");
	tests.testTrivial();
	tests.testSimple();
	tests.testNoSwap();
	tests.testGeneral();
	tests.testUnsolvable();
	tests.testComplex();
	System.out.println("all done!");
    }
}
