package com.lyeng.developers.mymedia.AddMovie;

import android.app.DatePickerDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.lyeng.developers.mymedia.R;
import com.lyeng.developers.mymedia.SeeMovie.MovieRatingView;
import com.lyeng.developers.mymedia.data.Movie;

import java.util.Calendar;

import static com.lyeng.developers.mymedia.data.ConversionHelper.boolArrayToIntConv;

public class AddMovieActivity extends AppCompatActivity {

    ChipGroup cg_movie_seen_status, cg_movie_medium, cg_add_movie_genre;
    ImageButton btn_dt_choose;
    EditText tv_add_movie_name, tv_add_movie_date, tv_add_movie_size, tv_add_movie_quality,
            tv_add_movie_runtime;
    MovieRatingView cv_add_movie_rating;
    DatePickerDialog mDatePickerDialog;
    Chip save_chip, close_chip;
    Movie m;
    Spinner language;

    Boolean seen;
    String medium, date, movieId, genre_result;
    int rating = 0, minutes = 0;

    AddMovieViewModel mAddMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        mAddMovieViewModel = ViewModelProviders
                .of(this, new AddMovieViewModelFactory(this.getApplication(), m))
                .get(AddMovieViewModel.class);

        mAddMovieViewModel.getMovieIs().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String pS) {
                int newId = Integer.parseInt(pS.substring(2));
                newId += 1;
                Log.i("AddMovieActivity", String.valueOf(newId));
                int los = String.valueOf(newId).length();
                if (los == 1) movieId = "MV000" + String.valueOf(newId);
                else if (los == 2) movieId = "MV00" + String.valueOf(newId);
                else if (los == 3) movieId = "MV0" + String.valueOf(newId);
                else movieId = "MV" + String.valueOf(newId);
            }
        });

        cg_movie_seen_status = findViewById(R.id.cg_add_movie_seen_status);
        cg_movie_seen_status.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup pChipGroup, int pI) {
                String seenId = getSelectedText(pChipGroup, pI);
                Log.i("AddMovieActivity", "ID: " + seenId);
                seen = seenId.equals("Saw");
            }
        });

        tv_add_movie_name = findViewById(R.id.tv_add_movie_name);

        //TODO: Get only one medium and fix bug with Netflix being selected always
        cg_movie_medium = findViewById(R.id.cg_add_movie_medium);
        String[] tags = {"Netflix", "Amazon Prime Video", "Own", "TV", "HBO", "Hulu", "Theatre"};
        LayoutInflater inflater = LayoutInflater.from(AddMovieActivity.this);
        for (String tag : tags) {
            Chip c = (Chip) inflater.inflate(R.layout.chip_item, null, false);
            c.setText(tag);
            cg_movie_medium.addView(c);
        }
        cg_movie_medium.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup pChipGroup, int pI) {
                Chip chip = pChipGroup.findViewById(pChipGroup.getCheckedChipId());
                if (chip != null) {
                    for (int i = 0; i < pChipGroup.getChildCount(); ++i) {
                        pChipGroup.getChildAt(i).setClickable(true);
                    }
                    //TODO: Set single clickable
                    chip.setClickable(false);
                }
                medium = getSelectedText(pChipGroup, pI);
                Log.i("AddMovieActivity", "ID: " + medium);
            }
        });


        //btn_dt_choose to set date to tv_add_movie_date
        tv_add_movie_date = findViewById(R.id.tv_add_movie_date);
        btn_dt_choose = findViewById(R.id.btn_dt_choose);
        //TODO: Change calendar color
        Calendar cal = Calendar.getInstance();
        btn_dt_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog = new DatePickerDialog(AddMovieActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date = dayOfMonth + "/" + month + "/" + year;
                                tv_add_movie_date.setText(date);
                            }
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                mDatePickerDialog.show();
            }
        });


        //TODO Get multiple genre text properly
        cg_add_movie_genre = findViewById(R.id.cg_add_movie_genre);
        String[] genres = {"Horror", "Thriller", "Comedy", "Mystery"};
        LayoutInflater genre_inflater = LayoutInflater.from(AddMovieActivity.this);
        for (String tag : genres) {
            Chip c = (Chip) genre_inflater.inflate(R.layout.chip_item, null, false);
            c.setText(tag);
            cg_add_movie_genre.addView(c);
        }

        tv_add_movie_size = findViewById(R.id.tv_add_movie_size);
        language = findViewById(R.id.lang_spinner);
        tv_add_movie_quality = findViewById(R.id.tv_add_movie_quality);
        cv_add_movie_rating = findViewById(R.id.cv_add_movie_rating);
        tv_add_movie_runtime = findViewById(R.id.tv_add_movie_runtime);

        save_chip = findViewById(R.id.chip_save);
        save_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //parse runtime
                String run;
                if (tv_add_movie_runtime.getText().toString().equals("")) run = "0";
                else run = tv_add_movie_runtime.getText().toString();
                minutes = Integer.parseInt(run);
                //Bool to int conversion
                rating = boolArrayToIntConv(cv_add_movie_rating.getMovieRating());
                //Load genre
                for (int i = 0; i < cg_add_movie_genre.getChildCount(); i++) {
                    Chip c = (Chip) cg_add_movie_genre.getChildAt(i);
                    if (c.isChecked()) {
                        if (i < cg_add_movie_genre.getChildCount() - 1) {
                            genre_result = c.getText().toString() + ",";
                            Log.i("AddMovieActivity", genre_result);
                        } else {
                            genre_result = c.getText().toString();
                        }
                    }

                }
                m = new Movie(movieId, tv_add_movie_name.getText().toString(),
                        seen,
                        date,
                        medium,
                        genre_result,
                        //(pMovieSize != null) ? pMovieSize : "unspecified"
                        tv_add_movie_size.getText().toString(),
                        language.getSelectedItem().toString(),
                        tv_add_movie_quality.getText().toString(),
                        rating,
                        minutes);
                Log.i("AddMovieActivity", movieId + " " + seen + " " + date + " " + medium + " " + genre_result +
                        " " + tv_add_movie_size.getText().toString() + " " + language.getSelectedItem().toString() + " " +
                        tv_add_movie_quality.getText().toString() + " " + minutes);
                mAddMovieViewModel.addMovieToDB(m);
                Toast.makeText(getApplicationContext(), "Saving.. ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        close_chip = findViewById(R.id.chip_cancel);
        close_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getSelectedText(ChipGroup pChipGroup, int pI) {
        Chip mySelection = pChipGroup.findViewById(pI);
        return (mySelection == null) ? "" : mySelection.getText().toString();
    }
}
