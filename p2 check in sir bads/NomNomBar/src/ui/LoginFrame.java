package ui;

import logic.Style;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();

    public LoginFrame() {
        Style.base(this, "NomNom Bar", 1050, 650);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Style.BG);

        JPanel hero = new JPanel(new GridBagLayout());
        hero.setBackground(new Color(8, 22, 34));
        JLabel logo = Style.label("🍸 NOMNOM", 42, true);
        JLabel sub = Style.label("O R D E R I N G   S Y S T E M", 13, true);
        JPanel branding = new JPanel();
        branding.setOpaque(false);
        branding.setLayout(new BoxLayout(branding, BoxLayout.Y_AXIS));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        branding.add(logo);
        branding.add(Box.createVerticalStrut(8));
        branding.add(sub);
        hero.add(branding);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Style.PANEL);
        form.setBorder(new EmptyBorder(35, 55, 35, 55));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(7, 0, 7, 0);
        g.weightx = 1;

        JLabel welcome = Style.label("Welcome!", 28, true);
        JLabel hint = Style.label("Please login to continue", 14, false);
        g.gridy=0; form.add(welcome,g); g.gridy=1; form.add(hint,g);

        username.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(55,80,102)), "Username"));
        password.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(55,80,102)), "Password"));
        username.setBackground(Style.PANEL2); username.setForeground(Style.TEXT);
        password.setBackground(Style.PANEL2); password.setForeground(Style.TEXT);
        username.setCaretColor(Style.GOLD); password.setCaretColor(Style.GOLD);
        g.gridy=2; form.add(username,g); g.gridy=3; form.add(password,g);

        JButton customer = Style.button("Customer Login", true);
        JButton admin = Style.button("Admin Login", false);
        g.gridy=4; form.add(customer,g); g.gridy=5; form.add(admin,g);

        customer.addActionListener(e -> login(false));
        admin.addActionListener(e -> login(true));

        root.add(hero, BorderLayout.CENTER);
        root.add(form, BorderLayout.EAST);
        form.setPreferredSize(new Dimension(430, 0));
        setContentPane(root);
    }

    private void login(boolean admin) {
        String u = username.getText().trim();
        String p = new String(password.getPassword());
        if (admin) {
            if (u.equalsIgnoreCase("admin") && p.equals("admin123")) {
                dispose(); new AdminDashboardFrame().setVisible(true);
            } else JOptionPane.showMessageDialog(this, "Demo admin: admin / admin123", "Login", JOptionPane.WARNING_MESSAGE);
        } else {
            if (!u.isEmpty() && !p.isEmpty()) {
                dispose(); new CustomerFrame(u).setVisible(true);
            } else JOptionPane.showMessageDialog(this, "Enter a username and password.", "Login", JOptionPane.WARNING_MESSAGE);
        }
    }
}
