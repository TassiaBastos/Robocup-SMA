package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		ComandoTime Sub7 = new ComandoTime();
		if (isProcessRunning()) {
			Sub7.launchTeam();
		} else {
			Sub7.launchTeamAndServer();

		}

	}

	private static boolean isProcessRunning() throws IOException {
		String line;
		String pidInfo = "";
		Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\tasklist.exe");
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = input.readLine()) != null) {
			pidInfo += line;
		}
		input.close();
		if (pidInfo.contains("rcssserver.exe"))
			return true;
		return false;
	}
}
