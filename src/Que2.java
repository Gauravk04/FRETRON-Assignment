import java.util.*;

public class Que2 {
    public static void main(String[] args) {
        distributeApples();
    }

    public static void distributeApples() {
        Scanner s = new Scanner(System.in);
        ArrayList<Integer> appleWeights = new ArrayList<>();

        // Collect apple weights from user input
        while (true) {
            System.out.print("Enter apple weight in gram (-1 to stop) : ");
            int val = s.nextInt();
            if (val == -1) {
                break;
            }
            appleWeights.add(val);
        }

        // Total amount paid by each person
        int tp = 100;
        int rp = 50;
        int sp = 30;
        int rhp = 20;

        // Total weight of apples
        int tw = appleWeights.stream().mapToInt(Integer::intValue).sum();

        // Maximum share of each person
        int rshare = rp * tw / tp;
        int sshare = sp * tw / tp;
        int rhshare = rhp * tw / tp;

        // Initialize sums and lists for each person
        int rsum = 0, ssum = 0, rhsum = 0;
        ArrayList<Integer> rl = new ArrayList<>();
        ArrayList<Integer> sl = new ArrayList<>();
        ArrayList<Integer> rhl = new ArrayList<>();

        // Sort apple weights in descending order
        Collections.sort(appleWeights, Collections.reverseOrder());

        // Distribute apples based on their weights
        for (int appleWeight : appleWeights) {
            if (rsum + appleWeight <= rshare) {
                rsum += appleWeight;
                rl.add(appleWeight);
            } else if (ssum + appleWeight <= sshare) {
                ssum += appleWeight;
                sl.add(appleWeight);
            } else if (rhsum + appleWeight <= rhshare) {
                rhsum += appleWeight;
                rhl.add(appleWeight);
            }
        }

        // Print the output
        System.out.println("Distribution Result :");

        // Print Ram's apples
        System.out.print("Ram : ");
        printAppleWeights(rl);

        // Print Sham's apples
        System.out.print("Sham : ");
        printAppleWeights(sl);

        // Print Rahim's apples
        System.out.print("Rahim : ");
        printAppleWeights(rhl);
    }

    private static void printAppleWeights(List<Integer> appleList) {
        if (appleList.isEmpty()) {
            System.out.println("No apples allocated.");
        } else {
            for (int i = 0; i < appleList.size(); i++) {
                System.out.print(appleList.get(i));
                if (i < appleList.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}
