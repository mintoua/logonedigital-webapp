package logonedigital.webappapi.utils;

import com.github.slugify.Slugify;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class Tool {
    public static String slugify(String field){
        final Slugify slg = Slugify.builder().build();
        return slg.slugify(field);
    }

    public static String generateFileName(MultipartFile file){
        return UUID.randomUUID()+"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
    }

}
