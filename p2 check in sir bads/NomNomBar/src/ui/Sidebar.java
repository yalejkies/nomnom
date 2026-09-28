package ui;

import logic.Style;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class Sidebar extends JPanel {
    public Sidebar(String title, Consumer<String> action) {
        setBackground(new Color(5, 18, 29));
        setPreferredSize(new Dimension(190, 0));
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(22, 16, 10, 16));
        JLabel logo = Style.label("🍸 NOMNOM", 25, true);
        JLabel small = Style.label("ORDERING SYSTEM", 9, true);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        small.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(logo); top.add(small);
        top.add(Box.createVerticalStrut(25));

        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        String[] names = title.equals("Admin") ?
                new String[]{"Dashboard","Orders","Queue","Stock","Reports"} :
                new String[]{"Home","Order","My Orders","Profile"};
        for (String n : names) {
            JButton b = Style.button("  " + n, false);
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            b.addActionListener(e -> action.accept(n));
            nav.add(b);
            nav.add(Box.createVerticalStrut(5));
        }
        top.add(nav);
        add(top, BorderLayout.NORTH);

        JButton logout = Style.button("↪  Logout", false);
        logout.setForeground(Style.RED);
        logout.addActionListener(e -> {
            Window w = SwingUtilities.getWindowAncestor(this);
            w.dispose();
            new LoginFrame().setVisible(true);
        });
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(10,16,18,16));
        bottom.add(logout, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
