# Final Report
### Data Structures and Algorithms | Assignment 6
### Evelyn Kessler | March 14, 2024

#### Project Summary
For this project I implemented the KMP Algorithm to get a better understanding of how the algorithm works.
Having done that, I created a second implementation of the algorithm as part of a find and replace program.
My find and replace program operates off the command line; when the FindAndReplace file is run it prompts the
user for an input line that contains the overall text, the string to find in the text, and the string to replace it with.

The input line can also contain optional flags -ignoreCase and/or -standalone to activate various features in the program.
Ignore case will set the program to be case-insensitive; with this enabled, the find string "test" would match to "test", 
"Test", "TEST", or any other uppercase/lowercase combination of the word in the text string.
Standalone will set the program to only consider instances of the find string that are standalone words; with this enabled,
the find string "test" would match "test", "test.", "test!", and any other combination with non-letter characters, but not 
"testing" or "tests".

To complete the project I added extensive unit testing for both the KMP class in KMP.kt and the replaceText function in
FindAndReplace.kt. In particular, I found and subsequently fixed several edge cases in the replaceText function by designing
a variety of unit tests. Some examples include when there are multiple overlapping find strings, such as finding the string
"aa" in the text "aaaaaaaaa" and when the replace string contains the find string such as replacing "is" with "is not".

#### Summary of Deliverables
For this project I produced code for the KMP Algorithm and for the Find and Replace function, which can be found in the files
KMP.kt and FindAndReplace.kt. I also produced unit tests for both which can be found in KMPTest.kt and FindAndReplaceTest.kt
respectively. All code contains function documentation and inline documentation, and all unit tests contain a brief test description.