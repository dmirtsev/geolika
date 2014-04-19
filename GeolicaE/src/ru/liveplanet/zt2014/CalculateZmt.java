package ru.liveplanet.zt2014;

import swisseph.SweDate;

public class CalculateZmt
{
	private  double shirotaEcl=0; 
public double getShirotaEcl() {
		return shirotaEcl;
	}
	
static final double VERY_SMALL=1E-10;
    private double fiSefarial;

    private double sind(double x) {
      return Math.sin(x * DEGTORAD);
    }
    private double asind(double x) {
      return (Math.asin(x) *RADTODEG);
    }

    private double cosd(double x) {
      return Math.cos(x *DEGTORAD);
    }
    private double tand(double x) {
      return Math.tan(x *DEGTORAD);
    }
    private double atand(double x) {
      return (Math.atan(x) *RADTODEG);
    }

     static double ZTKONST=16.216666;
    static final double DEGTORAD=0.0174532925199433;
    static final double RADTODEG=57.2957795130823;


    /**
     * ��������� ������ �������������� �������������� ������ � �������������
     * ������� - ������ ������� � ������������ ����������� � ����� ��������
     * � ������������� �������������� ������ � ������� ��������������� ��������� ���������
     * �� ������ ����. �� ���� ���������� ����� ������ - "������������� �������������� ������"
     * ����� ������� �������������� ������ ��������������� �� "������������� �������������� ������"
     * ���� � ������ �������  �����������
     * ���� �� ��� ��� ������ ����������
     * @param shir ������ ���������
     * @param dol ������� ���������
     * @param ecl
     * @return ��������������� ������
     * conversion between ecliptical and equatorial polar coordinates.

     */
    private double cotrans(double shir,double dol,double ecl)
    {
        dol=dol+ZTKONST;
        return ((asind((sind(shir)*cosd(ecl))-(cosd(shir)*sind(ecl)*sind(dol)))));
     }

public double degnorm(double x)
{
    double y;
    y = x%360.0;
    if (Math.abs(y) < 1e-13) {
      y = 0;
    }
    if( y < 0.0 ) {
      y += 360.0;
    }
    return(y);
  }


    /**
     * ������ ����������. ������������ � �������� ��������
     *
     * @param x1
     * @param f
     * @param sine
     * @param cose
     * @return
     */
    private double Asc1 (double x1, double f, double sine, double cose) {
      int n;
      double ass;
      x1 = degnorm(x1);
      n  = (int) ((x1 / 90) + 1);
      if (n == 1) {
        ass = ( Asc2 (x1, f, sine, cose));
      } else if (n == 2) {
        ass = (180 - Asc2 (180 - x1, - f, sine, cose));
      } else if (n == 3) {
        ass = (180 + Asc2 (x1 - 180, - f, sine, cose));
      } else {
        ass = (360 - Asc2 (360- x1,  f, sine, cose));
      }
      ass = degnorm(ass);
      if (Math.abs(ass - 90) < VERY_SMALL)
      {
        ass = 90;
      }
      if (Math.abs(ass - 180) < VERY_SMALL) {
        ass = 180;
      }
      if (Math.abs(ass - 270) < VERY_SMALL)
      {
        ass = 270;
      }
      if (Math.abs(ass - 360) < VERY_SMALL) {
        ass = 0;
      }
      return ass;
    }

    /**
     * ��������������� ����� ��� ������� ����������.
     * ����� ��������� �� ���� ��������������� ��������
     * ��������
     * @param x
     * @param f
     * @param sine
     * @param cose
     * @return
     */

    private double Asc2 (double x, double f, double sine, double cose) {
   //   int n;
      double ass, sinx;
      ass = - tand(f) * sine + cose * cosd(x);
      if (Math.abs(ass) < VERY_SMALL) {
        ass = 0;
      }
      sinx = sind(x);
      if (Math.abs(sinx) < VERY_SMALL) {
        sinx = 0;
      }
      if (sinx == 0) {
        if (ass < 0) {
          ass = -VERY_SMALL;
        } else {
          ass = VERY_SMALL;
        }
      } else if (ass == 0) {
        if (sinx < 0) {
          ass = -90;
        } else {
          ass = 90;
        }
      } else {
        ass = atand(sinx / ass);
      }
      if (ass < 0) {
        ass = 180 + ass;
      }
      return (ass);
    } /* Asc2 */



    /**
     * �����, ����������� ���������� �� ���������� ������� � ������ ���������
     * @param geolon �������������� �������
     * @param fiekv  �������������� ������
     * @param ekl ������ ���������
     * @return ������ ����������
     * double[0] ���������
     * double[1] ������������
     * double[2] ���������
     * double[3] ��������
     */
    public double[] zemletochka(double geolon,double fiekv, double ekl)
      {

        double[] zt= {0,0,0,0};
        double  tant,fi; //tane,tanfi,
        double sine, cose;
        cose  = cosd(ekl);
        //sine  = sind(ekl);
        //tane  = tand(ekl);

          /**
           * G������������� �������������� ������ (�� ����� ������� ��������������) � �������������
           * �������������� ������
           * ��� ���� �� ��������� ������������� ������ ����������
           */
          fi=this.shirotaEcl=cotrans(fiekv,geolon,ekl);
          
         // System.out.println("������ .������ ="+ fi);
          fiSefarial=fiekv;
          /**=
           * ���� �������� � ������������� ������.
           * ��������� ������� ����� �� ��������������� ����� �������� ��������, �� ��������
           * ������������ � �������������� �������
           * ������� ������� ������ ����������
           */
       geolon=geolon+ZTKONST;

          /**
           * ���������� �������� ������
           */
        if (Math.abs(Math.abs(fi) - 90) < VERY_SMALL) {
          if (fi < 0) {
            fi = -90 + VERY_SMALL;
          } else {
            fi = 90 - VERY_SMALL;
          }
        }
       // tanfi = tand(fi);
        /** ���������� ��������� � �������� (�������������� ���������)
         * ������� �� �������������� � �������� ����������� �� - �������� ����
         *  ��, ��������, ��� ����������� ������� � ��������� �� ������� �����
         *  */

        if (Math.abs(geolon - 90) > VERY_SMALL
          && Math.abs(geolon - 270) > VERY_SMALL) {
          tant = tand(geolon);
          zt[0] = atand(tant / cose);
          if (Math.abs(geolon) > 90 && Math.abs(geolon) <= 270) {
            zt[0] = degnorm(zt[0] + 180);
          }
        } else {
          if (Math.abs(geolon - 90) <= VERY_SMALL) {
            zt[0] = 90;
          } else {
            zt[0] = 270;
          }
        }

          /**
           * � ������ ������������ �������� ��������� � ���������
           */
        zt[0] = degnorm(zt[0]);
        zt[2] = degnorm(zt[0]+180);

        /**
         * �������� ������ ���������� ��������, �������� , �������������� � ������������� �� ������ ����������
         * � ������ ���������� ����������� �� ������ ����� ������������ �� (���������) ��������� ����������
         * �������������
         * ������������ ����������� �� ������ ���������
         * */
        cose  = cosd(ekl);
        sine  = sind(ekl);
        if ((fi>0 & fi<66) | (fi<0 & Math.abs(fi)<66)) // ����������� ��� ������, ��� ������� ���- ���� ��������
        { 	
        zt[1] = Asc1 (geolon + 90, fi, sine, cose); // ��� ����������� ��� ���
        zt[3] = degnorm(zt[1]+180);
        }
        else {
        	//System.out.println"!!!������������ �� � ��! ������ ������������� ������������� �� 66 ��������: "+fi);
            zt[1] = 1000;
            zt[3] = 1000;
			
		}

          return zt;
      }
  public static void main(String[] str)
  {
      CalculateZmt gp =new CalculateZmt();
      SweDate sd=new SweDate(1964,9,16,6+52/60.);
      //NaclEclipt naclonEcl= new NaclEclipt(2000,1,1,0);
      //double [] zemlet= gp.zemletochka(270.44447299198987,-46.0,new CalculateNaclEclipt(sd.getJulDay()).naklEcl);
     double [] zemlet= gp.zemletochka(-156,45.0,new CalculateNaclEclipt(sd.getJulDay()).naklEcl);
      //double [] zemlet= gp.zemletochka(-115,43.0,new CalculateNaclEclipt(sd.getJulDay()).naklEcl);
      
      double shirotaEcl =gp.getShirotaEcl();
      // ����� ��� �������� � ��������  �������� ������ ���������� ��� ���������, ��� ��� �� 2000 ��� ��� ���������� �� 
      // �������� �������, ��� ���� � ����������� ���������� �������� ���� ������� �������
      // ��� ���:
      //double [] zemlet= gp.zemletochka(37.617633,55.755786,23.4440459607407);
      //System.out.println("������ ���������\n"+ naclonEcl.naklEcl); // �����
      System.out.println("���������\n"+ zemlet[0]+" ���������� ��������"); // �����
      System.out.println("������������\n"+ zemlet[1]+" ���������� ��������"); // �����
      System.out.println("������������� ������\n"+ shirotaEcl); // �����
  }
  }