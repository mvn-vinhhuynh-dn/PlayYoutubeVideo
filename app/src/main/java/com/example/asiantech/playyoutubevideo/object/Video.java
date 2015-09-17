package com.example.asiantech.playyoutubevideo.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by VinhHlb on 9/17/15.
 */
@Data
public class Video {

    /**
     * kind : youtube#searchListResponse
     * etag : "jOXstHOM20qemPbHbyzf7ztZ7rI/jprAMPkEjFaMRrptrb2LyDqJojc"
     * nextPageToken : CAIQAA
     * pageInfo : {"totalResults":59,"resultsPerPage":2}
     * items : [{"kind":"youtube#searchResult","etag":"\"jOXstHOM20qemPbHbyzf7ztZ7rI/UGMoPHmzXYV4bkgEyHBiWPUaz-c\"","id":{"kind":"youtube#video","videoId":"mGCpd2908Ys"},"snippet":{"publishedAt":"2013-04-11T08:16:57.000Z","channelId":"UCwr3_8hNxNQKJz1uRVd6Jrw","title":"Extra English episode 1 Hectors arrival (part2)","description":"Eso es un series de vídeos que podría ayudarte mucho, estilo de Friends pero en inglés Británico. Los primeros están subtitulado para ayudar como hablamos en inglés, para comprender y para cuando llegas el punto de escribir tienes un idea como deletrearlo.","thumbnails":{"default":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/default.jpg"},"medium":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/mqdefault.jpg"},"high":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/hqdefault.jpg"}},"channelTitle":"Christopher Britt","liveBroadcastContent":"none"}},{"kind":"youtube#searchResult","etag":"\"jOXstHOM20qemPbHbyzf7ztZ7rI/X0eo4rOMKG2ydhwomX_dS-Uc4b4\"","id":{"kind":"youtube#video","videoId":"sKrf3yj2fi4"},"snippet":{"publishedAt":"2014-09-08T19:17:09.000Z","channelId":"UCJFwL5dF3XywGQe9UpZcZcA","title":"Extra English episode 7 full with substitles","description":"","thumbnails":{"default":{"url":"https://i.ytimg.com/vi/sKrf3yj2fi4/default.jpg"},"medium":{"url":"https://i.ytimg.com/vi/sKrf3yj2fi4/mqdefault.jpg"},"high":{"url":"https://i.ytimg.com/vi/sKrf3yj2fi4/hqdefault.jpg"}},"channelTitle":"Maria Aparecida","liveBroadcastContent":"none"}}]
     */
    private String kind;
    private String etag;
    private String nextPageToken;
    private PageInfoEntity pageInfo;
    private List<ItemsEntity> items;

    @Data
    public class PageInfoEntity {
        /**
         * totalResults : 59
         * resultsPerPage : 2
         */
        @SerializedName("totalResults")
        private int totalResults;
        @SerializedName("resultsPerPage")
        private int resultsPerPage;
    }

    @Data
    public class ItemsEntity {
        /**
         * kind : youtube#searchResult
         * etag : "jOXstHOM20qemPbHbyzf7ztZ7rI/UGMoPHmzXYV4bkgEyHBiWPUaz-c"
         * id : {"kind":"youtube#video","videoId":"mGCpd2908Ys"}
         * snippet : {"publishedAt":"2013-04-11T08:16:57.000Z","channelId":"UCwr3_8hNxNQKJz1uRVd6Jrw","title":"Extra English episode 1 Hectors arrival (part2)","description":"Eso es un series de vídeos que podría ayudarte mucho, estilo de Friends pero en inglés Británico. Los primeros están subtitulado para ayudar como hablamos en inglés, para comprender y para cuando llegas el punto de escribir tienes un idea como deletrearlo.","thumbnails":{"default":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/default.jpg"},"medium":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/mqdefault.jpg"},"high":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/hqdefault.jpg"}},"channelTitle":"Christopher Britt","liveBroadcastContent":"none"}
         */

        private String kind;
        private String etag;
        private IdEntity id;
        @SerializedName("snippet")
        private SnippetEntity snippet;

        @Data
        public class IdEntity {
            /**
             * kind : youtube#video
             * videoId : mGCpd2908Ys
             */
            @SerializedName("kind")
            private String kind;
            @SerializedName("videoId")
            private String videoId;

        }

        @Data
        public class SnippetEntity {
            /**
             * publishedAt : 2013-04-11T08:16:57.000Z
             * channelId : UCwr3_8hNxNQKJz1uRVd6Jrw
             * title : Extra English episode 1 Hectors arrival (part2)
             * description : Eso es un series de vídeos que podría ayudarte mucho, estilo de Friends pero en inglés Británico. Los primeros están subtitulado para ayudar como hablamos en inglés, para comprender y para cuando llegas el punto de escribir tienes un idea como deletrearlo.
             * thumbnails : {"default":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/default.jpg"},"medium":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/mqdefault.jpg"},"high":{"url":"https://i.ytimg.com/vi/mGCpd2908Ys/hqdefault.jpg"}}
             * channelTitle : Christopher Britt
             * liveBroadcastContent : none
             */
            @SerializedName("publishedAt")
            private String publishedAt;
            @SerializedName("channelId")
            private String channelId;
            private String title;
            private String description;
            private ThumbnailsEntity thumbnails;
            @SerializedName("channelTitle")
            private String channelTitle;
            private String liveBroadcastContent;

            @Data
            public class ThumbnailsEntity {
                /**
                 * default : {"url":"https://i.ytimg.com/vi/mGCpd2908Ys/default.jpg"}
                 * medium : {"url":"https://i.ytimg.com/vi/mGCpd2908Ys/mqdefault.jpg"}
                 * high : {"url":"https://i.ytimg.com/vi/mGCpd2908Ys/hqdefault.jpg"}
                 */

                @SerializedName("default")
                private DefaultEntity defaultX;
                private MediumEntity medium;
                private HighEntity high;

                @Data
                public class DefaultEntity {
                    /**
                     * url : https://i.ytimg.com/vi/mGCpd2908Ys/default.jpg
                     */

                    private String url;
                }

                @Data
                public class MediumEntity {
                    /**
                     * url : https://i.ytimg.com/vi/mGCpd2908Ys/mqdefault.jpg
                     */

                    private String url;

                }

                @Data
                public class HighEntity {
                    /**
                     * url : https://i.ytimg.com/vi/mGCpd2908Ys/hqdefault.jpg
                     */

                    private String url;
                }
            }
        }
    }
}
