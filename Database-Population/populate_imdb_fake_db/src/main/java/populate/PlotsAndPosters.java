package populate;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.DatabaseHandling;
import utilities.Rutines;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlotsAndPosters {

    private static final String keyName = "x-rapidapi-key";
    private static final String hostName = "x-rapidapi-host";

    private static final String keyValue = "6fae38f5d6msh5243996cd2a9653p122166jsnc11c3ed7fb29";
    private static final String hostValue = "imdb-internet-movie-database-unofficial.p.rapidapi.com";

    private static final String url = "https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/";

    public static Response requestFilm(OkHttpClient client, String titleId) throws IOException {


        Request request = new Request.Builder()
                .url(url + titleId)
                .get()
                .addHeader(keyName, keyValue)
                .addHeader(hostName, hostValue)
                .build();

        return client.newCall(request).execute();
    }

    public static List<String> getTitleIds(Statement statement) throws SQLException {

        ResultSet resultSet = statement.executeQuery(
                "select title_id from titles " +
                "where votes between 100 and 650 " +
                "order by votes desc, rating desc"
        );

        List<String> titleIds = new ArrayList<String>();

        while(resultSet.next()) {

            titleIds.add(resultSet.getString("title_id"));
        }

        return titleIds;
    }

    public static String getPoster(String titleInfo) throws IOException {

        Pattern pattern = Pattern.compile("\"poster\":\"(.+?)\"");
        Matcher matcher = pattern.matcher(titleInfo);

        if(matcher.find()) {

            return matcher.group(1);
        }

        return null;
    }

    public static String getPlot(String titleInfo) throws IOException {

        Pattern pattern = Pattern.compile("\"plot\":\"(.+)\",\"trailer\"");
        Matcher matcher = pattern.matcher(titleInfo);

        if(matcher.find()) {

            return matcher.group(1);
        }

        return null;
    }

    public static void fetchPlotsAndPosters(Connection con, Statement statement) throws SQLException {

        OkHttpClient client = new OkHttpClient();
        Response response;

        String plot = null, poster = null, titleInfo;

        List<String> titleIds = getTitleIds(statement);

        Populate.displayTime();

        int count = 0;

        for(String titleId : titleIds) {

            count++;

            if(count % 500 == 0) {
                Rutines.commitAndShowMessage(con, count);
            }

            try {
                response = requestFilm(client, titleId);
            } catch (Exception e) {
                response = null;
            }

            if(response != null) {

                try {
                    titleInfo = response.body().string();
                    //System.out.println(titleInfo);

                    try {
                        poster = getPoster(titleInfo).replace("\\", "");
                    } catch (Exception e) {
                        //System.out.println("NOOOOPE POOOOSTER");
                    }

                    try {
                        plot = getPlot(titleInfo);
                        plot = plot.replace("'", "\\'");
                    } catch (Exception e) {
                        //System.out.println("NOOOOPE PLOOOOOT");
                    }

                    //System.out.println(plot);

                    String fuckingQuery;
                    if(poster != null && plot != null) {
                        fuckingQuery = "update titles set plot = '%s', poster = '%s' where title_id='%s'";
                        try {
                            fuckingQuery = String.format(fuckingQuery, plot, poster, titleId);
                            statement.execute(fuckingQuery);
                        } catch (Exception e){
                            e.printStackTrace();
                            System.out.println(fuckingQuery);
                            System.out.println(plot);
                        }
                    } else if(poster != null) {
                        fuckingQuery = "update titles set poster='%s' where title_id='%s'";
                        try {
                            statement.execute(
                                    fuckingQuery = String.format(fuckingQuery, poster, titleId)
                            );
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    } else if(plot != null) {
                        fuckingQuery = "update titles set plot = \"%s\" where title_id='%s'";
                        try {
                            statement.execute(
                                    String.format(fuckingQuery, plot, titleId)
                            );
                        } catch (Exception e){
                            e.printStackTrace();
                            System.out.println(plot);
                        }
                    }

                } catch (Exception e) {
                    System.out.println("INFO NOT FETCHED");
                }

            }
        }

        try {
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Populate.displayTime();
        System.out.println(count);

        DatabaseHandling.closeConnection(con, statement);
    }
}
