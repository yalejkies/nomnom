package ui;

import data.AppData;
import logic.QueueManager;
import logic.Style;
import model.Order;
import model.Product;
import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {
    private final JPanel content=new JPanel(new BorderLayout());

    public AdminDashboardFrame(){
        Style.base(this,"NomNom Bar - Admin",1200,740);
        showDashboard();
    }

    private void setup(){
        JPanel root=new JPanel(new BorderLayout());root.setBackground(Style.BG);
        root.add(new Sidebar("Admin",this::navigate),BorderLayout.WEST);
        content.setBackground(Style.BG);content.removeAll();root.add(content,BorderLayout.CENTER);
        setContentPane(root);content.revalidate();content.repaint();
    }

    private void navigate(String s){
        if(s.equals("Dashboard"))showDashboard();
        else if(s.equals("Orders"))showOrders();
        else if(s.equals("Queue"))showQueue();
        else if(s.equals("Stock"))showStock();
        else if(s.equals("Reports"))showOrders();
    }

    private void showDashboard(){
        setup();
        JPanel p=new JPanel(new BorderLayout(0,18));p.setBackground(Style.BG);p.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        p.add(Style.label("Admin Dashboard",30,true),BorderLayout.NORTH);
        JPanel cards=new JPanel(new GridLayout(1,3,14,0));cards.setOpaque(false);
        cards.add(stat("Total Orders",String.valueOf(AppData.ORDERS.size())));
        cards.add(stat("In Queue",String.valueOf(AppData.ORDER_QUEUE.size())));
        long low=AppData.PRODUCTS.stream().filter(x->x.getStock()<=2).count();
        cards.add(stat("Stock Low",String.valueOf(low)));
        JPanel center=new JPanel(new BorderLayout(0,12));center.setOpaque(false);
        center.add(cards,BorderLayout.NORTH);
        center.add(recentTable(),BorderLayout.CENTER);
        p.add(center,BorderLayout.CENTER);content.add(p);
    }

    private JPanel stat(String a,String b){
        JPanel c=Style.card();c.setLayout(new BorderLayout());
        c.add(Style.label(a,13,false),BorderLayout.NORTH);c.add(Style.label(b,30,true),BorderLayout.CENTER);
        return c;
    }

    private JScrollPane recentTable(){
        String[] cols={"Order #","Customer","Items","Status","Time"};
        Object[][] rows=AppData.ORDERS.stream().map(o->new Object[]{
            String.format("%03d",o.getOrderNumber()),o.getCustomer(),o.getItemsSummary(),o.getStatus(),o.getTime()
        }).toArray(Object[][]::new);
        JTable t=new JTable(rows,cols);t.setRowHeight(32);t.setBackground(Style.PANEL);t.setForeground(Style.TEXT);t.setGridColor(Style.PANEL2);
        return new JScrollPane(t);
    }

    private void showOrders(){
        setup();
        JPanel p=new JPanel(new BorderLayout(0,12));p.setBackground(Style.BG);p.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        p.add(Style.label("Orders",28,true),BorderLayout.NORTH);p.add(recentTable(),BorderLayout.CENTER);content.add(p);
    }

    private void showQueue(){
        setup();
        JPanel p=new JPanel(new BorderLayout(0,12));p.setBackground(Style.BG);p.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        p.add(Style.label("Order Queue",28,true),BorderLayout.NORTH);
        String[] cols={"Order #","Customer","Items","Status","Action"};
        Object[][] rows=AppData.ORDER_QUEUE.stream().map(o->new Object[]{String.format("%03d",o.getOrderNumber()),o.getCustomer(),o.getItemsSummary(),o.getStatus(),"In Queue"}).toArray(Object[][]::new);
        JTable t=new JTable(rows,cols);t.setRowHeight(34);t.setBackground(Style.PANEL);t.setForeground(Style.TEXT);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.RIGHT));actions.setOpaque(false);
        JButton start=Style.button("Start Next",true), complete=Style.button("Complete Current",false);
        start.addActionListener(e->{QueueManager.startNext();showQueue();});
        complete.addActionListener(e->{QueueManager.completeCurrent();showQueue();});
        actions.add(start);actions.add(complete);
        p.add(new JScrollPane(t),BorderLayout.CENTER);p.add(actions,BorderLayout.SOUTH);content.add(p);
    }

    private void showStock(){
        setup();
        JPanel p=new JPanel(new BorderLayout(0,12));p.setBackground(Style.BG);p.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        p.add(Style.label("Stock Management",28,true),BorderLayout.NORTH);
        String[] cols={"Item","Category","Current Stock","Status","Action"};
        Object[][] rows=AppData.PRODUCTS.stream().map(x->new Object[]{x.getName(),x.getCategory(),x.getStock(),x.getStockStatus(),"Update"}).toArray(Object[][]::new);
        JTable t=new JTable(rows,cols);t.setRowHeight(34);t.setBackground(Style.PANEL);t.setForeground(Style.TEXT);
        JButton update=Style.button("Update Selected Stock",true);
        update.addActionListener(e->{
            int r=t.getSelectedRow();
            if(r<0){JOptionPane.showMessageDialog(this,"Select an item first.");return;}
            Product prod=AppData.PRODUCTS.get(r);
            String input=JOptionPane.showInputDialog(this,"New stock for "+prod.getName()+":",prod.getStock());
            try{prod.setStock(Integer.parseInt(input));showStock();}catch(Exception ex){JOptionPane.showMessageDialog(this,"Enter a valid number.");}
        });
        p.add(new JScrollPane(t),BorderLayout.CENTER);p.add(update,BorderLayout.SOUTH);content.add(p);
    }
}
