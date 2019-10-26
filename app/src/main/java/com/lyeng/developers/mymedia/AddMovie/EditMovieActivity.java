package com.lyeng.developers.mymedia.AddMovie;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
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
import static com.lyeng.developers.mymedia.data.ConversionHelper.intToBoolArrayConv5Star;

public class EditMovieActivity extends AppCompatActivity {
    private AddMovieViewModel mAddMovieViewModel;
    EditText tv_add_movie_name, tv_add_movie_date, tv_add_movie_size, tv_add_movie_quality,
            tv_add_movie_runtime;
    ChipGroup cg_movie_seen_status, cg_movie_medium, cg_add_movie_genre;
    ImageButton btn_dt_choose;

    MovieRatingView cv_add_movie_rating;
    DatePickerDialog mDatePickerDialog;
    Chip save_chip, close_chip;
    Movie m;
    Spinner language;

    Boolean seen;
    String medium, date, genre_result;
    int rating = 0, minutes = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        Intent i = getIntent();

        mAddMovieViewModel = ViewModelProviders
                .of(this, new AddMovieViewModelFactory(this.getApplication(), m))
                .get(AddMovieViewModel.class);

        cg_movie_seen_status = findViewById(R.id.cg_add_movie_seen_status);
        tv_add_movie_name = findViewById(R.id.tv_add_movie_name);
        cg_movie_medium = findViewById(R.id.cg_add_movie_medium);
        tv_add_movie_date = findViewById(R.id.tv_add_movie_date);
        tv_add_movie_size = findViewById(R.id.tv_add_movie_size);
        language = findViewById(R.id.lang_spinner);
        tv_add_movie_quality = findViewById(R.id.tv_add_movie_quality);
        tv_add_movie_runtime = findViewById(R.id.tv_add_movie_runtime);
        cg_add_movie_genre = findViewById(R.id.cg_add_movie_genre);
        cv_add_movie_rating = findViewById(R.id.cv_add_movie_rating);
        save_chip = findViewById(R.id.chip_save);
        close_chip = findViewById(R.id.chip_cancel);

        //TODO: Preload this properly seen status
        cg_movie_seen_status.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup pChipGroup, int pI) {
                String seenId = getSelectedText(pChipGroup, pI);
                Log.i("EditMovieActivity", "ID: " + seenId);
                seen = seenId.equals("Saw");
            }
        });
        tv_add_movie_name.setText(i.getStringExtra("EXTRA_MOVIE_NAME"));
        //TODO: Get only one medium and fix bug with Netflix being selected always
        //TODO: Preload this properly medium
        cg_movie_medium = findViewById(R.id.cg_add_movie_medium);
        String[] tags = {"Netflix", "Amazon Prime Video", "Own", "TV", "HBO", "Hulu", "Theatre"};
        LayoutInflater inflater = LayoutInflater.from(EditMovieActivity.this);
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
                Log.i("EditMovieActivity", "ID: " + medium);
            }
        });

        String dt = i.getStringExtra("EXTRA_MOVIE_SEEN_DATE");
        tv_add_movie_date.setText(dt);
        btn_dt_choose = findViewById(R.id.btn_dt_choose);
        Calendar cal = Calendar.getInstance();
        btn_dt_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog = new DatePickerDialog(EditMovieActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date = dayOfMonth + "/" + month + "/" + year;
                                tv_add_movie_date.setText(date);
                            }
                        }, Integer.parseInt(dt.substring(6,9)), Integer.parseInt(dt.substring(3,4)), Integer.parseInt(dt.substring(0,1)));
                mDatePickerDialog.show();
            }
        });
        tv_add_movie_size.setText(i.getStringExtra("EXTRA_MOVIE_SEEN_SIZE"));
        //Load Language
        String compareValue = i.getStringExtra("EXTRA_MOVIE_SEEN_LANGUAGE");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mov_lang,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            language.setSelection(spinnerPosition);
        }
//        language.setSelection(((ArrayAdapter<String>)language.getAdapter()).getPosition(compareValue));
        tv_add_movie_quality.setText(i.getStringExtra("EXTRA_MOVIE_SEEN_QUALITY"));
        int runtime = i.getIntExtra("EXTRA_MOVIE_SEEN_RUNTIME", 0);
        tv_add_movie_runtime.setText(String.valueOf(runtime));
        //TODO: Preload this properly - genre
        String[] genres = {"Horror", "Thriller", "Comedy", "Mystery"};
        LayoutInflater genre_inflater = LayoutInflater.from(EditMovieActivity.this);
        for (String tag : genres) {
            Chip c = (Chip) genre_inflater.inflate(R.layout.chip_item, null, false);
            c.setText(tag);
            cg_add_movie_genre.addView(c);
        }
        cv_add_movie_rating.setMovieRating(intToBoolArrayConv5Star(i.getIntExtra("EXTRA_MOVIE_SEEN_RATING",1)));

        save_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //parse runtime
                if (tv_add_movie_runtime.getText().toString().equals("")) minutes = 0;
                else minutes = Integer.parseInt(tv_add_movie_runtime.getText().toString());
                //Bool to int conversion
                rating = boolArrayToIntConv(cv_add_movie_rating.getMovieRating());
                //Load genre
                for (int i = 0; i < cg_add_movie_genre.getChildCount(); i++) {
                    Chip c = (Chip) cg_add_movie_genre.getChildAt(i);
                    if (c.isChecked()) {
                        if (i < cg_add_movie_genre.getChildCount() - 1) {
                            genre_result = c.getText().toString() + ",";
                            Log.i("EditMovieActivity", genre_result);
                        } else {
                            genre_result = c.getText().toString();
                        }
                    }

                }
                m = new Movie(i.getStringExtra("EXTRA_MOVIE_ID"),
                        tv_add_movie_name.getText().toString(),
                        seen,
                        date,
                        medium,
                        genre_result,
                        tv_add_movie_size.getText().toString(),
                        language.getSelectedItem().toString(),
                        tv_add_movie_quality.getText().toString(),
                        rating,
                        minutes);
                mAddMovieViewModel.updateMovieToDB(m);
                Toast.makeText(getApplicationContext(), "Updating.. ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
