package me.venomts;

import java.io.*;
import java.util.Objects;

public class FileManagement
{
    private static String Normalize(String input)
    {
        return input.trim().replaceAll("\\s+", " ");
    }

    public static boolean AreFilesSame(File f1, File f2)
    {
        try
        {
            BufferedReader r1 = new BufferedReader(new FileReader(f1));
            BufferedReader r2 = new BufferedReader(new FileReader(f2));

            String s1, s2;
            while(true)
            {
                s1 = r1.readLine();
                s2 = r2.readLine();

                String normalizedS1 = s1 == null ? null : Normalize(s1);
                String normalizedS2 = s2 == null ? null : Normalize(s2);

                if(!Objects.equals(normalizedS1, normalizedS2))
                    return false;

                if(s1 == null)
                    return true;
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
