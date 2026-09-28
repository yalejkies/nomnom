package ui;

import logic.CartManager;
import logic.Style;
import model.Order;
import model.OrderItem;
import data.AppData;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerFrame extends JFrame {
    private final String customer;
    private final CartManager cart = new CartManager();
    private final JPanel content = new JPanel(new BorderLayout());

    public CustomerFrame(String customer) {
        this.customer = customer;
        Style.base(this, "NomNom Bar - Customer", 1250, 760);
        showHome();
    }

    private void setup(String title) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Style.BG);
        root.add(new Sidebar("Customer", this::navigate), BorderLayout.WEST);
        content.setBackground(Style.BG);
        content.removeAll();
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);
        content.revalidate(); content.repaint();
    }

    private void navigate(String s) {
        if (s.equals("Home")) showHome();
        else if (s.equals("Order")) showOrder();
        else if (s.equals("My Orders")) showOrders();
        else if (s.equals("Profile")) showProfile();
    }

    private void showHome() {
        setup("Home");
        JPanel p = new JPanel(new BorderLayout(0,18));
        p.setBackground(Style.BG);
        p.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(Style.label("Welcome, " + customer + "!", 30, true), BorderLayout.WEST);
        JButton cartBtn = Style.button("🛒 Cart (" + cart.getCount() + ")", false);
        cartBtn.addActionListener(e -> showOrder());
        top.add(cartBtn, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);

        JPanel hero = Style.card();
        hero.setLayout(new BorderLayout());
        JLabel h = Style.label("<html><div style='font-size:22px'>Your Next Drink<br>Is Just a Click Away</div></html>",22,true);
        hero.add(h, BorderLayout.CENTER);
        JButton order = Style.button("Order Now", true);
        order.addActionListener(e -> showOrder());
        hero.add(order, BorderLayout.SOUTH);
        p.add(hero, BorderLayout.CENTER);

        JPanel featured = new JPanel(new GridLayout(1,4,12,0));
        featured.setOpaque(false);
        for (int i=0;i<4;i++) featured.add(productMini(AppData.PRODUCTS.get(i)));
        p.add(featured, BorderLayout.SOUTH);
        content.add(p);
    }

    private JPanel productMini(model.Product product) {
        JPanel c = Style.card();
        c.setLayout(new BorderLayout(5,5));
        JLabel photo = new JLabel(product.getName().equals("Mojito") ? "🍸" :
                product.getName().equals("Blue Lagoon") ? "🍹" :
                product.getName().equals("Margarita") ? "🍸" : "☕", SwingConstants.CENTER);
        photo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        c.add(photo,BorderLayout.CENTER);
        c.add(Style.label(product.getName()+"  ₱"+(int)product.getPrice(),13,true),BorderLayout.SOUTH);
        return c;
    }

    private void showOrder() {
        setup("Order");
        JPanel main = new JPanel(new BorderLayout(15,15));
        main.setBackground(Style.BG);
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel left = new JPanel(new BorderLayout(0,12)); left.setOpaque(false);
        JPanel cats = new JPanel(new FlowLayout(FlowLayout.LEFT)); cats.setOpaque(false);
        String[] categories = {"All","Cocktails","Beer","Non-Alcoholic","Shots"};
        for(String c: categories) {
            JButton b=Style.button(c,c.equals("All"));
            b.addActionListener(e -> renderProducts(left, c));
            cats.add(b);
        }
        left.add(cats,BorderLayout.NORTH);
        renderProducts(left,"All");

        JPanel cartPanel = Style.card();
        cartPanel.setPreferredSize(new Dimension(310,0));
        cartPanel.setLayout(new BorderLayout(8,8));
        cartPanel.add(Style.label("Your Cart",20,true),BorderLayout.NORTH);

        JPanel cartList = new JPanel();
        cartList.setOpaque(false);
        cartList.setLayout(new BoxLayout(cartList,BoxLayout.Y_AXIS));
        for(OrderItem item: cart.getItems()) {
            JPanel row=new JPanel(new BorderLayout(5,5)); row.setOpaque(false);
            row.add(Style.label(item.getProduct().getName()+" x"+item.getQuantity(),12,false),BorderLayout.CENTER);
            JButton minus=Style.button("−",false), plus=Style.button("+",false);
            JPanel bp=new JPanel(new FlowLayout(FlowLayout.RIGHT,2,0)); bp.setOpaque(false);
            minus.addActionListener(e->{cart.changeQuantity(item.getProduct(),-1);showOrder();});
            plus.addActionListener(e->{cart.changeQuantity(item.getProduct(),1);showOrder();});
            bp.add(minus);bp.add(plus);row.add(bp,BorderLayout.EAST);
            cartList.add(row);cartList.add(Box.createVerticalStrut(7));
        }
        cartPanel.add(new JScrollPane(cartList),BorderLayout.CENTER);
        JPanel bottom=new JPanel(new BorderLayout(5,5)); bottom.setOpaque(false);
        bottom.add(Style.label("Total: ₱"+String.format("%.0f",cart.getTotal()),17,true),BorderLayout.NORTH);
        JButton place=Style.button("Place Order",true);
        place.addActionListener(e->placeOrder());
        bottom.add(place,BorderLayout.SOUTH);
        cartPanel.add(bottom,BorderLayout.SOUTH);

        main.add(left,BorderLayout.CENTER); main.add(cartPanel,BorderLayout.EAST);
        content.add(main);
    }

    private void renderProducts(JPanel left, String category) {
        if (left.getComponentCount()>1) left.remove(1);
        JPanel grid=new JPanel(new GridLayout(0,4,12,12)); grid.setOpaque(false);
        for(model.Product p:AppData.PRODUCTS) {
            if(!category.equals("All") && !p.getCategory().equals(category)) continue;
            JPanel card=Style.card(); card.setLayout(new BorderLayout(6,6));
            JLabel emoji=new JLabel(p.getName().contains("Coffee")?"☕":"🍹",SwingConstants.CENTER);
            emoji.setFont(new Font("Segoe UI Emoji",Font.PLAIN,45));
            card.add(emoji,BorderLayout.CENTER);
            JPanel info=new JPanel(new BorderLayout());info.setOpaque(false);
            info.add(Style.label(p.getName(),13,true),BorderLayout.NORTH);
            info.add(Style.label("₱"+(int)p.getPrice()+"  Stock: "+p.getStock(),11,false),BorderLayout.CENTER);
            JButton add=Style.button("Add to Cart",true); add.setEnabled(p.getStock()>0);
            add.addActionListener(e->{cart.add(p);showOrder();});
            info.add(add,BorderLayout.SOUTH); card.add(info,BorderLayout.SOUTH);
            grid.add(card);
        }
        left.add(new JScrollPane(grid),BorderLayout.CENTER);
        left.revalidate();left.repaint();
    }

    private void placeOrder() {
        if(cart.getItems().isEmpty()){JOptionPane.showMessageDialog(this,"Your cart is empty.");return;}
        List<OrderItem> copy=cart.getItems();
        Order o=AppData.createOrder(customer,copy);
        if(o==null){JOptionPane.showMessageDialog(this,"Not enough stock for one or more items.");return;}
        cart.clear();
        JOptionPane.showMessageDialog(this,"Order #"+String.format("%03d",o.getOrderNumber())+" placed!\nAdded to FIFO queue.");
        showOrders();
    }

    private void showOrders() {
        setup("My Orders");
        JPanel p=new JPanel(new BorderLayout(0,15));p.setBackground(Style.BG);p.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        p.add(Style.label("My Orders",28,true),BorderLayout.NORTH);
        String[] cols={"Order #","Items","Total","Status","Time"};
        Object[][] rows=AppData.ORDERS.stream().filter(o->o.getCustomer().equals(customer)).map(o->new Object[]{
            String.format("%03d",o.getOrderNumber()),o.getItemsSummary(),"₱"+(int)o.getTotal(),o.getStatus(),o.getTime()
        }).toArray(Object[][]::new);
        JTable table=new JTable(rows,cols); table.setRowHeight(35); table.setBackground(Style.PANEL);table.setForeground(Style.TEXT);table.setGridColor(Style.PANEL2);
        p.add(new JScrollPane(table),BorderLayout.CENTER);content.add(p);
    }

    private void showProfile() {
        setup("Profile");
        JPanel p=Style.card();p.setLayout(new GridBagLayout());
        GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(10,10,10,10);g.anchor=GridBagConstraints.WEST;
        g.gridy=0;p.add(Style.label("Profile",28,true),g);g.gridy++;
        p.add(Style.label("Username: "+customer,16,false),g);
        content.add(p,BorderLayout.CENTER);
    }
}
