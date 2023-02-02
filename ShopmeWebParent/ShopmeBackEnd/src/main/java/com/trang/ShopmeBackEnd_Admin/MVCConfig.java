package com.trang.ShopmeBackEnd_Admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// User Upload Image
		String dirName = "user-photos";
		Path userPhotoDir = Paths.get(dirName);

		String userPhotosPath = userPhotoDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + userPhotosPath + "/");

		// Category Upload Image
		String categoryImagesDirName = "../category-images";
		Path categoryImageDir = Paths.get(categoryImagesDirName);

		String categoryImagesPath = categoryImageDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/category-images/**").addResourceLocations("file:/" + categoryImagesPath + "/");

		// Brand Upload Image
		String brandLogosDirName = "../brand-logos";
		Path brandLogosDir = Paths.get(brandLogosDirName);

		String brandLogosPath = brandLogosDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/brand-logos/**").addResourceLocations("file:/" + brandLogosPath + "/");
	}

}
