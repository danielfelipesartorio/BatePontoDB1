package br.com.db1.batepontodb1.data.utils;

public class CountString {

    public static int CountStringChaveDeSeg(final String string)
    {
        int count = 0;
        int idx = 0;
        while ((idx = string.indexOf("Chave de Seguran", idx)) != -1)
        {
            idx++;
            count++;
        }
        return count;
    }
}
