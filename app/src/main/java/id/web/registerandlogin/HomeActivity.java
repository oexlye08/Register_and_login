package id.web.registerandlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private TextView name, email;
    private Button btn_logout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        btn_logout = findViewById(R.id.btn_logout);

        HashMap<String, String> user = sessionManager.getUserDetai();
        String mName = user.get(sessionManager.NAME);
        String mEmail = user.get(sessionManager.EMAIL);

        name.setText(mName);
        email.setText(mEmail);


        //JIKA TIDAK MENGGUNAKAN SESSION MANAGER INI DI AKTIFKAN
//        Intent intent = getIntent();
//        String extraName = intent.getStringExtra("name");
//        String extraEmail = intent.getStringExtra("email");

//        name.setText(extraName);
//        email.setText(extraEmail);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //JIKA TANPA SESSION MANAGER DI AKTIFKAN
                //finish();

                sessionManager.logout();
            }
        });
    }
}
