package br.com.db1.batepontodb1.secondui;

public interface SecondActivityInterface {
    void updateMarkings(String[] strings);
    void updateStatus (boolean working,double time);
    void error();

    void successMsg();
}
