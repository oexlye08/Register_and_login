package id.web.registerandlogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private EditText ed_name, ed_email, ed_password, ed_c_password;
    private Button btn_regist;
    private ProgressBar loading;
    CircleImageView profile_image;
    private static String URL_REGIST = "http://206.189.6.93:8087/oki/register.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        ed_c_password = findViewById(R.id.ed_c_password);
        btn_regist = findViewById(R.id.btn_regist);
        profile_image = findViewById(R.id.profile_image);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //di bawah
                Regist();

            }
        });

    }

    private void Regist(){

        final String name = this.ed_name.getText().toString().trim();
        final String email = this.ed_email.getText().toString().trim();
        final String password = this.ed_password.getText().toString().trim();
        final String c_password = this.ed_c_password.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            ed_name.setError("Please Input Name");
            ed_name.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)){
            ed_email.setError("Please Input Email");
            ed_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ed_email.setError("Enter a valid Email");
            ed_email.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            ed_password.setError("Please Input Password");
            ed_password.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(c_password)){
            ed_c_password.setError("Please Input Password");
            ed_c_password.requestFocus();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success"); //di samakan dengan di php


                            if (success.equals("1")){
                                Toast.makeText(RegisterActivity.this, "REGISTER SUKSES!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility((View.GONE));
                            btn_regist.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegisterActivity.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility((View.GONE));
                        btn_regist.setVisibility(View.VISIBLE);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // name yang hijau menuju register.php $_POST['name'] (tabel php);
                // name yang ungu menuju ke final String name = this.name.getText().toString().trim();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // StringRequest stringRequest

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
