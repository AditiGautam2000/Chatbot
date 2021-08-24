package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFab;

    private final String BOT_KEY ="bot";
    private final String USER_KEY ="user";
    private ArrayList<ChatModal> chatsModalArrayList;
    private Chatrvadapter chatrvadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatsRV=findViewById(R.id.idrvchats);
        userMsgEdt=findViewById(R.id.idedtmessage);
        sendMsgFab=findViewById(R.id.idfabsend);
        chatsModalArrayList =new ArrayList<>();
        chatrvadapter =new Chatrvadapter(chatsModalArrayList,this);
        LinearLayoutManager manager= new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatrvadapter);
        sendMsgFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userMsgEdt.getText().toString().isEmpty())
                { Toast.makeText(MainActivity.this,"please your message",Toast.LENGTH_SHORT ).show();
                    return;
                }
                getResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText(" ");

            }
        });

    }
    private void getResponse(String message){
        chatsModalArrayList.add(new ChatModal(message,USER_KEY));
        chatrvadapter.notifyDataSetChanged();
        String url="http://api.brainshop.ai/get?bid=157051&key=FIbCgl9mJNv2esZE&uid=[uid]&msg="+message;
        String BASE_URL="http://api.brainshop.ai/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         RetrofitApi retrofitApi=retrofit.create(RetrofitApi.class);
        Call<MsgModal> call =retrofitApi.getMessage(url);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()){
                    MsgModal modal=response.body();
                    chatsModalArrayList.add(new ChatModal(modal.getCnt(),BOT_KEY));
                    chatrvadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatModal("Please revert your question",BOT_KEY));
                chatrvadapter.notifyDataSetChanged();

            }
        });


    }
}