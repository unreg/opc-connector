/*=========================================================================
 * Copyright (c) 2010-2014 Pivotal Software, Inc. All rights reserved.
 * This product is protected by U.S. and international copyright
 * and intellectual property laws. Pivotal products are covered by
 * one or more patents listed at http://www.gopivotal.com/patents.
 *=========================================================================
 */
package com.gopivotal.tola.opc.boot;

import java.io.File;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main - the spring boot application
 * 
 * @author mborges
 *
 */
@ComponentScan({ "com.gopivotal.tola.opc", "com.gopivotal.tola.opc.boot" })
@SpringBootApplication
public class Main {

	public static void main(String[] args) throws Exception {
		SpringApplication spa = new SpringApplication(Main.class);
		
		readManifest();

		spa.setWebEnvironment(false);
		spa.run(args);

	}

	private static void readManifest() {
		try {
			Properties props = System.getProperties();
			String classpath = props.getProperty("java.class.path");
			String userdir = props.getProperty("user.dir");
			String pathtojar;
			// Now, if there are no File.separators in the path, then the jar
			// was run from the jar's location on the HD
			// if there are File.separators then it was run from another place
			// on the HD
			if (classpath.indexOf(File.separator) != -1)
				pathtojar = classpath;
			else
				pathtojar = userdir + File.separator + classpath;
			// Now we have the full path to the jar, print it to see
			System.out.println("Path To Jar = " + pathtojar);
			JarFile jar = new JarFile(pathtojar);
			Manifest man = jar.getManifest();
			man.write(System.out);
			Attributes attr = man.getMainAttributes();
			// now you have the manifest - you can get any of the
			// attributes you put in the jar - Version, Author, etc.
			System.out.println(attr.getValue("Main-Class"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
