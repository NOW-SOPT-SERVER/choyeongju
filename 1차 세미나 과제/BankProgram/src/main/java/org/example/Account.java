package org.example;

public class Account {
    private String account;
    private String customer_name;
    private int money;

    public Account(String account, String customer_name, int money){
        this.account=account;
        this.customer_name=customer_name;
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void deposit(int money){ //입금
        this.money+=money;
        System.out.println(this.customer_name + "님에게 " + money + "원 입금 완료.");
        System.out.println(this.customer_name + "님의 현재 잔액은 " + this.money + "원 입니다.");
    }

    public void withDraw(int money){ //출금
        if(this.money<money) System.out.println("잔액이 부족합니다.");
        else {
            this.money -= money;
            System.out.println();
            System.out.println(money + "원 출금 완료.");
            System.out.println(this.customer_name + "님의 현재 잔액은 " + this.money + "원 입니다.");
        }
    }

    public void transfer(Account otherAccount, Account myAccount, int money){ //계좌 이체
        if(this.money<money) System.out.println("잔액이 부족합니다.");
        else{
            this.money-=money;
            System.out.println(otherAccount.getCustomer_name() + "님에게 " + money + "원을 이체 하였습니다.");
            otherAccount.deposit(money);
        }
    }
}
