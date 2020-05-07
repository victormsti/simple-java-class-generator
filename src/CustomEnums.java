import java.util.Arrays;
import java.util.Optional;

/**
 * Custom Enums used in the App
 * @author Victor Matheus da Silva
 */
public class CustomEnums {
    enum Options{
        ADD_ATTRIBUTE_OPTION(1),
        GENERATE_CLASS_OPTION(2),
        GENERATE_CLASS_AND_OPEN_FILE_OPTION(3);

        int value;

        Options(int value) {
            this.value = value;
        }

        public static Optional<Options> valueOfFromInt(int value) {
            return Arrays.stream(values())
                    .filter(opt -> opt.value == value)
                    .findFirst();
        }
    }

    enum OperatingSystems{
        WINDOWS,
        LINUX
    }

    enum Visibilities{
        PUBLIC,
        PRIVATE,
        PROTECTED,
        DEFAULT;

        public static Optional<Visibilities> valueOfFromString(String value) {
            return Arrays.stream(values())
                    .filter(vis -> vis.name().equalsIgnoreCase(value))
                    .findFirst();
        }
    }
}
