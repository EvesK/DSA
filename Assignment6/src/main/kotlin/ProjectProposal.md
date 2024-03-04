# Project Proposal
### Data Structures and Algorithms | Assignment 6
### Evelyn Kessler | March 14, 2024

<br>

#### Project Overview
For this project I will implement the Knuth–Morris–Pratt (KMP) Algorithm for string searching. This algorithm uses a "partial match" table which indicates where to start looking for the next match when a mismatch is found. This "partial match" table is built by understanding the location of any repeating characters in the needle string (i.e. string we are searching for). The KMP Algorithm is a significant improvement over the naive string search approach, particularly in cases where the needle and haystack strings (i.e. string we are searching for and string we are searching in respectively) have many of the same repeating character, for example searching for "AAAAAAAAB" in the string "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAA".

Having implemented and unit tested the KMP Algorithm, I will use it to build a simple find and replace program where the user can load/input a "document", print the "document", and perform any number of find and replace operations on the document. I will document and unit test my find and replace program before completing a writeup on my work. I will submit a GitHub repository with my initial project proposal, documented code, unit tests, and final writeup.

#### Project Deliverables
- KMP Algorithm Implementation with Function Documentation
- KMP Algorithm Unit Tests
- Find and Replace Implementation with Function Documentation
- Find and Replace "User Interface" (i.e. command line ability to load a "document", print a "document", and find and replace a string in the "document")
- Find and Replace Unit Tests
- Final Report/Write Up

#### Project Time Breakdown
- Researching KMP Algorithm : 30 min
- Outlining KMP Algorithm : 30 min
- Implementing KMP Algorithm : 3 - 4 hours
- Unit Testing the KMP Algorithm : 1 - 2 hours
- Outlining Find and Replace Program : 20 min
- Outlining Find and Replace Program "User Interface" : 10 min
- Implementing Find and Replace Program : 1 - 3 hours
- Unit Testing Find and Replace Program : 1 - 2 hours
- Cleanup, Optimization, and Report Writing : 1 - 2 hours

Total: 8.5 - 14.5 hours

#### Project Grading Rubric
**"A"**
- Implemented KMP Algorithm with no bugs.
- Implemented Find and Replace Program with no bugs.
- Implemented Find and Replace Program command line "user interface".
- Evidence of some optimization on KMP Algorithm, thought went into an efficient implementation.
- Documented all functions, including inline documentation for complicated operations.
- Unit tested a variety of non-obvious cases.

**"B"**
- Implemented KMP Algorithm with no bugs.
- Implemented Find and Replace Program with some bugs.
- Find and Replace Program run from main(), no "user interface". 
- No evidence of optimization on KMP Algorithm, no thought went into an efficient implementation.
- Documented most functions.
- Unit tested a variety of cases.

**"C"**
- Implemented KMP Algorithm with some bugs.
- Documented some functions. 
- Unit tested some cases.