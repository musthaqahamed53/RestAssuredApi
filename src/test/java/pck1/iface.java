package pck1;



class iface1 implements iifcInter {


    @Override
    public void pay(double amount) {
        System.out.println("asdfghn");
    }

    public static void main(String[] args) {
        iface1 if1 = new iface1();
        if1.pay(23.11);
    }
}
