package byc.avt.udemyjetpack.util;

import byc.avt.udemyjetpack.BuildConfig;

public class ApiHelper {
    private static String base_url = BuildConfig.BASE_URL;
    private static String api_key = BuildConfig.API_KEY;

    public static String discover_tv_shows() {
        return base_url + "/discover/tv?api_key=" + api_key + "&language=en-US";
    }
    public static String get_tv_genres(String id){
        return base_url + "/tv/" + id + "?api_key=" + api_key + "&language=en-US";
    }

    public static String get_cast_tv(String id) {
        return base_url + "/tv/"+ id +"/credits?api_key=" + api_key;
    }
}
