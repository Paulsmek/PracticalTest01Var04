package ro.pub.cs.systems.eim.practicaltest01var04;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    private Button btnDisplay, btnNavigate;
    private TextView textT;

    private CheckBox box1, box2;

    private EditText text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var04_main);

        btnDisplay = findViewById(R.id.button1);
        box1 = findViewById(R.id.checkBox1);
        box2 = findViewById(R.id.checkBox2);
        textT = findViewById(R.id.textView1);
        text1 = findViewById(R.id.editText1);
        text2 = findViewById(R.id.editText2);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (box1.isChecked() || box2.isChecked()) {
                    String name = text1.getText().toString();
                    String group = text2.getText().toString();
                    if ((name == null && box1.isChecked()) || (group == null && box2.isChecked())) {
                        //Toast.makeText(this, "eroare no text", Toast.LENGTH_LONG).show();
                    }
                    else {
                        textT.setText(name + " " + group);
                    }
                }

            }
        });
    }

//    private void checkAndStartService() {
//        int left = Integer.parseInt(counterLeft.getText().toString());
//        int right = Integer.parseInt(counterRight.getText().toString());
//        int sum = left + right;
//
//        // folosim pragul definit în service
//        if (sum > PracticalTest01Service.SUM_THRESHOLD && !serviceStarted) {
//            Intent serviceIntent = new Intent(getApplicationContext(), PracticalTest01Service.class);
//            // îi trimitem valorile actuale
//            serviceIntent.putExtra("NUMBER_1", left);
//            serviceIntent.putExtra("NUMBER_2", right);
//            startService(serviceIntent);
//            serviceStarted = true;
//            // doar ca feedback
//            Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
//        }
//    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        //unregisterReceiver(messageReceiver);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

//        savedInstanceState.putCharSequence("KEY_LEFT_COUNTER", counterLeft.getText());
//        savedInstanceState.putCharSequence("KEY_RIGHT_COUNTER", counterRight.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        CharSequence keyLeft = savedInstanceState.getCharSequence("KEY_LEFT_COUNTER");
        CharSequence keyRight = savedInstanceState.getCharSequence("KEY_RIGHT_COUNTER");

//        counterLeft.setText(keyLeft);
//        counterRight.setText(keyRight);
    }

    @Override
    protected void onDestroy() {
        //stopService(new Intent(getApplicationContext(), PracticalTest01Service.class));
        super.onDestroy();
    }
}