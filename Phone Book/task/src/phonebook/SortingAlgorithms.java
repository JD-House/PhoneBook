package phonebook;


import java.util.Collections;
import java.util.List;

public abstract class SortingAlgorithms {

    //Bubble sort
    public static <T extends Comparable<T>> boolean bubbleSort(List<T> array, long maxTime) {
        if (array.size() < 2) {
            return true;
        }

        boolean isSorted = false;
        T temp;

        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.size() - 1; ++i) {
                if (System.currentTimeMillis() > maxTime) {
                    return false;
                }
                if (array.get(i).compareTo(array.get(i + 1)) > 0) {
                    temp = array.get(i);
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);
                    isSorted = false;
                }
            }
        }
        return true;
    }

    //Quick sort
    public static <T extends Comparable<T>> void quickSort(List<T> array, int low, int high) {
        if (low < high)
        {
            int pi  = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);

        }


    }

    public static <T extends Comparable<T>> int partition(List<T> array, int low, int high) {
        T pivot = array.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++)
        {
            if (array.get(j).compareTo(pivot) < 0)
            {
                Collections.swap(array, ++i, j);
            }
        }
        Collections.swap(array, ++i, high);
        return i;
    }

}