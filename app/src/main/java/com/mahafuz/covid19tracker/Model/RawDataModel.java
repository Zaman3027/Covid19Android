package com.mahafuz.covid19tracker.Model;

public class RawDataModel {
    private String patientnumber;

    private String currentstatus;

    private String notes;

    private String gender;

    private String source3;

    private String numcases;

    private String typeoftransmission;

    private String agebracket;

    private String source1;

    private String source2;

    private String detectedstate;

    private String statecode;

    private String statuschangedate;

    private String dateannounced;

    private String detecteddistrict;

    private String entryid;

    private String nationality;

    private String contractedfromwhichpatientsuspected;

    private String detectedcity;

    private String statepatientnumber;

    public String getPatientnumber() {
        return patientnumber;
    }

    public void setPatientnumber(String patientnumber) {
        this.patientnumber = patientnumber;
    }

    public String getCurrentstatus() {
        return currentstatus;
    }

    public void setCurrentstatus(String currentstatus) {
        this.currentstatus = currentstatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSource3() {
        return source3;
    }

    public void setSource3(String source3) {
        this.source3 = source3;
    }

    public String getNumcases() {
        return numcases;
    }

    public void setNumcases(String numcases) {
        this.numcases = numcases;
    }

    public String getTypeoftransmission() {
        return typeoftransmission;
    }

    public void setTypeoftransmission(String typeoftransmission) {
        this.typeoftransmission = typeoftransmission;
    }

    public String getAgebracket() {
        return agebracket;
    }

    public void setAgebracket(String agebracket) {
        this.agebracket = agebracket;
    }

    public String getSource1() {
        return source1;
    }

    public void setSource1(String source1) {
        this.source1 = source1;
    }

    public String getSource2() {
        return source2;
    }

    public void setSource2(String source2) {
        this.source2 = source2;
    }

    public String getDetectedstate() {
        return detectedstate;
    }

    public void setDetectedstate(String detectedstate) {
        this.detectedstate = detectedstate;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getStatuschangedate() {
        return statuschangedate;
    }

    public void setStatuschangedate(String statuschangedate) {
        this.statuschangedate = statuschangedate;
    }

    public String getDateannounced() {
        return dateannounced;
    }

    public void setDateannounced(String dateannounced) {
        this.dateannounced = dateannounced;
    }

    public String getDetecteddistrict() {
        return detecteddistrict;
    }

    public void setDetecteddistrict(String detecteddistrict) {
        this.detecteddistrict = detecteddistrict;
    }

    public String getEntryid() {
        return entryid;
    }

    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getContractedfromwhichpatientsuspected() {
        return contractedfromwhichpatientsuspected;
    }

    public void setContractedfromwhichpatientsuspected(String contractedfromwhichpatientsuspected) {
        this.contractedfromwhichpatientsuspected = contractedfromwhichpatientsuspected;
    }

    public String getDetectedcity() {
        return detectedcity;
    }

    public void setDetectedcity(String detectedcity) {
        this.detectedcity = detectedcity;
    }

    public String getStatepatientnumber() {
        return statepatientnumber;
    }

    public void setStatepatientnumber(String statepatientnumber) {
        this.statepatientnumber = statepatientnumber;
    }

    @Override
    public String toString() {
        return "ClassPojo [patientnumber = " + patientnumber + ", currentstatus = " + currentstatus + ", notes = " + notes + ", gender = " + gender + ", source3 = " + source3 + ", numcases = " + numcases + ", typeoftransmission = " + typeoftransmission + ", agebracket = " + agebracket + ", source1 = " + source1 + ", source2 = " + source2 + ", detectedstate = " + detectedstate + ", statecode = " + statecode + ", statuschangedate = " + statuschangedate + ", dateannounced = " + dateannounced + ", detecteddistrict = " + detecteddistrict + ", entryid = " + entryid + ", nationality = " + nationality + ", contractedfromwhichpatientsuspected = " + contractedfromwhichpatientsuspected + ", detectedcity = " + detectedcity + ", statepatientnumber = " + statepatientnumber + "]";
    }
}
