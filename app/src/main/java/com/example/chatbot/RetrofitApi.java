package com.example.chatbot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {
    @GET
    Call<MsgModal> getMessage(@Url String url);                                     //getmessage method- to get message from out Api
                                                                                    //String 'url'-pass string from which we will get Api

}
