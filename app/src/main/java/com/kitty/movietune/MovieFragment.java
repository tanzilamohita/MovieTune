package com.kitty.movietune;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import retrofit2.Call;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {
    private static final String TAG = "MovieFragment";

    private View view;
    private LinearLayoutManager mgridLayoutManager;
    private static RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    TextView txtv;
    private String tabName;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;

    private MovieService movieService;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie, container, false);

        Bundle bundle=getArguments();
        tabName = String.valueOf(bundle.getString("tab"));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mgridLayoutManager =  new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mgridLayoutManager);

        movieAdapter = new MovieAdapter(getActivity());
        recyclerView.setAdapter(movieAdapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(mgridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(tabName == "New Release"){
                            loadNextPage_newRelease();
                        } else if(tabName == "Top Rated") {
                            loadNextPage_topRated();
                        } else if(tabName == "Upcoming") {
                            loadNextPage_upcoming();
                        }
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        if(tabName == "New Release"){
            movieService = MovieApi.getClient().create(MovieService.class);
            loadFirstPage_newRelease();
        } else if(tabName == "Top Rated") {
            movieService = MovieApi.getClient().create(MovieService.class);
            loadFirstPage_topRated();
        } else if(tabName == "Upcoming") {
            movieService = MovieApi.getClient().create(MovieService.class);
            loadFirstPage_upcoming();
        }

        return view;
    }

    //--------------------New Release Start-------------//
    private void loadFirstPage_newRelease() {
        Log.d(TAG, "loadFirstPage: ");

        callNewReleaseMoviesApi().enqueue(new retrofit2.Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
                // Got data. Send it to adapter

                List<MovieList> results = fetchResults_newRelease(response);
                movieAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) movieAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private List<MovieList> fetchResults_newRelease(retrofit2.Response<MoviesResponse> response) {
        MoviesResponse moviesResponse = response.body();
        return moviesResponse.getResults();
    }

    private void loadNextPage_newRelease() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        callNewReleaseMoviesApi().enqueue(new retrofit2.Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
                movieAdapter.removeLoadingFooter();
                isLoading = false;

                List<MovieList> results = fetchResults_newRelease(response);
                movieAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) movieAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Call<MoviesResponse> callNewReleaseMoviesApi() {
        return movieService.getNewReleaseMovies(
                "c37d3b40004717511adb2c1fbb15eda4",
                currentPage
        );
    }

    //--------------------New Release End-------------//

    //--------------------Top Rated Start-------------//

    private void loadFirstPage_topRated() {
        Log.d(TAG, "loadFirstPage: ");

        callTopRatedMoviesApi().enqueue(new retrofit2.Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
                // Got data. Send it to adapter

                List<MovieList> results = fetchResults_toprated(response);
                movieAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) movieAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private List<MovieList> fetchResults_toprated(retrofit2.Response<MoviesResponse> response) {
        MoviesResponse moviesResponse = response.body();
        return moviesResponse.getResults();
    }

    private void loadNextPage_topRated() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        callTopRatedMoviesApi().enqueue(new retrofit2.Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
                movieAdapter.removeLoadingFooter();
                isLoading = false;

                List<MovieList> results = fetchResults_toprated(response);
                movieAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) movieAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Call<MoviesResponse> callTopRatedMoviesApi() {
        return movieService.getTopRatedMovies(
                "c37d3b40004717511adb2c1fbb15eda4",
                currentPage
        );
    }

    //--------------------Top Rated End-------------//

    //--------------------Upcoming Start-------------//

    private void loadFirstPage_upcoming() {
        Log.d(TAG, "loadFirstPage: ");

        callUpcomingMoviesApi().enqueue(new retrofit2.Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
                // Got data. Send it to adapter

                List<MovieList> results = fetchResults_upcoming(response);
                movieAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) movieAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private List<MovieList> fetchResults_upcoming(retrofit2.Response<MoviesResponse> response) {
        MoviesResponse moviesResponse = response.body();
        return moviesResponse.getResults();
    }

    private void loadNextPage_upcoming() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        callUpcomingMoviesApi().enqueue(new retrofit2.Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
                movieAdapter.removeLoadingFooter();
                isLoading = false;

                List<MovieList> results = fetchResults_upcoming(response);
                movieAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) movieAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Call<MoviesResponse> callUpcomingMoviesApi() {
        return movieService.getUpcomingMovies(
                "c37d3b40004717511adb2c1fbb15eda4",
                currentPage
        );
    }

    //--------------------Upcoming End-------------//

}
