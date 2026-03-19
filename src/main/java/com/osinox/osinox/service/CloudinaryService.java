package com.osinox.osinox.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder}")
    private String folder;

    // Sube imagen y devuelve la URL pública
    public String subirImagen(MultipartFile archivo) throws IOException {
        Map<?, ?> resultado = cloudinary.uploader().upload(
                archivo.getBytes(),
                ObjectUtils.asMap(
                        "folder",           folder,
                        "resource_type",    "image",
                        "allowed_formats",  new String[]{"jpg", "jpeg", "png", "webp"}
                )
        );
        return resultado.get("secure_url").toString();
    }

    // Elimina imagen por su public_id (para cuando se reemplaza o elimina producto)
    public void eliminarImagen(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    // Extrae el public_id desde la URL de Cloudinary
    // Ejemplo URL: https://res.cloudinary.com/cloud/image/upload/v123/osinox/productos/abc.jpg
    // public_id resultado: osinox/productos/abc
    public String extraerPublicId(String url) {
        if (url == null || url.isBlank()) return null;
        try {
            String sinExtension = url.substring(0, url.lastIndexOf('.'));
            return sinExtension.substring(sinExtension.indexOf(folder));
        } catch (Exception e) {
            return null;
        }
    }
}