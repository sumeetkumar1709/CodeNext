package com.segmnf.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.database.FirebaseDatabase;
import com.segmnf.myapplication.databinding.ActivityLearnJavaBinding;

import br.tiagohm.codeview.CodeView;
import br.tiagohm.codeview.Language;
import br.tiagohm.codeview.Theme;


public class LearnJavaActivity extends AppCompatActivity implements CodeView.OnHighlightListener {
    ActivityLearnJavaBinding binding;
    private FirebaseDatabase database;


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideStatus() {
        /* To make the status bar transparent*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        binding = ActivityLearnJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        binding.imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String code= "// Single-line comments start with //\n" +
                "\n" +
                "/*\n" +
                "Multi-line comments look like this.\n" +
                "*/\n" +
                "\n" +
                "/**\n" +
                " * JavaDoc comments look like this. Used to describe the Class or various\n" +
                " * attributes of a Class.\n" +
                " * Main attributes:\n" +
                " *\n" +
                " * @author         Name (and contact information such as email) of author(s).\n" +
                " * @version     Current version of the program.\n" +
                " * @since        When this part of the program was first added.\n" +
                " * @param         For describing the different parameters for a method.\n" +
                " * @return        For describing what the method returns.\n" +
                " * @deprecated  For showing the code is outdated or shouldn't be used.\n" +
                " * @see         Links to another part of documentation.\n" +
                "*/\n" +
                "\n" +
                "// Import ArrayList class inside of the java.util package\n" +
                "import java.util.ArrayList;\n" +
                "// Import all classes inside of java.security package\n" +
                "import java.security.*;\n" +
                "\n" +
                "public class LearnJava {\n" +
                "\n" +
                "    // In order to run a java program, it must have a main method as an entry\n" +
                "    // point.\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "    ///////////////////////////////////////\n" +
                "    // Input/Output\n" +
                "    ///////////////////////////////////////\n" +
                "\n" +
                "        /*\n" +
                "        * Output\n" +
                "        */\n" +
                "\n" +
                "        // Use System.out.println() to print lines.\n" +
                "        System.out.println(\"Hello World!\");\n" +
                "        System.out.println(\n" +
                "            \"Integer: \" + 10 +\n" +
                "            \" Double: \" + 3.14 +\n" +
                "            \" Boolean: \" + true);\n" +
                "\n" +
                "        // To print without a newline, use System.out.print().\n" +
                "        System.out.print(\"Hello \");\n" +
                "        System.out.print(\"World\");\n" +
                "\n" +
                "        // Use System.out.printf() for easy formatted printing.\n" +
                "        System.out.printf(\"pi = %.5f\", Math.PI); // => pi = 3.14159\n" +
                "\n" +
                "        /*\n" +
                "         * Input\n" +
                "         */\n" +
                "\n" +
                "        // use Scanner to read input\n" +
                "        // must import java.util.Scanner;\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "\n" +
                "        // read string input\n" +
                "        String name = scanner.next();\n" +
                "\n" +
                "        // read byte input\n" +
                "        byte numByte = scanner.nextByte();\n" +
                "\n" +
                "        // read int input\n" +
                "        int numInt = scanner.nextInt();\n" +
                "\n" +
                "        // read long input\n" +
                "        float numFloat = scanner.nextFloat();\n" +
                "\n" +
                "        // read double input\n" +
                "        double numDouble = scanner.nextDouble();\n" +
                "\n" +
                "        // read boolean input\n" +
                "        boolean bool = scanner.nextBoolean();\n" +
                "\n" +
                "        ///////////////////////////////////////\n" +
                "        // Variables\n" +
                "        ///////////////////////////////////////\n" +
                "\n" +
                "        /*\n" +
                "        *  Variable Declaration\n" +
                "        */\n" +
                "        // Declare a variable using <type> <name>\n" +
                "        int fooInt;\n" +
                "        // Declare multiple variables of the same\n" +
                "        // type <type> <name1>, <name2>, <name3>\n" +
                "        int fooInt1, fooInt2, fooInt3;\n" +
                "\n" +
                "        /*\n" +
                "        *  Variable Initialization\n" +
                "        */\n" +
                "\n" +
                "        // Initialize a variable using <type> <name> = <val>\n" +
                "        int barInt = 1;\n" +
                "        // Initialize multiple variables of same type with same\n" +
                "        // value <type> <name1>, <name2>, <name3>\n" +
                "        // <name1> = <name2> = <name3> = <val>\n" +
                "        int barInt1, barInt2, barInt3;\n" +
                "        barInt1 = barInt2 = barInt3 = 1;\n" +
                "\n" +
                "        /*\n" +
                "        *  Variable types\n" +
                "        */\n" +
                "        // Byte - 8-bit signed two's complement integer\n" +
                "        // (-128 <= byte <= 127)\n" +
                "        byte fooByte = 100;\n" +
                "\n" +
                "        // If you would like to interpret a byte as an unsigned integer\n" +
                "        // then this simple operation can help\n" +
                "        int unsignedIntLessThan256 = 0xff & fooByte;\n" +
                "        // this contrasts a cast which can be negative.\n" +
                "        int signedInt = (int) fooByte;\n" +
                "\n" +
                "        // Short - 16-bit signed two's complement integer\n" +
                "        // (-32,768 <= short <= 32,767)\n" +
                "        short fooShort = 10000;\n" +
                "\n" +
                "        // Integer - 32-bit signed two's complement integer\n" +
                "        // (-2,147,483,648 <= int <= 2,147,483,647)\n" +
                "        int bazInt = 1;\n" +
                "\n" +
                "        // Long - 64-bit signed two's complement integer\n" +
                "        // (-9,223,372,036,854,775,808 <= long <= 9,223,372,036,854,775,807)\n" +
                "        long fooLong = 100000L;\n" +
                "        // L is used to denote that this variable value is of type Long;\n" +
                "        // anything without is treated as integer by default.\n" +
                "\n" +
                "        // Note: byte, short, int and long are signed. They can have positive and negative values.\n" +
                "        // There are no unsigned variants.\n" +
                "        // char, however, is 16-bit unsigned.\n" +
                "\n" +
                "        // Float - Single-precision 32-bit IEEE 754 Floating Point\n" +
                "        // 2^-149 <= float <= (2-2^-23) * 2^127\n" +
                "        float fooFloat = 234.5f;\n" +
                "        // f or F is used to denote that this variable value is of type float;\n" +
                "        // otherwise it is treated as double.\n" +
                "\n" +
                "        // Double - Double-precision 64-bit IEEE 754 Floating Point\n" +
                "        // 2^-1074 <= x <= (2-2^-52) * 2^1023\n" +
                "        double fooDouble = 123.4;\n" +
                "\n" +
                "        // Boolean - true & false\n" +
                "        boolean fooBoolean = true;\n" +
                "        boolean barBoolean = false;\n" +
                "\n" +
                "        // Char - A single 16-bit Unicode character\n" +
                "        char fooChar = 'A';\n" +
                "\n" +
                "        // final variables can't be reassigned,\n" +
                "        final int HOURS_I_WORK_PER_WEEK = 9001;\n" +
                "        // but they can be initialized later.\n" +
                "        final double E;\n" +
                "        E = 2.71828;\n" +
                "\n" +
                "        // BigInteger - Immutable arbitrary-precision integers\n" +
                "        //\n" +
                "        // BigInteger is a data type that allows programmers to manipulate\n" +
                "        // integers longer than 64-bits. Integers are stored as an array of\n" +
                "        // of bytes and are manipulated using functions built into BigInteger\n" +
                "        //\n" +
                "        // BigInteger can be initialized using an array of bytes or a string.\n" +
                "        BigInteger fooBigInteger = new BigInteger(fooByteArray);\n" +
                "\n" +
                "        // BigDecimal - Immutable, arbitrary-precision signed decimal number\n" +
                "        //\n" +
                "        // A BigDecimal takes two parts: an arbitrary precision integer\n" +
                "        // unscaled value and a 32-bit integer scale\n" +
                "        //\n" +
                "        // BigDecimal allows the programmer complete control over decimal\n" +
                "        // rounding. It is recommended to use BigDecimal with currency values\n" +
                "        // and where exact decimal precision is required.\n" +
                "        //\n" +
                "        // BigDecimal can be initialized with an int, long, double or String\n" +
                "        // or by initializing the unscaled value (BigInteger) and scale (int).\n" +
                "        BigDecimal fooBigDecimal = new BigDecimal(fooBigInteger, fooInt);\n" +
                "\n" +
                "        // Be wary of the constructor that takes a float or double as\n" +
                "        // the inaccuracy of the float/double will be copied in BigDecimal.\n" +
                "        // Prefer the String constructor when you need an exact value.\n" +
                "        BigDecimal tenCents = new BigDecimal(\"0.1\");\n" +
                "\n" +
                "        // Strings\n" +
                "        String fooString = \"My String Is Here!\";\n" +
                "\n" +
                "        // \\n is an escaped character that starts a new line\n" +
                "        String barString = \"Printing on a new line?\\nNo Problem!\";\n" +
                "        // \\t is an escaped character that adds a tab character\n" +
                "        String bazString = \"Do you want to add a tab?\\tNo Problem!\";\n" +
                "        System.out.println(fooString);\n" +
                "        System.out.println(barString);\n" +
                "        System.out.println(bazString);\n" +
                "\n" +
                "        // String Building\n" +
                "        // #1 - with plus operator\n" +
                "        // That's the basic way to do it (optimized under the hood)\n" +
                "        String plusConcatenated = \"Strings can \" + \"be concatenated \" + \"via + operator.\";\n" +
                "        System.out.println(plusConcatenated);\n" +
                "        // Output: Strings can be concatenated via + operator.\n" +
                "\n" +
                "        // #2 - with StringBuilder\n" +
                "        // This way doesn't create any intermediate strings. It just stores the string pieces, and ties them together\n" +
                "        // when toString() is called.\n" +
                "        // Hint: This class is not thread safe. A thread-safe alternative (with some impact on performance) is StringBuffer.\n" +
                "        StringBuilder builderConcatenated = new StringBuilder();\n" +
                "        builderConcatenated.append(\"You \");\n" +
                "        builderConcatenated.append(\"can use \");\n" +
                "        builderConcatenated.append(\"the StringBuilder class.\");\n" +
                "        System.out.println(builderConcatenated.toString()); // only now is the string built\n" +
                "        // Output: You can use the StringBuilder class.\n" +
                "\n" +
                "        // StringBuilder is efficient when the fully constructed String is not required until the end of some processing.\n" +
                "        StringBuilder stringBuilder = new StringBuilder();\n" +
                "        String inefficientString = \"\";\n" +
                "        for (int i = 0 ; i < 10; i++) {\n" +
                "            stringBuilder.append(i).append(\" \");\n" +
                "            inefficientString += i + \" \";\n" +
                "        }\n" +
                "        System.out.println(inefficientString);\n" +
                "        System.out.println(stringBuilder.toString());\n" +
                "        // inefficientString requires a lot more work to produce, as it generates a String on every loop iteration.\n" +
                "        // Simple concatenation with + is compiled to a StringBuilder and toString()\n" +
                "        // Avoid string concatenation in loops.\n" +
                "\n" +
                "        // #3 - with String formatter\n" +
                "        // Another alternative way to create strings. Fast and readable.\n" +
                "        String.format(\"%s may prefer %s.\", \"Or you\", \"String.format()\");\n" +
                "        // Output: Or you may prefer String.format().\n" +
                "\n" +
                "        // Arrays\n" +
                "        // The array size must be decided upon instantiation\n" +
                "        // The following formats work for declaring an array\n" +
                "        // <datatype>[] <var name> = new <datatype>[<array size>];\n" +
                "        // <datatype> <var name>[] = new <datatype>[<array size>];\n" +
                "        int[] intArray = new int[10];\n" +
                "        String[] stringArray = new String[1];\n" +
                "        boolean boolArray[] = new boolean[100];\n" +
                "\n" +
                "        // Another way to declare & initialize an array\n" +
                "        int[] y = {9000, 1000, 1337};\n" +
                "        String names[] = {\"Bob\", \"John\", \"Fred\", \"Juan Pedro\"};\n" +
                "        boolean bools[] = {true, false, false};\n" +
                "\n" +
                "        // Indexing an array - Accessing an element\n" +
                "        System.out.println(\"intArray @ 0: \" + intArray[0]);\n" +
                "\n" +
                "        // Arrays are zero-indexed and mutable.\n" +
                "        intArray[1] = 1;\n" +
                "        System.out.println(\"intArray @ 1: \" + intArray[1]); // => 1\n" +
                "\n" +
                "        // Other data types worth checking out\n" +
                "        // ArrayLists - Like arrays except more functionality is offered, and\n" +
                "        //              the size is mutable.\n" +
                "        // LinkedLists - Implementation of doubly-linked list. All of the\n" +
                "        //               operations perform as could be expected for a\n" +
                "        //               doubly-linked list.\n" +
                "        // Maps - A mapping of key Objects to value Objects. Map is\n" +
                "        //        an interface and therefore cannot be instantiated.\n" +
                "        //        The type of keys and values contained in a Map must\n" +
                "        //        be specified upon instantiation of the implementing\n" +
                "        //        class. Each key may map to only one corresponding value,\n" +
                "        //        and each key may appear only once (no duplicates).\n" +
                "        // HashMaps - This class uses a hashtable to implement the Map\n" +
                "        //            interface. This allows the execution time of basic\n" +
                "        //            operations, such as get and insert element, to remain\n" +
                "        //            constant-amortized even for large sets.\n" +
                "        // TreeMap - A Map that is sorted by its keys. Each modification\n" +
                "        //           maintains the sorting defined by either a Comparator\n" +
                "        //           supplied at instantiation, or comparisons of each Object\n" +
                "        //           if they implement the Comparable interface.\n" +
                "        //           Failure of keys to implement Comparable combined with failure to\n" +
                "        //           supply a Comparator will throw ClassCastExceptions.\n" +
                "        //           Insertion and removal operations take O(log(n)) time\n" +
                "        //           so avoid using this data structure unless you are taking\n" +
                "        //           advantage of the sorting.\n" +
                "\n" +
                "        ///////////////////////////////////////\n" +
                "        // Operators\n" +
                "        ///////////////////////////////////////\n" +
                "        System.out.println(\"\\n->Operators\");\n" +
                "\n" +
                "        int i1 = 1, i2 = 2; // Shorthand for multiple declarations\n" +
                "\n" +
                "        // Arithmetic is straightforward\n" +
                "        System.out.println(\"1+2 = \" + (i1 + i2)); // => 3\n" +
                "        System.out.println(\"2-1 = \" + (i2 - i1)); // => 1\n" +
                "        System.out.println(\"2*1 = \" + (i2 * i1)); // => 2\n" +
                "        System.out.println(\"1/2 = \" + (i1 / i2)); // => 0 (int/int returns int)\n" +
                "        System.out.println(\"1/2.0 = \" + (i1 / (double)i2)); // => 0.5\n" +
                "\n" +
                "        // Modulo\n" +
                "        System.out.println(\"11%3 = \"+(11 % 3)); // => 2\n" +
                "\n" +
                "        // Comparison operators\n" +
                "        System.out.println(\"3 == 2? \" + (3 == 2)); // => false\n" +
                "        System.out.println(\"3 != 2? \" + (3 != 2)); // => true\n" +
                "        System.out.println(\"3 > 2? \" + (3 > 2)); // => true\n" +
                "        System.out.println(\"3 < 2? \" + (3 < 2)); // => false\n" +
                "        System.out.println(\"2 <= 2? \" + (2 <= 2)); // => true\n" +
                "        System.out.println(\"2 >= 2? \" + (2 >= 2)); // => true\n" +
                "\n" +
                "        // Boolean operators\n" +
                "        System.out.println(\"3 > 2 && 2 > 3? \" + ((3 > 2) && (2 > 3))); // => false\n" +
                "        System.out.println(\"3 > 2 || 2 > 3? \" + ((3 > 2) || (2 > 3))); // => true\n" +
                "        System.out.println(\"!(3 == 2)? \" + (!(3 == 2))); // => true\n" +
                "\n" +
                "        // Bitwise operators!\n" +
                "        /*\n" +
                "        ~      Unary bitwise complement\n" +
                "        <<     Signed left shift\n" +
                "        >>     Signed/Arithmetic right shift\n" +
                "        >>>    Unsigned/Logical right shift\n" +
                "        &      Bitwise AND\n" +
                "        ^      Bitwise exclusive OR\n" +
                "        |      Bitwise inclusive OR\n" +
                "        */\n" +
                "\n" +
                "        // Increment operators\n" +
                "        int i = 0;\n" +
                "        System.out.println(\"\\n->Inc/Dec-rementation\");\n" +
                "        // The ++ and -- operators increment and decrement by 1 respectively.\n" +
                "        // If they are placed before the variable, they increment then return;\n" +
                "        // after the variable they return then increment.\n" +
                "        System.out.println(i++); // i = 1, prints 0 (post-increment)\n" +
                "        System.out.println(++i); // i = 2, prints 2 (pre-increment)\n" +
                "        System.out.println(i--); // i = 1, prints 2 (post-decrement)\n" +
                "        System.out.println(--i); // i = 0, prints 0 (pre-decrement)\n" +
                "\n" +
                "        ///////////////////////////////////////\n" +
                "        // Control Structures\n" +
                "        ///////////////////////////////////////\n" +
                "        System.out.println(\"\\n->Control Structures\");\n" +
                "\n" +
                "        // If statements are c-like\n" +
                "        int j = 10;\n" +
                "        if (j == 10) {\n" +
                "            System.out.println(\"I get printed\");\n" +
                "        } else if (j > 10) {\n" +
                "            System.out.println(\"I don't\");\n" +
                "        } else {\n" +
                "            System.out.println(\"I also don't\");\n" +
                "        }\n" +
                "\n" +
                "        // While loop\n" +
                "        int fooWhile = 0;\n" +
                "        while(fooWhile < 100) {\n" +
                "            System.out.println(fooWhile);\n" +
                "            // Increment the counter\n" +
                "            // Iterated 100 times, fooWhile 0,1,2...99\n" +
                "            fooWhile++;\n" +
                "        }\n" +
                "        System.out.println(\"fooWhile Value: \" + fooWhile);\n" +
                "\n" +
                "        // Do While Loop\n" +
                "        int fooDoWhile = 0;\n" +
                "        do {\n" +
                "            System.out.println(fooDoWhile);\n" +
                "            // Increment the counter\n" +
                "            // Iterated 100 times, fooDoWhile 0->99\n" +
                "            fooDoWhile++;\n" +
                "        } while(fooDoWhile < 100);\n" +
                "        System.out.println(\"fooDoWhile Value: \" + fooDoWhile);\n" +
                "\n" +
                "        // For Loop\n" +
                "        // for loop structure => for(<start_statement>; <conditional>; <step>)\n" +
                "        for (int fooFor = 0; fooFor < 10; fooFor++) {\n" +
                "            System.out.println(fooFor);\n" +
                "            // Iterated 10 times, fooFor 0->9\n" +
                "        }\n" +
                "        System.out.println(\"fooFor Value: \" + fooFor);\n" +
                "\n" +
                "        // Nested For Loop Exit with Label\n" +
                "        outer:\n" +
                "        for (int i = 0; i < 10; i++) {\n" +
                "          for (int j = 0; j < 10; j++) {\n" +
                "            if (i == 5 && j ==5) {\n" +
                "              break outer;\n" +
                "              // breaks out of outer loop instead of only the inner one\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "\n" +
                "        // For Each Loop\n" +
                "        // The for loop is also able to iterate over arrays as well as objects\n" +
                "        // that implement the Iterable interface.\n" +
                "        int[] fooList = {1, 2, 3, 4, 5, 6, 7, 8, 9};\n" +
                "        // for each loop structure => for (<object> : <iterable>)\n" +
                "        // reads as: for each element in the iterable\n" +
                "        // note: the object type must match the element type of the iterable.\n" +
                "        for (int bar : fooList) {\n" +
                "            System.out.println(bar);\n" +
                "            //Iterates 9 times and prints 1-9 on new lines\n" +
                "        }\n" +
                "\n" +
                "        // Switch Case\n" +
                "        // A switch works with the byte, short, char, and int data types.\n" +
                "        // It also works with enumerated types (discussed in Enum Types), the\n" +
                "        // String class, and a few special classes that wrap primitive types:\n" +
                "        // Character, Byte, Short, and Integer.\n" +
                "        // Starting in Java 7 and above, we can also use the String type.\n" +
                "        // Note: Do remember that, not adding \"break\" at end any particular case ends up in\n" +
                "        // executing the very next case(given it satisfies the condition provided) as well.\n" +
                "        int month = 3;\n" +
                "        String monthString;\n" +
                "        switch (month) {\n" +
                "            case 1: monthString = \"January\";\n" +
                "                    break;\n" +
                "            case 2: monthString = \"February\";\n" +
                "                    break;\n" +
                "            case 3: monthString = \"March\";\n" +
                "                    break;\n" +
                "            default: monthString = \"Some other month\";\n" +
                "                     break;\n" +
                "        }\n" +
                "        System.out.println(\"Switch Case Result: \" + monthString);\n" +
                "\n" +
                "\n" +
                "        // Try-with-resources (Java 7+)\n" +
                "        // Try-catch-finally statements work as expected in Java but in Java 7+\n" +
                "        // the try-with-resources statement is also available. Try-with-resources\n" +
                "        // simplifies try-catch-finally statements by closing resources\n" +
                "        // automatically.\n" +
                "\n" +
                "        // In order to use a try-with-resources, include an instance of a class\n" +
                "        // in the try statement. The class must implement java.lang.AutoCloseable.\n" +
                "        try (BufferedReader br = new BufferedReader(new FileReader(\"foo.txt\"))) {\n" +
                "            // You can attempt to do something that could throw an exception.\n" +
                "            System.out.println(br.readLine());\n" +
                "            // In Java 7, the resource will always be closed, even if it throws\n" +
                "            // an Exception.\n" +
                "        } catch (Exception ex) {\n" +
                "            //The resource will be closed before the catch statement executes.\n" +
                "            System.out.println(\"readLine() failed.\");\n" +
                "        }\n" +
                "        // No need for a finally statement in this case, the BufferedReader is\n" +
                "        // already closed. This can be used to avoid certain edge cases where\n" +
                "        // a finally statement might not be called.\n" +
                "        // To learn more:\n" +
                "        // https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html\n" +
                "\n" +
                "\n" +
                "        // Conditional Shorthand\n" +
                "        // You can use the '?' operator for quick assignments or logic forks.\n" +
                "        // Reads as \"If (statement) is true, use <first value>, otherwise, use\n" +
                "        // <second value>\"\n" +
                "        int foo = 5;\n" +
                "        String bar = (foo < 10) ? \"A\" : \"B\";\n" +
                "        System.out.println(\"bar : \" + bar); // Prints \"bar : A\", because the\n" +
                "        // statement is true.\n" +
                "        // Or simply\n" +
                "        System.out.println(\"bar : \" + (foo < 10 ? \"A\" : \"B\"));\n" +
                "\n" +
                "\n" +
                "        ////////////////////////////////////////\n" +
                "        // Converting Data Types\n" +
                "        ////////////////////////////////////////\n" +
                "\n" +
                "        // Converting data\n" +
                "\n" +
                "        // Convert String To Integer\n" +
                "        Integer.parseInt(\"123\");//returns an integer version of \"123\"\n" +
                "\n" +
                "        // Convert Integer To String\n" +
                "        Integer.toString(123);//returns a string version of 123\n" +
                "\n" +
                "        // For other conversions check out the following classes:\n" +
                "        // Double\n" +
                "        // Long\n" +
                "        // String\n" +
                "\n" +
                "        ///////////////////////////////////////\n" +
                "        // Classes And Functions\n" +
                "        ///////////////////////////////////////\n" +
                "\n" +
                "        System.out.println(\"\\n->Classes & Functions\");\n" +
                "\n" +
                "        // (definition of the Bicycle class follows)\n" +
                "\n" +
                "        // Use new to instantiate a class\n" +
                "        Bicycle trek = new Bicycle();\n" +
                "\n" +
                "        // Call object methods\n" +
                "        trek.speedUp(3); // You should always use setter and getter methods\n" +
                "        trek.setCadence(100);\n" +
                "\n" +
                "        // toString returns this Object's string representation.\n" +
                "        System.out.println(\"trek info: \" + trek.toString());\n" +
                "    } // End main method\n" +
                "\n" +
                "    private static class TestInitialization {\n" +
                "        // Double Brace Initialization\n" +
                "        // Before Java 11, the Java Language had no syntax for how to create\n" +
                "        // static Collections in an easy way. Usually you end up like this:\n" +
                "        private static final Set<String> COUNTRIES = new HashSet<String>();\n" +
                "        static {\n" +
                "           COUNTRIES.add(\"DENMARK\");\n" +
                "           COUNTRIES.add(\"SWEDEN\");\n" +
                "           COUNTRIES.add(\"FINLAND\");\n" +
                "        }\n" +
                "\n" +
                "        // There's a nifty way to achieve the same thing, \n" +
                "        // by using something that is called Double Brace Initialization.\n" +
                "        private static final Set<String> COUNTRIES_DOUBLE_BRACE = \n" +
                "        new HashSet<String>() {{\n" +
                "            add(\"DENMARK\");\n" +
                "            add(\"SWEDEN\");\n" +
                "            add(\"FINLAND\");\n" +
                "        }}\n" +
                "\n" +
                "        // The first brace is creating a new AnonymousInnerClass and the\n" +
                "        // second one declares an instance initializer block. This block\n" +
                "        // is called when the anonymous inner class is created.\n" +
                "        // This does not only work for Collections, it works for all\n" +
                "        // non-final classes.\n" +
                "\n" +
                "\n" +
                "        // Another option was to initialize the Collection from an array,\n" +
                "        // using Arrays.asList() method:\n" +
                "        private static final List<String> COUNTRIES_AS_LIST = \n" +
                "                        Arrays.asList(\"SWEDEN\", \"DENMARK\", \"NORWAY\");\n" +
                "        // This has one catch: the list we get is internally backed by the array,\n" +
                "        // and since arrays can't change their size, the list backed by the array\n" +
                "        // is not resizeable, which means we can't add new elements to it: \n" +
                "        public static void main(String[] args) {\n" +
                "            COUNTRIES.add(\"FINLAND\"); // throws UnsupportedOperationException!\n" +
                "            // However, we can replace elements by index, just like in array: \n" +
                "            COUNTRIES.set(1, \"FINLAND\");\n" +
                "            System.out.println(COUNTRIES); // prints [SWEDEN, FINLAND, NORWAY]\n" +
                "        }\n" +
                "        // The resizing problem can be circumvented \n" +
                "        // by creating another Collection from the List:\n" +
                "         private static final Set<String> COUNTRIES_SET = \n" +
                "                new HashSet<>(Arrays.asList(\"SWEDEN\", \"DENMARK\", \"NORWAY\"));\n" +
                "        // It's perfectly fine to add anything to the Set of COUNTRIES now. \n" +
                "    } // End TestInitialization class\n" +
                "\n" +
                "    private static class TestJava11Initialization {\n" +
                "        // Since Java 11, there is a convenient option to initialize Collections:\n" +
                "        // Set.of() and List.of() methods. \n" +
                "        private static final Set<String> COUNTRIES = \n" +
                "                Set.of(\"SWEDEN\", \"DENMARK\", \"NORWAY\");\n" +
                "        // There is a massive catch, though: Lists and Sets initialized like this \n" +
                "        // 1) are immutable \n" +
                "        // 2) can't contain null elements (even check for null elements fails)!\n" +
                "        public static void main(String[] args) {\n" +
                "            COUNTRIES.add(\"FINLAND\"); // throws UnsupportedOperationException\n" +
                "            COUNTRIES.remove(\"NORWAY\"); // throws UnsupportedOperationException \n" +
                "            COUNTRIES.contains(null); // throws NullPointerException\n" +
                "        }\n" +
                "        private static final Set<String> COUNTRIES_WITH_NULL = \n" +
                "                    Set.of(\"SWEDEN\", null, \"NORWAY\"); // throws NullPointerException\n" +
                "\n" +
                "    } // End TestJava11Initialization class\n" +
                "} // End LearnJava class\n" +
                "\n" +
                "// You can include other, non-public outer-level classes in a .java file,\n" +
                "// but it is not good practice. Instead split classes into separate files.\n" +
                "\n" +
                "// Class Declaration Syntax:\n" +
                "// <public/private/protected> class <class name> {\n" +
                "//    // data fields, constructors, functions all inside.\n" +
                "//    // functions are called as methods in Java.\n" +
                "// }\n" +
                "\n" +
                "class Bicycle {\n" +
                "\n" +
                "    // Bicycle's Fields/Variables\n" +
                "    public int cadence; // Public: Can be accessed from anywhere\n" +
                "    private int speed;  // Private: Only accessible from within the class\n" +
                "    protected int gear; // Protected: Accessible from the class and subclasses\n" +
                "    String name; // default: Only accessible from within this package\n" +
                "    static String className; // Static class variable\n" +
                "\n" +
                "    // Static block\n" +
                "    // Java has no implementation of static constructors, but\n" +
                "    // has a static block that can be used to initialize class variables\n" +
                "    // (static variables).\n" +
                "    // This block will be called when the class is loaded.\n" +
                "    static {\n" +
                "        className = \"Bicycle\";\n" +
                "    }\n" +
                "\n" +
                "    // Constructors are a way of creating classes\n" +
                "    // This is a constructor\n" +
                "    public Bicycle() {\n" +
                "        // You can also call another constructor:\n" +
                "        // this(1, 50, 5, \"Bontrager\");\n" +
                "        gear = 1;\n" +
                "        cadence = 50;\n" +
                "        speed = 5;\n" +
                "        name = \"Bontrager\";\n" +
                "    }\n" +
                "    // This is a constructor that takes arguments\n" +
                "    public Bicycle(int startCadence, int startSpeed, int startGear,\n" +
                "        String name) {\n" +
                "        this.gear = startGear;\n" +
                "        this.cadence = startCadence;\n" +
                "        this.speed = startSpeed;\n" +
                "        this.name = name;\n" +
                "    }\n" +
                "\n" +
                "    // Method Syntax:\n" +
                "    // <public/private/protected> <return type> <function name>(<args>)\n" +
                "\n" +
                "    // Java classes often implement getters and setters for their fields\n" +
                "\n" +
                "    // Method declaration syntax:\n" +
                "    // <access modifier> <return type> <method name>(<args>)\n" +
                "    public int getCadence() {\n" +
                "        return cadence;\n" +
                "    }\n" +
                "\n" +
                "    // void methods require no return statement\n" +
                "    public void setCadence(int newValue) {\n" +
                "        cadence = newValue;\n" +
                "    }\n" +
                "    public void setGear(int newValue) {\n" +
                "        gear = newValue;\n" +
                "    }\n" +
                "    public void speedUp(int increment) {\n" +
                "        speed += increment;\n" +
                "    }\n" +
                "    public void slowDown(int decrement) {\n" +
                "        speed -= decrement;\n" +
                "    }\n" +
                "    public void setName(String newName) {\n" +
                "        name = newName;\n" +
                "    }\n" +
                "    public String getName() {\n" +
                "        return name;\n" +
                "    }\n" +
                "\n" +
                "    //Method to display the attribute values of this Object.\n" +
                "    @Override // Inherited from the Object class.\n" +
                "    public String toString() {\n" +
                "        return \"gear: \" + gear + \" cadence: \" + cadence + \" speed: \" + speed +\n" +
                "            \" name: \" + name;\n" +
                "    }\n" +
                "} // end class Bicycle\n" +
                "\n" +
                "// PennyFarthing is a subclass of Bicycle\n" +
                "class PennyFarthing extends Bicycle {\n" +
                "    // (Penny Farthings are those bicycles with the big front wheel.\n" +
                "    // They have no gears.)\n" +
                "\n" +
                "    public PennyFarthing(int startCadence, int startSpeed) {\n" +
                "        // Call the parent constructor with super\n" +
                "        super(startCadence, startSpeed, 0, \"PennyFarthing\");\n" +
                "    }\n" +
                "\n" +
                "    // You should mark a method you're overriding with an @annotation.\n" +
                "    // To learn more about what annotations are and their purpose check this\n" +
                "    // out: http://docs.oracle.com/javase/tutorial/java/annotations/\n" +
                "    @Override\n" +
                "    public void setGear(int gear) {\n" +
                "        this.gear = 0;\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// Object casting\n" +
                "// Since the PennyFarthing class is extending the Bicycle class, we can say\n" +
                "// a PennyFarthing is a Bicycle and write :\n" +
                "// Bicycle bicycle = new PennyFarthing();\n" +
                "// This is called object casting where an object is taken for another one. There\n" +
                "// are lots of details and deals with some more intermediate concepts here:\n" +
                "// https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html\n" +
                "\n" +
                "// Interfaces\n" +
                "// Interface declaration syntax\n" +
                "// <access-level> interface <interface-name> extends <super-interfaces> {\n" +
                "//     // Constants\n" +
                "//     // Method declarations\n" +
                "// }\n" +
                "\n" +
                "// Example - Food:\n" +
                "public interface Edible {\n" +
                "    public void eat(); // Any class that implements this interface, must\n" +
                "                       // implement this method.\n" +
                "}\n" +
                "\n" +
                "public interface Digestible {\n" +
                "    public void digest();\n" +
                "    // Since Java 8, interfaces can have default method.\n" +
                "    public default void defaultMethod() {\n" +
                "        System.out.println(\"Hi from default method ...\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// We can now create a class that implements both of these interfaces.\n" +
                "public class Fruit implements Edible, Digestible {\n" +
                "    @Override\n" +
                "    public void eat() {\n" +
                "        // ...\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void digest() {\n" +
                "        // ...\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// In Java, you can extend only one class, but you can implement many\n" +
                "// interfaces. For example:\n" +
                "public class ExampleClass extends ExampleClassParent implements InterfaceOne,\n" +
                "    InterfaceTwo {\n" +
                "    @Override\n" +
                "    public void InterfaceOneMethod() {\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void InterfaceTwoMethod() {\n" +
                "    }\n" +
                "\n" +
                "}\n" +
                "\n" +
                "// Abstract Classes\n" +
                "\n" +
                "// Abstract Class declaration syntax\n" +
                "// <access-level> abstract class <abstract-class-name> extends\n" +
                "// <super-abstract-classes> {\n" +
                "//     // Constants and variables\n" +
                "//     // Method declarations\n" +
                "// }\n" +
                "\n" +
                "// Abstract Classes cannot be instantiated.\n" +
                "// Abstract classes may define abstract methods.\n" +
                "// Abstract methods have no body and are marked abstract\n" +
                "// Non-abstract child classes must @Override all abstract methods\n" +
                "// from their super-classes.\n" +
                "// Abstract classes can be useful when combining repetitive logic\n" +
                "// with customised behavior, but as Abstract classes require\n" +
                "// inheritance, they violate \"Composition over inheritance\"\n" +
                "// so consider other approaches using composition.\n" +
                "// https://en.wikipedia.org/wiki/Composition_over_inheritance\n" +
                "\n" +
                "public abstract class Animal\n" +
                "{\n" +
                "    private int age;\n" +
                "\n" +
                "    public abstract void makeSound();\n" +
                "\n" +
                "    // Method can have a body\n" +
                "    public void eat()\n" +
                "    {\n" +
                "        System.out.println(\"I am an animal and I am Eating.\");\n" +
                "        // Note: We can access private variable here.\n" +
                "        age = 30;\n" +
                "    }\n" +
                "\n" +
                "    public void printAge()\n" +
                "    {\n" +
                "        System.out.println(age);\n" +
                "    }\n" +
                "\n" +
                "    // Abstract classes can have main method.\n" +
                "    public static void main(String[] args)\n" +
                "    {\n" +
                "        System.out.println(\"I am abstract\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Dog extends Animal\n" +
                "{\n" +
                "    // Note still have to override the abstract methods in the\n" +
                "    // abstract class.\n" +
                "    @Override\n" +
                "    public void makeSound()\n" +
                "    {\n" +
                "        System.out.println(\"Bark\");\n" +
                "        // age = 30;    ==> ERROR!    age is private to Animal\n" +
                "    }\n" +
                "\n" +
                "    // NOTE: You will get an error if you used the\n" +
                "    // @Override annotation here, since java doesn't allow\n" +
                "    // overriding of static methods.\n" +
                "    // What is happening here is called METHOD HIDING.\n" +
                "    // Check out this SO post: http://stackoverflow.com/questions/16313649/\n" +
                "    public static void main(String[] args)\n" +
                "    {\n" +
                "        Dog pluto = new Dog();\n" +
                "        pluto.makeSound();\n" +
                "        pluto.eat();\n" +
                "        pluto.printAge();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// Final Classes\n" +
                "\n" +
                "// Final Class declaration syntax\n" +
                "// <access-level> final <final-class-name> {\n" +
                "//     // Constants and variables\n" +
                "//     // Method declarations\n" +
                "// }\n" +
                "\n" +
                "// Final classes are classes that cannot be inherited from and are therefore a\n" +
                "// final child. In a way, final classes are the opposite of abstract classes\n" +
                "// because abstract classes must be extended, but final classes cannot be\n" +
                "// extended.\n" +
                "public final class SaberToothedCat extends Animal\n" +
                "{\n" +
                "    // Note still have to override the abstract methods in the\n" +
                "    // abstract class.\n" +
                "    @Override\n" +
                "    public void makeSound()\n" +
                "    {\n" +
                "        System.out.println(\"Roar\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// Final Methods\n" +
                "public abstract class Mammal()\n" +
                "{\n" +
                "    // Final Method Syntax:\n" +
                "    // <access modifier> final <return type> <function name>(<args>)\n" +
                "\n" +
                "    // Final methods, like, final classes cannot be overridden by a child\n" +
                "    // class, and are therefore the final implementation of the method.\n" +
                "    public final boolean isWarmBlooded()\n" +
                "    {\n" +
                "        return true;\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// Enum Type\n" +
                "//\n" +
                "// An enum type is a special data type that enables for a variable to be a set\n" +
                "// of predefined constants. The variable must be equal to one of the values\n" +
                "// that have been predefined for it. Because they are constants, the names of\n" +
                "// an enum type's fields are in uppercase letters. In the Java programming\n" +
                "// language, you define an enum type by using the enum keyword. For example,\n" +
                "// you would specify a days-of-the-week enum type as:\n" +
                "public enum Day {\n" +
                "    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,\n" +
                "    THURSDAY, FRIDAY, SATURDAY\n" +
                "}\n" +
                "\n" +
                "// We can use our enum Day like that:\n" +
                "public class EnumTest {\n" +
                "    // Variable Enum\n" +
                "    Day day;\n" +
                "\n" +
                "    public EnumTest(Day day) {\n" +
                "        this.day = day;\n" +
                "    }\n" +
                "\n" +
                "    public void tellItLikeItIs() {\n" +
                "        switch (day) {\n" +
                "            case MONDAY:\n" +
                "                System.out.println(\"Mondays are bad.\");\n" +
                "                break;\n" +
                "            case FRIDAY:\n" +
                "                System.out.println(\"Fridays are better.\");\n" +
                "                break;\n" +
                "            case SATURDAY:\n" +
                "            case SUNDAY:\n" +
                "                System.out.println(\"Weekends are best.\");\n" +
                "                break;\n" +
                "            default:\n" +
                "                System.out.println(\"Midweek days are so-so.\");\n" +
                "                break;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        EnumTest firstDay = new EnumTest(Day.MONDAY);\n" +
                "        firstDay.tellItLikeItIs(); // => Mondays are bad.\n" +
                "        EnumTest thirdDay = new EnumTest(Day.WEDNESDAY);\n" +
                "        thirdDay.tellItLikeItIs(); // => Midweek days are so-so.\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// Enum types are much more powerful than we show above.\n" +
                "// The enum body can include methods and other fields.\n" +
                "// You can see more at https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html\n" +
                "\n" +
                "// Getting Started with Lambda Expressions\n" +
                "//\n" +
                "// New to Java version 8 are lambda expressions. Lambdas are more commonly found\n" +
                "// in functional programming languages, which means they are methods which can\n" +
                "// be created without belonging to a class, passed around as if it were itself\n" +
                "// an object, and executed on demand.\n" +
                "//\n" +
                "// Final note, lambdas must implement a functional interface. A functional\n" +
                "// interface is one which has only a single abstract method declared. It can\n" +
                "// have any number of default methods. Lambda expressions can be used as an\n" +
                "// instance of that functional interface. Any interface meeting the requirements\n" +
                "// is treated as a functional interface. You can read more about interfaces\n" +
                "// above.\n" +
                "//\n" +
                "import java.util.Map;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.function.*;\n" +
                "import java.security.SecureRandom;\n" +
                "\n" +
                "public class Lambdas {\n" +
                "    public static void main(String[] args) {\n" +
                "        // Lambda declaration syntax:\n" +
                "    // <zero or more parameters> -> <expression body or statement block>\n" +
                "\n" +
                "        // We will use this hashmap in our examples below.\n" +
                "        Map<String, String> planets = new HashMap<>();\n" +
                "            planets.put(\"Mercury\", \"87.969\");\n" +
                "            planets.put(\"Venus\", \"224.7\");\n" +
                "            planets.put(\"Earth\", \"365.2564\");\n" +
                "            planets.put(\"Mars\", \"687\");\n" +
                "            planets.put(\"Jupiter\", \"4,332.59\");\n" +
                "            planets.put(\"Saturn\", \"10,759\");\n" +
                "            planets.put(\"Uranus\", \"30,688.5\");\n" +
                "            planets.put(\"Neptune\", \"60,182\");\n" +
                "\n" +
                "        // Lambda with zero parameters using the Supplier functional interface\n" +
                "        // from java.util.function.Supplier. The actual lambda expression is\n" +
                "        // what comes after numPlanets =.\n" +
                "        Supplier<String> numPlanets = () -> Integer.toString(planets.size());\n" +
                "        System.out.format(\"Number of Planets: %s\\n\\n\", numPlanets.get());\n" +
                "\n" +
                "        // Lambda with one parameter and using the Consumer functional interface\n" +
                "        // from java.util.function.Consumer. This is because planets is a Map,\n" +
                "        // which implements both Collection and Iterable. The forEach used here,\n" +
                "        // found in Iterable, applies the lambda expression to each member of\n" +
                "        // the Collection. The default implementation of forEach behaves as if:\n" +
                "        /*\n" +
                "            for (T t : this)\n" +
                "                action.accept(t);\n" +
                "        */\n" +
                "\n" +
                "        // The actual lambda expression is the parameter passed to forEach.\n" +
                "        planets.keySet().forEach((p) -> System.out.format(\"%s\\n\", p));\n" +
                "\n" +
                "        // If you are only passing a single argument, then the above can also be\n" +
                "        // written as (note absent parentheses around p):\n" +
                "        planets.keySet().forEach(p -> System.out.format(\"%s\\n\", p));\n" +
                "\n" +
                "        // Tracing the above, we see that planets is a HashMap, keySet() returns\n" +
                "        // a Set of its keys, forEach applies each element as the lambda\n" +
                "        // expression of: (parameter p) -> System.out.format(\"%s\\n\", p). Each\n" +
                "        // time, the element is said to be \"consumed\" and the statement(s)\n" +
                "        // referred to in the lambda body is applied. Remember the lambda body\n" +
                "        // is what comes after the ->.\n" +
                "\n" +
                "        // The above without use of lambdas would look more traditionally like:\n" +
                "        for (String planet : planets.keySet()) {\n" +
                "            System.out.format(\"%s\\n\", planet);\n" +
                "        }\n" +
                "\n" +
                "        // This example differs from the above in that a different forEach\n" +
                "        // implementation is used: the forEach found in the HashMap class\n" +
                "        // implementing the Map interface. This forEach accepts a BiConsumer,\n" +
                "        // which generically speaking is a fancy way of saying it handles\n" +
                "        // the Set of each Key -> Value pairs. This default implementation\n" +
                "        // behaves as if:\n" +
                "        /*\n" +
                "            for (Map.Entry<K, V> entry : map.entrySet())\n" +
                "                action.accept(entry.getKey(), entry.getValue());\n" +
                "        */\n" +
                "\n" +
                "        // The actual lambda expression is the parameter passed to forEach.\n" +
                "        String orbits = \"%s orbits the Sun in %s Earth days.\\n\";\n" +
                "        planets.forEach((K, V) -> System.out.format(orbits, K, V));\n" +
                "\n" +
                "        // The above without use of lambdas would look more traditionally like:\n" +
                "        for (String planet : planets.keySet()) {\n" +
                "            System.out.format(orbits, planet, planets.get(planet));\n" +
                "        }\n" +
                "\n" +
                "        // Or, if following more closely the specification provided by the\n" +
                "        // default implementation:\n" +
                "        for (Map.Entry<String, String> planet : planets.entrySet()) {\n" +
                "            System.out.format(orbits, planet.getKey(), planet.getValue());\n" +
                "        }\n" +
                "\n" +
                "        // These examples cover only the very basic use of lambdas. It might not\n" +
                "        // seem like much or even very useful, but remember that a lambda can be\n" +
                "        // created as an object that can later be passed as parameters to other\n" +
                "        // methods.\n" +
                "    }\n" +
                "}";
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        binding.codeView.setOnHighlightListener(this)
                .setOnHighlightListener(this)
                .setTheme(Theme.MONOKAI_SUBLIME)
                .setCode(code)
                .setLanguage(Language.JAVA)
                .setWrapLine(false)
                .setFontSize(13)
                .setZoomEnabled(true)
                .setShowLineNumber(true)
                .apply();

    }

    @Override
    public void onStartCodeHighlight() {

    }

    @Override
    public void onFinishCodeHighlight() {

    }

    @Override
    public void onLanguageDetected(Language language, int relevance) {

    }

    @Override
    public void onFontSizeChanged(int sizeInPx) {

    }

    @Override
    public void onLineClicked(int lineNumber, String content) {

    }
}


