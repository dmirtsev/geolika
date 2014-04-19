package ru.liveplanet.zt;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 02.11.2009
 * Time: 9:17:45
 * To change this template use File | Settings | File Templates.
 */
public class FileWork {
    int[][] aspectPlanetZT;
    int planetCount;
    String findStr,comment;


    FileWork(int[][] aspectPlanetZT, int plCnt, String comment)

    {
        this.aspectPlanetZT=aspectPlanetZT;
        this.planetCount=plCnt;
        this.comment=comment;
    }

    //String[] readFile()
    ArrayList readFile()
    {
        String[] str;
        ArrayList al =new ArrayList();

        str=new String[50];
        int cnt=0;
            int ii=0; int jj=0;
           for(int i=0;i<=planetCount-1;i++)
          {
              for(int j=0;j<=3;j++)
              {

               if (aspectPlanetZT[i][j]!=-1)
               {
                   //setAspect(aspectPlanetZT[i][j]);
                   //System.out.println(" Аспект Планета - ZT "+i+" - "+j+" " + aspectPlanetZT[i][j]);
                   int asp=(int)aspectPlanetZT[i][j];
                   ii=i+1; jj=j+1;
                   str[cnt]="["+jj+"."+asp+"."+ii+"]";
                   al.add("["+jj+"."+asp+"."+ii+"]");
                   System.out.println("Искомая стр. "+str[cnt]);
                   cnt++;
               }
              }
          }

        return al;
    }

    void inFile(String strOut) throws IOException
    {
        PrintWriter out1 =
               new PrintWriter(
                 new BufferedWriter(
                   new FileWriter("IODemo.out")));
        out1.println(strOut);
        out1.close();
    }

    /*
    int cntArr(String[] findStr)
    {
        int cnt=0;
        for (int i=0; i<=findStr.length;i++)
        {
          if (findStr[i].equals(null))
              cnt=i+1;
        }
        return cnt;
    }
      */
    String findInFile(ArrayList findStr)
    {
               String str="";
                   try {
                       //String str="";

                       //String readPage(String filename, String fileString)               ;
                       String filename="interpret\\interpr.txt";

                       String fileString="";
	                   //FileInputStream fis=new FileInputStream(filename);
	                   InputStreamReader isr=new InputStreamReader(
                            new FileInputStream(filename),"windows-1251");
	                    BufferedReader in = new BufferedReader(isr);
                       PrintWriter out1 =
               new PrintWriter(
                 new BufferedWriter(
                   new FileWriter("man\\"+comment+"\\"+comment+".txt")));

             // cntArr(findStr);
               boolean readStr=false;
                       while (((str = in.readLine()) != null))
        {
            //System.out.println(str);  
            for (int i=0; i <= (findStr.size() - 1);i++)
                       {
          //fileString=fileString+"\n"+str;
            if (str.indexOf((String)findStr.get(i))!=-1)
            {

               readStr=true;
               System.out.println(str);
               out1.println(str);
                break;
            }
                           if (str.indexOf(("["))==-1 && readStr)
                           {
                               System.out.println(str);
                               out1.println(str);
                               break;
                           } else readStr=false;

                       }
        }

        in.close();
                       out1.close();
    } catch (IOException e)
                   {

                   }
        return str;
    } ;

    public static void main(String[] argStr)
    {
        int[][] aspectPlanetZT= new int[2][4] ;
        aspectPlanetZT[0][0]=120;
        aspectPlanetZT[0][1]=150;
        aspectPlanetZT[0][2]=120;
        aspectPlanetZT[0][3]=150;

        aspectPlanetZT[1][0]=90;
        aspectPlanetZT[1][1]=180;
        aspectPlanetZT[1][2]=90;
        aspectPlanetZT[1][3]=180;

        FileWork fw= new FileWork(aspectPlanetZT,2,"Соммент");
        //fw.readFile();
        fw.findInFile(fw.readFile());
    }
}
