package br.com.db1.batepontodb1.secondui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;

import br.com.db1.batepontodb1.R;

public class SecondActivity extends AppCompatActivity implements SecondActivityInterface {
    private SecondActivityPresenter presenter;
    private ProgressBar mTimeWorked;
    private ImageView status;
    private TextView jornada;
    private String user, password;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MarkingsListAdapter mMarkingsAdapter;
    public LinearLayoutManager mMarkingsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        MobileAds.initialize(this, getString(R.string.adId));
        AdView mAdView = findViewById(R.id.adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RecyclerView mMarkingsList = findViewById(R.id.markings_list);
        mTimeWorked = findViewById(R.id.timeworked);
        status = findViewById(R.id.atuando);
        jornada = findViewById(R.id.jornada_cumprida);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        presenter = new SecondActivityPresenter(this);

        user = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("pass");

        swipeRefreshLayout.setRefreshing(true);

        mMarkingsLayoutManager = new LinearLayoutManager(this);
        mMarkingsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        String[] blank = {"    \n\n  \n         "};
        mMarkingsAdapter = new MarkingsListAdapter(blank);

        mMarkingsList.setLayoutManager(mMarkingsLayoutManager);
        mMarkingsList.setAdapter(mMarkingsAdapter);

        presenter.getNewMarkings(user, password);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getNewMarkings(user, password);
            }
        });
    }

    @Override
    public void updateMarkings(String[] markings) {
        mTimeWorked.setProgress(presenter.getTimeWorked(markings));
        mTimeWorked.setVisibility(View.VISIBLE);
        mMarkingsAdapter.setMarkings(markings);
        mMarkingsAdapter.notifyDataSetChanged();
        TextView mTitulo = findViewById(R.id.titulo);
        mTitulo.setText(getString(R.string.ultimas_marca_es,mMarkingsAdapter.getItemCount()));
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void updateStatus(boolean working, double timeWorked) {
        if (working) {
            status.setColorFilter(Color.GREEN);
        } else {
            status.setColorFilter(Color.GRAY);
        }
        DecimalFormat df = new DecimalFormat("00");
        String timeFormatted = "" + df.format((int) Math.floor(timeWorked)) + ":" + df.format((int) Math.floor((timeWorked % 1) * 60));
        jornada.setText(getString(R.string.jornada, timeFormatted));
    }

    @Override
    public void error() {
        Toast.makeText(this, "Sem conexão. Reinicie aplicativo e tente novamente mais tarde", Toast.LENGTH_LONG).show();
    }

    @Override
    public void successMsg() {
        CustomDialog dialog = new CustomDialog(this);
        dialog.show();
        presenter.getNewMarkings(user, password);
    }

    public void openTaskButtonClick(View view) {
        Intent intentTaskWeb = presenter.openTaskWeb();
        startActivity(intentTaskWeb);
    }

    public void registrarPonto(View view) {
        presenter.register(user, password);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.clear();
    }
}