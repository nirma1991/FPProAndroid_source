package com.ibt.nirmal.fpproandroid_source.ifls;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ibt.nirmal.fpproandroid_source.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import static com.ibt.nirmal.fpproandroid_source.ifls.iflsMethods.idxtodaytime;
import static com.ibt.nirmal.fpproandroid_source.ifls.iflsMethods.manrest;
import static java.lang.Math.round;

public class IFLSCalculations {
    public static ArrayList<String> iflsprior = new ArrayList<String>();
    public static ArrayList<Float> iflsscore = new ArrayList<Float>();
    private Activity activity;
    private Context context;
    public IFLSData data = new IFLSData();

    IFLSParameters para = new IFLSParameters(activity);
    static final int minNTFrom = 17;// must match parameter ParametersViewController


    public IFLSCalculations(Activity activity) {
        this.activity = activity;
        para.getParameters(activity);
        //iflsDataRead();
        //print("Init IFLSCalculations - Parameters:",para.iflsparams)
    }

    private void iflsDataRead() {

        if (!data.Retrieve()) {
            //print("data NOT Retrieved: set Std arrays Prior and ASE")
            data.setStdPriorArray(para.determineDaysPrior());
            data.setStdASEArrays(8);
            data.startdate = new String();
            data.nod = 8;
            data.Store();
        }
        // check Integrity of data
        //print("data Retrieved prior & activ count:", data.prior.count, data.activ.count)
        if (data.adjustPriorfor(para.determineDaysPrior()) || data.adjustASEfor(data.nod)) {
            //print("restore data after adjustment Prior or ASE")
            data.Store();
        }
    }

    public String calculateIFLS(int indexmin, int idxmax, Boolean justExplain) throws JSONException {
        //print("exe calculateIFLS with indexmin indexmax justExplain:", indexmin, idxmax, justExplain)
        int indexmax = idxmax;
        if (justExplain) {
            if (indexmin != indexmax) {
                //print("Inconsistent Data!")
                indexmax = indexmin;
            }
        }
        int sleep48, sleep24, r3reset, hrsdutyd, hrsdutyn, dcreset, oscrctr, i, idx;
        sleep48 = 0;
        sleep24 = 0;
        r3reset = 0;
        hrsdutyd = 0;
        hrsdutyn = 0;
        dcreset = 0;
        oscrctr = 0;
        Double awake, rule1, rule2, rule3, sl24crc, sl48crc;
        awake = 0.0;
        rule1 = 0.0;
        rule2 = 0.0;
        rule3 = 0.0;
        sl24crc = 0.0;
        sl48crc = 0.0;

        // Subroutines
        // case activity = sleep/awake


        i = 0;
        if (indexmin < 48) {
            int startprior = data.prior.size() - 48 + indexmin;
            for (int idxp = startprior; idxp < data.prior.size(); i++) {
                i += 1;

                // count sleep/awake
                iflsMethods.sleepawake(data.prior.get(idxp), idxp, true, sleep24, sl24crc, i, sleep48, sl48crc, oscrctr, r3reset, awake, true);

                // uncomment following for testing
                //printdatatest(true, p48: true, dataindex: idxp, printRules: false, printDuty: true, sleep24: sleep24, sl24crc: sl24crc, sleep48: sleep48, sl48crc: sl48crc, oscrctr: oscrctr, awake: awake, rule1: rule1, rule2: rule2, rule3:rule3, hrsdutyd: hrsdutyd, hrsdutyn: hrsdutyn, dcreset: dcreset, manrest: manrest)

            } // end iteration through prior
            idx = 0;
        } else {
            idx = indexmin - 48;
        }
        // activ series
        if (i < 48) {
            do { // while i < 48
                i += 1;
                String actstr = data.activ.get(idx);
                //let act = actstr.substring(to: actstr.characters.index(actstr.startIndex, offsetBy: 1))
                String act = String.valueOf(actstr.startsWith(String.valueOf(1)));
                int hr_day[] = idxtodaytime(idx);

                // count sleep/awake & dutytime
                iflsMethods.sleepawake(data.activ.get(idx), idx, false, sleep24, sl24crc, i, sleep48, sl48crc, oscrctr, r3reset, awake, true);
                if (idx == 0) {

                    dcreset = para.jsonObject.getInt("dutyreset");
                }
                iflsMethods.dutytime(act, hr_day[0], hrsdutyd, hrsdutyn, dcreset);

                // uncomment following for testing
                //printdatatest(false, p48: true, dataindex: idx, printRules: false, printDuty: true, sleep24: sleep24, sl24crc: sl24crc, sleep48: sleep48, sl48crc: sl48crc, oscrctr: oscrctr, awake: awake, rule1: rule1, rule2: rule2, rule3:rule3, hrsdutyd: hrsdutyd, hrsdutyn: hrsdutyn, dcreset: dcreset, manrest: manrest)

                idx += 1;
            } while (i < 48); // end iteration through prior 48 hrs
        }

        // irriterate through data to be updated

        for (idx = indexmin; idx < indexmax; i++) {
            // Calculate IFLS
            Double sl24;
            Double sl48;
            Boolean oscrcor = ((para.jsonObject.getInt("oscrcor") == 1)) ? true : false;
            if (oscrcor) {
                sl24 = sl24crc;
                sl48 = sl48crc;
            } else {
                sl24 = Double.valueOf(sleep24);
                sl48 = Double.valueOf(sleep48);
            }
            // rule 1
            Double rule1factor = para.jsonObject.getDouble("r1factor");
            Double rule1sc0hr = para.jsonObject.getDouble("r1sc0hr");
            Double rule1maxsc = para.jsonObject.getDouble("r1maxsc");
            if (sl24 < rule1sc0hr) {
                rule1 = (rule1sc0hr - sl24) * rule1factor;
                if (rule1 > rule1maxsc) {
                    rule1 = rule1maxsc;
                }
            } else {
                rule1 = 0.0;
            }
            // rule 2
            Double rule2factor = para.jsonObject.getDouble("r2factor");
            Double rule2sc0hr = para.jsonObject.getDouble("r2sc0hr");
            Double rule2maxsc = para.jsonObject.getDouble("r2maxsc");
            if (sl48 < rule2sc0hr) {
                rule2 = (rule2sc0hr - sl48) * rule2factor;
                if (rule2 > rule2maxsc) {
                    rule2 = rule2maxsc;
                }
            } else {
                rule2 = 0.0;
            }
            // rule 3
            Double rule3factor = para.jsonObject.getDouble("r3factor");
            if (awake > sl48) {
                rule3 = (awake - sl48) * rule3factor;
            } else {
                rule3 = 0.0;
            }

            // adjust activ string for registering mandatory rest
            String actstr = data.activ.get(idx);
            //let act = actstr.substring(to: actstr.characters.index(actstr.startIndex, offsetBy: 1))
            String act = String.valueOf(actstr.startsWith(String.valueOf(1)));
            //let rest = actstr.substring(from: actstr.characters.index(actstr.startIndex, offsetBy: 1))
            String rest = String.valueOf(actstr.indexOf(String.valueOf(actstr.startsWith(actstr, 1))));
            if (!justExplain) {
                if (manrest == 0) {
                    data.activ.set(idx, act);
                } else {
                    data.activ.set(idx, act + "R");
                    //print("idx for MR: " + String(idx))
                }
            }

            // explain Calculations

            int hr_day[] = idxtodaytime(idx);

            // uncomment following for testing
            //printdatatest(false, p48: false, dataindex: idx, printRules: false, printDuty: true, sleep24: sleep24, sl24crc: sl24crc, sleep48: sleep48, sl48crc: sl48crc, oscrctr: oscrctr, awake: awake, rule1: rule1, rule2: rule2, rule3:rule3, hrsdutyd: hrsdutyd, hrsdutyn: hrsdutyn, dcreset: dcreset, manrest: manrest)

            if (justExplain) {
                String explstr = "Day #" + String.valueOf(hr_day[1]) + " Hour: " + String.valueOf(hr_day[0]) + " ~ " + String.valueOf(hr_day[0] + 1) + "\n";

                if (oscrcor) {
                    explstr += "hours Sleep outside Circadium Rythm prior 48 hours: " + String.valueOf(oscrctr) + "\n";
                    explstr += "Rule1 = hours Sleep prior 24: " + String.valueOf(sleep24) + " => corrected: " + String.valueOf(round(sl24crc * 10) / 10);
                } else {
                    explstr += "Rule1 = hours Sleep prior 24: " + String.valueOf(sleep24);
                }
                explstr += " < " + String.valueOf(para.jsonObject.getInt("r1sc0hr"));
                explstr += " x " + String.valueOf((round(para.jsonObject.getDouble("r1factor")) * 10) / 10);
                explstr += " (max " + String.valueOf((round(para.jsonObject.getDouble("r1maxsc")) * 10) / 10) + "\n";

                explstr += "Rule2 = hours Sleep prior 48: " + String.valueOf(sleep48);
                if (oscrcor) {
                    explstr += " => corrected: " + String.valueOf(round(sl48crc * 10) / 10);
                }
                explstr += " < " + String.valueOf(para.jsonObject.getInt("r2sc0hr"));
                explstr += " x " + String.valueOf((round(para.jsonObject.getDouble("r2factor")) * 10) / 10);
                explstr += " (max " + String.valueOf((round(para.jsonObject.getDouble("r2maxsc")) * 10) / 10) + "\n";
                explstr += "hours Awake since " + String.valueOf(para.jsonObject.getInt("r2sc0hr"));
                explstr += " hours sleep: " + String.valueOf(round(awake * 10) / 10) + "\n";
                if ((para.jsonObject.getDouble("fatactfactor")) != 1) {
                    explstr += "(Fatigueing Activity = " + String.valueOf((round(para.jsonObject.getDouble("fatactfactor")) * 10) / 10) + " hours awake)\n";
                }
                if (oscrcor) {
                    explstr += "Rule3 = " + String.valueOf(round(awake * 10) / 10);
                    explstr += " > " + String.valueOf(round(sl48crc * 10) / 10);
                } else {
                    explstr += "Rule3 = " + String.valueOf(round(awake * 10) / 10) + " > " + String.valueOf(sleep48);
                }
                explstr += " x " + String.valueOf(para.jsonObject.getDouble("r3factor")) + "\n";

                explstr += "IFLS = " + String.valueOf(round(rule1 * 10) / 10);
                explstr += " + " + String.valueOf(round(rule2 * 10) / 10);
                explstr += " + " + String.valueOf(round(rule3 * 10) / 10);
                explstr += " = " + String.valueOf(round((rule1 + rule2 + rule3) * 10) / 10) + "\n";
                explstr += "hours Duty (since " + String.valueOf(para.jsonObject.getInt("dutyreset"));
                explstr += " hours of no duty): D; " + String.valueOf(hrsdutyd) + " N; " + String.valueOf(hrsdutyn);
                explstr += " (non duty < " + String.valueOf(para.jsonObject.getInt("dutyreset"));
                Boolean restisduty = ((para.jsonObject.getInt("restisduty") == 1)) ? true : false;
                if (restisduty) {
                    explstr += " included)\n";
                } else {
                    explstr += " exluded)\n";
                }
                if (rest == "R") {
                    explstr += "Mandatory Rest; " + String.valueOf(manrest) + " hours to go";
                }
                //print(explstr)
                return explstr;

            } else {

                // store ifls
                data.score.set(idx, (float) (rule1 + rule2 + rule3));

                // adjust sleepcount for prior 48 activity
                String prioractstr;
                if (idx < 48) {
                    int indexp = data.prior.size() - 48 + idx;
                    prioractstr = data.prior.get(indexp);
                    iflsMethods.sleepawake(prioractstr, indexp, true, sleep24, sl24crc, 100, sleep48, sl48crc, oscrctr, r3reset, awake, false);
                } else {
                    int indexa = idx - 48;
                    prioractstr = data.activ.get(indexa);
                    iflsMethods.sleepawake(prioractstr, indexa, false, sleep24, sl24crc, 100, sleep48, sl48crc, oscrctr, r3reset, awake, false);
                }
                // adjust sleepcount for prior 24 activity
                if (idx < 24) {
                    int indexp = data.prior.size() - 24 + idx;
                    prioractstr = data.prior.get(indexp);
                    iflsMethods.sleepawake(prioractstr, indexp, true, sleep24, sl24crc, -1, sleep48, sl48crc, oscrctr, r3reset, awake, false);
                } else {
                    int indexa = idx - 24;
                    prioractstr = data.activ.get(indexa);
                    iflsMethods.sleepawake(prioractstr, indexa, false, sleep24, sl24crc, -1, sleep48, sl48crc, oscrctr, r3reset, awake, false);
                }

                // calculate IFLS drivers

                // count sleep/awake & dutytime
                iflsMethods.sleepawake(actstr, idx, false, sleep24, sl24crc, 100, sleep48, sl48crc, oscrctr, r3reset, awake, true);
                iflsMethods.dutytime(act,hr_day[0],hrsdutyd, hrsdutyn, dcreset);


            } // end not justExplain
        }
            return "Calculations Complete";
    }// end func calculateIFLS
    public String[] getTimeDetails(int idx,Boolean isprior) throws ParseException {
        int dayint = 0;
        if (isprior) {
            dayint = -para.determineDaysPrior() + idx/24;
        } else {
            dayint = idx/24;
        }
        String scrolldate = data.startdate;
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = fmt.parse(scrolldate);
        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        cal.setTime(date);
        String dayText = String.valueOf(cal.get(GregorianCalendar.DAY_OF_WEEK));
        String dateText = String.valueOf(cal.get(GregorianCalendar.DATE));
       /* Calendar gregorian = Calendar(identifier: Calendar.Identifier.gregorian)
        var daycomp = DateComponents()
        daycomp.day = dayint
        scrolldate = (gregorian as NSCalendar).date(byAdding: daycomp, to: scrolldate, options: NSCalendar.Options.wrapComponents)!
        let dateFormat = DateFormatter()
        dateFormat.dateStyle = DateFormatter.Style.full
        var datestr = dateFormat.string(from: scrolldate);
        //let dayText = datestr.substring(to: datestr.characters.index(datestr.startIndex, offsetBy: 3))
        String dayText = String(datestr.prefix(3));
        dateFormat.dateStyle = DateFormatter.Style.medium
        datestr = dateFormat.string(from: scrolldate)
        dateFormat.setLocalizedDateFormatFromTemplate("dd")
        dayint = Int(dateFormat.string(from: scrolldate))!;
        if (dayint < 10) {
            //dateText = datestr.substring(to: datestr.characters.index(datestr.startIndex, offsetBy: 5))
            dateText = String(datestr.prefix(5));
        } else {
            //dateText = datestr.substring(to: datestr.characters.index(datestr.startIndex, offsetBy: 6))
            dateText = String(datestr.prefix(6));
        }*/

        int hr = idx;
        if (hr > 23) {
            do {
                hr -= 24;
            } while (hr > 23);
        }
        String hrsText = String.valueOf(hr) + " ~ " + String.valueOf(hr + 1);

        return new String[] {dayText,dateText,hrsText};
    }
}
class iflsMethods {
    static int manrest = 0;
    private static Activity activity;
    static IFLSData data = new IFLSData();
    static IFLSParameters para = new IFLSParameters(activity);
    iflsMethods(Activity activity){
        this.activity=activity;
        para.getParameters(activity);
    }

    public static void sleepawake(String actstr, int dataIndex, Boolean isprior, int sleep24, Double sl24crc, int ctr24, int sleep48, Double sl48crc,int oscrctr, int r3reset,Double awake, Boolean plus) throws JSONException {

        //let act = actstr.substring(to: actstr.characters.index(actstr.startIndex, offsetBy: 1))
       // String act = String.valueOf(actstr.startsWith(String.valueOf(1)));
        String act = actstr;
        if (act == "s") {
            int crctr = 0;
            int indays = para.jsonObject.getInt("oscrcorindays");
            int mindays = para.jsonObject.getInt("oscrcormindays");

            // sleep24 & sleep48
            if (plus) {
                sleep48 += 1;
                if (ctr24 > 24) {
                    sleep24 += 1;
                    // sl24crc
                }
            } else {
                if (ctr24 < 0) {
                    sleep24 -= 1;
                } else {
                    sleep48 -= 1;
                }
            }

            // circadium rythm corrected sleep values
            for (int j = 0; j < indays; j++) {
                if (isprior) {
                    if (dataIndex - 24 * j < 0) {
                        crctr += 1;
                    } else {
                        if (data.prior.get(dataIndex - 24 * j) == "s") {
                            crctr += 1;
                        }
                    }
                } else {
                    if (dataIndex - 24 * j < 0) {
                        if (data.prior.size() + dataIndex - 24 * j < 0) {
                            crctr += 1;
                        } else {
                            if (data.prior.get(data.prior.size() + dataIndex - 24 * j) == "s") {
                                crctr += 1;
                            }
                        }
                    } else {
                        if (data.activ.get(dataIndex - 24 * j) == "s") {
                            crctr += 1;
                        }
                    }
                }
            }
            // outside circadium rythm
            if (crctr < mindays) {
                if (plus) {
                    sl48crc += Double.parseDouble(para.jsonObject.getString("oscrcorfactor"));
                    oscrctr += 1;
                    if (ctr24 > 24) {
                        sl24crc += para.jsonObject.getDouble("oscrcorfactor");
                    }
                } else {
                    if (ctr24< 0) {
                        sl24crc -= para.jsonObject.getDouble("oscrcorfactor");
                    } else{
                        oscrctr -= 1;
                        sl48crc -= para.jsonObject.getDouble("oscrcorfactor");
                    }
                }
                //print("(isprior) oscrcor for index:",isprior,dataIndex,"plus:", plus, oscrctr)
                // inside circadium rythm
            } else {
                if (plus) {
                    sl48crc += 1;
                    if (ctr24 > 24) {
                        sl24crc += 1;
                    }
                } else{
                    if(ctr24< 0){
                        sl24crc -= 1;
                    } else{
                        sl48crc -= 1;
                    }
                }
            }

            // reset awake counter (act=="S")
            if (plus && ctr24 > 0){
                r3reset += 1;
                if (r3reset >= para.jsonObject.getInt("r3reset")){
                    awake = 0.0;
                }
            }

            // awake counter (act != "S")
        } else {
            if (plus) {
                if (act == "M" || act == "F"){
                    awake += para.jsonObject.getDouble("fatactfactor");
                } else{
                    awake += 1;
                }
                r3reset = 0;
            }
        }
        //print("plus, r3reset",plus,r3reset)

    }
    // subroutine case activity = duty
    static void dutytime(String act,int hr, int hrsdutyd, int hrsdutyn, int dcreset) throws JSONException {
        if(act == "D" || act == "F" ){
            if ((para.jsonObject.getInt("ntfrom")) >= 24) {  // ntfrom 0~2
                if ((hr >= (para.jsonObject.getInt("ntfrom")) - 24 )
                        && (hr < (para.jsonObject.getInt("ntuntil")))) {
                    hrsdutyn += 1;
                } else {
                    hrsdutyd += 1;
                }
            } else {   // ntfrom 17~23
                if ((hr >= (para.jsonObject.getInt("ntfrom"))) ||(hr < (para.jsonObject.getInt("ntuntil")))) {
                    hrsdutyn += 1;
                } else {
                    hrsdutyd += 1;
                }
            }
            dcreset = 0;
        } else {
            dcreset += 1;
            Boolean restisduty = ((para.jsonObject.getInt("restisduty")==1))?true:false;
            if (restisduty) {
                if ((para.jsonObject.getInt("ntfrom")) >= 24 ){  // ntfrom 0~2
                    if ((hr >= (para.jsonObject.getInt("ntfrom")) - 24 )
                            && (hr < (para.jsonObject.getInt("ntuntil")))) {
                        hrsdutyn += 1;
                    } else {
                        hrsdutyd += 1;
                    }
                } else {   // ntfrom 17~23
                    if ((hr >= (para.jsonObject.getInt("ntfrom"))) ||(hr < (para.jsonObject.getInt("ntuntil")))) {
                        hrsdutyn += 1;
                    } else {
                        hrsdutyd += 1;
                    }
                }
            }
            if (dcreset >= para.jsonObject.getInt("dutyreset")) {
                hrsdutyd = 0;
                hrsdutyn = 0;
            }
        }
        // determine mandatory rest
        if (act == "D" || act == "F") {
            Boolean setmanrest = false;
            Boolean avmdts = ((para.jsonObject.getInt("avmdts")==1))?true:false;
            if (avmdts) {
                Double dayratio, nightratio;
                dayratio = new Double(hrsdutyd) / (para.jsonObject.getDouble("maxdutyd"));
                nightratio = new Double(hrsdutyn) / (para.jsonObject.getDouble("maxdutyn"));
                //print(dayratio, nightratio)
                if(dayratio + nightratio >= 1 ){
                    setmanrest = true;
                }
            } else {
                if (hrsdutyn > 0) {
                    if ((hrsdutyd + hrsdutyn) >= (para.jsonObject.getInt("maxdutyn"))){
                        setmanrest = true;
                    }
                } else {
                    if ((hrsdutyd + hrsdutyn) >= (para.jsonObject.getInt("maxdutyd"))) {
                        setmanrest = true;
                    }
                }
            }
            if (setmanrest) {
                if ((para.jsonObject.getInt("manrest")) == 0 ){
                    manrest = hrsdutyd + hrsdutyn;
                } else {
                    manrest = (para.jsonObject.getInt("manrest"));
                }
                manrest += 1;
            }
        }
        if (manrest > 0) {
            manrest -= 1;
        }
    }
// end subroutine case activity = sleep/awake

// subroutine index to day conversion
    public static int[] idxtodaytime(int idx) {
        int hr = idx;
        int day = idx/24 + 1;
        if (hr > 23) {
            do {
                hr -= 24;
            } while (hr > 23);
        }
        return new int[] {hr, day};
    }

    // subroutine print calculations for testing
    public void printdatatest(Boolean isprior,Boolean p48,int dataindex,Boolean printRules,Boolean printDuty, int sleep24,Float sl24crc,int sleep48,Float sl48crc,int oscrctr,Float awake, Float rule1, Float rule2, Float rule3,int hrsdutyd,int hrsdutyn, int dcreset,int manrest) {
        int hr_day[] = idxtodaytime(dataindex);
        String logstr = "";
        if (p48) {
            logstr = "P48  ";
        } else {
            logstr = "Calc ";
        }
        if (isprior) {
            int daypr = hr_day[1] - para.determineDaysPrior();
            logstr += "idx-Day-Hr-Act: " + String.valueOf(dataindex) + " - " + String.valueOf(daypr) + " - " + String.valueOf(hr_day[0]) + " - " + data.prior.get(dataindex);
        } else {
            logstr += "idx-Day-Hr-Act: " + String.valueOf(dataindex) + " - " + String.valueOf(hr_day[1]) + " - " + String.valueOf(hr_day[0]) + " - " + data.activ.get(dataindex);
        }
        if(printRules) {
            logstr += "  sl 24-48: " + String.valueOf(sleep24) + " - " + String.valueOf(sleep48);
            logstr += "  oscr pr48: " + String.valueOf(oscrctr);
            logstr += "  slcrc 24-48: " + String.valueOf(round(sl24crc*10)/10);
            logstr += " - " + String.valueOf(round(sl48crc*10)/10);
            logstr += "  awake: " + String.valueOf(round(awake*10)/10);
            logstr += "  Rule 1-2-3: " + String.valueOf(round(rule1*10)/10);
            logstr += " - " + String.valueOf(round(rule2*10)/10);
            logstr += " - " + String.valueOf(round(rule3*10)/10);
            logstr += "  IFLS= " + String.valueOf(round((rule1 + rule2 + rule3)*10)/10);
        }
        if (printDuty) {
            logstr += "  Duty_D-N dcr manrest: " + String.valueOf(hrsdutyd) + "-" + String.valueOf(hrsdutyn) + " " + String.valueOf(dcreset) + " " + String.valueOf(manrest);
        }
        //print(logstr);
    }

    // end subroutines - Start CALCULATIONS

    // prior 48 hrs Calculations

    // prior series

    // end irriterate through data
}
class IFLSData
{
    String startdate;
    Integer nod = 0;
    ArrayList<String> prior = new ArrayList<String>();
    ArrayList<String> activ = new ArrayList<String>();
    ArrayList<Float> score = new ArrayList<Float>();
    ArrayList<Float> evals = new ArrayList<Float>();

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String startdate_pref = "startdate";
    public static final String nod_pref = "nod";
    public static final String prior_pref = "prior";
    public static final String activ_pref = "activ";
    public static final String score_pref = "score";
    public static final String evals_pref = "evals";

    public  JSONArray paramset = new JSONArray();


    public IFLSData() {

        Calendar date = Calendar.getInstance();

       // sharedpreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        startdate = sdf.format(date.getTime());
        nod = 4;
        setStdPriorArray(2);
        setStdASEArrays(4);
    }


    void setStdASEArrays(int y)
    {
        ArrayList<String> initactiv = new ArrayList<String>();
        ArrayList<Float> initscore = new ArrayList<Float>();
        ArrayList<Float> initevals = new ArrayList<Float>();
        for(int i = 0;i<y;i++) {
            for(int j = 0;j<24;j++)
            {
                    if (j >= 22 || j < 6 )
                    {
                        initactiv.add("SR");
                    }
                    else {
                        initactiv.add("a");
                    }
                    initscore.add((float) (new Float(j) * 1.3));
                    initevals.add((float) 0);
                }
            }
            activ = initactiv;
            score = initscore;
            evals = initevals;

        IFLSCalculations.iflsscore = initscore;
    }

     void setStdPriorArray(int x) {
        ArrayList<String> initprior = new ArrayList<String>();
        for(int i = 0;i<x;i++) {
            for(int j = 0;j<24;j++)
            {
                if (j >= 22 || j<6 )
                {
                    initprior.add("s");
                } else {
                    initprior.add("a");
                }
            }
        }
        prior = initprior;
        IFLSCalculations.iflsprior = initprior;
    }

    public JSONArray makeJSONcompatible()
      {
            Calendar date = Calendar.getInstance();

            JSONObject rowObject = new JSONObject();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

          try {
              rowObject.put("startdate",sdf.format(date.getTime()));
              rowObject.put("nod",0);
              rowObject.put("prior",prior);
              rowObject.put("activ",activ);
              rowObject.put("score",score);
              rowObject.put("evals",evals);
          } catch (JSONException e) {
              e.printStackTrace();
          }

          paramset.put(rowObject);

        return paramset;
    }


    void Store() {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(startdate_pref, startdate);
        editor.putString(nod_pref, String.valueOf(nod));
        editor.putString(prior_pref, String.valueOf(prior));
        editor.putString(activ_pref, String.valueOf(activ));
        editor.putString(score_pref, String.valueOf(score));
        editor.putString(evals_pref, String.valueOf(evals));

        editor.commit();
    }

    public Boolean Retrieve() {
        //print("Retrieve IFLSData from file: ") //+ dataFile("IFLSData.archive"))
        Boolean inifail = false;
        if (sharedpreferences.contains(startdate_pref)
                && sharedpreferences.contains(nod_pref)
                && sharedpreferences.contains(prior_pref)
                && sharedpreferences.contains(activ_pref)
                && sharedpreferences.contains(score_pref)
                && sharedpreferences.contains(evals_pref))
        {
            startdate = sharedpreferences.getString(startdate_pref, "");
            nod = sharedpreferences.getInt(nod_pref, Integer.parseInt(""));
            prior = (ArrayList<String>) sharedpreferences.getStringSet(prior_pref, Collections.singleton(""));
            activ = (ArrayList<String>) sharedpreferences.getStringSet(activ_pref, Collections.singleton(""));
            //score = (ArrayList<Float>) sharedpreferences.getFloat(score_pref, Collections.singletonList(""));
           // evals = (ArrayList<Float>) sharedpreferences.getFloat(evals_pref, Array.parseFloat(""));
        }
        /*
        if nod * 24 != activ.count || nod * 24 != score.count || nod * 24 != evals.count {
            print("Inconsistent data","nod*24 activ score evals counts",nod * 24,activ.count,score.count,evals.count)
        }
        */
        if (inifail) {
            //print("IFLSData Retrieval Fail")
            return false;
        } else {
            return true;
        }
    }

     Boolean adjustPriorfor(int adjfornod){
        //print("adjustPriorfor:", adjfornod, "*24=", adjfornod*24, "priorcount:", prior.count)
        Boolean adjusted = false;
        if (prior.size() > adjfornod*24) {
            int maxi = prior.size() - adjfornod*24;
            for (int i=0;i< maxi;i++) {
                prior.remove( 0);
            }
            adjusted = true;
        }
        if (prior.size() < adjfornod*24) {
            ArrayList<String> newprior = new ArrayList<String>();
            int maxi = adjfornod*24 - prior.size();
            for(int i=0; i<maxi; i++) {
                int d = i/24;
                int h = i - d*24;
                if (h>=22 || h<6) {
                    newprior.add("s");
                } else {
                    newprior.add("a");
                }
            }
            for(int i=0; i< prior.size();i++) {
                newprior.add(prior.get(i));
            }
            prior = newprior;
            adjusted = true;
        }
        //print("adjusted prior.count ", prior.count)
        return adjusted;
    }

     Boolean adjustASEfor(int adjfornod){
        //print("adjustASEfor:", adjfornod, "*24=", adjfornod*24, "activcount:", activ.count)
        Boolean adjusted = false;

        if (activ.size() > adjfornod*24) {
            int i = activ.size();
            do {
                i -= 1;
                activ.remove(i);
                score.remove(i);
                evals.remove(i);
            } while (i > adjfornod*24);
            adjusted = true;
        }
        if (activ.size() < adjfornod*24) {
            //print("maxi = verschil:",adjfornod*24 - activ.count)
            int maxi = adjfornod*24 - activ.size();
            for(int i=0;i<maxi;i++) {
                int d = i/24;
                int h = i - d*24;
                if (h>=22 || h<6) {
                    activ.add("s");
                } else {
                    activ.add("a");
                }
                score.add((float) 0.0);
                evals.add((float) 0.0);
            }
            adjusted = true;
        }
        //print("adjusted activ.count ", activ.count)
        return adjusted;
    }
}
 class IFLSParameters {
    ArrayList<String> iflsparams = new ArrayList<String>();
    public  JSONArray paramsetDefault = new JSONArray();
    public JSONObject jsonObject = new JSONObject();
    DatabaseHelper db;
    // values <AnyObject> will be optionals!

    public JSONArray makeJSONcompatible(){
        paramsetDefault.put(iflsparams);
        return paramsetDefault;
    }

    public IFLSParameters(Activity activity) {
        DatabaseHelper db = new DatabaseHelper(activity);
        JSONArray paramDefaults = db.resultSet;
        // This gets you the first (zero indexed) element of the above array.
        try {
            jsonObject = paramDefaults.getJSONObject(0);
            String r1factor = jsonObject.getString("r1factor");
            Object aObj = jsonObject.getInt(String.valueOf(jsonObject));
            if(aObj instanceof Integer){
                System.out.println(aObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //check Defaults registration
        //print(defaults.float(forKey: "oscrcorfactor"))
    }

    // for use as part of IFLScalculations class

    public void getParameters(Activity activity) {
        //print("getParameters")
        DatabaseHelper db = new DatabaseHelper(activity);
        JSONArray paramDefaults = db.resultSet;
        // This gets you the first (zero indexed) element of the above array.
        try
        {
            jsonObject = paramDefaults.getJSONObject(0);
            for (String k : new AsIterable<String>(jsonObject.keys()))
            {
                try
                {
                    Object v = jsonObject.get(k);
                    //Log.d("+++","+++"+k+"=>"+v.getClass()+":"+v);
                    if (v instanceof Integer || v instanceof Long)
                    {
                        long intToUse = ((Number)v).longValue();
                    }
                    else if (v instanceof Boolean)
                    {
                        boolean boolToUse = ((Boolean)v).booleanValue();
                    }
                    else if (v instanceof Float || v instanceof Double)
                    {
                        double floatToUse = ((Number)v).doubleValue();
                    }
                    else if (JSONObject.NULL.equals(v))
                    {
                        Object nullToUse = null;
                    }
                    else
                    {
                        String stringToUse = jsonObject.getString(k);
                    }
                } catch (JSONException e2) {
                    // TODO Auto-generated catch block
                    // Log.d("exc: "+e2);
                    e2.printStackTrace();
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    /*func setParameterswith(_ newParams: [String:AnyObject]) {
        //print("setParameterswith")
        let defaults = UserDefaults.standard
        if let defdict = defaults.object(forKey: "Defaults") as? Dictionary<String,AnyObject> {
                let keylist = defdict.keys
        for paramkey in keylist {

            //print("Set:",paramkey,params[paramkey])

            if let param = newParams[paramkey] as? Int {
                //print("Int",paramkey,param)
                defaults.set(param, forKey: paramkey)
            }
            if let paramstr = newParams[paramkey] as? String {
                //print("String",paramkey,paramstr)
                let param = round(Float(paramstr)!*100)/100
                defaults.set(param, forKey: paramkey)
            }

        }
        } else {
            print("setparameterswith(): UserDefaults not initiated with standardUserDefaults ??")
        }
    }*/

    public int determineDaysPrior(){
        int numItems=0;
        try {
            if ( jsonObject.getString("oscrcor").equals("false"))
            {
                    numItems = Integer.parseInt(jsonObject.getString("oscrcorindays"));
            }
            else {
                numItems = 2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return numItems;
    }
}
class AsIterable<T> implements Iterable<T> {
    private Iterator<T> iterator;
    public AsIterable(Iterator<T> iterator) {
        this.iterator = iterator;
    }
    public Iterator<T> iterator() {
        return iterator;
    }
}
