package org.sleeksnap.uploaders;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import org.sleeksnap.upload.ByteStreamUpload;
import org.sleeksnap.upload.Upload;
import org.sleeksnap.upload.types.ImageUpload;
import org.sleeksnap.upload.types.TextUpload;
import org.sleeksnap.upload.types.URLUpload;
import org.sleeksnap.uploaders.types.FileUploader;
import org.sleeksnap.uploaders.types.ImageUploader;
import org.sleeksnap.uploaders.types.TextUploader;
import org.sleeksnap.uploaders.types.URLUploader;
import org.sleeksnap.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * An Uploader repository of sorts, containing information about all possible uploaders along with their instances.
 *
 * @author Nikki
 */
public class UploaderRegistry {

	/**
	 * A map of Uploader Type -> Upload Type
	 */
	private static Map<Class<? extends Uploader>, Class<? extends Upload>> uploadClasses = new HashMap<>();

	/**
	 * Populate the uploader type -> upload type map
	 */
	static {
		uploadClasses.put(FileUploader.class, ByteStreamUpload.class);
		uploadClasses.put(ImageUploader.class, ImageUpload.class);
		uploadClasses.put(TextUploader.class, TextUpload.class);
		uploadClasses.put(URLUploader.class, URLUpload.class);
	}

	/**
	 * A Multimap containing mappings of Upload types to Uploaders
	 */
	private Multimap<Class<? extends Upload>, UploaderContainer> uploaders = LinkedHashMultimap.create();

	/**
	 * Register an uploader by automatically detecting what types it can upload
	 *
	 * @param uploader The uploader to register
	 */
	public void register(UploaderContainer uploader) {
		List<Class<? extends Upload>> uploadTypes = new LinkedList<>();

		for (Class<?> cl : uploader.getClass().getInterfaces()) {
			if (ClassUtils.hasInterface(cl, Uploader.class)) {
				uploadTypes.add(uploadClasses.get(cl));
			}
		}

		registerUploaderTypes(uploader, uploadTypes.toArray(new Class[uploadTypes.size()]));
	}

	/**
	 * Register an uploader for the specified types
	 *
	 * @param uploader The uploader to register
	 * @param types    The list of types to register it for
	 */
	public void registerUploaderTypes(UploaderContainer uploader, Class... types) {
		for (Class<? extends Upload> type : types) {
			uploaders.put(type, uploader);
		}
	}


	/**
	 * Get uploaders for the specified upload type
	 *
	 * @param type The upload type
	 * @return The Collection of uploaders
	 */
	public Collection<UploaderContainer> getUploadersFor(Class<? extends Upload> type) {
		return uploaders.get(type);
	}
}
