package com.dumv.ailatrieuphu.object;

public class DapAn {
    private String idDapAn;
    private String idCauHoi;
    @Override
    public String toString() {
        return "DapAn{" +
                "idDapAn='" + idDapAn + '\'' +
                ", idCauHoi='" + idCauHoi + '\'' +
                ", noiDungDapAn='" + noiDungDapAn + '\'' +
                ", dung=" + dung +
                '}';
    }
    private String noiDungDapAn;

    public DapAn(){}

    public String getIdDapAn() {
        return idDapAn;
    }

    public void setIdDapAn(String idDapAn) {
        this.idDapAn = idDapAn;
    }

    public String getIdCauHoi() {
        return idCauHoi;
    }

    public void setIdCauHoi(String idCauHoi) {
        this.idCauHoi = idCauHoi;
    }
    public String getNoiDungDapAn() {
        return noiDungDapAn;
    }
    public void setNoiDungDapAn(String noiDungDapAn) {
        this.noiDungDapAn = noiDungDapAn;
    }
    public boolean isDung() {
        return dung;
    }
    public void setDung(boolean dung) {
        this.dung = dung;
    }
    private boolean dung;
}
