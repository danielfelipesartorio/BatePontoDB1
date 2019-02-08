package br.com.db1.batepontodb1.mainui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.db1.batepontodb1.R;
import br.com.db1.batepontodb1.data.utils.HideKeyboard;
import br.com.db1.batepontodb1.data.utils.HtmlResponseCache;
import br.com.db1.batepontodb1.data.utils.Mask;
import br.com.db1.batepontodb1.secondui.SecondActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity implements MainActivityInterface{
public EditText userCPF;
public EditText userPassword;
public MainActivityPresenter pontoPresenter;
public ProgressBar progressBar;
public ImageView logodb1, edoilteam;
public Context context;
public SharedPreferences sharedPref;
public CheckBox lembrarme;
public TextView loginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        pontoPresenter = new MainActivityPresenter(this);
        setContentView(R.layout.activity_main);
        userCPF = findViewById(R.id.user_cpf);
        userPassword = findViewById(R.id.user_password);
        progressBar = findViewById(R.id.loading);
        logodb1 = findViewById(R.id.logodb1);
        edoilteam = findViewById(R.id.edoil_team);
        lembrarme = findViewById(R.id.lembrar_me);
        loginSuccess = findViewById(R.id.login_success);

        userCPF.addTextChangedListener(Mask.insert(Mask.CPF_MASK,userCPF));

        userCPF.setText(sharedPref.getString("user",""));
        userPassword.setText(sharedPref.getString("password",""));
        lembrarme.setChecked(sharedPref.getBoolean("lembrarme",false));

        progressBar.setVisibility(View.INVISIBLE);

       Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                logodb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emitter.onNext(1);
                        Log.v("reste","click");
                    }
                });
            }
        })
               .publish(new Function<Observable<Integer>, ObservableSource<List<Integer>>>() {
                   @Override
                   public ObservableSource<List<Integer>> apply(Observable<Integer> integerObservable) throws Exception {
                       return integerObservable.buffer(integerObservable.debounce(200,TimeUnit.MILLISECONDS));
               }
               })
                .map(new Function<List<Integer>, Integer>() {
                    @Override
                    public Integer apply(List<Integer> views) throws Exception {
                        Log.v("reste","map");
                        return views.size();
                    }
                })
               .filter(new Predicate<Integer>() {
           @Override
           public boolean test(Integer integer) throws Exception {
               return integer>4;
           }
       })
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onNext(Integer view) {
                        Log.v("reste","its alive!!!!!");
                        openEasterEgg();
                    }
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onComplete() {}
                });

    }

    public void fazerLogin(View view) {
        HideKeyboard.hideKeyboard(this);
        pontoPresenter.validate(userCPF.getText().toString(),userPassword.getText().toString());
    }

    @Override
    public void loginError() {
        showLoading(false);
        Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(String  htmlResponse) {
        SharedPreferences.Editor editor=  sharedPref.edit();
        if (lembrarme.isChecked()){
            editor.putString("user",userCPF.getText().toString());
            editor.putString("password",userPassword.getText().toString());
        }else {
            editor.putString("user",null);
            editor.putString("password",null);
        }
        editor.putBoolean("lembrarme",lembrarme.isChecked());
        editor.apply();

        Intent intentToOpenSecondScreen = new Intent(this, SecondActivity.class);
        HtmlResponseCache htmlResponseCache = HtmlResponseCache.getInstance();
        htmlResponseCache.setHtmlResponse(htmlResponse);

        //intentToOpenSecondScreen.putExtra("markingsReference", htmlResponse);
        intentToOpenSecondScreen.putExtra("user",userCPF.getText().toString());
        intentToOpenSecondScreen.putExtra("pass",userPassword.getText().toString());
        startActivity(intentToOpenSecondScreen);
        progressBar.setVisibility(View.INVISIBLE);
        loginSuccess.setText("Login feito com sucesso");

    }

    @Override
    public void showLoading(boolean loading) {
    if (loading) {
        progressBar.setVisibility(View.VISIBLE);
    }else{
        progressBar.setVisibility(View.INVISIBLE);
    }
    }

    public void openEasterEgg(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_tiozao);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginSuccess.setText("");
    }
}
