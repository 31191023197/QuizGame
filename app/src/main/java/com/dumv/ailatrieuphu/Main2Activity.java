package com.dumv.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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


}
