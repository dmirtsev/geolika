package ru.liveplanet.zt;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: �����
 * Date: 18.11.2010
 * Time: 11:00:52
 * To change this template use File | Settings | File Templates.
 *    ����� �� ��������� ��������� � ������������ ����� � ������� � ���� ������ ZET
 * *******
 * �������� ���������
 * ���� findZT.properties �� ����������:
//�������-������ ������������ ����� ������� geoLatLong=12.4,45.6
geoLatLong=12.4,45.6
// ������ ����������� �������� � ����� �� �����, ����������� �������
arrayAspect= CONJ0,  VIG18, SEMINON20,  KVIND24,  SSX30,  DECL36  ,  NOVL40  ,  SKVAD45,  SEPT51  ,   SEXT60 ,    KVINT72,    BNOV80,    KVDR90,    SESKN100,    BSERT102,    TRDEC108,    TRIN120,    PLKVDR135,    BIKV144,    KVIK150,150     TRSEPT154,    TTRNVL160,    OPP180, majorAspect, minorAspect
// �������������� ��������� ��� ������ �� �������� � ZET ������� & �1������ ������ �� ���. ��������� & �2������ ������ �� ���. ��������� � ��.
parametrPlus=&"American"

//����� � ��� ��� ������� �������� ������� ��������� �� ��������������� �������
monthYear=12.2010
 
******************
 * �� ������ ����� ������� zet � ����������� crt � ������ "������������������� ������������������ ����������������������  �������������������������������.crt":
 *
 *
 * 
 */
public class FindInBase {

        String a="";
    String inputGeoLatLong="";
    String inputArrayAspect="";
    String inputParPlus="";
    String inputMonthYear;
    Properties props = new Properties();
        String[] alAspect;
    String[] strLatLongName;
    double longitude=0;double latitude=0;
    int year=0; int month=0;int orb=0;
    String[] strMonthYear;
    String targetZT="";
    String namePlace="";
   double[] asArr=new double[40];
    ArrayList valueRashodiashiisyaGrad =new ArrayList(); // ��� ������������� ��������
    ArrayList valueRashodiashiisyaAsp =new ArrayList(); // ��� ������������� ��������
    ArrayList valueShodiashiisyaGrad =new ArrayList();// ��� ���������� ��������
    ArrayList valueShodiashiisyaAsp =new ArrayList();// ��� ���������� ��������
    double valueRashodiashiisyGrad;
    double valueRashodiashiisyAsp;
    double valueShodiashiisyGrad;
    double valueShodiashiisyAsp;

//    ArrayList numZnakAndGradus  =new ArrayList();
    // String[]strNumGragus=new String[2];
    int numZnak=0;
    double gradusZnak=0;
    String[] arrPlanets= {"SUN","LUN","MER","VEN","MAR","JUP","SAT","URA","NEP","PLU","NOD","SND","LIL"};
    String nameZT;
    String strDirectory;

    

    void readProp() throws IOException
    {
        /*  �������� ����� - ��� ������� ���� ���� findZT.properties
        File f = new File("findZT.properties");
        System.out.println(f.getAbsolutePath());
          */

        //props.load(new Fi leInputStream("C:\\Users\\�����\\Documents\\GEO\\Projects Java\\zt\\zt\\findZT.properties"));
        props.load(new FileInputStream("findZT.properties"));
        inputGeoLatLong=props.getProperty("geoLatLongName");
        strLatLongName = inputGeoLatLong.split(","); // �������� ������ ������ - �������
        latitude= Double.parseDouble(strLatLongName[0]);
        longitude= Double.parseDouble(strLatLongName[1]);
        namePlace=strLatLongName[2];
        //TargetZT=ZCZJ
        targetZT=props.getProperty("TargetZT");// ��� �� (=All) ��� ������ ��-��(=ZSZJ)

        orb=Integer.parseInt(props.getProperty("Orb"));   // �������� ���� ��� ������������ �������, ������ 2 �������

        inputArrayAspect=props.getProperty("arrayAspect");
        inputParPlus=props.getProperty("parPlus");
        alAspect = inputArrayAspect.split(","); // �������� ������ ��������, ������� ���������� ���������
        inputMonthYear= props.getProperty("monthYear");
        strMonthYear=inputMonthYear.split("\\."); // �������� ������ �����-��� ��� ������� ������� ���������
        month=Integer.parseInt(strMonthYear[0]);
        year=Integer.parseInt(strMonthYear[1]);
        Geolika geol = new Geolika(year,month,1,1,longitude,latitude);
        System.out.println("������ ��������� "+geol.naclEkl);
        geol.getZT(geol.naclEkl); //�������� ������ doubl� geol.ztoch

        for (int j=0;j<asArr.length;j++)
        {
          asArr[j]=777;
        }
        /*
        for (int i=0;i<geol.ztoch.length;i++)
        {
        System.out.println(geol.ztoch[i]);    
        }
          */
        cycleZT(geol);  //������� ����������

    }

    void cycleZT(Geolika geol) throws FileNotFoundException //������� ����������
{
// ����� - �� �� � �� ��� �� ���� �������... - ����� �����, ���� �� ��������� �������� - ����� �� � ��
    int cntZT=0;
    if (targetZT.equals("ZCZJ")) cntZT =2; else // ������ �� �� � ��
    if (targetZT.equals("All")) cntZT =4;       // �� ���� ��

    createDir(namePlace,inputMonthYear);  // ��������� ����������

    for (int i=0;i<cntZT;i++)
    {
        switch (i)
            {
              case 0:nameZT="���������"; break;
              case 1:nameZT="������������"; break;
              case 2:nameZT="��������"; break;
              case 3:nameZT="���������"; break;
            }
        valueRashodiashiisyaGrad.clear();
        valueRashodiashiisyaAsp.clear();
    System.out.println(geol.ztoch[i]);
    cycleAspect(geol.ztoch[i], i);  /// ������� � ��������� ������ ��������, ���������� ��� asArr
        // ��� �� ����� ����� � ����� �������� �������� �� ������
        // ������ ����� ��������� � ������ ������, �������� �������� � ������ ������� - ������������ � ����������
        insertValue(geol.ztoch[i]);// ��������� �������� ������������ � ���������� �������� valueRashodiashiisya � valueShodiashiisya
       // valueRashodiashiisy= (double[]) valueRashodiashiisya.get(0);


       try {
           workWithValue(valueRashodiashiisyaGrad,valueRashodiashiisyaAsp, false);     //������� �������� ������� ����������� � ����������� �� ��������� ��������
           workWithValue(valueShodiashiisyaGrad,valueShodiashiisyaAsp, true);
       } catch (FileNotFoundException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }


        // truncValueInZodiak(geol.ztoch[i]);  //��������� strNumGragus - ����� ����� � �������
        System.out.println("���� "+numZnakZodiakToName(numZnak)+"  ������ "+ gradusZnak +" ��� ���������� "+ geol.ztoch[i]);
    };
     System.out.println("*****");
}

    private void createDir(String namePlace, String strMonthYear)
    {
        String aspForPath="";
        for (int i=0;i<alAspect.length; i++)
        {
            aspForPath=aspForPath+alAspect[i]+"_";
        }

        try{

        strDirectory ="zetFindBase/"+namePlace +"_AEPOHA_"+strMonthYear+"_"+aspForPath;

    // Create one directory
    boolean success = (new File(strDirectory)).mkdir();
    if (success) {
      System.out.println("Directory: " + strDirectory + " created");
    }


    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
  }
    

    //}

    /**
     * ������� �������� ������� ����������� � ����������� �� ��������� ��������
     * @param grad
     * @param asp
     */
    void workWithValue(ArrayList grad, ArrayList asp, boolean shod) throws FileNotFoundException
    {
        String shodRashod="";
     if (shod) shodRashod="����"; else shodRashod="������";
        for (int i=0;i< grad.size(); i++) // ������� �� ���������
        {
            valueRashodiashiisyGrad= Double.parseDouble(grad.get(i).toString());
            valueRashodiashiisyAsp = Double.parseDouble(asp.get(i).toString());

            //truncValueInZodiak(Double.parseDouble((valueRashodiashiisya.get(i).toString())));    //��������� numZnak � gradusZnak - ����� ����� � �������
             truncValueInZodiak(valueRashodiashiisyGrad);

            for (int j=0;j<arrPlanets.length; j++) //������� �� ������� ������
            {                                                                                                   
                try
                {
                    PrintWriter out1 = new PrintWriter   // ����� � �������� ������ �����
                      (new OutputStreamWriter          // �����-���������������
                         (new FileOutputStream         // ����� ������ ������ � ����
                            //("zetFindBase\\"+arrPlanets[j]+" ������ "+valueRashodiashiisyAsp+"  "+nameZT+" "+namePlace+".crt"), "Cp1251"));
                              (strDirectory+"\\"+arrPlanets[j]+"_"+nameZT+"_"+ this.valueRashodiashiisyAsp +"_"+shodRashod+"_"+namePlace+".crt"), "Cp1251"));
                    
                                           //namePlace +"_date_"+strMonthYear+"\\"

                if (proverkaNaDiap30(gradusZnak))
                {

                //String strWork="";

                System.out.println("**** ������ ��� ���������� �����");
                System.out.println("x1:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak)+"["+ (gradusZnak-orb)+"-"+(gradusZnak+orb)+"];");
                System.out.println("x1");
                    out1.println("x1:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak)+"["+ (gradusZnak-orb)+"-"+(gradusZnak+orb)+"];");
                out1.println("x1");
                    

                } //if (proverkaNa30(gradusZnak))
                   else
                  if (gradusZnak-orb>0)

                   {

                       System.out.println("�� ������ �������� ������ + ��� > 30 " +gradusZnak + " ����� ������ ����������");
                       System.out.println("****1 ������ ������ ��� ���������� �����");
                       System.out.println("x1:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak)+"["+ (gradusZnak-orb)+"-29.99999];");
                       System.out.println("x2:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak+1)+"[0-"+(gradusZnak+orb-30)+"];");
                       System.out.println("x1|x2");
                       //**
                       out1.println("x1:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak)+"["+ (gradusZnak-orb)+"-29.99999];");
                       out1.println("x2:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak+1)+"[0-"+(gradusZnak+orb-30)+"];");
                       out1.println("x1|x2");

                   } else
                  {
                        System.out.println("�� ������ �������� ������ - ��� < 0 " +gradusZnak + " ����� ������ ����������");
                        System.out.println("****2 ������ ������ ��� ���������� �����");
                        System.out.println("x1:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak-1)+"["+ (gradusZnak-orb+30)+"-29.99999];");
                        System.out.println("x2:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak)+"[0-"+(gradusZnak+orb)+"];");
                        System.out.println("x1|x2");
                       //**
                        out1.println("x1:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak-1)+"["+ (gradusZnak-orb+30)+"-29.99999];");
                        out1.println("x2:="+arrPlanets[j]+"."+numZnakZodiakToName(numZnak)+"[0-"+(gradusZnak+orb)+"];");
                        out1.println("x1|x2");
                  }

                out1.close();} 

                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }



            }

        }
    }


    /**
     * ������� �� �� ������� �������� ����� ������� ��� ����������� - ��������� �������� ����
     * @param gradus
     * @return
     */
    boolean proverkaNaDiap30(double gradus)
    {
        if (((gradus+orb)<30) & ((gradus-orb)>0))       return true;
        else return false;
    }

    /**
     * �������� ����� ����� � ������ � ������� ����� �������
     * @param value
     */
    void truncValueInZodiak(double value)
    {
       int numZnakZodiak= (int) (value/30)+1; // ����� ����� �������
        value=value-(numZnakZodiak-1)*30;
        //System.out.println("����� ����� "+numZnakZodiak+"  ������ "+ value);
        gradusZnak= value;
        numZnak=numZnakZodiak;
        // numZnakAndGradus.add(strNumGragus);
//        System.out.println("2 ����� ����� "+strNumGragus[1]+"  ������ "+ strNumGragus[0]);
        //return strNumGragus;
    }
    /**
     * ������� ������ ����� ������� � ������������
     * @param numZnakZodiak
     * @return
     */
 String numZnakZodiakToName (int numZnakZodiak)
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
          case 11: nameZnakZodiak="�������"; break;
          case 12: nameZnakZodiak="����"; break;
      }
     return nameZnakZodiak;
 }
    private void insertValue(double zt)
    {
        //double [] val = new double[2];
        //double [] valShod ={1,2};        double [] valRashod ={1,2};
        //double [][]val=new double [40][40];

        // ������� �������� ��� 777 � ���������� ��������
        for (int i=0; i<asArr.length; i++)
        {
            if (asArr[i]<777.0)
            {
              //val[i][0]=ZTLib.diap360(zt+asArr[i]); val[i][1]=asArr[i];
              //valueRashodiashiisya.add(Double.valueOf(ZTLib.diap360(zt+asArr[i])));
                valueRashodiashiisyaGrad.add(Double.valueOf(ZTLib.diap360(zt+asArr[i])));
                valueRashodiashiisyaAsp.add(Double.valueOf(asArr[i]));

                valueShodiashiisyaGrad.add(Double.valueOf(ZTLib.diap360(zt-asArr[i])));
                valueShodiashiisyaAsp.add(Double.valueOf(asArr[i]));

                /*
              valShod[0]=ZTLib.diap360(zt-asArr[i]); valShod[1]=asArr[i];
              //valueShodiashiisya.add(Double.valueOf(ZTLib.diap360(zt-asArr[i])));
                valueShodiashiisya.add(valShod);
                //val[0]=122; val[1]=12;
                */
            }
        }
    }

    void cycleAspect (double zt, int numZT)     // ������� � ��������� ������ ��������, ���������� ��� asArr
    {
        for (int i=0;i<alAspect.length;i++)
        {
            if (alAspect[i].contains("Aspect"))
            {
            findMassivAspectsValue(alAspect[i]);
            }
            else
            {
            findAspectsValue(alAspect[i], i);
            }
        }
         System.out.println();
    }


    /**
     * ��������� �������� ������ ������� ��������, �� ������� ����� ����� ������������ ��������
     * @param aspName
     * @return
     */
    void findMassivAspectsValue(String aspName)
    {
       if (aspName.equals("majorAspectModul"))
        {
          asArr[0]=Aspect.CONJ0;asArr[1]=Aspect.SEXT60;asArr[2]=Aspect.KVDR90;
        asArr[3]=Aspect.TRIN120;asArr[4]=Aspect.OPP180;

        }
        if (aspName.equals("AspectForInetrpretGeolika")) // ��������, ������������ � ������ ����� � Geolika
         {
           asArr[0]=Aspect.CONJ0;asArr[1]=Aspect.SEXT60;asArr[2]=Aspect.KVINT72;asArr[3]=Aspect.KVDR90;
           asArr[4]=Aspect.TRIN120;asArr[5]=Aspect.KVIK150;asArr[6]=Aspect.OPP180;

         }

        if (aspName.equals("minorAspectModul"))
        {
             asArr=new double[22];

        asArr[0]=Aspect.VIG18;asArr[1]=Aspect.SEMINON20;asArr[2]=Aspect.KVIND24; asArr[3]=Aspect.SSX30;
        asArr[4]=Aspect.DECL36;asArr[5]=Aspect.NOVL40;asArr[6]=Aspect.SKVAD45;
        asArr[7]=Aspect.SEPT51;asArr[8]=Aspect.KVINT72;asArr[9]=Aspect.BNOV80;
        asArr[10]=Aspect.SESKN100;asArr[11]=Aspect.BSERT102;asArr[12]=Aspect.TRDEC108;
        asArr[13]=Aspect.PLKVDR135;asArr[14]=Aspect.BIKV144;asArr[15]=Aspect.KVIK150;
        asArr[16]=Aspect.TTRNVL160;
        }
        if (aspName.equals("allAspectModul"))
        {
        asArr[0]=0;asArr[1]=60;asArr[2]=90;
        asArr[3]=120;asArr[4]=180;
        asArr[5]=Aspect.VIG18;asArr[6]=Aspect.SEMINON20;asArr[7]=Aspect.KVIND24;asArr[8]=Aspect.SSX30;
        asArr[9]=Aspect.DECL36;asArr[10]=Aspect.NOVL40;asArr[11]=Aspect.SKVAD45;
        asArr[12]=Aspect.SEPT51;asArr[13]=Aspect.KVINT72;asArr[14]=Aspect.BNOV80;
        asArr[15]=Aspect.SESKN100;asArr[16]=Aspect.BSERT102;asArr[17]=Aspect.TRDEC108;
        asArr[18]=Aspect.PLKVDR135;asArr[19]=Aspect.BIKV144;asArr[20]=Aspect.KVIK150;
        asArr[21]=Aspect.TTRNVL160;
        }
        if (aspName.equals("erthqAspectModul"))
        {
         asArr=new double[10];

        asArr[0]=Aspect.CONJ0;asArr[1]=Aspect.SEXT60;asArr[2]=Aspect.KVDR90;
        asArr[3]=Aspect.TRIN120;asArr[4]=Aspect.OPP180;

        
        }
    };

    /**
     * ��������� �������� ������ ��������� ��������, �� ������� ����� ����� ������������ ��������
     * @param aspName
     * @return
     */
    void findAspectsValue(String aspName, int num)
    {
 if (aspName.trim().equals("CONJ0")) asArr[num]=Aspect.CONJ0	;
 if (aspName.trim().equals("VIG18")) asArr[num]=Aspect.VIG18	;
 if (aspName.trim().equals("SEMINON20")) asArr[num]=Aspect.SEMINON20	;
 if (aspName.trim().equals("KVIND24")) asArr[num]=Aspect.KVIND24	;
 if (aspName.trim().equals("SSX30")) asArr[num]=Aspect.SSX30	;
 if (aspName.trim().equals("DECL36")) asArr[num]=Aspect.DECL36	;
 if (aspName.trim().equals("NOVL40")) asArr[num]=Aspect.NOVL40	;
 if (aspName.trim().equals("SKVAD45")) asArr[num]=Aspect.SKVAD45	;
 if (aspName.trim().equals("SEPT51")) asArr[num]=Aspect.SEPT51	;
 if (aspName.trim().equals("SEXT60")) asArr[num]=Aspect.SEXT60	;
 if (aspName.trim().equals("KVINT72")) asArr[num]=Aspect.KVINT72	;
 if (aspName.trim().equals("BNOV80")) asArr[num]=Aspect.BNOV80	;
 if (aspName.trim().equals("KVDR90")) asArr[num]=Aspect.KVDR90	;
 if (aspName.trim().equals("SESKN100")) asArr[num]=Aspect.SESKN100	;
 if (aspName.trim().equals("BSERT102")) asArr[num]=Aspect.BSERT102	;
 if (aspName.trim().equals("TRDEC108")) asArr[num]=Aspect.TRDEC108	;
 if (aspName.trim().equals("TRIN120")) asArr[num]=Aspect.TRIN120	;
 if (aspName.trim().equals("PLKVDR135")) asArr[num]=Aspect.PLKVDR135	;
 if (aspName.trim().equals("BIKV144")) asArr[num]=Aspect.BIKV144	;
 if (aspName.trim().equals("KVIK150")) asArr[num]=Aspect.KVIK150	;
 if (aspName.trim().equals("TRSEPT154")) asArr[num]=Aspect.TRSEPT154	;
 if (aspName.trim().equals("TTRNVL160")) asArr[num]=Aspect.TTRNVL160	;
 if (aspName.trim().equals("OPP180")) asArr[num]=Aspect.OPP180	;

    };



    public static void main(String[] argStr) throws IOException {

              FindInBase fb = new FindInBase();
        try {
            fb.readProp();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    };

};
