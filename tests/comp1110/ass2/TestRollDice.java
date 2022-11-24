package comp1110.ass2;

import java.util.Arrays;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestRollDice {

    public static String resourceVectorToString(int[] rv) {
	StringJoiner j = new StringJoiner(",");
	for (int i = 0; i < 6; i++)
	    j.add(Integer.toString(rv[i]));
	return "{" + j.toString() + "}";
    }

    private String errorPrefix(int n, int[] rv) {
        return "CatanDice.rollDice(" + n + ", " + resourceVectorToString(rv) + ")";
    }

    private void checkReturn(int n, int[] in, int[] out) {
	String prefix = errorPrefix(n, in);
	int n_in = 0;
	int n_out = 0;
	for (int i = 0; i < 6; i++) {
	    n_in += in[i];
	    n_out += out[i];
	    assertTrue(out[i] >= in[i], prefix);
	}
	assertTrue(n_in + n == n_out, prefix);
    }

    private void test(int n, int[] in) {
	int[] copy_of_in = Arrays.copyOf(in, 6);
        CatanDice.rollDice(n, copy_of_in);
	checkReturn(n, in, copy_of_in);
    }

    private void test2(int n1, int n2, int[] in) {
	int[] copy1 = Arrays.copyOf(in, 6);
        CatanDice.rollDice(n1, copy1);
	checkReturn(n1, in, copy1);
	int[] copy2 = Arrays.copyOf(copy1, 6);
        CatanDice.rollDice(n2, copy2);
	//System.out.println("rollDice: " + resourceVectorToString(copy1) + " + roll(" + n2 + ") -> " + resourceVectorToString(copy2));
	checkReturn(n2, copy1, copy2);
    }

    @Test
    public void rollAll() {
	int[] empty = { 0, 0, 0, 0, 0, 0 };
	for (int k = 0; k < 100; k++) {
	    test(6, empty);
	}
    }

    @Test
    public void rollTwice() {
	int[] empty = { 0, 0, 0, 0, 0, 0 };
	for (int k = 0; k < 100; k++) {
	    test2(3, 3, empty);
	}
    }

    // junit launcher doesn't work, so just use a simple main to run
    // the test functions...
    public static void main(String[] args) {
	TestRollDice tests = new TestRollDice();
	System.out.println("testing...");
	tests.rollAll();
	tests.rollTwice();
	System.out.println("all done!");
    }
}
