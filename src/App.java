import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Simple Java Class Generator based on Builder Design Pattern
 * @author Victor Matheus da Silva
 * @version 1.0
 */
public class App {

    static Scanner scanner;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        JavaClassBuilder builder;

        int chosenOption = 0;

        // Initial values
        String className, classVisibility;

        // Attribute values
        String attributeName, attributeType, attributeVisibility;

        welcomePrint();

        System.out.println("Choose the name of the class:");
        className = scanner.next();

        System.out.println("Choose the visibility of the class:");
        classVisibility = scanner.next().toLowerCase();

        if(!checkIfIsAValidVisibility(classVisibility)){
            printInvalidVisibilityMessage();
            return;
        }

        builder = new JavaClassBuilder(className, classVisibility);

        printOptions();

        try {
            chosenOption = scanner.nextInt();
        } catch (InputMismatchException e){
            printInputMismatchErrorMessage();
            return;
        }
        if(chosenOption == CustomEnums.Options.GENERATE_CLASS_OPTION.value ||
                chosenOption == CustomEnums.Options.GENERATE_CLASS_AND_OPEN_FILE_OPTION.value){
            printLackOfAttributeErrorMessage();
            return;
        }
        // Build the attributes
        while (chosenOption != CustomEnums.Options.GENERATE_CLASS_OPTION.value
                && chosenOption != CustomEnums.Options.GENERATE_CLASS_AND_OPEN_FILE_OPTION.value){

            if(!checkIfIsAValidOption(chosenOption)){
                printTryAgainMessage();
                printOptions();

                returnToOptionMenu(chosenOption);
                continue;
            }

            System.out.println("Choose the name of the attribute:");
            attributeName = scanner.next().toLowerCase();
            System.out.println("Choose the type of the attribute:");
            attributeType = scanner.next();
            System.out.println("Choose the visibility of the attribute:");
            attributeVisibility = scanner.next();

            if(!checkIfIsAValidVisibility(attributeVisibility)){
                printInvalidVisibilityMessage();
                printOptions();

                returnToOptionMenu(chosenOption);
                continue;
            }

            builder.addField(attributeName, attributeType, attributeVisibility);

            printOptions();

            try {
                chosenOption = scanner.nextInt();
            } catch (InputMismatchException e){
                printInputMismatchErrorMessage();
            }
        }

        printClassGenerationMessage();
        builder.finalizeClass();
        generateClass(builder, className, chosenOption);
    }

    /**
     * Prints the Options Menu
     */
    public static void printOptions(){
        System.out.println("====");
        System.out.println("Choose an option:");
        System.out.println("1- Add attributes");
        System.out.println("2- Generate Java Class");
        System.out.println("3- Generate Java Class and Open the File");
        System.out.println("====");
    }

    private static void welcomePrint(){
        System.out.println("== Welcome to the Java Class Builder! == \n");
    }

    private static void printLackOfAttributeErrorMessage(){
        System.out.println("The class doesn't have any attribute. Please run the app again");
    }

    private static void printInputMismatchErrorMessage(){
        System.out.println("Invalid option! The value should be a number!");
    }

    private static void printTryAgainMessage(){
        System.out.println("Invalid option! Please try again");
    }

    private static void printClassGenerationMessage(){
        System.out.println("Generating the class");
    }

    private static void generationSuccessfulMessage(){
        System.out.println("The Class was generated successfully! \n" +
                "Please check your project to find the generated class");
    }

    private static void printOpeningFileMessage(){
        System.out.println("Opening the Generated Java Class...");
    }

    private static void printFileExistMessage(){
        System.out.println("There is a file with the same name already. " +
                "It will be overridden!");
    }

    private static void printInvalidVisibilityMessage(){
        System.out.println("The visibility is invalid for Java. It should be from this list: " +
                Arrays.asList(CustomEnums.Visibilities.values()));
    }

    private static void printOperationNotSupportedMessage() {
        System.out.println("Sorry. This version doesn't support the Print Option your Operating System. \n" +
                "Check for new versions");
    }

    /**
     * Gets the option from the user to go back to the Option Menu
     * @param chosenOption int
     */
    private static void returnToOptionMenu(int chosenOption){
        try {
            chosenOption = scanner.nextInt(); // Avoid infinite loop
        } catch (InputMismatchException e){
            printInputMismatchErrorMessage();
            return;
        }
    }

    /**
     * Checks if the user sent a valid option
     * @param option int
     * @return
     */
    private static boolean checkIfIsAValidOption(int option){
        return CustomEnums.Options.valueOfFromInt(option).isPresent();
    }

    /**
     * Checks if the user sent a valid option
     * @param option int
     * @return
     */
    private static boolean checkIfIsAValidVisibility(String option){
        return CustomEnums.Visibilities.valueOfFromString(option).isPresent();
    }

    /**
     * Generates the java class to a java file and, if chosen, opens the file
     * @param builder {@link JavaClassBuilder}
     * @param className {@link String}
     */
    private static void generateClass(JavaClassBuilder builder, String className, int option){
        String fileName = Character.toUpperCase(className.charAt(0))
                + className.substring(1,className.length()) + ".java";

        if(new File(fileName).exists()){
            printFileExistMessage();
        }

        try(PrintStream out = new PrintStream(fileName)){
            out.println(builder.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Could not generate Java class: " + e.getCause());
        }
        generationSuccessfulMessage();

        if(option == CustomEnums.Options.GENERATE_CLASS_AND_OPEN_FILE_OPTION.value) {

            printOpeningFileMessage();

            try {
                if(System.getProperty("os.name").equalsIgnoreCase(CustomEnums.OperatingSystems.LINUX.name())){
                    Runtime.getRuntime().exec("gedit " + fileName);
                }
                else if(System.getProperty("os.name").equalsIgnoreCase(CustomEnums.OperatingSystems.WINDOWS.name())){
                    Runtime.getRuntime().exec("notepad " + fileName);
                }
                else{
                    printOperationNotSupportedMessage();
                }

            } catch (IOException e) {
                System.out.println("Could not open the Generated Java class: " + e.getCause());
            }
        }
    }
}