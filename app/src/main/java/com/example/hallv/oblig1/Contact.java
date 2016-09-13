package com.example.hallv.oblig1;

/**
 * Created by hallv on 02.09.2016.
 */

public class Contact {
    String name;
    String number;
    static int contactCount = 0;
    int id;
    MessageList messageList;

    public Contact(String name, String number){
        contactCount++;
        id = contactCount;
        this.name = name;
        this.number = number;
        this.messageList = new MessageList();
        addTestList();
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public void addTestList(){
        for (int i = 0; i<3; i++){
            messageList.addMessage("Test " + i);
        }
    }
    public String getLastMessage(){
       String msg =  messageList.getLastMessage().toString();
        return msg;
    }

}
