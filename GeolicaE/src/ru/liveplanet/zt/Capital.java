package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 08.10.2009
 * Time: 16:17:07
 * To change this template use File | Settings | File Templates.
 * ������������ bat-����� � ����������� ��������� ��� ������ ���������� �������, ���������� ������� ������� �� �����
 * ����� ��� bat-����� �����:
echo ------------- > PlKvZyaLondon.txt
echo ������� ������� � ������������ ������� >> PlKvZyaLondon.txt
echo ------------->> PlKvZyaLondon.txt
java Transits -p9 -lon1.72 -btoday -r -n12 -utnow -oloc >> PlKvZyaLondon.txt
echo ------------->> PlKvZyaLondon.txt
echo ������� ������� � ������������ ������>> PlKvZyaLondon.txt
echo ------------->> PlKvZyaLondon.txt
java Transits -p9 -lon5.72 -btoday -r -n12 -utnow -oloc >> PlKvZyaLondon.txt
echo ------------->> PlKvZyaLondon.txt

 */
public class Capital
{
    double geoSirota;
    double geoDolgota;
    String kodStrani;
    String capitalName;
    String stranaName;
    boolean g20;

}
