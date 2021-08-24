package com.example.chatbot;

public class ChatModal {                                        ///creating 2 different view for user and the bot
    private String message;                                    //This class will hold the data that will be displayed in the Recycler view
    private String sender;                                    //we are using multiple view type recycler VIEW
                                                              // message-data of message   sender- Bot/user




    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }
    public ChatModal(String message, String sender) {                //Adapter class to handle data in the recycler view
        this.message = message;
        this.sender = sender;
    }
}