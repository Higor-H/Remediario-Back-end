package br.edu.atitus.remediario.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dma5575ip",
            "api_key", "733379677865195",
            "api_secret", "R7S9Q9jftomPf9ZTRE-o2eNtU_Q"
        ));
    }

    public String uploadFile(byte[] fileData, String fileName) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(fileData, ObjectUtils.asMap("public_id", fileName));
        return uploadResult.get("secure_url").toString();
    }

    // Método para buscar a URL da imagem, caso necessário
    public String getImageUrl(String publicId) throws Exception {
        try {
            Map<String, Object> result = cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
            return result.get("secure_url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
