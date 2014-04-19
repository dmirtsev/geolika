package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 16.08.2006
 * Time: 12:23:18
 * To change this template use File | Settings | File Templates.
 */
public class parsUSCGXml {
    String xmlStr="";
    String sumTitle=""; //    <title>USGS M5+ Earthquakes</title>
    String sumDescription="";  //<description>Real-time, worldwide earthquake list for the past 7 days</description>
    String sumLink=""; //<link>http://earthquake.usgs.gov/eqcenter/recenteqsww/</link>
    String pubDate="";
    public parsUSCGXml(String xmlStr)
    {
      this.xmlStr=xmlStr;
    }

}
