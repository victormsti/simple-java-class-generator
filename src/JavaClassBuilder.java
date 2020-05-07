/**
 * Builder Class responsible for creating a new Java Class using User Inputs
 * @author Victor Matheus da Silva
 */
public class JavaClassBuilder {
    StringBuilder sb = new StringBuilder();
    private final String indentAttributes = "  ";

    /**
     * Constructor of the Builder with handles the beginning of the class
     * @param className {@link String}
     * @param classVisibility {@link String}
     */
    public JavaClassBuilder(String className, String classVisibility)
    {
        sb.append(classVisibility)
                .append(" class ")
                .append(className)
                .append(System.lineSeparator())
                .append("{");

    }

    /**
     * Method responsible for adding a new attribute to the class
     * @param name {@link String}
     * @param type {@link String}
     * @param visibility {@link String}
     * @return JavaClassBuilder
     */
    public JavaClassBuilder addField(String name, String type, String visibility)
    {
        sb.append(System.lineSeparator())
                .append(indentAttributes)
                .append(visibility).append(" ")
                .append(type)
                .append(" ")
                .append(name)
                .append(";");

        return this;
    }

    /**
     * Finalizes the class with the final code
     * @return JavaClassBuilder
     */
    public JavaClassBuilder finalizeClass(){
        sb.append(System.lineSeparator()).append("}");

        return this;
    }

    @Override
    public String toString()
    {
        return sb.toString();
    }
}
