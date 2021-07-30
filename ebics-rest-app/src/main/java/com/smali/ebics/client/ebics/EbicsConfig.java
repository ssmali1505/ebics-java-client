
package com.smali.ebics.client.ebics;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.ebics.client.api.Bank;
import org.ebics.client.api.EbicsModel;
import org.ebics.client.api.EbicsVersion;
import org.ebics.client.api.Partner;
import org.ebics.client.api.User;
import org.ebics.client.interfaces.Configuration;
import org.ebics.client.interfaces.PasswordCallback;
import org.ebics.client.messages.Messages;
import org.ebics.client.session.DefaultConfiguration;
import org.ebics.client.session.Product;

public class EbicsConfig {	
	 
	private Configuration configuration = null;
	private ConfigProperties properties = null;
	private final Messages messages = new Messages();
	private Product defaultProduct;
	private User defaultUser;
	private EbicsModel ebicsModel;

    private static EbicsConfig single_instance = null;

    public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public ConfigProperties getProperties() {
		return properties;
	}

	public void setProperties(ConfigProperties properties) {
		this.properties = properties;
	}

	public Product getDefaultProduct() {
		return defaultProduct;
	}

	public void setDefaultProduct(Product defaultProduct) {
		this.defaultProduct = defaultProduct;
	}

	public User getDefaultUser() {
		return defaultUser;
	}

	public void setDefaultUser(User defaultUser) {
		this.defaultUser = defaultUser;
	}

	public EbicsModel getEbicsModel() {
		return ebicsModel;
	}

	public void setEbicsModel(EbicsModel ebicsModel) {
		this.ebicsModel = ebicsModel;
	}

	public static EbicsConfig getInstance()
    {
        if (single_instance == null)
            single_instance = new EbicsConfig();  
        return single_instance;
    }

	public EbicsConfig() {
        init();
    }

    private PasswordCallback createPasswordCallback() {
		final String password = properties.get("password");
		return new PasswordCallback() {

			@Override
			public char[] getPassword() {
				return password.toCharArray();
			}
		};
	}

	private User createUser(ConfigProperties properties, PasswordCallback pwdHandler) throws Exception {
		String userId = properties.get("userId");
		String partnerId = properties.get("partnerId");
		String bankUrl = properties.get("bank.url");
		String bankName = properties.get("bank.name");
		String hostId = properties.get("hostId");
		String userName = properties.get("user.name");
		String userEmail = properties.get("user.email");
		String userCountry = properties.get("user.country");
		String userOrg = properties.get("user.org");

		EbicsVersion ebicsVersion = EbicsVersion.valueOf(properties.get("ebicsVersion"));
		boolean useCertificates = false;

		if (properties.get("useCertificates") != null && properties.get("useCertificates").equalsIgnoreCase("true")) {
			useCertificates = true;
		}

		if (ebicsVersion == EbicsVersion.H005) {
			useCertificates = true;
		}

		return ebicsModel.createUser(new URL(bankUrl), ebicsVersion, bankName, hostId, partnerId, userId, userName,
				userEmail, userCountry, userOrg, useCertificates, true, pwdHandler);
	}

	public void createDefaultUser() throws Exception {
		defaultUser = createUser(properties, createPasswordCallback());
	}

	public void loadDefaultUser() throws Exception {
		String userId = properties.get("userId");
		String hostId = properties.get("hostId");
		String partnerId = properties.get("partnerId");
		defaultUser = ebicsModel.loadUser(hostId, partnerId, userId, createPasswordCallback());
	}

	public void init() {
		try {

			File defaultRootDir = new File(
					System.getProperty("user.home") + File.separator + "ebics" + File.separator + "client");
			File ebicsClientProperties = new File(defaultRootDir, "ebics.txt");
			System.out.println("ebicsClientProperties:"+ebicsClientProperties.getAbsolutePath());
			
			properties = new ConfigProperties(ebicsClientProperties);
			final String country = properties.get("countryCode").toUpperCase();
			final String language = properties.get("languageCode").toLowerCase();
			final String productName = properties.get("productName");

			System.out.println("country:"+country);
			
			final Locale locale = new Locale(language, country);

			configuration = new DefaultConfiguration(defaultRootDir.getAbsolutePath()) {

				@Override
				public Locale getLocale() {
					return locale;
				}
			};
		

			defaultProduct = new Product(productName, language, null);

			ebicsModel = new EbicsModel(configuration);

			ebicsModel.saveAll();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}