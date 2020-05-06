import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Simple Java Class Generator based on Builder Design Pattern
 * @author Victor Matheus da Silva
 * @version 1.0
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JavaClassBuilder builder;
        int option = 0;

        // Initial values
        String className, classVisibility;

        // Attribute values
        String attributeName, attributeType, attributeVisibility;

        welcomePrint();
        System.out.println("Choose the name of the class:");
        className = scanner.next();

        System.out.println("Choose the visibility of the class:");
        classVisibility = scanner.next().toLowerCase();

        builder = new JavaClassBuilder(className, classVisibility);

        printOptions();
        try {
            option = scanner.nextInt();
        } catch (Exception e){
            exitWithCastError();
            return;
        }
        if(option == 2){
            exitWithLackOfAttributeError();
            return;
        }
        // Build the attributes
        while (option != 2){

            if(option != 1 && option != 2){
                tryAgainMessage();
            }

            System.out.println("Choose the name of the attribute:");
            attributeName = scanner.next().toLowerCase();
            System.out.println("Choose the type of the attribute:");
            attributeType = scanner.next();
            System.out.println("Choose the visibility of the attribute:");
            attributeVisibility = scanner.next();

            builder.addField(attributeName, attributeType, attributeVisibility);

            printOptions();
            try {
                option = scanner.nextInt();
            } catch (Exception e){
                exitWithCastError();
            }
        }

        classGenerationMessage();
        builder.finalizeClass();
        generateClass(builder, className);
    }

    public static void printOptions(){
        System.out.println("====");
        System.out.println("Choose an option:");
        System.out.println("1- Add attributes");
        System.out.println("2- Generate Java Class");
        System.out.println("====");
    }

    private static void welcomePrint(){
        System.out.println("== Welcome to the Java Class Builder! == \n");
    }

    private static void exitWithLackOfAttributeError(){
        System.out.println("The class doesn't have any attribute. Please run the app again");
    }

    private static void exitWithCastError(){
        System.out.println("Invalid option! The value should be a number!");
    }

    private static void tryAgainMessage(){
        System.out.println("Invalid option! Please try again");
    }

    private static void classGenerationMessage(){
        System.out.println("Generating the class");
    }
    private static void generationSuccessfulMessage(){
        System.out.println("The Class was generated successfully! \n" +
                "Please check your project to find the generated class");
    }

    private static void fileExistMessage(){
        System.out.println("There is a file with the same name already. " +
                "It will be overridden!");
    }

    private static void generateClass(JavaClassBuilder builder, String className){
        String fileName = Character.toUpperCase(className.charAt(0))
                + className.substring(1,className.length()) + ".java";

        if(new File(fileName).exists()){
            fileExistMessage();
        }
        try(PrintStream out = new PrintStream(fileName)){
            out.println(builder.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Could not generate Java class: " + e.getCause());
        }
        generationSuccessfulMessage();
        try {
            // It will works only in Linux machines
            // If you don't want to open the class in a text editor, just comment just line
            Runtime.getRuntime().exec("gedit " + fileName);
        } catch (IOException e) {
            System.out.println("Could not open the Generated Java class: " + e.getCause());
        }
    }
}
