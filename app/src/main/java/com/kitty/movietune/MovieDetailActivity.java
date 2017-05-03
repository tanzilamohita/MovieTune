package com.kitty.movietune;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";

    private MovieList mMovie;
    ImageView backdrop;
    TextView vote;
    TextView title;
    TextView description;
    TextView m_title;
    TextView m_language;
    TextView m_year;
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        title = (TextView) findViewById(R.id.movie_title);
        backdrop = (ImageView) findViewById(R.id.movie_poster);
        m_title = (TextView) findViewById(R.id.m_title);
        m_year = (TextView) findViewById(R.id.m_year);
        vote = (TextView) findViewById(R.id.vote);
        description = (TextView) findViewById(R.id.movie_description);
        m_language = (TextView) findViewById(R.id.movie_language);

        title.setText(mMovie.getTitle());
        Picasso.with(this).load(mMovie.getBackdrop()).into(backdrop);
        m_title.setText(mMovie.getTitle());
        year = mMovie.getReleaseDate().substring(0, 4);
        m_year.setText("("+year+ ")");
        vote.setText(mMovie.getVote());
        description.setText(mMovie.getDescription());
        m_language.setText(mMovie.getLanguage().toUpperCase());
    }

}
