package van.edu.duanquanlybanhang;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {

    RecyclerView recyclerBill;

    EditText edtSearch;

    Button btnPdf;

    ArrayList<Bill> list;

    ArrayList<Bill> originalList;

    BillAdapter adapter;

    DatabaseReference billRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        // ÁNH XẠ VIEW
        recyclerBill =
                findViewById(R.id.recyclerBill);

        edtSearch =
                findViewById(R.id.edtSearch);

        btnPdf =
                findViewById(R.id.btnPdf);

        // RECYCLERVIEW
        recyclerBill.setLayoutManager(
                new LinearLayoutManager(this));

        // DANH SÁCH
        list = new ArrayList<>();

        originalList = new ArrayList<>();

        // ADAPTER
        adapter = new BillAdapter(list);

        recyclerBill.setAdapter(adapter);

        // FIREBASE
        billRef = FirebaseDatabase
                .getInstance()
                .getReference("Bills");

        // LOAD DỮ LIỆU
        loadBills();

        // SEARCH REALTIME
        edtSearch.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {

                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        filterBill(s.toString());
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {

                    }
                });

        // NÚT PDF
        btnPdf.setOnClickListener(v -> {

            createPDF();
        });
    }

    // LOAD HÓA ĐƠN
    private void loadBills(){

        billRef.addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(
                            @NonNull DataSnapshot snapshot) {

                        list.clear();

                        originalList.clear();

                        for(DataSnapshot data :
                                snapshot.getChildren()){

                            Bill bill =
                                    data.getValue(Bill.class);

                            if(bill != null){

                                list.add(bill);

                                originalList.add(bill);
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError error) {

                    }
                });
    }

    // HÀM TÌM KIẾM
    private void filterBill(String text){

        list.clear();

        for(Bill bill : originalList){

            if(bill.getTable()
                    .toLowerCase()
                    .contains(text.toLowerCase())){

                list.add(bill);
            }
        }

        adapter.notifyDataSetChanged();
    }

    // HÀM PDF
    private void createPDF(){

        PdfDocument pdfDocument =
                new PdfDocument();

        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(
                        1200,
                        2010,
                        1).create();

        PdfDocument.Page page =
                pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        int y = 100;

        paint.setTextSize(40);

        canvas.drawText(
                "DANH SÁCH HÓA ĐƠN",
                300,
                y,
                paint);

        y += 100;

        for(Bill bill : list){

            canvas.drawText(
                    "Bàn: " + bill.getTable(),
                    100,
                    y,
                    paint);

            y += 50;

            canvas.drawText(
                    "Tổng tiền: "
                            + bill.getTotal()
                            + "đ",
                    100,
                    y,
                    paint);

            y += 50;

            canvas.drawText(
                    "Thời gian: "
                            + bill.getTime(),
                    100,
                    y,
                    paint);

            y += 100;
        }

        pdfDocument.finishPage(page);

        File file =
                new File(
                        Environment
                                .getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_DOWNLOADS),
                        "HoaDon.pdf");

        try {

            pdfDocument.writeTo(
                    new FileOutputStream(file));

            Toast.makeText(
                    this,
                    "Xuất PDF thành công",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (Exception e){

            e.printStackTrace();
        }

        pdfDocument.close();
    }
}