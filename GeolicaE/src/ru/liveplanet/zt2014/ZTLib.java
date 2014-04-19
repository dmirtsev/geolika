package ru.liveplanet.zt2014;

import swisseph.SMath;
import swisseph.SweConst;
/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 24.04.2006
 * Time: 10:41:26
 * To change this template use File | Settings | File Templates.
 */
public class ZTLib {

    static final double DEGTORAD=0.0174532925199433;
    static final double RADTODEG=57.2957795130823;

    /**
     *
     * @param numPlanet
     * @return
     */
    public String numToNameZodiak(int num)
    {
        String nameZodiak="";
        switch (num)
                {case (1): nameZodiak="Ari";break;
                case (2): nameZodiak="Tau";break;
                case (3): nameZodiak="Gem";break;
                case (4): nameZodiak="Can";break;
                case (5): nameZodiak="Leo";break;
                case (6): nameZodiak="Vir";break;
                case (7): nameZodiak="Lib";break;
                case (8): nameZodiak="Sco";break;
                case (9): nameZodiak="Sag";break;
                case (10): nameZodiak="Cap";break;
                case (11): nameZodiak="Aqu";break;
                case (12): nameZodiak="Pic";break;
        }
        return nameZodiak;
    }

    
 public static String PlanetFromNum(int numPlanet)
    {
        String str="";
        switch (numPlanet)
        {
        case 0: str="SUN"; break;
        case 1: str="MOON";break;
        case 2: str="MERCURY"; break;
        case 3: str="VENUS";break;
        case 4: str="MARS";break;
        case 5: str="JUPITER";break;
        case 6: str="SATURT";break;
        case 7: str="URAN";break;
        case 8: str="NEPTUN";break;
        case 9: str="PLUTON";break;
        case 10: str="MEAN_NODE";break;
        case 11: str="TRUE_NODE";break;
        case 12: str="LILIT";break;
   }
        return str;
    }

    public static String AspektFromNum(int numAspekt)
       {
           /*
           CONJ0= 0;// ����������
             VIG18=   18; //��������� (������, ����������, ����������)
             SEMINON20=  20;// �����������
             KVIND24=  24;// ����������
             DECL36  =36 ;// ������
             NOVL40  =40 ;// ������(�������)
             SKVAD45=45 ;//�����������(������)
             SEPT51  =51.43 ;//�������
              SEXT60 =60 ;//�������� (������������)
               KVINT72=72 ;//��������
               BNOV80=80 ;//�������� (���������)
               KVDR90=90 ;//�������
               SESKN100=100 ;//�����������
               BSERT102=102.86 ;//���������?
               TRDEC108=108  ;//���������
               TRIN120=120 ;//����
               PLKVDR135=135 ;//��������������� (�������������)
               BIKV144=144 ;//���������
               KVIK150=150 ;//�������
               TRSEPT154=154.29 ;//����������?
               TTRNVL160=160 ;//����������� (�����������)
               OPP180=180 ;//���������
             */
           String str="";
           switch (numAspekt)
           {
           case 0: str="Soedinet0"; break;
           case 18: str="Vigintil18";break;
           case 20: str="Seminonagon20"; break;
           case 36: str="Decil36";break;
           case 40: str="Novil40";break;
           case 45: str="Poukvadrat45";break;
           case 51: str="Septil51";break;
           case 60: str="Sextil60";break;
           case 72: str="Kvintil";break;
           case 80: str="BiNovil80";break;
           case 90: str="Kvadrat90";break;
           case 102: str="BiSeptil102";break;
           case 108: str="TriDecil108";break;
           case 120: str="Trin120";break;
           case 135: str="PolutoraKvadrat135";break;
           case 144: str="BiKvintil144";break;
           case 150: str="Kvikons150";break;
           case 154: str="TriSeptil154";break;
           case 160: str="TetraNovil160";break;
           case 180: str="Oppoziciya180";break;
      }
           return str;
       }

    /**
     *
     * @param ZJavn
     * @param ZCel
     * @return
     * ���������� ������, ��� 0-��,1-��,2-��,3-��
     */
    public static double [] fracAbsTZT(int [] ZJavn, int [] ZCel)
    {
        double [] absZT=new double[4];
        //absZT[0]= (ZJavn[1]+ZJavn[2]/60)+(ZJavn[0]+1)*30; // ������ � ������ ������������
        absZT[0]= (ZJavn[1]+(double)ZJavn[2]/60)+(ZJavn[0])*30; // ������ � ������ ������������
        absZT[2]=absZT[0]+180;
        if (absZT[2]>360) absZT[2]=absZT[2]-360; // ��������� ��������

        //absZT[1]= (ZCel[1]+ZCel[2]/60)+(ZCel[0]+1)*30; // ������ � ������ ������������
        absZT[1]= (ZCel[1]+(double)ZCel[2]/60)+(ZCel[0])*30; // ������ � ������ ������������
        absZT[3]=absZT[1]+180;
                if (absZT[3]>360) absZT[3]=absZT[3]-360; // ��������� ��������


        return absZT;
    }
    /**
 * �������� ���������� ������ ��� ����������� ���� ��� ����� ��� orbBreak ��������
      * @param planetBreak  ������ �������������� ������
      * @return ����������� ����������
 */

    /**
     * ��������� � �������� 360
     * @param x
     * @return
     */
public static double degnorm(double x) {
    double y;
    y = x%360.0;
    if (SMath.abs(y) < 1e-13) {
      y = 0;
    }
    if( y < 0.0 ) {
      y += 360.0;
    }
    return(y);
  }

public static double normWestLong(double x) {
    double y;
    if (x>180)
    {
    	y=-180+(x-180);
    } else y=x;
    return(y);
  }


 public static double [] returnAspect(String typeZT, double koordZT, double aspect)
    {
       double [] retAsp = new double[2];
        if (aspect!=0 & aspect!=180)
        {
        retAsp[0]=koordZT+aspect;
            if (retAsp[0]>360) retAsp[0]=retAsp[0]-360;
        retAsp[1]=koordZT-aspect;
            if (retAsp[1]<0) retAsp[1]=retAsp[1]+360;
        }
        else
        {
            retAsp[0]=koordZT+aspect;
            if (retAsp[0]>360) retAsp[0]=retAsp[0]-360;
            retAsp[1]=1000;
        }
        return retAsp;
    }
    // ���������� �� ������������ ���������� �� �������  "zj"="������������"
 public static String tipZT(String zt)
    {
        if (zt.equalsIgnoreCase("zj")) zt="������������"; else
        if (zt.equalsIgnoreCase("zc")) zt="���������"; else
        if (zt.equalsIgnoreCase("zt")) zt="��������"; else
        if (zt.equalsIgnoreCase("zb")) zt="���������";
        return zt;
    }
public static double[] positionBreak(double[] planetBreak, int orbBreak)
{
   double[] plBreak;
    int deltaKoord;
    plBreak=planetBreak;
    for (int i=0;i<=plBreak.length-1;i++)
    {
        if (plBreak[i]!=0)
        {
        for (int j=i+1;j<=plBreak.length-1;j++)
        {
           deltaKoord = (int) ZTLib.swe_difdeg2n(plBreak[i], plBreak[j]);
           if (Math.abs(deltaKoord)<=orbBreak)
           {
               plBreak[j]=swe_degnorm(plBreak[j]+(orbBreak-Math.abs(deltaKoord)));
           }
        }
        }
    }

    return plBreak;
}

/**
 * ��������� �������
 * @param ddeg  ������� � �����
 * @param roundflag ���� ����������
 * 4 ������ �� ������� ,2 ������ �� ������,1 ������ �� �������,8 ���� �������
 * ������� �������� �������� 8 � ��� ���� 0 ���� ������� � ��
 */
public int[] swe_split_deg1(double ddeg, int roundflag)
{
  int[] parsPosition={0,0,0,0,0};
  double dadd;
    dadd = 0;
    // isgn - ���� �������
  int isgn = 1;
  if (ddeg < 0) {
    isgn = -1;
    ddeg = -ddeg;
  }
  if ((roundflag & SweConst.SE_SPLIT_DEG_ROUND_DEG)!=0) {
    dadd = 0.5;
  } else if ((roundflag & SweConst.SE_SPLIT_DEG_ROUND_MIN)!=0) {
    dadd = 0.5 / 60;
  } else if ((roundflag & SweConst.SE_SPLIT_DEG_ROUND_SEC)!=0) {
    dadd = 0.5 / 3600;
  }
  if ((roundflag & SweConst.SE_SPLIT_DEG_KEEP_DEG)!=0) {
    if ((int) (ddeg + dadd) - (int) ddeg > 0) {
      dadd = 0;
    }
  } else if ((roundflag & SweConst.SE_SPLIT_DEG_KEEP_SIGN)!=0) {
    if ((ddeg % 30) + dadd >= 30) {
      dadd = 0;
    }
  }
  ddeg += dadd;
  if ((roundflag & SweConst.SE_SPLIT_DEG_ZODIACAL)!=0) {
    isgn = (int) (ddeg / 30);
    ddeg = ddeg % 30;
  }
    parsPosition[0]=isgn;
// isgn - ���� �������
//ideg - ����� �������
  int ideg = (int) ddeg;
   parsPosition[1]=ideg;
  ddeg -= ideg;
// imin ����� ������
  int imin = (int) (ddeg * 60);
  parsPosition[2]=imin;
  ddeg -= imin / 60.0;
// isec ����� �������
  int isec = (int) (ddeg * 3600);
  parsPosition[3]=isec;
// dsecfr ������������
  if ((roundflag & (SweConst.SE_SPLIT_DEG_ROUND_DEG | SweConst.SE_SPLIT_DEG_ROUND_MIN | SweConst.SE_SPLIT_DEG_ROUND_SEC))==0) {
    int dsecfr = ((int)(ddeg * 3600 - isec))*10;
      parsPosition[4]=dsecfr;
  }
return parsPosition;
}  /* end split_deg */

/* Reduce x modulo 360 degrees
   */
  /**
  * Normalizes a double to the range of 0.0 >= x < 360.0.
  */
  public static double swe_degnorm(double x) {
if (Double.doubleToLongBits(x) == 0xc11cd9d69f7e189dL) {
  x = Double.longBitsToDouble(0xc11cd9d69f7e189eL);  // Zeile 66: keine Verñnderung...
} else if (Double.doubleToLongBits(x) == 0xc11d376e20594b20L) {
  x = Double.longBitsToDouble(0xc11d376e20594b21L);  // Zeile 68: keine Verñnderung...
} else if (Double.doubleToLongBits(x) == 0xc11ae8edd4666694L) {
  x = Double.longBitsToDouble(0xc11ae8edd4666695L);  // Zeile 70: keine Verñnderung...
} else if (Double.doubleToLongBits(x) == 0x409fe5e10f4cc528L) {
  x = Double.longBitsToDouble(0x409fe5e10f4cc527L);  // Zeile 79: keine Verñnderung...
}
double y;
y = x%360.0;
if (Math.abs(y) < 1e-13) {
  y = 0;   /* Alois fix 11-dec-1999 */
}
if( y < 0.0 ) {
  y += 360.0;
}
return(y);
  }

/**
  * This calculates the difference of the two angles p1, p2 and normalizes
  * them to a range of -180.0 <= x < 180.0 degrees.
  * @param p1 The angle of point 1
  * @param p2 The angle of point 2
  * @return The normalized difference between p1, p2
  */
  public static double swe_difdeg2n(double p1, double p2) {
    double dif;
    dif = swe_degnorm(p1 - p2);
    if (dif  >= 180.0) {
      return (dif - 360.0);
    }
    return (dif);
  }
    public static double diap360 (double value)
    {
         if (value<0) value=value+360;
         if (value>360) value=value-360;
      return value;
    }
       public static void truncValueInZodiak(double value)
    {
       int numZnakZodiak= (int) (value/30)+1; // ����� ����� �������
        value=value-(numZnakZodiak-1)*30;
        System.out.println("����� ����� "+numZnakZodiak+"  ������ "+ value);

    }
    /**
     * ������� ������ ����� ������� � ������������
     * @param numZnakZodiak
     * @return
     */
  public static  String numZnakZodiakToName (int numZnakZodiak)
 {
     String nameZnakZodiak="";
      switch (numZnakZodiak)
      {
          case 1: nameZnakZodiak="����"; break;
          case 2: nameZnakZodiak="�����"; break;
          case 3: nameZnakZodiak="��������"; break;
          case 4: nameZnakZodiak="���"; break;
          case 5: nameZnakZodiak="���"; break;
          case 6: nameZnakZodiak="����"; break;
          case 7: nameZnakZodiak="����"; break;
          case 8: nameZnakZodiak="��������"; break;
          case 9: nameZnakZodiak="�������"; break;
          case 10: nameZnakZodiak="�������"; break;
          case 11: nameZnakZodiak="������"; break;
          case 12: nameZnakZodiak="����"; break;

      }
     return nameZnakZodiak;
 }
public static void main (String[] argStr)
{

        double[] plBreak=new double[]{1,2,3,4,55,66,77,78,91,92};

    positionBreak(plBreak,6);

}

}
