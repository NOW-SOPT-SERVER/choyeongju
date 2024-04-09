package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("은행 프로그램 입니다.");
        System.out.println("원하는 메뉴의 번호를 선택해 주세요.");

        showMenu(); //메뉴 출력

        while(true){
            String menu=sc.nextLine();
            switch (menu){
                case "1" :
                    createAccountService();
                    break;
                case "2" :
                    showAccountService();
                    break;
                case "3" :
                    depositService();
                    break;
                case "4" :
                    withDrawService();
                    break;
                case "5":
                    transferService();
                    break;
                case "6" :
                    System.out.println("은행 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("메뉴를 다시 선택해 주세요.");
            }
            showMenu();
        }
    }

    public static void showMenu(){
        System.out.println("-------------------------------");
        System.out.println("1. 계좌 개설");
        System.out.println("2. 계좌 목록 확인");
        System.out.println("3. 입금");
        System.out.println("4. 출금");
        System.out.println("5. 계좌이체");
        System.out.println("6. 프로그램 종료");
        System.out.println("-------------------------------");
    }

    public static void createAccountService(){
        System.out.println("계좌 생성 서비스 입니다.");
        System.out.print("생성할 새로운 계좌번호 입력 : ");

        Scanner sc = new Scanner(System.in);
        String account_num = sc.nextLine();

        System.out.print("예금주명 입력 : ");
        String customer_name = sc.nextLine();

        System.out.print("초기 금액 입력 : ");
        int init_money = sc.nextInt();

        Account ac = new Account(account_num, customer_name, init_money);
        accounts.add(ac);
        System.out.println("계좌 개설 완료!");
        System.out.println();
    }

    public static void showAccountService(){
        if(accounts.isEmpty()) System.out.println("계좌 목록이 비어 있습니다. 계좌를 개설해 주세요.");
        else{
            System.out.println("---------<계좌 목록 출력>---------");
            for(int i=0; i<accounts.size();i++){
                Account ac = accounts.get(i);
                System.out.println("계좌 번호 : " + ac.getAccount());
                System.out.println("예금주 : " + ac.getCustomer_name());
                System.out.println("잔액 : " + ac.getMoney());
                System.out.println();
            }
            System.out.println("-------------------------------");
        }
    }

    public static void depositService(){
        System.out.println("입금 서비스 입니다.");
        System.out.print("입금할 계좌번호 입력 : ");

        Scanner sc = new Scanner(System.in);
        String account_num = sc.nextLine();

        System.out.print("입금할 금액 입력 : ");
        int money = sc.nextInt();
        System.out.println();

        Account ac=findAccount(account_num);
        if(account_num == null) System.out.println("없는 계좌입니다.");
        else{
            ac.deposit(money);
        }
    }

    public static void withDrawService() {
        System.out.println("출금 서비스 입니다.");
        System.out.print("출금할 계좌번호 입력 : ");

        Scanner sc = new Scanner(System.in);
        String account_num = sc.nextLine();

        System.out.print("출금할 금액 입력 : ");
        int money = sc.nextInt();

        Account ac=findAccount(account_num);
        if(account_num == null) System.out.println("없는 계좌입니다.");
        else{
            ac.withDraw(money);
        }
    }

    public static void transferService(){
        Scanner sc= new Scanner(System.in);
        System.out.println("계좌이체 서비스 입니다.");

        System.out.print("당신의 계좌를 입력하세요(보내는 사람) : ");
        String account_send = sc.nextLine();

        System.out.print("계좌이체할 상대방의 계좌번호를 입력하세요(받는 사람) : ");
        String account_receive = sc.nextLine();

        System.out.print("이체할 금액 입력 : ");
        int money=sc.nextInt();
        System.out.println();

        Account ac_send = findAccount(account_send);
        Account ac_receive = findAccount(account_receive);
        if(ac_send == null ) System.out.println("보내는 사람이 없는 계좌입니다. 계좌를 개설해 주세요.");
        else if(ac_receive == null) System.out.println("받는 사람이 없는 계좌입니다. 계좌를 개설해 주세요.");
        else{
            ac_send.transfer(ac_receive, ac_send, money);
        }
    }

    private static Account findAccount(String account_num){
        for(int i = 0; i < accounts.size(); i++){
            Account ac = accounts.get(i);
            if(ac.getAccount().equals(account_num)){
                return ac;
            }
        }
        return null;
    }
}

