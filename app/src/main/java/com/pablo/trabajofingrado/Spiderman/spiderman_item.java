package com.pablo.trabajofingrado.Spiderman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pablo.trabajofingrado.R;

import java.util.ArrayList;

public class spiderman_item extends AppCompatActivity implements MiAdapterSpiderman.ItemClicListener {
    DatabaseReference mybd;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> anios = new ArrayList<>();
    ArrayList<String> sinop = new ArrayList<>();
    ArrayList<Integer> fotos = new ArrayList<>();
    ArrayList<String> duraciones = new ArrayList<>();
    ArrayList<DatosSpiderman> listaPelis = new ArrayList<>();
    ArrayList<Integer> imagenActor = new ArrayList<>();
    ArrayList <String[]> actoresSep = new ArrayList<>();
    ArrayList <String[]> personajesSep = new ArrayList<>();
    static int elementoSpiderman = 0;
    MiAdapterSpiderman adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiderman_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24_white);
        getSupportActionBar().setTitle("Spiderman");

        nombrePelis();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.search_item);

        final SearchView searchView = (SearchView)searchItem.getActionView();

        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filtrar(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filtrado(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void nombrePelis(){
        mybd = FirebaseDatabase.getInstance().getReference();
        mybd.child("Personajes").child("0").child("peliculas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String nombre = ds.child("nombre").getValue().toString();
                        String anyo = ds.child("anio").getValue().toString();
                        String descrip = ds.child("descripcion").getValue().toString();
                        String duracion = ds.child("duracion").getValue().toString();
                        String actor = ds.child("actor").getValue().toString();
                        String[] ac = actor.split(",");
                        String personaje = ds.child("personaje").getValue().toString();
                        String[] perso = personaje.split(",");

                        spiderman_item.this.actoresSep.add(ac);
                        spiderman_item.this.personajesSep.add(perso);
                        spiderman_item.this.nombres.add(nombre);
                        spiderman_item.this.anios.add(anyo);
                        spiderman_item.this.sinop.add(descrip);
                        spiderman_item.this.duraciones.add(duracion);
                    }
                    fotos.add(R.drawable.cartelera_sp11);
                    fotos.add(R.drawable.cartelera_sp2);
                    fotos.add(R.drawable.cartelera_sp33);
                    fotos.add(R.drawable.cartelera_asp);
                    fotos.add(R.drawable.cartelera_asp2);
                    fotos.add(R.drawable.cartelera_shc);
                    fotos.add(R.drawable.cartelera_sffh);
                    fotos.add(R.drawable.cartelera_snwh);

                    for (int i = 0; i < nombres.size(); i++) {
                        DatosSpiderman objeto = new DatosSpiderman(nombres.get(i), anios.get(i), fotos.get(i));
                        listaPelis.add(objeto);
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    adapter = new MiAdapterSpiderman(getApplicationContext(),listaPelis, spiderman_item.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void itemClicked(DatosSpiderman datosSpiderman) {
        int posicion = nombres.indexOf(datosSpiderman.getNombre());
        Intent intent = new Intent(getApplicationContext(), SpidermanInfo.class);
        switch (posicion){
            case 0:
                elementoSpiderman = 0;
                intent.putExtra("estado", elementoSpiderman);
                intent.putExtra("nombre", nombres.get(0));
                intent.putExtra("foto", fotos.get(0));
                intent.putExtra("anio", anios.get(0));
                intent.putExtra("sinopsis", sinop.get(0));
                intent.putExtra("duracion", duraciones.get(0));
                intent.putExtra("listaActor", actoresSep.get(0));
                intent.putExtra("listaPer", personajesSep.get(0));
                imagenActor.clear();
                imagenActor.add(R.drawable.tobey_maguire);
                imagenActor.add(R.drawable.willem_dafoe);
                imagenActor.add(R.drawable.kirsten_dunst);
                imagenActor.add(R.drawable.james_franco);
                imagenActor.add(R.drawable.jk_simmons);
                imagenActor.add(R.drawable.cliff_robertson);
                imagenActor.add(R.drawable.rosemary_harris);
                imagenActor.add(R.drawable.ted_raimi);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 1:
                elementoSpiderman = 1;
                intent.putExtra("estado", elementoSpiderman);
                intent.putExtra("nombre", nombres.get(1));
                intent.putExtra("foto", fotos.get(1));
                intent.putExtra("anio", anios.get(1));
                intent.putExtra("sinopsis", sinop.get(1));
                intent.putExtra("duracion", duraciones.get(1));
                intent.putExtra("listaActor", actoresSep.get(1));
                intent.putExtra("listaPer", personajesSep.get(1));
                imagenActor.clear();
                imagenActor.add(R.drawable.tobey_maguire);
                imagenActor.add(R.drawable.kirsten_dunst);
                imagenActor.add(R.drawable.alfred_molina);
                imagenActor.add(R.drawable.james_franco);
                imagenActor.add(R.drawable.willem_dafoe);
                imagenActor.add(R.drawable.rosemary_harris);
                imagenActor.add(R.drawable.daniel_gillies);
                imagenActor.add(R.drawable.jk_simmons);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 2:
                elementoSpiderman = 2;
                intent.putExtra("estado", elementoSpiderman);
                intent.putExtra("nombre", nombres.get(2));
                intent.putExtra("foto", fotos.get(2));
                intent.putExtra("anio", anios.get(2));
                intent.putExtra("sinopsis", sinop.get(2));
                intent.putExtra("duracion", duraciones.get(2));
                intent.putExtra("listaActor", actoresSep.get(2));
                intent.putExtra("listaPer", personajesSep.get(2));
                imagenActor.clear();
                imagenActor.add(R.drawable.tobey_maguire);
                imagenActor.add(R.drawable.kirsten_dunst);
                imagenActor.add(R.drawable.james_franco);
                imagenActor.add(R.drawable.thomas_haden);
                imagenActor.add(R.drawable.topher_grace);
                imagenActor.add(R.drawable.bryce_dallas);
                imagenActor.add(R.drawable.james_cromwell);
                imagenActor.add(R.drawable.rosemary_harris);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 3:
                elementoSpiderman = 3;
                intent.putExtra("estado", elementoSpiderman);
                intent.putExtra("nombre", nombres.get(3));
                intent.putExtra("foto", fotos.get(3));
                intent.putExtra("anio", anios.get(3));
                intent.putExtra("sinopsis", sinop.get(3));
                intent.putExtra("duracion", duraciones.get(3));
                intent.putExtra("listaActor", actoresSep.get(3));
                intent.putExtra("listaPer", personajesSep.get(3));
                imagenActor.clear();
                imagenActor.add(R.drawable.andrew_garfield);
                imagenActor.add(R.drawable.emma_stone);
                imagenActor.add(R.drawable.rhys_ifans);
                imagenActor.add(R.drawable.denis_leary);
                imagenActor.add(R.drawable.campbell_scott);
                imagenActor.add(R.drawable.irrfan_khan);
                imagenActor.add(R.drawable.martin_sheen);
                imagenActor.add(R.drawable.sally_field);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 4:
                elementoSpiderman = 4;
                intent.putExtra("estado", elementoSpiderman);
                intent.putExtra("nombre", nombres.get(4));
                intent.putExtra("foto", fotos.get(4));
                intent.putExtra("anio", anios.get(4));
                intent.putExtra("sinopsis", sinop.get(4));
                intent.putExtra("duracion", duraciones.get(4));
                intent.putExtra("listaActor", actoresSep.get(4));
                intent.putExtra("listaPer", personajesSep.get(4));
                imagenActor.clear();
                imagenActor.add(R.drawable.andrew_garfield);
                imagenActor.add(R.drawable.emma_stone);
                imagenActor.add(R.drawable.jamie_foxx);
                imagenActor.add(R.drawable.dane_dehaan);
                imagenActor.add(R.drawable.campbell_scott);
                imagenActor.add(R.drawable.embeth_davidtz);
                imagenActor.add(R.drawable.colm_feore);
                imagenActor.add(R.drawable.paul_giamatti);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 5:
                elementoSpiderman = 5;
                intent.putExtra("estado", elementoSpiderman);
                intent.putExtra("nombre", nombres.get(5));
                intent.putExtra("foto", fotos.get(5));
                intent.putExtra("anio", anios.get(5));
                intent.putExtra("sinopsis", sinop.get(5));
                intent.putExtra("duracion", duraciones.get(5));
                intent.putExtra("listaActor", actoresSep.get(5));
                intent.putExtra("listaPer", personajesSep.get(5));
                imagenActor.clear();
                imagenActor.add(R.drawable.tom_holland);
                imagenActor.add(R.drawable.michel_keaton);
                imagenActor.add(R.drawable.robert_downey_jr);
                imagenActor.add(R.drawable.zendaya);
                imagenActor.add(R.drawable.marisa_tomei);
                imagenActor.add(R.drawable.martin_starr);
                imagenActor.add(R.drawable.jacob_batalon);
                imagenActor.add(R.drawable.laura_harrier);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 6:
                elementoSpiderman = 6;
                intent.putExtra("estado", elementoSpiderman);
                intent.putExtra("nombre", nombres.get(6));
                intent.putExtra("foto", fotos.get(6));
                intent.putExtra("anio", anios.get(6));
                intent.putExtra("sinopsis", sinop.get(6));
                intent.putExtra("duracion", duraciones.get(6));
                intent.putExtra("listaActor", actoresSep.get(6));
                intent.putExtra("listaPer", personajesSep.get(6));
                imagenActor.clear();
                imagenActor.add(R.drawable.tom_holland);
                imagenActor.add(R.drawable.jake_gyllenhall);
                imagenActor.add(R.drawable.zendaya);
                imagenActor.add(R.drawable.samuel_ljackson);
                imagenActor.add(R.drawable.jon_favreau);
                imagenActor.add(R.drawable.marisa_tomei);
                imagenActor.add(R.drawable.cabie_smulders);
                imagenActor.add(R.drawable.jacob_batalon);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 7:
                elementoSpiderman = 7;
                intent.putExtra("estado", elementoSpiderman);
                intent.putExtra("nombre", nombres.get(7));
                intent.putExtra("foto", fotos.get(7));
                intent.putExtra("anio", anios.get(7));
                intent.putExtra("sinopsis", sinop.get(7));
                intent.putExtra("duracion", duraciones.get(7));
                intent.putExtra("listaActor", actoresSep.get(7));
                intent.putExtra("listaPer", personajesSep.get(7));
                imagenActor.clear();
                imagenActor.add(R.drawable.tom_holland);
                imagenActor.add(R.drawable.andrew_garfield);
                imagenActor.add(R.drawable.tobey_maguire);
                imagenActor.add(R.drawable.zendaya);
                imagenActor.add(R.drawable.benedict_cumberbatch);
                imagenActor.add(R.drawable.jacob_batalon);
                imagenActor.add(R.drawable.jon_favreau);
                imagenActor.add(R.drawable.jamie_foxx);
                imagenActor.add(R.drawable.willem_dafoe);
                imagenActor.add(R.drawable.alfred_molina);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Este es default", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}