import java.util.*;

class Bank {
  static LinkedHashMap<String, Integer> nameBalanceMap = new LinkedHashMap<>();
  static LinkedHashMap<Integer, Integer> idBalanceMap = new LinkedHashMap<>();

  String SUCCESS = "\nThe request is succesful!";
  String FAIL = "\nThe request is not succesful!";

  String welcomeMESSAGE = "\nWelcome to Somraj Payments Bank!";
  String byeMESSAGE = "\nHave a good day!";

  String sessionSTART = "\nSession Started!";
  String sessionCLOSE = "\nSession Closed!";

}

class Services extends Bank {

  public void displayBalance(int id) {

    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
    } else {
      System.out.println(FAIL);
    }

  }

  public void withdrawCash(int id) {
    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
    } else {
      System.out.println(FAIL);
    }
  }

  public void depositCash(int id) {
    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
    } else {
      System.out.println(FAIL);
    }
  }

  public void send(int senderId, int receiverId) {

    if (idBalanceMap.containsKey(senderId) && idBalanceMap.containsKey(receiverId)) {
      System.out.println(SUCCESS);
    } else {
      System.out.println(FAIL);
    }

  }

  public void displayDetails(int id) {
    if (idBalanceMap.containsKey(id)) {
      System.out.println(SUCCESS);
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
    nameBalanceMap.put(this.name, this.balance);
    idBalanceMap.put(this.id, this.balance);
  }

  public void displayDetails() {
    System.out.println("The Account Holder's name : " + this.name);
    System.out.println("The Account Holder's id : " + this.id);
    System.out.println("The Account Holder's balance: " + this.balance);
  }

}

public class App {

  public int reEnterId() {
    System.out.println("\nRe enter your id to confirm: ");
    Scanner sc = new Scanner(System.in);
    int id = sc.nextInt();
    return id;
  }

  public void sessionStart() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter your name to open account: ");
    String name = sc.nextLine();
    System.out.println("Enter the balance you want to deposit: ");
    int balance = sc.nextInt();
    Account account = new Account(name, balance);
    System.out.println("Do you want to go to Account Services ?");
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
          ;
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