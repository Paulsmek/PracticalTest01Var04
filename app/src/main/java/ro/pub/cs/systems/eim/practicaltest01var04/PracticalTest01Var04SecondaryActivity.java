package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    private TextView t1, t2;
    private Button btnOk, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        String name = getIntent().getStringExtra("name");
        String group = getIntent().getStringExtra("group");

        t1 = findViewById(R.id.textView11);
        t2 = findViewById(R.id.textView12);
        t1.setText(name);
        t2.setText(group);

        Intent intentToParent = new Intent();


        btnOk = findViewById(R.id.button11);
        btnCancel = findViewById(R.id.button12);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                intentToParent.putExtra("RESULT_MSG", "OK pressed");
                setResult(RESULT_OK, intentToParent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                intentToParent.putExtra("RESULT_MSG", "Cancel pressed");
                setResult(RESULT_CANCELED, intentToParent);
                finish();
            }
        });
    }
}