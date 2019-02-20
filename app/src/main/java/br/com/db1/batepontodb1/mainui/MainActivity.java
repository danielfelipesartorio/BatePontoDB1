package br.com.db1.batepontodb1.mainui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import br.com.db1.batepontodb1.data.utils.Mask;
import br.com.db1.batepontodb1.data.utils.PontoPreferenceHelperImpl;
import br.com.db1.batepontodb1.secondui.SecondActivity;

import cdflynn.android.library.checkview.CheckView;
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
    public ImageView db1Logo, teamEdoil;
    PontoPreferenceHelperImpl preference;
    public CheckBox rememberMe;
    public TextView loginSuccess;
    CheckView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        preference = PontoPreferenceHelperImpl.getPreferenceInstance(this.getApplicationContext());
        pontoPresenter = new MainActivityPresenter(this);
        setContentView(R.layout.activity_main);
        userCPF = findViewById(R.id.user_cpf);
        userPassword = findViewById(R.id.user_password);
        progressBar = findViewById(R.id.loading);
        db1Logo = findViewById(R.id.logodb1);
        teamEdoil = findViewById(R.id.edoil_team);
        rememberMe = findViewById(R.id.lembrar_me);
        loginSuccess = findViewById(R.id.login_success);
        check = findViewById(R.id.check);

        userCPF.addTextChangedListener(Mask.insert(Mask.CPF_MASK,userCPF));

        userCPF.setText(preference.getUser());
        userPassword.setText(preference.getPassword());
        rememberMe.setChecked(preference.getRememberMe());

        progressBar.setVisibility(View.INVISIBLE);

       Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                db1Logo.setOnClickListener(new View.OnClickListener() {
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
                       return integerObservable.buffer(1000,TimeUnit.MILLISECONDS,5);
               }
               })
                .map(new Function<List<Integer>, Integer>() {
                    @Override
                    public Integer apply(List<Integer> views) throws Exception {
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
        Toast.makeText(getApplicationContext(), "Erro ao fazer o login. Verifique a sua conexão com a internet e seus dados de usuario.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccess() {
            if (rememberMe.isChecked()){
                preference.saveUser(userCPF.getText().toString());
                preference.savePassword(userPassword.getText().toString());
            }else {
                preference.saveUser("");
                preference.savePassword("");
            }
            preference.saveRememberMe(rememberMe.isChecked());

        progressBar.setVisibility(View.INVISIBLE);
        //loginSuccess.setText("Login feito com sucesso");
        check.setVisibility(View.VISIBLE);
        check.check();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                openSecondActivity();
            }
        }, 1000);   //5 seconds

    }

    void openSecondActivity(){

        Intent intentToOpenSecondScreen = new Intent(this, SecondActivity.class);
        intentToOpenSecondScreen.putExtra("user",userCPF.getText().toString());
        intentToOpenSecondScreen.putExtra("pass",userPassword.getText().toString());
        //intentToOpenSecondScreen.putExtra("markings",allMarkings);
        startActivity(intentToOpenSecondScreen);
    }

    @Override
    public void showLoading(boolean loading) {
        if (loading) {progressBar.setVisibility(View.VISIBLE);}
        else{progressBar.setVisibility(View.INVISIBLE);}
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
        check.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        pontoPresenter.clear();
    }
}
