package ru.liveplanet.zt2014;

import swisseph.SweDate;

public class ElementZt extends Element {

	/**
	 * 
	 * @param dolg �������������� �������
	 * @param shir �������������� ������
	 * @param naclEcl �������� ������� ��������� �� ����
	 * @param typeZT ��� ����������
	 * 0 - ���������
	 * 1 - ������������
	 * 2 - ���������
	 * 3 - ��������
	 */
	public ElementZt(double dolg,double shir,double naclEcl, int typeZT) {
		// TODO Auto-generated constructor stub
		CalculateZmt czt=new CalculateZmt();
		double []zemlet=czt.zemletochka(dolg, shir, naclEcl);
		switch (typeZT) {
		case 1:
			this.setLongitude(zemlet[0]);
			break;
		case 2:
			this.setLongitude(zemlet[1]);
			break;
		case 3:
			this.setLongitude(zemlet[2]);
			break;
		case 4:
			this.setLongitude(zemlet[3]);
			break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		// ���������
   ElementZt zt = new ElementZt(37.617633,55.755786,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, 1);
   System.out.println("���������: "+zt.getLongitude());
	// ������������
   ElementZt zt1 = new ElementZt(37.617633,55.755786 ,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, 2);
   System.out.println("������������: "+zt1.getLongitude());

	// ���������
   ElementZt zt2 = new ElementZt(37.617633,55.755786,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, 3);
   System.out.println("���������: "+zt2.getLongitude());

	// ��������
   ElementZt zt3 = new ElementZt(37.617633,55.755786,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, 4);
   System.out.println("��������: "+zt.getLongitude());

	}

}
