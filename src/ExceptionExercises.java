import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExceptionExercises {
    public static void main(String[] args) {

        System.out.println("Calling problem 1:");
        System.out.println("Final product: " + Arrays.toString(problem1()));

        System.out.println();

        System.out.println("Calling problem 2:");
        System.out.println("Final product: " + problem2().toString());


    }

    static int[] problem1(){
        int[] intArray = {1,2,3,4,5};
        for(int i=0;i <= intArray.length;i++){
            try {
                //looping through elements in i
                intArray[i] = intArray[i - 1] + intArray[i]; //intArray[i] becomes the sum of itself and the element before it
            }
            catch(ArrayIndexOutOfBoundsException exp){
                System.out.println("Exception encountered: " + exp.getMessage());
            }

        }
        return intArray;
    }


    static ArrayList<Integer> problem2(){
        ArrayList<Integer> myArrayList = null;

        for(int i=0; i < 10; i++){
            try {
                myArrayList.add(i);
                myArrayList.set(i, i / myArrayList.get(i - 1));
            }
            catch(NullPointerException exp){
                System.out.println("Exception encountered: " + exp);
                myArrayList = new ArrayList<>();
                myArrayList.add(i);
            }
            catch(ArithmeticException exp){
                System.out.println("Exception encountered: " + exp + " at index " + i);
            }
        }

        return myArrayList;
    }
}
