package ss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.*;

import java.util.*;
 
public class GUI {
    JFrame frame = new JFrame();
    JTextField textField = new JTextField();
    JButton button = new JButton("submit");

    public GUI() {
        frame.setTitle("OS-1");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        button.addActionListener(new ButtonClickListener());
        textField.setPreferredSize(new Dimension(100, 30));

        frame.add(new JLabel("Enter Number of processes you want to serve: "));
        frame.add(textField);
        frame.add(button);

        frame.setVisible(true);
    }

    class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int size = Integer.parseInt(textField.getText());
                processInput(size);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number of processes.");
            }
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
    public  void processInput(int size) {
        ArrayList<pro> arr = new ArrayList<>();
        ArrayList<pro> arr2 = new ArrayList<>();
        Main2 obj=new Main2();
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            int at = Integer.parseInt(JOptionPane.showInputDialog("Enter Arrival Time for Process " + (i + 1) + ":"));
            int bt = Integer.parseInt(JOptionPane.showInputDialog("Enter Burst Time for Process " + (i + 1) + ":"));
            a[i]=at;
            arr.add(new pro(i, at, bt));
            arr2.add(new pro(i, at, bt));
        }
        obj.func(arr,arr2,a,size);

    }
}