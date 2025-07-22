package optimal;

import java.util.*;

public class OptimalPageReplacement {

    private static int pageFaults = 0;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter page requests (comma-separated): ");
            String[] input = scanner.nextLine().split(",");
            List<Integer> pages = new ArrayList<>();
            for (String page : input) {
                pages.add(Integer.parseInt(page.trim()));
            }

            System.out.print("Enter number of frames (1-3): ");
            int frameCount = Integer.parseInt(scanner.nextLine().trim());

            if (frameCount < 1 || frameCount > 3) {
                System.out.println("Number of frames must be between 1 and 3.");
                return;
            }

            List<Integer> finalFrames = optimalPageReplacement(pages, frameCount);

            System.out.println("Final state of frames: " + finalFrames);
            System.out.println("Total page faults: " + pageFaults);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> optimalPageReplacement(List<Integer> pages, int frameCount) {
        List<Integer> frames = new ArrayList<>();

        for (int step = 0; step < pages.size(); step++) {
            int page = pages.get(step);
            boolean pageFaultOccurred = false;

            if (!frames.contains(page)) {
                pageFaults++;
                pageFaultOccurred = true;

                if (frames.size() < frameCount) {
                    frames.add(page);
                } else {
                    int pageToReplace = findPageToReplace(frames, pages, step);
                    frames.set(frames.indexOf(pageToReplace), page);
                }
            }

            System.out.print("Step " + (step + 1) + ": " + frames);
            if (pageFaultOccurred) {
                System.out.println(" - Page fault");
            } else {
                System.out.println();
            }
        }

        return frames;
    }

    private static int findPageToReplace(List<Integer> frames, List<Integer> pages, int currentIndex) {
        int farthestIndex = -1;
        int pageToReplace = frames.get(0);

        for (int frame : frames) {
            int nextUseIndex = findNextUseCyclic(pages, currentIndex + 1, frame);

            if (nextUseIndex > farthestIndex) {
                farthestIndex = nextUseIndex;
                pageToReplace = frame;
            }
        }

        return pageToReplace;
    }

    private static int findNextUseCyclic(List<Integer> pages, int startIndex, int page) {
        int size = pages.size();
        for (int i = startIndex; i < startIndex + size; i++) {
            int index = i % size;
            if (pages.get(index) == page) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }
}
