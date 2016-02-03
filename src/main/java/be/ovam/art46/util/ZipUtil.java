package be.ovam.art46.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	
	public List<ZipEntry> getFilesFromZip(InputStream zipFile) {
		ZipInputStream zipInputStream = new ZipInputStream(zipFile);
		try {
			List<ZipEntry> zipEntries = new ArrayList<ZipEntry>();
			java.util.zip.ZipEntry entry = null;
			while ((entry = zipInputStream.getNextEntry()) != null) {
				 if (!entry.isDirectory()) {	
					 ByteArrayOutputStream content = new ByteArrayOutputStream();
					 IOUtils.copy(zipInputStream, content);
					zipEntries.add(new ZipEntry(content.toByteArray() , entry.getName())); 
				 }				
				zipInputStream.closeEntry();				
			 }
			return zipEntries;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public byte[] createZipFile(List<ZipEntry> zipEntries) {
		if (zipEntries != null && !zipEntries.isEmpty()) {
			ByteArrayOutputStream zipContent = new ByteArrayOutputStream();
			ZipOutputStream zipOutputStream = new ZipOutputStream(zipContent);
			for (ZipEntry zipEntry : zipEntries) {
				try {
					zipOutputStream.putNextEntry(new java.util.zip.ZipEntry(zipEntry.getFileName()));
					zipOutputStream.write(zipEntry.getContent());
					zipOutputStream.flush();
					zipOutputStream.closeEntry();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}			
			try {
				zipOutputStream.finish();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return zipContent.toByteArray();
		}		
		return null;
	}
	

}
