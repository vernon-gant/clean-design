package com.example.topic04;

public class QuickSort {

    // {P: arr.length > 0} quickSort(arr) {Q: arr is sorted}
    public static void quickSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /*
     * Precondition: P: arr.length > 0
     *
     * Function body:
     *     1. Check the precodntion in "if"
     *     2. Call quickSort(arr, 0, arr.length - 1) to sort the entire array.
     *
     * Postcondition: Q: arr is sorted
     *     The entire array is sorted in ascending order.
     *
     * Proof steps:
     *     1. Precondition is checked
     *     2. quickSort(arr, 0, arr.length - 1) is invoked and the precondition for it is always held because low is always 0 what is always less than entire array size which is at least 1.
     *        High is always less than arr.length because "arr.length - 1" is passed as argument for "high"
     *     3. By correctness proof of quickSort(arr, low, high), the array is sorted.
     */

    // {P: arr.length > 0 and 0 <= low <= high < arr.length} quickSort(arr, low, high) {Q: arr[low..high] is sorted}
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    /*
     * Precondition: P: arr.length > 0 and 0 <= low <= high < arr.length
     *
     * Function body:
     *     1. If low < high:
     *        - Partition the array around a pivot.
     *        - Recursively sort left and right partitions.
     *
     * Postcondition: Q: arr[low..high] is sorted
     *
     * Proof steps:
     *     1. Initialization:
     *        Base case low >= high trivially to satisfy the postcondition.
     *
     *     2. Recursive step:
     *        - Correct partitioning ensures pivot is correctly placed.
     *        - Preconditions for recursive calls are satisfied as shown in comments:
     *            - quickSort(arr, low, pi - 1) : low >= 0 per function precondition. 0 <= pivot < arr.length per function postcondition
     *            - quickSort(arr, pi + 1, high) :
     *        - Recursively sorting partitions ensures subarrays are sorted.
     *
     *     3. Termination:
     *        Each recursion reduces subarray size, thus termination is guaranteed.
     */

    // {P: arr.length > 0 and 0 <= low <= high < arr.length} partition(arr, low, high) {Q: arr[low..pivot-1] <= arr[pivot] <= arr[pivot+1..high]}
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    /*
     * Precondition: P: arr.length > 0 and 0 <= low <= high < arr.length
     *
     * Function body:
     *     - Select pivot as arr[high].
     *     - Reorder elements so that elements less than or equal to pivot are on the left,
     *       and elements greater than pivot are on the right.
     *
     * Postcondition: Q: arr[low..pivot-1] <= arr[pivot] <= arr[pivot+1..high]
     *
     * Loop invariant:
     *     - Elements from low to (i-1) <= pivot.
     *     - Elements from i to (j-1) > pivot.
     *     - Element at high is pivot.
     *
     * Proof:
     *     - Initialization: trivially true at the start.
     *     - Maintenance: Each iteration maintains this condition.
     *     - Termination: pivot is placed at correct index i.
     */

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}