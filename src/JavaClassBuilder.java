public class JavaClassBuilder {
    StringBuilder sb = new StringBuilder();
    private final String indentAttributes = "  ";

    public JavaClassBuilder(String className, String classVisibility)
    {
        sb.append(classVisibility)
                .append(" class ")
                .append(className)
                .append(System.lineSeparator())
                .append("{");

    }

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
