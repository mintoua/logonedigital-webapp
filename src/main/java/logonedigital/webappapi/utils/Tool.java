package logonedigital.webappapi.utils;

import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.safety.Safelist;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import org.jsoup.Jsoup;




import java.util.UUID;

@AllArgsConstructor
public class Tool {


    public static String slugify(String field){
        final Slugify slg = Slugify.builder().build();
        return slg.slugify(field);
    }

    public static String generateFileName(MultipartFile file){
        return UUID.randomUUID()+"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
    }

    public static String cleanIt(String arg0) {

        return Jsoup.clean(StringEscapeUtils.escapeHtml4(StringEscapeUtils.escapeHtml4(arg0)), Safelist.basic());
    }



}
