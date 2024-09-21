package ss;

import javax.swing.*;
import java.awt.*;
import java.util.*;
class pro {
    int at;
    int bt;
    int id;

    public pro(int id, int arrivalTime, int BurstTime) {
        this.id = id;
        this.at = arrivalTime;
        this.bt = BurstTime;
    }

    public int get_At() {
        return this.at;
    }

    public int get_Bt() {
        return this.bt;
    }

    public int get_id() {
        return this.id;
    }
}
public class Main2 {
    private JFrame frame;
    private JTextArea resultArea;
    private JPanel ganttChartPanel;
    ArrayList<pro> garr;
    public Main2(){
        resultArea = new JTextArea(10, 30); // Create text area for scheduling result
        resultArea.setEditable(false); // Make text area read-only
        frame=new JFrame();
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER); // Add text area to the center of the frame
        frame.setPreferredSize(new Dimension(1000,600));
        frame.setSize(400, 300);

        // Make the frame resizable
        frame.setResizable(true);
        this.frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        this.frame.pack(); // Pack components in the frame
        this.frame.setLocationRelativeTo(null); // Center the frame on the screen
        this.frame.setVisible(true); // Make the frame visible//
    }
    public  void func(ArrayList<pro> arr,ArrayList<pro> arr2,int a[],int size) {
        garr=new ArrayList<>(arr);;
        int c = 0;
        for (int i = 0; i < size - 1; i++) {
            if (a[i] == a[i + 1]) {
                c++;
            }
        }

        if (c == size - 1) {
            Collections.sort(arr, Comparator.comparing(p -> p.get_Bt()));
        } else {
            Collections.sort(arr, Comparator.comparing(p -> p.get_At()));
        }

        int[][] ct = new int[2][size];
        pro temp1 = arr.getFirst();

        ct[0][0] = temp1.get_id();
        ct[1][0] = temp1.get_Bt();
        int min , y = 0;
        int flag;
        for (int i = 1; i < size; i++) {
            flag = 0;
            min = arr.get(i).get_Bt();
            for (int j = i; j < size; j++) {
                if (ct[1][i - 1] > arr.get(j).get_At()) {
                    if (min > arr.get(j).get_Bt()) {
                        min = arr.get(j).get_Bt();
                        y = j;
                        flag = 1;
                    }
                }
            }
            if (flag == 1) {
                Collections.swap(arr, i, y);
            }
            ct[0][i] = arr.get(i).get_id();
            ct[1][i] = min + ct[1][i - 1];
        }

        drawGanttChart(size,ct);
        bubbleSort(ct, size);

        int[] TAT = new int[size];
        int[] WT = new int[size];
        int wt = 0, tat = 0;
        for (int i = 0; i < size; i++) {
            TAT[i] = ct[1][i] - arr2.get(i).get_At();
            if(TAT[i]<0){
                TAT[i]=0;
            }
            WT[i] = TAT[i] - arr2.get(i).get_Bt();
            if(WT[i]<0){
                WT[i]=0;
            }
            wt += WT[i];
            tat += TAT[i];
        }
        StringBuilder resultBuilder = new StringBuilder();

        resultBuilder.append("Process ID | Burst Time | Arrival Time | Waiting Time | Turnaround Time | Response Time | Completion Time\n");
        for (int i = 0; i < size; i++) {
            resultBuilder.append("P").append((i + 1)).append(" \t| ")
                    .append(arr2.get(i).get_Bt()).append("\t|")
                    .append(arr2.get(i).get_At()).append(" \t| ")
                    .append(WT[i]).append(" \t| ")
                    .append(TAT[i]).append("\t| ")
                    .append(WT[i]).append("\t\t| ")
                    .append(ct[1][i]).append("\t\n");//completion time
        }

        double avewt = (double) wt / size;
        double avetat = (double) tat / size;

        resultBuilder.append("\nAverage Waiting Time: ").append(avewt)
                .append("\nAverage Turnaround Time: ").append(avetat)
                .append("\nAverage Response Time: ").append(avewt);
        this.resultArea.setText(resultBuilder.toString()); // Set the result text in the text area

    }

    public void drawGanttChart(int size, int[][] ct) {

        ganttChartPanel = new JPanel(); // Create panel for Gantt chart
        frame.add(ganttChartPanel, BorderLayout.SOUTH); // Add Gantt chart panel to the bottom of the frame

        frame.pack(); // Pack components in the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible

        int totalBurstTime = 0; // Initialize total burst time
        for (pro process : garr) {
            totalBurstTime += process.get_Bt(); // Calculate total burst time
        }

        int scale = ganttChartPanel.getWidth() / totalBurstTime; // Calculate scale for Gantt chart
        for (int i = 0; i < size; i++) {
            JPanel processPanel = new JPanel(); // Create panel for each process
            processPanel.setLayout(new BorderLayout()); // Set layout for process panel
            processPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border to process panel

            JLabel processLabel = new JLabel("P" + (ct[0][i]+1)); // Adjusted label creation
            processLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the label
            processPanel.add(processLabel, BorderLayout.CENTER); // Add label to process panel

            // Adjusted width calculation using burst time and scale
            int width = (ct[1][i] - (i > 0 ? ct[1][i - 1] : 0)) * scale; // Width is the difference in burst time
            processPanel.setPreferredSize(new Dimension(width, 50)); // Set size of process panel

            ganttChartPanel.add(processPanel); // Add process panel to Gantt chart panel
        }
        ganttChartPanel.revalidate();
        ganttChartPanel.repaint();
    }


    public static void bubbleSort(int[][] ct, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ct[0][j] > ct[0][j + 1]) {
                    // Swap elements
                    int temp1 = ct[0][j];
                    ct[0][j] = ct[0][j + 1];
                    ct[0][j + 1] = temp1;
                    int temp2 = ct[1][j];
                    ct[1][j] = ct[1][j + 1];
                    ct[1][j + 1] = temp2;
                }
            }
        }
    }
}
