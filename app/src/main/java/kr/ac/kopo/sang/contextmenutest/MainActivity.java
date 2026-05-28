package kr.ac.kopo.sang.contextmenutest;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    Button btnRotation, btnZoomin;
    LinearLayout linear;

    float rotationDegree;   // 수정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnRotation = findViewById(R.id.btn_bg);
        btnZoomin = findViewById(R.id.btn_change);
        linear = findViewById(R.id.main);
        Button btnAlert = findViewById(R.id.btn_alert);
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 대화상자내의 메시지를 "배경색을 파랑색으로 변경할까요?" 로 변경
                // 대화상자내의 확인 버튼을 클릭: 액티비티의 LinearLayout 배경색 파랑으로 변경
                // 대화상자내의 취소 버튼을 클릭: 배경색 변화 없음
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("배경색 변경");
                dialog.setMessage("배경색을 파랑색으로 변경할까요?");
                dialog.setIcon(R.drawable.icon);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "확인 버튼을 클릭했어요.", Toast.LENGTH_SHORT).show();
                        linear.setBackgroundColor(Color.BLUE);
                    }
                });
                dialog.setNegativeButton("취소", null);
                dialog.show();
            }
        });

        registerForContextMenu(btnRotation);
        registerForContextMenu(btnZoomin);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();

        if(v == btnRotation)
        {
            menu.setHeaderTitle("배경색 변경");
            menuInflater.inflate(R.menu.context_menu1, menu);
        }
        if(v == btnZoomin)
        {
            menu.setHeaderTitle("버튼 변경");
            menuInflater.inflate(R.menu.context_menu2, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);
        if(item.getItemId() == R.id.item_bg_orange)
        {
            linear.setBackgroundColor(Color.rgb(255,165,0));
            return true;
        } else if (item.getItemId() == R.id.item_bg_yellow )
        {
            linear.setBackgroundColor(Color.YELLOW);
            return true;
        } else if(item.getItemId() == R.id.item_bg_red )
        {
            linear.setBackgroundColor(Color.RED);
            return true;
        } else if(item.getItemId() == R.id.item_btn_rotation )
        {
            rotationDegree += 45.0f;   
            btnZoomin.setRotation(rotationDegree);
            return true;
        }  else if(item.getItemId() == R.id.item_btn_zoomin )
        {
            btnZoomin.setScaleX(2);
            return true;
        }
        return false;
    }
}