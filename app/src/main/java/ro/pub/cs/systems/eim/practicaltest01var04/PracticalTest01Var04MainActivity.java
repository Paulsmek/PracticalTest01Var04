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

    private static final int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    private boolean serviceStarted = false;
    private android.content.BroadcastReceiver messageReceiver;

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
                        Toast.makeText(null, "eroare no text", Toast.LENGTH_LONG).show();
                    }
                    textT.setText((box1.isChecked() ? name:"") + " " + (box2.isChecked() ? group:""));
                }
                else {
                    textT.setText("");
                }
                checkAndStartService();
            }
        });

        btnNavigate = (Button)findViewById(R.id.button2);

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = text1.getText().toString();
                String group = text2.getText().toString();

                Intent intent = new Intent(PracticalTest01Var04MainActivity.this,
                        PracticalTest01Var04SecondaryActivity.class);

                intent.putExtra("name", name);
                intent.putExtra("group", group);

                startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
            }
        });

        messageReceiver = new android.content.BroadcastReceiver() {
            @Override
            public void onReceive(android.content.Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) return;

                String action = intent.getAction();
                String s = intent.getStringExtra("s");

                android.util.Log.d("PracticalTest01", s);

                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        };
    }

    private void checkAndStartService() {
        String name = text1.getText().toString();
        String group = text2.getText().toString();

        // folosim pragul definit în service
        if (name != null && group != null) {
            Intent serviceIntent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
            // îi trimitem valorile actuale
            serviceIntent.putExtra("name", name);
            serviceIntent.putExtra("group", group);
            startService(serviceIntent);
            serviceStarted = true;
            // doar ca feedback
            Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @Deprecated
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "OKAY", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "CANCEL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onResume() {
        super.onResume();
        android.content.IntentFilter filter = new android.content.IntentFilter();
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(messageReceiver, filter, RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(messageReceiver, filter);
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageReceiver);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

       savedInstanceState.putBoolean("box1", box1.isChecked());
       savedInstanceState.putBoolean("box2", box2.isChecked());
       savedInstanceState.putCharSequence("text1", text1.getText());
       savedInstanceState.putCharSequence("text2", text2.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Boolean b1 = savedInstanceState.getBoolean("box1");
        Boolean b2 = savedInstanceState.getBoolean("box2");
        CharSequence t1 = savedInstanceState.getCharSequence("text1");
        CharSequence t2 = savedInstanceState.getCharSequence("text2");

        box1.setActivated(b1);
        box2.setActivated(b2);
        text1.setText(t1);
        text2.setText(t2);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getApplicationContext(), PracticalTest01Var04Service.class));
        super.onDestroy();
    }
}