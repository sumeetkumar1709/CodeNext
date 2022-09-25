package com.segmnf.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.FirebaseDatabase;
import com.segmnf.myapplication.databinding.ActivityLearnCactivityBinding;
import com.segmnf.myapplication.databinding.ActivityLearnJavaBinding;

import br.tiagohm.codeview.CodeView;
import br.tiagohm.codeview.Language;
import br.tiagohm.codeview.Theme;

public class LearnCActivity extends AppCompatActivity implements CodeView.OnHighlightListener {
    ActivityLearnCactivityBinding binding;
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
        binding = ActivityLearnCactivityBinding.inflate(getLayoutInflater());
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
        String code= "// Single-line comments start with // - only available in C99 and later.\n" +
                "\n" +
                "/*\n" +
                "Multi-line comments look like this. They work in C89 as well.\n" +
                "*/\n" +
                "\n" +
                "/*\n" +
                "Multi-line comments don't nest /* Be careful */  // comment ends on this line...\n" +
                "*/ // ...not this one!\n" +
                "\n" +
                "// Constants: #define <keyword>\n" +
                "// Constants are written in all-caps out of convention, not requirement\n" +
                "#define DAYS_IN_YEAR 365\n" +
                "\n" +
                "// Enumeration constants are also ways to declare constants.\n" +
                "// All statements must end with a semicolon\n" +
                "enum days {SUN, MON, TUE, WED, THU, FRI, SAT};\n" +
                "// SUN gets 0, MON gets 1, TUE gets 2, etc.\n" +
                "\n" +
                "// Enumeration values can also be specified\n" +
                "enum days {SUN = 1, MON, TUE, WED = 99, THU, FRI, SAT};\n" +
                "// MON gets 2 automatically, TUE gets 3, etc.\n" +
                "// WED get 99, THU gets 100, FRI gets 101, etc.\n" +
                "\n" +
                "// Import headers with #include\n" +
                "#include <stdlib.h>\n" +
                "#include <stdio.h>\n" +
                "#include <string.h>\n" +
                "\n" +
                "// File names between <angle brackets> tell the compiler to look in your system\n" +
                "// libraries for the headers.\n" +
                "// For your own headers, use double quotes instead of angle brackets, and\n" +
                "// provide the path:\n" +
                "#include \"my_header.h\"      // local file\n" +
                "#include \"../my_lib/my_lib_header.h\" //relative path\n" +
                "\n" +
                "// Declare function signatures in advance in a .h file, or at the top of\n" +
                "// your .c file.\n" +
                "void function_1();\n" +
                "int function_2(void);\n" +
                "\n" +
                "// At a minimum, you must declare a 'function prototype' before its use in any function.\n" +
                "// Normally, prototypes are placed at the top of a file before any function definition.\n" +
                "int add_two_ints(int x1, int x2); // function prototype\n" +
                "// although `int add_two_ints(int, int);` is also valid (no need to name the args),\n" +
                "// it is recommended to name arguments in the prototype as well for easier inspection\n" +
                "\n" +
                "// Function prototypes are not necessary if the function definition comes before\n" +
                "// any other function that calls that function. However, it's standard practice to\n" +
                "// always add the function prototype to a header file (*.h) and then #include that\n" +
                "// file at the top. This prevents any issues where a function might be called\n" +
                "// before the compiler knows of its existence, while also giving the developer a\n" +
                "// clean header file to share with the rest of the project.\n" +
                "\n" +
                "// Your program's entry point is a function called \"main\". The return type can\n" +
                "// be anything, however most operating systems expect a return type of `int` for\n" +
                "// error code processing.\n" +
                "int main(void) {\n" +
                "  // your program\n" +
                "}\n" +
                "\n" +
                "// The command line arguments used to run your program are also passed to main\n" +
                "// argc being the number of arguments - your program's name counts as 1\n" +
                "// argv is an array of character arrays - containing the arguments themselves\n" +
                "// argv[0] = name of your program, argv[1] = first argument, etc.\n" +
                "int main (int argc, char** argv)\n" +
                "{\n" +
                "  // print output using printf, for \"print formatted\"\n" +
                "  // %d is an integer, \\n is a newline\n" +
                "  printf(\"%d\\n\", 0); // => Prints 0\n" +
                "\n" +
                "  ///////////////////////////////////////\n" +
                "  // Types\n" +
                "  ///////////////////////////////////////\n" +
                "\n" +
                "  // Compilers that are not C99-compliant require that variables MUST be\n" +
                "  // declared at the top of the current block scope.\n" +
                "  // Compilers that ARE C99-compliant allow declarations near the point where\n" +
                "  // the value is used.\n" +
                "  // For the sake of the tutorial, variables are declared dynamically under\n" +
                "  // C99-compliant standards.\n" +
                "\n" +
                "  // ints are usually 4 bytes (use the `sizeof` operator to check)\n" +
                "  int x_int = 0;\n" +
                "\n" +
                "  // shorts are usually 2 bytes (use the `sizeof` operator to check)\n" +
                "  short x_short = 0;\n" +
                "\n" +
                "  // chars are defined as the smallest addressable unit for a processor. \n" +
                "  // This is usually 1 byte, but for some systems it can be more (ex. for TMS320 from TI it's 2 bytes).\n" +
                "  char x_char = 0;\n" +
                "  char y_char = 'y'; // Char literals are quoted with ''\n" +
                "\n" +
                "  // longs are often 4 to 8 bytes; long longs are guaranteed to be at least\n" +
                "  // 8 bytes\n" +
                "  long x_long = 0;\n" +
                "  long long x_long_long = 0;\n" +
                "\n" +
                "  // floats are usually 32-bit floating point numbers\n" +
                "  float x_float = 0.0f; // 'f' suffix here denotes floating point literal\n" +
                "\n" +
                "  // doubles are usually 64-bit floating-point numbers\n" +
                "  double x_double = 0.0; // real numbers without any suffix are doubles\n" +
                "\n" +
                "  // integer types may be unsigned (greater than or equal to zero)\n" +
                "  unsigned short ux_short;\n" +
                "  unsigned int ux_int;\n" +
                "  unsigned long long ux_long_long;\n" +
                "\n" +
                "  // chars inside single quotes are integers in machine's character set.\n" +
                "  '0'; // => 48 in the ASCII character set.\n" +
                "  'A'; // => 65 in the ASCII character set.\n" +
                "\n" +
                "  // sizeof(T) gives you the size of a variable with type T in bytes\n" +
                "  // sizeof(obj) yields the size of the expression (variable, literal, etc.).\n" +
                "  printf(\"%zu\\n\", sizeof(int)); // => 4 (on most machines with 4-byte words)\n" +
                "\n" +
                "  // If the argument of the `sizeof` operator is an expression, then its argument\n" +
                "  // is not evaluated (except VLAs (see below)).\n" +
                "  // The value it yields in this case is a compile-time constant.\n" +
                "  int a = 1;\n" +
                "  // size_t is an unsigned integer type of at least 2 bytes used to represent\n" +
                "  // the size of an object.\n" +
                "  size_t size = sizeof(a++); // a++ is not evaluated\n" +
                "  printf(\"sizeof(a++) = %zu where a = %d\\n\", size, a);\n" +
                "  // prints \"sizeof(a++) = 4 where a = 1\" (on a 32-bit architecture)\n" +
                "\n" +
                "  // Arrays must be initialized with a concrete size.\n" +
                "  char my_char_array[20]; // This array occupies 1 * 20 = 20 bytes\n" +
                "  int my_int_array[20]; // This array occupies 4 * 20 = 80 bytes\n" +
                "  // (assuming 4-byte words)\n" +
                "\n" +
                "  // You can initialize an array of twenty ints that all equal 0 thusly:\n" +
                "  int my_array[20] = {0};\n" +
                "  // where the \"{0}\" part is called an \"array initializer\".\n" +
                "  // All elements (if any) past the ones in the initializer are initialized to 0:\n" +
                "  int my_array[5] = {1, 2};\n" +
                "  // So my_array now has five elements, all but the first two of which are 0: \n" +
                "  // [1, 2, 0, 0, 0]\n" +
                "  // NOTE that you get away without explicitly declaring the size \n" +
                "  // of the array IF you initialize the array on the same line:\n" +
                "  int my_array[] = {0};\n" +
                "  // NOTE that, when not declaring the size, the size of the array is the number \n" +
                "  // of elements in the initializer. With \"{0}\", my_array is now of size one: [0]\n" +
                "  // To evaluate the size of the array at run-time, divide its byte size by the\n" +
                "  // byte size of its element type:\n" +
                "  size_t my_array_size = sizeof(my_array) / sizeof(my_array[0]);\n" +
                "  // WARNING You should evaluate the size *before* you begin passing the array \n" +
                "  // to functions (see later discussion) because arrays get \"downgraded\" to \n" +
                "  // raw pointers when they are passed to functions (so the statement above \n" +
                "  // will produce the wrong result inside the function).\n" +
                "\n" +
                "  // Indexing an array is like other languages -- or,\n" +
                "  // rather, other languages are like C\n" +
                "  my_array[0]; // => 0\n" +
                "\n" +
                "  // Arrays are mutable; it's just memory!\n" +
                "  my_array[1] = 2;\n" +
                "  printf(\"%d\\n\", my_array[1]); // => 2\n" +
                "\n" +
                "  // In C99 (and as an optional feature in C11), variable-length arrays (VLAs)\n" +
                "  // can be declared as well. The size of such an array need not be a compile\n" +
                "  // time constant:\n" +
                "  printf(\"Enter the array size: \"); // ask the user for an array size\n" +
                "  int array_size;\n" +
                "  fscanf(stdin, \"%d\", &array_size);\n" +
                "  int var_length_array[array_size]; // declare the VLA\n" +
                "  printf(\"sizeof array = %zu\\n\", sizeof var_length_array);\n" +
                "\n" +
                "  // Example:\n" +
                "  // > Enter the array size: 10\n" +
                "  // > sizeof array = 40\n" +
                "\n" +
                "  // Strings are just arrays of chars terminated by a NULL (0x00) byte,\n" +
                "  // represented in strings as the special character '\\0'.\n" +
                "  // (We don't have to include the NULL byte in string literals; the compiler\n" +
                "  //  inserts it at the end of the array for us.)\n" +
                "  char a_string[20] = \"This is a string\";\n" +
                "  printf(\"%s\\n\", a_string); // %s formats a string\n" +
                "\n" +
                "  printf(\"%d\\n\", a_string[16]); // => 0\n" +
                "  // i.e., byte #17 is 0 (as are 18, 19, and 20)\n" +
                "\n" +
                "  // If we have characters between single quotes, that's a character literal.\n" +
                "  // It's of type `int`, and *not* `char` (for historical reasons).\n" +
                "  int cha = 'a'; // fine\n" +
                "  char chb = 'a'; // fine too (implicit conversion from int to char)\n" +
                "\n" +
                "  // Multi-dimensional arrays:\n" +
                "  int multi_array[2][5] = {\n" +
                "    {1, 2, 3, 4, 5},\n" +
                "    {6, 7, 8, 9, 0}\n" +
                "  };\n" +
                "  // access elements:\n" +
                "  int array_int = multi_array[0][2]; // => 3\n" +
                "\n" +
                "  ///////////////////////////////////////\n" +
                "  // Operators\n" +
                "  ///////////////////////////////////////\n" +
                "\n" +
                "  // Shorthands for multiple declarations:\n" +
                "  int i1 = 1, i2 = 2;\n" +
                "  float f1 = 1.0, f2 = 2.0;\n" +
                "\n" +
                "  int b, c;\n" +
                "  b = c = 0;\n" +
                "\n" +
                "  // Arithmetic is straightforward\n" +
                "  i1 + i2; // => 3\n" +
                "  i2 - i1; // => 1\n" +
                "  i2 * i1; // => 2\n" +
                "  i1 / i2; // => 0 (0.5, but truncated towards 0)\n" +
                "\n" +
                "  // You need to cast at least one integer to float to get a floating-point result\n" +
                "  (float)i1 / i2; // => 0.5f\n" +
                "  i1 / (double)i2; // => 0.5 // Same with double\n" +
                "  f1 / f2; // => 0.5, plus or minus epsilon\n" +
                "\n" +
                "  // Floating-point numbers are defined by IEEE 754, thus cannot store perfectly\n" +
                "  // exact values. For instance, the following does not produce expected results \n" +
                "  // because 0.1 might actually be 0.099999999999 insided the computer, and 0.3 \n" +
                "  // might be stored as 0.300000000001. \n" +
                "  (0.1 + 0.1 + 0.1) != 0.3; // => 1 (true)\n" +
                "  // and it is NOT associative due to reasons mentioned above.\n" +
                "  1 + (1e123 - 1e123) != (1 + 1e123) - 1e123; // => 1 (true)\n" +
                "  // this notation is scientific notations for numbers: 1e123 = 1*10^123\n" +
                "\n" +
                "  // It is important to note that most all systems have used IEEE 754 to\n" +
                "  // represent floating points. Even python, used for scientific computing,\n" +
                "  // eventually calls C which uses IEEE 754. It is mentioned this way not to\n" +
                "  // indicate that this is a poor implementation, but instead as a warning\n" +
                "  // that when doing floating point comparisons, a little bit of error (epsilon)\n" +
                "  // needs to be considered. \n" +
                "\n" +
                "  // Modulo is there as well, but be careful if arguments are negative\n" +
                "  11 % 3;    // => 2 as 11 = 2 + 3*x (x=3)\n" +
                "  (-11) % 3; // => -2, as one would expect\n" +
                "  11 % (-3); // => 2 and not -2, and it's quite counter intuitive\n" +
                "\n" +
                "  // Comparison operators are probably familiar, but\n" +
                "  // there is no Boolean type in C. We use ints instead.\n" +
                "  // (C99 introduced the _Bool type provided in stdbool.h)\n" +
                "  // 0 is false, anything else is true. (The comparison\n" +
                "  // operators always yield 0 or 1.)\n" +
                "  3 == 2; // => 0 (false)\n" +
                "  3 != 2; // => 1 (true)\n" +
                "  3 > 2;  // => 1\n" +
                "  3 < 2;  // => 0\n" +
                "  2 <= 2; // => 1\n" +
                "  2 >= 2; // => 1\n" +
                "\n" +
                "  // C is not Python - comparisons do NOT chain.\n" +
                "  // Warning: The line below will compile, but it means `(0 < a) < 2`.\n" +
                "  // This expression is always true, because (0 < a) could be either 1 or 0.\n" +
                "  // In this case it's 1, because (0 < 1).\n" +
                "  int between_0_and_2 = 0 < a < 2;\n" +
                "  // Instead use:\n" +
                "  int between_0_and_2 = 0 < a && a < 2;\n" +
                "\n" +
                "  // Logic works on ints\n" +
                "  !3; // => 0 (Logical not)\n" +
                "  !0; // => 1\n" +
                "  1 && 1; // => 1 (Logical and)\n" +
                "  0 && 1; // => 0\n" +
                "  0 || 1; // => 1 (Logical or)\n" +
                "  0 || 0; // => 0\n" +
                "\n" +
                "  // Conditional ternary expression ( ? : )\n" +
                "  int e = 5;\n" +
                "  int f = 10;\n" +
                "  int z;\n" +
                "  z = (e > f) ? e : f; // => 10 \"if e > f return e, else return f.\"\n" +
                "\n" +
                "  // Increment and decrement operators:\n" +
                "  int j = 0;\n" +
                "  int s = j++; // Return j THEN increase j. (s = 0, j = 1)\n" +
                "  s = ++j; // Increase j THEN return j. (s = 2, j = 2)\n" +
                "  // same with j-- and --j\n" +
                "\n" +
                "  // Bitwise operators!\n" +
                "  ~0x0F; // => 0xFFFFFFF0 (bitwise negation, \"1's complement\", example result for 32-bit int)\n" +
                "  0x0F & 0xF0; // => 0x00 (bitwise AND)\n" +
                "  0x0F | 0xF0; // => 0xFF (bitwise OR)\n" +
                "  0x04 ^ 0x0F; // => 0x0B (bitwise XOR)\n" +
                "  0x01 << 1; // => 0x02 (bitwise left shift (by 1))\n" +
                "  0x02 >> 1; // => 0x01 (bitwise right shift (by 1))\n" +
                "\n" +
                "  // Be careful when shifting signed integers - the following are undefined:\n" +
                "  // - shifting into the sign bit of a signed integer (int a = 1 << 31)\n" +
                "  // - left-shifting a negative number (int a = -1 << 2)\n" +
                "  // - shifting by an offset which is >= the width of the type of the LHS:\n" +
                "  //   int a = 1 << 32; // UB if int is 32 bits wide\n" +
                "\n" +
                "  ///////////////////////////////////////\n" +
                "  // Control Structures\n" +
                "  ///////////////////////////////////////\n" +
                "\n" +
                "  if (0) {\n" +
                "    printf(\"I am never run\\n\");\n" +
                "  } else if (0) {\n" +
                "    printf(\"I am also never run\\n\");\n" +
                "  } else {\n" +
                "    printf(\"I print\\n\");\n" +
                "  }\n" +
                "\n" +
                "  // While loops exist\n" +
                "  int ii = 0;\n" +
                "  while (ii < 10) { //ANY value less than ten is true.\n" +
                "    printf(\"%d, \", ii++); // ii++ increments ii AFTER using its current value.\n" +
                "  } // => prints \"0, 1, 2, 3, 4, 5, 6, 7, 8, 9, \"\n" +
                "\n" +
                "  printf(\"\\n\");\n" +
                "\n" +
                "  int kk = 0;\n" +
                "  do {\n" +
                "    printf(\"%d, \", kk);\n" +
                "  } while (++kk < 10); // ++kk increments kk BEFORE using its current value.\n" +
                "  // => prints \"0, 1, 2, 3, 4, 5, 6, 7, 8, 9, \"\n" +
                "\n" +
                "  printf(\"\\n\");\n" +
                "\n" +
                "  // For loops too\n" +
                "  int jj;\n" +
                "  for (jj=0; jj < 10; jj++) {\n" +
                "    printf(\"%d, \", jj);\n" +
                "  } // => prints \"0, 1, 2, 3, 4, 5, 6, 7, 8, 9, \"\n" +
                "\n" +
                "  printf(\"\\n\");\n" +
                "\n" +
                "  // *****NOTES*****:\n" +
                "  // Loops and Functions MUST have a body. If no body is needed:\n" +
                "  int i;\n" +
                "  for (i = 0; i <= 5; i++) {\n" +
                "    ; // use semicolon to act as the body (null statement)\n" +
                "  }\n" +
                "  // Or\n" +
                "  for (i = 0; i <= 5; i++);\n" +
                "\n" +
                "  // branching with multiple choices: switch()\n" +
                "  switch (a) {\n" +
                "  case 0: // labels need to be integral *constant* expressions (such as enums)\n" +
                "    printf(\"Hey, 'a' equals 0!\\n\");\n" +
                "    break; // if you don't break, control flow falls over labels\n" +
                "  case 1:\n" +
                "    printf(\"Huh, 'a' equals 1!\\n\");\n" +
                "    break;\n" +
                "    // Be careful - without a \"break\", execution continues until the\n" +
                "    // next \"break\" is reached.\n" +
                "  case 3:\n" +
                "  case 4:\n" +
                "    printf(\"Look at that.. 'a' is either 3, or 4\\n\");\n" +
                "    break;\n" +
                "  default:\n" +
                "    // if `some_integral_expression` didn't match any of the labels\n" +
                "    fputs(\"Error!\\n\", stderr);\n" +
                "    exit(-1);\n" +
                "    break;\n" +
                "  }\n" +
                "  /*\n" +
                "    Using \"goto\" in C\n" +
                "  */\n" +
                "  typedef enum { false, true } bool;\n" +
                "  // for C don't have bool as data type before C99 :(\n" +
                "  bool disaster = false;\n" +
                "  int i, j;\n" +
                "  for(i=0; i<100; ++i)\n" +
                "  for(j=0; j<100; ++j)\n" +
                "  {\n" +
                "    if((i + j) >= 150)\n" +
                "        disaster = true;\n" +
                "    if(disaster)\n" +
                "        goto error;  // exit both for loops\n" +
                "  }\n" +
                "  error: // this is a label that you can \"jump\" to with \"goto error;\"\n" +
                "  printf(\"Error occurred at i = %d & j = %d.\\n\", i, j);\n" +
                "  /*\n" +
                "    https://ideone.com/GuPhd6\n" +
                "    this will print out \"Error occurred at i = 51 & j = 99.\"\n" +
                "  */\n" +
                "  /*\n" +
                "    it is generally considered bad practice to do so, except if\n" +
                "    you really know what you are doing. See \n" +
                "    https://en.wikipedia.org/wiki/Spaghetti_code#Meaning\n" +
                "  */\n" +
                "\n" +
                "  ///////////////////////////////////////\n" +
                "  // Typecasting\n" +
                "  ///////////////////////////////////////\n" +
                "\n" +
                "  // Every value in C has a type, but you can cast one value into another type\n" +
                "  // if you want (with some constraints).\n" +
                "\n" +
                "  int x_hex = 0x01; // You can assign vars with hex literals\n" +
                "                    // binary is not in the standard, but allowed by some\n" +
                "                    // compilers (x_bin = 0b0010010110) \n" +
                "\n" +
                "  // Casting between types will attempt to preserve their numeric values\n" +
                "  printf(\"%d\\n\", x_hex); // => Prints 1\n" +
                "  printf(\"%d\\n\", (short) x_hex); // => Prints 1\n" +
                "  printf(\"%d\\n\", (char) x_hex); // => Prints 1\n" +
                "\n" +
                "  // If you assign a value greater than a types max val, it will rollover\n" +
                "  // without warning.\n" +
                "  printf(\"%d\\n\", (unsigned char) 257); // => 1 (Max char = 255 if char is 8 bits long)\n" +
                "\n" +
                "  // For determining the max value of a `char`, a `signed char` and an `unsigned char`,\n" +
                "  // respectively, use the CHAR_MAX, SCHAR_MAX and UCHAR_MAX macros from <limits.h>\n" +
                "\n" +
                "  // Integral types can be cast to floating-point types, and vice-versa.\n" +
                "  printf(\"%f\\n\", (double) 100); // %f always formats a double...\n" +
                "  printf(\"%f\\n\", (float)  100); // ...even with a float.\n" +
                "  printf(\"%d\\n\", (char)100.0);\n" +
                "\n" +
                "  ///////////////////////////////////////\n" +
                "  // Pointers\n" +
                "  ///////////////////////////////////////\n" +
                "\n" +
                "  // A pointer is a variable declared to store a memory address. Its declaration will\n" +
                "  // also tell you the type of data it points to. You can retrieve the memory address\n" +
                "  // of your variables, then mess with them.\n" +
                "\n" +
                "  int x = 0;\n" +
                "  printf(\"%p\\n\", (void *)&x); // Use & to retrieve the address of a variable\n" +
                "  // (%p formats an object pointer of type void *)\n" +
                "  // => Prints some address in memory;\n" +
                "\n" +
                "  // Pointers start with * in their declaration\n" +
                "  int *px, not_a_pointer; // px is a pointer to an int\n" +
                "  px = &x; // Stores the address of x in px\n" +
                "  printf(\"%p\\n\", (void *)px); // => Prints some address in memory\n" +
                "  printf(\"%zu, %zu\\n\", sizeof(px), sizeof(not_a_pointer));\n" +
                "  // => Prints \"8, 4\" on a typical 64-bit system\n" +
                "\n" +
                "  // To retrieve the value at the address a pointer is pointing to,\n" +
                "  // put * in front to dereference it.\n" +
                "  // Note: yes, it may be confusing that '*' is used for _both_ declaring a\n" +
                "  // pointer and dereferencing it.\n" +
                "  printf(\"%d\\n\", *px); // => Prints 0, the value of x\n" +
                "\n" +
                "  // You can also change the value the pointer is pointing to.\n" +
                "  // We'll have to wrap the dereference in parenthesis because\n" +
                "  // ++ has a higher precedence than *.\n" +
                "  (*px)++; // Increment the value px is pointing to by 1\n" +
                "  printf(\"%d\\n\", *px); // => Prints 1\n" +
                "  printf(\"%d\\n\", x); // => Prints 1\n" +
                "\n" +
                "  // Arrays are a good way to allocate a contiguous block of memory\n" +
                "  int x_array[20]; //declares array of size 20 (cannot change size)\n" +
                "  int xx;\n" +
                "  for (xx = 0; xx < 20; xx++) {\n" +
                "    x_array[xx] = 20 - xx;\n" +
                "  } // Initialize x_array to 20, 19, 18,... 2, 1\n" +
                "\n" +
                "  // Declare a pointer of type int and initialize it to point to x_array\n" +
                "  int* x_ptr = x_array;\n" +
                "  // x_ptr now points to the first element in the array (the integer 20).\n" +
                "  // This works because arrays often decay into pointers to their first element.\n" +
                "  // For example, when an array is passed to a function or is assigned to a pointer,\n" +
                "  // it decays into (implicitly converted to) a pointer.\n" +
                "  // Exceptions: when the array is the argument of the `&` (address-of) operator:\n" +
                "  int arr[10];\n" +
                "  int (*ptr_to_arr)[10] = &arr; // &arr is NOT of type `int *`!\n" +
                "  // It's of type \"pointer to array\" (of ten `int`s).\n" +
                "  // or when the array is a string literal used for initializing a char array:\n" +
                "  char otherarr[] = \"foobarbazquirk\";\n" +
                "  // or when it's the argument of the `sizeof` or `alignof` operator:\n" +
                "  int arraythethird[10];\n" +
                "  int *ptr = arraythethird; // equivalent with int *ptr = &arr[0];\n" +
                "  printf(\"%zu, %zu\\n\", sizeof(arraythethird), sizeof(ptr));\n" +
                "  // probably prints \"40, 4\" or \"40, 8\"\n" +
                "\n" +
                "  // Pointers are incremented and decremented based on their type\n" +
                "  // (this is called pointer arithmetic)\n" +
                "  printf(\"%d\\n\", *(x_ptr + 1)); // => Prints 19\n" +
                "  printf(\"%d\\n\", x_array[1]); // => Prints 19\n" +
                "\n" +
                "  // You can also dynamically allocate contiguous blocks of memory with the\n" +
                "  // standard library function malloc, which takes one argument of type size_t\n" +
                "  // representing the number of bytes to allocate (usually from the heap, although this\n" +
                "  // may not be true on e.g. embedded systems - the C standard says nothing about it).\n" +
                "  int *my_ptr = malloc(sizeof(*my_ptr) * 20);\n" +
                "  for (xx = 0; xx < 20; xx++) {\n" +
                "    *(my_ptr + xx) = 20 - xx; // my_ptr[xx] = 20-xx\n" +
                "  } // Initialize memory to 20, 19, 18, 17... 2, 1 (as ints)\n" +
                "\n" +
                "  // Be careful passing user-provided values to malloc! If you want\n" +
                "  // to be safe, you can use calloc instead (which, unlike malloc, also zeros out the memory)\n" +
                "  int* my_other_ptr = calloc(20, sizeof(int));\n" +
                "\n" +
                "  // Note that there is no standard way to get the length of a\n" +
                "  // dynamically allocated array in C. Because of this, if your arrays are\n" +
                "  // going to be passed around your program a lot, you need another variable\n" +
                "  // to keep track of the number of elements (size) of an array. See the\n" +
                "  // functions section for more info.\n" +
                "  size_t size = 10;\n" +
                "  int *my_arr = calloc(size, sizeof(int));\n" +
                "  // Add an element to the array\n" +
                "  size++;\n" +
                "  my_arr = realloc(my_arr, sizeof(int) * size);\n" +
                "  if (my_arr == NULL) {\n" +
                "    //Remember to check for realloc failure!\n" +
                "    return\n" +
                "  }\n" +
                "  my_arr[10] = 5;\n" +
                "\n" +
                "  // Dereferencing memory that you haven't allocated gives\n" +
                "  // \"unpredictable results\" - the program is said to invoke \"undefined behavior\"\n" +
                "  printf(\"%d\\n\", *(my_ptr + 21)); // => Prints who-knows-what? It may even crash.\n" +
                "\n" +
                "  // When you're done with a malloc'd block of memory, you need to free it,\n" +
                "  // or else no one else can use it until your program terminates\n" +
                "  // (this is called a \"memory leak\"):\n" +
                "  free(my_ptr);\n" +
                "\n" +
                "  // Strings are arrays of char, but they are usually represented as a\n" +
                "  // pointer-to-char (which is a pointer to the first element of the array).\n" +
                "  // It's good practice to use `const char *' when referring to a string literal,\n" +
                "  // since string literals shall not be modified (i.e. \"foo\"[0] = 'a' is ILLEGAL.)\n" +
                "  const char *my_str = \"This is my very own string literal\";\n" +
                "  printf(\"%c\\n\", *my_str); // => 'T'\n" +
                "\n" +
                "  // This is not the case if the string is an array\n" +
                "  // (potentially initialized with a string literal)\n" +
                "  // that resides in writable memory, as in:\n" +
                "  char foo[] = \"foo\";\n" +
                "  foo[0] = 'a'; // this is legal, foo now contains \"aoo\"\n" +
                "\n" +
                "  function_1();\n" +
                "} // end main function\n" +
                "\n" +
                "///////////////////////////////////////\n" +
                "// Functions\n" +
                "///////////////////////////////////////\n" +
                "\n" +
                "// Function declaration syntax:\n" +
                "// <return type> <function name>(<args>)\n" +
                "\n" +
                "int add_two_ints(int x1, int x2)\n" +
                "{\n" +
                "  return x1 + x2; // Use return to return a value\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                "Functions are call by value. When a function is called, the arguments passed to\n" +
                "the function are copies of the original arguments (except arrays). Anything you\n" +
                "do to the arguments in the function do not change the value of the original\n" +
                "argument where the function was called.\n" +
                "\n" +
                "Use pointers if you need to edit the original argument values (arrays are always\n" +
                "passed in as pointers).\n" +
                "\n" +
                "Example: in-place string reversal\n" +
                "*/\n" +
                "\n" +
                "// A void function returns no value\n" +
                "void str_reverse(char *str_in)\n" +
                "{\n" +
                "  char tmp;\n" +
                "  size_t ii = 0;\n" +
                "  size_t len = strlen(str_in); // `strlen()` is part of the c standard library\n" +
                "                               // NOTE: length returned by `strlen` DOESN'T\n" +
                "                               //       include the terminating NULL byte ('\\0')\n" +
                "  // in C99 and newer versions, you can directly declare loop control variables\n" +
                "  // in the loop's parentheses. e.g., `for (size_t ii = 0; ...`\n" +
                "  for (ii = 0; ii < len / 2; ii++) {\n" +
                "    tmp = str_in[ii];\n" +
                "    str_in[ii] = str_in[len - ii - 1]; // ii-th char from end\n" +
                "    str_in[len - ii - 1] = tmp;\n" +
                "  }\n" +
                "}\n" +
                "//NOTE: string.h header file needs to be included to use strlen()\n" +
                "\n" +
                "/*\n" +
                "char c[] = \"This is a test.\";\n" +
                "str_reverse(c);\n" +
                "printf(\"%s\\n\", c); // => \".tset a si sihT\"\n" +
                "*/\n" +
                "/*\n" +
                "as we can return only one variable\n" +
                "to change values of more than one variables we use call by reference\n" +
                "*/\n" +
                "void swapTwoNumbers(int *a, int *b)\n" +
                "{\n" +
                "    int temp = *a;\n" +
                "    *a = *b;\n" +
                "    *b = temp;\n" +
                "}\n" +
                "/*\n" +
                "int first = 10;\n" +
                "int second = 20;\n" +
                "printf(\"first: %d\\nsecond: %d\\n\", first, second);\n" +
                "swapTwoNumbers(&first, &second);\n" +
                "printf(\"first: %d\\nsecond: %d\\n\", first, second);\n" +
                "// values will be swapped\n" +
                "*/\n" +
                "\n" +
                "// Return multiple values. \n" +
                "// C does not allow for returning multiple values with the return statement. If\n" +
                "// you would like to return multiple values, then the caller must pass in the\n" +
                "// variables where they would like the returned values to go. These variables must\n" +
                "// be passed in as pointers such that the function can modify them.\n" +
                "int return_multiple( int *array_of_3, int *ret1, int *ret2, int *ret3)\n" +
                "{\n" +
                "    if(array_of_3 == NULL)\n" +
                "        return 0; //return error code (false)\n" +
                "\n" +
                "    //de-reference the pointer so we modify its value\n" +
                "   *ret1 = array_of_3[0]; \n" +
                "   *ret2 = array_of_3[1]; \n" +
                "   *ret3 = array_of_3[2]; \n" +
                "\n" +
                "   return 1; //return error code (true)\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                "With regards to arrays, they will always be passed to functions\n" +
                "as pointers. Even if you statically allocate an array like `arr[10]`,\n" +
                "it still gets passed as a pointer to the first element in any function calls.\n" +
                "Again, there is no standard way to get the size of a dynamically allocated\n" +
                "array in C.\n" +
                "*/\n" +
                "// Size must be passed!\n" +
                "// Otherwise, this function has no way of knowing how big the array is.\n" +
                "void printIntArray(int *arr, size_t size) {\n" +
                "    int i;\n" +
                "    for (i = 0; i < size; i++) {\n" +
                "        printf(\"arr[%d] is: %d\\n\", i, arr[i]);\n" +
                "    }\n" +
                "}\n" +
                "/*\n" +
                "int my_arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };\n" +
                "int size = 10;\n" +
                "printIntArray(my_arr, size);\n" +
                "// will print \"arr[0] is: 1\" etc\n" +
                "*/\n" +
                "\n" +
                "// if referring to external variables outside function, you should use the extern keyword.\n" +
                "int i = 0;\n" +
                "void testFunc() {\n" +
                "  extern int i; //i here is now using external variable i\n" +
                "}\n" +
                "\n" +
                "// make external variables private to source file with static:\n" +
                "static int j = 0; //other files using testFunc2() cannot access variable j\n" +
                "void testFunc2() {\n" +
                "  extern int j;\n" +
                "}\n" +
                "// The static keyword makes a variable inaccessible to code outside the\n" +
                "// compilation unit. (On almost all systems, a \"compilation unit\" is a .c\n" +
                "// file.) static can apply both to global (to the compilation unit) variables,\n" +
                "// functions, and function-local variables. When using static with\n" +
                "// function-local variables, the variable is effectively global and retains its\n" +
                "// value across function calls, but is only accessible within the function it\n" +
                "// is declared in. Additionally, static variables are initialized to 0 if not\n" +
                "// declared with some other starting value.\n" +
                "//**You may also declare functions as static to make them private**\n" +
                "\n" +
                "///////////////////////////////////////\n" +
                "// User-defined types and structs\n" +
                "///////////////////////////////////////\n" +
                "\n" +
                "// Typedefs can be used to create type aliases\n" +
                "typedef int my_type;\n" +
                "my_type my_type_var = 0;\n" +
                "\n" +
                "// Structs are just collections of data, the members are allocated sequentially,\n" +
                "// in the order they are written:\n" +
                "struct rectangle {\n" +
                "  int width;\n" +
                "  int height;\n" +
                "};\n" +
                "\n" +
                "// It's not generally true that\n" +
                "// sizeof(struct rectangle) == sizeof(int) + sizeof(int)\n" +
                "// due to potential padding between the structure members (this is for alignment\n" +
                "// reasons). [1]\n" +
                "\n" +
                "void function_1()\n" +
                "{\n" +
                "  struct rectangle my_rec = { 1, 2 }; // Fields can be initialized immediately\n" +
                "\n" +
                "  // Access struct members with .\n" +
                "  my_rec.width = 10;\n" +
                "  my_rec.height = 20;\n" +
                "\n" +
                "  // You can declare pointers to structs\n" +
                "  struct rectangle *my_rec_ptr = &my_rec;\n" +
                "\n" +
                "  // Use dereferencing to set struct pointer members...\n" +
                "  (*my_rec_ptr).width = 30;\n" +
                "\n" +
                "  // ... or even better: prefer the -> shorthand for the sake of readability\n" +
                "  my_rec_ptr->height = 10; // Same as (*my_rec_ptr).height = 10;\n" +
                "}\n" +
                "\n" +
                "// You can apply a typedef to a struct for convenience\n" +
                "typedef struct rectangle rect;\n" +
                "\n" +
                "int area(rect r)\n" +
                "{\n" +
                "  return r.width * r.height;\n" +
                "}\n" +
                "\n" +
                "// Typedefs can also be defined right during struct definition\n" +
                "typedef struct {\n" +
                "  int width;\n" +
                "  int height;\n" +
                "} rect;\n" +
                "// Like before, doing this means one can type\n" +
                "rect r;\n" +
                "// instead of having to type\n" +
                "struct rectangle r;\n" +
                "\n" +
                "// if you have large structs, you can pass them \"by pointer\" to avoid copying\n" +
                "// the whole struct:\n" +
                "int areaptr(const rect *r)\n" +
                "{\n" +
                "  return r->width * r->height;\n" +
                "}\n" +
                "\n" +
                "///////////////////////////////////////\n" +
                "// Function pointers\n" +
                "///////////////////////////////////////\n" +
                "/*\n" +
                "At run time, functions are located at known memory addresses. Function pointers are\n" +
                "much like any other pointer (they just store a memory address), but can be used\n" +
                "to invoke functions directly, and to pass handlers (or callback functions) around.\n" +
                "However, definition syntax may be initially confusing.\n" +
                "\n" +
                "Example: use str_reverse from a pointer\n" +
                "*/\n" +
                "void str_reverse_through_pointer(char *str_in) {\n" +
                "  // Define a function pointer variable, named f.\n" +
                "  void (*f)(char *); // Signature should exactly match the target function.\n" +
                "  f = &str_reverse; // Assign the address for the actual function (determined at run time)\n" +
                "  // f = str_reverse; would work as well - functions decay into pointers, similar to arrays\n" +
                "  (*f)(str_in); // Just calling the function through the pointer\n" +
                "  // f(str_in); // That's an alternative but equally valid syntax for calling it.\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                "As long as function signatures match, you can assign any function to the same pointer.\n" +
                "Function pointers are usually typedef'd for simplicity and readability, as follows:\n" +
                "*/\n" +
                "\n" +
                "typedef void (*my_fnp_type)(char *);\n" +
                "\n" +
                "// Then used when declaring the actual pointer variable:\n" +
                "// ...\n" +
                "// my_fnp_type f;\n" +
                "\n" +
                "\n" +
                "/////////////////////////////\n" +
                "// Printing characters with printf()\n" +
                "/////////////////////////////\n" +
                "\n" +
                "//Special characters:\n" +
                "/*\n" +
                "'\\a'; // alert (bell) character\n" +
                "'\\n'; // newline character\n" +
                "'\\t'; // tab character (left justifies text)\n" +
                "'\\v'; // vertical tab\n" +
                "'\\f'; // new page (form feed)\n" +
                "'\\r'; // carriage return\n" +
                "'\\b'; // backspace character\n" +
                "'\\0'; // NULL character. Usually put at end of strings in C.\n" +
                "//   hello\\n\\0. \\0 used by convention to mark end of string.\n" +
                "'\\\\'; // backslash\n" +
                "'\\?'; // question mark\n" +
                "'\\''; // single quote\n" +
                "'\\\"'; // double quote\n" +
                "'\\xhh'; // hexadecimal number. Example: '\\xb' = vertical tab character\n" +
                "'\\0oo'; // octal number. Example: '\\013' = vertical tab character\n" +
                "\n" +
                "//print formatting:\n" +
                "\"%d\";    // integer\n" +
                "\"%3d\";   // integer with minimum of length 3 digits (right justifies text)\n" +
                "\"%s\";    // string\n" +
                "\"%f\";    // float\n" +
                "\"%ld\";   // long\n" +
                "\"%3.2f\"; // minimum 3 digits left and 2 digits right decimal float\n" +
                "\"%7.4s\"; // (can do with strings too)\n" +
                "\"%c\";    // char\n" +
                "\"%p\";    // pointer. NOTE: need to (void *)-cast the pointer, before passing\n" +
                "         //                it as an argument to `printf`.\n" +
                "\"%x\";    // hexadecimal\n" +
                "\"%o\";    // octal\n" +
                "\"%%\";    // prints %\n" +
                "*/\n" +
                "\n" +
                "///////////////////////////////////////\n" +
                "// Order of Evaluation\n" +
                "///////////////////////////////////////\n" +
                "\n" +
                "// From top to bottom, top has higher precedence\n" +
                "//---------------------------------------------------//\n" +
                "//        Operators                  | Associativity //\n" +
                "//---------------------------------------------------//\n" +
                "// () [] -> .                        | left to right //\n" +
                "// ! ~ ++ -- + = *(type) sizeof      | right to left //\n" +
                "// * / %                             | left to right //\n" +
                "// + -                               | left to right //\n" +
                "// << >>                             | left to right //\n" +
                "// < <= > >=                         | left to right //\n" +
                "// == !=                             | left to right //\n" +
                "// &                                 | left to right //\n" +
                "// ^                                 | left to right //\n" +
                "// |                                 | left to right //\n" +
                "// &&                                | left to right //\n" +
                "// ||                                | left to right //\n" +
                "// ?:                                | right to left //\n" +
                "// = += -= *= /= %= &= ^= |= <<= >>= | right to left //\n" +
                "// ,                                 | left to right //\n" +
                "//---------------------------------------------------//\n" +
                "\n" +
                "/******************************* Header Files **********************************\n" +
                "\n" +
                "Header files are an important part of C as they allow for the connection of C\n" +
                "source files and can simplify code and definitions by separating them into\n" +
                "separate files.\n" +
                "\n" +
                "Header files are syntactically similar to C source files but reside in \".h\"\n" +
                "files. They can be included in your C source file by using the precompiler\n" +
                "command #include \"example.h\", given that example.h exists in the same directory\n" +
                "as the C file.\n" +
                "*/\n" +
                "\n" +
                "/* A safe guard to prevent the header from being defined too many times. This */\n" +
                "/* happens in the case of circle dependency, the contents of the header is    */\n" +
                "/* already defined.                                                           */\n" +
                "#ifndef EXAMPLE_H /* if EXAMPLE_H is not yet defined. */\n" +
                "#define EXAMPLE_H /* Define the macro EXAMPLE_H. */\n" +
                "\n" +
                "/* Other headers can be included in headers and therefore transitively */\n" +
                "/* included into files that include this header.                       */\n" +
                "#include <string.h>\n" +
                "\n" +
                "/* Like for c source files, macros can be defined in headers */\n" +
                "/* and used in files that include this header file.          */\n" +
                "#define EXAMPLE_NAME \"Dennis Ritchie\"\n" +
                "\n" +
                "/* Function macros can also be defined.  */\n" +
                "#define ADD(a, b) ((a) + (b))\n" +
                "\n" +
                "/* Notice the parenthesis surrounding the arguments -- this is important to   */\n" +
                "/* ensure that a and b don't get expanded in an unexpected way (e.g. consider */\n" +
                "/* MUL(x, y) (x * y); MUL(1 + 2, 3) would expand to (1 + 2 * 3), yielding an  */\n" +
                "/* incorrect result)                                                          */\n" +
                "\n" +
                "/* Structs and typedefs can be used for consistency between files. */\n" +
                "typedef struct Node\n" +
                "{\n" +
                "    int val;\n" +
                "    struct Node *next;\n" +
                "} Node;\n" +
                "\n" +
                "/* So can enumerations. */\n" +
                "enum traffic_light_state {GREEN, YELLOW, RED};\n" +
                "\n" +
                "/* Function prototypes can also be defined here for use in multiple files,  */\n" +
                "/* but it is bad practice to define the function in the header. Definitions */\n" +
                "/* should instead be put in a C file.                                       */\n" +
                "Node createLinkedList(int *vals, int len);\n" +
                "\n" +
                "/* Beyond the above elements, other definitions should be left to a C source */\n" +
                "/* file. Excessive includes or definitions should also not be contained in   */\n" +
                "/* a header file but instead put into separate headers or a C file.          */\n" +
                "\n" +
                "#endif /* End of the if precompiler directive. */";
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        binding.codeView.setOnHighlightListener(this)
                .setOnHighlightListener(this)
                .setTheme(Theme.MONOKAI_SUBLIME)
                .setCode(code)
                .setLanguage(Language.CPP)
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
