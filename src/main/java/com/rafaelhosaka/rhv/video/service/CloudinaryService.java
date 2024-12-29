package com.rafaelhosaka.rhv.video.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CloudinaryService {
    @Autowired
    private Environment environment;

    public void upload (byte[] fileBytes, String folderName, String fileName, String type) throws IOException {
        Cloudinary cloudinary = new Cloudinary(environment.getProperty("CLOUDINARY_URL"));
        var map = ObjectUtils.asMap(
                "overwrite", true,
                "folder", folderName,
                "resource_type", type,
                "public_id", fileName
        );
        cloudinary.uploader().upload(fileBytes, map);
    }
}
