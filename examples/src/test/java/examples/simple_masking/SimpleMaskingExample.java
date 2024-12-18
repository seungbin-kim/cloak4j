package examples.simple_masking;

import io.github.cloak4j.SimpleMasker;
import org.junit.jupiter.api.Test;

public class SimpleMaskingExample {

    @Test
    void simpleMask_example() {
        String masked1 = SimpleMasker.mask("0123456789", 4, 3, '*');
        System.out.println(masked1); // 0123***789

        String masked2 = SimpleMasker.mask("0123456789", 4, '*');
        System.out.println(masked2); // 0123******

        String masked3 = SimpleMasker.mask("0123456789", '*', 5);
        System.out.println(masked3); // *****56789
    }

}
