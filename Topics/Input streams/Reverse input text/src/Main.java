import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // start coding here
        String newStr = String.valueOf(reader.readLine());
        StringBuilder reversedStr = new StringBuilder(newStr).reverse();
        System.out.println(reversedStr);
        reader.close();
    }
}