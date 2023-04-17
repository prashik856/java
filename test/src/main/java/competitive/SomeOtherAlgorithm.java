package competitive;

public class SomeOtherAlgorithm {
    public static int solve(int a, int b) {
        int result = 0;
        int remainder = 0;

        if(b==0){
            result = b;
        }

        else{
            remainder = a % b;
            if(remainder == 0){
                result = b;
            }

            else{
                result = SomeOtherAlgorithm.solve(b, remainder);
            }
        }
        return result;
    }
}
