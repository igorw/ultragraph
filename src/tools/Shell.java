package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
	public static String exec(String command) throws IOException {
		Process proc = Runtime.getRuntime().exec(new String[] {"bash", "-c", command});
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		StringBuilder sb = new StringBuilder();
		
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		reader.close();
		
		return sb.toString();
	}
}
