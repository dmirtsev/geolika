package ru.liveplanet.zt;/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 08.08.2006
 * Time: 12:43:21
 * To change this template use File | Settings | File Templates.
 */
                         public class GetOpt {
                             /** The set of characters to look for */
                             protected String pattern;
                             /** Where we are in the options */
                             protected int optind = 0;
                             /** Public constant for "no more options"
                              * XXX should switch to hasNext()/next() pattern.
                              */
                             public static final int DONE = 0;
                             /** Internal flag - whether we are done all the options */
                             protected boolean done = false;

                             /** Retrieve the option index */
                             public int getOptInd() {
                                 return optind;
                             }

                             /** The option argument, if there is one. */
                             protected String optarg;

                             /** Retrieve the current option argument */
                             public String optarg() {
                                 return optarg;
                             }
     public double [] getAspectArr()

    {
        double []asArr=new double[120] ;
          String arg = optarg;

           if (arg.indexOf(',')!=-1)
        {
            String[] asArr1 =new String[arg.length()-((arg.length()-1)/2) ];
            String str;
             //plArrStr=new String[2];
           asArr1= arg.split(",");

           //plArr =new int[plArrStr.length];
              //int s=0;
            for (int i=0; i<asArr1.length; i++)
              {
                  //int c = arg(i);
                //str="Aspect."+asArr1[i];
                  asArr[i]=Double.valueOf(asArr1[i]).doubleValue();
                      //plArr[i]=Integer.valueOf(plArrStr[i]).intValue();


                //   s++;
                                                                  
              };
        }






        if (arg.equals("majorAspect"))
        {
          asArr[0]=Aspect.CONJ0;asArr[1]=Aspect.SEXT60;asArr[2]=Aspect.KVDR90;
        asArr[3]=Aspect.TRIN120;asArr[4]=Aspect.OPP180;

        asArr[5]=-Aspect.SEXT60;asArr[6]=-Aspect.KVDR90;
        asArr[7]=-Aspect.TRIN120;
        }
        if (arg.equals("minorAspect"))
        {
             asArr=new double[32];
        asArr[0]=Aspect.VIG18;asArr[1]=Aspect.SEMINON20;asArr[2]=Aspect.KVIND24;
        asArr[3]=Aspect.DECL36;asArr[4]=Aspect.NOVL40;asArr[5]=Aspect.SKVAD45;
        asArr[6]=Aspect.SEPT51;asArr[7]=Aspect.KVINT72;asArr[8]=Aspect.BNOV80;
        asArr[9]=Aspect.SESKN100;asArr[10]=Aspect.BSERT102;asArr[11]=Aspect.TRDEC108;
        asArr[12]=Aspect.PLKVDR135;asArr[13]=Aspect.BIKV144;asArr[14]=Aspect.KVIK150;
        asArr[15]=Aspect.TTRNVL160;
        asArr[16]=-Aspect.VIG18;asArr[17]=-Aspect.SEMINON20;asArr[18]=-Aspect.KVIND24;
        asArr[19]=-Aspect.DECL36;asArr[20]=-Aspect.NOVL40;asArr[21]=-Aspect.SKVAD45;
        asArr[22]=-Aspect.SEPT51;asArr[23]=-Aspect.KVINT72;asArr[24]=-Aspect.BNOV80;
        asArr[25]=-Aspect.SESKN100;asArr[26]=-Aspect.BSERT102;asArr[27]=-Aspect.TRDEC108;
        asArr[28]=-Aspect.PLKVDR135;asArr[29]=-Aspect.BIKV144;asArr[30]=-Aspect.KVIK150;
        asArr[31]=-Aspect.TTRNVL160;
        }
        if (arg.equals("allAspect"))
        {
        asArr[0]=0;asArr[1]=60;asArr[2]=90;
        asArr[3]=120;asArr[4]=180;
        asArr[5]=Aspect.VIG18;asArr[6]=Aspect.SEMINON20;asArr[7]=Aspect.KVIND24;
        asArr[8]=Aspect.DECL36;asArr[9]=Aspect.NOVL40;asArr[10]=Aspect.SKVAD45;
        asArr[11]=Aspect.SEPT51;asArr[12]=Aspect.KVINT72;asArr[13]=Aspect.BNOV80;
        asArr[14]=Aspect.SESKN100;asArr[15]=Aspect.BSERT102;asArr[16]=Aspect.TRDEC108;
        asArr[17]=Aspect.PLKVDR135;asArr[18]=Aspect.BIKV144;asArr[19]=Aspect.KVIK150;
        asArr[20]=Aspect.TTRNVL160;
        asArr[21]=-60;asArr[22]=-90;
        asArr[23]=-120;
        asArr[24]=-Aspect.VIG18;asArr[25]=-Aspect.SEMINON20;asArr[26]=-Aspect.KVIND24;
        asArr[27]=-Aspect.DECL36;asArr[28]=-Aspect.NOVL40;asArr[29]=-Aspect.SKVAD45;
        asArr[30]=-Aspect.SEPT51;asArr[31]=-Aspect.KVINT72;asArr[32]=-Aspect.BNOV80;
        asArr[33]=-Aspect.SESKN100;asArr[34]=-Aspect.BSERT102;asArr[35]=-Aspect.TRDEC108;
        asArr[36]=-Aspect.PLKVDR135;asArr[37]=-Aspect.BIKV144;asArr[38]=-Aspect.KVIK150;
        asArr[39]=-Aspect.TTRNVL160;
        }
        if (arg.equals("erthqAspect"))
        {
         asArr=new double[10];

        asArr[0]=Aspect.CONJ0;asArr[1]=Aspect.SEXT60;asArr[2]=Aspect.KVDR90;
        asArr[3]=Aspect.TRIN120;asArr[4]=Aspect.OPP180;

        asArr[5]=-Aspect.SEXT60;asArr[6]=-Aspect.KVDR90;
        asArr[7]=-Aspect.TRIN120;
        asArr[8]=Aspect.KVIK150;asArr[9]=-Aspect.KVIK150;
        }


        if (arg.equals("CONJ0")) asArr[0]=Aspect.CONJ0	;
 if (arg.equals("VIG18")) asArr[0]=Aspect.VIG18	;
 if (arg.equals("SEMINON20")) asArr[0]=Aspect.SEMINON20	;
 if (arg.equals("KVIND24")) asArr[0]=Aspect.KVIND24	;
 if (arg.equals("DECL36")) asArr[0]=Aspect.DECL36	;
 if (arg.equals("NOVL40")) asArr[0]=Aspect.NOVL40	;
 if (arg.equals("SKVAD45")) asArr[0]=Aspect.SKVAD45	;
 if (arg.equals("SEPT51")) asArr[0]=Aspect.SEPT51	;
 if (arg.equals("SEXT60")) asArr[0]=Aspect.SEXT60	;
 if (arg.equals("KVINT72")) asArr[0]=Aspect.KVINT72	;
 if (arg.equals("BNOV80")) asArr[0]=Aspect.BNOV80	;
 if (arg.equals("KVDR90")) asArr[0]=Aspect.KVDR90	;
 if (arg.equals("SESKN100")) asArr[0]=Aspect.SESKN100	;
 if (arg.equals("BSERT102")) asArr[0]=Aspect.BSERT102	;
 if (arg.equals("TRDEC108")) asArr[0]=Aspect.TRDEC108	;
 if (arg.equals("TRIN120")) asArr[0]=Aspect.TRIN120	;
 if (arg.equals("PLKVDR135")) asArr[0]=Aspect.PLKVDR135	;
 if (arg.equals("BIKV144")) asArr[0]=Aspect.BIKV144	;
 if (arg.equals("KVIK150")) asArr[0]=Aspect.KVIK150	;
 if (arg.equals("TRSEPT154")) asArr[0]=Aspect.TRSEPT154	;
 if (arg.equals("TTRNVL160")) asArr[0]=Aspect.TTRNVL160	;
 if (arg.equals("OPP180")) asArr[0]=Aspect.OPP180	;


        return asArr;
    };
    public int[] getPlanetArr()

    {
        int[] plArr ;
        String[] plArrStr ;
        String arg = optarg;
        if (arg.indexOf('-')!=-1)
        {
           plArrStr=new String[2];
           plArrStr= arg.split("-");

            plArr= new int[(Integer.valueOf(plArrStr[1]).intValue())-(Integer.valueOf(plArrStr[0]).intValue()-1)];

            int s=0;
            for (int i=Integer.valueOf(plArrStr[0]).intValue(); i<Integer.valueOf(plArrStr[1]).intValue()+1; i++)
              {
                  //int c = arg(i);
                      plArr[s]=Integer.valueOf(plArrStr[0]).intValue()+s;

                   s++;

              };




        }
        else
        {

            plArrStr =new String[arg.length()-((arg.length()-1)/2) ];

             //plArrStr=new String[2];
           plArrStr= arg.split(",");
           plArr =new int[plArrStr.length];
              //int s=0;
            for (int i=0; i<plArrStr.length; i++)
              {
                  //int c = arg(i);
                      plArr[i]=Integer.valueOf(plArrStr[i]).intValue();

                //   s++;

              };


            /*
            int s=0;
        for (int i=0; i<arg.length(); i++)
          {
              //int c = arg(i);
              char c = arg.charAt(i);
              if (c!=',')
              {
                  plArrStr[s]= String.valueOf(c);
                  plArr[s]=Integer.valueOf(plArrStr[s]).intValue();
              s++;}


          };

          */

        }
    return plArr;
    }



    ;


                             /* Construct a GetOpt object, storing the set of option characters. */
                             public GetOpt(String patt) {
                                 pattern = patt;
                                 rewind();
                             }

                             public void rewind() {
                                 done = false;
                                 optind = 0;
                             }

                             /** Return one argument.
                              */
                             public char getopt(String argv[]) {
                                 if (optind == (argv.length)) {
                                     done = true;
                                 }

                                 // Do not combine with previous if statement.
                                 if (done) {
                                     return DONE;
                                 }

                                 // Pick off the next command line argument, check if it starts "-".
                                 // If so look it up in the list.
                                 String thisArg = argv[optind++];
                                 if (thisArg.startsWith("-")) {
                                     optarg = null;
                                     for (int i=0; i<pattern.length(); i++) {
                                         char c = pattern.charAt(i);
                                         if (thisArg.equals("-"+c)) {	// we found it
                                             // If it needs an option argument, get it.
                                             if (i+1 < pattern.length() &&
                                                 pattern.charAt(i+1)==':' &&
                                                 optind < argv.length)
                                                 optarg = argv[optind++];
                                             return c;
                                         }
                                     }
                                     // Still no match, and not used all args, so must be error.
                                     return '?';
                                 } else {
                                     // Found non-argument non-option word in argv: end of options.
                                     optind--;
                                     done = true;
                                     return DONE;
                                 }
                             }
                         }
