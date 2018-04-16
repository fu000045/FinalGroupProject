package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Movie1Fragment extends Fragment {
   public boolean isTablet;

    EditText e_movieTitle;
    EditText e_mainActors;
    EditText e_length;
    EditText e_genre;
    EditText e_description;
    TextView txt_id;

    Button btn_delete;
    Button btn_update;
    Button btn_cancel;
    Bundle bundle;

 @Nullable
 @Override
 public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
     //Get message passed by tablet(chatwindow) or phone(messagedetails)
     bundle = getArguments();
     View view = inflater.inflate(R.layout.movie_fragment_layout, container, false);

     e_movieTitle = view.findViewById(R.id.edittext_movieTitle);
     e_mainActors = view.findViewById(R.id.edittext_mainActors);
     e_length = view.findViewById(R.id.edittext_length);
     e_genre = view.findViewById(R.id.edittext_genre);
     e_description = view.findViewById(R.id.edittext_description);
     txt_id = view.findViewById(R.id.id_fragment);
     btn_delete = view.findViewById(R.id.delete_movie);
     btn_update = view.findViewById(R.id.update_movie);
     btn_cancel = view.findViewById(R.id.cancel);


     String movieTitle = bundle.getString("Movie Title");
     String mainActors = bundle.getString("Main Actors");
     String length = bundle.getString("Length");
     String genre = bundle.getString("Genre");
     String description = bundle.getString("Description");
     final long Id = bundle.getLong("ID");
//  final long id_inMovie = bundle.getLong("IDInMovie");

     e_movieTitle.setText(movieTitle);
     e_mainActors.setText(mainActors);
     e_length.setText(length);
     e_genre.setText(genre);
     e_description.setText(description);
     txt_id.setText(String.valueOf(Id));


     btn_update.setOnClickListener(new View.OnClickListener() {
         public void onClick(View view) {
             String newTitle = e_movieTitle.getText().toString();
             String newMainActors = e_mainActors.getText().toString();
             String newLength = e_length.getText().toString();
             String newGenre = e_genre.getText().toString();
             String newDescription = e_description.getText().toString();

             if (isTablet) {
                 Movie1 movie1 = (Movie1) getActivity();
                 movie1.updateForTablet(Id, newTitle, newMainActors, newLength, newGenre, newDescription);
             } else {
                 Intent resultIntent = new Intent();
                 resultIntent.putExtra("ID", Id);
                 resultIntent.putExtra("Movie Title", newTitle);
                 resultIntent.putExtra("Main Actors", newMainActors);
                 resultIntent.putExtra("Length", newLength);
                 resultIntent.putExtra("Genre", newGenre);
                 resultIntent.putExtra("Description", newDescription);
                 getActivity().setResult(666, resultIntent);
                 getActivity().finish();
             }
         }
     });
     btn_delete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             AlertDialog.Builder builder = new AlertDialog.Builder(Movie1Fragment.this.getActivity());
             // 2. Chain together various setter methods to set the dialog characteristics
             builder.setMessage(R.string.movie_dialog_message)
                     .setTitle(R.string.movie_dialog_title)
                     .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             // User clicked OK button to end the activity and go back to the previous activity
                             if (isTablet) {
                                 Movie1 m1 = (Movie1) getActivity();
                                 m1.deleteForTablet(Id);
                                 getFragmentManager().beginTransaction().remove(Movie1Fragment.this).commit();
                             } else {
                                 Intent resultIntent = new Intent();
                                 resultIntent.putExtra("ID", Id);
                                 // resultIntent.putExtra("IDInMovie", id_inMovie);
                                 getActivity().setResult(Activity.RESULT_OK, resultIntent);
                                 getActivity().finish();
                             }
                         }
                     })
                     .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                         }
                     })
                     .show();
         }
     });
     //cancel button to return to quizpool activity
     btn_cancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(getActivity(), Movie1.class);
             startActivity(intent);
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
