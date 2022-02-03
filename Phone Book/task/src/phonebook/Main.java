package phonebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {
    public static void main(String[] args) {



        //Files path
        Path requestedDataPath = Paths.get("C:\\Pbook\\find.txt");
        Path baseDataPath  = Paths.get("C:\\Pbook\\directory.txt");

        //Storage
        List<String> requestedData = new ArrayList<>();
        List<String> baseData = new ArrayList<>();

        HashMap<String, String> baseHash = new HashMap<>();

        //Filling request data storage

        try (InputStream in = Files.newInputStream(requestedDataPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            String number;
            while ((line = reader.readLine()) != null) {
                number = line.replaceAll("\\D", "");
                requestedData.add(line.replaceAll("\\d", "").concat(" ").concat(number).trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Filling main data storage

        try (InputStream in = Files.newInputStream(baseDataPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            String number;
            while ((line = reader.readLine()) != null) {
                number = line.replaceAll("\\D", "");
                baseData.add(line.replaceAll("\\d", "").concat(" ").concat(number).trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Filling hash map
        Instant startFillingHashMap = Instant.now();
        try (InputStream in = Files.newInputStream(baseDataPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            String number;
            while ((line = reader.readLine()) != null) {
                number = line.replaceAll("\\D", "");
                baseHash.put(line.replaceAll("\\d", "").trim(), number);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Instant endFillingHashMap = Instant.now();
        Duration dtFillingHashMap = Duration.between(startFillingHashMap, endFillingHashMap);


        //Performing operations


        //Linear search
        System.out.println("Start searching (linear search)...");
        Instant startLinearSearch = Instant.now();
        String entries = SearchingAlgorithms.linearSearch(requestedData, baseData);
        Instant endLinearSearch = Instant.now();
        Duration dtLinearSearch = Duration.between(startLinearSearch, endLinearSearch);

        System.out.printf("Found %s entries. Time taken: %d min. %d sec. %d ms.\n\n",
                entries, dtLinearSearch.toMinutesPart(), dtLinearSearch.toSecondsPart(), dtLinearSearch.toMillisPart());

        //Bubble sort + Jump search

        System.out.println("Start searching (bubble sort + jump search)...");
        Instant startBubbleJump = Instant.now();
        Instant MaxTimeForSorting = startBubbleJump.plusMillis(dtLinearSearch.toMillis() * 10);

        //Bubble sort

        boolean isSorted = SortingAlgorithms.bubbleSort(baseData, MaxTimeForSorting.toEpochMilli());

        //Bubble sort time
        Instant endBubbleSort = Instant.now();
        Duration dtBubbleSort = Duration.between(startBubbleJump, endBubbleSort);

        //Jump search
        if (isSorted) {
            Instant startJumpSearch = Instant.now();
            String entriesSecond = SearchingAlgorithms.jumpSearch(requestedData, baseData);
            Instant endJumpSearch = Instant.now();
            Duration dtJumpSearch = Duration.between(startJumpSearch, endJumpSearch);
            Duration dtGeneralOne = dtBubbleSort.plusMillis(dtJumpSearch.toMillis());

            System.out.printf("Found %s entries. Time taken: %d min. %d sec. %d ms.\n",
                    entriesSecond, dtGeneralOne.toMinutesPart(), dtGeneralOne.toSecondsPart(), dtGeneralOne.toMillisPart());
            System.out.printf("Sorting time: %d min. %d sec. %d ms.\n", dtBubbleSort.toMinutesPart(),
                    dtBubbleSort.toSecondsPart(), dtBubbleSort.toMillisPart());
            System.out.printf("Searching time: %d min. %d sec. %d ms.\n\n", dtJumpSearch.toMinutesPart(),
                    dtJumpSearch.toSecondsPart(), dtJumpSearch.toMillisPart());
        }
        if (!isSorted) {
            Instant startNextLinearSearch = Instant.now();
            String entriesThird = SearchingAlgorithms.linearSearch(requestedData, baseData);
            Instant endNextLinearSearch = Instant.now();
            Duration dtNextLinearSearch = Duration.between(startNextLinearSearch, endNextLinearSearch);

            Duration dtGeneralTwo = dtBubbleSort.plusMillis(dtNextLinearSearch.toMillis());

            System.out.printf("Found %s entries. Time taken: %d min. %d sec. %d ms.\n",
                    entriesThird, dtGeneralTwo.toMinutesPart(),
                    dtGeneralTwo.toSecondsPart(), dtGeneralTwo.toMillisPart());
            System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search\n",
                    dtBubbleSort.toMinutesPart(),
                    dtBubbleSort.toSecondsPart(), dtBubbleSort.toMillisPart());
            System.out.printf("Searching time: %d min. %d sec. %d ms.\n\n", dtNextLinearSearch.toMinutesPart(),
                    dtNextLinearSearch.toSecondsPart(), dtNextLinearSearch.toMillisPart());
        }

        //Quick sort + binary search
        System.out.println("Start searching (quick sort + binary search)...");
        Instant startQuickSort = Instant.now();
        SortingAlgorithms.quickSort(baseData, 0, baseData.size() - 1);
        Instant endQuickSort = Instant.now();
        Instant startBinarySearch = Instant.now();
        String entriesFourth = SearchingAlgorithms.binarySearch(requestedData, baseData);
        Instant endBinarySearch = Instant.now();
        Duration dtBinarySearch = Duration.between(startBinarySearch, endBinarySearch);
        Duration dtQuickSort = Duration.between(startQuickSort, endQuickSort);
        Duration dtGeneralTwo = dtQuickSort.plusMillis(dtBinarySearch.toMillis());

        System.out.printf("Found %s entries. Time taken: %d min. %d sec. %d ms.\n",
                entriesFourth, dtGeneralTwo.toMinutesPart(), dtGeneralTwo.toSecondsPart(),
                dtGeneralTwo.toMillisPart());
        System.out.printf("Sorting time: %d min. %d sec. %d ms.\n", dtQuickSort.toMinutesPart(),
                dtQuickSort.toSecondsPart(), dtQuickSort.toMillisPart());
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n\n", dtBinarySearch.toMinutesPart(),
                dtBinarySearch.toSecondsPart(), dtBinarySearch.toMillisPart());

        //Hash map search
        System.out.println("Start searching (hash table)...");
        Instant startHashMapSearch = Instant.now();
        ArrayDeque<String> requests = new ArrayDeque<>(requestedData);
        int entriesFifth = 0;
        while (!requests.isEmpty()) {
            if (baseHash.containsKey(requests.pollFirst())) {
                ++entriesFifth;
            }
        }
        Instant endHashMapSearch = Instant.now();
        Duration dtHashMapSearch = Duration.between(startHashMapSearch, endHashMapSearch);
        Duration dtGeneralThree = dtFillingHashMap.plusMillis(dtHashMapSearch.toMillis());

        System.out.printf("Found %s/%s entries. Time taken: %d min. %d sec. %d ms.\n",
                entriesFifth, requestedData.size(), dtGeneralThree.toMinutesPart(), dtGeneralThree.toSecondsPart(),
                dtGeneralThree.toMillisPart());
        System.out.printf("Creating time: %d min. %d sec. %d ms.\n", dtFillingHashMap.toMinutesPart(),
                dtFillingHashMap.toSecondsPart(), dtFillingHashMap.toMillisPart());
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n", dtHashMapSearch.toMinutesPart(),
                dtHashMapSearch.toSecondsPart(), dtHashMapSearch.toMillisPart());


    }


}
