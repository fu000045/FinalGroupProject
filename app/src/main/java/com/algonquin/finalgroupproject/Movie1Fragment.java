package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Movie1Fragment extends Fragment {
   public boolean isTablet;
    TextView txt_movieTitle;
    TextView txt_mainActors;
    TextView txt_length;
    TextView txt_genre;
    TextView txt_description;
    TextView txt_id;
    Button btn_delete;
    Button btn_update;
    Bundle bundle;

 @Nullable
 @Override
 public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

  View view = inflater.inflate(R.layout.movie_fragment_layout, container, false);

  txt_movieTitle = view.findViewById(R.id.movie_title_framelayout);
  txt_mainActors = view.findViewById(R.id.movie_actor_framelayout);
  txt_length = view.findViewById(R.id.movie_length_framelayout);
  txt_genre = view.findViewById(R.id.movie_genre_framelayout);
  txt_description = view.findViewById(R.id.movie_description_framelayout);
  txt_id = view.findViewById(R.id.id_fragment);
  btn_delete = view.findViewById(R.id.delete_movie);
  btn_update = view.findViewById(R.id.update_movie);

  //Get message passed by tablet(chatwindow) or phone(messagedetails)
  bundle = getArguments();

  String movieTitle = bundle.getString("Movie Title");
  String mainActors = bundle.getString("Main Actors");
  String length = bundle.getString("Length");
  String genre = bundle.getString("Genre");
  String description = bundle.getString("Description");
  final long id = bundle.getLong("ID");
  final long id_movie = bundle.getLong("IDmovie");

  txt_movieTitle.setText(movieTitle);
  txt_mainActors.setText(mainActors);
  txt_length.setText(length);
  txt_genre.setText(genre);
  txt_description.setText(description);
  txt_id.setText(String.valueOf(id));


  btn_delete.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
    if(isTablet) {
     Movie1 m1 = (Movie1)getActivity();
    // m1.deleteForTablet(id, id_movie);
     getFragmentManager().beginTransaction().remove(Movie1Fragment.this).commit();
    } else {
     Intent resultIntent = new Intent();
     resultIntent.putExtra("DeleteID", id);
     resultIntent.putExtra("IDMovie", id_movie);
     getActivity().setResult(Activity.RESULT_OK, resultIntent);
     getActivity().finish();
    }
   }
  });

  return view;
 }

 @Override
 public void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
 }

 public void setIsTablet(boolean b){
  isTablet = b;
 }
}
