package application.api.initializing;

import application.data.utils.converters.CustomPropertySourceConverter;
import application.data.utils.loaders.CustomPropertyDataLoader;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GoogleDriveManager {
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

	public Drive getInstance() throws GeneralSecurityException, IOException {
		Map<String , String> driveAPIProperties =
				CustomPropertySourceConverter.convertToKeyValueFormat
						(CustomPropertyDataLoader.getResourceContent("classpath:gdrive/drive.properties"));
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(driveAPIProperties.get("application_name"))
				.build();
	}

	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		Map<String , String> driveAPIProperties =
				CustomPropertySourceConverter.convertToKeyValueFormat
						(CustomPropertyDataLoader.getResourceContent("classpath:gdrive/drive.properties"));
		InputStream in = GoogleDriveManager.class.getResourceAsStream(driveAPIProperties.get("credentials_path"));
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + driveAPIProperties.get("credentials_path"));
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(driveAPIProperties.get("tokens_path"))))
				.setAccessType(driveAPIProperties.get("access"))
				.build();
		Map<String , String> env = System.getenv();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder()
				.setHost(env.get("GDRIVE_HOST"))
				.setPort(Integer.parseInt(env.get("GDRIVE_PORT")))
				.build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
}