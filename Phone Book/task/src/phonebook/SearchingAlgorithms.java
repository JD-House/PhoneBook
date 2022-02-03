package phonebook;

import java.util.ArrayDeque;
import java.util.List;

public abstract class SearchingAlgorithms {

    public static String linearSearch(List<String> requests, List<String> baseData) {

        ArrayDeque<String> requestedData = new ArrayDeque<>(requests);

        //Entries counter
        int entriesCounter = 0;
        int requestedDataSize = requestedData.size();

        //Searching
        String request;
        while ((request = requestedData.pollFirst()) != null) {
            //Finding occurrences
            for (String data : baseData) {
                if (data.contains(request)) {
                    ++entriesCounter;
                    break;
                }
            }
        }

        return String.format("%d/%d", entriesCounter, requestedDataSize);
    }

    public static String jumpSearch(List<String> requests, List<String> baseData) {

        ArrayDeque<String> requestedData = new ArrayDeque<>(requests);
        //Entries counter
        int entriesCounter = 0;
        int requestedDataSize = requestedData.size();
        int baseDataSize = baseData.size();

        // Finding block size to be jumped
        int step = (int) Math.floor(Math.sqrt(baseDataSize));
        int previousStep = 0;
        int currentStep = 0;


        //Searching

        // Finding the block where element is present (if it is present)
        String request;

        while ((request = requestedData.pollFirst()) != null) {

            while (previousStep < baseDataSize) {
                //If last block is less than others
                if (currentStep > baseDataSize - 1) {
                    currentStep = baseDataSize - 1;
                }
                //If found
                if (baseData.get(currentStep).contains(request)) {
                    ++entriesCounter;
                    currentStep = 0;
                    previousStep = 0;
                    break;
                }
                //If current value greater than requested - Reversed linear search
                if (baseData.get(currentStep).compareTo(request) > 0) {
                    while (currentStep > previousStep) {
                        --currentStep;
                        if (baseData.get(currentStep).contains(request)) {
                            ++entriesCounter;
                            currentStep = 0;
                            previousStep = 0;
                            break;
                        }
                    }
                    break;
                }
                //Next step
                previousStep = currentStep;
                currentStep += step;
            }

        }

        return String.format("%d/%d", entriesCounter, requestedDataSize);
    }

    public static String binarySearch(List<String> requests, List<String> baseData) {

        ArrayDeque<String> requestedData = new ArrayDeque<>(requests);

        //Entries counter
        int entriesCounter = 0;
        int requestedDataSize = requestedData.size();

        String request;

        while ((request = requestedData.pollFirst()) != null) {
            entriesCounter += bSearch(request, baseData)? 1 : 0;
        }

        return String.format("%d/%d", entriesCounter, requestedDataSize);
    }

    private static boolean bSearch(String request, List<String> baseData) {
        int index = baseData.size() / 2;
        if (baseData.size() == 0) {
            return false;
        }
        if (baseData.get(index).contains(request)) {
            return true;
        }
        if (baseData.get(index).compareTo(request) > 0) {
            return bSearch(request, baseData.subList(0, index));
        }

        return bSearch(request, baseData.subList(index, baseData.size()));
    }
}
