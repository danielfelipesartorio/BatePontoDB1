package br.com.db1.batepontodb1.secondui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import br.com.db1.batepontodb1.R;
import br.com.db1.batepontodb1.data.PontoManager;
import br.com.db1.batepontodb1.mainui.MarkingsListAdapter;

public class SecondActivity extends AppCompatActivity implements SecondActivityInterface{
    private RecyclerView mMarkingsList;
    private SecondActivityPresenter presenter;
    private ProgressBar mTimeWorked;
    private ImageView status;
    private TextView jornada;
    private String user,password;
    private String[] markings;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mMarkingsList = findViewById(R.id.markings_list);
        mTimeWorked = findViewById(R.id.timeworked);
        status = findViewById(R.id.atuando);
        jornada = findViewById(R.id.jornada_cumprida);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getNewMarkings(user,password);
            }
        });
        presenter = new SecondActivityPresenter(this);

        markings = getIntent().getStringArrayExtra("markings");
        user = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("pass");
        updateMarkings(markings);
    }


    @Override
    public void updateMarkings(String[] markings) {

        LinearLayoutManager mMarkingsLayoutManager = new LinearLayoutManager(this);
        mMarkingsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView.Adapter mMarkingsAdapter = new MarkingsListAdapter(markings);
        mMarkingsList.setLayoutManager(mMarkingsLayoutManager);
        mMarkingsList.setAdapter(mMarkingsAdapter);

        mTimeWorked.setProgress(presenter.getTimeWorked(markings));
        mTimeWorked.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateStatus(boolean working, double timeWorked) {
        if (working){
            status.setColorFilter(Color.GREEN);
        } else {
            status.setColorFilter(Color.GRAY);
        }
        DecimalFormat df = new DecimalFormat("00");
        String timeFormatted = ""+ df.format((int) Math.floor(timeWorked))+":"+ df.format ((int) Math.floor((timeWorked%1)*60));
        jornada.setText(getString(R.string.jornada,timeFormatted));
    }

    @Override
    public void error() {
        Toast.makeText(this, "Sem conexão. Reinicie aplicativo e tente novamente mais tarde", Toast.LENGTH_LONG).show();
    }

    @Override
    public void successMsg() {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_ok_registro);
            dialog.show();
            presenter.getNewMarkings(user,password);
    }

    public void openTaskButtonClick(View view){
        Intent intentTaskWeb = presenter.openTaskWeb();
        startActivity(intentTaskWeb);
    }

    public void registrarPonto(View view) {
        presenter.register(user,password);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.clear();
    }
}
