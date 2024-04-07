1.What is your hash function like for hash table solution (if you implemented hash table)？

When calculating hash values, I used the technique of "prime modulus". The specific method is to multiply the current hash value by a specific prime number for each character in the word, and then add the ASCII value of that character. The advantage of doing this is that different word combinations are more likely to generate different hash values, thereby reducing the possibility of hash conflicts. But to prevent the hash value from exceeding the range of the array, I will use modular operations to ensure that the obtained hash value is always within the valid index range.

When encountering a hash conflict, where two different words produce the same hash value, I use linear detection to solve it. This means that when I calculate a hash value and find that the position in the hash table is already occupied by other words, I will not directly give up or report an error. On the contrary, I will start from that position and check the adjacent positions one by one until I find an empty slot to insert the current word. Although this method may increase some search time, it can effectively resolve hash conflicts and ensure that each word can find its own position in the hash table.

In short, I use prime modulus to calculate hash values, ensuring that each word receives a relatively unique index; When encountering hash conflicts, I use linear detection to find empty slots for insertion.

2.For binary search trees (if you implemented it), how does your implementation get the top-100 list?

Firstly, if the current node is empty (i.e. there are no nodes to handle), then the current index is directly returned, indicating that the end of a subtree has been traversed.

Next, starting from the current node, we recursively traverse its left subtree. During the process of traversing the left subtree, we may update the index because the nodes of the left subtree will be added to the array first. After traversing the left subtree, we will obtain an updated index that indicates that all nodes in the left subtree have been processed and added to the array.

Then, we add the current node itself to the array and update the index accordingly. This is done to ensure that the current node's position in the array is immediately after all nodes in its left subtree.

Finally, we recursively traverse the right subtree of the current node again. Similar to the processing of the left subtree, when traversing the right subtree, the index is also updated to reflect the situation where the right subtree node is added to the array.

3.What can you say about the correctness of your implementation? Any issues, bugs or problems you couldn't solve? Any idea why the problem persists and what could perhaps be the solution?

The code has undergone several rounds of debugging and modifications, and at present, there seem to be no apparent errors. However, it's worth noting that if the code is tasked with processing a substantial volume of files or containing numerous words simultaneously, it may encounter performance challenges. During my testing, I observed that my VSC occasionally became unresponsive, leading to a prompt asking whether I wished to continue waiting or reopen the file. Surprisingly, despite the apparent freezing, the actual execution time of the test was not commensurate with the duration of the apparent crash. This suggests that there might be underlying issues related to resource management or efficiency that need to be further investigated and optimized.

4.What can you say about the time complexity of your implementation? How efficient is the code in reading and managing the words and their counts? How efficient is your code in getting the top-100 list? Which sorting algorithm are you using? What is the time complexity of that algorithm?

Time Complexity Overview:
BST:
Insertion: Worst-case is O(log n), based on tree balance.
Traversal: O(n) for operations like counting word occurrences.
HASH:
Insertion: Average O(1), but can rise to O(n) with conflicts.
Heap Sorting: Time complexity is O(nlogn).
Word Count Efficiency:
BST:
Fast O(log n) search, insertion, and deletion based on keys.
HASH:
Average O(1) time complexity for insert, delete, and search.
Top 100 List Efficiency:
BST:
Efficiency varies with tree size and balance, but generally effective.
HASH:
Depends on sorting algorithm; heap sorting offers O(nlogn) complexity for medium-sized data.
Sorting Algorithm:
Both BST and HASH implementations use heap sorting, with a time complexity of O(nlogn).

5.What did you find the most difficult things to understand and implement in this programming task? Why?

Optimizing hash table performance for processing vast data is challenging. It demands comprehension of hash functions, conflict resolution, and table resizing. 

6.What did you learn doing this?

This learning experience has taught me to use hash tables for word frequency counts, deepened my understanding of hash functions, conflict handling, sorting algorithms, and calculating binary tree depth. I also gained skills in writing heap sorting algorithms.