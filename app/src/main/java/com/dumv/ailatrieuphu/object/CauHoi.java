package com.dumv.ailatrieuphu.object;

public class CauHoi {
    private String idCauHoi;

    @Override
    public String toString() {
        return "CauHoi{" +
                "idCauHoi='" + idCauHoi + '\'' +
                ", noiDung='" + noiDung + '\'' +
                '}';
    }

    private String noiDung;

    public String getIdCauHoi() {
        return idCauHoi;
    }

    public void setIdCauHoi(String idCauHoi) {
        this.idCauHoi = idCauHoi;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public CauHoi(){}

}
