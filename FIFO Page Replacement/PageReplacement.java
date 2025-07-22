package pagerep;

import java.util.LinkedList;
import java.util.Queue;

public class PageReplacement {
    private Queue<Integer> frames;
    private int capacity;
    private int pageFaults;

    public PageReplacement(int capacity) {
        this.capacity = capacity;
        this.frames = new LinkedList<>();
        this.pageFaults = 0;
    }

    public void processPages(int[] pages) {
        int step = 1; 
        for (int page : pages) {
     
            if (!frames.contains(page)) {
                pageFaults++; 
                if (frames.size() == capacity) {
                    frames.poll();
                }
                frames.add(page); 
            }
            System.out.println("Step " + step + ": " + frames + (frames.contains(page) ? "" : " (Page fault)"));
            step++;
        }
    }

    public int getPageFaults() {
        return pageFaults;
    }

    public void printFinalState() {
        System.out.println("Final state of frames: " + frames);
    }
}

