import java.util.*;

class Bank {
  static LinkedHashMap<Integer, LinkedHashMap<String, Integer>> idBalanceMap = new LinkedHashMap<>();

  String SUCCESS = "\nThe request is succesful!";
  String FAIL = "\nThe request is not succesful!";

  String welcomeMESSAGE = "\nWelcome to Somraj Payments Bank!";
  String byeMESSAGE = "\nHave a good day!";

  String sessionSTART = "\nSession Started!";
  String sessionCLOSE = "\nSession Closed!";

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
      System.out.println(getBalance(id));
    } else {
      System.out.println(FAIL);
    }

  }

  public void withdrawCash(int id) {
    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
      System.out.println("\nEnter the amount you want to withdraw:");
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
      System.out.println("\nEnter the amount you want to deposit:");
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

  public void send(int senderid, int receiverid) {

    if (idBalanceMap.containsKey(senderid) && idBalanceMap.containsKey(receiverid)) {
      System.out.println(SUCCESS);
      System.out.println("\nEnter the amount you want to send:");
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

  Account(String name, int balance) {
    this.name = name;
    int integer = (int) Math.round(Math.random() * 100);
    while (idBalanceMap.containsKey(integer)) {
      integer = (int) Math.round(Math.random() * 100);
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
    System.out.println("The Account Holder's balance: " + balance);
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
    System.out.println("\nEnter account id for verification and authentication: ");
    int id = sc.nextInt();
    return id;
  }

  public void sessionStart() {
    System.out.println("Enter your name to open account: ");
    String name = sc.nextLine();
    System.out.println("Enter the balance you want to deposit: ");
    int balance = sc.nextInt();
    Account account = new Account(name, balance);
    System.out.println("Do you want to go to Banking Services ?");
    System.out.println("Press 1 to Continue or 2 to Exit!");
    int choice = sc.nextInt();
    outer: if (choice == 1) {
      System.out.println(account.welcomeMESSAGE);
      System.out.println(account.sessionSTART);
      while (true) {
        System.out.println(
            "\nEnter Choice:\n1.Display Balance:\n2.Withdraw:\n3.Deposit:\n4.Send:\n5.Create Account:\n6.Display Account Details:\nAny Other Number: Exit");
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
          System.out.println("Sender ID:");
          id = reEnterId();
          System.out.println("Receiver ID:");
          recID = reEnterId();
          account.send(id, recID);
          break;
        case 5:
          App appNew = new App();
          appNew.sessionStart();
          break;
        case 6:
          id = reEnterId();
          account.displayDetails(id);
          break;
        default:

          System.out.println(account.byeMESSAGE);
          System.out.println(account.sessionCLOSE);
          System.exit(12);

          break outer;
        }
      }
    }

    System.out.println(account.byeMESSAGE);
    System.out.println(account.sessionCLOSE);

  }

  public static void main(String[] args) {
    App app = new App();
    app.sessionStart();
  }
}