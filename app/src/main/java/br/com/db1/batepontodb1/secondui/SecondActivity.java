package br.com.db1.batepontodb1.secondui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import br.com.db1.batepontodb1.data.utils.HtmlResponseCache;
import br.com.db1.batepontodb1.mainui.MarkingsListAdapter;

public class SecondActivity extends AppCompatActivity implements SecondActivityInterface{
    private RecyclerView mMarkingsList;
    private SecondActivityPresenter presenter;
    private ProgressBar mTimeWorked;
    private ImageView status;
    private TextView jornada;
    private String user,password,htmlResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mMarkingsList = findViewById(R.id.markings_list);
        mTimeWorked = findViewById(R.id.timeworked);
        status = findViewById(R.id.atuando);
        jornada = findViewById(R.id.jornada_cumprida);

        presenter = new SecondActivityPresenter(this);

        //htmlResponse = getIntent().getStringExtra("markings");
        user = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("pass");
        HtmlResponseCache htmlResponseCache = HtmlResponseCache.getInstance();
        htmlResponse= htmlResponseCache.getHtmlResponse();
        String[] markings =presenter.getAllMarkingsFromHTMLResponse(htmlResponse);
        updateMarkings(markings);
    }

    @Override
    public void updateMarkings(String[] markings) {

        LinearLayoutManager mMarkingsLayoutManager = new LinearLayoutManager(this);
        mMarkingsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView.Adapter mMarkingsAdapter = new MarkingsListAdapter(markings);
        mMarkingsList.setLayoutManager(mMarkingsLayoutManager);
        mMarkingsList.setAdapter(mMarkingsAdapter);

        int temp = (int) Math.ceil(presenter.getTimeWorked(markings)/8.8*100);
        mTimeWorked.setProgress(temp);
        mTimeWorked.setVisibility(View.VISIBLE);
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
    }

    public void openTaskButtonClick(View view){
        Intent intentTaskWeb = presenter.openTaskWeb();
        startActivity(intentTaskWeb);
    }

    public void registrarPonto(View view) {
        presenter.register(user,password);
    }
}
