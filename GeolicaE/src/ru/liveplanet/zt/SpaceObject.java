package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 16.10.2009
 * Time: 16:28:35
 * To change this template use File | Settings | File Templates.
 */

/**
 * Небесный объект
 *
 */
public class SpaceObject
{
    double lambda; // эклиптическая долгота
    double betta;// эклиптическая широта
    double alfa;// прямое восхождение
    double sigma;// склонение
    double distans;// дистанция в АЕ
    double speedLambda;// скорость по долготе
    double speedBetta;// скорость по широте
    double speedDistans;// скорость удаления
    double soDolgota; // географическая долгота небесного объекта
    double soShirota; // географическая широта небесного объекта
    int priznak;//1-Планеты;2-звезды;3-астероиды
    String nameSpaceObject; //Наименование - идентификатор объекта
}
