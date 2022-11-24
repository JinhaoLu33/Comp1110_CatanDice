package comp1110.ass2;

import java.util.*;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestResourceWithTS {
    private String errorPrefix(int[] target, String board_state, int[] resources) {
        StringJoiner j = new StringJoiner(",");
        StringJoiner t = new StringJoiner(",");
        for (int i = 0; i < 6; i++)
            j.add(Integer.toString(resources[i]));
        for (int i = 0; i < 6; i++)
            t.add(Integer.toString(target[i]));
        return "Resource.resourceWithTS({" +Arrays.toString(target) + "} , " + board_state + ", {" + Arrays.toString(resources) + "})";
    }

    private void test(String b, int[] rv,Resource.resourceType type,Resource.resourceType swapped,int amount, int[] answer) {
        Resource resource = new Resource(rv);
        String errorPrefix = errorPrefix(answer, b, rv);
        int[] out = resource.resourceWithTS(b, type,swapped, amount).resource;
        assertArrayEquals(answer, out, errorPrefix);
    }

    @Test
    public void testSet()
    {
        test("J1,J2,J3,J4,J5,J6", new int[]{1, 1, 1, 1, 1,2}, Resource.resourceType.BRICK,Resource.resourceType.ORE,1, new int[]{1,1,1,1,2,0});
        test("R0,R1,R2,R3",new int[]{1, 1, 1, 1, 1,2}, Resource.resourceType.BRICK,Resource.resourceType.ORE, 1, new int[]{1,1,1,1,2,0});
        test("J1,J2,J3,J4,J5,J6", new int[]{1, 1, 1, 1, 1,2}, Resource.resourceType.BRICK,Resource.resourceType.ORE,2, new int[]{0,1,1,1,3,0});
        test("R0,J1", new int[]{1,1,0,0,0,0}, Resource.resourceType.ORE,Resource.resourceType.GRAIN,1, new int[]{2,0,0,0,0,0});
        test("R0,R1", new int[]{1,1,0,0,0,0}, Resource.resourceType.ORE,Resource.resourceType.GRAIN,1, new int[]{1,1,0,0,0,0});

    }

    public static void main(String[] args) {
        TestResourceWithTS t = new TestResourceWithTS();
        t.testSet();
    }
}
