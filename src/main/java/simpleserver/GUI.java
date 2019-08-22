package simpleserver;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private static final int WIDTH = 650;
    private static final int HEIGHT = 450;
    private static final Font mainFont = new Font("Consolas", Font.PLAIN, 12);

    private JFrame frm;
    private JTextArea outputArea;

    public GUI() {
        frm = new JFrame("SimpleServer");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(WIDTH, HEIGHT);
        int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2;
        int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2;
        frm.setLocation(xPos, yPos);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        outputArea = new JTextArea();
        outputArea.setFont(mainFont);
        outputArea.setEditable(false);

        contentPane.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        frm.setContentPane(contentPane);
        frm.setVisible(true);
    }

    public void println(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                outputArea.append(text + "\n");
            }
        });
    }

    public void println() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                outputArea.append("\n");
            }
        });
    }

    public void print(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                outputArea.append(text);
            }
        });
    }

}
