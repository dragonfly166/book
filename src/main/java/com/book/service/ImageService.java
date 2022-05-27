package com.book.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunlongfei
 */
@Service
public class ImageService {

    @Value("${server.port}")
    private int port;

    /**
     * 保存文件
     */
    public String saveImage(MultipartFile image) throws IOException {
        File file = new File("image/");
        file.mkdirs();
        FileOutputStream out = new FileOutputStream("image/" + image.getOriginalFilename());
        out.write(image.getBytes());
        out.flush();
        return "http://127.0.0.1:" + port + "/image/" + image.getOriginalFilename();
    }
}
