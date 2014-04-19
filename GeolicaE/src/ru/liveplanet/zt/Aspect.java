package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 22.10.2009
 * Time: 11:10:57
 * To change this template use File | Settings | File Templates.
 */


public class Aspect
{

 public static final int ZC= 0;// ������ ���������
 public static final int ZJ= 1;// ������ ������������
 public static final int ZB= 2;// ������ ���������
 public static final int ZT= 3;// ������ ���������
    public static final String[] ZTOCH;
    static {
    ZTOCH = new String[4];
        ZTOCH[0]="���������";
        ZTOCH[1]="������������";
        ZTOCH[2]="���������";
        ZTOCH[3]="��������";
     }
      
 public static final double CONJ0= 0;//1 ����������    ��� � ZET   1
 public static final double VIG18=   18; //2��������� (������, ����������, ����������)
 public static final double SEMINON20=  20;//3 �����������
 public static final double KVIND24=  24;//4 ����������
 public static final double SSX30=  30;//5 ������������ ��� � ZET 2
 public static final double DECL36  =36 ;// 6������������ ��� � ZET   3
 public static final double NOVL40  =40 ;//7 ������(�������)   ��� � ZET  4
 public static final double SKVAD45=45 ;//8�����������(������)   ��� � ZET  5
 public static final double SEPT51  =51.43 ;//9�������   ��� � ZET
 public static final double  SEXT60 =60 ;//10�������� (������������)   ��� � ZET 6
 public static final double   KVINT72=72 ;//11��������   ��� � ZET  7
 public static final double   BNOV80=80 ;//12�������� (���������)   ��� � ZET
 public static final double   KVDR90=90 ;//13�������   ��� � ZET    8
 public static final double   SESKN100=100 ;//14�����������   ��� � ZET
 public static final double   BSERT102=102.86 ;//15���������?   ��� � ZET
 public static final double   TRDEC108=108  ;//16���������   ��� � ZET  9
 public static final double   TRIN120=120 ;//17����   ��� � ZET  10
 public static final double   PLKVDR135=135 ;//18��������������� (�������������)   ��� � ZET 11
 public static final double   BIKV144=144 ;//19���������   ��� � ZET  12
 public static final double   KVIK150=150 ;//20�������   ��� � ZET   13
 public static final double   TRSEPT154=154.29 ;//21����������?   ��� � ZET
 public static final double   TTRNVL160=160 ;//22����������� (�����������)   ��� � ZET
 public static final double   OPP180=180 ;//23���������   ��� � ZET  14
 public static final double ORB=2; //��� ��� ��������   ��� � ZET
    double [] majorAspect;
    double [] majorAspectModul;
    double [] minorAspect;
    double [] minorAspectModul;
    double [] allAspect;
    double [] allAspectModul;
    double [] erthqAspect;
    String[] strPlanet;

    Aspect()
    {

        /*
        SUN, LUN, MER, VEN, MAR, JUP, SAT, URA, NEP , PLU, NOD, SND, LIL
        */

        String[] strPlanet1= {"","",""};
        strPlanet= new String[13];
        strPlanet[0]="SUN";strPlanet[1]="LUN";strPlanet[2]="MER";strPlanet[3]="VEN";strPlanet[4]="MAR";
        strPlanet[5]="JUP";strPlanet[6]="SAT";strPlanet[7]="URA";
        strPlanet[8]="NEP";strPlanet[9]="PLU";strPlanet[10]="NOD";strPlanet[11]="SND";strPlanet[12]="LIL";

        majorAspect=new double[8];

        majorAspect[0]=Aspect.CONJ0;majorAspect[1]=Aspect.SEXT60;majorAspect[2]=Aspect.KVDR90;
        majorAspect[3]=Aspect.TRIN120;majorAspect[4]=Aspect.OPP180;

        majorAspect[5]=-Aspect.SEXT60;majorAspect[6]=-Aspect.KVDR90;
        majorAspect[7]=-Aspect.TRIN120;

        erthqAspect=new double[10];

        erthqAspect[0]=Aspect.CONJ0;erthqAspect[1]=Aspect.SEXT60;erthqAspect[2]=Aspect.KVDR90;
        erthqAspect[3]=Aspect.TRIN120;erthqAspect[4]=Aspect.OPP180;

        erthqAspect[5]=-Aspect.SEXT60;erthqAspect[6]=-Aspect.KVDR90;
        erthqAspect[7]=-Aspect.TRIN120;
        erthqAspect[8]=Aspect.KVIK150;erthqAspect[9]=-Aspect.KVIK150;


        minorAspect=new double[34];
        minorAspect[0]=Aspect.VIG18;minorAspect[1]=Aspect.SEMINON20;minorAspect[2]=Aspect.KVIND24;
        minorAspect[3]=Aspect.DECL36;minorAspect[4]=Aspect.NOVL40;minorAspect[5]=Aspect.SKVAD45;
        minorAspect[6]=Aspect.SEPT51;minorAspect[7]=Aspect.KVINT72;minorAspect[8]=Aspect.BNOV80;
        minorAspect[9]=Aspect.SESKN100;minorAspect[10]=Aspect.BSERT102;minorAspect[11]=Aspect.TRDEC108;
        minorAspect[12]=Aspect.PLKVDR135;minorAspect[13]=Aspect.BIKV144;minorAspect[14]=Aspect.KVIK150;
        minorAspect[15]=Aspect.TTRNVL160;
        minorAspect[16]=-Aspect.VIG18;minorAspect[17]=-Aspect.SEMINON20;minorAspect[18]=-Aspect.KVIND24;
        minorAspect[19]=-Aspect.DECL36;minorAspect[20]=-Aspect.NOVL40;minorAspect[21]=-Aspect.SKVAD45;
        minorAspect[22]=-Aspect.SEPT51;minorAspect[23]=-Aspect.KVINT72;minorAspect[24]=-Aspect.BNOV80;
        minorAspect[25]=-Aspect.SESKN100;minorAspect[26]=-Aspect.BSERT102;minorAspect[27]=-Aspect.TRDEC108;
        minorAspect[28]=-Aspect.PLKVDR135;minorAspect[29]=-Aspect.BIKV144;minorAspect[30]=-Aspect.KVIK150;
        minorAspect[31]=-Aspect.TTRNVL160; minorAspect[32]=Aspect.SSX30;minorAspect[33]=-Aspect.SSX30;

        allAspect= new double[42];
        allAspect[0]=0;allAspect[1]=60;allAspect[2]=90;
        allAspect[3]=120;allAspect[4]=180;
        allAspect[5]=Aspect.VIG18;allAspect[6]=Aspect.SEMINON20;allAspect[7]=Aspect.KVIND24;
        allAspect[8]=Aspect.DECL36;allAspect[9]=Aspect.NOVL40;allAspect[10]=Aspect.SKVAD45;
        allAspect[11]=Aspect.SEPT51;allAspect[12]=Aspect.KVINT72;allAspect[13]=Aspect.BNOV80;
        allAspect[14]=Aspect.SESKN100;allAspect[15]=Aspect.BSERT102;allAspect[16]=Aspect.TRDEC108;
        allAspect[17]=Aspect.PLKVDR135;allAspect[18]=Aspect.BIKV144;allAspect[19]=Aspect.KVIK150;
        allAspect[20]=Aspect.TTRNVL160;
        allAspect[21]=-60;allAspect[22]=-90;
        allAspect[23]=-120;
        allAspect[24]=-Aspect.VIG18;allAspect[25]=-Aspect.SEMINON20;allAspect[26]=-Aspect.KVIND24;
        allAspect[27]=-Aspect.DECL36;allAspect[28]=-Aspect.NOVL40;allAspect[29]=-Aspect.SKVAD45;
        allAspect[30]=-Aspect.SEPT51;allAspect[31]=-Aspect.KVINT72;allAspect[32]=-Aspect.BNOV80;
        allAspect[33]=-Aspect.SESKN100;allAspect[34]=-Aspect.BSERT102;allAspect[35]=-Aspect.TRDEC108;
        allAspect[36]=-Aspect.PLKVDR135;allAspect[37]=-Aspect.BIKV144;allAspect[38]=-Aspect.KVIK150;
        allAspect[39]=-Aspect.TTRNVL160;
        allAspect[40]=Aspect.SSX30;allAspect[41]=-Aspect.SSX30;

        majorAspectModul=new double[5];

               majorAspectModul[0]=Aspect.CONJ0;majorAspectModul[1]=Aspect.SEXT60;majorAspectModul[2]=Aspect.KVDR90;
               majorAspectModul[3]=Aspect.TRIN120;majorAspectModul[4]=Aspect.OPP180;



               minorAspectModul=new double[17];
               minorAspectModul[0]=Aspect.VIG18;minorAspectModul[1]=Aspect.SEMINON20;minorAspectModul[2]=Aspect.KVIND24;
               minorAspectModul[3]=Aspect.DECL36;minorAspectModul[4]=Aspect.NOVL40;minorAspectModul[5]=Aspect.SKVAD45;
               minorAspectModul[6]=Aspect.SEPT51;minorAspectModul[7]=Aspect.KVINT72;minorAspectModul[8]=Aspect.BNOV80;
               minorAspectModul[9]=Aspect.SESKN100;minorAspectModul[10]=Aspect.BSERT102;minorAspectModul[11]=Aspect.TRDEC108;
               minorAspectModul[12]=Aspect.PLKVDR135;minorAspectModul[13]=Aspect.BIKV144;minorAspectModul[14]=Aspect.KVIK150;
               minorAspectModul[15]=Aspect.TTRNVL160;minorAspectModul[16]=Aspect.SSX30;

               allAspectModul= new double[22];
               allAspectModul[0]=0;allAspectModul[1]=60;allAspectModul[2]=90;
               allAspectModul[3]=120;allAspectModul[4]=180;
               allAspectModul[5]=Aspect.VIG18;allAspectModul[6]=Aspect.SEMINON20;allAspectModul[7]=Aspect.KVIND24;
               allAspectModul[8]=Aspect.DECL36;allAspectModul[9]=Aspect.NOVL40;allAspectModul[10]=Aspect.SKVAD45;
               allAspectModul[11]=Aspect.SEPT51;allAspectModul[12]=Aspect.KVINT72;allAspectModul[13]=Aspect.BNOV80;
               allAspectModul[14]=Aspect.SESKN100;allAspectModul[15]=Aspect.BSERT102;allAspectModul[16]=Aspect.TRDEC108;
               allAspectModul[17]=Aspect.PLKVDR135;allAspectModul[18]=Aspect.BIKV144;allAspectModul[19]=Aspect.KVIK150;
               allAspectModul[20]=Aspect.TTRNVL160;
               allAspectModul[21]=Aspect.SSX30;






    }
    public static void main (String[] argStr)
    {
        Aspect as=new Aspect();
        System.exit(0);
    }
}
