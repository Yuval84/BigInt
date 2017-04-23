
import java.util.Scanner;

/**
 * Created by yuval_edelman on 4/2/2015.
 */
public class Main {
    public static void main(String [] args){
        String firstNumber,secondNumber;
        Scanner scan=new Scanner(System.in);
        int choice=1;
        do{
            System.out.println("please enter first number");
            firstNumber=scan.nextLine();
            BigInt number1=new BigInt(firstNumber);
            System.out.println("please enter second number" );
            secondNumber=scan.nextLine();
            BigInt number2=new BigInt(secondNumber);
            BigInt result= number1.plus(number2);
            System.out.println("first number plus second number equal:"+result);
            result=number1.minus(number2);
            System.out.println("first number minus second number equal:"+result);
            result=number1.multiply(number2);
            System.out.println("first number multiply second number equal:"+result);
            result=number1.divide(number2);
            System.out.println("first number divide second number equal:"+result);
            if (number1.equals(number2)){
                System.out.println("first number equal to the second number");
            }else{
                System.out.println("first number not equal to the second number");
            }
            int comp=number1.compareTo(number2);
            System.out.println("compare to method result: "+comp);
            System.out.println("if you want to enter another two numbers enter 1 else enter any number");
            try {
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }while(choice==1);
        System.out.println("you exit the program.");
















    }
}
