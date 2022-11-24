package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestIsActionWellFormed {

    private String errorPrefix(String action) {
        return "CatanDice.isActionWellFormed(\"" + action + "\")";
    }

    private void test(String in, boolean expected) {
        String errorPrefix = errorPrefix(in);
        boolean out = CatanDice.isActionWellFormed(in);
        assertEquals(expected, out, errorPrefix);
    }

    @Test
    public void trivialTrue() {
        test("build R0", true);
        test("build R12", true);
        test("build S3", true);
        test("build S11", true);
        test("build C20", true);
        test("build J5", true);
        test("trade 0", true);
        test("trade 1", true);
        test("trade 2", true);
        test("trade 3", true);
        test("trade 4", true);
        test("swap 0 1", true);
        test("swap 0 3", true);
        test("swap 0 5", true);
        test("swap 2 1", true);
        test("swap 2 3", true);
        test("swap 2 5", true);
        test("swap 4 0", true);
        test("swap 4 3", true);
        test("swap 4 5", true);
    }

    @Test
    public void trivialFalse() {
        test("bild J5", false); // typo in action word
        test("traed 2", false); // typo in action word
        test("SWAP 1 4", false); // case
        test(" 1 4", false); // no action word
        test("build_J5", false); // incorrect separator
        test("build-J5", false); // incorrect separator
        test("build\nJ5", false); // incorrect separator
        test("buildJ5", false); // no separator
        test("trade 6", false); // index out of range
        test("swap 1 -1", false); // index out of range
        test("build K", false); // incorrect structure name
        test("build R-1", false); // incorrect structure name
        test("build R20", false); // incorrect structure name
        test("build F12", false); // incorrect structure name
        test("build G3", false); // incorrect structure name
    }

    public static void main(String[] args) {
	TestIsActionWellFormed tests = new TestIsActionWellFormed();
	System.out.println("testing...");
	tests.trivialTrue();
	tests.trivialFalse();
	System.out.println("all done!");
    }
}
