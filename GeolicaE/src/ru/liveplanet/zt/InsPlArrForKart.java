package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 23.10.2009
 * Time: 11:27:52
 * To change this template use File | Settings | File Templates.
 */
public class InsPlArrForKart
{

    public int plName;
    public double aspect=0;
    public  double dolgota=0;

    InsPlArrForKart( )
    {
        this.aspect=0;
        this.plName=0;
        this.dolgota=0;
        //PlArrForKart p=new PlArrForKart();
        //this.pl=new PlArrForKart();
        //this.arrPlKt=new PlArrForKart[cnt];
    }
   public static void main(String[] argStr)
   {
       InsPlArrForKart[] ip= new InsPlArrForKart[22];
       ip[0].aspect=12;
       ip[0].plName=0;
   }
}
