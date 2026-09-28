package logic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Style {
    public static final Color BG = new Color(5, 17, 28);
    public static final Color PANEL = new Color(10, 28, 43);
    public static final Color PANEL2 = new Color(14, 36, 54);
    public static final Color GOLD = new Color(255, 193, 55);
    public static final Color TEXT = new Color(242, 246, 249);
    public static final Color MUTED = new Color(158, 174, 187);
    public static final Color RED = new Color(255, 68, 82);
    public static final Color GREEN = new Color(55, 210, 130);

    public static JButton button(String text, boolean primary) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(primary ? Color.BLACK : TEXT);
        b.setBackground(primary ? GOLD : PANEL2);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        return b;
    }

    public static JLabel label(String text, int size, boolean bold) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT);
        l.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, size));
        return l;
    }

    public static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(PANEL);
        p.setBorder(new EmptyBorder(14, 14, 14, 14));
        return p;
    }

    public static void base(JFrame f, String title, int w, int h) {
        f.setTitle(title);
        f.setSize(w, h);
        f.setMinimumSize(new Dimension(900, 600));
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(BG);
    }
}
