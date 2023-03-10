import java.util.Scanner;

public class RunONE {
    public static void main(String[] args) {

        System.out.println("Started==================");
        ResultSet_Iteration obj = new ResultSet_Iteration();

        Scanner sc = new Scanner(System.in);
        String str = "";
        while (!str.equals("exit")) {
            System.out.println("Enter next|prev|first|last ");
            str = sc.next();

            if (str.equals("next")) {
                obj.move_Next();
            } else if (str.equals("prev")) {
                obj.move_Previous();
            }
            if (str.equals("first")) {
                obj.move_First();
            }
            if (str.equals("last")) {
                obj.move_Last();
            }
        }


    }
}
