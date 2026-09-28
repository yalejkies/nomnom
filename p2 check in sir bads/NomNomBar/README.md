# NOMNOM BAR — NetBeans Java Swing Ordering System

Open in NetBeans: File > Open Project > select the NomNomBar folder (the folder containing nbproject) > Run.

Admin demo: username `admin`, password `admin123`.
Customer demo: any non-empty username/password.

Flow:
LoginFrame -> CustomerFrame -> Order/Cart/My Orders/Profile -> AppData.createOrder() -> FIFO Queue -> AdminDashboardFrame -> Queue/Stock/Reports.

Data structures:
- ArrayList<Product> = products/stock
- ArrayList<Order> = all orders
- Queue<Order> using LinkedList = FIFO queue
- ArrayList<OrderItem> = cart

No SQL, MySQL, or external database is required.
