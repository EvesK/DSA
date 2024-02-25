# Sorting Benchmarking Writeup
See associated code in the SortingBenchmarking.kt file.

In order to test my sorting algorithms, I designed a system with the following functions.
1. createRandomList: This function takes in a size and returns a mutable list of the given size filled with random numbers. It uses the kotlin random library to generate the random numbers.
2. measureSortingTimes: This function takes in a list and runs each sorting algorithm (insertion sort, selection sort, merge sort, and quick sort) on the same given list while tracking the time in milliseconds it took for each algorithm to run. It returns those run times in a list.
3. runTrials: This function implements a loop in order to test lists of different sizes. It takes in a start, end, and step value to bound the loop. At each size in the loop it calls createRandomList to generate a list of the given size, then calls measureSortingTimes to run all four sorting algorithms on that list. It stores the run times for all four algorithms at each size in a map.
4. plotResults: This function reorganizes the map from runTrials into a format that can be used on plotGraph, then calls plotGraph.
5. plotGraph: This function is called from plotResults and has the code to create a line graph of list size vs. run time for all four algorithms.

Note that I used ChatGPT to help me write the plotResults and plotGraph functions, since I felt those were more about creating visuals than data structures and algorithms specific learning. ChatGPT recommended the XChart library for creating the chart, which I added to the dependencies in the gradle build file.

To create my final visual, I call runTrials in my main function and then feed the results into plotGraph. I chose to plot 100 list sizes between 1 and 10,000. This means that I ran the experiment 100 times on lists ranging from size 1 to size 10,000.

The final plot clearly shows that merge sort and quick sort maintain run times below 5 milliseconds for all lists tested, with a very shallow upwards trend as the list size increases. This is consistent with the big O of O(n log n) of both algorithms. In contrast, the run time of insertion sort and selection sort quickly rise, reaching times about 70 milliseconds for lists close to 10,000 entries. This is expected as both of these sorting algorithms have big O of O(n^2), at least in the worst case.

While we see some variance in the run times or merge and quick sort, overall there isn't a meaningful difference between the run times of the two algorithms and they both trend the same way. The same can be said for the difference, or lack thereof, between insertion sort and selection sort.