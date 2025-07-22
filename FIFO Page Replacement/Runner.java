package pagerep;

import java.util.Scanner;

public class Runner {
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter page requests (comma-separated): ");
        String input = scanner.nextLine();
        String[] inputPages = input.split(",");
        int[] pages = new int[inputPages.length];
        for (int i = 0; i < inputPages.length; i++) {
            pages[i] = Integer.parseInt(inputPages[i].trim());
        }

        System.out.print("Enter number of frames (3-5): ");
        int frames = scanner.nextInt();

        if (frames < 3 || frames > 5) {
            System.out.println("Frame size must be between 3 and 5.");
            return;
        }

        PageReplacement pageReplacement = new PageReplacement(frames);
        
        pageReplacement.processPages(pages);
        System.out.println("Total page faults: " + pageReplacement.getPageFaults());
        pageReplacement.printFinalState();
        scanner.close();
    }
}


