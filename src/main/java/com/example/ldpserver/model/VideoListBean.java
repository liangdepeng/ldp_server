package com.example.ldpserver.model;

import java.io.Serializable;
import java.util.List;

/**
 * 新的电影描述
 * <p>
 * Date: 2022/6/9 17:06
 * Author: liangdp
 */
public class VideoListBean {


    public int code;
    public DataDTO data;
    public String msg;
    public String requestId;

    public static class DataDTO {

        public MobilemoviecomingDTO mobilemoviecoming;
        public List<HotPlayMoviesDTO> hotPlayMovies;
    }

    public static class MobilemoviecomingDTO {

        public int totalMovieComings;
        public List<RecommendsDTO> recommends;
        public List<MoviecomingsDTO> moviecomings;

    }

    public static class RecommendsDTO {

        public String recommendTitle;
        public List<MoviesDTO> movies;
    }

    public static class MoviesDTO {

        public int movieId;
        public String title;
        public String score;
        public String imgUrl;
        public boolean is3D;
        public boolean isDMAX;
        public boolean isIMAX;
        public boolean isIMAX3D;
        public String type;
        public int duration;
        public int wantedCount;
        public String releaseDateStr;
        //        public String shortComment;
        public String summary;
        public int btnShow;
        public int releaseYear;
        public int releaseMonth;
        public int releaseDay;
        public String director;
        public String actors;
        public boolean isFilter;
        public boolean isTicket;
        public boolean isVideo;
        public String videoId;
        public boolean isWantSee;
    }

    public static class MoviecomingsDTO {

        public int movieId;
        public String title;
        public String score;
        public String imgUrl;
        public boolean is3D;
        public boolean isDMAX;
        public boolean isIMAX;
        public boolean isIMAX3D;
        public String type;
        public int duration;
        public int wantedCount;
        public String releaseDateStr;
        //        public String shortComment;
        public String summary;
        public int btnShow;
        public int releaseYear;
        public int releaseMonth;
        public int releaseDay;
        public String director;
        public String actors;
        public boolean isFilter;
        public boolean isTicket;
        public boolean isVideo;
        public int videoId;
        public boolean isWantSee;
    }

    public static class HotPlayMoviesDTO {


        public String imgUrl;
        public int videoId;
        public int movieId;
        public String score;
        public String movieCn;
        public String movieEn;
        public String title;
        public int currentType;
        public int btnShow;
        public String directorName;
        public String actorNames;
        public String type;
        public String duration;
        public int wantedCount;
        public long releaseDate;
        public String releaseDateStr;
        //        public String shortComment;
        public String paragraph;
        public boolean isVideo;
        public boolean is3D;
        public boolean isDMAX;
        public boolean isIMAX;
        public boolean isIMAX3D;
        public boolean isNew;
        public boolean isHot;

        // 手动添加
        public String url1;
        public String url2;
        public String url3;
    }
}
