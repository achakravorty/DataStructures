package Arrays;

import java.util.Arrays;

public class ArrayImplemenation {

    public static void main(String[] args) {
        int[] arr = new int[5];

        int[] arr2 = { 2, 5, 6, 9, 7, 4, 3 };

        System.out.println(Arrays.toString(Arrays.stream(arr).toArray()));

    }
}
