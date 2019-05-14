package com.ibt.nirmal.fpproandroid_source.database.model;


import java.util.ArrayList;
import java.util.List;

public class params {
    public static final String TABLE_NAME = "params";

    public static final String COLUMN_ID = "id";
    public static final String  r1factor = "r1factor";
    public static final String r1sc0hr = "r1sc0hr";
    public static final String r1maxsc = "r1maxsc";
    public static final String r2factor = "r2factor";
    public static final String r2sc0hr = "r2sc0hr";
    public static final String r2maxsc = "r2maxsc";
    public static final String r3factor = "r3factor";
    public static final String r3reset = "r3reset";
    public static final String fatactfactor = "fatactfactor";
    public static final String yellowsec = "yellowsec";
    public static final String orangesec = "orangesec";
    public static final String redsec = "redsec";
    public static final String maxdutyd = "maxdutyd";
    public static final String maxdutyn = "maxdutyn";
    public static final String manrest = "manrest";
    public static final String ntfrom = "ntfrom";
    public static final String ntuntil = "ntuntil";
    public static final String avmdts = "avmdts";
    public static final String restisduty = "restisduty";
    public static final String dutyreset = "dutyreset";
    public static final String displaylabels = "displaylabels";
    public static final String onerowperday = "onerowperday";
    public static final String oscrcorfactor = "oscrcorfactor";
    public static final String oscrcor = "oscrcor";
    public static final String oscrcormindays = "oscrcormindays";
    public static final String oscrcorindays = "oscrcorindays";



    private int id;



    private Double r1factor_val,fatactfactor_val,yellowsec_val,oscrcorfactor_val;
    private Integer r1sc0hr_val,r1maxsc_val,r2factor_val,r2sc0hr_val,r2maxsc_val,r3factor_val,
                    r3reset_val,orangesec_val,redsec_val,maxdutyd_val,maxdutyn_val,manrest_val,
                    ntfrom_val,ntuntil_val,dutyreset_val,oscrcormindays_val,oscrcorindays_val;
    private int avmdts_val;
    private int restisduty_val;
    private int displaylabels_val;
    private int onerowperday_val;
    private int oscrcor_val;

    public ArrayList<Object> listOfParameters = new ArrayList<Object>();


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + r1factor + " REAL,"
                    + r1sc0hr + " INTEGER,"
                    + r1maxsc + " INTEGER,"
                    + r2factor + " INTEGER,"
                    + r2sc0hr + " INTEGER,"
                    + r2maxsc + " INTEGER,"
                    + r3factor + " INTEGER,"
                    + r3reset + " INTEGER,"
                    + fatactfactor + " REAL,"
                    + yellowsec + " REAL,"
                    + orangesec + " INTEGER,"
                    + redsec + " INTEGER,"
                    + maxdutyd + " INTEGER,"
                    + maxdutyn + " INTEGER,"
                    + manrest + " INTEGER,"
                    + ntfrom + " INTEGER,"
                    + ntuntil + " INTEGER,"
                    + avmdts + " BOOLEAN,"
                    + restisduty + " BOOLEAN,"
                    + dutyreset + " INTEGER,"
                    + displaylabels + " BOOLEAN,"
                    + onerowperday + " BOOLEAN,"
                    + oscrcor + " BOOLEAN,"
                    + oscrcorfactor + " REAL,"
                    + oscrcormindays + " INTEGER,"
                    + oscrcorindays + " INTEGER"
                    + ")";

    public params()
    {

    }

    public params(double r1factor, int r1sc0hr, int r1maxsc, int r2factor, int r2sc0hr, int r2maxsc,
                  int r3factor, int r3reset, double fatactfactor, double yellowsec, int orangesec,
                  int redsec, int maxdutyd, int  maxdutyn, int manrest, int ntfrom, int  ntuntil,
                  int avmdts, int restisduty, int dutyreset, int displaylabels,
                  int onerowperday, int oscrcor, double oscrcorfactor, int oscrcormindays,
                  int oscrcorindays ) {

        this.r1factor_val = r1factor;
        this.r1sc0hr_val = r1sc0hr;
        this.r1maxsc_val = r1maxsc;
        this.r2factor_val = r2factor;
        this.r2sc0hr_val = r2sc0hr;
        this.r2maxsc_val = r2maxsc;
        this.r3factor_val = r3factor;
        this.r3reset_val = r3reset;
        this.fatactfactor_val = fatactfactor;
        this.yellowsec_val = yellowsec;
        this.orangesec_val = orangesec;
        this.redsec_val = redsec;
        this.maxdutyd_val = maxdutyd;
        this.maxdutyn_val = maxdutyn;
        this.manrest_val = manrest;
        this.ntfrom_val = ntfrom;
        this.ntuntil_val = ntuntil;
        this.avmdts_val = avmdts;
        this.restisduty_val = restisduty;
        this.dutyreset_val = dutyreset;
        this.displaylabels_val = displaylabels;
        this.onerowperday_val = onerowperday;
        this.oscrcor_val = oscrcor;
        this.oscrcorfactor_val = oscrcorfactor;
        this.oscrcormindays_val = oscrcormindays;
        this.oscrcorindays_val = oscrcorindays;


     /*   listOfParameters.add(new params(r1factor,r1sc0hr,r1maxsc,r2factor,r2sc0hr,r2maxsc,
                                        r3factor,r3reset,fatactfactor,yellowsec,orangesec,
                                        redsec, maxdutyd,maxdutyn,manrest,ntfrom,ntuntil,
                                        avmdts,restisduty,dutyreset,displaylabels,
                                        onerowperday,oscrcor,oscrcorfactor,oscrcormindays,oscrcorindays ));
*/
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getR1factor_val() {
        return r1factor_val;
    }

    public void setR1factor_val(Double r1factor_val) {
        this.r1factor_val = r1factor_val;
    }

    public Double getFatactfactor_val() {
        return fatactfactor_val;
    }

    public void setFatactfactor_val(Double fatactfactor_val) {
        this.fatactfactor_val = fatactfactor_val;
    }

    public Double getYellowsec_val() {
        return yellowsec_val;
    }

    public void setYellowsec_val(Double yellowsec_val) {
        this.yellowsec_val = yellowsec_val;
    }

    public Double getOscrcorfactor_val() {
        return oscrcorfactor_val;
    }

    public void setOscrcorfactor_val(Double oscrcorfactor_val) {
        this.oscrcorfactor_val = oscrcorfactor_val;
    }

    public Integer getR1sc0hr_val() {
        return r1sc0hr_val;
    }

    public void setR1sc0hr_val(Integer r1sc0hr_val) {
        this.r1sc0hr_val = r1sc0hr_val;
    }

    public Integer getR1maxsc_val() {
        return r1maxsc_val;
    }

    public void setR1maxsc_val(Integer r1maxsc_val) {
        this.r1maxsc_val = r1maxsc_val;
    }

    public Integer getR2factor_val() {
        return r2factor_val;
    }

    public void setR2factor_val(Integer r2factor_val) {
        this.r2factor_val = r2factor_val;
    }

    public Integer getR2sc0hr_val() {
        return r2sc0hr_val;
    }

    public void setR2sc0hr_val(Integer r2sc0hr_val) {
        this.r2sc0hr_val = r2sc0hr_val;
    }

    public Integer getR2maxsc_val() {
        return r2maxsc_val;
    }

    public void setR2maxsc_val(Integer r2maxsc_val) {
        this.r2maxsc_val = r2maxsc_val;
    }

    public Integer getR3factor_val() {
        return r3factor_val;
    }

    public void setR3factor_val(Integer r3factor_val) {
        this.r3factor_val = r3factor_val;
    }

    public Integer getR3reset_val() {
        return r3reset_val;
    }

    public void setR3reset_val(Integer r3reset_val) {
        this.r3reset_val = r3reset_val;
    }

    public Integer getOrangesec_val() {
        return orangesec_val;
    }

    public void setOrangesec_val(Integer orangesec_val) {
        this.orangesec_val = orangesec_val;
    }

    public Integer getRedsec_val() {
        return redsec_val;
    }

    public void setRedsec_val(Integer redsec_val) {
        this.redsec_val = redsec_val;
    }

    public Integer getMaxdutyd_val() {
        return maxdutyd_val;
    }

    public void setMaxdutyd_val(Integer maxdutyd_val) {
        this.maxdutyd_val = maxdutyd_val;
    }

    public Integer getMaxdutyn_val() {
        return maxdutyn_val;
    }

    public void setMaxdutyn_val(Integer maxdutyn_val) {
        this.maxdutyn_val = maxdutyn_val;
    }

    public Integer getManrest_val() {
        return manrest_val;
    }

    public void setManrest_val(Integer manrest_val) {
        this.manrest_val = manrest_val;
    }

    public Integer getNtfrom_val() {
        return ntfrom_val;
    }

    public void setNtfrom_val(Integer ntfrom_val) {
        this.ntfrom_val = ntfrom_val;
    }

    public Integer getNtuntil_val() {
        return ntuntil_val;
    }

    public void setNtuntil_val(Integer ntuntil_val) {
        this.ntuntil_val = ntuntil_val;
    }

    public Integer getDutyreset_val() {
        return dutyreset_val;
    }

    public void setDutyreset_val(Integer dutyreset_val) {
        this.dutyreset_val = dutyreset_val;
    }

    public Integer getOscrcormindays_val() {
        return oscrcormindays_val;
    }

    public void setOscrcormindays_val(Integer oscrcormindays_val) {
        this.oscrcormindays_val = oscrcormindays_val;
    }

    public Integer getOscrcorindays_val() {
        return oscrcorindays_val;
    }

    public void setOscrcorindays_val(Integer oscrcorindays_val) {
        this.oscrcorindays_val = oscrcorindays_val;
    }

    public Integer getAvmdts_val() {
        return avmdts_val;
    }

    public void setAvmdts_val(int avmdts_val) {
        this.avmdts_val = avmdts_val;
    }

    public Integer getRestisduty_val() {
        return restisduty_val;
    }

    public void setRestisduty_val(int restisduty_val) {
        this.restisduty_val = restisduty_val;
    }

    public Integer getDisplaylabels_val() {
        return displaylabels_val;
    }

    public void setDisplaylabels_val(int displaylabels_val) {
        this.displaylabels_val = displaylabels_val;
    }

    public Integer getOnerowperday_val() {
        return onerowperday_val;
    }

    public void setOnerowperday_val(int onerowperday_val) {
        this.onerowperday_val = onerowperday_val;
    }

    public Integer getOscrcor_val() {
        return oscrcor_val;
    }

    public void setOscrcor_val(int oscrcor_val) {
        this.oscrcor_val = oscrcor_val;
    }

}
