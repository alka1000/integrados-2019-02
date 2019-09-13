package br.com.annahas.ultrassom.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public class UltrassomUtil {
	
	public static String multipartToString(final MultipartFormDataInput inputData) throws IOException {
		Map<String, List<InputPart>> dataMap = inputData.getFormDataMap();
		if (dataMap.get("ultrassom").size() > 0) {
			for (InputPart inPart : dataMap.get("ultrassom")) {
				
				MultivaluedMap<String,String> headers = inPart.getHeaders();
	
				String originalFileName = parseFileName(headers);
				String originalExtension = parseExtension(originalFileName);
//				String contentType = headers.getFirst("Content-Type");
				
				StringBuilder fileName = new StringBuilder();
	
				if (!originalExtension.isEmpty() && !originalExtension.equals(originalFileName))
					fileName.append(".").append(originalExtension);
							
				
				return generateStringFile(inPart.getBody(InputStream.class, Object.class));
				
			}
		}
		return null;
	}

	public static Blob multipartToBlob(final MultipartFormDataInput inputData) throws IOException, SQLException {
		Map<String, List<InputPart>> dataMap = inputData.getFormDataMap();
		if (dataMap.get("ultrassom").size() > 0) {
			for (InputPart inPart : dataMap.get("ultrassom")) {
				
				MultivaluedMap<String,String> headers = inPart.getHeaders();
	
				String originalFileName = parseFileName(headers);
				String originalExtension = parseExtension(originalFileName);
//				String contentType = headers.getFirst("Content-Type");
				
				StringBuilder fileName = new StringBuilder();
	
				if (!originalExtension.isEmpty() && !originalExtension.equals(originalFileName))
					fileName.append(".").append(originalExtension);
							
				
				return generateBlobFile(inPart.getBody(InputStream.class, Object.class));
				
			}
		}
		return null;
	}
	
	public static String parseFileName(MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("filename"))) {
                String[] tmp = name.split("=");
                String fileName = removerAcentos(tmp[1].trim().replaceAll("\"",""));
                
                return fileName;
            }
        }
        return "#ERRO";
    }
	
	public static String parseExtension(String originalFileName) {
		String[] split = originalFileName.split("[.]");
		return split[split.length - 1];
	}
	
	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static Blob generateBlobFile(InputStream uploadeInputStream) 
			throws IOException, SQLException {

		ByteArrayOutputStream bOs = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while ((nRead = uploadeInputStream.read(data)) != -1) {
			bOs.write(data, 0, nRead);
		}
		
		Blob blob = new SerialBlob(bOs.toByteArray());
		
		bOs.flush();
		bOs.close();
		
		return blob;
	}
	
	public static String generateStringFile(InputStream uploadeInputStream) 
			throws IOException {
		String conteudo = null;
		try (Scanner scanner = new Scanner(uploadeInputStream, "UTF-8")) {
			Scanner s = scanner.useDelimiter("\\A");
			conteudo = s.hasNext() ? s.next() : "";
		}
		return conteudo;
	}
}
