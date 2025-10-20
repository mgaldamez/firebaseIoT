package com.sv.unab.firebaseiot;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView labTemperature;
    TextView labHumidity;
    Button btnFan;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference firebaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        labTemperature = findViewById(R.id.textTemperature);
        labHumidity = findViewById(R.id.textHumidity);
        btnFan = findViewById(R.id.btnFan);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseReference = firebaseDatabase.getReference("IoT/");

        GetData();

        btnFan.setOnClickListener(view -> {
            if(btnFan.getText().toString().contains("Turn On"))
            {
                firebaseReference.child("FanBtn").setValue(true);
                btnFan.setText("Turn Off");
            }
            else
            {
                firebaseReference.child("FanBtn").setValue(false);
                btnFan.setText("Turn On");

            }
        });


    }

    private void GetData()
    {
        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                IoT iot = snapshot.getValue(IoT.class);
                Log.i("GetData", "onDataChange: "+iot.Temperature);
                if(iot != null){
                    labTemperature.setText(Integer.toString(iot.Temperature));
                    labHumidity.setText(Integer.toString(iot.Humidity));
                    if(iot.FanBtn)
                    {
                        btnFan.setText("Turn Off");
                    }
                    else {
                        btnFan.setText("Turn On");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}