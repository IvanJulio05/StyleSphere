package com.example.tiendaropa;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiendaropa.Models.User;
import com.example.tiendaropa.Models.UserCallBack;
import com.example.tiendaropa.Services.UserService;
import com.google.firebase.auth.FirebaseAuth;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button cerrar;
    private RelativeLayout datosPersonal;
    private TextView addFoto;
    private RelativeLayout vender;
    private UserService userService= new UserService();

    private ImageView photoPefilUser;
    private static final int COD_SEL_IMAGE= 300;
    private Uri image_url;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PerfilFragment() {
        // Required empty public constructor
    }





    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment perfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(getActivity() != null && getActivity() instanceof AppCompatActivity){
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }

        View view = inflater.inflate(R.layout.fragment_perfil,container,false);
        photoPefilUser = (ImageView) view.findViewById(R.id.imageView5);
        showPhoto();
        TextView nombrePerfil =(TextView) view.findViewById(R.id.txtNombrePerfil);
        //Intent intent= getActivity().getIntent();
        Intent intent= getActivity().getIntent();

        userService.findUserByEmail(intent.getStringExtra("email"), new UserCallBack() {
            @Override
            public void recibirUsuario(User u) {
                if(u != null){
                    if(u.getName()!=null){
                        nombrePerfil.setText(u.getName());
                    }
                    else{
                        nombrePerfil.setText("Sin Nombre");
                    }
                }
            }
        });



        //agregar foto
        addFoto = (TextView) view.findViewById(R.id.txtagregarFoto);

        addFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploaPhoto();
            }
        });

        //nombrePerfil.setText("carlos juan");
        cerrar = (Button) view.findViewById(R.id.btCerrarSesion);
        datosPersonal = (RelativeLayout) view.findViewById(R.id.rlDatosPersonales);
        vender = (RelativeLayout) view.findViewById(R.id.rlVender);

        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent venderIntent = new Intent(getActivity(),Vender.class);
                startActivity(venderIntent);
            }
        });


        datosPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDatosPersonal = new Intent(getActivity(),InformacionPersonal.class);
                Intent intent= getActivity().getIntent();
                intentDatosPersonal.putExtra("email",intent.getStringExtra("email"));
                startActivity(intentDatosPersonal);
            }
        });


        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEdit= prefs.edit();
                prefsEdit.clear();
                prefsEdit.apply();

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);

            }
        });
        //return inflater.inflate(R.layout.fragment_perfil, container, false);
        return view;

    }

    public void uploaPhoto(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,COD_SEL_IMAGE);
    }

    public void showPhoto(){
        Intent in = getActivity().getIntent();

        userService.findUserByEmail(in.getStringExtra("email"), new UserCallBack() {
            @Override
            public void recibirUsuario(User u) {
                if(u!=null){
                    if(u.getUrl_img()!=null && !u.getUrl_img().equals("")){

                        int radius = Math.min(photoPefilUser.getWidth(),photoPefilUser.getHeight())/2;

                        Glide.with(getContext())
                                .load(u.getUrl_img())
                                .centerCrop()
                                .transform(new RoundedCornersTransformation(radius,0))
                                .into(photoPefilUser);
                    }
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Intent in = getActivity().getIntent();
        if(requestCode == COD_SEL_IMAGE && resultCode == RESULT_OK && data!=null && data.getData() != null){
            image_url = data.getData();

            userService.uploadPhoto(image_url,in.getStringExtra("email"),getContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Mostrar la barra superior cuando el fragmento ya no está visible
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }



}