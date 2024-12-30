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

    private Cloudinary getCloudinary(){
        return new Cloudinary(environment.getProperty("CLOUDINARY_URL"));
    }

    public String upload (byte[] fileBytes, String folderName, String fileName, String type) throws IOException {
        var cloudinary = getCloudinary();
        var map = ObjectUtils.asMap(
                "folder", folderName,
                "resource_type", type,
                "public_id", fileName
        );
        var result = cloudinary.uploader().upload(fileBytes, map);
        return (String) result.get("url");
    }

    public String makeAuthenticated ( String fileName, String resourceType) throws Exception {
        var cloudinary = getCloudinary();
        var map = ObjectUtils.asMap(
                "type", "upload",
                "to_type", "authenticated",
                "resource_type" , resourceType,
                "invalidate" , true
        );
        var result = cloudinary.uploader().rename(fileName, fileName, map);
        return (String) result.get("url");
    }

    public String makePublic ( String fileName, String resourceType) throws Exception {
        var cloudinary = getCloudinary();
        var map = ObjectUtils.asMap(
                "type", "authenticated",
                "resource_type" , resourceType,
                "to_type", "upload"
        );
        var result = cloudinary.uploader().rename(fileName, fileName, map);
        return (String) result.get("url");
    }
}