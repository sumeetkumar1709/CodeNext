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
import com.segmnf.myapplication.databinding.ActivityLearnPyBinding;

import br.tiagohm.codeview.CodeView;
import br.tiagohm.codeview.Language;
import br.tiagohm.codeview.Theme;

public class LearnPyActivity extends AppCompatActivity implements CodeView.OnHighlightListener {
    ActivityLearnPyBinding binding;
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
        binding = ActivityLearnPyBinding.inflate(getLayoutInflater());
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
        String code= "# Single line comments start with a number symbol.\n" +
                "\n" +
                "\"\"\" Multiline strings can be written\n" +
                "    using three \"s, and are often used\n" +
                "    as documentation.\n" +
                "\"\"\"\n" +
                "\n" +
                "####################################################\n" +
                "## 1. Primitive Datatypes and Operators\n" +
                "####################################################\n" +
                "\n" +
                "# You have numbers\n" +
                "3  # => 3\n" +
                "\n" +
                "# Math is what you would expect\n" +
                "1 + 1   # => 2\n" +
                "8 - 1   # => 7\n" +
                "10 * 2  # => 20\n" +
                "35 / 5  # => 7.0\n" +
                "\n" +
                "# Integer division rounds down for both positive and negative numbers.\n" +
                "5 // 3       # => 1\n" +
                "-5 // 3      # => -2\n" +
                "5.0 // 3.0   # => 1.0 # works on floats too\n" +
                "-5.0 // 3.0  # => -2.0\n" +
                "\n" +
                "# The result of division is always a float\n" +
                "10.0 / 3  # => 3.3333333333333335\n" +
                "\n" +
                "# Modulo operation\n" +
                "7 % 3   # => 1\n" +
                "# i % j have the same sign as j, unlike C\n" +
                "-7 % 3  # => 2\n" +
                "\n" +
                "# Exponentiation (x**y, x to the yth power)\n" +
                "2**3  # => 8\n" +
                "\n" +
                "# Enforce precedence with parentheses\n" +
                "1 + 3 * 2    # => 7\n" +
                "(1 + 3) * 2  # => 8\n" +
                "\n" +
                "# Boolean values are primitives (Note: the capitalization)\n" +
                "True   # => True\n" +
                "False  # => False\n" +
                "\n" +
                "# negate with not\n" +
                "not True   # => False\n" +
                "not False  # => True\n" +
                "\n" +
                "# Boolean Operators\n" +
                "# Note \"and\" and \"or\" are case-sensitive\n" +
                "True and False  # => False\n" +
                "False or True   # => True\n" +
                "\n" +
                "# True and False are actually 1 and 0 but with different keywords\n" +
                "True + True # => 2\n" +
                "True * 8    # => 8\n" +
                "False - 5   # => -5\n" +
                "\n" +
                "# Comparison operators look at the numerical value of True and False\n" +
                "0 == False  # => True\n" +
                "1 == True   # => True\n" +
                "2 == True   # => False\n" +
                "-5 != False # => True\n" +
                "\n" +
                "# Using boolean logical operators on ints casts them to booleans for evaluation, but their non-cast value is returned\n" +
                "# Don't mix up with bool(ints) and bitwise and/or (&,|)\n" +
                "bool(0)     # => False\n" +
                "bool(4)     # => True\n" +
                "bool(-6)    # => True\n" +
                "0 and 2     # => 0\n" +
                "-5 or 0     # => -5\n" +
                "\n" +
                "# Equality is ==\n" +
                "1 == 1  # => True\n" +
                "2 == 1  # => False\n" +
                "\n" +
                "# Inequality is !=\n" +
                "1 != 1  # => False\n" +
                "2 != 1  # => True\n" +
                "\n" +
                "# More comparisons\n" +
                "1 < 10  # => True\n" +
                "1 > 10  # => False\n" +
                "2 <= 2  # => True\n" +
                "2 >= 2  # => True\n" +
                "\n" +
                "# Seeing whether a value is in a range\n" +
                "1 < 2 and 2 < 3  # => True\n" +
                "2 < 3 and 3 < 2  # => False\n" +
                "# Chaining makes this look nicer\n" +
                "1 < 2 < 3  # => True\n" +
                "2 < 3 < 2  # => False\n" +
                "\n" +
                "# (is vs. ==) is checks if two variables refer to the same object, but == checks\n" +
                "# if the objects pointed to have the same values.\n" +
                "a = [1, 2, 3, 4]  # Point a at a new list, [1, 2, 3, 4]\n" +
                "b = a             # Point b at what a is pointing to\n" +
                "b is a            # => True, a and b refer to the same object\n" +
                "b == a            # => True, a's and b's objects are equal\n" +
                "b = [1, 2, 3, 4]  # Point b at a new list, [1, 2, 3, 4]\n" +
                "b is a            # => False, a and b do not refer to the same object\n" +
                "b == a            # => True, a's and b's objects are equal\n" +
                "\n" +
                "# Strings are created with \" or '\n" +
                "\"This is a string.\"\n" +
                "'This is also a string.'\n" +
                "\n" +
                "# Strings can be added too\n" +
                "\"Hello \" + \"world!\"  # => \"Hello world!\"\n" +
                "# String literals (but not variables) can be concatenated without using '+'\n" +
                "\"Hello \" \"world!\"    # => \"Hello world!\"\n" +
                "\n" +
                "# A string can be treated like a list of characters\n" +
                "\"Hello world!\"[0]  # => 'H'\n" +
                "\n" +
                "# You can find the length of a string\n" +
                "len(\"This is a string\")  # => 16\n" +
                "\n" +
                "# You can also format using f-strings or formatted string literals (in Python 3.6+)\n" +
                "name = \"Reiko\"\n" +
                "f\"She said her name is {name}.\" # => \"She said her name is Reiko\"\n" +
                "# You can basically put any Python expression inside the braces and it will be output in the string.\n" +
                "f\"{name} is {len(name)} characters long.\" # => \"Reiko is 5 characters long.\"\n" +
                "\n" +
                "# None is an object\n" +
                "None  # => None\n" +
                "\n" +
                "# Don't use the equality \"==\" symbol to compare objects to None\n" +
                "# Use \"is\" instead. This checks for equality of object identity.\n" +
                "\"etc\" is None  # => False\n" +
                "None is None   # => True\n" +
                "\n" +
                "# None, 0, and empty strings/lists/dicts/tuples/sets all evaluate to False.\n" +
                "# All other values are True\n" +
                "bool(0)     # => False\n" +
                "bool(\"\")    # => False\n" +
                "bool([])    # => False\n" +
                "bool({})    # => False\n" +
                "bool(())    # => False\n" +
                "bool(set()) # => False\n" +
                "\n" +
                "####################################################\n" +
                "## 2. Variables and Collections\n" +
                "####################################################\n" +
                "\n" +
                "# Python has a print function\n" +
                "print(\"I'm Python. Nice to meet you!\")  # => I'm Python. Nice to meet you!\n" +
                "\n" +
                "# By default the print function also prints out a newline at the end.\n" +
                "# Use the optional argument end to change the end string.\n" +
                "print(\"Hello, World\", end=\"!\")  # => Hello, World!\n" +
                "\n" +
                "# Simple way to get input data from console\n" +
                "input_string_var = input(\"Enter some data: \") # Returns the data as a string\n" +
                "\n" +
                "# There are no declarations, only assignments.\n" +
                "# Convention is to use lower_case_with_underscores\n" +
                "some_var = 5\n" +
                "some_var  # => 5\n" +
                "\n" +
                "# Accessing a previously unassigned variable is an exception.\n" +
                "# See Control Flow to learn more about exception handling.\n" +
                "some_unknown_var  # Raises a NameError\n" +
                "\n" +
                "# if can be used as an expression\n" +
                "# Equivalent of C's '?:' ternary operator\n" +
                "\"yay!\" if 0 > 1 else \"nay!\"  # => \"nay!\"\n" +
                "\n" +
                "# Lists store sequences\n" +
                "li = []\n" +
                "# You can start with a prefilled list\n" +
                "other_li = [4, 5, 6]\n" +
                "\n" +
                "# Add stuff to the end of a list with append\n" +
                "li.append(1)    # li is now [1]\n" +
                "li.append(2)    # li is now [1, 2]\n" +
                "li.append(4)    # li is now [1, 2, 4]\n" +
                "li.append(3)    # li is now [1, 2, 4, 3]\n" +
                "# Remove from the end with pop\n" +
                "li.pop()        # => 3 and li is now [1, 2, 4]\n" +
                "# Let's put it back\n" +
                "li.append(3)    # li is now [1, 2, 4, 3] again.\n" +
                "\n" +
                "# Access a list like you would any array\n" +
                "li[0]   # => 1\n" +
                "# Look at the last element\n" +
                "li[-1]  # => 3\n" +
                "\n" +
                "# Looking out of bounds is an IndexError\n" +
                "li[4]  # Raises an IndexError\n" +
                "\n" +
                "# You can look at ranges with slice syntax.\n" +
                "# The start index is included, the end index is not\n" +
                "# (It's a closed/open range for you mathy types.)\n" +
                "li[1:3]   # Return list from index 1 to 3 => [2, 4]\n" +
                "li[2:]    # Return list starting from index 2 => [4, 3]\n" +
                "li[:3]    # Return list from beginning until index 3  => [1, 2, 4]\n" +
                "li[::2]   # Return list selecting every second entry => [1, 4]\n" +
                "li[::-1]  # Return list in reverse order => [3, 4, 2, 1]\n" +
                "# Use any combination of these to make advanced slices\n" +
                "# li[start:end:step]\n" +
                "\n" +
                "# Make a one layer deep copy using slices\n" +
                "li2 = li[:]  # => li2 = [1, 2, 4, 3] but (li2 is li) will result in false.\n" +
                "\n" +
                "# Remove arbitrary elements from a list with \"del\"\n" +
                "del li[2]  # li is now [1, 2, 3]\n" +
                "\n" +
                "# Remove first occurrence of a value\n" +
                "li.remove(2)  # li is now [1, 3]\n" +
                "li.remove(2)  # Raises a ValueError as 2 is not in the list\n" +
                "\n" +
                "# Insert an element at a specific index\n" +
                "li.insert(1, 2)  # li is now [1, 2, 3] again\n" +
                "\n" +
                "# Get the index of the first item found matching the argument\n" +
                "li.index(2)  # => 1\n" +
                "li.index(4)  # Raises a ValueError as 4 is not in the list\n" +
                "\n" +
                "# You can add lists\n" +
                "# Note: values for li and for other_li are not modified.\n" +
                "li + other_li  # => [1, 2, 3, 4, 5, 6]\n" +
                "\n" +
                "# Concatenate lists with \"extend()\"\n" +
                "li.extend(other_li)  # Now li is [1, 2, 3, 4, 5, 6]\n" +
                "\n" +
                "# Check for existence in a list with \"in\"\n" +
                "1 in li  # => True\n" +
                "\n" +
                "# Examine the length with \"len()\"\n" +
                "len(li)  # => 6\n" +
                "\n" +
                "\n" +
                "# Tuples are like lists but are immutable.\n" +
                "tup = (1, 2, 3)\n" +
                "tup[0]      # => 1\n" +
                "tup[0] = 3  # Raises a TypeError\n" +
                "\n" +
                "# Note that a tuple of length one has to have a comma after the last element but\n" +
                "# tuples of other lengths, even zero, do not.\n" +
                "type((1))   # => <class 'int'>\n" +
                "type((1,))  # => <class 'tuple'>\n" +
                "type(())    # => <class 'tuple'>\n" +
                "\n" +
                "# You can do most of the list operations on tuples too\n" +
                "len(tup)         # => 3\n" +
                "tup + (4, 5, 6)  # => (1, 2, 3, 4, 5, 6)\n" +
                "tup[:2]          # => (1, 2)\n" +
                "2 in tup         # => True\n" +
                "\n" +
                "# You can unpack tuples (or lists) into variables\n" +
                "a, b, c = (1, 2, 3)  # a is now 1, b is now 2 and c is now 3\n" +
                "# You can also do extended unpacking\n" +
                "a, *b, c = (1, 2, 3, 4)  # a is now 1, b is now [2, 3] and c is now 4\n" +
                "# Tuples are created by default if you leave out the parentheses\n" +
                "d, e, f = 4, 5, 6  # tuple 4, 5, 6 is unpacked into variables d, e and f\n" +
                "# respectively such that d = 4, e = 5 and f = 6\n" +
                "# Now look how easy it is to swap two values\n" +
                "e, d = d, e  # d is now 5 and e is now 4\n" +
                "\n" +
                "\n" +
                "# Dictionaries store mappings from keys to values\n" +
                "empty_dict = {}\n" +
                "# Here is a prefilled dictionary\n" +
                "filled_dict = {\"one\": 1, \"two\": 2, \"three\": 3}\n" +
                "\n" +
                "# Note keys for dictionaries have to be immutable types. This is to ensure that\n" +
                "# the key can be converted to a constant hash value for quick look-ups.\n" +
                "# Immutable types include ints, floats, strings, tuples.\n" +
                "invalid_dict = {[1,2,3]: \"123\"}  # => Raises a TypeError: unhashable type: 'list'\n" +
                "valid_dict = {(1,2,3):[1,2,3]}   # Values can be of any type, however.\n" +
                "\n" +
                "# Look up values with []\n" +
                "filled_dict[\"one\"]  # => 1\n" +
                "\n" +
                "# Get all keys as an iterable with \"keys()\". We need to wrap the call in list()\n" +
                "# to turn it into a list. We'll talk about those later.  Note - for Python\n" +
                "# versions <3.7, dictionary key ordering is not guaranteed. Your results might\n" +
                "# not match the example below exactly. However, as of Python 3.7, dictionary\n" +
                "# items maintain the order at which they are inserted into the dictionary.\n" +
                "list(filled_dict.keys())  # => [\"three\", \"two\", \"one\"] in Python <3.7\n" +
                "list(filled_dict.keys())  # => [\"one\", \"two\", \"three\"] in Python 3.7+\n" +
                "\n" +
                "\n" +
                "# Get all values as an iterable with \"values()\". Once again we need to wrap it\n" +
                "# in list() to get it out of the iterable. Note - Same as above regarding key\n" +
                "# ordering.\n" +
                "list(filled_dict.values())  # => [3, 2, 1]  in Python <3.7\n" +
                "list(filled_dict.values())  # => [1, 2, 3] in Python 3.7+\n" +
                "\n" +
                "# Check for existence of keys in a dictionary with \"in\"\n" +
                "\"one\" in filled_dict  # => True\n" +
                "1 in filled_dict      # => False\n" +
                "\n" +
                "# Looking up a non-existing key is a KeyError\n" +
                "filled_dict[\"four\"]  # KeyError\n" +
                "\n" +
                "# Use \"get()\" method to avoid the KeyError\n" +
                "filled_dict.get(\"one\")      # => 1\n" +
                "filled_dict.get(\"four\")     # => None\n" +
                "# The get method supports a default argument when the value is missing\n" +
                "filled_dict.get(\"one\", 4)   # => 1\n" +
                "filled_dict.get(\"four\", 4)  # => 4\n" +
                "\n" +
                "# \"setdefault()\" inserts into a dictionary only if the given key isn't present\n" +
                "filled_dict.setdefault(\"five\", 5)  # filled_dict[\"five\"] is set to 5\n" +
                "filled_dict.setdefault(\"five\", 6)  # filled_dict[\"five\"] is still 5\n" +
                "\n" +
                "# Adding to a dictionary\n" +
                "filled_dict.update({\"four\":4})  # => {\"one\": 1, \"two\": 2, \"three\": 3, \"four\": 4}\n" +
                "filled_dict[\"four\"] = 4         # another way to add to dict\n" +
                "\n" +
                "# Remove keys from a dictionary with del\n" +
                "del filled_dict[\"one\"]  # Removes the key \"one\" from filled dict\n" +
                "\n" +
                "# From Python 3.5 you can also use the additional unpacking options\n" +
                "{'a': 1, **{'b': 2}}  # => {'a': 1, 'b': 2}\n" +
                "{'a': 1, **{'a': 2}}  # => {'a': 2}\n" +
                "\n" +
                "\n" +
                "\n" +
                "# Sets store ... well sets\n" +
                "empty_set = set()\n" +
                "# Initialize a set with a bunch of values. Yeah, it looks a bit like a dict. Sorry.\n" +
                "some_set = {1, 1, 2, 2, 3, 4}  # some_set is now {1, 2, 3, 4}\n" +
                "\n" +
                "# Similar to keys of a dictionary, elements of a set have to be immutable.\n" +
                "invalid_set = {[1], 1}  # => Raises a TypeError: unhashable type: 'list'\n" +
                "valid_set = {(1,), 1}\n" +
                "\n" +
                "# Add one more item to the set\n" +
                "filled_set = some_set\n" +
                "filled_set.add(5)  # filled_set is now {1, 2, 3, 4, 5}\n" +
                "# Sets do not have duplicate elements\n" +
                "filled_set.add(5)  # it remains as before {1, 2, 3, 4, 5}\n" +
                "\n" +
                "# Do set intersection with &\n" +
                "other_set = {3, 4, 5, 6}\n" +
                "filled_set & other_set  # => {3, 4, 5}\n" +
                "\n" +
                "# Do set union with |\n" +
                "filled_set | other_set  # => {1, 2, 3, 4, 5, 6}\n" +
                "\n" +
                "# Do set difference with -\n" +
                "{1, 2, 3, 4} - {2, 3, 5}  # => {1, 4}\n" +
                "\n" +
                "# Do set symmetric difference with ^\n" +
                "{1, 2, 3, 4} ^ {2, 3, 5}  # => {1, 4, 5}\n" +
                "\n" +
                "# Check if set on the left is a superset of set on the right\n" +
                "{1, 2} >= {1, 2, 3} # => False\n" +
                "\n" +
                "# Check if set on the left is a subset of set on the right\n" +
                "{1, 2} <= {1, 2, 3} # => True\n" +
                "\n" +
                "# Check for existence in a set with in\n" +
                "2 in filled_set   # => True\n" +
                "10 in filled_set  # => False\n" +
                "\n" +
                "# Make a one layer deep copy\n" +
                "filled_set = some_set.copy()  # filled_set is {1, 2, 3, 4, 5}\n" +
                "filled_set is some_set        # => False\n" +
                "\n" +
                "\n" +
                "####################################################\n" +
                "## 3. Control Flow and Iterables\n" +
                "####################################################\n" +
                "\n" +
                "# Let's just make a variable\n" +
                "some_var = 5\n" +
                "\n" +
                "# Here is an if statement. Indentation is significant in Python!\n" +
                "# Convention is to use four spaces, not tabs.\n" +
                "# This prints \"some_var is smaller than 10\"\n" +
                "if some_var > 10:\n" +
                "    print(\"some_var is totally bigger than 10.\")\n" +
                "elif some_var < 10:    # This elif clause is optional.\n" +
                "    print(\"some_var is smaller than 10.\")\n" +
                "else:                  # This is optional too.\n" +
                "    print(\"some_var is indeed 10.\")\n" +
                "\n" +
                "\n" +
                "\"\"\"\n" +
                "For loops iterate over lists\n" +
                "prints:\n" +
                "    dog is a mammal\n" +
                "    cat is a mammal\n" +
                "    mouse is a mammal\n" +
                "\"\"\"\n" +
                "for animal in [\"dog\", \"cat\", \"mouse\"]:\n" +
                "    # You can use format() to interpolate formatted strings\n" +
                "    print(\"{} is a mammal\".format(animal))\n" +
                "\n" +
                "\"\"\"\n" +
                "\"range(number)\" returns an iterable of numbers\n" +
                "from zero to the given number\n" +
                "prints:\n" +
                "    0\n" +
                "    1\n" +
                "    2\n" +
                "    3\n" +
                "\"\"\"\n" +
                "for i in range(4):\n" +
                "    print(i)\n" +
                "\n" +
                "\"\"\"\n" +
                "\"range(lower, upper)\" returns an iterable of numbers\n" +
                "from the lower number to the upper number\n" +
                "prints:\n" +
                "    4\n" +
                "    5\n" +
                "    6\n" +
                "    7\n" +
                "\"\"\"\n" +
                "for i in range(4, 8):\n" +
                "    print(i)\n" +
                "\n" +
                "\"\"\"\n" +
                "\"range(lower, upper, step)\" returns an iterable of numbers\n" +
                "from the lower number to the upper number, while incrementing\n" +
                "by step. If step is not indicated, the default value is 1.\n" +
                "prints:\n" +
                "    4\n" +
                "    6\n" +
                "\"\"\"\n" +
                "for i in range(4, 8, 2):\n" +
                "    print(i)\n" +
                "\n" +
                "\"\"\"\n" +
                "To loop over a list, and retrieve both the index and the value of each item in the list\n" +
                "prints:\n" +
                "    0 dog\n" +
                "    1 cat\n" +
                "    2 mouse\n" +
                "\"\"\"\n" +
                "animals = [\"dog\", \"cat\", \"mouse\"]\n" +
                "for i, value in enumerate(animals):\n" +
                "    print(i, value)\n" +
                "\n" +
                "\"\"\"\n" +
                "While loops go until a condition is no longer met.\n" +
                "prints:\n" +
                "    0\n" +
                "    1\n" +
                "    2\n" +
                "    3\n" +
                "\"\"\"\n" +
                "x = 0\n" +
                "while x < 4:\n" +
                "    print(x)\n" +
                "    x += 1  # Shorthand for x = x + 1\n" +
                "\n" +
                "# Handle exceptions with a try/except block\n" +
                "try:\n" +
                "    # Use \"raise\" to raise an error\n" +
                "    raise IndexError(\"This is an index error\")\n" +
                "except IndexError as e:\n" +
                "    pass                 # Pass is just a no-op. Usually you would do recovery here.\n" +
                "except (TypeError, NameError):\n" +
                "    pass                 # Multiple exceptions can be handled together, if required.\n" +
                "else:                    # Optional clause to the try/except block. Must follow all except blocks\n" +
                "    print(\"All good!\")   # Runs only if the code in try raises no exceptions\n" +
                "finally:                 # Execute under all circumstances\n" +
                "    print(\"We can clean up resources here\")\n" +
                "\n" +
                "# Instead of try/finally to cleanup resources you can use a with statement\n" +
                "with open(\"myfile.txt\") as f:\n" +
                "    for line in f:\n" +
                "        print(line)\n" +
                "\n" +
                "# Writing to a file\n" +
                "contents = {\"aa\": 12, \"bb\": 21}\n" +
                "with open(\"myfile1.txt\", \"w+\") as file:\n" +
                "    file.write(str(contents))        # writes a string to a file\n" +
                "\n" +
                "with open(\"myfile2.txt\", \"w+\") as file:\n" +
                "    file.write(json.dumps(contents)) # writes an object to a file\n" +
                "\n" +
                "# Reading from a file\n" +
                "with open('myfile1.txt', \"r+\") as file:\n" +
                "    contents = file.read()           # reads a string from a file\n" +
                "print(contents)\n" +
                "# print: {\"aa\": 12, \"bb\": 21}\n" +
                "\n" +
                "with open('myfile2.txt', \"r+\") as file:\n" +
                "    contents = json.load(file)       # reads a json object from a file\n" +
                "print(contents)\n" +
                "# print: {\"aa\": 12, \"bb\": 21}\n" +
                "\n" +
                "\n" +
                "# Python offers a fundamental abstraction called the Iterable.\n" +
                "# An iterable is an object that can be treated as a sequence.\n" +
                "# The object returned by the range function, is an iterable.\n" +
                "\n" +
                "filled_dict = {\"one\": 1, \"two\": 2, \"three\": 3}\n" +
                "our_iterable = filled_dict.keys()\n" +
                "print(our_iterable)  # => dict_keys(['one', 'two', 'three']). This is an object that implements our Iterable interface.\n" +
                "\n" +
                "# We can loop over it.\n" +
                "for i in our_iterable:\n" +
                "    print(i)  # Prints one, two, three\n" +
                "\n" +
                "# However we cannot address elements by index.\n" +
                "our_iterable[1]  # Raises a TypeError\n" +
                "\n" +
                "# An iterable is an object that knows how to create an iterator.\n" +
                "our_iterator = iter(our_iterable)\n" +
                "\n" +
                "# Our iterator is an object that can remember the state as we traverse through it.\n" +
                "# We get the next object with \"next()\".\n" +
                "next(our_iterator)  # => \"one\"\n" +
                "\n" +
                "# It maintains state as we iterate.\n" +
                "next(our_iterator)  # => \"two\"\n" +
                "next(our_iterator)  # => \"three\"\n" +
                "\n" +
                "# After the iterator has returned all of its data, it raises a StopIteration exception\n" +
                "next(our_iterator)  # Raises StopIteration\n" +
                "\n" +
                "# We can also loop over it, in fact, \"for\" does this implicitly!\n" +
                "our_iterator = iter(our_iterable)\n" +
                "for i in our_iterator:\n" +
                "    print(i)  # Prints one, two, three\n" +
                "\n" +
                "# You can grab all the elements of an iterable or iterator by calling list() on it.\n" +
                "list(our_iterable)  # => Returns [\"one\", \"two\", \"three\"]\n" +
                "list(our_iterator)  # => Returns [] because state is saved\n" +
                "\n" +
                "\n" +
                "####################################################\n" +
                "## 4. Functions\n" +
                "####################################################\n" +
                "\n" +
                "# Use \"def\" to create new functions\n" +
                "def add(x, y):\n" +
                "    print(\"x is {} and y is {}\".format(x, y))\n" +
                "    return x + y  # Return values with a return statement\n" +
                "\n" +
                "# Calling functions with parameters\n" +
                "add(5, 6)  # => prints out \"x is 5 and y is 6\" and returns 11\n" +
                "\n" +
                "# Another way to call functions is with keyword arguments\n" +
                "add(y=6, x=5)  # Keyword arguments can arrive in any order.\n" +
                "\n" +
                "# You can define functions that take a variable number of\n" +
                "# positional arguments\n" +
                "def varargs(*args):\n" +
                "    return args\n" +
                "\n" +
                "varargs(1, 2, 3)  # => (1, 2, 3)\n" +
                "\n" +
                "# You can define functions that take a variable number of\n" +
                "# keyword arguments, as well\n" +
                "def keyword_args(**kwargs):\n" +
                "    return kwargs\n" +
                "\n" +
                "# Let's call it to see what happens\n" +
                "keyword_args(big=\"foot\", loch=\"ness\")  # => {\"big\": \"foot\", \"loch\": \"ness\"}\n" +
                "\n" +
                "\n" +
                "# You can do both at once, if you like\n" +
                "def all_the_args(*args, **kwargs):\n" +
                "    print(args)\n" +
                "    print(kwargs)\n" +
                "\"\"\"\n" +
                "all_the_args(1, 2, a=3, b=4) prints:\n" +
                "    (1, 2)\n" +
                "    {\"a\": 3, \"b\": 4}\n" +
                "\"\"\"\n" +
                "\n" +
                "# When calling functions, you can do the opposite of args/kwargs!\n" +
                "# Use * to expand tuples and use ** to expand kwargs.\n" +
                "args = (1, 2, 3, 4)\n" +
                "kwargs = {\"a\": 3, \"b\": 4}\n" +
                "all_the_args(*args)            # equivalent to all_the_args(1, 2, 3, 4)\n" +
                "all_the_args(**kwargs)         # equivalent to all_the_args(a=3, b=4)\n" +
                "all_the_args(*args, **kwargs)  # equivalent to all_the_args(1, 2, 3, 4, a=3, b=4)\n" +
                "\n" +
                "# Returning multiple values (with tuple assignments)\n" +
                "def swap(x, y):\n" +
                "    return y, x  # Return multiple values as a tuple without the parenthesis.\n" +
                "                 # (Note: parenthesis have been excluded but can be included)\n" +
                "\n" +
                "x = 1\n" +
                "y = 2\n" +
                "x, y = swap(x, y)     # => x = 2, y = 1\n" +
                "# (x, y) = swap(x,y)  # Again parenthesis have been excluded but can be included.\n" +
                "\n" +
                "# Function Scope\n" +
                "x = 5\n" +
                "\n" +
                "def set_x(num):\n" +
                "    # Local var x not the same as global variable x\n" +
                "    x = num    # => 43\n" +
                "    print(x)   # => 43\n" +
                "\n" +
                "def set_global_x(num):\n" +
                "    global x\n" +
                "    print(x)   # => 5\n" +
                "    x = num    # global var x is now set to 6\n" +
                "    print(x)   # => 6\n" +
                "\n" +
                "set_x(43)\n" +
                "set_global_x(6)\n" +
                "\n" +
                "\n" +
                "# Python has first class functions\n" +
                "def create_adder(x):\n" +
                "    def adder(y):\n" +
                "        return x + y\n" +
                "    return adder\n" +
                "\n" +
                "add_10 = create_adder(10)\n" +
                "add_10(3)   # => 13\n" +
                "\n" +
                "# There are also anonymous functions\n" +
                "(lambda x: x > 2)(3)                  # => True\n" +
                "(lambda x, y: x ** 2 + y ** 2)(2, 1)  # => 5\n" +
                "\n" +
                "# There are built-in higher order functions\n" +
                "list(map(add_10, [1, 2, 3]))          # => [11, 12, 13]\n" +
                "list(map(max, [1, 2, 3], [4, 2, 1]))  # => [4, 2, 3]\n" +
                "\n" +
                "list(filter(lambda x: x > 5, [3, 4, 5, 6, 7]))  # => [6, 7]\n" +
                "\n" +
                "# We can use list comprehensions for nice maps and filters\n" +
                "# List comprehension stores the output as a list which can itself be a nested list\n" +
                "[add_10(i) for i in [1, 2, 3]]         # => [11, 12, 13]\n" +
                "[x for x in [3, 4, 5, 6, 7] if x > 5]  # => [6, 7]\n" +
                "\n" +
                "# You can construct set and dict comprehensions as well.\n" +
                "{x for x in 'abcddeef' if x not in 'abc'}  # => {'d', 'e', 'f'}\n" +
                "{x: x**2 for x in range(5)}  # => {0: 0, 1: 1, 2: 4, 3: 9, 4: 16}\n" +
                "\n" +
                "\n" +
                "####################################################\n" +
                "## 5. Modules\n" +
                "####################################################\n" +
                "\n" +
                "# You can import modules\n" +
                "import math\n" +
                "print(math.sqrt(16))  # => 4.0\n" +
                "\n" +
                "# You can get specific functions from a module\n" +
                "from math import ceil, floor\n" +
                "print(ceil(3.7))   # => 4.0\n" +
                "print(floor(3.7))  # => 3.0\n" +
                "\n" +
                "# You can import all functions from a module.\n" +
                "# Warning: this is not recommended\n" +
                "from math import *\n" +
                "\n" +
                "# You can shorten module names\n" +
                "import math as m\n" +
                "math.sqrt(16) == m.sqrt(16)  # => True\n" +
                "\n" +
                "# Python modules are just ordinary Python files. You\n" +
                "# can write your own, and import them. The name of the\n" +
                "# module is the same as the name of the file.\n" +
                "\n" +
                "# You can find out which functions and attributes\n" +
                "# are defined in a module.\n" +
                "import math\n" +
                "dir(math)\n" +
                "\n" +
                "# If you have a Python script named math.py in the same\n" +
                "# folder as your current script, the file math.py will\n" +
                "# be loaded instead of the built-in Python module.\n" +
                "# This happens because the local folder has priority\n" +
                "# over Python's built-in libraries.\n" +
                "\n" +
                "\n" +
                "####################################################\n" +
                "## 6. Classes\n" +
                "####################################################\n" +
                "\n" +
                "# We use the \"class\" statement to create a class\n" +
                "class Human:\n" +
                "\n" +
                "    # A class attribute. It is shared by all instances of this class\n" +
                "    species = \"H. sapiens\"\n" +
                "\n" +
                "    # Basic initializer, this is called when this class is instantiated.\n" +
                "    # Note that the double leading and trailing underscores denote objects\n" +
                "    # or attributes that are used by Python but that live in user-controlled\n" +
                "    # namespaces. Methods(or objects or attributes) like: __init__, __str__,\n" +
                "    # __repr__ etc. are called special methods (or sometimes called dunder methods)\n" +
                "    # You should not invent such names on your own.\n" +
                "    def __init__(self, name):\n" +
                "        # Assign the argument to the instance's name attribute\n" +
                "        self.name = name\n" +
                "\n" +
                "        # Initialize property\n" +
                "        self._age = 0\n" +
                "\n" +
                "    # An instance method. All methods take \"self\" as the first argument\n" +
                "    def say(self, msg):\n" +
                "        print(\"{name}: {message}\".format(name=self.name, message=msg))\n" +
                "\n" +
                "    # Another instance method\n" +
                "    def sing(self):\n" +
                "        return 'yo... yo... microphone check... one two... one two...'\n" +
                "\n" +
                "    # A class method is shared among all instances\n" +
                "    # They are called with the calling class as the first argument\n" +
                "    @classmethod\n" +
                "    def get_species(cls):\n" +
                "        return cls.species\n" +
                "\n" +
                "    # A static method is called without a class or instance reference\n" +
                "    @staticmethod\n" +
                "    def grunt():\n" +
                "        return \"*grunt*\"\n" +
                "\n" +
                "    # A property is just like a getter.\n" +
                "    # It turns the method age() into a read-only attribute of the same name.\n" +
                "    # There's no need to write trivial getters and setters in Python, though.\n" +
                "    @property\n" +
                "    def age(self):\n" +
                "        return self._age\n" +
                "\n" +
                "    # This allows the property to be set\n" +
                "    @age.setter\n" +
                "    def age(self, age):\n" +
                "        self._age = age\n" +
                "\n" +
                "    # This allows the property to be deleted\n" +
                "    @age.deleter\n" +
                "    def age(self):\n" +
                "        del self._age\n" +
                "\n" +
                "\n" +
                "# When a Python interpreter reads a source file it executes all its code.\n" +
                "# This __name__ check makes sure this code block is only executed when this\n" +
                "# module is the main program.\n" +
                "if __name__ == '__main__':\n" +
                "    # Instantiate a class\n" +
                "    i = Human(name=\"Ian\")\n" +
                "    i.say(\"hi\")                     # \"Ian: hi\"\n" +
                "    j = Human(\"Joel\")\n" +
                "    j.say(\"hello\")                  # \"Joel: hello\"\n" +
                "    # i and j are instances of type Human, or in other words: they are Human objects\n" +
                "\n" +
                "    # Call our class method\n" +
                "    i.say(i.get_species())          # \"Ian: H. sapiens\"\n" +
                "    # Change the shared attribute\n" +
                "    Human.species = \"H. neanderthalensis\"\n" +
                "    i.say(i.get_species())          # => \"Ian: H. neanderthalensis\"\n" +
                "    j.say(j.get_species())          # => \"Joel: H. neanderthalensis\"\n" +
                "\n" +
                "    # Call the static method\n" +
                "    print(Human.grunt())            # => \"*grunt*\"\n" +
                "\n" +
                "    # Static methods can be called by instances too\n" +
                "    print(i.grunt())                # => \"*grunt*\"\n" +
                "\n" +
                "    # Update the property for this instance\n" +
                "    i.age = 42\n" +
                "    # Get the property\n" +
                "    i.say(i.age)                    # => \"Ian: 42\"\n" +
                "    j.say(j.age)                    # => \"Joel: 0\"\n" +
                "    # Delete the property\n" +
                "    del i.age\n" +
                "    # i.age                         # => this would raise an AttributeError\n" +
                "\n" +
                "\n" +
                "####################################################\n" +
                "## 6.1 Inheritance\n" +
                "####################################################\n" +
                "\n" +
                "# Inheritance allows new child classes to be defined that inherit methods and\n" +
                "# variables from their parent class.\n" +
                "\n" +
                "# Using the Human class defined above as the base or parent class, we can\n" +
                "# define a child class, Superhero, which inherits the class variables like\n" +
                "# \"species\", \"name\", and \"age\", as well as methods, like \"sing\" and \"grunt\"\n" +
                "# from the Human class, but can also have its own unique properties.\n" +
                "\n" +
                "# To take advantage of modularization by file you could place the classes above in their own files,\n" +
                "# say, human.py\n" +
                "\n" +
                "# To import functions from other files use the following format\n" +
                "# from \"filename-without-extension\" import \"function-or-class\"\n" +
                "\n" +
                "from human import Human\n" +
                "\n" +
                "\n" +
                "# Specify the parent class(es) as parameters to the class definition\n" +
                "class Superhero(Human):\n" +
                "\n" +
                "    # If the child class should inherit all of the parent's definitions without\n" +
                "    # any modifications, you can just use the \"pass\" keyword (and nothing else)\n" +
                "    # but in this case it is commented out to allow for a unique child class:\n" +
                "    # pass\n" +
                "\n" +
                "    # Child classes can override their parents' attributes\n" +
                "    species = 'Superhuman'\n" +
                "\n" +
                "    # Children automatically inherit their parent class's constructor including\n" +
                "    # its arguments, but can also define additional arguments or definitions\n" +
                "    # and override its methods such as the class constructor.\n" +
                "    # This constructor inherits the \"name\" argument from the \"Human\" class and\n" +
                "    # adds the \"superpower\" and \"movie\" arguments:\n" +
                "    def __init__(self, name, movie=False,\n" +
                "                 superpowers=[\"super strength\", \"bulletproofing\"]):\n" +
                "\n" +
                "        # add additional class attributes:\n" +
                "        self.fictional = True\n" +
                "        self.movie = movie\n" +
                "        # be aware of mutable default values, since defaults are shared\n" +
                "        self.superpowers = superpowers\n" +
                "\n" +
                "        # The \"super\" function lets you access the parent class's methods\n" +
                "        # that are overridden by the child, in this case, the __init__ method.\n" +
                "        # This calls the parent class constructor:\n" +
                "        super().__init__(name)\n" +
                "\n" +
                "    # override the sing method\n" +
                "    def sing(self):\n" +
                "        return 'Dun, dun, DUN!'\n" +
                "\n" +
                "    # add an additional instance method\n" +
                "    def boast(self):\n" +
                "        for power in self.superpowers:\n" +
                "            print(\"I wield the power of {pow}!\".format(pow=power))\n" +
                "\n" +
                "\n" +
                "if __name__ == '__main__':\n" +
                "    sup = Superhero(name=\"Tick\")\n" +
                "\n" +
                "    # Instance type checks\n" +
                "    if isinstance(sup, Human):\n" +
                "        print('I am human')\n" +
                "    if type(sup) is Superhero:\n" +
                "        print('I am a superhero')\n" +
                "\n" +
                "    # Get the Method Resolution search Order used by both getattr() and super()\n" +
                "    # This attribute is dynamic and can be updated\n" +
                "    print(Superhero.__mro__)    # => (<class '__main__.Superhero'>,\n" +
                "                                # => <class 'human.Human'>, <class 'object'>)\n" +
                "\n" +
                "    # Calls parent method but uses its own class attribute\n" +
                "    print(sup.get_species())    # => Superhuman\n" +
                "\n" +
                "    # Calls overridden method\n" +
                "    print(sup.sing())           # => Dun, dun, DUN!\n" +
                "\n" +
                "    # Calls method from Human\n" +
                "    sup.say('Spoon')            # => Tick: Spoon\n" +
                "\n" +
                "    # Call method that exists only in Superhero\n" +
                "    sup.boast()                 # => I wield the power of super strength!\n" +
                "                                # => I wield the power of bulletproofing!\n" +
                "\n" +
                "    # Inherited class attribute\n" +
                "    sup.age = 31\n" +
                "    print(sup.age)              # => 31\n" +
                "\n" +
                "    # Attribute that only exists within Superhero\n" +
                "    print('Am I Oscar eligible? ' + str(sup.movie))\n" +
                "\n" +
                "####################################################\n" +
                "## 6.2 Multiple Inheritance\n" +
                "####################################################\n" +
                "\n" +
                "# Another class definition\n" +
                "# bat.py\n" +
                "class Bat:\n" +
                "\n" +
                "    species = 'Baty'\n" +
                "\n" +
                "    def __init__(self, can_fly=True):\n" +
                "        self.fly = can_fly\n" +
                "\n" +
                "    # This class also has a say method\n" +
                "    def say(self, msg):\n" +
                "        msg = '... ... ...'\n" +
                "        return msg\n" +
                "\n" +
                "    # And its own method as well\n" +
                "    def sonar(self):\n" +
                "        return '))) ... ((('\n" +
                "\n" +
                "if __name__ == '__main__':\n" +
                "    b = Bat()\n" +
                "    print(b.say('hello'))\n" +
                "    print(b.fly)\n" +
                "\n" +
                "\n" +
                "# And yet another class definition that inherits from Superhero and Bat\n" +
                "# superhero.py\n" +
                "from superhero import Superhero\n" +
                "from bat import Bat\n" +
                "\n" +
                "# Define Batman as a child that inherits from both Superhero and Bat\n" +
                "class Batman(Superhero, Bat):\n" +
                "\n" +
                "    def __init__(self, *args, **kwargs):\n" +
                "        # Typically to inherit attributes you have to call super:\n" +
                "        # super(Batman, self).__init__(*args, **kwargs)\n" +
                "        # However we are dealing with multiple inheritance here, and super()\n" +
                "        # only works with the next base class in the MRO list.\n" +
                "        # So instead we explicitly call __init__ for all ancestors.\n" +
                "        # The use of *args and **kwargs allows for a clean way to pass arguments,\n" +
                "        # with each parent \"peeling a layer of the onion\".\n" +
                "        Superhero.__init__(self, 'anonymous', movie=True,\n" +
                "                           superpowers=['Wealthy'], *args, **kwargs)\n" +
                "        Bat.__init__(self, *args, can_fly=False, **kwargs)\n" +
                "        # override the value for the name attribute\n" +
                "        self.name = 'Sad Affleck'\n" +
                "\n" +
                "    def sing(self):\n" +
                "        return 'nan nan nan nan nan batman!'\n" +
                "\n" +
                "\n" +
                "if __name__ == '__main__':\n" +
                "    sup = Batman()\n" +
                "\n" +
                "    # Get the Method Resolution search Order used by both getattr() and super().\n" +
                "    # This attribute is dynamic and can be updated\n" +
                "    print(Batman.__mro__)       # => (<class '__main__.Batman'>,\n" +
                "                                # => <class 'superhero.Superhero'>,\n" +
                "                                # => <class 'human.Human'>,\n" +
                "                                # => <class 'bat.Bat'>, <class 'object'>)\n" +
                "\n" +
                "    # Calls parent method but uses its own class attribute\n" +
                "    print(sup.get_species())    # => Superhuman\n" +
                "\n" +
                "    # Calls overridden method\n" +
                "    print(sup.sing())           # => nan nan nan nan nan batman!\n" +
                "\n" +
                "    # Calls method from Human, because inheritance order matters\n" +
                "    sup.say('I agree')          # => Sad Affleck: I agree\n" +
                "\n" +
                "    # Call method that exists only in 2nd ancestor\n" +
                "    print(sup.sonar())          # => ))) ... (((\n" +
                "\n" +
                "    # Inherited class attribute\n" +
                "    sup.age = 100\n" +
                "    print(sup.age)              # => 100\n" +
                "\n" +
                "    # Inherited attribute from 2nd ancestor whose default value was overridden.\n" +
                "    print('Can I fly? ' + str(sup.fly)) # => Can I fly? False\n" +
                "\n" +
                "\n" +
                "\n" +
                "####################################################\n" +
                "## 7. Advanced\n" +
                "####################################################\n" +
                "\n" +
                "# Generators help you make lazy code.\n" +
                "def double_numbers(iterable):\n" +
                "    for i in iterable:\n" +
                "        yield i + i\n" +
                "\n" +
                "# Generators are memory-efficient because they only load the data needed to\n" +
                "# process the next value in the iterable. This allows them to perform\n" +
                "# operations on otherwise prohibitively large value ranges.\n" +
                "# NOTE: `range` replaces `xrange` in Python 3.\n" +
                "for i in double_numbers(range(1, 900000000)):  # `range` is a generator.\n" +
                "    print(i)\n" +
                "    if i >= 30:\n" +
                "        break\n" +
                "\n" +
                "# Just as you can create a list comprehension, you can create generator\n" +
                "# comprehensions as well.\n" +
                "values = (-x for x in [1,2,3,4,5])\n" +
                "for x in values:\n" +
                "    print(x)  # prints -1 -2 -3 -4 -5 to console/terminal\n" +
                "\n" +
                "# You can also cast a generator comprehension directly to a list.\n" +
                "values = (-x for x in [1,2,3,4,5])\n" +
                "gen_to_list = list(values)\n" +
                "print(gen_to_list)  # => [-1, -2, -3, -4, -5]\n" +
                "\n" +
                "\n" +
                "# Decorators\n" +
                "# In this example `beg` wraps `say`. If say_please is True then it\n" +
                "# will change the returned message.\n" +
                "from functools import wraps\n" +
                "\n" +
                "\n" +
                "def beg(target_function):\n" +
                "    @wraps(target_function)\n" +
                "    def wrapper(*args, **kwargs):\n" +
                "        msg, say_please = target_function(*args, **kwargs)\n" +
                "        if say_please:\n" +
                "            return \"{} {}\".format(msg, \"Please! I am poor :(\")\n" +
                "        return msg\n" +
                "\n" +
                "    return wrapper\n" +
                "\n" +
                "\n" +
                "@beg\n" +
                "def say(say_please=False):\n" +
                "    msg = \"Can you buy me a beer?\"\n" +
                "    return msg, say_please\n" +
                "\n" +
                "\n" +
                "print(say())                 # Can you buy me a beer?\n" +
                "print(say(say_please=True))  # Can you buy me a beer? Please! I am poor :(";
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        binding.codeView.setOnHighlightListener(this)
                .setOnHighlightListener(this)
                .setTheme(Theme.MONOKAI_SUBLIME)
                .setCode(code)
                .setLanguage(Language.PYTHON)
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