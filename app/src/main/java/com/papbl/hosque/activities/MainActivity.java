package com.papbl.hosque.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.papbl.hosque.R;
import com.papbl.hosque.data.dataJawaTimur;
import com.papbl.hosque.databinding.ActivityMainBinding;
import com.papbl.hosque.model.User;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final int RC_SIGN_IN = 9001;
    public final String TAG = "GoogleActivity";

    private BottomSheetBehavior bottomSheetBehaviorLogin, bottomSheetBehaviorRegis, bottomSheetBehaviorRegisCompleteData, bottomSheetBehaviorLupaPassword;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference, createUserRef;
    private String nama, no, kota, email, password, rePassword;
    private ProgressDialog PD;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private boolean checkLoginGoogleExixst = false;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        bottomSheetBehaviorLogin = BottomSheetBehavior.from(binding.bottomSheetLogin.bottomSheetLogin);
        bottomSheetBehaviorRegis = BottomSheetBehavior.from(binding.bottomSheetRegis.bottomSheetRegis);
        bottomSheetBehaviorLupaPassword = BottomSheetBehavior.from(binding.bottomSheetLupaPassword.bottomSheetLupaPassword);
        bottomSheetBehaviorRegisCompleteData = BottomSheetBehavior.from(binding.bottomSheetRegisdata.bottomSheetRegisdata);
        binding.btnGoLogin.setOnClickListener(this);
        binding.btnGoRegis.setOnClickListener(this);

        binding.bottomSheetLogin.btnLogin.setOnClickListener(this);
        binding.bottomSheetLupaPassword.btnLupaPassword.setOnClickListener(this);
        binding.bottomSheetLogin.btnGoogle.setOnClickListener(this);
        binding.bottomSheetLogin.tvGoRegis.setOnClickListener(this);
        binding.bottomSheetRegis.tvGoLogin.setOnClickListener(this);
        binding.bottomSheetLogin.tvLupaPassword.setOnClickListener(this);

        binding.bottomSheetRegis.btnRegisternext.setOnClickListener(this);
        binding.bottomSheetRegisdata.btnRegister.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_goLogin:
                bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btn_Google:
                loginGoogle();
                break;
            case R.id.btn_goRegis:
                bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btn_Login:
                email = binding.bottomSheetLogin.etEmailLogin.getText().toString();
                password = binding.bottomSheetLogin.etPasswordLogin.getText().toString();
                if (!(email.equals("") && password.equals(""))) {
                    Log.d("Cek", email + password);
                    loginUser(email, password);
                } else {
                    Toast.makeText(this, "Silahkan lengkapi data", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_Lupa_Password:
                email = binding.bottomSheetLupaPassword.etEmailLupaPassword.getText().toString();
                if (!email.equals("")) {
                    lupaPassword(email);
                } else {
                    Toast.makeText(this, "Email tidak boleh dikosongi", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_goRegis:
                bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.tv_goLogin:
                bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.tvLupaPassword:
                bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehaviorLupaPassword.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btn_registernext:

                email = binding.bottomSheetRegis.etEmailRegister.getText().toString();
                password = binding.bottomSheetRegis.etPasswordRegister.getText().toString();
                rePassword = binding.bottomSheetRegis.etUlangiPasswordRegister.getText().toString();
                if (!(email.equals("") && password.equals("") && rePassword.equals(""))) {
                    if (password.equals(rePassword)) {
                        bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        bottomSheetBehaviorRegisCompleteData.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Silahkan lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_register:
                PD = new ProgressDialog(MainActivity.this);
                PD.setMessage("Loading...");
                PD.setCancelable(true);
                PD.setCanceledOnTouchOutside(false);
                PD.show();
                email = binding.bottomSheetRegis.etEmailRegister.getText().toString();
                password = binding.bottomSheetRegis.etPasswordRegister.getText().toString();
                rePassword = binding.bottomSheetRegis.etUlangiPasswordRegister.getText().toString();
                if (!(email.equals("") && password.equals("") && rePassword.equals(""))) {
                    nama = binding.bottomSheetRegisdata.etNamaRegister.getText().toString();
                    no = binding.bottomSheetRegisdata.etNoTelpRegister.getText().toString();
                    kota = binding.bottomSheetRegisdata.etKotaRegister.getText().toString();

                    if (!(nama.equals("") && no.equals("") && kota.equals(""))) {
                        if (checkKota(nama)) {
                            adminExist(nama);
                        } else {
                            registerUser(nama, no, kota, email, password, "", false);
                        }
                    } else {
                        Toast.makeText(this, "Silahkan lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    nama = binding.bottomSheetRegisdata.etNamaRegister.getText().toString();
                    no = binding.bottomSheetRegisdata.etNoTelpRegister.getText().toString();
                    kota = binding.bottomSheetRegisdata.etKotaRegister.getText().toString();
                    if (!(nama.equals("") && no.equals("") && kota.equals(""))) {
                        firebaseAuthWithGoogle(account);
                    } else {
                        Toast.makeText(this, "Silahkan lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }


                }
                PD.dismiss();

        }
    }

    private void loginUser(String email, String password) {
        PD = new ProgressDialog(MainActivity.this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        PD.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = currentUser.getUid();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("patient").child(uid);


                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String nama = Objects.requireNonNull(dataSnapshot.child("nama").getValue()).toString();
                            Toast.makeText(MainActivity.this, nama, Toast.LENGTH_LONG).show();

                            if (checkAdmin(nama)) {
                                Intent intent = new Intent(MainActivity.this, MenuAdminActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    PD.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                    PD.dismiss();
                }
            }
        });
    }

    private void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);

                Query databaseReference =  FirebaseDatabase.getInstance().getReference().child("users").child("patient").orderByChild("email").equalTo(account.getEmail().toString());

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            bottomSheetBehaviorRegisCompleteData.setState(BottomSheetBehavior.STATE_EXPANDED);
//                            firebaseAuthWithGoogle(account);
                        } else {
                            checkLoginGoogleExixst = true;
                            PD = new ProgressDialog(MainActivity.this);
                            PD.setMessage("Loading...");
                            PD.setCancelable(true);
                            PD.setCanceledOnTouchOutside(false);
                            PD.show();
                            firebaseAuthWithGoogle(account);
                            bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (checkLoginGoogleExixst) {
                                Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                FirebaseUser user = auth.getCurrentUser();

                                email = user.getEmail();

                                writeUser(nama, no, kota, email, "", false);
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private boolean checkAdmin(String nama) {
        boolean checkKota = false;
        dataJawaTimur dt = new dataJawaTimur();
        for (String kotaJatim : dt.kota) {

            if (nama.equalsIgnoreCase(kotaJatim)) {
                checkKota = true;
            }
        }
        return checkKota;
    }

    @Override
    protected void onStart() {
        super.onStart();

        PD = new ProgressDialog(MainActivity.this);
        PD.setMessage("Memeriksa data...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        PD.show();

        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference fotoIdRef;

            fotoIdRef = FirebaseDatabase.getInstance().getReference().child("users").child("patient").child(uid);

            fotoIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String fotoIdentidas = dataSnapshot.child("fotoIdentitas").getValue().toString();
                    if (fotoIdentidas.equals("")) {
                        showAlert();
                        PD.dismiss();
                    } else if (fotoIdentidas.equals("isAdmin")) {
                        PD.dismiss();
                        Intent intent = new Intent(MainActivity.this, MenuAdminActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        PD.dismiss();
                        Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            PD.dismiss();
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Atur Foto identitas sekarang?")
                .setTitle("Perhatian!!");
        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MainActivity.this, BuktiDataDiriActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean checkKota(String nama) {
        boolean checkKota = false;
        dataJawaTimur dt = new dataJawaTimur();
        for (String kotaJatim : dt.kota) {
            if (nama.equalsIgnoreCase(kotaJatim)) {
                checkKota = true;
            }
        }
        return checkKota;
    }

    private void adminExist(final String nama) {
        databaseReference.child("Users").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean checkAdmin = false;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (nama.equalsIgnoreCase(childSnapshot.child("nama").getValue().toString())) {
                        Toast.makeText(MainActivity.this, "Anda telah terdaftar", Toast.LENGTH_SHORT).show();
                        checkAdmin = true;
                        break;
                    }
                }

                if (!checkAdmin) {
                    registerUser(nama, no, kota, email, password, "isAdmin", true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void registerUser(final String namaUser, final String noUser, final String kotaUser, final String emailUser, String passwordUser, final String isAdmin, final boolean statusAdmin) {

        auth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    writeUser(namaUser, noUser, kotaUser, emailUser, isAdmin, statusAdmin);
                } else {
                    Toast.makeText(MainActivity.this, "Gagal buat akun", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void lupaPassword(final String email) {
        PD = new ProgressDialog(MainActivity.this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        PD.show();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    PD.dismiss();
                    binding.bottomSheetLupaPassword.etEmailLupaPassword.setText("");
                    bottomSheetBehaviorLupaPassword.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Toast.makeText(MainActivity.this, "Email password reset telah terkirim, silahkan cek email anda", Toast.LENGTH_SHORT).show();
                } else {
                    PD.dismiss();
                    Toast.makeText(MainActivity.this, "Maaf email yang anda masukan tidak terdaftar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehaviorLogin.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (bottomSheetBehaviorRegis.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (bottomSheetBehaviorLupaPassword.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorLupaPassword.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    private void writeUser(final String namaUser, String noUser, String kotaUser, String emailUser, String isAdmin, boolean statusAdmin) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        createUserRef = FirebaseDatabase.getInstance().getReference().child("users").child("patient").child(userId);
        User user = new User(userId, namaUser, noUser, kotaUser, emailUser, isAdmin, "", statusAdmin);
        createUserRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (checkKota(namaUser)) {
                        Intent intent = new Intent(MainActivity.this, MenuAdminActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(MainActivity.this, BuktiDataDiriActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Gagal masukin data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        PD.dismiss();
    }


}
