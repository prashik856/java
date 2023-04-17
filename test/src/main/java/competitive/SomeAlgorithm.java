package competitive;

import java.util.Arrays;

public class SomeAlgorithm {
    SomeAlgorithm() {}

    public static String solve(String input) {
        char[] s = input.toCharArray();

        int pivot = 0;
        int n = s.length;

        for(int i=n-1; i>0; i--){
            if(s[i] > s[i-1]){
                pivot = i - 1;
                break;
            }
        }

        int successor = n-1;
        for(int i=pivot+1; i<n; i++){
            if(s[pivot] >= s[i]){
                successor = i-1;
                break;
            }
        }

        char temp = s[pivot];
        s[pivot] = s[successor];
        s[successor] = temp;

        StringBuilder tempReverseSuffix = new StringBuilder();
        for(int i=pivot+1; i<n; i++){
            tempReverseSuffix.append(s[i]);
        }

        char[] reverseSuffix = tempReverseSuffix.toString().toCharArray();

        int index = pivot + 1;
        for(int i=reverseSuffix.length-1; i>=0; i--){
            s[index] = reverseSuffix[i];
            index++;
        }

        input = Arrays.toString(s);

        return input;
    }
}
