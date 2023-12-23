package logonedigital.webappapi.utils;

import com.github.slugify.Slugify;

public class Tool {
    public static String slugify(String field){
        final Slugify slg = Slugify.builder().build();
        return slg.slugify(field);
    }
}
