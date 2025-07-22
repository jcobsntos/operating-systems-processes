package lru;

import java.util.*;

public class LRUPageReplacement {

    private static int pageFaults = 0;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter page requests (comma-separated): ");
            String[] input = scanner.nextLine().split(",");
            List<Integer> pages = new ArrayList<>();
            for (String page : input) {
                pages.add(Integer.parseInt(page.trim()));
            }

            System.out.print("Enter number of frames (1-5): ");
            int frameCount = Integer.parseInt(scanner.nextLine().trim());

            if (frameCount < 1 || frameCount > 5) {
                System.out.println("Number of frames must be between 1 and 3.");
                return;
            }

            List<Integer> finalFrames = lruPageReplacement(pages, frameCount);

            System.out.println("Final state of frames: " + finalFrames);
            System.out.println("Total page faults: " + pageFaults);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> lruPageReplacement(List<Integer> pages, int frameCount) {
        List<Integer> frames = new ArrayList<>();
        Map<Integer, Integer> pageIndexMap = new HashMap<>();

        for (int step = 0; step < pages.size(); step++) {
            int page = pages.get(step);
            boolean pageFaultOccurred = false;

            if (!frames.contains(page)) {
                pageFaults++;
                pageFaultOccurred = true;

                if (frames.size() < frameCount) {
                    frames.add(page);
                } else {
                    int pageToReplace = findLRUPage(frames, pageIndexMap);
                    frames.set(frames.indexOf(pageToReplace), page);
                }
            }

           
            pageIndexMap.put(page, step);

            System.out.print("Step " + (step + 1) + ": " + frames);
            if (pageFaultOccurred) {
                System.out.println(" - Page fault");
            } else {
                System.out.println();
            }
        }

        return frames;
    }

    private static int findLRUPage(List<Integer> frames, Map<Integer, Integer> pageIndexMap) {
        int lruPage = frames.get(0);
        int minIndex = Integer.MAX_VALUE;

        for (int page : frames) {
            int lastUsedIndex = pageIndexMap.getOrDefault(page, -1);
            if (lastUsedIndex < minIndex) {
                minIndex = lastUsedIndex;
                lruPage = page;
            }
        }

        return lruPage;
    }
}
