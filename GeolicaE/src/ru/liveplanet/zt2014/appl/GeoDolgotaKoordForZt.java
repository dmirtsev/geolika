package ru.liveplanet.zt2014.appl;
import java.util.ArrayList;

import ru.liveplanet.zt2014.CalculateNaclEclipt;
import ru.liveplanet.zt2014.ElementZt;
import ru.liveplanet.zt2014.ZTLib;
import swisseph.SweDate;
/**
 * 
 * @author dbashkirtsev
 *
 */
public class GeoDolgotaKoordForZt  
{

	/**
	 * 
	 * @param findZt ������� ������ ����������
	 * @param typeZt ��� ���������� 1-��, 2 - ��, 3 - ��, 4 - ��  
	 * @param shir ��� ����� ������ ������ ����������
	 * @param dopusk �������� �������
	 * @param step ��� �������
	 * ���������� dopusk � step ������ ���� �������� �����, �� dopusk ���� ������   
	 * @return ����������  �������������� �������
	 */
	double getArrGeoKoord(double findGradZt, int typeZt, double shir, double dopusk, double step)
	{
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		double geogrKoord=0;
		double ztKoord=0;

		while (Math.abs(ztKoord-findGradZt )>dopusk)
		{
			ElementZt zt1 = new ElementZt(geogrKoord,shir,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
			ztKoord=zt1.getLongitude();
			System.out.println("���������� ��������������  "+ geogrKoord);
			System.out.println("Result ��  "+ztKoord);
			System.out.println("Find ��  "+ findGradZt);
			System.out.println("��������   "+ (findGradZt-ztKoord));
			
          geogrKoord=ZTLib.diap360(geogrKoord+step);
		}
		return geogrKoord;
	}
	/**
	 * ���������������� ����� ������� ��� �������� ������ � ����� ����������
	 * ��� ������ ������� - ���� ������� � ��������� ��������� ��������
	 * ��� ����� �������� - ����� ������ ��� � ������ ��� 
	 * @param findGradZt �������� ������ ����������
	 * @param typeZt ��� ����������
	 * @param shir �������������� ������
	 * @param dopusk1 ������
	 * @param step1 ���, ������ ���� ��������� ������ ��� dopusk1 
	 * @param exactGeoDolg �������� ������ �����. �������, ���� ��� ��������, ���� �� �������� exactGeoDolg=0
	 * @return 
	 */
	public double getArrGeoKoordOpt(double findGradZt, int typeZt, double shir, double dopusk1, double step1, double exactGeoDolg )
	{
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		double geogrKoord=0;
		double ztKoord=10000;
		double dopusk=2;
		double  step=2;
		double  eclShirRas=0;
		double limit=1000;//(360+geogrKoord)/step;
		int schet=0;
		boolean stop=false;
 //if (typeZt==1) // ���� ������������ ���������
 //{
	 
 //} else
 
		if (exactGeoDolg==0)// ���� ���������� ������ �� ����� ������� ������� � ������ �����������
		{
		ElementZt zt2 = new ElementZt(geogrKoord,shir,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
		ztKoord=zt2.getLongitude();
		if (Math.abs(ztKoord-findGradZt )<40)
		{
			geogrKoord=90; // ���������� �� 90 ��. ���� ������ � ������ ��������
			limit=(360+geogrKoord)/step;
		}
		ztKoord=1000;
		while (Math.abs(ztKoord-findGradZt )>dopusk )
		{
			zt2 = new ElementZt(geogrKoord,shir,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
			ztKoord=zt2.getLongitude();
		if (ztKoord==1000) {stop=true; geogrKoord=10000;System.out.println("������ ��� ������ ������ "+shir+" ��� ����������1");break; }; // �������� ������������ ������
			schet++;
		if (schet>=limit)
		{
			System.out.println("������ ��� ������ ������ "+shir+" ��� ����������2");
			//System.exit(0);
			geogrKoord=10000;
			stop=true;
			break;
		}
	          geogrKoord=ZTLib.diap360(geogrKoord+step);
		} //while (Math.abs(ztKoord-findGradZt )>dopusk )
		//System.out.println("���-��  �������� "+ schet);
		geogrKoord=ZTLib.diap360(geogrKoord-step*3);
		//System.out.println("����� 1 ��������:"+ geogrKoord+" ���:"+shir+ " ��� � "+schet+" ����� �� "+findGradZt+ " ��� "+ztKoord);
		exactGeoDolg=geogrKoord;
		} //else ztKoord=1000; // �������������� ��������� �������� 
		if (stop!=true)
		{
		schet=0;
		//System.out.println("------------------���� exactGeoDolg:"+ exactGeoDolg);
		while (Math.abs(ztKoord-findGradZt )>dopusk1)
		{
			ElementZt zt1 = new ElementZt(exactGeoDolg,shir,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
			ztKoord=zt1.getLongitude();
			if (ztKoord==1000) {exactGeoDolg=10000;System.out.println("������ ��� ������ ������ "+shir+" ��� ����������1");break; }; // �������� ������������ ������			
			//if (Math.abs(ztKoord-findGradZt )<dopusk1)
			//System.out.println("�������� ���� exactGeoDolg:"+ exactGeoDolg+" ���:"+shir+ " ��� � "+schet+" ����� �� "+findGradZt+ " ��� "+ztKoord);
			//System.out.println("Result ��  "+ztKoord);
			//System.out.println("Find ��  "+ findGradZt);
			//System.out.println("��������2 "+ (findGradZt-ztKoord));
          exactGeoDolg=ZTLib.diap360(exactGeoDolg+step1);
          schet++;
		}
		//System.out.println("���-�� ��������2 "+ schet);
		//System.out.println("����� �������� 2 ����:"+exactGeoDolg+" ���:"+shir+ " ��� � "+schet+" ����� �� "+findGradZt+ " ��� "+ztKoord);
		geogrKoord=exactGeoDolg;
//		if (ztKoord==1000) {geogrKoord=10000;System.out.println("������ ��� ������ ������ "+shir+" ��� ����������1");break; }; // �������� ������������ ������		
		}
	//}
		return geogrKoord;
	}
	/**
	 * 
	 * @param findGradZt ������� ������ ����������
	 * @param typeZt ��� ����������
	 * @param stepShir ������ ������������� ���� �� ������
	 * @param dopusk ������, �������� 0.04
	 * @param step ���, ��������  0.008
	 * @return ������ ������ GeoKoord (������� � ������ ��� ������ ��) 
	 */
	public ArrayList<GeoKoord> arrayListGeoKoordForLineZT (double findGradZt, int typeZt, double stepShir, double dopusk, double step) 
	{
		ArrayList<GeoKoord> arrKoord = new ArrayList<GeoKoord>();
		// GeoKoord gkoord= new GeoKoord();
		//double iterShir=91;
		double iterShir=0;
		//double findGeoDolg=0;
		//while (iterShir>=-73) // �� -90 �� ����������, ��� ��� �������������� �������������� ������� ������� ����������� � ���� ��������
		double findGeoDolg,findGeoDolg2,findGeoDolgStep0,findGeoDolgStepPlus,findGeoDolgStepMinus;
		boolean stop=false;
		//double  geoShir=0;
//		int typeZt=1;
		//SweDate sd=new SweDate(1964,9,16,6+52/60.);
	
		GeoDolgotaKoordForZt a = new GeoDolgotaKoordForZt();
		findGeoDolgStep0=a.getArrGeoKoordOpt(findGradZt, typeZt, 0, dopusk, step,0 );
		findGeoDolgStepPlus=a.getArrGeoKoordOpt(findGradZt, typeZt, 3, dopusk, step,0 );
		findGeoDolgStepMinus=a.getArrGeoKoordOpt(findGradZt, typeZt, -3, dopusk, step,0 );
		System.out.println(" findGeoDolgStepMinus "+(findGeoDolgStepMinus)+" findGeoDolgStep0 "+(findGeoDolgStep0)+" findGeoDolgStepPlus "+(findGeoDolgStepPlus)				);
		if (findGeoDolgStepMinus < findGeoDolgStepPlus) // ������� ������� 
			findGeoDolg=findGeoDolgStepMinus; 
		else findGeoDolg=findGeoDolgStepPlus;  
		//iterShir=90;
		while (iterShir<=89) 
		{
			//GeoKoord gkoord= new GeoKoord();
			iterShir=iterShir+stepShir;
			findGeoDolg2=a.getArrGeoKoordOpt(findGradZt, typeZt, iterShir, dopusk, step,findGeoDolg);
			if (typeZt==1) 
			{
				System.out.println(" !!!!!!!!!!- �� ���� ������� ����� ");
				stop=true;
				arrKoord=arrayZC(findGeoDolg2);
				break;
			}
			findGeoDolg=ZTLib.diap360(findGeoDolg2-3);
			if ((typeZt==2 | typeZt==4) & findGeoDolg>360)
			{
				System.out.println("!!!������� findGeoDolg"+(findGeoDolg));
				break; // ���������� �� ������� 66
			}
		//	System.out.println("������� findGeoDolg-----!"+(findGeoDolg));
			//System.out.println("������� "+(findGeoDolgStepPlus));
			//System.out.println("������ "+iterShir);
			//gkoord.setShirota(iterShir);
			//gkoord.setDolgota((findGeoDolgStepPlus));
			//arrKoord.add(new GeoKoord(findGeoDolg2,iterShir));
			arrKoord.add(new GeoKoord(ZTLib.normWestLong(findGeoDolg2),iterShir));
		}
		if (findGeoDolgStepMinus < findGeoDolgStepPlus) // ������� ������� 
			findGeoDolg=findGeoDolgStepMinus; 
		else findGeoDolg=findGeoDolgStepPlus;
		double iterShir1=0;
		//double inputGeoDolg=findGeoDolg;
		if (stop!=true) // �� ������� ����, ���� ������������ ��
		while (iterShir1>=-89) 
		{
			//GeoKoord gkoord= new GeoKoord();
			iterShir1=iterShir1-stepShir;
			findGeoDolgStepPlus=a.getArrGeoKoordOpt(findGradZt, typeZt, iterShir1, dopusk, step,findGeoDolg);
			//System.out.println("�������1 findGeoDolgStepPlus-----!"+(findGeoDolgStepPlus));
			findGeoDolg=findGeoDolgStepPlus-3;
			//System.out.println("������� findGeoDolg-----!"+(findGeoDolg));
			if ((typeZt==2 | typeZt==4) & findGeoDolgStepPlus>360) 
			{
				System.out.println("!!!������� findGeoDolg"+(findGeoDolg));
				break; // ���������� �� ������� 66
			}; // ���������� �� ������� 66
			//System.out.println("������� "+(findGeoDolgStepPlus));
			//System.out.println("������ "+iterShir1);
			//gkoord.setShirota(iterShir1);
			//gkoord.setDolgota((findGeoDolgStepPlus));
			arrKoord.add(new GeoKoord(ZTLib.normWestLong(findGeoDolgStepPlus),iterShir1));
		}
		return arrKoord;
	}
	
	public ArrayList<GeoKoord>  arrayZC(double geoKoordZC) 
	{
		ArrayList<GeoKoord> arrKoord = new ArrayList<GeoKoord>();
		double iterShir=90;
		while (iterShir>=-90) 
		{
		arrKoord.add(new GeoKoord(ZTLib.normWestLong(geoKoordZC),iterShir));
		iterShir--;
		}
		return arrKoord;
	}

	public static void main(String[] arrStr)
	{
		double findGeoDolg;
		double gradZT=60, geoShir=0;
		int typeZt=2;
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		GeoDolgotaKoordForZt a = new GeoDolgotaKoordForZt();
		/*
		ArrayList<GeoKoord> arrKoord = new ArrayList<GeoKoord>();
		
		arrKoord=a.arrayListGeoKoordForLineZT(gradZT, typeZt, 1, 0.02, 0.005);
		System.out.println("-----------------------------------------");		
		System.out.println("-----------------------------------------");
		for (int i=0;arrKoord.size()>i;i++ )
		{
			System.out.println("������ "+ arrKoord.get(i).getShirota()+" ������� "+ arrKoord.get(i).getDolgota());
			 ElementZt zt2 = new ElementZt(arrKoord.get(i).getDolgota(),arrKoord.get(i).getShirota(),new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
			 double  ztKoord=zt2.getLongitude();
			System.out.println("�������� ��������� ������� "+arrKoord.get(i).getDolgota()+
					"��� ������ "+arrKoord.get(i).getShirota()+
					" ������� ��: "+gradZT+" ������. ���-� "+ztKoord);
		}

		*/
		findGeoDolg=a.getArrGeoKoordOpt(gradZT, typeZt, geoShir, 0.02, 0.005,0 );
		ElementZt zt1 = new ElementZt(findGeoDolg,geoShir,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
		
		System.out.println("��������� �������: "+findGeoDolg+ " ��� �������� ������: "+geoShir);
		
		double ztKoord1=zt1.getLongitude();
		System.out.println("�������� ��������� ������� "+findGeoDolg+ " ������� ��: "+gradZT+" ������. ���-� "+ztKoord1);
		

	}
}
