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
import com.segmnf.myapplication.databinding.ActivityLearnJavascriptBinding;

import br.tiagohm.codeview.CodeView;
import br.tiagohm.codeview.Language;
import br.tiagohm.codeview.Theme;

public class LearnJavascriptActivity extends AppCompatActivity implements CodeView.OnHighlightListener {
    ActivityLearnJavascriptBinding binding;
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
        binding = ActivityLearnJavascriptBinding.inflate(getLayoutInflater());
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
        String code= "// Single-line comments start with two slashes.\n" +
                "/* Multiline comments start with slash-star,\n" +
                "   and end with star-slash */\n" +
                "\n" +
                "// Statements can be terminated by ;\n" +
                "doStuff();\n" +
                "\n" +
                "// ... but they don't have to be, as semicolons are automatically inserted\n" +
                "// wherever there's a newline, except in certain cases.\n" +
                "doStuff()\n" +
                "\n" +
                "// Because those cases can cause unexpected results, we'll keep on using\n" +
                "// semicolons in this guide.\n" +
                "\n" +
                "///////////////////////////////////\n" +
                "// 1. Numbers, Strings and Operators\n" +
                "\n" +
                "// JavaScript has one number type (which is a 64-bit IEEE 754 double).\n" +
                "// Doubles have a 52-bit mantissa, which is enough to store integers\n" +
                "// up to about 9✕10¹⁵ precisely.\n" +
                "3; // = 3\n" +
                "1.5; // = 1.5\n" +
                "\n" +
                "// Some basic arithmetic works as you'd expect.\n" +
                "1 + 1; // = 2\n" +
                "0.1 + 0.2; // = 0.30000000000000004\n" +
                "8 - 1; // = 7\n" +
                "10 * 2; // = 20\n" +
                "35 / 5; // = 7\n" +
                "\n" +
                "// Including uneven division.\n" +
                "5 / 2; // = 2.5\n" +
                "\n" +
                "// And modulo division.\n" +
                "10 % 2; // = 0\n" +
                "30 % 4; // = 2\n" +
                "18.5 % 7; // = 4.5\n" +
                "\n" +
                "// Bitwise operations also work; when you perform a bitwise operation your float\n" +
                "// is converted to a signed int *up to* 32 bits.\n" +
                "1 << 2; // = 4\n" +
                "\n" +
                "// Precedence is enforced with parentheses.\n" +
                "(1 + 3) * 2; // = 8\n" +
                "\n" +
                "// There are three special not-a-real-number values:\n" +
                "Infinity; // result of e.g. 1/0\n" +
                "-Infinity; // result of e.g. -1/0\n" +
                "NaN; // result of e.g. 0/0, stands for 'Not a Number'\n" +
                "\n" +
                "// There's also a boolean type.\n" +
                "true;\n" +
                "false;\n" +
                "\n" +
                "// Strings are created with ' or \".\n" +
                "'abc';\n" +
                "\"Hello, world\";\n" +
                "\n" +
                "// Negation uses the ! symbol\n" +
                "!true; // = false\n" +
                "!false; // = true\n" +
                "\n" +
                "// Equality is ===\n" +
                "1 === 1; // = true\n" +
                "2 === 1; // = false\n" +
                "\n" +
                "// Inequality is !==\n" +
                "1 !== 1; // = false\n" +
                "2 !== 1; // = true\n" +
                "\n" +
                "// More comparisons\n" +
                "1 < 10; // = true\n" +
                "1 > 10; // = false\n" +
                "2 <= 2; // = true\n" +
                "2 >= 2; // = true\n" +
                "\n" +
                "// Strings are concatenated with +\n" +
                "\"Hello \" + \"world!\"; // = \"Hello world!\"\n" +
                "\n" +
                "// ... which works with more than just strings\n" +
                "\"1, 2, \" + 3; // = \"1, 2, 3\"\n" +
                "\"Hello \" + [\"world\", \"!\"]; // = \"Hello world,!\"\n" +
                "\n" +
                "// and are compared with < and >\n" +
                "\"a\" < \"b\"; // = true\n" +
                "\n" +
                "// Type coercion is performed for comparisons with double equals...\n" +
                "\"5\" == 5; // = true\n" +
                "null == undefined; // = true\n" +
                "\n" +
                "// ...unless you use ===\n" +
                "\"5\" === 5; // = false\n" +
                "null === undefined; // = false\n" +
                "\n" +
                "// ...which can result in some weird behaviour...\n" +
                "13 + !0; // 14\n" +
                "\"13\" + !0; // '13true'\n" +
                "\n" +
                "// You can access characters in a string with `charAt`\n" +
                "\"This is a string\".charAt(0);  // = 'T'\n" +
                "\n" +
                "// ...or use `substring` to get larger pieces.\n" +
                "\"Hello world\".substring(0, 5); // = \"Hello\"\n" +
                "\n" +
                "// `length` is a property, so don't use ().\n" +
                "\"Hello\".length; // = 5\n" +
                "\n" +
                "// There's also `null` and `undefined`.\n" +
                "null;      // used to indicate a deliberate non-value\n" +
                "undefined; // used to indicate a value is not currently present (although\n" +
                "           // `undefined` is actually a value itself)\n" +
                "\n" +
                "// false, null, undefined, NaN, 0 and \"\" are falsy; everything else is truthy.\n" +
                "// Note that 0 is falsy and \"0\" is truthy, even though 0 == \"0\".\n" +
                "\n" +
                "///////////////////////////////////\n" +
                "// 2. Variables, Arrays and Objects\n" +
                "\n" +
                "// Variables are declared with the `var` keyword. JavaScript is dynamically\n" +
                "// typed, so you don't need to specify type. Assignment uses a single `=`\n" +
                "// character.\n" +
                "var someVar = 5;\n" +
                "\n" +
                "// If you leave the var keyword off, you won't get an error...\n" +
                "someOtherVar = 10;\n" +
                "\n" +
                "// ...but your variable will be created in the global scope, not in the scope\n" +
                "// you defined it in.\n" +
                "\n" +
                "// Variables declared without being assigned to are set to undefined.\n" +
                "var someThirdVar; // = undefined\n" +
                "\n" +
                "// If you want to declare a couple of variables, then you could use a comma\n" +
                "// separator\n" +
                "var someFourthVar = 2, someFifthVar = 4;\n" +
                "\n" +
                "// There's shorthand for performing math operations on variables:\n" +
                "someVar += 5; // equivalent to someVar = someVar + 5; someVar is 10 now\n" +
                "someVar *= 10; // now someVar is 100\n" +
                "\n" +
                "// and an even-shorter-hand for adding or subtracting 1\n" +
                "someVar++; // now someVar is 101\n" +
                "someVar--; // back to 100\n" +
                "\n" +
                "// Arrays are ordered lists of values, of any type.\n" +
                "var myArray = [\"Hello\", 45, true];\n" +
                "\n" +
                "// Their members can be accessed using the square-brackets subscript syntax.\n" +
                "// Array indices start at zero.\n" +
                "myArray[1]; // = 45\n" +
                "\n" +
                "// Arrays are mutable and of variable length.\n" +
                "myArray.push(\"World\");\n" +
                "myArray.length; // = 4\n" +
                "\n" +
                "// Add/Modify at specific index\n" +
                "myArray[3] = \"Hello\";\n" +
                "\n" +
                "// Add and remove element from front or back end of an array\n" +
                "myArray.unshift(3); // Add as the first element\n" +
                "someVar = myArray.shift(); // Remove first element and return it\n" +
                "myArray.push(3); // Add as the last element\n" +
                "someVar = myArray.pop(); // Remove last element and return it\n" +
                "\n" +
                "// Join all elements of an array with semicolon\n" +
                "var myArray0 = [32,false,\"js\",12,56,90];\n" +
                "myArray0.join(\";\"); // = \"32;false;js;12;56;90\"\n" +
                "\n" +
                "// Get subarray of elements from index 1 (include) to 4 (exclude)\n" +
                "myArray0.slice(1,4); // = [false,\"js\",12]\n" +
                "\n" +
                "// Remove 4 elements starting from index 2, and insert there strings\n" +
                "// \"hi\",\"wr\" and \"ld\"; return removed subarray\n" +
                "myArray0.splice(2,4,\"hi\",\"wr\",\"ld\"); // = [\"js\",12,56,90]\n" +
                "// myArray0 === [32,false,\"hi\",\"wr\",\"ld\"]\n" +
                "\n" +
                "// JavaScript's objects are equivalent to \"dictionaries\" or \"maps\" in other\n" +
                "// languages: an unordered collection of key-value pairs.\n" +
                "var myObj = {key1: \"Hello\", key2: \"World\"};\n" +
                "\n" +
                "// Keys are strings, but quotes aren't required if they're a valid\n" +
                "// JavaScript identifier. Values can be any type.\n" +
                "var myObj = {myKey: \"myValue\", \"my other key\": 4};\n" +
                "\n" +
                "// Object attributes can also be accessed using the subscript syntax,\n" +
                "myObj[\"my other key\"]; // = 4\n" +
                "\n" +
                "// ... or using the dot syntax, provided the key is a valid identifier.\n" +
                "myObj.myKey; // = \"myValue\"\n" +
                "\n" +
                "// Objects are mutable; values can be changed and new keys added.\n" +
                "myObj.myThirdKey = true;\n" +
                "\n" +
                "// If you try to access a value that's not yet set, you'll get undefined.\n" +
                "myObj.myFourthKey; // = undefined\n" +
                "\n" +
                "///////////////////////////////////\n" +
                "// 3. Logic and Control Structures\n" +
                "\n" +
                "// The `if` structure works as you'd expect.\n" +
                "var count = 1;\n" +
                "if (count == 3){\n" +
                "    // evaluated if count is 3\n" +
                "} else if (count == 4){\n" +
                "    // evaluated if count is 4\n" +
                "} else {\n" +
                "    // evaluated if it's not either 3 or 4\n" +
                "}\n" +
                "\n" +
                "// As does `while`.\n" +
                "while (true){\n" +
                "    // An infinite loop!\n" +
                "}\n" +
                "\n" +
                "// Do-while loops are like while loops, except they always run at least once.\n" +
                "var input;\n" +
                "do {\n" +
                "    input = getInput();\n" +
                "} while (!isValid(input));\n" +
                "\n" +
                "// The `for` loop is the same as C and Java:\n" +
                "// initialization; continue condition; iteration.\n" +
                "for (var i = 0; i < 5; i++){\n" +
                "    // will run 5 times\n" +
                "}\n" +
                "\n" +
                "// Breaking out of labeled loops is similar to Java\n" +
                "outer:\n" +
                "for (var i = 0; i < 10; i++) {\n" +
                "    for (var j = 0; j < 10; j++) {\n" +
                "        if (i == 5 && j ==5) {\n" +
                "            break outer;\n" +
                "            // breaks out of outer loop instead of only the inner one\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "// The for/in statement allows iteration over properties of an object.\n" +
                "var description = \"\";\n" +
                "var person = {fname:\"Paul\", lname:\"Ken\", age:18};\n" +
                "for (var x in person){\n" +
                "    description += person[x] + \" \";\n" +
                "} // description = 'Paul Ken 18 '\n" +
                "\n" +
                "// The for/of statement allows iteration over iterable objects (including the built-in String, \n" +
                "// Array, e.g. the Array-like arguments or NodeList objects, TypedArray, Map and Set, \n" +
                "// and user-defined iterables).\n" +
                "var myPets = \"\";\n" +
                "var pets = [\"cat\", \"dog\", \"hamster\", \"hedgehog\"];\n" +
                "for (var pet of pets){\n" +
                "    myPets += pet + \" \";\n" +
                "} // myPets = 'cat dog hamster hedgehog '\n" +
                "\n" +
                "// && is logical and, || is logical or\n" +
                "if (house.size == \"big\" && house.colour == \"blue\"){\n" +
                "    house.contains = \"bear\";\n" +
                "}\n" +
                "if (colour == \"red\" || colour == \"blue\"){\n" +
                "    // colour is either red or blue\n" +
                "}\n" +
                "\n" +
                "// && and || \"short circuit\", which is useful for setting default values.\n" +
                "var name = otherName || \"default\";\n" +
                "\n" +
                "// The `switch` statement checks for equality with `===`.\n" +
                "// Use 'break' after each case\n" +
                "// or the cases after the correct one will be executed too.\n" +
                "grade = 'B';\n" +
                "switch (grade) {\n" +
                "  case 'A':\n" +
                "    console.log(\"Great job\");\n" +
                "    break;\n" +
                "  case 'B':\n" +
                "    console.log(\"OK job\");\n" +
                "    break;\n" +
                "  case 'C':\n" +
                "    console.log(\"You can do better\");\n" +
                "    break;\n" +
                "  default:\n" +
                "    console.log(\"Oy vey\");\n" +
                "    break;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "///////////////////////////////////\n" +
                "// 4. Functions, Scope and Closures\n" +
                "\n" +
                "// JavaScript functions are declared with the `function` keyword.\n" +
                "function myFunction(thing){\n" +
                "    return thing.toUpperCase();\n" +
                "}\n" +
                "myFunction(\"foo\"); // = \"FOO\"\n" +
                "\n" +
                "// Note that the value to be returned must start on the same line as the\n" +
                "// `return` keyword, otherwise you'll always return `undefined` due to\n" +
                "// automatic semicolon insertion. Watch out for this when using Allman style.\n" +
                "function myFunction(){\n" +
                "    return // <- semicolon automatically inserted here\n" +
                "    {thisIsAn: 'object literal'};\n" +
                "}\n" +
                "myFunction(); // = undefined\n" +
                "\n" +
                "// JavaScript functions are first class objects, so they can be reassigned to\n" +
                "// different variable names and passed to other functions as arguments - for\n" +
                "// example, when supplying an event handler:\n" +
                "function myFunction(){\n" +
                "    // this code will be called in 5 seconds' time\n" +
                "}\n" +
                "setTimeout(myFunction, 5000);\n" +
                "// Note: setTimeout isn't part of the JS language, but is provided by browsers\n" +
                "// and Node.js.\n" +
                "\n" +
                "// Another function provided by browsers is setInterval\n" +
                "function myFunction(){\n" +
                "    // this code will be called every 5 seconds\n" +
                "}\n" +
                "setInterval(myFunction, 5000);\n" +
                "\n" +
                "// Function objects don't even have to be declared with a name - you can write\n" +
                "// an anonymous function definition directly into the arguments of another.\n" +
                "setTimeout(function(){\n" +
                "    // this code will be called in 5 seconds' time\n" +
                "}, 5000);\n" +
                "\n" +
                "// JavaScript has function scope; functions get their own scope but other blocks\n" +
                "// do not.\n" +
                "if (true){\n" +
                "    var i = 5;\n" +
                "}\n" +
                "i; // = 5 - not undefined as you'd expect in a block-scoped language\n" +
                "\n" +
                "// This has led to a common pattern of \"immediately-executing anonymous\n" +
                "// functions\", which prevent temporary variables from leaking into the global\n" +
                "// scope.\n" +
                "(function(){\n" +
                "    var temporary = 5;\n" +
                "    // We can access the global scope by assigning to the \"global object\", which\n" +
                "    // in a web browser is always `window`. The global object may have a\n" +
                "    // different name in non-browser environments such as Node.js.\n" +
                "    window.permanent = 10;\n" +
                "})();\n" +
                "temporary; // raises ReferenceError\n" +
                "permanent; // = 10\n" +
                "\n" +
                "// One of JavaScript's most powerful features is closures. If a function is\n" +
                "// defined inside another function, the inner function has access to all the\n" +
                "// outer function's variables, even after the outer function exits.\n" +
                "function sayHelloInFiveSeconds(name){\n" +
                "    var prompt = \"Hello, \" + name + \"!\";\n" +
                "    // Inner functions are put in the local scope by default, as if they were\n" +
                "    // declared with `var`.\n" +
                "    function inner(){\n" +
                "        alert(prompt);\n" +
                "    }\n" +
                "    setTimeout(inner, 5000);\n" +
                "    // setTimeout is asynchronous, so the sayHelloInFiveSeconds function will\n" +
                "    // exit immediately, and setTimeout will call inner afterwards. However,\n" +
                "    // because inner is \"closed over\" sayHelloInFiveSeconds, inner still has\n" +
                "    // access to the `prompt` variable when it is finally called.\n" +
                "}\n" +
                "sayHelloInFiveSeconds(\"Adam\"); // will open a popup with \"Hello, Adam!\" in 5s\n" +
                "\n" +
                "///////////////////////////////////\n" +
                "// 5. More about Objects; Constructors and Prototypes\n" +
                "\n" +
                "// Objects can contain functions.\n" +
                "var myObj = {\n" +
                "    myFunc: function(){\n" +
                "        return \"Hello world!\";\n" +
                "    }\n" +
                "};\n" +
                "myObj.myFunc(); // = \"Hello world!\"\n" +
                "\n" +
                "// When functions attached to an object are called, they can access the object\n" +
                "// they're attached to using the `this` keyword.\n" +
                "myObj = {\n" +
                "    myString: \"Hello world!\",\n" +
                "    myFunc: function(){\n" +
                "        return this.myString;\n" +
                "    }\n" +
                "};\n" +
                "myObj.myFunc(); // = \"Hello world!\"\n" +
                "\n" +
                "// What this is set to has to do with how the function is called, not where\n" +
                "// it's defined. So, our function doesn't work if it isn't called in the\n" +
                "// context of the object.\n" +
                "var myFunc = myObj.myFunc;\n" +
                "myFunc(); // = undefined\n" +
                "\n" +
                "// Inversely, a function can be assigned to the object and gain access to it\n" +
                "// through `this`, even if it wasn't attached when it was defined.\n" +
                "var myOtherFunc = function(){\n" +
                "    return this.myString.toUpperCase();\n" +
                "};\n" +
                "myObj.myOtherFunc = myOtherFunc;\n" +
                "myObj.myOtherFunc(); // = \"HELLO WORLD!\"\n" +
                "\n" +
                "// We can also specify a context for a function to execute in when we invoke it\n" +
                "// using `call` or `apply`.\n" +
                "\n" +
                "var anotherFunc = function(s){\n" +
                "    return this.myString + s;\n" +
                "};\n" +
                "anotherFunc.call(myObj, \" And Hello Moon!\"); // = \"Hello World! And Hello Moon!\"\n" +
                "\n" +
                "// The `apply` function is nearly identical, but takes an array for an argument\n" +
                "// list.\n" +
                "\n" +
                "anotherFunc.apply(myObj, [\" And Hello Sun!\"]); // = \"Hello World! And Hello Sun!\"\n" +
                "\n" +
                "// This is useful when working with a function that accepts a sequence of\n" +
                "// arguments and you want to pass an array.\n" +
                "\n" +
                "Math.min(42, 6, 27); // = 6\n" +
                "Math.min([42, 6, 27]); // = NaN (uh-oh!)\n" +
                "Math.min.apply(Math, [42, 6, 27]); // = 6\n" +
                "\n" +
                "// But, `call` and `apply` are only temporary. When we want it to stick, we can\n" +
                "// use `bind`.\n" +
                "\n" +
                "var boundFunc = anotherFunc.bind(myObj);\n" +
                "boundFunc(\" And Hello Saturn!\"); // = \"Hello World! And Hello Saturn!\"\n" +
                "\n" +
                "// `bind` can also be used to partially apply (curry) a function.\n" +
                "\n" +
                "var product = function(a, b){ return a * b; };\n" +
                "var doubler = product.bind(this, 2);\n" +
                "doubler(8); // = 16\n" +
                "\n" +
                "// When you call a function with the `new` keyword, a new object is created, and\n" +
                "// made available to the function via the `this` keyword. Functions designed to be\n" +
                "// called like that are called constructors.\n" +
                "\n" +
                "var MyConstructor = function(){\n" +
                "    this.myNumber = 5;\n" +
                "};\n" +
                "myNewObj = new MyConstructor(); // = {myNumber: 5}\n" +
                "myNewObj.myNumber; // = 5\n" +
                "\n" +
                "// Unlike most other popular object-oriented languages, JavaScript has no\n" +
                "// concept of 'instances' created from 'class' blueprints; instead, JavaScript\n" +
                "// combines instantiation and inheritance into a single concept: a 'prototype'.\n" +
                "\n" +
                "// Every JavaScript object has a 'prototype'. When you go to access a property\n" +
                "// on an object that doesn't exist on the actual object, the interpreter will\n" +
                "// look at its prototype.\n" +
                "\n" +
                "// Some JS implementations let you access an object's prototype on the magic\n" +
                "// property `__proto__`. While this is useful for explaining prototypes it's not\n" +
                "// part of the standard; we'll get to standard ways of using prototypes later.\n" +
                "var myObj = {\n" +
                "    myString: \"Hello world!\"\n" +
                "};\n" +
                "var myPrototype = {\n" +
                "    meaningOfLife: 42,\n" +
                "    myFunc: function(){\n" +
                "        return this.myString.toLowerCase();\n" +
                "    }\n" +
                "};\n" +
                "\n" +
                "myObj.__proto__ = myPrototype;\n" +
                "myObj.meaningOfLife; // = 42\n" +
                "\n" +
                "// This works for functions, too.\n" +
                "myObj.myFunc(); // = \"hello world!\"\n" +
                "\n" +
                "// Of course, if your property isn't on your prototype, the prototype's\n" +
                "// prototype is searched, and so on.\n" +
                "myPrototype.__proto__ = {\n" +
                "    myBoolean: true\n" +
                "};\n" +
                "myObj.myBoolean; // = true\n" +
                "\n" +
                "// There's no copying involved here; each object stores a reference to its\n" +
                "// prototype. This means we can alter the prototype and our changes will be\n" +
                "// reflected everywhere.\n" +
                "myPrototype.meaningOfLife = 43;\n" +
                "myObj.meaningOfLife; // = 43\n" +
                "\n" +
                "// The for/in statement allows iteration over properties of an object,\n" +
                "// walking up the prototype chain until it sees a null prototype.\n" +
                "for (var x in myObj){\n" +
                "    console.log(myObj[x]);\n" +
                "}\n" +
                "///prints:\n" +
                "// Hello world!\n" +
                "// 43\n" +
                "// [Function: myFunc]\n" +
                "// true\n" +
                "\n" +
                "// To only consider properties attached to the object itself\n" +
                "// and not its prototypes, use the `hasOwnProperty()` check.\n" +
                "for (var x in myObj){\n" +
                "    if (myObj.hasOwnProperty(x)){\n" +
                "        console.log(myObj[x]);\n" +
                "    }\n" +
                "}\n" +
                "///prints:\n" +
                "// Hello world!\n" +
                "\n" +
                "// We mentioned that `__proto__` was non-standard, and there's no standard way to\n" +
                "// change the prototype of an existing object. However, there are two ways to\n" +
                "// create a new object with a given prototype.\n" +
                "\n" +
                "// The first is Object.create, which is a recent addition to JS, and therefore\n" +
                "// not available in all implementations yet.\n" +
                "var myObj = Object.create(myPrototype);\n" +
                "myObj.meaningOfLife; // = 43\n" +
                "\n" +
                "// The second way, which works anywhere, has to do with constructors.\n" +
                "// Constructors have a property called prototype. This is *not* the prototype of\n" +
                "// the constructor function itself; instead, it's the prototype that new objects\n" +
                "// are given when they're created with that constructor and the new keyword.\n" +
                "MyConstructor.prototype = {\n" +
                "    myNumber: 5,\n" +
                "    getMyNumber: function(){\n" +
                "        return this.myNumber;\n" +
                "    }\n" +
                "};\n" +
                "var myNewObj2 = new MyConstructor();\n" +
                "myNewObj2.getMyNumber(); // = 5\n" +
                "myNewObj2.myNumber = 6;\n" +
                "myNewObj2.getMyNumber(); // = 6\n" +
                "\n" +
                "// Built-in types like strings and numbers also have constructors that create\n" +
                "// equivalent wrapper objects.\n" +
                "var myNumber = 12;\n" +
                "var myNumberObj = new Number(12);\n" +
                "myNumber == myNumberObj; // = true\n" +
                "\n" +
                "// Except, they aren't exactly equivalent.\n" +
                "typeof myNumber; // = 'number'\n" +
                "typeof myNumberObj; // = 'object'\n" +
                "myNumber === myNumberObj; // = false\n" +
                "if (0){\n" +
                "    // This code won't execute, because 0 is falsy.\n" +
                "}\n" +
                "if (new Number(0)){\n" +
                "   // This code will execute, because wrapped numbers are objects, and objects\n" +
                "   // are always truthy.\n" +
                "}\n" +
                "\n" +
                "// However, the wrapper objects and the regular builtins share a prototype, so\n" +
                "// you can actually add functionality to a string, for instance.\n" +
                "String.prototype.firstCharacter = function(){\n" +
                "    return this.charAt(0);\n" +
                "};\n" +
                "\"abc\".firstCharacter(); // = \"a\"\n" +
                "\n" +
                "// This fact is often used in \"polyfilling\", which is implementing newer\n" +
                "// features of JavaScript in an older subset of JavaScript, so that they can be\n" +
                "// used in older environments such as outdated browsers.\n" +
                "\n" +
                "// For instance, we mentioned that Object.create isn't yet available in all\n" +
                "// implementations, but we can still use it with this polyfill:\n" +
                "if (Object.create === undefined){ // don't overwrite it if it exists\n" +
                "    Object.create = function(proto){\n" +
                "        // make a temporary constructor with the right prototype\n" +
                "        var Constructor = function(){};\n" +
                "        Constructor.prototype = proto;\n" +
                "        // then use it to create a new, appropriately-prototyped object\n" +
                "        return new Constructor();\n" +
                "    };\n" +
                "}\n" +
                "\n" +
                "// ES6 Additions\n" +
                "\n" +
                "// The \"let\" keyword allows you to define variables in a lexical scope, \n" +
                "// as opposed to a function scope like the var keyword does.\n" +
                "let name = \"Billy\";\n" +
                "\n" +
                "// Variables defined with let can be reassigned new values.\n" +
                "name = \"William\";\n" +
                "\n" +
                "// The \"const\" keyword allows you to define a variable in a lexical scope\n" +
                "// like with let, but you cannot reassign the value once one has been assigned.\n" +
                "\n" +
                "const pi = 3.14;\n" +
                "\n" +
                "pi = 4.13; // You cannot do this.\n" +
                "\n" +
                "// There is a new syntax for functions in ES6 known as \"lambda syntax\".\n" +
                "// This allows functions to be defined in a lexical scope like with variables\n" +
                "// defined by const and let. \n" +
                "\n" +
                "const isEven = (number) => {\n" +
                "    return number % 2 === 0;\n" +
                "};\n" +
                "\n" +
                "isEven(7); // false\n" +
                "\n" +
                "// The \"equivalent\" of this function in the traditional syntax would look like this:\n" +
                "\n" +
                "function isEven(number) {\n" +
                "    return number % 2 === 0;\n" +
                "};\n" +
                "\n" +
                "// I put the word \"equivalent\" in double quotes because a function defined\n" +
                "// using the lambda syntax cannnot be called before the definition.\n" +
                "// The following is an example of invalid usage:\n" +
                "\n" +
                "add(1, 8);\n" +
                "\n" +
                "const add = (firstNumber, secondNumber) => {\n" +
                "    return firstNumber + secondNumber;\n" +
                "};";
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        binding.codeView.setOnHighlightListener(this)
                .setOnHighlightListener(this)
                .setTheme(Theme.MONOKAI_SUBLIME)
                .setCode(code)
                .setLanguage(Language.JAVASCRIPT)
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
