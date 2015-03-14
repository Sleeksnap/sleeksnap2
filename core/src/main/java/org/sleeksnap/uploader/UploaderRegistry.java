package org.sleeksnap.uploader;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import org.sleeksnap.upload.ByteStreamUpload;
import org.sleeksnap.upload.Upload;
import org.sleeksnap.upload.types.ImageUpload;
import org.sleeksnap.upload.types.TextUpload;
import org.sleeksnap.upload.types.URLUpload;
import org.sleeksnap.uploader.types.FileUploader;
import org.sleeksnap.uploader.types.ImageUploader;
import org.sleeksnap.uploader.types.TextUploader;
import org.sleeksnap.uploader.types.TypeUploader;
import org.sleeksnap.uploader.types.URLUploader;
import org.sleeksnap.util.ClassUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * An Uploader repository of sorts, containing information about all possible uploaders along with their instances.
 *
 * @author Nikki
 */
public class UploaderRegistry {

	/**
	 * A map of Uploader Type -> Upload Type
	 */
	private static Map<Class<? extends TypeUploader>, Class<? extends Upload>> uploadClasses = new HashMap<>();

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
	private Multimap<Class<? extends Upload>, Uploader> uploaders = LinkedHashMultimap.create();

	/**
	 * Register an uploader by automatically detecting what types it can upload
	 *
	 * @param uploader The uploader to register
	 */
	public void register(Uploader uploader) {
		List<Class<? extends Upload>> uploadTypes = new LinkedList<>();

		for (Class cl : uploader.getClass().getInterfaces()) {
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
	public void registerUploaderTypes(Uploader uploader, Class... types) {
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
	public Collection<Uploader> getUploadersFor(Class<? extends Upload> type) {
		return uploaders.get(type);
	}
}
