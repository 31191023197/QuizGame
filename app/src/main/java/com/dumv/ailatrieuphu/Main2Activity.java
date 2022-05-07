package com.dumv.ailatrieuphu;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dumv.ailatrieuphu.object.CauHoi;
import com.dumv.ailatrieuphu.object.DapAn;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    DatabaseReference root, cauHoi_Tbl, dapAn_Tbl;
    int viTriLoadLanDau;
    int soDapAnLoadLanDau;
    ArrayList<CauHoi> cauHois;
    ArrayList<DapAn> dapAns;
    ArrayList<Integer> viTriCauHoiDaXuatHiens;
    int viTriCauHoiHienTai;
    Random random;
    int soCauHoi;

    TextView txvCauHoi, txvCauTL1, txvCauTL2, txvCauTL3, txvCauTL4, txvThuaGame;
    ImageView troGiup5050Button, troGiupKhanGiaButton;
    CauHoi cauHoiHienTai;
    ArrayList<DapAn> dapAnHienTais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AnhXa();
        KhoiTao();
        GanSuKien();

    }

    private void AnhXa() {
        txvCauHoi = findViewById(R.id.txvCauHoi);
        txvCauTL1 = findViewById(R.id.txvCauTL1);
        txvCauTL2 = findViewById(R.id.txvCauTL2);
        txvCauTL3 = findViewById(R.id.txvCauTL3);
        txvCauTL4 = findViewById(R.id.txvCauTL4);
        txvThuaGame = findViewById(R.id.txvThuaGame);
        troGiup5050Button = findViewById(R.id.troGiup5050Button);
        troGiupKhanGiaButton = findViewById(R.id.troGiupKhanGiaButton);

    }

    private void KhoiTao() {
        Intent intent = getIntent();
        soCauHoi = intent.getIntExtra("soCauHoi", 0);

        txvThuaGame.setVisibility(View.GONE);

        random = new Random();
        viTriCauHoiHienTai = random.nextInt(soCauHoi);
        viTriCauHoiDaXuatHiens = new ArrayList<>();

        root = FirebaseDatabase.getInstance().getReference();
        cauHoi_Tbl = root.child("CauHoi_Tbl");
        dapAn_Tbl = root.child("DapAn_Tbl");

        viTriLoadLanDau = 0;
        soDapAnLoadLanDau = 0;
        cauHoiHienTai = new CauHoi();
        dapAnHienTais = new ArrayList<>();
        dapAns = new ArrayList<>();
        cauHois = new ArrayList<>();
    }

    private void GanSuKien() {
        cauHoi_Tbl.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CauHoi cauHoi = snapshot.getValue(CauHoi.class);
                String idCauHoi = snapshot.getKey();
                cauHoi.setIdCauHoi(idCauHoi);
                if (viTriLoadLanDau == viTriCauHoiHienTai) {
                    cauHoiHienTai = cauHoi;
                    viTriCauHoiDaXuatHiens.add(viTriCauHoiHienTai);
                    LoadCauHoi(cauHoiHienTai);
                }
                cauHois.add(cauHoi);
                viTriLoadLanDau = viTriLoadLanDau + 1;

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dapAn_Tbl.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DapAn dapAn = snapshot.getValue(DapAn.class);
                String idDapAn = snapshot.getKey();
                dapAn.setIdDapAn(idDapAn);
                if (dapAn.getIdCauHoi().equals(cauHoiHienTai.getIdCauHoi())) {
                    dapAnHienTais.add(dapAn);
                    soDapAnLoadLanDau++;
                    if (soDapAnLoadLanDau == 4) {
                        LoadDapAn(dapAnHienTais);
                    }
                }
                dapAns.add(dapAn);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txvCauTL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KiemTraCauTraLoi(0);
            }
        });
        txvCauTL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KiemTraCauTraLoi(1);
            }
        });
        txvCauTL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KiemTraCauTraLoi(2);
            }
        });
        txvCauTL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KiemTraCauTraLoi(3);
            }
        });

        troGiup5050Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                troGiup5050();
            }
        });
        troGiupKhanGiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                troGiupKhanGia();
            }
        });
    }

    private void KiemTraCauTraLoi(int viTriTraLoi) {

        // Lấy vị trí câu trả lời đúng
        int viTriCauTraLoiDung = -1;
        for (int i = 0; i < dapAnHienTais.size(); i++) {
            if (dapAnHienTais.get(i).isDung()) {
                viTriCauTraLoiDung = i;
                break;
            }
        }

        if (viTriCauTraLoiDung == viTriTraLoi) {

            TraLoiDung();

        } else {
            TraLoiSai();
        }
    }

    private void TraLoiDung() {

        // Trường hợp hết câu hỏi
        if (cauHois.size() == viTriCauHoiDaXuatHiens.size()) {

            txvThuaGame.setVisibility(View.VISIBLE);
            txvThuaGame.setText("You Win!!");
            Toast.makeText(this, "Bạn đã chiến thắng", Toast.LENGTH_SHORT).show();
        }
        // Trường hợp còn câu hỏi
        else{
            TaoCauHoiMoi();
        }
    }

    private void TraLoiSai() {
        txvThuaGame.setVisibility(View.VISIBLE);
        txvThuaGame.setText("Game Lose!!");
        Toast.makeText(this, "Bạn đã trả lời sai", Toast.LENGTH_SHORT).show();

    }

    private void TaoCauHoiMoi() {

        this.setAllVisible();
        boolean trung = true;
        while (trung) {
            viTriCauHoiHienTai = random.nextInt(soCauHoi);
            trung = false;

            for (int viTriDaXuatHien : viTriCauHoiDaXuatHiens) {
                if (viTriDaXuatHien == viTriCauHoiHienTai) {
                    trung = true;
                    break;
                }
            }
        }

        // Load câu hỏi
        cauHoiHienTai = cauHois.get(viTriCauHoiHienTai);
        viTriCauHoiDaXuatHiens.add(viTriCauHoiHienTai);
        LoadCauHoi(cauHoiHienTai);

        // Load đáp án
        int soDapAn = 0;
        dapAnHienTais = new ArrayList<>();
        for (int i = 0; i < dapAns.size(); i++) {
            if (dapAns.get(i).getIdCauHoi().equals(cauHoiHienTai.getIdCauHoi())) {
                soDapAn = soDapAn + 1;
                dapAnHienTais.add(dapAns.get(i));
                if (soDapAn == 4) {
                    LoadDapAn(dapAnHienTais);
                    break;
                }
            }
        }

    }

    private void LoadCauHoi(CauHoi cauHoi) {
        this.txvCauHoi.setText(cauHoi.getNoiDung());
    }

    private void LoadDapAn(ArrayList<DapAn> dapAns) {
        this.txvCauTL1.setText(dapAns.get(0).getNoiDungDapAn());
        this.txvCauTL2.setText(dapAns.get(1).getNoiDungDapAn());
        this.txvCauTL3.setText(dapAns.get(2).getNoiDungDapAn());
        this.txvCauTL4.setText(dapAns.get(3).getNoiDungDapAn());
    }

    boolean troGiupDoiCauHoi = true;
    public void trogiupDoiCauHoi(View view) {
        if(troGiupDoiCauHoi == false){
            return;
        }
        TaoCauHoiMoi();
        troGiupDoiCauHoi =false;
    }

    boolean troGiup5050 = true;
    public void troGiup5050(){
        if (troGiup5050 == false) {
            return;
        }

        ArrayList<Integer> viTriDapAnSai = new ArrayList<>();
        for (int i = 0; i < dapAnHienTais.size(); i++) {
            if (!dapAnHienTais.get(i).isDung()) {
                viTriDapAnSai.add(i);
            }
        }

        int so1 = random.nextInt(2);
        int so2 = -1;

        while(true){
            so2 = random.nextInt(2);
            if (so1 != so2) {
                break;
            }
        }

        int viTriAn1 = viTriDapAnSai.get(so1);
        int viTriAn2 = viTriDapAnSai.get(so2);

        switch ( viTriAn1 ) {
            case  0:
                txvCauTL1.setVisibility(View.INVISIBLE);
                break;
            case  1:
                txvCauTL2.setVisibility(View.INVISIBLE);
                break;
            case  2:
                txvCauTL3.setVisibility(View.INVISIBLE);
                break;
            case  3:
                txvCauTL4.setVisibility(View.INVISIBLE);
                break;
            default:
        }

        switch ( viTriAn2 ) {
            case  0:
                txvCauTL1.setVisibility(View.INVISIBLE);
                break;
            case  1:
                txvCauTL2.setVisibility(View.INVISIBLE);
                break;
            case  2:
                txvCauTL3.setVisibility(View.INVISIBLE);
                break;
            case  3:
                txvCauTL4.setVisibility(View.INVISIBLE);
                break;
            default:
        }

        troGiup5050 = false;

    }

    private void setAllVisible(){
        txvCauTL1.setVisibility(View.VISIBLE);
        txvCauTL2.setVisibility(View.VISIBLE);
        txvCauTL3.setVisibility(View.VISIBLE);
        txvCauTL4.setVisibility(View.VISIBLE);
    }

    boolean troGiupKhanGia = true;
    private void troGiupKhanGia(){
        if (troGiupKhanGia == false) {
            return;
        }

        int viTriDung = -1;
        for (int i = 0; i < dapAnHienTais.size(); i++) {
            if (dapAnHienTais.get(i).isDung()) {
                viTriDung = i;
                break;
            }
        }

        if (viTriDung == 0) {
            txvCauTL1.setText(dapAnHienTais.get(0).getNoiDungDapAn() + "        53 %");
            txvCauTL2.setText(dapAnHienTais.get(1).getNoiDungDapAn() + "        13 %");
            txvCauTL3.setText(dapAnHienTais.get(2).getNoiDungDapAn() + "        5 %");
            txvCauTL4.setText(dapAnHienTais.get(3).getNoiDungDapAn() + "        29 %");
        }
        if (viTriDung == 1) {
            txvCauTL1.setText(dapAnHienTais.get(0).getNoiDungDapAn() + "        30 %");
            txvCauTL2.setText(dapAnHienTais.get(1).getNoiDungDapAn() + "        45 %");
            txvCauTL3.setText(dapAnHienTais.get(2).getNoiDungDapAn() + "        6 %");
            txvCauTL4.setText(dapAnHienTais.get(3).getNoiDungDapAn() + "        19 %");

        }
        if (viTriDung == 2) {
            txvCauTL1.setText(dapAnHienTais.get(0).getNoiDungDapAn() + "        14 %");
            txvCauTL2.setText(dapAnHienTais.get(1).getNoiDungDapAn() + "        15 %");
            txvCauTL3.setText(dapAnHienTais.get(2).getNoiDungDapAn() + "        50 %");
            txvCauTL4.setText(dapAnHienTais.get(3).getNoiDungDapAn() + "        21 %");
        }
        if (viTriDung == 3) {
            txvCauTL1.setText(dapAnHienTais.get(0).getNoiDungDapAn() + "        20 %");
            txvCauTL2.setText(dapAnHienTais.get(1).getNoiDungDapAn() + "        3 %");
            txvCauTL3.setText(dapAnHienTais.get(2).getNoiDungDapAn() + "        1 %");
            txvCauTL4.setText(dapAnHienTais.get(3).getNoiDungDapAn() + "        76 %");
        }
        troGiupKhanGia = false;
    }





}
