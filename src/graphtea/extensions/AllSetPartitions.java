package graphtea.extensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllSetPartitions {
    public static <T> List<List<List<T>>> partitions(List<T> inputSet) {
        List<List<List<T>>> res = new ArrayList<>();
        if (inputSet.isEmpty()) {
            List<List<T>> empty = new ArrayList<>();
            res.add(empty);
            return res;
        }
        // Note that this algorithm only works if inputSet.size() < 31
        // since you overflow int space beyond that. This is true even
        // if you use Math.pow and cast back to int. The original
        // Python code does not have this limitation because Python
        // will implicitly promote to a long, which in Python terms is
        // an arbitrary precision integer similar to Java's BigInteger.
        int limit = 1 << (inputSet.size() - 1);
        // Note the separate variable to avoid resetting
        // the loop variable on each iteration.
        for (int j = 0; j < limit; ++j) {
            List<List<T>> parts = new ArrayList<>();
            List<T> part1 = new ArrayList<>();
            List<T> part2 = new ArrayList<>();
            parts.add(part1);
            parts.add(part2);
            int i = j;
            for (T item : inputSet) {
                parts.get(i&1).add(item);
                i >>= 1;
            }
            for (List<List<T>> b : partitions(part2)) {
                List<List<T>> holder = new ArrayList<>();
                holder.add(part1);
                holder.addAll(b);
                res.add(holder);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        for (List<List<String>> partitions : partitions(Arrays.asList("a", "b", "c", "d"))) {
            System.out.println(partitions);
        }
    }
}