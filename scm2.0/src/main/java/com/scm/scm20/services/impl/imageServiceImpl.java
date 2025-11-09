package com.scm.scm20.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.scm20.hellper.AppConstents;
import com.scm.scm20.services.imageService;

@Service
public class imageServiceImpl implements imageService {

    private Cloudinary cloudinary;

    public imageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage , String filename) {


        // code for uploading image
     //    String filename = UUID.randomUUID().toString();
        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", filename ));
            return this.getUrlFromPublicId(filename);
            
      
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

        

        // return url
        
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
       return cloudinary.url().transformation(
        new Transformation<>()
        .width(AppConstents.CONTACT_IMAGE_WIDTH)
        .height(AppConstents.CONTACT_IMAGE_WIDTH)
        .crop(AppConstents.CONTACT_IMAGE_CROP)
       ).generate(publicId);
    }

   
}
