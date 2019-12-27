import java.util.*;

class Bank {
  static LinkedHashMap<Integer, LinkedHashMap<String, Integer>> idBalanceMap = new LinkedHashMap<>();

  String SUCCESS = "\nThe request is succesful!";
  String FAIL = "\nThe request is not succesful!";

  String welcomeMESSAGE = "\nWelcome to Somraj Payments Bank!";
  String byeMESSAGE = "\nHave a good day!";

  String sessionSTART = "\nSession Started!";
  String sessionCLOSE = "\nLogged Out!\nSession Closed!";

  float rate = 3.5;
  int sum = 100000;

  int accountCapacity = 100000;

}

class Services extends Bank {

  Scanner sc = new Scanner(System.in);

  public void updateDetails(String name, int id, int balance) {
    LinkedHashMap<String, Integer> nameBalanceMap = new LinkedHashMap<>();
    nameBalanceMap.put(name, balance);
    idBalanceMap.put(id, nameBalanceMap);
  }

  public int getBalance(int id) {

    int balance = 0;
    for (Map.Entry<String, Integer> entry : idBalanceMap.get(id).entrySet()) {
      balance = entry.getValue();
    }
    return balance;
  }

  public String getName(int id) {
    String name = "";
    for (Map.Entry<String, Integer> entry : idBalanceMap.get(id).entrySet()) {
      name = entry.getKey();
    }
    return name;
  }

  public void displayBalance(int id) {

    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
      System.out.println("\nAccount Balance of " + id + " : " + getBalance(id));
    } else {
      System.out.println(FAIL);
    }

  }

  public void withdrawCash(int id) {
    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
      System.out.println("\nEnter the amount you want to withdraw: ");
      int amount = sc.nextInt();

      if (hasBalance(id, amount)) {
        int avail = getBalance(id);
        String name = getName(id);
        int balance = avail - amount;
        updateDetails(name, id, balance);
        System.out.println(SUCCESS);
      } else {
        System.out.println(FAIL);
      }
    } else {
      System.out.println(FAIL);
    }
  }

  public void depositCash(int id) {
    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
      System.out.println("\nEnter the amount you want to deposit: ");
      int amount = sc.nextInt();
      int avail = getBalance(id);
      String name = getName(id);
      int balance = avail + amount;
      updateDetails(name, id, balance);
      System.out.println(SUCCESS);
    } else {
      System.out.println(FAIL);
    }
  }

  public boolean hasBalance(int id, int amount) {
    int avail = getBalance(id);
    if (avail >= amount) {
      return true;
    }
    return false;
  }

  public void displayTotal() {
    
    for (Map.Entry<Integer, LinkedHashMap<String, Integer>> entry : idBalanceMap.entrySet()) {
      int id = entry.getKey();
      sum = sum + getBalance(id);
    }

    System.out.println("\n\nThe Total Asset is : " + sum);

  }

  public void nextYear() {

    System.out.println("The interest rate is : " + rate);
    System.out.println("Enter the number of years for simulation : ");
    int time = sc.nextInt();
    for (Map.Entry<Integer, LinkedHashMap<String, Integer>> entry : idBalanceMap.entrySet()) {
      int id = entry.getKey();
      int balance = getBalance(id);
      String name = getName(id);
      balance = (int) Math.round( balance * Math.pow((1 + rate), time) ); // compound interest
      updateDetails(name, id, balance);
    }

    System.out.println("\nSystem has been updated!\n");

  }

  public void send(int senderid, int receiverid) {

    if (idBalanceMap.containsKey(senderid) && idBalanceMap.containsKey(receiverid)) {
      System.out.println(SUCCESS);
      System.out.println("\nEnter the amount you want to send : ");
      int amount = sc.nextInt();
      if (hasBalance(senderid, amount)) {
        int senderBalance = getBalance(senderid);
        String senderName = getName(senderid);
        senderBalance = senderBalance - amount;
        updateDetails(senderName, senderid, senderBalance);
        int receiverBalance = getBalance(receiverid);
        String receiverName = getName(receiverid);
        receiverBalance = receiverBalance + amount;
        updateDetails(receiverName, receiverid, receiverBalance);
        System.out.println(SUCCESS);

      } else {
        System.out.println(FAIL);
      }

    } else {
      System.out.println(FAIL);
    }

  }

}

class Account extends Services {

  String name;
  int id;
  int balance;

  Account() {

  }

  Account(String name, int balance) {
    this.name = name;
    int integer = (int) Math.round(Math.random() * accountCapacity);
    while (idBalanceMap.containsKey(integer)) {
      integer = (int) Math.round(Math.random() * accountCapacity);
    }
    this.id = integer;
    this.balance = balance;
    updateDetails();
    displayDetails();
  }

  public void updateDetails() {
    LinkedHashMap<String, Integer> nameBalanceMap = new LinkedHashMap<>();
    nameBalanceMap.put(this.name, this.balance);
    idBalanceMap.put(this.id, nameBalanceMap);
  }

  public void display(int id, String name, int balance) {
    System.out.println("The Account Holder's name : " + name);
    System.out.println("The Account Holder's id : " + id);
    System.out.println("The Account Holder's balance : " + balance);
  }

  public void displayDetails() {
    System.out.println();
    display(this.id, this.name, this.balance);
  }

  public void displayDetails(int id) {
    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
      int balance = getBalance(id);
      String name = getName(id);
      display(id, name, balance);
    } else {
      System.out.println(FAIL);
    }
  }

}

public class App {
  Scanner sc = new Scanner(System.in);

  public int reEnterId() {
    System.out.println("\nEnter account id for verification and authentication : ");
    int id = sc.nextInt();
    return id;
  }

  public void sessionStart() {
      Account account = new Account();
      System.out.println(account.welcomeMESSAGE);
      System.out.println(account.sessionSTART);
      while (true) {
        System.out.println(
            "\nEnter Choice:\n1.Display Balance:\n2.Withdraw:\n3.Deposit:\n4.Send:\n5.Create Account:\n6.Display Account Details:\n7.Display Total Assets:\n8.Go Next Year:\nAny Other Number: Exit");
        int ch = sc.nextInt();
        sc.nextLine();
        int id;
        switch (ch) {

        case 1:
          id = reEnterId();
          account.displayBalance(id);
          break;
        case 2:
          id = reEnterId();
          account.withdrawCash(id);
          break;
        case 3:
          id = reEnterId();

          account.depositCash(id);
          break;
        case 4:
          int recID;
          System.out.println("\nSender ID : ");
          id = reEnterId();
          System.out.println("\nReceiver ID :");
          recID = reEnterId();
          account.send(id, recID);
          break;
        case 5:
          System.out.println("Enter your name to open account : ");
          String name = sc.nextLine();
          System.out.println("Enter the balance you want to deposit : ");
          int balance = sc.nextInt();
          new Account(name, balance);
          break;
        case 6:
          id = reEnterId();
          account.displayDetails(id);
          break;
        case 7:
          account.displayTotal();
          break;
        case 8:
          account.nextYear();
          break;
        default:

          System.out.println(account.byeMESSAGE);
          System.out.println(account.sessionCLOSE);
          System.exit(12);

        }
      }

  }

  public static void main(String[] args) {
    App app = new App();
    app.sessionStart();
  }
}
